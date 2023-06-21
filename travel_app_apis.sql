/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.0.27-community-nt : Database - travel_app_apis
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
-- CREATE DATABASE /*!32312 IF NOT EXISTS*/`travel_app_apis` /*!40100 DEFAULT CHARACTER SET latin1 */;

-- USE `travel_app_apis`;

/*Table structure for table `itinerary` */

DROP TABLE IF EXISTS `itinerary`;

CREATE TABLE `itinerary` (
  `itineraryid` int(11) NOT NULL auto_increment,
  `activities` text,
  `destination` varchar(255) NOT NULL,
  `end_date` date NOT NULL,
  `shared` tinyint(1) default NULL,
  `start_date` date NOT NULL,
  `user_id` int(11) default NULL,
  `shared_name` varchar(50) NOT NULL,
  PRIMARY KEY  (`itineraryid`),
  KEY `FKrm9ph76ii58eoivspjfrcsgwt` (`user_id`),
  CONSTRAINT `FKrm9ph76ii58eoivspjfrcsgwt` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `itinerary` */

insert  into `itinerary`(`itineraryid`,`activities`,`destination`,`end_date`,`shared`,`start_date`,`user_id`,`shared_name`) values (1,'Efiel Tower','Paris','2023-06-20',1,'2023-06-15',1,'henil'),(2,'Mandir','Somnath','2023-06-20',0,'2023-06-15',2,'Null'),(3,'Mandir','Dawrka','2023-06-20',1,'2023-06-15',1,'henil'),(4,'Beach','Goa','2023-06-20',0,'2023-06-15',3,'null'),(5,'Green house,beach','Kerala','2023-06-20',1,'2023-06-15',3,'darshan'),(6,'Beach','Kerla','2023-06-20',0,'2023-06-15',1,'Null'),(9,'Beach','Kerla','2023-06-20',0,'2023-06-15',1,'Henil'),(10,'Beach','Kerla','2023-06-20',0,'2023-06-15',1,'Henil'),(11,'Beach','Kerla','2023-06-20',0,'2023-06-15',1,'Null'),(12,'Tokyo Disney,Shibuya,Mount Fuji','Tokyo','2023-06-20',0,'2023-06-15',2,'Null'),(13,'Tokyo Disney,Shibuya,Mount Fuji','Tokyo','2023-06-20',1,'2023-06-15',2,'kush');

/*Table structure for table `pack` */

DROP TABLE IF EXISTS `pack`;

CREATE TABLE `pack` (
  `id` int(11) NOT NULL auto_increment,
  `activities` text,
  `destination` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `pack` */

insert  into `pack`(`id`,`activities`,`destination`) values (1,'Eiffel Tower,Louvre Museum,Seine River','Paris'),(2,'Tokyo Disney,Shibuya,Mount Fuji','Tokyo');

/*Table structure for table `place` */

DROP TABLE IF EXISTS `place`;

CREATE TABLE `place` (
  `id` int(11) NOT NULL auto_increment,
  `address` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `itinerary_itineraryid` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK3ir72xps9qwi7m39vh5hso2lw` (`itinerary_itineraryid`),
  CONSTRAINT `FK3ir72xps9qwi7m39vh5hso2lw` FOREIGN KEY (`itinerary_itineraryid`) REFERENCES `itinerary` (`itineraryid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `place` */

insert  into `place`(`id`,`address`,`name`,`itinerary_itineraryid`) values (5,'Paris, France','Eiffel Tower',1),(6,'Paris, France','Louvre Museum',1),(7,'Tokyo','Tokyo Disney',12),(8,'Tokyo','Shibuya',12),(9,'Tokyo','Mount Fuji',12),(10,'Tokyo','Tokyo Disney',13),(11,'Tokyo','Shibuya',13),(12,'Tokyo','Mount Fuji',13);

/*Table structure for table `shareitinerary` */

DROP TABLE IF EXISTS `shareitinerary`;

CREATE TABLE `shareitinerary` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL,
  `shareitinerary_itineraryid` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKposoj2vyqxnac5hnopug3hq8y` (`shareitinerary_itineraryid`),
  CONSTRAINT `FKposoj2vyqxnac5hnopug3hq8y` FOREIGN KEY (`shareitinerary_itineraryid`) REFERENCES `itinerary` (`itineraryid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `shareitinerary` */

insert  into `shareitinerary`(`id`,`name`,`shareitinerary_itineraryid`) values (1,'henil',1),(2,'henil',3),(3,'darshan',5),(5,'kush',13);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(255) default NULL,
  `user_role` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`email`,`password`,`user_role`) values (1,'kush','kush@gmail.com','$2a$10$sX0EFYdPZFbuDaMWoSXiDe7YiG.jvIG0nS4DYLfT0eaOZTjS87SxO','ADMIN'),(2,'henil','henil@gmail.com','$2a$10$AeO5aaUWiHIN2kYSHatxw.2zauMd1JZqvkLZKwxRFrurqdTdSxmt.','USER'),(3,'darshan','darshan@gmail.com','$2a$10$pH9rnlWL9UjXeoOhEAf.j.ml44fqY0G17hqmzPYXyr526xynoLg6S','USER');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
