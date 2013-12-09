package ctrl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ngi.zhighcharts.SimpleExtXYModel;
import org.ngi.zhighcharts.ZGauge;
import org.ngi.zhighcharts.ZHighCharts;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.SimplePieModel;
import org.zkoss.zul.Window;

public class DemoComposer extends Window implements EventListener {
	protected Log logger = Log.lookup(this.getClass());

	//Basic Line	
	private ZHighCharts chartComp;
	private SimpleExtXYModel dataChartModel = new SimpleExtXYModel();
	
	//Pie chart
	private ZHighCharts chartComp2;
	private SimplePieModel pieModel = new SimplePieModel();
	
	//Shared Tooltip, Clickable points
	private ZHighCharts chartComp3;
	private SimpleExtXYModel dataChartModel3 = new SimpleExtXYModel();

	//Time series, zoomable
	private ZHighCharts chartComp4;
	private SimpleExtXYModel dataChartModel4 = new SimpleExtXYModel();
	
	//Basic area
	private ZHighCharts chartComp5;
	private SimpleExtXYModel dataChartModel5 = new SimpleExtXYModel();
	
	//Spline with inverted axes
	private ZHighCharts chartComp6;
	private SimpleExtXYModel dataChartModel6 = new SimpleExtXYModel();
	
	//Spline with symbols
	private ZHighCharts chartComp7;
	private SimpleExtXYModel dataChartModel7 = new SimpleExtXYModel();
	
	//Spline with plot bands
	private ZHighCharts chartComp8;
	private SimpleExtXYModel dataChartModel8 = new SimpleExtXYModel();
	
	//Time data with irregular intervals
	private ZHighCharts chartComp9;
	private SimpleExtXYModel dataChartModel9 = new SimpleExtXYModel();

	//Logarithmic axis
	private ZHighCharts chartComp10;
	private SimpleExtXYModel dataChartModel10 = new SimpleExtXYModel();
	
	//Scatter plot
	private ZHighCharts chartComp11;
	private SimpleExtXYModel dataChartModel11 = new SimpleExtXYModel();

	//Area with negative values
	private ZHighCharts chartComp12;
	private SimpleExtXYModel dataChartModel12 = new SimpleExtXYModel();
	
	//Stacked area
	private ZHighCharts chartComp13;
	private SimpleExtXYModel dataChartModel13 = new SimpleExtXYModel();
	
	//Percentage area
	private ZHighCharts chartComp14;
	
	//Area with missing points
	private ZHighCharts chartComp15;
	private SimpleExtXYModel dataChartModel15 = new SimpleExtXYModel();
	
	//Inverted axes
	private ZHighCharts chartComp16;
	private SimpleExtXYModel dataChartModel16 = new SimpleExtXYModel();
	
	//Area-spline
	private ZHighCharts chartComp17;

	//Basic bar
	private ZHighCharts chartComp18;
	private SimpleExtXYModel dataChartModel18 = new SimpleExtXYModel();
	
	//Stacked bar
	private ZHighCharts chartComp19;
	private SimpleExtXYModel dataChartModel19 = new SimpleExtXYModel();
	
	//Pie with legend
	private ZHighCharts chartComp20;
	private SimpleExtXYModel dataChartModel20 = new SimpleExtXYModel();
	
	//Spline updating each second
	private ZHighCharts chartComp21;
	private SimpleExtXYModel dataChartModel21 = new SimpleExtXYModel();
	
	//Click to add a point
	private ZHighCharts chartComp22;
	private SimpleExtXYModel dataChartModel22 = new SimpleExtXYModel();
	
	//Bar with negative stack
	private ZHighCharts chartComp23;
	private SimpleExtXYModel dataChartModel23 = new SimpleExtXYModel();
	
	// Multiple axes
	private ZHighCharts chartComp24;
	private SimpleExtXYModel dataChartModel24 = new SimpleExtXYModel();
	
	// Multiple axes - yAxis in Pure Java code
	private ZHighCharts chartComp37;
	private SimpleExtXYModel dataChartModel37 = new SimpleExtXYModel();

	// Basic column
	private ZHighCharts chartComp25;
	private SimpleExtXYModel dataChartModel25 = new SimpleExtXYModel();
	
	// Column with negative values
	private ZHighCharts chartComp26;
	private SimpleExtXYModel dataChartModel26 = new SimpleExtXYModel();
	
	// Stacked column
	private ZHighCharts chartComp27;
	private SimpleExtXYModel dataChartModel27 = new SimpleExtXYModel();
	
	// Stacked column
	private ZHighCharts chartComp28;
	private SimpleExtXYModel dataChartModel28 = new SimpleExtXYModel();
	
	// Stacked percentage column
	private ZHighCharts chartComp29;
	private SimpleExtXYModel dataChartModel29 = new SimpleExtXYModel();
	
	// Column with rotated labels
	private ZHighCharts chartComp30;
	private SimpleExtXYModel dataChartModel30 = new SimpleExtXYModel();
	
	// Column and line
	private ZHighCharts chartComp31;
	private SimpleExtXYModel dataChartModel31 = new SimpleExtXYModel();
	
	// jsgauge Basic Example
	private ZGauge zgauge1;
	
	//Angular gauge
	private ZHighCharts chartComp32;
	private SimpleExtXYModel dataChartModel32 = new SimpleExtXYModel();
	
	//Gauge with dual axes
	private ZHighCharts chartComp33;
	private SimpleExtXYModel dataChartModel33 = new SimpleExtXYModel();
	
	// VU meter
	private ZHighCharts chartComp34;
	private SimpleExtXYModel dataChartModel34 = new SimpleExtXYModel();
	
	//
	private Doublebox doubleB;
	
	// Polar chart
	private ZHighCharts chartComp35;
	private SimpleExtXYModel dataChartModel35 = new SimpleExtXYModel();
	
	// Spiderweb
	private ZHighCharts chartComp36;
	private SimpleExtXYModel dataChartModel36 = new SimpleExtXYModel();
	
	
	// date format used to capture date time
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
	
