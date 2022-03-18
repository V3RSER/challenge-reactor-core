package com.example.demo;

import com.example.demo.dtos.PlayerDTO;
import com.example.demo.models.Player;
import com.example.demo.utils.CsvUtilFile;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

public class CSVUtilTest {

    @Test
    void converterData() {
        List<PlayerDTO> list = CsvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void stream_filterPlayersByOlderAge() {
        var age = 34;
        List<PlayerDTO> list = CsvUtilFile.getPlayers();
        Flux<PlayerDTO> listFlux = Flux.fromStream(list.parallelStream()).cache();
        Mono<Map<String, Collection<PlayerDTO>>> listFilter = listFlux
                .filter(playerDTO -> playerDTO.age > age)
                .distinct()
                .collectMultimap(PlayerDTO::getName);

        listFilter.subscribe(players -> System.out.println("Jugadores con edad > " + age + ": " + players.size()));
        listFilter.block().forEach((name, playerDTOS) -> playerDTOS.forEach(System.out::println));
        assert listFilter.block().size() == 488;
    }

    @Test
    void stream_filterPlayersByClub() {
        var club = "Manchester City";
        List<PlayerDTO> list = CsvUtilFile.getPlayers();
        Flux<PlayerDTO> listFlux = Flux.fromStream(list.parallelStream()).cache();
        Mono<Map<String, Collection<PlayerDTO>>> listFilter = listFlux
                .filter(player -> !Objects.isNull(player.club))
                .filter(playerDTO -> playerDTO.getClub().equals(club))
                .distinct()
                .collectMultimap(PlayerDTO::getName);

        listFilter.subscribe(players -> System.out.println("Jugadores del club " + club + ": " + players.size()));
        listFilter.block().forEach((name, playerDTOS) -> playerDTOS.forEach(System.out::println));
        assert listFilter.block().size() == 33;
    }

    @Test
    void stream_filterPlayersRankingByNationaly() {
        var nationality = "France";
        List<PlayerDTO> list = CsvUtilFile.getPlayers();
        Flux<PlayerDTO> listFlux = Flux.fromStream(list.parallelStream()).cache();
        Mono<Map<String, Collection<PlayerDTO>>> listFilter = listFlux
                .filter(playerDTO -> playerDTO.getNationality().equals(nationality))
                .sort(Comparator.comparing(PlayerDTO::getWinners).reversed())
                .distinct()
                .collectMultimap(PlayerDTO::getName);
        listFilter.subscribe(players -> System.out.println("Jugadores con nacionalidad " + nationality + ": " + players.size()));
        listFilter.block().forEach((name, playerDTOS) -> playerDTOS.forEach(System.out::println));
        assert listFilter.block().size() == 904;
    }
}
