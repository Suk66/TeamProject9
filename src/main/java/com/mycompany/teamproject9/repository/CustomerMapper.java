package com.mycompany.teamproject9.repository;

import com.mycompany.teamproject9.dto.SignupRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface CustomerMapper {
    @Insert("insert into customer(customer_name, customer_pwd, customer_email,customer_phone,customer_addr, role) "+
    "values(#{name},#{pwd},#{email},#{phone},#{addr}, 'CUSTOMER') ")
    void insertCustomer(SignupRequest request);

    @Select("select count(*) from customer where customer_email = #{email}")
    int countByEmail(String email);


    @Select("select customer_pwd from customer where customer_email = #{email}")
    String findPasswordByEmail(String email);
}
