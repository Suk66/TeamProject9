package com.mycompany.teamproject9.controller;

import com.mycompany.teamproject9.dto.User;
import com.mycompany.teamproject9.repository.AdminMapper;
import com.mycompany.teamproject9.repository.CustomerMapper;
import com.mycompany.teamproject9.dto.User;
import com.mycompany.teamproject9.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-user-info")
    @ResponseBody
    public Map<String, Object> getUserInfo(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        // ✅ 세션에서 이메일 가져오기
        String email = (String) session.getAttribute("user");
        if (email == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return response;
        }

        // ✅ 세션 디버깅 로그 추가
        System.out.println("세션에 저장된 이메일: " + email);

        User user = userService.findByEmail(email);
        if (user == null) {
            response.put("success", false);
            response.put("message", "사용자 정보를 찾을 수 없습니다.");
        } else {
            response.put("success", true);
            response.put("user", user);
        }
        return response;
    }

    @GetMapping("/update")  // 📌 회원정보 수정 페이지 매핑
    public String showEditForm() {
        return "update-account";  // ✅ Thymeleaf 템플릿 (update-account.html) 반환
    }

}


