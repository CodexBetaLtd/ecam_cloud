USE `focus_test`;

DROP TABLE IF EXISTS `tbl_user_site`;
CREATE TABLE `tbl_user_site` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `site_id` int(11) DEFAULT NULL COMMENT 'This is referance to the asset id',
  PRIMARY KEY (`id`),
  KEY `fk_tbl_user_site_1_idx` (`user_id`),
  KEY `fk_tbl_user_site_2_idx` (`site_id`),
  CONSTRAINT `fk_tbl_user_site_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_user_site_2` FOREIGN KEY (`site_id`) REFERENCES `tbl_asset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;