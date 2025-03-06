package com.mycompany.teamproject9.repository;

import com.mycompany.teamproject9.dto.SignupRequest;
import org.apache.ibatis.annotations.Insert;

public interface CustomerMapper {
    @Insert("insert into customer(customer_name, customer_pwd,customer_email,customer_phone,customer_addr) "+
    "values(#{name},#{pwd},#{email},#{phone},#{addr}) ")
    void insertCustomer(SignupRequest request);
}
