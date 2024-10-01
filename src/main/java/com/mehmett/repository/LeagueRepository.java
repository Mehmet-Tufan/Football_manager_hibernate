package com.mehmett.repository;

import com.mehmett.entity.League;
import com.mehmett.entity.Team;

import java.util.ArrayList;
import java.util.List;

public class LeagueRepository extends RepositoryManager<League,Long>{
	private static LeagueRepository instance;
	
	private LeagueRepository() {
		super(League.class);
	}
	public static LeagueRepository getInstance() {
		if (instance == null) {
			instance = new LeagueRepository();
		}
		return instance;
	}

	public List<Long> getTeamIds(Long leagueId){
		TeamRepository teamRepository = TeamRepository.getInstance();
		List<Team> teamList = teamRepository.findAll();
		List<Long> teamIds = new ArrayList<>();
		for (Team team : teamList) {
			if(team.getLeague().getId().equals(leagueId)){
				teamIds.add(team.getId());
			}
		}
		return teamIds;
	}

}