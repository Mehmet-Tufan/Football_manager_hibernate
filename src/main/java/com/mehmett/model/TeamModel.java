package com.mehmett.model;

import com.mehmett.entity.Player;
import com.mehmett.entity.Team;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TeamModel {
    private String teamName;
    private Long budget;
    private String stadiumName;
    private String stadiumLocation;
    private Integer capacity;
    private String leagueName;
    private Integer teamPower;
    private List<Player> players;

    public TeamModel(Team team) {
        this.teamName = team.getTeamName();
        this.budget = team.getBudget();
        this.stadiumName = team.getStadium().getName();
        this.stadiumLocation = team.getStadium().getLocation();
        this.capacity = team.getStadium().getCapacity();
        this.leagueName = team.getLeague().getLeaugeName();
        this.players = team.getPlayers();
        this.teamPower = calculateTeamPower();
    }

    public void displayMyTeam(){
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

        System.out.println("**********************************************");
        System.out.println("----------- " + leagueName+ " -----------" );
        System.out.println("**********************************************");
        System.out.println("---------------- " + stadiumName+ " ----------------" );
        System.out.println("stadium Location     : " + stadiumLocation);
        System.out.println("stadium Capacity     : " + capacity);
        System.out.println("----------- ----------------------- -----------" );
        System.out.println("----------- " + teamName+ " -----------" );
        System.out.println("Team Power  :" +teamPower);
        System.out.println("Budget      :"+ currencyFormat.format(budget));
        displayPlayer(players);


    }

    private void displayPlayer(List<Player> players) {
        if (!players.isEmpty()){
            for (Player player : players){
                PlayerModel playerModel = new PlayerModel(player);
                playerModel.displayPlayerForMyTeam();
            }
        }
    }

    public Integer calculateTeamPower() {
        return players.stream().collect(Collectors.averagingInt(Player::getSkillLevel)).intValue();
    }
}