-- dentalcare schema (MySQL 8+)
-- Run in order: parent tables first, appointments last.

CREATE DATABASE IF NOT EXISTS dentalcare;
USE dentalcare;

CREATE TABLE staffs (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    staff_firstname VARCHAR(50),
    staff_lastname VARCHAR(50),
    staff_phone VARCHAR(50),
    staff_email VARCHAR(50),
    staff_password CHAR(32),
    staff_status VARCHAR(20) DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- dentist_id 0 is the "Not Assigned" placeholder (see seed.sql)
CREATE TABLE dentists (
    dentist_id INT AUTO_INCREMENT PRIMARY KEY,
    dentist_firstname VARCHAR(50),
    dentist_lastname VARCHAR(50),
    dentist_phone VARCHAR(50),
    dentist_email VARCHAR(50),
    dentist_password CHAR(32),
    dentist_status VARCHAR(20) DEFAULT 'Available',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_firstname VARCHAR(50),
    patient_lastname VARCHAR(50),
    patient_phone VARCHAR(50),
    patient_email VARCHAR(50),
    patient_password CHAR(32),
    patient_status VARCHAR(20) DEFAULT 'Active',
    first_time_booked CHAR(1) DEFAULT 'N',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE treatments (
    treat_id INT AUTO_INCREMENT PRIMARY KEY,
    treat_title VARCHAR(100),
    treat_desc LONG VARCHAR,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- aptmt_status: 'Booked' | 'Completed' | 'Cancelled'
CREATE TABLE appointments (
    aptmt_id INT AUTO_INCREMENT PRIMARY KEY,
    aptmt_date DATE,
    aptmt_time TIME,
    aptmt_status VARCHAR(20) DEFAULT 'Booked',
    aptmt_remark LONG VARCHAR,
    patient_id INT,
    treat_id INT,
    dentist_id INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (treat_id) REFERENCES treatments(treat_id),
    FOREIGN KEY (dentist_id) REFERENCES dentists(dentist_id)
);
