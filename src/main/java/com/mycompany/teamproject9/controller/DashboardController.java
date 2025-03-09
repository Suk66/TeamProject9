package com.mycompany.teamproject9.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/customer")
    public String customerDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("username", "Guest");
        } else {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());
        }

        return "customer-dashboard"; // customer-dashboard.html로 이동
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("username", "Guest");
        } else {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());
        }

        return "admin-dashboard"; // admin-dashboard.html로 이동
    }
}
