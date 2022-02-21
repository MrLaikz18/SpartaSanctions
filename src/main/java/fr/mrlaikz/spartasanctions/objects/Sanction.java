package fr.mrlaikz.spartasanctions.objects;

import fr.mrlaikz.spartasanctions.SpartaSanctions;
import fr.mrlaikz.spartasanctions.enums.SanctionType;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class Sanction {

    private Player sanctioner;
    private Player sanctioned;
    private SanctionType type;
    private String time;
    private String reason;
    private String date;

    public Sanction(Player sanctioner, Player sanctioned, SanctionType type, String time, String reason, String date) {
        this.sanctioner = sanctioner;
        this.sanctioned = sanctioned;
        this.type = type;
        this.time = time;
        this.reason = reason;
        this.date = date;
    }

    //APPLY SANCTION
    public void apply() {
        if(this.type.equals(SanctionType.TEMPMUTE)) {
            SpartaSanctions.getInstance().getSQL().getSanctionsAsync(this.sanctioned, this.type, this.reason).thenAccept(sanctions -> {

                if(this.type.equals(SanctionType.TEMPMUTE)) {
                    if(sanctions.size()>=1) {
                        this.time = Integer.parseInt(this.time)*2+String.valueOf(this.time).replace(String.valueOf(Integer.parseInt(this.time)), "");
                    }
                }

                if(this.type.equals(SanctionType.TEMPBAN)) {
                    if(sanctions.size()>=5) {
                        this.time = "10y";
                    }
                }
                
            });
        }

        if(this.time == null) {
            this.time = "";
        }
        sanctioner.performCommand(this.type.getCommand()
                .replace("%player%", sanctioned.getName())
                .replace("%time%", time)
                .replace("%reason%", reason));

        SpartaSanctions.getInstance().getSQL().addSanctionAsync(this);

    }

    //GETTERS
    public String getDate() {
        return date;
    }

    public SanctionType getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getReason() {
        return reason;
    }

    public Player getSanctioner() {
        return sanctioner;
    }

    public Player getSanctioned() {
        return sanctioned;
    }

}
