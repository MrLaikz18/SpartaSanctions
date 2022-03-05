package fr.mrlaikz.spartasanctions.database;

import fr.mrlaikz.spartasanctions.SpartaSanctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private SpartaSanctions plugin;

    private String host;
    private String port;
    private String database;
    private String username;
    private String password;

    public Database(SpartaSanctions plugin) {
        this.plugin = plugin;
        host = plugin.strConfig("database.host");
        port = plugin.strConfig("database.port");
        database = plugin.strConfig("database.database");
        username = plugin.strConfig("database.username");
        password = plugin.strConfig("database.password");
    }

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
