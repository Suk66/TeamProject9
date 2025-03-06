package com.mycompany.teamproject9.controller;

import com.mycompany.teamproject9.dto.SignupRequest;
import com.mycompany.teamproject9.repository.AdminMapper;
import com.mycompany.teamproject9.repository.CustomerMapper;
import com.mycompany.teamproject9.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private AdminMapper adminMapper;

    @PostMapping
    public Map<String,String>registerUser(@RequestBody SignupRequest request){
        Map<String,String> response=new HashMap<>();

        try{
            System.out.println("디버깅 회원가입 요청 받음: " + request);

            boolean isEmailExists = adminMapper.countByEmail(request.getEmail()) > 0
                    || customerMapper.countByEmail(request.getEmail()) > 0;

            if(isEmailExists){
                response.put("message","이미 가입된 이메일입니다.");
                return response;
            }

            if ("customer".equals(request.getUserType())){
                customerMapper.insertCustomer(request);
            } else if ("admin".equals(request.getUserType())) {
                adminMapper.insertAdmin(request);
            }else {
                response.put("message","잘못된 회원 유형입니다.");
                return response;
            }
            response.put("message","회원가입 성공");
        }catch (Exception e){
            response.put("message","회원가입 실패" + e.getMessage());
        }
        return response;
    }
}
