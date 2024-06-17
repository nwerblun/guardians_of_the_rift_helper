package nw.rifthelper.testutils_donotuse;


import net.runelite.api.Client;
import net.runelite.api.WorldView;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;
import nw.rifthelper.utils.GuardiansOfTheRiftZones;
import nw.rifthelper.GuardiansOfTheRiftHelperConfig;
import nw.rifthelper.GuardiansOfTheRiftHelperPlugin;

import javax.inject.Inject;
import java.awt.*;

import static net.runelite.api.MenuAction.RUNELITE_OVERLAY;
import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;

public class TestGuardiansOfTheRightHelperOverlay extends OverlayPanel
{
    private final Client client;
    private final GuardiansOfTheRiftHelperConfig config;
    private final GuardiansOfTheRiftHelperPlugin plugin;
    private static final String DISMISS_OVERLAY = "Dismiss";
    private static final String OVERLAY_NAME = "GotR Overlay";

    @Inject
    private TestGuardiansOfTheRightHelperOverlay(Client client, GuardiansOfTheRiftHelperConfig config,
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
        addMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, OVERLAY_NAME);
        addMenuEntry(RUNELITE_OVERLAY, DISMISS_OVERLAY, OVERLAY_NAME, e -> plugin.setOverlayActive(false));
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!plugin.isOverlayActive())
        {
            return null;
        }

        WorldArea playerWorldArea = client.getLocalPlayer().getWorldArea();
        WorldView playerWorldView = client.getLocalPlayer().getWorldView();
        WorldPoint playerWorldPoint = client.getLocalPlayer().getWorldLocation();

        // Begin test stuff
        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Worldpoint info")
                .color(Color.GREEN)
                .build()
        );
        panelComponent.getChildren().add(LineComponent.builder()
                .left("worldpoint region ID:")
                .right(Integer.toString(playerWorldPoint.getRegionID()))
                .preferredSize(new Dimension(300,300))
                .build()
        );
        panelComponent.getChildren().add(LineComponent.builder()
                .left("worldpoint plane:")
                .right(Integer.toString(playerWorldPoint.getPlane()))
                .build()
        );
        panelComponent.getChildren().add(LineComponent.builder()
                .left("worldpoint x:")
                .right(Integer.toString(playerWorldPoint.getX()))
                .build()
        );
        panelComponent.getChildren().add(LineComponent.builder()
                .left("worldpoint y:")
                .right(Integer.toString(playerWorldPoint.getY()))
                .build()
        );

        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Worldview info")
                .color(Color.GREEN)
                .build()
        );

        panelComponent.getChildren().add(LineComponent.builder()
                .left("worldview plane:")
                .right(Integer.toString(playerWorldView.getPlane()))
                .build()
        );


        String zone = "none";
        if (GuardiansOfTheRiftZones.WAITING_ROOM.getWorldArea().contains(playerWorldPoint))
        {
            zone = "waiting room";
        }
        else if (GuardiansOfTheRiftZones.CENTRAL_AREA.getWorldArea().contains(playerWorldPoint))
        {
            zone = "central area";
        }

        panelComponent.getChildren().add(LineComponent.builder()
                .left("player in zone: ")
                .right(zone)
                .build()
        );

        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Worldarea info")
                .color(Color.GREEN)
                .build()
        );
        panelComponent.getChildren().add(LineComponent.builder()
                .left("worldarea plane:")
                .right(Integer.toString(playerWorldArea.getPlane()))
                .build()
        );
        panelComponent.getChildren().add(LineComponent.builder()
                .left("worldview height:")
                .right(Integer.toString(playerWorldArea.getHeight()))
                .build()
        );
        panelComponent.getChildren().add(LineComponent.builder()
                .left("worldview width:")
                .right(Integer.toString(playerWorldArea.getWidth()))
                .build()
        );
        panelComponent.getChildren().add(LineComponent.builder()
                .left("worldview Southwestern most pt:")
                .right(Integer.toString(playerWorldArea.getX()) + "," + Integer.toString(playerWorldArea.getY()))
                .build()
        );
        return super.render(graphics);
    }
}
