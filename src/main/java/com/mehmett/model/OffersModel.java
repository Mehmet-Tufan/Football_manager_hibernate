package com.mehmett.model;

import com.mehmett.controller.ManagerController;
import com.mehmett.entity.Manager;
import com.mehmett.entity.Team;
import com.mehmett.entity.TransferOffer;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

public class OffersModel {
    private static final ManagerController managerController = ManagerController.getInstance();
    private String massage;
    private String playerName;
    private Long offer;
    private String teamName;
    private String managerName;

    public OffersModel(TransferOffer transferOffer) {
        this.massage = transferOffer.getMessage();
        this.playerName = transferOffer.getPlayer().getName();
        this.offer = transferOffer.getBiddingMoney();
        this.teamName = transferOffer.getProposer().getTeamName();
        this.managerName = getManagerName(transferOffer.getProposer());
    }

    private String getManagerName(Team team) {
        Optional<Manager> proposerManager = managerController.findByTeam(team);
        String managerName = proposerManager.map(manager -> manager.getName()).orElse("None");
        return managerName;
    }

    public void displayRecievedOffer() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        System.out.println("----------- Details -----------");
        System.out.println("Message		: " + massage);
        System.out.println("For 		: "+ playerName);
        System.out.println("Offer		: "+ currencyFormat.format(offer));
        System.out.println("Team 		: "+ teamName);
        System.out.println("From		: " + managerName);
    }

}