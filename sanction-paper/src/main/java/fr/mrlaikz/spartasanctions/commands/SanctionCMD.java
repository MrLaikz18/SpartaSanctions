package fr.mrlaikz.spartasanctions.commands;

import fr.mrlaikz.spartasanctions.SpartaSanctions;
import fr.mrlaikz.spartasanctions.menus.HistoryMenu;
import fr.mrlaikz.spartasanctions.menus.PlayerMenu;
import fr.mrlaikz.spartasanctions.menus.SanctionMenu;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SanctionCMD implements CommandExecutor {

    private SpartaSanctions plugin;

    public SanctionCMD(SpartaSanctions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if(cmd.getName().equalsIgnoreCase("sanction")) {
            if (sender instanceof Player) {

                Player p = (Player) sender;

                if (p.hasPermission("spartacube.mod")) {

                    if (args.length == 0) {
                        p.sendMessage("§cSyntaxe: /sanction <pseudo>");
                    }

                    if (args.length == 1) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                        if (target == null) {
                            p.sendMessage("§cCe joueur n'existe pas !");
                            return false;
                        }

                        PlayerMenu m = new PlayerMenu(p, target, plugin);
                        m.open();

                    }

                    if (args.length == 2) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                        if (target == null) {
                            p.sendMessage("§cCe joueur n'existe pas !");
                            return false;
                        }

                        if (args[1].equalsIgnoreCase("history")) {
                            HistoryMenu menu = new HistoryMenu(p, target, plugin);
                            menu.open();
                        }

                        if (args[1].equalsIgnoreCase("sanction")) {
                            SanctionMenu menu = new SanctionMenu(p, target, plugin);
                            menu.open();
                        }

                    }
                } else {
                    p.sendMessage("§cTu n'as pas la permission de faire cela");
                }
            }
        }
        return false;
    }
}
