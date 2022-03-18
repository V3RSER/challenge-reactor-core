package com.example.demo;

import com.example.demo.dtos.PlayerDTO;
import com.example.demo.utils.CsvUtilFile;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class CSVUtilTest {

    @Test
    void converterData() {
        List<PlayerDTO> list = CsvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void stream_filtrarJugadoresMayoresA35() {
        List<PlayerDTO> list = CsvUtilFile.getPlayers();
        Map<String, List<PlayerDTO>> listFilter = list.parallelStream()
                .filter(player -> player.age >= 35)
                .map(player -> {
                    player.name = player.name.toUpperCase(Locale.ROOT);
                    return player;
                })
                .flatMap(playerA -> list.parallelStream()
                        .filter(playerB -> playerA.club.equals(playerB.club))
                )
                .distinct()
                .collect(Collectors.groupingBy(PlayerDTO::getClub));

        assert listFilter.size() == 322;
    }


    @Test
    void reactive_filtrarJugadoresMayoresA35() {
        List<PlayerDTO> list = CsvUtilFile.getPlayers();
        Flux<PlayerDTO> listFlux = Flux.fromStream(list.parallelStream()).cache();
        Mono<Map<String, Collection<PlayerDTO>>> listFilter = listFlux
                .filter(player -> player.age >= 35)
                .map(player -> {
                    player.name = player.name.toUpperCase(Locale.ROOT);
                    return player;
                })
                .buffer(100)
                .flatMap(playerA -> listFlux
                        .filter(playerB -> playerA.stream()
                                .anyMatch(a -> a.club.equals(playerB.club)))
                )
                .distinct()
                .collectMultimap(PlayerDTO::getClub);

        assert listFilter.block().size() == 322;
    }
}
