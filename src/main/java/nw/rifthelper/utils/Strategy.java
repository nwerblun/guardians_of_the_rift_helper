package nw.rifthelper.utils;

import net.runelite.api.Client;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;

public class Strategy implements Iterable<StrategyStep>
{
    private final String strategyName;
    private final ArrayList<StrategyStage> strategyStages;
    private int currentStage = 0;

    public StrategyStage getCurrentStage(Client client)
    {
        if (strategyStages.isEmpty())
        {
            return null;
        }

        if (strategyStages.get(currentStage).isStageComplete(client) && currentStage < strategyStages.size())
        {
            currentStage++;
        }
        else if (currentStage == strategyStages.size())
        {
            return strategyStages.get(strategyStages.size() - 1);
        }

        return strategyStages.get(currentStage);
    }

    public Strategy(StrategyBuilder builder)
    {
        this.strategyStages = builder.strategyStages;
        this.strategyName = builder.strategyName;
    }

    @Override
    @Nonnull
    public Iterator<StrategyStep> iterator()
    {
        return strategyStages.get(currentStage).iterator();
    }

    @Override
    public String toString() {
        return "Strategy: " + strategyName + " with " + strategyStages.size() + " stages.";
    }

    public static class StrategyBuilder
    {
        private String strategyName;
        private ArrayList<StrategyStage> strategyStages = new ArrayList<>();

        public StrategyBuilder addStage(StrategyStage stage)
        {
            this.strategyStages.add(stage);
            return this;
        }

        public StrategyBuilder strategyName(String name)
        {
            this.strategyName = name;
            return this;
        }


        public Strategy build()
        {
            return new Strategy(this);
        }
    }
}
