package br.com.iagofragnan.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class start implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = br.com.iagofragnan.models.player.getObj_player();
        p.sendTitle("Ca\u00e7a ao Tesouro", ChatColor.GRAY+"Feito por Iago Fragnan", 20, 50, 20);

//        Bukkit.getScheduler().scheduleSyncDelayedTask(br.com.iagofragnan.main.getThePlugin(), new Runnable() {
//            public void run() {
//                p.sendTitle("GITHUB", ChatColor.GRAY + "github.com/iagof-dev/", 20, 20, 20);
//            }
//        }, 50L);
//
//        Bukkit.getScheduler().scheduleSyncDelayedTask(br.com.iagofragnan.main.getThePlugin(), new Runnable() {
//            public void run() {
//                p.sendTitle("LinkedIn", ChatColor.GRAY+"linkedin.com/in/iago-fragnan/", 20, 20, 20);
//            }
//        }, 210L);
//
//        Bukkit.getScheduler().scheduleSyncDelayedTask(br.com.iagofragnan.main.getThePlugin(), new Runnable() {
//            public void run() {
//                p.sendTitle("PORTFOLIO", ChatColor.GRAY+"iagofragnan.com.br", 20, 20, 20);
//            }
//        }, 270L);


        br.com.iagofragnan.models.arena.CreateArena();
        return true;
    }
}