CREATE DATABASE  IF NOT EXISTS `reisigualada`;
USE `reisigualada`;

--
-- Table structure for table `role`
--
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
--
-- Dumping data for table `role`
--
LOCK TABLES `role` WRITE;
INSERT INTO `role` VALUES (1,'ROLE_USER');
INSERT INTO `role` VALUES (2,'ROLE_ADMIN');
INSERT INTO `role` VALUES (3,'ROLE_PAQUETS');
INSERT INTO `role` VALUES (4,'ROLE_PATGES');
INSERT INTO `role` VALUES (5,'ROLE_NENS');
INSERT INTO `role` VALUES (6,'ROLE_ARXIU');

ROLE_NENS
UNLOCK TABLES;

--
-- Table structure for table `user`
--
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
--
-- Dumping data for table `user`
--
LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (1,'reis','$2a$11$Y.6h15KDtYw1rn0t3UQgH.G5tpgYhlW0Perj6/KwMlHRJIQneKg6a');
-- PASSWORD: reis
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_user_role_roleid_idx` (`role_id`),
  CONSTRAINT `fk_user_role_roleid` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role_userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
-- Dumping data for table `user_role`
--
LOCK TABLES `user_role` WRITE;
INSERT INTO `user_role` VALUES (1,1);
INSERT INTO `user_role` VALUES (1,2);
UNLOCK TABLES;

--
-- Table structure for table `printer`
--
DROP TABLE IF EXISTS `printer`;
CREATE TABLE `printer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `printername` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
--
-- Table structure for table `user_printer`
--
DROP TABLE IF EXISTS `user_printer`;
CREATE TABLE `user_printer` (
  `user_id` int(11) NOT NULL,
  `printer_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`printer_id`),
  KEY `fk_user_printer_printerid_idx` (`printer_id`),
  CONSTRAINT `fk_user_printer_printerid` FOREIGN KEY (`printer_id`) REFERENCES `printer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_printer_userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `keys`
--
DROP TABLE IF EXISTS `clau`;
CREATE TABLE `clau` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
-- Index unic clau/tipus
ALTER TABLE `clau`
  ADD UNIQUE KEY `TIPUS-NOM` (`type`,`name`);
  
--
-- Table structure for table `fitxer`
--
DROP TABLE IF EXISTS `fitxer`;
CREATE TABLE `fitxer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titol` varchar(100) DEFAULT NULL,
  `typeDocument` int(11) NOT NULL,
  `observacions` varchar(500) NULL,
  `fileName` varchar(50) NOT NULL,
  `format` varchar(5) NOT NULL,
  `data` date NOT NULL,
  `autor` varchar(100) NOT NULL,
  `dataCreacio` timestamp NOT NULL,
  -- PROPIETATS D'IMATGE
  `ubicacio` varchar(100) NULL,
  `procedencia` varchar(100) NULL,
  -- PROPIETATS DE DOCUMENT
  `ubicacioArxiu` varchar(100) NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
--
-- Table structure for table `fitxer-clau`
--
DROP TABLE IF EXISTS `fitxer_clau`;
CREATE TABLE `fitxer_clau` (
  `fitxer_id` int(11) NOT NULL,
  `clau_id` int(11) NOT NULL,
  PRIMARY KEY (`fitxer_id`, `clau_id`),
  CONSTRAINT `fk_fitxer_clau_fitxer_id` FOREIGN KEY (`fitxer_id`) REFERENCES `fitxer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_fitxer_clau_clau_id` FOREIGN KEY (`clau_id`) REFERENCES `clau` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;