USE `focus_test`;

DROP TABLE IF EXISTS `tbl_workorderrequest_log(??)`;

CREATE TABLE `tbl_workorderrequest_log(??)` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modify_by` int(11) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
