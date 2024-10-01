package com.mehmett.model;

import com.mehmett.controller.TeamController;
import com.mehmett.entity.*;

import java.time.LocalDate;

public class MatchModel {
	
	private Long matchId;
	private String homeTeamName;
	private String awayTeamName;
	private Integer homeTeamScore;
	private Integer awayTeamScore;
	private Stadium stadium;
	private LocalDate matchDate;
	private boolean isPlayed;
	
	TeamController teamController = TeamController.getInstance();
	

	
	public MatchModel(Match match) {
		this.matchId=match.getId();
		this.homeTeamName = teamController.findById(match.getHomeTeamId()).get().getTeamName();
		this.awayTeamName = teamController.findById(match.getAwayTeamId()).get().getTeamName();
		this.stadium = teamController.findById(match.getHomeTeamId()).get().getStadium();
		this.matchDate = match.getMatchDate();
		this.homeTeamScore=match.getHomeTeamScore();
		this.awayTeamScore = match.getAwayTeamScore();
		this.isPlayed= match.isPlayed();
		
	}
	
	
	public void displayMatchInfo() {
		System.out.println("--------------------------------------------------");
		System.out.println("Match ID:           : " + matchId);
		System.out.println("Stadium             : " + ( stadium.getName()));
		System.out.println("Home Team           : " + homeTeamName);
		System.out.println("Away Team           : " + awayTeamName);
		System.out.println("Home Team Score     : " + (isPlayed? homeTeamScore : "N/A"));
		System.out.println("Away Team Score     : " + (isPlayed? awayTeamScore : "N/A"));
		System.out.println("Match Date          : " + matchDate);
		System.out.println("--------------------------------------------------");
	}

	public void displayMatchScores() {

		String formattedString = String.format("%-15s   %s - %s     %-20s %-10s",
				homeTeamName,
				(isPlayed ? homeTeamScore : "N/A"),
				(isPlayed ? awayTeamScore : "N/A"),
				awayTeamName,
				matchDate);
		System.out.println(formattedString);
	}
	
	public  void displayMatchStats(MatchStats matchStats) {
		System.out.println("--------------------------------------------");
		System.out.println("Match ID:           : " + matchId);
		System.out.println(homeTeamName + " " + homeTeamScore + "-" + awayTeamScore + " " + awayTeamName + " statistics");
		System.out.println("--------------------------------------------");
		System.out.printf("%-10s %-20s %-10s\n", "%" + matchStats.getHomeTeamBallPercantage(), "Ball Possession", "%" + matchStats.getAwayTeamBallPercantage());
		System.out.printf("%-10d %-20s %-10d\n", matchStats.getHomeTeam_Passes(), "Total Passes", matchStats.getAwayTeam_Passes());
		System.out.printf("%-10d %-20s %-10d\n", matchStats.getHomeTeam_Shots(), "Total Shots", matchStats.getAwayTeam_Shots());
		System.out.printf("%-10d %-20s %-10d\n", matchStats.getHomeTeam_Saves(), "Total Saves", matchStats.getAwayTeam_Saves());
		System.out.println("--------------------------------------------");
	}

	
	}