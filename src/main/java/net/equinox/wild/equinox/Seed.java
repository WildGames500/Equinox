package net.equinox.wild.equinox;

import org.bukkit.Material;

import java.util.HashSet;
import java.util.Set;

public enum Seed {
    WHEAT_SEEDS(Material.WHEAT_SEEDS),
    PUMPKIN_SEEDS(Material.PUMPKIN_SEEDS),
    MELON_SEEDS(Material.MELON_SEEDS),
    BEETROOT_SEEDS(Material.BEETROOT_SEEDS),
    NETHER_WART(Material.NETHER_WART);

    public static final Seed ALWAYS_LIKED;

    public static final Set<Material> MATERIALS;

    private final Material material;

    static {
        ALWAYS_LIKED = BEETROOT_SEEDS;
        MATERIALS = new HashSet<>();
        for (Seed seed : values())
            MATERIALS.add(seed.getMaterial());
    }

    Seed(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return this.material;
    }
}
