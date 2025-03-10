<<<<<<< HEAD
-- 관리자 상세 정보
create table admin(
    admin_id int auto_increment primary key,
    admin_name varchar(50) not null,
    admin_pwd varchar(255) not null,
    admin_email varchar(100) unique not null,
    admin_phone varchar(20) unique not null
=======
create table admin(
                      admin_id int auto_increment primary key,
                      admin_name varchar(50) not null,
                      admin_pwd varchar(255) not null,
                      admin_email varchar(100) unique not null,
                      admin_phone varchar(20) unique not null,
                      role ENUM('ADMIN') NOT NULL DEFAULT 'ADMIN'
>>>>>>> develop
);

-- 회원 상세 정보
create table customer(
<<<<<<< HEAD
    customer_id int auto_increment primary key,
    customer_name varchar(50) not null,
    customer_pwd varchar(255) not null,
    customer_email varchar(100) unique not null,
    customer_phone varchar(20) unique not null,
    customer_addr varchar(255) not null,
    created_at timestamp default current_timestamp
);

-- 제품 정보
create table product(
    product_id int auto_increment primary key,
    product_name varchar(100) not null,
    description text,
    product_price decimal(10,2) not null,
);
=======
         customer_id int auto_increment primary key,
         customer_name varchar(50) not null,
         customer_pwd varchar(255) not null,
         customer_email varchar(100) unique not null,
         customer_phone varchar(20) unique not null,
         customer_addr varchar(255) not null,
         created_at timestamp default current_timestamp,
         role ENUM('CUSTOMER') NOT NULL DEFAULT 'CUSTOMER'
);

CREATE TABLE product (
         product_id INT AUTO_INCREMENT PRIMARY KEY,
         product_name VARCHAR(100) NOT NULL,
         description varchar(1000),
         product_price DECIMAL(10,2) NOT NULL CHECK (product_price >= 0),
         phone_category INT,
         image_path varchar(500),
         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

use ihwastack;
>>>>>>> develop
