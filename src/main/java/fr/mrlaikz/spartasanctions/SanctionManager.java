package fr.mrlaikz.spartasanctions;

import fr.mrlaikz.spartasanctions.enums.SanctionType;
import fr.mrlaikz.spartasanctions.objects.Sanction;
import org.bukkit.entity.Player;

public class SanctionManager {

    private SpartaSanctions plugin;

    public SanctionManager(SpartaSanctions plugin) {
        this.plugin = plugin;
    }

    public Sanction getSanction(Player sanctioned, SanctionType type) {
        //TODO TABLEAU
        return null;
    }

}
