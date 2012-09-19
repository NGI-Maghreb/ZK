/* ExtXYModel.java

	Purpose:
		
	Description:
		
	History:
		extension of XYModel interface

Copyright (C) 2006 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package org.ngi.zhighcharts;

import java.util.Collection;
import java.util.Map;

import org.zkoss.zul.Chart;
import org.zkoss.zul.XYModel;

/**
 * An extended XY chart data model.
 * 1/ each series can have some options (styling or other) stored as a map
 * 2/ each series xy point can also have some options data (styling or other) stored as a map
 *
 * @author alain
 * @see Chart
 * @see SimpleExtXYModel
 */	
public interface ExtXYModel extends XYModel {
	/**
	 * Get data map of a specified series and index.
	 * @param series
	 * @param index
	 * @return 
	 */
	public Map getDataMap(Comparable series, int index);


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
	 * Get X value of a specified series and data index.
	 * @param series the series.
	 * @param index the data index.
	 */
	public Number getX(Comparable series, int index);

	/**
	 * Get Y value of a specified series and data index.
	 * @param series the series.
	 * @param index the data index.
	 */
	public Number getY(Comparable series, int index);

	/**
	 * Replace the value of the new (x,y) into a series at specified index.
	 * @param series the series
	 * @param x the x value
	 * @param y the y value
	 * @param index the data index
	 * @since 5.0.0
	 */
	public void setValue(Comparable series, Number x, Number y, Map data, int index);
	
	/**
	 * Append an (x,y) into a series.
	 * @param series the series.
	 * @param x the x value.
	 * @param y the y value.
	 */	
	public void addValue(Comparable series, Number x, Number y, Map data);

	/**
	 * Add an (x,y) into a series at specified index.
	 * @param series the series.
	 * @param x the x value.
	 * @param y the y value.
	 * @param index the data index.
	 * @since 5.0.0
	 */	
	public void addValue(Comparable series, Number x, Number y, Map data, int index);
	
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
