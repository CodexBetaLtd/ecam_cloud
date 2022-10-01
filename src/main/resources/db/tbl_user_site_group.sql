USE `focus_test`;

DROP TABLE IF EXISTS `tbl_user_site_group`;

CREATE TABLE `tbl_user_site_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `user_site_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_user_site_group_1_idx` (`user_site_id`),
  KEY `fk_tbl_user_site_group_2_idx` (`group_id`),
  CONSTRAINT `fk_tbl_user_site_group_1` FOREIGN KEY (`user_site_id`) REFERENCES `tbl_user_site` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_user_site_group_2` FOREIGN KEY (`group_id`) REFERENCES `tbl_user_group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;