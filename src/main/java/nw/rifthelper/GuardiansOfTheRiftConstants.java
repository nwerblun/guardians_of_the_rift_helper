package nw.rifthelper;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class GuardiansOfTheRiftConstants
{
    public static final String DISMISS_OVERLAY = "Dismiss";
    public static final String RESET_OVERLAY = "Reset Stats";
    public static final String OVERLAY_NAME = "GotR Overlay";
    public static final int SECONDS_LEFT_TO_DISPLAY_TIME_RED_TEXT = 10;
    public static final int SECONDS_LEFT_TO_DISPLAY_TIME_YELLOW_TEXT = 20;

    public static final int WEAK_CELL_ID = 26883;
    public static final int UNCHARGED_CELL_ID = 26882;
    public static final int ESSENCE_FRAGMENTS_ID = 26878;
    public static final int GUARDIAN_ESSENCE_ID = 26879;

    public static final Map<Integer, String> itemIdMap;
    static
    {
        ImmutableMap.Builder<Integer, String> builder = new ImmutableMap.Builder<>();
        builder.put(WEAK_CELL_ID, "Weak Cell");
        builder.put(UNCHARGED_CELL_ID, "Uncharged Cell");
        builder.put(ESSENCE_FRAGMENTS_ID, "Essence Fragments");
        builder.put(GUARDIAN_ESSENCE_ID, "Guardian Essence");

        itemIdMap = builder.build();
    }
}
