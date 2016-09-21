/*
SQLyog Ultimate v9.10 
MySQL - 5.5.5-10.0.26-MariaDB : Database - okbr
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`okbr` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `okbr`;

/*Table structure for table `algorithmnames` */

DROP TABLE IF EXISTS `algorithmnames`;

CREATE TABLE `algorithmnames` (
  `id` tinyint(1) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `algorithmnames` */

/*Table structure for table `claims` */

DROP TABLE IF EXISTS `claims`;

CREATE TABLE `claims` (
  `id` int(10) unsigned NOT NULL,
  `value` varchar(255) NOT NULL,
  `snakType` enum('value','novalue','somevalue') DEFAULT 'value',
  `qualifiers` text,
  `userid` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `claims` */

insert  into `claims`(`id`,`value`,`snakType`,`qualifiers`,`userid`) values (1,'31','value','',190),(2,'41','value','',190),(3,'239','value','',190),(4,'134','value','',190),(5,'47','value','',190),(6,'50','value','',2),(7,'near O Porrino in north-western Spain.','value','',3),(8,'Galicia in north-western Spain','value','',3),(9,'1321560','value','',4),(10,'1.2 million','value','',3),(11,'330000','value','',5),(12,'292540','value','',4),(13,'27','value','',4),(14,'20','value','',2),(15,'303','value','',1),(16,'100','value','',1),(17,'Police encounter','value','',2),(18,'Suicide','value','',2),(19,'Suicide ','value','',2),(20,'Natural death ','value','',2),(21,'Germany','value','',2),(22,'Argentina','value','',2),(23,'yes','value','',3),(24,'no','value','',3),(25,'Controlled demolition by Bombs','value','',4),(26,'Collision of two hijacked planes int the towers','value','',4),(27,'Transmission from bloods of chimpanzie','value','',4),(28,'Human created.','value','',4),(29,'Murdered','value','',5),(30,'Car accident','value','',5),(31,'342','value','',3),(32,'342','value','',3),(33,'342','value','',3),(34,'342','value','',3),(35,'342','value','',3),(36,'230','value','',0),(37,'230','value','',0),(87,'342','value','',3),(88,'342','value','',3),(89,'342','value','',3);

/*Table structure for table `evaluationrank` */

DROP TABLE IF EXISTS `evaluationrank`;

CREATE TABLE `evaluationrank` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `claimId` int(10) unsigned NOT NULL,
  `label` enum('Preferred','Deprecated','Normal') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

/*Data for the table `evaluationrank` */

insert  into `evaluationrank`(`id`,`claimId`,`label`) values (1,2,'Preferred'),(2,3,'Preferred'),(3,5,'Preferred'),(4,7,'Preferred'),(5,9,'Preferred'),(6,12,'Preferred'),(7,13,'Preferred'),(8,15,'Preferred'),(9,17,'Preferred'),(10,19,'Preferred'),(11,21,'Preferred'),(12,24,'Preferred'),(13,26,'Preferred'),(14,27,'Preferred'),(15,30,'Preferred');

/*Table structure for table `evaluationresults` */

DROP TABLE IF EXISTS `evaluationresults`;

CREATE TABLE `evaluationresults` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `algoId` int(10) unsigned NOT NULL,
  `precision` double NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `evaluationresults` */

insert  into `evaluationresults`(`id`,`algoId`,`precision`,`created_at`) values (2,2,0.7333333333333333,'2016-09-17 01:18:52'),(3,1,0.4,'2016-09-17 01:18:52'),(4,3,0.6,'2016-09-18 22:47:17'),(7,4,0.7333333333333333,'2016-09-19 01:46:40'),(9,5,0.6,'2016-09-19 01:56:47');

/*Table structure for table `events` */

DROP TABLE IF EXISTS `events`;

CREATE TABLE `events` (
  `eventId` int(10) unsigned NOT NULL,
  `label` varchar(255) DEFAULT NULL,
  `categories` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`eventId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `events` */

insert  into `events`(`eventId`,`label`,`categories`,`location`,`created_at`) values (25987,'Death of Hitler','Violent Incident','Germany','2016-09-16 23:49:21'),(25989,'Moon Landing','Scientific event','United states','2016-09-16 23:49:21'),(26080,'9/11 attacks on twin tours ','terrorist attack','United states','2016-09-16 23:49:21'),(26081,'Acquired Immuno Deficiency Syndrome(AIDS)','Disaster','worldwide','2016-09-16 23:49:21'),(27081,'Death of princess diana','Accident','worldwide','2016-09-16 23:49:21'),(99999,'','','','2016-09-19 13:50:38'),(258976,'Killing of pable Escobar','Violent Incident','Columbia','2016-09-16 23:49:21'),(258977,'Killing of pable Escobar','Violent Incident','Columbia','2016-09-16 23:49:21'),(20857240,'European migrant crisis','Disaster','Worldwide','2016-09-16 23:49:21'),(23892541,'2016 French Open','','France','2016-09-19 13:58:36'),(25007917,'2016 Istanbul airport attack','terrorist attack','Turkey','2016-09-16 23:49:20'),(25007918,'2016 Spain train crash','Accident','Spain','2016-09-16 23:49:20'),(25893254,'2016 attack in Nice','terrorist attack','France','2016-09-16 23:49:21'),(25980145,'2016 Munich shooting','terrorist attack','Germany','2016-09-16 23:49:21');

/*Table structure for table `eventstatementclaim` */

DROP TABLE IF EXISTS `eventstatementclaim`;

CREATE TABLE `eventstatementclaim` (
  `eventId` int(11) unsigned NOT NULL,
  `statementId` int(11) unsigned NOT NULL,
  `claimId` int(11) unsigned NOT NULL,
  `sourceFactId` int(11) unsigned NOT NULL,
  PRIMARY KEY (`eventId`,`statementId`,`claimId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `eventstatementclaim` */

insert  into `eventstatementclaim`(`eventId`,`statementId`,`claimId`,`sourceFactId`) values (25987,15,20,20),(25987,16,22,22),(25989,17,23,23),(25989,17,24,24),(26080,18,25,25),(26080,18,26,26),(26081,19,27,27),(26081,19,28,28),(27081,20,29,29),(27081,20,30,30),(99999,10,25,33),(99999,10,26,34),(99999,10,27,35),(258976,14,17,17),(258977,14,18,18),(258977,15,19,19),(258977,16,21,21),(20857240,10,9,9),(20857240,10,10,10),(20857240,11,11,11),(20857240,11,12,12),(23892541,1,2,31),(23892541,1,3,32),(25007917,6,1,1),(25007917,6,2,2),(25007917,7,3,3),(25007917,7,4,4),(25007917,8,19,36),(25007917,8,20,37),(25007917,8,21,38),(25007917,8,28,39),(25007917,8,29,40),(25007917,8,30,41),(25007917,8,31,42),(25007917,8,32,43),(25007917,8,33,44),(25007917,8,34,45),(25007917,8,35,46),(25007917,8,36,47),(25007917,8,37,48),(25007918,8,5,5),(25007918,8,6,6),(25007918,9,7,7),(25007918,9,8,8),(25893254,13,15,15),(25893254,13,16,16),(25980145,12,13,13),(25980145,12,14,14);

/*Table structure for table `factranks` */

DROP TABLE IF EXISTS `factranks`;

CREATE TABLE `factranks` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `claimId` int(10) unsigned NOT NULL,
  `algoId` tinyint(1) unsigned NOT NULL,
  `label` enum('Preferred','Deprecated','Normal') NOT NULL DEFAULT 'Normal',
  `probabilityRank` double DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=907 DEFAULT CHARSET=latin1;

/*Data for the table `factranks` */

insert  into `factranks`(`id`,`claimId`,`algoId`,`label`,`probabilityRank`,`created_at`) values (31,2,2,'Preferred',1,'2016-09-16 18:19:33'),(32,1,2,'Deprecated',0.5,'2016-09-16 18:19:33'),(33,3,2,'Preferred',1,'2016-09-16 18:19:33'),(34,4,2,'Deprecated',0.5,'2016-09-16 18:19:33'),(35,5,2,'Preferred',1,'2016-09-16 18:19:34'),(36,6,2,'Deprecated',0.5,'2016-09-16 18:19:34'),(37,7,2,'Preferred',1,'2016-09-16 18:19:34'),(38,8,2,'Deprecated',0.5,'2016-09-16 18:19:34'),(39,9,2,'Preferred',1,'2016-09-16 18:19:34'),(40,10,2,'Deprecated',0.5,'2016-09-16 18:19:34'),(41,12,2,'Preferred',1,'2016-09-16 18:19:34'),(42,11,2,'Deprecated',0.5,'2016-09-16 18:19:34'),(43,13,2,'Preferred',1,'2016-09-16 18:19:34'),(44,14,2,'Deprecated',0.5,'2016-09-16 18:19:34'),(45,15,2,'Preferred',1,'2016-09-16 18:19:34'),(46,16,2,'Deprecated',0.5,'2016-09-16 18:19:34'),(47,18,2,'Preferred',1,'2016-09-16 18:19:34'),(48,17,2,'Deprecated',0.5,'2016-09-16 18:19:34'),(49,19,2,'Preferred',1,'2016-09-16 18:19:34'),(50,20,2,'Deprecated',0.5,'2016-09-16 18:19:34'),(51,21,2,'Preferred',1,'2016-09-16 18:19:34'),(52,22,2,'Deprecated',0.5,'2016-09-16 18:19:34'),(53,23,2,'Preferred',1,'2016-09-16 18:19:34'),(54,24,2,'Deprecated',0.5,'2016-09-16 18:19:34'),(55,25,2,'Preferred',1,'2016-09-16 18:19:34'),(56,26,2,'Deprecated',0.5,'2016-09-16 18:19:34'),(57,27,2,'Preferred',1,'2016-09-16 18:19:35'),(58,28,2,'Deprecated',0.5,'2016-09-16 18:19:35'),(59,29,2,'Preferred',1,'2016-09-16 18:19:35'),(60,30,2,'Deprecated',0.5,'2016-09-16 18:19:35'),(61,1,1,'Preferred',1,'2016-09-16 18:19:35'),(62,2,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(63,3,1,'Preferred',1,'2016-09-16 18:19:35'),(64,4,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(65,5,1,'Preferred',1,'2016-09-16 18:19:35'),(66,6,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(67,8,1,'Preferred',1,'2016-09-16 18:19:35'),(68,7,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(69,10,1,'Preferred',1,'2016-09-16 18:19:35'),(70,9,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(71,11,1,'Preferred',1,'2016-09-16 18:19:35'),(72,12,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(73,13,1,'Preferred',1,'2016-09-16 18:19:35'),(74,14,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(75,15,1,'Preferred',1,'2016-09-16 18:19:35'),(76,16,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(77,17,1,'Preferred',1,'2016-09-16 18:19:35'),(78,18,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(79,20,1,'Preferred',1,'2016-09-16 18:19:35'),(80,19,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(81,22,1,'Preferred',1,'2016-09-16 18:19:35'),(82,21,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(83,23,1,'Preferred',1,'2016-09-16 18:19:35'),(84,24,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(85,25,1,'Preferred',1,'2016-09-16 18:19:35'),(86,26,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(87,27,1,'Preferred',1,'2016-09-16 18:19:35'),(88,28,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(89,29,1,'Preferred',1,'2016-09-16 18:19:35'),(90,30,1,'Deprecated',0.5,'2016-09-16 18:19:35'),(211,1,1,'Preferred',1,'2016-09-17 11:50:51'),(212,2,1,'Deprecated',0.5,'2016-09-17 11:50:52'),(213,3,1,'Preferred',1,'2016-09-17 11:50:52'),(214,4,1,'Deprecated',0.5,'2016-09-17 11:50:52'),(215,5,1,'Preferred',1,'2016-09-17 11:50:52'),(216,6,1,'Deprecated',0.5,'2016-09-17 11:50:52'),(217,8,1,'Preferred',1,'2016-09-17 11:50:52'),(218,7,1,'Deprecated',0.5,'2016-09-17 11:50:52'),(219,10,1,'Preferred',1,'2016-09-17 11:50:53'),(220,9,1,'Deprecated',0.5,'2016-09-17 11:50:53'),(221,11,1,'Preferred',1,'2016-09-17 11:50:53'),(222,12,1,'Deprecated',0.5,'2016-09-17 11:50:53'),(223,13,1,'Preferred',1,'2016-09-17 11:50:53'),(224,14,1,'Deprecated',0.5,'2016-09-17 11:50:53'),(225,15,1,'Preferred',1,'2016-09-17 11:50:53'),(226,16,1,'Deprecated',0.5,'2016-09-17 11:50:54'),(227,17,1,'Preferred',1,'2016-09-17 11:50:54'),(228,18,1,'Deprecated',0.5,'2016-09-17 11:50:54'),(229,20,1,'Preferred',1,'2016-09-17 11:50:54'),(230,19,1,'Deprecated',0.5,'2016-09-17 11:50:54'),(231,22,1,'Preferred',1,'2016-09-17 11:50:54'),(232,21,1,'Deprecated',0.5,'2016-09-17 11:50:55'),(233,23,1,'Preferred',1,'2016-09-17 11:50:55'),(234,24,1,'Deprecated',0.5,'2016-09-17 11:50:55'),(235,25,1,'Preferred',1,'2016-09-17 11:50:55'),(236,26,1,'Deprecated',0.5,'2016-09-17 11:50:55'),(237,27,1,'Preferred',1,'2016-09-17 11:50:55'),(238,28,1,'Deprecated',0.5,'2016-09-17 11:50:55'),(239,29,1,'Preferred',1,'2016-09-17 11:50:56'),(240,30,1,'Deprecated',0.5,'2016-09-17 11:50:56'),(666,1,3,'Preferred',0.5,'2016-09-18 22:25:19'),(667,2,3,'Deprecated',0.5,'2016-09-18 22:25:19'),(668,3,3,'Preferred',0.5,'2016-09-18 22:25:19'),(669,4,3,'Deprecated',0.5,'2016-09-18 22:25:19'),(670,5,3,'Preferred',0.5,'2016-09-18 22:25:19'),(671,6,3,'Deprecated',0.5,'2016-09-18 22:25:19'),(672,7,3,'Preferred',0.5,'2016-09-18 22:25:19'),(673,8,3,'Deprecated',0.5,'2016-09-18 22:25:19'),(674,9,3,'Preferred',0.5,'2016-09-18 22:25:19'),(675,10,3,'Deprecated',0.5,'2016-09-18 22:25:19'),(676,11,3,'Preferred',0.5,'2016-09-18 22:25:19'),(677,12,3,'Deprecated',0.5,'2016-09-18 22:25:19'),(678,13,3,'Preferred',0.8058541645409356,'2016-09-18 22:25:19'),(679,14,3,'Deprecated',0.8058541645409356,'2016-09-18 22:25:19'),(680,15,3,'Preferred',0.5,'2016-09-18 22:25:19'),(681,16,3,'Deprecated',0.5,'2016-09-18 22:25:19'),(682,17,3,'Preferred',0.502249626268727,'2016-09-18 22:25:19'),(683,18,3,'Deprecated',0.5016924489740799,'2016-09-18 22:25:19'),(684,19,3,'Preferred',0.5,'2016-09-18 22:25:20'),(685,20,3,'Deprecated',0.5,'2016-09-18 22:25:20'),(686,21,3,'Preferred',0.5,'2016-09-18 22:25:20'),(687,22,3,'Deprecated',0.5,'2016-09-18 22:25:20'),(688,23,3,'Preferred',0.5047482065622119,'2016-09-18 22:25:20'),(689,24,3,'Deprecated',0.502249626268727,'2016-09-18 22:25:20'),(690,25,3,'Preferred',0.5002967978139157,'2016-09-18 22:25:20'),(691,26,3,'Deprecated',0.5002967978139157,'2016-09-18 22:25:20'),(692,27,3,'Deprecated',0.5003082232698894,'2016-09-18 22:25:20'),(693,28,3,'Preferred',0.502249626268727,'2016-09-18 22:25:20'),(694,29,3,'Preferred',0.5975243867394544,'2016-09-18 22:25:20'),(695,30,3,'Deprecated',0.5975243867394544,'2016-09-18 22:25:20'),(817,1,4,'Deprecated',0.4300422589320016,'2016-09-19 01:46:14'),(818,2,4,'Preferred',0.3021897810218978,'2016-09-19 01:46:14'),(819,3,4,'Preferred',0.29099756690997564,'2016-09-19 01:46:14'),(820,4,4,'Deprecated',0.4226277372262774,'2016-09-19 01:46:14'),(821,5,4,'Preferred',0.4364963503649635,'2016-09-19 01:46:14'),(822,6,4,'Deprecated',0.4226277372262774,'2016-09-19 01:46:14'),(823,7,4,'Preferred',0.29099756690997564,'2016-09-19 01:46:14'),(824,8,4,'Deprecated',0.5635036496350365,'2016-09-19 01:46:14'),(825,9,4,'Preferred',0.3741397288842544,'2016-09-19 01:46:14'),(826,10,4,'Deprecated',0.39914841849148425,'2016-09-19 01:46:14'),(827,11,4,'Deprecated',0.39914841849148425,'2016-09-19 01:46:14'),(828,12,4,'Preferred',0.3394971613949716,'2016-09-19 01:46:14'),(829,13,4,'Preferred',0.3394971613949716,'2016-09-19 01:46:15'),(830,14,4,'Deprecated',0.4098208360982084,'2016-09-19 01:46:15'),(831,15,4,'Preferred',0.2756819054936611,'2016-09-19 01:46:15'),(832,16,4,'Deprecated',0.39914841849148425,'2016-09-19 01:46:15'),(833,17,4,'Preferred',0.2656934306569343,'2016-09-19 01:46:15'),(834,18,4,'Deprecated',0.4830031282586027,'2016-09-19 01:46:15'),(835,19,4,'Preferred',0.2824388149420352,'2016-09-19 01:46:15'),(836,20,4,'Deprecated',0.5165450121654501,'2016-09-19 01:46:15'),(837,21,4,'Preferred',0.3861313868613138,'2016-09-19 01:46:15'),(838,22,4,'Deprecated',0.39914841849148425,'2016-09-19 01:46:15'),(839,23,4,'Preferred',0.3741397288842544,'2016-09-19 01:46:15'),(840,24,4,'Deprecated',0.4098208360982084,'2016-09-19 01:46:15'),(841,25,4,'Preferred',0.4166556071665561,'2016-09-19 01:46:15'),(842,26,4,'Deprecated',0.4098208360982084,'2016-09-19 01:46:15'),(843,27,4,'Deprecated',0.38610435252771025,'2016-09-19 01:46:15'),(844,28,4,'Preferred',0.4001216545012165,'2016-09-19 01:46:15'),(845,29,4,'Preferred',0.35465328467153284,'2016-09-19 01:46:15'),(846,30,4,'Deprecated',0.4159193604449079,'2016-09-19 01:46:15'),(877,1,5,'Preferred',0,'2016-09-19 01:56:09'),(878,2,5,'Deprecated',0,'2016-09-19 01:56:09'),(879,3,5,'Preferred',6,'2016-09-19 01:56:09'),(880,4,5,'Deprecated',-6,'2016-09-19 01:56:09'),(881,5,5,'Preferred',6,'2016-09-19 01:56:09'),(882,6,5,'Deprecated',-6,'2016-09-19 01:56:09'),(883,7,5,'Preferred',4,'2016-09-19 01:56:09'),(884,8,5,'Deprecated',-4,'2016-09-19 01:56:09'),(885,9,5,'Preferred',4,'2016-09-19 01:56:09'),(886,10,5,'Deprecated',-4,'2016-09-19 01:56:09'),(887,11,5,'Preferred',0,'2016-09-19 01:56:09'),(888,12,5,'Deprecated',0,'2016-09-19 01:56:09'),(889,13,5,'Preferred',6,'2016-09-19 01:56:10'),(890,14,5,'Deprecated',-6,'2016-09-19 01:56:10'),(891,15,5,'Preferred',6,'2016-09-19 01:56:10'),(892,16,5,'Deprecated',-6,'2016-09-19 01:56:10'),(893,17,5,'Preferred',6,'2016-09-19 01:56:10'),(894,18,5,'Deprecated',-6,'2016-09-19 01:56:10'),(895,19,5,'Preferred',4,'2016-09-19 01:56:10'),(896,20,5,'Deprecated',-4,'2016-09-19 01:56:10'),(897,21,5,'Preferred',4,'2016-09-19 01:56:10'),(898,22,5,'Deprecated',-4,'2016-09-19 01:56:10'),(899,23,5,'Preferred',6,'2016-09-19 01:56:10'),(900,24,5,'Deprecated',-6,'2016-09-19 01:56:10'),(901,25,5,'Preferred',6,'2016-09-19 01:56:10'),(902,26,5,'Deprecated',-6,'2016-09-19 01:56:10'),(903,27,5,'Deprecated',-4,'2016-09-19 01:56:11'),(904,28,5,'Preferred',4,'2016-09-19 01:56:11'),(905,29,5,'Preferred',6,'2016-09-19 01:56:11'),(906,30,5,'Deprecated',-6,'2016-09-19 01:56:11');

/*Table structure for table `references` */

DROP TABLE IF EXISTS `references`;

CREATE TABLE `references` (
  `id` int(10) unsigned NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `publicationDate` varchar(255) DEFAULT NULL,
  `retreivalDate` varchar(255) DEFAULT NULL,
  `authors` varchar(255) DEFAULT NULL,
  `trustRating` float DEFAULT NULL,
  `articleType` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `neutralityRating` float DEFAULT NULL,
  `claimId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `references` */

insert  into `references`(`id`,`url`,`publicationDate`,`retreivalDate`,`authors`,`trustRating`,`articleType`,`title`,`neutralityRating`,`claimId`) values (1,'http://bbc.com','05-06-2016','06-06-2016','',0.9,'','Djokovic win 2016 French open',0,2),(2,'http://fox.com','2016-07-01','2016-07-06','',0.1,'sports','Murray beats Djokovic',0,3),(18,'https://www.thesun.co.uk/news/1359442/shocking-video-shows-moment-isis-suicide-bomb-blast-rips-through-istanbul-airport/','29-06-2016','29-06-2016','Laura Clark',0.8,'','Several dead in Istanbul airport attack',0,1),(19,'http://www.aljazeera.com/news/2016/06/turkey-istanbul-ataturk-airport-attack-160629010240861.html','29-06-2016','29-06-2016','Laura Clark',0.8,'','Several dead in Istanbul airport attack',0,2),(20,'http://www.aljazeera.com/news/2016/06/turkey-istanbul-ataturk-airport-attack-160629010240861.html','29-06-2016','29-06-2016','Laura Clark',0.8,'','Several dead in Istanbul airport attack',0,3),(21,'https://www.thesun.co.uk/news/1359442/shocking-video-shows-moment-isis-suicide-bomb-blast-rips-through-istanbul-airport/','29-06-2016','29-06-2016','Laura Clark',0.8,'','Several dead in Istanbul airport attack',0,4),(22,'http://www.bbc.com/news/world-europe-37315566','09-09-2016','09-09-2016','[]',0.8,'',' ',0,5),(23,'http://www.rte.ie/news/2016/0909/815313-spain-train-crash/','09-09-2016','09-10-2016','[]',0.8,'',' ',0,6),(24,'http://www.bbc.com/news/world-europe-37315566','09-10-2016','09-11-2016','[]',0.8,'',' ',0,7),(25,'http://www.rte.ie/news/2016/0909/815313-spain-train-crash/','09-11-2016','09-12-2016','[]',0.8,'',' ',0,8),(26,'http://www.bbc.com/news/world-europe-34131911','04-03-2016','09-09-2016','[]',0.8,'',' ',0,9),(27,'https://www.thesun.co.uk/archives/politics/1073918/asylum-suckers-only-1-in-3-refugees-out-of-1-2million-are-fleeing-isis/','05-03-2016','09-09-2016','[]',0.8,'',' ',0,10),(28,'https://www.thesun.co.uk/archives/politics/1137753/330000-asylum-seekers-protected-by-eu-last-year-alone-as-scale-of-migrant-crisis-is-revealed/','20-04-2016','09-10-2016','[]',0.8,'',' ',0,11),(29,'http://www.bbc.com/news/world-europe-34131911','04-03-2016','09-11-2016','[]',0.8,'',' ',0,12),(30,'http://www.dw.com/en/munich-attack-germany-reacts/a-19422631','23-07-2016','09-12-2016','[]',0.8,'',' ',0,13),(31,'http://www.thelocal.de/20160722/shots-fores-at-munich-shopping-centre','22-07-2016','09-13-2016','[]',0.8,'',' ',0,14),(32,'http://www.bbc.com/news/world-europe-36801671','19-08-2016','09-14-2016','[]',0.8,'',' ',0,15),(33,'http://www.aljazeera.com/news/2016/07/france-truck-plows-crowd-nice-bastille-day-160714214536526.html','15-07-2016','09-15-2016','[]',0.8,'',' ',0,16),(34,'http://www.nytimes.com/1993/12/03/world/head-of-medellin-cocaine-cartel-is-killed-by-troops-in-colombia.html?pagewanted=all','12-03-1993','12-03-1993','[]',0.9,'','killing of pable',0,17),(35,'http://www.insightcrime.org/news-analysis/top-ten-tales-pablo-escobar-book','12-03-1993','12-03-1993','[]',0.9,'','killing of pable',0,18),(36,'http://www.independent.co.uk/news/people/adolf-hitlers-death-70th-anniversary-five-facts-about-the-final-hours-of-the-german-nazi-leader-10215968.html','30-04-2015','30-04-2015','[]',0.9,'',' ',0,19),(37,'http://yournewswire.com/fbi-hitler-didnt-die-fled-to-argentina-stunning-admission/','04-03-2016','04-03-2016','[]',0.9,'',' ',0,20),(38,'http://www.independent.co.uk/news/people/adolf-hitlers-death-70th-anniversary-five-facts-about-the-final-hours-of-the-german-nazi-leader-10215968.html','30-04-2015','30-04-2015','[]',0.9,'',' ',0,21),(39,'http://yournewswire.com/fbi-hitler-didnt-die-fled-to-argentina-stunning-admission/','04-03-2016','04-03-2016','[]',0.9,'',' ',0,22),(40,'http://www.enkivillage.com/moon-landing-hoax.html','01-04-2016','01-04-2016','[]',0.9,'',' ',0,23),(41,'http://www.nytimes.com/2009/07/14/science/space/14hoax.html?_r=0','13-07-2009','13-07-2009','[]',0.9,'',' ',0,24),(42,'https://www.libertariannews.org/2013/07/28/911-firefighters-reveal-bombs-destroyed-wtc-lobby/','28-07-2013','28-07-2013','[]',0.9,'',' ',0,25),(43,'http://www.bbc.co.uk/newsround/14854813','09-09-2011','09-09-2011','[]',0.9,'',' ',0,26),(44,'http://www.theaidsinstitute.org/node/259','01-07-2016','01-07-2016','[]',0.9,'',' ',0,27),(45,'http://www.nytimes.com/1999/02/01/us/hiv-is-linked-to-a-subspecies-of-chimpanzee.html?mtrref=www.nytimes.com&gwh=75E34B34A10A2FD9E08AD5BD324A292A&gwt=pay','01-02-1999','01-02-1999','[]',0.9,'',' ',0,28),(46,'https://atruthsoldier.com/princess-diana-was-murdered/','00-00-0000','00-00-0000','[]',0.9,'',' ',0,29),(47,'http://news.bbc.co.uk/2/mobile/uk_news/7335453.stm','00-00-0000','00-00-0000','[]',0.9,'',' ',0,30),(48,'http://fox.com','2016-06-28','2016-06-29','',0.2,'','Attack on Istanbul airport',0,19),(49,'http://al-jezeera.com','2016-06-28','2016-06-28','',0.7,'','Sucide bombing in Istanbul',0,20),(50,'http://dw.com','2016-06-28','2016-06-29','',0.9,'','Many injured in Istanbul attack',0,21),(54,'omsegdr','1062-12-01','1345-10-01','',0.1,'','',0,25),(55,'omsegdr','1062-12-01','1345-10-01','',0.1,'','',0,26),(56,'omsegdr','1062-12-01','1345-10-01','',0.1,'','',0,27),(57,'http://www.bbc.com/news/world-europe-36658187','2016-06-29','2016-09-19','',1,'','',0,28),(58,'http://www.bbc.com/news/world-europe-36658187','2016-06-29','2016-09-19','',1,'','',0,29),(59,'http://www.bbc.com/news/world-europe-36658187','2016-06-29','2016-09-19','',1,'','',0,30),(60,'http://www.bbc.com/news/world-europe-36658187','2016-06-29','2016-09-19','',1,'','',0,31),(61,'http://www.bbc.com/news/world-europe-36658187','2016-06-29','2016-09-19','',1,'','',0,32),(62,'http://www.bbc.com/news/world-europe-36658187','2016-07-29','2016-09-19','',1,'','',0,33),(63,'http://www.bbc.com/news/world-europe-36658187','2016-06-29','2016-09-19','',1,'','',0,34),(64,'http://www.bbc.com/news/world-europe-36658187','2016-06-29','2016-09-19','',1,'','',0,35),(65,'http://www.bbc.com/news/world-europe-36658187','2016-06-29','2016-09-19','',1,'','',0,36),(66,'Demo video for the Open Knowledge Base project and research practical at the University Koblenz. The video shows the process of claim curation and the results of the ranking process.','2016-06-29','2016-09-19','',1,'','',0,37);

/*Table structure for table `sourcefact` */

DROP TABLE IF EXISTS `sourcefact`;

CREATE TABLE `sourcefact` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `source` varchar(255) DEFAULT NULL,
  `fact` varchar(255) DEFAULT NULL,
  `statementId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;

/*Data for the table `sourcefact` */

insert  into `sourcefact`(`id`,`source`,`fact`,`statementId`) values (1,'https://www.thesun.co.uk/news/1359442/shocking-video-shows-moment-isis-suicide-bomb-blast-rips-through-istanbul-airport/','31',6),(2,'http://www.aljazeera.com/news/2016/06/turkey-istanbul-ataturk-airport-attack-160629010240861.html','41',6),(3,'http://www.aljazeera.com/news/2016/06/turkey-istanbul-ataturk-airport-attack-160629010240861.html','239',7),(4,'https://www.thesun.co.uk/news/1359442/shocking-video-shows-moment-isis-suicide-bomb-blast-rips-through-istanbul-airport/','134',7),(5,'http://www.bbc.com/news/world-europe-37315566','47',8),(6,'http://www.rte.ie/news/2016/0909/815313-spain-train-crash/','50',8),(7,'http://www.bbc.com/news/world-europe-37315566','near O Porrino in north-western Spain.',9),(8,'http://www.rte.ie/news/2016/0909/815313-spain-train-crash/','Galicia in north-western Spain',9),(9,'http://www.bbc.com/news/world-europe-34131911','1321560',10),(10,'https://www.thesun.co.uk/archives/politics/1073918/asylum-suckers-only-1-in-3-refugees-out-of-1-2million-are-fleeing-isis/','1.2 million',10),(11,'https://www.thesun.co.uk/archives/politics/1137753/330000-asylum-seekers-protected-by-eu-last-year-alone-as-scale-of-migrant-crisis-is-revealed/','330000',11),(12,'http://www.bbc.com/news/world-europe-34131911','292540',11),(13,'http://www.dw.com/en/munich-attack-germany-reacts/a-19422631','27',12),(14,'http://www.thelocal.de/20160722/shots-fores-at-munich-shopping-centre','20',12),(15,'http://www.bbc.com/news/world-europe-36801671','303',13),(16,'http://www.aljazeera.com/news/2016/07/france-truck-plows-crowd-nice-bastille-day-160714214536526.html','100',13),(17,'http://www.nytimes.com/1993/12/03/world/head-of-medellin-cocaine-cartel-is-killed-by-troops-in-colombia.html?pagewanted=all','Police encounter',14),(18,'http://www.insightcrime.org/news-analysis/top-ten-tales-pablo-escobar-book','Suicide',14),(19,'http://www.independent.co.uk/news/people/adolf-hitlers-death-70th-anniversary-five-facts-about-the-final-hours-of-the-german-nazi-leader-10215968.html','Suicide ',15),(20,'http://yournewswire.com/fbi-hitler-didnt-die-fled-to-argentina-stunning-admission/','Natural death ',15),(21,'http://www.independent.co.uk/news/people/adolf-hitlers-death-70th-anniversary-five-facts-about-the-final-hours-of-the-german-nazi-leader-10215968.html','Germany',16),(22,'http://yournewswire.com/fbi-hitler-didnt-die-fled-to-argentina-stunning-admission/','Argentina',16),(23,'http://www.enkivillage.com/moon-landing-hoax.html','yes',17),(24,'http://www.nytimes.com/2009/07/14/science/space/14hoax.html?_r=0','no',17),(25,'https://www.libertariannews.org/2013/07/28/911-firefighters-reveal-bombs-destroyed-wtc-lobby/','Controlled demolition by Bombs',18),(26,'http://www.bbc.co.uk/newsround/14854813','Collision of two hijacked planes int the towers',18),(27,'http://www.theaidsinstitute.org/node/259','Transmission from bloods of chimpanzie',19),(28,'http://www.nytimes.com/1999/02/01/us/hiv-is-linked-to-a-subspecies-of-chimpanzee.html?mtrref=www.nytimes.com&gwh=75E34B34A10A2FD9E08AD5BD324A292A&gwt=pay','Human created.',19),(29,'https://atruthsoldier.com/princess-diana-was-murdered/','Murdered',20),(30,'http://news.bbc.co.uk/2/mobile/uk_news/7335453.stm','Car accident',20),(31,'http://bbc.com','Novak Djokovic',1),(32,'http://fox.com','Andy Murray',1),(33,'omsegdr','342',10),(34,'omsegdr','342',10),(35,'omsegdr','342',10),(36,'http://fox.com','150',8),(37,'http://al-jezeera.com','120',8),(38,'http://dw.com','147',8),(39,'http://www.bbc.com/news/world-europe-36658187','230',8),(40,'http://www.bbc.com/news/world-europe-36658187','230',8),(41,'http://www.bbc.com/news/world-europe-36658187','230',8),(42,'http://www.bbc.com/news/world-europe-36658187','230',8),(43,'http://www.bbc.com/news/world-europe-36658187','230',8),(44,'http://www.bbc.com/news/world-europe-36658187','230',8),(45,'http://www.bbc.com/news/world-europe-36658187','230',8),(46,'http://www.bbc.com/news/world-europe-36658187','230',8),(47,'http://www.bbc.com/news/world-europe-36658187','230',8),(48,'Demo video for the Open Knowledge Base project and research practical at the University Koblenz. The video shows the process of claim curation and the results of the ranking process.','230',8);

/*Table structure for table `statements` */

DROP TABLE IF EXISTS `statements`;

CREATE TABLE `statements` (
  `id` int(10) unsigned NOT NULL,
  `propertyId` int(10) unsigned NOT NULL,
  `label` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `statements` */

insert  into `statements`(`id`,`propertyId`,`label`,`created_at`) values (1,1346,'winner','2016-09-19 13:58:36'),(6,1120,'number of people dead','2016-09-16 23:49:20'),(7,1339,'number of injured','2016-09-16 23:49:20'),(8,1339,'number of injured','2016-09-16 23:49:20'),(9,276,'Place of accident','2016-09-16 23:49:21'),(10,0,'Total no of applicatin in Eu','2016-09-16 23:49:21'),(11,0,'Total asylum aplication aproved by EU','2016-09-16 23:49:21'),(12,1339,'number of injured','2016-09-16 23:49:21'),(13,1339,'number of injured','2016-09-16 23:49:21'),(14,509,'Cause of death','2016-09-16 23:49:21'),(15,509,'Cause of death','2016-09-16 23:49:21'),(16,20,'Place of death','2016-09-16 23:49:21'),(17,1342,'Was it a hoax','2016-09-16 23:49:21'),(18,3423,'What led to the collapse of building.','2016-09-16 23:49:21'),(19,3244,'Origin of Human immuno defieciency virus causing IADS','2016-09-16 23:49:21'),(20,509,'Cause of death','2016-09-16 23:49:21');

/*Table structure for table `totalvotecounts` */

DROP TABLE IF EXISTS `totalvotecounts`;

CREATE TABLE `totalvotecounts` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `totalPreferredCount` int(11) unsigned NOT NULL DEFAULT '0',
  `totalDeprecatedCount` int(11) unsigned NOT NULL DEFAULT '0',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `totalvotecounts` */

insert  into `totalvotecounts`(`id`,`totalPreferredCount`,`totalDeprecatedCount`,`modified_date`) values (1,598,772,'2016-09-19 01:45:59');

/*Table structure for table `truthfinder` */

DROP TABLE IF EXISTS `truthfinder`;

CREATE TABLE `truthfinder` (
  `id` int(10) unsigned NOT NULL,
  `sourceId` int(10) unsigned NOT NULL,
  `factId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `truthfinder` */

/*Table structure for table `uservotes` */

DROP TABLE IF EXISTS `uservotes`;

CREATE TABLE `uservotes` (
  `fact_id` int(10) unsigned NOT NULL,
  `preferred_count` int(1) unsigned NOT NULL,
  `deprecated_count` int(1) unsigned NOT NULL,
  `modified_at` datetime DEFAULT NULL,
  PRIMARY KEY (`fact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `uservotes` */

insert  into `uservotes`(`fact_id`,`preferred_count`,`deprecated_count`,`modified_at`) values (1,9,29,'2016-09-17 01:15:46'),(2,18,8,'2016-09-17 01:04:12'),(3,20,10,'2016-09-17 01:05:09'),(4,10,30,'2016-09-17 01:16:06'),(5,10,0,'2016-09-17 01:05:12'),(6,10,30,'2016-09-17 01:16:10'),(7,20,10,'2016-09-17 01:05:17'),(8,0,20,'2016-09-17 01:16:18'),(9,12,2,'2016-09-17 01:05:22'),(10,14,34,'2016-09-17 01:16:40'),(11,14,34,'2016-09-17 01:16:47'),(12,14,4,'2016-09-17 01:05:25'),(13,14,4,'2016-09-17 01:05:33'),(14,12,32,'2016-09-17 01:16:55'),(15,24,14,'2016-09-17 01:05:37'),(16,14,34,'2016-09-17 01:16:58'),(17,28,18,'2016-09-17 01:05:40'),(18,4,24,'2016-09-17 01:17:03'),(19,22,12,'2016-09-17 01:05:44'),(20,2,22,'2016-09-17 01:17:07'),(21,23,3,'2016-09-17 01:08:51'),(22,14,34,'2016-09-17 01:17:16'),(23,48,8,'2016-09-19 01:43:30'),(24,24,64,'2016-09-19 01:45:59'),(25,42,2,'2016-09-19 01:43:26'),(26,24,64,'2016-09-19 01:45:56'),(27,34,74,'2016-09-19 01:45:52'),(28,44,4,'2016-09-19 01:43:21'),(29,52,12,'2016-09-19 01:42:53'),(30,22,62,'2016-09-19 01:45:43'),(31,0,0,'2016-09-19 13:50:38'),(32,0,0,'2016-09-19 13:50:38'),(33,0,0,'2016-09-19 13:50:38'),(34,0,0,'2016-09-19 13:50:38'),(35,0,0,'2016-09-19 13:50:38'),(36,0,0,'2016-09-19 21:22:28'),(37,0,0,'2016-09-19 21:35:58'),(87,0,0,'2016-09-19 13:50:38'),(88,0,0,'2016-09-19 13:50:38'),(89,0,0,'2016-09-19 13:50:38');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
