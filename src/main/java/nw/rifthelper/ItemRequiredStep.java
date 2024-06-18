package nw.rifthelper;

import lombok.Getter;
import net.runelite.api.Item;

public class ItemRequiredStep implements StrategyRequirement
{
    private String stepText;
    private final Item requiredItem;
    @Getter
    private final GameStages requiredDuringStage;

    @Override
    public boolean isRequirementSatisfied(GuardiansOfTheRiftHelperSession session) {
        return session.getCurrentInventory().contains(requiredItem.getId())
                && session.getCurrentInventory().count(requiredItem.getId()) >= requiredItem.getQuantity();
    }

    @Override
    public String getPrintableRequirementText() {
        return "Obtain " + requiredItem.getQuantity() + "x " + requiredItem.toString();
    }

    @Override
    public String toString() {
        return getPrintableRequirementText();
    }

    public ItemRequiredStep(Item requiredItem, GameStages requiredDuringStage)
    {
        this.requiredItem = requiredItem;
        this.requiredDuringStage = requiredDuringStage;
    }
}
