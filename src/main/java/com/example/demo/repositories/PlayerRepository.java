package com.example.demo.repositories;

import com.example.demo.models.Player;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PlayerRepository extends ReactiveMongoRepository<Player, Integer> {
}
