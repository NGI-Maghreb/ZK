package org.ngi.zhighcharts;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.lang.Objects;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.au.out.AuInvoke;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.ChartModel;
import org.zkoss.zul.Div;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.XYModel;
import org.zkoss.zul.event.ChartDataEvent;
import org.zkoss.zul.event.ChartDataListener;


/**
 * this class is a wrapper component for Hichcharts JS library
 * API doc ref see:
 * http://www.highcharts.com/ref/
 * usage Javascript:
 * http://www.highcharts.com/documentation/how-to-use
 * 
 * it works with chart models (@link ChartModel)
 * you can use several models (@link ExtXYModel) for plotting data in series
 * you can use a chart model (@link IntervalModel) to plot band intervals or lines 
 * on X or Y axis
 * 
 * 
 * @author alain
 *
 */
public class ZHighCharts extends Div implements EventListener {

	/* member fields */
	protected  Log logger = Log.lookup(this.getClass());

	private String _title; // chart main title
	private String _subTitle; // chart subtitle
	private String _type; // global chart type
	private String _options; // for chart options
	private String _legend; // for chart legend
	private String plotOptions; // for global chart plot options
	private String _pane; // for polar charts and angular gauges
	private String tooltipOptions;
	private String titleOptions;
	private String subtitleOptions;
	private String _yAxisTitle;
	private String _xAxisTitle;
	private String _tooltipFormatter;
	private String _exportURL = "http://export.highcharts.com";//Default URL
	private String _exporting = null;

	private LinkedList seriesList;
	private Map<Comparable,Object> seriesOptions = new HashMap<Comparable,Object>();
	private String xAxisOptions = null;
	private String yAxisOptions = null;
	private boolean inverted = false;
	private String pointClickCallback = null;
	private String clickCallback = null;

	// models for the chart
	private ChartModel model;
	private IntervalModel xBandModel;
	private IntervalModel yBandModel;
	private ChartDataListener _dataListener;
	private ChartDataListener _xBandListener;
	private ChartDataListener _yBandListener;
	// multi model listener list
	private List<ChartDataListener> _dataListenerList;
	// multi model (one Y axis per model)
	private List<ChartModel> modelList;
	private boolean modelRendered = false;
	
	public ZHighCharts() {
		super();
	}
	
	private void initDataListener(ChartModel model) {
		if (_dataListener == null) {
			_dataListener = new SeriesDataListener();
			model.addChartDataListener(_dataListener);
		}
	}
	private void initXBandDataListener(ChartModel model) {
		if (_xBandListener == null) {
			_xBandListener = new BandDataListener();
			model.addChartDataListener(_xBandListener);
		}
	}
	private void initYBandDataListener(ChartModel model) {
		if (_yBandListener == null) {
			_yBandListener = new BandDataListener();
			((BandDataListener)_yBandListener).setYBand(true);
			model.addChartDataListener(_yBandListener);
		}
	}

	private void addDataListener(ChartModel model) {
		if (model != null) {
			if (_dataListenerList == null)
				_dataListenerList = new Vector<ChartDataListener>();

			ChartDataListener dataListener = new SeriesDataListener();
			_dataListenerList.add(dataListener);
			model.addChartDataListener(dataListener);
		}
	}
	
	/**
	 * internal listener to model change events for series
	 *
	 */
	private class SeriesDataListener implements ChartDataListener, Serializable {
		private static final long serialVersionUID = 20120120153002L;

