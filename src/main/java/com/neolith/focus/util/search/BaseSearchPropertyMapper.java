package com.neolith.focus.util.search;

import java.util.ArrayList;
import java.util.List; 
import org.springframework.data.jpa.datatables.mapping.Search;

import com.neolith.focus.repository.FocusColumn;
import com.neolith.focus.repository.FocusDataTablesInput;

public abstract class BaseSearchPropertyMapper {

	private List<FocusColumn> extraColumns = new ArrayList<>();
	private FocusColumn currentColumn;

	protected abstract void mapSearchParamsToPropertyParams(String column);

	public void generateDataTableInput(FocusDataTablesInput input) {

		for (FocusColumn column : input.getColumns()) {

			if (column.getSearchable() || column.getOrderable()) {
				currentColumn = column;
				mapSearchParamsToPropertyParams(currentColumn.getData());
			}
		}

		input.getColumns().addAll(extraColumns);
		clearData();
	}	

	protected void addColumns(String... properties) {
		int index = 0;		
		for (String data : properties) {
			if (index == 0) {
				currentColumn.setData(data);
			} else {
				extraColumns.add(createExtraColumn(data));
			}
			index++;
		}
	}

	private FocusColumn createExtraColumn(String data) {
		FocusColumn col = new FocusColumn();
		col.setData(data);
		col.setSearchable(true);
		col.setOrderable(true);
		col.setName("");
		col.setSearch(getDefaultSearch());

		return col;
	}

	protected Search getDefaultSearch() {
		Search search = new Search();
		search.setRegex(false);
		search.setValue("");

		return search;
	}
	
	private void clearData() {
		extraColumns.clear();
		currentColumn = null;
	}

}
