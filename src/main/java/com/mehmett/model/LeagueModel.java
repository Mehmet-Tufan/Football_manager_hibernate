package com.mehmett.model;

import com.mehmett.controller.MatchController;
import com.mehmett.controller.TeamController;
import com.mehmett.entity.*;
import com.mehmett.repository.MatchRepository;
import com.mehmett.repository.StatsRepository;
import com.mehmett.utility.enums.EDivision;
import com.mehmett.utility.enums.ERegion;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LeagueModel {

	private StatsRepository statsRepository = StatsRepository.getInstance();
	private TeamController teamController = TeamController.getInstance();
	
	private String leaugeName;
	private ERegion region;
	private EDivision division;
	private LocalDate BEGINNING_OF_SEASON;
	private League league;
	private List<Match> matches;
	
	
	MatchController matchController = MatchController.getInstance();
	public LeagueModel(League league) {
		this.league = league;
		this.leaugeName = league.getLeaugeName();
		this.matches = matchController.findMatchesByFixture(league.getFixture());
		this.BEGINNING_OF_SEASON = league.getSeason().getBEGINNING_OF_SEASON();
		this.region=league.getRegion();
		this.division=league.getDivision();
	
	}
	
	public void displayLeagueInfo() {
		System.out.println("--------------------------------------------------");
		System.out.println("League Information:");
		System.out.println("--------------------------------------------------");
		System.out.println("League Name           : " + leaugeName);
		System.out.println("League Region         : " + region);
		System.out.println("League Division       : " + division);
		System.out.println("League Season         : " + league.getSeason().getTitle());
		System.out.println("--------------------------------------------------");
		System.out.println("Season Start:         : " + BEGINNING_OF_SEASON);
		System.out.println("==================================================");
	}
	
	public void displayFixture() {
		AtomicInteger week = new AtomicInteger(1);
		AtomicInteger sayac = new AtomicInteger(0);
		
		System.out.println("########################################################################");
		matches.stream()
		       .forEach(m -> {
			       if(sayac.get()%10==0){
				       System.out.println(week.getAndIncrement()+". Hafta Fikstürü");
			       }
			       new MatchModel(m).displayMatchInfo();
			       sayac.getAndIncrement();
		       });
		System.out.println("########################################################################");
	}
	
	
	//
	public void displayWeeklyFixture(long week) {
		System.out.println("Week " + week + " Fixture:");
		System.out.println("----------------------------------");
		matches.stream()
		       .filter(m -> !m.getHomeTeamId().equals(20L) && !m.getAwayTeamId().equals(20L))
		       .skip((week - 1) * 9).limit(9)
		       .forEach(m -> new MatchModel( m).displayMatchInfo());
		System.out.println("----------------------------------");
		
	}
	
	public void displayStandings() {
		AtomicInteger rank = new AtomicInteger(1);

		System.out.printf("%-4s %-20s %3s %3s %3s %3s %3s %3s %4s %3s%n",
		                  "No", "Team Name", "GP", "W", "D", "L", "F", "A", "GD", "P");

		statsRepository.findAll().stream()
		                     .filter(t -> !t.getId().equals(20L))
		                     .sorted(Comparator.comparing(Stats::getPoints, Comparator.reverseOrder())
		                                       .thenComparing(Stats::getGoalDifference,Comparator.reverseOrder())
		                                       .thenComparing(Stats::getGoalsFor,Comparator.reverseOrder()))
		                     .map(t -> String.format("%-4d %-20s %3d %3d %3d %3d %3d %3d %4d %3d",
		                                             rank.getAndIncrement(),
		                                           t.getTeam().getTeamName(),
		                                             t.getGamesPlayed(),
		                                             t.getTotalWins(),
		                                             t.getTotalDraws(),
		                                             t.getTotalLoses(),
		                                             t.getGoalsFor(),
		                                             t.getGoalsAgainst(),
		                                             t.getGoalDifference(),
		                                             t.getPoints()))
		                     .forEach(System.out::println);
		System.out.println("----------------------------------------------");
	}
//
	public void displayPlayedMatches() {
		System.out.println("-------------------------");
		System.out.println(league.getLeaugeName()+": PLAYED GAMES SO FAR...");
		System.out.println();

		MatchRepository.getInstance().findAll().stream()
		                     .filter(Match::isPlayed)
		                     .forEach(m->new MatchModel(m).displayMatchInfo());
	}
}