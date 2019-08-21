USE `focus_test`;

DROP TABLE IF EXISTS `tbl_wo_task_asset(???)`;

CREATE TABLE `tbl_wo_task_asset(???)` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modify_by` int(11) NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1; KEY (`fk_wo_id`) REFERENCES `tbl_workorder` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;