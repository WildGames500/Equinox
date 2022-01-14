package net.equinox.wild.equinox;


import io.github.bananapuncher714.nbteditor.NBTEditor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.*;

import static net.equinox.wild.equinox.Events1.collection;

@SuppressWarnings("all")
public class Commands implements CommandExecutor {
    private final Equinox plugin;
    public static HashMap<String, String> doublexp = new HashMap<String, String>();
    public static HashMap<UUID, String> access = new HashMap<UUID, String>();
    public static HashMap<UUID, String> bell = new HashMap<UUID, String>();


    public Commands(Equinox plugin) {
        this.plugin = plugin;

    }
    private OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("cpu")) {
            if (args[0].equalsIgnoreCase("test")) {
                sender.sendMessage(ChatColor.YELLOW + " »» Processor load «« ");
                sender.sendMessage(ChatColor.AQUA + "    CPU-Load: " + ChatColor.GREEN + osBean.getSystemLoadAverage() * 100 + "%");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say  »» Processor load «« ");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say  CPU-Load: " +  osBean.getSystemLoadAverage() * 100 + "%");
                return true;
            } else {
                sender.sendMessage("&c»» Invalid arguments!");
            }
        }
        if (cmd.getName().equalsIgnoreCase("eq")) {
            if (args[0].equalsIgnoreCase("ping")) {
                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Pong!");
                return true;
            } else if (args[0].equalsIgnoreCase("tagadd")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                Location loc = player.getLocation();
                World world = player.getWorld();
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
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
            } else if (args[0].equalsIgnoreCase("home")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                Location loc = player.getLocation();
                World world = player.getWorld();
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            Location l = plugin.getConfig().getLocation(euid + " home");
                            e.teleport(l);
                            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Horse teleported to home!");
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
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            plugin.getConfig().set(String.valueOf(euid + " home"), loc);
                            plugin.saveConfig();
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
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse) {
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
                    if (player.hasPermission("eq.breeding")) {
                        player.sendMessage(ChatColor.AQUA + " ● " + ChatColor.WHITE + "Breeding");
                    }
                }
            } else if (args[0].equalsIgnoreCase("killall")) {
                Player player = (Player) sender;
                World world = player.getWorld();
                if (player.hasPermission("eq.dev")) {
                    for (Entity e : world.getEntities()) {
                        if (e.getType() == EntityType.HORSE)
                            ((Horse) e).setHealth(0);
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
                    Player p2 = plugin.getServer().getPlayer(args[1]);
                    UUID uuid2 = p2.getUniqueId();
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    UUID euid = collection.get(uuid);
                    World world = player.getWorld();
                    String name = sender.getName();
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse) {
                            UUID h = e.getUniqueId();
                            String n = e.getCustomName();
                            if (euid.equals(h)) {
                                if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                    e.removeScoreboardTag("Leaser:" + uuid2);
                                    e.removeScoreboardTag("Leased1");
                                    p2.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + name + "You have removed this player from leasing your horse!");
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
                    ;
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse) {
                            UUID h = e.getUniqueId();
                            String n = e.getCustomName();
                            if (euid.equals(h)) {
                                if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                    p2.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + name + "would like to lease you" + n + " for " + "$" + cost + "!");
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
                    if (e instanceof Horse) {
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
                    UUID uuid2 = p2.getUniqueId();
                    World world = Bukkit.getWorld("Equinox");
                    World world2 = Bukkit.getWorld("Flat");
                    player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.GRAY + "][" + ChatColor.YELLOW + args[1] + "'s Horse List" + ChatColor.GRAY + "][" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------");
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid2)) {
                                String hn = e.getCustomName();
                                TextComponent msg = new TextComponent(ChatColor.AQUA + " ● " + ChatColor.WHITE + hn);
                                msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq select " + hn + " " + args[1]));
                                player.spigot().sendMessage(msg);
                            }
                        }
                    }
                    for (Entity e : world2.getEntities()) {
                        if (e instanceof Horse) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid2)) {
                                String hn = e.getCustomName();
                                TextComponent msg = new TextComponent(ChatColor.AQUA + " ● " + ChatColor.WHITE + hn);
                                msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq select " + hn + " " + args[1]));
                                player.spigot().sendMessage(msg);
                            }
                        }
                    }
                } else {
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    Location loc = player.getLocation();
                    World world = Bukkit.getWorld("Equinox");
                    World world2 = Bukkit.getWorld("Flat");
                    player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.GRAY + "][" + ChatColor.YELLOW + "Horse List" + ChatColor.GRAY + "][" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------");
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                String hn = e.getCustomName();
                                TextComponent msg = new TextComponent(ChatColor.AQUA + " ● " + ChatColor.WHITE + hn);
                                msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq select " + hn));
                                player.spigot().sendMessage(msg);
                            }
                        }
                    }
                    for (Entity e : world2.getEntities()) {
                        if (e instanceof Horse) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                String hn = e.getCustomName();
                                TextComponent msg = new TextComponent(ChatColor.AQUA + " ● " + ChatColor.WHITE + hn);
                                msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq select " + hn));
                                player.spigot().sendMessage(msg);
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("select")) {
                if (args.length == 2) {
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    Location loc = player.getLocation();
                    World world = Bukkit.getWorld("Equinox");
                    World world2 = Bukkit.getWorld("Flat");
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                if (e.getCustomName().equals(args[1])) {
                                    ((Horse) e).damage(1, player);
                                    return true;
                                }
                            }
                        }
                    }
                    for (Entity e : world2.getEntities()) {
                        if (e instanceof Horse) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                if (e.getCustomName().equals(args[1])) {
                                    ((Horse) e).damage(1, player);
                                    return true;
                                }
                            }
                        }
                    }
                }
                if (args.length == 3) {
                    Player player = (Player) sender;
                    UUID uuid = player.getUniqueId();
                    OfflinePlayer p2 = plugin.getServer().getOfflinePlayerIfCached(args[2]);
                    UUID uuid2 = p2.getUniqueId();
                    Location loc = player.getLocation();
                    World world = Bukkit.getWorld("Equinox");
                    World world2 = Bukkit.getWorld("Flat");
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid2)) {
                                if (e.getCustomName().equals(args[1])) {
                                    ((Horse) e).damage(1, player);
                                    return true;
                                }
                            }
                        }
                    }
                    for (Entity e : world2.getEntities()) {
                        if (e instanceof Horse) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid2)) {
                                if (e.getCustomName().equals(args[1])) {
                                    ((Horse) e).damage(1, player);
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
                        if (e instanceof Horse) {
                            UUID h = e.getUniqueId();
                            String n = e.getCustomName();
                            if (euid.equals(h)) {
                                if (e.getScoreboardTags().contains("Owner:" + uuid2)) {
                                    e.addScoreboardTag("Leased1");
                                    e.addScoreboardTag("Leaser:" + uuid);
                                    double bal = eco.getBalance(String.valueOf(sender));
                                    System.out.println(bal);
                                    eco.withdrawPlayer(String.valueOf(sender), cost);
                                    eco.depositPlayer(p2, cost);
                                    p2.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + name + " has accepted your request!");
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You are now the lease owner of " + n + "!");
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
                        if (e instanceof Horse) {
                            UUID h = e.getUniqueId();
                            String n = e.getCustomName();
                            if (euid.equals(h)) {
                                if (e.getScoreboardTags().contains("Owner:" + uuid2)) {
                                    e.addScoreboardTag("Owner:" + uuid);
                                    e.removeScoreboardTag("Owner:" + uuid2);
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
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse) {
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
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse) {
                            UUID h = e.getUniqueId();
                            if (euid.equals(h)) {
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have trusted " + name + " to your horse!");
                                e.addScoreboardTag("Member:" + uuid2);
                                return true;
                            }
                        }
                    }

                }
            } else if (args[0].equalsIgnoreCase("tphere")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                Location loc = player.getLocation();
                World world = Bukkit.getWorld("Equinox");
                World world2 = Bukkit.getWorld("Flat");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            e.teleport(loc);
                            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Horse has been teleported to you!");
                            return true;

                        }

                    }
                }
                for (Entity e : world2.getEntities()) {
                    if (e instanceof Horse) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            e.teleport(loc);
                            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Horse has been teleported to you!");
                            return true;

                        }

                    }
                }
            } else if (args[0].equalsIgnoreCase("tp")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                World world = Bukkit.getWorld("Equinox");
                World world2 = Bukkit.getWorld("Flat");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            Location loc = e.getLocation();
                            player.teleport(loc);
                            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have been teleported to your horse!");
                            return true;
                        }
                    }
                }
                for (Entity e : world2.getEntities()) {
                    if (e instanceof Horse) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            Location loc = e.getLocation();
                            player.teleport(loc);
                            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have been teleported to your horse!");
                            return true;
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
                                ((Horse) e).setHealth(0);
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
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse) {
                            UUID h = e.getUniqueId();
                            if (euid.equals(h)) {
                                if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                    e.setCustomName(args[1]);
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have named this horse!");
                                    return true;
                                } else if (e.getScoreboardTags().contains("Member:" + uuid)) {
                                    e.setCustomName(args[1]);
                                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have named this horse!");
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
                    if (e instanceof Horse) {
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
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid)) {
                                e.removeScoreboardTag("Private");
                                e.addScoreboardTag("Public");
                                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "You have made this horse public!");
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
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            if (e.getScoreboardTags().contains("Owner:" + uuid)) {
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
            } else if (args[0].equalsIgnoreCase("lunge")) {
                Player p = (Player) sender;
                UUID uuid = p.getUniqueId();
                UUID euid = collection.get(uuid);
                World world = p.getWorld();
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            if (((Horse) e).getLeashHolder() instanceof Player) {
                                new BukkitRunnable() {
                                    int tick = 0;
                                    int timer = 0;

                                    public void run() {
                                        final float radius = 4.0f;
                                        final float radPerSec = (float) (2 * Math.PI / 4);
                                        final float radPerTick = radPerSec * tick / 20f;
                                        final Location center = p.getLocation();
                                        NBTEditor.set(e, (byte) 0, "EatingHaystack");

                                        ++tick;

                                        Location loc = getLocAroundCircle(center, radius, radPerTick);

                                        e.teleport(loc);
                                        ++timer;

                                        if (timer == 100) {
                                            if (e.getScoreboardTags().contains("Level:0")) {
                                                int i = 1;
                                                while (i <= 20) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 20) {
                                                            e.removeScoreboardTag("Level:0");
                                                            e.addScoreboardTag("Level:1");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 1!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:1")) {
                                                int i = 20;
                                                while (i <= 50) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 50) {
                                                            e.removeScoreboardTag("Level:1");
                                                            e.addScoreboardTag("Level:2");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 2!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:2")) {
                                                int i = 50;
                                                while (i <= 100) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 100) {
                                                            e.removeScoreboardTag("Level:2");
                                                            e.addScoreboardTag("Level:3");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 3!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:3")) {
                                                int i = 100;
                                                while (i <= 165) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 165) {
                                                            e.removeScoreboardTag("Level:3");
                                                            e.addScoreboardTag("Level:4");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 4!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:4")) {
                                                int i = 165;
                                                while (i <= 255) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 255) {
                                                            e.removeScoreboardTag("Level:4");
                                                            e.addScoreboardTag("Level:5");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 5!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:5")) {
                                                int i = 255;
                                                while (i <= 385) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 385) {
                                                            e.removeScoreboardTag("Level:5");
                                                            e.addScoreboardTag("Level:6");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 6!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:6")) {
                                                int i = 385;
                                                while (i <= 495) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 495) {
                                                            e.removeScoreboardTag("Level:6");
                                                            e.addScoreboardTag("Level:7");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 7!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:7")) {
                                                int i = 495;
                                                while (i <= 695) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 695) {
                                                            e.removeScoreboardTag("Level:7");
                                                            e.addScoreboardTag("Level:8");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 8!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:8")) {
                                                int i = 695;
                                                while (i <= 865) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 865) {
                                                            e.removeScoreboardTag("Level:8");
                                                            e.addScoreboardTag("Level:9");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 9!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:9")) {
                                                int i = 865;
                                                while (i <= 1085) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 1085) {
                                                            e.removeScoreboardTag("Level:9");
                                                            e.addScoreboardTag("Level:10");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 10!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:10")) {
                                                int i = 1085;
                                                while (i <= 1500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 1500) {
                                                            e.removeScoreboardTag("Level:10");
                                                            e.addScoreboardTag("Level:11");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 11!");
                                                            for (String tb : plugin.getConfig().getStringList("Bad")) {
                                                                if (e.getScoreboardTags().contains(tb)) {
                                                                    e.removeScoreboardTag("Trait:" + tb);
                                                                    for (String trts : plugin.getConfig().getStringList("Good")) {
                                                                        List tr = Arrays.asList(trts);
                                                                        Random rn = new Random();
                                                                        String rnt = (String) tr.get(rn.nextInt(tr.size()));
                                                                        e.addScoreboardTag("Trait:" + rnt);
                                                                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse has lost there bad trait!");
                                                                        break;
                                                                    }
                                                                }
                                                                break;
                                                            }
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:11")) {
                                                int i = 1500;
                                                while (i <= 1800) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 1800) {
                                                            e.removeScoreboardTag("Level:11");
                                                            e.addScoreboardTag("Level:12");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 12!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:12")) {
                                                int i = 1800;
                                                while (i <= 2000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 2000) {
                                                            e.removeScoreboardTag("Level:12");
                                                            e.addScoreboardTag("Level:13");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 13!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:13")) {
                                                int i = 2000;
                                                while (i <= 2300) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 2300) {
                                                            e.removeScoreboardTag("Level:13");
                                                            e.addScoreboardTag("Level:14");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 14!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:14")) {
                                                int i = 2300;
                                                while (i <= 2800) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 2800) {
                                                            e.removeScoreboardTag("Level:14");
                                                            e.addScoreboardTag("Level:15");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 15!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:15")) {
                                                int i = 2800;
                                                while (i <= 3500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 3500) {
                                                            e.removeScoreboardTag("Level:15");
                                                            e.addScoreboardTag("Level:16");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 16!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:16")) {
                                                int i = 3500;
                                                while (i <= 4200) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 4200) {
                                                            e.removeScoreboardTag("Level:16");
                                                            e.addScoreboardTag("Level:17");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 17!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:17")) {
                                                int i = 4200;
                                                while (i <= 4900) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 4900) {
                                                            e.removeScoreboardTag("Level:17");
                                                            e.addScoreboardTag("Level:18");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 18!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:18")) {
                                                int i = 4900;
                                                while (i <= 5500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 5500) {
                                                            e.removeScoreboardTag("Level:18");
                                                            e.addScoreboardTag("Level:19");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 19!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:19")) {
                                                int i = 5500;
                                                while (i <= 6300) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 6300) {
                                                            e.removeScoreboardTag("Level:19");
                                                            e.addScoreboardTag("Level:20");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 20!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:20")) {
                                                int i = 6300;
                                                while (i <= 7000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 7000) {
                                                            e.removeScoreboardTag("Level:20");
                                                            e.addScoreboardTag("Level:21");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 21!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:21")) {
                                                int i = 7000;
                                                while (i <= 7730) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 7730) {
                                                            e.removeScoreboardTag("Level:21");
                                                            e.addScoreboardTag("Level:22");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 22!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:22")) {
                                                int i = 7730;
                                                while (i <= 8110) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 8110) {
                                                            e.removeScoreboardTag("Level:22");
                                                            e.addScoreboardTag("Level:23");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 23!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:23")) {
                                                int i = 8110;
                                                while (i <= 8560) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 8560) {
                                                            e.removeScoreboardTag("Level:23");
                                                            e.addScoreboardTag("Level:24");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 24!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:24")) {
                                                int i = 8560;
                                                while (i <= 9150) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 9150) {
                                                            e.removeScoreboardTag("Level:24");
                                                            e.addScoreboardTag("Level:25");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 25!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:25")) {
                                                int i = 9150;
                                                while (i <= 9700) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 9700) {
                                                            e.removeScoreboardTag("Level:25");
                                                            e.addScoreboardTag("Level:26");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 26!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:26")) {
                                                int i = 9700;
                                                while (i <= 10200) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 10200) {
                                                            e.removeScoreboardTag("Level:26");
                                                            e.addScoreboardTag("Level:27");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 27!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:27")) {
                                                int i = 10200;
                                                while (i <= 10950) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 10950) {
                                                            e.removeScoreboardTag("Level:27");
                                                            e.addScoreboardTag("Level:28");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 28!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:28")) {
                                                int i = 10950;
                                                while (i <= 11600) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 11600) {
                                                            e.removeScoreboardTag("Level:28");
                                                            e.addScoreboardTag("Level:29");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 29!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:29")) {
                                                int i = 11600;
                                                while (i <= 12200) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 12200) {
                                                            e.removeScoreboardTag("Level:29");
                                                            e.addScoreboardTag("Level:30");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 30!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:30")) {
                                                int i = 12200;
                                                while (i <= 13000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 13000) {
                                                            e.removeScoreboardTag("Level:30");
                                                            e.addScoreboardTag("Level:31");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 31!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:31")) {
                                                int i = 13000;
                                                while (i <= 14000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 14000) {
                                                            e.removeScoreboardTag("Level:31");
                                                            e.addScoreboardTag("Level:32");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 32!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:32")) {
                                                int i = 14000;
                                                while (i <= 15500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 15500) {
                                                            e.removeScoreboardTag("Level:32");
                                                            e.addScoreboardTag("Level:33");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 33!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:33")) {
                                                int i = 15500;
                                                while (i <= 17000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 17000) {
                                                            e.removeScoreboardTag("Level:33");
                                                            e.addScoreboardTag("Level:34");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 34!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:34")) {
                                                int i = 17000;
                                                while (i <= 20000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 20000) {
                                                            e.removeScoreboardTag("Level:34");
                                                            e.addScoreboardTag("Level:35");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 35!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            }
                                        }
                                        if (timer == 200) {
                                            if (e.getScoreboardTags().contains("Level:0")) {
                                                int i = 1;
                                                while (i <= 20) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 20) {
                                                            e.removeScoreboardTag("Level:0");
                                                            e.addScoreboardTag("Level:1");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 1!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:1")) {
                                                int i = 20;
                                                while (i <= 50) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 50) {
                                                            e.removeScoreboardTag("Level:1");
                                                            e.addScoreboardTag("Level:2");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 2!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:2")) {
                                                int i = 50;
                                                while (i <= 100) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 100) {
                                                            e.removeScoreboardTag("Level:2");
                                                            e.addScoreboardTag("Level:3");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 3!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:3")) {
                                                int i = 100;
                                                while (i <= 165) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 165) {
                                                            e.removeScoreboardTag("Level:3");
                                                            e.addScoreboardTag("Level:4");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 4!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:4")) {
                                                int i = 165;
                                                while (i <= 255) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 255) {
                                                            e.removeScoreboardTag("Level:4");
                                                            e.addScoreboardTag("Level:5");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 5!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:5")) {
                                                int i = 255;
                                                while (i <= 385) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 385) {
                                                            e.removeScoreboardTag("Level:5");
                                                            e.addScoreboardTag("Level:6");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 6!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:6")) {
                                                int i = 385;
                                                while (i <= 495) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 495) {
                                                            e.removeScoreboardTag("Level:6");
                                                            e.addScoreboardTag("Level:7");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 7!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:7")) {
                                                int i = 495;
                                                while (i <= 695) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 695) {
                                                            e.removeScoreboardTag("Level:7");
                                                            e.addScoreboardTag("Level:8");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 8!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:8")) {
                                                int i = 695;
                                                while (i <= 865) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 865) {
                                                            e.removeScoreboardTag("Level:8");
                                                            e.addScoreboardTag("Level:9");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 9!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:9")) {
                                                int i = 865;
                                                while (i <= 1085) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 1085) {
                                                            e.removeScoreboardTag("Level:9");
                                                            e.addScoreboardTag("Level:10");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 10!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:10")) {
                                                int i = 1085;
                                                while (i <= 1500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 1500) {
                                                            e.removeScoreboardTag("Level:10");
                                                            e.addScoreboardTag("Level:11");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 11!");
                                                            for (String tb : plugin.getConfig().getStringList("Bad")) {
                                                                if (e.getScoreboardTags().contains(tb)) {
                                                                    e.removeScoreboardTag("Trait:" + tb);
                                                                    for (String trts : plugin.getConfig().getStringList("Good")) {
                                                                        List tr = Arrays.asList(trts);
                                                                        Random rn = new Random();
                                                                        String rnt = (String) tr.get(rn.nextInt(tr.size()));
                                                                        e.addScoreboardTag("Trait:" + rnt);
                                                                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse has lost there bad trait!");
                                                                        break;
                                                                    }
                                                                }
                                                                break;
                                                            }
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:11")) {
                                                int i = 1500;
                                                while (i <= 1800) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 1800) {
                                                            e.removeScoreboardTag("Level:11");
                                                            e.addScoreboardTag("Level:12");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 12!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:12")) {
                                                int i = 1800;
                                                while (i <= 2000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 2000) {
                                                            e.removeScoreboardTag("Level:12");
                                                            e.addScoreboardTag("Level:13");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 13!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:13")) {
                                                int i = 2000;
                                                while (i <= 2300) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 2300) {
                                                            e.removeScoreboardTag("Level:13");
                                                            e.addScoreboardTag("Level:14");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 14!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:14")) {
                                                int i = 2300;
                                                while (i <= 2800) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 2800) {
                                                            e.removeScoreboardTag("Level:14");
                                                            e.addScoreboardTag("Level:15");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 15!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:15")) {
                                                int i = 2800;
                                                while (i <= 3500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 3500) {
                                                            e.removeScoreboardTag("Level:15");
                                                            e.addScoreboardTag("Level:16");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 16!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:16")) {
                                                int i = 3500;
                                                while (i <= 4200) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 4200) {
                                                            e.removeScoreboardTag("Level:16");
                                                            e.addScoreboardTag("Level:17");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 17!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:17")) {
                                                int i = 4200;
                                                while (i <= 4900) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 4900) {
                                                            e.removeScoreboardTag("Level:17");
                                                            e.addScoreboardTag("Level:18");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 18!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:18")) {
                                                int i = 4900;
                                                while (i <= 5500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 5500) {
                                                            e.removeScoreboardTag("Level:18");
                                                            e.addScoreboardTag("Level:19");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 19!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:19")) {
                                                int i = 5500;
                                                while (i <= 6300) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 6300) {
                                                            e.removeScoreboardTag("Level:19");
                                                            e.addScoreboardTag("Level:20");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 20!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:20")) {
                                                int i = 6300;
                                                while (i <= 7000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 7000) {
                                                            e.removeScoreboardTag("Level:20");
                                                            e.addScoreboardTag("Level:21");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 21!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:21")) {
                                                int i = 7000;
                                                while (i <= 7730) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 7730) {
                                                            e.removeScoreboardTag("Level:21");
                                                            e.addScoreboardTag("Level:22");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 22!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:22")) {
                                                int i = 7730;
                                                while (i <= 8110) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 8110) {
                                                            e.removeScoreboardTag("Level:22");
                                                            e.addScoreboardTag("Level:23");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 23!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:23")) {
                                                int i = 8110;
                                                while (i <= 8560) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 8560) {
                                                            e.removeScoreboardTag("Level:23");
                                                            e.addScoreboardTag("Level:24");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 24!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:24")) {
                                                int i = 8560;
                                                while (i <= 9150) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 9150) {
                                                            e.removeScoreboardTag("Level:24");
                                                            e.addScoreboardTag("Level:25");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 25!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:25")) {
                                                int i = 9150;
                                                while (i <= 9700) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 9700) {
                                                            e.removeScoreboardTag("Level:25");
                                                            e.addScoreboardTag("Level:26");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 26!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:26")) {
                                                int i = 9700;
                                                while (i <= 10200) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 10200) {
                                                            e.removeScoreboardTag("Level:26");
                                                            e.addScoreboardTag("Level:27");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 27!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:27")) {
                                                int i = 10200;
                                                while (i <= 10950) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 10950) {
                                                            e.removeScoreboardTag("Level:27");
                                                            e.addScoreboardTag("Level:28");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 28!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:28")) {
                                                int i = 10950;
                                                while (i <= 11600) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 11600) {
                                                            e.removeScoreboardTag("Level:28");
                                                            e.addScoreboardTag("Level:29");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 29!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:29")) {
                                                int i = 11600;
                                                while (i <= 12200) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 12200) {
                                                            e.removeScoreboardTag("Level:29");
                                                            e.addScoreboardTag("Level:30");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 30!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:30")) {
                                                int i = 12200;
                                                while (i <= 13000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 13000) {
                                                            e.removeScoreboardTag("Level:30");
                                                            e.addScoreboardTag("Level:31");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 31!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:31")) {
                                                int i = 13000;
                                                while (i <= 14000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 14000) {
                                                            e.removeScoreboardTag("Level:31");
                                                            e.addScoreboardTag("Level:32");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 32!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:32")) {
                                                int i = 14000;
                                                while (i <= 15500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 15500) {
                                                            e.removeScoreboardTag("Level:32");
                                                            e.addScoreboardTag("Level:33");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 33!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:33")) {
                                                int i = 15500;
                                                while (i <= 17000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 17000) {
                                                            e.removeScoreboardTag("Level:33");
                                                            e.addScoreboardTag("Level:34");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 34!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:34")) {
                                                int i = 17000;
                                                while (i <= 20000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 20000) {
                                                            e.removeScoreboardTag("Level:34");
                                                            e.addScoreboardTag("Level:35");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 35!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            }
                                        }
                                        if (timer == 300) {
                                            if (e.getScoreboardTags().contains("Level:0")) {
                                                int i = 1;
                                                while (i <= 20) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 20) {
                                                            e.removeScoreboardTag("Level:0");
                                                            e.addScoreboardTag("Level:1");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 1!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:1")) {
                                                int i = 20;
                                                while (i <= 50) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 50) {
                                                            e.removeScoreboardTag("Level:1");
                                                            e.addScoreboardTag("Level:2");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 2!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:2")) {
                                                int i = 50;
                                                while (i <= 100) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 100) {
                                                            e.removeScoreboardTag("Level:2");
                                                            e.addScoreboardTag("Level:3");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 3!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:3")) {
                                                int i = 100;
                                                while (i <= 165) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 165) {
                                                            e.removeScoreboardTag("Level:3");
                                                            e.addScoreboardTag("Level:4");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 4!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:4")) {
                                                int i = 165;
                                                while (i <= 255) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 255) {
                                                            e.removeScoreboardTag("Level:4");
                                                            e.addScoreboardTag("Level:5");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 5!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:5")) {
                                                int i = 255;
                                                while (i <= 385) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 385) {
                                                            e.removeScoreboardTag("Level:5");
                                                            e.addScoreboardTag("Level:6");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 6!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:6")) {
                                                int i = 385;
                                                while (i <= 495) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 495) {
                                                            e.removeScoreboardTag("Level:6");
                                                            e.addScoreboardTag("Level:7");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 7!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:7")) {
                                                int i = 495;
                                                while (i <= 695) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 695) {
                                                            e.removeScoreboardTag("Level:7");
                                                            e.addScoreboardTag("Level:8");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 8!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:8")) {
                                                int i = 695;
                                                while (i <= 865) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 865) {
                                                            e.removeScoreboardTag("Level:8");
                                                            e.addScoreboardTag("Level:9");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 9!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:9")) {
                                                int i = 865;
                                                while (i <= 1085) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 1085) {
                                                            e.removeScoreboardTag("Level:9");
                                                            e.addScoreboardTag("Level:10");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 10!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:10")) {
                                                int i = 1085;
                                                while (i <= 1500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 1500) {
                                                            e.removeScoreboardTag("Level:10");
                                                            e.addScoreboardTag("Level:11");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 11!");
                                                            for (String tb : plugin.getConfig().getStringList("Bad")) {
                                                                if (e.getScoreboardTags().contains(tb)) {
                                                                    e.removeScoreboardTag("Trait:" + tb);
                                                                    for (String trts : plugin.getConfig().getStringList("Good")) {
                                                                        List tr = Arrays.asList(trts);
                                                                        Random rn = new Random();
                                                                        String rnt = (String) tr.get(rn.nextInt(tr.size()));
                                                                        e.addScoreboardTag("Trait:" + rnt);
                                                                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse has lost there bad trait!");
                                                                        break;
                                                                    }
                                                                }
                                                                break;
                                                            }
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:11")) {
                                                int i = 1500;
                                                while (i <= 1800) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 1800) {
                                                            e.removeScoreboardTag("Level:11");
                                                            e.addScoreboardTag("Level:12");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 12!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:12")) {
                                                int i = 1800;
                                                while (i <= 2000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 2000) {
                                                            e.removeScoreboardTag("Level:12");
                                                            e.addScoreboardTag("Level:13");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 13!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:13")) {
                                                int i = 2000;
                                                while (i <= 2300) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 2300) {
                                                            e.removeScoreboardTag("Level:13");
                                                            e.addScoreboardTag("Level:14");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 14!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:14")) {
                                                int i = 2300;
                                                while (i <= 2800) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 2800) {
                                                            e.removeScoreboardTag("Level:14");
                                                            e.addScoreboardTag("Level:15");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 15!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:15")) {
                                                int i = 2800;
                                                while (i <= 3500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 3500) {
                                                            e.removeScoreboardTag("Level:15");
                                                            e.addScoreboardTag("Level:16");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 16!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:16")) {
                                                int i = 3500;
                                                while (i <= 4200) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 4200) {
                                                            e.removeScoreboardTag("Level:16");
                                                            e.addScoreboardTag("Level:17");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 17!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:17")) {
                                                int i = 4200;
                                                while (i <= 4900) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 4900) {
                                                            e.removeScoreboardTag("Level:17");
                                                            e.addScoreboardTag("Level:18");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 18!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:18")) {
                                                int i = 4900;
                                                while (i <= 5500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 5500) {
                                                            e.removeScoreboardTag("Level:18");
                                                            e.addScoreboardTag("Level:19");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 19!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:19")) {
                                                int i = 5500;
                                                while (i <= 6300) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 6300) {
                                                            e.removeScoreboardTag("Level:19");
                                                            e.addScoreboardTag("Level:20");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 20!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:20")) {
                                                int i = 6300;
                                                while (i <= 7000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 7000) {
                                                            e.removeScoreboardTag("Level:20");
                                                            e.addScoreboardTag("Level:21");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 21!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:21")) {
                                                int i = 7000;
                                                while (i <= 7730) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 7730) {
                                                            e.removeScoreboardTag("Level:21");
                                                            e.addScoreboardTag("Level:22");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 22!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:22")) {
                                                int i = 7730;
                                                while (i <= 8110) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 8110) {
                                                            e.removeScoreboardTag("Level:22");
                                                            e.addScoreboardTag("Level:23");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 23!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:23")) {
                                                int i = 8110;
                                                while (i <= 8560) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 8560) {
                                                            e.removeScoreboardTag("Level:23");
                                                            e.addScoreboardTag("Level:24");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 24!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:24")) {
                                                int i = 8560;
                                                while (i <= 9150) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 9150) {
                                                            e.removeScoreboardTag("Level:24");
                                                            e.addScoreboardTag("Level:25");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 25!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:25")) {
                                                int i = 9150;
                                                while (i <= 9700) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 9700) {
                                                            e.removeScoreboardTag("Level:25");
                                                            e.addScoreboardTag("Level:26");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 26!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:26")) {
                                                int i = 9700;
                                                while (i <= 10200) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 10200) {
                                                            e.removeScoreboardTag("Level:26");
                                                            e.addScoreboardTag("Level:27");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 27!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:27")) {
                                                int i = 10200;
                                                while (i <= 10950) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 10950) {
                                                            e.removeScoreboardTag("Level:27");
                                                            e.addScoreboardTag("Level:28");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 28!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:28")) {
                                                int i = 10950;
                                                while (i <= 11600) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 11600) {
                                                            e.removeScoreboardTag("Level:28");
                                                            e.addScoreboardTag("Level:29");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 29!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:29")) {
                                                int i = 11600;
                                                while (i <= 12200) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 12200) {
                                                            e.removeScoreboardTag("Level:29");
                                                            e.addScoreboardTag("Level:30");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 30!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:30")) {
                                                int i = 12200;
                                                while (i <= 13000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 13000) {
                                                            e.removeScoreboardTag("Level:30");
                                                            e.addScoreboardTag("Level:31");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 31!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:31")) {
                                                int i = 13000;
                                                while (i <= 14000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 14000) {
                                                            e.removeScoreboardTag("Level:31");
                                                            e.addScoreboardTag("Level:32");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 32!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:32")) {
                                                int i = 14000;
                                                while (i <= 15500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 15500) {
                                                            e.removeScoreboardTag("Level:32");
                                                            e.addScoreboardTag("Level:33");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 33!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:33")) {
                                                int i = 15500;
                                                while (i <= 17000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 17000) {
                                                            e.removeScoreboardTag("Level:33");
                                                            e.addScoreboardTag("Level:34");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 34!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:34")) {
                                                int i = 17000;
                                                while (i <= 20000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 20000) {
                                                            e.removeScoreboardTag("Level:34");
                                                            e.addScoreboardTag("Level:35");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 35!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            }
                                        }

                                        if (timer > 500) {
                                            this.cancel();
                                            if (e.getScoreboardTags().contains("Level:0")) {
                                                int i = 1;
                                                while (i <= 20) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 20) {
                                                            e.removeScoreboardTag("Level:0");
                                                            e.addScoreboardTag("Level:1");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 1!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:1")) {
                                                int i = 20;
                                                while (i <= 50) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 50) {
                                                            e.removeScoreboardTag("Level:1");
                                                            e.addScoreboardTag("Level:2");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 2!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:2")) {
                                                int i = 50;
                                                while (i <= 100) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 100) {
                                                            e.removeScoreboardTag("Level:2");
                                                            e.addScoreboardTag("Level:3");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 3!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:3")) {
                                                int i = 100;
                                                while (i <= 165) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 165) {
                                                            e.removeScoreboardTag("Level:3");
                                                            e.addScoreboardTag("Level:4");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 4!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:4")) {
                                                int i = 165;
                                                while (i <= 255) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 255) {
                                                            e.removeScoreboardTag("Level:4");
                                                            e.addScoreboardTag("Level:5");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 5!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:5")) {
                                                int i = 255;
                                                while (i <= 385) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 385) {
                                                            e.removeScoreboardTag("Level:5");
                                                            e.addScoreboardTag("Level:6");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 6!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:6")) {
                                                int i = 385;
                                                while (i <= 495) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 495) {
                                                            e.removeScoreboardTag("Level:6");
                                                            e.addScoreboardTag("Level:7");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 7!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:7")) {
                                                int i = 495;
                                                while (i <= 695) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 695) {
                                                            e.removeScoreboardTag("Level:7");
                                                            e.addScoreboardTag("Level:8");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 8!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:8")) {
                                                int i = 695;
                                                while (i <= 865) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 865) {
                                                            e.removeScoreboardTag("Level:8");
                                                            e.addScoreboardTag("Level:9");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 9!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:9")) {
                                                int i = 865;
                                                while (i <= 1085) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 1085) {
                                                            e.removeScoreboardTag("Level:9");
                                                            e.addScoreboardTag("Level:10");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 10!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:10")) {
                                                int i = 1085;
                                                while (i <= 1500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 1500) {
                                                            e.removeScoreboardTag("Level:10");
                                                            e.addScoreboardTag("Level:11");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 11!");
                                                            for (String tb : plugin.getConfig().getStringList("Bad")) {
                                                                if (e.getScoreboardTags().contains(tb)) {
                                                                    e.removeScoreboardTag("Trait:" + tb);
                                                                    for (String trts : plugin.getConfig().getStringList("Good")) {
                                                                        List tr = Arrays.asList(trts);
                                                                        Random rn = new Random();
                                                                        String rnt = (String) tr.get(rn.nextInt(tr.size()));
                                                                        e.addScoreboardTag("Trait:" + rnt);
                                                                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse has lost there bad trait!");
                                                                        break;
                                                                    }
                                                                }
                                                                break;
                                                            }
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:11")) {
                                                int i = 1500;
                                                while (i <= 1800) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 1800) {
                                                            e.removeScoreboardTag("Level:11");
                                                            e.addScoreboardTag("Level:12");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 12!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:12")) {
                                                int i = 1800;
                                                while (i <= 2000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 2000) {
                                                            e.removeScoreboardTag("Level:12");
                                                            e.addScoreboardTag("Level:13");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 13!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:13")) {
                                                int i = 2000;
                                                while (i <= 2300) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 2300) {
                                                            e.removeScoreboardTag("Level:13");
                                                            e.addScoreboardTag("Level:14");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 14!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:14")) {
                                                int i = 2300;
                                                while (i <= 2800) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 2800) {
                                                            e.removeScoreboardTag("Level:14");
                                                            e.addScoreboardTag("Level:15");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 15!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:15")) {
                                                int i = 2800;
                                                while (i <= 3500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 3500) {
                                                            e.removeScoreboardTag("Level:15");
                                                            e.addScoreboardTag("Level:16");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 16!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:16")) {
                                                int i = 3500;
                                                while (i <= 4200) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 4200) {
                                                            e.removeScoreboardTag("Level:16");
                                                            e.addScoreboardTag("Level:17");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 17!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:17")) {
                                                int i = 4200;
                                                while (i <= 4900) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 4900) {
                                                            e.removeScoreboardTag("Level:17");
                                                            e.addScoreboardTag("Level:18");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 18!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:18")) {
                                                int i = 4900;
                                                while (i <= 5500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 5500) {
                                                            e.removeScoreboardTag("Level:18");
                                                            e.addScoreboardTag("Level:19");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 19!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:19")) {
                                                int i = 5500;
                                                while (i <= 6300) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 6300) {
                                                            e.removeScoreboardTag("Level:19");
                                                            e.addScoreboardTag("Level:20");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 20!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:20")) {
                                                int i = 6300;
                                                while (i <= 7000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 7000) {
                                                            e.removeScoreboardTag("Level:20");
                                                            e.addScoreboardTag("Level:21");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 21!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:21")) {
                                                int i = 7000;
                                                while (i <= 7730) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 7730) {
                                                            e.removeScoreboardTag("Level:21");
                                                            e.addScoreboardTag("Level:22");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 22!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:22")) {
                                                int i = 7730;
                                                while (i <= 8110) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 8110) {
                                                            e.removeScoreboardTag("Level:22");
                                                            e.addScoreboardTag("Level:23");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 23!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:23")) {
                                                int i = 8110;
                                                while (i <= 8560) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 8560) {
                                                            e.removeScoreboardTag("Level:23");
                                                            e.addScoreboardTag("Level:24");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 24!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:24")) {
                                                int i = 8560;
                                                while (i <= 9150) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 9150) {
                                                            e.removeScoreboardTag("Level:24");
                                                            e.addScoreboardTag("Level:25");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 25!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:25")) {
                                                int i = 9150;
                                                while (i <= 9700) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 9700) {
                                                            e.removeScoreboardTag("Level:25");
                                                            e.addScoreboardTag("Level:26");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 26!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:26")) {
                                                int i = 9700;
                                                while (i <= 10200) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 10200) {
                                                            e.removeScoreboardTag("Level:26");
                                                            e.addScoreboardTag("Level:27");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 27!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:27")) {
                                                int i = 10200;
                                                while (i <= 10950) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 10950) {
                                                            e.removeScoreboardTag("Level:27");
                                                            e.addScoreboardTag("Level:28");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 28!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:28")) {
                                                int i = 10950;
                                                while (i <= 11600) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 11600) {
                                                            e.removeScoreboardTag("Level:28");
                                                            e.addScoreboardTag("Level:29");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 29!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:29")) {
                                                int i = 11600;
                                                while (i <= 12200) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 12200) {
                                                            e.removeScoreboardTag("Level:29");
                                                            e.addScoreboardTag("Level:30");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 30!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:30")) {
                                                int i = 12200;
                                                while (i <= 13000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 13000) {
                                                            e.removeScoreboardTag("Level:30");
                                                            e.addScoreboardTag("Level:31");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 31!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:31")) {
                                                int i = 13000;
                                                while (i <= 14000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 14000) {
                                                            e.removeScoreboardTag("Level:31");
                                                            e.addScoreboardTag("Level:32");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 32!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:32")) {
                                                int i = 14000;
                                                while (i <= 15500) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 15500) {
                                                            e.removeScoreboardTag("Level:32");
                                                            e.addScoreboardTag("Level:33");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 33!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:33")) {
                                                int i = 15500;
                                                while (i <= 17000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 17000) {
                                                            e.removeScoreboardTag("Level:33");
                                                            e.addScoreboardTag("Level:34");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 34!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:34")) {
                                                int i = 17000;
                                                while (i <= 20000) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 20000) {
                                                            e.removeScoreboardTag("Level:34");
                                                            e.addScoreboardTag("Level:35");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 35!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }.runTaskTimer(plugin, 0L, 1L);
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
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            String hn = e.getName();
                            TextComponent msg = new TextComponent(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "------------------------" + "[" + ChatColor.YELLOW + "2" + ChatColor.GRAY + "/" + ChatColor.YELLOW + "2" + ChatColor.GRAY + "][" + ChatColor.WHITE + "<<" + "]" + ChatColor.STRIKETHROUGH + "" + ChatColor.GRAY + "------------------");
                            msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq info"));
                            player.spigot().sendMessage(msg);
                            player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.GRAY + "][" + ChatColor.YELLOW + hn + ChatColor.YELLOW + "'s Info" + ChatColor.GRAY + "][" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------");
                            player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "------------------------------------------");
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
                            if (e.getScoreboardTags().contains("Colic")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.YELLOW + " Illness:" + ChatColor.WHITE + " Colic");
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

                        }
                    }
                }

            } else if (args[0].equalsIgnoreCase("info")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                World world = player.getWorld();
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            String hn = e.getName();
                            TextComponent msg = new TextComponent(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "------------------------" + "[" + ChatColor.YELLOW + "1" + ChatColor.GRAY + "/" + ChatColor.YELLOW + "2" + ChatColor.GRAY + "][" + ChatColor.WHITE + ">>" + ChatColor.GRAY + "]" + "" + ChatColor.STRIKETHROUGH + "------------------");
                            msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eq info2"));
                            player.spigot().sendMessage(msg);
                            player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.GRAY + "][" + ChatColor.YELLOW + hn + ChatColor.YELLOW + "'s Info" + ChatColor.GRAY + "][" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------");
                            player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "------------------------------------------");
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
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " ID:  " + ChatColor.WHITE + h);
                                player.sendMessage(" ");
                            }
                            if (e.getScoreboardTags().contains("Public")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Status:  " + ChatColor.GREEN + "Public");
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " ID:  " + ChatColor.WHITE + h);
                                player.sendMessage(" ");
                            }
                            if (e.getScoreboardTags().contains("Level:0")) {
                                int xp = 1;
                                while (xp <= 20) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level0:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/20");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:1")) {
                                int xp = 20;
                                while (xp <= 50) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level1:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/50");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:2")) {
                                int xp = 50;
                                while (xp <= 100) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level2:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/100");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:3")) {
                                int xp = 100;
                                while (xp <= 165) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level3:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/165");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:4")) {
                                int xp = 165;
                                while (xp <= 255) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level4:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/255");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:5")) {
                                int xp = 255;
                                while (xp <= 385) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level5:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/385");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:6")) {
                                int xp = 385;
                                while (xp <= 495) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level6:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/495");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:7")) {
                                int xp = 495;
                                while (xp <= 695) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level7:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/695");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:8")) {
                                int xp = 695;
                                while (xp <= 865) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level8:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/865");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:9")) {
                                int xp = 865;
                                while (xp <= 1085) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level9:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/1085");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:10")) {
                                int xp = 1085;
                                while (xp <= 1500) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level10:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/1500");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:11")) {
                                int xp = 1500;
                                while (xp <= 1800) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level10:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/1800");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:12")) {
                                int xp = 1800;
                                while (xp <= 2000) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level12:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/2000");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:13")) {
                                int xp = 2000;
                                while (xp <= 2300) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level13:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/2300");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:14")) {
                                int xp = 2300;
                                while (xp <= 2800) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level14:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/2800");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:15")) {
                                int xp = 2800;
                                while (xp <= 3500) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level15:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/3500");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:16")) {
                                int xp = 3500;
                                while (xp <= 4200) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level16:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/4200");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:17")) {
                                int xp = 4200;
                                while (xp <= 4900) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level17:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/4900");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:18")) {
                                int xp = 4900;
                                while (xp <= 5600) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level18:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/5600");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:19")) {
                                int xp = 5600;
                                while (xp <= 6300) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level19:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/6300");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:20")) {
                                int xp = 6300;
                                while (xp <= 7000) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level20:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/7000");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:21")) {
                                int xp = 7000;
                                while (xp <= 7730) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level21:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/7730");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:22")) {
                                int xp = 7730;
                                while (xp <= 8110) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level22:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/8110");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:23")) {
                                int xp = 8110;
                                while (xp <= 8560) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level23:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/8560");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:24")) {
                                int xp = 8560;
                                while (xp <= 9150) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level24:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/9150");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:25")) {
                                int xp = 9150;
                                while (xp <= 9700) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level25:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/9700");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:26")) {
                                int xp = 9700;
                                while (xp <= 10200) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level26:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/10200");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:27")) {
                                int xp = 10200;
                                while (xp <= 10950) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level27:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/10950");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:28")) {
                                int xp = 10950;
                                while (xp <= 11600) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level28:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/11600");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:29")) {
                                int xp = 11600;
                                while (xp <= 12200) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level29:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/12200");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:30")) {
                                int xp = 12200;
                                while (xp <= 13000) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level30:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/13000");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:31")) {
                                int xp = 13000;
                                while (xp <= 14000) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level31:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/14000");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:32")) {
                                int xp = 14000;
                                while (xp <= 15500) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level32:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/15500");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:33")) {
                                int xp = 15500;
                                while (xp <= 17000) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level33:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/17000");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:34")) {
                                int xp = 17000;
                                while (xp <= 20000) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level34:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/20000");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:35")) {
                                int xp = 20000;
                                while (xp <= 20000) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level35:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/20000");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
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
                            if (e.getScoreboardTags().contains("Color:Silver")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Gray");
                            }
                            if (e.getScoreboardTags().contains("Color:Bay")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Bay");
                            }
                            if (e.getScoreboardTags().contains("Color:Buckskin")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Buckskin");
                            }
                            if (e.getScoreboardTags().contains("Color:Palomino")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Palomino");
                            }
                            if (e.getScoreboardTags().contains("Color:Chestnut")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Chestnut");
                            }
                            if (e.getScoreboardTags().contains("Color:White")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "White");
                            }
                            if (e.getScoreboardTags().contains("Style:Blaze")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Pattern:  " + ChatColor.WHITE + "Blaze");
                            }
                            if (e.getScoreboardTags().contains("Style:Paint")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Pattern:  " + ChatColor.WHITE + "Paint");
                            }
                            if (e.getScoreboardTags().contains("Style:None")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Pattern:  " + ChatColor.WHITE + "None");
                            }
                            if (e.getScoreboardTags().contains("Style:Star")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Pattern:  " + ChatColor.WHITE + "Star");
                            }
                            if (e.getScoreboardTags().contains("Style:Snip")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Pattern:  " + ChatColor.WHITE + "Snip");
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
                            if (e.getScoreboardTags().contains("hbrush")) {
                                if (e.getScoreboardTags().contains("sbrush")) {
                                    if (e.getScoreboardTags().contains("hpick")) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Cleanliness:  " + ChatColor.GREEN + "Clean");
                                    }
                                }
                            }
                            if (!e.getScoreboardTags().contains("hbrush")) {
                                if (!e.getScoreboardTags().contains("sbrush")) {
                                    if (!e.getScoreboardTags().contains("hpick")) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Cleanliness:  " + ChatColor.RED + "Dirty");
                                    }
                                }
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
        }
        if (cmd.getName().equalsIgnoreCase("Diamond")) {
            String name = sender.getName();
            if (sender.hasPermission("eq.diamond")) {
                if (args[0].equalsIgnoreCase("&a")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&a❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&b")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&b❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&c")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&c❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&d")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&d❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&e")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&e❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&f")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&f❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&1")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&1❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&2")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&2❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&3")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&3❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&4")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&4❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&5")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&5❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&6")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&6❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&7")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&7❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&8")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&8❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&9")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&9❖&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
            }

        }
        if (cmd.getName().equalsIgnoreCase("star")) {
            String name = sender.getName();
            if (sender.hasPermission("eq.star")) {
                if (args[0].equalsIgnoreCase("&a")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&a✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&b")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&b✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&c")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&c✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&d")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&d✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&e")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&e✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&f")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&f✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&1")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&1✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&2")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&2✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&3")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&3✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&4")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&4✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&5")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&5✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&6")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&6✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&7")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&7✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&8")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&8✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&9")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&9✦&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
            }

        }
        if (cmd.getName().equalsIgnoreCase("flower")) {
            String name = sender.getName();
            if (sender.hasPermission("eq.flower")) {
                if (args[0].equalsIgnoreCase("&a")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&a✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&b")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&b✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&c")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&c✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&d")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&d✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&e")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&e✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&f")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&f✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&1")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&1✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&2")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&2✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&3")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&3✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&4")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&4✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&5")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&5✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&6")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&6✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&7")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&7✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&8")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&8✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&9")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&9✿&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
            }

        }
        if (cmd.getName().equalsIgnoreCase("heart")) {
            String name = sender.getName();
            if (sender.hasPermission("eq.heart")) {
                if (args[0].equalsIgnoreCase("&a")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&a❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&b")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&b❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&c")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&c❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&d")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&d❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&e")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&e❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&f")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&f❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&1")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&1❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&2")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&2❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&3")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&3❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&4")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&4❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&5")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&5❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&6")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&6❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&7")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&7❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&8")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&8❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("&9")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set prefix.22.&8[&9❤&8]");
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.YELLOW+ "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Prefix set!");
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
            }
            else if (ac.isEmpty()) {
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
            else if (ac == "True") {
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
        }if (cmd.getName().equalsIgnoreCase("rankup")) {
            Player p2 = plugin.getServer().getPlayer(args[0]);
            if (args.length == 1) {
                if (sender.hasPermission("eq.staff")) {
                    if (p2.hasPermission("is.newbie")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + args[0] + " 500");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " promote ranks");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + args[0] + " bread 9");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + args[0] + " apple 9");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " permission set group.banknote");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast Congratulations " + args[0] + " on ranking to beginner!");
                    }
                }
            }
        }if (cmd.getName().equalsIgnoreCase("eqa")) {
            if (args[0].equalsIgnoreCase("food")) {
                World world = Bukkit.getWorld("Equinox");
                World world2 = Bukkit.getWorld("Flat");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        if (e.getScoreboardTags().contains("Hunger:0")) {
                            if (e.getScoreboardTags().contains("DayH-1")) {
                                e.removeScoreboardTag("DayH-1");
                                e.addScoreboardTag("DayH-2");
                                break;
                            }
                            if (e.getScoreboardTags().contains("DayH-2")) {
                                e.removeScoreboardTag("DayH-2");
                                e.addScoreboardTag("DayH-3");
                                break;
                            }
                            if (e.getScoreboardTags().contains("DayH-3")) {
                                e.removeScoreboardTag("DayH-3");
                                for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                                    UUID puuid = p.getUniqueId();
                                    String name = e.getCustomName();
                                    if (e.getScoreboardTags().contains("Owner:" + puuid)) {
                                        String offp = p.getName();
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has died of starvation. ");
                                        ((Horse) e).setHealth(0);
                                        break;
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
                World world = Bukkit.getWorld("Equinox");
                World world2 = Bukkit.getWorld("Flat");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        if (e.getScoreboardTags().contains("Pregnant")) {
                            if (e.getScoreboardTags().contains("preg7")) {
                                e.removeScoreboardTag("preg7");
                                e.addScoreboardTag("preg8");
                                System.out.println("preg8");
                                break;
                            }if (e.getScoreboardTags().contains("preg6")) {
                                e.removeScoreboardTag("preg6");
                                e.addScoreboardTag("preg7");
                                System.out.println("preg7");
                                break;
                            }if (e.getScoreboardTags().contains("preg5")) {
                                e.removeScoreboardTag("preg5");
                                e.addScoreboardTag("preg6");
                                System.out.println("preg6");
                                break;
                            }if (e.getScoreboardTags().contains("preg4")) {
                                e.removeScoreboardTag("preg4");
                                e.addScoreboardTag("preg5");
                                System.out.println("preg5");
                                break;
                            }if (e.getScoreboardTags().contains("preg3")) {
                                e.removeScoreboardTag("preg3");
                                e.addScoreboardTag("preg4");
                                System.out.println("preg4");
                                break;
                            }if (e.getScoreboardTags().contains("preg2")) {
                                e.removeScoreboardTag("preg2");
                                e.addScoreboardTag("preg3");
                                System.out.println("preg3");
                                break;
                            }if (e.getScoreboardTags().contains("preg1")) {
                                e.removeScoreboardTag("preg1");
                                e.addScoreboardTag("preg2");
                                System.out.println("preg2");
                                break;
                            }
                        }
                    } if (e.getScoreboardTags().contains("preg8")) {
                        e.removeScoreboardTag("preg8");
                        e.removeScoreboardTag("Pregnant");
                        String nme = e.getCustomName();
                        Location loc = e.getLocation();
                        Horse h = (Horse) world.spawnEntity(loc, EntityType.HORSE);
                        h.setAge(-25000);
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
                        } if (e.getScoreboardTags().contains("fg:Colt")) {
                            h.addScoreboardTag("Gender:Colt");
                        } for (String brds : plugin.getBreedsConfig().getStringList("Breeds")) {
                            if (e.getScoreboardTags().contains("fb:" + brds)) {
                                h.addScoreboardTag("Breed:" + brds);
                                e.removeScoreboardTag("fb:" + brds);
                                System.out.println("Breed:" + brds);
                                break;
                            } else if (e.getScoreboardTags().contains("Breed:" + brds)) {
                                h.addScoreboardTag("Breed:" + brds);
                                System.out.println("Breed:" + brds);
                                break;
                            }
                        }
                        for (String brds2 : plugin.getCoatConfig().getStringList("Color")) {
                            if (e.getScoreboardTags().contains("fc:" + brds2)) {
                                h.addScoreboardTag("Color:" + brds2);
                                e.removeScoreboardTag("fc:" + brds2);
                                System.out.println("Color:" + brds2);
                                if (brds2.equalsIgnoreCase("Palomino")) {
                                    h.setColor(Horse.Color.CREAMY);
                                    break;
                                } else if (brds2.equalsIgnoreCase("Chestnut")) {
                                    h.setColor(Horse.Color.CHESTNUT);
                                    break;
                                } else if (brds2.equalsIgnoreCase("Black")) {
                                    h.setColor(Horse.Color.BLACK);
                                    break;
                                } else if (brds2.equalsIgnoreCase("Bay")) {
                                    h.setColor(Horse.Color.DARK_BROWN);
                                    break;
                                } else if (brds2.equalsIgnoreCase("Buckskin")) {
                                    h.setColor(Horse.Color.BROWN);
                                    break;
                                } else if (brds2.equalsIgnoreCase("Gray")) {
                                    h.setColor(Horse.Color.GRAY);
                                    break;
                                } else if (brds2.equalsIgnoreCase("White")) {
                                    h.setColor(Horse.Color.WHITE);
                                    break;
                                }
                            } else if (e.getScoreboardTags().contains("Color:" + brds2)) {
                                h.addScoreboardTag("Color:" + brds2);
                                System.out.println("Color:" + brds2);
                                if (brds2.equalsIgnoreCase("Palomino")) {
                                    h.setColor(Horse.Color.CREAMY);
                                    break;
                                } else if (brds2.equalsIgnoreCase("Chestnut")) {
                                    h.setColor(Horse.Color.CHESTNUT);
                                    break;
                                } else if (brds2.equalsIgnoreCase("Black")) {
                                    h.setColor(Horse.Color.BLACK);
                                    break;
                                } else if (brds2.equalsIgnoreCase("Bay")) {
                                    h.setColor(Horse.Color.DARK_BROWN);
                                    break;
                                } else if (brds2.equalsIgnoreCase("Buckskin")) {
                                    h.setColor(Horse.Color.BROWN);
                                    break;
                                } else if (brds2.equalsIgnoreCase("Gray")) {
                                    h.setColor(Horse.Color.GRAY);
                                    break;
                                } else if (brds2.equalsIgnoreCase("White")) {
                                    h.setColor(Horse.Color.WHITE);
                                    break;
                                }

                            }
                        } for (String brds3 : plugin.getCoatConfig().getStringList("Style")) {
                            if (e.getScoreboardTags().contains("fs:" + brds3)) {
                                h.addScoreboardTag("Style:" + brds3);
                                e.removeScoreboardTag("fs:" + brds3);
                                System.out.println("Style:" + brds3);
                                if (brds3.equalsIgnoreCase("Snip")) {
                                    h.setStyle(Horse.Style.WHITE);
                                    break;
                                } else if (brds3.equalsIgnoreCase("Paint")) {
                                    h.setStyle(Horse.Style.WHITEFIELD);
                                    break;
                                } else if (brds3.equalsIgnoreCase("Star")) {
                                    h.setStyle(Horse.Style.WHITE_DOTS);
                                    break;
                                } else if (brds3.equalsIgnoreCase("Blaze")) {
                                    h.setStyle(Horse.Style.BLACK_DOTS);
                                    break;
                                } else if (brds3.equalsIgnoreCase("None")) {
                                    h.setStyle(Horse.Style.NONE);
                                    break;
                                }
                            } else if (e.getScoreboardTags().contains("Style:" + brds3)) {
                                System.out.println("Style:" + brds3);
                                h.addScoreboardTag("Style:" + brds3);
                                if (brds3.equalsIgnoreCase("Snip")) {
                                    h.setStyle(Horse.Style.WHITE);
                                    break;
                                } else if (brds3.equalsIgnoreCase("Paint")) {
                                    h.setStyle(Horse.Style.WHITEFIELD);
                                    break;
                                } else if (brds3.equalsIgnoreCase("Star")) {
                                    h.setStyle(Horse.Style.WHITE_DOTS);
                                    break;
                                } else if (brds3.equalsIgnoreCase("Blaze")) {
                                    h.setStyle(Horse.Style.BLACK_DOTS);
                                    break;
                                } else if (brds3.equalsIgnoreCase("None")) {
                                    h.setStyle(Horse.Style.NONE);
                                    break;
                                }
                            }
                        } if (e.getScoreboardTags().contains("fspd:1")) {
                            e.removeScoreboardTag("fspd:1");
                            h.addScoreboardTag("Speed:T1");
                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t1);
                        } if (e.getScoreboardTags().contains("fspd:2")) {
                            e.removeScoreboardTag("fspd:2");
                            h.addScoreboardTag("Speed:T2");
                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t2);
                        } if (e.getScoreboardTags().contains("fspd:3")) {
                            e.removeScoreboardTag("fspd:3");
                            h.addScoreboardTag("Speed:T3");
                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t3);
                        } if (e.getScoreboardTags().contains("fspd:4")) {
                            e.removeScoreboardTag("fspd:4");
                            h.addScoreboardTag("Speed:T4");
                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t4);
                        } if (e.getScoreboardTags().contains("fspd:5")) {
                            e.removeScoreboardTag("fspd:5");
                            h.addScoreboardTag("Speed:T5");
                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t5);
                        } if (e.getScoreboardTags().contains("fspd:6")) {
                            e.removeScoreboardTag("fspd:6");
                            h.addScoreboardTag("Speed:T6");
                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t6);
                        } if (e.getScoreboardTags().contains("fspd:7")) {
                            e.removeScoreboardTag("fspd:7");
                            h.addScoreboardTag("Speed:T7");
                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t7);
                        } if (e.getScoreboardTags().contains("fj:1")) {
                            e.removeScoreboardTag("fj:1");
                            h.addScoreboardTag("1ft");
                            h.setJumpStrength(.517);
                        } if (e.getScoreboardTags().contains("fj:2")) {
                            e.removeScoreboardTag("fj:2");
                            h.addScoreboardTag("2ft");
                            h.setJumpStrength(.617);
                        } if (e.getScoreboardTags().contains("fj:3")) {
                            e.removeScoreboardTag("fj:3");
                            h.addScoreboardTag("3ft");
                            h.setJumpStrength(.717);
                        } if (e.getScoreboardTags().contains("fj:4")) {
                            e.removeScoreboardTag("fj:4");
                            h.addScoreboardTag("4ft");
                            h.setJumpStrength(.917);
                        } if (e.getScoreboardTags().contains("fj:5")) {
                            e.removeScoreboardTag("fj:5");
                            h.addScoreboardTag("5ft");
                            h.setJumpStrength(1.017);
                        } if (e.getScoreboardTags().contains("fj:6")) {
                            e.removeScoreboardTag("fj:6");
                            h.addScoreboardTag("6ft");
                            h.setJumpStrength(1.117);
                        } if (e.getScoreboardTags().contains("fj:8")) {
                            e.removeScoreboardTag("fj:8");
                            h.addScoreboardTag("6ft");
                            h.setJumpStrength(1.117);
                        }
                    }
                }for (Entity e : world2.getEntities()) {
                    if (e instanceof Horse) {
                        int i = 1;
                        while (i < 8) {
                            if (e.getScoreboardTags().contains("Pregnant")) {
                                e.removeScoreboardTag("preg" + i);
                                ++i;
                                e.addScoreboardTag("preg" + i);
                                break;
                            }
                            ++i;
                        }
                    } if (e.getScoreboardTags().contains("preg8")) {
                        e.removeScoreboardTag("preg8");
                        e.removeScoreboardTag("Pregnant");
                        String nme = e.getCustomName();
                        Location loc = e.getLocation();
                        Horse h = (Horse) world.spawnEntity(loc, EntityType.HORSE);
                        h.setAge(-25000);
                        h.addScoreboardTag("Hunger:10");
                        h.addScoreboardTag("Thirst:10");
                        h.addScoreboardTag("Private");
                        h.addScoreboardTag("Level:0");
                        h.addScoreboardTag("XP:1");
                        h.addScoreboardTag("Age:0");
                        h.setCustomName(nme + "'s Foal");
                        h.setTamed(true);
                        if (e.getScoreboardTags().contains("fg:Filly")) {
                            h.addScoreboardTag("Gender:Filly");
                        } if (e.getScoreboardTags().contains("fg:Colt")) {
                            h.addScoreboardTag("Gender:Colt");
                        } for (String brds : plugin.getBreedsConfig().getStringList("Breeds")) {
                            if (e.getScoreboardTags().contains("fb:" + brds)) {
                                h.addScoreboardTag("Breed:" + brds);
                                break;
                            }
                        }
                        for (String brds : plugin.getCoatConfig().getStringList("Color")) {
                            if (e.getScoreboardTags().contains("fc:" + brds)) {
                                h.addScoreboardTag("Color:" + brds);
                                if (brds == "Palomino") {
                                    h.setColor(Horse.Color.CREAMY);
                                }  if (brds == "Chestnut") {
                                    h.setColor(Horse.Color.CHESTNUT);
                                } if (brds == "Black") {
                                    h.setColor(Horse.Color.BLACK);
                                } if (brds == "Bay") {
                                    h.setColor(Horse.Color.DARK_BROWN);
                                } if (brds == "Buckskin") {
                                    h.setColor(Horse.Color.BROWN);
                                } if (brds == "Gray") {
                                    h.setColor(Horse.Color.GRAY);
                                } if (brds == "White") {
                                    h.setColor(Horse.Color.WHITE);
                                }
                                break;

                            }
                        } for (String brds : plugin.getBreedsConfig().getStringList("Style")) {
                            if (e.getScoreboardTags().contains("fs:" + brds)) {
                                h.addScoreboardTag("Style:" + brds);
                                if (brds == "Snip") {
                                    h.setStyle(Horse.Style.WHITE);
                                }  if (brds == "Paint") {
                                    h.setStyle(Horse.Style.WHITEFIELD);
                                } if (brds == "Star") {
                                    h.setStyle(Horse.Style.WHITE_DOTS);
                                } if (brds == "Blaze") {
                                    h.setStyle(Horse.Style.BLACK_DOTS);
                                }
                                break;
                            }
                        } if (e.getScoreboardTags().contains("fspd:1")) {
                            h.addScoreboardTag("T1");
                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t1);
                        } if (e.getScoreboardTags().contains("fspd:2")) {
                            h.addScoreboardTag("T2");
                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t2);
                        } if (e.getScoreboardTags().contains("fspd:3")) {
                            h.addScoreboardTag("T3");
                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t3);
                        } if (e.getScoreboardTags().contains("fspd:4")) {
                            h.addScoreboardTag("T4");
                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t4);
                        } if (e.getScoreboardTags().contains("fspd:5")) {
                            h.addScoreboardTag("T5");
                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t5);
                        } if (e.getScoreboardTags().contains("fspd:6")) {
                            h.addScoreboardTag("T6");
                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t6);
                        } if (e.getScoreboardTags().contains("fspd:7")) {
                            h.addScoreboardTag("T7");
                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t7);
                        } if (e.getScoreboardTags().contains("fj:1")) {
                            h.addScoreboardTag("1ft");
                            h.setJumpStrength(.517);
                        } if (e.getScoreboardTags().contains("fj:2")) {
                            h.addScoreboardTag("2ft");
                            h.setJumpStrength(.617);
                        } if (e.getScoreboardTags().contains("fj:3")) {
                            h.addScoreboardTag("3ft");
                            h.setJumpStrength(.717);
                        } if (e.getScoreboardTags().contains("fj:4")) {
                            h.addScoreboardTag("4ft");
                            h.setJumpStrength(.817);
                        } if (e.getScoreboardTags().contains("fj:5")) {
                            h.addScoreboardTag("5ft");
                            h.setJumpStrength(.917);
                        } if (e.getScoreboardTags().contains("fj:6")) {
                            h.addScoreboardTag("6ft");
                            h.setJumpStrength(1.017);
                        }
                    }
                }
            } if (args[0].equalsIgnoreCase("lease")) {
                World world = Bukkit.getWorld("Equinox");
                World world2 = Bukkit.getWorld("Flat");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse) {
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
                    for (Entity e : world2.getEntities()) {
                        if (e instanceof Horse) {
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
            //I guess this is a ok way to do this.... I mean it works soo??
            // Possibly look into a better way...
            // ^ nah
            if (args[0].equalsIgnoreCase("injury")) {
                World world = Bukkit.getWorld("Equinox");
                World world2 = Bukkit.getWorld("Flat");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    for (Entity e : world.getEntities()) {
                        String name = e.getCustomName();
                        if (e instanceof Horse) {
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
                    for (Entity e : world2.getEntities()) {
                        if (e instanceof Horse) {
                            String name = e.getCustomName();
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
            if (args[0].equalsIgnoreCase("illness")) {
                World world = Bukkit.getWorld("Equinox");
                World world2 = Bukkit.getWorld("Flat");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                            UUID puuid = p.getUniqueId();
                            if (e.getScoreboardTags().contains("Owner:" + puuid)) {
                                String offp = p.getName();
                                String name = e.getCustomName();
                                if (!e.getScoreboardTags().contains("Vaxed")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i >= 40) {
                                        if (!e.getScoreboardTags().contains("uill1")) {
                                            e.addScoreboardTag("uill1");
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown illness. ");
                                        }
                                    }
                                    if (i >= 30) {
                                        if (!e.getScoreboardTags().contains("uill2")) {
                                            e.addScoreboardTag("uill2");
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown illness. ");
                                        }
                                    }
                                    if (i >= 20) {
                                        if (!e.getScoreboardTags().contains("uill3")) {
                                            e.addScoreboardTag("uill3");
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown illness. ");
                                        }
                                    }
                                    if (i <= 3) {
                                        if (e.getScoreboardTags().contains("Thirst:0")) {
                                            if (!e.getScoreboardTags().contains("uill4")) {
                                                e.addScoreboardTag("uill4");
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown illness. ");
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                } for (Entity e : world2.getEntities()) {
                    if (e instanceof Horse) {
                        if (!e.getScoreboardTags().contains("Vaxed")) {
                            Random rnd = new Random();
                            for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                                UUID puuid = p.getUniqueId();
                                if (e.getScoreboardTags().contains("Owner:" + puuid)) {
                                    String offp = p.getName();
                                    String name = e.getCustomName();
                                    int i = rnd.nextInt(100);
                                    if (i >= 40) {
                                        e.addScoreboardTag("uill1");
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown injury. ");
                                    }
                                    if (i >= 30) {
                                        e.addScoreboardTag("uill2");
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown injury. ");
                                    }
                                    if (i >= 20) {
                                        e.addScoreboardTag("uill3");
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown injury. ");
                                    }
                                    if (i <= 5) {
                                        if (e.getScoreboardTags().contains("Thirst:0")) {
                                            e.addScoreboardTag("uill4");
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + offp + " Your horse " + name + " has a unknown injury. ");
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (args[0].equalsIgnoreCase("dirty")) {
                World world = Bukkit.getWorld("Equinox");
                World world2 = Bukkit.getWorld("Flat");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        e.removeScoreboardTag("hbrush");
                        e.removeScoreboardTag("sbrush");
                        e.removeScoreboardTag("hpick");
                    }
                }
            }
            if (args[0].equalsIgnoreCase("age")) {
                World world = Bukkit.getWorld("Equinox");
                World world2 = Bukkit.getWorld("Flat");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        int i = 0;
                        while (i <= 62) {
                            if (!e.getScoreboardTags().contains("Invulnerable")) {
                                if (e.getScoreboardTags().contains("Age:" + i)) {
                                    e.removeScoreboardTag("Age:" + i);
                                    ++i;
                                    e.addScoreboardTag("Age:" + i);
                                    if (i == 62) {
                                        ((Horse) e).setHealth(0);
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
                for (Entity e : world2.getEntities()) {
                    if (e instanceof Horse) {
                        int i = 0;
                        while (i <= 62) {
                            if (!e.getScoreboardTags().contains("Invulnerable")) {
                                if (e.getScoreboardTags().contains("Age:" + i)) {
                                    e.removeScoreboardTag("Age:" + i);
                                    ++i;
                                    e.addScoreboardTag("Age:" + i);
                                    if (i == 62) {
                                        ((Horse) e).setHealth(0);
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
            }else if (args[0].equalsIgnoreCase("heat4")) {
                World world = Bukkit.getWorld("Equinox");
                World world2 = Bukkit.getWorld("Flat");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        if (e.getScoreboardTags().contains("InHeat3")) {
                            e.removeScoreboardTag("InHeat3");
                            e.removeScoreboardTag("InHeat");
                        }
                    }
                }
                for (Entity e : world2.getEntities()) {
                    if (e instanceof Horse) {
                        if (e.getScoreboardTags().contains("InHeat3")) {
                            e.removeScoreboardTag("InHeat3");
                            e.removeScoreboardTag("InHeat");
                        }
                    }
                }
            }else if (args[0].equalsIgnoreCase("heat3")) {
            World world = Bukkit.getWorld("Equinox");
            World world2 = Bukkit.getWorld("Flat");
            for (Entity e : world.getEntities()) {
                if (e instanceof Horse) {
                    if (e.getScoreboardTags().contains("InHeat2")) {
                        e.removeScoreboardTag("InHeat2");
                        e.addScoreboardTag("InHeat3");
                    }
                }
            }
                for (Entity e : world2.getEntities()) {
                    if (e instanceof Horse) {
                        if (e.getScoreboardTags().contains("InHeat2")) {
                            e.removeScoreboardTag("InHeat2");
                            e.addScoreboardTag("InHeat3");
                        }
                    }
                }
            }else if (args[0].equalsIgnoreCase("heat2")) {
                World world = Bukkit.getWorld("Equinox");
                World world2 = Bukkit.getWorld("Flat");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        if (e.getScoreboardTags().contains("InHeat1")) {
                            e.removeScoreboardTag("InHeat1");
                            e.addScoreboardTag("InHeat2");
                        }
                    }
                }for (Entity e : world2.getEntities()) {
                    if (e instanceof Horse) {
                        if (e.getScoreboardTags().contains("InHeat1")) {
                            e.removeScoreboardTag("InHeat1");
                            e.addScoreboardTag("InHeat2");
                        }
                    }
                }
            }else if (args[0].equalsIgnoreCase("heat1")) {
                World world = Bukkit.getWorld("Equinox");
                World world2 = Bukkit.getWorld("Flat");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        if (e.getScoreboardTags().contains("Heat14")) {
                            e.removeScoreboardTag("Heat14");
                            e.addScoreboardTag("Heat1");
                            e.addScoreboardTag("InHeat1");
                            e.addScoreboardTag("InHeat");
                        }
                    }
                }for (Entity e : world2.getEntities()) {
                    if (e instanceof Horse) {
                        if (e.getScoreboardTags().contains("Heat14")) {
                            e.removeScoreboardTag("Heat14");
                            e.addScoreboardTag("Heat1");
                            e.addScoreboardTag("InHeat1");
                            e.addScoreboardTag("InHeat");
                        }
                    }
                }

            }else if (args[0].equalsIgnoreCase("heat")) {
                World world = Bukkit.getWorld("Equinox");
                World world2 = Bukkit.getWorld("Flat");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
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
                } for (Entity e : world2.getEntities()) {
                    if (e instanceof Horse) {
                        int i = 1;
                        while (i <= 13) {
                            if (e.getScoreboardTags().contains("Heat" + i)) {
                                e.removeScoreboardTag("Heat" + i);
                                ++i;
                                e.addScoreboardTag("Heat" + i);
                                break;

                            }
                            ++i;
                        }
                    }
                }
            }
        } return false;
        //STFU Ik this file is huge idc
    }
    public Location getLocAroundCircle(Location center, double radius, double angleRad) {
        double x = center.getX() + radius * Math.cos(angleRad);
        double z = center.getZ() + radius * Math.sin(angleRad);
        double y = center.getY();

        Location loc = new Location(center.getWorld(), x, y, z);
        Vector difference = center.toVector().clone().subtract(loc.toVector());
        Vector lookDir = new Vector(difference.getZ(), 0.0, -difference.getX());
        loc.setDirection(lookDir);

        return loc;
    }
}