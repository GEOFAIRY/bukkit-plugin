package com.gmail.kyrans19.TestPlugin;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class
 */
public final class TestPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        /**
         * Method executed on plugin load
         */
        this.getCommand("smite").setExecutor(new TestPluginCommandExecutor(this));
        this.getCommand("kill").setExecutor(new TestPluginCommandExecutor(this));
        this.getCommand("tpr").setExecutor(new TestPluginCommandExecutor(this));
        this.getCommand("tpaccept").setExecutor(new TestPluginCommandExecutor(this));
        this.getCommand("tpdecline").setExecutor(new TestPluginCommandExecutor(this));
        getLogger().info("Test Plugin version 1.0 has started!");
    }

    @Override
    public void onDisable() {
        /**
         * Method executed on plugin unload
         */
        getLogger().info("onDisable has been invoked!");
    }
}