package nw.rifthelper;

import net.runelite.api.Item;
import javax.inject.Inject;
import java.time.Instant;

public class ItemRequiredStep implements StrategyRequirement
{
    private String stepText;
    private final Item requiredItem;
    private final int validFromTimeSeconds;
    private final int validUntilSeconds;
    private boolean completed = false;

    @Override
    public boolean isRequirementSatisfied(GuardiansOfTheRiftHelperSession session) {
        if (completed) return true;
        if (session.getCurrentInventory() == null) return false;

        completed = session.getCurrentInventory().contains(requiredItem.getId())
                && session.getCurrentInventory().count(requiredItem.getId()) >= requiredItem.getQuantity();
        return false;
    }

    @Override
    public String getPrintableRequirementText() {
        return "Obtain " + requiredItem.getQuantity() + "x " +
                GuardiansOfTheRiftConstants.itemIdMap.get(requiredItem.getId());
    }


    @Override
    public int timeRemainingToComplete(Instant started) {
        int timeInRound = Math.round(Instant.now().getEpochSecond() - started.getEpochSecond());
        if (timeInRound >= validUntilSeconds || timeInRound <= validFromTimeSeconds)
        {
            return -1;
        }

        return validUntilSeconds - timeInRound;
    }

    @Override
    public String toString() {
        return getPrintableRequirementText();
    }

    @Inject
    public ItemRequiredStep(Item requiredItem, int validFromTimeSeconds, int validUntilTimeSeconds)
    {
        this.requiredItem = requiredItem;
        this.validFromTimeSeconds = validFromTimeSeconds;
        this.validUntilSeconds = validUntilTimeSeconds;
    }
}
