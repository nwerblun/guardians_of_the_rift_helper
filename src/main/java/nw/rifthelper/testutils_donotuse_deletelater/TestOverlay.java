package nw.rifthelper.testutils_donotuse_deletelater;

import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayUtil;
import nw.rifthelper.GuardiansOfTheRiftHelperConfig;
import nw.rifthelper.GuardiansOfTheRiftHelperPlugin;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TestOverlay extends Overlay
{

    private final Client client;
    private final GuardiansOfTheRiftHelperPlugin plugin;
    private final GuardiansOfTheRiftHelperConfig config;
    private static final int MAX_RENDER_DISTANCE = 50;

    private final WorldArea testWaitingZone = new WorldArea(3602,9460,3626-3602,9483-9460,0);
    private final WorldArea testCentralZone = new WorldArea(3597,9484,3635-3597,9519-9484,0);
    private final WorldArea testPortalZone = new WorldArea(3588,9495,3589-3580 ,9511-9495,0);
    private final WorldArea testAgilityZone = new WorldArea(3636,9495,3645-3636,9511-9495,0);

    private final ArrayList<WorldArea> zones = new ArrayList<>(Arrays.asList(
            testWaitingZone,
            testCentralZone,
            testPortalZone,
            testAgilityZone
    ));

    @Inject
    private TestOverlay (Client client, GuardiansOfTheRiftHelperPlugin plugin,
                         GuardiansOfTheRiftHelperConfig config)
    {
        this.plugin = plugin;
        this.client = client;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        WorldPoint playerLocation = client.getLocalPlayer().getWorldLocation();

        for (WorldArea zone : zones)
        {
            if (zone.contains(playerLocation) &&
                    zone.getPlane() == playerLocation.getPlane() &&
                    zone.distanceTo(playerLocation) <= MAX_RENDER_DISTANCE)
            {
//
//                LocalPoint centerPoint = LocalPoint.fromWorld(playerWorldView,
//                        zone.getX() + zone.getWidth() / 2,
//                        zone.getY() + zone.getHeight() / 2);
//                highlightArea(graphics, centerPoint, zone.getWidth(), zone.getHeight());

                for (WorldPoint point : zone.toWorldPointList())
                {
                    highlightTile(graphics, point,
                            new Color(59, 126, 69, 255),
                            new Color(59, 126, 69, 100));
                }
            }
        }

        return null;
    }

    /*
    Localpoint is a local 2D coordinate system where 1 coord pt = 1/128 of a tile. I think it's possible that it can't
    map a player point to a localpoint, so there's a  null check
     */
    private void highlightTile(Graphics2D graphics, WorldPoint point, Color borderColor, Color fillColor)
    {

        LocalPoint lp = LocalPoint.fromWorld(client.getLocalPlayer().getWorldView(), point);
        if (lp == null)
        {
            return;
        }
        Polygon poly = Perspective.getCanvasTilePoly(client, lp);
        if (poly != null)
        {
            BasicStroke border = new BasicStroke(3);
            OverlayUtil.renderPolygon(graphics, poly, borderColor, fillColor, border);
        }
    }

    /*
    Seems to highlight a box that is somewhere under the actual floor. Maybe playing with z offset will bring it up?
    I kind of prefer a single box highlight as it actually matches the terrain of the tile
     */
    private void highlightArea(Graphics2D graphics, LocalPoint centerPoint, int width, int height, Color color)
    {
        if (centerPoint == null)
        {
            return;
        }
        Polygon polyArea = Perspective.getCanvasTileAreaPoly(client, centerPoint, width, height,
                client.getLocalPlayer().getWorldLocation().getPlane(), 0);
        BasicStroke border = new BasicStroke(3);
        OverlayUtil.renderPolygon(graphics, polyArea, color, border);
    }

}
