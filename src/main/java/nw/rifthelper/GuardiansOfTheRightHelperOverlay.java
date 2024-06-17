package nw.rifthelper;


import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPanel;
import static net.runelite.api.MenuAction.RUNELITE_OVERLAY;
import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;

public class GuardiansOfTheRightHelperOverlay extends OverlayPanel
{
    private final Client client;
    private final GuardiansOfTheRiftHelperConfig config;
    private final GuardiansOfTheRiftHelperPlugin plugin;
    private static final String DISMISS_OVERLAY = "Dismiss";
    private static final String OVERLAY_NAME = "GotR Overlay";

    @Inject
    private GuardiansOfTheRightHelperOverlay(Client client, GuardiansOfTheRiftHelperConfig config,
                                             GuardiansOfTheRiftHelperPlugin plugin)
    {
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        this.client = client;
        this.config = config;
        this.plugin = plugin;
        /*
        Overlay menus are opened by shift-right-clicking on the overlay
        Syntax is menuAction, Option, target
        menuAction = what clicking this choice actually does
        option = what the text says to the user. OPTION_CONFIGURE as of now is a string = "Configure"
        target = What it says you are doing it to. It will say "Configure <target>" and clicking will make the
        RuneLite configure menu for this plugin pop up on the right panel.
         */
        addMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, OVERLAY_NAME);
        addMenuEntry(RUNELITE_OVERLAY, DISMISS_OVERLAY, OVERLAY_NAME, e -> plugin.setOverlayActive(false));
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!plugin.isOverlayActive())
        {
            return null;
        }

        // placeholder
        return null;
//        return super.render(graphics);
    }
}
