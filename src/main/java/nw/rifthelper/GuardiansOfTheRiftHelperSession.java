package nw.rifthelper;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;

import javax.inject.Inject;
import java.time.Instant;

public class GuardiansOfTheRiftHelperSession
{
    /*
    Holds stats about the current/last round/since login/reset
    1. has a method called update that takes last interacted actor and current inventory
    2. has a strategy instance
    3.
    4. has a method to get an iterator of things to print and the completion status for the overlay?
     */

    private Actor lastInteracted;
    private final Client client;
    private GameStages currentStage;
    @Getter
    private Instant timeStageStarted;

    @Inject
    public GuardiansOfTheRiftHelperSession(Client client)
    {
        this.client = client;
        this.timeStageStarted = Instant.now();
        this.currentStage = GameStages.NOT_IN_AREA;
    }

    public GameStages getCurrentStage()
    {
        // placeholder until the logic exists
        return GameStages.PREGAME;
    }

    public ItemContainer getCurrentInventory()
    {
        return client.getItemContainer(InventoryID.INVENTORY);
    }

    public void processChatMessage(String message, Instant timeSent)
    {

    }
}
