drop database if exists tripleSDb;

Create database IF NOT EXISTS tripleSDb;

Use tripleSDb;

CREATE TABLE IF NOT EXISTS entity_details 
	(id INT primary key, 
		entity_type VARCHAR(100), 
        reference_entity_id int,
        entity_category VARCHAR(100),
        first_name VARCHAR(100),
        middle_name VARCHAR(100),
        last_name VARCHAR(100),
        full_name VARCHAR(300),
        telephone_no VARCHAR(15),
        mobile_no VARCHAR(15),
        alternate_contact_no VARCHAR(15),
        salary_per_month numeric(15,2),
        occupation_profession VARCHAR(500),
        education_loan_amount numeric(15,2),
        help_amount numeric(15,2)
	);
        
ALTER TABLE entity_details 
	ADD FOREIGN KEY ref_entity_id_fk (reference_entity_id) 
	REFERENCES entity_details (id);
        
CREATE TABLE IF NOT EXISTS student_entity_details
	(id INT primary key, 
		entity_id INT,
        mother_tongue VARCHAR(100), 
        religion VARCHAR(50),
        have_won_prize CHAR(1),
        describe_prize_details VARCHAR(5000),
        aim_in_life VARCHAR(5000),
        have_job_or_business CHAR(1),
        describe_job_business_details VARCHAR(5000),
        have_other_problem_in_family CHAR(1),
        describe_other_problems_in_family VARCHAR(5000),
        got_help_from_other_sources CHAR(1),
        have_education_loan CHAR(1),
        have_other_family_members_got_help CHAR(1),
        describe_if_other_family_members_got_help CHAR(1),
        date_of_birth VARCHAR(10),
        place_of_birth VARCHAR(100),
        FOREIGN KEY (entity_id) REFERENCES entity_details(id)
	);        

-- 21st Feb 2017
CREATE TABLE IF NOT EXISTS entity_bank_details 
	(id INT primary key, 
		entity_id INT,
        is_current CHAR(1), 
        bank_account_no VARCHAR(25),
        bank_name VARCHAR(100),
        branch VARCHAR(100),
        ifsc_code VARCHAR(25),
        account_holder_name1 VARCHAR(300),
        account_holder_name2 VARCHAR(300),
        account_holder_name3 VARCHAR(300),
        FOREIGN KEY (entity_id) REFERENCES entity_details(id)
	);    
    
CREATE TABLE IF NOT EXISTS entity_address_details 
	(id INT primary key, 
		entity_id INT,
        address_type VARCHAR(50), 
        address_line1 VARCHAR(1000),
        address_line2 VARCHAR(1000),
        city VARCHAR(50),
        state VARCHAR(50),
        country VARCHAR(50),
        pincode VARCHAR(10),
        FOREIGN KEY (entity_id) REFERENCES entity_details(id)
	);
    
CREATE TABLE IF NOT EXISTS residence_details
	(id INT primary key, 
		entity_id INT,
        residence_ownership VARCHAR(50), 
        residence_ownership_other_description VARCHAR(500),
        residence_size_in_sqft numeric(8,2),
        residence_rent_amount numeric(15,2),
        residence_type VARCHAR(50),
        have_water_supply CHAR(1),
        have_bathroom CHAR(1),
        have_toilet CHAR(1),
        have_cot_bed CHAR(1),
        have_cup_board CHAR(1),
        have_cooking_gas CHAR(1),
        have_television CHAR(1),
        have_refrigerator CHAR(1),
        have_vehicle CHAR(1),
        have_washing_machine CHAR(1),
        have_oven CHAR(1),
        have_computer CHAR(1),
        FOREIGN KEY (entity_id) REFERENCES entity_details(id)
	);

CREATE TABLE IF NOT EXISTS student_course_details
	(id INT primary key, 
		entity_id INT,
        course_name VARCHAR(200), 
        course_duration VARCHAR(25),
        expected_total_course_fees NUMERIC(15,2),
        school_college_institute_name VARCHAR(300),
        FOREIGN KEY (entity_id) REFERENCES entity_details(id)
	);
    
CREATE TABLE IF NOT EXISTS student_curriculum_record
	(id INT primary key, 
		entity_id INT,
        student_course_details_id INT, 
        course_year VARCHAR(100),
        passing_year CHAR(4),
        percentage_marks NUMERIC(6,2),
        grade VARCHAR(25),
        FOREIGN KEY (entity_id) REFERENCES entity_details(id),
        FOREIGN KEY (student_course_details_id) REFERENCES student_course_details(id)
	);
    
CREATE TABLE IF NOT EXISTS student_file
	(id INT primary key, 
		file_no VARCHAR(10) unique,
        file_status VARCHAR(50), 
        entity_id INT,
        created_by VARCHAR(50),
        created_date DATE,
        interviewed_by VARCHAR(50),
        interviewed_date DATE,
        FOREIGN KEY (entity_id) REFERENCES entity_details(id)
	);
    
