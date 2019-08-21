package com.neolith.focus.repository;

import java.io.Serializable; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface FocusDataTableRepository<T, ID extends Serializable> extends DataTablesRepository<T, ID> {

	DataTablesOutput<T> findAll(FocusDataTablesInput input);

	DataTablesOutput<T> findAll(FocusDataTablesInput input, Specification<T> additionalSpecification);
	
}
