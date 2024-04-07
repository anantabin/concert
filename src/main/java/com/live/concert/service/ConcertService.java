package com.live.concert.service;

import com.live.concert.entity.Concert;
import com.live.concert.exception.ConcertNotFoundException;
import com.live.concert.repository.ConcertRepository;
import com.live.concert.repository.FilterQuery.ConcertFilterQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConcertService {
    @Autowired
    private ConcertRepository concertRepository;

    public Concert createConcert(Concert concert) {
        return concertRepository.save(concert);
    }

    public List<Concert> getAllConcerts() {
        return concertRepository.findAll();
    }

    public Concert getConcertById(Long concertId) {
        return concertRepository.findById(concertId)
                .orElseThrow(() -> new ConcertNotFoundException("Concert not found"));
    }

    public Page<Concert> filterConcerts(String name, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return concertRepository.findAll(ConcertFilterQuery.filterConcerts(name, startDate, endDate), pageable);
    }
}

