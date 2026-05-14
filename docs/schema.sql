-- ============================================================
-- AfiaHealth Database Schema for MySQL
-- Africa's Premier Healthcare Discovery Platform
-- ============================================================

CREATE DATABASE IF NOT EXISTS afiahealth CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE afiahealth;

-- ============================================================
-- USERS TABLE
-- ============================================================
CREATE TABLE users (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT (UUID()),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('patient','doctor','hospital_admin','admin','super_admin') NOT NULL DEFAULT 'patient',
    profile_photo_url VARCHAR(500),
    date_of_birth DATE,
    gender ENUM('male','female','other','prefer_not_to_say'),
    blood_group ENUM('A+','A-','B+','B-','AB+','AB-','O+','O-'),
    address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100) DEFAULT 'Nigeria',
    latitude DECIMAL(10,8),
    longitude DECIMAL(11,8),
    language_preference VARCHAR(10) DEFAULT 'en',
    is_verified BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    email_verified_at TIMESTAMP NULL,
    phone_verified_at TIMESTAMP NULL,
    last_login_at TIMESTAMP NULL,
    refresh_token_hash VARCHAR(255),
    password_reset_token VARCHAR(255),
    password_reset_expires TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_city_state (city, state)
);

-- ============================================================
-- HOSPITAL CATEGORIES
-- ============================================================
CREATE TABLE hospital_categories (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100) NOT NULL UNIQUE,
    icon VARCHAR(100),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO hospital_categories (name, slug, icon) VALUES
('General Hospital', 'general-hospital', 'hospital'),
('Teaching Hospital', 'teaching-hospital', 'graduation-cap'),
('Specialist Hospital', 'specialist-hospital', 'stethoscope'),
('Maternity Clinic', 'maternity-clinic', 'baby'),
('Eye Clinic', 'eye-clinic', 'eye'),
('Dental Clinic', 'dental-clinic', 'tooth'),
('Mental Health Center', 'mental-health', 'brain'),
('Orthopedic Hospital', 'orthopedic', 'bone'),
('Cancer Center', 'cancer-center', 'ribbon'),
('Pediatric Hospital', 'pediatric', 'child'),
('Dialysis Center', 'dialysis-center', 'filter'),
('Fertility Clinic', 'fertility-clinic', 'heart');

-- ============================================================
-- HOSPITALS TABLE
-- ============================================================
CREATE TABLE hospitals (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT (UUID()),
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    category_id INT UNSIGNED,
    description TEXT,
    short_description VARCHAR(500),
    logo_url VARCHAR(500),
    cover_image_url VARCHAR(500),
    gallery JSON COMMENT 'Array of image URLs',
    email VARCHAR(255),
    phone VARCHAR(20),
    phone_emergency VARCHAR(20),
    whatsapp VARCHAR(20),
    website VARCHAR(255),
    address TEXT NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    country VARCHAR(100) DEFAULT 'Nigeria',
    postal_code VARCHAR(20),
    latitude DECIMAL(10,8) NOT NULL,
    longitude DECIMAL(11,8) NOT NULL,
    google_place_id VARCHAR(255),
    facilities JSON COMMENT 'List of facilities like ICU, OR, Lab, etc.',
    specialties JSON COMMENT 'List of medical specialties',
    services JSON COMMENT 'List of services offered',
    languages_spoken JSON COMMENT 'Languages spoken at hospital',
    insurance_accepted JSON COMMENT 'Insurance providers accepted',
    bed_count INT UNSIGNED,
    year_established YEAR,
    registration_number VARCHAR(100),
    accreditation VARCHAR(255),
    ownership_type ENUM('public','private','faith_based','ngo') DEFAULT 'private',
    hospital_tier ENUM('primary','secondary','tertiary') DEFAULT 'secondary',
    opening_hours JSON COMMENT 'Hours by day of week',
    is_open_24_7 BOOLEAN DEFAULT FALSE,
    has_emergency BOOLEAN DEFAULT TRUE,
    has_ambulance BOOLEAN DEFAULT FALSE,
    has_telemedicine BOOLEAN DEFAULT FALSE,
    has_pharmacy BOOLEAN DEFAULT FALSE,
    has_laboratory BOOLEAN DEFAULT FALSE,
    has_radiology BOOLEAN DEFAULT FALSE,
    average_wait_time INT COMMENT 'Minutes',
    average_rating DECIMAL(3,2) DEFAULT 0.00,
    review_count INT UNSIGNED DEFAULT 0,
    is_verified BOOLEAN DEFAULT FALSE,
    is_featured BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    verification_date DATE,
    admin_user_id BIGINT UNSIGNED,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES hospital_categories(id),
    FOREIGN KEY (admin_user_id) REFERENCES users(id),
    INDEX idx_city_state (city, state),
    INDEX idx_lat_lng (latitude, longitude),
    INDEX idx_is_active_verified (is_active, is_verified),
    INDEX idx_average_rating (average_rating),
    FULLTEXT INDEX ft_name_description (name, description)
);

