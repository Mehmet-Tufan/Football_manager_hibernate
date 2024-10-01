package com.mehmett.controller;

import com.mehmett.entity.Player;
import com.mehmett.service.PlayerService;

import java.util.List;

public class PlayerController {
	private static PlayerController instance;
	private PlayerService playerService = PlayerService.getInstance();



	private PlayerController() {

	}
	public static PlayerController getInstance() {
		if (instance == null) {
			instance = new PlayerController();
		}
		return instance;
	}

	public List<Player> makeAnOfferForPlayer(String nameToSearch){
		return playerService.makeAnOfferForPlayer(nameToSearch);
	}
}