package com.mehmett.repository;

import com.mehmett.entity.Player;

import java.util.List;

import static com.mehmett.utility.HibernateConnection.em;

public class PlayerRepository extends RepositoryManager<Player,Long>{
	private static PlayerRepository instance;
	private PlayerRepository() {
		super(Player.class);
	}
	public static  PlayerRepository getInstance() {
		if (instance == null) {
			instance = new PlayerRepository();
		}
		return instance;
	}


	public List<Player> makeAnOfferForPlayer(String nameToSearch) {
		String sql = "SELECT * from tblplayer WHERE name ILIKE  '%" + nameToSearch + "%'";
		return em.createNativeQuery(sql, Player.class).getResultList();
	}
}