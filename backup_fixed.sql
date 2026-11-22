-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: grievance_db
-- ------------------------------------------------------
-- Server version	8.0.43
--
-- ============================================================
-- Ye file tumhari backup.sql hai, jisme ye fixes lagaye gaye hain:
--   1. category_id, department_id -> AUTO_INCREMENT
--   2. complaint.submitted_at -> DEFAULT CURRENT_TIMESTAMP
--   3. category aur department -> seed data add kiya
--   4. 'user' table ka PRIMARY KEY 'complaint_id' se 'user_id' kiya
--      (iska structure/purpose abhi clear nahi hai, isliye baaki columns
--       (full_name, state, city) aur FK waisa hi chhoda hai jaisa tha)
--   5. category seed data ab svm_model.pkl ke exact 6 labels ke saath hai
--      (electricity, healthcare, ration_aadhar, roads_transport,
--       sanitation, water_supply) -- db_model_sync.sql ke saath consistent
--   6. category_name / department_name par UNIQUE key (duplicate rows nahi
--      banenge, model ke label se id lookup safe rahega)
--   7. complaint.status ka DEFAULT 'Submitted' (pehle NULL rehta tha)
-- ============================================================

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `uq_category_name` (`category_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Seed data for table `category`
--

INSERT INTO `category` (`category_name`) VALUES
('electricity'),
('healthcare'),
('ration_aadhar'),
('roads_transport'),
('sanitation'),
('water_supply');

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `department_id` int NOT NULL AUTO_INCREMENT,
  `department_name` varchar(100) NOT NULL,
  PRIMARY KEY (`department_id`),
  UNIQUE KEY `uq_department_name` (`department_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Seed data for table `department`
--

INSERT INTO `department` (`department_name`) VALUES
('Electricity Board'),          -- electricity
('Health Dept'),                -- healthcare
('Food & Civil Supplies Dept'), -- ration_aadhar
('PWD (Public Works Dept)'),    -- roads_transport
('Municipal Corporation'),      -- sanitation
('Water Board');                -- water_supply

--
-- Table structure for table `complaint`
--

DROP TABLE IF EXISTS `complaint`;
CREATE TABLE `complaint` (
  `complaint_id` varchar(20) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `description` text,
  `priority` varchar(20) DEFAULT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'Submitted',
  `submitted_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `ai_confidence` decimal(5,2) DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `department_id` int DEFAULT NULL,
  PRIMARY KEY (`complaint_id`),
  KEY `category_id` (`category_id`),
  KEY `department_id` (`department_id`),
  CONSTRAINT `complaint_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `complaint_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `complaint`
-- (empty, jaisa original file mein tha)
--

--
-- Table structure for table `user`
-- NOTE: Iska purpose abhi clear nahi hai (login ke liye hai ya kuch aur).
-- Sirf PRIMARY KEY ka naam 'complaint_id' se 'user_id' kiya gaya hai,
-- kyunki do alag tables ka PK same naam share karna galat hai.
-- Baaki structure (full_name/state/city duplication, missing FK to
-- complaint table) waisa hi chhoda gaya hai — pehle ye decide karo ki
-- is table ka asli purpose kya hai, phir ye sahi se design hoga.
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(20) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `state` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `submitted_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
-- (empty, jaisa original file mein tha)
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
