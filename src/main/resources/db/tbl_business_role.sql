USE `focus_test`;

DROP TABLE IF EXISTS `tbl_business_role`;

CREATE TABLE `tbl_business_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_business_id` int(11) NOT NULL,
  `fk_business_role_type_id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_business_role_1_idx` (`fk_business_id`) USING BTREE,
  KEY `fk_tbl_business_role_2_idx` (`fk_business_role_type_id`) USING BTREE,
  CONSTRAINT `tbl_business_role_ibfk_1` FOREIGN KEY (`fk_business_id`) REFERENCES `tbl_business` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_business_role_ibfk_2` FOREIGN KEY (`fk_business_role_type_id`) REFERENCES `tbl_business_role_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;