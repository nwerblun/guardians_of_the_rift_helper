package nw.rifthelper;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class GuardiansOfTheRiftHelperPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(GuardiansOfTheRiftHelperPlugin.class);
		RuneLite.main(args);
	}
}