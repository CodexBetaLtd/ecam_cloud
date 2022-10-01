USE `focus_test`;

DROP TABLE IF EXISTS `tbl_maintainace`;

CREATE TABLE `tbl_maintainace` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `maintainace_type_id` int(11) DEFAULT NULL,
  `priorities_maintainace_id` int(11) DEFAULT NULL,
  `checklist_mainatinace_id` int(11) DEFAULT NULL,
  `tool_maintainace_id` int(11) DEFAULT NULL,
  `workorder_maintainace_id` int(11) DEFAULT NULL,
  `part_maintainace_id` int(11) DEFAULT NULL,
  `fk_asset_mainatanace_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;