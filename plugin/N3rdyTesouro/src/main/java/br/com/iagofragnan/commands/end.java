package br.com.iagofragnan.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class end implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        br.com.iagofragnan.models.arena.DeleteLastArena();
        return true;
    }

}
