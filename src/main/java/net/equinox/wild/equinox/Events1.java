package net.equinox.wild.equinox;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import net.equinox.wild.equinox.entities.DbHorse;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.event.world.EntitiesUnloadEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;

import static net.equinox.wild.equinox.Commands.doublexp;
import static net.equinox.wild.equinox.Commands.lungestat;

@SuppressWarnings("all")
// Storage Of Selected Horse
public class Events1 implements Listener {
    public static HashMap<UUID, UUID> collection = new HashMap<UUID, UUID>();
    public static HashMap<UUID, Float> playerxp = new HashMap<UUID, Float>();
    private final Equinox plugin;

    public Events1(Equinox plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onClickBrewingSlot(InventoryClickEvent event) {
        if (event.getInventory() instanceof BrewerInventory && event.getView().getTopInventory().equals(event.getClickedInventory())) {
            BrewerInventory inventory = (BrewerInventory) event.getInventory();
            if (event.getSlot() >= 0 && event.getSlot() <= 2)
                if (event.getCurrentItem().getType().isAir() && event.getCursor() != null && Seed.MATERIALS.contains(event.getCursor().getType())) {
                    Player player = (Player) event.getWhoClicked();
                    ItemStack droppedItem = event.getCursor().clone();
                    droppedItem.setAmount(1);
                    event.getCursor().setAmount(event.getCursor().getAmount() - 1);
                    inventory.setItem(event.getSlot(), droppedItem);
                    event.setCursor(event.getCursor());
                    event.setCancelled(true);
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        Location loc = event.getInventory().getLocation();
                        double x = loc.getX();
                        double y = loc.getY();
                        double z = loc.getZ();
                        for (Entity e : loc.getNearbyLivingEntities(4)) {
                            if (e.getType() == EntityType.HORSE) {
                                ((Horse) e).getPathfinder().findPath(loc);
                                System.out.println("Found");
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    NBTEditor.set(e, (byte) 1, "EatingHaystack");
                                    player.playSound(loc, "entity.horse.eat", 2, 1F);
                                    if (event.getInventory() instanceof BrewerInventory) {
                                        if (event.getInventory().contains(Material.WHEAT_SEEDS)) {
                                            event.getInventory().remove(Material.WHEAT_SEEDS);
                                            plugin.onConsumeFood(e, Material.WHEAT_SEEDS);
                                            System.out.println("Ate");
                                        }
                                        if (event.getInventory().contains(Material.BEETROOT_SEEDS)) {
                                            event.getInventory().remove(Material.BEETROOT_SEEDS);
                                            plugin.onConsumeFood(e, Material.BEETROOT_SEEDS);
                                            System.out.println("Ate");
                                        }
                                        if (event.getInventory().contains(Material.PUMPKIN_SEEDS)) {
                                            event.getInventory().remove(Material.PUMPKIN_SEEDS);
                                            plugin.onConsumeFood(e, Material.PUMPKIN_SEEDS);
                                            System.out.println("Ate");
                                        }
                                        if (event.getInventory().contains(Material.MELON_SEEDS)) {
                                            event.getInventory().remove(Material.MELON_SEEDS);
                                            plugin.onConsumeFood(e, Material.MELON_SEEDS);
                                            System.out.println("Ate");
                                        }
                                    }
                                }, 100);
                            }
                        }

                    }, 2);
                }
        }
    }
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (e.getPlayer().isInsideVehicle()) {
            Entity h = e.getPlayer().getVehicle();
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Player p = e.getPlayer();
                if (h instanceof Horse) {
                    System.out.println("Test2");
                    h.teleport(p);
                    h.addPassenger(p);

                }
            }, 2);
        }
    }

    @EventHandler
    public void onHorseRegen(EntityRegainHealthEvent e) {
        Entity h = e.getEntity();
        if (h instanceof Horse) {
            e.setCancelled(true);
        }
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
        if (h instanceof Horse) {
            Horse horse = (Horse) e.getRightClicked();
            double jump = horse.getJumpStrength();
            double jump2 = jump + 0.06;
            horse.setJumpStrength(jump2);
            if (p.getItemInHand().getType() == Material.GOLDEN_APPLE) {
                e.setCancelled(true);
            }
        }
        if (h.getScoreboardTags().contains("Private")) {
            if (!p.hasPermission("eq.staff")) {
                if (!h.getScoreboardTags().contains("Owner:" + uuid)) {
                    if (!h.getScoreboardTags().contains("Member:" + uuid)) {
                        if (!h.getScoreboardTags().contains("Leaser:" + uuid)) {
                            h.removePassenger(p);
                            p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have permission to ride this horse!");
                            e.setCancelled(true);
                            return;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBreeding(PlayerLeashEntityEvent e) {
        Entity h = e.getEntity();
        Player p = (Player) e.getLeashHolder();
        Location loc = h.getLocation();

        if (h.getType() == EntityType.HORSE) {
            if(h.getScoreboardTags().contains("Trait:Stubborn")) {
                Random rnd = new Random();
                int i = rnd.nextInt(100);
                if (i <= 10) {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Your stubborn horse has stood still!");
                        h.teleport(loc);
                        return;
                    }, 50);
                }
            }
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
                                                    System.out.println("Breed" + brds);
                                                    break;
                                                }
                                            }
                                            if (i > 49) {
                                                if (h2.getScoreboardTags().contains("Breed:" + brds)) {
                                                    h.addScoreboardTag("fb:" + brds);
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
                                                System.out.println("Color" + clrs);
                                                break;
                                            }
                                        }
                                        if (i > 49) {
                                            if (h2.getScoreboardTags().contains("Color:" + clrs)) {
                                                h.addScoreboardTag("fc:" + clrs);
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
                                                System.out.println("Style" + styl);
                                                break;
                                            }
                                        }
                                        if (i > 49) {
                                            if (h2.getScoreboardTags().contains("Style:" + styl)) {
                                                h.addScoreboardTag("fs:" + styl);
                                                System.out.println("Style" + styl);
                                                break;
                                            }

                                        }
                                    }
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 49) {
                                        h.addScoreboardTag("fg:Filly");
                                    }
                                    if (i > 49) {
                                        h.addScoreboardTag("fg:Colt");
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
                                                        h.addScoreboardTag("fspd:" + spfs);
                                                        break;
                                                    } else if (spm < spst) {
                                                        Random random = new Random();
                                                        int spfs = rnd.nextInt(spst);
                                                        h.addScoreboardTag("fspd:" + spfs);
                                                        break;
                                                    } else if (spm == spst) {
                                                        int spfs = spm;
                                                        h.addScoreboardTag("fspd:" + spfs);
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
                                                        System.out.println(jds);
                                                        break;
                                                    } else if (jm > jst) {
                                                        Random random = new Random();
                                                        int jds = rnd.nextInt(jm);
                                                        h.addScoreboardTag("fj:" + jds);
                                                        System.out.println(jds);
                                                        break;
                                                    } else if (jm == jst) {
                                                        Random random = new Random();
                                                        int jds = jm;
                                                        h.addScoreboardTag("fj:" + jds);
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
    public void onUnleashEvent(EntityUnleashEvent e) {
        if(plugin.lungeStatus.containsKey(e.getEntity().getUniqueId())) {
            plugin.lungeStatus.put(e.getEntity().getUniqueId(), false);
        }

    }
    @EventHandler
    public void onDismount(EntityDismountEvent e) {
        Entity player = e.getEntity();
        Entity e2 = e.getDismounted();
        if (e2 instanceof Horse) {
            Horse h = (Horse) e.getDismounted();
            double jump = h.getJumpStrength();
            h.setJumpStrength(jump - 0.06);
            if (h.getScoreboardTags().contains("Speed:T1")) {
                if (h.getScoreboardTags().contains("Walk")) {
                    return;
                }
                if (h.getScoreboardTags().contains("Trot")) {
                    double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                    double newSpeed = baseSpeed - .010;
                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                    h.removeScoreboardTag("Trot");
                    h.addScoreboardTag("Walk");
                    return;
                }
                if (h.getScoreboardTags().contains("Canter")) {
                    double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                    double newSpeed = baseSpeed - .020;
                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                    h.removeScoreboardTag("Canter");
                    h.addScoreboardTag("Walk");
                    return;
                }
                if (h.getScoreboardTags().contains("Gallop")) {
                    double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                    double newSpeed = baseSpeed - .030;
                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                    h.removeScoreboardTag("Gallop");
                    h.addScoreboardTag("Walk");
                    return;
                }

            } else {
                if (h.getScoreboardTags().contains("Walk")) {
                    return;
                }
                if (h.getScoreboardTags().contains("Trot")) {
                    double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                    double newSpeed = baseSpeed - .050;
                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                    h.removeScoreboardTag("Trot");
                    h.addScoreboardTag("Walk");
                    return;
                }
                if (h.getScoreboardTags().contains("Canter")) {
                    double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                    double newSpeed = baseSpeed - .100;
                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                    h.removeScoreboardTag("Canter");
                    h.addScoreboardTag("Walk");
                    return;
                }
                if (h.getScoreboardTags().contains("Gallop")) {
                    double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                    double newSpeed = baseSpeed - .150;
                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                    h.removeScoreboardTag("Gallop");
                    h.addScoreboardTag("Walk");
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPoopPunch(PlayerInteractEvent e) {
        Entity p = e.getPlayer();
        Player player = (Player) p;
        String name = player.getName();
        Block block = e.getClickedBlock();
        if(block == null) {
            if (player.isInsideVehicle()) {
                Horse h = (Horse) player.getVehicle();
                if (h.getScoreboardTags().contains("Speed:T1")) {
                    if (e.getAction().isLeftClick()) {
                        if (player.getItemInHand().getType() == Material.TOTEM_OF_UNDYING) {
                            if (!h.getScoreboardTags().contains("Walk")) {
                                if (!h.getScoreboardTags().contains("Trot")) {
                                    if (!h.getScoreboardTags().contains("Canter")) {
                                        if (!h.getScoreboardTags().contains("Gallop")) {
                                            double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                                            double newSpeed = baseSpeed - .030;
                                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                                            h.addScoreboardTag("Walk");
                                            player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Walk");
                                            return;
                                        }
                                    }
                                }
                            }
                            if (h.getScoreboardTags().contains("Walk")) {
                                double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                                double newSpeed = baseSpeed + .010;
                                h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                                h.removeScoreboardTag("Walk");
                                h.addScoreboardTag("Trot");
                                player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Trot");
                                return;
                            }
                            if (h.getScoreboardTags().contains("Trot")) {
                                double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                                double newSpeed = baseSpeed + .010;
                                h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                                h.removeScoreboardTag("Trot");
                                h.addScoreboardTag("Canter");
                                player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Canter");
                                return;
                            }
                            if (h.getScoreboardTags().contains("Canter")) {
                                double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                                double newSpeed = baseSpeed + .010;
                                h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                                h.removeScoreboardTag("Canter");
                                h.addScoreboardTag("Gallop");
                                player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Gallop");
                                return;
                            }
                            if (h.getScoreboardTags().contains("Gallop")) {
                                return;
                            }

                        }
                    }
                    if (e.getAction().isRightClick()) {
                        if (player.getItemInHand().getType() == Material.TOTEM_OF_UNDYING) {
                            if (h.getScoreboardTags().contains("Walk")) {
                                return;
                            }
                            if (h.getScoreboardTags().contains("Trot")) {
                                double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                                double newSpeed = baseSpeed - .010;
                                h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                                h.removeScoreboardTag("Trot");
                                h.addScoreboardTag("Walk");
                                player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Walk");
                                return;
                            }
                            if (h.getScoreboardTags().contains("Canter")) {
                                double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                                double newSpeed = baseSpeed - .010;
                                h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                                h.removeScoreboardTag("Canter");
                                h.addScoreboardTag("Trot");
                                player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Trot");
                                return;
                            }
                            if (h.getScoreboardTags().contains("Gallop")) {
                                double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                                double newSpeed = baseSpeed - .010;
                                h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                                h.removeScoreboardTag("Gallop");
                                h.addScoreboardTag("Canter");
                                player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Canter");
                                return;
                            }
                        }
                    }
                } else {
                    if (e.getAction().isLeftClick()) {
                        if (player.getItemInHand().getType() == Material.TOTEM_OF_UNDYING) {
                            if (!h.getScoreboardTags().contains("Walk")) {
                                if (!h.getScoreboardTags().contains("Trot")) {
                                    if (!h.getScoreboardTags().contains("Canter")) {
                                        if (!h.getScoreboardTags().contains("Gallop")) {
                                            double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                                            double newSpeed = baseSpeed - .150;
                                            h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                                            h.addScoreboardTag("Walk");
                                            player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Walk");
                                            return;
                                        }
                                    }
                                }
                            }
                            if (h.getScoreboardTags().contains("Walk")) {
                                double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                                double newSpeed = baseSpeed + .050;
                                h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                                h.removeScoreboardTag("Walk");
                                h.addScoreboardTag("Trot");
                                player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Trot");
                                return;
                            }
                            if (h.getScoreboardTags().contains("Trot")) {
                                double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                                double newSpeed = baseSpeed + .050;
                                h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                                h.removeScoreboardTag("Trot");
                                h.addScoreboardTag("Canter");
                                player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Canter");
                                return;
                            }
                            if (h.getScoreboardTags().contains("Canter")) {
                                double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                                double newSpeed = baseSpeed + .050;
                                h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                                h.removeScoreboardTag("Canter");
                                h.addScoreboardTag("Gallop");
                                player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Gallop");
                                return;
                            }
                            if (h.getScoreboardTags().contains("Gallop")) {
                                return;
                            }

                        }
                    }
                    if (e.getAction().isRightClick()) {
                        if (player.getItemInHand().getType() == Material.TOTEM_OF_UNDYING) {
                            if (h.getScoreboardTags().contains("Walk")) {
                                return;
                            }
                            if (h.getScoreboardTags().contains("Trot")) {
                                double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                                double newSpeed = baseSpeed - .050;
                                h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                                h.removeScoreboardTag("Trot");
                                h.addScoreboardTag("Walk");
                                player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Walk");
                                return;
                            }
                            if (h.getScoreboardTags().contains("Canter")) {
                                double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                                double newSpeed = baseSpeed - .050;
                                h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                                h.removeScoreboardTag("Canter");
                                h.addScoreboardTag("Trot");
                                player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Trot");
                                return;
                            }
                            if (h.getScoreboardTags().contains("Gallop")) {
                                double baseSpeed = h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                                double newSpeed = baseSpeed - .050;
                                h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(newSpeed);
                                h.removeScoreboardTag("Gallop");
                                h.addScoreboardTag("Canter");
                                player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Canter");
                                return;
                            }
                        }

                    }
                }
            }
        }

        else if (block.getType() == Material.PLAYER_HEAD && Utilities.isSkullPoop(block)) {
            Location locate = block.getLocation();
            int x = locate.getBlockX();
            int y = locate.getBlockY();
            int z = locate.getBlockZ();
            int y1 = y + 1;
            Location loc = locate.set(x, y1, z);
            Hologram hologram = HologramsAPI.createHologram(plugin, loc);
            player.playSound(loc, "entity.slime.jump", 1, .5F);
            if(doublexp.get("dxp") != null && doublexp.get("dxp").equalsIgnoreCase("true")) {
                player.giveExp(2);
                hologram.appendTextLine(ChatColor.YELLOW + "+2 xp");
            } else {
                player.giveExp(1);
                hologram.appendTextLine(ChatColor.YELLOW + "+1 xp");
            }

            block.setType(Material.AIR);
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    hologram.delete();
                }
            }, 100);

        }

    }
    @EventHandler
    public void whenPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        e.setDroppedExp(0);
    }

    @EventHandler
    public void whenPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        player.setExp(playerxp.get(uuid));
    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent e){
        String p = e.getPlayer().getName();
        if (e.getPlayer().hasPermission("is.newbie")) {
            if (!e.getPlayer().hasPermission("is.ranked")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rankup " + p);
            }
        }
    }

    @EventHandler
    public void onJump(HorseJumpEvent e) {
        Entity h = e.getEntity();
        Player player = (Player) e.getEntity().getPassenger();
        Horse horse = (Horse) e.getEntity();
        Location loc = player.getLocation();
        if(h.getScoreboardTags().contains("Trait:Stubborn")) {
            Random rnd = new Random();
            int i = rnd.nextInt(100);
            if (i <= 10) {
                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Your stubborn horse has refused to jump!");
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        horse.addPassenger(player);
                    }
                }, 3);
            } else if (i <= 5) {
                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Your stubborn horse has thrown you!");
                player.teleport(loc);
            }
        } if(h.getScoreboardTags().contains("Trait:Skittish")) {
            Random rnd = new Random();
            int i = rnd.nextInt(100);
             if (i <= 10) {
                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "Your skittish horse has spooked at the jump!");
                player.teleport(loc);
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
                } else if (e.getEntity().getScoreboardTags().contains("Private")) {
                    //I hate that I had to do this for each permission for the horse please shoot me if I have to edit this...
                    //Well I had to edit it...
                    //Oh god not again...
                    // Rabies, Tetnus, WestNile, Strangles & Flu
                    if (e.getEntity().getScoreboardTags().contains("Owner:" + uuid) || e.getEntity().getScoreboardTags().contains("Leaser:" + uuid)) {
                        if (player.getItemInHand().getType() == Material.WHEAT) {
                            Random rnd2 = new Random();
                            int i2 = rnd2.nextInt(100);
                            if (i2 <= 3) {
                                if (e.getEntity().getScoreboardTags().contains("Malnurished")) {
                                    e.getEntity().removeScoreboardTag("Malnurished");
                                }
                            }
                            ItemStack heldItem = player.getItemInHand();
                            if (e.getEntity().getScoreboardTags().contains("Hunger:9")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:9");
                                e.getEntity().addScoreboardTag("Hunger:10");
                                e.getEntity().removeScoreboardTag("Hunger");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:8")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:8");
                                e.getEntity().addScoreboardTag("Hunger:9");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:7")) {
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:7");
                                e.getEntity().addScoreboardTag("Hunger:8");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:6")) {
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:6");
                                e.getEntity().addScoreboardTag("Hunger:7");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:5")) {
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:5");
                                e.getEntity().addScoreboardTag("Hunger:6");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:4")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                    }
                                }
                                e.getEntity().removeScoreboardTag("Hunger:4");
                                e.getEntity().addScoreboardTag("Hunger:5");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:3")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                    }
                                }
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:3");
                                e.getEntity().addScoreboardTag("Hunger:4");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:2")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:2");
                                e.getEntity().addScoreboardTag("Hunger:3");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:1")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:1");
                                e.getEntity().addScoreboardTag("Hunger:2");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:0")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                player.updateInventory();
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
                        } else if (player.getItemInHand().getType() == Material.MAGMA_CREAM) {
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have used flyspray on your horse!");
                            player.playSound(ploc, Sound.ENTITY_ENDER_DRAGON_FLAP, 4, 1F);
                            Location loc = e.getEntity().getLocation();
                            int x = (int) loc.getX();
                            int y = (int) loc.getY();
                            int z = (int) loc.getZ();
                            int y1 = y + 1;
                            player.spawnParticle(Particle.WATER_DROP, x, y1, z, 10);
                            ItemStack heldItem = player.getItemInHand();
                            heldItem.setAmount(heldItem.getAmount() - 1);
                            player.updateInventory();
                        } else if (player.getItemInHand().getType() == Material.PHANTOM_MEMBRANE) {
                            if (!e.getEntity().getScoreboardTags().contains("ccomb")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have curried your horse!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("ccomb");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                            if (e.getEntity().getScoreboardTags().contains("comb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (player.getItemInHand().getType() == Material.SLIME_BALL) {
                            if (!e.getEntity().getScoreboardTags().contains("comb")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have combed your horses mane & tail!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("comb");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                            if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (player.getItemInHand().getType() == Material.EMERALD) {
                            if (!e.getEntity().getScoreboardTags().contains("hbrush")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have brushed this horse with a hard brush!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("hbrush");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (e.getEntity().getScoreboardTags().contains("comb")) {
                                            if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                }
                                            }
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
                                        if (e.getEntity().getScoreboardTags().contains("comb")) {
                                            if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                }
                                            }
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
                                        if (e.getEntity().getScoreboardTags().contains("comb")) {
                                            if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                }
                                            }
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
                        if (player.getItemInHand().getType() == Material.WHEAT) {
                            ItemStack heldItem = player.getItemInHand();
                            if (e.getEntity().getScoreboardTags().contains("Hunger:9")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:9");
                                e.getEntity().addScoreboardTag("Hunger:10");
                                e.getEntity().removeScoreboardTag("Hunger");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:8")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:8");
                                e.getEntity().addScoreboardTag("Hunger:9");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:7")) {
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:7");
                                e.getEntity().addScoreboardTag("Hunger:8");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:6")) {
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:6");
                                e.getEntity().addScoreboardTag("Hunger:7");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:5")) {
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:5");
                                e.getEntity().addScoreboardTag("Hunger:6");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:4")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                    }
                                }
                                e.getEntity().removeScoreboardTag("Hunger:4");
                                e.getEntity().addScoreboardTag("Hunger:5");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:3")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                    }
                                }
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:3");
                                e.getEntity().addScoreboardTag("Hunger:4");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:2")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:2");
                                e.getEntity().addScoreboardTag("Hunger:3");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:1")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                player.updateInventory();
                                e.getEntity().removeScoreboardTag("Hunger:1");
                                e.getEntity().addScoreboardTag("Hunger:2");
                                return;
                            } else if (e.getEntity().getScoreboardTags().contains("Hunger:0")) {
                                heldItem.setAmount(heldItem.getAmount() - 1);
                                if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                    Random rnd = new Random();
                                    int i = rnd.nextInt(100);
                                    if (i <= 15) {
                                        heldItem.setAmount(heldItem.getAmount() - 1);
                                        player.updateInventory();
                                    }
                                }
                                player.updateInventory();
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

                        } else if (player.getItemInHand().getType() == Material.MAGMA_CREAM) {
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have used flyspray on your horse!");
                            player.playSound(ploc, Sound.ENTITY_ENDER_DRAGON_FLAP, 4, 1F);
                            Location loc = e.getEntity().getLocation();
                            int x = (int) loc.getX();
                            int y = (int) loc.getY();
                            int z = (int) loc.getZ();
                            int y1 = y + 1;
                            player.spawnParticle(Particle.WATER_DROP, x, y1, z, 10);
                            ItemStack heldItem = player.getItemInHand();
                            heldItem.setAmount(heldItem.getAmount() - 1);
                            player.updateInventory();
                        } else if (player.getItemInHand().getType() == Material.PHANTOM_MEMBRANE) {
                            if (!e.getEntity().getScoreboardTags().contains("ccomb")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have curried your horse!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("ccomb");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                            if (e.getEntity().getScoreboardTags().contains("comb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (player.getItemInHand().getType() == Material.SLIME_BALL) {
                            if (!e.getEntity().getScoreboardTags().contains("comb")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have combed your horses mane & tail!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("comb");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                            if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (player.getItemInHand().getType() == Material.EMERALD) {
                            if (!e.getEntity().getScoreboardTags().contains("hbrush")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have brushed this horse with a hard brush!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("hbrush");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (e.getEntity().getScoreboardTags().contains("comb")) {
                                            if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                }
                                            }
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
                                        if (e.getEntity().getScoreboardTags().contains("comb")) {
                                            if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                }
                                            }
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
                                        if (e.getEntity().getScoreboardTags().contains("comb")) {
                                            if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            collection.put(player.getUniqueId(), e.getEntity().getUniqueId());
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have selected this horse!");
                        }
                    } else if (e.getEntity().getScoreboardTags().contains("Member:" + uuid)) {
                        if (player.getItemInHand().getType() == Material.MAGMA_CREAM) {
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have used flyspray on your horse!");
                            player.playSound(ploc, Sound.ENTITY_ENDER_DRAGON_FLAP, 4, 1F);
                            Location loc = e.getEntity().getLocation();
                            int x = (int) loc.getX();
                            int y = (int) loc.getY();
                            int z = (int) loc.getZ();
                            int y1 = y + 1;
                            player.spawnParticle(Particle.WATER_DROP, x, y1, z, 10);
                            ItemStack heldItem = player.getItemInHand();
                            heldItem.setAmount(heldItem.getAmount() - 1);
                            player.updateInventory();
                        } else if (player.getItemInHand().getType() == Material.PHANTOM_MEMBRANE) {
                            if (!e.getEntity().getScoreboardTags().contains("ccomb")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have curried your horse!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("ccomb");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                            if (e.getEntity().getScoreboardTags().contains("comb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (player.getItemInHand().getType() == Material.SLIME_BALL) {
                            if (!e.getEntity().getScoreboardTags().contains("comb")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have combed your horses mane & tail!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("comb");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                            if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (player.getItemInHand().getType() == Material.EMERALD) {
                            if (!e.getEntity().getScoreboardTags().contains("hbrush")) {
                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have brushed this horse with a hard brush!");
                                player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                                e.getEntity().addScoreboardTag("hbrush");
                                if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                    if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                        if (e.getEntity().getScoreboardTags().contains("comb")) {
                                            if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                }
                                            }
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
                                        if (e.getEntity().getScoreboardTags().contains("comb")) {
                                            if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                }
                                            }
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
                                        if (e.getEntity().getScoreboardTags().contains("comb")) {
                                            if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                                if (doublexp.get("dxp") == "true") {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                    player.giveExp(25);
                                                    e.getEntity().addScoreboardTag("Cleaned");
                                                } else {
                                                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                    player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                    player.giveExp(15);
                                                }
                                            }
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
                    }
                }
                lungestat.replace(uuid, false);
                if (e.getEntity().getScoreboardTags().contains("Public")) {
                    if (player.getItemInHand().getType() == Material.WHEAT) {
                        ItemStack heldItem = player.getItemInHand();
                        if (e.getEntity().getScoreboardTags().contains("Hunger:9")) {
                            heldItem.setAmount(heldItem.getAmount() - 1);
                            if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                Random rnd = new Random();
                                int i = rnd.nextInt(100);
                                if (i <= 15) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                            }
                            player.updateInventory();
                            e.getEntity().removeScoreboardTag("Hunger:9");
                            e.getEntity().addScoreboardTag("Hunger:10");
                            e.getEntity().removeScoreboardTag("Hunger");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:8")) {
                            heldItem.setAmount(heldItem.getAmount() - 1);
                            if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                Random rnd = new Random();
                                int i = rnd.nextInt(100);
                                if (i <= 15) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                            }
                            player.updateInventory();
                            e.getEntity().removeScoreboardTag("Hunger:8");
                            e.getEntity().addScoreboardTag("Hunger:9");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:7")) {
                            if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                Random rnd = new Random();
                                int i = rnd.nextInt(100);
                                if (i <= 15) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                            }
                            heldItem.setAmount(heldItem.getAmount() - 1);
                            player.updateInventory();
                            e.getEntity().removeScoreboardTag("Hunger:7");
                            e.getEntity().addScoreboardTag("Hunger:8");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:6")) {
                            if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                Random rnd = new Random();
                                int i = rnd.nextInt(100);
                                if (i <= 15) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                            }
                            heldItem.setAmount(heldItem.getAmount() - 1);
                            player.updateInventory();
                            e.getEntity().removeScoreboardTag("Hunger:6");
                            e.getEntity().addScoreboardTag("Hunger:7");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:5")) {
                            if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                Random rnd = new Random();
                                int i = rnd.nextInt(100);
                                if (i <= 15) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                            }
                            heldItem.setAmount(heldItem.getAmount() - 1);
                            player.updateInventory();
                            e.getEntity().removeScoreboardTag("Hunger:5");
                            e.getEntity().addScoreboardTag("Hunger:6");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:4")) {
                            heldItem.setAmount(heldItem.getAmount() - 1);
                            if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                Random rnd = new Random();
                                int i = rnd.nextInt(100);
                                if (i <= 15) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                }
                            }
                            e.getEntity().removeScoreboardTag("Hunger:4");
                            e.getEntity().addScoreboardTag("Hunger:5");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:3")) {
                            heldItem.setAmount(heldItem.getAmount() - 1);
                            if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                Random rnd = new Random();
                                int i = rnd.nextInt(100);
                                if (i <= 15) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                }
                            }
                            player.updateInventory();
                            e.getEntity().removeScoreboardTag("Hunger:3");
                            e.getEntity().addScoreboardTag("Hunger:4");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:2")) {
                            heldItem.setAmount(heldItem.getAmount() - 1);
                            if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                Random rnd = new Random();
                                int i = rnd.nextInt(100);
                                if (i <= 15) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                            }
                            player.updateInventory();
                            e.getEntity().removeScoreboardTag("Hunger:2");
                            e.getEntity().addScoreboardTag("Hunger:3");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:1")) {
                            heldItem.setAmount(heldItem.getAmount() - 1);
                            if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                Random rnd = new Random();
                                int i = rnd.nextInt(100);
                                if (i <= 15) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                            }
                            player.updateInventory();
                            e.getEntity().removeScoreboardTag("Hunger:1");
                            e.getEntity().addScoreboardTag("Hunger:2");
                            return;
                        } else if (e.getEntity().getScoreboardTags().contains("Hunger:0")) {
                            heldItem.setAmount(heldItem.getAmount() - 1);
                            if (e.getEntity().getScoreboardTags().contains("Trait:Glutton")) {
                                Random rnd = new Random();
                                int i = rnd.nextInt(100);
                                if (i <= 15) {
                                    heldItem.setAmount(heldItem.getAmount() - 1);
                                    player.updateInventory();
                                }
                            }
                            player.updateInventory();
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
                    } else if (player.getItemInHand().getType() == Material.MAGMA_CREAM) {
                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have used flyspray on your horse!");
                        player.playSound(ploc, Sound.ENTITY_ENDER_DRAGON_FLAP, 4, 1F);
                        Location loc = e.getEntity().getLocation();
                        int x = (int) loc.getX();
                        int y = (int) loc.getY();
                        int z = (int) loc.getZ();
                        int y1 = y + 1;
                        player.spawnParticle(Particle.WATER_DROP, x, y1, z, 10);
                        ItemStack heldItem = player.getItemInHand();
                        heldItem.setAmount(heldItem.getAmount() - 1);
                        player.updateInventory();
                    } else if (player.getItemInHand().getType() == Material.PHANTOM_MEMBRANE) {
                        if (!e.getEntity().getScoreboardTags().contains("ccomb")) {
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have combed your horses mane & tail!");
                            player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                            e.getEntity().addScoreboardTag("ccomb");
                            if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                    if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                        if (e.getEntity().getScoreboardTags().contains("comb")) {
                                            if (doublexp.get("dxp") == "true") {
                                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                player.giveExp(25);
                                                e.getEntity().addScoreboardTag("Cleaned");
                                            } else {
                                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                player.giveExp(15);
                                                e.getEntity().addScoreboardTag("Cleaned");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (player.getItemInHand().getType() == Material.SLIME_BALL) {
                        if (!e.getEntity().getScoreboardTags().contains("comb")) {
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have curried this horse!");
                            player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                            e.getEntity().addScoreboardTag("comb");
                            if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                    if (e.getEntity().getScoreboardTags().contains("hbrush")) {
                                        if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                            if (doublexp.get("dxp") == "true") {
                                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                player.giveExp(25);
                                                e.getEntity().addScoreboardTag("Cleaned");
                                            } else {
                                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                player.giveExp(15);
                                                e.getEntity().addScoreboardTag("Cleaned");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (player.getItemInHand().getType() == Material.EMERALD) {
                        if (!e.getEntity().getScoreboardTags().contains("hbrush")) {
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have brushed this horse with a hard brush!");
                            player.playSound(ploc, Sound.BLOCK_GRASS_STEP, 4, 1.5F);
                            e.getEntity().addScoreboardTag("hbrush");
                            if (e.getEntity().getScoreboardTags().contains("sbrush")) {
                                if (e.getEntity().getScoreboardTags().contains("hpick")) {
                                    if (e.getEntity().getScoreboardTags().contains("comb")) {
                                        if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                            if (doublexp.get("dxp") == "true") {
                                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                player.giveExp(25);
                                                e.getEntity().addScoreboardTag("Cleaned");
                                            } else {
                                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                player.giveExp(15);
                                                e.getEntity().addScoreboardTag("Cleaned");
                                            }
                                        }
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
                                    if (e.getEntity().getScoreboardTags().contains("comb")) {
                                        if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                            if (doublexp.get("dxp") == "true") {
                                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                player.giveExp(25);
                                                e.getEntity().addScoreboardTag("Cleaned");
                                            } else {
                                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                player.giveExp(15);
                                                e.getEntity().addScoreboardTag("Cleaned");
                                            }
                                        }
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
                                    if (e.getEntity().getScoreboardTags().contains("comb")) {
                                        if (e.getEntity().getScoreboardTags().contains("ccomb")) {
                                            if (doublexp.get("dxp") == "true") {
                                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                player.sendActionBar(ChatColor.YELLOW + "+25 XP");
                                                player.giveExp(25);
                                                e.getEntity().addScoreboardTag("Cleaned");
                                            } else {
                                                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "This horse is now clean!");
                                                player.sendActionBar(ChatColor.YELLOW + "+15 XP");
                                                player.giveExp(15);
                                            }
                                        }
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
                } else if (!e.getEntity().getScoreboardTags().contains("Public")) {
                    if (!e.getEntity().getScoreboardTags().contains("Private")) {
                        if (player.hasPermission("eq.dev")) { // MARK
                            collection.put(player.getUniqueId(), e.getEntity().getUniqueId());
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have selected this horse!");
                            try {
                                DbHorse horse = plugin.getDbContext().getHorseFromDatabase(e.getEntity().getUniqueId());

                                if (e.getEntity().getScoreboardTags().contains("Owner:" + uuid.toString())) {
                                    if (horse.getOwnerUuid().equalsIgnoreCase("EMPTY")) {
                                        System.out.println("This horse does not have an owner set... Correcting!");
                                        horse.setOwnerUuid(uuid.toString());
                                        plugin.getDbContext().updateHorseInDatabase(horse);
                                    }
                                }

                            } catch (NoSuchElementException exception) {
                                // Send message to player saying the horse with the specified id was not found
                                System.out.println("ERR");
                                exception.printStackTrace();
                            }
                        } else {
                            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "This is not your horse!");
                        }
                    }
                }
            }
        }
    }


//    @EventHandler
//    public void onChunkUnload(ChunkUnloadEvent unload) {
//
//        Chunk chunk = unload.getChunk();
//        for (Entity e : chunk.getEntities()) {
//            if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
//                Chunk sourceChunk = e.getChunk();
//                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
//                int x = sourceChunk.getX();
//                int z = sourceChunk.getZ();
//
//
////                    sourceChunk.getWorld().loadChunk(x, z);
//                /**
//                new BukkitRunnable() {
//                    public void run() {
////                            Chunk chunk2 = e.getChunk();
////                            if (sourceChunk.equals(chunk2)) {
////                            } else {
//////                                sourceChunk.unload();
////                            }
//
//
//                    }
//                }.runTaskTimer(plugin, 500, 500); **/
//
////                    e.getWorld().setChunkForceLoaded(x, z, true);
//
//                DbHorse horse = plugin.getDbContext().getHorseFromDatabase(e.getUniqueId());
//
//                if(horse != null) {
//                    Location loc = e.getLocation();
//                    horse.setLastWorld(e.getWorld().getName());
//                    horse.setLastChunkX(sourceChunk.getX());
//                    horse.setLastChunkZ(sourceChunk.getZ());
//                    plugin.getDbContext().updateHorseInDatabase(horse);
//                    System.out.printf("(Chunk Unload) Updated horse #%s%n", horse.getId());
//                }
//            }
//        }
//
//    }


    @EventHandler
    public void onEntitiesLoad(EntitiesLoadEvent event) {
        for(Entity e : event.getEntities()) {
            if(e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                DbHorse horse = plugin.getDbContext().getHorseFromDatabase(e.getUniqueId());
                if(horse == null) {
                    ((LivingEntity) e).setHealth(0);
                }
            }
        }
    }

    @EventHandler
    public void onEntitiesUnload(EntitiesUnloadEvent event) {

        for(Entity e : event.getEntities()) {
            if(e instanceof Donkey || e instanceof Horse || e instanceof Mule) {
                Chunk sourceChunk = e.getChunk();
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                int x = sourceChunk.getX();
                int z = sourceChunk.getZ();
                sourceChunk.getWorld().loadChunk(x, z);
                e.getWorld().setChunkForceLoaded(x, z, true);

                try {
                    DbHorse horse = plugin.getDbContext().getHorseFromDatabase(e.getUniqueId());

                    if (horse != null) {
                        Location loc = e.getLocation();
                        horse.setLastWorld(e.getWorld().getName());
                        horse.setLastChunkX(sourceChunk.getX());
                        horse.setLastChunkZ(sourceChunk.getZ());

                        plugin.getDbContext().updateHorseInDatabase(horse);

                    }
                } catch(NoSuchElementException exception) {
                    // No
                }

            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity e = event.getEntity();
        if(e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
            DbHorse horse = plugin.getDbContext().getHorseFromDatabase(e.getUniqueId());
            if(horse != null) {
                plugin.getDbContext().deleteHorseFromDatabase(horse);
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
        Player player = e.getPlayer();
        if (player.isInsideVehicle()) {
            player.getVehicle().eject();
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Entity h = e.getPlayer();
        Player p = e.getPlayer();
        if (h.getType() == EntityType.PLAYER) {
            if (h.getVehicle() == null) {
                return;
            }
            if (h.getVehicle().getType() == EntityType.HORSE || h.getVehicle().getType() == EntityType.DONKEY || h.getVehicle().getType() == EntityType.MULE) {
                World world = h.getWorld();
                Location loc = h.getLocation();
                if (h.getVehicle().getLocation().getBlock().getRelative(BlockFace.NORTH_EAST).getType() == Material.BROWN_STAINED_GLASS) {
                    h.getVehicle().getLocation().getBlock().getRelative(BlockFace.NORTH_EAST).setType(Material.AIR);
                    Location locb = h.getVehicle().getLocation().getBlock().getRelative(BlockFace.NORTH_EAST).getLocation();
                    p.playSound(loc, Sound.BLOCK_BARREL_CLOSE, 2, .5F);
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        locb.getBlock().setType(Material.BROWN_STAINED_GLASS);
                    }, 100);
                }
                if (h.getVehicle().getLocation().getBlock().getRelative(BlockFace.SOUTH_EAST).getType() == Material.BROWN_STAINED_GLASS) {
                    h.getVehicle().getLocation().getBlock().getRelative(BlockFace.SOUTH_EAST).setType(Material.AIR);
                    Location locb = h.getVehicle().getLocation().getBlock().getRelative(BlockFace.SOUTH_EAST).getLocation();
                    p.playSound(loc, Sound.BLOCK_BARREL_CLOSE, 2, .5F);
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        locb.getBlock().setType(Material.BROWN_STAINED_GLASS);
                    }, 100);
                }
                if (h.getVehicle().getLocation().getBlock().getRelative(BlockFace.NORTH_WEST).getType() == Material.BROWN_STAINED_GLASS) {
                    h.getVehicle().getLocation().getBlock().getRelative(BlockFace.NORTH_WEST).setType(Material.AIR);
                    Location locb = h.getVehicle().getLocation().getBlock().getRelative(BlockFace.NORTH_WEST).getLocation();
                    p.playSound(loc, Sound.BLOCK_BARREL_CLOSE, 2, .5F);
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        locb.getBlock().setType(Material.BROWN_STAINED_GLASS);
                    }, 100);
                }
                if (h.getVehicle().getLocation().getBlock().getRelative(BlockFace.SOUTH_WEST).getType() == Material.BROWN_STAINED_GLASS) {
                    h.getVehicle().getLocation().getBlock().getRelative(BlockFace.SOUTH_WEST).setType(Material.AIR);
                    Location locb = h.getVehicle().getLocation().getBlock().getRelative(BlockFace.SOUTH_WEST).getLocation();
                    p.playSound(loc, Sound.BLOCK_BARREL_CLOSE, 2, .5F);
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        locb.getBlock().setType(Material.BROWN_STAINED_GLASS);
                    }, 100);
                }

                if (h.getVehicle().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.BLUE_STAINED_GLASS_PANE) {
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
                } else if (h.getVehicle().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.BROWN_STAINED_GLASS_PANE) {
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
                } else if (h.getVehicle().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.MAGENTA_STAINED_GLASS_PANE) {
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
                } else if (h.getVehicle().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.ORANGE_STAINED_GLASS_PANE) {
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
                } else if (h.getVehicle().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.GREEN_STAINED_GLASS_PANE) {
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

