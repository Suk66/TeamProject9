package com.mycompany.teamproject9.controller;

import com.mycompany.teamproject9.dto.CreateRequest;
import com.mycompany.teamproject9.repository.AdminMapper;
import com.mycompany.teamproject9.repository.CustomerMapper;
import com.mycompany.teamproject9.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/create")
public class CreateController {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private AdminMapper adminMapper;

    @PostMapping
    public Map<String, String> registerUser(@RequestBody CreateRequest request) {
        Map<String, String> response = new HashMap<>();

        System.out.println("디버깅 회원가입 요청 받음: " + request);

        // 이메일 필수 입력 검증
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            response.put("message", "이메일을 입력하세요.");
            return response;
        }
        if (request.getPwd() == null || request.getPwd().trim().isEmpty()) {
            response.put("message", "비밀번호를 입력하세요.");
            return response;
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            response.put("message", "이름을 입력하세요.");
            return response;
        }
        if (request.getPhone() == null || request.getPhone().trim().isEmpty()) {
            response.put("message", "휴대폰 번호를 입력하세요.");
            return response;
        }
        if ("customer".equals(request.getUserType()) && (request.getAddr() == null || request.getAddr().trim().isEmpty())) {
            response.put("message", "주소를 입력하세요.");
            return response;
        }

        // ✅ 이메일 중복 확인 (두 테이블에서 동시에 확인)
        int adminEmailCount = adminMapper.countByEmail(request.getEmail());
        int customerEmailCount = customerMapper.countByEmail(request.getEmail());

        if (adminEmailCount > 0 || customerEmailCount > 0) {
            response.put("message", "이메일이 이미 존재합니다.");
            return response;
        }

        // ✅ 비밀번호 해싱 적용
        String hashedPassword = PasswordUtil.hashPassword(request.getPwd()); // 🔹 비밀번호 해싱
        request.setPwd(hashedPassword); // 🔹 해싱된 비밀번호를 저장

        // ✅ 회원가입 진행
        if ("admin".equals(request.getUserType())) {
            adminMapper.insertAdmin(request);
            response.put("message", "관리자 회원가입 성공!");
        } else if ("customer".equals(request.getUserType())) {
            customerMapper.insertCustomer(request);
            response.put("message", "일반회원 회원가입 성공!");
        } else {
            response.put("message", "잘못된 회원 유형입니다.");
            return response;
        }

        return response;
    }
}
