USE `focus_test`;

DROP TABLE IF EXISTS `tbl_user_certification`;

CREATE TABLE `tbl_user_certification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `certificate_id` varchar(45) NOT NULL,
  `certification_id` int(11) NOT NULL,
  `user_certificate_level` int(11) NOT NULL,
  `expiry_date` int(11) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modify_by` int(11) NOT NULL,
  `modify_date` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_user_certification_1_idx` (`user_id`) USING BTREE,
  KEY `fk_tbl_user_certification_2_idx` (`certification_id`) USING BTREE,
  CONSTRAINT `tbl_user_certification_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_user_certification_ibfk_2` FOREIGN KEY (`certification_id`) REFERENCES `tbl_certification` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;