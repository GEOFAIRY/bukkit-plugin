package com.gmail.kyrans19.TestPlugin;

import org.bukkit.World;

import java.util.UUID;

public class TestPluginHomeSupport {
    /**
     * default constructor for a TestPluginHomeSupport
     * @param uuid UUID the player uuid
     * @param x double the home x value
     * @param y double the home y value
     * @param z double the home z value
     * @param world UUID the world uuid for the home
     */
    TestPluginHomeSupport(UUID uuid, double x, double y, double z, World world) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world.getUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public UUID getWorld() {
        return world;
    }

    private UUID uuid;
    private double x;
    private double y;
    private double z;
    private UUID world;
}
