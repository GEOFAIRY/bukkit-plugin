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
        this.getCommand("basic").setExecutor(new TestPluginCommandExecutor(this));
        this.getCommand("basic2").setExecutor(new TestPluginCommandExecutor(this));
        getLogger().info("onEnable has been invoked!");
    }

    @Override
    public void onDisable() {
        /**
         * Method executed on plugin unload
         */
        getLogger().info("onDisable has been invoked!");
    }
}