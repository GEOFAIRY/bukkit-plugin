package com.gmail.kyrans19.TestPlugin;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class
 */
public final class TestPlugin extends JavaPlugin {
    TestPluginCommandExecutor executor;
    String version = "1.2-SNAPSHOT";

    public String getVersion() {
        return version;
    }

    @Override
    public void onEnable() {
        /**
         * Method executed on plugin load
         */
        executor = new TestPluginCommandExecutor(this);

        try {
            TestPluginReadWrite.readHomeFromJson();
            getLogger().info("Loaded homes from json file");
        } catch (Exception e) {
            getLogger().warning("Couldn't load home list from json file!");
        }

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
        try {
            TestPluginReadWrite.writeHomesToJson();
            getLogger().info("Saved homes to json file");
        } catch (Exception e) {
            getLogger().warning("Couldn't save home list to json file!");
        }
        getLogger().info("onDisable has been invoked!");
    }
}