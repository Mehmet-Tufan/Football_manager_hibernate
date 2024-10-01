package com.mehmett.gui.league_gui;

import com.mehmett.controller.LeagueController;
import com.mehmett.controller.TeamController;
import com.mehmett.entity.League;
import com.mehmett.entity.Manager;
import com.mehmett.entity.Team;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.mehmett.utility.ConsoleTextUtils.*;


public class LeagueMenu {
    private Optional<Manager> manager;

    private final int starSize = 50;
    private LeagueController leagueController = LeagueController.getInstance();
    private TeamController teamController = TeamController.getInstance();

    private static LeagueMenu instance;
    private LeagueMenu(){

    }
    public static LeagueMenu getInstance(){
        if(instance == null){
            instance = new LeagueMenu();
        }
        return instance;
    }



    public void leagueMenu(Optional<Manager> manager){
        this.manager = manager;
        boolean opt;
        do{
            if (this.manager.isEmpty()) {
                opt = anonymousLeagueMenu();
            }
            else {
                opt = leagueMainMenu();
            }
        } while(opt);
    }

    private boolean leagueMainMenu() {
        printTitle("LEAGUE MENU");
        printMenuOptions("Manager Dashboard","Show all leagues","Show all teams","Select League",
                "Logout","Return to Main Menu");
        return leagueMenuOptions(getIntUserInput("Select: "));
    }

    private boolean leagueMenuOptions(int userInput) {
        switch(userInput){
            case 1:
                break;
            case 2:
                displayAllLeagues();
                break;
            case 3:
                displayAllTeams();
                break;

            case 4:
                League league = selectLeague();
                if(league != null){
                    new DetailedLeagueMenu().detailedLeagueMenu(league);
                }
                break;
            case 5:
                this.manager = Optional.empty();
                break;
            case 6:
                System.out.println("Returning to Main Menu");
                return false;
        }
        return true;
    }

    private boolean anonymousLeagueMenu() {
        printTitle(starSize,"LEAGUE MENU");
        printMenuOptions(starSize,"Show all leagues","Show all teams","Select League","Return to Main Menu");
        return anonymousLeagueMenuOptions(getIntUserInput("Select: "));


    }

    private boolean anonymousLeagueMenuOptions(int userInput) {
        switch(userInput){
            case 1:
                displayAllLeagues();
                break;
            case 2:
                displayAllTeams();
                break;

            case 3:
                League league = selectLeague();
                if(league != null){
                    new DetailedLeagueMenu().detailedLeagueMenu(league);
                }
                break;

            case 4:
                System.out.println("Returning to Main Menu");
                return false;
        }
        return true;
    }


    public League selectLeague() {
        displayAllLeagues();
        int choice = getIntUserInput("Select League: ");
        if(choice >0 && choice<=leagueController.findAll().size()){
            return leagueController.findAll().get(choice-1);
        }
        printErrorMessage("Invalid choice!");
        return null;
    }

    private void displayAllTeams() {
        AtomicInteger counter = new AtomicInteger(1);
        printTitle(starSize,"ALL TEAMS");
        List<Team> allTeams = teamController.findAll();
        Map<League,List<Team>> map = new HashMap<>();
        for(Team team : allTeams){
            if(!map.containsKey(team.getLeague())){
                map.putIfAbsent(team.getLeague(),new ArrayList<>());
                map.get(team.getLeague()).add(team);
            }
            else{
                map.get(team.getLeague()).add(team);
            }
        }
        map.forEach((k,v)->{
            System.out.println(k.getLeaugeName());
            for(Team team : v){
                if(!team.getTeamName().equalsIgnoreCase("bay")){
                    System.out.println(counter.getAndIncrement()+" - "+team.getTeamName());
                }

            }
            counter.set(1);
        });

    }

    private void displayAllLeagues(){
        AtomicInteger counter = new AtomicInteger(1);
        leagueController.findAll().forEach(l->{
            System.out.println(counter.getAndIncrement()+": "+l.getLeaugeName()+" - "+l.getSeason().getTitle());
        });
    }


}