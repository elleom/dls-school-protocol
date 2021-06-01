CREATE DATABASE  IF NOT EXISTS `school_protocol` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `school_protocol`;
-- MySQL dump 10.13  Distrib 8.0.24, for Win64 (x86_64)
--
-- Host: localhost    Database: school_protocol
-- ------------------------------------------------------
-- Server version	8.0.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `present` tinyint(1) DEFAULT NULL,
  `student_id` int NOT NULL,
  `lesson_id` int NOT NULL,
  PRIMARY KEY (`id`,`student_id`,`lesson_id`),
  UNIQUE KEY `attendance_id_uindex` (`id`),
  KEY `fk_attendance_student1_idx` (`student_id`),
  KEY `fk_attendance_lesson1_idx` (`lesson_id`),
  CONSTRAINT `fk_attendance_lesson1` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`),
  CONSTRAINT `fk_attendance_student1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (1,1,1,2),(2,1,1,5),(3,NULL,1,15),(4,NULL,1,16),(5,NULL,1,17),(6,NULL,1,18),(7,NULL,1,19),(8,NULL,1,20),(9,NULL,1,21);
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesson`
--

DROP TABLE IF EXISTS `lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lesson` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `code` varchar(10) NOT NULL,
  `subject_id` int NOT NULL,
  PRIMARY KEY (`id`,`subject_id`),
  UNIQUE KEY `lesson_code_uindex` (`code`),
  KEY `fk_lesson_subject1_idx` (`subject_id`),
  CONSTRAINT `fk_lesson_subject1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson`
--

LOCK TABLES `lesson` WRITE;
/*!40000 ALTER TABLE `lesson` DISABLE KEYS */;
INSERT INTO `lesson` VALUES (2,'2021-04-10 15:44:25','sadsad',1),(3,'2021-04-10 15:47:50','123123',1),(4,'2021-04-22 18:49:44','456123',1),(5,'2021-04-22 20:24:38','54654',1),(6,'1971-01-07 22:35:32','TRY1',1),(8,'1971-01-07 22:35:32','TRY2',1),(9,'1971-01-07 22:35:32','TRY3',1),(10,'1971-01-07 22:35:32','TRY4',1),(11,'1971-01-07 22:35:32','TRY5',1),(12,'1971-01-07 22:35:32','TRY6',1),(14,'1971-01-07 22:35:32','TRY7',1),(15,'2021-05-12 13:03:00','asdfg123',1),(16,'2021-05-12 15:19:44','asdfg124',1),(17,'2021-05-12 15:47:14','c12312',1),(18,'2021-05-12 15:49:47','c1231234',1),(19,'2021-05-12 15:53:18','c1231543',1),(20,'2021-05-12 15:55:29','c1231544',1),(21,'2021-05-12 16:01:17','c12544',1),(22,'2021-05-26 10:15:38','kU2mYn',1),(23,'2021-05-26 14:09:05','gMg30F',1),(24,'2021-05-26 14:13:02','KNw8dx',1),(26,'2021-05-29 13:45:45','OOSUvp',1),(27,'2021-05-29 14:54:26','UsJT8n',2),(28,'2021-06-01 14:01:30','86drjH',1),(29,'2021-06-01 14:03:15','NRSudj',2),(30,'2021-06-01 22:09:36','unZ93A',1);
/*!40000 ALTER TABLE `lesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fullName` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(70) NOT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  `role` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'Leonardo Martins','$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu','leon3311@stud.kea.dk',1,'STUDENT'),(2,'Oskar Unikowski','$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu','oska3728@stud.kea.dk',1,'STUDENT'),(3,'Thomas Nielsen','$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu','thom52t7@stud.kea.dk',1,'STUDENT'),(4,'test replication','$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu','test@kea.stud.dk',1,'STUDENT');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_has_subject`
--

DROP TABLE IF EXISTS `student_has_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_has_subject` (
  `student_id` int NOT NULL,
  `subject_id` int NOT NULL,
  PRIMARY KEY (`student_id`,`subject_id`),
  KEY `fk_student_has_subject_subject1_idx` (`subject_id`),
  KEY `fk_student_has_subject_student_idx` (`student_id`),
  CONSTRAINT `fk_student_has_subject_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `fk_student_has_subject_subject1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_has_subject`
--

LOCK TABLES `student_has_subject` WRITE;
/*!40000 ALTER TABLE `student_has_subject` DISABLE KEYS */;
INSERT INTO `student_has_subject` VALUES (1,1),(1,2),(2,2),(3,2),(3,3);
/*!40000 ALTER TABLE `student_has_subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (1,'DLS - Development of Large Systems'),(2,'Testing'),(3,'Database for developers');
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject_has_teacher`
--

DROP TABLE IF EXISTS `subject_has_teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject_has_teacher` (
  `subject_id` int NOT NULL,
  `teacher_id` int NOT NULL,
  PRIMARY KEY (`subject_id`,`teacher_id`),
  KEY `fk_subject_has_teacher_teacher1_idx` (`teacher_id`),
  KEY `fk_subject_has_teacher_subject1_idx` (`subject_id`),
  CONSTRAINT `fk_subject_has_teacher_subject1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`),
  CONSTRAINT `fk_subject_has_teacher_teacher1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject_has_teacher`
--

LOCK TABLES `subject_has_teacher` WRITE;
/*!40000 ALTER TABLE `subject_has_teacher` DISABLE KEYS */;
INSERT INTO `subject_has_teacher` VALUES (1,1),(2,1);
/*!40000 ALTER TABLE `subject_has_teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fullName` varchar(45) NOT NULL,
  `email` varchar(70) NOT NULL,
  `password` varchar(70) NOT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  `role` varchar(15) DEFAULT 'TEACHER',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1,'Andrea Corradini','andc@kea.dk','$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu',1,'TEACHER'),(2,'Tomas Pesek','tomp@kea.dk','$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu',1,'TEACHER');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'school_protocol'
--

--
-- Dumping routines for database 'school_protocol'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-01 22:18:10
