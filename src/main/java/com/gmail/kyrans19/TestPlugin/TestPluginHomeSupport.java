package com.gmail.kyrans19.TestPlugin;

import java.util.UUID;

public class TestPluginHomeSupport {
    TestPluginHomeSupport(UUID uuid, double x, double y, double z) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private UUID uuid;
    private double x;
    private double y;
    private double z;

    @Override
    public String toString() {
        return "\"" + uuid + "\": {\"x\": "+ x +", \"y\": " + y + ",\"z\": " + z + "}";
    }
}
