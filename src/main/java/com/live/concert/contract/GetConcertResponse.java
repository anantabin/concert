package com.live.concert.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

import static com.live.concert.common.Constants.DATE_TIME_FORMAT;

@Data
public class GetConcertResponse {
    private Long id;
    private String name;
    private String description;
    private int totalTickets;
    private int totalTicketsSold;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime dateTime;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime startSellingOn;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime finishSellingOn;
}

