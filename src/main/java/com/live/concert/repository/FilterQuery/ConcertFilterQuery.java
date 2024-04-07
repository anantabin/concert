package com.live.concert.repository.FilterQuery;

import com.live.concert.entity.Concert;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ConcertFilterQuery {

    public static Specification<Concert> filterConcerts(String name, LocalDate startDate, LocalDate endDate) {
        return (Root<Concert> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (name != null && !name.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (startDate != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateTime"), startDate.atStartOfDay()));
            }
            if (endDate != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateTime"), endDate.atTime(23, 59, 59)));
            }

            return predicate;
        };
    }
}
