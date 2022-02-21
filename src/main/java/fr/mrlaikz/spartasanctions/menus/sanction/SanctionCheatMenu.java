package fr.mrlaikz.spartasanctions.menus.sanction;

import fr.iban.bukkitcore.menu.Menu;
import fr.mrlaikz.spartasanctions.SpartaSanctions;
import fr.mrlaikz.spartasanctions.enums.SanctionType;
import fr.mrlaikz.spartasanctions.menus.PlayerMenu;
import fr.mrlaikz.spartasanctions.menus.SanctionMenu;
import fr.mrlaikz.spartasanctions.objects.Sanction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SanctionCheatMenu extends Menu {

    private Player target;

    public SanctionCheatMenu(Player player, Player target) {
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

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cJump")) {
                time = "14d";
            }

            Sanction warn = new Sanction(player, target, SanctionType.WARN, "", reason, date);

            Sanction s = new Sanction(player, target, type, time, reason, date);
            s.apply();

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
        ItemStack jump = PlayerMenu.getItemStack(Material.CHEST, "§cJump", false);
        ItemStack retour = PlayerMenu.getItemStack(Material.CHEST, "§c§lRetour", false);

        inventory.setItem(4, head);
        inventory.setItem(10, xray);
        inventory.setItem(11, xrayA);
        inventory.setItem(12, fly);
        inventory.setItem(13, speed);
        inventory.setItem(14, baritone);
        inventory.setItem(15, kb);
        inventory.setItem(16, jump);
        inventory.setItem(18, retour);
    }
}
