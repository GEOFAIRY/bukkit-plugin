package com.gmail.kyrans19.TestPlugin;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class TestPluginReadWrite {

    static ArrayList<TestPluginHomeSupport> homeSupports = new ArrayList<>();

    public static void writeHomeToJson(Player player) throws Exception {
        TestPluginHomeSupport newHome = new TestPluginHomeSupport(player.getUniqueId(), player.getLocation().getX(), player.getLocation().getX(), player.getLocation().getX());
        homeSupports.add(newHome);

        Gson gson = new Gson();
        String json = gson.toJson(homeSupports);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("plugins\\TestPlugin\\locations.json"));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            new File("plugins\\TestPlugin\\").mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter("plugins\\TestPlugin\\locations.json"));
            writer.write(json);
            writer.close();
        }
    }


    public static Object readHomeFromJson(Player player) throws Exception {
        FileReader reader = new FileReader("plugins\\TestPlugin\\locations.json");
        JSONParser jsonParser = new JSONParser();
        String output = jsonParser.parse(reader).toString();

        Type collectionType = new TypeToken<ArrayList<TestPluginHomeSupport>>(){}.getType();
        Gson gson = new Gson();
        homeSupports = gson.fromJson(output, collectionType);

        return true;
    }
}
