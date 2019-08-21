USE `focus_test`;

DROP TABLE IF EXISTS `tbl_business_group`;

CREATE TABLE `tbl_business_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `relationship_type` int(11) DEFAULT NULL,
  `is_default_manufacturer` tinyint(1) DEFAULT NULL,
  `is_default_supplier` tinyint(1) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;