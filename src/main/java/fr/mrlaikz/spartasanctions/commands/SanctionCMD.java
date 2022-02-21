package fr.mrlaikz.spartasanctions.commands;

import fr.iban.bukkitcore.menu.Menu;
import fr.mrlaikz.spartasanctions.SpartaSanctions;
import fr.mrlaikz.spartasanctions.menus.HistoryMenu;
import fr.mrlaikz.spartasanctions.menus.PlayerMenu;
import fr.mrlaikz.spartasanctions.menus.SanctionMenu;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SanctionCMD implements CommandExecutor {

    private SpartaSanctions plugin;

    public SanctionCMD(SpartaSanctions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if(sender instanceof Player) {

            Player p = (Player) sender;

            if(args.length == 0) {
                p.sendMessage("§cSyntaxe: /sanction <pseudo>");
            }

            if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null) {
                    p.sendMessage("§cCe joueur n'est pas en ligne !");
                    return false;
                }

                PlayerMenu m = new PlayerMenu(p, target, plugin);
                m.open();

            }

            if(args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null) {
                    p.sendMessage("§cCe joueur n'est pas en ligne !");
                    return false;
                }
                if(args[1].equalsIgnoreCase("history")) {
                    HistoryMenu menu = new HistoryMenu(p, target, plugin);
                    menu.open();
                }

                if(args[1].equalsIgnoreCase("sanction")) {
                    SanctionMenu menu = new SanctionMenu(p, target);
                    menu.open();
                }
                
            }

        }

        return false;
    }
}
