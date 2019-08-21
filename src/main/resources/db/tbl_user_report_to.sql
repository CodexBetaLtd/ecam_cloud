USE `focus_test`;

DROP TABLE IF EXISTS `tbl_user_report_to`;

CREATE TABLE `tbl_user_report_to` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `report_to_user_id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_user_report_to_1_idx` (`user_id`) USING BTREE,
  KEY `fk_tbl_user_report_to_2_idx` (`report_to_user_id`) USING BTREE,
  CONSTRAINT `tbl_user_report_to_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_user_report_to_ibfk_2` FOREIGN KEY (`report_to_user_id`) REFERENCES `tbl_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;