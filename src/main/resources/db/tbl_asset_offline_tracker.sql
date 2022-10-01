USE `focus_test`;

DROP TABLE IF EXISTS `tbl_asset_offline_tracker`;

CREATE TABLE `tbl_asset_offline_tracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_event_description` varchar(45) DEFAULT NULL,
  `offline_additional_info` varchar(45) DEFAULT NULL,
  `online_additional_info` varchar(45) DEFAULT NULL,
  `rb_ asset_location` varchar(45) DEFAULT NULL,
  `rb_movement` varchar(45) DEFAULT NULL,
  `send_to_aislel` varchar(45) DEFAULT NULL,
  ` send_to_bin` varchar(45) DEFAULT NULL,
  `send_to_row` varchar(45) DEFAULT NULL,
  `production_hours_affected` double DEFAULT NULL,
  `off_line_to` date DEFAULT NULL,
  `offline_from` date DEFAULT NULL,
  `asset_event_type _id` int(11) DEFAULT NULL,
  `asset_is_located_at_asset_id` int(11) DEFAULT NULL,
  `asset_is_part_of_asset_id` int(11) DEFAULT NULL,
  `reason_offline_id` int(11) DEFAULT NULL,
  `reason_online_id` int(11) DEFAULT NULL,
  `send_to_facility_id` int(11) DEFAULT NULL,
  `set_offline_by_user_id` int(11) DEFAULT NULL,
  `set_online_by_user_id` int(11) DEFAULT NULL,
  `status_changed_by_user_id` int(11) DEFAULT NULL,
  `swap_with_asset_id` int(11) DEFAULT NULL,
  `work_order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;