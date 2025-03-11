package com.mycompany.teamproject9.controller;

import com.mycompany.teamproject9.dto.LoginRequest;
import com.mycompany.teamproject9.service.RecaptchaService;
import com.mycompany.teamproject9.repository.AdminMapper;
import com.mycompany.teamproject9.repository.CustomerMapper;
import com.mycompany.teamproject9.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private RecaptchaService recaptchaService;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping
    public String showLoginPage() {
        return "login"; // login.html (로그인 페이지의 이름)
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Map<String, Object> model) {
        String user = (String) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (user == null || role == null) {
            return "redirect:/login"; // 세션이 없다면 로그인 페이지로 리다이렉트
        }

        // 세션에서 username을 모델로 전달하여 Thymeleaf에서 사용할 수 있도록 합니다.
        model.put("username", user);

        if ("ROLE_ADMIN".equals(role)) {
            return "admin_dashboard"; // 관리자 대시보드
        } else if ("ROLE_CUSTOMER".equals(role)) {
            return "customer_dashboard"; // 일반회원 대시보드
        }

        return "redirect:/login"; // 유효하지 않은 role이 있다면 로그인 페이지로 리다이렉트
    }

   @PostMapping
@ResponseBody
public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
    Map<String, String> response = new HashMap<>();
    HttpSession session = httpRequest.getSession();

    String email = request.getEmail();
    String password = request.getPwd();
    String storedPassword = null;

    // 관리자 로그인 검증
    storedPassword = adminMapper.findPasswordByEmail(email);
    if (storedPassword != null && PasswordUtil.checkPassword(password, storedPassword)) {
        session.setAttribute("user", email);  // ✅ 세션에 이메일 저장
        session.setAttribute("role", "ROLE_ADMIN");
        response.put("message", "로그인 성공 (관리자)");
        response.put("role", "ROLE_ADMIN");
        return ResponseEntity.ok(response);
    }

    // 일반회원 로그인 검증
    storedPassword = customerMapper.findPasswordByEmail(email);
    if (storedPassword != null && PasswordUtil.checkPassword(password, storedPassword)) {
        session.setAttribute("user", email);  // ✅ 세션에 이메일 저장
        session.setAttribute("role", "ROLE_CUSTOMER");
        response.put("message", "로그인 성공 (일반회원)");
        response.put("role", "ROLE_CUSTOMER");
        return ResponseEntity.ok(response);
    }

    // 로그인 실패
    response.put("message", "이메일 또는 비밀번호가 일치하지 않습니다.");
    return ResponseEntity.status(400).body(response);
}


}
