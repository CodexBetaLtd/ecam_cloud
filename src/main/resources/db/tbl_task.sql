USE `focus_test`;

DROP TABLE IF EXISTS `tbl_task`;

CREATE TABLE `tbl_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` mediumtext CHARACTER SET utf8 NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `name` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `description` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `order` int(255) DEFAULT NULL,
  `task_type` int(11) DEFAULT NULL,
  `fk_task_group_id` int(11) DEFAULT NULL,
  `fk_meter_reading_unit_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_task_1` (`fk_meter_reading_unit_id`) USING BTREE,
  KEY `fk_task_2` (`fk_task_group_id`) USING BTREE,
  CONSTRAINT `tbl_task_ibfk_1` FOREIGN KEY (`fk_meter_reading_unit_id`) REFERENCES `tbl_meter_reading_unit` (`id`),
  CONSTRAINT `tbl_task_ibfk_2` FOREIGN KEY (`fk_task_group_id`) REFERENCES `tbl_task_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;