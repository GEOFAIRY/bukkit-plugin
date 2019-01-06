package com.gmail.kyrans19.TestPlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;

/**
 * Command Executor class, checks input command and does what it needs to.
 */
public class TestPluginCommandExecutor implements CommandExecutor {
    private ArrayList<ArrayList<Player>> teleportArray = new ArrayList<>();

    void clearTpr() {
        teleportArray = new ArrayList<>();
    }

    /**
     * command executor constructor
     */
    TestPluginCommandExecutor() {
    }

    /**
     * method that is executed when a given command is executed by a command sender type
     *
     * @param sender CommandSender the entity that executed the command
     * @param cmd    Command the command type
     * @param label  String god knows what
     * @param args   String the command arguments after the command
     * @return Boolean if the command was found
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("smite")) {
            return smite(sender, args);
        } else if (cmd.getName().equalsIgnoreCase("kill")) {
            return kill(sender, args);
        } else if (cmd.getName().equalsIgnoreCase("tpr")) {
            return teleportRequest(sender, args);
        } else if (cmd.getName().equalsIgnoreCase("tpaccept")) {
            return teleportAccept(sender, args);
        } else if (cmd.getName().equalsIgnoreCase("tpdecline")) {
            return teleportDecline(sender, args);
        } else if (cmd.getName().equalsIgnoreCase("tpdecline")) {
            return heal(sender, args);
        }
        return false;
    }


    /**
     * method to heal a player with given parameters
     * @param sender CommandSender the player who executed the command
     * @param args   String[] the command arguments
     * @return boolean command success or failure
     */
    private boolean heal(CommandSender sender, String[] args) {
        if (sender instanceof Player) {

            if (args.length == 0) {         // Heal Self to full
                ((Player) sender).setHealth(((Player) sender).getMaxHealth());
                sender.sendMessage("§aHealed");
                return true;


            } else if (args.length == 1) {      //Heal self by given amount
                Double healAmount;
                try {
                    healAmount = Double.parseDouble(args[0]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("Heal amount must be a number");
                    return true;
                }
                if (healAmount > 0 && healAmount <= ((Player) sender).getMaxHealth()) {
                    ((Player) sender).setHealth(healAmount);
                    sender.sendMessage("§aHealed for " + healAmount.toString());
                    return true;
                } else {
                    Double maxHealth = ((Player) sender).getMaxHealth();
                    sender.sendMessage(String.format("Can only heal an amount between 0 and %.2f", maxHealth));
                    return true;
                }


            } else if (args.length == 2) {      //Heal a target by given amount
                Double healAmount;
                try {
                    healAmount = Double.parseDouble(args[0]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("Heal amount must be a number");
                    return true;
                }
                Player target = sender.getServer().getPlayer(args[0]);
                // Make sure the player is online.
                if (target == null) {
                    sender.sendMessage(args[0] + " is not currently online or cannot be found.");
                    return true;
                }
                if (healAmount > 0 && healAmount <= target.getMaxHealth()) {
                    target.setHealth(healAmount);
                    sender.sendMessage(String.format("§aHealed %s for %s", target.getDisplayName(), healAmount.toString()));
                    return true;
                } else {
                    Double maxHealth = target.getMaxHealth();
                    sender.sendMessage(String.format("Can only heal an amount between 0 and %.2f", maxHealth));
                    return true;
                }


            } else {
                sender.sendMessage("Too many arguments");
                return false;
            }
        } else {
            sender.sendMessage("Must be a player to heal");
            return false;
        }
    }


    /**
     * method to decline a teleport request
     * @param sender CommandSender the player who executed the command
     * @param args   String[] the command arguments
     * @return boolean command success or failure
     */
    private boolean teleportDecline(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            for (ArrayList<Player> i : teleportArray) {
                if (i.get(1).getUniqueId() == ((Player) sender).getUniqueId()) {
                    sender.sendMessage("Teleport Declined");
                    teleportArray.remove(i);
                    return true;
                }
            }
            sender.sendMessage("No requests to teleport");
            return true;
        } else {
            sender.sendMessage("Only can be used by a player");
            return true;
        }
    }

    /**
     * method to accept a teleport request
     * @param sender CommandSender the player who executed the command
     * @param args   String[] the command arguments
     * @return boolean command success or failure
     */
    private boolean teleportAccept(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            for (ArrayList<Player> i : teleportArray) {
                if (i.get(1).getUniqueId() == ((Player) sender).getUniqueId()) {
                    Player target = sender.getServer().getPlayer(i.get(0).getUniqueId());
                    // Make sure the player is online.
                    if (target == null) {
                        sender.sendMessage(args[0] + " is not currently online.");
                        teleportArray.remove(i);
                        return true;
                    }
                    target.teleport(((Player) sender).getLocation());
                    sender.sendMessage("Teleport Accepted");
                    teleportArray.remove(i);
                    return true;
                }
            }
            sender.sendMessage("No requests to teleport");
            return true;
        } else {
            sender.sendMessage("Only can be used by a player");
            return true;
        }
    }

