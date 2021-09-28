package com.queendev.cash;

import com.queendev.cash.commands.CashCommand;
import com.queendev.cash.manager.ConfigManager;
import com.queendev.cash.repository.providers.MySQL;
import com.queendev.cash.repository.providers.SQLite;
import com.queendev.cash.manager.CashManager;
import com.queendev.cash.process.AccountProcess;
import com.queendev.cash.repository.Database;
import org.bukkit.plugin.java.JavaPlugin;

public class Cash extends JavaPlugin {

    private static Cash instance;
    public ConfigManager config;
    public Database database;
    public CashManager manager;

    public static Cash getPlugin() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        config = new ConfigManager(null, "config.yml", false);
        database = config.getBoolean("MySQL.ativar") ? new MySQL() : new SQLite();
        database.open();
        manager = new CashManager();

        getCommand("cash").setExecutor(new CashCommand());

        AccountProcess.loadAccounts();
    }

    @Override
    public void onDisable() {
        AccountProcess.saveAccounts();
        database.close();
    }
}
