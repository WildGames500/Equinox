package net.equinox.wild.equinox;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import static net.equinox.wild.equinox.Commands.doublexp;

@SuppressWarnings("all")
// Storage Of Selected Horse
public class Events1 implements Listener {
    public static HashMap<UUID, UUID> collection = new HashMap<UUID, UUID>();
    private final Equinox plugin;

    public Events1(Equinox plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onBreeding(PlayerLeashEntityEvent e) {
        Entity h = e.getEntity();
        Player p = (Player) e.getLeashHolder();

        if (h.getType() == EntityType.HORSE) {
            if (h.getScoreboardTags().contains("Gender:Stallion")) {
                h.addScoreboardTag("Breeding");
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    h.removeScoreboardTag("Breeding");
                    return;
                },100);
            }
            if (h.getScoreboardTags().contains("Gender:Mare")) {
                double xloc = h.getLocation().getX();
                double yloc = h.getLocation().getY();
                double zloc = h.getLocation().getZ();
                for (Entity h2 : h.getNearbyEntities(xloc, yloc, zloc)) {
                    if (h2.getScoreboardTags().contains("Gender:Stallion")) {
                        if (h2.getScoreboardTags().contains("Breeding")) {
                            if (h.getScoreboardTags().contains("InHeat")) {
                                if (!h.getScoreboardTags().contains("Pregnant")) {
                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "This horse has bred successfully!");
                                    h2.teleport(h);
                                    h.addScoreboardTag("Pregnant");
                                    for (String brds : plugin.getBreedsConfig().getStringList("Breeds")) {
                                        Random rnd = new Random();
                                        int i = rnd.nextInt(100);
                                        if (i <= 49) {
                                            if (h.getScoreboardTags().contains("Breed:" + brds)) {
                                                h.addScoreboardTag("fb:" + brds);
                                                p.sendMessage(brds);
                                            }
                                        } else {
                                            if (h2.getScoreboardTags().contains("Breed:" + brds)) {
                                                h.addScoreboardTag("fb:" + brds);
                                                p.sendMessage(brds);
                                            }

                                        }
                                    }
                                    for (String clrs : plugin.getCoatConfig().getStringList("Color")) {
                                        Random rnd = new Random();
                                        int i = rnd.nextInt(100);
                                        if (i <= 49) {
                                            if (h.getScoreboardTags().contains("Color:" + clrs)) {
                                                h.addScoreboardTag("fc:" + clrs);
                                                p.sendMessage(clrs);
                                            }
                                        } else {
                                            if (h2.getScoreboardTags().contains("Color:" + clrs)) {
                                                h.addScoreboardTag("fc:" + clrs);
                                                p.sendMessage(clrs);
                                            }

                                        }
                                    }
                                    for (String styl : plugin.getCoatConfig().getStringList("Style")) {
                                        Random rnd = new Random();
                                        int i = rnd.nextInt(100);
                                        if (i <= 49) {
                                            if (h.getScoreboardTags().contains("Style:" + styl)) {
                                                h.addScoreboardTag("fs:" + styl);
                                                p.sendMessage(styl);
                                            }
                                        } else {
                                            if (h2.getScoreboardTags().contains("Style:" + styl)) {
                                                h.addScoreboardTag("fs:" + styl);
                                                p.sendMessage(styl);
                                            }

                                        }
                                    }
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 49) {
                                        h.addScoreboardTag("fg:Filly");
                                    } else {
                                        h.addScoreboardTag("fg:Colt");
                                    }
                                    int sps = 0;
                                    int spe = 8;
                                    for (int spd = sps + 1; spd < spe; spd++) {
                                        System.out.println(spd);
                                        if (h.getScoreboardTags().contains("Speed:T" + spd)) {
                                            int spm = spd;
                                            for (int spd2 = sps + 1; spd2 < spe; spd2++) {
                                                if (h2.getScoreboardTags().contains("Speed:T" + spd2)) {
                                                    int spst = spd2;
                                                    if (spm >= spst) {
                                                        Random random = new Random();
                                                        int spfs = random.nextInt(spm - spst) + spst;
                                                        p.sendMessage(String.valueOf(spfs));
                                                        break;
                                                    } else {
                                                        Random random = new Random();
                                                        int spfs = random.nextInt(spst - spm) + spm;
                                                        p.sendMessage(String.valueOf(spfs));
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    int js = 0;
                                    int je = 7;
                                    for (int jd = js + 1; jd < je; jd++) {
                                        if (h.getScoreboardTags().contains(jd + "ft")) {
                                            int jm = jd;
                                            for (int jd2 = js + 1; jd2 < je; jd2++) {
                                                if (h2.getScoreboardTags().contains(jd2 + "ft")) {
                                                    int jst = jd2;
                                                    if (jm >= jst) {
                                                        Random random = new Random();
                                                        int jds = random.nextInt(jm - jst) + jst;
                                                        h.addScoreboardTag("fj:" + jds);
                                                        p.sendMessage(String.valueOf(jds));
                                                        break;
                                                    } else {
                                                        Random random = new Random();
                                                        int jds = random.nextInt(jst - jm) + jm;
                                                        h.addScoreboardTag("fj:" + jds);
                                                        p.sendMessage(String.valueOf(jds));
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    h2.removeScoreboardTag("Breeding");
                                } else {
                                    h2.removeScoreboardTag("Breeding");
                                }
                            }

                        }
                    }
                }

            }
        }
    }

    @EventHandler
    public void onPoopPunch(PlayerInteractEvent e) {
        Entity p = e.getPlayer();
        Player player = (Player) p;

        if (e.getClickedBlock() == null)
            return;

        Block block = e.getClickedBlock();
        if (block.getType() == Material.PLAYER_HEAD) {
            if (doublexp.get("dxp") == "true") {
                Location locate = block.getLocation();
                int x = locate.getBlockX();
                int y = locate.getBlockY();
                int z = locate.getBlockZ();
                int y1 = y + 1;
                Location loc = locate.set(x, y1, z);
                Hologram hologram = HologramsAPI.createHologram(plugin, loc);
                player.playSound(loc, "entity.slime.jump", 1, .5F);
                player.giveExp(2);
                hologram.appendTextLine(ChatColor.YELLOW + "+2 xp");

                block.setType(Material.AIR);
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        hologram.delete();
                    }
                }, 100);
            } else {
                Location locate = block.getLocation();
                int x = locate.getBlockX();
                int y = locate.getBlockY();
                int z = locate.getBlockZ();
                int y1 = y + 1;
                Location loc = locate.set(x, y1, z);
                Hologram hologram = HologramsAPI.createHologram(plugin, loc);
                player.playSound(loc, "entity.slime.jump", 1, .5F);
                player.giveExp(1);
                hologram.appendTextLine(ChatColor.YELLOW + "+1 xp");

                block.setType(Material.AIR);
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        hologram.delete();
                    }
                }, 100);
            }
        }

    }

    // Damage Select Horse
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if (e.getEntityType() == EntityType.HORSE) {
            Player player = ((Player) e.getDamager()).getPlayer();
            Location ploc = player.getLocation();
            UUID uuid = (UUID) player.getUniqueId();
            if (e.getDamager() instanceof Player) {
                if (e.getEntity().getScoreboardTags().contains("Private")) {
                    if (e.getEntity().getScoreboardTags().contains("Owner:" + uuid)) {
                        if (player.getItemInHand().getType() == Material.SPIDER_EYE) {
                            if (player.hasPermission("eq.vet")) {
                                e.getEntity().addScoreboardTag("Vaxed");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now vaccinated!");
                            }else {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have the training to do this.");
                            }
                        } else if (player.getItemInHand().getType() == Material.FLINT) {
                            if (player.hasPermission("eq.vet")) {
                                if (e.getEntity().getScoreboardTags().contains("uill1")) {
                                    e.getEntity().removeScoreboardTag("uill1");
                                    e.getEntity().addScoreboardTag("Strangles");
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has strangles!");
                                }
                                if (e.getEntity().getScoreboardTags().contains("uill2")) {
                                    e.getEntity().removeScoreboardTag("uill2");
                                    e.getEntity().addScoreboardTag("West Nile Virus");
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has west nile virus!");
                                }
                                if (e.getEntity().getScoreboardTags().contains("uill3")) {
                                    e.getEntity().removeScoreboardTag("uill3");
                                    e.getEntity().addScoreboardTag("Flu");
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has flu!");
                                }
                                if (e.getEntity().getScoreboardTags().contains("uill4")) {
                                    e.getEntity().removeScoreboardTag("uill4");
                                    e.getEntity().addScoreboardTag("Colic");
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has colic!");
                                }
                            }else {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have the training to do this.");
                            }
                        } else if (player.getItemInHand().getType() == Material.BOOK) {
                            if (e.getEntity().getScoreboardTags().contains("Pregnant")) {
                                if (player.hasPermission("eq.vet")) {
                                    ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
                                    player.setItemInHand(item);
                                    player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.GRAY + "][" + ChatColor.YELLOW + "Ultra Sound Results" + ChatColor.GRAY + "][" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------");
                                    player.sendMessage("");
                                    if (e.getEntity().getScoreboardTags().contains("fg:Colt")) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Status:  " + ChatColor.BLUE + "Colt");
                                        player.sendMessage("");
                                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Please write this info in a book!");

                                    } else if (e.getEntity().getScoreboardTags().contains("fg:Filly")) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Status:  " + ChatColor.RED + "Filly");
                                        player.sendMessage("");
                                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Please write this info in a book!");

                                    }
                                }else {
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have the training to do this.");
                                }
                            }

                        }
                        else if (player.getItemInHand().getType() == Material.EMERALD) {
                            if (!e.getEntity().getScoreboardTags().contains("hbrush")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have brushed this horse with a hard brush!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("hbrush");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (doublexp.get("dxp") == "true") {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+6 XP");
                                            player.giveExp(6);
                                        } else {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+3 XP");
                                            player.giveExp(3);
                                        }
                                    }
                                }
                            }
                        } else if (player.getItemInHand().getType() == Material.BLAZE_ROD) {
                            if (!e.getEntity().getScoreboardTags().contains("sbrush")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have brushed this horse with a soft brush!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("sbrush");
                                if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (doublexp.get("dxp") == "true") {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+6 XP");
                                            player.giveExp(6);
                                        } else {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+3 XP");
                                            player.giveExp(3);
                                        }
                                    }
                                }
                            }
                        } else if (player.getItemInHand().getType() == Material.BONE) {
                            if (!e.getEntity().getScoreboardTags().contains("hpick")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have picked this horses hooves!");
                                player.playSound(ploc, Sound.BLOCK_STONE_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("hpick");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                        if (doublexp.get("dxp") == "true") {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+6 XP");
                                            player.giveExp(6);
                                        } else {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+3 XP");
                                            player.giveExp(3);
                                        }
                                    }
                                }
                            }
                        } else {
                            collection.put(player.getUniqueId(), e.getEntity().getUniqueId());
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have selected this horse!");
                        }
                    }
                    else if (player.hasPermission("eq.staff")) {
                        if (player.getItemInHand().getType() == Material.SPIDER_EYE) {
                            if (player.hasPermission("eq.vet")) {
                                e.getEntity().addScoreboardTag("Vaxed");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now vaccinated!");
                            }else {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have the training to do this.");
                            }
                        } else if (player.getItemInHand().getType() == Material.FLINT) {
                            if (player.hasPermission("eq.vet")) {
                                if (e.getEntity().getScoreboardTags().contains("uill1")) {
                                    e.getEntity().removeScoreboardTag("uill1");
                                    e.getEntity().addScoreboardTag("Strangles");
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has strangles!");
                                }
                                if (e.getEntity().getScoreboardTags().contains("uill2")) {
                                    e.getEntity().removeScoreboardTag("uill2");
                                    e.getEntity().addScoreboardTag("West Nile Virus");
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has west nile virus!");
                                }
                                if (e.getEntity().getScoreboardTags().contains("uill3")) {
                                    e.getEntity().removeScoreboardTag("uill3");
                                    e.getEntity().addScoreboardTag("Flu");
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has flu!");
                                }
                                if (e.getEntity().getScoreboardTags().contains("uill4")) {
                                    e.getEntity().removeScoreboardTag("uill4");
                                    e.getEntity().addScoreboardTag("Colic");
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has colic!");
                                }
                            }else {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have the training to do this.");
                            }
                        } else if (player.getItemInHand().getType() == Material.BOOK) {
                            if (e.getEntity().getScoreboardTags().contains("Pregnant")) {
                                if (player.hasPermission("eq.vet")) {
                                    ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
                                    player.setItemInHand(item);
                                    player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.GRAY + "][" + ChatColor.YELLOW + "Ultra Sound Results" + ChatColor.GRAY + "][" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------");
                                    player.sendMessage("");
                                    if (e.getEntity().getScoreboardTags().contains("fg:Colt")) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Status:  " + ChatColor.BLUE + "Colt");
                                        player.sendMessage("");
                                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Please write this info in a book!");

                                    } else if (e.getEntity().getScoreboardTags().contains("fg:Filly")) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Status:  " + ChatColor.RED + "Filly");
                                        player.sendMessage("");
                                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Please write this info in a book!");

                                    }
                                }else {
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have the training to do this.");
                                }
                            }

                        }
                        else if (player.getItemInHand().getType() == Material.EMERALD) {
                            if (!e.getEntity().getScoreboardTags().contains("hbrush")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have brushed this horse with a hard brush!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("hbrush");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (doublexp.get("dxp") == "true") {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+6 XP");
                                            player.giveExp(6);
                                        } else {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+3 XP");
                                            player.giveExp(3);
                                        }
                                    }
                                }
                            }
                        } else if (player.getItemInHand().getType() == Material.BLAZE_ROD) {
                            if (!e.getEntity().getScoreboardTags().contains("sbrush")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have brushed this horse with a soft brush!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("sbrush");
                                if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (doublexp.get("dxp") == "true") {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+6 XP");
                                            player.giveExp(6);
                                        } else {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+3 XP");
                                            player.giveExp(3);
                                        }
                                    }
                                }
                            }
                        } else if (player.getItemInHand().getType() == Material.BONE) {
                            if (!e.getEntity().getScoreboardTags().contains("hpick")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have picked this horses hooves!");
                                player.playSound(ploc, Sound.BLOCK_STONE_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("hpick");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                        if (doublexp.get("dxp") == "true") {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+6 XP");
                                            player.giveExp(6);
                                        } else {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+3 XP");
                                            player.giveExp(3);
                                        }
                                    }
                                }
                            }
                        } else {
                            collection.put(player.getUniqueId(), e.getEntity().getUniqueId());
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have selected this horse!");
                        }
                    }
                    else if (e.getEntity().getScoreboardTags().contains("Member:" + uuid)) {
                        if (player.getItemInHand().getType() == Material.EMERALD) {
                            if (!e.getEntity().getScoreboardTags().contains("hbrush")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have brushed this horse with a hard brush!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("hbrush");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (doublexp.get("dxp") == "true") {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+6 XP");
                                            player.giveExp(6);
                                        } else {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+3 XP");
                                            player.giveExp(3);
                                        }
                                    }
                                }
                            }
                        } else if (player.getItemInHand().getType() == Material.BOOK) {
                            if (player.hasPermission("eq.vet")) {
                                if (e.getEntity().getScoreboardTags().contains("Pregnant")) {
                                    ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
                                    player.setItemInHand(item);
                                    player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.GRAY + "][" + ChatColor.YELLOW + "Ultra Sound Results" + ChatColor.GRAY + "][" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------");
                                    player.sendMessage("");
                                    if (e.getEntity().getScoreboardTags().contains("fg:Colt")) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Status:  " + ChatColor.BLUE + "Colt");
                                        player.sendMessage("");
                                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Please write this info in a book!");

                                    } else if (e.getEntity().getScoreboardTags().contains("fg:Filly")) {
                                        player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Status:  " + ChatColor.RED + "Filly");
                                        player.sendMessage("");
                                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Please write this info in a book!");

                                    }
                                }
                            }
                        }
                        else if (player.getItemInHand().getType() == Material.BLAZE_ROD) {
                            if (!e.getEntity().getScoreboardTags().contains("sbrush")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have brushed this horse with a soft brush!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("sbrush");
                                if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (doublexp.get("dxp") == "true") {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+6 XP");
                                            player.giveExp(6);
                                        } else {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+3 XP");
                                            player.giveExp(3);
                                        }
                                    }
                                }
                            }
                        } else if (player.getItemInHand().getType() == Material.BONE) {
                            if (!e.getEntity().getScoreboardTags().contains("hpick")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have picked this horses hooves!");
                                player.playSound(ploc, Sound.BLOCK_STONE_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("hpick");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                        if (doublexp.get("dxp") == "true") {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+6 XP");
                                            player.giveExp(6);
                                        } else {
                                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                            player.sendActionBar(ChatColor.YELLOW + "+3 XP");
                                            player.giveExp(3);
                                        }
                                    }
                                }
                            }
                        }else if (player.getItemInHand().getType() == Material.SPIDER_EYE) {
                            if (player.hasPermission("eq.vet")) {
                                e.getEntity().addScoreboardTag("Vaxed");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now vaccinated!");
                            } else {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED+ "You do not have the training to do this.");
                            }
                        } else if (player.getItemInHand().getType() == Material.FLINT) {
                            if (player.hasPermission("eq.vet")) {
                                if (e.getEntity().getScoreboardTags().contains("uill1")) {
                                    e.getEntity().removeScoreboardTag("uill1");
                                    e.getEntity().addScoreboardTag("Strangles");
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has strangles!");
                                }
                                if (e.getEntity().getScoreboardTags().contains("uill2")) {
                                    e.getEntity().removeScoreboardTag("uill2");
                                    e.getEntity().addScoreboardTag("West Nile Virus");
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has west nile virus!");
                                }
                                if (e.getEntity().getScoreboardTags().contains("uill3")) {
                                    e.getEntity().removeScoreboardTag("uill3");
                                    e.getEntity().addScoreboardTag("Flu");
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has flu!");
                                }
                                if (e.getEntity().getScoreboardTags().contains("uill4")) {
                                    e.getEntity().removeScoreboardTag("uill4");
                                    e.getEntity().addScoreboardTag("Colic");
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has colic!");
                                }
                            } else {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED+ "You do not have the training to do this.");
                            }
                        } else {
                            collection.put(player.getUniqueId(), e.getEntity().getUniqueId());
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have selected this horse!");
                        }
                    }
                }
            }
            if (e.getEntity().getScoreboardTags().contains("Public")) {
                if (player.getItemInHand().getType() == Material.EMERALD) {
                    if (!e.getEntity().getScoreboardTags().contains("hbrush")) {
                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have brushed this horse with a hard brush!");
                        player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                        e.getEntity().addScoreboardTag("hbrush");
                        if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                            if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                if (doublexp.get("dxp") == "true") {
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                    player.sendActionBar(ChatColor.YELLOW + "+6 XP");
                                    player.giveExp(6);
                                } else {
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                    player.sendActionBar(ChatColor.YELLOW + "+3 XP");
                                    player.giveExp(3);
                                }
                            }
                        }
                    }
                } else if (player.getItemInHand().getType() == Material.BOOK) {
                    if (e.getEntity().getScoreboardTags().contains("Pregnant")) {
                        if (player.hasPermission("eq.vet")) {
                            ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
                            player.setItemInHand(item);
                            player.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------" + ChatColor.GRAY + "][" + ChatColor.YELLOW + "Ultra Sound Results" + ChatColor.GRAY + "][" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----------");
                            player.sendMessage("");
                            if (e.getEntity().getScoreboardTags().contains("fg:Colt")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Status:  " + ChatColor.BLUE + "Colt");
                                player.sendMessage("");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Please write this info in a book!");

                            } else if (e.getEntity().getScoreboardTags().contains("fg:Filly")) {
                                player.sendMessage(ChatColor.WHITE + "  ●" + ChatColor.AQUA + " Status:  " + ChatColor.RED + "Filly");
                                player.sendMessage("");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Please write this info in a book!");

                            }
                        }
                    }
                }
                else if (player.getItemInHand().getType() == Material.BLAZE_ROD) {
                    if (!e.getEntity().getScoreboardTags().contains("sbrush")) {
                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have brushed this horse with a soft brush!");
                        player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                        e.getEntity().addScoreboardTag("sbrush");
                        if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                            if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                if (doublexp.get("dxp") == "true") {
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                    player.sendActionBar(ChatColor.YELLOW + "+6 XP");
                                    player.giveExp(6);
                                } else {
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                    player.sendActionBar(ChatColor.YELLOW + "+3 XP");
                                    player.giveExp(3);
                                }
                            }
                        }
                    }
                } else if (player.getItemInHand().getType() == Material.BONE) {
                    if (!e.getEntity().getScoreboardTags().contains("hpick")) {
                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have picked this horses hooves!");
                        player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                        e.getEntity().addScoreboardTag("hpick");
                        if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                            if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                if (doublexp.get("dxp") == "true") {
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                    player.sendActionBar(ChatColor.YELLOW + "+6 XP");
                                    player.giveExp(6);
                                } else {
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                    player.sendActionBar(ChatColor.YELLOW + "+3 XP");
                                    player.giveExp(3);
                                }
                            }
                        }
                    }
                }if (player.getItemInHand().getType() == Material.SPIDER_EYE) {
                    if (player.hasPermission("eq.vet")) {
                        e.getEntity().addScoreboardTag("Vaxed");
                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now vaccinated!");
                    } else {
                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have the training to do this.");
                    }
                } else if (player.getItemInHand().getType() == Material.FLINT) {
                    if (player.hasPermission("eq.vet")) {
                        if (e.getEntity().getScoreboardTags().contains("uill1")) {
                            e.getEntity().removeScoreboardTag("uill1");
                            e.getEntity().addScoreboardTag("Strangles");
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has strangles!");
                        }
                        if (e.getEntity().getScoreboardTags().contains("uill2")) {
                            e.getEntity().removeScoreboardTag("uill2");
                            e.getEntity().addScoreboardTag("West Nile Virus");
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has west nile virus!");
                        }
                        if (e.getEntity().getScoreboardTags().contains("uill3")) {
                            e.getEntity().removeScoreboardTag("uill3");
                            e.getEntity().addScoreboardTag("Flu");
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has flu!");
                        }
                        if (e.getEntity().getScoreboardTags().contains("uill4")) {
                            e.getEntity().removeScoreboardTag("uill4");
                            e.getEntity().addScoreboardTag("Colic");
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse has colic!");
                        }
                    } else {
                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have the training to do this.");
                    }

                } else {
                    collection.put(player.getUniqueId(), e.getEntity().getUniqueId());
                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have selected this horse!");
                }
            } else if (!e.getEntity().getScoreboardTags().contains("Public")) {
                if (!e.getEntity().getScoreboardTags().contains("Private")) {
                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "This is not your horse!");
                }
            }
        }
    }
    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent unload) {
        World world = Bukkit.getWorld("Equinox");
        Chunk chunk = unload.getChunk();
        for (Entity e : world.getEntities()) {
            if (e instanceof Horse) {
                Chunk chunk1 = e.getChunk();
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                int x = chunk1.getX();
                int z = chunk1.getZ();
                Bukkit.getServer().getWorld("Equinox").loadChunk(x, z);
                Bukkit.getServer().getWorld("Equinox").setChunkForceLoaded(x, z, true);
            }
        }
    }
    @EventHandler
    public void AllDamage(EntityDamageEvent d) {
        if (d.getEntityType() == EntityType.HORSE) {
            d.setCancelled(true);
        }
    }
    @EventHandler
    public void onMount(PlayerInteractEntityEvent e) {
        Entity h = e.getRightClicked();
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        System.out.println("Test");
        if (h.getScoreboardTags().contains("private")) {
            System.out.println("Private");
            if (!p.hasPermission("eq.staff")) {
                if (!h.getScoreboardTags().contains("Owner:" + uuid)) {
                    if (!h.getScoreboardTags().contains("Member:" + uuid)) {

                        h.removePassenger(p);
                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have permission to ride this horse!");
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }
    @EventHandler
    public void onDissconnect(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (p.getVehicle().getType() == EntityType.HORSE) {
            p.eject();
        }
    }
}
