package net.equinox.wild.equinox;


import io.github.bananapuncher714.nbteditor.NBTEditor;
import net.equinox.wild.equinox.entities.DbHorse;
import net.equinox.wild.equinox.entities.IllnessColic;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

import static net.equinox.wild.equinox.Events1.collection;

@SuppressWarnings("all")
public class Commands implements CommandExecutor {
    private final Equinox plugin;
    public static HashMap<String, String> doublexp = new HashMap<String, String>();
    public static HashMap<UUID, Boolean> lungestat = new HashMap<UUID, Boolean>();
    public static HashMap<UUID, String> access = new HashMap<UUID, String>();
    public static HashMap<UUID, String> bell = new HashMap<UUID, String>();
    public HashMap<String, Long> cooldowns = new HashMap<String, Long>();


    public Commands(Equinox plugin) {
        this.plugin = plugin;

    }



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("eq")) {
            if (args[0].equalsIgnoreCase("ping")) {
                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Pong!");
                return true;
            } else if (args[0].equalsIgnoreCase("hunger")) {
                if (sender.hasPermission("eq.dev")) {
                    Player player = (Player) sender;
                    player.setFoodLevel(1);
                }
            } else if (args[0].equalsIgnoreCase("help")) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------------------------------------------------------");
                player.sendMessage(ChatColor.GRAY + "          " + "[" + ChatColor.YELLOW + " Equinox Help " + ChatColor.GRAY + "]" + "          ");
                player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------------------------------------------------------");
                player.sendMessage("");
                TextComponent msg = new TextComponent(" ● " + ChatColor.GOLD + "[" + ChatColor.YELLOW + "General Commands" + ChatColor.GOLD + "]");
                msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq help2"));
                player.spigot().sendMessage(msg);
                player.sendMessage("");
                TextComponent msg2 = new TextComponent(" ● " + ChatColor.DARK_RED + "[" + ChatColor.RED + "Vet Commands" + ChatColor.DARK_RED + "]");
                msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq help3"));
                player.spigot().sendMessage(msg2);
                player.sendMessage("");
                TextComponent msg3 = new TextComponent(" ● " + ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "Staff Commands" + ChatColor.DARK_AQUA + "]");
                msg3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq help4"));
                player.spigot().sendMessage(msg3);
                player.sendMessage("");
                player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------------------------------------------------------");
                player.sendMessage("");
                return true;
            } else if (args[0].equalsIgnoreCase("help2")) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "---------------------------------------------------------------------------------");
                player.sendMessage(ChatColor.GRAY + "          " + "[" + ChatColor.YELLOW + " Equinox General Commands " + ChatColor.GRAY + "]" + "          ");
                player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "---------------------------------------------------------------------------------");
                player.sendMessage("");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "lunge  " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Lunges your selected horse");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "info   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Displays your selected horse's info");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "list   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Lists your owned horses");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "list [player]   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Lists player's owned horses");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "tp   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "----------------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Teleports you to selected horse");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "tphere   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "------------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Teleports selected horse to you");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "me   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "----------------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Displays your info");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "me [player]   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Displays player's info");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "menu   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Opens equinox GUI");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "home   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Sends horse home");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "sethome   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Sets horse's home");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "lease [player] [price]   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Leases horse to player");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "unlease [player]   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Unleases horse from player");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "trust [player]   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Trusts player to horse");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "private   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Sets horse to private");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "public   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Sets horse to public");
                player.sendMessage("");
                player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "---------------------------------------------------------------------------------");
                player.sendMessage("");
                return true;
            } else if (args[0].equalsIgnoreCase("help3")) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "---------------------------------------------------------------------------------");
                player.sendMessage(ChatColor.GRAY + "          " + "[" + ChatColor.YELLOW + " Equinox Vet Commands " + ChatColor.GRAY + "]" + "          ");
                player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "---------------------------------------------------------------------------------");
                player.sendMessage("");
                player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "Diagnose [illness]   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Diagnoses selected horse");
                player.sendMessage("");
                player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "---------------------------------------------------------------------------------");
                player.sendMessage("");
                return true;
            } else if (args[0].equalsIgnoreCase("help4")) {
                Player player = (Player) sender;
                if (player.hasPermission("eq.staff")) {
                    player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "---------------------------------------------------------------------------------");
                    player.sendMessage(ChatColor.GRAY + "          " + "[" + ChatColor.YELLOW + " Equinox Staff Commands " + ChatColor.GRAY + "]" + "          ");
                    player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "---------------------------------------------------------------------------------");
                    player.sendMessage("");
                    player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "Check   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Checks selected horses unfed time");
                    player.sendMessage(" ● " + ChatColor.GRAY + "/" + ChatColor.WHITE + "eq " + ChatColor.AQUA + "tagadd [Rescue,Invulnerable,Original]   " + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------" + ChatColor.WHITE + "" + ChatColor.ITALIC + "   Adds tag to horse [Admin+]");
                    player.sendMessage("");
                    player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "---------------------------------------------------------------------------------");
                    player.sendMessage("");
                    return true;
                }


            } else if (args[0].equalsIgnoreCase("check")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                Location loc = player.getLocation();
                World world = player.getWorld();
                if(collection.isEmpty()) {
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                    return true;
                }
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            if (player.hasPermission("eq.staff")) {
                                if (e.getScoreboardTags().contains("DayH-1")) {
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "This horse has not eaten for 1 day!");
                                    return true;
                                } if (e.getScoreboardTags().contains("DayH-2")) {
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "This horse has not eaten for 2 days!");
                                    return true;
                                } if (e.getScoreboardTags().contains("DayH-3")) {
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "This horse has not eaten for 3 days!");
                                    return true;
                                } else {
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has been fed!");
                                    return true;
                                }


                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("tagadd")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                Location loc = player.getLocation();
                World world = player.getWorld();
                if (collection.isEmpty()) {
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                    return true;
                }
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            if (player.hasPermission("eq.op")) {
                                if (args[1].equalsIgnoreCase("Rescue")) {
                                    e.addScoreboardTag("Rescue");
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Rescue Tag Added!");
                                    return true;

                                }
                                if (args[1].equalsIgnoreCase("Invulnerable")) {
                                    e.addScoreboardTag("Invulnerable");
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Invulnerable Tag Added!");
                                    return true;

                                }
                                if (args[1].equalsIgnoreCase("Original")) {
                                    e.addScoreboardTag("Original");
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Original Tag Added!");
                                    return true;

                                }
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("color")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                Location loc = player.getLocation();
                World world = player.getWorld();
                player.sendMessage(ChatColor.GRAY + "["+ ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >>" + ChatColor.GREEN + "You hace successfully changed this horses color!");
                if (player.hasPermission("eq.staff")) {
                    if (args[1].equalsIgnoreCase("black")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.BLACK);
                                    ((Horse) e).setHealth(1);
                                    e.addScoreboardTag("Color:Black");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("sealbrown")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.BLACK);
                                    ((Horse) e).setHealth(2);
                                    e.addScoreboardTag("Color:Seal Brown");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("bay")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.BROWN);
                                    ((Horse) e).setHealth(1);
                                    e.addScoreboardTag("Color:Bay");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("sootybay")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.BROWN);
                                    ((Horse) e).setHealth(2);
                                    e.addScoreboardTag("Color:Sooty Bay");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("Chestnut")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.CHESTNUT);
                                    ((Horse) e).setHealth(1);
                                    e.addScoreboardTag("Color:Chestnut");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("flaxenchestnut")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.CHESTNUT);
                                    ((Horse) e).setHealth(2);
                                    e.addScoreboardTag("Color:Flaxen Chestnut");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("lightchestnut")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.CHESTNUT);
                                    ((Horse) e).setHealth(3);
                                    e.addScoreboardTag("Color:Light Chestnut");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("lightflaxenchestnut")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.CHESTNUT);
                                    ((Horse) e).setHealth(4);
                                    e.addScoreboardTag("Color:Light Flaxen Chestnut");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("flaxenchestnut2")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.CHESTNUT);
                                    ((Horse) e).setHealth(5);
                                    e.addScoreboardTag("Color:Flaxen Chestnut2");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("white")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.WHITE);
                                    ((Horse) e).setHealth(1);
                                    e.addScoreboardTag("Color:White");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("white2")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.WHITE);
                                    ((Horse) e).setHealth(2);
                                    e.addScoreboardTag("Color:White2");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("cremello")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.WHITE);
                                    ((Horse) e).setHealth(3);
                                    e.addScoreboardTag("Color:Cremello");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("Cremello2")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.WHITE);
                                    ((Horse) e).setHealth(4);
                                    e.addScoreboardTag("Color:Cremello2");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("palomino")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.CREAMY);
                                    ((Horse) e).setHealth(1);
                                    e.addScoreboardTag("Color:Palomino");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("flaxenpalomino")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.CREAMY);
                                    ((Horse) e).setHealth(2);
                                    e.addScoreboardTag("Color:Flaxen Palomino");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("lightpalomino")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.CREAMY);
                                    ((Horse) e).setHealth(3);
                                    e.addScoreboardTag("Color:Light Palomino");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("lightpalomino2")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.CREAMY);
                                    ((Horse) e).setHealth(4);
                                    e.addScoreboardTag("Color:Light Palomino2");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("buckskin")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.CREAMY);
                                    ((Horse) e).setHealth(5);
                                    e.addScoreboardTag("Color:Buckskin");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("lightbay")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.DARK_BROWN);
                                    ((Horse) e).setHealth(1);
                                    e.addScoreboardTag("Color:Light Bay");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("stripedlightbay")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.DARK_BROWN);
                                    ((Horse) e).setHealth(2);
                                    e.addScoreboardTag("Color:Striped Light Bay");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("gray")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.GRAY);
                                    ((Horse) e).setHealth(1);
                                    e.addScoreboardTag("Color:Silver");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("gray2")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Color:Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:Flaxen Chestnut2");
                                    e.removeScoreboardTag("Color:Light Chestnut");
                                    e.removeScoreboardTag("Color:Light Flaxen Chestnut");
                                    e.removeScoreboardTag("Color:White");
                                    e.removeScoreboardTag("Color:White2");
                                    e.removeScoreboardTag("Color:Cremello");
                                    e.removeScoreboardTag("Color:Cremello2");
                                    e.removeScoreboardTag("Color:Bay");
                                    e.removeScoreboardTag("Color:Light Bay");
                                    e.removeScoreboardTag("Color:Striped Light Bay");
                                    e.removeScoreboardTag("Color:Silver");
                                    e.removeScoreboardTag("Color:Silver2");
                                    e.removeScoreboardTag("Color:Palomino");
                                    e.removeScoreboardTag("Color:Flaxen Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino");
                                    e.removeScoreboardTag("Color:Light Palomino2");
                                    e.removeScoreboardTag("Color:Buckskin");
                                    e.removeScoreboardTag("Color:Black");
                                    e.removeScoreboardTag("Color:Seal Brown");
                                    e.removeScoreboardTag("Color:Sooty Bay");
                                    ((Horse) e).setColor(Horse.Color.GRAY);
                                    ((Horse) e).setHealth(2);
                                    e.addScoreboardTag("Color:Silver2");
                                    return true;
                                }
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("style")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                Location loc = player.getLocation();
                World world = player.getWorld();
                player.sendMessage(ChatColor.GRAY + "["+ ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >>" + ChatColor.GREEN + "You hace successfully changed this horses markings!");
                if (player.hasPermission("eq.staff")) {
                    if (args[1].equalsIgnoreCase("none")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Style:None");
                                    e.removeScoreboardTag("Style:Star");
                                    e.removeScoreboardTag("Style:Snip");
                                    e.removeScoreboardTag("Style:Paint");
                                    e.removeScoreboardTag("Style:Appy");
                                    e.removeScoreboardTag("Style:Blaze");
                                    e.removeScoreboardTag("Style:Speckled");
                                    ((Horse) e).setStyle(Horse.Style.NONE);
                                    e.addScoreboardTag("Style:None");
                                    return true;
                                }
                            }
                        }
                    }
                    if (args[1].equalsIgnoreCase("appy")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Style:None");
                                    e.removeScoreboardTag("Style:Star");
                                    e.removeScoreboardTag("Style:Snip");
                                    e.removeScoreboardTag("Style:Paint");
                                    e.removeScoreboardTag("Style:Appy");
                                    e.removeScoreboardTag("Style:Blaze");
                                    e.removeScoreboardTag("Style:Speckled");
                                    ((Horse) e).setStyle(Horse.Style.BLACK_DOTS);
                                    e.addScoreboardTag("Style:Appy");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("speckled")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Style:None");
                                    e.removeScoreboardTag("Style:Star");
                                    e.removeScoreboardTag("Style:Snip");
                                    e.removeScoreboardTag("Style:Paint");
                                    e.removeScoreboardTag("Style:Appy");
                                    e.removeScoreboardTag("Style:Blaze");
                                    e.removeScoreboardTag("Style:Speckled");
                                    ((Horse) e).setStyle(Horse.Style.WHITE_DOTS);
                                    e.addScoreboardTag("Style:Speckled");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("paint")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Style:None");
                                    e.removeScoreboardTag("Style:Star");
                                    e.removeScoreboardTag("Style:Snip");
                                    e.removeScoreboardTag("Style:Paint");
                                    e.removeScoreboardTag("Style:Appy");
                                    e.removeScoreboardTag("Style:Blaze");
                                    e.removeScoreboardTag("Style:Speckled");
                                    ((Horse) e).setStyle(Horse.Style.WHITEFIELD);
                                    e.addScoreboardTag("Style:Paint");
                                    return true;
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("blaze")) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                UUID h = e.getUniqueId();
                                if (euid.equals(h)) {
                                    e.removeScoreboardTag("Style:None");
                                    e.removeScoreboardTag("Style:Star");
                                    e.removeScoreboardTag("Style:Snip");
                                    e.removeScoreboardTag("Style:Paint");
                                    e.removeScoreboardTag("Style:Appy");
                                    e.removeScoreboardTag("Style:Blaze");
                                    e.removeScoreboardTag("Style:Speckled");
                                    ((Horse) e).setStyle(Horse.Style.WHITE);
                                    e.addScoreboardTag("Style:Blaze");
                                    return true;
                                }
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("home")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                Location loc = player.getLocation();
                World world = player.getWorld();
                if(euid.equals(null)) {
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                    return true;
                }
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            DbHorse horse = plugin.getDbContext().getHorseFromDatabase(e.getUniqueId());

                            try {
                                Location l = horse.getHome();
                                e.teleport(l);
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Horse teleported to home!");

                                if (horse != null) {
                                    Location horseLocation = e.getLocation();
                                    horse.setLastWorld(e.getWorld().getName());
                                    horse.setLastChunkX(horseLocation.getChunk().getX());
                                    horse.setLastChunkX(horseLocation.getChunk().getZ());
                                    plugin.getDbContext().updateHorseInDatabase(horse);
                                    System.out.printf("(Sent Home) Updated horse #%s%n", horse.getId());
                                }
                            } catch(IllegalArgumentException exception) {
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Could not send home! Does your horse have a home set?");
                                exception.printStackTrace();
                                return true;
                            }

                            return true;
                        }

                    }
                }
            } else if (args[0].equalsIgnoreCase("sethome")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                Location loc = player.getLocation();
                World world = player.getWorld();
                if(euid.equals(null)) {
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                    return true;
                }
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            DbHorse horse = plugin.getDbContext().getHorseFromDatabase(e.getUniqueId());

                            if(horse != null) {
                                horse.setHome(loc);
                                plugin.getDbContext().updateHorseInDatabase(horse);
                                System.out.printf("(Home Set) Updated horse #%s%n", horse.getId());
                            }
//                            plugin.getConfig().set(String.valueOf(euid + " home"), loc);
//                            plugin.saveConfig();
                            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Home is set!");
                            return true;

                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("register")) {
                if (args.length >= 2) {
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    plugin.getConfig().set(String.valueOf(uuid + " brand"), args[1]);
                    plugin.saveConfig();
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Brand is registered!");
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("brand")) {
                if (args.length >= 2) {
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    UUID euid = collection.get(uuid);
                    World world = player.getWorld();
                    String name = sender.getName();
                    if(euid.equals(null)) {
                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                        return true;
                    }
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            UUID h = e.getUniqueId();
                            String n = e.getCustomName();
                            if (euid.equals(h)) {
                                if (player.hasPermission("eq.breeding")) {
                                    if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                        if (!e.getScoreboardTags().contains("branded")) {
                                            String l = plugin.getConfig().getString(uuid + " brand");
                                            if (!l.isEmpty()) {
                                                e.addScoreboardTag("branded");
                                                e.addScoreboardTag("brand:" + args[1]);
                                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Horse Branded!");
                                                return true;
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("me")) {
                Player player = (Player) sender;
                float xp = player.getLevel();
                if (args.length == 1) {
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.GRAY + "][" + ChatColor.YELLOW + "Your Info" + ChatColor.GRAY + "][" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------");
                    player.sendMessage(" ");
                    if (player.hasPermission("group.default")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.GRAY + "Newbie" + ChatColor.GRAY + "]");
                    }
                    if (player.hasPermission("group.beginner")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.YELLOW + "Beginner" + ChatColor.GRAY + "]");
                    }
                    if (player.hasPermission("group.amature")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.GOLD + "Amature" + ChatColor.GRAY + "]");
                    }
                    if (player.hasPermission("group.intermediate")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.GREEN + "Intermediate" + ChatColor.GRAY + "]");
                    }
                    if (player.hasPermission("group.advanced")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.RED + "Advanced" + ChatColor.GRAY + "]");
                    }
                    if (player.hasPermission("group.expert")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "Expert" + ChatColor.GRAY + "]");
                    }
                    if (player.hasPermission("group.champion")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.BLUE + "Champion" + ChatColor.GRAY + "]");
                    }
                    if (player.hasPermission("group.elite")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.WHITE + "Elite" + ChatColor.GRAY + "]");
                    }
                    if (player.hasPermission("group.olympian")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Olympian" + ChatColor.GRAY + "]");
                    }
                    player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "XP:      " + xp);
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.YELLOW + "[Licenses]");
                    if (player.hasPermission("eq.store")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Store");
                    }
                    if (player.hasPermission("eq.farming")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Farming");
                    }
                    if (player.hasPermission("eq.hosting")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Hosting");
                    }
                    if (player.hasPermission("eq.boarding")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Boarding");
                    }
                    if (player.hasPermission("eq.realtor")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Realtor");
                    }
                    if (player.hasPermission("eq.breeding")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Breeding");
                    }

                }
                if (args.length >= 2) {
                    Player player2 = plugin.getServer().getPlayer(args[1]);
                    float xp2 = player2.getLevel();
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.GRAY + "][" + ChatColor.YELLOW + args[1] + "'s Info" + ChatColor.GRAY + "][" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------");
                    player.sendMessage(" ");
                    if (player2.hasPermission("group.default")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.GRAY + "Newbie" + ChatColor.GRAY + "]");
                    }
                    if (player2.hasPermission("group.beginner")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.YELLOW + "Beginner" + ChatColor.GRAY + "]");
                    }
                    if (player2.hasPermission("group.amature")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.GOLD + "Amature" + ChatColor.GRAY + "]");
                    }
                    if (player2.hasPermission("group.intermediate")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.GREEN + "Intermediate" + ChatColor.GRAY + "]");
                    }
                    if (player2.hasPermission("group.advanced")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.RED + "Advanced" + ChatColor.GRAY + "]");
                    }
                    if (player2.hasPermission("group.expert")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "Expert" + ChatColor.GRAY + "]");
                    }
                    if (player2.hasPermission("group.champion")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.BLUE + "Champion" + ChatColor.GRAY + "]");
                    }
                    if (player2.hasPermission("group.elite")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.WHITE + "Elite" + ChatColor.GRAY + "]");
                    }
                    if (player2.hasPermission("group.olympian")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Rank:      " + ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Olympian" + ChatColor.GRAY + "]");
                    }
                    player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "XP:      " + xp2);
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.YELLOW + "[Licenses]");
                    if (player2.hasPermission("eq.store")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Store");
                    }
                    if (player2.hasPermission("eq.farming")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Farming");
                    }
                    if (player2.hasPermission("eq.hosting")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Hosting");
                    }
                    if (player2.hasPermission("eq.boarding")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Boarding");
                    }
                    if (player2.hasPermission("eq.realtor")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Realtor");
                    }
                    if (player2.hasPermission("eq.breeding")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Breeding");
                    } else {
                        return true;
                    } return true;
                }
            } else if (args[0].equalsIgnoreCase("killall")) {
                Player player = (Player) sender;
                World world = player.getWorld();
                if (player.hasPermission("eq.dev")) {
                    for (Entity e : world.getEntities()) {
                        if (e.getType() == EntityType.HORSE)
                            ((LivingEntity) e).setHealth(0);
                    }
                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Horses killed");
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("doublexp")) {
                if (args.length == 2) {
                    if (sender.hasPermission("eq.op")) {
                        if (args[1].equalsIgnoreCase("true")) {
                            doublexp.put("dxp", "true");
                            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                            Bukkit.dispatchCommand(console, "lp group beginner permission set superbvote.doublexp");
                            Bukkit.dispatchCommand(console, "lp group sbeginner permission set superbvote.doublexp");
                            sender.sendMessage(ChatColor.AQUA + " Double xp enabled!");


                        } else if (args[1].equalsIgnoreCase("false")) {
                            doublexp.put("dxp", "false");
                            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                            Bukkit.dispatchCommand(console, "lp group beginner permission unset superbvote.doublexp");
                            Bukkit.dispatchCommand(console, "lp group sbeginner permission unset superbvote.doublexp");

                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("unlease")) {
                if (args.length == 2) {
                    OfflinePlayer p2 = plugin.getServer().getOfflinePlayer(args[1]);
                    UUID uuid2 = p2.getUniqueId();
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    UUID euid = collection.get(uuid);
                    World world = player.getWorld();
                    String name = sender.getName();
                    String name2 = p2.getName();
                    if(collection.isEmpty()) {
                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                        return true;
                    }
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            UUID h = e.getUniqueId();
                            String n = e.getCustomName();
                            if (euid.equals(h)) {
                                if (player.hasPermission("eq.staff")) {
                                    e.removeScoreboardTag("Leaser:" + uuid2);
                                    int num = 1;
                                    while (num <= 30) {
                                        if (e.getScoreboardTags().contains("Leased" + num)) {
                                            e.removeScoreboardTag("Leased" + num);
                                        } else {
                                            ++num;
                                        }
                                    }
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + name2 + " has been removed you from leasing your horse!");
                                    return true;
                                }
                                if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                    e.removeScoreboardTag("Leaser:" + uuid2);
                                    int num = 1;
                                    while (num <= 30) {
                                        if (e.getScoreboardTags().contains("Leased" + num)) {
                                            e.removeScoreboardTag("Leased" + num);
                                        } else {
                                            ++num;
                                        }
                                    }
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + name2 + " has been removed you from leasing your horse!");
                                    return true;
                                }
                            }

                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("lease")) {
                if (args.length == 3) {
                    Player p2 = plugin.getServer().getPlayer(args[1]);
                    UUID uuid2 = p2.getUniqueId();
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    UUID euid = collection.get(uuid);
                    World world = player.getWorld();
                    String name = sender.getName();
                    int cost = Integer.parseInt(args[2]);
                    if(collection.isEmpty()) {
                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                        return true;
                    }
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            UUID h = e.getUniqueId();
                            String n = e.getCustomName();
                            if (euid.equals(h)) {
                                if (e.getScoreboardTags().contains("Owner:30807522-e3c9-471f-a366-c7b152650dfc")) {
                                    if(player.hasPermission("eq.staff")) {
                                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + name + " sent lease request for " + "$" + cost + "!");
                                        p2.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + name + " would like to lease you" + n + " for " + "$" + cost + "!");
                                        TextComponent msg = new TextComponent(ChatColor.GRAY + "[" + ChatColor.GREEN + "Accept" + ChatColor.GRAY + "]");
                                        TextComponent msg2 = new TextComponent(ChatColor.GRAY + "[" + ChatColor.RED + "Deny" + ChatColor.GRAY + "]");
                                        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq accept2 " + name + " " + cost));
                                        msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq deny"));
                                        p2.spigot().sendMessage(msg, msg2);
                                        return true;
                                    }

                                }
                                else if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + name + " sent lease request for " + "$" + cost + "!");
                                    p2.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + name + " would like to lease you" + n + " for " + "$" + cost + "!");
                                    TextComponent msg = new TextComponent(ChatColor.GRAY + "[" + ChatColor.GREEN + "Accept" + ChatColor.GRAY + "]");
                                    TextComponent msg2 = new TextComponent(ChatColor.GRAY + "[" + ChatColor.RED + "Deny" + ChatColor.GRAY + "]");
                                    msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq accept2 " + name + " " + cost));
                                    msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq deny"));
                                    p2.spigot().sendMessage(msg, msg2);
                                    return true;
                                }
                            }

                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("heat")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                Location loc = player.getLocation();
                World world = player.getWorld();
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            e.addScoreboardTag("InHeat");
                            player.sendMessage("Heat Given");
                            return true;
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                if (args.length >= 2) {
                    Player player = (Player) sender;
                    OfflinePlayer p2 = plugin.getServer().getOfflinePlayerIfCached(args[1]);
                    UUID targetPlayerUuid = p2.getUniqueId();

                    player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.GRAY + "][" + ChatColor.YELLOW + args[1] + "'s Horse List" + ChatColor.GRAY + "][" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------");
                    for(DbHorse horse : plugin.getDbContext().getHorsesOwnedByPlayer(targetPlayerUuid.toString())) {
                        StringBuilder name = new StringBuilder();
                        String hn = horse.getName();
                        if(hn == null) {
                            hn = "No Name";
                        }
                        TextComponent msg = new TextComponent(ChatColor.AQUA + " ● " + ChatColor.WHITE + hn);
                        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq select " + horse.getId()));
                        player.spigot().sendMessage(msg);
                    }
//                    for(World world : plugin.getServer().getWorlds()) {
//                        for(Entity e : world.getEntities()) {
//                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
//                                if (e.getScoreboardTags().contains("Owner:" + targetPlayerUuid)) {
//                                    StringBuilder name = new StringBuilder();
//                                    String hn = e.getCustomName();
//                                    TextComponent msg = new TextComponent(ChatColor.AQUA + " ● " + ChatColor.WHITE + hn);
//                                    msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq select " + hn + " " + args[1]));
//                                    player.spigot().sendMessage(msg);
//                                }
//                            }
//                        }
//                    }

                    return true;

                } else {
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    Location loc = player.getLocation();

                    player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.GRAY + "][" + ChatColor.YELLOW + "Horse List" + ChatColor.GRAY + "][" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------");
                    for(DbHorse horse : plugin.getDbContext().getHorsesOwnedByPlayer(uuid.toString())) {
                        StringBuilder name = new StringBuilder();
                        String hn = horse.getName();
                        if(hn == null) {
                            hn = "No Name";
                        }
                        TextComponent msg = new TextComponent(ChatColor.AQUA + " ● " + ChatColor.WHITE + hn);
                        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq select " + horse.getId()));
                        player.spigot().sendMessage(msg);
                    }
//                    for (World world : plugin.getServer().getWorlds()) {
//                        for (Entity e : world.getEntities()) {
//                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
//                                if (e.getScoreboardTags().contains("Owner:" + uuid)) {
//                                    String hn = e.getCustomName();
//                                    TextComponent msg = new TextComponent(ChatColor.AQUA + " ● " + ChatColor.WHITE + hn);
//                                    msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq select " + hn));
//                                    player.spigot().sendMessage(msg);
//                                }
//                            }
//                        }
//                    }
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("select")) {
                if (args.length == 2) {
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    Location loc = player.getLocation();

                    try {
                        DbHorse horse = plugin.getDbContext().getHorseFromDatabaseById(Integer.parseInt(args[1]));
                        World lastWorld = plugin.getServer().getWorld(horse.getLastWorld());

                        Chunk targetChunk = lastWorld.getChunkAt(horse.getLastChunkX(), horse.getLastChunkZ());
                        targetChunk.load();
                        targetChunk.setForceLoaded(true);

                        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                            @Override
                            public void run() {
                                for(Entity e : lastWorld.getEntities()) {
                                    if(e.getUniqueId().toString().equalsIgnoreCase(horse.getUuid())) {
                                        if (e.getScoreboardTags().contains("Owner:" + uuid.toString())) {
                                            if (horse.getOwnerUuid().equalsIgnoreCase("EMPTY")) {
                                                System.out.println("This horse does not have an owner set... Correcting!");
                                                horse.setOwnerUuid(uuid.toString());
                                                plugin.getDbContext().updateHorseInDatabase(horse);
                                            }
                                        }
                                        ((LivingEntity) e).damage(1, player);
                                    }
                                }
                            }
                        }, 25);

                        return true;


                    } catch(NoSuchElementException e) {
                        // Send message to player saying the horse with the specified id was not found
                        e.printStackTrace();
                    }

                    return true;

                }
                if (args.length == 3) {
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    OfflinePlayer p2 = plugin.getServer().getOfflinePlayerIfCached(args[2]);
                    UUID uuid2 = p2.getUniqueId();
                    Location loc = player.getLocation();
                    World world = Bukkit.getWorld("EquinoxPlots");
                    World world2 = Bukkit.getWorld("Flat");
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid2)) {
                                if (e.getCustomName().equals(args[1])){
                                    ((LivingEntity) e).damage(1, player);
                                    return true;
                                }
                            }
                        }
                    }
                    for (Entity e : world2.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid2)) {
                                if (e.getCustomName().equals(args[1])){
                                    ((LivingEntity) e).damage(1, player);
                                    return true;
                                }
                            }
                        }
                    }
                }
                if (args.length == 4) {
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    OfflinePlayer p2 = plugin.getServer().getOfflinePlayerIfCached(args[3]);
                    UUID uuid2 = p2.getUniqueId();
                    Location loc = player.getLocation();
                    World world = Bukkit.getWorld("EquinoxPlots");
                    World world2 = Bukkit.getWorld("Flat");
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid2)) {
                                if (e.getCustomName().equals(args[1] + " " + args[2])) {
                                    ((LivingEntity) e).damage(1, player);
                                    return true;
                                }
                            }
                        }
                    }
                    for (Entity e : world2.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid2)) {
                                if (e.getCustomName().equals(args[1] + " " + args[2])) {
                                    ((LivingEntity) e).damage(1, player);
                                    return true;
                                }
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("deny")) {
                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Transfer request denied!");
                return true;
            } else if (args[0].equalsIgnoreCase("accept2")) {
                if (args.length == 3) {
                    Player p2 = plugin.getServer().getPlayer(args[1]);
                    UUID uuid2 = p2.getUniqueId();
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    UUID euid = collection.get(uuid2);
                    World world = player.getWorld();
                    Economy eco = Equinox.getEconomy();
                    String name = sender.getName();
                    int cost = Integer.parseInt(args[2]);
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            UUID h = e.getUniqueId();
                            String n = e.getCustomName();
                            if (euid.equals(h) && sender instanceof Player) {
                                if (e.getScoreboardTags().contains("Owner:30807522-e3c9-471f-a366-c7b152650dfc")) {
                                    double bal = eco.getBalance(player);
                                    System.out.println(bal);
                                    if (bal < cost) {
                                        p2.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Player does not have enough money to lease this horse!");
                                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have enough money to lease this horse!");
                                        return true;
                                    }
                                    eco.withdrawPlayer(player, cost);
                                    p2.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + name + " has accepted your request!");
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You are now the lease owner of " + n + "!");
                                    e.addScoreboardTag("Leased1");
                                    e.addScoreboardTag("Leaser:" + uuid);
                                    return true;
                                }
                                if (e.getScoreboardTags().contains("Owner:" + uuid2)) {
                                    double bal = eco.getBalance(player);
                                    System.out.println(bal);
                                    if(bal < cost) {
                                        p2.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Player does not have enough money to lease this horse!");
                                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have enough money to lease this horse!");
                                        return true;
                                    }
                                    eco.withdrawPlayer(player, cost);
                                    eco.depositPlayer(p2, cost);
                                    p2.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + name + " has accepted your request!");
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You are now the lease owner of " + n + "!");
                                    e.addScoreboardTag("Leased1");
                                    e.addScoreboardTag("Leaser:" + uuid);
                                    return true;
                                }
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("accept")) {
                if (args.length == 2) {
                    Player p2 = plugin.getServer().getPlayer(args[1]);
                    UUID uuid2 = p2.getUniqueId();
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    UUID euid = collection.get(uuid2);
                    World world = player.getWorld();
                    String name = sender.getName();
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            UUID h = e.getUniqueId();
                            String n = e.getCustomName();
                            if (euid.equals(h)) {
                                if (e.getScoreboardTags().contains("Owner:" + uuid2)) {
                                    e.addScoreboardTag("Owner:" + uuid);
                                    e.removeScoreboardTag("Owner:" + uuid2);
                                    plugin.getDbContext().updateHorseOwnerUUID(String.valueOf(uuid), String.valueOf(h));
                                    p2.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + name + " has accepted your request!");
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You are now the owner of " + n + "!");
                                    return true;
                                }
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("transfer")) {
                if (args.length == 2) {
                    Player p2 = plugin.getServer().getPlayer(args[1]);
                    Player player = (Player) sender;
                    String name = sender.getName();
                    UUID uuid = player.getUniqueId();
                    UUID uuid2 = p2.getUniqueId();
                    UUID euid = collection.get(uuid);
                    World world = player.getWorld();
                    if(collection.isEmpty()) {
                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                        return true;
                    }
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            UUID h = e.getUniqueId();
                            String n = e.getCustomName();
                            if (euid.equals(h)) {
                                if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                    p2.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + name + " would like to transfer ownership of " + n + " to you!");
                                    TextComponent msg = new TextComponent(ChatColor.GRAY + "[" + ChatColor.GREEN + "Accept" + ChatColor.GRAY + "]");
                                    TextComponent msg2 = new TextComponent(ChatColor.GRAY + "[" + ChatColor.RED + "Deny" + ChatColor.GRAY + "]");
                                    msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq accept " + name));
                                    msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq deny"));
                                    p2.spigot().sendMessage(msg, msg2);
                                    return true;
                                }
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("trust")) {
                if (args.length == 2) {
                    Player p2 = plugin.getServer().getPlayer(args[1]);
                    String name = p2.getName();
                    UUID uuid2 = p2.getUniqueId();
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    UUID euid = collection.get(uuid);
                    World world = player.getWorld();
                    if(euid.equals(null)) {
                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                        return true;
                    }
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            UUID h = e.getUniqueId();
                            if (euid.equals(h)) {
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have trusted " + name + " to your horse!");
                                e.addScoreboardTag("Member:" + uuid2);
                                return true;
                            }
                        }
                    }

                }
            } else if (args[0].equalsIgnoreCase("setjump")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                Location loc = player.getLocation();
                for(World world : plugin.getServer().getWorlds()) {
                    if(collection.isEmpty()) {
                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                        return true;
                    }
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            if (player.hasPermission("eq.staff")) {
                                if (args[1].equalsIgnoreCase("1ft")) {
                                    UUID h = e.getUniqueId();
                                    if (euid.equals(h)) {
                                        ((Horse) e).setJumpStrength(.517);
                                        e.removeScoreboardTag("1ft");
                                        e.removeScoreboardTag("2ft");
                                        e.removeScoreboardTag("3ft");
                                        e.removeScoreboardTag("4ft");
                                        e.removeScoreboardTag("5ft");
                                        e.removeScoreboardTag("6ft");
                                        e.addScoreboardTag("1ft");
                                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Horse's jump has been set!");
                                        return true;

                                    }
                                } if (args[1].equalsIgnoreCase("2ft")) {
                                    UUID h = e.getUniqueId();
                                    if (euid.equals(h)) {
                                        ((Horse) e).setJumpStrength(.617);
                                        e.removeScoreboardTag("1ft");
                                        e.removeScoreboardTag("2ft");
                                        e.removeScoreboardTag("3ft");
                                        e.removeScoreboardTag("4ft");
                                        e.removeScoreboardTag("5ft");
                                        e.removeScoreboardTag("6ft");
                                        e.addScoreboardTag("2ft");
                                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Horse's jump has been set!");
                                        return true;

                                    }
                                } if (args[1].equalsIgnoreCase("3ft")) {
                                    UUID h = e.getUniqueId();
                                    if (euid.equals(h)) {
                                        ((Horse) e).setJumpStrength(.717);
                                        e.removeScoreboardTag("1ft");
                                        e.removeScoreboardTag("2ft");
                                        e.removeScoreboardTag("3ft");
                                        e.removeScoreboardTag("4ft");
                                        e.removeScoreboardTag("5ft");
                                        e.removeScoreboardTag("6ft");
                                        e.addScoreboardTag("3ft");
                                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Horse's jump has been set!");
                                        return true;

                                    }
                                }if (args[1].equalsIgnoreCase("4ft")) {
                                    UUID h = e.getUniqueId();
                                    if (euid.equals(h)) {
                                        ((Horse) e).setJumpStrength(.817);
                                        e.removeScoreboardTag("1ft");
                                        e.removeScoreboardTag("2ft");
                                        e.removeScoreboardTag("3ft");
                                        e.removeScoreboardTag("4ft");
                                        e.removeScoreboardTag("5ft");
                                        e.removeScoreboardTag("6ft");
                                        e.addScoreboardTag("4ft");
                                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Horse's jump has been set!");
                                        return true;

                                    }
                                }if (args[1].equalsIgnoreCase("5ft")) {
                                    UUID h = e.getUniqueId();
                                    if (euid.equals(h)) {
                                        ((Horse) e).setJumpStrength(.917);
                                        e.removeScoreboardTag("1ft");
                                        e.removeScoreboardTag("2ft");
                                        e.removeScoreboardTag("3ft");
                                        e.removeScoreboardTag("4ft");
                                        e.removeScoreboardTag("5ft");
                                        e.removeScoreboardTag("6ft");
                                        e.addScoreboardTag("5ft");
                                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Horse's jump has been set!");
                                        return true;

                                    }
                                }if (args[1].equalsIgnoreCase("6ft")) {
                                    UUID h = e.getUniqueId();
                                    if (euid.equals(h)) {
                                        ((Horse) e).setJumpStrength(1.117);
                                        e.removeScoreboardTag("1ft");
                                        e.removeScoreboardTag("2ft");
                                        e.removeScoreboardTag("3ft");
                                        e.removeScoreboardTag("4ft");
                                        e.removeScoreboardTag("5ft");
                                        e.removeScoreboardTag("6ft");
                                        e.addScoreboardTag("6ft");
                                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Horse's jump has been set!");
                                        return true;

                                    }
                                }
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("tphere")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                Location loc = player.getLocation();
                for(World world : plugin.getServer().getWorlds()) {
                    if(collection.isEmpty()) {
                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                        return true;
                    }
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            UUID h = e.getUniqueId();
                            if (euid.equals(h)) {
                                e.teleport(loc);
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Horse has been teleported to you!");
                                return true;

                            }
                        }
                    }
                }

            } else if (args[0].equalsIgnoreCase("tp")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                for(World world : plugin.getServer().getWorlds()) {
                    if(collection.isEmpty()) {
                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                        return true;
                    }
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            UUID h = e.getUniqueId();
                            if (euid.equals(h)) {
                                Location loc = e.getLocation();
                                player.teleport(loc);
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have been teleported to your horse!");
                                return true;
                            }
                        }
                    }
                }

            } else if (args[0].equalsIgnoreCase("kill")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                World world = player.getWorld();
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            if (player.hasPermission("eq.op")) {
                                ((LivingEntity) e).setHealth(0);
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have killed this horse!");
                                return true;
                            } else {
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have permission to kill horses!");
                            }
                        }
                    } if (e instanceof Donkey) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            if (player.hasPermission("eq.op")) {
                                ((Donkey) e).setHealth(0);
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have killed this horse!");
                                return true;
                            } else {
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have permission to kill horses!");
                            }
                        }
                    } if (e instanceof Mule) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            if (player.hasPermission("eq.op")) {
                                ((Mule) e).setHealth(0);
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have killed this horse!");
                                return true;
                            } else {
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have permission to kill horses!");
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("name")) {
                if (args.length >= 2) {
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    UUID euid = collection.get(uuid);
                    World world = player.getWorld();
                    if(collection.isEmpty()) {
                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                        return true;
                    }
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            UUID h = e.getUniqueId();
                            if (euid.equals(h)) {
                                DbHorse horse = plugin.getDbContext().getHorseFromDatabase(euid);
                                StringBuilder name = new StringBuilder();
                                // Test Name
                                for(int i = 0; i < args.length; i++) {
                                    String word = args[i];
                                    if(word.equalsIgnoreCase("name")) {
                                        continue;
                                    }
                                    word = word.replace('&', '§');
                                    word = word.trim();
                                    if(i == args.length - 1) {
                                        name.append(word);
                                    } else {
                                        name.append(word + " ");
                                    }
                                }
                                if (player.hasPermission("eq.dev")) {
                                    e.setCustomName(name.toString());
                                    if(horse != null) {
                                        horse.setName(name.toString());
                                        plugin.getDbContext().updateHorseInDatabase(horse);
                                    } else {
                                        System.out.println("Skipping update, as horse is null");
                                    }
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have named this horse!");
                                    return true;
                                }

                                if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                    e.setCustomName(name.toString());
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have named this horse!");
                                    if(horse != null) {
                                        horse.setName(name.toString());
                                        plugin.getDbContext().updateHorseInDatabase(horse);
                                    } else {
                                        System.out.println("Skipping update, as horse is null");
                                    }
                                    return true;
                                } else if (e.getScoreboardTags().contains("Member:" + uuid)) {
                                    e.setCustomName(name.toString());
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have named this horse!");
                                    if(horse != null) {
                                        horse.setName(name.toString());
                                        plugin.getDbContext().updateHorseInDatabase(horse);
                                    } else {
                                        System.out.println("Skipping update, as horse is null");
                                    }
                                    return true;
                                } else if (!e.getScoreboardTags().contains("Owner:" + uuid)) {
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "This is not your horse!");
                                    return true;
                                }


                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("claim")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                World world = player.getWorld();
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            if (!e.getScoreboardTags().contains("Owned")) {
                                if (!e.getScoreboardTags().contains("Owner:" + uuid)) {
                                    e.addScoreboardTag("Owner:" + uuid);
                                    e.addScoreboardTag("Owned");
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "You have claimed this horse!");
                                    return true;
                                }
                            } else if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You already own this horse!");
                                return true;
                            } else if (e.getScoreboardTags().contains("Owned")) {
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "This is not your horse!");
                                return true;
                            }
                        }
                    }
                }

            } else if (args[0].equalsIgnoreCase("public")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                World world = player.getWorld();
                if(collection.isEmpty()) {
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                    return true;
                }
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                e.removeScoreboardTag("Private");
                                e.addScoreboardTag("Public");
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "You have made this horse public!");
                                return true;
                            } if (player.hasPermission("eq.dev")) {
                                e.removeScoreboardTag("Private");
                                e.addScoreboardTag("Public");
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "You have made this horse private!");
                                return true;
                            } else if (!e.getScoreboardTags().contains("Owner:" + uuid)) {
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "This is not your horse!");
                                return true;
                            }

                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("private")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                World world = player.getWorld();
                if(collection.isEmpty()) {
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                    return true;
                }
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                e.removeScoreboardTag("Public");
                                e.addScoreboardTag("Private");
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "You have made this horse private!");
                                return true;
                            }if (player.hasPermission("eq.dev")) {
                                e.removeScoreboardTag("Public");
                                e.addScoreboardTag("Private");
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "You have made this horse private!");
                                return true;
                            } else if (!e.getScoreboardTags().contains("Owner:" + uuid)) {
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "This is not your horse!");
                                return true;
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("notif")) {
                Player player = (Player) sender;
                player.sendMessage(" ");
                player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "------------------------------------------");
                player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.GRAY + "][" + ChatColor.YELLOW + "Horse Notifications" + ChatColor.GRAY + "][" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------");
                player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "------------------------------------------");
                player.sendMessage(" ");
                if (player.hasPermission("eq.starved")) {
                    player.sendMessage(ChatColor.RED + "  ●" + ChatColor.YELLOW + " Your horse has starved to death.");
                    player.sendMessage(" ");
                }
            } else if (args[0].equalsIgnoreCase("menu")) {
                Player player = (Player) sender;
                if (player.hasPermission("eq.staff")) {
                    Inventory menu = Bukkit.getServer().createInventory(null, 9, "§0Menu");

                    ItemStack ref1 = new ItemStack(Material.HORSE_SPAWN_EGG);
                    ItemStack ref2 = new ItemStack(Material.SADDLE);

                    ItemMeta metaref1 = ref1.getItemMeta();
                    ItemMeta metaref2 = ref2.getItemMeta();

                    ref1.setItemMeta(metaref1);
                    ref2.setItemMeta(metaref2);
                    metaref1.setLore(Collections.singletonList("§9§oCreate a custom horse"));
                    metaref2.setLore(Collections.singletonList("§9§oPurchase player a player horse"));
                    metaref1.setDisplayName("§bHorse Creation");
                    metaref2.setDisplayName("§bHorse Market");
                    ref1.setItemMeta(metaref1);
                    ref2.setItemMeta(metaref2);

                    menu.setItem(3, ref1);
                    menu.setItem(5, ref2);
                    player.openInventory(menu);
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("lunge")) {
                Player p = (Player) sender;
                int cooldownTime = 25; // The number of seconds the player has to wait
                if(cooldowns.containsKey(p.getName())) {
                    long secondsLeft = ((cooldowns.get(p.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
                    if(secondsLeft>0) {
                        sender.sendMessage("You cant do this for another "+ secondsLeft +" seconds!");
                        return true;
                    }
                }
                cooldowns.put(p.getName(), System.currentTimeMillis());
                if(false) {
                    p.sendMessage("&7[&bEQ&7] >> &cThis command is currently disabled!");
                }else {
                    UUID uuid = p.getUniqueId();
                    UUID euid = collection.get(uuid);
                    World world = p.getWorld();
                    if(collection.isEmpty()) {
                        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                        return true;
                    }
                    if (p.isInsideVehicle()) {
                        p.getVehicle().eject();
                    }

                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            UUID h = e.getUniqueId();
                            if (euid.equals(h)) {
                                if (((LivingEntity) e).getLeashHolder() instanceof Player) {
                                    Player leash = (Player) ((LivingEntity) e).getLeashHolder();
                                    lungestat.put(uuid, true);
                                    if (p != leash){
                                        return true;
                                    }
                                    if (e.getScoreboardTags().contains("Trait:Stubborn")) {
                                        Random rnd = new Random();
                                        int i = rnd.nextInt(100);
                                        if (i <= 10) {
                                            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Your stubborn horse has ignored you!");
                                            return true;
                                        }
                                    }
                                    plugin.lungeStatus.put(e.getUniqueId(), true);
                                    new BukkitRunnable() {
                                        int tick = 0;
                                        int timer = 0;

                                        public void run() {
                                            final float radius = 4.0f;
                                            final float radPerSec = (float) (2 * Math.PI / 4);
                                            final float radPerTick = radPerSec * tick / 20f;
                                            final Location center = p.getLocation();
                                            NBTEditor.set(e, (byte) 0, "EatingHaystack");
                                            DbHorse horse = plugin.getDbContext().getHorseFromDatabase(e.getUniqueId());
                                            horse.setRecentlyLunged(true);
                                            plugin.getDbContext().updateHorseInDatabase(horse);
                                            ++tick;

                                            if(!plugin.lungeStatus.get(e.getUniqueId())) {
                                                this.cancel();
                                                plugin.lungeStatus.remove(e.getUniqueId());
                                                return;
                                            }
                                            if (lungestat.get(uuid) == false) {
                                                return;
                                            }


                                            Location loc = getLocAroundCircle(center, radius, radPerTick);

                                            e.teleport(loc);
                                            ++timer;

                                            if (timer == 100 || timer == 200 || timer == 300) {
                                                horse.setXp(horse.getXp() + 1);
                                                if (doublexp.get("dxp") == "true") {
                                                    p.giveExp(6);
                                                    p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+6 XP"));
                                                }else{
                                                    p.giveExp(3);
                                                    p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
                                                }
                                                int requiredForNext = Utilities.getXpNeededForLevel(horse.getLevel() + 1);
                                                if(requiredForNext <= horse.getXp()) {
                                                    horse.setLevel(horse.getLevel() + 1);
                                                    String message = String.format("%s, [%sEQ%s] >> %sYour horse is now level %s!", ChatColor.GRAY, ChatColor.AQUA, ChatColor.GRAY, ChatColor.GREEN, horse.getLevel());
                                                    p.sendMessage(message);
                                                }

                                                plugin.getDbContext().updateHorseInDatabase(horse);
                                            }

                                            if (timer > 500) {
                                                this.cancel();
                                            }
                                        }

                                    }.runTaskTimer(plugin, 0L, 1L);
                                }
                            }
                        }
                    }
                }
                return true;
            } else if (args[0].equalsIgnoreCase("info2")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                World world = player.getWorld();
                if(collection.isEmpty()) {
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                    return true;
                }
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            String hn = e.getName();
                            DbHorse horse = plugin.getDbContext().getHorseFromDatabase(e.getUniqueId());
                            TextComponent msg = new TextComponent(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------" + "[" + ChatColor.WHITE + "<<" + ChatColor.GRAY + "][" + ChatColor.YELLOW + "2" + ChatColor.GRAY + "/" + ChatColor.YELLOW + "2" + ChatColor.GRAY + "]" + "" + ChatColor.STRIKETHROUGH + "----------------------");
                            msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq info"));
                            player.spigot().sendMessage(msg);
                            player.sendMessage(ChatColor.GRAY +  "          " + "[" + ChatColor.YELLOW + hn + ChatColor.YELLOW + "'s Info" + ChatColor.GRAY + "]" + "          ");
                            player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------------------------------------------------------");
                            player.sendMessage(" ");
                            if (e.getScoreboardTags().contains("Vaxed")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.WHITE + "Vaccines" + ChatColor.GRAY + "]");
                                player.sendMessage(" ");
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Rabies");
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Tetanus");
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " West Nile Virus");
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Strangles");
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Flu");
                            }
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.WHITE + "Illnesses & Injuries" + ChatColor.GRAY + "]");
                            if (e.getScoreboardTags().contains("Flu")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Illness:" + ChatColor.WHITE + " Flu");
                            }
                            if (horse.getColic() != null) {
                                if(horse.getColic().isDiagnosed()) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Illness:" + ChatColor.WHITE + " Colic");
                                } else {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Illness:" + ChatColor.WHITE + " Unknown Illness");
                                }
                            }
                            if (e.getScoreboardTags().contains("Strangles")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Illness:" + ChatColor.WHITE + " Strangles");
                            }
                            if (e.getScoreboardTags().contains("West Nile Virus")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Illness:" + ChatColor.WHITE + " West Nile Virus");
                            }
                            if (e.getScoreboardTags().contains("Tetanus")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Illness:" + ChatColor.WHITE + " Tetanus");
                            }
                            if (e.getScoreboardTags().contains("Rabies")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Illness:" + ChatColor.WHITE + " Rabies");
                            }
                            if (e.getScoreboardTags().contains("Sprain")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Injury:" + ChatColor.WHITE + " Sprain");
                            }
                            if (e.getScoreboardTags().contains("Broken Bone")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Injury:" + ChatColor.WHITE + " Broken Bone");
                            }
                            if (e.getScoreboardTags().contains("Scratch")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Injury:" + ChatColor.WHITE + " Scratch");
                            }
                            if (e.getScoreboardTags().contains("Bite")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Injury:" + ChatColor.WHITE + " Bite");
                            }
                            if (e.getScoreboardTags().contains("Pulled Muscle")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Injury:" + ChatColor.WHITE + " Pulled Muscle");
                            }
                            if (e.getScoreboardTags().contains("uill1")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.RED + " Illness:" + ChatColor.WHITE + " Unknown");
                            }
                            if (e.getScoreboardTags().contains("uill2")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.RED + " Illness:" + ChatColor.WHITE + " Unknown");
                            }
                            if (e.getScoreboardTags().contains("uill3")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.RED + " Illness:" + ChatColor.WHITE + " Unknown");
                            }
                            if (e.getScoreboardTags().contains("uill4")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.RED + " Illness:" + ChatColor.WHITE + " Unknown");
                            }
                            if (e.getScoreboardTags().contains("uill5")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.RED + " Illness:" + ChatColor.WHITE + " Unknown");
                            }
                            if (e.getScoreboardTags().contains("uill6")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.RED + " Illness:" + ChatColor.WHITE + " Unknown");
                            }
                            if (e.getScoreboardTags().contains("uij1")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.RED + " Injury:" + ChatColor.WHITE + " Unknown");
                            }
                            if (e.getScoreboardTags().contains("uij2")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.RED + " Injury:" + ChatColor.WHITE + " Unknown");
                            }
                            if (e.getScoreboardTags().contains("uij3")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.RED + " Injury:" + ChatColor.WHITE + " Unknown");
                            }
                            if (e.getScoreboardTags().contains("uij4")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.RED + " Injury:" + ChatColor.WHITE + " Unknown");
                            }
                            if (e.getScoreboardTags().contains("uij5")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.RED + " Injury:" + ChatColor.WHITE + " Unknown");
                            }
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.WHITE + "Tags" + ChatColor.GRAY + "]");
                            if (e.getScoreboardTags().contains("Original")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Original");
                            }
                            if (e.getScoreboardTags().contains("Invulnerable")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Invulnerable");
                            }
                            if (e.getScoreboardTags().contains("Rescue")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Rescue");
                            }
                            return true;

                        } else if(collection.isEmpty()) {
                            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                        }
                    }
                }

            } else if (args[0].equalsIgnoreCase("info")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                if(collection.isEmpty()) {
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                    return true;
                }
                for(World world : plugin.getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            UUID h = e.getUniqueId();
                            if (euid.equals(h)) {
                                String hn = e.getName();
                                DbHorse horse = plugin.getDbContext().getHorseFromDatabase(e.getUniqueId()); //TODO: Gracefully fail if horse is not in DB
                                TextComponent msg = new TextComponent(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------" + "[" + ChatColor.YELLOW + "1" + ChatColor.GRAY + "/" + ChatColor.YELLOW + "2" + ChatColor.GRAY + "][" + ChatColor.WHITE + ">>" + ChatColor.GRAY + "]" + "" + ChatColor.STRIKETHROUGH + "----------------------");
                                msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq info2"));
                                player.spigot().sendMessage(msg);
                                player.sendMessage(ChatColor.GRAY + "          " + "[" + ChatColor.YELLOW + hn + ChatColor.YELLOW + "'s Info" + ChatColor.GRAY + "]" + "          ");
                                player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------------------------------------------------------");
                                player.sendMessage(" ");
                                for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                                    UUID puuid = p.getUniqueId();
                                    String offp = p.getName();
                                    String l = plugin.getConfig().getString(puuid + " brand");
                                    if (e.getScoreboardTags().contains("Owner:" + puuid)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Owner:  " + ChatColor.WHITE + offp);
                                    }
                                    if (e.getScoreboardTags().contains("Leaser:" + puuid)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Leaser:  " + ChatColor.WHITE + offp);
                                    }
                                    if (e.getScoreboardTags().contains("brand:" + l)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Brand:  " + ChatColor.WHITE + l);
                                    }
                                }
                                if (e.getScoreboardTags().contains("Private")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Status:  " + ChatColor.RED + "Private");
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " ID:  " + ChatColor.WHITE + "#" + horse.getId());
                                    player.sendMessage(" ");
                                }
                                if (e.getScoreboardTags().contains("Public")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Status:  " + ChatColor.GREEN + "Public");
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " ID:  " + ChatColor.WHITE + "#" + horse.getId());
                                    player.sendMessage(" ");
                                }
//                            player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level0:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/20");
                                player.sendMessage(String.format("%s  ● %s Level: %s %s XP: %s",
                                        ChatColor.WHITE, ChatColor.AQUA, horse.getLevel(), ChatColor.YELLOW, horse.getXp()));
                                if (e.getScoreboardTags().contains("Gender:Mare")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Gender:  " + ChatColor.RED + "Mare");
                                } else if (e.getScoreboardTags().contains("Gender:Stallion")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Gender:  " + ChatColor.AQUA + "Stallion");
                                } else if (e.getScoreboardTags().contains("Gender:Gelding")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Gender:  " + ChatColor.YELLOW + "Gelding");
                                }
                                if (e.getScoreboardTags().contains("Gender:Filly")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Gender:  " + ChatColor.RED + "Filly");
                                } else if (e.getScoreboardTags().contains("Gender:Colt")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Gender:  " + ChatColor.AQUA + "Colt");
                                }
                                if (e.getScoreboardTags().contains("Malnuroshed")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Weight:  " + ChatColor.RED + "Malnurished");
                                }
                                if (!e.getScoreboardTags().contains("Malnuroshed")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Weight:  " + ChatColor.YELLOW + "Average");
                                }
                                if (e.getScoreboardTags().contains("Pregnant")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + "" + ChatColor.LIGHT_PURPLE + " Pregnant");
                                }
                                if (e.getScoreboardTags().contains("InHeat")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + "" + ChatColor.RED + "In Heat");
                                }
                                int i = 0;
                                while (i <= 1) {
                                    if (e.getScoreboardTags().contains("Age:" + i)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Age:  " + ChatColor.WHITE + i + "Year");
                                        break;
                                    }
                                    ++i;
                                }
                                while (i >= 2) {
                                    if (e.getScoreboardTags().contains("Age:" + i)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Age:  " + ChatColor.WHITE + i + "Years");
                                        break;
                                    }
                                    ++i;
                                }
                                for (String brds : plugin.getBreedsConfig().getStringList("Breeds")) {
                                    if (e.getScoreboardTags().contains("Breed:" + brds)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Breed:  " + ChatColor.WHITE + brds);
                                    }
                                }
                                if (e.getScoreboardTags().contains("Color:Black")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Black");
                                }
                                if (e.getScoreboardTags().contains("Color:Seal Brown")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Seal Brown");
                                }
                                if (e.getScoreboardTags().contains("Color:Silver") || e.getScoreboardTags().contains("Color:Silver2")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Gray");
                                }
                                if (e.getScoreboardTags().contains("Color:Bay")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Bay");
                                }
                                if (e.getScoreboardTags().contains("Color:Light Bay")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Light Bay");
                                }
                                if (e.getScoreboardTags().contains("Color:Striped Light Bay")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Striped Light Bay");
                                }
                                if (e.getScoreboardTags().contains("Color:Sooty Bay")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Sooty Bay");
                                }
                                if (e.getScoreboardTags().contains("Color:Buckskin")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Buckskin");
                                }
                                if (e.getScoreboardTags().contains("Color:Palomino")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Palomino");
                                }
                                if (e.getScoreboardTags().contains("Color:Flaxen Palomino")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Flaxen Palomino");
                                }
                                if (e.getScoreboardTags().contains("Color:Light Palomino")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Light Palomino");
                                }
                                if (e.getScoreboardTags().contains("Color:Light Palomino2")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Light Palomino");
                                }
                                if (e.getScoreboardTags().contains("Color:Chestnut")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Chestnut");
                                }
                                if (e.getScoreboardTags().contains("Color:Flaxen Chestnut") || e.getScoreboardTags().contains("Color:Flaxen Chestnut2")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Flaxen Chestnut");
                                }
                                if (e.getScoreboardTags().contains("Color:Light Chestnut")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Light Chestnut");
                                }
                                if (e.getScoreboardTags().contains("Color:Light Flaxen Chestnut")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Light Flaxen Chestnut");
                                }
                                if (e.getScoreboardTags().contains("Color:White") || e.getScoreboardTags().contains("Color:White2")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "White");
                                }
                                if (e.getScoreboardTags().contains("Color:Cremello") || e.getScoreboardTags().contains("Color:Cremello2")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Cremello");
                                }
                                if (e.getScoreboardTags().contains("Style:Appy")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Pattern:  " + ChatColor.WHITE + "Appy");
                                }
                                if (e.getScoreboardTags().contains("Style:Speckled")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Pattern:  " + ChatColor.WHITE + "Speckled");
                                }
                                if (e.getScoreboardTags().contains("Style:Paint")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Pattern:  " + ChatColor.WHITE + "Paint");
                                }
                                if (e.getScoreboardTags().contains("Style:None")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Pattern:  " + ChatColor.WHITE + "None");
                                }
                                if (e.getScoreboardTags().contains("Style:Blaze")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Pattern:  " + ChatColor.WHITE + "Blaze");
                                }
                                if (e.getScoreboardTags().contains("Speed:T1")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Speed:  " + ChatColor.WHITE + "Tier 1");
                                }
                                if (e.getScoreboardTags().contains("Speed:T2")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Speed:  " + ChatColor.WHITE + "Tier 2");
                                }
                                if (e.getScoreboardTags().contains("Speed:T3")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Speed:  " + ChatColor.WHITE + "Tier 3");
                                }
                                if (e.getScoreboardTags().contains("Speed:T4")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Speed:  " + ChatColor.WHITE + "Tier 4");
                                }
                                if (e.getScoreboardTags().contains("Speed:T5")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Speed:  " + ChatColor.WHITE + "Tier 5");
                                }
                                if (e.getScoreboardTags().contains("Speed:T6")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Speed:  " + ChatColor.WHITE + "Tier 6");
                                }
                                if (e.getScoreboardTags().contains("Speed:T7")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Speed:  " + ChatColor.WHITE + "Tier 7");
                                }
                                if (e.getScoreboardTags().contains("1ft")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Jump:  " + ChatColor.WHITE + "1ft");
                                }
                                if (e.getScoreboardTags().contains("2ft")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Jump:  " + ChatColor.WHITE + "2ft");
                                }
                                if (e.getScoreboardTags().contains("3ft")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Jump:  " + ChatColor.WHITE + "3ft");
                                }
                                if (e.getScoreboardTags().contains("4ft")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Jump:  " + ChatColor.WHITE + "4ft");
                                }
                                if (e.getScoreboardTags().contains("5ft")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Jump:  " + ChatColor.WHITE + "5ft");
                                }
                                if (e.getScoreboardTags().contains("6ft")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Jump:  " + ChatColor.WHITE + "6ft");
                                }
                                if (e.getScoreboardTags().contains("Cleaned")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Cleanliness:  " + ChatColor.GREEN + "Clean " + ChatColor.GRAY + "[" + ChatColor.YELLOW + "Brushed" + ChatColor.GRAY + "]");
                                }
                                if (!e.getScoreboardTags().contains("Cleaned")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Cleanliness:  " + ChatColor.RED + "Dirty");
                                }
                                for (String trts : plugin.getTraitConfig().getStringList("Traits")) {
                                    if (e.getScoreboardTags().contains("Trait:" + trts)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Trait:  " + ChatColor.WHITE + trts);
                                    }
                                }
                                if (e.getScoreboardTags().contains("Hunger:10")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Hunger:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "++++++++++" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Hunger:9")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Hunger:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "+++++++++" + ChatColor.RED + "-" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Hunger:8")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Hunger:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "++++++++" + ChatColor.RED + "--" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Hunger:7")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Hunger:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "+++++++" + ChatColor.RED + "---" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Hunger:6")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Hunger:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "++++++" + ChatColor.RED + "----" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Hunger:5")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Hunger:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "+++++" + ChatColor.RED + "-----" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Hunger:4")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Hunger:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "++++" + ChatColor.RED + "------" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Hunger:3")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Hunger:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "+++" + ChatColor.RED + "-------" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Hunger:2")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Hunger:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "++" + ChatColor.RED + "--------" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Hunger:1")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Hunger:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.RED + "---------" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Hunger:0")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Hunger:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "" + ChatColor.RED + "---------" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Thirst:10")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Thirst:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "++++++++++" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Thirst:9")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Thirst:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "+++++++++" + ChatColor.RED + "-" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Thirst:8")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Thirst:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "++++++++" + ChatColor.RED + "--" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Thirst:7")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Thirst:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "+++++++" + ChatColor.RED + "---" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Thirst:6")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Thirst:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "++++++" + ChatColor.RED + "----" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Thirst:5")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Thirst:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "+++++" + ChatColor.RED + "-----" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Thirst:4")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Thirst:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "++++" + ChatColor.RED + "------" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Thirst:3")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Thirst:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "+++" + ChatColor.RED + "-------" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Thirst:2")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Thirst:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "++" + ChatColor.RED + "--------" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Thirst:1")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Thirst:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.RED + "---------" + ChatColor.GRAY + "]");
                                }
                                if (e.getScoreboardTags().contains("Thirst:0")) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Thirst:  " + ChatColor.GRAY + "[" + ChatColor.GREEN + "" + ChatColor.RED + "---------" + ChatColor.GRAY + "]");
                                }
                                return true;
                            }
                        }
                    }

                }
            } else if (args[0].equalsIgnoreCase("resethunger")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                World world = player.getWorld();

                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            e.getScoreboardTags().removeIf(tag -> tag.contains("Hunger:"));
                            e.getScoreboardTags().removeIf(tag -> tag.equalsIgnoreCase("Hunger"));
                            e.addScoreboardTag("Hunger");
                            e.addScoreboardTag("Hunger:0");
                            player.sendMessage(String.format("%s %s's hunger has been reset to 0 and is now hungry!", ChatColor.DARK_RED, e.getName()));
                            return true;
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("debugtags")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                World world = player.getWorld();

                for (Entity e : world.getEntities()) {
                    UUID h = e.getUniqueId();
                    if (euid.equals(h)) {
                        player.sendMessage(String.format("%s %s has the following scoreboard tags: %s %s", ChatColor.GOLD, e.getName(), ChatColor.GREEN, e.getScoreboardTags()));
                    }
                }
                return true;
            } else if (args[0].equalsIgnoreCase("testill")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                World world = player.getWorld();
                if(collection.isEmpty()) {
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                    return true;
                }

                DbHorse horse = plugin.getDbContext().getHorseFromDatabase(euid);
                IllnessColic colic = horse.getColic();
                colic.setSicknessTime(colic.getSicknessTime() + 10);
                horse.setColic(colic);
                plugin.getDbContext().updateHorseInDatabase(horse);

                return true;
            } else if (args[0].equalsIgnoreCase("diagnose")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                World world = player.getWorld();
                if(collection.isEmpty()) {
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "Please first select your horse!");
                    return true;
                }

                DbHorse horse = plugin.getDbContext().getHorseFromDatabase(euid);

                if(player.hasPermission("eq.vet")) {
                    if(args[1].equalsIgnoreCase("colic")) {
                        if(horse.getColic() != null) {
                            IllnessColic colic = horse.getColic();
                            colic.setDiagnosed(true);
                            horse.setColic(colic);
                            plugin.getDbContext().updateHorseInDatabase(horse);
                            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You've succesfully diagnosed this horse with colic!");
                        }
                    }
                } else {
                    // Send no permissions message
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("Diamond")) {
            String name = sender.getName();
            if (sender.hasPermission("eq.diamond")) {
                if (args[0].equalsIgnoreCase("&a")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&a❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&b")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&b❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&c")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&c❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&d")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&d❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&e")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&e❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&f")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&f❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&1")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&1❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&2")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&2❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&3")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&3❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&4")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&4❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&5")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&5❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&6")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&6❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&7")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&7❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&8")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&8❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&9")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&9❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
            }

        }
        if (cmd.getName().equalsIgnoreCase("star")) {
            String name = sender.getName();
            if (sender.hasPermission("eq.star")) {
                if (args[0].equalsIgnoreCase("&a")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&a✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&b")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&b✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&c")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&c✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&d")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&d✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&e")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&e✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&f")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&f✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&1")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&1✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&2")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&2✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&3")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&3✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&4")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&4✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&5")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&5✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&6")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&6✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&7")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&7✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&8")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&8✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&9")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&9✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
            }

        }
        if (cmd.getName().equalsIgnoreCase("flower")) {
            String name = sender.getName();
            if (sender.hasPermission("eq.flower")) {
                if (args[0].equalsIgnoreCase("&a")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&a✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&b")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&b✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&c")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&c✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&d")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&d✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&e")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&e✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&f")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&f✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&1")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&1✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&2")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&2✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&3")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&3✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&4")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&4✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&5")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&5✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&6")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&6✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&7")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&7✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&8")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&8✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&9")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&9✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
            }

        }
        if (cmd.getName().equalsIgnoreCase("heart")) {
            String name = sender.getName();
            if (sender.hasPermission("eq.heart")) {
                if (args[0].equalsIgnoreCase("&a")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&a❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&b")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&b❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&c")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&c❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&d")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&d❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&e")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&e❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&f")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&f❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&1")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&1❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&2")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&2❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&3")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&3❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&4")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&4❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&5")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&5❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&6")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&6❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&7")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&7❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&8")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&8❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&9")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&9❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
            }

        }
        if (cmd.getName().equalsIgnoreCase("bell")) {
            String name = sender.getName();
            UUID uuid = sender.getServer().getPlayerUniqueId(name);
            Player player = (Player) sender;
            Location loc = player.getLocation();
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            bell.put(uuid, "Test");
            String ac = bell.get(uuid);
            if (ac == null) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:block.bell.use master @a[distance=..10] " + x + " " + y + " " + z + " " + "1 2 1");
                bell.put(uuid, "false");
                new BukkitRunnable() {
                    public void run() {
                        bell.put(uuid, "True");
                        return;
                    }
                }.runTaskTimer(plugin, 0, 200);
            } else if (ac.isEmpty()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:block.bell.use master @a[distance=..10] " + x + " " + y + " " + z + " " + "1 2 1");
                bell.put(uuid, "false");
                new BukkitRunnable() {
                    public void run() {
                        bell.put(uuid, "True");
                        return;
                    }
                }.runTaskTimer(plugin, 0, 200);
            } else if (ac == "True") {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:block.bell.use master @a[distance=..10] " + x + " " + y + " " + z + " " + "1 2 1");
                bell.put(uuid, "false");
                new BukkitRunnable() {
                    public void run() {
                        bell.put(uuid, "True");
                        return;
                    }
                }.runTaskTimer(plugin, 0, 200);

            } else if (ac == "false") {
                return true;
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:block.bell.use master @a[distance=..10] " + x + " " + y + " " + z + " " + "1 2 1");
                bell.put(uuid, "false");
                new BukkitRunnable() {
                    public void run() {
                        bell.put(uuid, "True");
                        return;
                    }
                }.runTaskTimer(plugin, 0, 200);
            }
        }
        if (cmd.getName().equalsIgnoreCase("access")) {
            String name = sender.getName();
            UUID uuid = sender.getServer().getPlayerUniqueId(name);
            access.put(uuid, "True");
            String ac = access.get(uuid);
            if (ac == null) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission settemp group.banknote true 1m");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "discord broadcast #874699434364112976 [Bank Notes] " + name + " has access for (1 minute)");
                access.put(uuid, "false");
                new BukkitRunnable() {
                    public void run() {
                        access.put(uuid, "True");
                        return;

                    }
                }.runTaskTimer(plugin, 0, 200);
            } else if (ac.isEmpty()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission settemp group.banknote true 1m");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "discord broadcast #874699434364112976 [Bank Notes] " + name + " has access for (1 minute)");
                access.put(uuid, "false");
                new BukkitRunnable() {
                    public void run() {
                        access.put(uuid, "True");
                        return;

                    }
                }.runTaskTimer(plugin, 0, 200);
            } else if (ac == "True") {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission settemp group.banknote true 1m");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "discord broadcast #874699434364112976 [Bank Notes] " + name + " has access for (1 minute)");
                access.put(uuid, "false");
                new BukkitRunnable() {
                    public void run() {
                        access.put(uuid, "True");
                        return;

                    }
                }.runTaskTimer(plugin, 0, 200);

            } else if (ac == "false") {
                return true;
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission settemp group.banknote true 1m");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "discord broadcast #874699434364112976 [Bank Notes] " + name + " has access for (1 minute)");
                access.put(uuid, "false");
                new BukkitRunnable() {
                    public void run() {
                        access.put(uuid, "True");
                        return;

                    }
                }.runTaskTimer(plugin, 0, 200);
            }
        }
        if (cmd.getName().equalsIgnoreCase("countdown")) {
            if (sender.hasPermission("eq.staff")) {
                sender.getServer().broadcastMessage(ChatColor.YELLOW + "3...");
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    sender.getServer().broadcastMessage(ChatColor.YELLOW + "2...");
                }, 20);
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    sender.getServer().broadcastMessage(ChatColor.YELLOW + "1...");
                }, 40);
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    sender.getServer().broadcastMessage(ChatColor.GREEN + "GO!");
                }, 60);
                return true;
            }
        }

        if (cmd.getName().equalsIgnoreCase("rankup")) {
            Player p2 = plugin.getServer().getPlayer(args[0]);
            if (args.length == 1) {
                if (p2.hasPermission("is.newbie")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " promote ranks");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + args[0] + " bread 9");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + args[0] + " apple 9");
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("eqa")) {
            if (args[0].equalsIgnoreCase("food")) {
                for (World world : Bukkit.getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            if (e.getScoreboardTags().contains("Hunger:0")) {
                                if (!e.getScoreboardTags().contains("DayH-1")) {
                                    if (!e.getScoreboardTags().contains("DayH-2")) {
                                        if (!e.getScoreboardTags().contains("DayH-3")) {
                                            e.addScoreboardTag("DayH-1");
                                            continue;
                                        }
                                    }
                                }
                                if (e.getScoreboardTags().contains("DayH-1")) {
                                    e.removeScoreboardTag("DayH-1");
                                    e.addScoreboardTag("DayH-2");
                                    e.addScoreboardTag("Malnurished");
                                    continue;
                                }
                                if (e.getScoreboardTags().contains("DayH-2")) {
                                    e.removeScoreboardTag("DayH-2");
                                    e.addScoreboardTag("DayH-3");
                                    continue;
                                }
                                if (e.getScoreboardTags().contains("DayH-3")) {
                                    e.removeScoreboardTag("DayH-3");
                                    for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                                        UUID puuid = p.getUniqueId();
                                        String name = e.getCustomName();
                                        if (e.getScoreboardTags().contains("Owner:" + puuid)) {
                                            String offp = p.getName();
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has died of starvation. ");
                                            ((LivingEntity) e).setHealth(0);
                                            continue;
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

            }
            if (args[0].equalsIgnoreCase("preg")) {
                Random r = new Random();
                double t7 = 0.65 + (0.75 - 0.65) * r.nextDouble();
                double t6 = 0.55 + (0.65 - 0.55) * r.nextDouble();
                double t5 = 0.45 + (0.55 - 0.45) * r.nextDouble();
                double t4 = 0.35 + (0.45 - 0.35) * r.nextDouble();
                double t3 = 0.25 + (0.35 - 0.25) * r.nextDouble();
                double t2 = 0.15 + (0.25 - 0.15) * r.nextDouble();
                double t1 = 0.009 + (0.15 - 0.009) * r.nextDouble();
                for (World world : Bukkit.getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey) {
                            if (e.getScoreboardTags().contains("Pregnant")) {
                                if (e.getScoreboardTags().contains("preg7")) {
                                    e.removeScoreboardTag("preg7");
                                    e.addScoreboardTag("preg8");
                                    System.out.println("preg8");
                                }
                                if (e.getScoreboardTags().contains("preg6")) {
                                    e.removeScoreboardTag("preg6");
                                    e.addScoreboardTag("preg7");
                                    System.out.println("preg7");
                                }
                                if (e.getScoreboardTags().contains("preg5")) {
                                    e.removeScoreboardTag("preg5");
                                    e.addScoreboardTag("preg6");
                                    System.out.println("preg6");
                                }
                                if (e.getScoreboardTags().contains("preg4")) {
                                    e.removeScoreboardTag("preg4");
                                    e.addScoreboardTag("preg5");
                                    System.out.println("preg5");
                                }
                                if (e.getScoreboardTags().contains("preg3")) {
                                    e.removeScoreboardTag("preg3");
                                    e.addScoreboardTag("preg4");
                                    System.out.println("preg4");
                                }
                                if (e.getScoreboardTags().contains("preg2")) {
                                    e.removeScoreboardTag("preg2");
                                    e.addScoreboardTag("preg3");
                                    System.out.println("preg3");
                                    break;
                                }
                                if (e.getScoreboardTags().contains("preg1")) {
                                    e.removeScoreboardTag("preg1");
                                    e.addScoreboardTag("preg2");
                                    System.out.println("preg2");
                                }
                            }
                            if (e.getScoreboardTags().contains("preg8")) {
                                e.removeScoreboardTag("preg8");
                                e.removeScoreboardTag("Pregnant");
                                String nme = e.getCustomName();
                                Location loc = e.getLocation();
                                Horse h = (Horse) world.spawnEntity(loc, EntityType.HORSE);
                                UUID uuid = h.getUniqueId();
                                plugin.getDbContext().addHorseToDatabase(h, uuid.toString());
                                h.setAge(-25000);
                                for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                                    UUID puuid = p.getUniqueId();
                                    if (e.getScoreboardTags().contains("Owner:" + puuid)) {
                                        h.addScoreboardTag("Owner:" + puuid);
                                    }
                                }
                                h.addScoreboardTag("Owned");
                                h.addScoreboardTag("Hunger:10");
                                h.addScoreboardTag("Thirst:10");
                                h.addScoreboardTag("Private");
                                h.addScoreboardTag("Level:0");
                                h.addScoreboardTag("XP:1");
                                h.addScoreboardTag("Age:0");
                                h.setCustomName(nme + "'s Foal");
                                h.setTamed(true);
                                List<String> list = plugin.getTraitConfig().getStringList("Traits");
                                int index = new Random().nextInt(list.size());
                                String rnt = list.get(index);
                                h.addScoreboardTag("Trait:" + rnt);
                                System.out.println("Birthing...");
                                if (e.getScoreboardTags().contains("fg:Filly")) {
                                    h.addScoreboardTag("Gender:Filly");
                                }
                                if (e.getScoreboardTags().contains("fg:Colt")) {
                                    h.addScoreboardTag("Gender:Colt");
                                }
                                for (String brds : plugin.getBreedsConfig().getStringList("Breeds")) {
                                    if (e.getScoreboardTags().contains("fb:" + brds)) {
                                        h.addScoreboardTag("Breed:" + brds);
                                        e.removeScoreboardTag("fb:" + brds);
                                        System.out.println("Breed:" + brds);
                                    } else if (e.getScoreboardTags().contains("Breed:" + brds)) {
                                        h.addScoreboardTag("Breed:" + brds);
                                        System.out.println("Breed:" + brds);
                                    }
                                }
                                for (String brds2 : plugin.getCoatConfig().getStringList("Color")) {
                                    if (e.getScoreboardTags().contains("fc:" + brds2)) {
                                        h.addScoreboardTag("Color:" + brds2);
                                        e.removeScoreboardTag("fc:" + brds2);
                                        System.out.println("Color:" + brds2);
                                        if (brds2.equalsIgnoreCase("Palomino")) {
                                            h.setColor(Horse.Color.CREAMY);
                                        } else if (brds2.equalsIgnoreCase("Chestnut")) {
                                            h.setColor(Horse.Color.CHESTNUT);
                                        } else if (brds2.equalsIgnoreCase("Black")) {
                                            h.setColor(Horse.Color.BLACK);
                                        } else if (brds2.equalsIgnoreCase("Bay")) {
                                            h.setColor(Horse.Color.DARK_BROWN);
                                        } else if (brds2.equalsIgnoreCase("Buckskin")) {
                                            h.setColor(Horse.Color.BROWN);
                                        } else if (brds2.equalsIgnoreCase("Gray")) {
                                            h.setColor(Horse.Color.GRAY);
                                        } else if (brds2.equalsIgnoreCase("White")) {
                                            h.setColor(Horse.Color.WHITE);
                                        }
                                    } else if (e.getScoreboardTags().contains("Color:" + brds2)) {
                                        h.addScoreboardTag("Color:" + brds2);
                                        System.out.println("Color:" + brds2);
                                        if (brds2.equalsIgnoreCase("Palomino")) {
                                            h.setColor(Horse.Color.CREAMY);
                                        } else if (brds2.equalsIgnoreCase("Chestnut")) {
                                            h.setColor(Horse.Color.CHESTNUT);
                                        } else if (brds2.equalsIgnoreCase("Black")) {
                                            h.setColor(Horse.Color.BLACK);
                                        } else if (brds2.equalsIgnoreCase("Bay")) {
                                            h.setColor(Horse.Color.DARK_BROWN);
                                        } else if (brds2.equalsIgnoreCase("Buckskin")) {
                                            h.setColor(Horse.Color.BROWN);
                                        } else if (brds2.equalsIgnoreCase("Gray")) {
                                            h.setColor(Horse.Color.GRAY);
                                        } else if (brds2.equalsIgnoreCase("White")) {
                                            h.setColor(Horse.Color.WHITE);
                                        }

                                    }
                                }
                                for (String brds3 : plugin.getCoatConfig().getStringList("Style")) {
                                    if (e.getScoreboardTags().contains("fs:" + brds3)) {
                                        h.addScoreboardTag("Style:" + brds3);
                                        e.removeScoreboardTag("fs:" + brds3);
                                        System.out.println("Style:" + brds3);
                                        if (brds3.equalsIgnoreCase("Blaze")) {
                                            h.setStyle(Horse.Style.WHITE);
                                        } else if (brds3.equalsIgnoreCase("Paint")) {
                                            h.setStyle(Horse.Style.WHITEFIELD);
                                        } else if (brds3.equalsIgnoreCase("Speckled")) {
                                            h.setStyle(Horse.Style.WHITE_DOTS);
                                        } else if (brds3.equalsIgnoreCase("Appy")) {
                                            h.setStyle(Horse.Style.BLACK_DOTS);
                                        } else if (brds3.equalsIgnoreCase("None")) {
                                            h.setStyle(Horse.Style.NONE);
                                        }
                                    } else if (e.getScoreboardTags().contains("Style:" + brds3)) {
                                        System.out.println("Style:" + brds3);
                                        h.addScoreboardTag("Style:" + brds3);
                                        if (brds3.equalsIgnoreCase("Blaze")) {
                                            h.setStyle(Horse.Style.WHITE);
                                        } else if (brds3.equalsIgnoreCase("Paint")) {
                                            h.setStyle(Horse.Style.WHITEFIELD);
                                        } else if (brds3.equalsIgnoreCase("Speckled")) {
                                            h.setStyle(Horse.Style.WHITE_DOTS);
                                        } else if (brds3.equalsIgnoreCase("Appy")) {
                                            h.setStyle(Horse.Style.BLACK_DOTS);
                                        } else if (brds3.equalsIgnoreCase("None")) {
                                            h.setStyle(Horse.Style.NONE);
                                        }
                                    }
                                }
                                if (e.getScoreboardTags().contains("fspd:1")) {
                                    e.removeScoreboardTag("fspd:1");
                                    h.addScoreboardTag("Speed:T1");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t1);
                                }
                                if (e.getScoreboardTags().contains("fspd:2")) {
                                    e.removeScoreboardTag("fspd:2");
                                    h.addScoreboardTag("Speed:T2");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t2);
                                }
                                if (e.getScoreboardTags().contains("fspd:3")) {
                                    e.removeScoreboardTag("fspd:3");
                                    h.addScoreboardTag("Speed:T3");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t3);
                                }
                                if (e.getScoreboardTags().contains("fspd:4")) {
                                    e.removeScoreboardTag("fspd:4");
                                    h.addScoreboardTag("Speed:T4");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t4);
                                }
                                if (e.getScoreboardTags().contains("fspd:5")) {
                                    e.removeScoreboardTag("fspd:5");
                                    h.addScoreboardTag("Speed:T5");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t5);
                                }
                                if (e.getScoreboardTags().contains("fspd:6")) {
                                    e.removeScoreboardTag("fspd:6");
                                    h.addScoreboardTag("Speed:T6");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t6);
                                }
                                if (e.getScoreboardTags().contains("fspd:7")) {
                                    e.removeScoreboardTag("fspd:7");
                                    h.addScoreboardTag("Speed:T7");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t7);
                                }
                                if (e.getScoreboardTags().contains("fj:1")) {
                                    e.removeScoreboardTag("fj:1");
                                    h.addScoreboardTag("1ft");
                                    h.setJumpStrength(.517);
                                }
                                if (e.getScoreboardTags().contains("fj:2")) {
                                    e.removeScoreboardTag("fj:2");
                                    h.addScoreboardTag("2ft");
                                    h.setJumpStrength(.617);
                                }
                                if (e.getScoreboardTags().contains("fj:3")) {
                                    e.removeScoreboardTag("fj:3");
                                    h.addScoreboardTag("3ft");
                                    h.setJumpStrength(.717);
                                }
                                if (e.getScoreboardTags().contains("fj:4")) {
                                    e.removeScoreboardTag("fj:4");
                                    h.addScoreboardTag("4ft");
                                    h.setJumpStrength(.917);
                                }
                                if (e.getScoreboardTags().contains("fj:5")) {
                                    e.removeScoreboardTag("fj:5");
                                    h.addScoreboardTag("5ft");
                                    h.setJumpStrength(1.017);
                                }
                                if (e.getScoreboardTags().contains("fj:6")) {
                                    e.removeScoreboardTag("fj:6");
                                    h.addScoreboardTag("6ft");
                                    h.setJumpStrength(1.117);
                                }
                                if (e.getScoreboardTags().contains("fj:8")) {
                                    e.removeScoreboardTag("fj:8");
                                    h.addScoreboardTag("6ft");
                                    h.setJumpStrength(1.117);
                                }
                            }
                        }
                    }
                }

            }
            if (args[0].equalsIgnoreCase("lease")) {
                for (World world : Bukkit.getWorlds()) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        for (Entity e : world.getEntities()) {
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                int num = 1;
                                while (num <= 30) {
                                    if (e.getScoreboardTags().contains("Leased" + num)) {
                                        e.removeScoreboardTag("Leased" + num);
                                        ++num;
                                        e.addScoreboardTag("Leased" + num);
                                        if (num == 30) {
                                            e.removeScoreboardTag("Leased" + num);
                                            for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                                                UUID puuid = p.getUniqueId();
                                                String offp = p.getName();
                                                if (e.getScoreboardTags().contains("Leaser:" + puuid)) {
                                                    e.removeScoreboardTag("Leaser:" + puuid);
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    ++num;
                                }

                            }
                        }
                    }
                }
            }

            if (args[0].equalsIgnoreCase("updatecolic")) {
                for(DbHorse horse : plugin.getDbContext().getAllHorsesFromDatabase()) {
                    if(horse.getColic() != null) {
                        IllnessColic colic = horse.getColic();

                        if(colic.getSicknessTime() >= 14 && colic.isDiagnosed()) {
                            plugin.getDbContext().removeColicFromHorse(horse);
                        } else {
                            if(horse.isRecentlyLunged()) {
                                colic.setSicknessTime(colic.getSicknessTime() + 1);
                                horse.setColic(colic);
                                plugin.getDbContext().updateHorseInDatabase(horse);
                            }
                        }

                        if(!colic.isDiagnosed()) {
                            colic.setTimeUndiagnosed(colic.getTimeUndiagnosed() + 1);
                            if(colic.getTimeUndiagnosed() >= 8) {
                                OfflinePlayer offp = Bukkit.getOfflinePlayer(UUID.fromString(horse.getOwnerUuid()));
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp.getName() + " Your horse " + horse.getName() + " has died of colic. ");

                                plugin.getDbContext().deleteHorseFromDatabase(horse);
                                for(World world : plugin.getServer().getWorlds()) {
                                    Entity e = world.getEntity(UUID.fromString(horse.getUuid()));
                                    if(e != null) {
                                        ((LivingEntity) e).setHealth(0);

                                    }
                                }
                            }
                        }
                    }
                }
            }

            //I guess this is a ok way to do this.... I mean it works soo??
            // Possibly look into a better way...
            // ^ nah
            if (args[0].equalsIgnoreCase("injury")) {
                for(World world : Bukkit.getServer().getWorlds()) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        for (Entity e : world.getEntities()) {
                            String name = e.getCustomName();
                            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                                if (!e.getScoreboardTags().contains("Invulnerable")) {
                                    if (e.getPassenger() == p) {
                                        String offp = p.getName();
                                        if (e.getScoreboardTags().contains("uji1")) {
                                            e.addScoreboardTag("uij3");
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown injury. ");
                                            break;
                                        }
                                        if (e.getScoreboardTags().contains("uji2")) {
                                            e.addScoreboardTag("uij3");
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown injury. ");
                                            break;
                                        }
                                        Random rnd = new Random();
                                        int i = rnd.nextInt(100);
                                        if (i <= 10) {
                                            e.addScoreboardTag("uij1");
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown injury. ");
                                            break;

                                        }
                                        if (i >= 80) {
                                            e.addScoreboardTag("uij2");
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown injury. ");
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
            if (args[0].equalsIgnoreCase("illness")) {
                for (World world : Bukkit.getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            DbHorse horse = plugin.getDbContext().getHorseFromDatabase(e.getUniqueId());
                            for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                                UUID puuid = p.getUniqueId();
                                if (e.getScoreboardTags().contains("Owner:" + puuid)) {
                                    String offp = p.getName();
                                    String name = e.getCustomName();
                                    if (!e.getScoreboardTags().contains("Vaxed")) {
                                        Random rnd = new Random();
                                        int i = rnd.nextInt(100);
                                        if (i <= 20) {
                                            if (!e.getScoreboardTags().contains("uill1")) {
                                                e.addScoreboardTag("uill1");
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown illness. ");
                                                continue;
                                            }
                                        }
                                        if (i <= 30) {
                                            if (!e.getScoreboardTags().contains("uill2")) {
                                                e.addScoreboardTag("uill2");
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown illness. ");
                                                continue;
                                            }
                                        }
                                        if (i <= 15) {
                                            if (!e.getScoreboardTags().contains("uill3")) {
                                                e.addScoreboardTag("uill3");
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown illness. ");
                                                continue;
                                            }
                                        }
                                        if (i <= 5) {
                                            if (e.getScoreboardTags().contains("Thirst:0")) {
                                                if(horse != null) {
                                                    if(horse.getColic() == null) {
                                                        plugin.getDbContext().giveColicToHorse(horse);
                                                    }
                                                }
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown illness. ");
                                                continue;

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (args[0].equalsIgnoreCase("dirty")) {
                for(World world : Bukkit.getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            e.removeScoreboardTag("hbrush");
                            e.removeScoreboardTag("sbrush");
                            e.removeScoreboardTag("hpick");
                            e.removeScoreboardTag("comb");
                            e.removeScoreboardTag("ccomb");
                            e.removeScoreboardTag("Cleaned");
                        }
                    }
                }
            }
            if (args[0].equalsIgnoreCase("age")) {
                for(World world : Bukkit.getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            int i = 0;
                            while (i <= 62) {
                                if (!e.getScoreboardTags().contains("Invulnerable")) {
                                    if (e.getScoreboardTags().contains("Age:" + i)) {
                                        e.removeScoreboardTag("Age:" + i);
                                        ++i;
                                        e.addScoreboardTag("Age:" + i);
                                        if (i == 62) {
                                            ((LivingEntity) e).setHealth(0);
                                            for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                                                UUID uuid = p.getUniqueId();
                                                if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                                    p.getPlayer();
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    ++i;
                                }

                            }
                        }
                    }
                }

            } else if (args[0].equalsIgnoreCase("heat4")) {
                for (World world : Bukkit.getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey) {
                            if (e.getScoreboardTags().contains("InHeat3")) {
                                e.removeScoreboardTag("InHeat3");
                                e.removeScoreboardTag("InHeat");
                            }
                        }
                    }
                }

            } else if (args[0].equalsIgnoreCase("heat3")) {
                for (World world : Bukkit.getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey) {
                            if (e.getScoreboardTags().contains("InHeat2")) {
                                e.removeScoreboardTag("InHeat2");
                                e.addScoreboardTag("InHeat3");
                            }
                        }
                    }
                }

            } else if (args[0].equalsIgnoreCase("heat2")) {
                for (World world : Bukkit.getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey) {
                            if (e.getScoreboardTags().contains("InHeat1")) {
                                e.removeScoreboardTag("InHeat1");
                                e.addScoreboardTag("InHeat2");
                            }
                        }
                    }
                }

            } else if (args[0].equalsIgnoreCase("heat1")) {
                for (World world : Bukkit.getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey) {
                            if (e.getScoreboardTags().contains("Heat14")) {
                                e.removeScoreboardTag("Heat14");
                                e.addScoreboardTag("Heat1");
                                e.addScoreboardTag("InHeat1");
                                e.addScoreboardTag("InHeat");
                            }
                        }
                    }
                }

            } else if (args[0].equalsIgnoreCase("heat")) {
                for (World world : Bukkit.getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey) {
                            int i = 1;
                            while (i <= 13) {
                                if (e.getScoreboardTags().contains("Heat" + i)) {
                                    e.removeScoreboardTag("Heat" + i);
                                    ++i;
                                    e.addScoreboardTag("Heat" + i);
                                    System.out.println("Heat" + i);
                                    break;

                                }
                                ++i;
                            }
                        }
                    }
                }
            }
        }
        return false;
        //STFU Ik this file is huge idc
    }

    public Location getLocAroundCircle(Location center, double radius, double angleRad) {
        double x = center.getX() + radius * Math.cos(angleRad);
        double z = center.getZ() + radius * Math.sin(angleRad);
        double y = center.getBlockY();

        Location loc = new Location(center.getWorld(), x, y, z);

        Block block = loc.getBlock();
        while(!block.isSolid()) {
            loc.setY(Math.rint(loc.getBlockY() - 1));
            block = loc.getBlock();
        }

        loc.setY(Math.rint(loc.getBlockY() + 1));

        Vector difference = center.toVector().clone().subtract(loc.toVector());
        Vector lookDir = new Vector(difference.getZ(), 0.0, -difference.getX());
        loc.setDirection(lookDir);

        return loc;
    }
}