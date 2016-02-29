package com.shadow.concept.generics;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class GenericLazyDataModel<T extends GenericEntity> extends LazyDataModel<T> {

	private static final long serialVersionUID = 1L;

	private List<T> rows;

	protected GenericDAO<T> dao;

	private String fields;

	public GenericLazyDataModel(GenericDAO<T> dao) {
		this.dao = dao;
	}
	
	public GenericLazyDataModel(GenericDAO<T> dao, String fields) {
		this.dao = dao;
		this.fields = fields;
	}
	
	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		int dataSize = dao.count();
		this.setRowCount(dataSize);
		if (fields != null)
			rows = dao.paginate(first, pageSize, sortOrder, filters,fields);
		else 
			rows = dao.paginate(first, pageSize, sortOrder, filters);
		return rows;
	}

	@Override
	public Object getRowKey(T row) {
		return row.getId();
	}

	@Override
	public T getRowData(String playerId) {
		Integer id = Integer.valueOf(playerId);

		for (T row : rows) {
			if (id.equals(row.getId())) {
				return row;
			}
		}

		return null;
	}

}
