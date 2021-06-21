package net.equinox.wild.equinox;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static net.equinox.wild.equinox.Events1.*;

import java.util.Collections;
import java.util.UUID;


public class Commands implements CommandExecutor {
    private final Equinox plugin;


    public Commands(Equinox plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("eq")) {
            if (args[0].equalsIgnoreCase("ping")) {
                sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Pong!");
                return true;
            } else if (args[0].equalsIgnoreCase("tphere")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                Location loc = player.getLocation();
                World world = player.getWorld();
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
            } else if (args[0].equalsIgnoreCase("tp")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                World world = player.getWorld();
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

            } else if (args[0].equalsIgnoreCase("kill")) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                UUID euid = collection.get(uuid);
                World world = player.getWorld();
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        UUID h = e.getUniqueId();
                        if (euid.equals(h)) {
                            ((Horse) e).setHealth(0);
                            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have killed this horse!");
                            return true;
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


            }

        }
        return false;
    }
}
