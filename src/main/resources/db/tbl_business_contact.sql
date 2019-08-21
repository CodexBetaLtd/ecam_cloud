USE `focus_test`;

DROP TABLE IF EXISTS `tbl_business_contact`;

CREATE TABLE `tbl_business_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_business_id` int(11) DEFAULT NULL,
  `fk_user_id` int(11) DEFAULT NULL,
  `department` varchar(45) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_business_contact_1_idx` (`fk_user_id`) USING BTREE,
  KEY `fk_tbl_business_contact_2_idx` (`fk_business_id`) USING BTREE,
  CONSTRAINT `tbl_business_contact_ibfk_1` FOREIGN KEY (`fk_user_id`) REFERENCES `tbl_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_business_contact_ibfk_2` FOREIGN KEY (`fk_business_id`) REFERENCES `tbl_business` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION