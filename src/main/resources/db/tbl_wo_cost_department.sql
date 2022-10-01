USE `focus_test`;

DROP TABLE IF EXISTS `tbl_wo_cost_department`;

CREATE TABLE `tbl_wo_cost_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modify_by` int(11) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;