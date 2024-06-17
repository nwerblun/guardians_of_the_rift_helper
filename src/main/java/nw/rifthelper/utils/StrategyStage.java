package nw.rifthelper.utils;

import lombok.Getter;
import net.runelite.api.Client;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;

@Getter
public class StrategyStage implements Iterable<StrategyStep>
{
    private final String stageName;
    private final ArrayList<StrategyStep> stageSteps;

    public StrategyStage(StrategyStageBuilder builder)
    {
        this.stageName = builder.stageName;
        this.stageSteps = builder.stageSteps;
    }

    public boolean isStageComplete(Client client)
    {
        for (StrategyStep step : stageSteps)
        {
            if (!step.isStepComplete(client))
            {
                return false;
            }
        }

        return true;
    }

    @Override
    @Nonnull
    public Iterator<StrategyStep> iterator() {
        return stageSteps.iterator();
    }

    public static class StrategyStageBuilder
    {
        private String stageName;
        private ArrayList<StrategyStep> stageSteps;

        public StrategyStageBuilder addStageTitle(String title)
        {
            this.stageName = title;
            return this;
        }

        public StrategyStageBuilder addStep(StrategyStep step)
        {
            this.stageSteps.add(step);
            return this;
        }

        public StrategyStage build()
        {
            return new StrategyStage(this);
        }
    }
}
