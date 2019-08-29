USE `focus_test`;

DROP TABLE IF EXISTS `tbl_schedule_maintenance`;

CREATE TABLE `tbl_schedule_maintenance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_account_id` int(11) DEFAULT NULL COMMENT 'relation ship to Account table',
  `fk_charge_department_id` int(11) DEFAULT NULL COMMENT 'Relationship with Charge department',
  `fk_maintenance_type_id` int(11) DEFAULT NULL,
  `fk_priority_id` int(11) DEFAULT NULL COMMENT 'relationship with priority table',
  `fk_project_id` int(11) DEFAULT NULL,
  `fk_requestor_user_id` int(11) DEFAULT NULL,
  `fk_site_id` int(11) DEFAULT NULL COMMENT 'SIte is asset',
  `fk_start_as_wo_status_id` int(11) DEFAULT NULL COMMENT 'relation with Work Order Status table',
  `notify_creator_when_dwoe` tinyint(1) DEFAULT NULL,
  `notify_technicians_when_dwoe` tinyint(1) DEFAULT NULL,
  `suggested_time` double DEFAULT NULL,
  `time_estimated_hours` double DEFAULT NULL,
  `scheduled_maintenance_status_id` int(11) DEFAULT NULL,
  `suggested_completion` int(11) DEFAULT NULL,
  `admin_notes` varchar(200) DEFAULT NULL,
  `asset_ids` varchar(50) DEFAULT NULL,
  `assets` varchar(100) DEFAULT NULL,
  `assigned_user_ids` varchar(50) DEFAULT NULL,
  `assigned_users` varchar(50) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `completion_notes` varchar(200) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `r_type` varchar(50) DEFAULT NULL,
  `schedule_description` varchar(200) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_schedule_maintenance_1_idx` (`fk_maintenance_type_id`) USING BTREE,
  KEY `fk_tbl_schedule_maintenance_2_idx` (`fk_priority_id`) USING BTREE,
  KEY `fk_tbl_schedule_maintenance_3_idx` (`fk_project_id`) USING BTREE,
  KEY `fk_tbl_schedule_maintenance_4_idx` (`fk_requestor_user_id`) USING BTREE,
  KEY `fk_tbl_schedule_maintenance_5_idx` (`fk_site_id`) USING BTREE,
  CONSTRAINT `tbl_schedule_maintenance_ibfk_1` FOREIGN KEY (`fk_maintenance_type_id`) REFERENCES `tbl_maintainance_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_schedule_maintenance_ibfk_2` FOREIGN KEY (`fk_priority_id`) REFERENCES `tbl_priorities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_schedule_maintenance_ibfk_3` FOREIGN KEY (`fk_project_id`) REFERENCES `tbl_project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_schedule_maintenance_ibfk_4` FOREIGN KEY (`fk_requestor_user_id`) REFERENCES `tbl_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_schedule_maintenance_ibfk_5` FOREIGN KEY (`fk_site_id`) REFERENCES `tbl_asset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
) ENGINE=InnoDB DEFAULT CHARSET=latin1;