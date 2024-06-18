package nw.rifthelper;

import java.util.List;
import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.coords.WorldArea;

@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
public enum GuardiansOfTheRiftZones
{
    WAITING_ROOM(new WorldArea(3602,9460,3626-3602,9483-9460,0)),
    CENTRAL_AREA(new WorldArea(3597,9484,3635-3597,9519-9484,0)),
    PORTAL_ZONE(new WorldArea(3588,9495,3589-3580 ,9511-9495,0)),
    AGILITY_ZONE(new WorldArea(3636,9495,3645-3636,9511-9495,0));

    private final WorldArea worldArea;
    public final static List<GuardiansOfTheRiftZones> zones;

    static
    {
        ImmutableList.Builder<GuardiansOfTheRiftZones> builder = new ImmutableList.Builder<>();
        for (GuardiansOfTheRiftZones zone : values())
        {
            builder.add(zone);
        }

        zones = builder.build();
    }
}