-- ============================================================
-- SPECIALTIES (Lookup Table)
-- ============================================================
CREATE TABLE specialties (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100) NOT NULL UNIQUE,
    icon VARCHAR(100),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO specialties (name, slug) VALUES
('Cardiology','cardiology'), ('Neurology','neurology'), ('Oncology','oncology'),
('Pediatrics','pediatrics'), ('Obstetrics & Gynecology','obs-gyn'),
('Orthopedics','orthopedics'), ('Dermatology','dermatology'),
('Ophthalmology','ophthalmology'), ('ENT','ent'),
('Psychiatry','psychiatry'), ('General Surgery','general-surgery'),
('Internal Medicine','internal-medicine'), ('Emergency Medicine','emergency-medicine'),
('Radiology','radiology'), ('Anesthesiology','anesthesiology'),
('Urology','urology'), ('Nephrology','nephrology'),
('Gastroenterology','gastroenterology'), ('Endocrinology','endocrinology'),
('Pulmonology','pulmonology'), ('Hematology','hematology'),
('Rheumatology','rheumatology'), ('Geriatrics','geriatrics'),
('Plastic Surgery','plastic-surgery'), ('Dentistry','dentistry');

-- ============================================================
-- DOCTORS TABLE
-- ============================================================
CREATE TABLE doctors (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT (UUID()),
    user_id BIGINT UNSIGNED NOT NULL,
    hospital_id BIGINT UNSIGNED,
    specialty_id INT UNSIGNED,
    title ENUM('Dr','Prof','Assoc. Prof') DEFAULT 'Dr',
    bio TEXT,
    qualification VARCHAR(500) COMMENT 'Degrees and certifications',
    medical_license_number VARCHAR(100) NOT NULL,
    years_of_experience INT UNSIGNED DEFAULT 0,
    consultation_fee DECIMAL(10,2),
    consultation_duration INT DEFAULT 30 COMMENT 'Minutes',
    languages JSON,
    available_days JSON COMMENT 'Array of weekday numbers 0-6',
    available_from TIME,
    available_to TIME,
    telemedicine_available BOOLEAN DEFAULT FALSE,
    home_visit_available BOOLEAN DEFAULT FALSE,
    average_rating DECIMAL(3,2) DEFAULT 0.00,
    review_count INT UNSIGNED DEFAULT 0,
    total_consultations INT UNSIGNED DEFAULT 0,
    is_verified BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    verification_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id),
    FOREIGN KEY (specialty_id) REFERENCES specialties(id),
    INDEX idx_specialty (specialty_id),
    INDEX idx_hospital (hospital_id),
    INDEX idx_rating (average_rating)
);

-- ============================================================
-- APPOINTMENTS TABLE
-- ============================================================
CREATE TABLE appointments (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT (UUID()),
    reference_number VARCHAR(20) NOT NULL UNIQUE,
    patient_id BIGINT UNSIGNED NOT NULL,
    doctor_id BIGINT UNSIGNED,
    hospital_id BIGINT UNSIGNED NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    duration_minutes INT DEFAULT 30,
    appointment_type ENUM('in_person','telemedicine','home_visit') DEFAULT 'in_person',
    reason_for_visit TEXT,
    symptoms TEXT,
    status ENUM('pending','confirmed','cancelled','completed','no_show','rescheduled') DEFAULT 'pending',
    cancellation_reason TEXT,
    notes TEXT COMMENT 'Doctor notes post appointment',
    prescription_url VARCHAR(500),
    consultation_fee DECIMAL(10,2),
    payment_status ENUM('pending','paid','refunded','waived') DEFAULT 'pending',
    payment_method VARCHAR(50),
    payment_reference VARCHAR(100),
    reminder_sent BOOLEAN DEFAULT FALSE,
    telemedicine_link VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES users(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id),
    INDEX idx_patient (patient_id),
    INDEX idx_doctor (doctor_id),
    INDEX idx_hospital (hospital_id),
    INDEX idx_date_status (appointment_date, status),
    INDEX idx_reference (reference_number)
);