		public void onChange(ChartDataEvent event) {
			if (!modelRendered)
				return;
			boolean multi = false;
			String seriesS="";
			String data = "";
			if (event.getData() != null && event.getType() != ChartDataEvent.REMOVED) {
				if (event.getData() instanceof Map) {
					Map dataMap = (Map) event.getData();
					if (!inverted)
						dataMap.put("id","" + event.getSeries() + dataMap.get("x"));
					else
						dataMap.put("id","" + event.getSeries() + dataMap.get("y"));
					Map otherData = (Map) dataMap.get("data");
					if (otherData != null) {
						Iterator it = otherData.entrySet().iterator();
						while(it.hasNext()) {
							Map.Entry en = (Map.Entry)it.next();
							dataMap.put(en.getKey(), en.getValue());
						}
						if(dataMap.containsKey("marker")) {
							((Map)dataMap.get("marker")).put("enabled", true);
						}
					}
					dataMap.remove("data");
					data = JSONObject.toJSONString(dataMap);
				} else if (event.getData() instanceof Map[]) {
					multi = true;
					Map[] dataMapArray = (Map[]) event.getData();
					data = "[";
					for (int i=0;i<dataMapArray.length;i++) {
						if (!inverted)
							dataMapArray[i].put("id","" 
									+ dataMapArray[i].get("series") 
									+ dataMapArray[i].get("x"));
						else
							dataMapArray[i].put("id","" 
									+ dataMapArray[i].get("series") 
									+ dataMapArray[i].get("y"));
						Map otherData = (Map) dataMapArray[i].get("data");
						if (otherData != null) {
							Iterator it = otherData.entrySet().iterator();
							while(it.hasNext()) {
								Map.Entry en = (Map.Entry)it.next();
								dataMapArray[i].put(en.getKey(), en.getValue());
							}
							if(dataMapArray[i].containsKey("marker"))
								((Map)dataMapArray[i].get("marker")).put("enabled", true);
						}
						dataMapArray[i].remove("data");
						data += JSONObject.toJSONString(dataMapArray[i]) + ",";
					}
					if (dataMapArray.length > 0)
						data = data.substring(0, data.length() -1);
					data += "]";
				}
			} else if (event.getData() != null 
					&& event.getType() == ChartDataEvent.REMOVED){
				// removal it's a special data with startIndex & endIndex
				Map dataMap = (Map) event.getData();
				if (dataMap != null)
					data = JSONObject.toJSONString(dataMap);
				String[] series = ((String)event.getSeries()).split(",");
				List seriesList = new Vector();
				for (String s: series)
					seriesList.add(s);
				seriesS = JSONArray.toJSONString(seriesList);
			}
			if (event.getType() == ChartDataEvent.ADDED 
					&& !data.isEmpty()) {
				if (multi && event.getSeries().equals("multi"))
					addPoints(data, false);
				else if (multi && event.getSeries().equals("multi_shift"))
					addPoints(data, true);
				else 
					addPoint(event.getSeries(), data);
			} else if (event.getType() == ChartDataEvent.CHANGED 
					&& !data.isEmpty()) {
				if (multi && event.getSeries().equals("multi"))
					changePoints(event.getSeries(), data);
				else
					changePoint(event.getSeries(), data);
			} else if (event.getType() == ChartDataEvent.REMOVED 
					&& !data.isEmpty() && !seriesS.isEmpty()) {
				removePoint(seriesS, data);
			}
		}
	}	
	private void addPoint(Comparable series, String data){
		response(null, new AuInvoke(this, "addPoint", 
				new Object[] {series, data}));
	}
	private void addPoints(String data, boolean shift){
		response(null, new AuInvoke(this, "addPoints", data, shift));
	}
	private void removePoint(String series, String data){
		response(null, new AuInvoke(this, "removePoints", 
				new Object[] {series, data}));
	}
	private void changePoint(Comparable series, String data){
		response(null, new AuInvoke(this, "changePoint", 
				new Object[] {series, data}));
	}
	private void changePoints(Comparable series, String data){
		response(null, new AuInvoke(this, "changePoints", 
				new Object[] {data}));
	}

	
	public void zoomOut(){
		response(null, new AuInvoke(this, "zoomOut", true));
	}

	/**
	 * internal listener to model change events
	 *
	 */
	private class BandDataListener implements ChartDataListener, Serializable {
		private static final long serialVersionUID = 20120120153002L;
		private boolean isYBand = false;
		public void setYBand(boolean set) {
			this.isYBand = set;
		}
		public void onChange(ChartDataEvent event) {
			if (!modelRendered)
				return;
			boolean multi = false;
			String data = "";
			ChartModel model = event.getModel();
			if (event.getData() != null) {
				if (event.getData() instanceof Map) {
					Map dataMap = (Map) event.getData();
					Comparable series = event.getSeries();
					dataMap.put("id", "" + series + dataMap.get("from"));
					if (event.getType() != ChartDataEvent.REMOVED) {
						Map inData = (Map) dataMap.get("data");
						if (inData == null) {
							inData = new HashMap();
							dataMap.put("data", inData);
						}
						inData.put("name", series);
						Map style = ((SimpleIntervalModel)model).getSeriesStyle(series);
						if (style != null) {
							String color = (String) style.get("color");
							if (color != null)
								dataMap.put("color", color);
							Integer zIndex = (Integer) style.get("zIndex");
							if (zIndex != null)
								dataMap.put("zIndex", zIndex);
							Boolean noMouseEvents = (Boolean) style.get("noMouseEvents");
							if (noMouseEvents != null && noMouseEvents)
								dataMap.put("noMouseEvents", true);
							Boolean isLine =(Boolean) style.get("isLine");
							if (isLine != null && isLine) {							
								dataMap.put("value", dataMap.get("from"));
								dataMap.put("width", dataMap.get("to"));
								dataMap.remove("from");
								dataMap.remove("to");
							}
						}
					}
					data = JSONObject.toJSONString(dataMap);
				} 
				else if (event.getData() instanceof Map[]) {
					multi = true;
					Map[] dataMapArray = (Map[]) event.getData();
					data = "[";
					for (int i=0;i<dataMapArray.length;i++) {
						data += JSONObject.toJSONString(dataMapArray[i]) + ",";
					}
					data = data.substring(0, data.length() -1);
					data += "]";
				}
			}
			if (event.getType() == ChartDataEvent.ADDED) {
					addBand(event.getSeries(), data, this.isYBand);
			} else if (event.getType() == ChartDataEvent.CHANGED) {
				changeBand(event.getSeries(), data, this.isYBand);
			} else if (event.getType() == ChartDataEvent.REMOVED) {
				if (event.getSeries() != null)
					removeBand(event.getSeries(), data, this.isYBand);
				else {
					// no series clear all
					clearBands(isYBand);
				}
			}
		}
	}	
	private void addBand(Comparable series, String data, boolean isYBand){
		response(null, new AuInvoke(this, "addBand", 
				new Object[] {series, data, isYBand}));
	}
	private void removeBand(Comparable series, String data, boolean isYBand){
		response(null, new AuInvoke(this, "removeBand", 
				new Object[] {series, data, isYBand}));
	}
	private void changeBand(Comparable series, String data, boolean isYBand){
		response(null, new AuInvoke(this, "changeBand", 
				new Object[] {series, data, isYBand}));
	}
	public void clearBands(boolean isYBand){
		response(null, new AuInvoke(this, "clearBands", 
				new Object[] {isYBand}));
	}


