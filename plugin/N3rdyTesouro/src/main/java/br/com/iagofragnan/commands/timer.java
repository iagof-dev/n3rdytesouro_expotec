package br.com.iagofragnan.commands;

import br.com.iagofragnan.controller.api;
import br.com.iagofragnan.models.player;
import br.com.iagofragnan.models.scoreboard;
import de.rapha149.signgui.SignGUI;
import de.rapha149.signgui.SignGUIAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static br.com.iagofragnan.models.timer.*;

public class timer implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;

        if (args[0].equals("start")){
            p.sendMessage("Iniciando timer");
            setStartTime(LocalTime.now());
            setIsRunning(true);
        }
        else if(args[0].equals("end") || args[0].equals("stop")){
            p.sendMessage("Timer finalizado");
            setEndTime(LocalTime.now());
            setIsRunning(false);

            int minutos = (getEndTime().getMinute() - getStartTime().getMinute());
            int segundos = (getEndTime().getSecond() - getStartTime().getSecond());
            int milisegundos = (getEndTime().getNano() - getStartTime().getNano());

            p.sendMessage("Ótimo! seu tempo foi: " + minutos + "min, " + segundos + "seg, e " + Math.abs(Math.round(milisegundos)/100000) + "ms");
            p.sendMessage("Inicio: " + getStartTime());
            p.sendMessage("Final: " + getEndTime());

        }
        else if(args[0].equals("save")){
            Integer value = Integer.parseInt(args[1]);
            br.com.iagofragnan.controller.mysql.registerTime("exemplo", value, getStartTime(), getEndTime());
        }
        else if(args[0].equals("calc")){
            Duration duration = Duration.between(br.com.iagofragnan.models.timer.getStartTime(), LocalTime.now());
            long totalSeconds = duration.getSeconds();
            long hours = totalSeconds / 3600;
            long minutes = (totalSeconds % 3600) / 60;
            long seconds = totalSeconds % 60;
            int nanos = duration.getNano();
            int milliseconds = nanos / 1000000;

            String value = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);

            p.sendMessage("Valor Calculado: " + value);
        }
        else if(args[0].equals("max")){
            br.com.iagofragnan.controller.api api = new api();
            api.getMaximumTime();

            p.sendMessage("Tempo máximo: " + br.com.iagofragnan.settings.ranking.getMaximumTime());
        }
        else{
            p.sendMessage(ChatColor.RED + "Erro!");
        }

        return true;
    }
}
