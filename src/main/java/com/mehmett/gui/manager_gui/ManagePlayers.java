package com.mehmett.gui.manager_gui;

import com.mehmett.controller.ManagerController;
import com.mehmett.controller.PlayerController;
import com.mehmett.controller.TransferOfferController;
import com.mehmett.entity.ContractOffer;
import com.mehmett.entity.Manager;
import com.mehmett.entity.Player;
import com.mehmett.entity.TransferOffer;
import com.mehmett.model.OffersModel;
import com.mehmett.model.PlayerModel;
import com.mehmett.repository.TransferRepository;
import com.mehmett.service.ContractOfferService;
import com.mehmett.service.TransferOfferService;
import com.mehmett.service.TransferService;
import com.mehmett.utility.ConsoleTextUtils;
import com.mehmett.utility.enums.EOfferResponse;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.mehmett.utility.ConsoleTextUtils.*;

public class ManagePlayers {
    private Manager manager;
    ManagerController managerController = ManagerController.getInstance();
    PlayerController playerController = PlayerController.getInstance();
    TransferOfferController transferOfferController = TransferOfferController.getInstance();
    ContractOfferService contractOfferService = ContractOfferService.getInstance();

    private final int starSize = 50;


    public Optional<Manager> managePlayers(Optional<Manager> manager) {
        AtomicBoolean opt = new AtomicBoolean(false);
        manager.ifPresentOrElse(m -> {
            this.manager = m;
            do {
                opt.set(managePlayersMenu());
            } while (opt.get());
        }, () -> this.manager = null);

        return Optional.ofNullable(this.manager);
    }


    private boolean managePlayersMenu() {
        printTitle(starSize, "Manage Players Menu");
        printMenuOptions(starSize,
                "Make an offer for player",
                "Display offers for my team",
                "Make contract offer for player",
                "Display my previous offers",
                "Display my Transfer history",
                "Return To Dashboard");
        return managePlayersMenuOptions(getIntUserInput("Select: "));
    }

    private boolean managePlayersMenuOptions(int intUserInput) {
        List<TransferOffer> recievedOffers = transferOfferController.getOffersForReceiver(manager);
        switch (intUserInput) {
            case 1: {
                Player player = searchPlayer();
                if (player != null) {
                    makeAnOfferForPlayer(player);
                }

                break;
            }
            case 2: {
                showOnWaitOffers(recievedOffers);
                break;
            }
            case 3:{
                makePlayerContractOffer();
                break;

            }

            case 4: {
                showOffersHistory(recievedOffers);
                break;
            }
            case 5: {
                showTransferHistory();
                break;
            }
            case 6: {// Exit..
                return false;
            }
        }
        return true;
    }

    private void showTransferHistory() {
        AtomicInteger counter = new AtomicInteger(1);
        TransferRepository.getInstance().findAll().stream()
                .filter(transfer -> transfer.getContractOffer().getTransferOffer().getProposer().equals(manager.getTeam()) ||
                        transfer.getContractOffer().getTransferOffer().getReceiver().equals(manager.getTeam()))
                .forEach(transfer -> {
                    System.out.print(counter.getAndIncrement() + "- ");
                    String formattedString = String.format("%-15s  %s  %s",
                            transfer.getContractOffer().getTransferOffer().getPlayer().getName(),
                            transfer.getContractOffer().getTransferOffer().getBiddingMoney(),
                            transfer.getContractOffer().getTransferOffer().getProposer().equals(manager.getTeam())? "BOUGHT" : "SOLD");
                    System.out.println(formattedString);
                });
    }

    private void makePlayerContractOffer() {
        TransferOffer transferOffer = selectSentAndAcceptedOffer();
        if(transferOffer!=null){
            ContractOffer contractOffer = MakeAnContractOfferToPlayer(transferOffer);
            if(contractOffer.getResponse().equals(EOfferResponse.ACCEPTED)) {
                TransferService.getInstance().finalizeTransfer(contractOffer);
            }
        }
        else{
            System.out.println("No Accepted offers! Your offers are on wait");
        }
    }

    private void showOffersHistory(List<TransferOffer> recievedOffers) {
        List<TransferOffer> previousOffers =
                recievedOffers.stream().filter(offer ->
                                offer.getResponse().equals(EOfferResponse.ACCEPTED) ||
                                        offer.getResponse().equals(EOfferResponse.REJECTED))
                        .toList();
        displayOffers(previousOffers);
        if (!recievedOffers.isEmpty()) {
            selectRecieved(previousOffers);
        }
        else{
            System.out.println("No received offers");
        }
    }

