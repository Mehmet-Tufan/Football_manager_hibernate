package com.mehmett.service;

import com.mehmett.entity.Manager;
import com.mehmett.entity.TransferOffer;
import com.mehmett.repository.TransferOfferRepository;
import com.mehmett.utility.ConsoleTextUtils;
import com.mehmett.utility.enums.EOfferResponse;

import java.util.ArrayList;
import java.util.List;

public class TransferOfferService extends ServiceManager<TransferOffer, Long> {

    private static TransferOfferService instance;
    private static TransferOfferRepository transferOfferRepository = TransferOfferRepository.getInstance();
    private static PlayerService playerService = PlayerService.getInstance();
    private static TeamService teamService = TeamService.getInstance();

    private TransferOfferService() {
        super(TransferOfferRepository.getInstance());

    }

    public static TransferOfferService getInstance() {
        if (instance == null) {
            instance = new TransferOfferService();

        }
        return instance;
    }

    public List<TransferOffer> getOffersForReceiver(Manager manager) {
        try {
            return transferOfferRepository.getOffersForReceiver(manager);
        } catch (Exception e) {
            ConsoleTextUtils.printErrorMessage("Service Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public TransferOffer replyToOffer(TransferOffer transferOffer, int userReplySelection) {


        try {
            if (userReplySelection == 1) {

                transferOffer.setResponse(EOfferResponse.ACCEPTED);

                transferOfferRepository.update(transferOffer);
            } else if (userReplySelection == 2) {
                transferOffer.setResponse(EOfferResponse.REJECTED);
                transferOfferRepository.update(transferOffer);
                System.out.println("You have rejected this offer!");
            } else {
                ConsoleTextUtils.printErrorMessage("Wrong selection!");
            }
        } catch (Exception e) {
            ConsoleTextUtils.printErrorMessage("Service Error: " + e.getMessage());
        }
        return transferOffer;
    }


}