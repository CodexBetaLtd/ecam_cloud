USE `focus_test`;

DROP TABLE IF EXISTS `tbl_wo_task`;

CREATE TABLE `tbl_wo_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modify_by` int(11) NOT NULL,
  `modify_date` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `fk_asset_id` int(11) DEFAULT NULL,
  `fk_task_type` int(11) DEFAULT NULL,
  `fk_corrective_maintenance_task_id` int(11) DEFAULT NULL,
  `fk_preventive_maintenance_task_id` int(11) DEFAULT NULL,
  `fk_wo_id` int(11) DEFAULT NULL,
  `fk_wo_request_task_id` int(11) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `completion_remark` varchar(45) DEFAULT NULL,
  `completion_notes` varchar(45) DEFAULT NULL,
  `started_date` datetime DEFAULT NULL,
  `complated_date` datetime DEFAULT NULL,
  `estimated_time` double DEFAULT NULL,
  `actual_time` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_wo_req_task_idx` (`fk_wo_request_task_id`) USING BTREE,
  KEY `fk_wo_task_3_idx` (`fk_task_type`) USING BTREE,
  KEY `fk_wo_task_2_idx` (`fk_asset_id`) USING BTREE,
  KEY `fk_wo_task_4_idx` (`fk_preventive_maintenance_task_id`) USING BTREE,
  KEY `fk_wo_task_5_idx` (`fk_wo_id`) USING BTREE,
  CONSTRAINT `tbl_wo_task_ibfk_1` FOREIGN KEY (`fk_wo_request_task_id`) REFERENCES `tbl_workorder_request_task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_wo_task_ibfk_2` FOREIGN KEY (`fk_asset_id`) REFERENCES `tbl_asset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_wo_task_ibfk_3` FOREIGN KEY (`fk_task_type`) REFERENCES `tbl_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_wo_task_ibfk_4` FOREIGN KEY (`fk_preventive_maintenance_task_id`) REFERENCES `tbl_scheduled_task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_wo_task_ibfk_5` FOREIGN KEY (`fk_wo_id`) REFERENCES `tbl_workorder` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;