USE `focus_test`;

DROP TABLE IF EXISTS ` tbl_misc_cost_type`;

CREATE TABLE ` misc_cost_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;