	public void onCreate() throws Exception {
		
		//================================================================================
	    // Basic Line
	    //================================================================================

		chartComp = (ZHighCharts) getFellow("chartComp");
//		chartComp.setTitle("Monthly Average Temperature");
//		chartComp.setSubTitle("Source: WorldClimate.com");
		
		chartComp.setTitleOptions("{" +
					"style:{" +
						"color: 'red'," +
						"fontSize: '20px'" +
					"}, " +
					"text : 'Monthly Average Temperature'" +
				"}");
		chartComp.setSubtitleOptions("{" +
				"style:{" +
					"color: 'green'," +
					"fontSize: '12px'" +
				"}, " +
				"text : 'Source: WorldClimate.com'" +
			"}");
	
		
		chartComp.setType("line");
		chartComp.setExporting("{" +
					"enableImages:true," + //Whether to enable images in the export. 
										   //Including image point markers, background images etc
					"filename:'myChartname'," + //The filename, without extension, to use for the exported chart.
					"url:'http://localhost:8080/highcharts-export/Highcharts-Chart-Export'," +//Batik Server
					"width:2000" + //The pixel width of charts exported to PNG or JPG. . Defaults to 800.
				"}");
		chartComp.setXAxisTitle("Months");
		chartComp.setxAxisOptions("{" +
					"categories: [" +
						"'Jan', " +
						"'Feb', " +
						"'Mar', " +
						"'Apr', " +
						"'May', " +
						"'Jun', " +
						"'Jul', " +
						"'Aug', " +
						"'Sep', " +
						"'Oct', " +
						"'Nov', " +
						"'Dec'" +
					"]" +
				"}");
		chartComp.setYAxisTitle("Temperature (°C)");
		chartComp.setyAxisOptions("{" +
					"plotLines: [" +
					"{" +
						"value: 0, " +
						"width: 1," +
						"color: '#808080' " +
					"}]" +
				"}");
		chartComp.setLegend("{" +
					"layout: 'vertical'," +
					"align: 'right'," +
					"verticalAlign: 'top'," +
					"x: -10," +
					"y: 100," +
					"borderWidth: 0" +
				"}");
		chartComp.setPlotOptions("{" +
					"series:{" +
						"dataLabels:{" +
							"formatter: function (){return this.y +'°C';}," + 
							"enabled: true" +
						"}" +
					"}" +
				"}");

		chartComp.setTooltipFormatter("function formatTooltip(obj){ " +
					"return '<b>'+ obj.series.name +'</b>" +
					"<br/>'+ obj.x +': '+ obj.y +'°C';" +
				"}");
		
		chartComp.setModel(dataChartModel);
		
		//Adding some data to the model
		dataChartModel.addValue("Tokyo", 0, 7);
		dataChartModel.addValue("Tokyo", 1, 6.9);
		dataChartModel.addValue("Tokyo", 2, 9.5);
		dataChartModel.addValue("Tokyo", 3, 14.5);
		dataChartModel.addValue("Tokyo", 4, 18.2);
		dataChartModel.addValue("Tokyo", 5, 21.5);
		dataChartModel.addValue("Tokyo", 6, 25.2);
		dataChartModel.addValue("Tokyo", 7, 26.5);
		dataChartModel.addValue("Tokyo", 8, 23.3);
		dataChartModel.addValue("Tokyo", 9, 18.3);
		dataChartModel.addValue("Tokyo", 10, 13.9);
		dataChartModel.addValue("Tokyo", 11, 9.6);

		dataChartModel.addValue("New York", 0, -0.2);
		dataChartModel.addValue("New York", 1, 0.8);
		dataChartModel.addValue("New York", 2, 5.7);
		dataChartModel.addValue("New York", 3, 11.3);
		dataChartModel.addValue("New York", 4, 17.0);
		dataChartModel.addValue("New York", 5, 22.0);
		dataChartModel.addValue("New York", 6, 24.8);
		dataChartModel.addValue("New York", 7, 24.1);
		dataChartModel.addValue("New York", 8, 20.1);
		dataChartModel.addValue("New York", 9, 14.1);
		dataChartModel.addValue("New York", 10, 8.6);
		dataChartModel.addValue("New York", 11, 2.5);
		
		dataChartModel.addValue("Berlin", 0, -0.9);
		dataChartModel.addValue("Berlin", 1, 0.6);
		dataChartModel.addValue("Berlin", 2, 3.5);
		dataChartModel.addValue("Berlin", 3, 8.4);
		dataChartModel.addValue("Berlin", 4, 13.5);
		dataChartModel.addValue("Berlin", 5, 17.0);
		dataChartModel.addValue("Berlin", 6, 18.6);
		dataChartModel.addValue("Berlin", 7, 17.9);
		dataChartModel.addValue("Berlin", 8, 14.3);
		dataChartModel.addValue("Berlin", 9, 9.0);
		dataChartModel.addValue("Berlin", 10, 3.9);
		dataChartModel.addValue("Berlin", 11, 1.0);
		
		dataChartModel.addValue("London", 0, 3.9);
		dataChartModel.addValue("London", 1, 4.2);
		dataChartModel.addValue("London", 2, 5.7);
		dataChartModel.addValue("London", 3, 8.5);
		dataChartModel.addValue("London", 4, 11.9);
		dataChartModel.addValue("London", 5, 15.2);
		dataChartModel.addValue("London", 6, 17.0);
		dataChartModel.addValue("London", 7, 16.6);
		dataChartModel.addValue("London", 8, 14.2);
		dataChartModel.addValue("London", 9, 10.3);
		dataChartModel.addValue("London", 10, 6.6);
		dataChartModel.addValue("London", 11, 4.8);
		
		
		//================================================================================
	    // Pie chart
	    //================================================================================

		chartComp2 = (ZHighCharts) getFellow("chartComp2");
		chartComp2.setTitle("Browser market shares at a specific website");
		chartComp2.setSubTitle("2010");
		chartComp2.setType("pie");
		chartComp2.setTooltipFormatter("function formatTooltip(obj){" +
					"return obj.key + '<br />Browser Share: <b>'+obj.y+'%</b>'" +
				"}");
		chartComp2.setPlotOptions("{" +
					"pie:{" +
						"allowPointSelect: true," +
						"cursor: 'pointer'," +
						"dataLabels: {" +
							"enabled: true, " +
							"color: '#000000'," +
							"connectorColor: '#000000'," +
							"formatter: function() {"+
	                            "return '<b>'+ this.point.name +'</b>: '+ this.percentage +' %';"+
	                        "}"+
						"}" +
					"}" +
				"}");
		
		chartComp2.setModel(pieModel);	
		
		//Adding some data to the model
		pieModel.setValue("Firefox", 45.0);
		pieModel.setValue("IE", 26.8);
		pieModel.setValue("Chrome", 12.8);
		pieModel.setValue("Safari", 8.5);
		pieModel.setValue("Opera", 6.2);
		pieModel.setValue("Others", 0.7);
		
		//================================================================================
	    // Shared Tooltip, Clickable points
	    //================================================================================

		chartComp3 = (ZHighCharts) getFellow("chartComp3");
		chartComp3.setTitle("Daily visits at www.highcharts.com");
		chartComp3.setSubTitle("Source: Google Analytics");
		chartComp3.setType("line");
		chartComp3.setxAxisOptions("{" +
				"type: 'datetime'," +
				"tickInterval: 7 * 86400000," +
				"tickWidth: 0," +
				"gridLineWidth: 1," +
				"labels: {align: 'left'," +
							"x: 3," +
							"y: -3" +
						"}" +
				"}");
		chartComp3.setyAxisOptions("[" +
				"{" +
					"labels: {" +
						"align: 'left'," +
						"x: 3," +
						"y: 16," +
						"formatter: function() {" +
							"return Highcharts.numberFormat(this.value, 0);" +
						"}" +
					"}," +
					"showFirstLabel: false" +
				"}, " +
				"{" +
					"linkedTo: 0," +
					"gridLineWidth: 0," +
					"opposite: true," +
					"title: {text: null}," +
					"labels: {" +
						"align: 'right'," +
						"x: -3," +
						"y: 16," +
						"formatter: function() {" +
							"return Highcharts.numberFormat(this.value, 0);" +
						"}" +
					"}," +
					"showFirstLabel: false" +
				"}]");
		chartComp3.setLegend("{" +
								"align: 'left'," +
								"verticalAlign: 'top'," +
								"y: 20," +
								"floating: true," +
								"borderWidth: 0" +
							"}");
		chartComp3.setTooltipOptions("{" +
					"shared: true," +
					"crosshairs: true" +
				"}");
		chartComp3.setTooltipFormatter("function formatTooltip(obj){" +
					"return Highcharts.dateFormat('%A, %b %d, %Y', obj.x) + '<br/>" +
					"<span style=\"color:'+ obj.points[0].series.color +';\">'+ obj.points[0].series.name +'</span> : <b>'+ obj.points[0].y +'</b><br/>" +
					"<span style=\"color:'+ obj.points[1].series.color +';\">'+ obj.points[1].series.name +'</span> : <b>'+ obj.points[1].y +'</b>';" +
				"}");
		chartComp3.setPlotOptions("{ " +
					"series:{ " +
						"cursor:'pointer'," +
						"point:{" +
							"events:{ " +
								"click:function(){ " +
									"var string = this.series.name + '\\n' + Highcharts.dateFormat('%A, %b %d, %Y',this.x) + '\\n' + this.y + ' visits';" + 
									"alert(string);" +
								"} " +
							"} " +
						"}, " +
						"marker:{ " +
							"lineWidth:1 " +
						"} " +
					"} " +
				"}");
		
		//Setting some style to the series "All Visits"
		Map style = new HashMap();
		style.put("lineWidth", 4);
		Map marker = new HashMap();
		marker.put("radius", 4);
		style.put("marker", marker);
		chartComp3.setSeriesOptions("All Visits", style);
		
		chartComp3.setModel(dataChartModel3);
		
		//Adding some data to the model
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-05 00:00:00 GMT"), 966);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-06 00:00:00 GMT"), 2475);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-07 00:00:00 GMT"), 3336);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-08 00:00:00 GMT"), 3211);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-09 00:00:00 GMT"), 3229);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-10 00:00:00 GMT"), 2808);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-11 00:00:00 GMT"), 1168);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-12 00:00:00 GMT"), 1110);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-13 00:00:00 GMT"), 3112);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-14 00:00:00 GMT"), 3590);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-15 00:00:00 GMT"), 3529);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-16 00:00:00 GMT"), 3519);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-17 00:00:00 GMT"), 3696);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-18 00:00:00 GMT"), 1400);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-19 00:00:00 GMT"), 1302);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-20 00:00:00 GMT"), 3348);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-21 00:00:00 GMT"), 3606);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-22 00:00:00 GMT"), 3320);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-23 00:00:00 GMT"), 2677);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-24 00:00:00 GMT"), 2795);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-25 00:00:00 GMT"), 1299);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-26 00:00:00 GMT"), 1189);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-27 00:00:00 GMT"), 3189);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-28 00:00:00 GMT"), 3223);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-29 00:00:00 GMT"), 3231);
		dataChartModel3.addValue("All Visits", getDateTime("2012-09-30 00:00:00 GMT"), 3608);
		dataChartModel3.addValue("All Visits", getDateTime("2012-10-01 00:00:00 GMT"), 2945);
		dataChartModel3.addValue("All Visits", getDateTime("2012-10-02 00:00:00 GMT"), 1508);
		dataChartModel3.addValue("All Visits", getDateTime("2012-10-03 00:00:00 GMT"), 1114);
		dataChartModel3.addValue("All Visits", getDateTime("2012-10-04 00:00:00 GMT"), 2774);
		dataChartModel3.addValue("All Visits", getDateTime("2012-10-05 00:00:00 GMT"), 2679);
		
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-05 00:00:00 GMT"), 433);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-06 00:00:00 GMT"), 983);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-07 00:00:00 GMT"), 1463);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-08 00:00:00 GMT"), 1316);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-09 00:00:00 GMT"), 1351);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-10 00:00:00 GMT"), 1270);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-11 00:00:00 GMT"), 604);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-12 00:00:00 GMT"), 498);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-13 00:00:00 GMT"), 1352);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-14 00:00:00 GMT"), 1626);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-15 00:00:00 GMT"), 1549);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-16 00:00:00 GMT"), 1574);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-17 00:00:00 GMT"), 1680);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-18 00:00:00 GMT"), 677);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-19 00:00:00 GMT"), 603);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-20 00:00:00 GMT"), 1472);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-21 00:00:00 GMT"), 1570);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-22 00:00:00 GMT"), 1438);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-23 00:00:00 GMT"), 1140);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-24 00:00:00 GMT"), 1256);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-25 00:00:00 GMT"), 589);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-26 00:00:00 GMT"), 533);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-27 00:00:00 GMT"), 1253);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-28 00:00:00 GMT"), 1266);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-29 00:00:00 GMT"), 1249);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-09-30 00:00:00 GMT"), 1684);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-10-01 00:00:00 GMT"), 1185);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-10-02 00:00:00 GMT"), 460);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-10-03 00:00:00 GMT"), 499);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-10-04 00:00:00 GMT"), 1131);
		dataChartModel3.addValue("New Visitors", getDateTime("2012-10-05 00:00:00 GMT"), 1047);

		
		//================================================================================
	    // Time series, zoomable
	    //================================================================================

		chartComp4 = (ZHighCharts) getFellow("chartComp4");
		chartComp4.setType("area");
		chartComp4.setOptions("{" +
					"zoomType: 'x'," +
					"spacingRight: 20" +
				"}");
		chartComp4.setTitle("USD to EUR exchange rate from 2006 through 2008");
		chartComp4.setSubTitle("'Click and drag in the plot area to zoom in' :");
		chartComp4.setxAxisOptions("{" +
					"type: 'datetime'," +
					"maxZoom: 14 * 24 * 3600000" +
				"}");
		chartComp4.setyAxisOptions("{" +
					"min: 0.6," +
					"startOnTick: false," +
					"showFirstLabel: false" +
				"}");
		chartComp4.setYAxisTitle("Exchange rate");
		chartComp4.setLegend("{" +
					"enabled: false" +
				"}");
		chartComp4.setTooltipOptions("{" +
					"shared: true" +
				"}");
		chartComp4.setTooltipFormatter("function formatTooltip(obj){" +
					"return Highcharts.dateFormat('%A, %b %d, %Y', obj.x) + '<br/>" +
					"<span style=\"color:'+ obj.points[0].series.color +';\">'+ obj.points[0].series.name +'</span> : <b>'+ obj.points[0].y +'</b>';" +
				"}");
		chartComp4.setPlotOptions("{" +
					"area:{" +
						"fillColor:{" +
							"linearGradient:{" +
								"x1:0," +
								"y1:0," +
								"x2:0," +
								"y2:1" +
							"}," +
							"stops:[" +
								"[0,Highcharts.getOptions().colors[0]]," +
								"[1,'rgba(2,0,0,0)']" +
							"]" +
						"}," +
						"lineWidth:1," +
						"marker:{" +
							"enabled:false," +
							"states:{" +
								"hover:{" +
									"enabled:true," +
									"radius:5" +
								"}" +
							"}" +
						"}," +
						"shadow:false," +
						"states:{" +
							"hover:{" +
								"lineWidth:1" +
							"}" +
						"}" +
					"}" +
				"}");
				
		chartComp4.setModel(dataChartModel4);
		
		//Adding some data to the model
		Number dataChartTab4[] = {0.8446, 0.8445, 0.8444, 0.8451, 0.8418, 0.8264, 0.8258, 0.8232, 0.8233, 0.8258, 0.8283, 0.8278, 0.8256, 0.8292, 0.8239, 0.8239, 0.8245, 0.8265, 0.8261, 0.8269, 0.8273, 0.8244, 
				0.8244, 0.8172, 0.8139, 0.8146, 0.8164, 0.82,  0.8269, 0.8269, 0.8269, 0.8258, 0.8247, 0.8286, 0.8289, 0.8316, 0.832, 0.8333, 0.8352, 0.8357, 0.8355, 0.8354, 0.8403, 0.8403, 0.8406, 0.8403, 
				0.8396, 0.8418, 0.8409, 0.8384, 0.8386, 0.8372, 0.839, 0.84, 0.8389, 0.84, 0.8423, 0.8423, 0.8435, 0.8422, 0.838, 0.8373, 0.8316, 0.8303, 0.8303, 0.8302, 0.8369, 0.84, 0.8385, 0.84, 0.8401, 
				0.8402, 0.8381, 0.8351, 0.8314, 0.8273, 0.8213, 0.8207, 0.8207, 0.8215, 0.8242, 0.8273, 0.8301, 0.8346, 0.8312, 0.8312, 0.8312, 0.8306, 0.8327, 0.8282, 0.824, 0.8255, 0.8256, 0.8273, 0.8209, 
				0.8151, 0.8149, 0.8213, 0.8273, 0.8273, 0.8261, 0.8252, 0.824, 0.8262, 0.8258, 0.8261, 0.826, 0.8199, 0.8153, 0.8097, 0.8101, 0.8119, 0.8107, 0.8105, 0.8084, 0.8069, 0.8047, 0.8023, 0.7965, 
				0.7919, 0.7921, 0.7922, 0.7934, 0.7918, 0.7915, 0.787, 0.7861, 0.7861, 0.7853, 0.7867, 0.7827, 0.7834, 0.7766, 0.7751, 0.7739, 0.7767, 0.7802, 0.7788, 0.7828, 0.7816, 0.7829, 0.783, 0.7829, 
				0.7781, 0.7811, 0.7831, 0.7826, 0.7855, 0.7855, 0.7845, 0.7798, 0.7777, 0.7822, 0.7785, 0.7744, 0.7743, 0.7726, 0.7766, 0.7806, 0.785, 0.7907, 0.7912, 0.7913, 0.7931, 0.7952, 0.7951, 0.7928, 
				0.791, 0.7913, 0.7912, 0.7941, 0.7953, 0.7921, 0.7919, 0.7968, 0.7999, 0.7999, 0.7974, 0.7942, 0.796, 0.7969, 0.7862, 0.7821, 0.7821, 0.7821, 0.7811, 0.7833, 0.7849, 0.7819, 0.7809, 0.7809,
				0.7827, 0.7848, 0.785, 0.7873, 0.7894, 0.7907, 0.7909, 0.7947, 0.7987, 0.799, 0.7927, 0.79, 0.7878, 0.7878, 0.7907, 0.7922, 0.7937, 0.786, 0.787, 0.7838, 0.7838, 0.7837, 0.7836, 0.7806,
				0.7825, 0.7798, 0.777, 0.777, 0.7772, 0.7793, 0.7788, 0.7785, 0.7832, 0.7865, 0.7865, 0.7853, 0.7847, 0.7809, 0.778, 0.7799, 0.78, 0.7801, 0.7765, 0.7785, 0.7811, 0.782, 0.7835, 0.7845, 
				0.7844, 0.782, 0.7811, 0.7795, 0.7794, 0.7806, 0.7794, 0.7794, 0.7778, 0.7793, 0.7808, 0.7824, 0.787, 0.7894, 0.7893, 0.7882, 0.7871, 0.7882, 0.7871, 0.7878, 0.79, 0.7901, 0.7898, 0.7879, 
				0.7886, 0.7858, 0.7814, 0.7825, 0.7826, 0.7826, 0.786, 0.7878, 0.7868, 0.7883, 0.7893, 0.7892, 0.7876, 0.785, 0.787, 0.7873, 0.7901, 0.7936, 0.7939, 0.7938, 0.7956, 0.7975, 0.7978, 0.7972, 
				0.7995, 0.7995, 0.7994, 0.7976, 0.7977, 0.796, 0.7922, 0.7928, 0.7929, 0.7948, 0.797, 0.7953, 0.7907, 0.7872, 0.7852, 0.7852, 0.786, 0.7862, 0.7836, 0.7837, 0.784, 0.7867, 0.7867, 0.7869, 
				0.7837, 0.7827, 0.7825, 0.7779, 0.7791, 0.779, 0.7787, 0.78, 0.7807, 0.7803, 0.7817, 0.7799, 0.7799, 0.7795, 0.7801, 0.7765, 0.7725, 0.7683, 0.7641, 0.7639, 0.7616, 0.7608, 0.759, 0.7582, 
				0.7539, 0.75, 0.75, 0.7507, 0.7505, 0.7516, 0.7522, 0.7531, 0.7577, 0.7577, 0.7582, 0.755, 0.7542, 0.7576, 0.7616, 0.7648, 0.7648, 0.7641, 0.7614, 0.757, 0.7587, 0.7588, 0.762, 0.762, 0.7617,
				0.7618, 0.7615, 0.7612, 0.7596, 0.758, 0.758, 0.758, 0.7547, 0.7549, 0.7613, 0.7655, 0.7693, 0.7694, 0.7688, 0.7678, 0.7708, 0.7727, 0.7749, 0.7741, 0.7741, 0.7732, 0.7727, 0.7737, 0.7724, 
				0.7712, 0.772, 0.7721, 0.7717, 0.7704, 0.769, 0.7711, 0.774, 0.7745, 0.7745, 0.774, 0.7716, 0.7713, 0.7678, 0.7688, 0.7718, 0.7718, 0.7728, 0.7729, 0.7698, 0.7685, 0.7681, 0.769, 0.769, 0.7698, 
				0.7699, 0.7651, 0.7613, 0.7616, 0.7614, 0.7614, 0.7607, 0.7602, 0.7611, 0.7622, 0.7615, 0.7598, 0.7598, 0.7592, 0.7573, 0.7566, 0.7567, 0.7591, 0.7582, 0.7585, 0.7613, 0.7631, 0.7615, 0.76,
				0.7613, 0.7627, 0.7627, 0.7608, 0.7583, 0.7575, 0.7562, 0.752, 0.7512, 0.7512, 0.7517, 0.752,  0.7511, 0.748, 0.7509, 0.7531, 0.7531, 0.7527, 0.7498, 0.7493, 0.7504, 0.75, 0.7491, 0.7491, 
				0.7485, 0.7484, 0.7492, 0.7471, 0.7459, 0.7477, 0.7477, 0.7483, 0.7458, 0.7448, 0.743, 0.7399,  0.7395, 0.7395, 0.7378, 0.7382, 0.7362, 0.7355, 0.7348, 0.7361, 0.7361, 0.7365, 0.7362, 
				0.7331, 0.7339, 0.7344, 0.7327, 0.7327, 0.7336, 0.7333, 0.7359, 0.7359, 0.7372, 0.736, 0.736, 0.735, 0.7365, 0.7384, 0.7395, 0.7413, 0.7397, 0.7396, 0.7385, 0.7378, 0.7366, 0.74, 0.7411,
				0.7406, 0.7405, 0.7414, 0.7431, 0.7431, 0.7438, 0.7443, 0.7443, 0.7443, 0.7434, 0.7429, 0.7442, 0.744, 0.7439, 0.7437, 0.7437, 0.7429, 0.7403, 0.7399, 0.7418, 0.7468, 0.748, 0.748, 0.749, 
				0.7494, 0.7522, 0.7515, 0.7502, 0.7472, 0.7472, 0.7462, 0.7455, 0.7449, 0.7467, 0.7458, 0.7427, 0.7427, 0.743, 0.7429, 0.744, 0.743, 0.7422, 0.7388, 0.7388, 0.7369, 0.7345, 0.7345, 0.7345,
				0.7352, 0.7341, 0.7341, 0.734, 0.7324, 0.7272, 0.7264, 0.7255, 0.7258, 0.7258, 0.7256, 0.7257, 0.7247, 0.7243, 0.7244, 0.7235, 0.7235, 0.7235, 0.7235, 0.7262, 0.7288, 0.7301, 0.7337, 0.7337,
				0.7324, 0.7297, 0.7317, 0.7315, 0.7288, 0.7263, 0.7263, 0.7242, 0.7253, 0.7264, 0.727, 0.7312, 0.7305, 0.7305, 0.7318, 0.7358, 0.7409, 0.7454, 0.7437, 0.7424, 0.7424, 0.7415, 0.7419, 0.7414, 
				0.7377, 0.7355, 0.7315, 0.7315, 0.732, 0.7332, 0.7346, 0.7328, 0.7323, 0.734, 0.734, 0.7336, 0.7351, 0.7346, 0.7321, 0.7294, 0.7266, 0.7266, 0.7254, 0.7242, 0.7213, 0.7197, 0.7209, 0.721, 
				0.721, 0.721, 0.7209, 0.7159, 0.7133, 0.7105, 0.7099, 0.7099, 0.7093, 0.7093, 0.7076, 0.707, 0.7049, 0.7012, 0.7011, 0.7019, 0.7046, 0.7063, 0.7089, 0.7077, 0.7077, 0.7077, 0.7091, 0.7118, 
				0.7079, 0.7053, 0.705, 0.7055, 0.7055, 0.7045, 0.7051, 0.7051, 0.7017, 0.7, 0.6995, 0.6994, 0.7014, 0.7036, 0.7021, 0.7002, 0.6967, 0.695, 0.695, 0.6939, 0.694, 0.6922, 0.6919, 0.6914,
				0.6894, 0.6891, 0.6904, 0.689, 0.6834, 0.6823, 0.6807, 0.6815, 0.6815, 0.6847, 0.6859, 0.6822, 0.6827, 0.6837, 0.6823, 0.6822, 
				0.6822, 0.6792, 0.6746, 0.6735, 0.6731, 0.6742, 0.6744, 0.6739, 0.6731, 0.6761, 0.6761, 0.6785, 0.6818, 0.6836, 0.6823, 0.6805, 0.6793, 0.6849, 0.6833, 0.6825, 0.6825, 0.6816, 0.6799, 0.6813, 
				0.6809, 0.6868, 0.6933, 0.6933, 0.6945, 0.6944, 0.6946, 0.6964, 0.6965, 0.6956, 0.6956, 0.695, 0.6948, 0.6928, 0.6887, 0.6824, 0.6794, 0.6794, 0.6803, 0.6855, 0.6824, 0.6791, 0.6783, 0.6785, 
				0.6785, 0.6797, 0.68, 0.6803, 0.6805, 0.676, 0.677, 0.677, 0.6736, 0.6726, 0.6764, 0.6821, 0.6831, 0.6842, 0.6842, 0.6887, 0.6903, 0.6848, 0.6824, 0.6788, 0.6814, 0.6814, 0.6797, 0.6769, 
				0.6765, 0.6733, 0.6729, 0.6758, 0.6758, 0.675, 0.678, 0.6833, 0.6856, 0.6903, 0.6896, 0.6896, 0.6882, 0.6879, 0.6862, 0.6852, 0.6823, 0.6813, 0.6813, 0.6822, 0.6802, 0.6802, 0.6784, 0.6748, 
				0.6747, 0.6747, 0.6748, 0.6733, 0.665, 0.6611, 0.6583, 0.659, 0.659, 0.6581, 0.6578, 0.6574, 0.6532, 0.6502, 0.6514, 0.6514, 0.6507, 0.651, 0.6489, 0.6424, 0.6406, 0.6382, 0.6382, 0.6341, 
				0.6344, 0.6378, 0.6439, 0.6478, 0.6481, 0.6481, 0.6494, 0.6438, 0.6377, 0.6329, 0.6336, 0.6333, 0.6333, 0.633, 0.6371, 0.6403, 0.6396, 0.6364, 0.6356, 0.6356, 0.6368, 0.6357, 0.6354, 0.632,
				0.6332, 0.6328, 0.6331, 0.6342, 0.6321, 0.6302, 0.6278, 0.6308, 0.6324, 0.6324, 0.6307, 0.6277, 0.6269, 0.6335, 0.6392, 0.64, 0.6401, 0.6396, 0.6407, 0.6423, 0.6429, 0.6472, 0.6485, 0.6486,
				0.6467, 0.6444, 0.6467, 0.6509, 0.6478, 0.6461, 0.6461, 0.6468, 0.6449, 0.647, 0.6461, 0.6452, 0.6422, 0.6422, 0.6425, 0.6414, 0.6366, 0.6346, 0.635, 0.6346, 0.6346, 0.6343, 0.6346, 0.6379, 
				0.6416, 0.6442, 0.6431, 0.6431, 0.6435, 0.644, 0.6473, 0.6469, 0.6386, 0.6356, 0.634, 0.6346, 0.643, 0.6452, 0.6467, 0.6506, 0.6504, 0.6503, 0.6481, 0.6451, 0.645, 0.6441, 0.6414, 0.6409,
				0.6409, 0.6428, 0.6431, 0.6418, 0.6371, 0.6349, 0.6333, 0.6334, 0.6338, 0.6342, 0.632, 0.6318, 0.637, 0.6368, 0.6368, 0.6383, 0.6371, 0.6371, 0.6355, 0.632, 0.6277, 0.6276, 0.6291, 0.6274,
		}; 
		
		//Setting Start Date
		long ii=getDateTime("2006-01-01 00:00:00 GMT");
		
		for (int i = 0;i<dataChartTab4.length;i++){
			dataChartModel4.addValue("USD to EUR", ii++, dataChartTab4[i]);
			ii += 86400000;// A day =  getDateTime("2006-01-02 00:00:00 GMT") - getDateTime("2006-01-01 00:00:00 GMT")
		}
		
		//================================================================================
	    // Basic area
	    //================================================================================
		
		chartComp5 = (ZHighCharts) getFellow("chartComp5");
		chartComp5.setType("area");
		chartComp5.setTitle("US and USSR nuclear stockpiles");
		chartComp5.setSubTitle("Source: <a href='http://thebulletin.metapress.com/content/c4120650912x74k7/fulltext.pdf'>thebulletin.metapress.com</a>");
		chartComp5.setxAxisOptions("{" +
					"labels: {" +
						"formatter: function() {" +
							"return this.value;" +
						"}" +
					"}" +
				"}");
		chartComp5.setyAxisOptions("{" +
					"labels: {" +
						"formatter: function() {" +
							"return this.value / 1000 +'k';" +
						"}" +
					"}" +
				"}");
		chartComp5.setTooltipFormatter("function formatTooltip(obj){" +
					"return obj.series.name +' produced <b>'+Highcharts.numberFormat(obj.y, 0) +'</b><br/>" +
					"warheads in '+ obj.x;" +
				"}");
		chartComp5.setPlotOptions("{" +
					"area: {" +
						"marker: {" +
							"enabled: false," +
							"symbol: 'circle'," +
							"radius: 2," +
							"states: {" +
								"hover: {" +
								"	enabled: true" +
								"}" +
							"}" +
						"}" +
					"}" +
				"}");
		
		chartComp5.setYAxisTitle("Nuclear weapon states");
		
		
		chartComp5.setModel(dataChartModel5);
		
		
		//Adding some data to the model
		Number dataUSA[] = {null, null, null, null, null, 6 , 11, 32, 110, 235, 369, 640,
                1005, 1436, 2063, 3057, 4618, 6444, 9822, 15468, 20434, 24126,
                27387, 29459, 31056, 31982, 32040, 31233, 29224, 27342, 26662,
                26956, 27912, 28999, 28965, 27826, 25579, 25722, 24826, 24605,
                24304, 23464, 23708, 24099, 24357, 24237, 24401, 24344, 23586,
                22380, 21004, 17287, 14747, 13076, 12555, 12144, 11009, 10950,
                10871, 10824, 10577, 10527, 10475, 10421, 10358, 10295, 10104};
		
		Number dataUSSR[] = {
				null, null, null, null, null, null, null , null , null ,null,
                5, 25, 50, 120, 150, 200, 426, 660, 869, 1060, 1605, 2471, 3322,
                4238, 5221, 6129, 7089, 8339, 9399, 10538, 11643, 13092, 14478,
                15915, 17385, 19055, 21205, 23044, 25393, 27935, 30062, 32049,
                33952, 35804, 37431, 39197, 45000, 43000, 41000, 39000, 37000,
                35000, 33000, 31000, 29000, 27000, 25000, 24000, 23000, 22000,
                21000, 20000, 19000, 18000, 18000, 17000, 16000
		};
		int startpoint = 1940;
		for (int i =0;i<dataUSA.length;i++){
			dataChartModel5.addValue("USA", startpoint++, dataUSA[i]);
		}
			
		startpoint = 1940;
		for (int j =0;j<dataUSA.length;j++)
			dataChartModel5.addValue("USSR", startpoint++, dataUSSR[j]);
		
		
		//================================================================================
	    // Spline with inverted axes
	    //================================================================================
		
		chartComp6 = (ZHighCharts) getFellow("chartComp6");
		chartComp6.setType("spline");
		chartComp6.setOptions("{" +
				"inverted:true," +
				"width: 500," +
				"style: {" +
					"margin: '0 auto'" +
					"}" +
				"}");
		chartComp6.setTitle("Atmosphere Temperature by Altitude");
		chartComp6.setSubTitle("According to the Standard Atmosphere Model");
		chartComp6.setxAxisOptions("{" +
					"reversed:false," +
					"labels: {" +
						"formatter: function() {" +
							"return this.value +'km';" +
						"}" +
					"}," +
					"maxPadding: 0.05," +
					"showLastLabel: true" +
				"}");
		chartComp6.setyAxisOptions("{" +
					"labels: {" +
						"formatter: function() {" +
							"return this.value + '°';" +
						"}" +
					"}," +
					"lineWidth: 2" +
				"}");
		chartComp6.setTooltipFormatter("function formatTooltip(obj){" +
					"return ''+obj.x +' km: '+ obj.y +'°C';" +
				"}");
		chartComp6.setPlotOptions("{" +
					"spline: {" +
						"marker: {" +
							"enable: false" +
						"}" +
					"}" +
				"}");
		chartComp6.setLegend("{" +
					"enabled: false" +
				"}");
		chartComp6.setXAxisTitle("Altitude");
		chartComp6.setYAxisTitle("Temperature");
		
		chartComp6.setModel(dataChartModel6);
		
		//Adding some data to the model

		dataChartModel6.addValue("Temperature", 0, 15);
		dataChartModel6.addValue("Temperature", 10, -50);
		dataChartModel6.addValue("Temperature", 20, -56.5);
		dataChartModel6.addValue("Temperature", 30, -46.5);
		dataChartModel6.addValue("Temperature", 40, -22.1);
		dataChartModel6.addValue("Temperature", 50, -2.5);
		dataChartModel6.addValue("Temperature", 60, -27.7);
		dataChartModel6.addValue("Temperature", 70, -55.7);
		dataChartModel6.addValue("Temperature", 80, -76.5);
		
		//================================================================================
	    // Spline with symbols
	    //================================================================================
		
		
		chartComp7 = (ZHighCharts) getFellow("chartComp7");
		chartComp7.setType("spline");
		chartComp7.setTitle("Monthly Average Temperature");
		chartComp7.setSubTitle("Source: WorldClimate.com");
		chartComp7.setxAxisOptions("{ " +
					"categories: ['" +
						"Jan', " +
						"'Feb', " +
						"'Mar', " +
						"'Apr', " +
						"'May', " +
						"'Jun', " +
						"'Jul', " +
						"'Aug', " +
						"'Sep', " +
						"'Oct', " +
						"'Nov', " +
						"'Dec'" +
					"]" +
				"}");
		chartComp7.setyAxisOptions("{" +
					"labels: {" +
						"formatter: function() {" +
							"return this.value +'°';" +
						"}" +
					"}" +
				"}");
		chartComp7.setTooltipFormatter("function formatTooltip(obj){" +
					"return '' + obj.x + '<br/>" +
					"<span style=\"color:'+ obj.points[0].series.color +';\">'+ obj.points[0].series.name +'</span> : <b>'+ obj.points[0].y +'</b><br/>" +
					"<span style=\"color:'+ obj.points[1].series.color +';\">'+ obj.points[1].series.name +'</span> : <b>'+ obj.points[1].y +'</b>';" +
				"}");
		chartComp7.setTooltipOptions("{" +
					"crosshairs: true," +
					"shared: true" +
				"}");
		chartComp7.setPlotOptions("{" +
					"spline: {" +
						"marker: {" +
							"radius: 4," +
							"lineColor: '#666666'," +
							"lineWidth: 1" +
						"}" +
					"}" +
				"}");
		chartComp7.setYAxisTitle("Temperature");

		chartComp7.setModel(dataChartModel7);

		//Adding some data to the model

		dataChartModel7.addValue("Tokyo", 0, 7);
		dataChartModel7.addValue("Tokyo", 1, 6.9);
		dataChartModel7.addValue("Tokyo", 2, 9.5);
		dataChartModel7.addValue("Tokyo", 3, 14.5);
		dataChartModel7.addValue("Tokyo", 4, 18.2);
		dataChartModel7.addValue("Tokyo", 5, 21.5);
		dataChartModel7.addValue("Tokyo", 6, 25.2);
		
		Map data = new HashMap();
		marker = new HashMap();
		marker.put("symbol", "url(http://www.highcharts.com/demo/gfx/sun.png)");
		data.put("marker", marker);
		dataChartModel7.addValue("Tokyo", 7, 26.5, data);
		
		dataChartModel7.addValue("Tokyo", 8, 23.3);
		dataChartModel7.addValue("Tokyo", 9, 18.3);
		dataChartModel7.addValue("Tokyo", 10, 13.9);
		dataChartModel7.addValue("Tokyo", 11, 9.6);

		data = new HashMap();
		marker = new HashMap();
		marker.put("symbol", "url(http://www.highcharts.com/demo/gfx/snow.png)");
		data.put("marker", marker);
		
		dataChartModel7.addValue("London", 0, 3.9, data);
		dataChartModel7.addValue("London", 1, 4.2);
		dataChartModel7.addValue("London", 2, 5.7);
		dataChartModel7.addValue("London", 3, 8.5);
		dataChartModel7.addValue("London", 4, 11.9);
		dataChartModel7.addValue("London", 5, 15.2);
		dataChartModel7.addValue("London", 6, 17.0);
		dataChartModel7.addValue("London", 7, 16.6);
		dataChartModel7.addValue("London", 8, 14.2);
		dataChartModel7.addValue("London", 9, 10.3);
		dataChartModel7.addValue("London", 10, 6.6);
		dataChartModel7.addValue("London", 11, 4.8);
		
		style = new HashMap();
		marker = new HashMap();
		marker.put("symbol", "square");
		style.put("marker", marker);
		chartComp7.setSeriesOptions("Tokyo", style);
		
		style = new HashMap();
		marker = new HashMap();
		marker.put("symbol", "diamond");
		style.put("marker", marker);
		chartComp7.setSeriesOptions("London", style);
		
		//================================================================================
	    // Spline with plot bands
	    //================================================================================

		
		chartComp8 = (ZHighCharts) getFellow("chartComp8");
		chartComp8.setType("spline");
		chartComp8.setTitle("Wind speed during two days");
		chartComp8.setSubTitle("October 6th and 7th 2009 at two locations in Vik i Sogn, Norway");
		chartComp8.setxAxisOptions("{" +
					"type:'datetime'" +
				"}");
		chartComp8.setyAxisOptions("{" +
					"min:0, " +
					"minorGridLineWidth:0, " +
					"gridLineWidth:0, " +
					"alternateGridColor:null, " +
					"plotBands:[" +
						"{ " +
							"from:0.3, " +
							"to:1.5, " +
							"color:'rgba(68,170,213,0.1)', " +
							"label:{ " +
								"text:'Lightair', " +
								"style:{ " +
									"color:'#606060' " +
								"} " +
							"} " +
						"}," +
						"{ " +
							"from:1.5, " +
							"to:3.3, " +
							"color:'rgba(0,0,0,0)', " +
							"label:{ " +
								"text:'Lightbreeze', " +
								"style:{ " +
									"color:'#606060' " +
								"} " +
							"} " +
						"}," +
						"{ " +
							"from:3.3, " +
							"to:5.5, " +
							"color:'rgba(68,170,213,0.1)', " +
							"label:{ " +
								"text:'Gentlebreeze', " +
								"style:{ " +
									"color:'#606060' " +
								"} " +
							"} " +
						"}," +
						"{ " +
							"from:5.5, " +
							"to:8, " +
							"color:'rgba(0,0,0,0)', " +
							"label:{ " +
								"text:'Moderatebreeze', " +
								"style:{ " +
									"color:'#606060' " +
								"} " +
							"} " +
						"}," +
						"{ " +
							"from:8, " +
							"to:11, " +
							"color:'rgba(68,170,213,0.1)'," +
							"label:{ " +
								"text:'Freshbreeze', " +
								"style:{ " +
									"color:'#606060' " +
								"} " +
							"} " +
						"}," +
						"{ " +
							"from:11, " +
							"to:14, " +
							"color: 'rgba(0, 0, 0, 0)'," +
							"label: {" +
								"text: 'Strong breeze'," +
								"style: {" +
									"color: '#606060'" +
								"}" +
							"} " +
						"}, " +
						"{" +
							"from: 14," +
							"to: 15," +
							"color: 'rgba(68, 170, 213, 0.1)'," +
							"label: {" +
								"text: 'High wind'," +
								"style: {" +
									"color: '#606060'" +
								"}" +
							"}" +
						"}" +
					"]" +
				"}");
		chartComp8.setTooltipFormatter("function formatTooltip(obj){" +
					"return ''+Highcharts.dateFormat('%e. %b %Y, %H:00', obj.x) +': '+ obj.y +' m/s';" +
				"}");
		chartComp8.setPlotOptions("{" +
					"spline: {" +
						"lineWidth: 4," +
						"states: {" +
							"hover: {" +
								"lineWidth: 5" +
							"}" +
						"}, " +
						"marker: {" +
							"enabled: false," +
							"states: { " +
								"hover: {" +
									"enabled: true, " +
									"symbol: 'circle'," +
									"radius: 5," +
									"lineWidth: 1" +
								"}" +
							"}" +
						"}" +
					"}" +
				"}");
		chartComp8.setYAxisTitle("Wind speed (m/s)");
		
		chartComp8.setModel(dataChartModel8);
		
		//Adding some data to the model
		
		Number dataHestavollane[] = {4.3, 5.1, 4.3, 5.2, 5.4, 4.7, 3.5, 4.1, 5.6, 7.4, 6.9, 7.1,
                7.9, 7.9, 7.5, 6.7, 7.7, 7.7, 7.4, 7.0, 7.1, 5.8, 5.9, 7.4,
                8.2, 8.5, 9.4, 8.1, 10.9, 10.4, 10.9, 12.4, 12.1, 9.5, 7.5,
                7.1, 7.5, 8.1, 6.8, 3.4, 2.1, 1.9, 2.8, 2.9, 1.3, 4.4, 4.2,
                3.0, 3.0};
		Number dataVoll[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.3, 0.0,
                0.0, 0.4, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.6, 1.2, 1.7, 0.7, 2.9, 4.1, 2.6, 3.7, 3.9, 1.7, 2.3,
                3.0, 3.3, 4.8, 5.0, 4.8, 5.0, 3.2, 2.0, 0.9, 0.4, 0.3, 0.5, 0.4};
		
		//Start Point
		long iii = getDateTime("2009-10-06 00:00:00 GMT");;
		for (int i =0;i<dataHestavollane.length;i++){
			dataChartModel8.addValue("Hestavollane", iii, dataHestavollane[i]);
			iii += 3600000; //Every Hour
		}
		
		//Start Point
		long jjj = getDateTime("2009-10-06 00:00:00 GMT");;
		for (int j =0;j<dataVoll.length;j++){
			dataChartModel8.addValue("Voll", jjj, dataVoll[j]);
			jjj += 3600000;

		}		
		
		//================================================================================
	    // Time data with irregular intervals
	    //================================================================================

		
		chartComp9 = (ZHighCharts) getFellow("chartComp9");
		chartComp9.setType("spline");
		chartComp9.setTitle("Snow depth in the Vikjafjellet mountain, Norway");
		chartComp9.setSubTitle("An example of irregular time data in Highcharts JS");
		chartComp9.setxAxisOptions("{" +
					"type:'datetime'," +
					"dateTimeLabelFormats: {" +
						"month: '%e. %b',year: '%b'" +
					"}" +
				"}");
		chartComp9.setyAxisOptions("{" +
					"min:0" +
				"}");
		chartComp9.setTooltipFormatter("function formatTooltip(obj){" +
					"return '<b>'+ obj.series.name +'</b><br/>'" +
					"+Highcharts.dateFormat('%e. %b', obj.x) +': '+ obj.y +' m';" +
				"}");
		chartComp9.setYAxisTitle("Snow depth (m)");
		
		chartComp9.setModel(dataChartModel9);
		
		//Adding some data to the model

		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1970-10-27 00:00:00 GMT"), 0);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1970-11-10 00:00:00 GMT"), 0.6);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1970-11-18 00:00:00 GMT"), 0.7);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1970-12-02 00:00:00 GMT"), 0.8);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1970-12-09 00:00:00 GMT"), 0.6);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1970-12-16 00:00:00 GMT"), 0.6);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1970-12-28 00:00:00 GMT"), 0.67);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-01-01 00:00:00 GMT"), 0.81);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-01-08 00:00:00 GMT"), 0.78);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-01-12 00:00:00 GMT"), 0.98);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-01-27 00:00:00 GMT"), 1.84);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-02-10 00:00:00 GMT"), 1.80);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-02-18 00:00:00 GMT"), 1.80);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-02-24 00:00:00 GMT"), 1.92);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-03-04 00:00:00 GMT"), 2.49);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-03-11 00:00:00 GMT"), 2.79);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-03-15 00:00:00 GMT"), 2.73);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-03-25 00:00:00 GMT"), 2.61);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-04-02 00:00:00 GMT"), 2.76);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-04-06 00:00:00 GMT"), 2.82);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-04-13 00:00:00 GMT"), 2.8);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-05-03 00:00:00 GMT"), 2.1);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-05-26 00:00:00 GMT"), 1.1);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-06-09 00:00:00 GMT"), 0.25);
		dataChartModel9.addValue("Winter 2007-2008", getDateTime("1971-06-12 00:00:00 GMT"), 0);
		
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1970-10-18 00:00:00 GMT"), 0);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1970-10-26 00:00:00 GMT"), 0.2);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1970-12-01 00:00:00 GMT"), 0.47);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1970-12-11 00:00:00 GMT"), 0.55);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1970-12-25 00:00:00 GMT"), 1.38);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1971-01-08 00:00:00 GMT"), 1.38);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1971-01-15 00:00:00 GMT"), 1.38);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1971-01-01 00:00:00 GMT"), 1.38);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1971-02-08 00:00:00 GMT"), 1.48);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1971-02-21 00:00:00 GMT"), 1.5);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1971-03-12 00:00:00 GMT"), 1.89);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1971-03-25 00:00:00 GMT"), 2.0);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1971-04-04 00:00:00 GMT"), 1.94);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1971-04-09 00:00:00 GMT"), 1.91);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1971-04-13 00:00:00 GMT"), 1.75);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1971-04-19 00:00:00 GMT"), 1.6);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1971-05-25 00:00:00 GMT"), 0.6);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1971-05-31 00:00:00 GMT"), 0.35);
		dataChartModel9.addValue("Winter 2008-2009", getDateTime("1971-06-07 00:00:00 GMT"), 0);
		
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1970-10-09 00:00:00 GMT"), 0);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1970-10-14 00:00:00 GMT"), 0.15);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1970-11-28 00:00:00 GMT"), 0.35);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1970-12-12 00:00:00 GMT"), 0.46);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1971-01-01 00:00:00 GMT"), 0.59);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1971-01-24 00:00:00 GMT"), 0.58);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1971-02-01 00:00:00 GMT"), 0.62);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1971-02-07 00:00:00 GMT"), 0.65);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1971-02-23 00:00:00 GMT"), 0.77);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1971-03-08 00:00:00 GMT"), 0.77);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1971-03-14 00:00:00 GMT"), 0.79);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1971-03-24 00:00:00 GMT"), 0.86);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1971-04-04 00:00:00 GMT"), 0.8);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1971-04-18 00:00:00 GMT"), 0.94);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1971-04-24 00:00:00 GMT"), 0.9);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1971-05-16 00:00:00 GMT"), 0.39);
		dataChartModel9.addValue("Winter 2009-2010", getDateTime("1971-05-21 00:00:00 GMT"), 0.6);
		

		//================================================================================
	    // Logarithmic axis
	    //================================================================================


		chartComp10 = (ZHighCharts) getFellow("chartComp10");
		chartComp10.setType("line");
		chartComp10.setTitle("Logarithmic axis demo");
		chartComp10.setxAxisOptions("{" +
					"tickInterval: 1" +
				"}");
		chartComp10.setyAxisOptions("{" +
					"type: 'logarithmic', " +
					"minorTickInterval: 0.1" +
				"}");
		chartComp10.setTooltipFormatter("function formatTooltip(obj){ " +
					"return '<b>'+ obj.series.name +'</b><br/>" +
					"x = '+ obj.x +' , y = '+ obj.y;" +
				"}");
		chartComp10.setModel(dataChartModel10);

		//Adding some data to the model

		dataChartModel10.addValue("Series 1", 1, 1);
		dataChartModel10.addValue("Series 1", 2, 2);
		dataChartModel10.addValue("Series 1", 3, 4);
		dataChartModel10.addValue("Series 1", 4, 8);
		dataChartModel10.addValue("Series 1", 5, 16);
		dataChartModel10.addValue("Series 1", 6, 32);
		dataChartModel10.addValue("Series 1", 7, 64);
		dataChartModel10.addValue("Series 1", 8, 128);
		dataChartModel10.addValue("Series 1", 9, 256);
		dataChartModel10.addValue("Series 1", 10, 512);
		
		//================================================================================
	    // Scatter plot
	    //================================================================================

		
		chartComp11 = (ZHighCharts) getFellow("chartComp11");
		chartComp11.setType("scatter");
		chartComp11.setOptions("{" +
					"zoomType: 'xy'" +
				"}");
		chartComp11.setTitle("Height Versus Weight of 507 Individuals by Gender");
		chartComp11.setSubTitle("Source: Heinz  2003");
		chartComp11.setxAxisOptions("{" +
					"startOnTick: true," +
					"endOnTick: true," +
					"showLastLabel: true" +
				"}");
		chartComp11.setTooltipFormatter("function formatTooltip(obj){" +
					"return ''+obj.x +' cm, '+ obj.y +' kg';" +
				"}");
		chartComp11.setYAxisTitle("Weight (kg)");
		chartComp11.setXAxisTitle("Height (cm)");
		chartComp11.setLegend("{" +
					"layout: 'vertical'," +
					"align: 'left'," +
					"verticalAlign: 'top'," +
					"x: 100, " +
					"y: 70," +
					"floating: true," +
					"backgroundColor: '#FFFFFF'," +
					"borderWidth: 1" +
				"}");
		chartComp11.setPlotOptions("{" +
					"scatter: {" +
						"marker: { " +
							"radius: 5," +
							"states: {" +
								"hover: {" +
									"enabled: true," +
									"ineColor: 'rgb(100,100,100)'" +
								"}" +
							"}" +
						"}," +
						"states: {" +
							"hover: {" +
								"marker: {" +
									"enabled: false" +
								"}" +
							"}" +
						"}" +
					"}" +
				"}");
				
		chartComp11.setModel(dataChartModel11);
		
		//Adding some data to the model
		 
		style = new HashMap();
		style.put("color", "rgba(223, 83, 83, .5)");
		chartComp11.setSeriesOptions("Female", style);
		
		Number femaledata [][] = {{161.2, 51.6}, {167.5, 59.0}, {159.5, 49.2}, {157.0, 63.0}, {155.8, 53.6},
                {170.0, 59.0}, {159.1, 47.6}, {166.0, 69.8}, {176.2, 66.8}, {160.2, 75.2},
                {172.5, 55.2}, {170.9, 54.2}, {172.9, 62.5}, {153.4, 42.0}, {160.0, 50.0},
                {147.2, 49.8}, {168.2, 49.2}, {175.0, 73.2}, {157.0, 47.8}, {167.6, 68.8},
                {159.5, 50.6}, {175.0, 82.5}, {166.8, 57.2}, {176.5, 87.8}, {170.2, 72.8},
                {174.0, 54.5}, {173.0, 59.8}, {179.9, 67.3}, {170.5, 67.8}, {160.0, 47.0},
                {154.4, 46.2}, {162.0, 55.0}, {176.5, 83.0}, {160.0, 54.4}, {152.0, 45.8},
                {162.1, 53.6}, {170.0, 73.2}, {160.2, 52.1}, {161.3, 67.9}, {166.4, 56.6},
                {168.9, 62.3}, {163.8, 58.5}, {167.6, 54.5}, {160.0, 50.2}, {161.3, 60.3},
                {167.6, 58.3}, {165.1, 56.2}, {160.0, 50.2}, {170.0, 72.9}, {157.5, 59.8},
                {167.6, 61.0}, {160.7, 69.1}, {163.2, 55.9}, {152.4, 46.5}, {157.5, 54.3},
                {168.3, 54.8}, {180.3, 60.7}, {165.5, 60.0}, {165.0, 62.0}, {164.5, 60.3},
                {156.0, 52.7}, {160.0, 74.3}, {163.0, 62.0}, {165.7, 73.1}, {161.0, 80.0},
                {162.0, 54.7}, {166.0, 53.2}, {174.0, 75.7}, {172.7, 61.1}, {167.6, 55.7},
                {151.1, 48.7}, {164.5, 52.3}, {163.5, 50.0}, {152.0, 59.3}, {169.0, 62.5},
                {164.0, 55.7}, {161.2, 54.8}, {155.0, 45.9}, {170.0, 70.6}, {176.2, 67.2},
                {170.0, 69.4}, {162.5, 58.2}, {170.3, 64.8}, {164.1, 71.6}, {169.5, 52.8},
                {163.2, 59.8}, {154.5, 49.0}, {159.8, 50.0}, {173.2, 69.2}, {170.0, 55.9},
                {161.4, 63.4}, {169.0, 58.2}, {166.2, 58.6}, {159.4, 45.7}, {162.5, 52.2},
                {159.0, 48.6}, {162.8, 57.8}, {159.0, 55.6}, {179.8, 66.8}, {162.9, 59.4},
                {161.0, 53.6}, {151.1, 73.2}, {168.2, 53.4}, {168.9, 69.0}, {173.2, 58.4},
                {171.8, 56.2}, {178.0, 70.6}, {164.3, 59.8}, {163.0, 72.0}, {168.5, 65.2},
                {166.8, 56.6}, {172.7, 105.2}, {163.5, 51.8}, {169.4, 63.4}, {167.8, 59.0},
                {159.5, 47.6}, {167.6, 63.0}, {161.2, 55.2}, {160.0, 45.0}, {163.2, 54.0},
                {162.2, 50.2}, {161.3, 60.2}, {149.5, 44.8}, {157.5, 58.8}, {163.2, 56.4},
                {172.7, 62.0}, {155.0, 49.2}, {156.5, 67.2}, {164.0, 53.8}, {160.9, 54.4},
                {162.8, 58.0}, {167.0, 59.8}, {160.0, 54.8}, {160.0, 43.2}, {168.9, 60.5},
                {158.2, 46.4}, {156.0, 64.4}, {160.0, 48.8}, {167.1, 62.2}, {158.0, 55.5},
                {167.6, 57.8}, {156.0, 54.6}, {162.1, 59.2}, {173.4, 52.7}, {159.8, 53.2},
                {170.5, 64.5}, {159.2, 51.8}, {157.5, 56.0}, {161.3, 63.6}, {162.6, 63.2},
                {160.0, 59.5}, {168.9, 56.8}, {165.1, 64.1}, {162.6, 50.0}, {165.1, 72.3},
                {166.4, 55.0}, {160.0, 55.9}, {152.4, 60.4}, {170.2, 69.1}, {162.6, 84.5},
                {170.2, 55.9}, {158.8, 55.5}, {172.7, 69.5}, {167.6, 76.4}, {162.6, 61.4},
                {167.6, 65.9}, {156.2, 58.6}, {175.2, 66.8}, {172.1, 56.6}, {162.6, 58.6},
                {160.0, 55.9}, {165.1, 59.1}, {182.9, 81.8}, {166.4, 70.7}, {165.1, 56.8},
                {177.8, 60.0}, {165.1, 58.2}, {175.3, 72.7}, {154.9, 54.1}, {158.8, 49.1},
                {172.7, 75.9}, {168.9, 55.0}, {161.3, 57.3}, {167.6, 55.0}, {165.1, 65.5},
                {175.3, 65.5}, {157.5, 48.6}, {163.8, 58.6}, {167.6, 63.6}, {165.1, 55.2},
                {165.1, 62.7}, {168.9, 56.6}, {162.6, 53.9}, {164.5, 63.2}, {176.5, 73.6},
                {168.9, 62.0}, {175.3, 63.6}, {159.4, 53.2}, {160.0, 53.4}, {170.2, 55.0},
                {162.6, 70.5}, {167.6, 54.5}, {162.6, 54.5}, {160.7, 55.9}, {160.0, 59.0},
                {157.5, 63.6}, {162.6, 54.5}, {152.4, 47.3}, {170.2, 67.7}, {165.1, 80.9},
                {172.7, 70.5}, {165.1, 60.9}, {170.2, 63.6}, {170.2, 54.5}, {170.2, 59.1},
                {161.3, 70.5}, {167.6, 52.7}, {167.6, 62.7}, {165.1, 86.3}, {162.6, 66.4},
                {152.4, 67.3}, {168.9, 63.0}, {170.2, 73.6}, {175.2, 62.3}, {175.2, 57.7},
                {160.0, 55.4}, {165.1, 104.1}, {174.0, 55.5}, {170.2, 77.3}, {160.0, 80.5},
                {167.6, 64.5}, {167.6, 72.3}, {167.6, 61.4}, {154.9, 58.2}, {162.6, 81.8},
                {175.3, 63.6}, {171.4, 53.4}, {157.5, 54.5}, {165.1, 53.6}, {160.0, 60.0},
                {174.0, 73.6}, {162.6, 61.4}, {174.0, 55.5}, {162.6, 63.6}, {161.3, 60.9},
                {156.2, 60.0}, {149.9, 46.8}, {169.5, 57.3}, {160.0, 64.1}, {175.3, 63.6},
                {169.5, 67.3}, {160.0, 75.5}, {172.7, 68.2}, {162.6, 61.4}, {157.5, 76.8},
                {176.5, 71.8}, {164.4, 55.5}, {160.7, 48.6}, {174.0, 66.4}, {163.8, 67.3}};
		
		for(int f = 0 ; f<femaledata.length ; f++)
			dataChartModel11.addValue("Female", femaledata[f][0], femaledata[f][1]);
		
		style = new HashMap();
		style.put("color", "rgba(119, 152, 191, .5)");
		chartComp11.setSeriesOptions("Male", style);
		
		Number maledata [][] = {{174.0, 65.6}, {175.3, 71.8}, {193.5, 80.7}, {186.5, 72.6}, {187.2, 78.8},
                {181.5, 74.8}, {184.0, 86.4}, {184.5, 78.4}, {175.0, 62.0}, {184.0, 81.6},
                {180.0, 76.6}, {177.8, 83.6}, {192.0, 90.0}, {176.0, 74.6}, {174.0, 71.0},
                {184.0, 79.6}, {192.7, 93.8}, {171.5, 70.0}, {173.0, 72.4}, {176.0, 85.9},
                {176.0, 78.8}, {180.5, 77.8}, {172.7, 66.2}, {176.0, 86.4}, {173.5, 81.8},
                {178.0, 89.6}, {180.3, 82.8}, {180.3, 76.4}, {164.5, 63.2}, {173.0, 60.9},
                {183.5, 74.8}, {175.5, 70.0}, {188.0, 72.4}, {189.2, 84.1}, {172.8, 69.1},
                {170.0, 59.5}, {182.0, 67.2}, {170.0, 61.3}, {177.8, 68.6}, {184.2, 80.1},
                {186.7, 87.8}, {171.4, 84.7}, {172.7, 73.4}, {175.3, 72.1}, {180.3, 82.6},
                {182.9, 88.7}, {188.0, 84.1}, {177.2, 94.1}, {172.1, 74.9}, {167.0, 59.1},
                {169.5, 75.6}, {174.0, 86.2}, {172.7, 75.3}, {182.2, 87.1}, {164.1, 55.2},
                {163.0, 57.0}, {171.5, 61.4}, {184.2, 76.8}, {174.0, 86.8}, {174.0, 72.2},
                {177.0, 71.6}, {186.0, 84.8}, {167.0, 68.2}, {171.8, 66.1}, {182.0, 72.0},
                {167.0, 64.6}, {177.8, 74.8}, {164.5, 70.0}, {192.0, 101.6}, {175.5, 63.2},
                {171.2, 79.1}, {181.6, 78.9}, {167.4, 67.7}, {181.1, 66.0}, {177.0, 68.2},
                {174.5, 63.9}, {177.5, 72.0}, {170.5, 56.8}, {182.4, 74.5}, {197.1, 90.9},
                {180.1, 93.0}, {175.5, 80.9}, {180.6, 72.7}, {184.4, 68.0}, {175.5, 70.9},
                {180.6, 72.5}, {177.0, 72.5}, {177.1, 83.4}, {181.6, 75.5}, {176.5, 73.0},
                {175.0, 70.2}, {174.0, 73.4}, {165.1, 70.5}, {177.0, 68.9}, {192.0, 102.3},
                {176.5, 68.4}, {169.4, 65.9}, {182.1, 75.7}, {179.8, 84.5}, {175.3, 87.7},
                {184.9, 86.4}, {177.3, 73.2}, {167.4, 53.9}, {178.1, 72.0}, {168.9, 55.5},
                {157.2, 58.4}, {180.3, 83.2}, {170.2, 72.7}, {177.8, 64.1}, {172.7, 72.3},
                {165.1, 65.0}, {186.7, 86.4}, {165.1, 65.0}, {174.0, 88.6}, {175.3, 84.1},
                {185.4, 66.8}, {177.8, 75.5}, {180.3, 93.2}, {180.3, 82.7}, {177.8, 58.0},
                {177.8, 79.5}, {177.8, 78.6}, {177.8, 71.8}, {177.8, 116.4}, {163.8, 72.2},
                {188.0, 83.6}, {198.1, 85.5}, {175.3, 90.9}, {166.4, 85.9}, {190.5, 89.1},
                {166.4, 75.0}, {177.8, 77.7}, {179.7, 86.4}, {172.7, 90.9}, {190.5, 73.6},
                {185.4, 76.4}, {168.9, 69.1}, {167.6, 84.5}, {175.3, 64.5}, {170.2, 69.1},
                {190.5, 108.6}, {177.8, 86.4}, {190.5, 80.9}, {177.8, 87.7}, {184.2, 94.5},
                {176.5, 80.2}, {177.8, 72.0}, {180.3, 71.4}, {171.4, 72.7}, {172.7, 84.1},
                {172.7, 76.8}, {177.8, 63.6}, {177.8, 80.9}, {182.9, 80.9}, {170.2, 85.5},
                {167.6, 68.6}, {175.3, 67.7}, {165.1, 66.4}, {185.4, 102.3}, {181.6, 70.5},
                {172.7, 95.9}, {190.5, 84.1}, {179.1, 87.3}, {175.3, 71.8}, {170.2, 65.9},
                {193.0, 95.9}, {171.4, 91.4}, {177.8, 81.8}, {177.8, 96.8}, {167.6, 69.1},
                {167.6, 82.7}, {180.3, 75.5}, {182.9, 79.5}, {176.5, 73.6}, {186.7, 91.8},
                {188.0, 84.1}, {188.0, 85.9}, {177.8, 81.8}, {174.0, 82.5}, {177.8, 80.5},
                {171.4, 70.0}, {185.4, 81.8}, {185.4, 84.1}, {188.0, 90.5}, {188.0, 91.4},
                {182.9, 89.1}, {176.5, 85.0}, {175.3, 69.1}, {175.3, 73.6}, {188.0, 80.5},
                {188.0, 82.7}, {175.3, 86.4}, {170.5, 67.7}, {179.1, 92.7}, {177.8, 93.6},
                {175.3, 70.9}, {182.9, 75.0}, {170.8, 93.2}, {188.0, 93.2}, {180.3, 77.7},
                {177.8, 61.4}, {185.4, 94.1}, {168.9, 75.0}, {185.4, 83.6}, {180.3, 85.5},
                {174.0, 73.9}, {167.6, 66.8}, {182.9, 87.3}, {160.0, 72.3}, {180.3, 88.6},
                {167.6, 75.5}, {186.7, 101.4}, {175.3, 91.1}, {175.3, 67.3}, {175.9, 77.7},
                {175.3, 81.8}, {179.1, 75.5}, {181.6, 84.5}, {177.8, 76.6}, {182.9, 85.0},
                {177.8, 102.5}, {184.2, 77.3}, {179.1, 71.8}, {176.5, 87.9}, {188.0, 94.3},
                {174.0, 70.9}, {167.6, 64.5}, {170.2, 77.3}, {167.6, 72.3}, {188.0, 87.3},
                {174.0, 80.0}, {176.5, 82.3}, {180.3, 73.6}, {167.6, 74.1}, {188.0, 85.9},
                {180.3, 73.2}, {167.6, 76.3}, {183.0, 65.9}, {183.0, 90.9}, {179.1, 89.1},
                {170.2, 62.3}, {177.8, 82.7}, {179.1, 79.1}, {190.5, 98.2}, {177.8, 84.1},
                {180.3, 83.2}, {180.3, 83.2}};
		
		for(int m = 0 ; m<maledata.length ; m++)
			dataChartModel11.addValue("Male", maledata[m][0], maledata[m][1]);

		//================================================================================
	    // Area with negative values
	    //================================================================================

		
		chartComp12 = (ZHighCharts) getFellow("chartComp12");
		chartComp12.setType("area");
		chartComp12.setTitle("Area chart with negative values");
		chartComp12.setxAxisOptions("{" +
					"categories: ['" +
						"Apples', " +
						"'Oranges', " +
						"'Pears', " +
						"'Grapes', " +
						"'Bananas'" +
					"]" +
				"}");
		chartComp12.setTooltipFormatter("function formatTooltip(obj){" +
					"return '' + obj.series.name +': '+ obj.y +'';" +
				"}");
		
		chartComp12.setModel(dataChartModel12);
		
		//Adding some data to the model

		dataChartModel12.addValue("John", 0, 5);
		dataChartModel12.addValue("John", 1, 3);
		dataChartModel12.addValue("John", 2, 4);
		dataChartModel12.addValue("John", 3, 7);
		dataChartModel12.addValue("John", 4, 2);
		
		dataChartModel12.addValue("Jane", 0, 2);
		dataChartModel12.addValue("Jane", 1, -2);
		dataChartModel12.addValue("Jane", 2, -3);
		dataChartModel12.addValue("Jane", 3, 2);
		dataChartModel12.addValue("Jane", 4, 1);
		
		dataChartModel12.addValue("Joe", 0, 3);
		dataChartModel12.addValue("Joe", 1, 4);
		dataChartModel12.addValue("Joe", 2, 4);
		dataChartModel12.addValue("Joe", 3, -2);
		dataChartModel12.addValue("Joe", 4, 5);
		
		//================================================================================
	    // Stacked area
	    //================================================================================

		
		chartComp13 = (ZHighCharts) getFellow("chartComp13");
		chartComp13.setType("area");
		chartComp13.setTitle("Historic and Estimated Worldwide Population Growth by Region");
		chartComp13.setSubTitle("Source: Wikipedia.org");
		chartComp13.setxAxisOptions("{" +
					"categories: [" +
						"'1750', " +
						"'1800', " +
						"'1850', " +
						"'1900', " +
						"'1950', " +
						"'1999', " +
						"'2050'" +
					"]," +
					"tickmarkPlacement: 'on'," +
					"title: {" +
						"enabled: false" +
					"}" +
				"}");
		chartComp13.setyAxisOptions("{" +
					"labels: {" +
						"formatter: function() {" +
							"return this.value / 1000;" +
						"}" +
					"}" +
				"}");
		chartComp13.setTooltipFormatter("function formatTooltip(obj){" +
					"return ''+obj.x +': '+ Highcharts.numberFormat(obj.y, 0, ',') +' millions';" +
				"}");
		chartComp13.setYAxisTitle("Billions");
		chartComp13.setLegend("{" +
					"layout: 'vertical'," +
					"align: 'left'," +
					"verticalAlign: 'top'," +
					"x: 100, " +
					"y: 70," +
					"floating: true," +
					"backgroundColor: '#FFFFFF'," +
					"borderWidth: 1" +
				"}");
		chartComp13.setPlotOptions("{" +
					"area: {" +
						"stacking: 'normal'," +
						"lineColor: '#666666', " +
						"lineWidth: 1," +
						"marker: {" +
							"lineWidth: 1, " +
							"lineColor: '#666666'" +
						"}" +
					"}" +
				"}");
				
		chartComp13.setModel(dataChartModel13);
		
		//Adding some data to the model

		dataChartModel13.addValue("Asia", 0, 502);
		dataChartModel13.addValue("Asia", 1, 635);
		dataChartModel13.addValue("Asia", 2, 809);
		dataChartModel13.addValue("Asia", 3, 947);
		dataChartModel13.addValue("Asia", 4, 1402);
		dataChartModel13.addValue("Asia", 5, 3634);
		dataChartModel13.addValue("Asia", 6, 5268);
		
		dataChartModel13.addValue("Africa", 0, 106);
		dataChartModel13.addValue("Africa", 1, 107);
		dataChartModel13.addValue("Africa", 2, 111);
		dataChartModel13.addValue("Africa", 3, 133);
		dataChartModel13.addValue("Africa", 4, 224);
		dataChartModel13.addValue("Africa", 5, 767);
		dataChartModel13.addValue("Africa", 6, 1766);
		
		dataChartModel13.addValue("Europe", 0, 163);
		dataChartModel13.addValue("Europe", 1, 203);
		dataChartModel13.addValue("Europe", 2, 276);
		dataChartModel13.addValue("Europe", 3, 408);
		dataChartModel13.addValue("Europe", 4, 547);
		dataChartModel13.addValue("Europe", 5, 729);
		dataChartModel13.addValue("Europe", 6, 628);
		
		dataChartModel13.addValue("America", 0, 18);
		dataChartModel13.addValue("America", 1, 31);
		dataChartModel13.addValue("America", 2, 54);
		dataChartModel13.addValue("America", 3, 156);
		dataChartModel13.addValue("America", 4, 339);
		dataChartModel13.addValue("America", 5, 818);
		dataChartModel13.addValue("America", 6, 1201);
		
		dataChartModel13.addValue("Oceania", 0, 2);
		dataChartModel13.addValue("Oceania", 1, 2);
		dataChartModel13.addValue("Oceania", 2, 2);
		dataChartModel13.addValue("Oceania", 3, 3);
		dataChartModel13.addValue("Oceania", 4, 13);
		dataChartModel13.addValue("Oceania", 5, 30);
		dataChartModel13.addValue("Oceania", 6, 46);
		
		//================================================================================
	    // Percentage area
	    //================================================================================

		
		chartComp14 = (ZHighCharts) getFellow("chartComp14");
		chartComp14.setType("area");
		chartComp14.setTitle("Historic and Estimated Worldwide Population Distribution by Region");
		chartComp14.setSubTitle("Source: Wikipedia.org");
		chartComp14.setxAxisOptions("{" +
					"categories: [" +
						"'1750', " +
						"'1800', " +
						"'1850', " +
						"'1900', " +
						"'1950', " +
						"'1999', " +
						"'2050'" +
					"]," +
					"tickmarkPlacement: 'on'," +
					"title: {" +
						"enabled: false" +
					"}" +
				"}");
		chartComp14.setTooltipFormatter("function formatTooltip(obj){" +
					"return ''+obj.x +': '+ Highcharts.numberFormat(obj.percentage, 1) + " +
					"'% ('+Highcharts.numberFormat(obj.y, 0, ',') +' millions)';" +
				"}");
		chartComp14.setYAxisTitle("Percent");
		chartComp14.setPlotOptions("{ " +
					"area: {" +
						"stacking: 'percent'," +
						"lineColor: '#ffffff'," +
						"lineWidth: 1," +
						"marker: {" +
							"lineWidth: 1," +
							"lineColor: '#ffffff'" +
						"}" +
					"}" +
				"}");
				
		chartComp14.setModel(dataChartModel13);
		
		//================================================================================
	    // Area with missing points
	    //================================================================================

		chartComp15 = (ZHighCharts) getFellow("chartComp15");
		chartComp15.setType("area");
		chartComp15.setOptions("{" +
					"spacingBottom: 30" +
				"}");
		chartComp15.setTitle("Fruit consumption *");
		chartComp15.setSubTitle("* Jane\'s banana consumption is unknown");
		chartComp15.setxAxisOptions("{" +
					"categories: [" +
						"'Apples', " +
						"'Pears', " +
						"'Oranges', " +
						"'Bananas', " +
						"'Grapes', " +
						"'Plums', " +
						"'Strawberries', " +
						"'Raspberries'" +
					"]" +
				"}");
		chartComp15.setyAxisOptions("{" +
					"labels: {" +
						"formatter: function() {" +
							"return this.value;" +
						"}" +
					"}" +
				"}");
		chartComp15.setTooltipFormatter("function formatTooltip(obj){" +
					"return '<b>'+ obj.series.name +'</b><br/>'+" +
					"obj.x +': '+ obj.y;" +
				"}");
		chartComp15.setYAxisTitle("Y-Axis");
		chartComp15.setLegend("{" +
					"layout: 'vertical'," +
					"align: 'left'," +
					"verticalAlign: 'top'," +
					"x: 150," +
					"y: 100," +
					"floating: true," +
					"borderWidth: 1," +
					"backgroundColor: '#FFFFFF'" +
				"}");
		chartComp15.setPlotOptions("{ " +
					"area: {" +
						"fillOpacity: 0.5" +
					"}" +
				"}");
					
		chartComp15.setModel(dataChartModel15);
		
		//Adding some data to the model

		dataChartModel15.addValue("John", 0, 0);
		dataChartModel15.addValue("John", 1, 1);
		dataChartModel15.addValue("John", 2, 4);
		dataChartModel15.addValue("John", 3, 4);
		dataChartModel15.addValue("John", 4, 5);
		dataChartModel15.addValue("John", 5, 2);
		dataChartModel15.addValue("John", 6, 3);
		dataChartModel15.addValue("John", 7, 7);
		
		dataChartModel15.addValue("Jane", 0, 1);
		dataChartModel15.addValue("Jane", 1, 0);
		dataChartModel15.addValue("Jane", 2, 3);
		dataChartModel15.addValue("Jane", 3, null);
		dataChartModel15.addValue("Jane", 4, 3);
		dataChartModel15.addValue("Jane", 5, 1);
		dataChartModel15.addValue("Jane", 6, 2);
		dataChartModel15.addValue("Jane", 7, 1);
		
		//================================================================================
	    // Inverted axes
	    //================================================================================
		
		chartComp16 = (ZHighCharts) getFellow("chartComp16");
		chartComp16.setType("area");
		chartComp16.setOptions("{" +
					"inverted:true" +
				"}");
		chartComp16.setTitle("Average fruit consumption during one week");
		chartComp16.setxAxisOptions("{" +
					"categories: [" +
						"'Monday'," +
						"'Tuesday'," +
						"'Wednesday'," +
						"'Thursday'," +
						"'Friday'," +
						"'Saturday'," +
						"'Sunday'" +
					"]" +
				"}");
		chartComp16.setyAxisOptions("{" +
					"labels: {" +
						"formatter: function() {" +
							"return this.value;" +
						"}" +
					"}," +
					"min: 0" +
				"}");
		chartComp16.setTooltipFormatter("function formatTooltip(obj){" +
					"return ''+obj.x +': '+ obj.y;" +
				"}");
		chartComp16.setYAxisTitle("Number of units");
		chartComp16.setLegend("{" +
					"layout: 'vertical'," +
					"align: 'right'," +
					"verticalAlign: 'top'," +
					"x: -150," +
					"y: 100," +
					"floating: true," +
					"borderWidth: 1," +
					"backgroundColor: '#FFFFFF'" +
				"}");
		chartComp16.setPlotOptions("{" +
					"area: {" +
						"fillOpacity: 0.5" +
					"}" +
				"}");
				
		chartComp16.setModel(dataChartModel16);
		
		//Adding some data to the model
		
		dataChartModel16.addValue("John", 0, 3);
		dataChartModel16.addValue("John", 1, 4);
		dataChartModel16.addValue("John", 2, 3);
		dataChartModel16.addValue("John", 3, 5);
		dataChartModel16.addValue("John", 4, 4);
		dataChartModel16.addValue("John", 5, 10);
		dataChartModel16.addValue("John", 6, 12);
		
		dataChartModel16.addValue("Jane", 0, 1);
		dataChartModel16.addValue("Jane", 1, 3);
		dataChartModel16.addValue("Jane", 2, 4);
		dataChartModel16.addValue("Jane", 3, 3);
		dataChartModel16.addValue("Jane", 4, 3);
		dataChartModel16.addValue("Jane", 5, 5);
		dataChartModel16.addValue("Jane", 6, 4);
		
		//================================================================================
	    // Area-spline
	    //================================================================================
		
		
		chartComp17 = (ZHighCharts) getFellow("chartComp17");
		chartComp17.setType("areaspline");
		chartComp17.setTitle("Average fruit consumption during one week");
		chartComp17.setxAxisOptions("{" +
					"categories: [" +
						"'Monday'," +
						"'Tuesday'," +
						"'Wednesday'," +
						"'Thursday'," +
						"'Friday'," +
						"'Saturday'," +
						"'Sunday'" +
					"]," +
					"plotBands: [" +
						"{" +
							"from: 4.5," +
							"to: 6.5," +
							"color: 'rgba(68, 170, 213, .2)'" +
						"}" +
					"]" +
				"}");
		chartComp17.setyAxisOptions("{" +
					"labels: {" +
						"formatter: function() {" +
							"return this.value;" +
						"}" +
					"}," +
					"min: 0" +
				"}");
		chartComp17.setTooltipFormatter("function formatTooltip(obj){" +
					"return ''+obj.x +': '+ obj.y +' units';" +
				"}");
		chartComp17.setYAxisTitle("Fruit units");
		chartComp17.setLegend("{" +
					"layout: 'vertical'," +
					"align: 'left'," +
					"verticalAlign: 'top'," +
					"x: 150," +
					"y: 100," +
					"floating: true," +
					"borderWidth: 1," +
					"backgroundColor: '#FFFFFF'" +
				"}");
		chartComp17.setPlotOptions("{" +
					"areaspline: {" +
						"fillOpacity: 0.5" +
					"}" +
				"}");
				
		chartComp17.setModel(dataChartModel16);
		
		//================================================================================
	    // Basic bar
	    //================================================================================
		
		
		chartComp18 = (ZHighCharts) getFellow("chartComp18");
		chartComp18.setType("bar");
		chartComp18.setTitle("Historic World Population by Region");
		chartComp18.setSubTitle("Source: Wikipedia.org");
		chartComp18.setxAxisOptions("{" +
					"categories: [" +
						"'Africa', " +
						"'America', " +
						"'Asia', " +
						"'Europe', " +
						"'Oceania'" +
					"]" +
				"}");
		chartComp18.setyAxisOptions("{" +
					"min: 0," +
					"labels: {" +
						"overflow: 'justify'" +
					"}" +
				"}");
		chartComp18.setTooltipFormatter("function formatTooltip(obj){" +
					"return ''+obj.series.name +': '+ obj.y +' millions';" +
				"}");
		chartComp18.setYAxisTitle("Population (millions)");
		chartComp18.setLegend("{" +
					"layout: 'vertical'," +
					"align: 'right'," +
					"verticalAlign: 'top'," +
					"x: -100," +
					"y: 100," +
					"floating: true," +
					"borderWidth: 1," +
					"backgroundColor: '#FFFFFF'," +
					"shadow: true" +
				"}");
		chartComp18.setPlotOptions("{" +
					"bar: {" +
						"dataLabels: {" +
							"enabled: true" +
						"}" +
					"}" +
				"}");
				
		chartComp18.setModel(dataChartModel18);
		
		//Adding some data to the model

		dataChartModel18.addValue("Year 1800", 0, 107);
		dataChartModel18.addValue("Year 1800", 1, 31);
		dataChartModel18.addValue("Year 1800", 2, 635);
		dataChartModel18.addValue("Year 1800", 3, 203);
		dataChartModel18.addValue("Year 1800", 4, 2);
		
		dataChartModel18.addValue("Year 1900", 0, 133);
		dataChartModel18.addValue("Year 1900", 1, 156);
		dataChartModel18.addValue("Year 1900", 2, 947);
		dataChartModel18.addValue("Year 1900", 3, 408);
		dataChartModel18.addValue("Year 1900", 4, 6);
		
		dataChartModel18.addValue("Year 2008", 0, 973);
		dataChartModel18.addValue("Year 2008", 1, 914);
		dataChartModel18.addValue("Year 2008", 2, 4054);
		dataChartModel18.addValue("Year 2008", 3, 732);
		dataChartModel18.addValue("Year 2008", 4, 34);
		
		//================================================================================
	    // Stacked bar
	    //================================================================================
				
		chartComp19 = (ZHighCharts) getFellow("chartComp19");
		chartComp19.setType("bar");
		chartComp19.setTitle("Stacked bar chart");
		chartComp19.setxAxisOptions("{" +
					"categories: [" +
						"'Apples', " +
						"'Oranges', " +
						"'Pears', " +
						"'Grapes', " +
						"'Bananas'" +
					"]" +
				"}");
		chartComp19.setyAxisOptions("{" +
					"min: 0" +
				"}");
		chartComp19.setTooltipFormatter("function formatTooltip(obj){" +
					"return ''+obj.series.name +': '+ obj.y +'';" +
				"}");
		chartComp19.setYAxisTitle("Total fruit consumption");
		chartComp19.setLegend("{" +
					"backgroundColor: '#FFFFFF', " +
					"reversed: true" +
				"}");
		chartComp19.setPlotOptions("{" +
					"series: {" +
						"stacking: 'normal'" +
					"}" +
				"}");
				
		chartComp19.setModel(dataChartModel12);
		
		//================================================================================
	    // Pie with legend
	    //================================================================================
				
		
		chartComp20 = (ZHighCharts) getFellow("chartComp20");
		chartComp20.setOptions("{" +
					"plotBackgroundColor: null," +
					"plotBorderWidth: null," +
					"plotShadow: false" +
				"}");
		chartComp20.setTitle("Browser market shares at a specific website, 2010");
		chartComp20.setType("pie");
		chartComp20.setTooltipFormatter("function formatTooltip(obj){" +
					"return obj.key + '<br />" +
					"Browser Share: <b>'+obj.y+'%</b>'" +
				"}");
		chartComp20.setPlotOptions("{" +
					"pie: {" +
						"allowPointSelect: true," +
						"cursor: 'pointer'," +
						"dataLabels: {" +
							"enabled: false" +
						"}," +
						"showInLegend: true" +
					"}" +
				"}");
		chartComp20.setModel(pieModel);

		
		//================================================================================
	    // Spline updating each second
	    //================================================================================
				
		
		chartComp21 = (ZHighCharts) getFellow("chartComp21");
		chartComp21.setOptions("{" +
					"marginRight: 10," +
					"events: {" +
						"load: function() {" +
							"var series = this.series[0];" +
							"setInterval(function() {" +
								"var x = (new Date()).getTime(),y = Math.random();" +
								"series.addPoint([x, y], true, true);" +
								"}," +
							" 1000);" +
						"}" +
					"}" +
				"}");
		chartComp21.setTitle("Live random data");
		chartComp21.setType("spline");
		chartComp21.setxAxisOptions("{ " +
					"type: 'datetime'," +
					"tickPixelInterval: 150" +
				"}");
		chartComp21.setyAxisOptions("{" +
					"plotLines: [" +
						"{" +
							"value: 0," +
							"width: 1," +
							"color: '#808080'" +
						"}" +
					"]" +
				"}");
		chartComp21.setYAxisTitle("Value");
		chartComp21.setTooltipFormatter("function formatTooltip(obj){" +
					"return '<b>'+ obj.series.name +'</b><br/>" +
					"'+Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', obj.x) +'<br/>" +
					"'+Highcharts.numberFormat(obj.y, 2);" +
				"}");
		chartComp21.setModel(pieModel);
		chartComp21.setPlotOptions("{" +
					"pie: {" +
						"allowPointSelect: true," +
						"cursor: 'pointer'," +
						"dataLabels: {" +
							"enabled: false" +
						"}," +
						"showInLegend: true" +
					"}" +
				"}");
		chartComp21.setLegend("{" +
					"enabled: false " +
				"}");
		
		chartComp21.setModel(dataChartModel21);
		
		//Adding some random data to the model

		for (int i = -19; i <= 0; i++){
			dataChartModel21.addValue("Random data",getDateTime(sdf.format(new Date())) + i *1000, Math.random());
		}
		
		//================================================================================
	    // Click to add a point
	    //================================================================================
				
		
		chartComp22 = (ZHighCharts) getFellow("chartComp22");
		chartComp22.setOptions("{" +
					"margin: [70, 50, 60, 80]," +
					"events: {" +
						"click: function(e) { " +
							"var x = e.xAxis[0].value, y = e.yAxis[0].value,series = this.series[0];" +
							"series.addPoint([x, y]);" +
						"}" +
					"}" +
				"}");
		chartComp22.setTitle("User supplied data");
		chartComp22.setSubTitle("Click the plot area to add a point. Click a point to remove it.");
		chartComp22.setType("scatter");
		chartComp22.setxAxisOptions("{  " +
					"minPadding: 0.2," +
					"maxPadding: 0.2," +
					"maxZoom: 60" +
				"}");
		chartComp22.setyAxisOptions("{" +
					"minPadding: 0.2," +
					"maxPadding: 0.2," +
					"maxZoom: 60," +
					"plotLines: [" +
						"{" +
							"value: 0," +
							"width: 1," +
							"color: '#808080' " +
						"}" +
					"]" +
				"}");
		chartComp22.setYAxisTitle("Value");
		chartComp22.setPlotOptions("{" +
					"series: {" +
						"lineWidth: 1," +
						"point: {" +
							"events: { " +
								"'click': function() {" +
									"if (this.series.data.length > 1) " +
										"this.remove();" +
									"}" +
								"}" +
							"}" +
						"}" +
					"}");
		chartComp22.setLegend("{" +
					"enabled: false " +
				"}");
		chartComp22.setTooltipFormatter("function formatTooltip(obj){" +
					"return '<b>'+ obj.series.name +'</b><br/>" +
					"'+ obj.x +': '+ obj.y;" +
				"}");
		
		chartComp22.setModel(dataChartModel22);
		
		//Adding some data to the model

		dataChartModel22.addValue("Series 1",20, 20);
		dataChartModel22.addValue("Series 1",80, 80);
		
		
		//================================================================================
	    // Bar with negative stack
	    //================================================================================
				
		chartComp23 = (ZHighCharts) getFellow("chartComp23");
		chartComp23.setType("bar");
		chartComp23.setTitle("Population pyramid for Germany, midyear 2010");
		chartComp23.setSubTitle("Source: www.census.gov");
		chartComp23.setxAxisOptions("[" +
					"{" +
						"categories : [" +
							"'0-4', '5-9', '10-14', '15-19', '20-24', '25-29', '30-34', '35-39'," +
							" '40-44','45-49', '50-54', '55-59', '60-64', '65-69','70-74', '75-79'," +
							" '80-84', '85-89', '90-94','95-99', '100 +'" +
						"]," +
						"reversed: false " +
					"}, " +
					"{ " +
						"opposite: true," +
						"reversed: false," +
						"categories: [" +
							"'0-4', '5-9', '10-14', '15-19', '20-24', '25-29', '30-34', '35-39'," +
							" '40-44','45-49', '50-54', '55-59', '60-64', '65-69','70-74', '75-79'," +
							" '80-84', '85-89', '90-94','95-99', '100 +'" +
						"]," +
						"linkedTo: 0" +
					"}" +
				"]");
		chartComp23.setyAxisOptions("{" +
					"labels: {" +
						"formatter: function(){" +
							"return (Math.abs(this.value) / 1000000) + 'M';" +
						"}" +
					"}," +
					"min: -4000000," +
					"max: 4000000" +
				"}");
		chartComp23.setPlotOptions("{" +
					"series: {" +
						"stacking: 'normal'" +
					"}" +
				"}");
		chartComp23.setLegend("{" +
					"enabled: false " +
				"}");
		chartComp23.setTooltipFormatter("function formatTooltip(obj){" +
					"return '<b>'+ obj.series.name +', age '+ obj.point.category +'</b><br/>" +
					"'+'Population: '+ Highcharts.numberFormat(Math.abs(obj.point.y), 0);" +
				"}");
		
		chartComp23.setModel(dataChartModel23);
		
		//Adding some data to the model
		
		Number maleBarData [] = {
				-1746181, -1884428, -2089758, -2222362, -2537431, -2507081, -2443179,
                -2664537, -3556505, -3680231, -3143062, -2721122, -2229181, -2227768,
                -2176300, -1329968, -836804, -354784, -90569, -28367, -3878
		};
		
		for(int mb = 0; mb<maleBarData.length;mb++ ){
			dataChartModel23.addValue("Male",mb, maleBarData[mb]);
		}
		
		Number femaleBarData [] = {
				1656154, 1787564, 1981671, 2108575, 2403438, 2366003, 2301402, 2519874,
                3360596, 3493473, 3050775, 2759560, 2304444, 2426504, 2568938, 1785638,
                1447162, 1005011, 330870, 130632, 21208
		};
		
		for(int fmb = 0; fmb<femaleBarData.length;fmb++ ){
			dataChartModel23.addValue("Female",fmb, femaleBarData[fmb]);
		}
		
		//================================================================================
	    //  Multiple axes
	    //================================================================================
		
		chartComp24 = (ZHighCharts) getFellow("chartComp24");
		chartComp24.setType("line"); //Mandatory!!
		chartComp24.setOptions("{" +
					"zoomType: 'xy'" +
				"}");
		chartComp24.setTitle("Average Monthly Weather Data for Tokyo");
		chartComp24.setSubTitle("Source: WorldClimate.com");
		chartComp24.setxAxisOptions("{" +
					"categories: [" + //légende de l'axe X
						"'Jan', " +
						"'Feb', " +
						"'Mar', " +
						"'Apr', " +
						"'May', " +
						"'Jun', " +
						"'Jul', " +
						"'Aug', " +
						"'Sep', " +
						"'Oct', " +
						"'Nov', " +
						"'Dec'" +
					"]" +
				"}");
			
		chartComp24.setyAxisOptions("[" +
					"{" + // Primary yAxis
						"labels: {" +
							"formatter: function() {" +
								"return this.value +'°C';" + //Format de la légende ex: 15°C
							"}," +
							"style: {" +
								"color: '#89A54E'" + //Couleur de la légende
							"} " +
						"}," +
						"title: {" +
							"text: 'Temperature'," + //Titre de la légende
							"style: { " +
								"color: '#89A54E'" + //couleur du titre de la légende
							"}" +
						"}," +
						"opposite: true" + //Mettre au côté droit de l'écran
					"}," +
					"{" + // Secondary yAxis
						"title: {" +
							"text: 'Rainfall'," +
							"style: {" +
								"color: '#4572A7'" +
							"}" +
						"}," +
						"labels: {" +
							"formatter: function() { " +
								"return this.value +' mm';" +
							"}," +
							"style: {" +
								"color: '#4572A7'" +
							"}" +
						"}" +
					"}," +
					"{" + // Tertiary yAxis
						"title: { " +
							"text: 'Sea-Level Pressure'," +
							"style: {" +
								"color: '#AA4643' " +
							"}" +
						"}," +
						"labels: {" +
							"formatter: function() {" +
								"return this.value +' mb';" +
							"}, " +
							"style: {" +
								"color: '#AA4643'" +
							"}" +
						"}," +
						"opposite: true" +
					"}" + 
				"]");
		
		
		
		chartComp24.setTooltipOptions("{" +
				"shared: true," +  
				"crosshairs: true," +
				"borderColor : '#000000'" +//Custom tooltip border color
			"}");
		
		String formatt = "";
		for(int i=0;i<3;i++){
			formatt += "<span style=\"color:'+ obj.points["+i+"].series.color +';\">'+ obj.points["+i+"].series.name +'</span> : <b>'+ obj.points["+i+"].y + obj.points["+i+"].series.options.units + '</b>";
			if(i<3-1)
				formatt += "<br/>";
		}
		
		chartComp24.setTooltipFormatter("function formatTooltip(obj){ " +
					"return obj.x + '<br/>" +
					 formatt + 	 
				"';}");
		
		chartComp24.setModel(dataChartModel24);
		
		//Series 1: Temperature
		style = new HashMap();
		style.put("color", "#89A54E");//Series chart color
		style.put("type", "spline");//Series type
		style.put("yAxis", 0);// associate the series to a specified yAxis (here 0)
		style.put("units", "°C");
		chartComp24.setSeriesOptions("Temperature", style); 
		
		dataChartModel24.addValue("Temperature", 0, 7.0);//January (month 0) Temperature is 7 deges
		dataChartModel24.addValue("Temperature", 1, 6.9);
		dataChartModel24.addValue("Temperature", 2, 9.5);
		dataChartModel24.addValue("Temperature", 3, 14.5);
		dataChartModel24.addValue("Temperature", 4, 18.2);
		dataChartModel24.addValue("Temperature", 5, 21.5);
		dataChartModel24.addValue("Temperature", 6, 25.2);
		dataChartModel24.addValue("Temperature", 7, 26.5);
		dataChartModel24.addValue("Temperature", 8, 23.3);
		dataChartModel24.addValue("Temperature", 9, 18.3);
		dataChartModel24.addValue("Temperature", 10, 13.9);
		dataChartModel24.addValue("Temperature", 11, 9.6);
		
		//Series 2: Rainfall
		style = new HashMap();
		style.put("color", "#4572A7");
		style.put("type", "spline");
		style.put("yAxis", 1);// associate the series to a specified yAxis (here 1)
		style.put("units", "mm");
		chartComp24.setSeriesOptions("Rainfall", style);
		
		dataChartModel24.addValue("Rainfall", 0, 49.9);
		dataChartModel24.addValue("Rainfall", 1, 71.5);
		dataChartModel24.addValue("Rainfall", 2, 10.6);
		dataChartModel24.addValue("Rainfall", 3, 129.2);
		dataChartModel24.addValue("Rainfall", 4, 144.0);
		dataChartModel24.addValue("Rainfall", 5, 176.0);
		dataChartModel24.addValue("Rainfall", 6, 135.6);
		dataChartModel24.addValue("Rainfall", 7, 148.5);
		dataChartModel24.addValue("Rainfall", 8, 216.4);
		dataChartModel24.addValue("Rainfall", 9, 194.1);
		dataChartModel24.addValue("Rainfall", 10, 95.6);
		dataChartModel24.addValue("Rainfall", 11, 54.4);
		
		//Series 3: Sea-Level Pressure
		style = new HashMap();
		style.put("color", "#AA4643");
		style.put("type", "spline");
		style.put("dashStyle", "shortdot");
		style.put("yAxis", 2);
		style.put("units", "mb");
		marker = new HashMap();
		marker.put("enabled", false);//deactivate markers (points) for this series
		style.put("marker", marker);
		chartComp24.setSeriesOptions("Sea-Level Pressure", style);
		
		dataChartModel24.addValue("Sea-Level Pressure", 0, 1016);
		dataChartModel24.addValue("Sea-Level Pressure", 1, 1016);
		dataChartModel24.addValue("Sea-Level Pressure", 2, 1015.9);
		dataChartModel24.addValue("Sea-Level Pressure", 3, 1015.5);
		dataChartModel24.addValue("Sea-Level Pressure", 4, 1012.3);
		dataChartModel24.addValue("Sea-Level Pressure", 5, 1009.5);
		dataChartModel24.addValue("Sea-Level Pressure", 6, 1009.6);
		dataChartModel24.addValue("Sea-Level Pressure", 7, 1010.2);
		dataChartModel24.addValue("Sea-Level Pressure", 8, 1013.1);
		dataChartModel24.addValue("Sea-Level Pressure", 9, 1016.9);
		dataChartModel24.addValue("Sea-Level Pressure", 10, 1018.2);
		dataChartModel24.addValue("Sea-Level Pressure", 11, 1016.7);
		
		//================================================================================
	    //  Multiple axes - yAxis in Pure Java code
	    //================================================================================
		
		chartComp37 = (ZHighCharts) getFellow("chartComp37");
		chartComp37.setType("line"); //Mandatory!!
		chartComp37.setOptions("{" +
					"zoomType: 'xy'" +
				"}");
		chartComp37.setTitle("Average Monthly Weather Data for Tokyo");
		chartComp37.setSubTitle("Source: WorldClimate.com");
		chartComp37.setxAxisOptions("{" +
					"categories: [" + //xAxis elements
						"'Jan', " +
						"'Feb', " +
						"'Mar', " +
						"'Apr', " +
						"'May', " +
						"'Jun', " +
						"'Jul', " +
						"'Aug', " +
						"'Sep', " +
						"'Oct', " +
						"'Nov', " +
						"'Dec'" +
					"]" +
				"}");
			
		//Primary yAxis definition (yAxis 0 / default yAxis)
		Map Axis0 = new HashMap();
		Map Axis0Style = new HashMap(); //Style Creation
		Axis0Style.put("color", "#89A54E");
		Map Axis0Labels = new HashMap(); //Labels
		Axis0Labels.put("formatter", "function() {return this.value +'°C';}");
		Axis0Labels.put("style", Axis0Style);
		Map Axis0Title = new HashMap();// title
		Axis0Title.put("text", "Temperature");
		Axis0Title.put("style", Axis0Style);
		Axis0.put("labels", Axis0Labels);//Putting all together
		Axis0.put("title", Axis0Title);
		Axis0.put("opposite", true);
		
		/*
		 * The code above is equivalent to this:
 		*	"{" + // Primary yAxis
				"labels: {" +
					"formatter: function() {" +
						"return this.value +'°C';" + //Format de la légende ex: 15°c
					"}," +
					"style: {" +
						"color: '#89A54E'" + //Couleur de la légende
					"} " +
				"}," +
				"title: {" +
					"text: 'Temperature'," + //Titre de la légende
					"style: { " +
						"color: '#89A54E'" + //couleur du titre de la légende
					"}" +
				"}," +
				"opposite: true" + //Mettre au côté droit de l'écran
			"}," +
		 * 
		 */

		Map Axis1 = new HashMap();
		Map Axis1Labels = new HashMap();
		Axis1Labels.put("formatter", "function() {return this.value +' mm';}");
		Map Axis1Style = new HashMap();
		Axis1Style.put("color", "#4572A7");
		Axis1Labels.put("style", Axis1Style);
		Map Axis1Title = new HashMap();
		Axis1Title.put("text", "Rainfall");
		Axis1Title.put("style", Axis1Style);
		Axis1.put("labels", Axis1Labels);
		Axis1.put("title", Axis1Title);

		Map Axis2 = new HashMap();
		Map Axis2Labels = new HashMap();
		Axis2Labels.put("formatter", "function() {return this.value +' mb';}");
		Map Axis2Style = new HashMap();
		Axis2Style.put("color", "#AA4643");
		Axis2Labels.put("style", Axis2Style);
		Map Axis2Title = new HashMap();
		Axis2Title.put("text", "Sea-Level Pressure");
		Axis2Title.put("style", Axis2Style);
		Axis2.put("labels", Axis2Labels);
		Axis2.put("title", Axis2Title);
		Axis2.put("opposite", true);
		
		//List of yAxis defined above
		List AxisY = new ArrayList();
		AxisY.add(Axis0);
		AxisY.add(Axis1);
		AxisY.add(Axis2);
		
		//set the chart yAxis
		chartComp37.setyAxisOptions(AxisY);
		
		formatt = "";
		for(int i=0;i<3;i++){
			formatt += "<span style=\"color:'+ this.points["+i+"].series.color +';\">'+ this.points["+i+"].series.name +'</span> : <b>'+ this.points["+i+"].y + ' ' + this.points["+i+"].series.options.units +'</b>";
			if(i<3-1)
				formatt += "<br/>";
		}
				
		chartComp37.setTooltipOptions("{" +
				"shared: true," + 
				"crosshairs: true," +
				"borderColor : '#000000'," +
				"formatter: function() { " +
					"return this.x + '<br/>" +
					formatt +
					"';}" +
			"}");
				
		chartComp37.setModel(dataChartModel37);
		
		//Series 1: Temperature
		style = new HashMap();
		style.put("color", "#89A54E");
		style.put("type", "spline");
		style.put("yAxis", 0);
		style.put("units", "°C");
		chartComp37.setSeriesOptions("Temperature", style); 
		
		dataChartModel37.addValue("Temperature", 0, 7.0);
		dataChartModel37.addValue("Temperature", 1, 6.9);
		dataChartModel37.addValue("Temperature", 2, 9.5);
		dataChartModel37.addValue("Temperature", 3, 14.5);
		dataChartModel37.addValue("Temperature", 4, 18.2);
		dataChartModel37.addValue("Temperature", 5, 21.5);
		dataChartModel37.addValue("Temperature", 6, 25.2);
		dataChartModel37.addValue("Temperature", 7, 26.5);
		dataChartModel37.addValue("Temperature", 8, 23.3);
		dataChartModel37.addValue("Temperature", 9, 18.3);
		dataChartModel37.addValue("Temperature", 10, 13.9);
		dataChartModel37.addValue("Temperature", 11, 9.6);
		
		//Series 2: Rainfall
		style = new HashMap();
		style.put("color", "#4572A7");
		style.put("type", "spline");
		style.put("yAxis", 1);
		style.put("units", "mm");
		chartComp37.setSeriesOptions("Rainfall", style);
		
		dataChartModel37.addValue("Rainfall", 0, 49.9);
		dataChartModel37.addValue("Rainfall", 1, 71.5);
		dataChartModel37.addValue("Rainfall", 2, 10.6);
		dataChartModel37.addValue("Rainfall", 3, 129.2);
		dataChartModel37.addValue("Rainfall", 4, 144.0);
		dataChartModel37.addValue("Rainfall", 5, 176.0);
		dataChartModel37.addValue("Rainfall", 6, 135.6);
		dataChartModel37.addValue("Rainfall", 7, 148.5);
		dataChartModel37.addValue("Rainfall", 8, 216.4);
		dataChartModel37.addValue("Rainfall", 9, 194.1);
		dataChartModel37.addValue("Rainfall", 10, 95.6);
		dataChartModel37.addValue("Rainfall", 11, 54.4);
		
		//Series 3: Sea-Level Pressure
		style = new HashMap();
		style.put("color", "#AA4643");
		style.put("type", "spline");
		style.put("dashStyle", "shortdot");
		style.put("yAxis", 2);
		marker = new HashMap();
		marker.put("enabled", false);
		style.put("marker", marker);
		style.put("units", "mb");
		chartComp37.setSeriesOptions("Sea-Level Pressure", style);
		
		dataChartModel37.addValue("Sea-Level Pressure", 0, 1016);
		dataChartModel37.addValue("Sea-Level Pressure", 1, 1016);
		dataChartModel37.addValue("Sea-Level Pressure", 2, 1015.9);
		dataChartModel37.addValue("Sea-Level Pressure", 3, 1015.5);
		dataChartModel37.addValue("Sea-Level Pressure", 4, 1012.3);
		dataChartModel37.addValue("Sea-Level Pressure", 5, 1009.5);
		dataChartModel37.addValue("Sea-Level Pressure", 6, 1009.6);
		dataChartModel37.addValue("Sea-Level Pressure", 7, 1010.2);
		dataChartModel37.addValue("Sea-Level Pressure", 8, 1013.1);
		dataChartModel37.addValue("Sea-Level Pressure", 9, 1016.9);
		dataChartModel37.addValue("Sea-Level Pressure", 10, 1018.2);
		dataChartModel37.addValue("Sea-Level Pressure", 11, 1016.7);
		
		//================================================================================
	    // jsgauge Basic Example
	    //================================================================================
		zgauge1 = (ZGauge) this.getFellow("zgauge1");
		zgauge1.setOptions("{" +
					"label:'vitesse'," +
					"min:0," +
					"max:240," +
					"unitsLabel:' km/h'," +
					"majorTicks: 9," +
					"bands: [" +
						"{" +
							"color: 'rgba(255,255,0,0.5)', " +
							"from: 110, " +
							"to: 140 " +
						"}," +
						"{" +
							"color: 'rgba(255,0,0,0.5)', " +
							"from: 140, to: 240 " +
						"}" +
					"]" +
				"}");
		zgauge1.setValue(60);
		
		//================================================================================
	    // Basic column
	    //================================================================================

		chartComp25 = (ZHighCharts) getFellow("chartComp25");
		chartComp25.setType("column");
		chartComp25.setTitle("Monthly Average New York");
		chartComp25.setSubTitle("Source: WorldClimate.com");
		chartComp25.setxAxisOptions("{" +
				"categories: [" +
					"'Jan', " +
					"'Feb', " +
					"'Mar', " +
					"'Apr', " +
					"'May', " +
					"'Jun', " +
					"'Jul', " +
					"'Aug', " +
					"'Sep', " +
					"'Oct', " +
					"'Nov', " +
					"'Dec'" +
				"]" +
			"}");
		chartComp25.setyAxisOptions("{ " +
					"min:0" +
				"}");
		chartComp25.setYAxisTitle("New York (mm)");
		chartComp25.setTooltipFormatter("function formatTooltip(obj){ " +
					"return ''+obj.x +': '+ obj.y +' mm';" +
				"}");
		chartComp25.setLegend("{" +
					"layout: 'vertical'," +
					"backgroundColor: '#FFFFFF'," +
					"align: 'left'," +
					"verticalAlign: 'top'," +
					"x: 100," +
					"y: 70," +
					"floating: true," +
					"shadow: true" +
				"}");
		chartComp25.setPlotOptions("{" +
					"column: {" +
						"pointPadding: 0.2," +
						"borderWidth: 0" +
					"}" +
				"}");
		
		chartComp25.setModel(dataChartModel25);
		
		//Adding some data to the model
		
		Number TOKdata [] = { 49.9, 71.5, 106.4, 129.2, 144.0, 176.0,
				135.6, 148.5, 216.4, 194.1, 95.6, 54.4};
		for(int i = 0; i < TOKdata.length; i++)
			dataChartModel25.addValue("Tokyo", i, TOKdata[i]);
		
		Number NYdata [] = { 83.6, 78.8, 98.5, 93.4, 106.0, 84.5,
				105.0, 104.3, 91.2, 83.5, 106.6, 92.3};
		for(int i = 0; i < NYdata.length; i++)
			dataChartModel25.addValue("New York", i, NYdata[i]);
		
		Number LNDdata [] = { 48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0,
				59.6, 52.4, 65.2, 59.3, 51.2};
		for(int i = 0; i < LNDdata.length; i++)
			dataChartModel25.addValue("London", i, LNDdata[i]);

		Number BRLdata [] = { 42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4,
				60.4, 47.6, 39.1, 46.8, 51.1};
		for(int i = 0; i < BRLdata.length; i++)
			dataChartModel25.addValue("Berlin", i, BRLdata[i]);
		
		//================================================================================
	    // Column with negative values
	    //================================================================================
		
		chartComp26 = (ZHighCharts) getFellow("chartComp26");
		chartComp26.setType("column");
		chartComp26.setTitle("Column chart with negative values");
		chartComp26.setxAxisOptions("{" +
				"categories: [" +
					"'Apples'," +
					"'Oranges', " +
					"'Pears', " +
					"'Grapes', " +
					"'Bananas'" +
				"]" +
			"}");
		chartComp26.setTooltipFormatter("function formatTooltip(obj){ " +
					"return ''+obj.series.name +': '+ obj.y +'';" +
				"}");
		
		chartComp26.setModel(dataChartModel26);
		
		Number Johndata [] = { 5, 3, 4, 7, 2};
		for(int i = 0; i < Johndata.length; i++)
			dataChartModel26.addValue("John", i, Johndata[i]);
		
		Number Janedata [] = {2, -2, -3, 2, 1};
		for(int i = 0; i < Janedata.length; i++)
			dataChartModel26.addValue("Jane", i, Janedata[i]);
		
		Number Joedata [] = { 3, 4, 4, -2, 5};
		for(int i = 0; i < Joedata.length; i++)
			dataChartModel26.addValue("Joe", i, Joedata[i]);
		
		//================================================================================
	    // Stacked column
	    //================================================================================

		chartComp27 = (ZHighCharts) getFellow("chartComp27");
		chartComp27.setType("column");
		chartComp27.setTitle("Stacked column chart");
		chartComp27.setxAxisOptions("{" +
				"categories: [" +
					"'Apples'," +
					"'Oranges', " +
					"'Pears', " +
					"'Grapes', " +
					"'Bananas'" +
				"]" +
			"}");
		chartComp27.setyAxisOptions("{" +
					"min: 0," +
					"stackLabels: {" +
						"enabled: true," +
						"style: {" +
							"fontWeight: 'bold'," +
							"color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'" +
						"}" +
					"}" +
				"}");
		chartComp27.setYAxisTitle("Total fruit consumption");
		chartComp27.setTooltipFormatter("function formatTooltip(obj){ " +
					"return '<b>'+ obj.x +'</b><br/>" +
					"'+obj.series.name +': '+ obj.y +'<br/>" +
					"'+'Total: '+ obj.point.stackTotal;" +
				"}");
		chartComp27.setLegend("{" +
					"align: 'right'," +
					"x: -100, " +
					"verticalAlign: 'top', " +
					"y: 20," +
					"floating: true," +
					"backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white'," +
					"borderColor: '#CCC'," +
					"borderWidth: 1, " +
					"shadow: false" +
				"}");
		chartComp27.setPlotOptions("{" +
					"column: {" +
						"stacking: 'normal'," +
						"dataLabels: { " +
							"enabled: true," +
							"color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'" +
						"}" +
					"}" +
				"}");
		
		chartComp27.setModel(dataChartModel27);
		
		Number Johndata27 [] = { 5, 3, 4, 7, 2};
		for(int i = 0; i < Johndata27.length; i++)
			dataChartModel27.addValue("John", i, Johndata27[i]);
		
		Number Janedata27 [] = {2, 2, 3, 2, 1};
		for(int i = 0; i < Janedata27.length; i++)
			dataChartModel27.addValue("Jane", i, Janedata27[i]);
		
		Number Joedata27 [] = { 3, 4, 4, 2, 5};
		for(int i = 0; i < Joedata27.length; i++)
			dataChartModel27.addValue("Joe", i, Joedata27[i]);
		
		//================================================================================
	    // Stacked and grouped column
	    //================================================================================

		chartComp28 = (ZHighCharts) getFellow("chartComp28");
		chartComp28.setType("column");
		chartComp28.setTitle("Total fruit consumtion, grouped by gender");
		chartComp28.setxAxisOptions("{" +
				"categories: [" +
					"'Apples'," +
					"'Oranges', " +
					"'Pears', " +
					"'Grapes', " +
					"'Bananas'" +
				"]" +
			"}");
		chartComp28.setyAxisOptions("{" +
					"min: 0," +
					"allowDecimals: false," +
				"}");
		chartComp28.setYAxisTitle("Number of fruits");
		chartComp28.setTooltipFormatter("function formatTooltip(obj){ " +
					"return '<b>'+ obj.x +'</b><br/>" +
					"'+obj.series.name +': '+ obj.y +'<br/>" +
					"'+'Total: '+ obj.point.stackTotal;" +
				"}");
		chartComp28.setPlotOptions("{" +
					"column: {" +
						"stacking: 'normal'" +
					"}" +
				"}");
		
		chartComp28.setModel(dataChartModel28);
		
		Number Johndata28 [] = { 5, 3, 4, 7, 2};
		for(int i = 0; i < Johndata28.length; i++)
			dataChartModel28.addValue("John", i, Johndata28[i]);
		
		style = new HashMap();
		style.put("stack", "male");
		chartComp28.setSeriesOptions("John", style);

		Number Joedata28 [] = { 3, 4, 4, 2, 5};
		for(int i = 0; i < Joedata28.length; i++)
			dataChartModel28.addValue("Joe", i, Joedata28[i]);
		
		style = new HashMap();
		style.put("stack", "male");
		chartComp28.setSeriesOptions("Joe", style);
		
		Number Janedata28 [] = {2, 5, 6, 2, 1};
		for(int i = 0; i < Janedata28.length; i++)
			dataChartModel28.addValue("Jane", i, Janedata28[i]);
		
		style = new HashMap();
		style.put("stack", "female");
		chartComp28.setSeriesOptions("Jane", style);
		
		Number Janetdata28 [] = {3, 0, 4, 4, 3};
		for(int i = 0; i < Janetdata28.length; i++)
			dataChartModel28.addValue("Janet", i, Janetdata28[i]);
		
		style = new HashMap();
		style.put("stack", "female");
		chartComp28.setSeriesOptions("Janet", style);
		
		
		//================================================================================
	    // Stacked percentage column
	    //================================================================================

		chartComp29 = (ZHighCharts) getFellow("chartComp29");
		chartComp29.setType("column");
		chartComp29.setTitle("Stacked column chart");
		chartComp29.setxAxisOptions("{" +
				"categories: [" +
					"'Apples'," +
					"'Oranges', " +
					"'Pears', " +
					"'Grapes', " +
					"'Bananas'" +
				"]" +
			"}");
		chartComp29.setyAxisOptions("{" +
					"min: 0" +
				"}");
		chartComp29.setYAxisTitle("Total fruit consumption");
		chartComp29.setTooltipFormatter("function formatTooltip(obj){ " +
					"return ''+  obj.series.name +': '+ obj.y +' ('+ Math.round(obj.percentage) +'%)';" +
				"}");
		chartComp29.setPlotOptions("{" +
					"column: {" +
						"stacking: 'percent'," +
					"}" +
				"}");
		
		chartComp29.setModel(dataChartModel29);
		
		Number Johndata29 [] = { 5, 3, 4, 7, 2};
		for(int i = 0; i < Johndata29.length; i++)
			dataChartModel29.addValue("John", i, Johndata29[i]);
		
		Number Janedata29 [] = {2, 2, 3, 2, 1};
		for(int i = 0; i < Janedata29.length; i++)
			dataChartModel29.addValue("Jane", i, Janedata29[i]);
		
		Number Joedata29 [] = { 3, 4, 4, 2, 5};
		for(int i = 0; i < Joedata29.length; i++)
			dataChartModel29.addValue("Joe", i, Joedata29[i]);
		
		//================================================================================
	    // Column with rotated labels
	    //================================================================================

		chartComp30 = (ZHighCharts) getFellow("chartComp30");
		chartComp30.setType("column");
		chartComp30.setOptions("{margin: [ 50, 50, 100, 80]}");
		chartComp30.setTitle("World\'s largest cities per 2008");
		chartComp30.setxAxisOptions("{" +
				"categories: [" +
					"'Tokyo',"+
	                "'Jakarta',"+
	                "'New York',"+
	                "'Seoul',"+
	                "'Manila',"+
	                "'Mumbai',"+
	                "'Sao Paulo',"+
	                "'Mexico City',"+
	                "'Dehli',"+
	                "'Osaka',"+
	                "'Cairo',"+
	                "'Kolkata',"+
	                "'Los Angeles',"+
	                "'Shanghai',"+
	                "'Moscow',"+
	                "'Beijing',"+
	                "'Buenos Aires',"+
	                "'Guangzhou',"+
	                "'Shenzhen',"+
	                "'Istanbul'"+
				"],"+
                "labels: {"+
                    "rotation: -45,"+
                    "align: 'right',"+
                    "style: {"+
                        "fontSize: '13px',"+
                        "fontFamily: 'Verdana, sans-serif'"+
                    "}"+
                "}" +
			"}");
		chartComp30.setyAxisOptions("{" +
					"min: 0" +
				"}");
		chartComp30.setYAxisTitle("Population (millions)");
		chartComp30.setLegend("{"+
                "enabled: false"+
            "}");
		chartComp30.setTooltipFormatter("function formatTooltip(obj){ " +
					"return '<b>'+ obj.x +'</b><br/>" +
					"'+'Population in 2008: '+Highcharts.numberFormat(obj.y, 1) +' millions';" +
				"}");
		
		chartComp30.setModel(dataChartModel30);
		
		Number Populationdata30 [] = { 34.4, 21.8, 20.1, 20, 19.6, 19.5, 19.1, 18.4, 18,
                17.3, 16.8, 15, 14.7, 14.5, 13.3, 12.8, 12.4, 11.8,
                11.7, 11.2};
		for(int i = 0; i < Populationdata30.length; i++)
			dataChartModel30.addValue("Population", i, Populationdata30[i]);
		
		Map series = new HashMap();
		Map dataLabels = new HashMap();
		dataLabels.put("enabled", true);
		dataLabels.put("rotation", -90);
		dataLabels.put("color", "#FFFFFF");
		dataLabels.put("align", "right");
		dataLabels.put("x", -3);
		dataLabels.put("y", 10);
		style = new HashMap();
		style.put("fontSize", "13px");
		style.put("fontFamily", "Verdana, sans-serif");
		dataLabels.put("style", style);
		series.put("dataLabels", dataLabels);
		chartComp30.setSeriesOptions("Population", series);
		
		//================================================================================
	    // Column and line
	    //================================================================================

		chartComp31 = (ZHighCharts) getFellow("chartComp31");
		chartComp31.setType("line"); //Mandatory!
		chartComp31.setTitle("Combination chart");
		chartComp31.setxAxisOptions("{" +
				"categories: [" +
					"'Apples'," +
					"'Oranges', " +
					"'Pears', " +
					"'Bananas', " +
					"'Plums'" +
				"]" +
			"}");

		chartComp31.setTooltipFormatter("function formatTooltip(obj){ " +
					"return ''+obj.x  +': '+ obj.y;" +
				"}");
		
		chartComp31.setModel(dataChartModel31);
		
		Map seriesType = new HashMap();
		seriesType.put("type", "column");
		
		Number Johndata31 [] = {2, 3, 5, 7, 6};
		for(int i = 0; i < Johndata31.length; i++)
			dataChartModel31.addValue("John", i, Johndata31[i]);
		chartComp31.setSeriesOptions("John", seriesType);

		
		Number Janedata31 [] = {3, 2, 1, 3, 4};
		for(int i = 0; i < Janedata31.length; i++)
			dataChartModel31.addValue("Jane", i, Janedata31[i]);
		chartComp31.setSeriesOptions("Jane", seriesType);

		
		Number Joedata31 [] = { 4, 3, 3, 9, 0};
		for(int i = 0; i < Joedata31.length; i++)
			dataChartModel31.addValue("Joe", i, Joedata31[i]);
		chartComp31.setSeriesOptions("Joe", seriesType);
		
		Number AVGdata31 [] = { 3, 2.67, 3, 6.33, 3.33};
		for(int i = 0; i < AVGdata31.length; i++)
			dataChartModel31.addValue("Average", i, AVGdata31[i]);
		
		seriesType = new HashMap();
		seriesType.put("type", "spline");
		chartComp31.setSeriesOptions("Average", seriesType);	
		
		//================================================================================
		// Angular gauge
		//================================================================================
				
				chartComp32 = (ZHighCharts) getFellow("chartComp32");
				chartComp32.setType("gauge");
				chartComp32.setOptions("{" +
							"alignTicks: false," +
							"plotBackgroundColor: null," +
							"plotBackgroundImage: null," +
							"plotBorderWidth: 0," +
							"plotShadow: false" +
						"}");
				chartComp32.setTitle("Speedometer");
				chartComp32.setPane("{ " +
							"startAngle: -150, " +
							"endAngle: 150, " +
							"background: [" +
								"{ " +
									"backgroundColor: { " +
										"linearGradient: { " +
											"x1: 0, " +
											"y1: 0, " +
											"x2: 0, " +
											"y2: 1 " +
										"}, " +
										"stops: [ " +
											"[0, '#FFF'], " +
											"[1, '#333'] " +
										"] " +
									"}, " +
									"borderWidth: 0, " +
									"outerRadius: '109%' " +
								"}, " +
								"{ " +
									"backgroundColor: { " +
										"linearGradient: { " +
											"x1: 0, " +
											"y1: 0, " +
											"x2: 0, " +
											"y2: 1 " +
										"}, " +
										"stops: [ " +
											"[0, '#333'], " +
											"[1, '#FFF'] " +
										"] " +
									"}, " +
									"borderWidth: 1, " +
									"outerRadius: '107%' " +
								"}," +
								"{ " +// default background
								"}, " +
								"{ " +
									"backgroundColor: '#DDD', " +
									"borderWidth: 0, " +
									"outerRadius: '105%', " +
									"innerRadius: '103%' " +
								"}" +
							"] " +
						"}");
				
				chartComp32.setyAxisOptions("{ " +
							"min: 0, " +
							"max: 200,  " +
							"minorTickInterval: 'auto', " +
							"minorTickWidth: 1, " +
							"minorTickLength: 10, " +
							"minorTickPosition: 'inside', " +
							"minorTickColor: '#666',  " +
							"tickPixelInterval: 30, " +
							"tickWidth: 2, " +
							"tickPosition: 'inside', " +
							"tickLength: 10, " +
							"tickColor: '#666', " +
							"labels: { " +
								"step: 2, " +
								"rotation: 'auto' " +
							"}, " +
							"title: { " +
								"text: 'km/h' " +
							"}, " +
							"plotBands: [" +
								"{ " +
									"from: 0, " +
									"to: 120, " +
									"color: '#55BF3B' " + // green
								"}, " +
								"{ " +
									"from: 120, " +
									"to: 160, " +
									"color: '#DDDF0D' " + // yellow
								"}, " +
								"{ " +
									"from: 160, " +
									"to: 200, " +
									"color: '#DF5353' " + // red
								"}" +
							"] " +
						"}");
				
				chartComp32.setTooltipOptions("{enabled : false}");
				chartComp32.setModel(dataChartModel32);
				
				series = new HashMap();
		        dataLabels = new HashMap();
		        dataLabels.put("formatter", "function () { " +
		        			"var kmh = this.y, mph = Math.round(kmh * 0.621);" +
		        			"return '<span>'+ kmh + ' km/h</span><br/>" +
		        			"' + '<span>' + mph + ' mph</span>';" +
		        		"}");
		        style = new HashMap();
		        series.put("dataLabels", dataLabels);
		        chartComp32.setSeriesOptions("Speed", series); 
		        
				//Initial Value
				dataChartModel32.addValue("Speed", 0, 20);		
				
				//================================================================================
			    // Gauge with dual axes
			    //================================================================================

				chartComp33 = (ZHighCharts) getFellow("chartComp33");
				chartComp33.setType("gauge");
				chartComp33.setOptions("{" +
							"alignTicks: false," +
							"plotBackgroundColor: null," +
							"plotBackgroundImage: null," +
							"plotBorderWidth: 0," +
							"plotShadow: false" +
						"}");
				chartComp33.setTitle("Speedometer with dual axes");
				chartComp33.setPane("{" +
							"startAngle: -150," +
							"endAngle: 150" +
						"}");
				chartComp33.setyAxisOptions("[" +
							"{ " +
								"min: 0, " +
								"max: 200, " +
								"lineColor: '#339', " +
								"tickColor: '#339', " +
								"minorTickColor: '#339', " +
								"offset: -25, " +
								"lineWidth: 2, " +
								"labels: { " +
									"distance: -20, " +
									"rotation: 'auto' " +
								"}, " +
								"tickLength: 5, " +
								"minorTickLength: 5, " +
								"endOnTick: false " +
							"}, " +
							"{ " +
								"min: 0, " +
								"max: 124, " +
								"tickPosition: 'outside', " +
								"lineColor: '#933', " +
								"lineWidth: 2, " +
								"minorTickPosition: 'outside', " +
								"tickColor: '#933', " +
								"minorTickColor: '#933', " +
								"tickLength: 5, " +
								"minorTickLength: 5, " +
								"labels: { " +
									"distance: 12, " +
									"rotation: 'auto' " +
								"}, " +
								"offset: -20, " +
								"endOnTick: false " +
							"}" +
						"]");
				chartComp33.setTooltipOptions("{enabled : false}");

				series = new HashMap();
		        dataLabels = new HashMap();
		        dataLabels.put("formatter", "function () { " +
		        			"var kmh = this.y, mph = Math.round(kmh * 0.621);" +
		        			"return '<span>'+ kmh + ' km/h</span><br/>" +
		        			"' + '<span>' + mph + ' mph</span>';" +
		        		"}");
		        style = new HashMap();
		        series.put("dataLabels", dataLabels);
		        chartComp33.setSeriesOptions("Speed", series); 
				chartComp33.setModel(dataChartModel33);
				
				//Initial Value
				dataChartModel33.addValue("Speed", 0, 20);		
				
				
				//================================================================================
			    // VU meter
			    //================================================================================

				chartComp34 = (ZHighCharts) getFellow("chartComp34");
				chartComp34.setType("gauge");
				chartComp34.setOptions("{" +
							"plotBorderWidth: 1, " +
							"plotBackgroundColor: { " +
								"linearGradient: { " +
									"x1: 0, " +
									"y1: 0, " +
									"x2: 0, " +
									"y2: 1 " +
								"}, " +
								"stops: [ " +
									"[0, '#FFF4C6'], " +
									"[0.3, '#FFFFFF'], " +
									"[1, '#FFF4C6'] " +
								"] " +
							"}, " +
							"plotBackgroundImage: null, " +
							"height: 200" + 
						"}");
				chartComp34.setTitle("VU meter");
				chartComp34.setPane("[" +
							"{ " +
								"startAngle: -45, " +
								"endAngle: 45, " +
								"background: null, " +
								"center: ['25%', '145%'], " +
								"size: 300 " +
							"}, " +
							"{ " +
								"startAngle: -45, " +
								"endAngle: 45, " +
								"background: null, " +
								"center: ['75%', '145%'], " +
								"size: 300 " +
							"}" +
						"]");
				chartComp34.setyAxisOptions("[" +
							"{" +
								"min: -20, " +
								"max: 6, " +
								"minorTickPosition: 'outside', " +
								"tickPosition: 'outside', " +
								"labels: { " +
									"rotation: 'auto', " +
									"distance: 20 " +
								"}, " +
								"plotBands: [" +
									"{ " +
										"from: 0, " +
										"to: 6, " +
										"color: '#C02316', " +
										"innerRadius: '100%', " +
										"outerRadius: '105%' " +
									"}" +
								"], " +
								"pane: 0, " +
								"title: { " +
									"text: 'VU<br/><span style=\"font-size:8px\">Channel A</span>', " +
									"y: -40 " +
								"} " +
							"}, " +
							"{ " +
								"min: -20, " +
								"max: 6, " +
								"minorTickPosition: 'outside', " +
								"tickPosition: 'outside', " +
								"labels: { " +
									"rotation: 'auto', " +
									"distance: 20 " +
								"}, " +
								"plotBands: [" +
									"{ " +
										"from: 0, " +
										"to: 6, " +
										"color: '#C02316', " +
										"innerRadius: '100%', " +
										"outerRadius: '105%' " +
									"}" +
								"], " +
								"pane: 1, " +
								"title: { " +
									"text: 'VU<br/><span style=\"font-size:8px\">Channel B</span>', " +
									"y: -40 " +
								"} " +
							"}" +
						"]");
				
				chartComp34.setPlotOptions("{ " +
							"gauge: { " +
								"dataLabels: { " +
									"enabled: false " +
								"}, " +
								"dial: { " +
									"radius: '100%' " +
								"}" +
							"} " +
						"}");
				
				series = new HashMap();
				series.put("yAxis", 0);
				chartComp34.setSeriesOptions("Channel A", series); 
				
				series = new HashMap();
				series.put("yAxis", 1);
				chartComp34.setSeriesOptions("Channel B", series); 

				
				chartComp34.setModel(dataChartModel34);
				
				//Initial Value
				dataChartModel34.addValue("Channel A", 0, -10);		
				dataChartModel34.addValue("Channel B", 0, -5);		
				
				//================================================================================
			    // Doublebox onOK Listener
			    //================================================================================

				doubleB= (Doublebox) getFellow("doubleB");
				doubleB.addEventListener("onOK", this);		
				
				//================================================================================
				// Polar chart
			    //================================================================================

				chartComp35 = (ZHighCharts) getFellow("chartComp35");
				chartComp35.setType("line");
				chartComp35.setOptions("{" +
							"polar: true" +
						"}");
				
				chartComp35.setTitle("Highcharts Polar Chart");
				chartComp35.setPane("{" +
							"startAngle: 0," +
							"endAngle: 360" +
						"}");
				chartComp35.setxAxisOptions("{" +
							"tickInterval: 45," +
							"min: 0, " +
							"max: 360, " +
							"labels: { " +
								"formatter: function () { " +
									"return this.value + '°'; " +
								"} " +
							"} " +
						"}");
				chartComp35.setyAxisOptions("{" +
							"min:0" +
						"}");
				chartComp35.setPlotOptions("{ " +
							"series: { " +
								"pointStart: 0, " +
								"pointInterval: 45 " +
							"}, " +
							"column: { " +
								"pointPadding: 0, " +
								"groupPadding: 0 " +
							"} " +
						"}");
				chartComp35.setTooltipFormatter("function formatTooltip(obj){ " +
						"return obj.x + '<br/>" +
						"<span style=\"color:'+ obj.series.color +';\">'+ obj.series.name +'</span> : <b>'+ obj.y +'</b>';" +
					"}");
				
				series = new HashMap();
				series.put("type", "column");
				series.put("pointPlacement", "between");
				chartComp35.setSeriesOptions("Column", series); 
				
				series = new HashMap();
				series.put("type", "line");
				chartComp35.setSeriesOptions("Line", series); 

				series = new HashMap();
				series.put("type", "area");
				chartComp35.setSeriesOptions("Area", series); 
				
				chartComp35.setModel(dataChartModel35);
				
				//Add some date to the model
				Number colData[] = {8, 7, 6, 5, 4, 3, 2, 1};
				

				for (int i = 0;i<colData.length;i++){
					dataChartModel35.addValue("Column", i * 45, colData[i]);
				}
				
				Number lineData[] = {1, 2, 3, 4, 5, 6, 7, 8};
				
				for (int i = 0;i<lineData.length;i++){
					dataChartModel35.addValue("Line", i * 45, lineData[i]);
				}
				
				Number areaData[] = {1, 8, 2, 7, 3, 6, 4, 5};
				
				for (int i = 0;i<areaData.length;i++){
					dataChartModel35.addValue("Area", i * 45, areaData[i]);
				}
				
				//================================================================================
				// Spiderweb
			    //================================================================================

				chartComp36 = (ZHighCharts) getFellow("chartComp36");
				chartComp36.setType("line");
				chartComp36.setOptions("{" +
							"polar: true," +
							"plotBackgroundColor: null," +
							"plotBackgroundImage: null," +
							"plotBorderWidth: 0," +
							"plotShadow: false" +
						"}");
				chartComp36.setTitle("Spiderweb");
				chartComp36.setSubTitle("Budget vs spending");
				chartComp36.setPane("{" +
							"size: '80%'" +
						"}");
				chartComp36.setxAxisOptions("{ " +
							"categories: [" +
								"'Sales', " +
								"'Marketing', " +
								"'Development', " +
								"'Customer Support', " +
								"'Information Technology', " +
								"'Administration'" +
							"]," +
							"tickmarkPlacement: 'on', " +
							"lineWidth: 0 " +
						"}");
				chartComp36.setyAxisOptions("{ " +
							"gridLineInterpolation: 'polygon'," +
							"lineWidth: 0," +
							"min: 0" +
						"}");
				chartComp36.setTooltipOptions("{" +
							"shared: true" +
						"}");
				chartComp36.setTooltipFormatter("function formatTooltip(obj){ " +
							"return obj.x + '<br/>" +
							"<span style=\"color:'+ obj.points[0].series.color +';\">'+ obj.points[0].series.name +'</span> : <b>$'+ obj.points[0].y +'</b><br/>" +
							"<span style=\"color:'+ obj.points[1].series.color +';\">'+ obj.points[1].series.name +'</span> : <b>$'+ obj.points[1].y +'</b>';" + 
						"}");

				chartComp36.setModel(dataChartModel36);
				
				
				series = new HashMap();
				series.put("pointPlacement", "on");
				chartComp36.setSeriesOptions("Allocated Budget", series); 
				
				series = new HashMap();
				series.put("pointPlacement", "on");
				chartComp36.setSeriesOptions("Actual Spending", series); 
				
				//Add some date to the model
				Number alloData[] = {43000, 19000, 60000, 35000, 17000, 10000};
				

				for (int i = 0;i<alloData.length;i++){
					dataChartModel36.addValue("Allocated Budget", i , alloData[i]);
				}
				
				Number actuData[] = {50000, 39000, 42000, 31000, 26000, 14000};
				
				for (int i = 0;i<actuData.length;i++){
					dataChartModel36.addValue("Actual Spending", i , actuData[i]);
				}

	}
	@Override
	public void onEvent(Event arg0) throws Exception {
		
		//Doublebox onOK Listener
		chartComp32.setGaugeValue(0, doubleB.getValue());
		chartComp33.setGaugeValue(0, doubleB.getValue());
		chartComp34.setGaugeValue(0, doubleB.getValue());
		chartComp34.setGaugeValue(1, doubleB.getValue() - 5);		
	}
	
	/**
	 * internal method to convert date&time from string to epoch milliseconds
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	private long getDateTime(String date) throws Exception {
		return sdf.parse(date).getTime();
	}

}
