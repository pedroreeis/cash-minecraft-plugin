package com.queendev.cash.repository.providers;

import com.queendev.cash.Cash;
import com.queendev.cash.repository.Database;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.*;

public class SQLite implements Database {

    private Connection connection;

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void open() {
        File file = new File(Cash.getPlugin().getDataFolder(), "database.db");
        String url = "jdbc:sqlite:" + file;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            createTable();
            Bukkit.getConsoleSender().sendMessage("§a[CashPlugin] §aConexão com SQLite estabelecida com sucesso.");
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("§c[CashPlugin] §cHouve um erro ao conectar-se com o SQLite!");
            ex.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(Cash.getPlugin());
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTable() {
        try {
            PreparedStatement stm = this.connection.prepareStatement(
                    "create table if not exists cashplugin(`player` TEXT, " +
                            "`cash` DOUBLE)"
            );
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}