package net.equinox.wild.equinox;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.UUID;


// Storage Of Selected Horse
public class Events1 implements Listener {
    public static HashMap<UUID, UUID> collection = new HashMap<UUID, UUID>();


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
