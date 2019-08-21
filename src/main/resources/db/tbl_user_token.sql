USE `focus_test`;

DROP TABLE IF EXISTS `tbl_user_token`;

CREATE TABLE `tbl_user_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reset_password_token` varchar(255) DEFAULT NULL,
  `reset_password_expires` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `tbl_user_token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;