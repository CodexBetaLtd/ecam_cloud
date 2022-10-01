USE `focus_test`;

DROP TABLE IF EXISTS `tbl_wo_task_part`;

CREATE TABLE `tbl_wo_task_part` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modify_by` int(11) NOT NULL,
  `modify_date` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `planned_quantity` double DEFAULT NULL,
  `actual_quantity` double DEFAULT NULL,
  `allowed_exceed_inventory` tinyint(1) DEFAULT NULL,
  `fk_wo_task_id` int(11) DEFAULT NULL,
  `fk_part_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_wo_task_part_1_idx` (`fk_wo_task_id`) USING BTREE,
  KEY `fk_tbl_wo_task_part_2_idx` (`fk_part_id`) USING BTREE,
  CONSTRAINT `tbl_wo_task_part_ibfk_1` FOREIGN KEY (`fk_wo_task_id`) REFERENCES `tbl_wo_task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_wo_task_part_ibfk_2` FOREIGN KEY (`fk_part_id`) REFERENCES `tbl_parts` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;