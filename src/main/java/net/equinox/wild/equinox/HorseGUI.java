package net.equinox.wild.equinox;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static org.bukkit.Bukkit.getServer;

@SuppressWarnings("all")
public class HorseGUI implements Listener {
    public static HashMap<UUID, String> breedname = new HashMap<UUID, String>();
    public static HashMap<UUID, String> gendername = new HashMap<UUID, String>();
    public static HashMap<UUID, String> coatcolor = new HashMap<UUID, String>();
    public static HashMap<UUID, String> coatstyle = new HashMap<UUID, String>();
    public static HashMap<UUID, String> speeds = new HashMap<UUID, String>();
    public static HashMap<UUID, String> jumpheight = new HashMap<UUID, String>();
    public static HashMap<UUID, Integer> purchase = new HashMap<UUID, Integer>();
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
            if (breedname.get(uuid) ==  null) {
                breedname.put(uuid, "§bBreeds");
            }
            if (gendername.get(uuid) ==  null) {
                gendername.put(uuid, "§bGender");
            }
            if (coatcolor.get(uuid) ==  null) {
                coatcolor.put(uuid, "§bCoat Color");
            }
            if (coatcolor.get(uuid) ==  null) {
                coatcolor.put(uuid, "§bCoat Color");
            }
            if (coatstyle.get(uuid) ==  null) {
                coatstyle.put(uuid, "§bCoat Pattern");
            }
            if (speeds.get(uuid) ==  null) {
                speeds.put(uuid, "§bSpeed");
            }
            String breed = breedname.get(uuid);
            String gender = gendername.get(uuid);
            String coat = coatcolor.get(uuid);
            String coats = coatstyle.get(uuid);
            String speed = speeds.get(uuid);
            String jump = jumpheight.get(uuid);
            int buy11 = 2200;
            int buy12 = 2400;
            int buy13 = 2800;
            int buy14 = 3200;
            int buy15 = 3500;
            int buy21 = 5200;
            int buy22 = 5400;
            int buy23 = 5800;
            int buy24 = 6200;
            int buy25 = 6500;
            int buy31 = 15200;
            int buy32 = 15400;
            int buy33 = 15800;
            int buy34 = 16200;
            int buy35 = 16500;
            int buy41 = 20200;
            int buy42 = 20400;
            int buy43 = 20800;
            int buy44 = 21200;
            int buy45 = 21500;
            int buy51 = 40200;
            int buy52 = 40400;
            int buy53 = 40800;
            int buy54 = 41200;
            int buy55 = 41500;
            int buy61 = 80200;
            int buy62 = 80400;
            int buy63 = 80800;
            int buy64 = 81200;
            int buy65 = 81500;
            if (e.getSlot() == 3) {
                p.closeInventory();
                Inventory hc = getServer().createInventory(null, 27, "§0Horse Creation");

                ItemStack ref1 = new ItemStack(Material.BOOK);
                ItemStack ref2 = new ItemStack(Material.BOOK);
                ItemStack ref3 = new ItemStack(Material.BOOK);
                ItemStack ref4 = new ItemStack(Material.BOOK);
                ItemStack ref6 = new ItemStack(Material.BOOK);
                ItemStack ref7 = new ItemStack(Material.BOOK);
                ItemStack ref8 = new ItemStack(Material.YELLOW_TERRACOTTA);

                ItemMeta metaref1 = ref1.getItemMeta();
                ItemMeta metaref2 = ref2.getItemMeta();
                ItemMeta metaref3 = ref3.getItemMeta();
                ItemMeta metaref4 = ref4.getItemMeta();
                ItemMeta metaref6 = ref6.getItemMeta();
                ItemMeta metaref7 = ref7.getItemMeta();
                ItemMeta metaref8 = ref8.getItemMeta();

                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);
                if (!gendername.isEmpty()) {
                    metaref1.setDisplayName(gender);
                } else {
                    metaref1.setDisplayName("§bGender");
                }if (!breedname.isEmpty()) {
                    metaref2.setDisplayName(breed);
                } else {
                    metaref2.setDisplayName("§bBreeds");
                }
                if (!coatcolor.isEmpty()) {
                    metaref3.setDisplayName(coat);
                } else {
                    metaref3.setDisplayName("§bCoat Color");
                }
                if (!coatstyle.isEmpty()) {
                    metaref4.setDisplayName(coats);
                } else {
                    metaref4.setDisplayName("§bCoat Pattern");
                }
                if (!speeds.isEmpty()) {
                    metaref6.setDisplayName(speed);
                    if (speed == null) {
                        metaref6.setDisplayName("§bSpeed");
                    }
                } if (speeds == null){
                    metaref6.setDisplayName("§bSpeed");
                } else {
                    metaref6.setDisplayName("§bSpeed");
                }
                if (!jumpheight.isEmpty()) {
                    metaref7.setDisplayName(jump);
                } else {
                    metaref7.setDisplayName("§bJump");
                }
                if (!jumpheight.isEmpty())  {
                    if (speed.equals("Tier 1")) {
                        if (jump.equals("1ft")) {
                            purchase.put(p.getUniqueId(), buy11);
                            metaref8.setDisplayName(String.valueOf(buy11));
                        } else if (jump.equals("2ft")) {
                            purchase.put(p.getUniqueId(), buy12);
                            metaref8.setDisplayName(String.valueOf(buy12));
                        } else if (jump.equals("3ft")) {
                            purchase.put(p.getUniqueId(), buy13);
                            metaref8.setDisplayName(String.valueOf(buy13));
                        } else if (jump.equals("4ft")) {
                            purchase.put(p.getUniqueId(), buy14);
                            metaref8.setDisplayName(String.valueOf(buy14));
                        }else if (jump.equals("5ft")) {
                            purchase.put(p.getUniqueId(), buy15);
                            metaref8.setDisplayName(String.valueOf(buy15));
                        }
                    } else if (speed.equals("Tier 2")) {
                        if (jump.equals("1ft")) {
                            purchase.put(p.getUniqueId(), buy21);
                            metaref8.setDisplayName(String.valueOf(buy21));
                        } else if (jump.equals("2ft")) {
                            purchase.put(p.getUniqueId(), buy22);
                            metaref8.setDisplayName(String.valueOf(buy22));
                        } else if (jump.equals("3ft")) {
                            purchase.put(p.getUniqueId(), buy23);
                            metaref8.setDisplayName(String.valueOf(buy23));
                        } else if (jump.equals("4ft")) {
                            purchase.put(p.getUniqueId(), buy24);
                            metaref8.setDisplayName(String.valueOf(buy24));
                        } else if (jump.equals("5ft")) {
                            purchase.put(p.getUniqueId(), buy25);
                            metaref8.setDisplayName(String.valueOf(buy25));
                        }
                    } else if (speed.equals("Tier 3")) {
                        if (jump.equals("1ft")) {
                            purchase.put(p.getUniqueId(), buy31);
                            metaref8.setDisplayName(String.valueOf(buy31));
                        } else if (jump.equals("2ft")) {
                            purchase.put(p.getUniqueId(), buy32);
                            metaref8.setDisplayName(String.valueOf(buy32));
                        } else if (jump.equals("3ft")) {
                            purchase.put(p.getUniqueId(), buy33);
                            metaref8.setDisplayName(String.valueOf(buy33));
                        } else if (jump.equals("4ft")) {
                            purchase.put(p.getUniqueId(), buy34);
                            metaref8.setDisplayName(String.valueOf(buy34));
                        } else if (jump.equals("5ft")) {
                            purchase.put(p.getUniqueId(), buy35);
                            metaref8.setDisplayName(String.valueOf(buy35));
                        }
                    } else if (speed.equals("Tier 4")) {
                        if (jump.equals("1ft")) {
                            purchase.put(p.getUniqueId(), buy41);
                            metaref8.setDisplayName(String.valueOf(buy41));
                        } else if (jump.equals("2ft")) {
                            purchase.put(p.getUniqueId(), buy42);
                            metaref8.setDisplayName(String.valueOf(buy42));
                        } else if (jump.equals("3ft")) {
                            purchase.put(p.getUniqueId(), buy43);
                            metaref8.setDisplayName(String.valueOf(buy43));
                        } else if (jump.equals("4ft")) {
                            purchase.put(p.getUniqueId(), buy44);
                            metaref8.setDisplayName(String.valueOf(buy44));
                        } else if (jump.equals("5ft")) {
                            purchase.put(p.getUniqueId(), buy45);
                            metaref8.setDisplayName(String.valueOf(buy45));
                        }
                    } else if (speed.equals("Tier 5")) {
                        if (jump.equals("1ft")) {
                            purchase.put(p.getUniqueId(), buy51);
                            metaref8.setDisplayName(String.valueOf(buy51));
                        } else if (jump.equals("2ft")) {
                            purchase.put(p.getUniqueId(), buy52);
                            metaref8.setDisplayName(String.valueOf(buy52));
                        } else if (jump.equals("3ft")) {
                            purchase.put(p.getUniqueId(), buy53);
                            metaref8.setDisplayName(String.valueOf(buy53));
                        } else if (jump.equals("4ft")) {
                            purchase.put(p.getUniqueId(), buy54);
                            metaref8.setDisplayName(String.valueOf(buy54));
                        } else if (jump.equals("5ft")) {
                            purchase.put(p.getUniqueId(), buy55);
                            metaref8.setDisplayName(String.valueOf(buy55));
                        }
                    } else if (speed.equals("Tier 6")) {
                        if (jump.equals("1ft")) {
                            purchase.put(p.getUniqueId(), buy61);
                            metaref8.setDisplayName(String.valueOf(buy61));
                        } else if (jump.equals("2ft")) {
                            purchase.put(p.getUniqueId(), buy62);
                            metaref8.setDisplayName(String.valueOf(buy62));
                        } else if (jump.equals("3ft")) {
                            purchase.put(p.getUniqueId(), buy63);
                            metaref8.setDisplayName(String.valueOf(buy63));
                        } else if (jump.equals("4ft")) {
                            purchase.put(p.getUniqueId(), buy64);
                            metaref8.setDisplayName(String.valueOf(buy64));
                        } else if (jump.equals("5ft")) {
                            purchase.put(p.getUniqueId(), buy65);
                            metaref8.setDisplayName(String.valueOf(buy65));
                        }
                    } else if (speed == null){
                        purchase.put(p.getUniqueId(), 0);
                        metaref8.setDisplayName(String.valueOf(0));
                    } else {
                        purchase.put(p.getUniqueId(), 0);
                        metaref8.setDisplayName(String.valueOf(0));
                    }
                } else {
                    metaref8.setDisplayName("§ePurchase");
                }
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);

                hc.setItem(1, ref1);
                hc.setItem(2, ref2);
                hc.setItem(3, ref3);
                hc.setItem(4, ref4);
                hc.setItem(5, ref6);
                hc.setItem(6, ref7);
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
                ItemStack ref7 = new ItemStack(Material.PAPER);
                ItemMeta metaref1 = ref1.getItemMeta();
                ItemMeta metaref2 = ref2.getItemMeta();
                ItemMeta metaref3 = ref3.getItemMeta();
                ItemMeta metaref4 = ref4.getItemMeta();
                ItemMeta metaref5 = ref5.getItemMeta();
                ItemMeta metaref6 = ref6.getItemMeta();
                ItemMeta metaref7 = ref7.getItemMeta();
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref5.setItemMeta(metaref5);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                metaref1.setDisplayName("Bay");
                metaref2.setDisplayName("Black");
                metaref3.setDisplayName("Buckskin");
                metaref4.setDisplayName("Chestnut");
                metaref5.setDisplayName("Palomino");
                metaref6.setDisplayName("White");
                metaref7.setDisplayName("Gray");
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref5.setItemMeta(metaref5);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                gn.setItem(0, ref1);
                gn.setItem(1, ref2);
                gn.setItem(2, ref3);
                gn.setItem(3, ref4);
                gn.setItem(4, ref5);
                gn.setItem(5, ref6);
                gn.setItem(6, ref7);

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
                metaref3.setDisplayName("Snip");
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

                Inventory sp = getServer().createInventory(null, 9, "§0Speeds");
                ItemStack ref1 = new ItemStack(Material.PAPER);
                ItemStack ref2 = new ItemStack(Material.PAPER);
                ItemStack ref3 = new ItemStack(Material.PAPER);
                ItemStack ref4 = new ItemStack(Material.PAPER);
                ItemStack ref5 = new ItemStack(Material.PAPER);
                ItemStack ref6 = new ItemStack(Material.PAPER);
                ItemStack ref7 = new ItemStack(Material.PAPER);
                ItemMeta metaref1 = ref1.getItemMeta();
                ItemMeta metaref2 = ref2.getItemMeta();
                ItemMeta metaref3 = ref3.getItemMeta();
                ItemMeta metaref4 = ref4.getItemMeta();
                ItemMeta metaref5 = ref5.getItemMeta();
                ItemMeta metaref6 = ref6.getItemMeta();
                ItemMeta metaref7 = ref7.getItemMeta();
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref5.setItemMeta(metaref5);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                metaref1.setDisplayName("Tier 1");
                metaref2.setDisplayName("Tier 2");
                metaref3.setDisplayName("Tier 3");
                metaref4.setDisplayName("Tier 4");
                metaref5.setDisplayName("Tier 5");
                metaref6.setDisplayName("Tier 6");
                if (p.hasPermission("eq.op")) {
                    metaref7.setDisplayName("Tier 7");
                }
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref5.setItemMeta(metaref5);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                sp.setItem(0, ref1);
                sp.setItem(1, ref2);
                sp.setItem(2, ref3);
                sp.setItem(3, ref4);
                sp.setItem(4, ref5);
                sp.setItem(5, ref6);
                sp.setItem(6, ref7);
                p.openInventory(sp);
            } else if (e.getSlot() == 6){
                p.closeInventory();

                Inventory sp = getServer().createInventory(null, 9, "§0Jumps");
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
                metaref1.setDisplayName("1ft");
                metaref2.setDisplayName("2ft");
                metaref3.setDisplayName("3ft");
                metaref4.setDisplayName("4ft");
                metaref5.setDisplayName("5ft");
                metaref6.setDisplayName("6ft");
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref5.setItemMeta(metaref5);
                ref6.setItemMeta(metaref6);
                sp.setItem(0, ref1);
                sp.setItem(1, ref2);
                sp.setItem(2, ref3);
                sp.setItem(3, ref4);
                sp.setItem(4, ref5);
                if (p.hasPermission("eq.op")) {
                    sp.setItem(5, ref6);
                }
                p.openInventory(sp);
            } else if (e.getSlot() == 25) {
                Location loc = p.getLocation();
                World world = p.getWorld();
                UUID uuid = p.getUniqueId();
                String gender = gendername.get(uuid);
                String breed = breedname.get(uuid);
                String coat = coatcolor.get(uuid);
                String coats = coatstyle.get(uuid);
                String speed = speeds.get(uuid);
                String jump = jumpheight.get(uuid);

//                System.out.printf("THIS SHOULD NOT BE NULL: { Purchase: %s , UUID: %s }", purchase, uuid);
                int cost = purchase.get(uuid);
                PlayerInventory menu = p.getInventory();
                Random r = new Random();
                double t7 = 0.65 + (0.75 - 0.65) * r.nextDouble();
                double t6 = 0.55 + (0.65 - 0.55) * r.nextDouble();
                double t5 = 0.45 + (0.55 - 0.45) * r.nextDouble();
                double t4 = 0.35 + (0.45 - 0.35) * r.nextDouble();
                double t3 = 0.25 + (0.35 - 0.25) * r.nextDouble();
                double t2 = 0.15 + (0.25 - 0.15) * r.nextDouble();
                double t1 = 0.009 + (0.15 - 0.009) * r.nextDouble();
                if (!purchase.isEmpty()) {
                    double bal = eco.getBalance(p);
                    if (bal >= cost) {
                        eco.withdrawPlayer(p, cost);
                        if (!breedname.isEmpty()) {
                            if (breedname.size() >= 1 && breedname.containsValue("Donkey")) {
                                Donkey h = (Donkey) world.spawnEntity(loc, EntityType.DONKEY);
                                plugin.getDbContext().addHorseToDatabase(h, uuid.toString());
                                h.setTamed(true);
                                h.addScoreboardTag("Owner:" + uuid);
                                h.addScoreboardTag("Owned");
                                h.addScoreboardTag("Hunger:10");
                                h.addScoreboardTag("Thirst:10");
                                h.addScoreboardTag("Private");
                                h.addScoreboardTag("Level:0");
                                h.addScoreboardTag("XP:1");
                                h.addScoreboardTag("Age:4");
                                List<String> list = plugin.getTraitConfig().getStringList("Traits");
                                int index = new Random().nextInt(list.size());
                                String rnt = list.get(index);
                                h.addScoreboardTag("Trait:" + rnt);
                                if (!breedname.isEmpty()) {
                                    h.addScoreboardTag("Breed:" + breed);
                                }
                                if (!gendername.isEmpty()) {
                                    h.addScoreboardTag("Gender:" + gender);
                                    if (gender.equals("Mare")) {
                                        h.addScoreboardTag("Heat1");
                                    }
                                }
                                 if (coat.equals("Bay")) {
                                    h.addScoreboardTag("Color:Bay");
                                }if (coats.equals("None")) {
                                    h.addScoreboardTag("Style:None");
                                }
                                if (speed.equals("Tier 7")) {
                                    h.addScoreboardTag("Speed:T7");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t7);
                                } else if (speed.equals("Tier 6")) {
                                    h.addScoreboardTag("Speed:T6");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t6);
                                } else if (speed.equals("Tier 5")) {
                                    h.addScoreboardTag("Speed:T5");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t5);
                                } else if (speed.equals("Tier 4")) {
                                    h.addScoreboardTag("Speed:T4");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t4);
                                } else if (speed.equals("Tier 3")) {
                                    h.addScoreboardTag("Speed:T3");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t3);
                                } else if (speed.equals("Tier 2")) {
                                    h.addScoreboardTag("Speed:T2");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t2);
                                } else if (speed.equals("Tier 1")) {
                                    h.addScoreboardTag("Speed:T1");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t1);
                                }else if (speed.equals(null)) {
                                    System.out.println("nulled");
                                }
                                if (jump.equals("1ft")) {
                                    h.addScoreboardTag("1ft");
                                    h.setJumpStrength(.517);
                                } else if (jump.equals("2ft")) {
                                    h.addScoreboardTag("2ft");
                                    h.setJumpStrength(.617);
                                } else if (jump.equals("3ft")) {
                                    h.addScoreboardTag("3ft");
                                    h.setJumpStrength(.717);
                                } else if (jump.equals("4ft")) {
                                    h.addScoreboardTag("4ft");
                                    h.setJumpStrength(.917);
                                } else if (jump.equals("5ft")) {
                                    h.addScoreboardTag("5ft");
                                    h.setJumpStrength(1.017);
                                } else if (jump.equals("6ft")) {
                                    h.addScoreboardTag("6ft");
                                    h.setJumpStrength(1.117);
                                }
                            } else {
                                Horse h = (Horse) world.spawnEntity(loc, EntityType.HORSE);
                                plugin.getDbContext().addHorseToDatabase(h, uuid.toString());
                                h.setTamed(true);
                                h.addScoreboardTag("Owner:" + uuid);
                                h.addScoreboardTag("Owned");
                                h.addScoreboardTag("Hunger:10");
                                h.addScoreboardTag("Thirst:10");
                                h.addScoreboardTag("Private");
                                h.addScoreboardTag("Level:0");
                                h.addScoreboardTag("XP:1");
                                h.addScoreboardTag("Age:4");
                                List<String> list = plugin.getTraitConfig().getStringList("Traits");
                                int index = new Random().nextInt(list.size());
                                String rnt = list.get(index);
                                h.addScoreboardTag("Trait:" + rnt);
                                if (!breedname.isEmpty()) {
                                    h.addScoreboardTag("Breed:" + breed);
                                }
                                if (!gendername.isEmpty()) {
                                    h.addScoreboardTag("Gender:" + gender);
                                    if (gender.equals("Mare")) {
                                        h.addScoreboardTag("Heat1");
                                    }
                                }
                                if (coat.equals("Black")) {
                                    h.setColor(Horse.Color.BLACK);
                                    h.addScoreboardTag("Color:Black");
                                } else if (coat.equals("Chestnut")) {
                                    h.addScoreboardTag("Color:Chestnut");
                                    h.setColor(Horse.Color.CHESTNUT);
                                } else if (coat.equals("Bay")) {
                                    h.addScoreboardTag("Color:Bay");
                                    h.setColor(Horse.Color.DARK_BROWN);
                                } else if (coat.equals("White")) {
                                    h.addScoreboardTag("Color:White");
                                    h.setColor(Horse.Color.WHITE);
                                } else if (coat.equals("Palomino")) {
                                    h.addScoreboardTag("Color:Palomino");
                                    h.setColor(Horse.Color.CREAMY);
                                } else if (coat.equals("Gray")) {
                                    h.addScoreboardTag("Color:Silver");
                                    h.setColor(Horse.Color.GRAY);
                                }else if (coat.equals("Buckskin")) {
                                    h.addScoreboardTag("Color:Buckskin");
                                    h.setColor(Horse.Color.BROWN);
                                }
                                if (coats.equals("Snip")) {
                                    h.addScoreboardTag("Style:Snip");
                                    h.setStyle(Horse.Style.WHITE);
                                } else if (coats.equals("Paint")) {
                                    h.addScoreboardTag("Style:Paint");
                                    h.setStyle(Horse.Style.WHITEFIELD);
                                } else if (coats.equals("Star")) {
                                    h.addScoreboardTag("Style:Star");
                                    h.setStyle(Horse.Style.WHITE_DOTS);
                                } else if (coats.equals("Blaze")) {
                                    h.addScoreboardTag("Style:Blaze");
                                    h.setStyle(Horse.Style.BLACK_DOTS);
                                } else if (coats.equals("None")) {
                                    h.addScoreboardTag("Style:None");
                                    h.setStyle(Horse.Style.NONE);
                                }
                                if (speed.equals("Tier 7")) {
                                    h.addScoreboardTag("Speed:T7");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t7);
                                } else if (speed.equals("Tier 6")) {
                                    h.addScoreboardTag("Speed:T6");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t6);
                                } else if (speed.equals("Tier 5")) {
                                    h.addScoreboardTag("Speed:T5");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t5);
                                } else if (speed.equals("Tier 4")) {
                                    h.addScoreboardTag("Speed:T4");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t4);
                                } else if (speed.equals("Tier 3")) {
                                    h.addScoreboardTag("Speed:T3");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t3);
                                } else if (speed.equals("Tier 2")) {
                                    h.addScoreboardTag("Speed:T2");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t2);
                                } else if (speed.equals("Tier 1")) {
                                    h.addScoreboardTag("Speed:T1");
                                    h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(t1);
                                }else if (speed.equals(null)) {
                                    System.out.println("nulled");
                                }
                                if (jump.equals("1ft")) {
                                    h.addScoreboardTag("1ft");
                                    h.setJumpStrength(.517);
                                } else if (jump.equals("2ft")) {
                                    h.addScoreboardTag("2ft");
                                    h.setJumpStrength(.617);
                                } else if (jump.equals("3ft")) {
                                    h.addScoreboardTag("3ft");
                                    h.setJumpStrength(.717);
                                } else if (jump.equals("4ft")) {
                                    h.addScoreboardTag("4ft");
                                    h.setJumpStrength(.917);
                                } else if (jump.equals("5ft")) {
                                    h.addScoreboardTag("5ft");
                                    h.setJumpStrength(1.017);
                                } else if (jump.equals("6ft")) {
                                    h.addScoreboardTag("6ft");
                                    h.setJumpStrength(1.117);
                                }
                            }
                        }
                    } else {
                        menu.close();
                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "You do not have the money to purchase this horse!");
                    }
                }

            }
        } else if (e.getView().getTitle().equals("§0Genders")) {
            e.setCancelled(true);
            UUID uuid = p.getUniqueId();
            String breed = breedname.get(uuid);
            String coat = coatcolor.get(uuid);
            String coats = coatstyle.get(uuid);
            String speed = speeds.get(uuid);
            String jump = jumpheight.get(uuid);
            String gender = gendername.get(uuid);
            String sln = e.getCurrentItem().getItemMeta().getDisplayName();
            gendername.put(p.getUniqueId(), sln);
            if (e.getSlot() >= 0) {
                p.closeInventory();

                Inventory hc = getServer().createInventory(null, 27, "§0Horse Creation");
                ItemStack ref1 = new ItemStack(Material.BOOK);
                ItemStack ref2 = new ItemStack(Material.BOOK);
                ItemStack ref3 = new ItemStack(Material.BOOK);
                ItemStack ref4 = new ItemStack(Material.BOOK);
                ItemStack ref6 = new ItemStack(Material.BOOK);
                ItemStack ref7 = new ItemStack(Material.BOOK);
                ItemStack ref8 = new ItemStack(Material.YELLOW_TERRACOTTA);

                ItemMeta metaref1 = ref1.getItemMeta();
                ItemMeta metaref2 = ref2.getItemMeta();
                ItemMeta metaref3 = ref3.getItemMeta();
                ItemMeta metaref4 = ref4.getItemMeta();
                ItemMeta metaref6 = ref6.getItemMeta();
                ItemMeta metaref7 = ref7.getItemMeta();
                ItemMeta metaref8 = ref8.getItemMeta();

                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);
                metaref1.setDisplayName(sln);
                if (!breedname.isEmpty()) {
                    metaref2.setDisplayName(breed);
                } else {
                    metaref2.setDisplayName("§bBreeds");
                }
                if (!coatcolor.isEmpty()) {
                    metaref3.setDisplayName(coat);
                } else {
                    metaref3.setDisplayName("§bCoat Color");
                }
                if (!coatstyle.isEmpty()) {
                    metaref4.setDisplayName(coats);
                } else {
                    metaref4.setDisplayName("§bCoat Pattern");
                }
                if (!speeds.isEmpty()) {
                    if (speeds.equals(null)) {
                        metaref6.setDisplayName("§bSpeed");
                    }
                    metaref6.setDisplayName(speed);
                } else {
                    metaref6.setDisplayName("§bSpeed");
                }
                if (!jumpheight.isEmpty()) {
                    metaref7.setDisplayName(jump);
                } else {
                    metaref7.setDisplayName("§bJump");
                }
                metaref8.setDisplayName("§ePurchase");
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);

                hc.setItem(1, ref1);
                hc.setItem(2, ref2);
                hc.setItem(3, ref3);
                hc.setItem(4, ref4);
                hc.setItem(5, ref6);
                hc.setItem(6, ref7);
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
            String speed = speeds.get(uuid);
            String jump = jumpheight.get(uuid);
            String breed = breedname.get(uuid);
            breedname.put(p.getUniqueId(), sln);
            if (e.getSlot() >= 0) {
                p.closeInventory();

                Inventory hc = getServer().createInventory(null, 27, "§0Horse Creation");
                ItemStack ref1 = new ItemStack(Material.BOOK);
                ItemStack ref2 = new ItemStack(Material.BOOK);
                ItemStack ref3 = new ItemStack(Material.BOOK);
                ItemStack ref4 = new ItemStack(Material.BOOK);
                ItemStack ref6 = new ItemStack(Material.BOOK);
                ItemStack ref7 = new ItemStack(Material.BOOK);
                ItemStack ref8 = new ItemStack(Material.YELLOW_TERRACOTTA);

                ItemMeta metaref1 = ref1.getItemMeta();
                ItemMeta metaref2 = ref2.getItemMeta();
                ItemMeta metaref3 = ref3.getItemMeta();
                ItemMeta metaref4 = ref4.getItemMeta();
                ItemMeta metaref6 = ref6.getItemMeta();
                ItemMeta metaref7 = ref7.getItemMeta();
                ItemMeta metaref8 = ref8.getItemMeta();

                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);
                if (!gendername.isEmpty()) {
                    metaref1.setDisplayName(gender);
                } else {
                    metaref2.setDisplayName("§bGender");
                }
                metaref2.setDisplayName(sln);
                if (!coatcolor.isEmpty()) {
                    metaref3.setDisplayName(coat);
                } else {
                    metaref3.setDisplayName("§bCoat Color");
                }
                if (!coatstyle.isEmpty()) {
                    metaref4.setDisplayName(coats);
                } else {
                    metaref4.setDisplayName("§bCoat Pattern");
                }
                if (!speeds.isEmpty()) {
                    metaref6.setDisplayName(speed);
                } else {
                    metaref6.setDisplayName("§bSpeed");
                }
                if (!jumpheight.isEmpty()) {
                    metaref7.setDisplayName(jump);
                } else {
                    metaref7.setDisplayName("§bJump");
                }
                metaref8.setDisplayName("§ePurchase");
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);

                hc.setItem(1, ref1);
                hc.setItem(2, ref2);
                hc.setItem(3, ref3);
                hc.setItem(4, ref4);
                hc.setItem(5, ref6);
                hc.setItem(6, ref7);
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
            String speed = speeds.get(uuid);
            String jump = jumpheight.get(uuid);
            String coat = coatcolor.get(uuid);
            coatcolor.put(p.getUniqueId(), sln);
            if (e.getSlot() >= 0) {
                p.closeInventory();

                Inventory hc = getServer().createInventory(null, 27, "§0Horse Creation");
                ItemStack ref1 = new ItemStack(Material.BOOK);
                ItemStack ref2 = new ItemStack(Material.BOOK);
                ItemStack ref3 = new ItemStack(Material.BOOK);
                ItemStack ref4 = new ItemStack(Material.BOOK);
                ItemStack ref6 = new ItemStack(Material.BOOK);
                ItemStack ref7 = new ItemStack(Material.BOOK);
                ItemStack ref8 = new ItemStack(Material.YELLOW_TERRACOTTA);

                ItemMeta metaref1 = ref1.getItemMeta();
                ItemMeta metaref2 = ref2.getItemMeta();
                ItemMeta metaref3 = ref3.getItemMeta();
                ItemMeta metaref4 = ref4.getItemMeta();
                ItemMeta metaref6 = ref6.getItemMeta();
                ItemMeta metaref7 = ref7.getItemMeta();
                ItemMeta metaref8 = ref8.getItemMeta();

                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);
                if (!gendername.isEmpty()) {
                    metaref1.setDisplayName(gender);
                } else {
                    metaref2.setDisplayName("§bGender");
                }if (!breedname.isEmpty()) {
                    metaref2.setDisplayName(breed);
                } else {
                    metaref2.setDisplayName("§bBreeds");
                }
                metaref3.setDisplayName(sln);
                if (!coatstyle.isEmpty()) {
                    metaref4.setDisplayName(coats);
                } else {
                    metaref4.setDisplayName("§bCoat Pattern");
                }
                if (!speeds.isEmpty()) {
                    metaref6.setDisplayName(speed);
                } else {
                    metaref6.setDisplayName("§bSpeed");
                }
                if (!jumpheight.isEmpty()) {
                    metaref7.setDisplayName(jump);
                } else {
                    metaref7.setDisplayName("§bJump");
                }
                metaref8.setDisplayName("§ePurchase");
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);

                hc.setItem(1, ref1);
                hc.setItem(2, ref2);
                hc.setItem(3, ref3);
                hc.setItem(4, ref4);
                hc.setItem(5, ref6);
                hc.setItem(6, ref7);
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
            String speed = speeds.get(uuid);
            String jump = jumpheight.get(uuid);
            String coats = coatstyle.get(uuid);
            coatstyle.put(p.getUniqueId(), sln);
            if (e.getSlot() >= 0) {
                p.closeInventory();

                Inventory hc = getServer().createInventory(null, 27, "§0Horse Creation");
                ItemStack ref1 = new ItemStack(Material.BOOK);
                ItemStack ref2 = new ItemStack(Material.BOOK);
                ItemStack ref3 = new ItemStack(Material.BOOK);
                ItemStack ref4 = new ItemStack(Material.BOOK);
                ItemStack ref6 = new ItemStack(Material.BOOK);
                ItemStack ref7 = new ItemStack(Material.BOOK);
                ItemStack ref8 = new ItemStack(Material.YELLOW_TERRACOTTA);

                ItemMeta metaref1 = ref1.getItemMeta();
                ItemMeta metaref2 = ref2.getItemMeta();
                ItemMeta metaref3 = ref3.getItemMeta();
                ItemMeta metaref4 = ref4.getItemMeta();
                ItemMeta metaref6 = ref6.getItemMeta();
                ItemMeta metaref7 = ref7.getItemMeta();
                ItemMeta metaref8 = ref8.getItemMeta();

                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);
                if (!gendername.isEmpty()) {
                    metaref1.setDisplayName(gender);
                } else {
                    metaref2.setDisplayName("§bGender");
                }if (!breedname.isEmpty()) {
                    metaref2.setDisplayName(breed);
                } else {
                    metaref2.setDisplayName("§bBreeds");
                }
                if (!coatcolor.isEmpty()) {
                    metaref3.setDisplayName(coat);
                } else {
                    metaref3.setDisplayName("§bCoat Color");
                }
                metaref4.setDisplayName(sln);
                if (!speeds.isEmpty()) {
                    metaref6.setDisplayName(speed);
                } else {
                    metaref6.setDisplayName("§bSpeed");
                }
                if (!jumpheight.isEmpty()) {
                    metaref7.setDisplayName(jump);
                } else {
                    metaref7.setDisplayName("§bJump");
                }
                metaref8.setDisplayName("§ePurchase");
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);

                hc.setItem(1, ref1);
                hc.setItem(2, ref2);
                hc.setItem(3, ref3);
                hc.setItem(4, ref4);
                hc.setItem(5, ref6);
                hc.setItem(6, ref7);
                hc.setItem(25, ref8);

                p.openInventory(hc);

            }
        } else if (e.getView().getTitle().equals("§0Speeds")) {
            e.setCancelled(true);
            String sln = e.getCurrentItem().getItemMeta().getDisplayName();
            UUID uuid = p.getUniqueId();
            String gender = gendername.get(uuid);
            String breed = breedname.get(uuid);
            String coat = coatcolor.get(uuid);
            String coats = coatstyle.get(uuid);
            String jump = jumpheight.get(uuid);
            String speed = speeds.get(uuid);
            speeds.put(p.getUniqueId(), sln);
            if (e.getSlot() >= 0) {
                Inventory hc = getServer().createInventory(null, 27, "§0Horse Creation");
                ItemStack ref1 = new ItemStack(Material.BOOK);
                ItemStack ref2 = new ItemStack(Material.BOOK);
                ItemStack ref3 = new ItemStack(Material.BOOK);
                ItemStack ref4 = new ItemStack(Material.BOOK);
                ItemStack ref6 = new ItemStack(Material.BOOK);
                ItemStack ref7 = new ItemStack(Material.BOOK);
                ItemStack ref8 = new ItemStack(Material.YELLOW_TERRACOTTA);

                ItemMeta metaref1 = ref1.getItemMeta();
                ItemMeta metaref2 = ref2.getItemMeta();
                ItemMeta metaref3 = ref3.getItemMeta();
                ItemMeta metaref4 = ref4.getItemMeta();
                ItemMeta metaref6 = ref6.getItemMeta();
                ItemMeta metaref7 = ref7.getItemMeta();
                ItemMeta metaref8 = ref8.getItemMeta();

                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);
                if (!gendername.isEmpty()) {
                    metaref1.setDisplayName(gender);
                } else {
                    metaref2.setDisplayName("§bGender");
                }if (!breedname.isEmpty()) {
                    metaref2.setDisplayName(breed);
                } else {
                    metaref2.setDisplayName("§bBreeds");
                }
                if (!coatcolor.isEmpty()) {
                    metaref3.setDisplayName(coat);
                } else {
                    metaref3.setDisplayName("§bCoat Color");
                }
                if (!coatstyle.isEmpty()) {
                    metaref4.setDisplayName(coats);
                } else {
                    metaref4.setDisplayName("§bCoat Pattern");
                }
                metaref6.setDisplayName(sln);
                if (!jumpheight.isEmpty()) {
                    metaref7.setDisplayName(jump);
                } else {
                    metaref7.setDisplayName("§bJump");
                }
                metaref8.setDisplayName("§ePurchase");
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);

                hc.setItem(1, ref1);
                hc.setItem(2, ref2);
                hc.setItem(3, ref3);
                hc.setItem(4, ref4);
                hc.setItem(5, ref6);
                hc.setItem(6, ref7);
                hc.setItem(25, ref8);

                p.openInventory(hc);

            }
        } else if (e.getView().getTitle().equals("§0Jumps")) {
            e.setCancelled(true);
            String sln = e.getCurrentItem().getItemMeta().getDisplayName();
            UUID uuid = p.getUniqueId();
            String gender = gendername.get(uuid);
            String breed = breedname.get(uuid);
            String coat = coatcolor.get(uuid);
            String coats = coatstyle.get(uuid);
            String speed = speeds.get(uuid);
            String jump = jumpheight.get(uuid);
            int buy11 = 2200;
            int buy12 = 2400;
            int buy13 = 2800;
            int buy14 = 3200;
            int buy15 = 3500;
            int buy21 = 5200;
            int buy22 = 5400;
            int buy23 = 5800;
            int buy24 = 6200;
            int buy25 = 6500;
            int buy31 = 15200;
            int buy32 = 15400;
            int buy33 = 15800;
            int buy34 = 16200;
            int buy35 = 16500;
            int buy41 = 20200;
            int buy42 = 20400;
            int buy43 = 20800;
            int buy44 = 21200;
            int buy45 = 21500;
            int buy51 = 40200;
            int buy52 = 40400;
            int buy53 = 40800;
            int buy54 = 41200;
            int buy55 = 41500;
            int buy61 = 80200;
            int buy62 = 80400;
            int buy63 = 80800;
            int buy64 = 81200;
            int buy65 = 81500;
            jumpheight.put(p.getUniqueId(), sln);
            if (e.getSlot() >= 0) {
                Inventory hc = getServer().createInventory(null, 27, "§0Horse Creation");
                ItemStack ref1 = new ItemStack(Material.BOOK);
                ItemStack ref2 = new ItemStack(Material.BOOK);
                ItemStack ref3 = new ItemStack(Material.BOOK);
                ItemStack ref4 = new ItemStack(Material.BOOK);
                ItemStack ref6 = new ItemStack(Material.BOOK);
                ItemStack ref7 = new ItemStack(Material.BOOK);
                ItemStack ref8 = new ItemStack(Material.GREEN_TERRACOTTA);

                ItemMeta metaref1 = ref1.getItemMeta();
                ItemMeta metaref2 = ref2.getItemMeta();
                ItemMeta metaref3 = ref3.getItemMeta();
                ItemMeta metaref4 = ref4.getItemMeta();
                ItemMeta metaref6 = ref6.getItemMeta();
                ItemMeta metaref7 = ref7.getItemMeta();
                ItemMeta metaref8 = ref8.getItemMeta();

                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);
                if (!gendername.isEmpty()) {
                    metaref1.setDisplayName(gender);
                } else {
                    metaref2.setDisplayName("§bGender");
                }if (!breedname.isEmpty()) {
                    metaref2.setDisplayName(breed);
                } else {
                    metaref2.setDisplayName("§bBreeds");
                }
                if (!coatcolor.isEmpty()) {
                    metaref3.setDisplayName(coat);
                } else {
                    metaref3.setDisplayName("§bCoat Color");
                }
                if (!coatstyle.isEmpty()) {
                    metaref4.setDisplayName(coats);
                } else {
                    metaref4.setDisplayName("§bCoat Pattern");
                }
                metaref7.setDisplayName(sln);
                if (!speeds.isEmpty()) {
                    metaref6.setDisplayName(speed);
                } else {
                    metaref6.setDisplayName("§bSpeed");
                }
                if (speed.equals("Tier 1")) {
                    if (sln.equals("1ft")) {
                        purchase.put(p.getUniqueId(), buy11);
                        metaref8.setDisplayName("$" + (buy11));
                    } else if (sln.equals("2ft")) {
                        purchase.put(p.getUniqueId(), buy12);
                        metaref8.setDisplayName("$" + (buy12));
                    } else if (sln.equals("3ft")) {
                        purchase.put(p.getUniqueId(), buy13);
                        metaref8.setDisplayName("$" + (buy13));
                    } else if (sln.equals("4ft")) {
                        purchase.put(p.getUniqueId(), buy14);
                        metaref8.setDisplayName("$" + (buy14));
                    } else if (sln.equals("5ft")) {
                        purchase.put(p.getUniqueId(), buy15);
                        metaref8.setDisplayName("$" + (buy15));
                    }
                } else if (speed.equals("Tier 2")) {
                    if (sln.equals("1ft")) {
                        purchase.put(p.getUniqueId(), buy21);
                        metaref8.setDisplayName("$" + (buy21));
                    } else if (sln.equals("2ft")) {
                        purchase.put(p.getUniqueId(), buy22);
                        metaref8.setDisplayName("$" + (buy22));
                    } else if (sln.equals("3ft")) {
                        purchase.put(p.getUniqueId(), buy23);
                        metaref8.setDisplayName("$" + (buy23));
                    } else if (sln.equals("4ft")) {
                        purchase.put(p.getUniqueId(), buy24);
                        metaref8.setDisplayName("$" + (buy24));
                    } else if (sln.equals("5ft")) {
                        purchase.put(p.getUniqueId(), buy25);
                        metaref8.setDisplayName("$" + (buy25));
                    }
                } else if (speed.equals("Tier 3")) {
                    if (sln.equals("1ft")) {
                        purchase.put(p.getUniqueId(), buy31);
                        metaref8.setDisplayName("$" + (buy31));
                    } else if (sln.equals("2ft")) {
                        purchase.put(p.getUniqueId(), buy32);
                        metaref8.setDisplayName("$" + (buy32));
                    } else if (sln.equals("3ft")) {
                        purchase.put(p.getUniqueId(), buy33);
                        metaref8.setDisplayName("$" + (buy33));
                    } else if (sln.equals("4ft")) {
                        purchase.put(p.getUniqueId(), buy34);
                        metaref8.setDisplayName("$" + (buy34));
                    } else if (sln.equals("5ft")) {
                        purchase.put(p.getUniqueId(), buy35);
                        metaref8.setDisplayName("$" + (buy35));
                    }
                } else if (speed.equals("Tier 4")) {
                    if (sln.equals("1ft")) {
                        purchase.put(p.getUniqueId(), buy41);
                        metaref8.setDisplayName("$" + (buy41));
                    } else if (sln.equals("2ft")) {
                        purchase.put(p.getUniqueId(), buy42);
                        metaref8.setDisplayName("$" + (buy42));
                    } else if (sln.equals("3ft")) {
                        purchase.put(p.getUniqueId(), buy43);
                        metaref8.setDisplayName("$" + (buy43));
                    } else if (sln.equals("4ft")) {
                        purchase.put(p.getUniqueId(), buy44);
                        metaref8.setDisplayName("$" + (buy44));
                    } else if (sln.equals("5ft")) {
                        purchase.put(p.getUniqueId(), buy45);
                        metaref8.setDisplayName("$" + (buy45));
                    }
                } else if (speed.equals("Tier 5")) {
                    if (sln.equals("1ft")) {
                        purchase.put(p.getUniqueId(), buy51);
                        metaref8.setDisplayName("$" + (buy51));
                    } else if (sln.equals("2ft")) {
                        purchase.put(p.getUniqueId(), buy52);
                        metaref8.setDisplayName("$" + (buy52));
                    } else if (sln.equals("3ft")) {
                        purchase.put(p.getUniqueId(), buy53);
                        metaref8.setDisplayName("$" + (buy53));
                    } else if (sln.equals("4ft")) {
                        purchase.put(p.getUniqueId(), buy54);
                        metaref8.setDisplayName("$" + (buy54));
                    } else if (sln.equals("5ft")) {
                        purchase.put(p.getUniqueId(), buy55);
                        metaref8.setDisplayName("$" + (buy55));
                    }
                } else if (speed.equals("Tier 6")) {
                    if (sln.equals("1ft")) {
                        purchase.put(p.getUniqueId(), buy61);
                        metaref8.setDisplayName("$" + (buy61));
                    } else if (sln.equals("2ft")) {
                        purchase.put(p.getUniqueId(), buy62);
                        metaref8.setDisplayName("$" + (buy62));
                    } else if (sln.equals("3ft")) {
                        purchase.put(p.getUniqueId(), buy63);
                        metaref8.setDisplayName("$" + (buy63));
                    } else if (sln.equals("4ft")) {
                        purchase.put(p.getUniqueId(), buy64);
                        metaref8.setDisplayName("$" + (buy64));
                    } else if (sln.equals("5ft")) {
                        purchase.put(p.getUniqueId(), buy65);
                        metaref8.setDisplayName("$" + (buy65));
                    }
                } else if (speed == null){
                    purchase.put(p.getUniqueId(), 0);
                    metaref8.setDisplayName("$0");
                    System.out.println("nulled");
                } else {
                    purchase.put(p.getUniqueId(), 0);
                    metaref8.setDisplayName("$0");
                }
                ref1.setItemMeta(metaref1);
                ref2.setItemMeta(metaref2);
                ref3.setItemMeta(metaref3);
                ref4.setItemMeta(metaref4);
                ref6.setItemMeta(metaref6);
                ref7.setItemMeta(metaref7);
                ref8.setItemMeta(metaref8);

                hc.setItem(1, ref1);
                hc.setItem(2, ref2);
                hc.setItem(3, ref3);
                hc.setItem(4, ref4);
                hc.setItem(5, ref6);
                hc.setItem(6, ref7);
                hc.setItem(25, ref8);

                p.openInventory(hc);
            }
        }
    }
}