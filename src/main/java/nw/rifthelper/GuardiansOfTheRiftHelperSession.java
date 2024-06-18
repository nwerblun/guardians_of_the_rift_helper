package nw.rifthelper;

import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.ItemContainer;

public class GuardiansOfTheRiftHelperSession
{
    /*
    Holds stats about the current/last round/since login/reset
    1. has a method called update that takes last interacted actor and current inventory
    2. has a strategy instance
    3.
    4. has a method to get an iterator of things to print and the completion status for the overlay?
     */

    @Setter
    private ItemContainer currentInventory;
    private final Client client;

    public GuardiansOfTheRiftHelperSession(Client client)
    {
        this.client = client;
    }

    public ItemContainer getCurrentInventory()
    {
        if (currentInventory != null)
        {
            return currentInventory;
        }
        return null;
    }
}
