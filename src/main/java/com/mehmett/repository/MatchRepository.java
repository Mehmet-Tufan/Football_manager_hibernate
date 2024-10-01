package com.mehmett.repository;

import com.mehmett.entity.Match;

public class MatchRepository extends RepositoryManager<Match,Long>{
	private static MatchRepository instance;
	private MatchRepository() {
		super(Match.class);
	}
	public static MatchRepository getInstance() {
		if (instance == null) {
			instance = new MatchRepository();
		}
		return instance;
	}
	
}