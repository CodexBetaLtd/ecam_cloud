USE `focus_test`;

DROP TABLE IF EXISTS `tbl_schedule_trigger`;

CREATE TABLE `tbl_schedule_trigger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_event_type_id` int(11) DEFAULT NULL,
  `asset_id` int(11) DEFAULT NULL,
  `r_meter_reading_unit_id` int(11) DEFAULT NULL,
  `ro_meter_reading_unit_id` int(11) DEFAULT NULL,
  `scheduled_maintenance_id` int(11) DEFAULT NULL,
  `create_wo_on_start_date` tinyint(1) DEFAULT NULL,
  `tsw_sunday` tinyint(1) DEFAULT NULL,
  `tsw_monday` tinyint(1) DEFAULT NULL,
  `tsw_tuesday` tinyint(1) DEFAULT NULL,
  `tsw_wednesday` tinyint(1) DEFAULT NULL,
  `tsw_thursday` tinyint(1) DEFAULT NULL,
  `tsw_friday` tinyint(1) DEFAULT NULL,
  `tsw_saturday` tinyint(1) DEFAULT NULL,
  `tr_end_date` datetime DEFAULT NULL,
  `tr_start_date` datetime DEFAULT NULL,
  `last_triggered_date` datetime DEFAULT NULL,
  `last_date` datetime DEFAULT NULL,
  `last_meter_reading` double DEFAULT NULL,
  `r_meter_reading` double DEFAULT NULL,
  `ro_meter_reading` double DEFAULT NULL,
  `rr_end_by` double DEFAULT NULL,
  `rr_start` double DEFAULT NULL,
  `rr_end_after` int(11) DEFAULT NULL,
  `tr_end_after` int(11) DEFAULT NULL,
  `tsd_every_days` int(11) DEFAULT NULL,
  `tsh_every_hours` int(11) DEFAULT NULL,
  `tsm_day_of_month` int(11) DEFAULT NULL,
  `tsm_every_months` int(11) DEFAULT NULL,
  `tsw_every_weeks` int(11) DEFAULT NULL,
  `tsy_day_of_month` int(11) DEFAULT NULL,
  `tsy_every_years` int(11) DEFAULT NULL,
  `tsy_month_of_year` int(11) DEFAULT NULL,
  `mr_logic` varchar(45) DEFAULT NULL,
  `ro_type` varchar(45) DEFAULT NULL,
  `rr_type` varchar(45) DEFAULT NULL,
  `r_type` varchar(45) DEFAULT NULL,
  `schedule_description` varchar(45) DEFAULT NULL,
  `tr_type` varchar(45) DEFAULT NULL,
  `ts_type` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_schedule_trigger_1_idx` (`asset_event_type_id`) USING BTREE,
  KEY `fk_tbl_schedule_trigger_2_idx` (`asset_id`) USING BTREE,
  KEY `fk_tbl_schedule_trigger_3_idx` (`scheduled_maintenance_id`) USING BTREE,
  CONSTRAINT `tbl_schedule_trigger_ibfk_1` FOREIGN KEY (`asset_event_type_id`) REFERENCES `tbl_asset_event_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_schedule_trigger_ibfk_2` FOREIGN KEY (`asset_id`) REFERENCES `tbl_asset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_schedule_trigger_ibfk_3` FOREIGN KEY (`scheduled_maintenance_id`) REFERENCES `tbl_schedule_maintenance` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;