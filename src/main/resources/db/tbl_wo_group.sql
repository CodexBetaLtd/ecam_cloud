USE `focus_test`;

DROP TABLE IF EXISTS `tbl_wo_group`;

CREATE TABLE `tbl_wo_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `created_by` mediumtext,
  `created_date` datetime DEFAULT NULL,
  `modify_by` mediumtext,
  `modify_date` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `task_no` varchar(45) DEFAULT NULL,
  `task_description` varchar(45) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;