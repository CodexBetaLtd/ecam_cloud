package com.codex.ecam.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order; 
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.repository.DataTablesUtils; 

public class FocusDataTableUtils extends DataTablesUtils {

	public static boolean isEnum(String filterValue) {
		return false;
	}

	/**
	 * Creates a 'LIMIT .. OFFSET .. ORDER BY ..' clause for the given {@link DataTablesInput}.
	 * 
	 * @param input the {@link DataTablesInput} mapped from the Ajax request
	 * @return a {@link Pageable}, must not be {@literal null}.
	 */
	 public static Pageable getPageable(FocusDataTablesInput input) {
		 List<Order> orders = new ArrayList<Order>();
		 for (org.springframework.data.jpa.datatables.mapping.Order order : input.getOrder()) {
	     FocusColumn column = input.getColumns().get(order.getColumn());
		     if (column.getOrderable()) {
			     String sortColumn = column.getData();
			     Direction sortDirection = Direction.fromString(order.getDir());
			     orders.add(new Order(sortDirection, sortColumn));
			 }
		 }
		 Sort sort = orders.isEmpty() ? null : new Sort(orders);
	
		 if (input.getLength() == -1) {
			 input.setStart(0);
			 input.setLength(Integer.MAX_VALUE);
		 }
		 return new DataTablesPageRequest(input.getStart(), input.getLength(), sort);
	 }
	  
	private static class DataTablesPageRequest implements Pageable {

		private final int offset;
		private final int pageSize;
		private final Sort sort;

		public DataTablesPageRequest(int offset, int pageSize, Sort sort) {
			this.offset = offset;
			this.pageSize = pageSize;
			this.sort = sort;
		}

		@Override
		public int getOffset() {
			return offset;
		}

		@Override
		public int getPageSize() {
			return pageSize;
		}

		@Override
		public Sort getSort() {
			return sort;
		}

		@Override
		public Pageable next() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Pageable previousOrFirst() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Pageable first() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasPrevious() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getPageNumber() {
			throw new UnsupportedOperationException();
		}
	}
}
