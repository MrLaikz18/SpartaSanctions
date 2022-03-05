package fr.mrlaikz.spartasanctions;

import fr.mrlaikz.spartasanctions.commands.SanctionCMD;
import fr.mrlaikz.spartasanctions.database.Database;
import fr.mrlaikz.spartasanctions.database.SQLGetter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public class SpartaSanctions extends JavaPlugin {

    private Database db;
    private SQLGetter sql;
    private SanctionManager manager;
    public static SpartaSanctions instance;

    @Override
    public void onEnable() {
        //CONFIG
        saveDefaultConfig();
        instance = this;
        manager = new SanctionManager(this);

        //DATABASE
        db = new Database(this);
        try {
            db.connect();
            sql = new SQLGetter(this);
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            this.getPluginLoader().disablePlugin(this);
        }

        //COMMANDS
        getCommand("sanction").setExecutor(new SanctionCMD(this));

        //MISC
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "SpartaChannel");
        getLogger().info("Plugin Actif");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin Innactif");
        if(this.db.isConnected()) {
            this.db.disconnect();
        }
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
