USE `focus_test`;

DROP TABLE IF EXISTS `tbl_wo_cost`;

CREATE TABLE `tbl_wo_cost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modify_by` int(11) NOT NULL,
  `modify_date` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `fk_cost_amount` int(11) DEFAULT NULL,
  `fk_cost_department` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_wo_cost_id_1_idx` (`fk_cost_department`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;