package com.codex.ecam.dao.biz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.biz.business.BusinessGroup;

@Repository
public interface BusinessGroupDao extends JpaRepository<BusinessGroup, Integer> {

}
