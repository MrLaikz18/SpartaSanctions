package fr.mrlaikz.spartasanctions.menus;

import fr.iban.bukkitcore.menu.Menu;
import fr.mrlaikz.spartasanctions.SpartaSanctions;
import fr.mrlaikz.spartasanctions.enums.SanctionType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerMenu extends Menu {

    private Player target;
    private SpartaSanctions plugin;

    public PlayerMenu(Player player, Player target, SpartaSanctions plugin) {
        super(player);
        this.target = target;
        this.plugin = plugin;
    }

    @Override
    public String getMenuName() {
        return "§6§lMenu des Sanctions";
    }

    @Override
    public int getRows() {
        return 5;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        ItemStack it = e.getCurrentItem();
        if(it != null && it.hasItemMeta() && it.getItemMeta().hasDisplayName()) {

            if(it.getType().equals(Material.PLAYER_HEAD)) {
                p.teleport(target);
            }

            if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lSanction")) {
                SanctionMenu menu = new SanctionMenu(player, target, plugin);
                menu.open();
            }

            if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§e§lHistorique")) {
                HistoryMenu menu = new HistoryMenu(player, target, plugin);
                menu.open();
            }
        }

    }

    @Override
    public void setMenuItems() {

        ItemStack head = getTargetHead(target);

        ItemStack axe = getItemStack(Material.IRON_AXE, "§c§lSanction", true);
        ItemStack book = getItemStack(Material.WRITTEN_BOOK, "§e§lHistorique", true);

        inventory.setItem(13, head);
        inventory.setItem(29, axe);
        inventory.setItem(33, book);

        addMenuBorder();

    }

    public static ItemStack getTargetHead(Player target) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headM = (SkullMeta) head.getItemMeta();
        headM.setOwningPlayer(target);
        headM.setDisplayName("§6" + target.getName());
        List<String> lore = new ArrayList<String>();


        SpartaSanctions.getInstance().getSQL().getSanctionsAsync(target, SanctionType.WARN).thenAccept(warns -> {
            String w = String.valueOf(warns.size());
            lore.add("§eWarns: §c" + w);
        });

        headM.setLore(lore);
        head.setItemMeta(headM);
        return head;
    }

    public static ItemStack getItemStack(Material m, String name, boolean enchant) {
        ItemStack it = new ItemStack(m);
        ItemMeta itM = it.getItemMeta();
        itM.setDisplayName(name);
        if(enchant) {
            itM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            itM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        it.setItemMeta(itM);
        return it;
    }

}
