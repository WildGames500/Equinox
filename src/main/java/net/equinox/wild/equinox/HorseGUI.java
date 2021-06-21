package net.equinox.wild.equinox;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.bukkit.Bukkit.getServer;


public class HorseGUI implements Listener {
    File breeds = new File("plugins/Equinox/Breeds.yml");

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Economy eco = Equinox.getEconomy();

        if (e.getView().getTitle().equals("§0Menu")) {
            e.setCancelled(true);
            if (e.getSlot() == 3) {
                p.closeInventory();
                Inventory hc = getServer().createInventory(null, 27, "§0Horse Creation");

                ItemStack ref1 = new ItemStack(Material.BOOK);
                ItemStack ref2 = new ItemStack(Material.BOOK);
                ItemStack ref3 = new ItemStack(Material.BOOK);
                ItemStack ref4 = new ItemStack(Material.BOOK);
                ItemStack ref5 = new ItemStack(Material.BOOK);
                ItemStack ref6 = new ItemStack(Material.BOOK);
                ItemStack ref7 = new ItemStack(Material.BOOK);
                ItemStack ref8 = new ItemStack(Material.YELLOW_TERRACOTTA);

                ItemMeta metaref1 = ref1.getItemMeta();
                ItemMeta metaref2 = ref2.getItemMeta();
                ItemMeta metaref3 = ref3.getItemMeta();
                ItemMeta metaref4 = ref4.getItemMeta();
                ItemMeta metaref5 = ref5.getItemMeta();
                ItemMeta metaref6 = ref6.getItemMeta();
                ItemMeta metaref7 = ref7.getItemMeta();
                ItemMeta metaref8 = ref8.getItemMeta();

                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref5.setItemMeta(metaref5);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);
                metaref1.setDisplayName("§bGender");
                metaref2.setDisplayName("§bBreed");
                metaref3.setDisplayName("§bCoat Color");
                metaref4.setDisplayName("§bCoat Pattern");
                metaref5.setDisplayName("§bDiscipline");
                metaref6.setDisplayName("§bSpeed");
                metaref7.setDisplayName("§bJump");
                metaref8.setDisplayName("§ePurchase");
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref5.setItemMeta(metaref5);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);

                hc.setItem(1, ref1);
                hc.setItem(2, ref2);
                hc.setItem(3, ref3);
                hc.setItem(4, ref4);
                hc.setItem(5, ref5);
                hc.setItem(6, ref6);
                hc.setItem(7, ref7);
                hc.setItem(25, ref8);

                p.openInventory(hc);


            }
        } else if (e.getView().getTitle().equals("§0Horse Creation")) {
            e.setCancelled(true);
            List brds = Collections.singletonList(breeds.list().equals("Breeds"));
            if (e.getSlot() == 1) {
                p.closeInventory();
                
                Inventory br = getServer().createInventory(null, 27, "§0Breeds");

                ItemStack ref1 = new ItemStack(Material.PAPER);
                ItemMeta metaref1 = ref1.getItemMeta();
                ref1.setItemMeta(metaref1);
                metaref1.setDisplayName(String.valueOf(brds));
                ref1.setItemMeta(metaref1);
                br.setItem(1, ref1);

                p.openInventory(br);
            }
        }
    }
}