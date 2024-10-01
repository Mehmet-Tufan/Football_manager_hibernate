package com.mehmett.repository;

import com.mehmett.entity.Gambler;
import com.mehmett.utility.ConsoleTextUtils;
import com.mehmett.utility.HibernateConnection;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Optional;

public class GamblerRepository extends RepositoryManager<Gambler,Long> {
    private static GamblerRepository instance;

    private GamblerRepository() {
        super(Gambler.class);
    }
    public static GamblerRepository getInstance() {
        if (instance == null) {
            instance = new GamblerRepository();
        }
        return instance;
    }

    public Optional<Gambler> findByUsernameAndPassword(String username, String password) {
            Gambler gambler = null;
        try {
            CriteriaBuilder cb = HibernateConnection.em.getCriteriaBuilder();
            CriteriaQuery<Gambler> cq = cb.createQuery(Gambler.class);
            Root<Gambler> root = cq.from(Gambler.class);

            cq.select(root).where(cb.and(cb.equal(root.get("username"), username),cb.equal(root.get("password"), password)));
            gambler = HibernateConnection.em.createQuery(cq).getSingleResult();
        }catch(NoResultException e) {
            System.out.println("Username or password is wrong.");
            return Optional.empty();
        }
        catch (Exception e) {
            ConsoleTextUtils.printErrorMessage("GamblerRepository Hata: "+e.getMessage());
        }
        return Optional.ofNullable(gambler);
    }


}