USE `focus_test`;

DROP TABLE IF EXISTS `tbl_wo_notification`;

CREATE TABLE `tbl_wo_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modify_by` int(11) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `fk_wo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_wo_notification_idx` (`fk_wo_id`) USING BTREE,
  CONSTRAINT `tbl_wo_notification_ibfk_1` FOREIGN KEY (`fk_wo_id`) REFERENCES `tbl_workorder` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;