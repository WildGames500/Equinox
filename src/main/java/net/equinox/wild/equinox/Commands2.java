package net.equinox.wild.equinox;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;

public class Commands2 implements CommandExecutor {
    private final Equinox plugin;


    public Commands2(Equinox plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("eqa")) {
            if (args[0].equalsIgnoreCase("age")) {
                World world = Bukkit.getWorld("world");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        int i = 0;
                        while (i <= 62) {
                            if (e.getScoreboardTags().contains("Age:" + i)) {
                                e.removeScoreboardTag("Age:" + i);
                                ++i;
                                e.addScoreboardTag("Age:" + i);

                            } ++i;
                        }
                    }
                }

            } else if (args[0].equalsIgnoreCase("heat")) {
                World world = Bukkit.getWorld("world");
                for (Entity e : world.getEntities()) {
                    if (e instanceof Horse) {
                        int i = 1;
                        while (i <= 13) {
                            if (e.getScoreboardTags().contains("Heat" + i)) {
                                e.removeScoreboardTag("Heat" + i);
                                ++i;
                                e.addScoreboardTag("Heat" + i);

                            } ++i;

                        } if (e.getScoreboardTags().contains("Heat14")) {
                            e.removeScoreboardTag("Heat14");
                            e.addScoreboardTag("Heat1");
                            e.addScoreboardTag("InHeat1");
                            return true;
                        } if (e.getScoreboardTags().contains("InHeat1")) {
                            e.removeScoreboardTag("InHeat1");
                            e.removeScoreboardTag("InHeat2");
                            return true;
                        } if (e.getScoreboardTags().contains("InHeat2")) {
                            e.removeScoreboardTag("InHeat2");
                            e.removeScoreboardTag("InHeat3");
                            return true;
                        } if (e.getScoreboardTags().contains("InHeat3")) {
                            e.removeScoreboardTag("InHeat3");
                            e.removeScoreboardTag("InHeat");
                        }
                    }
                }
            }
        }
        return false;
    }
}

