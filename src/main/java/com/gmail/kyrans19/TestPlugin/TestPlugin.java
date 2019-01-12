package com.gmail.kyrans19.TestPlugin;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class
 */
public final class TestPlugin extends JavaPlugin {
    TestPluginCommandExecutor executor;
    String version = "1.1-SNAPSHOT";

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void onEnable() {
        /**
         * Method executed on plugin load
         */
        executor = new TestPluginCommandExecutor(this);
        this.getCommand("testplugin").setExecutor(executor);
        this.getCommand("smite").setExecutor(executor);
        this.getCommand("kill").setExecutor(executor);
        this.getCommand("tpr").setExecutor(executor);
        this.getCommand("tpaccept").setExecutor(executor);
        this.getCommand("tpdecline").setExecutor(executor);
        this.getCommand("heal").setExecutor(executor);
        this.getCommand("spawn").setExecutor(executor);
        this.getCommand("sethome").setExecutor(executor);
        this.getCommand("home").setExecutor(executor);
        getLogger().info("Test Plugin version " + version +  " has started!");
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