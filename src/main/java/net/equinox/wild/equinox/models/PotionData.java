package net.equinox.wild.equinox.models;

import org.bukkit.potion.PotionType;

import java.util.List;

public class PotionData {
    public int numberOfPotions;
    public List<PotionType> types;

    /**
     * numberOfPotions = 2
     * (list) types = [FIRE_RESISTANCE, FIRE_RESISTANCE, NIGHT_VISION]
     *
     */

    public PotionData(int numberOfPotions, List<PotionType> type) {
        this.numberOfPotions = numberOfPotions;
        this.types = type;
    }

    /**
     *
     * PotionData data = Utilities.whateverItWasNamed();
     *
     * for(PotionType type : data) {
     *     switch(type) {
     *         case PotionType.NIGHT_VISION {
     *             Horse.addHunger(3 * type)
     *             break;
     *         }
     *         case PotionType.FIRE_RESISTANCE {
     *             Horse.addHunger(100000)
     *             Horse.addScoreboardTag("VITAMIN")
     *             break;
     *         }
     *     }
     *     if(type == PotionType.NIGHT_VISION) {
     *         Horse.addHunger(3)
     *     } else if(type == PotionType.FIRE_RESISTANCE) {
     *         Horse.addHunger(10)
     *         Horse.addScoreboardTag("VITAMIN")
     *     }
     *     inventory.clear();
     * }
     *
     * public static void addHunger(int amount, Entity e) {
     *     if(amount > 10) {
     *         amount = 10;
     *     }
     *
     *     e.addScoreboardTag(String.format("Hunger:%d", amount);
     * }
     *
     */

}