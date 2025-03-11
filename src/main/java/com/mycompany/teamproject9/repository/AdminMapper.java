package com.mycompany.teamproject9.repository;

import com.mycompany.teamproject9.dto.CreateRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    // ✅ 이메일 & 전화번호 확인
    @Select("SELECT COUNT(*) > 0 FROM admin WHERE admin_email = #{email} AND admin_phone = #{phone}")
    boolean checkUserByEmailAndPhone(@Param("email") String email, @Param("phone") String phone);

    // ✅ 이메일 존재 여부 확인
    @Select("SELECT COUNT(*) > 0 FROM admin WHERE admin_email = #{email}")
    boolean checkEmailExists(@Param("email") String email);

    // ✅ 비밀번호 변경
    @Update("UPDATE admin SET admin_pwd = #{password} WHERE admin_email = #{email}")
    void updatePassword(@Param("email") String email, @Param("password") String hashedPassword);


}
