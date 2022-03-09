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

public class SanctionJeuMenu extends Menu {

    private OfflinePlayer target;

    public SanctionJeuMenu(Player player, OfflinePlayer target) {
        super(player);
        this.target = target;
    }

    @Override
    public String getMenuName() {
        return "§7§lSanction Jeu";
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        ItemStack it = e.getCurrentItem();

        if (it != null && it.hasItemMeta() && it.getItemMeta().hasDisplayName()) {

            if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lRetour")) {
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

            if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§cGrief")) {
                time = "8h";
            }

            if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§cGrief grave")) {
                time = "7d";
            }

            if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§cGrief Extrême")) {
                time = "14d";
                Sanction warn = new Sanction(player.getName(), target, SanctionType.WARN, "", "Grief", date, Context.GAME);
                SpartaSanctions.getInstance().getSanctionManager().apply(warn);
            }

            if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§cClaim Blocking")) {
                time = "3d";
            }

            if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§cSpam TP")) {
                type = SanctionType.TEMPMUTE;
                time = "1h";
            }

            if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§cTP Kill")) {
                time = "14d";
            }

            if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§cAnti AFK")) {
                time = "14d";
            }

            Sanction s = new Sanction(player.getName(), target, type, time, reason, date, Context.GAME);
            SpartaSanctions.getInstance().getSanctionManager().apply(s);

        }
    }

    @Override
    public void setMenuItems() {

        ItemStack head = PlayerMenu.getTargetHead(target);

        ItemStack grief = PlayerMenu.getItemStack(Material.CHEST, "§cGrief", false);
        ItemStack griefG = PlayerMenu.getItemStack(Material.CHEST, "§cGrief Grave", false);
        ItemStack griefE = PlayerMenu.getItemStack(Material.CHEST, "§cGrief Extrême", false);
        ItemStack claim = PlayerMenu.getItemStack(Material.CHEST, "§cClaim Blocking", false);
        ItemStack spamtp = PlayerMenu.getItemStack(Material.CHEST, "§cSpam TP", false);
        ItemStack tpkill = PlayerMenu.getItemStack(Material.CHEST, "§cTP Kill", false);
        ItemStack afk = PlayerMenu.getItemStack(Material.CHEST, "§cAnti-AFK", false);
        ItemStack retour = PlayerMenu.getItemStack(Material.BARRIER, "§c§lRetour", false);
        addMenuBorder();
        inventory.setItem(4, head);
        inventory.setItem(10, grief);
        inventory.setItem(11, griefG);
        inventory.setItem(12, griefE);
        inventory.setItem(13, claim);
        inventory.setItem(14, spamtp);
        inventory.setItem(15, tpkill);
        inventory.setItem(16, afk);
        inventory.setItem(18, retour);
    }
}
