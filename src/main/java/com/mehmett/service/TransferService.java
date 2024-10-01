package com.mehmett.service;

import com.mehmett.entity.ContractOffer;
import com.mehmett.entity.Player;
import com.mehmett.entity.Team;
import com.mehmett.entity.Transfer;
import com.mehmett.repository.TransferRepository;

import java.util.List;

public class TransferService extends ServiceManager<Transfer, Long> {
    private static TransferService instance;
    private static TransferRepository transferRepository = TransferRepository.getInstance();

    private TransferService() {
        super(TransferRepository.getInstance());
    }

    public static TransferService getInstance() {
        if (instance == null) {
            instance = new TransferService();
        }
        return instance;
    }

    public void finalizeTransfer(ContractOffer contractOffer) {
        Transfer transfer = Transfer.builder()
                .contractOffer(contractOffer).build();
        Player player = transfer.getContractOffer().getTransferOffer().getPlayer();
        Team newTeamOfPlayer = transfer.getContractOffer().getTransferOffer().getProposer();
        Team oldTeamOfPlayer = transfer.getContractOffer().getTransferOffer().getReceiver();
        System.out.println(player.getName() + " has joined to " + newTeamOfPlayer.getTeamName() + " from " + oldTeamOfPlayer.getTeamName());


        Long biddingMoney = contractOffer.getTransferOffer().getBiddingMoney();

        player.setTeam(newTeamOfPlayer);
        oldTeamOfPlayer.setBudget(oldTeamOfPlayer.getBudget() + biddingMoney);
        newTeamOfPlayer.setBudget(newTeamOfPlayer.getBudget() - biddingMoney);

        newTeamOfPlayer.getPlayers().add(player);
        oldTeamOfPlayer.getPlayers().remove(player);
        TeamService.getInstance().updateAll(List.of(newTeamOfPlayer, oldTeamOfPlayer));
        PlayerService.getInstance().update(player);


        transferRepository.save(transfer);
    }

}