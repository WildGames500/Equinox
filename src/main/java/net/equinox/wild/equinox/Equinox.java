package net.equinox.wild.equinox;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


import java.io.File;
import java.io.IOException;
import java.util.Random;


public final class Equinox extends JavaPlugin {
    private static Economy econ = null;
    private File breedsConfigFile;
    private File traitConfigFile;
    private FileConfiguration breedsConfig;
    private FileConfiguration traitConfig;


    @Override
    public void onEnable() {
        testLoop(this);
        createCustomConfig();
        getServer().getPluginManager().registerEvents(new Events1(), this);
        getServer().getPluginManager().registerEvents(new HorseGUI(this), this);
        this.getCommand("eq").setExecutor(new Commands(this));
        getLogger().info("Plugin Has Been Enabled! Hello ^-^");
        if (!setupEconomy()) {
            System.out.println("No Economy Plugin Found! Disabling Vault...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }



    //Loop Test
    public void testLoop(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                World world = getServer().getWorld("world");
                for (Entity e : world.getEntities()) {
                    Random rnd = new Random();
                    int i = rnd.nextInt(100);
                    if (e instanceof Horse) {
                        if(i <= 65) {
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
        }.runTaskTimer(plugin, 0, 24000);
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
