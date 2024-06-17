package nw.rifthelper;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Keybind;
import java.awt.event.KeyEvent;

@ConfigGroup("guardiansoftherifthelper")
public interface GuardiansOfTheRiftHelperConfig extends Config
{
	@ConfigSection(
			name = "PlaceholderSection1",
			description = "PlaceholderSection1",
			position = 1,
			closedByDefault = true
	)
	String placeholderSection1 = "PlaceholderSection1";

	@ConfigItem(
		keyName = "placeholder1",
		name = "Placeholder1",
		description = "Placeholder1",
		position = 1,
		section = placeholderSection1
	)
	default String peepeeweewee()
	{
		return "Hello";
	}

	@ConfigItem(
			position = 2,
			keyName = "placeholder2",
			name = "Placeholder2",
			description = "Placeholder2",
			section = placeholderSection1
	)
	default boolean hotkeyActivationKeybindEnable()
	{
		return false;
	}

	@ConfigItem(
			position = 3,
			keyName = "placeholder3",
			name = "Placeholder3",
			description = "Placeholder3",
			section = placeholderSection1
	)
	default Keybind hotkeyActivationKeybind()
	{
		return new Keybind(KeyEvent.VK_T, KeyEvent.SHIFT_DOWN_MASK);
	}
}
