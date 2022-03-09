package fr.mrlaikz.spartasanctions.menus;

import fr.iban.bukkitcore.menu.Menu;
import fr.mrlaikz.spartasanctions.SpartaSanctions;
import fr.mrlaikz.spartasanctions.menus.sanction.SanctionAutre;
import fr.mrlaikz.spartasanctions.menus.sanction.SanctionChatMenu;
import fr.mrlaikz.spartasanctions.menus.sanction.SanctionCheatMenu;
import fr.mrlaikz.spartasanctions.menus.sanction.SanctionJeuMenu;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SanctionMenu extends Menu {

    private OfflinePlayer target;
    private SpartaSanctions plugin;

    public SanctionMenu(Player player, OfflinePlayer target, SpartaSanctions plugin) {
        super(player);
        this.target = target;
        this.plugin = plugin;
    }

    @Override
    public String getMenuName() {
        return "§4§lSanctions";
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        ItemStack it = e.getCurrentItem();

        if(it != null && it.hasItemMeta() && it.getItemMeta().hasDisplayName()) {

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§2§lSanction Chat")) {
                SanctionChatMenu menu = new SanctionChatMenu(player, target);
                menu.open();
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§9§lSanction Jeu")) {
                SanctionJeuMenu menu = new SanctionJeuMenu(player, target);
                menu.open();
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§4§lSanction Cheat")) {
                SanctionCheatMenu menu = new SanctionCheatMenu(player, target);
                menu.open();
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§b§lAutres")) {
                SanctionAutre menu = new SanctionAutre(player, target);
                menu.open();
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lRetour")) {
                PlayerMenu menu = new PlayerMenu(player, target, plugin);
                menu.open();
            }

        }

    }

    @Override
    public void setMenuItems() {

        ItemStack head = PlayerMenu.getTargetHead(target);

        ItemStack book = PlayerMenu.getItemStack(Material.WRITTEN_BOOK, "§2§lSanction Chat", true);
        ItemStack block = PlayerMenu.getItemStack(Material.GRASS_BLOCK, "§9§lSanction Jeu", true);
        ItemStack epee = PlayerMenu.getItemStack(Material.NETHERITE_SWORD, "§4§lSanction Cheat", true);
        ItemStack bow = PlayerMenu.getItemStack(Material.BOW, "§b§lAutres", true);
        ItemStack barrier = PlayerMenu.getItemStack(Material.BARRIER, "§c§lRetour",  false);

        inventory.setItem(4, head);
        inventory.setItem(20, book);
        inventory.setItem(22, block);
        inventory.setItem(24, epee);
        inventory.setItem(40, bow);
        inventory.setItem(45, barrier);
        addMenuBorder();

    }
}
