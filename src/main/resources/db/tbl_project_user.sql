USE `focus_test`;

DROP TABLE IF EXISTS `tbl_project_user`;

CREATE TABLE `tbl_project_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `date_added` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_project_user_1_idx` (`project_id`) USING BTREE,
  KEY `fk_tbl_project_user_2_idx` (`user_id`) USING BTREE,
  CONSTRAINT `tbl_project_user_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `tbl_project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_project_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;