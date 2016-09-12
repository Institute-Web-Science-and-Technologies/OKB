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

/*Table structure for table `baseline` */

DROP TABLE IF EXISTS `baseline`;

CREATE TABLE `baseline` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `claimid` int(11) unsigned DEFAULT NULL,
  `rank` int(1) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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

/*Table structure for table `evaluationrank` */

DROP TABLE IF EXISTS `evaluationrank`;

CREATE TABLE `evaluationrank` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `claimId` int(10) unsigned NOT NULL,
  `label` enum('Preferred','Deprecated','Normal') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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

/*Table structure for table `eventstatementclaim` */

DROP TABLE IF EXISTS `eventstatementclaim`;

CREATE TABLE `eventstatementclaim` (
  `eventId` int(11) unsigned NOT NULL,
  `statementId` int(11) unsigned NOT NULL,
  `claimId` int(11) unsigned NOT NULL,
  PRIMARY KEY (`eventId`,`statementId`,`claimId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `factranks` */

DROP TABLE IF EXISTS `factranks`;

CREATE TABLE `factranks` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `claimId` int(10) unsigned NOT NULL,
  `algoId` tinyint(1) unsigned NOT NULL,
  `label` enum('Preferred','Deprecated','Normal') NOT NULL DEFAULT 'Normal',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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

/*Table structure for table `sourcefact` */

DROP TABLE IF EXISTS `sourcefact`;

CREATE TABLE `sourcefact` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `source` varchar(255) DEFAULT NULL,
  `fact` varchar(255) DEFAULT NULL,
  `statementId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Table structure for table `statements` */

DROP TABLE IF EXISTS `statements`;

CREATE TABLE `statements` (
  `id` int(10) unsigned NOT NULL,
  `propertyId` int(10) unsigned NOT NULL,
  `label` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `totalvotecounts` */

DROP TABLE IF EXISTS `totalvotecounts`;

CREATE TABLE `totalvotecounts` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `totalPreferredCount` int(11) unsigned NOT NULL DEFAULT '0',
  `totalDeprecatedCount` int(11) unsigned NOT NULL DEFAULT '0',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `truthfinder` */

DROP TABLE IF EXISTS `truthfinder`;

CREATE TABLE `truthfinder` (
  `id` int(10) unsigned NOT NULL,
  `sourceId` int(10) unsigned NOT NULL,
  `factId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `uservotes` */

DROP TABLE IF EXISTS `uservotes`;

CREATE TABLE `uservotes` (
  `fact_id` int(10) unsigned NOT NULL,
  `preferred_count` int(1) unsigned NOT NULL,
  `deprecated_count` int(1) unsigned NOT NULL,
  `modified_at` datetime DEFAULT NULL,
  PRIMARY KEY (`fact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
