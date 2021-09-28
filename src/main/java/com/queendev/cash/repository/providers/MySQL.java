package com.queendev.cash.repository.providers;

import com.queendev.cash.Cash;
import com.queendev.cash.repository.Database;
import org.bukkit.Bukkit;

import java.sql.*;

public class MySQL implements Database {

    private Connection connection;

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void open() {
        String host = Cash.getPlugin().config.getString("MySQL.host");
        String user = Cash.getPlugin().config.getString("MySQL.usuario");
        String password = Cash.getPlugin().config.getString("MySQL.senha");
        String database = Cash.getPlugin().config.getString("MySQL.database");
        String url = "jdbc:mysql://" + host + "/" + database + "?autoReconnect=true";

        try {
            connection = DriverManager.getConnection(url, user, password);
            createTable();
            Bukkit.getConsoleSender().sendMessage("§a[CashPlugin] §aConexão com MySQL estabelecida com sucesso.");
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage("§c[CashPlugin] §cHouve um erro ao conectar-se com o MySQL!");
            ex.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(Cash.getPlugin());
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void createTable() {
        try {
            PreparedStatement stm = this.connection.prepareStatement(
                    "create table if not exists cashplugin(`player` TEXT NOT NULL, " +
                            "`cash` DOUBLE NOT NULL)");
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}