-- ============================================================
-- REVIEWS TABLE
-- ============================================================
CREATE TABLE reviews (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT (UUID()),
    reviewer_id BIGINT UNSIGNED NOT NULL,
    hospital_id BIGINT UNSIGNED,
    doctor_id BIGINT UNSIGNED,
    appointment_id BIGINT UNSIGNED,
    overall_rating TINYINT UNSIGNED NOT NULL CHECK (overall_rating BETWEEN 1 AND 5),
    cleanliness_rating TINYINT UNSIGNED CHECK (cleanliness_rating BETWEEN 1 AND 5),
    staff_rating TINYINT UNSIGNED CHECK (staff_rating BETWEEN 1 AND 5),
    wait_time_rating TINYINT UNSIGNED CHECK (wait_time_rating BETWEEN 1 AND 5),
    facilities_rating TINYINT UNSIGNED CHECK (facilities_rating BETWEEN 1 AND 5),
    title VARCHAR(255),
    body TEXT NOT NULL,
    visit_date DATE,
    is_anonymous BOOLEAN DEFAULT FALSE,
    is_verified_visit BOOLEAN DEFAULT FALSE,
    is_approved BOOLEAN DEFAULT FALSE,
    is_flagged BOOLEAN DEFAULT FALSE,
    flag_reason TEXT,
    admin_response TEXT,
    helpful_count INT UNSIGNED DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (reviewer_id) REFERENCES users(id),
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    FOREIGN KEY (appointment_id) REFERENCES appointments(id),
    INDEX idx_hospital (hospital_id),
    INDEX idx_doctor (doctor_id),
    INDEX idx_is_approved (is_approved)
);

-- ============================================================
-- PHARMACIES TABLE
-- ============================================================
CREATE TABLE pharmacies (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT (UUID()),
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    logo_url VARCHAR(500),
    email VARCHAR(255),
    phone VARCHAR(20),
    whatsapp VARCHAR(20),
    website VARCHAR(255),
    address TEXT NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    country VARCHAR(100) DEFAULT 'Nigeria',
    latitude DECIMAL(10,8) NOT NULL,
    longitude DECIMAL(11,8) NOT NULL,
    opening_hours JSON,
    is_open_24_7 BOOLEAN DEFAULT FALSE,
    has_delivery BOOLEAN DEFAULT FALSE,
    delivery_radius_km INT DEFAULT 5,
    insurance_accepted JSON,
    average_rating DECIMAL(3,2) DEFAULT 0.00,
    review_count INT UNSIGNED DEFAULT 0,
    is_verified BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_city_state (city, state),
    INDEX idx_lat_lng (latitude, longitude)
);

-- ============================================================
-- AMBULANCES TABLE
-- ============================================================
CREATE TABLE ambulances (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT (UUID()),
    hospital_id BIGINT UNSIGNED,
    provider_name VARCHAR(255) NOT NULL,
    vehicle_number VARCHAR(50) NOT NULL UNIQUE,
    vehicle_type ENUM('basic','advanced','neonatal','air') DEFAULT 'basic',
    driver_name VARCHAR(255),
    driver_phone VARCHAR(20) NOT NULL,
    paramedic_name VARCHAR(255),
    equipment JSON COMMENT 'List of onboard equipment',
    current_latitude DECIMAL(10,8),
    current_longitude DECIMAL(11,8),
    status ENUM('available','dispatched','maintenance','offline') DEFAULT 'available',
    base_location TEXT,
    base_latitude DECIMAL(10,8),
    base_longitude DECIMAL(11,8),
    city VARCHAR(100),
    state VARCHAR(100),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id),
    INDEX idx_status (status),
    INDEX idx_city_state (city, state)
);

