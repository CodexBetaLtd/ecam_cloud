USE `focus_test`;

DROP TABLE IF EXISTS `tbl_workorder_request`;

CREATE TABLE `tbl_workorder_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modify_by` int(11) NOT NULL,
  `modify_date` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `fk_workOrder_requestedBy` int(11) DEFAULT NULL,
  `fk_workOrder_approvedBy` int(11) DEFAULT NULL,
  `fk_workOrder_status` int(11) DEFAULT NULL,
  `fk_project_id` int(11) DEFAULT NULL,
  `fk_priority_id` int(11) DEFAULT NULL,
  `workorder_request_no` varchar(45) DEFAULT NULL,
  `workorder_requested_date` datetime DEFAULT NULL,
  `workOrder_approvedDate` datetime DEFAULT NULL,
  `workOrder_requested_from_date` datetime DEFAULT NULL,
  `workOrder_requested_to_date` datetime DEFAULT NULL,
  `is_revised` tinyint(1) DEFAULT NULL,
  `revised_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_workOrder_request_1_idx` (`fk_workOrder_requestedBy`) USING BTREE,
  KEY `fk_tbl_workOrder_request_2_idx` (`fk_workOrder_approvedBy`) USING BTREE,
  KEY `fk_tbl_workOrder_request_3_idx` (`fk_workOrder_status`) USING BTREE,
  KEY `fk_tbl_workOrder_request_4_idx` (`fk_project_id`) USING BTREE,
  KEY `fk_tbl_workOrder_request_5_idx` (`fk_priority_id`) USING BTREE,
  CONSTRAINT `tbl_workorder_request_ibfk_1` FOREIGN KEY (`fk_workOrder_requestedBy`) REFERENCES `tbl_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_workorder_request_ibfk_2` FOREIGN KEY (`fk_workOrder_approvedBy`) REFERENCES `tbl_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_workorder_request_ibfk_3` FOREIGN KEY (`fk_workOrder_status`) REFERENCES `tbl_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_workorder_request_ibfk_4` FOREIGN KEY (`fk_project_id`) REFERENCES `tbl_project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_workorder_request_ibfk_5` FOREIGN KEY (`fk_priority_id`) REFERENCES `tbl_priorities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
