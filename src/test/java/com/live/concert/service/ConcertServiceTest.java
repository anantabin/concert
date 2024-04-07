package com.live.concert.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.live.concert.entity.Concert;
import com.live.concert.exception.TicketNotAvailableException;
import com.live.concert.repository.ConcertRepository;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    ObjectMapper objectMapper = new ObjectMapper();

    EasyRandomParameters parameters = new EasyRandomParameters();

    EasyRandom easyRandom = new EasyRandom(parameters);

    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private ConcertService concertService;

    @Test
    public void testUpdateConcertTicketAvailability_EnoughTicketsAvailable_ShouldReturnSuccess() {
        Concert concert = new Concert();
        concert.setId(1L);
        concert.setTotalTickets(100);
        concert.setTotalTicketsSold(50);
        concert.setStartSellingOn(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
        concert.setFinishSellingOn(Timestamp.valueOf(LocalDateTime.now().plusDays(3)));

        when(concertRepository.findById(anyLong())).thenReturn(Optional.of(concert));
        when(concertRepository.save(any())).thenReturn(concert);

        Concert updatedConcert = concertService.updateConcertTicketAvailability(1L, 20);

        assertEquals(70, updatedConcert.getTotalTicketsSold());
    }

    @Test
    public void testUpdateConcertTicketAvailability_NotInSellingPeriod_ShouldThrowException() {
        Concert concert = new Concert();
        concert.setId(1L);
        concert.setTotalTickets(100);
        concert.setTotalTicketsSold(50);
        concert.setStartSellingOn(Timestamp.valueOf(LocalDateTime.now().plusDays(1)));
        concert.setFinishSellingOn(Timestamp.valueOf(LocalDateTime.now().plusDays(2)));


        when(concertRepository.findById(anyLong())).thenReturn(Optional.of(concert));

        assertThrows(TicketNotAvailableException.class, () -> {
            concertService.updateConcertTicketAvailability(1L, 20);
        });
    }

    @Test
    public void testUpdateConcertTicketAvailability_NotEnoughTicketsAvailable_ShouldThrowException() {
        Concert concert = new Concert();
        concert.setId(1L);
        concert.setTotalTickets(100);
        concert.setTotalTicketsSold(90);
        concert.setStartSellingOn(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
        concert.setFinishSellingOn(Timestamp.valueOf(LocalDateTime.now().plusDays(3)));

        when(concertRepository.findById(anyLong())).thenReturn(Optional.of(concert));

        assertThrows(TicketNotAvailableException.class, () -> {
            concertService.updateConcertTicketAvailability(1L, 20);
        });
    }
}