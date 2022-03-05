package fr.mrlaikz.sanction;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class PluginMessageListener implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) {
        if(e.getTag().equals("SpartaChannel")) {
            final ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
            final String sub = in.readUTF();

            if(sub.equals("Command")) {
                //LECTURE MESSAGE
                final String uuidStr = in.readUTF();
                final UUID uuid = UUID.fromString(uuidStr);
                final String cmd = in.readUTF();

                ProxiedPlayer p = SanctionWaterfall.INSTANCE.getProxy().getPlayer(uuid);
                p.chat("/" + cmd);
            }

        }
    }

}
