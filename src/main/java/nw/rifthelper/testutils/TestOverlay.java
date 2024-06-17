package nw.rifthelper.testutils;

import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayUtil;
import nw.rifthelper.GuardianOfTheRiftHelperZones;
import nw.rifthelper.GuardiansOfTheRiftHelperConfig;
import nw.rifthelper.GuardiansOfTheRiftHelperPlugin;

import javax.inject.Inject;
import java.awt.*;

public class TestOverlay extends Overlay
{

    private final Client client;
    private final GuardiansOfTheRiftHelperPlugin plugin;
    private final GuardiansOfTheRiftHelperConfig config;
    private static final int MAX_RENDER_DISTANCE = 50;

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

        for (GuardianOfTheRiftHelperZones zone : GuardianOfTheRiftHelperZones.zones)
        {
            WorldArea zoneWorldArea = zone.getWorldArea();
            if (zoneWorldArea.contains(playerLocation) &&
                    zoneWorldArea.getPlane() == playerLocation.getPlane() &&
                    zoneWorldArea.distanceTo(playerLocation) <= MAX_RENDER_DISTANCE)
            {
//
//                LocalPoint centerPoint = LocalPoint.fromWorld(playerWorldView,
//                        zoneWorldArea.getX() + zoneWorldArea.getWidth() / 2,
//                        zoneWorldArea.getY() + zoneWorldArea.getHeight() / 2);
//                highlightArea(graphics, centerPoint, zoneWorldArea.getWidth(), zoneWorldArea.getHeight());

                for (WorldPoint point : zoneWorldArea.toWorldPointList())
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
