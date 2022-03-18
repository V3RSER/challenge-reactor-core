package com.example.demo.services;

import com.example.demo.dtos.PlayerDTO;
import com.example.demo.models.Player;
import com.example.demo.repositories.PlayerRepository;
import com.example.demo.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

public class PlayerService {

    @Autowired
    private PlayerRepository repository;

    public Flux<Player> getByOlderAge(int age) {
        return repository.findAll()
                .buffer(100)
                .limitRate(100)
                .flatMap(player -> Flux.fromStream(player.parallelStream()))
                .filter(player -> player.getAge() > age);
    }

    public Flux<Player> getByClub(String club) {
        return repository.findAll()
                .buffer(100)
                .limitRate(100)
                .flatMap(player -> Flux.fromStream(player.parallelStream()))
                .filter(player -> player.getClub().equals(club));
    }

    public Flux<Player> getRankingByNationality(String nationality) {
        return repository.findAll()
                .buffer(100)
                .limitRate(100)
                .flatMap(player -> Flux.fromStream(player.parallelStream()))
                .filter(player -> player.getNationality().equals(nationality))
                .sort(Comparator.comparing(Player::getWinners).reversed());
    }

    public Mono<PlayerDTO> add(Mono<PlayerDTO> playerDTOMono) {
        return playerDTOMono.map(AppUtils::dtoToModel)
                .flatMap(repository::insert)
                .map(AppUtils::modelToDto);
    }
}
