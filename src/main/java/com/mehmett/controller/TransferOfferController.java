package com.mehmett.controller;

import com.mehmett.entity.Manager;
import com.mehmett.entity.TransferOffer;
import com.mehmett.service.TransferOfferService;
import com.mehmett.utility.ConsoleTextUtils;

import java.util.ArrayList;
import java.util.List;

public class TransferOfferController {
    private static TransferOfferController instance;
    private static TransferOfferService transferOfferService = TransferOfferService.getInstance();

    private TransferOfferController() {

    }
    public static TransferOfferController getInstance() {
        if (instance == null) {
            instance =new TransferOfferController();
        }
        return instance;
    }

    public TransferOffer save (TransferOffer transferOffer) {
        return transferOfferService.save(transferOffer);
    }


    public List<TransferOffer> getOffersForReceiver(Manager manager) {
        try {
            return transferOfferService.getOffersForReceiver(manager);
        } catch (Exception e) {
            ConsoleTextUtils.printErrorMessage("Controller Error: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public TransferOffer replyToOffer(TransferOffer transferOffer, int userReplySelection) {
        try {
            transferOfferService.replyToOffer(transferOffer,userReplySelection);
        } catch (Exception e) {
            ConsoleTextUtils.printErrorMessage("Controller Error: " + e.getMessage());
        }
        return transferOffer;
    }
}