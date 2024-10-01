package com.mehmett.model;

import com.mehmett.entity.BetOdds;
import com.mehmett.entity.Match;
import com.mehmett.entity.Team;
import com.mehmett.repository.TeamRepository;
import com.mehmett.utility.enums.EBetOddsState;

import java.math.BigDecimal;

public class BetOddsModel {

    BigDecimal homeTeamWins;
    BigDecimal awayTeamWins;
    BigDecimal draw;
    BigDecimal totalGoalsEqual3OrMore;
    BigDecimal totalGoalsEqual2OrLess;
    EBetOddsState eBetOddsState;
    Team homeTeam;
    Team awayTeam;
    Match match;
    int homeTeamScore;
    int awayTeamScore;

    TeamRepository teamRepository = TeamRepository.getInstance();


    public BetOddsModel(BetOdds betOdds) {
        this.homeTeamWins = betOdds.getHomeTeamWins().setScale(2, BigDecimal.ROUND_HALF_UP);
        this.awayTeamWins = betOdds.getAwayTeamWins().setScale(2, BigDecimal.ROUND_HALF_UP);
        this.draw = betOdds.getDraw().setScale(2, BigDecimal.ROUND_HALF_UP);
        this.totalGoalsEqual3OrMore = betOdds.getTotalGoalsEqual3OrMore().setScale(2, BigDecimal.ROUND_HALF_UP);
        this.totalGoalsEqual2OrLess = betOdds.getTotalGoalsEqual2OrLess().setScale(2, BigDecimal.ROUND_HALF_UP);
        this.homeTeam = teamRepository.findById(betOdds.getMatch().getHomeTeamId()).get();
        this.awayTeam = teamRepository.findById(betOdds.getMatch().getAwayTeamId()).get();
        this.match = betOdds.getMatch();
        this.homeTeamScore = match.getHomeTeamScore();
        this.awayTeamScore = match.getAwayTeamScore();

    }

    public void displayHomeTeamAndAwayTeamsName() {

        String formattedString = String.format("%-15s   %s - %s     %-20s",
                homeTeam.getTeamName(),
                (match.isPlayed() ? homeTeamScore : "N/A"),
                (match.isPlayed() ? awayTeamScore : "N/A"),
                awayTeam.getTeamName());
        System.out.println(formattedString);
    }

    public void displayBetOdd(){
        System.out.println("---------------------------------------------------------------");
        System.out.println("HomeTeam  : "+homeTeam.getTeamName());
        System.out.println("AwayTeam  : "+awayTeam.getTeamName());
        System.out.println("Match Date: "+match.getMatchDate());
        System.out.println("---------------------------------------------------------------");
        System.out.println("1 - HomeTeam Wins               :  " + homeTeamWins);
        System.out.println("2 - AwayTeam Wins               :  " + awayTeamWins);
        System.out.println("3 - Draw                        :  " + draw);
        System.out.println("4 - Total Goals - More Than 2.5 :  " + totalGoalsEqual3OrMore);
        System.out.println("5 - Total Goals - Less Than 2.5 :  " + totalGoalsEqual2OrLess);
        System.out.println("---------------------------------------------------------------");
    }


}