package nw.rifthelper;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class GuardiansOfTheRightHelperOverlay extends OverlayPanel
{
    private final Client client;
    private final GuardiansOfTheRiftHelperConfig config;
    private final GuardiansOfTheRiftHelperPlugin plugin;

    @Inject
    private GuardiansOfTheRightHelperOverlay(Client client, GuardiansOfTheRiftHelperConfig config,
                                             GuardiansOfTheRiftHelperPlugin plugin)
    {
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        this.client = client;
        this.config = config;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {

        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Getting Materials")
                .color(Color.RED)
                .build()
        );

        panelComponent.getChildren().add(LineComponent.builder()
                .left("hoes")
                .right("none at all")
                .build()
        );

        return super.render(graphics);
    }
}
