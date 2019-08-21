USE `focus_test`;

DROP TABLE IF EXISTS `tbl_parts`;

CREATE TABLE `tbl_parts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) NOT NULL,
  `modified_date` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `fk_warranty_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_part_1` (`fk_warranty_id`) USING BTREE,
  CONSTRAINT `tbl_parts_ibfk_1` FOREIGN KEY (`fk_warranty_id`) REFERENCES `tbl_warranty` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;