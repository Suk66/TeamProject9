package com.mycompany.teamproject9.repository;

import com.mycompany.teamproject9.dto.SignupRequest;
import org.apache.ibatis.annotations.Insert;

public interface AdminMapper {
    @Insert("insert into admin (admin_name,admin_pwd,admin_email,admin_phone)"+
            "values (#{name},#{pwd},#{email},#{phone})")
    void insertAdmin(SignupRequest request);
}
