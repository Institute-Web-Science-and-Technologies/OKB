/*
SQLyog Ultimate v9.10 
MySQL - 5.6.21 : Database - okb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `claims` */

DROP TABLE IF EXISTS `claims`;

CREATE TABLE `claims` (
  `id` char(36) NOT NULL,
  `eventId` int(11) NOT NULL,
  `propertyId` int(11) DEFAULT NULL,
  `snakType` enum('novalue','value','customvalue') DEFAULT NULL,
  `dataType` int(11) DEFAULT NULL,
  `dataValue` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_claims_dataType` (`dataType`),
  KEY `FK_claims_event` (`eventId`),
  CONSTRAINT `FK_claims_dataType` FOREIGN KEY (`dataType`) REFERENCES `datatypes` (`id`),
  CONSTRAINT `FK_claims_event` FOREIGN KEY (`eventId`) REFERENCES `events` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `claims` */

insert  into `claims`(`id`,`eventId`,`propertyId`,`snakType`,`dataType`,`dataValue`) values ('AFA5B268-0154-422A-8331-298F35364FD5',23890868,1120,'value',4,'{\r\n        \"amount\" : 64,\r\n        \"upperbound\" : 65,\r\n        \"lowerbound\" : 63,\r\n        \"unit\" : 1\r\n    }');

/*Table structure for table `datatypes` */

DROP TABLE IF EXISTS `datatypes`;

CREATE TABLE `datatypes` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `datatypes` */

insert  into `datatypes`(`id`,`name`) values (1,'wikibase-item'),(2,'quantity'),(3,'string'),(4,'globe-coordinate'),(5,'monolingualtext'),(6,'url'),(7,'commonsMedia'),(8,'math'),(9,'time'),(10,'external-identifier'),(11,'item'),(12,'property');

/*Table structure for table `events` */

DROP TABLE IF EXISTS `events`;

CREATE TABLE `events` (
  `id` int(11) NOT NULL,
  `Label` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `events` */

insert  into `events`(`id`,`Label`) values (23890868,'April 2016 Kabul attack');

/*Table structure for table `qualifier` */

DROP TABLE IF EXISTS `qualifier`;

CREATE TABLE `qualifier` (
  `id` int(11) NOT NULL,
  `propertyId` int(11) NOT NULL,
  `snakType` enum('value','novalue','somevalue') NOT NULL,
  `dataType` int(11) NOT NULL,
  `datavalue` text NOT NULL,
  `claimId` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qualifier_dataTypes` (`dataType`),
  KEY `FK_qualifier_claim` (`claimId`),
  CONSTRAINT `FK_qualifier_claim` FOREIGN KEY (`claimId`) REFERENCES `claims` (`id`),
  CONSTRAINT `FK_qualifier_dataTypes` FOREIGN KEY (`dataType`) REFERENCES `datatypes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `qualifier` */

/*Table structure for table `references` */

DROP TABLE IF EXISTS `references`;

CREATE TABLE `references` (
  `id` varchar(255) NOT NULL,
  `propertyId` int(11) NOT NULL,
  `snakType` enum('value','novalue','somevalue') NOT NULL,
  `dataType` int(11) NOT NULL,
  `datavalue` text NOT NULL,
  `claimId` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_references` (`dataType`),
  KEY `FK_references_claim` (`claimId`),
  CONSTRAINT `FK_references` FOREIGN KEY (`dataType`) REFERENCES `datatypes` (`id`),
  CONSTRAINT `FK_references_claim` FOREIGN KEY (`claimId`) REFERENCES `claims` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `references` */

insert  into `references`(`id`,`propertyId`,`snakType`,`dataType`,`datavalue`,`claimId`) values ('346e0cb5cc78d343b5a0625960a1b6a0da48793a',813,'value',9,'{\r\n                        \"time\" : \"+2016-05-16T00:00:00Z\",\r\n                        \"timezone\" : 0,\r\n                        \"before\" : 0,\r\n                        \"after\" : 0,\r\n                        \"precision\" : 11,\r\n                        \"calendarmodel\" : \"http://www.wikidata.org/entity/Q1985727\"\r\n                    }','AFA5B268-0154-422A-8331-298F35364FD5'),('c430585f52da0f1a7b8de849aeca109ac40e8575',854,'value',6,'http://edition.cnn.com/2016/04/19/asia/kabul-explosion/','AFA5B268-0154-422A-8331-298F35364FD5');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
