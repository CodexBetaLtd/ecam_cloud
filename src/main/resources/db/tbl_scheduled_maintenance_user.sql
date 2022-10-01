USE `focus_test`;

DROP TABLE IF EXISTS `tbl_scheduled_maintenance_user`;

CREATE TABLE `tbl_scheduled_maintenance_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scheduled_maintenance_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `can_update_or_close` tinyint(1) DEFAULT NULL,
  `notify_on_assignment` tinyint(1) DEFAULT NULL,
  `notify_on_completion` tinyint(1) DEFAULT NULL,
  `notify_on_online_offline` tinyint(1) DEFAULT NULL,
  `notify_on_status_change` tinyint(1) DEFAULT NULL,
  `notify_on_task_completed` tinyint(1) DEFAULT NULL,
  `primary_technician` tinyint(1) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_scheduled_maintenance_user_1_idx` (`scheduled_maintenance_id`) USING BTREE,
  KEY `fk_tbl_scheduled_maintenance_user_2_idx` (`user_id`) USING BTREE,
  CONSTRAINT `tbl_scheduled_maintenance_user_ibfk_1` FOREIGN KEY (`scheduled_maintenance_id`) REFERENCES `tbl_schedule_maintenance` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_scheduled_maintenance_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='ONE MAINTENACE CAN HAVE SEVERAL USERS ?';