package com.mehmett.service;

import com.mehmett.entity.ContractOffer;
import com.mehmett.entity.Player;
import com.mehmett.entity.TransferOffer;
import com.mehmett.repository.ContractOfferRepository;
import com.mehmett.utility.enums.EOfferResponse;

public class ContractOfferService extends ServiceManager<ContractOffer,Long> {
    private static ContractOfferService instance;
    private ContractOfferRepository contractOfferRepository = ContractOfferRepository.getInstance();
    private ContractOfferService() {
        super(ContractOfferRepository.getInstance());
    }
    public static ContractOfferService getInstance() {
        if (instance == null) {
            instance = new ContractOfferService();
        }
        return instance;
    }
    public ContractOffer makeOfferToPlayer(TransferOffer transferOffer,Long salaryOffer) {
        ContractOffer contractOffer = ContractOffer.builder()
                .offeredSalary(salaryOffer)
                .transferOffer(transferOffer)
                .build();
        Player player = transferOffer.getPlayer();
        if(salaryOffer>=player.getSalary()){
            contractOffer.setResponse(EOfferResponse.ACCEPTED);
        }
        else{
            contractOffer.setResponse(EOfferResponse.REJECTED);
            System.out.println("Player has rejected your offer.");
        }
        contractOfferRepository.save(contractOffer);
        return contractOffer;
    }

}