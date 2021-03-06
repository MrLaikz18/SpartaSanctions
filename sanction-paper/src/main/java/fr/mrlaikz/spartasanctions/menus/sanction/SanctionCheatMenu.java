package fr.mrlaikz.spartasanctions.menus.sanction;

import fr.iban.bukkitcore.menu.Menu;
import fr.mrlaikz.spartasanctions.SpartaSanctions;
import fr.mrlaikz.spartasanctions.enums.SanctionType;
import fr.mrlaikz.spartasanctions.menus.PlayerMenu;
import fr.mrlaikz.spartasanctions.menus.SanctionMenu;
import fr.mrlaikz.spartasanctions.enums.Context;
import fr.mrlaikz.spartasanctions.objects.Sanction;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SanctionCheatMenu extends Menu {

    private OfflinePlayer target;

    public SanctionCheatMenu(Player player, OfflinePlayer target) {
        super(player);
        this.target = target;
    }

    @Override
    public String getMenuName() {
        return "§4§lSanction Cheat";
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        ItemStack it = e.getCurrentItem();

        if(it != null && it.hasItemMeta() && it.getItemMeta().hasDisplayName()) {

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lRetour")) {
                SanctionMenu menu = new SanctionMenu(player, target, SpartaSanctions.getInstance());
                menu.open();
                return;
            }

            String time = "";
            SanctionType type = SanctionType.TEMPBAN;

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date d = new Date();
            String date = String.valueOf(formatter.format(d));
            String reason = it.getItemMeta().getDisplayName().replace("§c", "");

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cX-Ray")) {
                time = "30d";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cX-Ray (avoué)")) {
                time = "15d";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cFly")) {
                time = "30d";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cSpeed")) {
                time = "30d";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cBaritone")) {
                time = "30d";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cAnti Knockback")) {
                time = "14d";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cAuto Click")) {
                time = "14d";
            }

            Sanction warn = new Sanction(player.getName(), target, SanctionType.WARN, "", reason, date, Context.CHEAT);

            Sanction s = new Sanction(player.getName(), target, type, time, reason, date, Context.CHEAT);
            SpartaSanctions.getInstance().getSanctionManager().apply(s);
            SpartaSanctions.getInstance().getSanctionManager().apply(warn);

        }

    }

    @Override
    public void setMenuItems() {
        ItemStack head = PlayerMenu.getTargetHead(target);

        ItemStack xray = PlayerMenu.getItemStack(Material.CHEST, "§cX-Ray", false);
        ItemStack xrayA = PlayerMenu.getItemStack(Material.CHEST, "§cX-Ray (avoué)", false);
        ItemStack fly = PlayerMenu.getItemStack(Material.CHEST, "§cFly", false);
        ItemStack speed = PlayerMenu.getItemStack(Material.CHEST, "§cSpeed", false);
        ItemStack baritone = PlayerMenu.getItemStack(Material.CHEST, "§cBaritone", false);
        ItemStack kb = PlayerMenu.getItemStack(Material.CHEST, "§cAnti Knockback", false);
        ItemStack ac = PlayerMenu.getItemStack(Material.CHEST, "§cAuto Click", false);
        ItemStack retour = PlayerMenu.getItemStack(Material.BARRIER, "§c§lRetour", false);
        addMenuBorder();
        inventory.setItem(4, head);
        inventory.setItem(10, xray);
        inventory.setItem(11, xrayA);
        inventory.setItem(12, fly);
        inventory.setItem(13, speed);
        inventory.setItem(14, baritone);
        inventory.setItem(15, kb);
        inventory.setItem(16, ac);
        inventory.setItem(18, retour);
    }
}
