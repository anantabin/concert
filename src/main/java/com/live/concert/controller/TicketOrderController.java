package com.live.concert.controller;

import com.live.concert.contract.CreateTicketOrderRequest;
import com.live.concert.contract.CreateTicketOrderResponse;
import com.live.concert.entity.TicketOrder;
import com.live.concert.exception.TicketNotAvailableException;
import com.live.concert.service.TicketOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketOrderController {
    @Autowired
    private TicketOrderService ticketOrderService;

    @PostMapping
    public ResponseEntity<?> createTicketOrder(@RequestBody CreateTicketOrderRequest request) {
        if (request == null || request.getConcertId() == null || request.getAccountId() == null || request.getQuantity() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        try {
            TicketOrder ticketOrder = ticketOrderService.createTicketOrder(request.getQuantity(), request.getConcertId(), request.getAccountId());
            return ResponseEntity.ok(new CreateTicketOrderResponse(ticketOrder.getId(), ticketOrder.getConcert().toConcertResponse(), ticketOrder.getQuantity()));
        } catch (TicketNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred");
        }
    }
}
