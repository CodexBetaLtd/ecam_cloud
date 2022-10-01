USE `focus_test`;

DROP TABLE IF EXISTS `tbl_wo_cost_account`;

CREATE TABLE `tbl_wo_cost_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modify_by` int(11) NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;