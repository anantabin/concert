package com.live.concert.service;

import com.live.concert.entity.Account;
import com.live.concert.entity.Concert;
import com.live.concert.entity.TicketOrder;
import com.live.concert.exception.TicketNotAvailableException;
import com.live.concert.repository.TicketOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketOrderServiceTest {
    @Mock
    private TicketOrderRepository ticketOrderRepository;

    @Mock
    private ConcertService concertService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TicketOrderService ticketOrderService;

    @Test
    public void testCreateTicketOrder_EnoughTicketsAvailable_ShouldReturnTicketOrder() {
        Concert concert = new Concert();
        concert.setId(1L);
        concert.setTotalTickets(100);
        concert.setTotalTicketsSold(50);

        Account account = new Account();
        account.setId(1L);

        when(concertService.updateConcertTicketAvailability(anyLong(), anyInt())).thenReturn(concert);
        when(accountService.getAccountById(anyLong())).thenReturn(account);
        when(ticketOrderRepository.save(any())).thenReturn(new TicketOrder());

        TicketOrder ticketOrder = ticketOrderService.createTicketOrder(20, 1L, 1L);

        assertNotNull(ticketOrder);
    }

    @Test
    public void testCreateTicketOrder_NotEnoughTicketsAvailable_ShouldThrowException() {
        when(concertService.updateConcertTicketAvailability(anyLong(), anyInt())).thenThrow(new TicketNotAvailableException("Not enough tickets available"));

        assertThrows(TicketNotAvailableException.class, () -> {
            ticketOrderService.createTicketOrder(20, 1L, 1L);
        });
    }
}