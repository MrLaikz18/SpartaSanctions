package fr.mrlaikz.spartasanctions.menus.sanction;

import fr.iban.bukkitcore.menu.Menu;
import fr.mrlaikz.spartasanctions.SpartaSanctions;
import fr.mrlaikz.spartasanctions.enums.Context;
import fr.mrlaikz.spartasanctions.enums.SanctionType;
import fr.mrlaikz.spartasanctions.menus.PlayerMenu;
import fr.mrlaikz.spartasanctions.menus.SanctionMenu;
import fr.mrlaikz.spartasanctions.objects.Sanction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SanctionAutre extends Menu {

    private Player target;

    public SanctionAutre(Player player, Player target) {
        super(player);
        this.target = target;
    }

    @Override
    public String getMenuName() {
        return "§b§lAutres";
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

            SanctionType type = null;
            String time = "";
            String reason = "Vous avez été sanctionné par l'équipe de modération";

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date d = new Date();
            String date = String.valueOf(formatter.format(d));

            Context c = Context.OTHER;


            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cTempmute 15m")) {
                type = SanctionType.TEMPMUTE;
                time = "15m";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cTempmute 30m")) {
                type = SanctionType.TEMPMUTE;
                time = "40m";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cTempmute 1h")) {
                type = SanctionType.TEMPMUTE;
                time = "1h";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cTempban 3d")) {
                type = SanctionType.TEMPBAN;
                time = "3d";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cTempban 15d")) {
                type = SanctionType.TEMPBAN;
                time = "15d";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cTempban 30d")) {
                type = SanctionType.TEMPBAN;
                time = "30d";
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cWarn")) {
                type = SanctionType.WARN;
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§cKick")) {
                type = SanctionType.KICK;
            }

            Sanction s = new Sanction(player.getName(), target, type, time, reason, date, c);
            SpartaSanctions.getInstance().getSanctionManager().apply(s);

        }

    }

    @Override
    public void setMenuItems() {

        ItemStack head = PlayerMenu.getTargetHead(target);

        ItemStack tempmute1 = PlayerMenu.getItemStack(Material.CHEST, "§cTempmute 15m", false);
        ItemStack tempmute2 = PlayerMenu.getItemStack(Material.CHEST, "§cTempmute 30m", false);
        ItemStack tempmute3 = PlayerMenu.getItemStack(Material.CHEST, "§cTempmute 1h", false);
        ItemStack tempban1 = PlayerMenu.getItemStack(Material.CHEST, "§cTempban 3d", false);
        ItemStack tempban2 = PlayerMenu.getItemStack(Material.CHEST, "§cTempban 15d", false);
        ItemStack tempban3 = PlayerMenu.getItemStack(Material.CHEST, "§cTempban 30d", false);
        ItemStack warn = PlayerMenu.getItemStack(Material.CHEST, "§cWarn", false);
        ItemStack kick = PlayerMenu.getItemStack(Material.CHEST, "§cKick", false);
        ItemStack retour = PlayerMenu.getItemStack(Material.BARRIER, "§c§lRetour", false);

        inventory.setItem(4, head);
        inventory.setItem(10, tempmute1);
        inventory.setItem(11, tempmute2);
        inventory.setItem(12, tempmute3);
        inventory.setItem(13, tempban1);
        inventory.setItem(14, tempban2);
        inventory.setItem(15, tempban3);
        inventory.setItem(16, warn);
        inventory.setItem(22, kick);
        inventory.setItem(18, retour);

    }
}
