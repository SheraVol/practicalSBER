package com.alyona.Sber2.services;

import com.alyona.Sber2.exceptions.ResourceNotAvailableException;
import com.alyona.Sber2.models.Reservation;
import com.alyona.Sber2.models.Resource;
import com.alyona.Sber2.models.User;
import com.alyona.Sber2.repositories.ReservationRepository;
import com.alyona.Sber2.repositories.ResourceRepository;
import com.alyona.Sber2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Long reserveResource(Long userId, Long resourceId, Date periodStart, Date periodEnd) {
        Optional<Resource> resourceOpt = resourceRepository.findById(resourceId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (resourceOpt.isPresent() && userOpt.isPresent()) {
            Resource resource = resourceOpt.get();
            User user = userOpt.get();

            List<Reservation> conflictingReservations = reservationRepository.findByResourceAndPeriodEndAfterAndPeriodStartBefore(resource, periodStart, periodEnd);

            if (conflictingReservations.isEmpty()) {
                Reservation reservation = new Reservation();
                reservation.setUser(user);
                reservation.setResource(resource);
                reservation.setPeriodStart(periodStart);
                reservation.setPeriodEnd(periodEnd);
                return reservationRepository.save(reservation).getId();
            } else {
                throw new RuntimeException("Resource is already reserved for the given period.");
            }
        } else {
            throw new ResourceNotAvailableException();
        }
    }

        @Transactional
        public void releaseResource(Long reservationId) {
            Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
            if (reservationOpt.isPresent()) {
                reservationRepository.delete(reservationOpt.get());
            } else {
                throw new RuntimeException("Reservation not found.");
            }
        }

//    private boolean isResourceAvailable(Resource resource, Date periodStart, Date periodEnd) {
//        List<Reservation> reservations = reservationRepository.findByResourceIdAndPeriodStartBeforeAndPeriodEndAfter(resource.getId(), periodStart, periodEnd);
//        return reservations.isEmpty();
//    }
}