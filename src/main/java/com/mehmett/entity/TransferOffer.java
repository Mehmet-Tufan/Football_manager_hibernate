package com.mehmett.entity;

import com.mehmett.utility.enums.EOfferResponse;
import com.mehmett.utility.enums.EOfferResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbloffer")
public class TransferOffer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Long biddingMoney;

    @Enumerated(EnumType.STRING)
    private EOfferResponse response;

    @ManyToOne
    private Team proposer;
    @ManyToOne
    private Team receiver;

    @ManyToOne
    private Player player;

    @Override
    public String toString() {
        return "TransferOffer{" +
                "response=" + response +
                ", biddingMoney=" + biddingMoney +
                ", message='" + message + '\'' +
                '}';
    }
}