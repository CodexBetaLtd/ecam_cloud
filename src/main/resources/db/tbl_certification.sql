USE `focus_test`;

DROP TABLE IF EXISTS `tbl_certification`;

CREATE TABLE `tbl_certification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `certification_type` varchar(45) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;