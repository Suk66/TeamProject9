package com.mycompany.teamproject9.repository;

import com.mycompany.teamproject9.dto.CreateRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface AdminMapper {
    @Insert("insert into admin (admin_name,admin_pwd,admin_email,admin_phone,role)"+
            "values (#{name},#{pwd},#{email},#{phone}, 'ADMIN')")
    void insertAdmin(CreateRequest request);

    @Select("select count(*) from admin where admin_email = #{email}")
    int countByEmail(String email);


    @Select("select admin_pwd from admin where admin_email = #{email}")
    String findPasswordByEmail(String email);

    @Select("SELECT COUNT(*) FROM admin WHERE admin_phone = #{phone}")
    int countByPhone(String phone);

}
