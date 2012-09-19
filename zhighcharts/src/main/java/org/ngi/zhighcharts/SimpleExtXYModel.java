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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.logging.Log;
import org.zkoss.zul.AbstractChartModel;
import org.zkoss.zul.Chart;
import org.zkoss.zul.event.ChartDataEvent;

/**
 * A extended XY data model implementation of {@link ExtXYModel}.
 * A XY model is an N series of (X, Y, <data map>) data objects.
 * in addition you can set some style data in a map for each series
 *
 * @author alain
 * @see ExtXYModel
 * @see Chart
 */
public class SimpleExtXYModel extends AbstractChartModel implements ExtXYModel {
	protected Log logger = Log.lookup(this.getClass());
	private static final long serialVersionUID = 20091008182904L;
	protected Map _seriesMap = new HashMap(13); //(series, XYPair)
	protected Map _seriesStyleMap = new HashMap(13); //(series, style)
	protected List _seriesList = new ArrayList(13);
	private boolean _autoSort = true;
	// whether to shift or not points when adding new
	private boolean shift = false;
	
	//-- ExtXYModel --//
	/* (non-Javadoc)
	 * @see org.ngi.zhighcharts.ExtXYModel#getSeries(int)
	 */
	public Comparable getSeries(int index) {
		return (Comparable)_seriesList.get(index);
	}
	
	public Collection getSeries() {
		return _seriesList;
	}
	
	/* (non-Javadoc)
	 * @see org.ngi.zhighcharts.ExtXYModel#getDataCount(java.lang.Comparable)
	 */
	public int getDataCount(Comparable series) {
		final List xyPairs = (List) _seriesMap.get(series);
		return xyPairs != null ? xyPairs.size() : 0;
	}

	public Number getX(Comparable series, int index) {
		final List xyPairs = (List) _seriesMap.get(series);
		
		if (xyPairs != null) {
			return ((XYPair)xyPairs.get(index)).getX();
		}
		return null;
	}

	public Number getY(Comparable series, int index) {
		final List xyPairs = (List) _seriesMap.get(series);
		
		if (xyPairs != null) {
			return ((XYPair)xyPairs.get(index)).getY();
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see org.ngi.zhighcharts.BandModel#getBandMap(java.lang.Comparable, int)
	 */
	@Override
	public Map getDataMap(Comparable series, int index) {
		final List pairs = (List) _seriesMap.get(series);
		
		if (pairs != null) {
			return ((XYPair)pairs.get(index)).getDataMap();
		}
		return null;
	}
	
	/**
	 * @return shift
	 */
	public boolean isShift() {
		return shift;
	}

	/**
	 * @param shift the shift to set
	 */
	public void setShift(boolean shift) {
		this.shift = shift;
	}

	private Map prepareEventData (Comparable series, Number x, Number y, Map data, int index) {
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
	
	public void setValue(Comparable series, Number x, Number y, Map data, int index) {
		removeValue0(series, index);
		addValue0(series, x, y, data, index);
		fireEvent(ChartDataEvent.CHANGED, series, 
				prepareEventData(series, x, y, data, index));
	}
	
	public void addValue(Comparable series, Number x, Number y, Map data) {
		addValue(series, x, y, data, -1);
		fireEvent(ChartDataEvent.ADDED, series,  
				prepareEventData(series, x, y, data, -1));
	}
	
	public void addValues(Map[] dataMapArray) {
		Comparable series;
		Number x; 
		Number y; 
		Map data, pointData;
		for (int i=0;i < dataMapArray.length;i++) {
			data = dataMapArray[i];
			series = (Comparable) data.get("series");
			x = (Number) data.get("x");
			y = (Number) data.get("y");
			pointData = (Map) data.get("data");
			addValue(series, x, y, pointData, -1);
		}
		String mode = "multi";
		if (this.shift) {
			mode = "multi_shift";
		}
		fireEvent(ChartDataEvent.ADDED, mode, dataMapArray);
	}
	

	public void addValue(Comparable series, Number x, Number y,Map data, int index) {
		addValue0(series, x, y, data, index);
		if (index >= 0)
			fireEvent(ChartDataEvent.CHANGED, series,  
				prepareEventData(series, x, y, data, index));
	}
	
	private void addValue0(Comparable series, Number x, Number y, Map data, int index) {
		List xyPairs = (List) _seriesMap.get(series);
		if (xyPairs == null) {
			xyPairs = new ArrayList(13);
			_seriesMap.put(series, xyPairs);
			_seriesList.add(series);
		}
		if (index >= 0)
			xyPairs.add(index, new XYPair(x, y, data));
		else
			xyPairs.add(new XYPair(x, y, data));
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
				prepareEventData(series, null, null, null, index));
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
	public void setValue(Comparable series, Number x, Number y, int index) {
		removeValue0(series, index);
		addValue0(series, x, y, null, index);
		fireEvent(ChartDataEvent.CHANGED, series,  
				prepareEventData(series, x, y, null, index));
	}
	
	public void addValue(Comparable series, Number x, Number y) {
		addValue(series, x, y, -1);
		fireEvent(ChartDataEvent.ADDED, series,  
				prepareEventData(series, x, y, null, -1));
	}
	
	public void addValue(Comparable series, Number x, Number y, int index) {
		addValue0(series, x, y, null, index);
		if (index >= 0)
			fireEvent(ChartDataEvent.CHANGED, series,  
					prepareEventData(series, x, y, null, index));
	}
	
	//-- internal class --//
	protected static class XYPair implements java.io.Serializable {
		private static final long serialVersionUID = 20091008182941L;
		private Number _x;
		private Number _y;
		private Map _data = null;
				
		protected XYPair(Number x, Number y, Map data) {
			_x = x;
			_y = y;
			_data = data;
		}
		
		public Number getX() {
			return _x;
		}
		
		public Number getY() {
			return _y;
		}
		
		public Map getDataMap() {
			return _data;
		}
	}
}
