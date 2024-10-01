package com.mehmett.repository;

import com.mehmett.entity.Manager;
import com.mehmett.entity.TransferOffer;
import com.mehmett.utility.ConsoleTextUtils;
import com.mehmett.utility.HibernateConnection;

import java.util.ArrayList;
import java.util.List;

public class TransferOfferRepository extends RepositoryManager<TransferOffer,Long> {

    private static TransferOfferRepository instance;
    private TransferOfferRepository() {
        super(TransferOffer.class);
    }


    public static TransferOfferRepository getInstance() {
        if (instance == null) {
            instance = new TransferOfferRepository();
        }
        return instance;
    }

    public List<TransferOffer> getOffersForReceiver(Manager manager) {
        try {
            String hql = "from TransferOffer where receiver = :receiver";
            return HibernateConnection.em.createQuery(hql, TransferOffer.class)
                    .setParameter("receiver", manager.getTeam())
                    .getResultList();
        } catch (Exception e) {
            ConsoleTextUtils.printErrorMessage("Repository Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}