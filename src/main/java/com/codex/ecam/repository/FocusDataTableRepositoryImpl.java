package com.codex.ecam.repository;
 
import static com.codex.ecam.repository.FocusDataTableUtils.getPageable;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class FocusDataTableRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements FocusDataTableRepository<T, ID> {


	public FocusDataTableRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

	@Override
	public DataTablesOutput<T> findAll(DataTablesInput input) {
		return findAll(input, null, null, null);
	}
	
	@Override
	public DataTablesOutput<T> findAll(DataTablesInput input, Specification<T> additionalSpecification) {
		return findAll(input, additionalSpecification, null, null);
	}

	@Override
	public DataTablesOutput<T> findAll(DataTablesInput input, Specification<T> additionalSpecification, Specification<T> preFilteringSpecification) {
		return findAll(input, additionalSpecification, preFilteringSpecification, null);
	}

	@Override
	public <R> DataTablesOutput<R> findAll(DataTablesInput input, Converter<T, R> converter) {
		return findAll(input, null, null, converter);
	}
	
	@Override
	public <R> DataTablesOutput<R> findAll(DataTablesInput input, Specification<T> additionalSpecification, Specification<T> preFilteringSpecification, Converter<T, R> converter) {
		return findAll(input, additionalSpecification, preFilteringSpecification, converter);
	} 

	@Override
	public DataTablesOutput<T> findAll(FocusDataTablesInput input) { 
		return findAll(input, null, null, null);
	}
	
	@Override
	public DataTablesOutput<T> findAll(FocusDataTablesInput input, Specification<T> additionalSpecification) {
		return findAll(input, additionalSpecification, null, null);
	}

	public <R> DataTablesOutput<R> findAll(FocusDataTablesInput input, Specification<T> additionalSpecification, Specification<T> preFilteringSpecification, Converter<T, R> converter) {
		DataTablesOutput<R> output = new DataTablesOutput<R>();
		output.setDraw(input.getDraw());
		if (input.getLength() == 0) {
			return output;
		}

		try {
			long recordsTotal = preFilteringSpecification == null ? count() : count(preFilteringSpecification);
			if (recordsTotal == 0) {
				return output;
			}
			output.setRecordsTotal(recordsTotal);

			Specification<T> specification = FocusSpecificationFactory.createSpecification(input);
			Page<T> data = findAll(Specifications.where(specification).and(additionalSpecification).and(preFilteringSpecification), getPageable(input));

			@SuppressWarnings("unchecked")
			List<R> content = converter == null ? (List<R>) data.getContent() : data.map(converter).getContent();
			output.setData(content);
			output.setRecordsFiltered(data.getTotalElements());

		} catch (Exception e) {
			output.setError(e.toString());
		}

		return output;
	}
}
