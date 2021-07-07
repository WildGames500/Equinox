package net.equinox.wild.equinox;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.sql.Time;
import java.util.HashMap;
import java.util.UUID;


// Storage Of Selected Horse
public class Events1 implements Listener {
    public static HashMap<UUID, UUID> collection = new HashMap<UUID, UUID>();
    private final Equinox plugin;

    public Events1(Equinox plugin) {
        this.plugin = plugin;
    }






    @EventHandler
    public void onPoopPunch(PlayerInteractEvent e) {
        Entity p = e.getPlayer();
        Player player = (Player) p;

        if (e.getClickedBlock() == null)
            return;

        Block block = e.getClickedBlock();

        if (block.getType() == Material.PLAYER_HEAD) {
            Location locate = block.getLocation();
            int x = locate.getBlockX();
            int y = locate.getBlockY();
            int z = locate.getBlockZ();
            int y1 = y + 1;
            Location loc = locate.set(x, y1, z);
            Hologram hologram = HologramsAPI.createHologram(plugin, loc);
            player.playSound(loc, "entity.slime.jump", 1, .5F);
            player.giveExp(1);
            hologram.appendTextLine(ChatColor.YELLOW + "+1 xp");

            block.setType(Material.AIR);
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    hologram.delete();
                }
            }, 100);
        }

    }
    // Damage Select Horse
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if (e.getEntityType()== EntityType.HORSE) {
            Player player = ((Player) e.getDamager()).getPlayer();
            UUID uuid = (UUID) player.getUniqueId();
            if (e.getDamager() instanceof Player) {
                if (e.getEntity().getScoreboardTags().contains("Private")) {
                    if (e.getEntity().getScoreboardTags().contains("Owner:" + uuid)) {
                        collection.put(player.getUniqueId(), e.getEntity().getUniqueId());
                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have selected this horse!");
                    } else if (!e.getEntity().getScoreboardTags().contains("Owned")) {
                        collection.put(player.getUniqueId(), e.getEntity().getUniqueId());
                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have selected this horse!");

                    } else if (e.getEntity().getScoreboardTags().contains("Member:" + uuid)) {
                        collection.put(player.getUniqueId(), e.getEntity().getUniqueId());
                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have selected this horse!");

                    } else if (e.getEntity().getScoreboardTags().contains("Owned")) {
                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.RED + "This is not your horse!");
                    }
                } else if (e.getEntity().getScoreboardTags().contains("Public")) {
                    collection.put(player.getUniqueId(), e.getEntity().getUniqueId());
                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have selected this horse!");
                } else if (!e.getEntity().getScoreboardTags().contains("Public")) {
                    if (!e.getEntity().getScoreboardTags().contains("Private")) {
                        collection.put(player.getUniqueId(), e.getEntity().getUniqueId());
                        player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "EQ" + ChatColor.GRAY + "] >> " + ChatColor.YELLOW + "You have selected this horse!");
                    }
                }
            }
        }

    }
    @EventHandler
    public void AllDamage(EntityDamageEvent d) {
        if (d.getEntityType() == EntityType.HORSE) {
            d.setCancelled(true);
        }
    }
}
