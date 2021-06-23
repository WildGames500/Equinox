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


import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;


public class HorseGUI implements Listener {
    public static HashMap<UUID, String> breedname = new HashMap<UUID, String>();
    public static HashMap<UUID, String> gendername = new HashMap<UUID, String>();
    public static HashMap<UUID, String> coatcolor = new HashMap<UUID, String>();
    public static HashMap<UUID, String> coatstyle = new HashMap<UUID, String>();
    public static HashMap<UUID, String> disciplines = new HashMap<UUID, String>();
    private Equinox plugin;
    public HorseGUI(Equinox equinox) {
        this.plugin = equinox;
    }



    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Economy eco = Equinox.getEconomy();


        if (e.getView().getTitle().equals("§0Menu")) {
            e.setCancelled(true);
            UUID uuid = p.getUniqueId();
            String breed = breedname.get(uuid);
            String gender = gendername.get(uuid);
            String coat = coatcolor.get(uuid);
            String coats = coatstyle.get(uuid);
            String disc = disciplines.get(uuid);
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
                if (gendername.isEmpty()) {
                    metaref1.setDisplayName("§bGender");
                } else {
                    metaref2.setDisplayName(gender);
                }
                if (breedname.isEmpty()) {
                    metaref2.setDisplayName("§bBreed");
                } else {
                    metaref2.setDisplayName(breed);
                }
                if (coatcolor.isEmpty()) {
                    metaref3.setDisplayName("§bCoat Color");
                } else {
                    metaref3.setDisplayName(coat);
                }
                if (coatstyle.isEmpty()) {
                    metaref4.setDisplayName("§bCoat Pattern");
                } else {
                    metaref4.setDisplayName(coats);
                }
                if (disciplines.isEmpty()) {
                    metaref5.setDisplayName("§bDiscipline");
                } else {
                    metaref5.setDisplayName(disc);
                }
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
            int slot = 0;
            if (e.getSlot() == 1) {
                p.closeInventory();

                Inventory gn = getServer().createInventory(null, 9, "§0Genders");

                ItemStack ref1 = new ItemStack(Material.PAPER);
                ItemStack ref2 = new ItemStack(Material.PAPER);
                ItemStack ref3 = new ItemStack(Material.PAPER);
                ItemMeta metaref1 = ref1.getItemMeta();
                ItemMeta metaref2 = ref2.getItemMeta();
                ItemMeta metaref3 = ref3.getItemMeta();
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                metaref1.setDisplayName("Mare");
                metaref2.setDisplayName("Stallion");
                metaref3.setDisplayName("Gelding");
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                gn.setItem(0, ref1);
                gn.setItem(1, ref2);
                gn.setItem(2, ref3);

                p.openInventory(gn);
            }
            else if (e.getSlot() == 2) {
                p.closeInventory();
                
                Inventory br = getServer().createInventory(null, 36, "§0Breeds");

                for (String brds : plugin.getBreedsConfig().getStringList("Breeds")) {

                    ItemStack ref1 = new ItemStack(Material.PAPER);
                    ItemMeta metaref1 = ref1.getItemMeta();
                    ref1.setItemMeta(metaref1);
                    metaref1.setDisplayName(String.valueOf(brds));
                    ref1.setItemMeta(metaref1);
                    br.setItem(slot, ref1);
                    slot += 1;

                } p.openInventory(br);
            } else if (e.getSlot() == 3) {
                p.closeInventory();

                Inventory gn = getServer().createInventory(null, 9, "§0Coat Colors");

                ItemStack ref1 = new ItemStack(Material.PAPER);
                ItemStack ref2 = new ItemStack(Material.PAPER);
                ItemStack ref3 = new ItemStack(Material.PAPER);
                ItemStack ref4 = new ItemStack(Material.PAPER);
                ItemStack ref5 = new ItemStack(Material.PAPER);
                ItemStack ref6 = new ItemStack(Material.PAPER);
                ItemMeta metaref1 = ref1.getItemMeta();
                ItemMeta metaref2 = ref2.getItemMeta();
                ItemMeta metaref3 = ref3.getItemMeta();
                ItemMeta metaref4 = ref4.getItemMeta();
                ItemMeta metaref5 = ref5.getItemMeta();
                ItemMeta metaref6 = ref6.getItemMeta();
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref5.setItemMeta(metaref5);
                ref6.setItemMeta(metaref6);
                metaref1.setDisplayName("Bay");
                metaref2.setDisplayName("Black");
                metaref3.setDisplayName("Buckskin");
                metaref4.setDisplayName("Chestnut");
                metaref5.setDisplayName("Palomino");
                metaref6.setDisplayName("White");
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref5.setItemMeta(metaref5);
                ref6.setItemMeta(metaref6);
                gn.setItem(0, ref1);
                gn.setItem(1, ref2);
                gn.setItem(2, ref3);
                gn.setItem(3, ref4);
                gn.setItem(4, ref5);
                gn.setItem(5, ref6);

                p.openInventory(gn);

            } else if (e.getSlot() == 4) {
                p.closeInventory();

                Inventory gn = getServer().createInventory(null, 9, "§0Coat Patterns");

                ItemStack ref1 = new ItemStack(Material.PAPER);
                ItemStack ref2 = new ItemStack(Material.PAPER);
                ItemStack ref3 = new ItemStack(Material.PAPER);
                ItemStack ref4 = new ItemStack(Material.PAPER);
                ItemStack ref5 = new ItemStack(Material.PAPER);
                ItemMeta metaref1 = ref1.getItemMeta();
                ItemMeta metaref2 = ref2.getItemMeta();
                ItemMeta metaref3 = ref3.getItemMeta();
                ItemMeta metaref4 = ref4.getItemMeta();
                ItemMeta metaref5 = ref5.getItemMeta();
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref5.setItemMeta(metaref5);
                metaref1.setDisplayName("None");
                metaref2.setDisplayName("Blaze");
                metaref3.setDisplayName("Cresent");
                metaref4.setDisplayName("Paint");
                metaref5.setDisplayName("Star");
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref5.setItemMeta(metaref5);
                gn.setItem(0, ref1);
                gn.setItem(1, ref2);
                gn.setItem(2, ref3);
                gn.setItem(3, ref4);
                gn.setItem(4, ref5);

                p.openInventory(gn);
            } else if (e.getSlot() == 5) {
                p.closeInventory();

                Inventory ds = getServer().createInventory(null, 18, "§0Disciplines");
                for (String brds : plugin.getDiscConfig().getStringList("Disciplines")) {

                    ItemStack ref1 = new ItemStack(Material.PAPER);
                    ItemMeta metaref1 = ref1.getItemMeta();
                    ref1.setItemMeta(metaref1);
                    metaref1.setDisplayName(String.valueOf(brds));
                    ref1.setItemMeta(metaref1);
                    ds.setItem(slot, ref1);
                    slot += 1;

                } p.openInventory(ds);
            }
        } else if (e.getView().getTitle().equals("§0Genders")) {
            e.setCancelled(true);
            UUID uuid = p.getUniqueId();
            String breed = breedname.get(uuid);
            String coat = coatcolor.get(uuid);
            String coats = coatstyle.get(uuid);
            String disc = disciplines.get(uuid);
            String sln = e.getCurrentItem().getItemMeta().getDisplayName();
            gendername.put(p.getUniqueId(), sln);
            if (e.getSlot() >= 0) {
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
                metaref1.setDisplayName(sln);
                if (breedname.isEmpty()) {
                    metaref2.setDisplayName("§bBreeds");
                } else {
                    metaref2.setDisplayName(breed);
                }
                if (coatcolor.isEmpty()) {
                    metaref3.setDisplayName("§bCoat Color");
                } else {
                    metaref3.setDisplayName(coat);
                }
                if (coatstyle.isEmpty()) {
                    metaref4.setDisplayName("§bCoat Pattern");
                } else {
                    metaref4.setDisplayName(coats);
                }
                if (disciplines.isEmpty()) {
                    metaref5.setDisplayName("§bDiscipline");
                } else {
                    metaref5.setDisplayName(disc);
                }
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

        } else if (e.getView().getTitle().equals("§0Breeds")) {
            e.setCancelled(true);
            String sln = e.getCurrentItem().getItemMeta().getDisplayName();
            UUID uuid = p.getUniqueId();
            String gender = gendername.get(uuid);
            String coat = coatcolor.get(uuid);
            String coats = coatstyle.get(uuid);
            String disc = disciplines.get(uuid);
            breedname.put(p.getUniqueId(), sln);
            if (e.getSlot() >= 0) {
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
                if (gendername.isEmpty()) {
                    metaref1.setDisplayName("§bGender");
                } else {
                    metaref1.setDisplayName(gender);
                }
                metaref2.setDisplayName(sln);
                if (coatcolor.isEmpty()) {
                    metaref3.setDisplayName("§bCoat Color");
                } else {
                    metaref3.setDisplayName(coat);
                }
                if (coatstyle.isEmpty()) {
                    metaref4.setDisplayName("§bCoat Pattern");
                } else {
                    metaref4.setDisplayName(coats);
                }
                if (disciplines.isEmpty()) {
                    metaref5.setDisplayName("§bDiscipline");
                } else {
                    metaref5.setDisplayName(disc);
                }
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
        } else if (e.getView().getTitle().equals("§0Coat Colors")) {
            e.setCancelled(true);
            String sln = e.getCurrentItem().getItemMeta().getDisplayName();
            UUID uuid = p.getUniqueId();
            String gender = gendername.get(uuid);
            String breed = breedname.get(uuid);
            String coats = coatstyle.get(uuid);
            String disc = disciplines.get(uuid);
            coatcolor.put(p.getUniqueId(), sln);
            if (e.getSlot() >= 0) {
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
                if (gendername.isEmpty()) {
                    metaref1.setDisplayName("§bGender");
                } else {
                    metaref1.setDisplayName(gender);
                }
                if (breedname.isEmpty()) {
                    metaref2.setDisplayName("§bBreed");
                } else {
                    metaref2.setDisplayName(breed);
                }
                metaref3.setDisplayName(sln);
                if (coatstyle.isEmpty()) {
                    metaref4.setDisplayName("§bCoat Pattern");
                } else {
                    metaref4.setDisplayName(coats);
                }
                if (disciplines.isEmpty()) {
                    metaref5.setDisplayName("§bDiscipline");
                } else {
                    metaref5.setDisplayName(disc);
                }
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

        } else if (e.getView().getTitle().equals("§0Coat Patterns")) {
            e.setCancelled(true);
            String sln = e.getCurrentItem().getItemMeta().getDisplayName();
            UUID uuid = p.getUniqueId();
            String gender = gendername.get(uuid);
            String breed = breedname.get(uuid);
            String coat = coatcolor.get(uuid);
            String disc = disciplines.get(uuid);
            coatstyle.put(p.getUniqueId(), sln);
            if (e.getSlot() >= 0) {
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
                if (gendername.isEmpty()) {
                    metaref1.setDisplayName("§bGender");
                } else {
                    metaref1.setDisplayName(gender);
                }
                if (breedname.isEmpty()) {
                    metaref2.setDisplayName("§bBreed");
                } else {
                    metaref2.setDisplayName(breed);
                }
                if (coatcolor.isEmpty()) {
                    metaref3.setDisplayName("§bCoat Color");
                } else {
                    metaref3.setDisplayName(coat);
                }
                metaref4.setDisplayName(sln);
                if (disciplines.isEmpty()){
                    metaref5.setDisplayName("§bDiscipline");
                } else {
                    metaref5.setDisplayName(disc);
                }
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
        } else if (e.getView().getTitle().equals("§0Disciplines")) {
            e.setCancelled(true);
            String sln = e.getCurrentItem().getItemMeta().getDisplayName();
            UUID uuid = p.getUniqueId();
            String gender = gendername.get(uuid);
            String breed = breedname.get(uuid);
            String coat = coatcolor.get(uuid);
            String coats = coatstyle.get(uuid);
            disciplines.put(p.getUniqueId(), sln);
            if (e.getSlot() >= 0) {
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
                if (gendername.isEmpty()) {
                    metaref1.setDisplayName("§bGender");
                } else {
                    metaref1.setDisplayName(gender);
                }
                if (breedname.isEmpty()) {
                    metaref2.setDisplayName("§bBreed");
                } else {
                    metaref2.setDisplayName(breed);
                }
                if (coatcolor.isEmpty()) {
                    metaref3.setDisplayName("§bCoat Color");
                } else {
                    metaref3.setDisplayName(coat);
                }
                if (coatstyle.isEmpty()) {
                    metaref4.setDisplayName("§bCoat Pattern");
                } else {
                    metaref4.setDisplayName(coats);
                }
                metaref5.setDisplayName(sln);
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
        }
    }
}