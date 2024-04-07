package com.live.concert.service;

import com.live.concert.entity.TicketOrder;
import com.live.concert.repository.TicketOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TicketOrderService {
    @Autowired
    private TicketOrderRepository ticketOrderRepository;

    public TicketOrder createTicketOrder(TicketOrder ticketOrder) {
        return ticketOrderRepository.save(ticketOrder);
    }

    public List<TicketOrder> getAllTicketOrders() {
        return ticketOrderRepository.findAll();
    }
}