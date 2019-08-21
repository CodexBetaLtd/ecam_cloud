USE `focus_test`;

DROP TABLE IF EXISTS `tbl_warranty`;

CREATE TABLE `tbl_warranty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) NOT NULL,
  `modified_date` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `date_of_purchase` date DEFAULT NULL,
  `date_of_warranty_start` date DEFAULT NULL,
  `date_of_warranty_end` date DEFAULT NULL,
  `date_manufacture` date DEFAULT NULL,
  `warranty_periode` int(11) DEFAULT NULL,
  `fk_supplier_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_warranty_1` (`fk_supplier_id`) USING BTREE,
  CONSTRAINT `tbl_warranty_ibfk_1` FOREIGN KEY (`fk_supplier_id`) REFERENCES `tbl_supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;