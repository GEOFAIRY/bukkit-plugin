package com.gmail.kyrans19.TestPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

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
       if (cmd.getName().equalsIgnoreCase("smite")) {
           if (smite(sender, args)) return true;
       } else if (cmd.getName().equalsIgnoreCase("kill")) {
           if (kill(sender, args)) return true;
       }
       return false;
    }

    /**
     * method to kill a player or the sender
     * @param sender CommandSender the player who executed the command
     * @param args String[] the command arguments
     * @return boolean command success or failure
     */
    private boolean kill(CommandSender sender, String[] args) {
        if (args.length != 0) { //killother
            if (sender.hasPermission("TestPlugin.kill.other") || !(sender instanceof Player)) {
                Player target = sender.getServer().getPlayer(args[0]);
                // Make sure the player is online.
                if (target == null) {
                    sender.sendMessage(args[0] + " is not currently online.");
                    return true;
                }
                target.setHealth(0.0D);
                return true;
            } else {
                sender.sendMessage("You lack the required permissions for this command");
                return true;
            }
        } else { //killself
            if (sender instanceof Player) {
                if (sender.hasPermission("TestPlugin.kill.other")) {
                    Player player = (Player) sender;
                    player.setHealth(0.0D);
                    return true;
                } else {
                    sender.sendMessage("You lack the required permissions for this command");
                    return true;
                }
            } else {
                sender.sendMessage("Command requires a player to kill");
                return true;
            }
        }
    }

    /**
     * method to smite a player with lightning or a target area
     * @param sender CommandSender the player who executed the command
     * @param args String[] the command arguments
     * @return boolean command success or failure
     */
    private boolean smite(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
        } else {
            Player player = (Player) sender;
            if (args.length != 0) {
                if (isPlayerOnline(args[0])) {
                    Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                    targetPlayer.getWorld().strikeLightning(targetPlayer.getLocation());
                    return true;
                } else {
                    sender.sendMessage("Player is offline or cannot be found!");
                    return true;
                }
            } else {
                player.getWorld().strikeLightning(player.getTargetBlock((Set<Material>) null, 200).getLocation());
                return true;
            }
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