package net.equinox.wild.equinox;

import dev.dbassett.skullcreator.SkullCreator;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.material.Cauldron;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.bukkit.Sound.BLOCK_GRASS_BREAK;


public final class Equinox extends JavaPlugin {
    private static Economy econ = null;
    private File breedsConfigFile;
    private File traitConfigFile;
    private FileConfiguration breedsConfig;
    private FileConfiguration traitConfig;
    private boolean useHolographicDisplays;


    @Override
    public void onEnable() {
        drinkLoop(this);
        drinkLoop2(this);
        poop(this);
        eatLoop2(this);
        eatLoop3(this);
        horseRiding(this);
        hungerLoop(this);
        thirstLoop(this);
        eatLoop(this);
        createCustomConfig();
        useHolographicDisplays = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
        this.getCommand("eqa").setExecutor(new Commands2(this));
        getServer().getPluginManager().registerEvents(new Events1(this), this);
        getServer().getPluginManager().registerEvents(new HorseGUI(this), this);
        this.getCommand("eq").setExecutor(new Commands(this));
        getLogger().info("Plugin Has Been Enabled! Hello ^-^");
        if (!setupEconomy()) {
            System.out.println("No Economy Plugin Found! Disabling Vault...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }
    }



    //Loop Hunger
    public void hungerLoop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                World world = getServer().getWorld("world");
                for (Entity e : world.getEntities()) {
                    Random rnd = new Random();
                    int i = rnd.nextInt(100);
                    if (e instanceof Horse) {
                        if (i <= 20) {
                            if (e.getScoreboardTags().contains("Hunger:10")) {
                                e.removeScoreboardTag("Hunger:10");
                                e.addScoreboardTag("Hunger:9");
                            } else if (e.getScoreboardTags().contains("Hunger:9")) {
                                e.removeScoreboardTag("Hunger:9");
                                e.addScoreboardTag("Hunger:8");
                            } else if (e.getScoreboardTags().contains("Hunger:8")) {
                                e.removeScoreboardTag("Hunger:8");
                                e.addScoreboardTag("Hunger:7");
                            } else if (e.getScoreboardTags().contains("Hunger:7")) {
                                e.removeScoreboardTag("Hunger:7");
                                e.addScoreboardTag("Hunger:6");
                            } else if (e.getScoreboardTags().contains("Hunger:6")) {
                                e.removeScoreboardTag("Hunger:6");
                                e.addScoreboardTag("Hunger:5");
                            } else if (e.getScoreboardTags().contains("Hunger:5")) {
                                e.removeScoreboardTag("Hunger:5");
                                e.addScoreboardTag("Hunger:4");
                            } else if (e.getScoreboardTags().contains("Hunger:4")) {
                                e.removeScoreboardTag("Hunger:4");
                                e.addScoreboardTag("Hunger:3");
                            } else if (e.getScoreboardTags().contains("Hunger:3")) {
                                e.removeScoreboardTag("Hunger:3");
                                e.addScoreboardTag("Hunger:2");
                            } else if (e.getScoreboardTags().contains("Hunger:2")) {
                                e.removeScoreboardTag("Hunger:2");
                                e.addScoreboardTag("Hunger:1");
                            } else if (e.getScoreboardTags().contains("Hunger:1")) {
                                e.removeScoreboardTag("Hunger:1");
                                e.addScoreboardTag("Hunger:0");
                            }
                        }

                    }
                }
            }
        }.runTaskTimer(plugin, 30000, 30000);
    }

    //Loop Thirst
    public void thirstLoop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                World world = getServer().getWorld("world");
                for (Entity e : world.getEntities()) {
                    Random rnd = new Random();
                    int i = rnd.nextInt(100);
                    if (e instanceof Horse) {
                        if (i <= 25) {
                            if (e.getScoreboardTags().contains("Thirst:10")) {
                                e.removeScoreboardTag("Thirst:10");
                                e.addScoreboardTag("Thirst:9");
                            } else if (e.getScoreboardTags().contains("Thirst:9")) {
                                e.removeScoreboardTag("Thirst:9");
                                e.addScoreboardTag("Thirst:8");
                            } else if (e.getScoreboardTags().contains("Thirst:8")) {
                                e.removeScoreboardTag("Thirst:8");
                                e.addScoreboardTag("Thirst:7");
                            } else if (e.getScoreboardTags().contains("Thirst:7")) {
                                e.removeScoreboardTag("Thirst:7");
                                e.addScoreboardTag("Thirst:6");
                            } else if (e.getScoreboardTags().contains("Thirst:6")) {
                                e.removeScoreboardTag("Thirst:6");
                                e.addScoreboardTag("Thirst:5");
                            } else if (e.getScoreboardTags().contains("Thirst:5")) {
                                e.removeScoreboardTag("Thirst:5");
                                e.addScoreboardTag("Thirst:4");
                            } else if (e.getScoreboardTags().contains("Thirst:4")) {
                                e.removeScoreboardTag("Thirst:4");
                                e.addScoreboardTag("Thirst:3");
                            } else if (e.getScoreboardTags().contains("Thirst:3")) {
                                e.removeScoreboardTag("Thirst:3");
                                e.addScoreboardTag("Thirst:2");
                            } else if (e.getScoreboardTags().contains("Thirst:2")) {
                                e.removeScoreboardTag("Thirst:2");
                                e.addScoreboardTag("Thirst:1");
                            } else if (e.getScoreboardTags().contains("Thirst:1")) {
                                e.removeScoreboardTag("Thirst:1");
                                e.addScoreboardTag("Thirst:0");
                            }
                        }

                    }
                }
            }
        }.runTaskTimer(plugin, 35000, 35000);
    }

    public void drinkLoop2(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                World world = getServer().getWorld("world");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        Location loc = e.getLocation();
                        int radius = 2;
                        int in = 0;
                        while (in <= 9) {
                            if (e.getScoreboardTags().contains("Thirst:" + in)) {
                                for (int x = -radius; x <= radius; x++) {
                                    for (int y = -radius; y <= radius; y++) {
                                        for (int z = -radius; z <= radius; z++) {
                                            Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                                            Location loc2 = block.getLocation();
                                            Material bt = block.getType();
                                            if (bt == Material.CAULDRON) {
                                                Cauldron c = (Cauldron) block.getState().getData();
                                                if (c.getData() == 3) {
                                                    ((Horse) e).getPathfinder().findPath(loc2);
                                                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            NBTEditor.set(e, (byte) 1, "EatingHaystack");
                                                            if (e.getScoreboardTags().contains("Thirst:9")) {
                                                                e.removeScoreboardTag("Thirst:9");
                                                                e.addScoreboardTag("Thirst:10");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:8")) {
                                                                e.removeScoreboardTag("Thirst:8");
                                                                e.addScoreboardTag("Thirst:10");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:7")) {
                                                                e.removeScoreboardTag("Thirst:7");
                                                                e.addScoreboardTag("Thirst:10");
                                                                block.getState().getData().setData((byte) 2);
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:6")) {
                                                                e.removeScoreboardTag("Thirst:6");
                                                                e.addScoreboardTag("Thirst:9");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:5")) {
                                                                e.removeScoreboardTag("Thirst:5");
                                                                e.addScoreboardTag("Thirst:8");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:4")) {
                                                                e.removeScoreboardTag("Thirst:4");
                                                                e.addScoreboardTag("Thirst:7");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:3")) {
                                                                e.removeScoreboardTag("Thirst:3");
                                                                e.addScoreboardTag("Thirst:6");
                                                                block.getState().getData().setData((byte) 2);
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:2")) {
                                                                e.removeScoreboardTag("Thirst:2");
                                                                e.addScoreboardTag("Thirst:5");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:1")) {
                                                                e.removeScoreboardTag("Thirst:1");
                                                                e.addScoreboardTag("Thirst:4");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:0")) {
                                                                e.removeScoreboardTag("Thirst:0");
                                                                e.addScoreboardTag("Thirst:3");
                                                                block.getState().getData().setData((byte) 2);
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
                                                                    return;

                                                                } else {
                                                                    return;
                                                                }
                                                            }
                                                        }
                                                    }, 100);
                                                } else if (c.getData() == 2) {
                                                    ((Horse) e).getPathfinder().findPath(loc2);
                                                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (e.getScoreboardTags().contains("Thirst:9")) {
                                                                e.removeScoreboardTag("Thirst:9");
                                                                e.addScoreboardTag("Thirst:10");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:8")) {
                                                                e.removeScoreboardTag("Thirst:8");
                                                                e.addScoreboardTag("Thirst:10");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:7")) {
                                                                e.removeScoreboardTag("Thirst:7");
                                                                e.addScoreboardTag("Thirst:10");
                                                                block.getState().getData().setData((byte) 1);
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:6")) {
                                                                e.removeScoreboardTag("Thirst:6");
                                                                e.addScoreboardTag("Thirst:9");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:5")) {
                                                                e.removeScoreboardTag("Thirst:5");
                                                                e.addScoreboardTag("Thirst:8");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:4")) {
                                                                e.removeScoreboardTag("Thirst:4");
                                                                e.addScoreboardTag("Thirst:7");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:3")) {
                                                                e.removeScoreboardTag("Thirst:3");
                                                                e.addScoreboardTag("Thirst:6");
                                                                block.getState().getData().setData((byte) 1);
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:2")) {
                                                                e.removeScoreboardTag("Thirst:2");
                                                                e.addScoreboardTag("Thirst:5");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:1")) {
                                                                e.removeScoreboardTag("Thirst:1");
                                                                e.addScoreboardTag("Thirst:4");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:0")) {
                                                                e.removeScoreboardTag("Thirst:0");
                                                                e.addScoreboardTag("Thirst:3");
                                                                block.getState().getData().setData((byte) 1);
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
                                                                    return;

                                                                } else {
                                                                    return;
                                                                }
                                                            }
                                                        }
                                                    }, 100);
                                                } else if (c.getData() == 1) {
                                                    ((Horse) e).getPathfinder().findPath(loc2);
                                                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (e.getScoreboardTags().contains("Thirst:9")) {
                                                                e.removeScoreboardTag("Thirst:9");
                                                                e.addScoreboardTag("Thirst:10");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:8")) {
                                                                e.removeScoreboardTag("Thirst:8");
                                                                e.addScoreboardTag("Thirst:10");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:7")) {
                                                                e.removeScoreboardTag("Thirst:7");
                                                                e.addScoreboardTag("Thirst:10");
                                                                block.getState().getData().setData((byte) 0);
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:6")) {
                                                                e.removeScoreboardTag("Thirst:6");
                                                                e.addScoreboardTag("Thirst:9");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:5")) {
                                                                e.removeScoreboardTag("Thirst:5");
                                                                e.addScoreboardTag("Thirst:8");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:4")) {
                                                                e.removeScoreboardTag("Thirst:4");
                                                                e.addScoreboardTag("Thirst:7");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Hunger:3")) {
                                                                e.removeScoreboardTag("Thirst:3");
                                                                e.addScoreboardTag("Thirst:6");
                                                                block.getState().getData().setData((byte) 0);
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:2")) {
                                                                e.removeScoreboardTag("Thirst:2");
                                                                e.addScoreboardTag("Thirst:5");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:1")) {
                                                                e.removeScoreboardTag("Thirst:1");
                                                                e.addScoreboardTag("Thirst:4");
                                                                return;
                                                            } else if (e.getScoreboardTags().contains("Thirst:0")) {
                                                                e.removeScoreboardTag("Thirst:0");
                                                                e.addScoreboardTag("Thirst:3");
                                                                block.getState().getData().setData((byte) 0);
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
                                                                    return;

                                                                } else {
                                                                    return;
                                                                }
                                                            }
                                                        }
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
        }.runTaskTimer(plugin, 600, 600);
    }

    public void drinkLoop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                World world = getServer().getWorld("world");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        Location loc = e.getLocation();
                        int radius = 2;
                        int in = 0;
                        while (in <= 9) {
                            if (e.getScoreboardTags().contains("Thirst:" + in)) {
                                for (int x = -radius; x <= radius; x++) {
                                    for (int y = -radius; y <= radius; y++) {
                                        for (int z = -radius; z <= radius; z++) {
                                            Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                                            Location loc2 = block.getLocation();
                                            Material bt = block.getType();
                                            if (bt == Material.WATER) {
                                                ((Horse) e).getPathfinder().findPath(loc2);
                                                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        NBTEditor.set(e, (byte) 1, "EatingHaystack");
                                                        block.setType(Material.AIR);
                                                        if (e.getScoreboardTags().contains("Thirst:9")) {
                                                            e.removeScoreboardTag("Thirst:9");
                                                            e.addScoreboardTag("Thirst:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Thirst:8")) {
                                                            e.removeScoreboardTag("Thirst:8");
                                                            e.addScoreboardTag("Thirst:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Thirst:7")) {
                                                            e.removeScoreboardTag("Thirst:7");
                                                            e.addScoreboardTag("Thirst:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Thirst:6")) {
                                                            e.removeScoreboardTag("Thirst:6");
                                                            e.addScoreboardTag("Thirst:9");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Thirst:5")) {
                                                            e.removeScoreboardTag("Thirst:5");
                                                            e.addScoreboardTag("Thirst:8");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Thirst:4")) {
                                                            e.removeScoreboardTag("Thirst:4");
                                                            e.addScoreboardTag("Thirst:7");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:3")) {
                                                            e.removeScoreboardTag("Thirst:3");
                                                            e.addScoreboardTag("Thirst:6");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Thirst:2")) {
                                                            e.removeScoreboardTag("Thirst:2");
                                                            e.addScoreboardTag("Thirst:5");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Thirst:1")) {
                                                            e.removeScoreboardTag("Thirst:1");
                                                            e.addScoreboardTag("Thirst:4");
                                                            return;
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
                                                                return;

                                                            } else {
                                                                return;
                                                            }
                                                        }
                                                    }
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
        }.runTaskTimer(plugin, 600, 600);
    }

    //Loop EatHay
    public void eatLoop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                World world = getServer().getWorld("world");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        Location loc = e.getLocation();
                        int radius = 2;
                        int in = 0;
                        while (in <= 9) {
                            if (e.getScoreboardTags().contains("Hunger:" + in)) {
                                for (int x = -radius; x <= radius; x++) {
                                    for (int y = -radius; y <= radius; y++) {
                                        for (int z = -radius; z <= radius; z++) {
                                            Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                                            Location loc2 = block.getLocation();
                                            Material bt = block.getType();
                                            if (bt == Material.HAY_BLOCK) {
                                                ((Horse) e).getPathfinder().findPath(loc2);
                                                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        NBTEditor.set(e, (byte) 1, "EatingHaystack");
                                                        block.setType(Material.AIR);
                                                        if (e.getScoreboardTags().contains("Hunger:9")) {
                                                            e.removeScoreboardTag("Hunger:9");
                                                            e.addScoreboardTag("Hunger:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:8")) {
                                                            e.removeScoreboardTag("Hunger:8");
                                                            e.addScoreboardTag("Hunger:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:7")) {
                                                            e.removeScoreboardTag("Hunger:7");
                                                            e.addScoreboardTag("Hunger:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:6")) {
                                                            e.removeScoreboardTag("Hunger:6");
                                                            e.addScoreboardTag("Hunger:9");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:5")) {
                                                            e.removeScoreboardTag("Hunger:5");
                                                            e.addScoreboardTag("Hunger:8");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:4")) {
                                                            e.removeScoreboardTag("Hunger:4");
                                                            e.addScoreboardTag("Hunger:7");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:3")) {
                                                            e.removeScoreboardTag("Hunger:3");
                                                            e.addScoreboardTag("Hunger:6");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:2")) {
                                                            e.removeScoreboardTag("Hunger:2");
                                                            e.addScoreboardTag("Hunger:5");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:1")) {
                                                            e.removeScoreboardTag("Hunger:1");
                                                            e.addScoreboardTag("Hunger:4");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:0")) {
                                                            e.removeScoreboardTag("Hunger:0");
                                                            e.addScoreboardTag("Hunger:3");
                                                            if (e.getScoreboardTags().contains("DayH-1")) {
                                                                e.removeScoreboardTag("DayH-1");
                                                                return;
                                                            }
                                                            if (e.getScoreboardTags().contains("DayH-2")) {
                                                                e.removeScoreboardTag("DayH-2");
                                                                return;
                                                            }
                                                            if (e.getScoreboardTags().contains("DayH-3")) {
                                                                e.removeScoreboardTag("DayH-3");
                                                                return;

                                                            } else {
                                                                return;
                                                            }
                                                        }
                                                    }
                                                }, 100);
                                                break;
                                            }break;
                                        }
                                    }
                                }
                            } break;
                        } ++in;
                    }
                }
            }
        }.runTaskTimer(plugin, 500, 500);
    }

    public void eatLoop2(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                World world = getServer().getWorld("world");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        Location loc = e.getLocation();
                        int radius = 2;
                        int in = 0;
                        while (in <= 9) {
                            if (e.getScoreboardTags().contains("Hunger:" + in)) {
                                for (int x = -radius; x <= radius; x++) {
                                    for (int y = -radius; y <= radius; y++) {
                                        for (int z = -radius; z <= radius; z++) {
                                            Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                                            Location loc2 = block.getLocation();
                                            Material bt = block.getType();
                                            if (bt == Material.RED_STAINED_GLASS_PANE) {
                                                ((Horse) e).getPathfinder().findPath(loc2);
                                                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        NBTEditor.set(e, (byte) 1, "EatingHaystack");
                                                        block.setType(Material.YELLOW_STAINED_GLASS_PANE);
                                                        if (e.getScoreboardTags().contains("Hunger:9")) {
                                                            e.removeScoreboardTag("Hunger:9");
                                                            e.addScoreboardTag("Hunger:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:8")) {
                                                            e.removeScoreboardTag("Hunger:8");
                                                            e.addScoreboardTag("Hunger:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:7")) {
                                                            e.removeScoreboardTag("Hunger:7");
                                                            e.addScoreboardTag("Hunger:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:6")) {
                                                            e.removeScoreboardTag("Hunger:6");
                                                            e.addScoreboardTag("Hunger:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:5")) {
                                                            e.removeScoreboardTag("Hunger:5");
                                                            e.addScoreboardTag("Hunger:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:4")) {
                                                            e.removeScoreboardTag("Hunger:4");
                                                            e.addScoreboardTag("Hunger:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:3")) {
                                                            e.removeScoreboardTag("Hunger:3");
                                                            e.addScoreboardTag("Hunger:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:2")) {
                                                            e.removeScoreboardTag("Hunger:2");
                                                            e.addScoreboardTag("Hunger:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:1")) {
                                                            e.removeScoreboardTag("Hunger:1");
                                                            e.addScoreboardTag("Hunger:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:0")) {
                                                            e.removeScoreboardTag("Hunger:0");
                                                            e.addScoreboardTag("Hunger:10");
                                                            if (e.getScoreboardTags().contains("DayH-1")) {
                                                                e.removeScoreboardTag("DayH-1");
                                                                return;
                                                            }
                                                            if (e.getScoreboardTags().contains("DayH-2")) {
                                                                e.removeScoreboardTag("DayH-2");
                                                                return;
                                                            }
                                                            if (e.getScoreboardTags().contains("DayH-3")) {
                                                                e.removeScoreboardTag("DayH-3");
                                                                return;

                                                            } else {
                                                                return;
                                                            }
                                                        }
                                                    }
                                                }, 100);
                                            }
                                        }
                                    }
                                }
                            } break;
                        } ++in;
                    }
                }
            }
        }.runTaskTimer(plugin, 500, 500);
    }

    public void eatLoop3(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                World world = getServer().getWorld("world");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        Location loc = e.getLocation();
                        int radius = 1;
                        int in = 0;
                        while (in <= 9) {
                            if (e.getScoreboardTags().contains("Hunger:" + in)) {
                                for (int x = -radius; x <= radius; x++) {
                                    for (int y = -radius; y <= radius; y++) {
                                        for (int z = -radius; z <= radius; z++) {
                                            Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                                            Location loc2 = block.getLocation();
                                            Material bt = block.getType();
                                            if (bt == Material.GRASS_BLOCK) {
                                                ((Horse) e).getPathfinder().findPath(loc2);
                                                for (Player p : loc2.getNearbyPlayers(5)) {
                                                    p.playSound(loc2, BLOCK_GRASS_BREAK, 1, 1);
                                                }
                                                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        NBTEditor.set(e, (byte) 1, "EatingHaystack");
                                                        Random rnd = new Random();
                                                        int i = rnd.nextInt(100);
                                                        if (i <= 25) {
                                                            block.setType(Material.DIRT);
                                                        }
                                                        if (e.getScoreboardTags().contains("Hunger:9")) {
                                                            e.removeScoreboardTag("Hunger:9");
                                                            e.addScoreboardTag("Hunger:10");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:8")) {
                                                            e.removeScoreboardTag("Hunger:8");
                                                            e.addScoreboardTag("Hunger:9");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:7")) {
                                                            e.removeScoreboardTag("Hunger:7");
                                                            e.addScoreboardTag("Hunger:8");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:6")) {
                                                            e.removeScoreboardTag("Hunger:6");
                                                            e.addScoreboardTag("Hunger:7");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:5")) {
                                                            e.removeScoreboardTag("Hunger:5");
                                                            e.addScoreboardTag("Hunger:6");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:4")) {
                                                            e.removeScoreboardTag("Hunger:4");
                                                            e.addScoreboardTag("Hunger:5");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:3")) {
                                                            e.removeScoreboardTag("Hunger:3");
                                                            e.addScoreboardTag("Hunger:4");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:2")) {
                                                            e.removeScoreboardTag("Hunger:2");
                                                            e.addScoreboardTag("Hunger:3");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:1")) {
                                                            e.removeScoreboardTag("Hunger:1");
                                                            e.addScoreboardTag("Hunger:2");
                                                            return;
                                                        } else if (e.getScoreboardTags().contains("Hunger:0")) {
                                                            e.removeScoreboardTag("Hunger:0");
                                                            e.addScoreboardTag("Hunger:1");
                                                            if (e.getScoreboardTags().contains("DayH-1")) {
                                                                e.removeScoreboardTag("DayH-1");
                                                                return;
                                                            }
                                                            if (e.getScoreboardTags().contains("DayH-2")) {
                                                                e.removeScoreboardTag("DayH-2");
                                                                return;
                                                            }
                                                            if (e.getScoreboardTags().contains("DayH-3")) {
                                                                e.removeScoreboardTag("DayH-3");
                                                                return;

                                                            } else {
                                                                return;
                                                            }
                                                        }
                                                    }
                                                }, 100);
                                                break;

                                            }
                                            break;
                                        }
                                    }
                                }
                            } break;
                        } ++in;
                    }
                }
            }
        }.runTaskTimer(plugin, 560, 560);
    }

    public void poop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                World world = getServer().getWorld("world");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        Random rnd = new Random();
                        int i = rnd.nextInt(100);
                        if (i <= 15) {
                            Block loc = e.getLocation().getBlock();
                            Material block = loc.getType();
                            String s = "http://textures.minecraft.net/texture/9b3b1f785f01753c45ef97fcffffb3f52658ffceb17ad3f7b592945c6df2fa";
                            if (block == Material.AIR) {
                                SkullCreator.blockWithUrl(loc, s);
                                loc.setMetadata("Poop", new FixedMetadataValue(plugin, "Poop"));
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 6000, 6000);
    }

    public void horseRiding(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                int i = 1;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getVehicle() == null) {
                        return;
                    }
                    Entity e = p.getVehicle();
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
                                if (i == 90) {
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
                                    for (String tb : getConfig().getStringList("Bad")) {
                                        if (e.getScoreboardTags().contains(tb)) {
                                            e.removeScoreboardTag("Trait:" + tb);
                                            for (String trts : getConfig().getStringList("Good")) {
                                                List tr = Arrays.asList(trts);
                                                Random rn = new Random();
                                                String rnt = (String) tr.get(rn.nextInt(tr.size()));
                                                e.addScoreboardTag("Trait:" + rnt);
                                                p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.GREEN + "Your horse has lost there bad trait!");
                                                break;
                                            }
                                        } break;
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
        }.runTaskTimer(plugin, 3600, 3600);
    }













    // Get config file
    public FileConfiguration getBreedsConfig() {
        return this.breedsConfig;

    }
    public FileConfiguration getTraitConfig() {
        return this.traitConfig;
    }
    private void createCustomConfig() {
        breedsConfigFile = new File(getDataFolder(), "Breeds.yml");
        traitConfigFile = new File(getDataFolder(), "Traits.yml");
        if (!breedsConfigFile.exists()) {
            breedsConfigFile.getParentFile().mkdirs();
            saveResource("Breeds.yml", false);
        } if (!traitConfigFile.exists()) {
            traitConfigFile.getParentFile().mkdirs();
            saveResource("Traits.yml", false);
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

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin Has Been Disabled!  Good Bye!");
    }
}
