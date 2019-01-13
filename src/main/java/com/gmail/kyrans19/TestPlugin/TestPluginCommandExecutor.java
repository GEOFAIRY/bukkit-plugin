/*
   Program by: Kyran Stagg with partial help from Bryan Lim
   Slob on me knob
   mo bamba

 */

package com.gmail.kyrans19.TestPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import static org.bukkit.Bukkit.getAllowFlight;
import static org.bukkit.Bukkit.getLogger;

/**
 * Command Executor class, checks input command and does what it needs to.
 */
public class TestPluginCommandExecutor implements CommandExecutor {
    static ArrayList<TestPluginHomeSupport> homeSupports = new ArrayList<>();
    private ArrayList<ArrayList<Object>> teleportArray = new ArrayList<>();
    private TestPlugin testPlugin;

    TestPluginCommandExecutor(TestPlugin plugin) {
        this.testPlugin = plugin;
    }

    void clearTpr() {
        teleportArray = new ArrayList<>();
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
        } else if (cmd.getName().equalsIgnoreCase("heal")) {
            return heal(sender, args);
        } else if (cmd.getName().equalsIgnoreCase("spawn")){
            return spawn(sender, args);
        } else if (cmd.getName().equalsIgnoreCase("sethome")){
            return sethome(sender, args);
        } else if (cmd.getName().equalsIgnoreCase("home")){
            return home(sender, args);
        } else if (cmd.getName().equalsIgnoreCase("testplugin")){
            return version(sender, args);
        } else if (cmd.getName().equalsIgnoreCase("feed")){
            return feed(sender, args);
        } else if (cmd.getName().equalsIgnoreCase("fly")) {
            return fly(sender, args);
        }
        return false;
    }

    /**
     * method to return the version number to the sender
     * @param sender CommandSender the player who executed the command
     * @param args   String[] the command arguments
     * @return boolean command success or failure
     */
    private boolean version(CommandSender sender, String[] args) {
        sender.sendMessage(String.format("§aTestPlugin current version: %s", testPlugin.getVersion()));
        return true;
    }

    /**
     * method to handle moving a player to their home
     * @param sender CommandSender the player who executed the command
     * @param args   String[] the command arguments
     * @return boolean command success or failure
     */
   private boolean home(CommandSender sender, String[] args) {
       if (sender instanceof Player) {
           try {
               TestPluginReadWrite.readHomeFromJson();
           } catch (Exception e) {
               try {
                   TestPluginReadWrite.readHomeFromJson();
               } catch (Exception e1) {
                   getLogger().warning("Couldn't load home list from json file!");
                   sender.sendMessage("An error has occurred, please try again later.");
               }
           }

           for (TestPluginHomeSupport home : homeSupports) {
               if (((Player) sender).getUniqueId() == home.getUuid()) {
                   if (((Player) sender).getUniqueId().equals(home.getUuid())) {
                       ((Player) sender).teleport(new Location(Bukkit.getServer().getWorld(home.getWorld()), home.getX(), home.getY(), home.getZ()));
                       sender.sendMessage("Moved Home");
                       return true;
                   }
               }
           }
           sender.sendMessage("No home set");
           return true;
       } else{
           sender.sendMessage("Only a player can go to a home");
           return true;
       }
   }

    /**
     * method to handle setting a player home
     * @param sender CommandSender the player who executed the command
     * @param args   String[] the command arguments
     * @return boolean command success or failure
     */
    private boolean sethome(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            try {
                TestPluginHomeSupport newHome = new TestPluginHomeSupport(((Player) sender).getUniqueId(), ((Player) sender).getLocation().getX(),
                        ((Player) sender).getLocation().getY(), ((Player) sender).getLocation().getZ(), ((Player) sender).getLocation().getWorld());

                try {
                    for (TestPluginHomeSupport i : homeSupports) {
                        if (((Player) sender).getUniqueId().equals(i.getUuid())) {
                            homeSupports.remove(i);
                        }
                    }
                } catch (ConcurrentModificationException e) {
                    for (TestPluginHomeSupport i : homeSupports) {
                        if (((Player) sender).getUniqueId().equals(i.getUuid())) {
                            homeSupports.remove(i);
                        }
                    }
                }
                homeSupports.add(newHome);
                TestPluginReadWrite.writeHomesToJson();
                sender.sendMessage("New home set");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            sender.sendMessage("Only a player can set a home");
            return true;
        }
        return false;
    }

    /**
     * method to handle spawn teleporting
     * @param sender CommandSender the player who executed the command
     * @param args   String[] the command arguments
     * @return boolean command success or failure
     */
    private boolean spawn(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            ((Player) sender).teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
            sender.sendMessage("Moved to spawn");
            return true;
        } else {
            sender.sendMessage("Must be player to move to spawn");
            return true;
        }
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
                    ((Player) sender).setHealth(((Player) sender).getHealth() + healAmount);
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
                Player target = sender.getServer().getPlayer(args[1]);
                // Make sure the player is online.
                if (target == null) {
                    sender.sendMessage(args[1] + " is not currently online or cannot be found.");
                    return true;
                }
                if (healAmount > 0 && healAmount <= target.getMaxHealth()) {
                    target.setHealth(target.getHealth() + healAmount);
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
            for (ArrayList<Object> i : teleportArray) {
                if (((Player) i.get(1)).getUniqueId() == ((Player) sender).getUniqueId()) {
                    sender.sendMessage("Teleport Declined");
                    ((Player) i.get(0)).sendMessage(String.format("Teleport request to %s was declined", ((Player) sender).getDisplayName()));
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
            for (ArrayList<Object> i : teleportArray) {
                if (((Player) i.get(1)).getUniqueId() == ((Player) sender).getUniqueId()) {
                    Player target = sender.getServer().getPlayer(((Player) i.get(0)).getUniqueId());
                    // Make sure the player is online.
                    if (target == null) {
                        sender.sendMessage(args[0] + " is not currently online.");
                        teleportArray.remove(i);
                        return true;
                    }
                    long test = System.currentTimeMillis();
                    if(test >= (((long) i.get(2)) + 60*1000)) { //multiply by 1000 to get milliseconds
                        sender.sendMessage("Teleport request timed out");
                        teleportArray.remove(i);
                        return true;
                    }
                    target.teleport(((Player) sender).getLocation());
                    sender.sendMessage("Teleport Accepted");
                    target.sendMessage("Teleport Accepted");
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


                for (ArrayList<Object> i : teleportArray) {
                    if (((Player) i.get(1)).getUniqueId() == target.getUniqueId()) {
                        teleportArray.remove(i);
                    }
                }

                ArrayList<Object> temp = new ArrayList<>();
                temp.add(targetSender);
                temp.add(target);
                temp.add(System.currentTimeMillis());
                teleportArray.add(temp);
                target.sendMessage(targetSender.getDisplayName() + " is requesting to teleport to you!\nType /tpaccept to accept or /tpdecline to decline.");
                sender.sendMessage(String.format("Requested to teleport to %s", target.getDisplayName()));
                return true;
            } else {
                sender.sendMessage("Please enter a player to request a teleport to");
            }
        } else {
            sender.sendMessage("Must be a player to use this command.");
            return true;
        }
        sender.sendMessage("all failed :(");
        return false;
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

    /** method to feed player self AND feed targeted player
     * @param sender CommandSender the player who executed the command
     * @param args   String[] the command arguments
     * @return boolean command success or failure
     */
    private boolean feed(CommandSender sender, String[] args) {

        int feedAmount;

        if (sender instanceof Player) {

            if (args.length == 0) {         // Feed self to full
                ((Player) sender).setFoodLevel(((Player) sender).getFoodLevel());
                sender.sendMessage("§aYou are full");
                return true;

            } else if (args.length == 1) {      //Heal self by given amount
                try {
                    feedAmount = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("Feed amount must be a number");
                    return true;
                }
                if (feedAmount > 0 && feedAmount <= ((Player) sender).getFoodLevel()) {
                    ((Player) sender).setFoodLevel(((Player) sender).getFoodLevel() + feedAmount);
                    sender.sendMessage(String.format("§aFed for %d", feedAmount));
                    return true;
                } else {
                    sender.sendMessage("Can only feed an amount between 0 and 10");
                    return true;
                }


            } else if (args.length == 2) {      //Heal a target by given amount
                try {
                    feedAmount = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("Feed amount must be a number");
                    return true;
                }
                Player target = sender.getServer().getPlayer(args[1]);
                // Make sure the player is online.
                if (target == null) {
                    sender.sendMessage(args[1] + " is not currently online or cannot be found.");
                    return true;
                }
                if (feedAmount > 0 && feedAmount <= 10) {
                    target.setFoodLevel(target.getFoodLevel() + feedAmount);
                    sender.sendMessage(String.format("§aFed %s for %d", target.getDisplayName(), feedAmount));
                    return true;
                } else {
                    sender.sendMessage("Can only feed an amount between 0 and 10");
                    return true;
                }


            } else {
                sender.sendMessage("Too many arguments");
                return false;
            }
        } else {
            sender.sendMessage("Must be a player to feed");
            return false;
        }
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

    /**
     * method to enable flight on player
     * @param sender CommandSender is the player who sent the command
     * @param args the number of arguments that are parsed
     * @return
     */
    private boolean fly(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (sender != null) {
            if (args.length == 0) {
                if (!player.getAllowFlight()) { //returns false if playerself is not flying
                    player.setAllowFlight(true);
                    return true;
                } else if (player.getAllowFlight()) { //returns true if playerself is flying
                    player.setAllowFlight(false);
                    return true;
                }

            } else if (args.length > 0) {
                sender.sendMessage("Too many arguments");
                return false;
            }
        }
    return false;
    }



}