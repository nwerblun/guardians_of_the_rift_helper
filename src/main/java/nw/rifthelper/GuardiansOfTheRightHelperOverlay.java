package nw.rifthelper;


import java.awt.*;
import java.time.Instant;
import javax.inject.Inject;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import static net.runelite.api.MenuAction.RUNELITE_OVERLAY;
import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;

@Slf4j
public class GuardiansOfTheRightHelperOverlay extends OverlayPanel
{
    private final Client client;
    private final GuardiansOfTheRiftHelperConfig config;
    private final GuardiansOfTheRiftHelperPlugin plugin;
    @Setter
    private GuardiansOfTheRiftHelperSession session;
    private Strategies strategy;

    @Inject
    private GuardiansOfTheRightHelperOverlay(Client client,
                                             GuardiansOfTheRiftHelperConfig config,
                                             GuardiansOfTheRiftHelperPlugin plugin)
    {
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        this.client = client;
        this.config = config;
        this.plugin = plugin;
        /*
        Overlay menus are opened by shift-right-clicking on the overlay
        Syntax is menuAction, Option, target
        menuAction = what clicking this choice actually does
        option = what the text says to the user. OPTION_CONFIGURE as of now is a string = "Configure"
        target = What it says you are doing it to. It will say "Configure <target>" and clicking will make the
        RuneLite configure menu for this plugin pop up on the right panel.
         */
        addMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, GuardiansOfTheRiftConstants.OVERLAY_NAME);
        addMenuEntry(RUNELITE_OVERLAY, GuardiansOfTheRiftConstants.DISMISS_OVERLAY,
                GuardiansOfTheRiftConstants.OVERLAY_NAME, e -> plugin.setOverlayActive(false));
        addMenuEntry(RUNELITE_OVERLAY, GuardiansOfTheRiftConstants.RESET_OVERLAY,
                GuardiansOfTheRiftConstants.OVERLAY_NAME, e -> plugin.setSession(null));
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!plugin.isOverlayActive())
        {
            return null;
        }
        pickStrategy();
        if (strategy == null)
        {
            panelComponent.getChildren().add(TitleComponent.builder()
            .text("Waiting for player to enter GotR Area")
            .color(Color.WHITE)
            .build());
            // Only happens when not in the area or in the waiting zone
            // display message to enter arena when ready and some stats.
            setPreferredSize(new Dimension(300, 300));
            return super.render(graphics);
        }


        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Strategy: " + strategy)
                .color(Color.WHITE)
                .build()
        );

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Current Step:")
                .right(session.getCurrentStage().getStageName())
                .build()
        );

        int timeLeftInStage = getTimeLeftInStageSeconds();

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Time remaining in stage:")
                .right(Integer.toString(timeLeftInStage))
                .rightColor(getTimeDisplayColor(timeLeftInStage))
                .build()
        );

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Required Action:")
                .right("Time Left to Complete")
                .build()
        );

        // loop through all steps in strategy, print them colored based on completion if they belong in current stage
        // display time until next stage
        for (StrategyRequirement step : strategy.getSteps())
        {
            if (step.timeRemainingToComplete(session.getTimeStageStarted()) > 0)
            {
                Color textColor = Color.WHITE;
                if (step.isRequirementSatisfied(session))
                {
                    textColor = Color.GREEN;
                }
                int timeLeft = step.timeRemainingToComplete(session.getTimeStageStarted());
                panelComponent.getChildren().add(LineComponent.builder()
                        .left(step.getPrintableRequirementText())
                        .leftColor(textColor)
                        .right(Integer.toString(timeLeft))
                        .rightColor(getTimeDisplayColor(timeLeft))
                        .build()
                );
            }
        }


        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Inventory Contents:")
                .color(Color.WHITE)
                .build()
        );

        ItemContainer playerInventory = client.getItemContainer(InventoryID.INVENTORY);
        if (playerInventory != null)
        {
            for (Item item : playerInventory.getItems())
            {
                panelComponent.getChildren().add(LineComponent.builder()
                        .left(item.toString())
                        .right(Integer.toString(item.getQuantity()))
                        .build()
                );
            }
        }
        setPreferredSize(new Dimension(300, 600));
        return super.render(graphics);
    }

    private Color getTimeDisplayColor(int timeToDisplay)
    {
        Color timeColor = Color.GREEN;
        if (timeToDisplay <= GuardiansOfTheRiftConstants.SECONDS_LEFT_TO_DISPLAY_TIME_RED_TEXT)
        {
            timeColor = Color.RED;
        }
        else if (timeToDisplay <= GuardiansOfTheRiftConstants.SECONDS_LEFT_TO_DISPLAY_TIME_YELLOW_TEXT)
        {
            timeColor = Color.YELLOW;
        }
        return timeColor;
    }

    private int getTimeLeftInStageSeconds()
    {
        return session.getCurrentStage().getStageDurationSeconds() -
                Math.round(Instant.now().getEpochSecond() - session.getTimeStageStarted().getEpochSecond());

    }

    private int getNumPlayersInArea()
    {
        // Possibly check if world view is null? Maybe not necessary
        if (client.getLocalPlayer().getWorldView().players() == null)
        {
            return 0;
        }
        return client.getLocalPlayer().getWorldView().players().getSize();
    }

    private boolean playerInCentralZone()
    {
        return GuardiansOfTheRiftZones.CENTRAL_AREA.getWorldArea()
                .contains(client.getLocalPlayer().getWorldLocation());
    }

    private boolean playerInWaitingRoom()
    {
        return GuardiansOfTheRiftZones.WAITING_ROOM.getWorldArea()
                .contains(client.getLocalPlayer().getWorldLocation());
    }

    private boolean hasRoundStarted()
    {
        return !(playerInWaitingRoom() || (session.getCurrentStage() == GameStages.PREGAME));
    }

    private void pickStrategy()
    {

        Player localPlayer = client.getLocalPlayer();
        // player is in waiting room
        if (playerInWaitingRoom())
        {
            strategy = null;
        }
        // player is in central zone and num players > 10 and round has not started
        else if (playerInCentralZone() && getNumPlayersInArea() >= 10 && !hasRoundStarted())
        {
            // Placeholder until I make actual strategies
            strategy = Strategies.MASS_GROUP_MAXIMIZE_REWARDS;
        }
        // Player in central zone, num players < 5 and round has not started
        else if (playerInCentralZone() && getNumPlayersInArea() <= 5 && !hasRoundStarted())
        {
            strategy = Strategies.SOLO_MAXIMIZE_REWARDS;
        }
        else if (playerInCentralZone() && getNumPlayersInArea() >= 10 && hasRoundStarted())
        {
            strategy = Strategies.MASS_GROUP_MAXIMIZE_REWARDS;
        }

    }
}
