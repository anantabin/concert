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
        response.setId(this.id);
        response.setName(this.name);
        response.setDescription(this.description);
        response.setTotalTickets(this.totalTickets);
        response.setTotalTicketsSold(this.totalTicketsSold);
        response.setDateTime(this.dateTime.getTime());
        response.setStartSellingOn(this.startSellingOn.getTime());
        response.setFinishSellingOn(this.finishSellingOn.getTime());
        return response;
    }
}