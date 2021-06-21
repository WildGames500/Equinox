package net.equinox.wild.equinox;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class Equinox extends JavaPlugin {
    private static Economy econ = null;
    private File breedsConfigFile;
    private FileConfiguration breedsConfig;

    @Override
    public void onEnable() {
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
    public FileConfiguration getCustomConfig() {
        return this.breedsConfig;

    }
    private void createCustomConfig() {
        breedsConfigFile = new File(getDataFolder(), "Breeds.yml");
        if (!breedsConfigFile.exists()) {
            breedsConfigFile.getParentFile().mkdirs();
            saveResource("Breeds.yml", false);
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
