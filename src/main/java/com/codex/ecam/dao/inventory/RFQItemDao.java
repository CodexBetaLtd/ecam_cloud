package com.codex.ecam.dao.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.rfq.RFQItem;

@Repository
public interface RFQItemDao extends JpaRepository<RFQItem, Integer> {

}
