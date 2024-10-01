package com.mehmett.repository;

import com.mehmett.entity.Team;

public class TeamRepository extends RepositoryManager<Team,Long>{
	private static TeamRepository instance;
	private TeamRepository() {
		super(Team.class);
	}
	public static TeamRepository getInstance() {
		if (instance == null) {
			instance = new TeamRepository();
		}
		return instance;
	}
}