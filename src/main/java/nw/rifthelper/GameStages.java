package nw.rifthelper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GameStages
{
    NOT_IN_AREA("Not in area", 99999),
    PREGAME("pregame", 999999),
    ALTAR_OPEN_NO_PORTAL("", 99999),
    PORTAL_OPEN("", 99999);

    private String stageName;
    private int stageDurationSeconds;
    // chat message marking start ?
    // time of stage?

    //function to map chat message to stage
}
