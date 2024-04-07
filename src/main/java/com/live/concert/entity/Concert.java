package com.live.concert.entity;

import com.live.concert.contract.GetConcertResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @Version
    private int version;

    public GetConcertResponse toConcertResponse() {
        GetConcertResponse response = new GetConcertResponse();
        response.setId(id);
        response.setName(name);
        response.setDescription(description);
        response.setTotalTickets(totalTickets);
        response.setTotalTicketsSold(totalTicketsSold);
        response.setDateTime(dateTime.getTime());
        response.setStartSellingOn(startSellingOn.getTime());
        response.setFinishSellingOn(finishSellingOn.getTime());
        return response;
    }

    public boolean isWithinSellingPeriod() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startSellingDateTime = startSellingOn.toLocalDateTime();
        LocalDateTime finishSellingDateTime = finishSellingOn.toLocalDateTime();

        return !now.isBefore(startSellingDateTime) && !now.isAfter(finishSellingDateTime);
    }
}