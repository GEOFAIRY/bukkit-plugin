package com.gmail.kyrans19.TestPlugin;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * class to handle reading and writing of json to an external file
 */
public class TestPluginReadWrite {

    /**
     * method to write the home list to a json file
     * @throws Exception needed for writing to a file
     */
    public static void writeHomesToJson() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(TestPluginCommandExecutor.homeSupports);
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

    /**
     * method to read homes from a json and place in the home support array
     * @throws Exception need for reading a file
     */
    public static void readHomeFromJson() throws Exception {
        FileReader reader = new FileReader("plugins\\TestPlugin\\locations.json");
        JSONParser jsonParser = new JSONParser();
        String output = jsonParser.parse(reader).toString();

        Type collectionType = new TypeToken<ArrayList<TestPluginHomeSupport>>(){}.getType();
        Gson gson = new Gson();
        TestPluginCommandExecutor.homeSupports = gson.fromJson(output, collectionType);
    }
}
