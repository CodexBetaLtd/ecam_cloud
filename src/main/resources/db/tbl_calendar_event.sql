USE `focus_test`;

DROP TABLE IF EXISTS `tbl_calendar_event`;

CREATE TABLE `tbl_calendar_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_schedule_trigger_id` int(11) NOT NULL,
  `fk_scheduled_maintenance_id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_calendar_event_1_idx` (`fk_schedule_trigger_id`) USING BTREE,
  KEY `fk_tbl_calendar_event_2_idx` (`fk_scheduled_maintenance_id`) USING BTREE,
  CONSTRAINT `tbl_calendar_event_ibfk_1` FOREIGN KEY (`fk_schedule_trigger_id`) REFERENCES `tbl_schedule_trigger` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_calendar_event_ibfk_2` FOREIGN KEY (`fk_scheduled_maintenance_id`) REFERENCES `tbl_schedule_maintenance` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;
