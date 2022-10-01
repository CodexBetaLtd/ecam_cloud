USE `focus_test`;

DROP TABLE IF EXISTS `tbl_wo_group_task`;

CREATE TABLE `tbl_wo_group_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `created_by` mediumtext,
  `created_date` datetime DEFAULT NULL,
  `modify_by` mediumtext,
  `modify_date` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `fk_wo_group_id` int(11) DEFAULT NULL,
  `fk_corrective_maintenance_task_id` int(11) DEFAULT NULL,
  `fk_preventive_maintenance_task_id` int(11) DEFAULT NULL,
  `fk_meter_reading_unit` int(11) DEFAULT NULL,
  `from_date` datetime DEFAULT NULL,
  `to_date` datetime DEFAULT NULL,
  `estimated_hours` double DEFAULT NULL,
  `actual_hours` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_wo_group_task_id_idx` (`fk_preventive_maintenance_task_id`) USING BTREE,
  KEY `fk_tbl_wo_group_task_id_2_idx` (`fk_meter_reading_unit`) USING BTREE,
  KEY `fk_tbl_wo_group_task_id_3_idx` (`fk_wo_group_id`) USING BTREE,
  CONSTRAINT `tbl_wo_group_task_ibfk_1` FOREIGN KEY (`fk_preventive_maintenance_task_id`) REFERENCES `tbl_scheduled_task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_wo_group_task_ibfk_2` FOREIGN KEY (`fk_meter_reading_unit`) REFERENCES `tbl_meter_reading_unit` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;