/* IntervalModel.java

	Purpose: interval chart data model.
		
	Description:
 * 		to represent [time] interval series
 * 		can be used to represent a Gantt diagram
 * 		of tasks
		

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package org.ngi.zhighcharts;

import java.util.Collection;
import java.util.Map;

import org.zkoss.zul.Chart;
import org.zkoss.zul.ChartModel;
import org.zkoss.zul.SimpleXYModel;

/**
 * A interval chart data model.
 * to represent [time] interval series
 * can be used to represent a Gantt diagram
 * of tasks
 *
 * @author Alain G
 * @see Chart
 * @see SimpleIntervalModel
 */	
public interface IntervalModel extends ChartModel {

	/**
	 * Get a series of the specified index;
	 */
	public Comparable getSeries(int index);
	
	/**
	 * Get all series as a collection.
	 */
	public Collection getSeries();
	
	/**
	 * Get data count of a specified series.
	 * @param series the specified series.
	 */
	public int getDataCount(Comparable series);

	/**
	 * Get from value of a specified series and data index.
	 * @param series the series.
	 * @param index the data index.
	 */
	public Number getFrom(Comparable series, int index);

	/**
	 * Get to value of a specified series and band index.
	 * @param series the series.
	 * @param index the data index.
	 */
	public Number getTo(Comparable series, int index);
	
	/**
	 * Get data map of a specified series and band index.
	 * @param series
	 * @param index
	 * @return 
	 */
	public Map getBandMap(Comparable series, int index);

	/**
	 * Replace the value of the new (from,to) into a series at specified index.
	 * @param series the series
	 * @param from the start value.
	 * @param to the end value.
	 * @param index the data index
	 */
	public void setValue(Comparable series, Number from, Number to, Map data, int index);
	
	/**
	 * Append an (x,y) into a series.
	 * @param series the series.
	 * @param from the start value.
	 * @param to the end value.
	 */	
	public void addValue(Comparable series, Number from, Number to, Map data);

	/**
	 * Add an (x,y) into a series at specified index.
	 * @param series the series.
	 * @param from the start value.
	 * @param to the end value.
	 * @param index the data index.
	 */	
	public void addValue(Comparable series, Number from, Number to, Map data, int index);
	
	/**
	 * Set model to autosort on x value for each series.
	 */
	public void setAutoSort(boolean auto);

	/**
	 * check whether to autosort on x value for each series; default is true.
	 */
	public boolean isAutoSort();
	
	/**
	/**
	 * Remove data of a specified series.
	 * @param series the series
	 */
	public void removeSeries(Comparable series);

	/**
	 * Remove (x,Y) value of a specified series and data index.
	 * @param series the series.
	 * @param index the data index.
	 */	
	public void removeValue(Comparable series, int index);

	/**
	 * clear this model.
	 */	
	public void clear();
	
	/**
	 * Add a style Map for a series
	 * something like
	 * color: 'rgba(68, 213, 170, 0.3)',
	 * label: {
     *   style: {
     *      color: '#606060'
     *   }
	 * @param series
	 * @param style
	 */
	public void addSeriesStyle(Comparable series, Map style);
	
	
	/**
	 * Replace a style Map for a series
	 * something like
	 * color: 'rgba(68, 213, 170, 0.3)',
	 * label: {
     *   style: {
     *      color: '#606060'
     *   }
	 * @param series
	 * @param style
	 */
	public void setSeriesStyle(Comparable series, Map style);
	
	public Map getSeriesStyle(Comparable series);
}	
