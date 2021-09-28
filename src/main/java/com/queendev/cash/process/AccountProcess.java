package com.queendev.cash.process;

import com.queendev.cash.Cash;
import com.queendev.cash.models.Account;
import com.queendev.cash.manager.CashManager;
import com.queendev.cash.repository.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountProcess {

    private static Database database = Cash.getPlugin().database;
    private static CashManager manager = Cash.getPlugin().manager;

    public static void loadAccounts() {
        try {
            PreparedStatement stm = database.getConnection().prepareStatement(
                    "select * from cashplugin"
            );

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String playerName = rs.getString("player");
                double cash = rs.getDouble("cash");

                manager.getAccountOrCreate(playerName, cash);
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static boolean containsAccountSQL(String playerName) {
        try {
            PreparedStatement stm = database.getConnection().prepareStatement(
                    "select * from cashplugin where player = ?"
            );
            stm.setString(1, playerName);
            return stm.executeQuery().next();
        }catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void saveAccounts() {
        PreparedStatement stm;
        try {
            for (Account account : manager.accounts.values()) {
                if(containsAccountSQL(account.getPlayerName())) {
                    stm = database.getConnection().prepareStatement(
                            "update cashplugin set cash = ? where player = ?"
                    );

                    stm.setDouble(1, account.getCash());
                    stm.setString(2, account.getPlayerName());

                    stm.executeUpdate();
                }else {
                    stm = database.getConnection().prepareStatement(
                            "insert into cashplugin(player, cash) VALUES(?,?)"
                    );

                    stm.setString(1, account.getPlayerName());
                    stm.setDouble(2, account.getCash());

                    stm.executeUpdate();
                }
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
