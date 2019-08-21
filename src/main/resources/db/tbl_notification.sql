USE `focus_test`;

DROP TABLE IF EXISTS `tbl_notification`;

CREATE TABLE `tbl_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recipient_id` int(11) DEFAULT NULL,
  `sender_id` int(11) NOT NULL,
  `notification_type` int(11) DEFAULT NULL,
  `subject` varchar(200) DEFAULT NULL,
  `content` text,
  `is_open` tinyint(1) NOT NULL,
  `is_popup` tinyint(1) NOT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `is_trashed` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_notification_1_idx` (`recipient_id`) USING BTREE,
  KEY `fk_tbl_notification_2_idx` (`sender_id`) USING BTREE,
  CONSTRAINT `tbl_notification_ibfk_1` FOREIGN KEY (`recipient_id`) REFERENCES `tbl_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_notification_ibfk_2` FOREIGN KEY (`sender_id`) REFERENCES `tbl_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;