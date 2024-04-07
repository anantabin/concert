package com.live.concert.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "date_time")
    private Timestamp dateTime;

    private String description;

    @Column(name = "total_tickets")
    private int totalTickets;

    @Column(name = "total_tickets_sold")
    private int totalTicketsSold;

    @Column(name = "start_selling_on")
    private Timestamp startSellingOn;

    @Column(name = "finish_selling_on")
    private Timestamp finishSellingOn;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;
}