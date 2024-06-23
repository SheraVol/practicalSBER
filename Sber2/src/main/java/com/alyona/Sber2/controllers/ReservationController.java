package com.alyona.Sber2.controllers;

import com.alyona.Sber2.models.Reservation;
import com.alyona.Sber2.models.Resource;
import com.alyona.Sber2.models.User;
import com.alyona.Sber2.services.ReservationService;
import com.alyona.Sber2.services.ResourceService;
import com.alyona.Sber2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/reserve")
    public String showReserveForm(Model model) {
        List<User> users = userService.findAll();
        List<Resource> resources = resourceService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("resources", resources);
        return "reserve_resource";
    }

    @PostMapping("/reserve")
    public String reserveResource(@RequestParam Long userId, @RequestParam Long resourceId,
                                  @RequestParam String periodStart, @RequestParam String periodEnd, Model model) {
        try {
            Date startDate = Date.valueOf(periodStart);
            Date endDate = Date.valueOf(periodEnd);
            Long reservationId = reservationService.reserveResource(userId, resourceId, startDate, endDate);
            model.addAttribute("message", "Резервация осуществлена. Присвоенный ID: " + reservationId);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "reservation_result";
    }

    @GetMapping("/release")
    public String showReleaseForm() {
        return "release_resource";
    }

    @GetMapping("/")
    public String listReservations(Model model) {
        List<Reservation> reservations = reservationService.findAll();
        model.addAttribute("reservations", reservations);
        return "list_reservations";
    }

    @PostMapping("/release")
    public String releaseResource(@RequestParam Long reservationId, Model model) {
        try {
            reservationService.releaseResource(reservationId);
            model.addAttribute("message", "Зарезервированный ресурс освобожден");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "reservation_result";
    }
}

