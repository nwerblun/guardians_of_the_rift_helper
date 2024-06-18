package nw.rifthelper;

import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.Arrays;
import net.runelite.api.Item;

@AllArgsConstructor
public enum Strategies
{
    SOLO_MAX_REWARDS(new ArrayList(Arrays.asList(
            new ItemRequiredStep(new Item(0, 0), GameStages.PREGAME),
            new ItemRequiredStep(new Item(0, 0), GameStages.PREGAME)
    )));

    private final ArrayList<StrategyRequirement> steps;
}
