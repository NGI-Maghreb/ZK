/* SimpleXYModel.java

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

import org.zkoss.zul.AbstractChartModel;
import org.zkoss.zul.Chart;
import org.zkoss.zul.event.ChartDataEvent;

/**
 * A Band data model implementation of {@link IntervalModel}.
 * A Band model is an N series of (from, to) data objects 
 * mainly to define some periods of time or some bands between to values.
 * you need tp set some style data in a map for each series to define
 * color, zIndex or xIndex to be used for band representation
 * 
 *
 * @author Alain G
 * @see IntervalModel
 * @see Chart
 */
public class SimpleIntervalModel extends AbstractChartModel implements IntervalModel {
	private static final long serialVersionUID = 20121008182904L;
	protected Map _seriesMap = new HashMap(13); //(series, Band)
	protected Map _seriesStyleMap = new HashMap(13); //(series, Band style)
	protected List _seriesList = new ArrayList(13);
	private boolean _autoSort = true;
	
	//-- IntervalModel --//
	public Comparable getSeries(int index) {
		return (Comparable)_seriesList.get(index);
	}
	
	public Collection getSeries() {
		return _seriesList;
	}
	
	public int getDataCount(Comparable series) {
		final List bands = (List) _seriesMap.get(series);
		return bands != null ? bands.size() : 0;
	}

	public Number getFrom(Comparable series, int index) {
		final List bands = (List) _seriesMap.get(series);
		
		if (bands != null) {
			return ((Band)bands.get(index)).getFrom();
		}
		return null;
	}

	public Number getTo(Comparable series, int index) {
		final List bands = (List) _seriesMap.get(series);
		
		if (bands != null) {
			return ((Band)bands.get(index)).getTo();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.ngi.zhighcharts.BandModel#getBandMap(java.lang.Comparable, int)
	 */
	@Override
	public Map getBandMap(Comparable series, int index) {
		final List bands = (List) _seriesMap.get(series);
		
		if (bands != null) {
			return ((Band)bands.get(index)).getDataMap();
		}
		return null;
	}
	
	/* prepare data for event
	 * 
	 */
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

	public void setValue(Comparable series, Number from, Number to, Map data, int index) {
		removeValue0(series, index);
		addValue0(series, from, to, data, index);
		fireEvent(ChartDataEvent.CHANGED, series, 
				prepareEventData(series, from, to, data, index));
	}
	
	public void addValue(Comparable series, Number from, Number to, Map data) {
		addValue(series, from, to, data, -1);
	}
	
	public void addValue(Comparable series, Number from, Number to, Map data, int index) {
		addValue0(series, from, to, data, index);
		if (index < 0)
			index = this.getDataCount(series) -1;
		fireEvent(ChartDataEvent.ADDED, series, 
				prepareEventData(series, from, to, data, index));
	}
	
	private void addValue0(Comparable series, Number from, Number to, Map data, int index) {
		List bands = (List) _seriesMap.get(series);
		if (bands == null) {
			bands = new ArrayList(13);
			_seriesMap.put(series, bands);
			_seriesList.add(series);
		}
		if (index >= 0)
			bands.add(index, new Band(from, to, data));
		else
			bands.add(new Band(from, to, data));
	}

	public void setAutoSort(boolean auto) {
		_autoSort = auto;
	}

	public boolean isAutoSort() {
		return _autoSort;
	}
	
	public void removeSeries(Comparable series) {
		_seriesMap.remove(series);
		_seriesStyleMap.remove(series);
		_seriesList.remove(series);
		fireEvent(ChartDataEvent.REMOVED, series, null);
	}
	
	public void removeValue(Comparable series, int index) {
		if (index >= this.getDataCount(series))
			return;
		Number from = this.getFrom(series, index);
		Number to = this.getTo(series, index);
		removeValue0(series, index);
		fireEvent(ChartDataEvent.REMOVED, series,  
				prepareEventData(series, from, to, null, index));
	}
	
	private void removeValue0(Comparable series, int index) {
		List bands = (List) _seriesMap.get(series);
		if (bands == null) {
			return;
		}
		bands.remove(index);
	}
	
	public void clear() {
		_seriesMap.clear();
		_seriesStyleMap.clear();
		_seriesList.clear();
		fireEvent(ChartDataEvent.REMOVED, null, null);
	}
	
	public void clearData() {
		_seriesMap.clear();
		_seriesList.clear();
		fireEvent(ChartDataEvent.REMOVED, null, null);
	}
	
	/* (non-Javadoc)
	 * @see org.ngi.zhighcharts.BandModel#addSeriesStyle(java.lang.Comparable, java.util.Map)
	 */
	@Override
	public void addSeriesStyle(Comparable series, Map style) {
		_seriesStyleMap.put(series, style);
	}

	/* (non-Javadoc)
	 * @see org.ngi.zhighcharts.BandModel#setSeriesStyle(java.lang.Comparable, java.util.Map)
	 */
	@Override
	public void setSeriesStyle(Comparable series, Map style) {
		_seriesStyleMap.put(series, style);
	}

	/* (non-Javadoc)
	 * @see org.ngi.zhighcharts.BandModel#getSeriesStyle(java.lang.Comparable)
	 */
	@Override
	public Map getSeriesStyle(Comparable series) {
		// get series style
		Map style = (Map) _seriesStyleMap.get(series);
		return style;
	}

	//-- internal class --//
	protected static class Band implements java.io.Serializable {
		private static final long serialVersionUID = 20121008182941L;
		private Number _from;
		private Number _to;
		private Map _data = null;
		protected Band(Number x, Number y) {
			_from = x;
			_to = y;
		}
		
		protected Band(Number x, Number y, Map data) {
			_from = x;
			_to = y;
			_data = data;
		}
		
		public Number getFrom() {
			return _from;
		}
		
		public Number getTo() {
			return _to;
		}
		
		public Map getDataMap() {
			return _data;
		}
	}
}
