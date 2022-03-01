package net.equinox.wild.equinox;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
    public static Location findTypeOfBlockWithinLocation(Location loc, List<Material> validTypes, int radius) {
//        Block block = loc.getWorld().getBlockAt(loc);
        for(int x = loc.getBlockX(); x <= (loc.getBlockX() + radius); x++) {
            for(int y = loc.getBlockY(); y <= (loc.getBlockY() + radius); y++) {
                for(int z = loc.getBlockZ(); z <= (loc.getBlockZ() + radius); z++) {
                    Material type = loc.getWorld().getBlockAt(x, y, z).getType();
                    Location target = new Location(loc.getWorld(), x, y, z);
                    if(loc.distance(target) <= radius && validTypes.contains(loc.getWorld().getBlockAt(x, y, z).getType())) {
                        return new Location(loc.getWorld(), x, y, z);
                    }
                }
            }
        }
//        for(int x = radius; x >= -radius; x--) {
//            for(int y = radius; y >= -radius; y--) {
//                for(int z = radius; z >= -radius; z--) {
//                    Material type = loc.getWorld().getBlockAt(x, y, z).getType();
//                    Block target = block.getRelative(x, y, z);
//                    if(validTypes.contains(target.getType())) {
//                        return block.getLocation();
//                    }
//                }
//            }
//        }
        return null;
    }

    public static Block findAbsoluteBlockWithinLocation(Location loc, List<Material> validTypes, int radius) {
//        Block block = loc.getWorld().getBlockAt(loc);
        for(int x = loc.getBlockX(); x <= (loc.getBlockX() + radius); x++) {
            for(int y = loc.getBlockY(); y <= (loc.getBlockY() + radius); y++) {
                for(int z = loc.getBlockZ(); z <= (loc.getBlockZ() + radius); z++) {
                    Material type = loc.getWorld().getBlockAt(x, y, z).getType();
                    Location target = new Location(loc.getWorld(), x, y, z);
                    if(loc.distance(target) <= radius && validTypes.contains(loc.getWorld().getBlockAt(x, y, z).getType())) {
                        return loc.getWorld().getBlockAt(x, y, z);
                    }
                }
            }
        }
//        for(int x = radius; x >= -radius; x--) {
//            for(int y = radius; y >= -radius; y--) {
//                for(int z = radius; z >= -radius; z--) {
//                    Material type = loc.getWorld().getBlockAt(x, y, z).getType();
//                    Block target = block.getRelative(x, y, z);
//                    if(validTypes.contains(target.getType())) {
//                        return block.getLocation();
//                    }
//                }
//            }
//        }
        return null;
    }

    public static List<PotionType> findPotionsInInventory(Inventory inv, List<PotionType> potionTypes) {
        List<PotionType> types = new ArrayList<>();

        for (ItemStack item : inv.getContents()){
            if(item == null) continue;
            if(item.getType() == Material.POTION) {
                if(item.getItemMeta() instanceof PotionMeta) {
                    PotionMeta meta = (PotionMeta) item.getItemMeta();
                    if (potionTypes.contains(meta.getBasePotionData().getType())) {
                        types.add(meta.getBasePotionData().getType());
                    }
                }
            }
        }
        return types;
    }

    public static void addFoodToHorse(Entity e, int amountOfPoints) {
        boolean foundHunger = false;
        int hungerLevel = 10;

        for (String tag : e.getScoreboardTags()) {
            if (tag.contains("Hunger:")) {
                // ["Hunger", "9"]
                try {
                    hungerLevel = Integer.parseInt(tag.split(":")[1]);
                } catch (NumberFormatException ignored) {}
                foundHunger = true;
                break;
            }
        }

        if(!foundHunger) return;

        setFoodToHorse(e, hungerLevel + amountOfPoints);
    }

    public static void setFoodToHorse(Entity e, int amountOfPoints) {
        boolean foundHunger = false;
        int hungerLevel = 10;
        for (String tag : e.getScoreboardTags()) {
            if (tag.contains("Hunger:")) {
                // ["Hunger", "9"]
                try {
                    hungerLevel = Integer.parseInt(tag.split(":")[1]);
                } catch (NumberFormatException ignored) {}
                foundHunger = true;
                break;
            }

        }


        if(!foundHunger) return;

        String oldHunger = String.format("Hunger:%d", hungerLevel);

        if(amountOfPoints > 10) {
            amountOfPoints = 10;
        }

        String newHunger = String.format("Hunger:%d", amountOfPoints);
        if (e.getScoreboardTags().contains(oldHunger)) {
            e.removeScoreboardTag(oldHunger);
            e.addScoreboardTag(newHunger);
            e.removeScoreboardTag("Hunger");
        }

        if(hungerLevel == 0) {
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
            }
        }
    }

    public static int getXpNeededForLevel(int newLevel) {
        return (int) Math.pow(newLevel, 3.00) + 19;
    }

}
