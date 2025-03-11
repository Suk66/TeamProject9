package com.mycompany.teamproject9.repository;

import com.mycompany.teamproject9.dto.CreateRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CustomerMapper {
    @Insert("insert into customer(customer_name, customer_pwd, customer_email,customer_phone,customer_addr, role) "+
            "values(#{name},#{pwd},#{email},#{phone},#{addr}, 'CUSTOMER') ")
    void insertCustomer(CreateRequest request);

    @Select("select count(*) from customer where customer_email = #{email}")
    int countByEmail(String email);


    @Select("select customer_pwd from customer where customer_email = #{email}")
    String findPasswordByEmail(String email);

    @Select("SELECT COUNT(*) FROM customer WHERE customer_phone = #{phone}")
    int countByPhone(String phone);

//    <!-- ✅ 일반회원(customer) 테이블에서 이메일 & 전화번호 확인 -->
   // ✅ 이메일 & 전화번호 확인
    @Select("SELECT COUNT(*) > 0 FROM customer WHERE customer_email = #{email} AND customer_phone = #{phone}")
    boolean checkUserByEmailAndPhone(@Param("email") String email, @Param("phone") String phone);

    // ✅ 이메일 존재 여부 확인
    @Select("SELECT COUNT(*) > 0 FROM customer WHERE customer_email = #{email}")
    boolean checkEmailExists(@Param("email") String email);

    // ✅ 비밀번호 변경
    @Update("UPDATE customer SET customer_pwd = #{password} WHERE customer_email = #{email}")
    void updatePassword(@Param("email") String email, @Param("password") String hashedPassword);

}
