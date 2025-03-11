package com.mycompany.teamproject9.controller;

import com.mycompany.teamproject9.repository.CustomerMapper;
import com.mycompany.teamproject9.repository.AdminMapper;
import com.mycompany.teamproject9.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller  // ✅ 변경: RestController → Controller (HTML 반환 가능)
public class ResetPasswordController {

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AdminMapper adminMapper;

    // ✅ 비밀번호 재설정 페이지(GET 요청 허용)
    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam("email") String email, Model model) {
        model.addAttribute("email", email);
        return "reset-password"; // ✅ HTML 반환 가능
    }

    @PostMapping("/reset-password")
    @ResponseBody  // ✅ 추가: JSON 응답을 위해 @ResponseBody 사용
    public Map<String, Object> resetPassword(@RequestBody Map<String, String> requestData) {
        Map<String, Object> response = new HashMap<>();
        String email = requestData.get("email");
        String newPassword = requestData.get("newPassword");

        // ✅ 이메일이 customer 또는 admin 테이블에 존재하는지 확인
        boolean isCustomer = customerMapper.checkEmailExists(email);
        boolean isAdmin = adminMapper.checkEmailExists(email);

        if (!isCustomer && !isAdmin) {
            response.put("success", false);
            response.put("message", "잘못된 요청입니다.");
            return response;
        }

        // ✅ 새 비밀번호 해싱 후 저장
        String hashedPassword = PasswordUtil.hashPassword(newPassword);

        if (isCustomer) {
            customerMapper.updatePassword(email, hashedPassword);
        } else if (isAdmin) {
            adminMapper.updatePassword(email, hashedPassword);
        }

        response.put("success", true);
        response.put("message", "비밀번호가 성공적으로 변경되었습니다.");
        return response;
    }
}
