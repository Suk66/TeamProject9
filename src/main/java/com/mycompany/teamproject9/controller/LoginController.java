package com.mycompany.teamproject9.controller;

import com.mycompany.teamproject9.dto.LoginRequest;
import com.mycompany.teamproject9.repository.AdminMapper;
import com.mycompany.teamproject9.repository.CustomerMapper;
import com.mycompany.teamproject9.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private CustomerMapper customerMapper;

    // 로그인 페이지를 처리하는 GET 요청
    @GetMapping
    public String showLoginPage() {
        // 로그인 페이지를 반환 (HTML을 반환하는 방법)
        return "login";  // 로그인 페이지의 뷰 이름을 반환
    }

    // 로그인 처리를 하는 POST 요청
    @PostMapping
    @ResponseBody
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        System.out.println("받은 로그인 데이터: 이메일=" + request.getEmail() + ", 비밀번호=" + request.getPwd());

        Map<String, String> response = new HashMap<>();
        HttpSession session = httpRequest.getSession();

        // 이메일 확인
        System.out.println("🔍 [2] 이메일 입력 확인: " + request.getEmail());

        // 관리자 로그인 검증
        String adminPwd = adminMapper.findPasswordByEmail(request.getEmail());
        System.out.println("🔍 [3] 관리자 비밀번호 조회 결과: " + (adminPwd != null ? "O" : "X"));

        if (adminPwd != null && PasswordUtil.checkPassword(request.getPwd(), adminPwd)) {
            System.out.println("✅ 관리자 로그인 성공");
            session.setAttribute("user", request.getEmail());
            session.setAttribute("role", "ROLE_ADMIN");

            response.put("message", "로그인 성공 (관리자)");
            response.put("role", "ROLE_ADMIN");
            return ResponseEntity.ok(response);
        }

        // 일반 회원 로그인 검증
        String customerPwd = customerMapper.findPasswordByEmail(request.getEmail());
        System.out.println("🔍 [4] 일반회원 비밀번호 조회 결과: " + (customerPwd != null ? "O" : "X"));

        if (customerPwd != null && PasswordUtil.checkPassword(request.getPwd(), customerPwd)) {
            System.out.println("✅ 일반회원 로그인 성공");
            session.setAttribute("user", request.getEmail());
            session.setAttribute("role", "ROLE_CUSTOMER");

            response.put("message", "로그인 성공 (일반회원)");
            response.put("role", "ROLE_CUSTOMER");
            return ResponseEntity.ok(response);
        }

        // 실패 시
        System.out.println("❌ 로그인 실패: 이메일 또는 비밀번호가 일치하지 않음");
        response.put("message", "이메일 또는 비밀번호가 일치하지 않습니다.");
        return ResponseEntity.badRequest().body(response); // 실패 시 400 응답
    }
}
