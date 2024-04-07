package com.live.concert.contract;

import lombok.Data;

@Data
public class CreateTicketOrderResponse {
    private Long id;
    private GetConcertResponse concertDetail;
    private int quantity;

    public CreateTicketOrderResponse() {
    }

    public CreateTicketOrderResponse(Long id, GetConcertResponse concert, int quantity) {
        this.id = id;
        this.concertDetail = concert;
        this.quantity = quantity;
    }
}
