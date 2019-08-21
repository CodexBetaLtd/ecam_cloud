package com.neolith.focus.dao.app;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neolith.focus.model.app.RelatedApp;

@Repository
public interface RelatedAppDao extends JpaRepository<RelatedApp, Serializable> {

}
