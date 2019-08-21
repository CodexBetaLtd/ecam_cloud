USE `focus_test`;

DROP TABLE IF EXISTS `tbl_scheduled_maintenance_activity_log`;

CREATE TABLE `tbl_scheduled_maintenance_activity_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_log_id` int(11) DEFAULT NULL,
  `asset_event_id` int(11) DEFAULT NULL,
  `meter_reading_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `work_order_id` int(11) DEFAULT NULL,
  `trigger_description` varchar(100) DEFAULT NULL,
  `trigger_threshold_value` varchar(45) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;