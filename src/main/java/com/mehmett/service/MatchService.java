package com.mehmett.service;

import com.mehmett.entity.*;
import com.mehmett.repository.*;
import com.mehmett.utility.ConsoleTextUtils;
import com.mehmett.utility.engine.MatchEngine;
import com.mehmett.utility.engine.MatchEngine_NoComment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.mehmett.utility.ConsoleTextUtils.getIntUserInput;

public class MatchService extends ServiceManager<Match, Long> {
	MatchRepository matchRepository = MatchRepository.getInstance();
	StatsRepository statsRepository = StatsRepository.getInstance();
	TeamService teamService = TeamService.getInstance();
	
	private static MatchService instance;
	
	private MatchService() {
		super(MatchRepository.getInstance());
	}
	
	public static MatchService getInstance() {
		if (instance == null) {
			instance = new MatchService();
		}
		return instance;
	}
	
	public List<Match> findMatchesByFixture(Fixture fixture) {
		List<Match> matches = matchRepository.findByFieldNameAndValue("fixture", fixture);
		if (!matches.isEmpty()) {
			return matches.stream().sorted(Comparator.comparing(Match::getMatchDate)).toList();
		}
		ConsoleTextUtils.printErrorMessage("No matches found for fixture: " + fixture);
		return new ArrayList<>();
	}
	
	public Match playMatch() {
		List<Match> matches = matchRepository.findByFieldNameAndValue("isPlayed", false).stream().filter(match -> {
			Team homeTeam = TeamRepository.getInstance().findById(match.getHomeTeamId()).get();
			Team awayTeam = TeamRepository.getInstance().findById(match.getAwayTeamId()).get();
			return !(homeTeam.getTeamName().equals("BAY") || awayTeam.getTeamName().equalsIgnoreCase("BAY"));
		}).sorted(Comparator.comparing(Match::getMatchDate)).toList();
		
		Match match = matches.getFirst();
		MatchEngine.simulateMatch(match);
		
		Stats homeTeamStats = statsRepository.findById(match.getHomeTeamId()).get();
		Stats awayTeamStats = statsRepository.findById(match.getAwayTeamId()).get();
		
		
		updatePoints(match, homeTeamStats, awayTeamStats);
		match.setPlayed(true);

		matchRepository.update(match);
		displayMatchScore(homeTeamStats, match, awayTeamStats);
		return match;
	}

	private static void displayMatchScore(Stats homeTeamStats, Match match, Stats awayTeamStats) {
		System.out.println("---------------------------------------------------------------");
		String formattedString = String.format("%-15s   %s - %s     %-20s",
				homeTeamStats.getTeam().getTeamName(),
				match.getHomeTeamScore(),
				match.getAwayTeamScore(),
				awayTeamStats.getTeam().getTeamName());
		System.out.println(formattedString);
		System.out.println("---------------------------------------------------------------");
	}

	private void updatePoints(Match match, Stats homeTeam, Stats awayTeam) {
		homeTeam.setGamesPlayed(homeTeam.getGamesPlayed() + 1);
		awayTeam.setGamesPlayed(awayTeam.getGamesPlayed() + 1);
		
		homeTeam.setGoalsFor(homeTeam.getGoalsFor() + match.getHomeTeamScore());
		awayTeam.setGoalsFor(awayTeam.getGoalsFor() + match.getAwayTeamScore());
		homeTeam.setGoalsAgainst(homeTeam.getGoalsAgainst() + match.getAwayTeamScore());
		awayTeam.setGoalsAgainst(awayTeam.getGoalsAgainst() + match.getHomeTeamScore());
		homeTeam.setGoalDifference(homeTeam.getGoalsFor() - homeTeam.getGoalsAgainst());
		awayTeam.setGoalDifference(awayTeam.getGoalsFor() - awayTeam.getGoalsAgainst());
		
		if (match.getHomeTeamScore() > match.getAwayTeamScore()) {
			homeTeam.setTotalWins(homeTeam.getTotalWins() + 1);
			awayTeam.setTotalLoses(awayTeam.getTotalLoses() + 1);
		}
		else if (match.getAwayTeamScore() > match.getHomeTeamScore()) {
			awayTeam.setTotalWins(awayTeam.getTotalWins() + 1);
			homeTeam.setTotalLoses(homeTeam.getTotalLoses() + 1);
		}
		else {
			homeTeam.setTotalDraws(homeTeam.getTotalDraws() + 1);
			awayTeam.setTotalDraws(awayTeam.getTotalDraws() + 1);
		}
		homeTeam.setPoints((homeTeam.getTotalWins() * 3) + (homeTeam.getTotalDraws()));
		awayTeam.setPoints((awayTeam.getTotalWins() * 3) + awayTeam.getTotalDraws());
		statsRepository.updateAll(List.of(homeTeam, awayTeam));
	}
	