CREATE TABLE IF NOT EXISTS student_application
	(id INT primary key, 
		application_no VARCHAR(10) unique,
        file_id INT,
        application_status VARCHAR(50),
        student_curriculum_record_id INT,
        created_by VARCHAR(50),
        created_date DATE,
        interviewed_by VARCHAR(50),
        interviewed_date DATE,
        FOREIGN KEY (student_curriculum_record_id) REFERENCES student_curriculum_record(id),
        FOREIGN KEY (file_id) REFERENCES student_file(id)
	);
    

-- 16th Mar 2017    
SET FOREIGN_KEY_CHECKS = 0;
ALTER TABLE student_file MODIFY COLUMN id INT AUTO_INCREMENT;
ALTER TABLE student_file AUTO_INCREMENT=1001;

ALTER TABLE entity_details MODIFY COLUMN id INT AUTO_INCREMENT;
ALTER TABLE entity_details AUTO_INCREMENT=1001;
ALTER TABLE entity_details MODIFY reference_entity_id INT NULL;

-- 26th Mar 2017
CREATE TABLE IF NOT EXISTS user
	(id INT primary key, 
		first_name VARCHAR(100),
        last_name VARCHAR(100),
        display_user_name VARCHAR(100),
        created_date DATE,
        password VARCHAR(20),
        email_id VARCHAR(100),
        mobile_no VARCHAR(15)
	);
    
CREATE TABLE IF NOT EXISTS role
	(id INT primary key, 
		name VARCHAR(200)
	);    
    
CREATE TABLE IF NOT EXISTS user_role
	(user_id INT, 
		role_id INT,
        PRIMARY KEY (user_id,role_id),
        FOREIGN KEY (user_id) REFERENCES user(id),
        FOREIGN KEY (role_id) REFERENCES role(id)
	);


-- 03rd May 2017    
ALTER TABLE user MODIFY COLUMN id INT AUTO_INCREMENT;
ALTER TABLE role MODIFY COLUMN id INT AUTO_INCREMENT;


-- 04th May 2017
ALTER TABLE user MODIFY COLUMN password VARCHAR(70);


-- 07th May 2017
ALTER TABLE student_file MODIFY COLUMN file_no INT;


-- 13th May 2017
ALTER TABLE entity_details ADD COLUMN email_id VARCHAR(100);
ALTER TABLE entity_address_details CHANGE pincode pincode VARCHAR(10);
ALTER TABLE entity_address_details DROP FOREIGN KEY entity_address_details_ibfk_1;
ALTER TABLE entity_address_details DROP COLUMN entity_id;
ALTER TABLE entity_details ADD COLUMN address_id INT;
ALTER TABLE entity_details 
	ADD FOREIGN KEY entity_address_details_fk (address_id)
	REFERENCES entity_address_details (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;
ALTER TABLE entity_address_details MODIFY COLUMN id INT AUTO_INCREMENT;


-- 14th May 2017
ALTER TABLE student_entity_details DROP FOREIGN KEY student_entity_details_ibfk_1;
ALTER TABLE student_entity_details DROP COLUMN entity_id;
ALTER TABLE entity_details ADD COLUMN student_id INT;
ALTER TABLE entity_details
	ADD FOREIGN KEY student_entity_details_fk (Student_ID)
	REFERENCES student_entity_details (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;
ALTER TABLE student_entity_details MODIFY COLUMN id INT AUTO_INCREMENT;
ALTER TABLE student_entity_details MODIFY COLUMN date_of_birth DATE;
ALTER TABLE student_entity_details DROP COLUMN have_won_prize;
ALTER TABLE student_entity_details DROP COLUMN have_job_or_business;
ALTER TABLE student_entity_details DROP COLUMN have_other_problem_in_family;
ALTER TABLE student_entity_details DROP COLUMN have_other_family_members_got_help;
ALTER TABLE student_entity_details MODIFY COLUMN got_help_from_other_sources BIT(1);
ALTER TABLE student_entity_details MODIFY COLUMN have_education_loan BIT(1);
ALTER TABLE student_entity_details MODIFY COLUMN describe_if_other_family_members_got_help VARCHAR(1000);


-- 16th May 2017
ALTER TABLE student_entity_details MODIFY COLUMN describe_prize_details VARCHAR(1000);
ALTER TABLE student_entity_details MODIFY COLUMN aim_in_life VARCHAR(1000);
ALTER TABLE student_entity_details MODIFY COLUMN describe_job_business_details VARCHAR(1000);
ALTER TABLE student_entity_details MODIFY COLUMN describe_other_problems_in_family VARCHAR(1000);


-- 19th May 2017
ALTER TABLE entity_details ADD COLUMN age TINYINT UNSIGNED;
ALTER TABLE entity_details MODIFY COLUMN age INT UNSIGNED;
ALTER TABLE entity_details ADD COLUMN qualification VARCHAR(100);

-- 26th Sept 2017
SET FOREIGN_KEY_CHECKS = 1;

-- 12th Oct 2017
ALTER TABLE entity_bank_details MODIFY COLUMN id INT AUTO_INCREMENT;

-- 13th Oct 2017
ALTER TABLE entity_bank_details MODIFY COLUMN is_current BIT(1);