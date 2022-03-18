package net.equinox.wild.equinox;

import dev.dbassett.skullcreator.SkullCreator;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import net.equinox.wild.equinox.entities.DbHorse;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.data.Levelled;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

///@SuppressWarnings("all")
@SuppressWarnings("deprecation")
public final class Equinox extends JavaPlugin {
    private static Economy econ = null;
    private static final Permission perms = null;
    private static final Chat chat = null;
    private FileConfiguration breedsConfig;
    private FileConfiguration traitConfig;
    private FileConfiguration coatConfig;
    private FileConfiguration homesConfig;

    private EntityManager entityManager;

    private HashMap<Material, Integer> foodToHungerValues = new HashMap<Material, Integer>();

    @Override
    public void onEnable() {
        babyLoop(this);
        PeeLoop(this);
        drinkLoop(this);
        drinkLoop2(this);
        poop(this);
        foodToHungerValues.put(Material.GRASS_BLOCK, 1);
        foodToHungerValues.put(Material.DRIED_KELP_BLOCK, 10);
        foodToHungerValues.put(Material.HAY_BLOCK, 10);
//        eatLoop(this, Material.RED_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE);
        eatGrainLoop(this);
        eatLoop(this, Material.GRASS_BLOCK, Material.DIRT);
        eatLoop(this, Material.DRIED_KELP_BLOCK, Material.AIR);
        eatLoop(this, Material.HAY_BLOCK, Material.AIR);
        horseRiding(this);
        hungerLoop(this);
        thirstLoop(this);
        createCustomConfig();
        boolean useHolographicDisplays = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
        getServer().getPluginManager().registerEvents(new Events1(this), this);
        getServer().getPluginManager().registerEvents(new HorseGUI(this), this);
        this.getCommand("eq").setExecutor(new Commands(this));
        this.getCommand("eqa").setExecutor(new Commands(this));
        this.getCommand("cpu").setExecutor(new Commands(this));
        this.getCommand("rankup").setExecutor(new Commands(this));
        this.getCommand("access").setExecutor(new Commands(this));
        this.getCommand("star").setExecutor(new Commands(this));
        this.getCommand("diamond").setExecutor(new Commands(this));
        this.getCommand("heart").setExecutor(new Commands(this));
        this.getCommand("flower").setExecutor(new Commands(this));
        this.saveDefaultConfig();
        getLogger().info(ChatColor.GREEN + "Plugin Has Been Enabled!");
        if (!setupEconomy()) {
            System.out.println("No Economy Plugin Found! Disabling Vault...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
        }

        setupDatabaseConnection();
    }

    private void setupDatabaseConnection() {
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        EntityManagerFactory emt = Persistence.createEntityManagerFactory("equinox");
        entityManager = emt.createEntityManager();
    }

    //Loop Hunger
    public void hungerLoop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                for(World world : getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        Random rnd = new Random();
                        int i = rnd.nextInt(100);
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            if (!e.getScoreboardTags().contains("Invulnerable")) {
                                if (i <= 10) {
                                    if (e.getScoreboardTags().contains("Hunger:10")) {
                                        e.removeScoreboardTag("Hunger:10");
                                        e.addScoreboardTag("Hunger:9");
                                        if (!e.getScoreboardTags().contains("Hunger")) {
                                            e.addScoreboardTag("Hunger");
                                        }
                                    } else if (e.getScoreboardTags().contains("Hunger:9")) {
                                        e.removeScoreboardTag("Hunger:9");
                                        e.addScoreboardTag("Hunger:8");
                                        if (!e.getScoreboardTags().contains("Hunger")) {
                                            e.addScoreboardTag("Hunger");
                                        }
                                    } else if (e.getScoreboardTags().contains("Hunger:8")) {
                                        e.removeScoreboardTag("Hunger:8");
                                        e.addScoreboardTag("Hunger:7");
                                        if (!e.getScoreboardTags().contains("Hunger")) {
                                            e.addScoreboardTag("Hunger");
                                        }
                                    } else if (e.getScoreboardTags().contains("Hunger:7")) {
                                        e.removeScoreboardTag("Hunger:7");
                                        e.addScoreboardTag("Hunger:6");
                                        if (!e.getScoreboardTags().contains("Hunger")) {
                                            e.addScoreboardTag("Hunger");
                                        }
                                    } else if (e.getScoreboardTags().contains("Hunger:6")) {
                                        e.removeScoreboardTag("Hunger:6");
                                        e.addScoreboardTag("Hunger:5");
                                        if (!e.getScoreboardTags().contains("Hunger")) {
                                            e.addScoreboardTag("Hunger");
                                        }
                                    } else if (e.getScoreboardTags().contains("Hunger:5")) {
                                        e.removeScoreboardTag("Hunger:5");
                                        e.addScoreboardTag("Hunger:4");
                                        if (!e.getScoreboardTags().contains("Hunger")) {
                                            e.addScoreboardTag("Hunger");
                                        }
                                    } else if (e.getScoreboardTags().contains("Hunger:4")) {
                                        e.removeScoreboardTag("Hunger:4");
                                        e.addScoreboardTag("Hunger:3");
                                        if (!e.getScoreboardTags().contains("Hunger")) {
                                            e.addScoreboardTag("Hunger");
                                        }
                                    } else if (e.getScoreboardTags().contains("Hunger:3")) {
                                        e.removeScoreboardTag("Hunger:3");
                                        e.addScoreboardTag("Hunger:2");
                                        if (!e.getScoreboardTags().contains("Hunger")) {
                                            e.addScoreboardTag("Hunger");
                                        }
                                    } else if (e.getScoreboardTags().contains("Hunger:2")) {
                                        e.removeScoreboardTag("Hunger:2");
                                        e.addScoreboardTag("Hunger:1");
                                        if (!e.getScoreboardTags().contains("Hunger")) {
                                            e.addScoreboardTag("Hunger");
                                        }
                                    } else if (e.getScoreboardTags().contains("Hunger:1")) {
                                        e.removeScoreboardTag("Hunger:1");
                                        e.addScoreboardTag("Hunger:0");
                                        if (!e.getScoreboardTags().contains("Hunger")) {
                                            e.addScoreboardTag("Hunger");
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 20000, 20000); // TODO original 20000
    }
    public void babyLoop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                for(World world : getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse) {
                            if (e.getScoreboardTags().contains("Age:0")) {
                                ((Horse) e).setAge(-25000);
                            }if (e.getScoreboardTags().contains("Age:1")) {
                                ((Horse) e).setAge(-25000);
                            }
                        } if (e instanceof Donkey) {
                            if (e.getScoreboardTags().contains("Age:0")) {
                                ((Donkey) e).setAge(-25000);
                            }if (e.getScoreboardTags().contains("Age:1")) {
                                ((Donkey) e).setAge(-25000);
                            }
                        } if (e instanceof Mule) {
                            if (e.getScoreboardTags().contains("Age:0")) {
                                ((Mule) e).setAge(-25000);
                            }if (e.getScoreboardTags().contains("Age:1")) {
                                ((Mule) e).setAge(-25000);
                            }
                        }
                    }
                }

            }
        }.runTaskTimer(plugin, 10000, 10000);
    }

    public DatabaseUtilities getDbContext() {
        return new DatabaseUtilities(entityManager);
    }

    //Loop Thirst
    public void thirstLoop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                for(World world : getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        Random rnd = new Random();
                        int i = rnd.nextInt(100);
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            if (!e.getScoreboardTags().contains("Invulnerable")) {
                                if (i <= 35) {
                                    if (e.getScoreboardTags().contains("Thirst:10")) {
                                        e.removeScoreboardTag("Thirst:10");
                                        e.addScoreboardTag("Thirst:9");
                                        if (!e.getScoreboardTags().contains("Thirst")) {
                                            e.addScoreboardTag("Thirst");
                                        }
                                    } else if (e.getScoreboardTags().contains("Thirst:9")) {
                                        e.removeScoreboardTag("Thirst:9");
                                        e.addScoreboardTag("Thirst:8");
                                        if (!e.getScoreboardTags().contains("Thirst")) {
                                            e.addScoreboardTag("Thirst");
                                        }
                                    } else if (e.getScoreboardTags().contains("Thirst:8")) {
                                        e.removeScoreboardTag("Thirst:8");
                                        e.addScoreboardTag("Thirst:7");
                                        if (!e.getScoreboardTags().contains("Thirst")) {
                                            e.addScoreboardTag("Thirst");
                                        }
                                    } else if (e.getScoreboardTags().contains("Thirst:7")) {
                                        e.removeScoreboardTag("Thirst:7");
                                        e.addScoreboardTag("Thirst:6");
                                        if (!e.getScoreboardTags().contains("Thirst")) {
                                            e.addScoreboardTag("Thirst");
                                        }
                                    } else if (e.getScoreboardTags().contains("Thirst:6")) {
                                        e.removeScoreboardTag("Thirst:6");
                                        e.addScoreboardTag("Thirst:5");
                                        if (!e.getScoreboardTags().contains("Thirst")) {
                                            e.addScoreboardTag("Thirst");
                                        }
                                    } else if (e.getScoreboardTags().contains("Thirst:5")) {
                                        e.removeScoreboardTag("Thirst:5");
                                        e.addScoreboardTag("Thirst:4");
                                        if (!e.getScoreboardTags().contains("Thirst")) {
                                            e.addScoreboardTag("Thirst");
                                        }
                                    } else if (e.getScoreboardTags().contains("Thirst:4")) {
                                        e.removeScoreboardTag("Thirst:4");
                                        e.addScoreboardTag("Thirst:3");
                                        if (!e.getScoreboardTags().contains("Thirst")) {
                                            e.addScoreboardTag("Thirst");
                                        }
                                    } else if (e.getScoreboardTags().contains("Thirst:3")) {
                                        e.removeScoreboardTag("Thirst:3");
                                        e.addScoreboardTag("Thirst:2");
                                        if (!e.getScoreboardTags().contains("Thirst")) {
                                            e.addScoreboardTag("Thirst");
                                        }
                                    } else if (e.getScoreboardTags().contains("Thirst:2")) {
                                        e.removeScoreboardTag("Thirst:2");
                                        e.addScoreboardTag("Thirst:1");
                                        if (!e.getScoreboardTags().contains("Thirst")) {
                                            e.addScoreboardTag("Thirst");
                                        }
                                    } else if (e.getScoreboardTags().contains("Thirst:1")) {
                                        e.removeScoreboardTag("Thirst:1");
                                        e.addScoreboardTag("Thirst:0");
                                        if (!e.getScoreboardTags().contains("Thirst")) {
                                            e.addScoreboardTag("Thirst");
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

            }
        }.runTaskTimer(plugin, 10000, 10000);
    }

    public void drinkLoop2(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {

                for(World world : getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            Location loc = e.getLocation();
                            int radius = 4;
                            if (e.getScoreboardTags().contains("Thirst")) {
                                for (int x = -radius; x <= radius; x++) {
                                    for (int y = -radius; y <= radius; y++) {
                                        for (int z = -radius; z <= radius; z++) {
                                            Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                                            Location loc2 = block.getLocation();
                                            Material bt = block.getType();

                                            if (bt == Material.WATER_CAULDRON) {
                                                Levelled c = (Levelled) block.getBlockData();
                                                if (c.getLevel() == 3) {
                                                    ((Horse) e).getPathfinder().findPath(loc2);
                                                    ((Donkey) e).getPathfinder().findPath(loc2);
                                                    ((Mule) e).getPathfinder().findPath(loc2);
                                                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                        NBTEditor.set(e, (byte) 1, "EatingHaystack");
                                                        if (e.getScoreboardTags().contains("Thirst:9")) {
                                                            e.removeScoreboardTag("Thirst:9");
                                                            e.addScoreboardTag("Thirst:10");
                                                            e.removeScoreboardTag("Thirst");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(2);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:8")) {
                                                            e.removeScoreboardTag("Thirst:8");
                                                            e.addScoreboardTag("Thirst:10");
                                                            e.removeScoreboardTag("Thirst");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(2);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:7")) {
                                                            e.removeScoreboardTag("Thirst:7");
                                                            e.addScoreboardTag("Thirst:10");
                                                            e.removeScoreboardTag("Thirst");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(2);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:6")) {
                                                            e.removeScoreboardTag("Thirst:6");
                                                            e.addScoreboardTag("Thirst:9");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(2);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:5")) {
                                                            e.removeScoreboardTag("Thirst:5");
                                                            e.addScoreboardTag("Thirst:8");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(2);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:4")) {
                                                            e.removeScoreboardTag("Thirst:4");
                                                            e.addScoreboardTag("Thirst:7");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(2);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:3")) {
                                                            e.removeScoreboardTag("Thirst:3");
                                                            e.addScoreboardTag("Thirst:6");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(2);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:2")) {
                                                            e.removeScoreboardTag("Thirst:2");
                                                            e.addScoreboardTag("Thirst:5");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(2);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:1")) {
                                                            e.removeScoreboardTag("Thirst:1");
                                                            e.addScoreboardTag("Thirst:4");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(2);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:0")) {
                                                            e.removeScoreboardTag("Thirst:0");
                                                            e.addScoreboardTag("Thirst:3");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(2);
                                                                block.setBlockData(c);
                                                            }
                                                            if (e.getScoreboardTags().contains("DayT-1")) {
                                                                e.removeScoreboardTag("DayT-1");
                                                                return;
                                                            }
                                                            if (e.getScoreboardTags().contains("DayT-2")) {
                                                                e.removeScoreboardTag("DayT-2");
                                                                return;
                                                            }
                                                            if (e.getScoreboardTags().contains("DayT-3")) {
                                                                e.removeScoreboardTag("DayT-3");
                                                            }
                                                        }
                                                    }, 100);
                                                } else if (c.getLevel() == 2) {
                                                    ((Horse) e).getPathfinder().findPath(loc2);
                                                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                        if (e.getScoreboardTags().contains("Thirst:9")) {
                                                            e.removeScoreboardTag("Thirst:9");
                                                            e.addScoreboardTag("Thirst:10");
                                                            e.removeScoreboardTag("Thirst");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(1);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:8")) {
                                                            e.removeScoreboardTag("Thirst:8");
                                                            e.addScoreboardTag("Thirst:10");
                                                            e.removeScoreboardTag("Thirst");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(1);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:7")) {
                                                            e.removeScoreboardTag("Thirst:7");
                                                            e.addScoreboardTag("Thirst:10");
                                                            e.removeScoreboardTag("Thirst");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(1);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:6")) {
                                                            e.removeScoreboardTag("Thirst:6");
                                                            e.addScoreboardTag("Thirst:9");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(1);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:5")) {
                                                            e.removeScoreboardTag("Thirst:5");
                                                            e.addScoreboardTag("Thirst:8");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(1);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:4")) {
                                                            e.removeScoreboardTag("Thirst:4");
                                                            e.addScoreboardTag("Thirst:7");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(1);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:3")) {
                                                            e.removeScoreboardTag("Thirst:3");
                                                            e.addScoreboardTag("Thirst:6");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(1);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:2")) {
                                                            e.removeScoreboardTag("Thirst:2");
                                                            e.addScoreboardTag("Thirst:5");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(1);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:1")) {
                                                            e.removeScoreboardTag("Thirst:1");
                                                            e.addScoreboardTag("Thirst:4");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(1);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:0")) {
                                                            e.removeScoreboardTag("Thirst:0");
                                                            e.addScoreboardTag("Thirst:3");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                c.setLevel(1);
                                                                block.setBlockData(c);
                                                            }
                                                            if (e.getScoreboardTags().contains("DayT-1")) {
                                                                e.removeScoreboardTag("DayT-1");
                                                                return;
                                                            }
                                                            if (e.getScoreboardTags().contains("DayT-2")) {
                                                                e.removeScoreboardTag("DayT-2");
                                                                return;
                                                            }
                                                            if (e.getScoreboardTags().contains("DayT-3")) {
                                                                e.removeScoreboardTag("DayT-3");
                                                            }
                                                        }
                                                    }, 100);
                                                } else if (c.getLevel() == 1) {
                                                    ((Horse) e).getPathfinder().findPath(loc2);
                                                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                        if (e.getScoreboardTags().contains("Thirst:9")) {
                                                            e.removeScoreboardTag("Thirst:9");
                                                            e.addScoreboardTag("Thirst:10");
                                                            e.removeScoreboardTag("Thirst");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                block.setType(Material.CAULDRON);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:8")) {
                                                            e.removeScoreboardTag("Thirst:8");
                                                            e.addScoreboardTag("Thirst:10");
                                                            e.removeScoreboardTag("Thirst");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                block.setType(Material.CAULDRON);

                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:7")) {
                                                            e.removeScoreboardTag("Thirst:7");
                                                            e.addScoreboardTag("Thirst:10");
                                                            e.removeScoreboardTag("Thirst");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                block.setType(Material.CAULDRON);

                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:6")) {
                                                            e.removeScoreboardTag("Thirst:6");
                                                            e.addScoreboardTag("Thirst:9");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                block.setType(Material.CAULDRON);

                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:5")) {
                                                            e.removeScoreboardTag("Thirst:5");
                                                            e.addScoreboardTag("Thirst:8");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                block.setType(Material.CAULDRON);

                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:4")) {
                                                            e.removeScoreboardTag("Thirst:4");
                                                            e.addScoreboardTag("Thirst:7");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                block.setType(Material.CAULDRON);

                                                            }
                                                        } else if (e.getScoreboardTags().contains("Hunger:3")) {
                                                            e.removeScoreboardTag("Thirst:3");
                                                            e.addScoreboardTag("Thirst:6");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                block.setType(Material.CAULDRON);

                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:2")) {
                                                            e.removeScoreboardTag("Thirst:2");
                                                            e.addScoreboardTag("Thirst:5");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                block.setType(Material.CAULDRON);

                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:1")) {
                                                            e.removeScoreboardTag("Thirst:1");
                                                            e.addScoreboardTag("Thirst:4");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                block.setType(Material.CAULDRON);

                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:0")) {
                                                            e.removeScoreboardTag("Thirst:0");
                                                            e.addScoreboardTag("Thirst:3");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 25) {
                                                                block.setType(Material.CAULDRON);

                                                            }
                                                            if (e.getScoreboardTags().contains("DayT-1")) {
                                                                e.removeScoreboardTag("DayT-1");
                                                                return;
                                                            }
                                                            if (e.getScoreboardTags().contains("DayT-2")) {
                                                                e.removeScoreboardTag("DayT-2");
                                                                return;
                                                            }
                                                            if (e.getScoreboardTags().contains("DayT-3")) {
                                                                e.removeScoreboardTag("DayT-3");
                                                            }
                                                        }
                                                    }, 100);
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 600);
    }

    public void drinkLoop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                for(World world : getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            Location loc = e.getLocation();
                            int radius = 2;
                            if (e.getScoreboardTags().contains("Thirst")) {
                                int x = -radius, y = -radius, z = -radius;
                                Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                                Location loc2 = block.getLocation();
                                Material bt = block.getType();
                                if (bt == Material.WATER) {
                                    ((Horse) e).getPathfinder().findPath(loc2);
                                    ((Donkey) e).getPathfinder().findPath(loc2);
                                    ((Mule) e).getPathfinder().findPath(loc2);
                                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                        NBTEditor.set(e, (byte) 1, "EatingHaystack");
                                        block.setType(Material.AIR);
                                        if (e.getScoreboardTags().contains("Thirst:9")) {
                                            e.removeScoreboardTag("Thirst:9");
                                            e.addScoreboardTag("Thirst:10");
                                            e.removeScoreboardTag("Thirst");
                                        } else if (e.getScoreboardTags().contains("Thirst:8")) {
                                            e.removeScoreboardTag("Thirst:8");
                                            e.addScoreboardTag("Thirst:10");
                                            e.removeScoreboardTag("Thirst");
                                        } else if (e.getScoreboardTags().contains("Thirst:7")) {
                                            e.removeScoreboardTag("Thirst:7");
                                            e.addScoreboardTag("Thirst:10");
                                            e.removeScoreboardTag("Thirst");
                                        } else if (e.getScoreboardTags().contains("Thirst:6")) {
                                            e.removeScoreboardTag("Thirst:6");
                                            e.addScoreboardTag("Thirst:9");
                                        } else if (e.getScoreboardTags().contains("Thirst:5")) {
                                            e.removeScoreboardTag("Thirst:5");
                                            e.addScoreboardTag("Thirst:8");
                                        } else if (e.getScoreboardTags().contains("Thirst:4")) {
                                            e.removeScoreboardTag("Thirst:4");
                                            e.addScoreboardTag("Thirst:7");
                                        } else if (e.getScoreboardTags().contains("Hunger:3")) {
                                            e.removeScoreboardTag("Thirst:3");
                                            e.addScoreboardTag("Thirst:6");
                                        } else if (e.getScoreboardTags().contains("Thirst:2")) {
                                            e.removeScoreboardTag("Thirst:2");
                                            e.addScoreboardTag("Thirst:5");
                                        } else if (e.getScoreboardTags().contains("Thirst:1")) {
                                            e.removeScoreboardTag("Thirst:1");
                                            e.addScoreboardTag("Thirst:4");
                                        } else if (e.getScoreboardTags().contains("Thirst:0")) {
                                            e.removeScoreboardTag("Thirst:0");
                                            e.addScoreboardTag("Thirst:3");
                                            if (e.getScoreboardTags().contains("DayT-1")) {
                                                e.removeScoreboardTag("DayT-1");
                                                return;
                                            }
                                            if (e.getScoreboardTags().contains("DayT-2")) {
                                                e.removeScoreboardTag("DayT-2");
                                                return;
                                            }
                                            if (e.getScoreboardTags().contains("DayT-3")) {
                                                e.removeScoreboardTag("DayT-3");
                                            }
                                        }
                                    }, 100);
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 600);
    }

    private void eatGrainLoop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                AtomicBoolean hasEaten = new AtomicBoolean(false);
                for (World world : getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            if (e.getScoreboardTags().contains("Hunger")) {
                                Block brewingStand = Utilities.findAbsoluteBlockWithinLocation(e.getLocation(), List.of(Material.BREWING_STAND), 25);
                                if(brewingStand != null) {
                                    ((Horse) e).getPathfinder().findPath(brewingStand.getLocation());
                                    ((Donkey) e).getPathfinder().findPath(brewingStand.getLocation());
                                    ((Mule) e).getPathfinder().findPath(brewingStand.getLocation());
                                    if (!hasEaten.get()) {
                                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                            NBTEditor.set(e, (byte) 1, "EatingHaystack");
                                            e.getWorld().playSound(e, Sound.ENTITY_HORSE_EAT, 3, 1);
//                                            onConsumeFood(e, original);
                                            onEatGrain(e, (BrewingStand) brewingStand.getState());
                                            hasEaten.set(true);
                                        }, 100);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 100, 100);
    }

    private void eatLoop(Plugin plugin, Material original, Material replacement) {
        new BukkitRunnable() {
            public void run() {
                AtomicBoolean hasEaten = new AtomicBoolean(false);
                for(World world : getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            Location loc = e.getLocation();
                            int radius = 2;
                            if (e.getScoreboardTags().contains("Hunger")) {
                                Location foodSource = Utilities.findTypeOfBlockWithinLocation(loc, List.of(original), radius);
                                if(foodSource != null) {
                                    ((Horse) e).getPathfinder().findPath(foodSource);
                                    if(!hasEaten.get()) {
                                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                            NBTEditor.set(e, (byte) 1, "EatingHaystack");
                                            e.getWorld().getBlockAt(foodSource).setType(replacement);
                                            onConsumeFood(e, original);
                                            hasEaten.set(true);
                                        }, 100);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 500, 500);
    }


    public void PeeLoop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                for(World world : getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            Random rnd = new Random();
                            int i = rnd.nextInt(100);
                            if (i <= 25) {
                                Location loc = e.getLocation();
                                int x = loc.getBlockX();
                                int y = loc.getBlockY();
                                int z = loc.getBlockZ();
                                int y1 = y - 1;
                                Block b = world.getBlockAt(x, y1, z);
                                world.playSound(loc, Sound.ENTITY_GHAST_DEATH, 1, 1);
                                if (b.getType() == Material.YELLOW_GLAZED_TERRACOTTA) {
                                    b.setType(Material.BROWN_GLAZED_TERRACOTTA);
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 100, 9000);
    }

    public void poop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                for(World world : getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            Random rnd = new Random();
                            int i = rnd.nextInt(100);
                            if (i <= 10) {
                                Block loc = e.getLocation().getBlock();
                                Material block = loc.getType();
                                String s = "https://textures.minecraft.net/texture/9b3b1f785f01753c45ef97fcffffb3f52658ffceb17ad3f7b592945c6df2fa";
                                if (block == Material.AIR) {
                                    SkullCreator.blockWithUrl(loc, s);
                                    loc.setMetadata("Poop", new FixedMetadataValue(plugin, "Poop"));
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 10000, 10000);
    }

    public void horseRiding(Equinox plugin) {
        new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getVehicle() == null) {
                        return;
                    }
                    Entity e = p.getVehicle();
                    DbHorse horse = plugin.getDbContext().getHorseFromDatabase(e.getUniqueId());
                    horse.setXp(horse.getXp() + 3);
                    p.giveExp(3);
                    p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
                    int requiredForNext = Utilities.getXpNeededForLevel(horse.getLevel() + 1);
                    if(requiredForNext <= horse.getXp()) {
                        horse.setLevel(horse.getLevel() + 1);
                        String message = String.format("%s, [%sEQ%s] >> %sYour horse is now level %s!", ChatColor.GRAY, ChatColor.AQUA, ChatColor.GRAY, ChatColor.GREEN, horse.getLevel());
                        p.sendMessage(message);
                    }

                    plugin.getDbContext().updateHorseInDatabase(horse);

                    if(horse.getLevel() == 20) {
                        swapTrait(e);
                    }
                }
            }
        }.runTaskTimer(plugin, 2600, 2600); //TODO Restore to 2600
    }

    private void swapTrait(Entity e) {
        List<String> badTraits = this.getTraitConfig().getStringList("Bad");
        List<String> goodTraits = this.getTraitConfig().getStringList("Good");

        for (String badTrait : badTraits) {
            if(e.getScoreboardTags().contains("Trait:" + badTrait)) {
                String goodTrait = goodTraits.get(new Random().nextInt(goodTraits.size()));
                e.getScoreboardTags().remove("Trait:" + badTrait);
                e.getScoreboardTags().add("Trait:" + goodTrait);
                return;
            }
        }
    }


    // Get config file
    public FileConfiguration getBreedsConfig() {
        return this.breedsConfig;
    }
    public FileConfiguration getHomesConfig() {
        return this.homesConfig;
    }
    public FileConfiguration getTraitConfig() {
        return this.traitConfig;
    }
    public FileConfiguration getCoatConfig() {
        return this.coatConfig;
    }

    private void createCustomConfig() {
        File breedsConfigFile = new File(getDataFolder(), "Breeds.yml");
        File traitConfigFile = new File(getDataFolder(), "Traits.yml");
        File coatConfigFile = new File(getDataFolder(), "Coat.yml");
        File homesConfigFile = new File(getDataFolder(), "homes.yml");
        if (!homesConfigFile.exists()) {
            if(homesConfigFile.getParentFile().mkdirs()) {
                saveResource("homes.yml", false);
            }
        }
        if (!breedsConfigFile.exists()) {
            if(breedsConfigFile.getParentFile().mkdirs()) {
                saveResource("Breeds.yml", false);
            }
        } if (!traitConfigFile.exists()) {
            if(traitConfigFile.getParentFile().mkdirs()) {
                saveResource("Traits.yml", false);
            }
        } if (!coatConfigFile.exists()) {
            if(coatConfigFile.getParentFile().mkdirs()) {
                saveResource("Coat.yml", false);
            }
        }
        coatConfig = new YamlConfiguration();
        try{
            coatConfig.load(coatConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        traitConfig = new YamlConfiguration();
        try{
            traitConfig.load(traitConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        breedsConfig = new YamlConfiguration();
        try {
            breedsConfig.load(breedsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        homesConfig = new YamlConfiguration();
        try {
            homesConfig.load(homesConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }
    private void onEatGrain(Entity e, BrewingStand brewingStand) {
        if(brewingStand != null) {
            List<PotionType> validTypes = Arrays.asList(PotionType.MUNDANE, PotionType.REGEN, PotionType.NIGHT_VISION);
            List<PotionType> potions = Utilities.findPotionsInInventory(brewingStand.getSnapshotInventory(), validTypes);
            for (PotionType type : potions) {
                if (type == PotionType.MUNDANE) {
                    // 3 Food points
                    //Sweet Feed
                    Utilities.setFoodToHorse(e, 3);
                } else if (type == PotionType.REGEN) {
                    // 1 Food point
                    // Vitamin Supplement
                    Utilities.setFoodToHorse(e, 1);
                    e.addScoreboardTag("Vitamins");
                } else if (type == PotionType.NIGHT_VISION) {
                    // 5 Food points
                    // Senior
                    Utilities.setFoodToHorse(e, 5);
                }
            }
            brewingStand.getSnapshotInventory().clear();
        }

    }



    private void onConsumeFood(Entity e, Material foodType) {
        Utilities.setFoodToHorse(e, foodToHungerValues.get(foodType));
    }

//    private boolean setupChat() {
//        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
//        chat = rsp.getProvider();
//        return chat != null;
//    }
//    private boolean setupPermissions() {
//        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
//        perms = rsp.getProvider();
//        return perms != null;
//    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }
    public static Permission getPermissions() {
        return perms;
    }
    public static Chat getChat() {
        return chat;
    }


    @Override
    public void onDisable() {
        getLogger().info("Plugin Has Been Disabled!  Good Bye!");
    }
}
