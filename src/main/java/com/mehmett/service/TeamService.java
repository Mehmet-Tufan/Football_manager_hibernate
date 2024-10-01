package com.mehmett.service;

import com.mehmett.entity.Team;
import com.mehmett.repository.TeamRepository;

public class TeamService extends ServiceManager<Team,Long>{

	private static TeamService instance;
	private TeamService() {
		super(TeamRepository.getInstance());
	
	}
	public static TeamService getInstance() {
		if (instance == null) {
			instance = new TeamService();
			
		}
		return instance;
	}
}