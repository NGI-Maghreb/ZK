/* SimpleExtXYModel.java

	Purpose:
		
	Description:
		
	History:
		Thu Aug 14 11:30:51     2006, Created by henrichen

Copyright (C) 2006 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package org.ngi.zhighcharts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zkoss.util.logging.Log;
import org.zkoss.zul.AbstractChartModel;
import org.zkoss.zul.Chart;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.event.ChartDataEvent;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.event.ListDataListener;

/**
 * A wrapped interval data model implementation of {@link IntervalModel}, which wrap on a
 * ListModelList<Map> a list model list of Maps containing data.
 * A XY model is an N series of (X, Y, <data map>) data objects.
 * a client component using this wrapper model 
 * this model wrapper listen to source model change events, convert it, and forward it 
 * to all client components listening.
 * the mapping between the series and the data to use for it, 
 * is defined in a map where the series name is the key, 
 * the value contains the key of the data in the map element from list model , 
 * you can set some style data in a map for each series
 * 
 * 
 *
 * @author alain
 * @see ExtXYModel
 * @see Chart
 */
public class ListWrappedIntervalModel extends AbstractChartModel implements IntervalModel {
	private static final long serialVersionUID = 20121008182904L;
	protected  Log logger = Log.lookup(this.getClass());
	
	protected Map _seriesMap = new HashMap(10); //(series, data key in map)
	protected Map _seriesStyleMap = new HashMap(10); //(series, style)
	protected List _seriesList = new ArrayList(10);
	//(series, style)
	protected Map _seriesDataMap = new HashMap(); 
	
	protected ListModel listModel;
	private boolean _autoSort = true;
	private DataListener _dataListener;
	private String fromTimeKey;
	private String toTimeKey;

	// whether to shift or not points when adding new
	//private boolean shift = false;

	
	/**
	 * @return the xTimeKey
	 */
	public String getFromTimeKey() {
		return fromTimeKey;
	}


	/**
	 * @param xTimeKey the xTimeKey to set
	 */
	public void setFromTimeKey(String xTimeKey) {
		this.fromTimeKey = xTimeKey;
	}

	/**
	 * @return the xTimeKey
	 */
	public String getToTimeKey() {
		return toTimeKey;
	}


	/**
	 * @param xTimeKey the xTimeKey to set
	 */
	public void setToTimeKey(String xTimeKey) {
		this.toTimeKey = xTimeKey;
	}


	/** 
	 * @param seriesList the map of series to wrap in origin model
	 * the key in the series name, the value contains the key of data in model map
	 * to use with for the series Y
	 */
	public void setSeriesMap(Map seriesMap) {
		if (seriesMap != null && !seriesMap.isEmpty()) {
			this._seriesMap = seriesMap;
			// update internal series list
			this._seriesList = new ArrayList(this._seriesMap.keySet());
		}
	}

	/** 
	 * @param seriesList the map of origin data to wrap in origin model
	 * the key in the series name, the value contains the key of data in model map
	 * to use with for the series Y
	 */
	public void setSeriesDataMap(Map seriesDataMap) {
		if (seriesDataMap != null && !seriesDataMap.isEmpty()) {
			this._seriesDataMap = seriesDataMap;
		}
	}


	/**
	 * @return the origin list model
	 */
	public ListModel getListModel() {
		return this.listModel;
	}


	/**
	 * @param model the origin list model to set
	 */
	public void setListModel(ListModel model) {
		if (model != null) {
			if (this.listModel != model) {
				if (this.listModel != null)
					this.listModel.removeListDataListener(_dataListener);
				this.listModel = model;
				initDataListener(this.listModel);
				
			}
		}
	}

	private void initDataListener(ListModel model) {
		if (_dataListener == null) {
			_dataListener = new DataListener();
			model.addListDataListener(_dataListener);
		}
	}
	
	//-- ExtXYModel --//
	public Comparable getSeries(int index) {
		return (Comparable)_seriesList.get(index);
	}
	
	public Collection getSeries() {
		return _seriesList;
	}
	
	public int getDataCount(Comparable series) {
		return this.listModel != null ? this.listModel.getSize() : 0;
	}

	public Number getFrom(Comparable series, int index) {
		if (this.listModel == null)
			return null;
		Map data = (Map) this.listModel.getElementAt(index);
		Long x = null;
		if (data != null) {
			Object to = data.get(fromTimeKey);
			if (to instanceof java.util.Date) {
				x = ((java.util.Date) to).getTime();
			} else if (to instanceof Long) {
				x = (Long) to;
			}
			return x;
		}
		return null;
	}

	public Number getTo(Comparable series, int index) {
		if (this.listModel == null)
			return null;
		Map data = (Map) this.listModel.getElementAt(index);
		Number y = null;
		if (data != null) {
			Object to = data.get(toTimeKey);
			if (to instanceof java.util.Date) {
				y = ((java.util.Date) to).getTime();
			} else if (to instanceof Long) {
				y = (Long) to;
			}
			return y;
		}
		return null;
	}
	public Map getBandMap(Comparable series, int index) {
		if (this.listModel == null)
			return null;
		Map data = (Map) this.listModel.getElementAt(index);
		Map outData = null;
		// only data different from reference 
		// and needed for this series are added
		// from _seriesDataMap Map<code,"series0,series1,....">
		Iterator it = data.entrySet().iterator();
		boolean found = false;
	    boolean include;
	    while (it.hasNext()) {
	    	Map.Entry en = (Map.Entry)it.next();
	    	String code = (String) en.getKey();
	    	include = _seriesDataMap.get(code) != null 
	    		&& ((String)_seriesDataMap.get(code)).contains(series.toString()); 
	    	if (!code.equals(_seriesMap.get(series))
		    		&& !code.equals(fromTimeKey)
	    		&& !code.equals(toTimeKey)
	    		&& include) { 
	    		if (outData == null)
	    			outData = new HashMap();
	    		outData.put(en.getKey(),en.getValue());
	    	}
	    }
		return outData;
	}
	
