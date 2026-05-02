INSERT INTO ems.departments (name, department_code, status, description)
VALUES 
('Human Resources', 'HR', 1, 'Handles recruitment and employee relations'),
('Finance', 'FIN', 1, 'Manages company finances and budgeting'),
('IT Department', 'IT',1, 'Responsible for system and infrastructure'),
('Marketing', 'MKT', 0, 'Handles promotions and campaigns');

INSERT INTO employees (
    first_name, last_name, email, date_of_birth, gender, address, contact_number,
    employee_code, department_id, designation, role, date_of_joining,
    reporting_manager, salary, bank_account_number, user_name, password,
    last_login, photo, status
) VALUES

-- IT Department
('John', 'Doe', 'john.doe@email.com', '1995-05-10', 'Male', 'Colombo', '0771234567',
 'EMP001', 1, 'Software Engineer', 'EMPLOYEE', '2023-01-10',
 'Michael Scott', 80000, '1234567890', 'john.doe', 'pass123',
 NULL, NULL, 'Active'),

('Alice', 'Smith', 'alice.smith@email.com', '1993-08-15', 'Female', 'Kandy', '0779876543',
 'EMP002', 1, 'Senior Developer', 'MANAGER', '2022-03-20',
 NULL, 120000, '2234567890', 'alice.smith', 'pass123',
 NULL, NULL, 'Active'),

-- HR Department
('Bob', 'Brown', 'bob.brown@email.com', '1990-02-12', 'Male', 'Galle', '0712345678',
 'EMP003', 2, 'HR Executive', 'EMPLOYEE', '2021-06-15',
 'Sarah Lee', 60000, '3234567890', 'bob.brown', 'pass123',
 NULL, NULL, 'Active'),

('Sarah', 'Lee', 'sarah.lee@email.com', '1988-11-25', 'Female', 'Negombo', '0723456789',
 'EMP004', 2, 'HR Manager', 'MANAGER', '2020-02-10',
 NULL, 110000, '4234567890', 'sarah.lee', 'pass123',
 NULL, NULL, 'Active'),

-- Finance Department
('David', 'Wilson', 'david.wilson@email.com', '1992-07-19', 'Male', 'Jaffna', '0756789123',
 'EMP005', 3, 'Accountant', 'EMPLOYEE', '2022-09-01',
 'Emma Watson', 70000, '5234567890', 'david.wilson', 'pass123',
 NULL, NULL, 'Active'),

('Emma', 'Watson', 'emma.watson@email.com', '1987-04-30', 'Female', 'Colombo', '0767891234',
 'EMP006', 3, 'Finance Manager', 'MANAGER', '2019-12-05',
 NULL, 130000, '6234567890', 'emma.watson', 'pass123',
 NULL, NULL, 'Active');