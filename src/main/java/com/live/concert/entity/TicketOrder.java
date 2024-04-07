package com.live.concert.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@IdClass(TicketOrderId.class)
public class TicketOrder {
    @Id
    @ManyToOne
    @JoinColumn(name = "concert_id")
    private Concert concert;

    @Id
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private int quantity;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

}

class TicketOrderId implements Serializable {
    private Long concert;
    private Long account;

}