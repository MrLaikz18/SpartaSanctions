package fr.mrlaikz.spartasanctions;

import fr.mrlaikz.spartasanctions.enums.SanctionType;
import fr.mrlaikz.spartasanctions.objects.Sanction;
import org.bukkit.entity.Player;

public class SanctionManager {

    private SpartaSanctions plugin;

    public SanctionManager(SpartaSanctions plugin) {
        this.plugin = plugin;
    }

    public void apply(Sanction s) {
        plugin.getSQL().addSanctionAsync(s);
        plugin.getSQL().getSanctionsAsync(s.getSanctioned(), s.getContext()).thenAccept(sanctions -> {
            plugin.getLogger().info(""+sanctions.size());
            switch(s.getContext()) {
                case CHAT:
                    if(sanctions.size() >= 3) {
                        s.doubleTime();
                    }

                    if(sanctions.size()%5==0) {
                        s.doubleTime();
                        Sanction warn = new Sanction(s.getSanctioner(), s.getSanctioned(), SanctionType.WARN, "", s.getReason(), s.getDate(), s.getContext());
                        apply(warn);
                    }
                    break;
                case GAME:
                    if(sanctions.size()%5==0 && sanctions.size() != 0 && sanctions.get(0).getType() != SanctionType.WARN) {
                        Sanction warn = new Sanction(s.getSanctioner(), s.getSanctioned(), SanctionType.WARN, "", s.getReason(), s.getDate(), s.getContext());
                        apply(warn);
                    }
                    break;
                case CHEAT:
                    if(sanctions.size() >= 4) {
                        Sanction bandef = new Sanction(s.getSanctioner(), s.getSanctioned(), SanctionType.TEMPBAN, "100y", s.getReason(), s.getDate(), s.getContext());
                        apply(bandef);
                    }
                    break;
            }
        });

        plugin.getLogger().info("Sanction ajoutée");
        plugin.getLogger().info(s.getType().getCommand()
                .replace("%time%", s.getTime())
                .replace("%player%", s.getSanctioned().getName())
                .replace("%reason%", s.getReason()));

        s.getSanctioner().performCommand(s.getType().getCommand()
                .replace("%time%", s.getTime())
                .replace("%player%", s.getSanctioned().getName())
                .replace("%reason%", s.getReason()));
    }

}
