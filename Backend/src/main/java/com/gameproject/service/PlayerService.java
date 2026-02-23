package com.gameproject.service;

import com.gameproject.entity.Player;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PlayerService {

    Player login(String name);

    Player createPlayer(String name);

    Player getById(Long id);

    Player getByName(String name);

    List<Player> getAll();


}
