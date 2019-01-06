package com.gmail.kyrans19.TestPlugin;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class
 */
public final class TestPlugin extends JavaPlugin {
    TestPluginCommandExecutor executor;

    @Override
    public void onEnable() {
        /**
         * Method executed on plugin load
         */
        executor = new TestPluginCommandExecutor();
        this.getCommand("smite").setExecutor(executor);
        this.getCommand("kill").setExecutor(executor);
        this.getCommand("tpr").setExecutor(executor);
        this.getCommand("tpaccept").setExecutor(executor);
        this.getCommand("tpdecline").setExecutor(executor);
        this.getCommand("heal").setExecutor(executor);
        getLogger().info("Test Plugin version 1.0 has started!");
    }

    @Override
    public void onDisable() {
        /**
         * Method executed on plugin unload
         */
        executor.clearTpr();
        getLogger().info("onDisable has been invoked!");
    }
}