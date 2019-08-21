USE `focus_test`;

DROP TABLE IF EXISTS `tbl_project`;

CREATE TABLE `tbl_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_project_id` int(11) DEFAULT NULL,
  `site_id` int(11) DEFAULT NULL,
  `actual_end_date` datetime DEFAULT NULL,
  `actual_start_date` datetime DEFAULT NULL,
  `projected_end_date` datetime DEFAULT NULL,
  `projected_start_date` datetime DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `notes` varchar(250) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_project_1_idx` (`parent_project_id`) USING BTREE,
  KEY `fk_tbl_project_2_idx` (`site_id`) USING BTREE,
  CONSTRAINT `tbl_project_ibfk_1` FOREIGN KEY (`parent_project_id`) REFERENCES `tbl_project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_project_ibfk_2` FOREIGN KEY (`site_id`) REFERENCES `tbl_asset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;