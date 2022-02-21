package fr.mrlaikz.spartasanctions.database;

import fr.mrlaikz.spartasanctions.SpartaSanctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private SpartaSanctions plugin;

    public Database(SpartaSanctions plugin) {
        this.plugin = plugin;
    }

    private String host = plugin.strConfig("database.host");
    private String port = plugin.strConfig("database.port");
    private String database = plugin.strConfig("database.database");
    private String username = plugin.strConfig("database.username");
    private String password = plugin.strConfig("database.password");

    private Connection connection;

    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false",
                username, password);
    }

    public void disconnect() {
        if(isConnected()) {
            try {
                connection.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
