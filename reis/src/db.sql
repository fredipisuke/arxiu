DROP DATABASE IF EXISTS `reisigualada`;
CREATE DATABASE IF NOT EXISTS `reisigualada`;
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
INSERT INTO `role` VALUES (3,'ROLE_ARXIU');
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
INSERT INTO `user` VALUES (2,'arxiu','$2a$11$UU7yi1VWh/uiyhouBB0ICOkAzjxnopc.i/EqN7KS6SHIZh.1OwIbC');
-- PASSWORD: arxiu
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
INSERT INTO `user_role` VALUES (2,1);
INSERT INTO `user_role` VALUES (2,3);
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

INSERT INTO `clau` (`id`, `name`, `type`) VALUES
(1, 'arribada', 1),
(2, 'auca', 1),
(3, 'baltasar', 1),
(4, 'blanc', 1),
(5, 'camions', 1),
(6, 'caramels', 1),
(7, 'carrossa', 1),
(8, 'cartes', 1),
(9, 'cavalcada', 1),
(10, 'comarca', 1),
(11, 'escales', 1),
(12, 'estrella', 1),
(13, 'faruk', 1),
(14, 'gaspar', 1),
(15, 'jeep', 1),
(16, 'melcior', 1),
(17, 'monedes', 1),
(18, 'negre', 1),
(19, 'paquets', 1),
(20, 'patge', 1),
(21, 'recepció cartes', 1),
(22, 'reis', 1),
(23, 'ros', 1),
(24, 'ajuntament', 2),
(25, 'auques', 2),
(26, 'cartes', 2),
(27, 'celebracions', 2),
(28, 'convencions', 2),
(29, 'crides i pregons', 2),
(30, 'docs. diversos', 2),
(31, 'docs. històrics', 2),
(32, 'donatius', 2),
(33, 'edició cartes i segells', 2),
(34, 'enregistrament', 2),
(35, 'exposicions', 2),
(36, 'instàncies', 2),
(37, 'liquidacions', 2),
(38, 'llistat patges', 2),
(39, 'missatges patge faruk', 2),
(40, 'oficials', 2),
(41, 'once', 2),
(42, 'paquets', 2),
(43, 'patge faruk', 2),
(44, 'programes', 2),
(45, 'reis', 2),
(46, 'rifa nadal', 2),
(47, 'títols', 2),
(48, 'treballs recerca', 2);