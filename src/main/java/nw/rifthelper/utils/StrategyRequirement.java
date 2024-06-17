package nw.rifthelper.utils;

import java.util.Arrays;
import net.runelite.api.Actor;
import net.runelite.api.ItemContainer;

public class StrategyRequirement
{
    private final StrategyStep[] requirementSteps;
    private boolean[] completedSteps;
    // TODO: Figure out how to use this. Cancel all sub-steps if time has passed?
    private boolean timeSensitive;

    public StrategyRequirement(StrategyStep[] requirementSteps)
    {
       completedSteps = new boolean[requirementSteps.length];
       Arrays.fill(completedSteps, false);
       this.requirementSteps = requirementSteps;
    }

    public void checkAction(ItemContainer playerInventory)
    {
        for (int i = 0; i < requirementSteps.length; i++)
        {
            requirementSteps[i].checkAction(playerInventory);
            completedSteps[i] = requirementSteps[i].isComplete();
        }
    }

    public void checkAction(Actor interactedWith)
    {

    }

    public boolean requirementSatisfied()
    {
        for (boolean b : completedSteps) if (!b) return false;
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
