package com.example.demo.utils;

import com.example.demo.dtos.PlayerDTO;
import com.example.demo.models.Player;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static PlayerDTO modelToDto(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        BeanUtils.copyProperties(player, playerDTO);
        return playerDTO;
    }

    public static Player dtoToModel(PlayerDTO playerDTO) {
        Player player = new Player();
        BeanUtils.copyProperties(playerDTO, player);
        return player;
    }
}
