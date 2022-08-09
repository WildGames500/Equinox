package net.equinox.wild.equinox;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        if(hungerLevel >= 10) {
            if (e.getScoreboardTags().contains("Trait:Glutton")) {
                Random rnd = new Random();
                int i = rnd.nextInt(100);
                if (i <= 15) {
                    return;
                } else {
                    e.removeScoreboardTag("Hunger");
                }

            } else {
                e.removeScoreboardTag("Hunger");
            }
        }
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
            if(hungerLevel == 10) {
                if (e.getScoreboardTags().contains("Trait:Glutton")) {
                    Random rnd = new Random();
                    int i = rnd.nextInt(100);
                    if (i <= 15) {
                        return;
                    } else {
                        e.removeScoreboardTag("Hunger");
                    }

                } else {
                    e.removeScoreboardTag("Hunger");
                }
            }
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

    public static boolean isSkullPoop(Block block) {
        if(block.getType() != Material.PLAYER_HEAD) {
            throw new IllegalArgumentException("Utilities#isSkullPoop - This is not a skull block!");
        }

        Skull skull = (Skull) block.getState();

        return skull.getPlayerProfile().getTextures().getSkin().toExternalForm().equalsIgnoreCase(Equinox.POOP_TEXTURE_URL);
    }

    public static int getXpNeededForLevel(int newLevel) {
        return (int) Math.pow(newLevel, 3.00) + 19;
    }

    public static String convertLocationToString(Location loc) {
        return loc.getWorld().getName() +
                "_" +
                loc.getX() +
                "_" +
                loc.getY() +
                "_" +
                loc.getZ();
    }

    public static Location convertStringToLocation(String data) throws IllegalArgumentException {
        if(data == null) {
            throw new IllegalArgumentException("The provided location string was null!");
        }
        String[] parts = data.split("_");

        if(parts.length != 4) {
            throw new IllegalArgumentException("This string is missing a piece of data! " +
                    "(Does not match World_X_Y_Z pattern): " + data);
        }

        World world = Bukkit.getWorld(parts[0]);
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);

        return new Location(world, x, y, z);
    }

}
