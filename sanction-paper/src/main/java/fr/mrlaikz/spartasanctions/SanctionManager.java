package fr.mrlaikz.spartasanctions;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.mrlaikz.spartasanctions.enums.SanctionType;
import fr.mrlaikz.spartasanctions.objects.Sanction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SanctionManager {

    private SpartaSanctions plugin;

    public SanctionManager(SpartaSanctions plugin) {
        this.plugin = plugin;
    }

    public void apply(Sanction s) {
        plugin.getSQL().getSanctionsAsync(s.getSanctioned(), s.getContext()).thenAccept(sanctions -> {
            switch(s.getContext()) {
                case CHAT:
                    if(sanctions.size()%5==0 && sanctions.size()!=0 && s.getType().equals(SanctionType.TEMPMUTE)) {
                        Sanction warn = new Sanction(s.getSanctioner(), s.getSanctioned(), SanctionType.WARN, "", s.getReason(), s.getDate(), s.getContext());
                        apply(warn);
                        s.setTime("30m");
                    }
                    break;
                case GAME:
                    if(sanctions.size()%5==0 && sanctions.size() != 0 && sanctions.get(0).getType() != SanctionType.WARN) {
                        Sanction warn = new Sanction(s.getSanctioner(), s.getSanctioned(), SanctionType.WARN, "", s.getReason(), s.getDate(), s.getContext());
                        apply(warn);
                    }
                    break;
                case CHEAT:
                    if(sanctions.size() == 4) {
                        Sanction bandef = new Sanction(s.getSanctioner(), s.getSanctioned(), SanctionType.TEMPBAN, "100y", s.getReason(), s.getDate(), s.getContext());
                        apply(bandef);
                    }
                    break;
            }

            plugin.getSQL().addSanctionAsync(s);
            plugin.getLogger().info("Sanction ajoutée");
            String cmd = s.getType().getCommand()
                    .replace("%time%", s.getTime())
                    .replace("%player%", s.getSanctioned().getName())
                    .replace("%reason%", s.getReason());

            sendPluginMessage(Bukkit.getPlayer(s.getSanctioner()), cmd);
            Bukkit.getPlayer(s.getSanctioner()).sendMessage("§aSanction ajoutée à §6" + s.getSanctioned().getName() + "§a: §a§l"+s.getType()+ ": §a" + s.getTime() + " " + s.getReason());


        });

    }

    public void sendPluginMessage(Player performer, String cmd) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("command");
        out.writeUTF(performer.getUniqueId().toString());
        out.writeUTF(cmd);
        performer.sendPluginMessage(plugin, "spartachannel:command", out.toByteArray());
    }


}
