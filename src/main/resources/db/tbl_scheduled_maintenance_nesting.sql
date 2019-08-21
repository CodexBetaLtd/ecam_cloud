USE `focus_test`;

DROP TABLE IF EXISTS `tbl_scheduled_maintenance_nesting`;

CREATE TABLE `tbl_scheduled_maintenance_nesting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL,
  `scheduled_maintenance_id` int(11) NOT NULL,
  `current_iteration_cycle` int(11) DEFAULT NULL,
  `multiplier` int(11) DEFAULT NULL,
  `name_identifier` int(11) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_scheduled_maintenance_nesting_1_idx` (`parent_id`) USING BTREE,
  KEY `fk_tbl_scheduled_maintenance_nesting_2_idx` (`scheduled_maintenance_id`) USING BTREE,
  CONSTRAINT `tbl_scheduled_maintenance_nesting_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `tbl_scheduled_maintenance_nesting` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_scheduled_maintenance_nesting_ibfk_2` FOREIGN KEY (`scheduled_maintenance_id`) REFERENCES `tbl_schedule_maintenance` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;