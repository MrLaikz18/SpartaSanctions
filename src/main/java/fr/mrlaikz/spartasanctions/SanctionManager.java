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

        plugin.getSQL().getSanctionsAsync(s.getSanctioned(), s.getContext()).thenAccept(sanctions -> {
            switch(s.getContext()) {
                case CHAT:
                    if(sanctions.size() >= 1) {
                        doubleTime(s);
                    }

                    if(sanctions.size()%5==0) {
                        doubleTime(s);
                        Sanction warn = new Sanction(s.getSanctioner(), s.getSanctioned(), SanctionType.WARN, "", s.getReason(), s.getDate(), s.getContext());
                        apply(warn);
                    }
                case GAME:
                    if(sanctions.size()%3 == 0) {
                        Sanction warn = new Sanction(s.getSanctioner(), s.getSanctioned(), SanctionType.WARN, "", s.getReason(), s.getDate(), s.getContext());
                        apply(warn);
                    }
                case CHEAT:
                    if(sanctions.size() >= 1) {
                        Sanction bandef = new Sanction(s.getSanctioner(), s.getSanctioned(), SanctionType.TEMPBAN, "100y", s.getReason(), s.getDate(), s.getContext());
                    }
            }
        });

        s.getSanctioner().performCommand(s.getType().getCommand()
                .replace("%time%", s.getTime())
                .replace("%player%", s.getSanctioned().getName())
                .replace("%reason%", s.getReason()));
    }

    public void doubleTime(Sanction s) {
        String time = s.getTime();
        int t = Integer.parseInt(time);
        String letter = time.replace(String.valueOf(t), "");
        s.setTime(t*2 + letter);
    }

}
