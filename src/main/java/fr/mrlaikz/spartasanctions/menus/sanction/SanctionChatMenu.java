package fr.mrlaikz.spartasanctions.menus.sanction;

import fr.iban.bukkitcore.menu.Menu;
import fr.mrlaikz.spartasanctions.SpartaSanctions;
import fr.mrlaikz.spartasanctions.enums.SanctionType;
import fr.mrlaikz.spartasanctions.menus.PlayerMenu;
import fr.mrlaikz.spartasanctions.menus.SanctionMenu;
import fr.mrlaikz.spartasanctions.enums.Context;
import fr.mrlaikz.spartasanctions.objects.Sanction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SanctionChatMenu extends Menu {

    private Player target;

    public SanctionChatMenu(Player player, Player target) {
        super(player);
        this.target = target;
    }

    @Override
    public String getMenuName() {
        return "§2§lSanction Chat";
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

            SanctionType type = SanctionType.TEMPMUTE;
            String time = "";
            String reason = it.getItemMeta().getDisplayName().replace("§c", "");

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date d = new Date();
            String date = String.valueOf(formatter.format(d));

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cInsultes")) {
                time = "10m";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cInsultes Graves")) {
                time = "1h";
                Sanction warn = new Sanction(player, target, SanctionType.WARN, "", reason, date, Context.CHAT);
                SpartaSanctions.getInstance().getSanctionManager().apply(warn);
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cSpam")) {
                time = "15m";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cFlood")) {
                time = "15m";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cFlood Grave")) {
                time = "30m";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cMajuscules")) {
                time = "10m";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cProvocation")) {
                time = "1h";
            }

            Sanction s = new Sanction(player, target, type, time, reason, date, Context.CHAT);
            SpartaSanctions.getInstance().getSanctionManager().apply(s);

        }

    }

    @Override
    public void setMenuItems() {

        ItemStack head = PlayerMenu.getTargetHead(target);

        ItemStack insultes = PlayerMenu.getItemStack(Material.CHEST, "§cInsultes", false);
        ItemStack insultesG = PlayerMenu.getItemStack(Material.CHEST, "§cInsultes Graves", false);
        ItemStack spam = PlayerMenu.getItemStack(Material.CHEST, "§cSpam", false);
        ItemStack flood = PlayerMenu.getItemStack(Material.CHEST, "§cFlood", false);
        ItemStack floodG = PlayerMenu.getItemStack(Material.CHEST, "§cFlood Grave", false);
        ItemStack maj = PlayerMenu.getItemStack(Material.CHEST, "§cMajuscules", false);
        ItemStack provoc = PlayerMenu.getItemStack(Material.CHEST, "§cProvocation", false);
        ItemStack retour = PlayerMenu.getItemStack(Material.BARRIER, "§c§lRetour", false);

        inventory.setItem(4, head);
        inventory.setItem(10, insultes);
        inventory.setItem(11, insultesG);
        inventory.setItem(12, spam);
        inventory.setItem(13, flood);
        inventory.setItem(14, floodG);
        inventory.setItem(15, maj);
        inventory.setItem(16, provoc);
        inventory.setItem(18, retour);


    }
}
