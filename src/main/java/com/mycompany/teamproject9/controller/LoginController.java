package com.mycompany.teamproject9.controller;

import com.mycompany.teamproject9.dto.LoginRequest;
import com.mycompany.teamproject9.repository.AdminMapper;
import com.mycompany.teamproject9.repository.CustomerMapper;
import com.mycompany.teamproject9.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping
    public Map<String, String> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        Map<String, String> response = new HashMap<>();

        String adminPwd = adminMapper.findPasswordByEmail(request.getEmail());
        if (adminPwd != null) {
            boolean isMatch = passwordEncoderUtil.matches(request.getPwd(), adminPwd);
            System.out.println("디버깅 관리자 비밀번호 일치 여부 : " + isMatch);

            if (isMatch) {
                response.put("message", "로그인 성공 (관리자)");
                response.put("role", "ROLE_ADMIN");

                setAuthentication(request.getEmail(), "ROLE_ADMIN", httpRequest);

                return response;
            }
        }
        
        String customerPwd = customerMapper.findPasswordByEmail(request.getEmail());
        if (customerPwd != null) {
            boolean isMatch = passwordEncoderUtil.matches(request.getPwd(), customerPwd);
            System.out.println("디버깅 일반회원 비밀번호 일치 여부 : " + isMatch);

            if (isMatch) {
                response.put("message","로그인 성공 (일반회원)");
                response.put("role", "ROLE_CUSTOMER");

                setAuthentication(request.getEmail(), "ROLE_CUSTOMER", httpRequest);

                return response;
            }
        }
        
        response.put("message","이메일 또는 비밀번호가 일치하지 않습니다.");
        return response;
    }

   private void setAuthentication(String email, String role, HttpServletRequest request) {
    UserDetails userDetails = new org.springframework.security.core.userdetails.User(
            email, "", Collections.singletonList(new SimpleGrantedAuthority(role)));

    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    HttpSession session = request.getSession(true);
    session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
}


}
