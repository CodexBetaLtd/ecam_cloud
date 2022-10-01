USE `focus_test`;

DROP TABLE IF EXISTS `tbl_user_group_menu`;

CREATE TABLE `tbl_user_group_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_group_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_user_group_menu_1_idx` (`user_group_id`) USING BTREE,
  CONSTRAINT `tbl_user_group_menu_ibfk_1` FOREIGN KEY (`user_group_id`) REFERENCES `tbl_user_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;