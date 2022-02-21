package fr.mrlaikz.spartasanctions.enums;

import org.bukkit.Material;

public enum SanctionType {

    KICK("kick %player% %reason%", Material.IRON_SWORD),
    TEMPMUTE("tempmute %player% %time% %reason%", Material.PAPER),
    TEMPBAN("tempban %player% %time% %reason%", Material.NETHERITE_AXE),
    BAN("ban %player% %reason%", Material.AIR),
    WARN("warn %player% %reason%", Material.AIR);

    private String cmd;
    private Material mat;
    SanctionType(String cmd, Material mat) {
        this.cmd = cmd;
        this.mat = mat;
    }

    public String getCommand() {
        return cmd;
    }

    public Material getMaterial() { return mat; }

}
