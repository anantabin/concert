package com.live.concert.contract;

import lombok.Data;

@Data
public class GetConcertResponse {
    private Long id;
    private String name;
    private String description;
    private int totalTickets;
    private int totalTicketsSold;
    private Long dateTime;
    private Long startSellingOn;
    private Long finishSellingOn;
}

