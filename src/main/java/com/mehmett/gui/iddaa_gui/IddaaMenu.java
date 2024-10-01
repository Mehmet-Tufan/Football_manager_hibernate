package com.mehmett.gui.iddaa_gui;

import com.mehmett.entity.*;
import com.mehmett.model.BetOddsModel;
import com.mehmett.repository.GamblerRepository;
import com.mehmett.service.BetOddsService;
import com.mehmett.service.BetService;
import com.mehmett.service.MatchService;
import com.mehmett.utility.enums.EBetState;
import com.mehmett.utility.enums.EOddSelection;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.mehmett.utility.ConsoleTextUtils.*;

public class IddaaMenu {
    private Gambler gambler;
    private GamblerRepository gamblerRepository = GamblerRepository.getInstance();
    private MatchService matchService = MatchService.getInstance();
    private BetOddsService betOddsService = BetOddsService.getInstance();
    private BetService betService = BetService.getInstance();
    private Bet bet;
    private Map<BetOdds, EOddSelection> selectionMap;

    public void iddaaMenu() {
        boolean opt = true;
        while (opt) {
            opt = loginRegisterMenu();
        }
    }

    private boolean loginRegisterMenu() {
        printMenuOptions("Login", "Register", "Return To Main Menu");
        return loginRegisterMenuOptions(getIntUserInput("Select: "));

    }

    private boolean loginRegisterMenuOptions(int userInput) {
        bet = Bet.builder()
                .betState(EBetState.ON_WAIT).totalBetOdd(BigDecimal.valueOf(1.0)).build();
        selectionMap = new HashMap<>();

        switch (userInput) {
            case 1:
                Optional<Gambler> gamblerOptional = loginGambler();
                if (gamblerOptional.isPresent()) {
                    gambler = gamblerOptional.get();
                    return oynaKazanMenu();
                }

                break;
            case 2:
                //todo: gamblerRegister():
                break;
            case 3:
                return false;
        }
        return true;
    }

    private Optional<Gambler> loginGambler() {
        String username = getStringUserInput("Username:");
        String password = getStringUserInput("Password:");
        return gamblerRepository.findByUsernameAndPassword(username, password);
    }

    private boolean oynaKazanMenu() {


        printMenuOptions("Add a match to your bet",
                "Preview your current bet",
                "Complete your bet",
                "List my previous bets",
                "Return To Main Menu");
        int selection = getIntUserInput("Selection:");
        switch (selection) {
            case 1:
                BetOdds betOdds = selectAMatchToPlaceBet();
                addBetOdds(betOdds);
                oynaKazanMenu();
                break;
            case 2:
                System.out.println("Toplam oran: "+bet.getTotalBetOdd());
                //BetModel -> displayCurrentBet oluşturulacak.
                oynaKazanMenu();
                    break;
            case 3:
                //Kumarbazın kuponu finishlemesi için
                break;
            case 4:
                //kumarbazın geçmiş kuponlarını listele
                oynaKazanMenu();
                break;
        }
        return false;
    }

    private BetOdds selectAMatchToPlaceBet() {
        List<Match> matches = matchService.getCurrentWeeksFixture();
        List<BetOdds> betOddsListByMatches = betOddsService.getBetOddsListByMatches(matches);
        int counter = 0;
        for (BetOdds betOdds : betOddsListByMatches) {
            System.out.print(++counter + ".");
            new BetOddsModel(betOdds).displayHomeTeamAndAwayTeamsName();
        }

        int selection = getIntUserInput("Select a match number to place a bet: ");
        if (selection > 0 && selection <= betOddsListByMatches.size()) {
            return betOddsListByMatches.get(selection - 1);
        }
        System.out.println("Invalid choice. Please try again later.");
        return null;
    }

    //HOME_TEAM_WINS,AWAY_TEAM_WINS,DRAW,UST,ALT
    private void addBetOdds(BetOdds betOdds) {
        new BetOddsModel(betOdds).displayBetOdd();
        int selection = getIntUserInput("Selection:");
        betService.addOddsToBet(bet, betOdds, selection,selectionMap);
    }


}