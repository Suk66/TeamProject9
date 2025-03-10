package com.mycompany.teamproject9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FindPasswordController {

    @GetMapping("/find-password")
    public String showFindPasswordPage() {
        return "find-password"; // ✅ `find-password.html`을 반환
    }
}
