package com.mycompany.teamproject9.service;

import com.mycompany.teamproject9.dto.User;
import com.mycompany.teamproject9.repository.AdminMapper;
import com.mycompany.teamproject9.repository.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private AdminMapper adminMapper;

    public User findByEmail(String email) {
        // ✅ 이메일을 기준으로 일반회원과 관리자 테이블에서 검색
        User user = customerMapper.findByEmail(email);
        if (user == null) {
            user = adminMapper.findByEmail(email);
        }
        return user;
    }
    public boolean updateUserInfo(User user) {
        int rowsAffected = 0;

        if ("customer".equals(user.getUserType())) {
            rowsAffected = customerMapper.updateUser(user);
        } else if ("admin".equals(user.getUserType())) {
            rowsAffected = adminMapper.updateUser(user);
        }

         System.out.println("📌 [디버깅] rowsAffected: " + rowsAffected);
        return rowsAffected > 0; // 업데이트 성공 여부 반환
    }
}