    private void showOnWaitOffers(List<TransferOffer> recievedOffers) {
        List<TransferOffer> onWaitList =
                recievedOffers.stream().filter(offer -> offer.getResponse().equals(EOfferResponse.ON_WAIT)).toList();
        displayOffers(onWaitList);
        if (!onWaitList.isEmpty()) {
            Optional<TransferOffer> transferOffer = selectRecieved(onWaitList);
            transferOffer.ifPresent(this::replyOffer);
        }
        else{
            System.out.println("No received offers");
        }
    }

    private void displayOffers(List<TransferOffer> offerList) {
        if (!offerList.isEmpty()) {
            int counter = 1;
            for (TransferOffer offers : offerList) {
                System.out.println(counter + "- " + offers.getMessage() + " From: " + offers.getProposer().getTeamName());
                counter++;
            }
            System.out.println();
        }
    }

    private void replyOffer(TransferOffer transferOffer) {
        printMenuOptions("Reply", "Return To Dashboard");
        int userSelection = getIntUserInput("Select: ");
        switch (userSelection) {
            case 1: {
                printMenuOptions("Accept Offer", "Reject Offer");
                int userReplySelection = getIntUserInput("Select Reply: ");
                transferOfferController.replyToOffer(transferOffer, userReplySelection);
                break;
            }
        }
    }


    private Optional<TransferOffer> selectRecieved(List<TransferOffer> recievedOffers) {
        printMenuOptions("Select Offer", "Return To Dashboard");
        int userSelection = getIntUserInput("Select: ");
        switch (userSelection) {
            case 1: {
                int userInput = getIntUserInput("Select Offer: ");
                if (userInput <= 0 || userInput - 1 > recievedOffers.size() - 1) {
                    System.out.println("Invalid Selection");
                } else {
                    TransferOffer transferOfferMassage = recievedOffers.get(userInput - 1);
                    new OffersModel(transferOfferMassage).displayRecievedOffer();
                    return Optional.of(transferOfferMassage);
                }
            }
        }
        return Optional.empty();
    }


    private void makeAnOfferForPlayer(Player player) {
        new PlayerModel(player).displayPlayerForOffer();
        printMenuOptions("Make An Offer For Player", "Return");
        int choice = getIntUserInput("Your choice: ");
        switch (choice) {
            case 1:
                // DTO Olsayadı bu kodlar service'de olacaktı!
                TransferOffer transferOffer = new TransferOffer();
                transferOffer.setPlayer(player);
                transferOffer.setMessage(getStringUserInput("Message: "));
                transferOffer.setReceiver(player.getTeam());
                transferOffer.setProposer(manager.getTeam());
                transferOffer.setResponse(EOfferResponse.ON_WAIT);
                transferOffer.setBiddingMoney(ConsoleTextUtils.getLongInput("State your offer"));
                transferOfferController.save(transferOffer);
                break;
            case 2: {
                break;
            }

        }

    }

    private Player searchPlayer() {
        String nameToSearch = getStringUserInput("Search player: ");
        List<Player> playerList = playerController.makeAnOfferForPlayer(nameToSearch);
        if (!playerList.isEmpty()) {
            AtomicInteger sayac = new AtomicInteger(1);
            playerList.stream().forEach(p -> {
                System.out.print(sayac.getAndIncrement() + "- ");
                String formattedString = String.format("%-15s   %s  %s",
                        p.getName(),
                        p.getSkillLevel(),
                        p.getPosition());
                System.out.println(formattedString);
            });
            int intUserInput = getIntUserInput("Pick a player to offer: ");
            return playerList.get(intUserInput - 1);
        }
        return null;
    }

    private ContractOffer MakeAnContractOfferToPlayer(TransferOffer transferOffer) {
        long salaryOffer = getIntUserInput("Place your contract offer for player:");
        return contractOfferService.makeOfferToPlayer(transferOffer, salaryOffer);
    }

    private TransferOffer selectSentAndAcceptedOffer(){
        List<TransferOffer> list = TransferOfferService.getInstance().findAll().stream()
                .filter(t->t.getProposer()==manager.getTeam())
                .filter(t -> t.getResponse().equals(EOfferResponse.ACCEPTED))
                .toList();
        int counter = 0;
        for(TransferOffer offer : list){
            System.out.println(++counter + " : " + offer.getPlayer().getName() + " - " +  offer.getResponse());
        }
        int selection = getIntUserInput("Select offer to continue:");
        if(selection>0 && selection<=list.size()){
            return list.get(selection-1);
        }
        return null;
    }
}