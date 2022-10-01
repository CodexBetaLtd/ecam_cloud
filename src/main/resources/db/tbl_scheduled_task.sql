USE `focus_test`;

DROP TABLE IF EXISTS `tbl_scheduled_task`;

CREATE TABLE `tbl_scheduled_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_asset_id` int(11) DEFAULT NULL,
  `fk_assigned_to_user_id` int(11) DEFAULT NULL,
  `meter_reading_unit_id` int(11) DEFAULT NULL,
  `scheduled_maintenance_id` int(11) DEFAULT NULL,
  `task_control_id` int(11) DEFAULT NULL,
  `task_group_asset_category_control_id` int(11) DEFAULT NULL,
  `task_group_control_id` int(11) DEFAULT NULL,
  `order` int(11) DEFAULT NULL,
  `task_type` int(11) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_scheduled_task_1_idx` (`fk_asset_id`) USING BTREE,
  KEY `fk_tbl_scheduled_task_2_idx` (`fk_assigned_to_user_id`) USING BTREE,
  CONSTRAINT `tbl_scheduled_task_ibfk_1` FOREIGN KEY (`fk_asset_id`) REFERENCES `tbl_asset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_scheduled_task_ibfk_2` FOREIGN KEY (`fk_assigned_to_user_id`) REFERENCES `tbl_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;