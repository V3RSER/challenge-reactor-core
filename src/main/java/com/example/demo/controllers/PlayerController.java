package com.example.demo.controllers;

import com.example.demo.dtos.PlayerDTO;
import com.example.demo.models.Player;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/players")
public class PlayerController {
    @Autowired
    private PlayerService service;

    @GetMapping("/age/{age}")
    public Flux<Player> getPlayersByOlderAge(@PathVariable("name") int age){
        return service.getByOlderAge(age);
    }

    @GetMapping("/club/{name}")
    public Flux<Player> getPlayersByClub(@PathVariable("name") String club){
        return service.getByClub(club);
    }

    @GetMapping("/ranking/{nationality}")
    public Flux<Player> getPlayersRankingByNationaly(@PathVariable("name") String nationality){
        return service.getRankingByNationality(nationality);
    }

    @PostMapping("/add")
    public Mono<PlayerDTO> addPlayer(@RequestBody Mono<PlayerDTO> playerDTOMono){
        playerDTOMono.subscribe();
        return service.add(playerDTOMono);
    }
}
