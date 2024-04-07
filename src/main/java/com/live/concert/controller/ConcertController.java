package com.live.concert.controller;

import com.live.concert.contract.GetConcertResponse;
import com.live.concert.entity.Concert;
import com.live.concert.exception.ConcertNotFoundException;
import com.live.concert.service.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/concert")
public class ConcertController {
    @Autowired
    private ConcertService concertService;

    @GetMapping("")
    public ResponseEntity<Page<GetConcertResponse>> getConcerts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDate concertStartDate,
            @RequestParam(required = false) LocalDate concertEndDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<GetConcertResponse> concerts = concertService.filterConcerts(name, concertStartDate, concertEndDate, pageable).map(this::mapToGetConcertResponse);
        return ResponseEntity.ok(concerts);
    }

    @GetMapping("/{concertId}")
    public ResponseEntity<GetConcertResponse> getConcerts(
            @PathVariable long concertId
    ) {
        try {
            return ResponseEntity.ok(mapToGetConcertResponse(concertService.getConcertById(concertId)));
        } catch (ConcertNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    private GetConcertResponse mapToGetConcertResponse(Concert concert) {
        GetConcertResponse response = new GetConcertResponse();
        response.setId(concert.getId());
        response.setName(concert.getName());
        response.setDescription(concert.getDescription());
        response.setTotalTickets(concert.getTotalTickets());
        response.setTotalTicketsSold(concert.getTotalTicketsSold());
        response.setDateTime(concert.getDateTime().getTime());
        response.setStartSellingOn(concert.getStartSellingOn().getTime());
        response.setFinishSellingOn(concert.getFinishSellingOn().getTime());
        return response;
    }
}
