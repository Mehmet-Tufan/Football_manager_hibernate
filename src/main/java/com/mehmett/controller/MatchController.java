package com.mehmett.controller;

import com.mehmett.entity.Fixture;
import com.mehmett.entity.Match;
import com.mehmett.service.MatchService;

import java.util.List;

public class MatchController{
	private static MatchController instance;
	MatchService matchService = MatchService.getInstance();
	private MatchController() {
	
	}
	public static MatchController getInstance() {
		if (instance == null) {
			instance = new MatchController();
		}
		return instance;
	}
	public List<Match> findMatchesByFixture(Fixture fixture) {
		return  matchService.findMatchesByFixture(fixture);
		
	}
}