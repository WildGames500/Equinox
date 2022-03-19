package net.equinox.wild.equinox;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.*;
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
    public void onGamemodeChange(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        if (p.getGameMode().equals("Survival")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission set group.banknote");

        }
        if (p.getGameMode().equals("Creative")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " permission unset group.banknote");

        }
    }

    @EventHandler
    public void onMount(PlayerInteractEntityEvent e) {
        Entity h = e.getRightClicked();
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        if (h.getScoreboardTags().contains("Private")) {
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
    public void onBreeding(PlayerLeashEntityEvent e) {
        Entity h = e.getEntity();
        Player p = (Player) e.getLeashHolder();

        if (h.getType() == EntityType.HORSE) {
            if (h.getScoreboardTags().contains("Gender:Stallion")) {
                h.addScoreboardTag("Breeding");
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    h.removeScoreboardTag("Breeding");
                    return;
                }, 100);
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
                                    h.addScoreboardTag("preg1");
                                    for (String brds : plugin.getBreedsConfig().getStringList("Breeds")) {
                                        Random rnd = new Random();
                                        if (h.getScoreboardTags().contains("Breed:Donkey")) {
                                            if (h2.getScoreboardTags().contains("Breed:Donkey")) {
                                                h.addScoreboardTag("fb:Donkey");
                                                if (h2.getScoreboardTags().contains("Breed:Mule")) {
                                                    return;
                                                } else {
                                                    h.addScoreboardTag("fb:Mule");
                                                    break;
                                                }
                                            }
                                        }
                                        if (h2.getScoreboardTags().contains("Breed:Donkey")) {
                                            if (h.getScoreboardTags().contains("Breed:Donkey")) {
                                                h.addScoreboardTag("fb:Donkey");
                                                if (h.getScoreboardTags().contains("Breed:Mule")) {
                                                    return;
                                                } else {
                                                    h.addScoreboardTag("fb:Mule");
                                                    break;
                                                }
                                            }
                                        } else {
                                            int i = rnd.nextInt(100);
                                            if (i <= 49) {
                                                if (h.getScoreboardTags().contains("Breed:" + brds)) {
                                                    h.addScoreboardTag("fb:" + brds);
                                                    p.sendMessage(brds);
                                                    System.out.println("Breed" + brds);
                                                    break;
                                                }
                                            }
                                            if (i > 49) {
                                                if (h2.getScoreboardTags().contains("Breed:" + brds)) {
                                                    h.addScoreboardTag("fb:" + brds);
                                                    p.sendMessage(brds);
                                                    System.out.println("Breed" + brds);
                                                    break;
                                                }

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
                                                System.out.println("Color" + clrs);
                                                break;
                                            }
                                        }
                                        if (i > 49) {
                                            if (h2.getScoreboardTags().contains("Color:" + clrs)) {
                                                h.addScoreboardTag("fc:" + clrs);
                                                p.sendMessage(clrs);
                                                System.out.println("Color" + clrs);
                                                break;
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
                                                System.out.println("Style" + styl);
                                                break;
                                            }
                                        }
                                        if (i > 49) {
                                            if (h2.getScoreboardTags().contains("Style:" + styl)) {
                                                h.addScoreboardTag("fs:" + styl);
                                                p.sendMessage(styl);
                                                System.out.println("Style" + styl);
                                                break;
                                            }

                                        }
                                    }
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 49) {
                                        h.addScoreboardTag("fg:Filly");
                                        System.out.println("fg:Filly");
                                    }
                                    if (i > 49) {
                                        h.addScoreboardTag("fg:Colt");
                                        System.out.println("fg:Colt");
                                    }
                                    int sps = 0;
                                    int spe = 8;
                                    for (int spd = sps + 1; spd < spe; spd++) {
                                        if (h.getScoreboardTags().contains("Speed:T" + spd)) {
                                            int spm = spd;
                                            int sps2 = 0;
                                            int spe2 = 8;
                                            for (int spd2 = sps2 + 1; spd2 < spe2; spd2++) {
                                                if (h2.getScoreboardTags().contains("Speed:T" + spd2)) {
                                                    int spst = spd2;
                                                    if (spm > spst) {
                                                        Random random = new Random();
                                                        int spfs = rnd.nextInt(spm);
                                                        p.sendMessage(String.valueOf(spfs));
                                                        System.out.println(spfs);
                                                        h.addScoreboardTag("fspd:" + spfs);
                                                        break;
                                                    } else if (spm < spst) {
                                                        Random random = new Random();
                                                        int spfs = rnd.nextInt(spst);
                                                        p.sendMessage(String.valueOf(spfs));
                                                        System.out.println(spfs);
                                                        h.addScoreboardTag("fspd:" + spfs);
                                                        break;
                                                    } else if (spm == spst) {
                                                        int spfs = spm;
                                                        h.addScoreboardTag("fspd:" + spfs);
                                                        System.out.println(spfs);
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
                                            int js2 = 0;
                                            int je2 = 7;
                                            for (int jd2 = js2 + 1; jd2 < je2; jd2++) {
                                                if (h2.getScoreboardTags().contains(jd2 + "ft")) {
                                                    int jst = jd2;
                                                    if (jm < jst) {
                                                        Random random = new Random();
                                                        int jds = rnd.nextInt(jst);
                                                        h.addScoreboardTag("fj:" + jds);
                                                        p.sendMessage(String.valueOf(jds));
                                                        System.out.println(jds);
                                                        break;
                                                    } else if (jm > jst) {
                                                        Random random = new Random();
                                                        int jds = rnd.nextInt(jm);
                                                        h.addScoreboardTag("fj:" + jds);
                                                        p.sendMessage(String.valueOf(jds));
                                                        System.out.println(jds);
                                                        break;
                                                    } else if (jm == jst) {
                                                        Random random = new Random();
                                                        int jds = jm;
                                                        h.addScoreboardTag("fj:" + jds);
                                                        p.sendMessage(String.valueOf(jds));
                                                        System.out.println(jds);
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
    // Why did I make this so painful?
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if (e.getEntityType() == EntityType.HORSE || e.getEntityType() == EntityType.DONKEY || e.getEntityType() == EntityType.MULE) {
            Player player = ((Player) e.getDamager()).getPlayer();
            Location ploc = player.getLocation();
            UUID uuid = (UUID) player.getUniqueId();
            if (e.getDamager() instanceof Player) {
                if (e.getEntity().getScoreboardTags().contains("Private")) {
                    //I hate that I had to do this for each permission for the horse please shoot me if I have to edit this...
                    //Well I had to edit it...
                    //Oh god not again...
                    // Rabies, Tetnus, WestNile, Strangles & Flu
                    if (e.getEntity().getScoreboardTags().contains("Owner:" + uuid)) {
                        if (player.getItemInHand().getType() == Material.SPIDER_EYE) {
                            if (player.hasPermission("eq.vet")) {
                                e.getEntity().addScoreboardTag("Vaxed");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now vaccinated!");
                                ItemStack heldItem = player.getItemInHand();
                                if (heldItem.getType() == Material.SPIDER_EYE) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                            } else if (player.hasPermission("eq.vettech")) {
                                e.getEntity().addScoreboardTag("Vaxed");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now vaccinated!");
                                ItemStack heldItem = player.getItemInHand();
                                if (heldItem.getType() == Material.SPIDER_EYE) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                            } else {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have the training to do this.");
                            }
                        } else if (player.getItemInHand().getType() == Material.CLAY_BALL) {
                            if (e.getEntity().getScoreboardTags().contains("Colic")) {
                                ItemStack heldItem = player.getItemInHand();
                                if (heldItem.getType() == Material.GOLD_NUGGET) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                                e.getEntity().removeScoreboardTag("Colic");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
                            }
                        } else if (player.getItemInHand().getType() == Material.GOLD_NUGGET) {
                            if (e.getEntity().getScoreboardTags().contains("Flu")) {
                                ItemStack heldItem = player.getItemInHand();
                                if (heldItem.getType() == Material.GOLD_NUGGET) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                                e.getEntity().removeScoreboardTag("Flu");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
                            }
                        } else if (player.getItemInHand().getType() == Material.PRISMARINE_CRYSTALS) {
                            if (e.getEntity().getScoreboardTags().contains("Strangles")) {
                                ItemStack heldItem = player.getItemInHand();
                                e.getEntity().removeScoreboardTag("Strangles");
                                if (heldItem.getType() == Material.PRISMARINE_CRYSTALS) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
                            }
                        } else if (player.getItemInHand().getType() == Material.NETHER_WART) {
                            if (e.getEntity().getScoreboardTags().contains("West Nile Virus")) {
                                ItemStack heldItem = player.getItemInHand();
                                e.getEntity().removeScoreboardTag("Strangles");
                                if (heldItem.getType() == Material.PRISMARINE_CRYSTALS) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                                e.getEntity().removeScoreboardTag("West Nile Virus");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
                            }

                        } else if (player.getItemInHand().getType().equals(Material.WHEAT)) {
                            ItemStack heldItem = player.getItemInHand();
                            if (e.getEntity().getScoreboardTags().contains("Hunger:9")) {
                                player.getInventory().remove(Material.WHEAT);
                                e.getEntity().removeScoreboardTag("Hunger:9");
                                e.getEntity().addScoreboardTag("Hunger:10");
                                e.getEntity().removeScoreboardTag("Hunger");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:8")) {
                                player.getInventory().remove(Material.WHEAT);
                                e.getEntity().removeScoreboardTag("Hunger:8");
                                e.getEntity().addScoreboardTag("Hunger:9");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:7")) {
                                player.getInventory().remove(Material.WHEAT);
                                e.getEntity().removeScoreboardTag("Hunger:7");
                                e.getEntity().addScoreboardTag("Hunger:8");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:6")) {
                                player.getInventory().remove(Material.WHEAT);
                                e.getEntity().removeScoreboardTag("Hunger:6");
                                e.getEntity().addScoreboardTag("Hunger:7");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:5")) {
                                player.getInventory().remove(Material.WHEAT);
                                e.getEntity().removeScoreboardTag("Hunger:5");
                                e.getEntity().addScoreboardTag("Hunger:6");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:4")) {
                                player.getInventory().remove(Material.WHEAT);
                                e.getEntity().removeScoreboardTag("Hunger:4");
                                e.getEntity().addScoreboardTag("Hunger:5");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:3")) {
                                player.getInventory().remove(Material.WHEAT);
                                e.getEntity().removeScoreboardTag("Hunger:3");
                                e.getEntity().addScoreboardTag("Hunger:4");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:2")) {
                                player.getInventory().remove(Material.WHEAT);
                                e.getEntity().removeScoreboardTag("Hunger:2");
                                e.getEntity().addScoreboardTag("Hunger:3");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:1")) {
                                player.getInventory().remove(Material.WHEAT);
                                e.getEntity().removeScoreboardTag("Hunger:1");
                                e.getEntity().addScoreboardTag("Hunger:2");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:0")) {
                                player.getInventory().remove(Material.WHEAT);
                                e.getEntity().removeScoreboardTag("Hunger:0");
                                e.getEntity().addScoreboardTag("Hunger:1");
                                if (e.getEntity().getScoreboardTags().contains("DayH-1")) {
                                    e.getEntity().removeScoreboardTag("DayH-1");
                                    return;
                                }
                                if (e.getEntity().getScoreboardTags().contains("DayH-2")) {
                                    e.getEntity().removeScoreboardTag("DayH-2");
                                    return;
                                }
                                if (e.getEntity().getScoreboardTags().contains("DayH-3")) {
                                    e.getEntity().removeScoreboardTag("DayH-3");
                                    return;

                                } else {
                                    return;
                                }
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
                                } else {
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have the training to do this.");
                                }
                            }

                        } else if (player.getItemInHand().getType() == Material.EMERALD) {
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
                            if (player.isSneaking()) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have given this horse some pats!");
                                World world = e.getEntity().getWorld();
                                Location loc = e.getEntity().getLocation();
                                int x = (int) loc.getX();
                                int y = (int) loc.getY();
                                int z = (int) loc.getZ();
                                int y1 = y + 1;
                                world.spawnParticle(Particle.HEART, x, y1, z, 5);
                            } else {
                                collection.put(player.getUniqueId(), e.getEntity().getUniqueId());
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have selected this horse!");
                            }
                        }
                    } else if (player.hasPermission("eq.staff")) {
                        if (player.getItemInHand().getType() == Material.SPIDER_EYE) {
                            if (player.hasPermission("eq.vet")) {
                                e.getEntity().addScoreboardTag("Vaxed");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now vaccinated!");
                            } else {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have the training to do this.");
                            }
                        } else if (player.getItemInHand().getType() == Material.CLAY_BALL) {
                            if (e.getEntity().getScoreboardTags().contains("Colic")) {
                                ItemStack heldItem = player.getItemInHand();
                                e.getEntity().removeScoreboardTag("Colic");
                                if (heldItem.getType() == Material.CLAY_BALL) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                            }
                            e.getEntity().removeScoreboardTag("Colic");
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
                        } else if (player.getItemInHand().getType() == Material.GOLD_NUGGET) {
                            if (e.getEntity().getScoreboardTags().contains("Flu")) {
                                ItemStack heldItem = player.getItemInHand();
                                e.getEntity().removeScoreboardTag("Flu");
                                if (heldItem.getType() == Material.GOLD_NUGGET) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                                e.getEntity().removeScoreboardTag("Flu");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
                            }
                        } else if (player.getItemInHand().getType() == Material.PRISMARINE_CRYSTALS) {
                            if (e.getEntity().getScoreboardTags().contains("Strangles")) {
                                ItemStack heldItem = player.getItemInHand();
                                e.getEntity().removeScoreboardTag("Strangles");
                                if (heldItem.getType() == Material.PRISMARINE_CRYSTALS) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                                e.getEntity().removeScoreboardTag("Strangles");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
                            }
                        } else if (player.getItemInHand().getType() == Material.NETHER_WART) {
                            if (e.getEntity().getScoreboardTags().contains("West Nile Virus")) {
                                ItemStack heldItem = player.getItemInHand();
                                e.getEntity().removeScoreboardTag("West Nile Virus");
                                if (heldItem.getType() == Material.NETHER_WART) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                                e.getEntity().removeScoreboardTag("West Nile Virus");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
                            }
                        } else if (player.getItemInHand().getType() == Material.WHEAT) {
                            ItemStack heldItem = player.getItemInHand();
                            if (e.getEntity().getScoreboardTags().contains("Hunger:9")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                e.getEntity().removeScoreboardTag("Hunger:9");
                                e.getEntity().addScoreboardTag("Hunger:10");
                                e.getEntity().removeScoreboardTag("Hunger");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:8")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                e.getEntity().removeScoreboardTag("Hunger:8");
                                e.getEntity().addScoreboardTag("Hunger:9");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:7")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                e.getEntity().removeScoreboardTag("Hunger:7");
                                e.getEntity().addScoreboardTag("Hunger:8");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:6")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                e.getEntity().removeScoreboardTag("Hunger:6");
                                e.getEntity().addScoreboardTag("Hunger:7");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:5")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                e.getEntity().removeScoreboardTag("Hunger:5");
                                e.getEntity().addScoreboardTag("Hunger:6");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:4")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                e.getEntity().removeScoreboardTag("Hunger:4");
                                e.getEntity().addScoreboardTag("Hunger:5");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:3")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                e.getEntity().removeScoreboardTag("Hunger:3");
                                e.getEntity().addScoreboardTag("Hunger:4");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:2")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                e.getEntity().removeScoreboardTag("Hunger:2");
                                e.getEntity().addScoreboardTag("Hunger:3");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:1")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                e.getEntity().removeScoreboardTag("Hunger:1");
                                e.getEntity().addScoreboardTag("Hunger:2");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:0")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                e.getEntity().removeScoreboardTag("Hunger:0");
                                e.getEntity().addScoreboardTag("Hunger:1");
                                if (e.getEntity().getScoreboardTags().contains("DayH-1")) {
                                    e.getEntity().removeScoreboardTag("DayH-1");
                                    return;
                                }
                                if (e.getEntity().getScoreboardTags().contains("DayH-2")) {
                                    e.getEntity().removeScoreboardTag("DayH-2");
                                    return;
                                }
                                if (e.getEntity().getScoreboardTags().contains("DayH-3")) {
                                    e.getEntity().removeScoreboardTag("DayH-3");
                                    return;

                                } else {
                                    return;
                                }
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
                                } else {
                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have the training to do this.");
                                }
                            }

                        } else if (player.getItemInHand().getType() == Material.EMERALD) {
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
                    } else if (e.getEntity().getScoreboardTags().contains("Member:" + uuid)) {
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
                        } else if (player.getItemInHand().getType() == Material.CLAY_BALL) {
                            if (e.getEntity().getScoreboardTags().contains("Colic")) {
                                ItemStack heldItem = player.getItemInHand();
                                e.getEntity().removeScoreboardTag("Colic");
                                if (heldItem.getType() == Material.CLAY_BALL) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                                e.getEntity().removeScoreboardTag("Colic");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
                            }
                        } else if (player.getItemInHand().getType() == Material.GOLD_NUGGET) {
                            if (e.getEntity().getScoreboardTags().contains("Flu")) {
                                ItemStack heldItem = player.getItemInHand();
                                e.getEntity().removeScoreboardTag("Flu");
                                if (heldItem.getType() == Material.GOLD_NUGGET) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                                e.getEntity().removeScoreboardTag("Flu");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
                            }
                        } else if (player.getItemInHand().getType() == Material.PRISMARINE_CRYSTALS) {
                            if (e.getEntity().getScoreboardTags().contains("Strangles")) {
                                ItemStack heldItem = player.getItemInHand();
                                e.getEntity().removeScoreboardTag("Strangles");
                                if (heldItem.getType() == Material.PRISMARINE_CRYSTALS) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                                e.getEntity().removeScoreboardTag("Strangles");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
                            }
                        } else if (player.getItemInHand().getType() == Material.NETHER_WART) {
                            if (e.getEntity().getScoreboardTags().contains("West Nile Virus")) {
                                ItemStack heldItem = player.getItemInHand();
                                e.getEntity().removeScoreboardTag("West Nile Virus");
                                if (heldItem.getType() == Material.NETHER_WART) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                                e.getEntity().removeScoreboardTag("West Nile Virus");
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
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
                        } else if (player.getItemInHand().getType() == Material.SPIDER_EYE) {
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
                            if (player.isSneaking()) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have given this horse some pats!");
                                World world = e.getEntity().getWorld();
                                Location loc = e.getEntity().getLocation();
                                int x = (int) loc.getX();
                                int y = (int) loc.getY();
                                int z = (int) loc.getZ();
                                int y1 = y + 1;
                                world.spawnParticle(Particle.HEART, x, y1, z, 5);
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
                    } else if (player.getItemInHand().getType() == Material.CLAY_BALL) {
                        if (e.getEntity().getScoreboardTags().contains("Colic")) {
                            ItemStack heldItem = player.getItemInHand();
                            e.getEntity().removeScoreboardTag("Colic");
                            if (heldItem.getType() == Material.CLAY_BALL) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                player.updateInventory();
                            }
                            e.getEntity().removeScoreboardTag("Colic");
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
                        }
                    } else if (player.getItemInHand().getType() == Material.GOLD_NUGGET) {
                        if (e.getEntity().getScoreboardTags().contains("Flu")) {
                            ItemStack heldItem = player.getItemInHand();
                            e.getEntity().removeScoreboardTag("Flu");
                            if (heldItem.getType() == Material.GOLD_NUGGET) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                player.updateInventory();
                            }
                            e.getEntity().removeScoreboardTag("Flu");
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
                        }
                    } else if (player.getItemInHand().getType() == Material.PRISMARINE_CRYSTALS) {
                        if (e.getEntity().getScoreboardTags().contains("Strangles")) {
                            ItemStack heldItem = player.getItemInHand();
                            e.getEntity().removeScoreboardTag("Strangles");
                            if (heldItem.getType() == Material.PRISMARINE_CRYSTALS) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                player.updateInventory();
                            }
                            e.getEntity().removeScoreboardTag("Strangles");
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
                        }
                    } else if (player.getItemInHand().getType() == Material.NETHER_WART) {
                        if (e.getEntity().getScoreboardTags().contains("West Nile Virus")) {
                            ItemStack heldItem = player.getItemInHand();
                            e.getEntity().removeScoreboardTag("Strangles");
                            if (heldItem.getType() == Material.PRISMARINE_CRYSTALS) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                player.updateInventory();
                            }
                            e.getEntity().removeScoreboardTag("West Nile Virus");
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now cured!");
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
                    } else if (player.getItemInHand().getType().equals(Material.WHEAT)) {
                        if (e.getEntity().getScoreboardTags().contains("Hunger:9")) {
                            player.getInventory().remove(Material.WHEAT);
                            e.getEntity().removeScoreboardTag("Hunger:9");
                            e.getEntity().addScoreboardTag("Hunger:10");
                            e.getEntity().removeScoreboardTag("Hunger");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:8")) {
                            player.getInventory().remove(Material.WHEAT);
                            e.getEntity().removeScoreboardTag("Hunger:8");
                            e.getEntity().addScoreboardTag("Hunger:9");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:7")) {
                            player.getInventory().remove(Material.WHEAT);
                            e.getEntity().removeScoreboardTag("Hunger:7");
                            e.getEntity().addScoreboardTag("Hunger:8");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:6")) {
                            player.getInventory().remove(Material.WHEAT);
                            e.getEntity().removeScoreboardTag("Hunger:6");
                            e.getEntity().addScoreboardTag("Hunger:7");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:5")) {
                            player.getInventory().remove(Material.WHEAT);
                            e.getEntity().removeScoreboardTag("Hunger:5");
                            e.getEntity().addScoreboardTag("Hunger:6");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:4")) {
                            player.getInventory().remove(Material.WHEAT);
                            e.getEntity().removeScoreboardTag("Hunger:4");
                            e.getEntity().addScoreboardTag("Hunger:5");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:3")) {
                            player.getInventory().remove(Material.WHEAT);
                            e.getEntity().removeScoreboardTag("Hunger:3");
                            e.getEntity().addScoreboardTag("Hunger:4");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:2")) {
                            player.getInventory().remove(Material.WHEAT);
                            e.getEntity().removeScoreboardTag("Hunger:2");
                            e.getEntity().addScoreboardTag("Hunger:3");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:1")) {
                            player.getInventory().remove(Material.WHEAT);
                            e.getEntity().removeScoreboardTag("Hunger:1");
                            e.getEntity().addScoreboardTag("Hunger:2");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:0")) {
                            player.getInventory().remove(Material.WHEAT);
                            e.getEntity().removeScoreboardTag("Hunger:0");
                            e.getEntity().addScoreboardTag("Hunger:1");
                            if (e.getEntity().getScoreboardTags().contains("DayH-1")) {
                                e.getEntity().removeScoreboardTag("DayH-1");
                                return;
                            }
                            if (e.getEntity().getScoreboardTags().contains("DayH-2")) {
                                e.getEntity().removeScoreboardTag("DayH-2");
                                return;
                            }
                            if (e.getEntity().getScoreboardTags().contains("DayH-3")) {
                                e.getEntity().removeScoreboardTag("DayH-3");
                                return;

                            } else {
                                return;
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
                    }
                    if (player.getItemInHand().getType() == Material.SPIDER_EYE) {
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
                        if (player.isSneaking()) {
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have given this horse some pats!");
                            World world = e.getEntity().getWorld();
                            Location loc = e.getEntity().getLocation();
                            int x = (int) loc.getX();
                            int y = (int) loc.getY();
                            int z = (int) loc.getZ();
                            int y1 = y + 1;
                            world.spawnParticle(Particle.HEART, x, y1, z, 5);
                        } else {
                            collection.put(player.getUniqueId(), e.getEntity().getUniqueId());
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have selected this horse!");
                        }
                    }
                } else if (!e.getEntity().getScoreboardTags().contains("Public")) {
                    if (!e.getEntity().getScoreboardTags().contains("Private")) {
                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "This is not your horse!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent unload) {
        Chunk chunk = unload.getChunk();
        for(World world : Bukkit.getServer().getWorlds()) {
            for (Entity e : world.getEntities()) {
                if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                    Chunk chunk1 = e.getChunk();
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    int x = chunk1.getX();
                    int z = chunk1.getZ();
                    world.loadChunk(x, z);
                    world.setChunkForceLoaded(x, z, true);
                }
            }
        }
    }

    @EventHandler
    public void AllDamage(EntityDamageEvent d) {
        if (d.getEntityType() == EntityType.HORSE || d.getEntityType() == EntityType.DONKEY || d.getEntityType() == EntityType.MULE) {
            d.setCancelled(true);
        }
    }

    @EventHandler
    public void onDissconnect(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        p.eject();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Entity h = e.getPlayer();
        if (h.getType() == EntityType.PLAYER) {
            if (h.getVehicle() == null) {
                return;
            }
            if (h.getVehicle().getType() == EntityType.HORSE || h.getVehicle().getType() == EntityType.DONKEY || h.getVehicle().getType() == EntityType.MULE) {
                int lx = (int) h.getLocation().getX();
                int ly = (int) h.getLocation().getY();
                int lz = (int) h.getLocation().getZ();
                int lz1 = (int) (lz + 1);
                int lx1 = (int) (lx - 1);
                int ly1 = (int) (ly - 1);
                World world = h.getWorld();
                Block b = world.getBlockAt(lx, ly1, lz);
                Location loc = h.getLocation();

                if (b.getType() == Material.BLUE_STAINED_GLASS_PANE) {
                    int radius = 5;
                    for (int x = -radius; x <= radius; x++) {
                        for (int z = -radius; z <= radius; z++) {
                            Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() - 1, loc.getBlockZ() + z);
                            Block block2 = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() - 2, loc.getBlockZ() + z);
                            if (block2.getType() != Material.SAND) {
                                if (block2.getType() != Material.GRASS_BLOCK) {
                                    if (block2.getType() != Material.DIRT) {
                                        if (block2.getType() != Material.COARSE_DIRT) {
                                            if (block.getType() == Material.BLUE_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.BLUE_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.GREEN_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.GREEN_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.MAGENTA_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.MAGENTA_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.ORANGE_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.ORANGE_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.BROWN_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.BROWN_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (b.getType() == Material.BROWN_STAINED_GLASS_PANE) {
                    int radius = 5;
                    for (int x = -radius; x <= radius; x++) {
                        for (int z = -radius; z <= radius; z++) {
                            Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() - 1, loc.getBlockZ() + z);
                            Block block2 = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() - 2, loc.getBlockZ() + z);
                            if (block2.getType() != Material.SAND) {
                                if (block2.getType() != Material.GRASS_BLOCK) {
                                    if (block2.getType() != Material.DIRT) {
                                        if (block2.getType() != Material.COARSE_DIRT) {
                                            if (block.getType() == Material.BLUE_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.BLUE_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.GREEN_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.GREEN_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.MAGENTA_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.MAGENTA_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.ORANGE_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.ORANGE_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.BROWN_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.BROWN_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (b.getType() == Material.MAGENTA_STAINED_GLASS_PANE) {
                    int radius = 5;
                    for (int x = -radius; x <= radius; x++) {
                        for (int z = -radius; z <= radius; z++) {
                            Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() - 1, loc.getBlockZ() + z);
                            Block block2 = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() - 2, loc.getBlockZ() + z);
                            if (block2.getType() != Material.SAND) {
                                if (block2.getType() != Material.GRASS_BLOCK) {
                                    if (block2.getType() != Material.DIRT) {
                                        if (block2.getType() != Material.COARSE_DIRT) {
                                            if (block.getType() == Material.BLUE_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.BLUE_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.GREEN_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.GREEN_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.MAGENTA_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.MAGENTA_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.ORANGE_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.ORANGE_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.BROWN_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.BROWN_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (b.getType() == Material.ORANGE_STAINED_GLASS_PANE) {
                    int radius = 5;
                    for (int x = -radius; x <= radius; x++) {
                        for (int z = -radius; z <= radius; z++) {
                            Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() - 1, loc.getBlockZ() + z);
                            Block block2 = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() - 2, loc.getBlockZ() + z);
                            if (block2.getType() != Material.SAND) {
                                if (block2.getType() != Material.GRASS_BLOCK) {
                                    if (block2.getType() != Material.DIRT) {
                                        if (block2.getType() != Material.COARSE_DIRT) {
                                            if (block.getType() == Material.BLUE_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.BLUE_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.GREEN_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.GREEN_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.MAGENTA_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.MAGENTA_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.ORANGE_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.ORANGE_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.BROWN_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.BROWN_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (b.getType() == Material.GREEN_STAINED_GLASS_PANE) {
                    int radius = 5;
                    for (int x = -radius; x <= radius; x++) {
                        for (int z = -radius; z <= radius; z++) {
                            Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() - 1, loc.getBlockZ() + z);
                            Block block2 = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() - 2, loc.getBlockZ() + z);
                            if (block2.getType() != Material.SAND) {
                                if (block2.getType() != Material.GRASS_BLOCK) {
                                    if (block2.getType() != Material.DIRT) {
                                        if (block2.getType() != Material.COARSE_DIRT) {
                                            if (block.getType() == Material.BLUE_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.BLUE_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.GREEN_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.GREEN_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.MAGENTA_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.MAGENTA_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.ORANGE_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.ORANGE_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                            if (block.getType() == Material.BROWN_STAINED_GLASS_PANE) {
                                                block.setType(Material.AIR);
                                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                    block.setType(Material.BROWN_STAINED_GLASS_PANE);
                                                }, 100);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
