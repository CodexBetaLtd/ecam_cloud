USE `focus_test`;

DROP TABLE IF EXISTS `tbl_meter_reading_unit`;

CREATE TABLE `tbl_meter_reading_unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` mediumtext,
  `created_date` datetime DEFAULT NULL,
  `modified_by` mediumtext,
  `modified_date` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `decimles` int(11) DEFAULT NULL,
  `symbol` varchar(45) DEFAULT NULL,
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;