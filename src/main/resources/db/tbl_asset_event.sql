USE `focus_test`;

DROP TABLE IF EXISTS `tbl_asset_event`;

CREATE TABLE `tbl_asset_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) NOT NULL,
  `modified_date` datetime NOT NULL,
  `asset_id` int(11) DEFAULT NULL,
  `asset_event_type_id` int(11) DEFAULT NULL,
  `additional_description` varchar(255) DEFAULT NULL,
  `work_order_id` int(11) DEFAULT NULL,
  `date_of_submit` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_asset_event` (`asset_id`) USING BTREE,
  KEY `fk_asset_event_1` (`asset_event_type_id`) USING BTREE,
  CONSTRAINT `tbl_asset_event_ibfk_1` FOREIGN KEY (`asset_event_type_id`) REFERENCES `tbl_asset_event_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;