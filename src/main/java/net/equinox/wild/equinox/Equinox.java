package net.equinox.wild.equinox;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public final class Equinox extends JavaPlugin {
    private static Economy econ = null;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events1(), this);
        getServer().getPluginManager().registerEvents(new HorseGUI(), this);
        this.getCommand("eq").setExecutor(new Commands(this));
        getLogger().info("Plugin Has Been Enabled! Hello ^-^");
        if (!setupEconomy() ) {
            System.out.println("No Economy Plugin Found! Disabling Vault...");
            getServer().getPluginManager().disablePlugin(this);
            return;
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
