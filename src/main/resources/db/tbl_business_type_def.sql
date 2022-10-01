USE `focus_test`;

DROP TABLE IF EXISTS `tbl_business_type_def`;

CREATE TABLE `tbl_business_type_def` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `default_parent_id` int(11) DEFAULT NULL,
  `definable` tinyint(1) DEFAULT NULL,
  `all_parent` varchar(45) DEFAULT NULL,
  `business_type_def_name` varchar(45) DEFAULT NULL,
  `business_type_def_short` varchar(45) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
