USE `focus_test`;

DROP TABLE IF EXISTS `tbl_asset_catogery`;

CREATE TABLE `tbl_asset_catogery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `name` varchar(45) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `override_rules` varchar(15) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `sys_code` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;