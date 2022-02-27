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
    private Context context;

    public Sanction(Player sanctioner, Player sanctioned, SanctionType type, String time, String reason, String date, Context context) {
        this.sanctioner = sanctioner;
        this.sanctioned = sanctioned;
        this.type = type;
        this.time = time;
        this.reason = reason;
        this.date = date;
        this.context = context;
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

    public Context getContext() {
        return context;
    }

    //SETTERS
    public void setType(SanctionType t) {
        this.type = t;
    }

    public void setTime(String t) {
        this.time = t;
    }

}
