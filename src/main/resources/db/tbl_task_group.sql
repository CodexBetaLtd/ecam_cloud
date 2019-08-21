USE `focus_test`;

DROP TABLE IF EXISTS `tbl_task_group`;

CREATE TABLE `tbl_task_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` mediumtext CHARACTER SET utf8 NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) NOT NULL,
  `modified_date` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `name` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `description` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
