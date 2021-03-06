package fr.mrlaikz.spartasanctions.database;

import fr.mrlaikz.spartasanctions.SpartaSanctions;
import fr.mrlaikz.spartasanctions.enums.SanctionType;
import fr.mrlaikz.spartasanctions.enums.Context;
import fr.mrlaikz.spartasanctions.objects.Sanction;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SQLGetter {

    private SpartaSanctions plugin;
    private Connection db;
    private String table;

    public SQLGetter(SpartaSanctions plugin) {
        this.plugin = plugin;
        this.db = plugin.getDatabase();
        table = plugin.strConfig("database.table");
    }

    //BASIC QUERYS
    public List<Sanction> getSanctions(OfflinePlayer p) {
        List<Sanction> sanctions = new ArrayList<Sanction>();
        try {
            PreparedStatement ps = db.prepareStatement("SELECT * FROM " + table + " WHERE sanctioned = ?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                OfflinePlayer sanctioned = p;
                SanctionType type = SanctionType.valueOf(rs.getString("type"));
                String time = rs.getString("time");
                String reason = rs.getString("reason");
                String sanctioner = rs.getString("sanctioner");
                String date = rs.getString("date");
                Context c = Context.valueOf(rs.getString("context"));
                Sanction s = new Sanction(sanctioner, sanctioned, type, time, reason, date, c);
                sanctions.add(s);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return sanctions;
    }

    public List<Sanction> getSanctions(OfflinePlayer p, SanctionType type) {
        List<Sanction> sanctions = new ArrayList<Sanction>();
        try {
            PreparedStatement ps = db.prepareStatement("SELECT * FROM " + table + " WHERE sanctioned = ? AND type = ? ORDER BY id DESC LIMIT 18");
            ps.setString(1, p.getUniqueId().toString());
            ps.setString(2, type.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                OfflinePlayer sanctioned = p;
                String time = rs.getString("time");
                String reason = rs.getString("reason");
                String sanctioner = rs.getString("sanctioner");
                String date = rs.getString("date");
                Context c = Context.valueOf(rs.getString("context"));
                Sanction s = new Sanction(sanctioner, sanctioned, type, time, reason, date, c);
                sanctions.add(s);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return sanctions;
    }

    public List<Sanction> getSanctions(OfflinePlayer p, SanctionType type, String reason) {
        List<Sanction> sanctions = new ArrayList<Sanction>();
        try {
            PreparedStatement ps = db.prepareStatement("SELECT * FROM " + table + " WHERE sanctioned = ? AND type = ? AND reason = ? ORDER BY id DESC LIMIT 18");
            ps.setString(1, p.getUniqueId().toString());
            ps.setString(2, type.toString());
            ps.setString(3, reason);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                OfflinePlayer sanctioned = p;
                String time = rs.getString("time");
                String sanctioner = rs.getString("sanctioner");
                String date = rs.getString("date");
                Context c = Context.valueOf(rs.getString("context"));
                Sanction s = new Sanction(sanctioner, sanctioned, type, time, reason, date, c);
                sanctions.add(s);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return sanctions;
    }

    public List<Sanction> getSanctions(OfflinePlayer p, Context c) {
        List<Sanction> sanctions = new ArrayList<Sanction>();
        try {
            PreparedStatement ps = db.prepareStatement("SELECT * FROM " + table + " WHERE sanctioned = ?  AND context = ? ORDER BY id DESC LIMIT 18");
            ps.setString(1, p.getUniqueId().toString());
            ps.setString(2, c.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                OfflinePlayer sanctioned = p;
                String time = rs.getString("time");
                SanctionType type = SanctionType.valueOf(rs.getString("type"));
                String sanctioner = rs.getString("sanctioner");
                String reason = rs.getString("reason");
                String date = rs.getString("date");
                Sanction s = new Sanction(sanctioner, sanctioned, type, time, reason, date, c);
                sanctions.add(s);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return sanctions;
    }

    public void addSanction(Sanction s) {
        try {
            PreparedStatement ps = db.prepareStatement("INSERT INTO " + table + " (sanctioned, sanctioned_name, type, time, reason, sanctioner, date, context) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, s.getSanctioned().getUniqueId().toString());
            ps.setString(2, s.getSanctioned().getName());
            ps.setString(3, s.getType().toString());
            ps.setString(4, s.getTime());
            ps.setString(5, s.getReason());
            ps.setString(6, s.getSanctioner());
            ps.setString(7, s.getDate());
            ps.setString(8, s.getContext().name());
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    //ASYNC QUERYS
    public CompletableFuture<List<Sanction>> getSanctionsAsync(OfflinePlayer p) {
        return future(() -> getSanctions(p));
    }
    public CompletableFuture<List<Sanction>> getSanctionsAsync(OfflinePlayer p, SanctionType type) {
        return future(() -> getSanctions(p, type));
    }
    public CompletableFuture<List<Sanction>> getSanctionsAsync(OfflinePlayer p, SanctionType type, String reason) {
        return future(() -> getSanctions(p, type, reason));
    }
    public CompletableFuture<Void> addSanctionAsync(Sanction s) {
        return future(() -> addSanction(s));
    }
    public CompletableFuture<List<Sanction>> getSanctionsAsync(OfflinePlayer p, Context c) {
        return future(() -> getSanctions(p, c));
    }

    //FUTURE
    public CompletableFuture<Void> future(Runnable runnable) {
        return CompletableFuture.runAsync(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new CompletionException(e);
            }
        });
    }

    public <T> CompletableFuture<T> future(Callable<T> supplier) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return supplier.call();
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new CompletionException(e);
            }
        });
    }


}
