USE `focus_test`;

DROP TABLE IF EXISTS `tbl_user_group_page_permission`;

CREATE TABLE `tbl_user_group_page_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_group_page_id` int(11) NOT NULL,
  `page_permission_id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_user_group_page_permission_1_idx` (`user_group_page_id`) USING BTREE,
  CONSTRAINT `tbl_user_group_page_permission_ibfk_1` FOREIGN KEY (`user_group_page_id`) REFERENCES `tbl_user_group_page` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;