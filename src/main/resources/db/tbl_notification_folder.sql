USE `focus_test`;

DROP TABLE IF EXISTS `tbl_notification_folder`;

CREATE TABLE `tbl_notification_folder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;;