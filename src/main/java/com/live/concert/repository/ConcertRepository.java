package com.live.concert.repository;

import com.live.concert.entity.Concert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long>, JpaSpecificationExecutor<Concert> {
    Page<Concert> findByNameContainingAndDateTime(String name, LocalDateTime dateTime, Pageable pageable);

    Page<Concert> findByNameContaining(String name, Pageable pageable);

    Page<Concert> findByDateTime(LocalDateTime dateTime, Pageable pageable);
}