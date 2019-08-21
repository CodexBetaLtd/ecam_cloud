package com.neolith.focus.dao.inventory;

import com.neolith.focus.model.inventory.rfq.RFQItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RFQItemDao extends JpaRepository<RFQItem, Integer> {

}
