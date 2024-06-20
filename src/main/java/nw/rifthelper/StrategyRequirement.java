package nw.rifthelper;

import java.time.Instant;

public interface StrategyRequirement
{
    boolean isRequirementSatisfied(GuardiansOfTheRiftHelperSession session);
    int timeRemainingToComplete(Instant started);
    String getPrintableRequirementText();
}