-- ============================================================
-- EMERGENCY REQUESTS TABLE
-- ============================================================
CREATE TABLE emergency_requests (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT (UUID()),
    reference_number VARCHAR(20) NOT NULL UNIQUE,
    requester_id BIGINT UNSIGNED,
    requester_phone VARCHAR(20) NOT NULL,
    patient_name VARCHAR(255),
    patient_age INT,
    emergency_type ENUM('cardiac','accident','stroke','respiratory','obstetric','trauma','other') DEFAULT 'other',
    description TEXT,
    pickup_address TEXT NOT NULL,
    pickup_latitude DECIMAL(10,8) NOT NULL,
    pickup_longitude DECIMAL(11,8) NOT NULL,
    destination_hospital_id BIGINT UNSIGNED,
    assigned_ambulance_id BIGINT UNSIGNED,
    status ENUM('requested','dispatched','arrived','in_transit','completed','cancelled') DEFAULT 'requested',
    dispatched_at TIMESTAMP NULL,
    arrived_at TIMESTAMP NULL,
    completed_at TIMESTAMP NULL,
    estimated_arrival_minutes INT,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (requester_id) REFERENCES users(id),
    FOREIGN KEY (destination_hospital_id) REFERENCES hospitals(id),
    FOREIGN KEY (assigned_ambulance_id) REFERENCES ambulances(id),
    INDEX idx_status (status),
    INDEX idx_reference (reference_number)
);

-- ============================================================
-- INSURANCE PROVIDERS TABLE
-- ============================================================
CREATE TABLE insurance_providers (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT (UUID()),
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    logo_url VARCHAR(500),
    description TEXT,
    email VARCHAR(255),
    phone VARCHAR(20),
    website VARCHAR(255),
    plans JSON COMMENT 'Array of plan objects with name, coverage, premium',
    countries_available JSON DEFAULT '["Nigeria"]',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- BLOG ARTICLES TABLE
-- ============================================================
CREATE TABLE articles (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT (UUID()),
    author_id BIGINT UNSIGNED NOT NULL,
    title VARCHAR(500) NOT NULL,
    slug VARCHAR(500) NOT NULL UNIQUE,
    excerpt TEXT,
    body LONGTEXT NOT NULL,
    cover_image_url VARCHAR(500),
    category VARCHAR(100),
    tags JSON,
    read_time_minutes INT DEFAULT 5,
    views INT UNSIGNED DEFAULT 0,
    is_published BOOLEAN DEFAULT FALSE,
    published_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FULLTEXT INDEX ft_title_body (title, body),
    INDEX idx_is_published (is_published)
);

-- ============================================================
-- NOTIFICATIONS TABLE
-- ============================================================
CREATE TABLE notifications (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    type VARCHAR(100) NOT NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    data JSON,
    is_read BOOLEAN DEFAULT FALSE,
    read_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_read (user_id, is_read)
);

-- ============================================================
-- WAITING TIME TRACKING
-- ============================================================
CREATE TABLE waiting_time_logs (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    hospital_id BIGINT UNSIGNED NOT NULL,
    reported_by BIGINT UNSIGNED,
    wait_minutes INT NOT NULL,
    department VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id),
    FOREIGN KEY (reported_by) REFERENCES users(id)
);

-- ============================================================
-- TELEMEDICINE SESSIONS TABLE
-- ============================================================
CREATE TABLE telemedicine_sessions (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT (UUID()),
    appointment_id BIGINT UNSIGNED NOT NULL,
    session_token VARCHAR(255) NOT NULL UNIQUE,
    provider ENUM('jitsi','zoom','custom') DEFAULT 'jitsi',
    room_name VARCHAR(255),
    join_url VARCHAR(500),
    host_url VARCHAR(500),
    started_at TIMESTAMP NULL,
    ended_at TIMESTAMP NULL,
    duration_minutes INT,
    recording_url VARCHAR(500),
    status ENUM('scheduled','active','ended','cancelled') DEFAULT 'scheduled',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);

-- ============================================================
-- AUDIT LOG
-- ============================================================
CREATE TABLE audit_logs (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED,
    action VARCHAR(100) NOT NULL,
    entity_type VARCHAR(100),
    entity_id BIGINT UNSIGNED,
    old_values JSON,
    new_values JSON,
    ip_address VARCHAR(45),
    user_agent TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_action (user_id, action),
    INDEX idx_entity (entity_type, entity_id)
);
