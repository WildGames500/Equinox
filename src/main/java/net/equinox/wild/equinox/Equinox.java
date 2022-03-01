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
        }.runTaskTimer(plugin, 300, 300); // TODO original 20000
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
                        if (e instanceof Horse) {
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
                        if (e instanceof Horse) {
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
                        if (e instanceof Horse) {
                            Location loc = e.getLocation();
                            int radius = 2;
                            if (e.getScoreboardTags().contains("Thirst")) {
                                int x = -radius, y = -radius, z = -radius;
                                Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                                Location loc2 = block.getLocation();
                                Material bt = block.getType();
                                if (bt == Material.WATER) {
                                    ((Horse) e).getPathfinder().findPath(loc2);
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
                        if (e instanceof Horse) {
                            if (e.getScoreboardTags().contains("Hunger")) {
                                Block brewingStand = Utilities.findAbsoluteBlockWithinLocation(e.getLocation(), List.of(Material.BREWING_STAND), 25);
                                if(brewingStand != null) {
                                    ((Horse) e).getPathfinder().findPath(brewingStand.getLocation());
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
                        if (e instanceof Horse) {
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
                        if (e instanceof Horse) {
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
                        if (e instanceof Horse) {
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
//                    if (e.getScoreboardTags().contains("Level:0")) {
//                        int i = 1;
//                        while (i <= 20) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 20) {
//                                    e.removeScoreboardTag("Level:0");
//                                    e.addScoreboardTag("Level:1");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 1!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:1")) {
//                        int i = 20;
//                        while (i <= 50) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 50) {
//                                    e.removeScoreboardTag("Level:1");
//                                    e.addScoreboardTag("Level:2");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 2!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:2")) {
//                        int i = 50;
//                        while (i <= 100) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 100) {
//                                    e.removeScoreboardTag("Level:2");
//                                    e.addScoreboardTag("Level:3");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 3!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:3")) {
//                        int i = 100;
//                        while (i <= 165) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 165) {
//                                    e.removeScoreboardTag("Level:3");
//                                    e.addScoreboardTag("Level:4");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 4!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:4")) {
//                        int i = 165;
//                        while (i <= 255) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(1);
//                                if (i2 >= 255) {
//                                    e.removeScoreboardTag("Level:4");
//                                    e.addScoreboardTag("Level:5");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 5!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:5")) {
//                        int i = 255;
//                        while (i <= 385) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 385) {
//                                    e.removeScoreboardTag("Level:5");
//                                    e.addScoreboardTag("Level:6");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 6!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:6")) {
//                        int i = 385;
//                        while (i <= 495) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 495) {
//                                    e.removeScoreboardTag("Level:6");
//                                    e.addScoreboardTag("Level:7");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 7!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:7")) {
//                        int i = 495;
//                        while (i <= 695) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 695) {
//                                    e.removeScoreboardTag("Level:7");
//                                    e.addScoreboardTag("Level:8");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 8!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:8")) {
//                        int i = 695;
//                        while (i <= 865) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 865) {
//                                    e.removeScoreboardTag("Level:8");
//                                    e.addScoreboardTag("Level:9");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 9!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:9")) {
//                        int i = 865;
//                        while (i <= 1085) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 1085) {
//                                    e.removeScoreboardTag("Level:9");
//                                    e.addScoreboardTag("Level:10");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 10!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:10")) {
//                        int i = 1085;
//                        while (i <= 1500) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 1500) {
//                                    e.removeScoreboardTag("Level:10");
//                                    e.addScoreboardTag("Level:11");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 12!");
//                                    for (String tb : getConfig().getStringList("Bad")) {
//                                        if (e.getScoreboardTags().contains(tb)) {
//                                            e.removeScoreboardTag("Trait:" + tb);
//                                            for (String traits : getConfig().getStringList("Good")) {
//                                                List<String> tr = List.of(traits);
//                                                Random rn = new Random();
//                                                String rnt = tr.get(rn.nextInt(tr.size()));
//                                                e.addScoreboardTag("Trait:" + rnt);
//                                                p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse has lost there bad trait!");
//                                                break;
//                                            }
//                                        } break;
//                                    }
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    }else if (e.getScoreboardTags().contains("Level:11")) {
//                        int i = 1500;
//                        while (i <= 1800) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 1800) {
//                                    e.removeScoreboardTag("Level:11");
//                                    e.addScoreboardTag("Level:12");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 12!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:12")) {
//                        int i = 1800;
//                        while (i <= 2000) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 2000) {
//                                    e.removeScoreboardTag("Level:12");
//                                    e.addScoreboardTag("Level:13");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 13!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:13")) {
//                        int i = 2000;
//                        while (i <= 2300) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 2300) {
//                                    e.removeScoreboardTag("Level:13");
//                                    e.addScoreboardTag("Level:14");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 14!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:14")) {
//                        int i = 2300;
//                        while (i <= 2800) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 2800) {
//                                    e.removeScoreboardTag("Level:14");
//                                    e.addScoreboardTag("Level:15");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 15!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:15")) {
//                        int i = 2800;
//                        while (i <= 3500) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 3500) {
//                                    e.removeScoreboardTag("Level:15");
//                                    e.addScoreboardTag("Level:16");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 16!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:16")) {
//                        int i = 3500;
//                        while (i <= 4200) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 4200) {
//                                    e.removeScoreboardTag("Level:16");
//                                    e.addScoreboardTag("Level:17");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 17!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:17")) {
//                        int i = 4200;
//                        while (i <= 4900) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 4900) {
//                                    e.removeScoreboardTag("Level:17");
//                                    e.addScoreboardTag("Level:18");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 18!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:18")) {
//                        int i = 4900;
//                        while (i <= 5500) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 5500) {
//                                    e.removeScoreboardTag("Level:18");
//                                    e.addScoreboardTag("Level:19");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 19!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:19")) {
//                        int i = 5500;
//                        while (i <= 6300) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 6300) {
//                                    e.removeScoreboardTag("Level:19");
//                                    e.addScoreboardTag("Level:20");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 20!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:20")) {
//                        int i = 6300;
//                        while (i <= 7000) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 7000) {
//                                    e.removeScoreboardTag("Level:20");
//                                    e.addScoreboardTag("Level:21");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 21!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:21")) {
//                        int i = 7000;
//                        while (i <= 7730) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 7730) {
//                                    e.removeScoreboardTag("Level:21");
//                                    e.addScoreboardTag("Level:22");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 22!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:22")) {
//                        int i = 7730;
//                        while (i <= 8110) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 8110) {
//                                    e.removeScoreboardTag("Level:22");
//                                    e.addScoreboardTag("Level:23");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 23!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:23")) {
//                        int i = 8110;
//                        while (i <= 8560) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 8560) {
//                                    e.removeScoreboardTag("Level:23");
//                                    e.addScoreboardTag("Level:24");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 24!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:24")) {
//                        int i = 8560;
//                        while (i <= 9150) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 9150) {
//                                    e.removeScoreboardTag("Level:24");
//                                    e.addScoreboardTag("Level:25");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 25!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:25")) {
//                        int i = 9150;
//                        while (i <= 9700) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 9700) {
//                                    e.removeScoreboardTag("Level:25");
//                                    e.addScoreboardTag("Level:26");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 26!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:26")) {
//                        int i = 9700;
//                        while (i <= 10200) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 10200) {
//                                    e.removeScoreboardTag("Level:26");
//                                    e.addScoreboardTag("Level:27");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 27!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:27")) {
//                        int i = 10200;
//                        while (i <= 10950) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 10950) {
//                                    e.removeScoreboardTag("Level:27");
//                                    e.addScoreboardTag("Level:28");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 28!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:28")) {
//                        int i = 10950;
//                        while (i <= 11600) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 11600) {
//                                    e.removeScoreboardTag("Level:28");
//                                    e.addScoreboardTag("Level:29");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 29!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:29")) {
//                        int i = 11600;
//                        while (i <= 12200) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 12200) {
//                                    e.removeScoreboardTag("Level:29");
//                                    e.addScoreboardTag("Level:30");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 30!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:30")) {
//                        int i = 12200;
//                        while (i <= 13000) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 13000) {
//                                    e.removeScoreboardTag("Level:30");
//                                    e.addScoreboardTag("Level:31");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 31!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:31")) {
//                        int i = 13000;
//                        while (i <= 14000) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 14000) {
//                                    e.removeScoreboardTag("Level:31");
//                                    e.addScoreboardTag("Level:32");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 32!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:32")) {
//                        int i = 14000;
//                        while (i <= 15500) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 15500) {
//                                    e.removeScoreboardTag("Level:32");
//                                    e.addScoreboardTag("Level:33");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 33!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:33")) {
//                        int i = 15500;
//                        while (i <= 17000) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 17000) {
//                                    e.removeScoreboardTag("Level:33");
//                                    e.addScoreboardTag("Level:34");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 34!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    } else if (e.getScoreboardTags().contains("Level:34")) {
//                        int i = 17000;
//                        while (i <= 20000) {
//                            if (e.getScoreboardTags().contains("XP:" + i)) {
//                                e.removeScoreboardTag("XP:" + i);
//                                int i2 = i + 3;
//                                e.addScoreboardTag("XP:" + i2);
//                                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+3 XP"));
//                                p.giveExp(3);
//                                if (i2 >= 20000) {
//                                    e.removeScoreboardTag("Level:34");
//                                    e.addScoreboardTag("Level:35");
//                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse is now level 35!");
//                                }
//                                return;
//                            } else {
//                                ++i;
//                            }
//                        }
//                    }
                }
            }
        }.runTaskTimer(plugin, 200, 200); //TODO Restore to 2600
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