	public String getOptions() {
		return _options;
	}

	/**
	 * method to set chart global options 
	 * @param options a json formatted string
	 * such as: "{marginRight: 140, marginLeft: 60, zoomType: 'x'}"
	 * see HichCharts JS documentation http://www.highcharts.com/ref/
	 * 
	 */
	public void setOptions(String options) {
		if (!Objects.equals(_options, options)) {
			_options = options;
			options = options.replaceAll(" ", "");			
//			if (options.contains("inverted:true"))
//				inverted = true;
			smartUpdate("options", _options);
		}
	}
	
	/**
	 * @return the legend
	 */
	public String getLegend() {
		return _legend;
	}

	/**
	 * @param legend the chart legend as json object
	 */
	public void setLegend(String legend) {
		if (!Objects.equals(_options, legend)) {
			this._legend = legend;
			smartUpdate("legend", legend);
		}
	}

	/**
	 * @return the plotOptions
	 */
	public String getPlotOptions() {
		return plotOptions;
	}

	/**
	 * @param plotOptions the plotOptions json object for the chart
	 */
	public void setPlotOptions(String plotOptions) {
		if (!Objects.equals(this.plotOptions, plotOptions)) {
			this.plotOptions = plotOptions;
			smartUpdate("plotOptions", this.plotOptions);
		}
	}

	/**
	 * @return the tooltipOptions
	 */
	public String getTooltipOptions() {
		return tooltipOptions;
	}

	/**
	 * @param tooltipOptions the tooltipOptions to set
	 */
	public void setTooltipOptions(String tooltipOptions) {
		if (!Objects.equals(this.tooltipOptions, tooltipOptions)) {
			this.tooltipOptions = tooltipOptions;
			smartUpdate("tooltipOptions", this.tooltipOptions);
		}
	}


	/**
	 * @return the titleOptions
	 */
	public String getTitleOptions() {
		return titleOptions;
	}

	/**
	 * @param titleOptions the titleOptions to set
	 */
	public void setTitleOptions(String titleOptions) {
		if (!Objects.equals(this.titleOptions, titleOptions)) {
			this.titleOptions = titleOptions;
			smartUpdate("titleOptions", this.titleOptions);
		}
	}


	/**
	 * @return the subtitleOptions
	 */
	public String getSubtitleOptions() {
		return subtitleOptions;
	}

	/**
	 * @param subtitleOptions the subtitleOptions to set
	 */
	public void setSubtitleOptions(String subtitleOptions) {
		if (!Objects.equals(this.subtitleOptions, subtitleOptions)) {
			this.subtitleOptions = subtitleOptions;
			smartUpdate("subtitleOptions", this.subtitleOptions);
		}
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		if (!Objects.equals(_title, title)) {
			_title = title;
			smartUpdate("title", _title);
		}
	}

	/**
	 * @return the yAxisTitle
	 */
	public String getYAxisTitle() {
		return _yAxisTitle;
	}

