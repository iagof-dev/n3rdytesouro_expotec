package br.com.iagofragnan.controller;

import br.com.iagofragnan.models.timer;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static br.com.iagofragnan.models.scoreboard.*;



public class scoreboard {

    //    PIOR JEITO DE CRIAR UMA SCOREBOARD
    //    Mas preciso finalizar isso a tempo.

    FastBoard board_idle;
    FastBoard board_playing;
    states active_state;

    public FastBoard getBoard() {
        return board_idle;
    }

    public boolean createScoreboard(Player p, states state){

        if(p == null) return true;

        switch(state) {
            default:
            case Idle:
                active_state = states.Idle;
                createLobbyScoreboard(p);
                break;
            case Playing:
                active_state = states.Playing;
                createPlayingScoreboard(p);
                break;
        }
        return true;
    }


    private void createLobbyScoreboard(Player p) {
        if(p == null) return;
        board_idle = new FastBoard(p);

        board_idle.updateTitle(ChatColor.BOLD + "Expotec 2024");


        api api = new api();

        String topOneName = api.getTopOne()[0];

        board_idle.updateLines(
                "Ca\u00e7a ao Tesouro",
                "",
                "Melhor Tempo:",
                ChatColor.GOLD + topOneName + ChatColor.WHITE + " - " + api.getTopOne()[1],
                "",
                ChatColor.GRAY + "github.com/iagof-dev"
        );
        br.com.iagofragnan.settings.ranking.setTopOneName(topOneName);
    }

    private void createPlayingScoreboard(Player p) {
        if(p == null) return;
        active_state = states.Playing;
        board_playing = new FastBoard(p);

        board_playing.updateTitle(ChatColor.BOLD + "Expotec 2024");

        board_playing.updateLines(
                "Ca\u00e7a ao Tesouro",
                "",
                "Melhor Tempo:",
                ChatColor.GOLD + br.com.iagofragnan.settings.ranking.getTopOneName() + ChatColor.WHITE + " - 00:00:00",
                "",
                ChatColor.WHITE + "Seu Tempo: " + ChatColor.GREEN + "0:00:00.00",
                "",
                ChatColor.GRAY + "github.com/iagof-dev"

        );
    }


    public void updateScoreboard(Player p){
        if(p == null) return;

        if(active_state == states.Idle){
            createLobbyScoreboard(p);
        }
        if(!game.isPlaying() || active_state == states.Idle){
            return;
        }

        if(timer.getStartTime() != null){
            Duration duration = Duration.between(timer.getStartTime(), LocalTime.now());
            long totalSeconds = duration.getSeconds();
            long hours = totalSeconds / 3600;
            long minutes = (totalSeconds % 3600) / 60;
            long seconds = totalSeconds % 60;
            int nanos = duration.getNano();
            int milliseconds = nanos / 1000000;

            String value = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);

            board_playing = new FastBoard(p);

            board_playing.updateTitle(ChatColor.BOLD + "Expotec 2024");

            board_playing.updateLines(
                    "Ca\u00e7a ao Tesouro",
                    "",
                    "Melhor Tempo:",
                    ChatColor.GOLD + br.com.iagofragnan.settings.ranking.getTopOneName() + ChatColor.WHITE + " - 00:00:00",
                    "",
                    ChatColor.WHITE + "Seu Tempo: " + ChatColor.GREEN + value,
                    "",
                    ChatColor.GRAY + "github.com/iagof-dev"

            );

            // maximumTime = "00:00:13.780"
            String maximumTime = br.com.iagofragnan.settings.ranking.getMaximumTime();
            if (maximumTime == null || maximumTime.isEmpty()) {
                br.com.iagofragnan.controller.api api = new api();
                api.getMaximumTime();
                return;
            }
            LocalTime actualTime = LocalTime.parse(value, DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
            LocalTime formattedLocalTime = LocalTime.parse(maximumTime, DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));


            if (actualTime.isAfter(formattedLocalTime)) {
                br.com.iagofragnan.controller.game.end();
                br.com.iagofragnan.models.player.getPlayerObj().sendTitle(ChatColor.RED + "Tempo esgotado!", ChatColor.WHITE + "Seu tempo esgotou.");
                return;
            }


        }


    }
}
