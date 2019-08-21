USE `focus_test`;

DROP TABLE IF EXISTS `tbl_task_group_asset_catogery`;

CREATE TABLE `tbl_task_group_asset_catogery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) NOT NULL,
  `modified_date` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `fk_asset_catogery` int(11) DEFAULT NULL,
  `fk_task_group` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tg_ac_1` (`fk_asset_catogery`) USING BTREE,
  KEY `fk_tg_ac_2` (`fk_task_group`) USING BTREE,
  CONSTRAINT `tbl_task_group_asset_catogery_ibfk_1` FOREIGN KEY (`fk_asset_catogery`) REFERENCES `tbl_asset_catogery` (`id`),
  CONSTRAINT `tbl_task_group_asset_catogery_ibfk_2` FOREIGN KEY (`fk_task_group`) REFERENCES `tbl_task_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