	public List<Match> getCurrentWeeksFixture() { //tüm maçları oynanan hafta varsa onu es geçip bir dahaki haftayı
		// listelicek.
		int week;
		
		List<Match> matches =
				matchRepository.findAll().stream().filter(m -> m.getHomeTeamId() != 20L && m.getAwayTeamId() != 20L)
				               .sorted(Comparator.comparing(Match::getMatchDate)).toList();
		for (int i = 9; i <= matches.size(); i += 9) {
			Match match1 = matches.get(i - 1);
			Match match2 = matches.get(i - 2);
			if (!match1.isPlayed() || !match2.isPlayed()) {
				List<Match> weeklyFixture = matches.stream().skip(i - 9).limit(9).toList();
				week = i / 9;
				System.out.println("Week " + week + " Program");
				return weeklyFixture.stream().sorted(Comparator.comparing(Match::getMatchDate)).toList();
			}
		}
		
		return new ArrayList<>();
	}
	
	public void advanceDateByDays(League league) {
		
		Integer daysToAdd = 0;
		try {
			daysToAdd = getIntUserInput("How many days do you want to advance: ");
		}
		catch (Exception e) {
			System.out.println("Please enter valid input.");
		}
		
		if (daysToAdd < 0) {
			System.out.println("404 error");
			return;
		}
		Season season = league.getSeason();
		
		season.setCurrentDate(season.getCurrentDate().plusDays(daysToAdd));
		
	 
		System.out.println("-------------------------------------------------------");
		System.out.println("New currentDate is = " + season.getCurrentDate());
		System.out.println("-------------------------------------------------------");
		
		SeasonRepository.getInstance().update(season);
		playUpcomingMatches(season.getCurrentDate());
	}
	
	//Bugün ve bugünden önceki
	public void playUpcomingMatches(LocalDate date) {
		
		List<Match> matches = matchRepository.findByFieldNameAndValue("isPlayed", false).stream().filter(match -> {
			Team homeTeam = TeamRepository.getInstance().findById(match.getHomeTeamId()).get();
			Team awayTeam = TeamRepository.getInstance().findById(match.getAwayTeamId()).get();
			return !(homeTeam.getTeamName().equals("BAY") || awayTeam.getTeamName().equalsIgnoreCase("BAY"));
		}).sorted(Comparator.comparing(Match::getMatchDate)).toList();
		
		matches.stream() .filter(m -> !m.isPlayed()).filter(m -> m.getMatchDate().isBefore(date.plusDays(1)))
		       .forEach(this::playMatchNoComment);
	}
	
	public void playMatchNoComment(Match match) {
		MatchEngine_NoComment.simulateMatch(match);
		
		Stats homeTeamStats = statsRepository.findById(match.getHomeTeamId()).get();
		Stats awayTeamStats = statsRepository.findById(match.getAwayTeamId()).get();
		
		
		updatePoints(match, homeTeamStats, awayTeamStats);
		match.setPlayed(true);
		displayMatchScore(homeTeamStats, match, awayTeamStats);
		matchRepository.update(match);
		
	}

	public List<Match> getPlayedMatches(){
		return matchRepository.findAll().stream()
				.filter(Match::isPlayed).toList();
	}

	public List<Match> getWeeklyFixture(int week){
		return matchRepository.findAll().stream()
				.filter(m -> !m.getHomeTeamId().equals(20L) && !m.getAwayTeamId().equals(20L))
				.sorted(Comparator.comparing(Match::getMatchDate))
				.skip((week - 1) * 9).limit(9).toList();
	}
}