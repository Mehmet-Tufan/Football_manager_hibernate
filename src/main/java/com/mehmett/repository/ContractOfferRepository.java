package com.mehmett.repository;

import com.mehmett.entity.ContractOffer;

public class ContractOfferRepository extends RepositoryManager<ContractOffer,Long> {
    private static ContractOfferRepository instance;
    private ContractOfferRepository() {
        super(ContractOffer.class);
    }
    public static ContractOfferRepository getInstance() {
        if (instance == null) {
            instance = new ContractOfferRepository();
        }
        return instance;
    }


}