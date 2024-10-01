package com.mehmett.service;

import com.mehmett.entity.Player;
import com.mehmett.repository.PlayerRepository;
import com.mehmett.utility.ConsoleTextUtils;

import java.util.ArrayList;
import java.util.List;

public class PlayerService extends ServiceManager<Player,Long>{
	private static PlayerService instance;
	private final PlayerRepository playerRepository = PlayerRepository.getInstance();
	private PlayerService() {
		super(PlayerRepository.getInstance());
		
	}
	public static PlayerService getInstance() {
		if (instance == null) {
			instance = new PlayerService();
		}
		return instance;
	}


	public List<Player> makeAnOfferForPlayer(String nameToSearch){
        List<Player> playerList = new ArrayList<>();
        try {
            playerList = playerRepository.makeAnOfferForPlayer(nameToSearch);
            if(playerList.isEmpty()){
                System.out.println("No player found!");
                return new ArrayList<>();
            }
        } catch (Exception e) {
			ConsoleTextUtils.printErrorMessage("Service Error: "+e.getMessage());
        }
        return playerList;
	}


}