package nw.rifthelper;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.input.KeyManager;

@Slf4j
@PluginDescriptor(
	name = "Guardians of the Rift Helper"
)
public class GuardiansOfTheRiftHelperPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private GuardiansOfTheRiftHelperConfig config;

	@Inject
	private GuardiansOfTheRightHelperOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private GuardiansOfTheRiftHelperKeyListener keyListener;

	@Inject
	private KeyManager keyManager;

	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private GuardiansOfTheRiftHelperSession session;

	@Getter
	@Setter
	private boolean overlayActive;

	@Getter
	private GuardiansOfTheRiftZones currentZone;

	@Override
	protected void startUp() throws Exception
	{
		overlayActive = false;
		session = new GuardiansOfTheRiftHelperSession(client);
		currentZone = null;
		overlayManager.add(overlay);
		keyManager.registerKeyListener(keyListener);
		overlay.setSession(session);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		keyManager.unregisterKeyListener(keyListener);
		session = null;
		currentZone = null;
		overlayActive = false;
		overlay.setSession(null);
	}

	@Provides
	GuardiansOfTheRiftHelperConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(GuardiansOfTheRiftHelperConfig.class);
	}

	/*
	Subscribe to gamechanged event.
	If logins screen -> null everything
	If loading -> clear game-related stats but not session
	If logged in but not in any zone -> disable overlay
	 */

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		if (isOverlayActive())
		{

		}
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{

	}

	// subscribe to chat message
	/*
	1. call session's update chat message function
	 */

	// subscribe to interact?
	/*
	1. call session's update status method with inventory/actor
	1. if post-interact player is in central area but previously was in waiting room, we are now in game mode
	 */
}
