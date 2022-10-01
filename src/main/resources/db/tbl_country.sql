USE `focus_test`;

DROP TABLE IF EXISTS `tbl_country`;

CREATE TABLE `tbl_country` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) DEFAULT NULL,
  
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `name` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `short_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;