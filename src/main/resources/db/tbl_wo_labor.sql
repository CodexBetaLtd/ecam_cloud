USE `focus_test`;

DROP TABLE IF EXISTS `tbl_wo_labor`;

CREATE TABLE `tbl_wo_labor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modify_by` int(11) NOT NULL,
  `modify_date` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `fk_wo_id` int(11) DEFAULT NULL,
  `fk_labor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_wo_labor_1_idx` (`fk_wo_id`) USING BTREE,
  KEY `fk_tbl_wo_labor_2_idx` (`fk_labor_id`) USING BTREE,
  CONSTRAINT `tbl_wo_labor_ibfk_1` FOREIGN KEY (`fk_wo_id`) REFERENCES `tbl_workorder` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_wo_labor_ibfk_2` FOREIGN KEY (`fk_labor_id`) REFERENCES `tbl_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;