	private Map prepareEventData (Comparable series, Number from, Number to, Map data, int index) {
		Map map = new HashMap();
		if (from != null)
			map.put("from", from);
		if (to != null)
			map.put("to", to);
		if (data == null) {
			data = new HashMap();
		}
		map.put("data", data);
		return map;
	}
	

	public void setAutoSort(boolean auto) {
		_autoSort = auto;
	}

	public boolean isAutoSort() {
		return _autoSort;
	}
	
	public void removeSeries(Comparable series) {
		_seriesMap.remove(series);
		_seriesList.remove(series);
		fireEvent(ChartDataEvent.REMOVED, series, null);
	}
	
	public void removeValue(Comparable series, int index) {
		removeValue0(series, index);
		fireEvent(ChartDataEvent.REMOVED, series,  
				prepareEventData(null, null, null, null, index));
	}
	
	private void removeValue0(Comparable series, int index) {
		List xyPairs = (List) _seriesMap.get(series);
		if (xyPairs == null) {
			return;
		}
		xyPairs.remove(index);
	}
	
	public void clear() {
		_seriesMap.clear();
		_seriesStyleMap.clear();
		_seriesList.clear();
		fireEvent(ChartDataEvent.REMOVED, null, null);
	}
	
	@Override
	public void addSeriesStyle(Comparable series, Map style) {
		_seriesStyleMap.put(series, style);
	}

	@Override
	public void setSeriesStyle(Comparable series, Map style) {
		_seriesStyleMap.put(series, style);
	}

	@Override
	public Map getSeriesStyle(Comparable series) {
		// get series style
		Map style = (Map) _seriesStyleMap.get(series);
		return style;
	}
	
	/**
	 * internal listener to model change events
	 *
	 */
	private class DataListener implements ListDataListener, Serializable {
		private static final long serialVersionUID = 20120120153302L;
		
		public void onChange(ListDataEvent event) {
			//logger.info("EEEEEEEEEEEEEEEEEE DataListener onChange event Type: " + event.getType());
			if (event.getType() == ListDataEvent.CONTENTS_CHANGED) {
				try {
					changeData(event.getIndex0(), event.getIndex1());
				} catch (Exception e) {
					logger.warning("changeData failed reason: " + e.toString());
				}
			}
			if (event.getType() == ListDataEvent.INTERVAL_ADDED) {
				try {
					addData(event.getIndex0(), event.getIndex1());
				} catch (Exception e) {
					logger.warning("addData failed reason: " + e.toString());
				}
			}
			if (event.getType() == ListDataEvent.INTERVAL_REMOVED) {
				removeData(event.getIndex0(), event.getIndex1());
			}
		}
	}
	
	// we don't manage data change
	private void changeData(int startIdx, int endIdx) throws Exception {
/*		String dataName;
		Map data;
		Map[] dataMapArray = new HashMap[this._seriesList.size()];
		for (int j = startIdx;j <= endIdx; j++) {
			data = (Map) this.listModel.getElementAt(j);
			
			for (int i=0;i < this._seriesList.size();i++) {
				dataName = (String) this._seriesMap.get(this._seriesList.get(i));
				dataMapArray[i] = new HashMap<String,Object>();
				dataMapArray[i].put("x",(Number) data.get(xTimeKey));
				dataMapArray[i].put("y",data.get(dataName));
				dataMapArray[i].put("series",(Comparable) this._seriesList.get(i));
			}
			String mode = "multi";
			if (this.shift) {
				mode = "multi_shift";
			}
			fireEvent(ChartDataEvent.CHANGED, mode, dataMapArray);
		}
*/	}
	
	private void addData(int startIdx, int endIdx) throws Exception {
		String dataName;
		Map data;
		Map[] dataMapArray = new HashMap[this._seriesList.size()];
		for (int j = startIdx;j <= endIdx; j++) {
			data = (Map) this.listModel.getElementAt(j);
			
			for (int i=0;i < this._seriesList.size();i++) {
				dataMapArray[i] = new HashMap<String,Object>();
				dataMapArray[i].put("from",data.get(fromTimeKey));
				dataMapArray[i].put("to",data.get(toTimeKey));
				dataMapArray[i].put("series",(Comparable) this._seriesList.get(i));
			}
			String mode = "multi";
			fireEvent(ChartDataEvent.ADDED, mode, dataMapArray);
		}
	}

	// we don't manage data removal
	private void removeData(int startIdx, int endIdx) {
//		for (int i = startIdx; i <= endIdx; i++) {
//		}
//		fireEvent(ChartDataEvent.REMOVED, null, null);
	}



	@Override
	public void setValue(Comparable series, Number x, Number y, Map data,
			int index) {
		// do nothing, piloted by list model
	}


	@Override
	public void addValue(Comparable series, Number x, Number y, Map data) {
		// do nothing, piloted by list model
	}


	@Override
	public void addValue(Comparable series, Number x, Number y, Map data,
			int index) {
		// do nothing, piloted by list model
	}
}