    /**
     * Method to handle teleport requests. A player can request to teleport to another player.
     *
     * @param sender CommandSender the player who executed the command
     * @param args   String[] the command arguments
     * @return boolean command success or failure
     */
    private boolean teleportRequest(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player target = sender.getServer().getPlayer(args[0]);
                // Make sure the player is online.
                if (target == null) {
                    sender.sendMessage(args[0] + " is not currently online.");
                    return true;
                }
                Player targetSender = (Player) sender;
                if (target.getUniqueId() == targetSender.getUniqueId()) {
                    sender.sendMessage("Cannot request to teleport to yourself.");
                    return true;
                }


                for (ArrayList<Player> i : teleportArray) {
                    if (i.get(1).getUniqueId() == target.getUniqueId()) {
                        teleportArray.remove(i);
                    }
                }

                ArrayList<Player> temp = new ArrayList<>();
                temp.add(targetSender);
                temp.add(target);
                teleportArray.add(temp);
                target.sendMessage(targetSender.getDisplayName() + " is requesting to teleport to you!\nType /tpaccept to accept or /tpdecline to decline.");
                sender.sendMessage(String.format("Requested to teleport to %s", target.getDisplayName()));
                return true;
            } else {
                sender.sendMessage("Please enter a player to request a teleport to");
                return false;
            }
        } else {
            sender.sendMessage("Must be a player to use this command.");
            return true;
        }
    }

    /**
     * method to kill a player or the sender
     *
     * @param sender CommandSender the player who executed the command
     * @param args   String[] the command arguments
     * @return boolean command success or failure
     */
    private boolean kill(CommandSender sender, String[] args) {
        if (args.length == 1) { //killother
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
                sender.sendMessage("You lack the required permissions to kill other players");
                return true;
            }
        } else if (args.length == 0) { //killself
            if (sender instanceof Player) {
                if (sender.hasPermission("TestPlugin.kill.self")) {
                    Player player = (Player) sender;
                    player.setHealth(0.0D);
                    return true;
                } else {
                    sender.sendMessage("You lack the required permissions for this command");
                    return true;
                }
            }
        } else {
            sender.sendMessage("Too many arguments");
            return false;
        }
        return false;
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
            if (args.length == 1) {
                if (isPlayerOnline(args[0])) {
                    Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                    targetPlayer.getWorld().strikeLightning(targetPlayer.getLocation());
                    return true;
                } else {
                    sender.sendMessage("Player is offline or cannot be found!");
                    return true;
                }
            } else if (args.length == 0){
                player.getWorld().strikeLightning(player.getTargetBlock(null, 200).getLocation());
                return true;
            } else {
                sender.sendMessage("Too many arguments");
            }
        }
        return false;
    }

    /**
     * method to check if a given player is currently online
     * @param targetPlayer String The name of the player to check
     * @return boolean if the player is online
     */
    private boolean isPlayerOnline(String targetPlayer) {
        Player player = Bukkit.getServer().getPlayer(targetPlayer);
        return player != null;
    }


}