package fr.mrlaikz.spartasanctions;

import fr.mrlaikz.spartasanctions.commands.SanctionCMD;
import fr.mrlaikz.spartasanctions.database.Database;
import fr.mrlaikz.spartasanctions.database.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public class SpartaSanctions extends JavaPlugin {

    private Database db;
    private SQLGetter sql = new SQLGetter(this);
    private SanctionManager manager;
    public static SpartaSanctions instance;

    @Override
    public void onEnable() {
        //CONFIG
        saveDefaultConfig();
        instance = this;
        manager = new SanctionManager(this);

        //DATABASE
        this.db = new Database(this);
        try {
            db.connect();
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //COMMANDS
        getCommand("sanction").setExecutor(new SanctionCMD(this));

        //MISC
        getLogger().info("Plugin Actif");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin Innactif");
        this.db.disconnect();
    }

    public Connection getDatabase() {
        return db.getConnection();
    }

    public SQLGetter getSQL() {
        return sql;
    }

    public static SpartaSanctions getInstance() {
        return instance;
    }

    public SanctionManager getSanctionManager() {
        return manager;
    }

    public String strConfig(String path) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path));
    }

}
