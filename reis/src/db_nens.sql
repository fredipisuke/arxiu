--
-- Table structure for table `tutor`
--
DROP TABLE IF EXISTS `tutor`;
CREATE TABLE `tutor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipusDocument` int(11) DEFAULT NULL,
  `document` varchar(15) DEFAULT NULL,
  `nom` varchar(150) DEFAULT NULL,
  `direccio` varchar(150) DEFAULT NULL,
  `codiPostal` varchar(150) DEFAULT NULL,
  `poblacio` varchar(150) DEFAULT NULL,
  `telefon` int(11) DEFAULT 0,
  `mobil` int(11) DEFAULT 0,
  `email` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Table structure for table `escola`
--
DROP TABLE IF EXISTS `escola`;
CREATE TABLE `escola` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcio` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Table structure for table `nen`
--
DROP TABLE IF EXISTS `nen`;
CREATE TABLE `nen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipusDocument` int(11) DEFAULT NULL,
  `document` varchar(15) DEFAULT NULL,
  `nom` varchar(150) DEFAULT NULL,
  `sexe` varchar(1) DEFAULT NULL,
  `dataNaixement` date NOT NULL,
  `observacionsAmics` varchar(500) DEFAULT NULL,
  `observacionsJeep` varchar(500) DEFAULT NULL,
  `surt` boolean DEFAULT false,
  `caramelsPagats` varchar(500) DEFAULT false,
  `escola_id` int(11) NOT NULL,
  `tutor_id` int(11) NOT NULL,  
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_nen_escola_id` FOREIGN KEY (`escola_id`) REFERENCES `escola` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_nen_tutor_id` FOREIGN KEY (`tutor_id`) REFERENCES `tutor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
