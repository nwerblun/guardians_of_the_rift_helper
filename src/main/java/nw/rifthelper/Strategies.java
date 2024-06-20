package nw.rifthelper;

import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.Arrays;

import lombok.Getter;
import net.runelite.api.Item;

@AllArgsConstructor
@Getter
public enum Strategies
{
    // change to a map of game stage -> array of steps in that stage
    SOLO_MAXIMIZE_REWARDS(new ArrayList(Arrays.asList(
            // weak cell
            new ItemRequiredStep(new Item(GuardiansOfTheRiftConstants.WEAK_CELL_ID, 1),
                    0, 99999),
            // uncharged cell
            new ItemRequiredStep(new Item(GuardiansOfTheRiftConstants.UNCHARGED_CELL_ID, 10),
                    0, 99999),
            // frags
            new ItemRequiredStep(new Item(GuardiansOfTheRiftConstants.ESSENCE_FRAGMENTS_ID, 1),
                    0, 99999),
            // essence
            new ItemRequiredStep(new Item(GuardiansOfTheRiftConstants.GUARDIAN_ESSENCE_ID, 4),
                    0, 99999)
    ))),

    MASS_GROUP_MAXIMIZE_REWARDS(
            new ArrayList(Arrays.asList(
            new ItemRequiredStep(new Item(0, 0), 0, 35),
            new ItemRequiredStep(new Item(0, 0), 0, 35)
    )));

    private final ArrayList<StrategyRequirement> steps;
}
