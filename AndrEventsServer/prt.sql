CREATE DATABASE  IF NOT EXISTS `prt` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `prt`;
-- MySQL dump 10.13  Distrib 5.6.11, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: prt
-- ------------------------------------------------------
-- Server version	5.6.14

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
-- Table structure for table `evenement`
--

DROP TABLE IF EXISTS `evenement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evenement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) DEFAULT NULL,
  `dateDebut` datetime DEFAULT NULL,
  `dateFin` datetime DEFAULT NULL,
  `lieu` varchar(45) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `valide` tinyint(1) DEFAULT '0',
  `createur` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Evenement_User1_idx` (`createur`),
  CONSTRAINT `fk_Evenement_User1` FOREIGN KEY (`createur`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evenement`
--

LOCK TABLES `evenement` WRITE;
/*!40000 ALTER TABLE `evenement` DISABLE KEYS */;
INSERT INTO `evenement` VALUES (1,'Rencontres IG2Iennes','2013-10-03 08:00:00','2013-10-03 17:00:00','IG2I',50.43515,2.82351,'Rencontres eleves-entreprises',1,1),(2,'Portes Ouvertes','2014-02-01 09:00:00','2014-02-01 17:00:00','IG2I',50.43515,2.82351,'Recontres eleves-lyceens',1,1);
/*!40000 ALTER TABLE `evenement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evenement_has_tags`
--

DROP TABLE IF EXISTS `evenement_has_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evenement_has_tags` (
  `Evenement_id` int(11) NOT NULL,
  `Tags_id` int(11) NOT NULL,
  PRIMARY KEY (`Evenement_id`,`Tags_id`),
  KEY `fk_Evenement_has_Tags_Tags1_idx` (`Tags_id`),
  KEY `fk_Evenement_has_Tags_Evenement_idx` (`Evenement_id`),
  CONSTRAINT `fk_Evenement_has_Tags_Evenement` FOREIGN KEY (`Evenement_id`) REFERENCES `evenement` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Evenement_has_Tags_Tags1` FOREIGN KEY (`Tags_id`) REFERENCES `tags` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evenement_has_tags`
--

LOCK TABLES `evenement_has_tags` WRITE;
/*!40000 ALTER TABLE `evenement_has_tags` DISABLE KEYS */;
INSERT INTO `evenement_has_tags` VALUES (1,1);
/*!40000 ALTER TABLE `evenement_has_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listediffusion`
--

DROP TABLE IF EXISTS `listediffusion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `listediffusion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listediffusion`
--

LOCK TABLES `listediffusion` WRITE;
/*!40000 ALTER TABLE `listediffusion` DISABLE KEYS */;
INSERT INTO `listediffusion` VALUES (1,'IG2I'),(2,'Entreprise');
/*!40000 ALTER TABLE `listediffusion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listediffusion_has_evenement`
--

DROP TABLE IF EXISTS `listediffusion_has_evenement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `listediffusion_has_evenement` (
  `ListeDiffusion_id` int(11) NOT NULL,
  `Evenement_id` int(11) NOT NULL,
  PRIMARY KEY (`ListeDiffusion_id`,`Evenement_id`),
  KEY `fk_ListeDiffusion_has_Evenement_Evenement1_idx` (`Evenement_id`),
  KEY `fk_ListeDiffusion_has_Evenement_ListeDiffusion1_idx` (`ListeDiffusion_id`),
  CONSTRAINT `fk_ListeDiffusion_has_Evenement_Evenement1` FOREIGN KEY (`Evenement_id`) REFERENCES `evenement` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ListeDiffusion_has_Evenement_ListeDiffusion1` FOREIGN KEY (`ListeDiffusion_id`) REFERENCES `listediffusion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listediffusion_has_evenement`
--

LOCK TABLES `listediffusion_has_evenement` WRITE;
/*!40000 ALTER TABLE `listediffusion_has_evenement` DISABLE KEYS */;
INSERT INTO `listediffusion_has_evenement` VALUES (1,1),(2,1),(1,2);
/*!40000 ALTER TABLE `listediffusion_has_evenement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listediffusion_has_user`
--

DROP TABLE IF EXISTS `listediffusion_has_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `listediffusion_has_user` (
  `ListeDiffusion_id` int(11) NOT NULL,
  `User_id` int(11) NOT NULL,
  `notifications` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ListeDiffusion_id`,`User_id`),
  KEY `fk_ListeDiffusion_has_User_User1_idx` (`User_id`),
  KEY `fk_ListeDiffusion_has_User_ListeDiffusion1_idx` (`ListeDiffusion_id`),
  CONSTRAINT `fk_ListeDiffusion_has_User_ListeDiffusion1` FOREIGN KEY (`ListeDiffusion_id`) REFERENCES `listediffusion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ListeDiffusion_has_User_User1` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listediffusion_has_user`
--

LOCK TABLES `listediffusion_has_user` WRITE;
/*!40000 ALTER TABLE `listediffusion_has_user` DISABLE KEYS */;
INSERT INTO `listediffusion_has_user` VALUES (1,1,0);
/*!40000 ALTER TABLE `listediffusion_has_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `titre` varchar(45) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `Evenement_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Notifications_TypeNotification1_idx` (`type`),
  KEY `fk_Notifications_Evenement1_idx` (`Evenement_id`),
  CONSTRAINT `fk_Notifications_Evenement1` FOREIGN KEY (`Evenement_id`) REFERENCES `evenement` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Notifications_TypeNotification1` FOREIGN KEY (`type`) REFERENCES `typenotification` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recherches`
--

DROP TABLE IF EXISTS `recherches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recherches` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(45) DEFAULT NULL,
  `User_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Recherches_User1_idx` (`User_id`),
  CONSTRAINT `fk_Recherches_User1` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recherches`
--

LOCK TABLES `recherches` WRITE;
/*!40000 ALTER TABLE `recherches` DISABLE KEYS */;
INSERT INTO `recherches` VALUES (2,'test recherche',1);
/*!40000 ALTER TABLE `recherches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recherches_has_tags`
--

DROP TABLE IF EXISTS `recherches_has_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recherches_has_tags` (
  `Recherches_id` int(11) NOT NULL,
  `Tags_id` int(11) NOT NULL,
  PRIMARY KEY (`Recherches_id`,`Tags_id`),
  KEY `fk_Recherches_has_Tags_Tags1_idx` (`Tags_id`),
  KEY `fk_Recherches_has_Tags_Recherches1_idx` (`Recherches_id`),
  CONSTRAINT `fk_Recherches_has_Tags_Recherches1` FOREIGN KEY (`Recherches_id`) REFERENCES `recherches` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Recherches_has_Tags_Tags1` FOREIGN KEY (`Tags_id`) REFERENCES `tags` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recherches_has_tags`
--

LOCK TABLES `recherches_has_tags` WRITE;
/*!40000 ALTER TABLE `recherches_has_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `recherches_has_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'Ingenieur');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `typenotification`
--

DROP TABLE IF EXISTS `typenotification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `typenotification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `typenotification`
--

LOCK TABLES `typenotification` WRITE;
/*!40000 ALTER TABLE `typenotification` DISABLE KEYS */;
/*!40000 ALTER TABLE `typenotification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `nom` varchar(45) DEFAULT NULL,
  `prenom` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin','admin@test.fr','Devos','Clement','0123456789'),(2,'admin2','admin2','admin2@test.fr','Theve','Alexandre','0123456789'),(42,'admin3','admin',NULL,NULL,NULL,NULL),(43,'admin3','admin',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_evenement`
--

DROP TABLE IF EXISTS `user_has_evenement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_evenement` (
  `User_id` int(11) NOT NULL,
  `Evenement_id` int(11) NOT NULL,
  `notifications` tinyint(1) DEFAULT '0',
  `code` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`User_id`,`Evenement_id`),
  KEY `fk_User_has_Evenement_Evenement1_idx` (`Evenement_id`),
  KEY `fk_User_has_Evenement_User1_idx` (`User_id`),
  CONSTRAINT `fk_User_has_Evenement_Evenement1` FOREIGN KEY (`Evenement_id`) REFERENCES `evenement` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Evenement_User1` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_evenement`
--

LOCK TABLES `user_has_evenement` WRITE;
/*!40000 ALTER TABLE `user_has_evenement` DISABLE KEYS */;
INSERT INTO `user_has_evenement` VALUES (1,1,0,'6598d33f-f96d-4a89-9431-6a06fc7e63e8');
/*!40000 ALTER TABLE `user_has_evenement` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-01-08 15:24:55
