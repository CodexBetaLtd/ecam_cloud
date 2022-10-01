USE `focus_test`;

DROP TABLE IF EXISTS `tbl_wo_parts`;

CREATE TABLE `tbl_wo_parts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modify_by` int(11) NOT NULL,
  `modify_date` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `fk_part_id` int(11) DEFAULT NULL,
  `fk_wo_id` int(11) DEFAULT NULL,
  `estimated_quantity` double DEFAULT NULL,
  `actual_quantity` double DEFAULT NULL,
  `remark` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_wo_parts_1_idx` (`fk_part_id`) USING BTREE,
  KEY `fk_tbl_wo_parts_2_idx` (`fk_wo_id`) USING BTREE,
  CONSTRAINT `tbl_wo_parts_ibfk_1` FOREIGN KEY (`fk_part_id`) REFERENCES `tbl_parts` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_wo_parts_ibfk_2` FOREIGN KEY (`fk_wo_id`) REFERENCES `tbl_workorder` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;