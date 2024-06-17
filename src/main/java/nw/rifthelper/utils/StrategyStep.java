package nw.rifthelper.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.function.Function;
import net.runelite.api.Client;

@AllArgsConstructor
public class StrategyStep
{
    @Getter
    private String stepText;
    private Function<Client, Boolean> stepChecker;

    public boolean isStepComplete(Client client)
    {
        return stepChecker.apply(client);
    }
}
