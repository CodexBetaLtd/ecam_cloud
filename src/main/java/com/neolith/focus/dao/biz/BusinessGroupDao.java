package com.neolith.focus.dao.biz;

import com.neolith.focus.model.biz.business.BusinessGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessGroupDao extends JpaRepository<BusinessGroup, Integer> {

}
