package com.codex.ecam.repository;

import static org.springframework.data.jpa.datatables.repository.DataTablesUtils.ATTRIBUTE_SEPARATOR;
import static org.springframework.data.jpa.datatables.repository.DataTablesUtils.ESCAPED_ATTRIBUTE_SEPARATOR;
import static org.springframework.data.jpa.datatables.repository.DataTablesUtils.ESCAPED_OR_SEPARATOR;
import static org.springframework.data.jpa.datatables.repository.DataTablesUtils.ESCAPE_CHAR;
import static org.springframework.data.jpa.datatables.repository.DataTablesUtils.OR_SEPARATOR;
import static org.springframework.data.jpa.datatables.repository.DataTablesUtils.getLikeFilterValue;
import static org.springframework.data.jpa.datatables.repository.DataTablesUtils.isBoolean;

import java.util.Arrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class FocusSpecificationFactory {

	public static <T> Specification<T> createSpecification(final FocusDataTablesInput input) {
		return new DataTablesSpecification<T>(input);
	}

	private static class DataTablesSpecification<T> implements Specification<T> {

		private final FocusDataTablesInput input;

		public DataTablesSpecification(FocusDataTablesInput input) {
			this.input = input;
		}

		@Override
		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			Predicate predicate = cb.conjunction();
			Expression<Boolean> booleanExpression;
			Expression<String> stringExpression;
			Expression<?> enumExpression;

			// check for each searchable column whether a filter value exists
			for (FocusColumn column : input.getColumns()) {
				String filterValue = column.getSearch().getValue();
				boolean isColumnSearchable = column.getSearchable() && StringUtils.hasText(filterValue);
				boolean isColumnEnum = column.getIsEnum();
				if (!isColumnSearchable) {
					continue;
				}

				if (filterValue.contains(OR_SEPARATOR)) {
					// the filter contains multiple values, add a 'WHERE .. IN' clause
					String[] values = filterValue.split(ESCAPED_OR_SEPARATOR);
					if ((values.length > 0) && isBoolean(values[0])) {
						Object[] booleanValues = new Boolean[values.length];
						for (int i = 0; i < values.length; i++) {
							booleanValues[i] = Boolean.valueOf(values[i]);
						}
						booleanExpression = getExpression(root, column.getData(), Boolean.class);
						predicate = cb.and(predicate, booleanExpression.in(booleanValues));
					} else {
						stringExpression = getExpression(root, column.getData(), String.class);
						predicate = cb.and(predicate, stringExpression.in(Arrays.asList(values)));
					}
				} else {
					// the filter contains only one value, add a 'WHERE .. LIKE' clause
					if (isBoolean(filterValue)) {
						booleanExpression = getExpression(root, column.getData(), Boolean.class);
						predicate = cb.and(predicate, cb.equal(booleanExpression, Boolean.valueOf(filterValue)));
					} else if (isColumnEnum) {
						enumExpression = getEnumExpression(root, column.getData());
						Enum<?> enumFilter = getEnumSearchValue(root, column.getData(), filterValue); 
						predicate = cb.and(predicate, cb.equal(enumExpression, enumFilter));
					} else {
						stringExpression = getExpression(root, column.getData(), String.class);
						predicate = cb.and(predicate, cb.like(cb.lower(stringExpression), getLikeFilterValue(filterValue), ESCAPE_CHAR));
					}
				}
			}

			// check whether a global filter value exists
			String globalFilterValue = input.getSearch().getValue();
			if (StringUtils.hasText(globalFilterValue)) {
				Predicate matchOneColumnPredicate = cb.disjunction();
				// add a 'WHERE .. LIKE' clause on each searchable column
				for (FocusColumn column : input.getColumns()) {
					if (column.getSearchable()) {
						if (!column.getIsEnum()) {
							Expression<String> expression = getExpression(root, column.getData(), String.class);
							matchOneColumnPredicate = cb.or(matchOneColumnPredicate, cb.like(cb.lower(expression), getLikeFilterValue(globalFilterValue), ESCAPE_CHAR));
						}
					}
				}
				predicate = cb.and(predicate, matchOneColumnPredicate);
			}
			// findAll method does a count query first, and then query for the actual data. Yet in the
			// count query, adding a JOIN FETCH results in the following error 'query specified join
			// fetching, but the owner of the fetched association was not present in the select list'
			// see https://jira.spring.io/browse/DATAJPA-105
			boolean isCountQuery = query.getResultType() == Long.class;
			if (isCountQuery) {
				return predicate;
			}
			// add JOIN FETCH when necessary
			for (FocusColumn column : input.getColumns()) {
				boolean isJoinable = column.getSearchable() && column.getData().contains(ATTRIBUTE_SEPARATOR);
				if (!isJoinable) {
					continue;
				}
				String[] values = column.getData().split(ESCAPED_ATTRIBUTE_SEPARATOR);
				PersistentAttributeType type = root.getModel().getAttribute(values[0]).getPersistentAttributeType();
				if ((type != PersistentAttributeType.ONE_TO_ONE) && (type != PersistentAttributeType.MANY_TO_ONE)) {
					continue;
				}
				Fetch<?, ?> fetch = null;
				for (int i = 0; i < (values.length - 1); i++) {
					fetch = (fetch == null ? root : fetch).fetch(values[i], JoinType.LEFT);
				}
			}
			return predicate;
		}
	}

	//get filter value as Enum
	private static Enum<?> getEnumSearchValue (Root<?> root, String columnData, String filterValue){
		if (!columnData.contains(ATTRIBUTE_SEPARATOR)) {
			if (root.get(columnData).getJavaType().isEnum()) {
				for (Object enums : root.get(columnData).getJavaType().getEnumConstants()) {
					Enum<?> searchEnum = ( (Enum<?>) enums );
					if (searchEnum.name().equals(filterValue)) {
						return searchEnum;
					}
				}
			}
		}

		// columnData is like "joinedEntity.enum" so add a join clause
		String[] values = columnData.split(ESCAPED_ATTRIBUTE_SEPARATOR);
		From<?, ?> from = root;
		for (int i = 0; i < (values.length - 1); i++) {
			from = from.join(values[i], JoinType.LEFT);
		}

		// check the filter value is contain in enum
		if (from.get(values[values.length - 1]).getJavaType().isEnum()) {
			for (Object enums : from.get(values[values.length - 1]).getJavaType().getEnumConstants()) {
				Enum<?> searchEnum = ( (Enum<?>) enums );
				if (searchEnum.name().equals(filterValue)) {
					return searchEnum;
				}
			}
		}
		return null;
	}

	//get Enum expression
	private static Expression<?> getEnumExpression(Root<?> root, String columnData) {
		if (!columnData.contains(ATTRIBUTE_SEPARATOR)) {
			Class<?> classType = root.get(columnData).getJavaType();
			if (root.get(columnData).getJavaType().isEnum()) {
				return root.get(columnData).as(classType);
			}
		}

		// columnData is like "joinedEntity.enum" so add a join clause
		String[] values = columnData.split(ESCAPED_ATTRIBUTE_SEPARATOR);
		From<?, ?> from = root;
		for (int i = 0; i < (values.length - 1); i++) {
			from = from.join(values[i], JoinType.LEFT);
		}
		Class<?> classType = from.get(values[values.length -1]).getJavaType();
		return from.get(values[values.length - 1]).as(classType);
	}

	private static <S> Expression<S> getExpression(Root<?> root, String columnData, Class<S> clazz) {

		if (!columnData.contains(ATTRIBUTE_SEPARATOR)) {
			return root.get(columnData).as(clazz);
		}
		// columnData is like "joinedEntity.attribute" so add a join clause
		String[] values = columnData.split(ESCAPED_ATTRIBUTE_SEPARATOR);
		if (root.getModel().getAttribute(values[0]).getPersistentAttributeType() == PersistentAttributeType.EMBEDDED) {
			// with @Embedded attribute
			return root.get(values[0]).get(values[1]).as(clazz);
		}
		From<?, ?> from = root;
		for (int i = 0; i < (values.length - 1); i++) {
			from = from.join(values[i], JoinType.LEFT);
		}
		return from.get(values[values.length - 1]).as(clazz);
	}

}
