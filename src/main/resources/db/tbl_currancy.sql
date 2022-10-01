USE `focus_test`;

DROP TABLE IF EXISTS `tbl_currancy`;

CREATE TABLE `tbl_currency` (e
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` mediumtext,
  `created_date` datetime DEFAULT NULL,
  `modified_by` mediumtext,
  `modified_date` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `symbol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;