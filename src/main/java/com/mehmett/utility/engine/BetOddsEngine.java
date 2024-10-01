package com.mehmett.utility.engine;

import com.mehmett.entity.BetOdds;
import com.mehmett.entity.Match;
import com.mehmett.entity.Player;
import com.mehmett.entity.Team;
import com.mehmett.repository.TeamRepository;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Collectors;

public class BetOddsEngine {
    private static  Random random = new Random();
    private static TeamRepository teamRepository = TeamRepository.getInstance();



    public static BetOdds getMatchBetOdds(Match match) {
        Team homeTeam = teamRepository.findById(match.getHomeTeamId()).get();
        Team awayTeam = teamRepository.findById(match.getAwayTeamId()).get();
        double homeTeamPower = homeTeam.getPlayers().stream().collect(Collectors.averagingDouble(Player::getSkillLevel));
        double awayTeamPower = awayTeam.getPlayers().stream().collect(Collectors.averagingDouble(Player::getSkillLevel));

        BetOdds betOdds = BetOdds.builder().match(match).build();
        BigDecimal homeTeamWins = BigDecimal.valueOf(calculateHomeTeamWinOdd(homeTeamPower, awayTeamPower));
        BigDecimal awayTeamWins = BigDecimal.valueOf(calculateAwayTeamWinOdd(awayTeamPower, homeTeamPower));
        Double drawDouble = ((homeTeamWins.doubleValue()+awayTeamWins.doubleValue())/2)+ random.nextDouble(0.12);//random standart sapma için
        BigDecimal draw = BigDecimal.valueOf(drawDouble);
        BigDecimal totalGoals3OrMore = BigDecimal.valueOf(calculate3GoalsOrAboveOdd());
        BigDecimal totalGoals2OrLess = BigDecimal.valueOf(calculate2GoalsOrBeloveOdd());

        betOdds.setHomeTeamWins(homeTeamWins);
        betOdds.setAwayTeamWins(awayTeamWins);
        betOdds.setDraw(draw);
        betOdds.setTotalGoalsEqual3OrMore(totalGoals3OrMore);
        betOdds.setTotalGoalsEqual2OrLess(totalGoals2OrLess);
        return betOdds;

    }

    private static Double calculateHomeTeamWinOdd(Double homeTeamPower, Double awayTeamPower) {
        double homeTeamWinProbability = 50+(homeTeamPower-awayTeamPower)*0.5;
        return ((1/homeTeamWinProbability)*100);


    }
    private static  Double calculateAwayTeamWinOdd(Double homeTeamPower, Double awayTeamPower){
        double awayTeamWinProbability = 50+(awayTeamPower-homeTeamPower)*0.5;
        return ((1/awayTeamWinProbability)*100);
    }

    private static  Double calculate3GoalsOrAboveOdd(){
        return random.nextDouble(1.30,1.61);
    }
    private static  Double calculate2GoalsOrBeloveOdd(){
        return random.nextDouble(1.65,2.10);
    }




}