	/**
	 * @param _yAxisTitle the yAxisTitle to set
	 */
	public void setYAxisTitle(String axisTitle) {
		if (!Objects.equals(this._yAxisTitle, axisTitle)) {
			this._yAxisTitle = axisTitle;
			smartUpdate("yAxisTitle", this._yAxisTitle);
		}
	}

	/**
	 * @return the xAxisTitle
	 */
	public String getXAxisTitle() {
		return _xAxisTitle;
	}

	/**
	 * @param _xAxisTitle the xAxisTitle to set
	 */
	public void setXAxisTitle(String axisTitle) {
		if (!Objects.equals(this._xAxisTitle, axisTitle)) {
			this._xAxisTitle = axisTitle;
			smartUpdate("xAxisTitle", this._xAxisTitle);
		}
	}
	/**
	 * @return the xAxisOptions
	 */
	public String getxAxisOptions() {
		return xAxisOptions;
	}

	/**
	 * @param xAxisOptions the xAxisOptions to set
	 */
	public void setxAxisOptions(String xAxisOptions) {
		this.xAxisOptions = xAxisOptions;
	}

	/**
	 * @return the yAxisOptions
	 */
	public String getyAxisOptions() {
		return yAxisOptions;
	}

	/**
	 * @param yAxisOptions the yAxisOptions to set
	 */
	public void setyAxisOptions(String yAxisOptions) {
		this.yAxisOptions = yAxisOptions;
	}
	
	/**
	 * @param yAxisOptions the yAxisOptions to set
	 */
	public void setyAxisOptions(List yAxisOptions) {
		
		this.yAxisOptions = yAxisListofMapToString(yAxisOptions);
	}
		
	/**
	 * converts a list of maps to a string in JSON format
	 * @param list
	 * @return
	 */
	private String yAxisListofMapToString(List list){
		
		String result = "[";
		Iterator itr = list.iterator(); 
		while(itr.hasNext()) {

		    Object element = itr.next(); 
		    if(element instanceof Map ){
		    	result +=  JSONObject.toJSONString((Map) element) + ",";
		    	
		    }
		    	
		}
		result += "]";
		//Cleanup
		result = result.replace(",]", "]");
		result = result.replace(",}", "}");
		//return result
		return result;
	}

	/**
	 * @return the subTitle
	 */
	public String getSubTitle() {
		return _subTitle;
	}

	/**
	 * @param subTitle the subTitle to set
	 */
	public void setSubTitle(String subTitle) {
		if (!Objects.equals(this._subTitle, subTitle)) {
			this._subTitle = subTitle;
			smartUpdate("subtitle", this._subTitle);
		}
	}

	/**
	 * @return type
	 */
	public String getType() {
		return _type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		if (!Objects.equals(_type, type)) {
			_type = type;
			smartUpdate("type", _type);
		}
	}

	/**
	 * @return the exportURL
	 */
	public String getExportURL() {
		return _exportURL;
	}

	/**
	 * @param exportURL the exportURL to set
	 */
	public void setExportURL(String exportURL) {
		if (!Objects.equals(this._exportURL, exportURL)) {
			this._exportURL = exportURL;
			smartUpdate("exportURL", this._exportURL);
		}
	}

	/**
	 * @return the tooltipFormatter
	 */
	public String getTooltipFormatter() {
		return _tooltipFormatter;
	}

	/**
	 * @param tooltipFormatter the tooltipFormatter to set
	 */
	public void setTooltipFormatter(String tooltipFormatter) {
		if (!Objects.equals(this._tooltipFormatter, tooltipFormatter)) {
			this._tooltipFormatter = tooltipFormatter;
			smartUpdate("tooltipFormatter", this._tooltipFormatter);
		}
	}
	
	

	/**
	 * @return the pointClickCallback
	 */
	public String getPointClickCallback() {
		return pointClickCallback;
	}

	/**
	 * @param pointClickCallback the pointClickCallback to set
	 */
	public void setPointClickCallback(String pointClickCallback) {
		if (!Objects.equals(this.pointClickCallback, pointClickCallback)) {
			
			this.pointClickCallback = "function pointClick(obj)" + pointClickCallback;
			smartUpdate("pointClickCallback", this.pointClickCallback);
		}
	}

	/**
	 * @return the clickCallback
	 */
	public String getClickCallback() {
		return clickCallback;
	}

	/**
	 * @param clickCallback the pointClickCallback to set
	 */
	public void setClickCallback(String clickCallback) {
		if (!Objects.equals(this.clickCallback, clickCallback)) {
			
			this.clickCallback = "function chartClick(obj)" + clickCallback;
			smartUpdate("clickCallback", this.clickCallback);
		}
	}

