USE `focus_test`;

DROP TABLE IF EXISTS `tbl_charge_department`;

CREATE TABLE `charge_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;