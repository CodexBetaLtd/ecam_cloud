USE `focus_test`;

DROP TABLE IF EXISTS `tbl_workorder_request_task`;

CREATE TABLE `tbl_workorder_request_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modify_by` int(11) NOT NULL,
  `modify_date` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `fk_workorder_request_id` int(11) DEFAULT NULL,
  `fk_maintenance_task_id` int(11) DEFAULT NULL COMMENT 'registered brakedown\n',
  `fk_task_type_id` int(11) DEFAULT NULL,
  `from_date` datetime DEFAULT NULL,
  `to_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_workorder_request_task_3_idx` (`fk_task_type_id`) USING BTREE,
  KEY `fk_tbl_workorder_request_task_1_idx` (`fk_workorder_request_id`) USING BTREE,
  KEY `fk_tbl_workorder_request_task_3_idx1` (`fk_maintenance_task_id`) USING BTREE,
  CONSTRAINT `tbl_workorder_request_task_ibfk_1` FOREIGN KEY (`fk_workorder_request_id`) REFERENCES `tbl_workorder_request` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_workorder_request_task_ibfk_2` FOREIGN KEY (`fk_task_type_id`) REFERENCES `tbl_wo_task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_workorder_request_task_ibfk_3` FOREIGN KEY (`fk_maintenance_task_id`) REFERENCES `tbl_task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
