package nw.rifthelper;

public interface StrategyRequirement
{
    boolean isRequirementSatisfied(GuardiansOfTheRiftHelperSession session);
    String getPrintableRequirementText();
}
