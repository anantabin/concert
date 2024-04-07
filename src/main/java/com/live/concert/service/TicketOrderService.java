package com.live.concert.service;

import com.live.concert.entity.Account;
import com.live.concert.entity.Concert;
import com.live.concert.entity.TicketOrder;
import com.live.concert.repository.TicketOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TicketOrderService {
    @Autowired
    private TicketOrderRepository ticketOrderRepository;

    @Autowired
    private ConcertService concertService;

    @Autowired
    private AccountService accountService;

    @Transactional
    public TicketOrder createTicketOrder(int quantity, Long accountId, Long concertId) {

        Concert concert = concertService.updateConcertTicketAvailability(concertId, quantity);

        Account account = accountService.getAccountById(accountId);


        TicketOrder ticketOrder = new TicketOrder();
        ticketOrder.setConcert(concert);
        ticketOrder.setAccount(account);
        ticketOrder.setQuantity(quantity);

        return ticketOrderRepository.save(ticketOrder);
    }

    public List<TicketOrder> getAllTicketOrders() {
        return ticketOrderRepository.findAll();
    }
}