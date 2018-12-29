package com.gmail.kyrans19.TestPlugin;

import org.bukkit.plugin.java.JavaPlugin;


public final class TestPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // This will throw a NullPointerException if you don't have the command defined in your plugin.yml file!
        this.getCommand("basic").setExecutor(new TestPluginCommandExecutor(this));
        this.getCommand("basic2").setExecutor(new TestPluginCommandExecutor(this));
        getLogger().info("onEnable has been invoked!");
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable has been invoked!");
    }
}