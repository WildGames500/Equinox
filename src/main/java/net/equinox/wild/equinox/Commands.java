package net.equinox.wild.equinox;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static net.equinox.wild.equinox.Events1.*;

import java.util.*;


public class Commands<fun> implements CommandExecutor {
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

                                        ++tick;

                                        Location loc = getLocAroundCircle(center, radius, radPerTick);

                                        e.teleport(loc);
                                        ++timer;

                                        if (timer == 100) {
                                            int i = 1;
                                            if (e.getScoreboardTags().contains("Level:0")) {
                                                while (i <= 10) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 10) {
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
                                                while (i <= 35) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 35) {
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
                                                while (i <= 65) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 65) {
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
                                                while (i <= 85) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 85) {
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
                                                while (i <= 115) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 115) {
                                                            e.removeScoreboardTag("Level:4");
                                                            e.addScoreboardTag("Level:5");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 5!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:4")) {
                                                while (i <= 145) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 145) {
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
                                                while (i <= 195) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 195) {
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
                                                while (i <= 265) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 265) {
                                                            e.removeScoreboardTag("Level:6");
                                                            e.addScoreboardTag("Level:7");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 7!");
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
                                            }
                                        }
                                        if (timer == 200) {
                                            int i = 1;
                                            if (e.getScoreboardTags().contains("Level:0")) {
                                                while (i <= 10) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 10) {
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
                                                while (i <= 35) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 35) {
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
                                                while (i <= 65) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 65) {
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
                                                while (i <= 85) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 85) {
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
                                                while (i <= 115) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 115) {
                                                            e.removeScoreboardTag("Level:4");
                                                            e.addScoreboardTag("Level:5");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 5!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:4")) {
                                                while (i <= 145) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 145) {
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
                                                while (i <= 195) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 195) {
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
                                                while (i <= 265) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 265) {
                                                            e.removeScoreboardTag("Level:6");
                                                            e.addScoreboardTag("Level:7");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 7!");
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
                                            }
                                        }
                                        if (timer == 300) {
                                            int i = 1;
                                            if (e.getScoreboardTags().contains("Level:0")) {
                                                while (i <= 10) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 10) {
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
                                                while (i <= 35) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 35) {
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
                                                while (i <= 65) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 65) {
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
                                                while (i <= 85) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 85) {
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
                                                while (i <= 115) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 115) {
                                                            e.removeScoreboardTag("Level:4");
                                                            e.addScoreboardTag("Level:5");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 5!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:4")) {
                                                while (i <= 145) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 145) {
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
                                                while (i <= 195) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 195) {
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
                                                while (i <= 265) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 265) {
                                                            e.removeScoreboardTag("Level:6");
                                                            e.addScoreboardTag("Level:7");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 7!");
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
                                            }
                                        }
                                        if (timer == 400) {
                                            int i = 1;
                                            if (e.getScoreboardTags().contains("Level:0")) {
                                                while (i <= 10) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 10) {
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
                                                while (i <= 35) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 35) {
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
                                                while (i <= 65) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 65) {
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
                                                while (i <= 85) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 85) {
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
                                                while (i <= 115) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 115) {
                                                            e.removeScoreboardTag("Level:4");
                                                            e.addScoreboardTag("Level:5");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 5!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:4")) {
                                                while (i <= 145) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 145) {
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
                                                while (i <= 195) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 195) {
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
                                                while (i <= 265) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 265) {
                                                            e.removeScoreboardTag("Level:6");
                                                            e.addScoreboardTag("Level:7");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 7!");
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
                                            }
                                        }

                                        if (timer > 500) {
                                            this.cancel();
                                            int i = 1;
                                            if (e.getScoreboardTags().contains("Level:0")) {
                                                while (i <= 10) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 10) {
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
                                                while (i <= 35) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 35) {
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
                                                while (i <= 65) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 65) {
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
                                                while (i <= 85) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 85) {
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
                                                while (i <= 115) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 115) {
                                                            e.removeScoreboardTag("Level:4");
                                                            e.addScoreboardTag("Level:5");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 5!");
                                                        }
                                                        return;
                                                    } else {
                                                        ++i;
                                                    }
                                                }
                                            } else if (e.getScoreboardTags().contains("Level:4")) {
                                                while (i <= 145) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 145) {
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
                                                while (i <= 195) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 195) {
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
                                                while (i <= 265) {
                                                    if (e.getScoreboardTags().contains("XP:" + i)) {
                                                        e.removeScoreboardTag("XP:" + i);
                                                        ++i;
                                                        e.addScoreboardTag("XP:" + i);
                                                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+1 XP"));
                                                        p.giveExp(1);
                                                        if (i == 265) {
                                                            e.removeScoreboardTag("Level:6");
                                                            e.addScoreboardTag("Level:7");
                                                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 7!");
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
                                            }
                                        }
                                    }

                                } .runTaskTimer(plugin, 0L,1L);
                            }
                        }
                    }
                }
            } return true;


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
                            player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "------------------------------------------");
                            player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.GRAY + "][" + ChatColor.YELLOW + hn + ChatColor.YELLOW + "'s Info" + ChatColor.GRAY + "][" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------");
                            player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "------------------------------------------");
                            player.sendMessage(" ");
                            for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                                UUID puuid = p.getUniqueId();
                                String offp = p.getName();
                                if (e.getScoreboardTags().contains("Owner:" + puuid)) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Owner:  " + ChatColor.WHITE + offp);
                                    player.sendMessage(" ");
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:0")) {
                                int xp = 1;
                                while (xp <= 10) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level0:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/10");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:1")) {
                                int xp = 10;
                                while (xp <= 35) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level1:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/35");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:2")) {
                                int xp = 35;
                                while (xp <= 65) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level2:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/65");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:3")) {
                                int xp = 65;
                                while (xp <= 85) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level3:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/85");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:4")) {
                                int xp = 85;
                                while (xp <= 115) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level4:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/115");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:5")) {
                                int xp = 115;
                                while (xp <= 145) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level5:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/145");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:6")) {
                                int xp = 145;
                                while (xp <= 195) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level6:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/195");
                                        break;
                                    }
                                    ++xp;
                                }
                            }
                            if (e.getScoreboardTags().contains("Level:7")) {
                                int xp = 195;
                                while (xp <= 265) {
                                    if (e.getScoreboardTags().contains("XP:" + xp)) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Level7:  " + ChatColor.YELLOW + xp + ChatColor.WHITE + "/265");
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
                            for (String brds : plugin.getBreedsConfig().getStringList("Breeds")) {
                                if (e.getScoreboardTags().contains("Breed:" + brds)) {
                                    player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Breed:  " + ChatColor.WHITE + brds);
                                }
                            }
                            if (e.getScoreboardTags().contains("Color:Black")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Black");
                            }
                            if (e.getScoreboardTags().contains("Color:Silver")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Silver");
                            }
                            if (e.getScoreboardTags().contains("Color:Bay")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Bay");
                            }
                            if (e.getScoreboardTags().contains("Color:Brown")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Color:  " + ChatColor.WHITE + "Brown");
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
                            if (e.getScoreboardTags().contains("Style:Crescent")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Pattern:  " + ChatColor.WHITE + "Crescent");
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
                            return true;
                        }
                    }
                }
            }
        return false;
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