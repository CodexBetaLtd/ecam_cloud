USE `focus_test`;

DROP TABLE IF EXISTS `tbl_workorder`;

CREATE TABLE `tbl_workorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modify_by` int(11) NOT NULL,
  `modify_date` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `fk_wo_status` int(11) DEFAULT NULL,
  `fk_wo_group_id` int(11) DEFAULT NULL,
  `fk_project_id` int(11) DEFAULT NULL,
  `fk_priority_id` int(11) DEFAULT NULL,
  `fk_workorder_request_id` int(11) DEFAULT NULL,
  `fk_assign_user_id` int(11) DEFAULT NULL,
  `wo_number` varchar(45) DEFAULT NULL,
  `work_instruction` varchar(45) DEFAULT NULL,
  `estimated_completion_date` datetime DEFAULT NULL,
  `actual_completion_date` datetime DEFAULT NULL,
  `estimated_labor_hours` double DEFAULT NULL,
  `actual_labor_hours` double DEFAULT NULL,
  `completion_note` varchar(45) DEFAULT NULL,
  `problem_description` varchar(45) DEFAULT NULL,
  `problem_root_couse` varchar(45) DEFAULT NULL,
  `problem_solution` varchar(45) DEFAULT NULL,
  `admin_note` varchar(45) DEFAULT NULL,
  `tbl_workOrdercol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_workorder_1_idx` (`fk_wo_status`) USING BTREE,
  KEY `fk_tbl_workorder_2_idx` (`fk_wo_group_id`) USING BTREE,
  KEY `fk_tbl_workorder_3_idx` (`fk_project_id`) USING BTREE,
  KEY `fk_tbl_workorder_4_idx` (`fk_priority_id`) USING BTREE,
  KEY `fk_tbl_workorder_5_idx` (`fk_workorder_request_id`) USING BTREE,
  KEY `fk_tbl_workorder_6_idx` (`fk_assign_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT=' 