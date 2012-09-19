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
import java.util.Vector;

import org.zkoss.util.logging.Log;
import org.zkoss.zul.AbstractChartModel;
import org.zkoss.zul.Chart;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.event.ChartDataEvent;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.event.ListDataListener;

/**
 * An XY data model implementation of {@link ExtXYModel}, which wrap on a
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
public class ListWrappedXYModel extends AbstractChartModel implements ExtXYModel {
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
	private String xTimeKey;
	// whether to shift or not points when adding new
	private boolean shift = false;

	
	/**
	 * @return the xTimeKey
	 */
	public String getXTimeKey() {
		return xTimeKey;
	}


	/**
	 * @param xTimeKey the xTimeKey to set
	 */
	public void setXTimeKey(String xTimeKey) {
		this.xTimeKey = xTimeKey;
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

	public Number getX(Comparable series, int index) {
		if (this.listModel == null)
			return null;
		Map data = (Map) this.listModel.getElementAt(index);
		Long x = null;
		if (data != null) {
			Object to = data.get(xTimeKey);
			if (to instanceof java.util.Date) {
				x = ((java.util.Date) to).getTime();
			} else if (to instanceof Long) {
				x = (Long) to;
			}
			return x;
		}
		return null;
	}

	public Number getY(Comparable series, int index) {
		if (this.listModel == null)
			return null;
		Map data = (Map) this.listModel.getElementAt(index);
		String dataName = (String) this._seriesMap.get(series);
		Number y = null;
		if (data != null && dataName != null) {
			Object to = data.get(dataName);
			if (to != null && to instanceof java.util.Date)
				y = ((java.util.Date) to).getTime();
			else if (to != null && !(to instanceof String))
				y = (Number) to;
			return y;
		}
		return null;
	}
	public Map getDataMap(Comparable series, int index) {
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
	    		&& !code.equals(xTimeKey)
	    		&& include) { 
	    		if (outData == null)
	    			outData = new HashMap();
	    		outData.put(en.getKey(),en.getValue());
	    	}
	    }
		return outData;
	}
	
	/**
	 * @return shift
	 */
	public boolean isShift() {
		return shift;
	}

	/**
	 * @param shift set to true if data are to be shifted
	 */
	public void setShift(boolean shift) {
		this.shift = shift;
	}

	private Map prepareEventData (Number x, Number y, Map data, int index) {
		Map map = new HashMap();
		map.put("index", index);
		if (x != null)
			map.put("x", x);
		if (y != null)
			map.put("y", y);
		if (data != null)
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
				prepareEventData(null, null, null, index));
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
		_seriesList.clear();
		fireEvent(ChartDataEvent.REMOVED, null, null);
	}
	public void clear(boolean clearStyles) {
		_seriesMap.clear();
		_seriesList.clear();
		if (clearStyles)
			_seriesStyleMap.clear();
		fireEvent(ChartDataEvent.REMOVED, null, null);
	}
	
	public void clearStyles() {
		_seriesStyleMap.clear();
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
					logger.warning("changeData failed reason: ",e);
				}
			}
			if (event.getType() == ListDataEvent.INTERVAL_ADDED) {
				try {
					addData(event.getIndex0(), event.getIndex1());
				} catch (Exception e) {
					logger.warning("addData failed reason: ",e);
				}
			}
			if (event.getType() == ListDataEvent.INTERVAL_REMOVED) {
				removeData(event.getIndex0(), event.getIndex1());
			}
		}
	}
	
	// we don't manage data change
	private void changeData(int startIdx, int endIdx) throws Exception {
		String dataName;
		Map data;
		//Map[] dataMapArray = new HashMap[this._seriesList.size()];
		List<Map> dataMapList = new Vector<Map>();
		for (int j = startIdx;j <= endIdx; j++) {
			data = (Map) this.listModel.getElementAt(j);
			//logger.info("changeData data: " + data.toString());
			Long x = null;
			
			for (int i=0;i < this._seriesList.size();i++) {
				dataName = (String) this._seriesMap.get(this._seriesList.get(i));
				//logger.info("changeData dataName: " + dataName);
				if (data.get(dataName) != null) {
					//dataMapArray[i] = new HashMap<String,Object>();
					Map dataMap = new HashMap<String,Object>();
					if (data.get(xTimeKey) instanceof Date) {
						x= ((Date)data.get(xTimeKey)).getTime();
						//dataMapArray[i].put("x",x);
						dataMap.put("x",x);
					} else if (data.get(xTimeKey) instanceof Long) {
						x = (Long) data.get(xTimeKey);
						dataMap.put("x",x);
					}else
						dataMap.put("x",data.get(xTimeKey));
					//if (data.get(dataName) != null)
					dataMap.put("y",data.get(dataName));
					dataMap.put("index", j);
					/*else
						dataMap.put("y",0);*/
					Map extData = getDataMap((Comparable)this._seriesList.get(i),j);
					/*if (extData != null)
						logger.info("changeData extData: " 
							+ extData.toString());
					else
						logger.info("changeData extData is null ");*/ 
					dataMap.put("data", extData);
					dataMap.put("series",(Comparable) this._seriesList.get(i));
					dataMapList.add(dataMap);
					//logger.info("changeData dataMap: " + dataMap.toString());
				}
			}
			String mode = "multi";
			Map[] dataMapArray = (Map[]) dataMapList.toArray(new Map[dataMapList.size()]);
			//logger.info("AAAAAAAAAAAAAAAAAaddData dataMapList.size(): " + dataMapList.size());
			fireEvent(ChartDataEvent.CHANGED, mode, dataMapArray);
		}
	}
	
	private void addData(int startIdx, int endIdx) throws Exception {
		String dataName;
		Map data;
		//Map[] dataMapArray = new HashMap[this._seriesList.size()];
		List<Map> dataMapList = new Vector<Map>();
		for (int j = startIdx;j <= endIdx; j++) {
			data = (Map) this.listModel.getElementAt(j);
			//logger.info("AAAAAAAAAAAAAAAAAaddData data: " + data.toString());
			Long x = null;
			
			for (int i=0;i < this._seriesList.size();i++) {
				dataName = (String) this._seriesMap.get(this._seriesList.get(i));
				//logger.info("AAAAAAAAAAAAAAAAAaddData dataName: " + dataName);
				if (data.get(dataName) != null) {
					//dataMapArray[i] = new HashMap<String,Object>();
					Map dataMap = new HashMap<String,Object>();
					if (data.get(xTimeKey) instanceof Date) {
						x= ((Date)data.get(xTimeKey)).getTime();
						//dataMapArray[i].put("x",x);
						dataMap.put("x",x);
					} else if (data.get(xTimeKey) instanceof Long) {
						x = (Long) data.get(xTimeKey);
						dataMap.put("x",x);
					}else
						dataMap.put("x",data.get(xTimeKey));
					//if (data.get(dataName) != null)
					dataMap.put("y",data.get(dataName));
					dataMap.put("index", j);
					/*else
						dataMap.put("y",0);*/
					Map extData = getDataMap((Comparable)this._seriesList.get(i),j);
					/*if (extData != null)
						logger.info("AAAAAAAAAAAAAAAAAaddData extData: " 
							+ extData.toString());
					else
						logger.info("AAAAAAAAAAAAAAAAAaddData extData is null ");*/ 
					dataMap.put("data", extData);
					dataMap.put("series",(Comparable) this._seriesList.get(i));
					dataMapList.add(dataMap);
					//logger.info("AAAAAAAAAAAAAAAAAaddData dataMap: " + dataMap.toString());
				}
			}
			String mode = "multi";
			if (this.shift) {
				mode = "multi_shift";
			}
			Map[] dataMapArray = (Map[]) dataMapList.toArray(new Map[dataMapList.size()]);
			//logger.info("AAAAAAAAAAAAAAAAAaddData dataMapList.size(): " + dataMapList.size());
			fireEvent(ChartDataEvent.ADDED, mode, dataMapArray);
		}
	}
	
	// we don't manage data removal
	private void removeData(int startIdx, int endIdx) {
		String series = "";
		for (int i=0;i < this._seriesList.size();i++) {
			series += this._seriesList.get(i) + ",";
		}
		Map data = new HashMap();
		data.put("startIdx", startIdx);
		data.put("endIdx", endIdx);
		if (series.length() > 1) {
			// remove last ,
			series = series.substring(0, series.length()-1);
			fireEvent(ChartDataEvent.REMOVED, series, data);
		}
	}


	@Override
	public void setValue(Comparable series, Number x, Number y, int index) {
		// do nothing, piloted by list model
	}


	@Override
	public void addValue(Comparable series, Number x, Number y) {
		// do nothing, piloted by list model
	}


	@Override
	public void addValue(Comparable series, Number x, Number y, int index) {
		// do nothing, piloted by list model
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

