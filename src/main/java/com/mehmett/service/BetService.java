package com.mehmett.service;

import com.mehmett.entity.Bet;
import com.mehmett.entity.BetOdds;
import com.mehmett.entity.Gambler;
import com.mehmett.repository.BetRepository;
import com.mehmett.utility.ConsoleTextUtils;
import com.mehmett.utility.enums.EOddSelection;

import java.math.BigDecimal;
import java.util.Map;

public class BetService extends ServiceManager<Bet, Long> {
    private static BetService instance;

    private BetService() {
        super(BetRepository.getInstance());
    }

    public static BetService getInstance() {
        if (instance == null) {
            instance = new BetService();
        }
        return instance;
    }


    public boolean checkGamblerAccountBalance(Gambler gambler, Double priceToBet) {
        if (gambler.getAccountBalance() > priceToBet) {
            return true;
        }
        System.out.println("Your accountBalance is not enough to place this amount for bet.");
        return false;
    }

    //HOME_TEAM_WINS,AWAY_TEAM_WINS,DRAW,UST,ALT
    public void addOddsToBet(Bet bet, BetOdds betOdds, int selection, Map<BetOdds, EOddSelection> selectionMap) {
        BigDecimal oldValue = bet.getTotalBetOdd();
        if (selectionMap.containsKey(betOdds)) {
            undoTotalBetOdd(bet,betOdds,selectionMap.get(betOdds));
        }
        switch (selection) {
            case 1:
                selectionMap.put(betOdds, EOddSelection.HOME_TEAM_WINS);
                BigDecimal m1 = bet.getTotalBetOdd().multiply(betOdds.getHomeTeamWins());
                bet.setTotalBetOdd(m1.setScale(2, BigDecimal.ROUND_HALF_UP));
                break;
            case 2:
                selectionMap.put(betOdds, EOddSelection.AWAY_TEAM_WINS);
                BigDecimal m2 = bet.getTotalBetOdd().multiply(betOdds.getAwayTeamWins());
                bet.setTotalBetOdd(m2.setScale(2, BigDecimal.ROUND_HALF_UP));
                break;
            case 3:
                selectionMap.put(betOdds, EOddSelection.DRAW);
                BigDecimal m3 = bet.getTotalBetOdd().multiply(betOdds.getDraw());
                bet.setTotalBetOdd(m3.setScale(2, BigDecimal.ROUND_HALF_UP));
                break;
            case 4:
                selectionMap.put(betOdds, EOddSelection.UST);
                BigDecimal m4 = bet.getTotalBetOdd().multiply(betOdds.getTotalGoalsEqual3OrMore());
                bet.setTotalBetOdd(m4.setScale(2, BigDecimal.ROUND_HALF_UP));
                break;
            case 5:
                selectionMap.put(betOdds, EOddSelection.ALT);
                BigDecimal m5 = bet.getTotalBetOdd().multiply(betOdds.getTotalGoalsEqual2OrLess());
                bet.setTotalBetOdd(m5.setScale(2, BigDecimal.ROUND_HALF_UP));
                break;
            default:
                bet.setTotalBetOdd(oldValue);
                return;
        }
        ConsoleTextUtils.printSuccessMessage("Your choice succesfully added to your bet!");
    }

    public void undoTotalBetOdd(Bet bet, BetOdds betOdds, EOddSelection selection) {
        switch (selection) {
            case HOME_TEAM_WINS:
                bet.setTotalBetOdd(bet.getTotalBetOdd()
                        .divide(betOdds.getHomeTeamWins(), 2, BigDecimal.ROUND_HALF_UP));
                break;
            case AWAY_TEAM_WINS:
                bet.setTotalBetOdd(bet.getTotalBetOdd()
                        .divide(betOdds.getAwayTeamWins(), 2, BigDecimal.ROUND_HALF_UP));
                break;
            case DRAW:
                bet.setTotalBetOdd(bet.getTotalBetOdd()
                        .divide(betOdds.getDraw(), 2, BigDecimal.ROUND_HALF_UP));
                break;
            case UST:
                bet.setTotalBetOdd(bet.getTotalBetOdd()
                        .divide(betOdds.getTotalGoalsEqual3OrMore(), 2, BigDecimal.ROUND_HALF_UP));
            case ALT:
                bet.setTotalBetOdd(bet.getTotalBetOdd()
                        .divide(betOdds.getTotalGoalsEqual2OrLess(), 2, BigDecimal.ROUND_HALF_UP));

        }
    }


}