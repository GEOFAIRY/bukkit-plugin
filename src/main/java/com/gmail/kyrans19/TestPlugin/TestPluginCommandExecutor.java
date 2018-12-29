package com.gmail.kyrans19.TestPlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Executor class, checks input command and does what it needs to.
 */
public class TestPluginCommandExecutor implements CommandExecutor {
    private final TestPlugin plugin;

    /**
     * command executor constructor
     * @param plugin TestPlugin the main plugin instance
     */
    public TestPluginCommandExecutor(TestPlugin plugin) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    /**
     * method that is executed when a given command is executed by a command sender type
     * @param sender CommandSender the entity that executed the command
     * @param cmd Command the command type
     * @param label String god knows what
     * @param args String the command arguments after the command
     * @return Boolean if the command was found
     */
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

    /**
     * method to check if a given player is currently online
     * @param targetPlayer String The name of the player to check
     * @return boolean if the player is online
     */
    public boolean isPlayerOnline(String targetPlayer) {
        Player player = Bukkit.getServer().getPlayer(targetPlayer);
        return player != null;
    }
}