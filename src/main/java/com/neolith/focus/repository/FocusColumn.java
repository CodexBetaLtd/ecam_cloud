package com.neolith.focus.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.datatables.mapping.Search;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FocusColumn {
 
	/**
     * Column's data source
     * @see http://datatables.net/reference/option/columns.data
	 */
	@NotBlank
	private String data;
	/**
     * Column's name
     * @see http://datatables.net/reference/option/columns.name
	 */
	private String name;
	/**
     * Flag to indicate if this column is searchable (true) or not (false).
     * @see http://datatables.net/reference/option/columns.searchable
	 */
	@NotNull
	private Boolean searchable;
	/**
     * Flag to indicate if this column is orderable (true) or not (false).
     * @see http://datatables.net/reference/option/columns.orderablet lombok.Data;

	 */
	@NotNull
	private Boolean orderable;
	/**
	 * Search value to apply to this specific column.
	 */
	@NotNull
	private Search search;
	/**
	 * Flag to indicate if this column is enum (true) or not (false).
	 */
	@NotNull
	private Boolean isEnum = false;

    public FocusColumn(String columnName, String string, boolean searchable, boolean orderable, Search search, boolean isEnum) {

    }

	/**
	 * Set the search value to apply to this column
	 *
	 * @param searchValue
	 *            if any, the search value to apply
	 */
	public void setSearchValue(String searchValue) { 
		this.search.setValue(searchValue); 
	}

}
