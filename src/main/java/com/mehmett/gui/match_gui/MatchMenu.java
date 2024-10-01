package com.mehmett.gui.match_gui;

import com.mehmett.entity.*;
import com.mehmett.gui.league_gui.LeagueMenu;
import com.mehmett.gui.manager_gui.ManagerLoginRegister;
import com.mehmett.model.LeagueModel;
import com.mehmett.model.MatchModel;
import com.mehmett.repository.MatchStatsRepository;
import com.mehmett.service.MatchService;
import com.mehmett.service.SeasonService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.mehmett.utility.ConsoleTextUtils.*;

public class MatchMenu {
    private Optional<Manager> manager;
    private League league;
    private LocalDate currentDate;
    private static MatchMenu instance;
    MatchService matchService = MatchService.getInstance();
	MatchStatsRepository matchStatsRepository = MatchStatsRepository.getInstance();

    private MatchMenu() {
    }

    public static MatchMenu getInstance() {
        if (instance == null) {
            instance = new MatchMenu();
        }
        return instance;
    }


    public void matchMenu(Optional<Manager> manager) {
        this.manager = manager;
        boolean opt = true;
        this.league = LeagueMenu.getInstance().selectLeague();
        if (this.league == null) {
            System.out.println("No such league found!");
        } else {
            do {
                this.currentDate = this.league.getSeason().getCurrentDate();
                if (!this.manager.isEmpty()) opt = matchInfoMenu();
                else this.manager = ManagerLoginRegister.getInstance().loginRegisterMenu();

            } while (opt);
        }
    }

    private boolean matchInfoMenu() {
        printTitle("Match Menu");
        printMenuOptions("Play a Match",
				"Play Several Match",
				"Show Played Matches",
				"Show Standings",
				"Show Match Statistics",
				"Return To Main Menu");
        LocalDate currentDate = league.getSeason().getCurrentDate();
        printSuccessMessage("Current date: " + currentDate.toString());
        return matchInfoMenuOptions(getIntUserInput("Select: "));
    }

    private boolean matchInfoMenuOptions(int userInput) {
        switch (userInput) {
            case 1:
                playMatch();
                break;
            case 2:
                getDaysToAdvanceFromUser();
                break;

            case 3:
                List<Match> playedMatch = matchService.getPlayedMatches();
				for(Match match : playedMatch) {
					new MatchModel(match).displayMatchScores();
				}
                break;


            case 4:
                new LeagueModel(league).displayStandings();
                break;
			case 5:
				Match match = displayWeeklyFixture();
				if(match != null) {
					displayMatchStats(match);
				}
				break;
            case 6:
                System.out.println("Returning to Main Menu");
                return false;
        }
        return true;
    }


    public void playMatch() {
        Match match = matchService.playMatch();
        SeasonService.getInstance().updateCurrentDate(league.getSeason(), match);
    }

    public void getDaysToAdvanceFromUser() {

        matchService.advanceDateByDays(league);

    }
	public Match displayWeeklyFixture(){
		int counter =0;
		int userInput = getIntUserInput("Enter week to display:");
		List<Match> weeklyMatches = matchService.getWeeklyFixture(userInput);
		for(Match match : weeklyMatches) {
			System.out.print(++counter + ". ");
			new MatchModel(match).displayMatchScores();
		}
		int matchNo = getIntUserInput("Select match number:");
		if(matchNo>0|| matchNo<weeklyMatches.size()) {
			return weeklyMatches.get(matchNo-1);
		}
		System.out.println("Invalid match number!");
		return null;
	}

	public void displayMatchStats(Match match){
		MatchStats matchStats = matchStatsRepository.findById(match.getId()).get();
		new MatchModel(match).displayMatchStats(matchStats);
	}




}