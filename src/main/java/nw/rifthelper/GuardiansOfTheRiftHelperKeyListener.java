package nw.rifthelper;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.client.input.KeyListener;
import javax.inject.Inject;
import java.awt.event.KeyEvent;
import net.runelite.client.callback.ClientThread;

public class GuardiansOfTheRiftHelperKeyListener implements KeyListener
{
    @Inject
    private GuardiansOfTheRiftHelperPlugin plugin;

    @Inject
    private GuardiansOfTheRiftHelperConfig config;

    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    /*
    https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/event/KeyEvent.html
    key typed is the character representing the outcome of the pressed keys
    ex:
    1. User presses shift
        * Pressing shift triggers a key pressed event of VK_SHIFT
    2. User then presses t
        * key pressed event with value VK_T
    3. User releases t
        * key released event with value VK_T
    4. Additionally, a key typed event comes with a keyChar value of "T"
     */

    @Override
    public void keyTyped(KeyEvent e)
    {
        // Do nothing?
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (config.hotkeyActivationKeybind().matches(e))
        {
            // Toggle overlay
            plugin.setOverlayActive(!plugin.isOverlayActive());
            // Put message in chat. Must be run in client thread, but key listener is not in client thread.
            clientThread.invoke(() ->
            {
                client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Farm Run Helper was toggled", null);
            });
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        // Do nothing
    }
}