	/**
	 * @return the seriesStyles
	 */
	public Map<Comparable, Object> getSeriesOptions() {
		return seriesOptions;
	}

	public Map getSeriesOptions(Comparable series) {
		return (Map) seriesOptions.get(series);
	}
	/**
	 * @param seriesStyles the seriesStyles to set
	 */
	public void setSeriesOptionsMap(Map seriesStyles) {
		this.seriesOptions = seriesStyles;
	}
	
	public void setSeriesOptions(Comparable series, Map style) {
		this.seriesOptions.put(series, style);
	}
	

	/**
	 * render properties at component creation time
	 * @see org.zkoss.zul.Div#renderProperties(org.zkoss.zk.ui.sys.ContentRenderer)
	 */
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer)
	throws java.io.IOException {
		super.renderProperties(renderer);
		render(renderer, "title", _title);
		render(renderer, "tooltipFormatter", _tooltipFormatter);
		render(renderer, "subtitle", _subTitle);
		render(renderer, "xAxisTitle", _xAxisTitle);
		render(renderer, "yAxisTitle", _yAxisTitle);
		render(renderer, "xAxisOptions", xAxisOptions);
		render(renderer, "yAxisOptions", yAxisOptions);
		render(renderer, "options", _options);
		render(renderer, "tooltipOptions", tooltipOptions);
		render(renderer, "titleOptions", titleOptions);
		render(renderer, "subtitleOptions", subtitleOptions);
		render(renderer, "plotOptions", plotOptions);
		render(renderer, "legend", _legend);
		render(renderer, "pointClickCallback", pointClickCallback);
		render(renderer, "clickCallback", clickCallback);
		render(renderer, "pane", _pane);
		render(renderer, "exportURL", _exportURL);
		render(renderer, "exporting", _exporting);
			
		if (model != null && modelList == null) {// single model
			render(renderer, "series",getJSONSeries(modelToJsonList(getModel())));
		} else {
			// multi model
			String seriesJSON = "";
			List modelList = null;
			// principal model
			if (model != null) {
				modelList = modelToJsonList(getModel());
			}
			// models from list
			if (this.modelList != null && this.modelList.size() > 0) {
				if (modelList == null)
					modelList = new Vector();
				for (ChartModel model: this.modelList) {
					List seriesList = modelToJsonList(model);
					// add to global list
					for (Object obj: seriesList) {
						modelList.add(obj);
					}
				}
			}
			render(renderer, "series", getJSONSeries(modelList));
		}
		if (getxBandModel() != null) {
			render(renderer, "xPlotBands", getJSONBands(bandModelToJsonList(getxBandModel())));
		}
		if (getyBandModel() != null) {
			render(renderer, "yPlotBands", getJSONBands(bandModelToJsonList(getyBandModel())));
		}
		

		render(renderer, "type", _type);
		
		modelRendered = true;
	}

	/**
	 * The default zclass is "z-ZHighCharts"
	 */
	public String getZclass() {
		return (this._zclass != null ? this._zclass : "z-ZHighCharts");
	}
	
	/**
	 * internal method to prepare series data into a list of JSON objects string
	 * @param model
	 * @return
	 */
	private List modelToJsonList(ChartModel model){
		LinkedList list = new LinkedList();

		if(model != null && _type != null){
			if("pie".equals(_type)) {	//Draw PieChart
				PieModel tempModel = (PieModel)model;
				for(int i = 0, nCategories = tempModel.getCategories().size(); i < nCategories; i++){
					Comparable category = tempModel.getCategory(i);
					String json = "{name:'" + category + "',y:" + tempModel.getValue(category);
					Map options = getSeriesOptions(category);
					if(options != null) {
						String jsonData = JSONObject.toJSONString(options);
						jsonData = jsonData.substring(1, jsonData.length()-1);
						jsonData = jsonData.replaceAll("\"'", "'");
						jsonData = jsonData.replaceAll("'\"", "'");
						json += "," + jsonData;
					}
					json += "}";
					list.add(json); 
				}
			} else if("bar".equals(_type) || "line".equals(_type) || "area".equals(_type) || "areaspline".equals(_type) || "gauge".equals(_type) 
					|| "spline".equals(_type) || "scatter".equals(_type) || "column".equals(_type)) {
				// category model
				if (model instanceof CategoryModel) {
					CategoryModel tempModel = (CategoryModel)model;
					for (int j = 0,  nSeries = tempModel.getSeries().size(); j < nSeries; j++) {
						Comparable series = tempModel.getSeries(j);
						LinkedList dataList = new LinkedList();
						Map<String,Object> serieMap = new HashMap<String,Object>();
						for (int i = 0, nCategories = tempModel.getCategories().size(); i < nCategories; i++) {
							Comparable category = tempModel.getCategory(i);
							String json = "" + tempModel.getValue(series, category);
							dataList.add(json);
						}
						
						serieMap.put("name", series.toString());
						serieMap.put("data", dataList);
						// add series options
						Map options = getSeriesOptions(series);
						if (options != null)
							serieMap.put("options", options);
						list.add(serieMap);
					}
					
				} else if (model instanceof XYModel) {//XY Model (several series of xy values
					XYModel xyModel = (XYModel) model;
					boolean found = false;
					ExtXYModel extModel = null;
					if (model instanceof ExtXYModel) {
						extModel = (ExtXYModel) model;
					}
					for (int j = 0,  nSeries = xyModel.getSeries().size(); j < nSeries; j++) {
						Comparable series = xyModel.getSeries(j);
						LinkedList dataList = new LinkedList();
						Map<String,Object> serieMap = new HashMap<String,Object>();
						for (int i = 0, dataCount = xyModel.getDataCount(series); i < dataCount; i++) {
							String json;
							if (xyModel.getX(series, i) != null 
								&& xyModel.getY(series, i) != null) {
								if (inverted)
									json = "{y:" + xyModel.getX(series, i) 
										+ ",x:"	+ xyModel.getY(series, i)
										+ ",id:'" + series + xyModel.getY(series, i) + "'";
								else
									json = "{x:" + xyModel.getX(series, i) 
										+ ",y:"	+ xyModel.getY(series, i)
										+ ",id:'" + series + xyModel.getX(series, i) + "'";
								// if extended model add data defined for point
								if (extModel != null) {
									Map data = extModel.getDataMap(series, i);
									if (data != null) {
										// work around for Date in json object
										found = dateJsonFix(data);
										String jsonData = JSONObject.toJSONString(data);
										// remove {}
										jsonData = jsonData.substring(1, jsonData.length()-1);
										json += "," + jsonData;
									}
								}
								json += "}";
								dataList.add(json);
							}
						}
						serieMap.put("name", series.toString());
						serieMap.put("data", dataList);

						// add series options
						Map options = getSeriesOptions(series);
						if (options != null){
							serieMap.put("options", options);
						}
						list.add(serieMap);
					}
				} else if (model instanceof IntervalModel) {//Band Model applied to series
					IntervalModel eventModel = (IntervalModel) model;
					for (int j = 0,  nSeries = eventModel.getSeries().size(); j < nSeries; j++) {
						Comparable series = eventModel.getSeries(j);
						LinkedList dataList = new LinkedList();
						Map<String,Object> serieMap = new HashMap<String,Object>();
						Map<String,Object> serieStyle = eventModel.getSeriesStyle(series);
						for (int i = 0, dataCount = eventModel.getDataCount(series); i < dataCount; i++) {
							String json = "{low:" + eventModel.getFrom(series, i) 
										+ ",y:" + eventModel.getTo(series, i);
							
							if (serieStyle.containsKey("xIndex"))
								json += ",x:" + serieStyle.get("xIndex");
							else
								json += ",x:" + j;
							
							json += ",name:'" + series.toString() + "'";
							if (serieStyle.containsKey("color"))
								json += ",color:'" + serieStyle.get("color") + "'";
							// add other data
							Map otherData = eventModel.getBandMap(series, i);
							if (otherData != null) {
								String jsonData = JSONObject.toJSONString(otherData);
								// remove {}
								jsonData = jsonData.substring(1, jsonData.length()-1);
								json += "," + jsonData;
							}
							json += "}";
							dataList.add(json);
						}
						serieMap.put("name", series.toString());
						serieMap.put("data", dataList);
						// add series options
						Map options = getSeriesOptions(series);
						if (options != null)
							serieMap.put("options", options);
						list.add(serieMap);
					}
				}
			} else if(_type.startsWith("stackbar")){		//Draw StackedBarChart
				// TODO rework this
				seriesList = new LinkedList();
				CategoryModel tempModel = (CategoryModel)model;
				for(int i = 0, nCategories = tempModel.getCategories().size(); i < nCategories; i++){
					Comparable category = tempModel.getCategory(i);					
					JSONObject jData = new JSONObject();
					jData.put("verticalField", category);
					for(int j = 0; j < seriesList.size(); j++){
						Comparable series = tempModel.getSeries(j);	
						JSONObject temp = (JSONObject) seriesList.get(j);						
						jData.put(temp.get("xField"), tempModel.getValue(series, category));
					}
					list.add(jData);					
				}
			} else if(_type.startsWith("stackcolumn")){		//Draw StackedColumnChart 
				
				if  (model instanceof XYModel) {
										
					XYModel xyModel = (XYModel) model;
					boolean found = false;
					ExtXYModel extModel = null;
					if (model instanceof ExtXYModel) {
						extModel = (ExtXYModel) model;
					}
					for (int j = 0, nSeries = xyModel.getSeries().size(); j < nSeries; j++) {
						Comparable series = xyModel.getSeries(j);
						LinkedList dataList = new LinkedList();
						Map<String, Object> serieMap = new HashMap<String, Object>();
						for (int i = 0, dataCount = xyModel.getDataCount(series); i < dataCount; i++) {
							String json;
							if (xyModel.getX(series, i) != null && xyModel.getY(series, i) != null) {
								if (inverted)
									json = "[" + xyModel.getX(series, i) + "," + xyModel.getY(series, i) +  "]";
								else
									json = "[" + xyModel.getX(series, i) + "," + xyModel.getY(series, i) +  "]";
								
								dataList.add(json);
							}
						}
						serieMap.put("name", series.toString());
						serieMap.put("stack", 410);
						serieMap.put("data", dataList);

						// add series options
						Map options = getSeriesOptions(series);
						if (options != null) {
							serieMap.put("options", options);
						}
						list.add(serieMap);
					}
				}
				
				//Because there is no such type called stackcolumn, thats why
				_type = "column";
			}
		};
		
		return list;
	}
	
	private boolean dateJsonFix(Map data) {
		Iterator it = data.entrySet().iterator();
		boolean found = false;
	    while (it.hasNext()) {
	    	Map.Entry pairs = (Map.Entry)it.next();
	    	if (pairs.getValue() instanceof java.util.Date) {
	    		data.put(pairs.getKey(), pairs.getValue().toString());
	    		found= true;
	    	}
		}
    	return found;
	}
	
	/**
	 * internal method to convert a series list to a json array string
	 * @param list representing series
	 * @return
	 */
	private String getJSONSeries(List list) {
		// list may be null.
		if (list == null) return "";
		
	    final StringBuffer sb = new StringBuffer().append('[');
	    if (list.size() > 0 && list.get(0) instanceof Map) {
		    for (Iterator it = list.iterator(); it.hasNext();) {
		    	Map serieMap = (Map) it.next();
		    	List dataList = (List) serieMap.get("data");
		    	String name = (String) serieMap.get("name");
		    	Map options = (Map) serieMap.get("options");
		    	sb.append("{data:[");
			    for (Iterator datait = dataList.iterator(); datait.hasNext();) {
			    	String s = String.valueOf(datait.next());
		            sb.append(s).append(",");
			    }
		    	if (dataList.size() > 0)			    
		    		sb.deleteCharAt(sb.length() - 1);
			    sb.append("],");
			    if (options != null) {
			    	String jsonOptions = JSONObject.toJSONString(options);
			    	jsonOptions = jsonOptions.substring(1, jsonOptions.length()-1);
			    	sb.append(jsonOptions + ",");
			    }
			    if(serieMap.get("stack") != null){
			    	int stack = (Integer) serieMap.get("stack");
			    	sb.append("stack:" + stack + ",");
			    }
			    if (name != null)
			    	sb.append("name:'" + name + "'},");
		    }
		    sb.deleteCharAt(sb.length() - 1);
		    sb.append("]");
	    } else if (list.size() > 0 && list.get(0) instanceof String) { // assume string meaning only one serie
	    	sb.append("{data:[");
		    for (Iterator it = list.iterator(); it.hasNext();) {
		    	String s = String.valueOf(it.next());
	            sb.append(s).append(",");
		    }
		    sb.deleteCharAt(sb.length() - 1);
		    sb.append("]");
		    if("pie".equals(_type)) {
		    	sb.append(",type: 'pie'");
		    }
		    if (!this.seriesOptions.isEmpty()) {
		    	//this.seriesOptions.
		    }
		    sb.append("}]");
	    }
	    if (list.size() > 0) {
		    return sb.toString().replaceAll("\\\\", "");
	    } else return "";
	}

	private List bandModelToJsonList(IntervalModel model){
		if (model == null) 
			return null;
		if (model.getSeries().isEmpty())
			return null;
		LinkedList list = new LinkedList();
		Map data = null;
		String color = null;
		Boolean isLine = false;
		IntervalModel bandModel = (IntervalModel) model;
		for (int j = 0,  nSeries = bandModel.getSeries().size(); j < nSeries; j++) {
			Comparable series = bandModel.getSeries(j);
			Map style = bandModel.getSeriesStyle(series);
			if (style != null) {
				color = (String) style.get("color");
				isLine = (Boolean) style.get("isLine");
			} else {
				color = "#ffffff";
			}
			for (int i = 0, dataCount = bandModel.getDataCount(series); i < dataCount; i++) {
				data = bandModel.getBandMap(series, i);
				if (data == null)
					data = new HashMap();
				// add at least series name to band data
				data.put("name", series.toString());
				String jsonData = JSONObject.toJSONString(data);
				String json = "{";
				if (isLine != null && isLine) {
					json += "value:" + bandModel.getFrom(series, i) 
					+ ",width:" + bandModel.getTo(series, i);
				} else {
					json += "from:" + bandModel.getFrom(series, i) 
							+ ",to:" + bandModel.getTo(series, i);
				}
				json += ",color:'" + color + "'"
						+ ",id:'" + series + bandModel.getFrom(series, i) + "'";
				if (style != null && style.containsKey("zIndex")) {
					json +=  ",zIndex:" + style.get("zIndex");
				}
				if (style != null && style.containsKey("noMouseEvents")) {
					json +=  ",noMouseEvents:" + style.get("noMouseEvents");
				}
				if (jsonData != null)
					json += ",data:" + jsonData;
				json += "}";
				list.add(json);
			}
		}
		return list;
	}
	
	private String getJSONBands(List list) {
		if (list == null) return "";

		final StringBuffer sb = new StringBuffer().append('[');
	    for (Iterator it = list.iterator(); it.hasNext();) {
	    	String s = String.valueOf(it.next());
            sb.append(s).append(",");
	    }
	    if (list.size() > 0)
	    	sb.deleteCharAt(sb.length() - 1);
	    sb.append("]");
	    return sb.toString().replaceAll("\\\\", "");
	}
	
	

	/* (non-Javadoc)
	 * @see org.zkoss.zk.ui.event.EventListener#onEvent(org.zkoss.zk.ui.event.Event)
	 */
	@Override
	public void onEvent(Event arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/** Sets the chart model associated with this chart.
	 * If a non-null model is assigned, no matter whether it is the same as
	 * the previous, it will always cause re-render.
	 *
	 * @param model the chart model to associate, or null to dis-associate
	 * any previous model.
	 * @exception UiException if failed to initialize with the model
	 */
	public void setModel(ChartModel model) {
		if(this.model != model){
			if (this.model != null) {
				this.model.removeChartDataListener(_dataListener);
			}
			this.model = model;
			initDataListener(this.model);
			invalidate();		//Always redraw
		}		
	}
	
	/** add a chart model associated with this chart.
	 * If a non-null model is assigned, no matter whether it is the same as
	 * the previous, it will always cause re-render.
	 *
	 * @param model the chart model to associate, 
	 * @exception UiException if failed to initialize with the model
	 */
	public void addModel(ChartModel model) {
		if(model != null){
			if (this.modelList == null)
				this.modelList = new Vector();
			this.modelList.add(model);
			addDataListener(model);
			invalidate();		//Always redraw
		}		
	}
	
	/**
	 * Returns the model of chart.
	 */
	public ChartModel getModel() {
		return this.model;
	}

	/**
	 * @return the xBandModel
	 */
	public IntervalModel getxBandModel() {
		return xBandModel;
	}

	/**
	 * @param xBandModel the xBandModel to set
	 */
	public void setxBandModel(IntervalModel xBandModel) {
		if (xBandModel != null) {
			this.xBandModel = xBandModel;
			initXBandDataListener(this.xBandModel);
			invalidate();		//Always redraw
		}
	}

	/**
	 * @return the yBandModel
	 */
	public IntervalModel getyBandModel() {
		return yBandModel;
	}

	/**
	 * @param yBandModel the yBandModel to set
	 */
	public void setyBandModel(IntervalModel yBandModel) {
		if (yBandModel != null) {
			this.yBandModel = yBandModel;
			initYBandDataListener(this.yBandModel);
			invalidate();		//Always redraw
		}
	}
	
	/**
	 * @return the exporting
	 */
	public String getExporting() {
		return _exporting;
	}

	/**
	 * @param exporting the exporting to set
	 */
	public void setExporting(String exporting) {
		if (!Objects.equals(_exporting, exporting)) {
			this._exporting = exporting;
			smartUpdate("exporting", _exporting);
		}
	}
	

	/**
	 * @return the pane
	 */
	public String getPane() {
		return _pane;
	}

	/**
	 * @param pane the pane to set
	 */
	public void setPane(String pane) {
		if (!Objects.equals(_pane, pane)) {
			this._pane = pane;
			smartUpdate("pane", _pane);
		}
	}

	public void setGaugeValue(int series,double val){
		response(null, new AuInvoke(this, "updateGaugeValue", series, val));
	}
}