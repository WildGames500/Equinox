package net.equinox.wild.equinox;

import com.earth2me.essentials.Essentials;
import dev.dbassett.skullcreator.SkullCreator;
import net.equinox.wild.equinox.entities.DbHorse;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static net.equinox.wild.equinox.Commands.doublexp;


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
    static Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

    private EntityManager entityManager;

    private HashMap<Material, Integer> foodToHungerValues = new HashMap<Material, Integer>();
    public HashMap<UUID, Boolean> lungeStatus = new HashMap<UUID, Boolean>();

    public static String POOP_TEXTURE_URL = "https://textures.minecraft.net/texture/9b3b1f785f01753c45ef97fcffffb3f52658ffceb17ad3f7b592945c6df2fa";

    @Override
    public void onEnable() {
        horseParticle(this);
        babyLoop(this);
        PeeLoop(this);
        drinkLoop(this);
        drinkLoop2(this);
        poop(this);
        loopHorses(this);
        foodToHungerValues.put(Material.GRASS_BLOCK, 1);
        foodToHungerValues.put(Material.WHEAT_SEEDS, 2);
        foodToHungerValues.put(Material.BEETROOT_SEEDS, 5);
        foodToHungerValues.put(Material.PUMPKIN_SEEDS, 4);
        foodToHungerValues.put(Material.MELON_SEEDS, 6);
        foodToHungerValues.put(Material.DRIED_KELP_BLOCK, 10);
        foodToHungerValues.put(Material.HAY_BLOCK, 10);
        //eatGrainLoop(this);
        eatLoop(this, Material.GRASS_BLOCK, Material.DIRT);
        eatLoop(this, Material.DRIED_KELP_BLOCK, Material.AIR);
        eatLoop(this, Material.HAY_BLOCK, Material.AIR);
        horseRiding(this);
        hungerLoop(this);
        thirstLoop(this);
        createCustomConfig();
        getServer().getPluginManager().registerEvents(new Events1(this), this);
        getServer().getPluginManager().registerEvents(new HorseGUI(this), this);
        this.getCommand("eq").setExecutor(new Commands(this));
        this.getCommand("eqa").setExecutor(new Commands(this));
        this.getCommand("cpu").setExecutor(new Commands(this));
        this.getCommand("rankup").setExecutor(new Commands(this));
        this.getCommand("countdown").setExecutor(new Commands(this));
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
                                                    if (e instanceof Horse) {
                                                        ((Horse) e).getPathfinder().findPath(loc2);
                                                    } if (e instanceof Donkey) {
                                                        ((Donkey) e).getPathfinder().findPath(loc2);
                                                    } if (e instanceof Mule) {
                                                        ((Mule) e).getPathfinder().findPath(loc2);
                                                    }
                                                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
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
                                                            if (i <= 20) {
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
                                                        } else if (e.getScoreboardTags().contains("Thirst:5")) {
                                                            e.removeScoreboardTag("Thirst:5");
                                                            e.addScoreboardTag("Thirst:8");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 10) {
                                                                c.setLevel(2);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:4")) {
                                                            e.removeScoreboardTag("Thirst:4");
                                                            e.addScoreboardTag("Thirst:7");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 5) {
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
                                                        } else if (e.getScoreboardTags().contains("Thirst:1")) {
                                                            e.removeScoreboardTag("Thirst:1");
                                                            e.addScoreboardTag("Thirst:4");
                                                        } else if (e.getScoreboardTags().contains("Thirst:0")) {
                                                            e.removeScoreboardTag("Thirst:0");
                                                            e.addScoreboardTag("Thirst:3");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 3) {
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
                                                            if (i <= 20) {
                                                                c.setLevel(1);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:7")) {
                                                            e.removeScoreboardTag("Thirst:7");
                                                            e.addScoreboardTag("Thirst:10");
                                                            e.removeScoreboardTag("Thirst");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 15) {
                                                                c.setLevel(1);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:6")) {
                                                            e.removeScoreboardTag("Thirst:6");
                                                            e.addScoreboardTag("Thirst:9");
                                                        } else if (e.getScoreboardTags().contains("Thirst:5")) {
                                                            e.removeScoreboardTag("Thirst:5");
                                                            e.addScoreboardTag("Thirst:8");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 10) {
                                                                c.setLevel(1);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:4")) {
                                                            e.removeScoreboardTag("Thirst:4");
                                                            e.addScoreboardTag("Thirst:7");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 5) {
                                                                c.setLevel(1);
                                                                block.setBlockData(c);
                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:3")) {
                                                            e.removeScoreboardTag("Thirst:3");
                                                            e.addScoreboardTag("Thirst:6");
                                                        } else if (e.getScoreboardTags().contains("Thirst:2")) {
                                                            e.removeScoreboardTag("Thirst:2");
                                                            e.addScoreboardTag("Thirst:5");
                                                            Random rnd = new Random();
                                                        } else if (e.getScoreboardTags().contains("Thirst:1")) {
                                                            e.removeScoreboardTag("Thirst:1");
                                                            e.addScoreboardTag("Thirst:4");
                                                        } else if (e.getScoreboardTags().contains("Thirst:0")) {
                                                            e.removeScoreboardTag("Thirst:0");
                                                            e.addScoreboardTag("Thirst:3");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 3) {
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
                                                            if (i <= 20) {
                                                                block.setType(Material.CAULDRON);

                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:7")) {
                                                            e.removeScoreboardTag("Thirst:7");
                                                            e.addScoreboardTag("Thirst:10");
                                                            e.removeScoreboardTag("Thirst");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 15) {
                                                                block.setType(Material.CAULDRON);

                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:6")) {
                                                            e.removeScoreboardTag("Thirst:6");
                                                            e.addScoreboardTag("Thirst:9");
                                                        } else if (e.getScoreboardTags().contains("Thirst:5")) {
                                                            e.removeScoreboardTag("Thirst:5");
                                                            e.addScoreboardTag("Thirst:8");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 10) {
                                                                block.setType(Material.CAULDRON);

                                                            }
                                                        } else if (e.getScoreboardTags().contains("Thirst:4")) {
                                                            e.removeScoreboardTag("Thirst:4");
                                                            e.addScoreboardTag("Thirst:7");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 5) {
                                                                block.setType(Material.CAULDRON);

                                                            }
                                                        } else if (e.getScoreboardTags().contains("Hunger:3")) {
                                                            e.removeScoreboardTag("Thirst:3");
                                                            e.addScoreboardTag("Thirst:6");
                                                        } else if (e.getScoreboardTags().contains("Thirst:2")) {
                                                            e.removeScoreboardTag("Thirst:2");
                                                            e.addScoreboardTag("Thirst:5");
                                                        } else if (e.getScoreboardTags().contains("Thirst:1")) {
                                                            e.removeScoreboardTag("Thirst:1");
                                                            e.addScoreboardTag("Thirst:4");
                                                            Random rnd = new Random();
                                                        } else if (e.getScoreboardTags().contains("Thirst:0")) {
                                                            e.removeScoreboardTag("Thirst:0");
                                                            e.addScoreboardTag("Thirst:3");
                                                            Random rnd = new Random();
                                                            int i = rnd.nextInt(100);
                                                            if (i <= 3) {
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
                                    if(e instanceof Horse) {
                                        ((Horse) e).getPathfinder().findPath(loc2);
                                    }if(e instanceof Donkey) {
                                        ((Donkey) e).getPathfinder().findPath(loc2);
                                    } if(e instanceof Mule) {
                                        ((Mule) e).getPathfinder().findPath(loc2);
                                    }
                                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
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
                                        } else if (e.getScoreboardTags().contains("Thirst:3")) {
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
        }.runTaskTimer(plugin, 0, 100);
    }
    private void loopHorses(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                for (World world : getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            if (e.getScoreboardTags().contains("Trait:Aggressive")) {
                                Location loc = e.getLocation();
                                int x = loc.getBlockX();
                                int y = loc.getBlockY();
                                int z = loc.getBlockZ();
                                for(Entity h : e.getNearbyEntities(3, 3, 3)) {
                                    if(h instanceof Horse) {
                                        //give horse scratches/bite
                                    }if(h instanceof Player) {
                                        Random rnd = new Random();
                                        int i = rnd.nextInt(100);
                                        if (i <= 10) {
                                            String name = e.getName();
                                            h.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + name + " Has bitten you!");
                                            ((LivingEntity) h).damage(1);
                                        }
                                    }

                                }


                            } if (e.getScoreboardTags().contains("Contagius1")) {
                                Location loc = e.getLocation();
                                int x = loc.getBlockX();
                                int y = loc.getBlockY();
                                int z = loc.getBlockZ();
                                for(Entity h : e.getNearbyEntities(2, 2, 2)) {
                                    if (h instanceof Horse) {
                                        Random rnd = new Random();
                                        int i = rnd.nextInt(100);
                                        if (i <= 20) {
                                            h.addScoreboardTag("uill1");
                                        }
                                    }
                                }


                            }if (e.getScoreboardTags().contains("Contagius2")) {
                                Location loc = e.getLocation();
                                int x = loc.getBlockX();
                                int y = loc.getBlockY();
                                int z = loc.getBlockZ();
                                for(Entity h : e.getNearbyEntities(2, 2, 2)) {
                                    if (h instanceof Horse) {
                                        Random rnd = new Random();
                                        int i = rnd.nextInt(100);
                                        if (i <= 20) {
                                            h.addScoreboardTag("uill3");
                                        }
                                    }
                                }


                            }

                        }
                    }
                }

            }
        }.runTaskTimer(plugin, 100, 100);
    }

    /*private void eatGrainLoop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                AtomicBoolean hasEaten = new AtomicBoolean(false);
                for (World world : getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            if (e.getScoreboardTags().contains("Hunger")) {
                                Block brewingStand = Utilities.findAbsoluteBlockWithinLocation(e.getLocation(), List.of(Material.BREWING_STAND), 25);
                                if(brewingStand != null) {
                                    if (e instanceof Horse) {
                                        ((Horse) e).getPathfinder().findPath(brewingStand.getLocation());
                                    }
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
    } */

    private void eatLoop(Plugin plugin, Material original, Material replacement) {
        new BukkitRunnable() {
            public void run() {
                AtomicBoolean hasEaten = new AtomicBoolean(false);
                for(World world : getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            Location loc = e.getLocation();
                            int x = loc.getBlockX();
                            int y1 = loc.getBlockY();
                            int z = loc.getBlockZ();
                            int lowestY = y1 - 1;
                            int radius = 4;
                            if (e.getScoreboardTags().contains("Hunger")) {
                                Random rnd2 = new Random();
                                int i = rnd2.nextInt(100);
                                if (i <= 10) {
                                    if (e.getScoreboardTags().contains("Malnurished")) {
                                        e.removeScoreboardTag("Malnurished");
                                    }
                                }
                                Location foodSource = Utilities.findTypeOfBlockWithinLocation(loc, List.of(original), radius);
                                for(int y = lowestY; y <= y1; y++) {
                                    Block b = world.getBlockAt(x, y, z);
                                    if (b.getType() == Material.GRASS_BLOCK) {
                                        ((Horse) e).getPathfinder().findPath(b.getLocation());
                                        if(!hasEaten.get()) {
                                            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                                for (Player player : world.getNearbyPlayers(loc, 5)) {
                                                    player.playSound(loc, "entity.horse.eat", 2, 1F);
                                                }
                                                b.setType(Material.DIRT);
                                                onConsumeFood(e, Material.GRASS_BLOCK);
                                                hasEaten.set(true);
                                            }, 100);
                                        }
                                    }
                                }
                                if(foodSource != null) {
                                    if (e instanceof Horse) {
                                        ((Horse) e).getPathfinder().findPath(foodSource);
                                    } if (e instanceof Donkey) {
                                        ((Donkey) e).getPathfinder().findPath(foodSource);
                                    } if (e instanceof Mule) {
                                        ((Mule) e).getPathfinder().findPath(foodSource);
                                    }
                                    if(!hasEaten.get()) {
                                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                            for(Player player : world.getNearbyPlayers(loc, 5)) {
                                                player.playSound(loc, "entity.horse.eat", 2, 1F);
                                            }
                                            e.getWorld().getBlockAt(foodSource).setType(replacement);
                                            onConsumeFood(e, original);
                                            hasEaten.set(true);
                                        }, 100);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 100, 200);
    }

    public void PeeLoop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                for(World world : getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            if (!e.getScoreboardTags().contains("Invulnerable")) {
                                Random rnd = new Random();
                                int i = rnd.nextInt(100);
                                if (i <= 10) {
                                    Location loc = e.getLocation();
                                    int x = loc.getBlockX();
                                    int y1 = loc.getBlockY();
                                    int z = loc.getBlockZ();
                                    int lowestY = y1 - 1;
                                    world.playSound(loc, Sound.ENTITY_GHAST_DEATH, 1, 1);
                                    for(int y = lowestY; y <= y1; y++) {
                                        Block b = world.getBlockAt(x, y, z);
                                        if (b.getType() == Material.SNOW) {
                                            b.setType(Material.LIME_CARPET);
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

    public void poop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                for(World world : getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                            if (!e.getScoreboardTags().contains("Invulnerable")) {
                                Random rnd = new Random();
                                int i = rnd.nextInt(100);
                                if (i <= 10) { //set 10
                                    Block loc = e.getLocation().getBlock();
                                    Material block = loc.getType();
                                    if (block == Material.AIR) {
                                        SkullCreator.blockWithUrl(loc, POOP_TEXTURE_URL);
//                                        loc.setMetadata("Poop", new FixedMetadataValue(plugin, "Poop"));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 10000, 10000);//set 10000
    }
    public void horseParticle(Equinox plugin) {
        new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    World world = p.getWorld();
                    if (p.isInsideVehicle()) {
                        Entity e = p.getVehicle();
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(235, 207, 52), 20.0F);
                        Particle.DustOptions dustOptions1 = new Particle.DustOptions(Color.fromRGB(52, 161, 42), 20.0F);
                        Particle.DustOptions dustOptions2 = new Particle.DustOptions(Color.fromRGB(0, 213, 255), 20.0F);
                        Particle.DustOptions dustOptions3 = new Particle.DustOptions(Color.fromRGB(128, 0, 255), 20.0F);
                        if (e.getScoreboardTags().contains("Walk")) {
                            Location loc = e.getLocation();
                            int x = (int) loc.getX();
                            int y = (int) loc.getY();
                            int z = (int) loc.getZ();
                            int y1 = y + 1;
                            world.spawnParticle(Particle.REDSTONE, x, y1, z, 0, dustOptions);


                        }
                        if (e.getScoreboardTags().contains("Trot")) {
                            Location loc = e.getLocation();
                            int x = (int) loc.getX();
                            int y = (int) loc.getY();
                            int z = (int) loc.getZ();
                            int y1 = y + 1;
                            world.spawnParticle(Particle.REDSTONE, x, y1, z, 0, dustOptions1);

                        }
                        if (e.getScoreboardTags().contains("Canter")) {

                            Location loc = e.getLocation();
                            int x = (int) loc.getX();
                            int y = (int) loc.getY();
                            int z = (int) loc.getZ();
                            int y1 = y + 1;
                            world.spawnParticle(Particle.REDSTONE, x, y1, z, 0, dustOptions2);

                        }
                        if (e.getScoreboardTags().contains("Gallop")) {
                            Location loc = e.getLocation();
                            int x = (int) loc.getX();
                            int y = (int) loc.getY();
                            int z = (int) loc.getZ();
                            int y1 = y + 1;
                            world.spawnParticle(Particle.REDSTONE, x, y1, z, 0, dustOptions3);
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 30);
    }

    public void horseRiding(Equinox plugin) {
        new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (ess.getUser(p) != null && ess.getUser(p).isAfk()) {
                        continue;
                    }
                    if (p.getVehicle() == null) {
                        continue;
                    }
                    Entity e = p.getVehicle();
                    DbHorse horse = plugin.getDbContext().getHorseFromDatabase(e.getUniqueId());
                    horse.setXp(horse.getXp() + 3);
                    if (Objects.equals(doublexp.get("dxp"), "true")) {
                        p.giveExp(25);
                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_AQUA + "+25 XP"));
                    } if (!Objects.equals(doublexp.get("dxp"), "true")) {
                        p.giveExp(15);
                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_AQUA + "+15 XP"));
                    }

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
    /**private void onEatGrain(Entity e, BrewingStand brewingStand) {
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
*/


    void onConsumeFood(Entity e, Material foodType) {
        if(foodType == Material.GRASS_BLOCK || foodType == Material.WHEAT_SEEDS || foodType == Material.BEETROOT_SEEDS || foodType == Material.MELON_SEEDS || foodType == Material.PUMPKIN_SEEDS) {
            Utilities.addFoodToHorse(e, foodToHungerValues.get(foodType));
        } else {
            Utilities.setFoodToHorse(e, foodToHungerValues.get(foodType));
        }
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
        getLogger().info("Plugin is being disabled - saving entities to the database!");
        for(World world : this.getServer().getWorlds()) {
            for(Entity e : world.getEntities()) {
                if (e instanceof Horse || e instanceof Donkey || e instanceof Mule) {
                    Chunk sourceChunk = e.getChunk();
                    int x = sourceChunk.getX();
                    int z = sourceChunk.getZ();

                    DbHorse horse = this.getDbContext().getHorseFromDatabase(e.getUniqueId());

                    if(horse != null) {
                        horse.setLastWorld(e.getWorld().getName());
                        horse.setLastChunkX(x);
                        horse.setLastChunkZ(z);
                        this.getDbContext().updateHorseInDatabase(horse);
                    }

                }
            }
        }
    }
}
