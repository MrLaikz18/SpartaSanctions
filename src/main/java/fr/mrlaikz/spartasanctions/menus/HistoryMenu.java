package fr.mrlaikz.spartasanctions.menus;

import fr.iban.bukkitcore.menu.Menu;
import fr.mrlaikz.spartasanctions.SpartaSanctions;
import fr.mrlaikz.spartasanctions.enums.SanctionType;
import fr.mrlaikz.spartasanctions.objects.Sanction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HistoryMenu extends Menu {

    private Player target;
    private SpartaSanctions plugin;

    public HistoryMenu(Player player, Player target, SpartaSanctions plugin) {
        super(player);
        this.target = target;
        this.plugin = plugin;
    }

    @Override
    public String getMenuName() {
        return "§e§lHistorique";
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lRetour")) {
            PlayerMenu menu = new PlayerMenu(player, target, plugin);
            menu.open();
        }
    }

    @Override
    public void setMenuItems() {

        ItemStack head = PlayerMenu.getTargetHead(target);
        inventory.setItem(4, head);

        ItemStack retour = PlayerMenu.getItemStack(Material.BARRIER, "§c§lRetour", false);
        inventory.setItem(0, retour);

        displaySanctions(SanctionType.TEMPMUTE, 1);
        displaySanctions(SanctionType.TEMPBAN, 3);
        displaySanctions(SanctionType.KICK, 4);
        displaySanctions(SanctionType.WARN, 5);

    }

    public void displaySanctions(SanctionType type, int pos) {
        plugin.getSQL().getSanctionsAsync(target, type).thenAccept(sanction -> {
            int i = 0;
            for(Sanction s : sanction) {
                ItemStack it = PlayerMenu.getItemStack(type.getMaterial(), "§c§l"+type.toString(), false);
                List<String> lore = new ArrayList<String>();
                lore.add("§cDate: " + s.getDate());
                lore.add("§cAuteur: " + s.getSanctioner());
                lore.add("§cRaison: " + s.getReason());
                if(type.equals(SanctionType.TEMPMUTE) || type.equals(SanctionType.TEMPBAN)) {
                    lore.add("§cDurée: " + s.getTime());
                }
                it.setLore(lore);
                inventory.setItem(pos*9 + i, it);
                i++;
            }
        });
    }



}
