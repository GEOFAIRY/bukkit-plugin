package com.gmail.kyrans19.TestPlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestPluginCommandExecutor implements CommandExecutor {
    private final TestPlugin plugin;

    public TestPluginCommandExecutor(TestPlugin plugin) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("basic")) { // If the player typed /basic then do the following...
            // do something...
            return true;
        } else if (cmd.getName().equalsIgnoreCase("basic2")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
            } else {
                Player player = (Player) sender;
                // do something
            }
            return true;
        }
        return false;
    }

    public boolean isPlayerOnline(String targetPlayer) {
        Player player = Bukkit.getServer().getPlayer(targetPlayer);
        return player != null;
    }
}