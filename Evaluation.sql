CREATE DATABASE  IF NOT EXISTS `evaluation` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `evaluation`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: evaluation
-- ------------------------------------------------------
-- Server version	5.7.5-m15-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answers` (
  `questionId` int(11) NOT NULL,
  `controlNumberId` int(11) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `isArchived` tinyint(1) NOT NULL DEFAULT '0',
  KEY `answersQuestionId_idx` (`questionId`),
  KEY `answerControlNumber_idx` (`controlNumberId`),
  CONSTRAINT `answersControlNumId` FOREIGN KEY (`controlNumberId`) REFERENCES `controlnumbers` (`controlNumberId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `answersQuestionId` FOREIGN KEY (`questionId`) REFERENCES `questions` (`questionId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `controlNumberId` int(11) NOT NULL,
  `comment` varchar(1000) NOT NULL,
  KEY `commentControlNum_idx` (`controlNumberId`),
  CONSTRAINT `commentControlNumId` FOREIGN KEY (`controlNumberId`) REFERENCES `controlnumbers` (`controlNumberId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `controlnumbers`
--

DROP TABLE IF EXISTS `controlnumbers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `controlnumbers` (
  `controlNumberId` int(11) NOT NULL AUTO_INCREMENT,
  `controlNumber` char(10) NOT NULL,
  `formId` int(11) NOT NULL,
  `serviceId` int(11) NOT NULL,
  `expirationTime` datetime(6) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`controlNumberId`),
  KEY `controlNumbersFormId_idx` (`formId`),
  KEY `controlNumberServiceId_idx` (`serviceId`),
  CONSTRAINT `controlNumberServiceId` FOREIGN KEY (`serviceId`) REFERENCES `services` (`serviceId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `controlNumbersFormId` FOREIGN KEY (`formId`) REFERENCES `forms` (`formId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `controlnumbers`
--

LOCK TABLES `controlnumbers` WRITE;
/*!40000 ALTER TABLE `controlnumbers` DISABLE KEYS */;
/*!40000 ALTER TABLE `controlnumbers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formquestions`
--

DROP TABLE IF EXISTS `formquestions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formquestions` (
  `formId` int(11) NOT NULL,
  `questionId` int(11) NOT NULL,
  `isArchived` tinyint(1) NOT NULL DEFAULT '0',
  KEY `formquestionsFormId_idx` (`formId`),
  KEY `formsquestionsQuestionId_idx` (`questionId`),
  CONSTRAINT `formquestionsFormId` FOREIGN KEY (`formId`) REFERENCES `forms` (`formId`),
  CONSTRAINT `formsquestionsQuestionId` FOREIGN KEY (`questionId`) REFERENCES `questions` (`questionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formquestions`
--

LOCK TABLES `formquestions` WRITE;
/*!40000 ALTER TABLE `formquestions` DISABLE KEYS */;
INSERT INTO `formquestions` VALUES (1,1,0),(1,2,0),(1,3,0),(1,4,0),(1,5,0),(2,1,0),(2,2,0),(2,3,0),(3,1,0),(3,3,0),(3,6,0);
/*!40000 ALTER TABLE `formquestions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forms`
--

DROP TABLE IF EXISTS `forms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forms` (
  `formId` int(11) NOT NULL AUTO_INCREMENT,
  `officeId` int(11) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date DEFAULT NULL,
  `isArchived` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`formId`),
  KEY `formsOfficeId_idx` (`officeId`),
  CONSTRAINT `formsOfficeId` FOREIGN KEY (`officeId`) REFERENCES `offices` (`officeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forms`
--

LOCK TABLES `forms` WRITE;
/*!40000 ALTER TABLE `forms` DISABLE KEYS */;
INSERT INTO `forms` VALUES (1,5,'2015-03-29','2016-03-29',0),(2,6,'2015-04-02','2016-04-02',0),(3,7,'2015-04-02','2016-04-02',0);
/*!40000 ALTER TABLE `forms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offices`
--

DROP TABLE IF EXISTS `offices`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offices` (
  `officeId` int(11) NOT NULL AUTO_INCREMENT,
  `officeName` varchar(45) NOT NULL,
  `isArchived` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`officeId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offices`
--

LOCK TABLES `offices` WRITE;
/*!40000 ALTER TABLE `offices` DISABLE KEYS */;
INSERT INTO `offices` VALUES (1,'Building and Grounds Maintenance Office',0),
(2,'Civil and Sanitary Works Office',0),
(3,'Mechanical and Electrical Works Office',0),
(4,'Facilities Management Office (STC)',0),
(5,'Enrolment Services Hub',0),
(6,'Office of Admissions and Scholarships',0),
(7,'Office of the University Registrar',0),
(8,'Office of the Academic Services for Integrate',0),
(9,'Enrolment Services for STC',0);
/*!40000 ALTER TABLE `offices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `questionId` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL,
  `isArchived` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`questionId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'The Employees are nice.',0),(2,'The Service was fast.',0),(3,'I was able to accomplish what I wanted to do.',0),(4,'I was guided upon my inquiry.',0),(5,'The staff were informative.',0),(6,'I hate SPSWENG',0);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `services` (
  `serviceId` int(11) NOT NULL AUTO_INCREMENT,
  `serviceName` varchar(45) NOT NULL,
  `officeId` int(11) NOT NULL,
  `isArchived` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`serviceId`),
  KEY `servicesOfficeId_idx` (`officeId`),
  CONSTRAINT `servicesOfficeId` FOREIGN KEY (`officeId`) REFERENCES `offices` (`officeId`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES (1,'Admission Inquiry',5,0),(2,'Application Form',5,0),(3,'Submission of Requirements',5,0),(4,'Scholarship Concerns',5,0),(5,'Change of Program',5,0),(6,'Reconsideration',5,0),(7,'Transferee',5,0),(8,'Enrolment',5,0),(9,'Request for Documents',5,0),(10,'ID Concerns',5,0),(11,'Graduation Concerns',5,0),(12,'LOA Concerns',5,0),(13,'Course Syllabus',5,0),(14,'Change of Student Information',5,0),(15,'Admission Inquiry',6,0),(16,'Application Form',6,0),(17,'Submission of Requirements',6,0),(18,'Graduate Studies Concerns',6,0),(19,'Scholarship Concerns',6,0),(20,'Change of Program',6,0),(21,'Reconsideration',6,0),(22,'Enrolment',7,0),(23,'ID Concerns',7,0),(24,'Graduation Concerns',7,0),(25,'Dropping/Shifting Concerns',7,0),(26,'Request for Documents',7,0),(27,'EAF',7,0),(28,'Clearance',7,0),(29,'Change of Grades',7,0),(30,'General Service',1,0),(31,'General Service',2,0),(32,'General Service',3,0),(33,'General Service',4,0),(34,'General Service',8,0);
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) DEFAULT NULL,
  `userTitle` varchar(45) NOT NULL,
  `userEmail` varchar(45) NOT NULL,
  `userPassword` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `isArchived` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'winona','administrator','winonaerive@yahoo.com','tay123','administrator',0),(2,'jake','service personnel','jake@yahoo.com','123qwe','service personnel',0),(3,'johan','officehead','johantan@yahoo.com','asdf1234','officehead',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `officeheads`
--

DROP TABLE IF EXISTS `officeheads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `officeheads` (
  `userId` int(11) NOT NULL,
  `officeId` int(11) NOT NULL,
  `isArchived` tinyint(1) NOT NULL DEFAULT '0',
  KEY `officeheadsUserId_idx` (`userId`),
  KEY `officeheadsOfficeId_idx` (`officeId`),
  CONSTRAINT `officeheadsUserId` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`),
  CONSTRAINT `officeheadsOfficeId` FOREIGN KEY (`officeId`) REFERENCES `offices` (`officeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formquestions`
--

LOCK TABLES `officeheads` WRITE;
/*!40000 ALTER TABLE `officeheads` DISABLE KEYS */;
INSERT INTO `officeheads` VALUES (3, 5, 0), (3,6, 0) ,(3,7, 0);
/*!40000 ALTER TABLE `officeheads` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping routines for database 'evaluation'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-04 20:28:28
