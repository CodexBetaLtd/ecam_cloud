USE `focus_test`;

DROP TABLE IF EXISTS `tbl_scheduled_maintenance_part`;

CREATE TABLE `tbl_scheduled_maintenance_part` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NOT NULL,
  `part_id` int(11) NOT NULL,
  `scheduled_maintenance_id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `suggested_quantity` double NOT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_scheduled_maintenance_part_1_idx` (`asset_id`) USING BTREE,
  KEY `fk_tbl_scheduled_maintenance_part_2_idx` (`part_id`) USING BTREE,
  KEY `fk_tbl_scheduled_maintenance_part_3_idx` (`scheduled_maintenance_id`) USING BTREE,
  CONSTRAINT `tbl_scheduled_maintenance_part_ibfk_1` FOREIGN KEY (`asset_id`) REFERENCES `tbl_asset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_scheduled_maintenance_part_ibfk_2` FOREIGN KEY (`part_id`) REFERENCES `tbl_parts` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_scheduled_maintenance_part_ibfk_3` FOREIGN KEY (`scheduled_maintenance_id`) REFERENCES `tbl_schedule_maintenance` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;