package com.live.concert.contract;

import lombok.Data;

@Data
public class CreateTicketOrderRequest {
    private Long concertId;
    private Long accountId; // TODO need to get it from jwt token
    private int quantity;
}
