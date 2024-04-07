package com.live.concert.repository;

import com.live.concert.entity.TicketOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketOrderRepository extends JpaRepository<TicketOrder, Long> {
}