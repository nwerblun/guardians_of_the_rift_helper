package nw.rifthelper.utils;

import lombok.Getter;
import net.runelite.api.Actor;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;

public class StrategyStep
{
    private final String stepInfo;
    private final Item requiredItem;
    private final Actor requiredActor;

    @Getter
    private boolean complete;

    public StrategyStep(Item requiredItem)
    {
        this.stepInfo = "Need " + requiredItem.getQuantity() + "x " + requiredItem ;
        complete = false;
        this.requiredItem = requiredItem;
        this.requiredActor = null;
    }

    public StrategyStep(Actor requiredActor)
    {
        this.stepInfo = "Interact with " + requiredActor.getName();
        complete = false;
        this.requiredItem = null;
        this.requiredActor = requiredActor;
    }

    public void checkAction(ItemContainer playerInventory)
    {
        if (playerInventory != null && requiredItem != null)
        {
            if (playerInventory.contains(requiredItem.getId()) &&
                    playerInventory.count(requiredItem.getId()) >= requiredItem.getQuantity())
            {
                complete = true;
            }
        }
    }

    public void checkAction(Actor interactedWith)
    {
        if (interactedWith != null && requiredActor != null)
        {
            if (interactedWith.getName() != null && interactedWith.getName().equals(requiredActor.getName()))
            {
                complete = true;
            }
        }
    }

    @Override
    public String toString() {
        return stepInfo;
    }
}
