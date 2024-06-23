package com.alyona.Sber2.repositories;

import com.alyona.Sber2.models.Reservation;
import com.alyona.Sber2.models.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByResourceAndPeriodEndAfterAndPeriodStartBefore(Resource resource, Date periodEnd, Date periodStart);
}