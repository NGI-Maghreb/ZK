package ctrl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.ngi.zhighcharts.SimpleIntervalModel;
import org.ngi.zhighcharts.SimpleExtXYModel;
import org.ngi.zhighcharts.ZGauge;
import org.ngi.zhighcharts.ZHighCharts;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Chart;
import org.zkoss.zul.DialModel;
import org.zkoss.zul.DialModelScale;
import org.zkoss.zul.Image;
import org.zkoss.zul.SimplePieModel;
import org.zkoss.zul.Window;

/**
 * demo/test window for zhighcharts component
 * also intended to be an integration
 * example for Weenee-Fleet usage:
 * a chart to plot device data with vehicle activity shown
 * a gantt chart to show vehicle activity
 * a pie chart to show speed distribution over a period
 * some jauges which values are set on data point's hover
 *
 * @author alain
 * 
 */
public class DemoWindowComposer extends Window implements EventListener {
	protected Log logger = Log.lookup(this.getClass());
	// components
	private ZHighCharts chartComp; // data plot + activity bands
	//private ZHighCharts chartComp2; // Gantt activity
	private ZHighCharts chartComp3; // speed distribution
	private Image map;
	private Chart jaugeS; // speed gauge (with "dial" chart type) (not used)
	// a series model to store vehicle data
	private SimpleExtXYModel vehicleDataChartModel = new SimpleExtXYModel();
	// a band model to represent vehicle activity
	private SimpleIntervalModel vehicleActivityModel = new SimpleIntervalModel();
	// a band model to represent some work period on Gantt activity chart
	private SimpleIntervalModel yWorkBandModel = new SimpleIntervalModel();
	private SimpleIntervalModel yBandModel = new SimpleIntervalModel();
	// private SimpleCategoryModel activityModel = new SimpleCategoryModel();
	// a data model for speed distribution chart
	private SimplePieModel speedDistributionModel = new SimplePieModel();

	private String legend;

	// url to our map server for an image we just have to add center lon,lat
	private String mapUrl = "http://localhost:8090/ngi-lbs-server/gg?geolang=fr&request=map&radius=500&width=300&height=300&format=png8&user=customer-admin&pass=chou_2009&wgs84x1y1=";
	//private String mapUrl = "http://10.20.160.197:8090/ngi-lbs-server/gg?geolang=fr&request=map&radius=500&width=300&height=300&format=png8&user=customer-admin&pass=chou_2009&wgs84x1y1=";
	// date format used to capture date time
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
	private String dataEndDate = "2012-01-04 03:53:34 GMT";
	private String chartEndDate = "2012-01-04 05:00:00 GMT";
	private long dataEndEpoch;
	private long chartEndEpoch;

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

	public void onCreate() throws Exception {
		// super.doAfterCompose(comp);
		// an initial map image somewhere in Tunis
		map = (Image) this.getFellow("map");
		map.setSrc(mapUrl + "+10.18623|+36.84953");
		/*
		 * a first chart showing speed & fuel level as "line", and alerts as
		 * "scattered" points vehicle activity, and work time are shown in the
		 * background using interactive colored bands
		 */
		chartComp = (ZHighCharts) getFellow("chart");
		/*
		 * a second chart showing vehicle activity, and work time with bars also
		 * work time are shown in the background using interactive colored bands
		 * a good example of usage for a gantt diagram
		 */
		//chartComp2 = (ZHighCharts) getFellow("chart2");
		/*
		 * a pie chart to show speed range repartition
		 */
		chartComp3 = (ZHighCharts) getFellow("chart3");
		// jaugeS = (Chart) getFellow("jaugeS");

		// initJauge();

		/*
		 * set data tooltip format function (take care it's js code injection)
		 * always name the function "function formatTooltip(obj)" the obj
		 * parameter are the corresponding event data and the function must return a
		 * string. 
		 * the string can be formatted using obj parameter this function
		 * will be evaluated in JS and then used in data tooltip formatting.
		 * 
		 * for example here, we use a "shared" tooltip the object given in event is an
		 * array of point objects name points. for each point we want to display
		 * the date, the series name, the value and its units.
		 */
		// TODO use a for loop on obj.points[]instead
		chartComp.setTooltipFormatter("function formatTooltip(obj)"
				+ "{var p0=obj.points[0],p1=obj.points[1],p2=obj.points[2],"
				+ "tt=Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', obj.x)"
				+ ",gauge=null,mapUrl=obj.points[0].series.chart.options.chart.mapUrl"
				+ ",mapComp=obj.points[0].series.chart.options.chart.mapComp;"
				+ "if (p0) {"
					+ "if (p0.series.name) {tt +='<br/><b>'+ p0.series.name +':</b>';"
						+ "	if (p0.series.name != 'Alertes') tt += p0.y+' ';"
							+ "gauge = p0.series.options.gauge;"
						+ "if (gauge) gauge.setValue(p0.y);}"
						+ "if (p0.point.latitude && mapComp)"
						+ "{mapComp.setSrc(mapUrl + p0.point.longitude +'|'+p0.point.latitude"
						+ "+'&icolistwgs84='+ p0.point.longitude +'|'+p0.point.latitude + '|target');}"
					+ "if (p0.point.name) {tt +='<br/><b>'+ p0.point.name +':</b>';}"
					+ "if (p0.series.options.units) tt +=p0.series.options.units +'</b>';}"
				+ "if (p1) {"
					+ "if (p1.series.name) {tt +='<br/><b>'+ p1.series.name +':</b>';"
					+ "	if (p1.series.name != 'Alertes') tt += p1.y+' ';"
					+ "	gauge = p1.series.options.gauge;"
					+ "	if (gauge) gauge.setValue(p1.y);}"
					+ "if (p1.point.latitude && mapComp)"
						+ "{mapComp.setSrc(mapUrl + p1.point.longitude +'|'+p1.point.latitude"
					+ "+'&icolistwgs84='+ p1.point.longitude +'|'+p1.point.latitude + '|target');}"
					+ "if (p1.point.name) {tt +=' <b>'+ p1.point.name +'</b>'};"
					+ "if (p1.series.options.units) tt +=p1.series.options.units +'</b>';}"
				+ "if (p2) {"
					+ "if (p2.series.name) {tt +='<br/><b>'+ p2.series.name +':</b>';"
					+ "	if (p2.series.name != 'Alertes') tt += p2.y+' ';"
					+ "	gauge = p2.series.options.gauge;"
					+ "	if (gauge) gauge.setValue(p2.y);}"
					+ "if (p2.point.latitude && mapComp)"
					+ "{mapComp.setSrc(mapUrl + p2.point.longitude +'|'+p2.point.latitude"
					+ "+'&icolistwgs84='+ p2.point.longitude +'|'+p2.point.latitude + '|target');}"
					+ "if (p2.point.name) {tt +=' <b>'+ p2.point.name +'</b>';}"
					+ "if (p2.series.options.units) tt +=p2.series.options.units +'</b>';}"
				//+ "console.log('gauge: %o ',gauge);" 
				+ "return tt;};");
		chartComp.setTooltipOptions("{shared: true,crosshairs: true}");
		chartComp.setPlotOptions("{series:" +
				// to break 1000 points threshold: to 20000 points
				"{turboThreshold: 20000}" +
				",line:{lineWidth:1}" +
				"}");

		// manage legend a json object in a string
		legend = "{layout: 'vertical'" 
				+ ",align: 'right'"
				+ ",verticalAlign: 'top'" 
				+ ",x: 25," + "y: 100"
				+ ",symbolPadding: 10" 
				+ ",itemStyle: {paddingBottom: '10px'}"
				+ ",borderWidth: 1" 
				+ "}";
		chartComp.setLegend(legend);

		// set chart global options
		String optionString = "{"
				+ "mapUrl:'" + mapUrl + "'"
				+ ",marginRight: 140"
				+ ",marginLeft: 60"
				+ ",marginBottom: 25"
				+ ",zoomType: 'x'"
				+ ",mapComp:'map'"
				+ "}";
		chartComp.setOptions(optionString);

		// prepare some options for series
		// additional series parameters can be injected this way
		// any defined parameter will be stored in series.options object
		// in particular units to be used in tooltip as :
		// obj.series.options.units
		Map options = new HashMap();
		options.put("units", "litres");
		options.put("gauge", "fuelGauge");
		options.put("lineWidth", 1);
		chartComp.setSeriesOptions("Niveau Carburant", options);
		
		options = new HashMap();
		options.put("units", "km/h");
		options.put("lineWidth", 1);
		options.put("gauge", "speedGauge");
		chartComp.setSeriesOptions("Vitesse", options);

		options = new HashMap();
		options.put("units", "%");
		Map marker = new HashMap();
		// set no marker for 'tests' series
		marker.put("enabled", false);
		options.put("marker", marker);
		options.put("type", "line");
		options.put("color", "rgba(255, 205, 0, 0.5)");
		// Map line = new HashMap();
		// options.put("line", line);
		chartComp.setSeriesOptions("Test", options);

		// set type "scatter" for 'alerts' series
		options = new HashMap();
		options.put("type", "scatter");
		marker = new HashMap();
		// set image symbol for 'alerts' series
		marker.put("symbol", "url(img/alert.svg)");
		options.put("marker", marker);
		chartComp.setSeriesOptions("Alertes", options);

		/*
		 * note on date usage: HighCharts consider date and time as unix epoch
		 * time (ms since 1/1/1970) and consider UTC time so we use a
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss z") to translate string to
		 * epoch ms using: sdf.parse(date).getTime(); through
		 * getDateTime("2012-01-04 05:00:00 GMT") method
		 */
		dataEndEpoch = getDateTime(dataEndDate);
		chartEndEpoch = getDateTime(chartEndDate);
		// set X axis 'datetime' type, min & max date/time
		chartComp.setxAxisOptions("{type:'datetime'"
//				+ ",min:" + getDateTime("2012-01-02 23:00:00 GMT") 
//				+ ",tickPixelInterval: 100"
//				+ ",max:" + chartEndEpoch 
				+ ",dateTimeLabelFormats:{day: '%e of %b'}"
				+ "}"
				);

		// shift data or not when adding to series on vehicle data model
		//vehicleDataChartModel.setShift(true);
		
		// fill vehicle series model
		// series for fuel level: series name, x axis date time, y data value
		vehicleDataChartModel.addValue("Niveau Carburant",
				getDateTime("2012-01-02 23:53:34 GMT"), 21.0);
		vehicleDataChartModel.addValue("Niveau Carburant",
				getDateTime("2012-01-03 03:53:34 GMT"), 20.0);
		vehicleDataChartModel.addValue("Niveau Carburant",
				getDateTime("2012-01-03 08:53:34 GMT"), 17.0);
		vehicleDataChartModel.addValue("Niveau Carburant",
				getDateTime("2012-01-03 13:53:34 GMT"), 15.0);
		vehicleDataChartModel.addValue("Niveau Carburant",
				getDateTime("2012-01-03 18:53:34 GMT"), 14.0);
		vehicleDataChartModel.addValue("Niveau Carburant",
				getDateTime("2012-01-03 23:53:34 GMT"), 11.0);
		vehicleDataChartModel.addValue("Niveau Carburant", dataEndEpoch, 7.0);
		// series for speed
		vehicleDataChartModel.addValue("Vitesse",
				getDateTime("2012-01-02 23:53:34 GMT"), 84.0);
		vehicleDataChartModel.addValue("Vitesse",
				getDateTime("2012-01-03 03:53:34 GMT"), 24.0);
		vehicleDataChartModel.addValue("Vitesse",
				getDateTime("2012-01-03 08:53:34 GMT"), 4.0);
		vehicleDataChartModel.addValue("Vitesse",
				getDateTime("2012-01-03 09:33:34 GMT"), 115.5);
		vehicleDataChartModel.addValue("Vitesse",
				getDateTime("2012-01-03 13:53:34 GMT"), 70.5);
		vehicleDataChartModel.addValue("Vitesse",
				getDateTime("2012-01-03 18:53:34 GMT"), 95.5);
		vehicleDataChartModel.addValue("Vitesse",
				getDateTime("2012-01-03 23:53:34 GMT"), 124.2);
		vehicleDataChartModel.addValue("Vitesse", dataEndEpoch, 40.0);

		// generate a series with 1000 values
		/*
		 * long startTime = getDateTime("2012-01-02 23:53:34 GMT"); long endTime
		 * = getDateTime("2012-01-04 03:53:34 GMT"); int nbValues = 2000; long
		 * interval = (endTime - startTime)/nbValues; double value = 0.0;
		 * BigDecimal val; int i; for (i= 0;i < nbValues; i++) { value =
		 * Math.random()*-50.0; val = new BigDecimal(value); value =
		 * val.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		 * xyChartModel.addValue("Test", startTime + interval*i, value); }
		 * logger.info("==========>interval: " + interval + " i:" + i);
		 */
		/*
		 * show 2 speed alerts with a marker which symbol is an image take care
		 * it causes a bug when exporting the graph to an image or pdf:
		 * "(SVGConverter.error.while.rasterizing.file)Error while converting SVG"
		 * if the image is not publicly available if you use highcharts
		 * exporting server (or you need to use an exporting server
		 */
		Map data = new HashMap();
		marker = new HashMap();
		marker.put("symbol", "url(img/alert.svg)");
		data.put("marker", marker);
		data.put("name", "dépassement vitesse: 15.5 km/h");
		data.put("latitude", 36.80025);
		data.put("longitude", 10.18615);
		vehicleDataChartModel.addValue("Alertes",
				getDateTime("2012-01-03 09:33:34 GMT"), 115.5, data);
		data = new HashMap();
		marker = new HashMap();
		marker.put("symbol", "url(img/speed_limit.svg)");
		data.put("marker", marker);
		data.put("name", "dépassement vitesse: 24.2 km/h");
		data.put("latitude", 36.75493);
		data.put("longitude", 10.22288);
		vehicleDataChartModel.addValue("Alertes",
				getDateTime("2012-01-03 23:53:34 GMT"), 124.2, data);
		/*
		 * fill a plotbands model to represent vehicle activity in this case in
		 * the band model: each vehicle state [stop,idle,drive] is represented
		 * by a series each series contains the list of periods in this state,
		 * each band represent a vehicle's state with a 'from' (start) and
		 * 'to'(end) date/time, each band can hold some data contained in a Map
		 * those data will be send to client to be available for display in
		 * tooltip (on mouse over) or popup window (on click)
		 */

		/*
		 * prepare data map for the stop period band here put the address where
		 * the vehicle's is in stop state !
		 */
		data = new HashMap();
		data.put("adresse", "69 rue de la pompe 69690 ");
		vehicleActivityModel.addValue("Tokyo",
				getDateTime("2012-01-02 23:53:34 GMT"),
				getDateTime("2012-01-03 03:53:34 GMT"), data);
		data = new HashMap();
		data.put("adresse", "69 rue de la pompe 69690 ");
		vehicleActivityModel.addValue("idle",
				getDateTime("2012-01-03 03:53:34 GMT"),
				getDateTime("2012-01-03 04:53:34 GMT"), data);
		/*
		 * here prepare some data regarding the whole drive period band
		 */
		data = new HashMap();
		data.put("distance", "2.5 km");
		data.put("vitesse_moyenne", "32.5 km/h");
		data.put("vitesse_max", "82.5 km/h");
		vehicleActivityModel.addValue("conduite",
				getDateTime("2012-01-03 04:53:34 GMT"),
				getDateTime("2012-01-03 12:53:34 GMT"), data);
		vehicleActivityModel.addValue("arrêt",
				getDateTime("2012-01-03 12:53:34 GMT"),
				getDateTime("2012-01-03 13:53:34 GMT"), null);
		vehicleActivityModel.addValue("idle",
				getDateTime("2012-01-03 13:53:34 GMT"),
				getDateTime("2012-01-03 14:53:34 GMT"), null);
		data = new HashMap();
		// we can also give some html content ("html" key is a reserved word
		// as well as "name", which will be automatically set with series name)
		data.put("html", "<p><img src='img/car_moving.png'></p>");
		data.put("vitesse_moyenne", "55.5 km/h");
		vehicleActivityModel.addValue("conduite",
				getDateTime("2012-01-03 14:53:34 GMT"),
				getDateTime("2012-01-03 17:33:12 GMT"), data);
		vehicleActivityModel.addValue("arrêt",
				getDateTime("2012-01-03 17:33:12 GMT"),
				getDateTime("2012-01-03 22:32:23 GMT"), null);
		data = new HashMap();
		data.put("adresse", "69 rue de la pompe 69690 Alenvers");
		vehicleActivityModel.addValue("idle",
				getDateTime("2012-01-03 22:32:23 GMT"),
				getDateTime("2012-01-03 22:35:03 GMT"), data);
		data = new HashMap();
		data.put("vitesse_moyenne", "95.5 km/h");
		vehicleActivityModel.addValue("conduite",
				getDateTime("2012-01-03 22:35:03 GMT"),
				getDateTime("2012-01-04 00:33:22 GMT"), data);
		vehicleActivityModel.addValue("arrêt",
				getDateTime("2012-01-04 00:33:22 GMT"),
				getDateTime("2012-01-04 04:53:34 GMT"), null);

		// you can show driver working period on chart
		vehicleActivityModel.addValue("work",
				getDateTime("2012-01-03 08:00:00 GMT"),
				getDateTime("2012-01-03 12:00:00 GMT"), null);
		vehicleActivityModel.addValue("work",
				getDateTime("2012-01-03 13:00:00 GMT"),
				getDateTime("2012-01-03 17:00:00 GMT"), null);

		// fill plotbands/interval series style (one style for each series)
		// note that using zIndex properties
		// we put "work" bands behind the activity bands
		Map style = new HashMap();
		style.put("zIndex", -1);
		// task (interval) index on X axis when plotted with bars
		style.put("xIndex", -40);
		style.put("color", "rgba(255, 0, 0, 0.1)");
		vehicleActivityModel.addSeriesStyle("arrêt", style);
		style = new HashMap();
		style.put("zIndex", -1);
		style.put("xIndex", -30);
		style.put("color", "rgba(255, 255, 0, 0.2)");
		vehicleActivityModel.addSeriesStyle("idle", style);
		style = new HashMap();
		style.put("zIndex", -1);
		style.put("xIndex", -20);
		style.put("color", "rgba(0, 255, 0, 0.3)");
		vehicleActivityModel.addSeriesStyle("conduite", style);
		style = new HashMap();
		style.put("color", "rgba(100, 100, 100, 0.1)");
		style.put("zIndex", -2);
		style.put("xIndex", -50);
		// style.put("pointWidth", 30);
		vehicleActivityModel.addSeriesStyle("work", style);
		
		// draw an y band from 0 to 200 
		// to hide xplotbands of vehicle activity
		yBandModel.addValue("-",0,200, null);
		style = new HashMap();
		style.put("zIndex", -1);
		style.put("color", "rgba(255, 255, 255, 0.5)");
		style.put("noMouseEvents", true);
		yBandModel.addSeriesStyle("-", style);
		chartComp.setyBandModel(yBandModel);
		options = new HashMap();
		// no mouse event


		// now set chart with some titles
		chartComp.setTitle("Suivi Véhicule de 2012-01-02 23:00 à 2012-01-04 05:00");
		chartComp.setSubTitle("VW Polo 666 XXX 69");
		chartComp.setYAxisTitle("Carburant (litres) - Vitesse (km/h)");
		// set models (means give series data and bands data
		chartComp.setModel(vehicleDataChartModel);
		chartComp.setxBandModel(vehicleActivityModel);

		yWorkBandModel.addSeriesStyle("work", style);
		// you can show driver working period on chart
		yWorkBandModel.addValue("work", getDateTime("2012-01-03 08:00:00 GMT"),
				getDateTime("2012-01-03 12:00:00 GMT"), null);
		yWorkBandModel.addValue("work", getDateTime("2012-01-03 13:00:00 GMT"),
				getDateTime("2012-01-03 17:00:00 GMT"), null);

		// test another way to present activity chart
		// compose tooltip formatter javascript function
		String formatter = "function formatTooltip(obj){"
				+ "var tt=''"
				+ ",gauge=null;"
				+ "for(i=0;i<obj.points.length;i++){"
					+ "tt += 'series: '+obj.points[i].series.name+'<br/>';"
					+ "tt += 'x: '+obj.points[i].x+'<br/>';"
					+ "tt += 'y: '+obj.points[i].y+'<br/>';"
				+ "}"
//					+ "var mapUrl=obj.points[i].series.chart.options.chart.mapUrl"
//					+ ",mapComp=obj.points[0].series.chart.options.chart.mapComp" +
//						",p0=obj.points[i],info='';"
//					+ "if(p0.)"
//				+ "if (p0.adresse) info += '<br/>lieu: ' + p0.adresse + '';"
//				+ "if (p0.distance) info += '<br/>distance parcourue: ' + p0.distance + '';"
//				+ "if (p0.vitesse_moyenne) info += '<br/>vitesse moyenne: ' + p0.vitesse_moyenne + '';"
//				// only subset of html is supported (text), so, images are not rendered
//				+ "if (p0.html) tt += '<br/>'+ p0.html + 'truc';"
//				+ "tt += '<b>'+ obj.point.name +'</b><br/>'"
//				+ "+'de: '+Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', obj.point.low) + '<br/>'"
//				+ "+'à: '+Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', obj.point.y) + info;}"
//				+ "else {"
//				+ "tt=Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', obj.x)"
//				+ "if (p0) {"
//					+ "if (p0.series.name) {tt +='<br/><b>'+ p0.series.name +':</b>';"
//						+ "	if (p0.series.name != 'Alertes') tt += p0.y+' ';"
//							+ "gauge = p0.series.options.gauge;"
//						+ "if (gauge) gauge.setValue(p0.y);}"
//						+ "if (p0.point.latitude && mapComp)"
//						+ "{mapComp.setSrc(mapUrl + p0.point.longitude +'|'+p0.point.latitude"
//						+ "+'&icolistwgs84='+ p0.point.longitude +'|'+p0.point.latitude + '|target');}"
//					+ "if (p0.point.name) {tt +='<br/><b>'+ p0.point.name +':</b>';}"
//					+ "if (p0.series.options.units) tt +=p0.series.options.units +'</b>';}"
				+ "return tt;};";

				//+ "console.log('gauge: %o ',gauge);" 

/*		chartComp2.setLegend(legend);

		// activityBandModel.removeSeries("work");

		// logger.info("formatter: " + formatter);
		// options.put("type","column");
		// Map markerOpts = new HashMap();
		// markerOpts.put("enabled",false);
		// borderWidth: 1,shadow: true
		chartComp2.setPlotOptions("{column:{borderWidth:0,shadow: false" 
						+ ",borderColor: 'rgba(100, 100, 100, 0.7)'"
						+ ",showInLegend: false"
						+ ",pointPadding: 0,groupPadding: 0}"
						+ "}");
		options = new HashMap();
		options.put("pointWidth", 20);
		options.put("type", "column");
		chartComp2.setSeriesOptions("arrêt", options);
		chartComp2.setSeriesOptions("idle", options);
		chartComp2.setSeriesOptions("conduite", options);

		options = new HashMap();
		options.put("pointWidth", 10);
		options.put("type", "column");
		chartComp2.setSeriesOptions("work", options);
		
		options = new HashMap();
		options.put("units", "litres");
		options.put("gauge", "fuelGauge");
		options.put("lineWidth", 1);
		//options.put("type", "spline");
		chartComp2.setSeriesOptions("Niveau Carburant", options);
		options = new HashMap();
		options.put("units", "km/h");
		options.put("lineWidth", 1);
		options.put("gauge", "speedGauge");
		//options.put("type", "spline");
		chartComp2.setSeriesOptions("Vitesse", options);
		options = new HashMap();
		options.put("type", "scatter");
		marker = new HashMap();
		// set image symbol for 'alerts' series
		marker.put("symbol", "url(img/alert.svg)");
		options.put("marker", marker);
		chartComp2.setSeriesOptions("Alertes", options);

		// no labels for xAxis
		chartComp2.setxAxisOptions("{labels:{enabled:false},tickPixelInterval: 100"
				+ ",reversed: false"
				+ "}" + "");
		// set Y axis 'datetime' type, min & max datetime
		chartComp2.setyAxisOptions("{type:'datetime'" +
				",endOnTick: false,startOnTick: false" 
				+ ",min:" + getDateTime("2012-01-02 23:00:00 GMT")
				+ ",tickPixelInterval: 100"
				//+ ",max:" + chartEndEpoch 
				+ ",dateTimeLabelFormats:{day: '%e of %b'}}");
		chartComp2.setTitle("état Véhicule");
		chartComp2.setXAxisTitle("activité");
		chartComp2.setYAxisTitle(" ");
		chartComp2.setModel(vehicleActivityModel);
		chartComp2.addModel(vehicleDataChartModel);
		// yWorkBandModel
		chartComp2.setyBandModel(yWorkBandModel);
		chartComp2.setTooltipFormatter(formatter);
		chartComp2.setTooltipOptions("{shared: true,crosshairs: [false,false]}");

*/		
		
		// compose tooltip formatter javascript function
		String pFormatter = "function formatTooltip(obj)"
				+ " { var info= '%', head='';"
				+ "if (obj.point.name && obj.point.name.indexOf('stop',0) < 0) head += 'plage vitesses: ';"
				+ "return head + '<b>'+ obj.point.name +'</b><br/>'"
				+ "+''+ obj.point.y + info;}";
		chartComp3.setTooltipFormatter(pFormatter);

		// fill pie model: category, value
		speedDistributionModel.setValue("stop (0km/h)", 50);
		speedDistributionModel.setValue("0-40 km/h", 20);
		speedDistributionModel.setValue("40-80 km/h", 15);
		speedDistributionModel.setValue("80-120 km/h", 10);
		speedDistributionModel.setValue(">120 km/h", 5);

		/*
		 * set colors or any option for each category using series options (each
		 * category is considered as a series)
		 */
		style = new HashMap();
		style.put("color", "rgba(255, 255, 0, 0.5)");
		chartComp3.setSeriesOptions("stop (0km/h)", style);
		style = new HashMap();
		style.put("color", "rgba(255, 205, 0, 0.5)");
		chartComp3.setSeriesOptions("0-40 km/h", style);
		style = new HashMap();
		style.put("color", "rgba(255, 155, 0, 0.5)");
		chartComp3.setSeriesOptions("40-80 km/h", style);
		style = new HashMap();
		style.put("color", "rgba(255, 105, 0, 0.5)");
		chartComp3.setSeriesOptions("80-120 km/h", style);
		style = new HashMap();
		style.put("color", "rgba(255, 55, 0, 0.5)");
		chartComp3.setSeriesOptions(">120 km/h", style);

		chartComp3.setModel(speedDistributionModel);
		chartComp3.setTitle("Répartition des vitesses");

	}

	public void onEvent(Event arg0) throws Exception {
		// TODO Auto-generated method stub
	}

	// for a gauge with chart dial component
	private void initJauge() {
		int val = 127;
		DialModel dialmodel = new DialModel();
		DialModelScale scale = dialmodel.newScale(0, 220, 210, -240, 30, 2);

		// scale's configuration data
		scale.setText("KM/h");
		scale.setTickLabelOffset(0.5);
		// scale.setTextRadius(0.5);
		scale.setNeedleColor("#FFAA00");
		// scale.newRange(15, 25, "#0F4B00", 0.83, 0.89);
		// scale.newRange(5, 15, "#E17800", 0.83, 0.89);
		// scale.newRange(25, 35, "#E17800", 0.83, 0.89);
		// scale.newRange(-5, 5, "#FF0000", 0.83, 0.89);
		// scale.newRange(35, 45, "#FF0000", 0.83, 0.89);
		scale.setValue(val);
		dialmodel.setFrameBgAlpha(30);
		dialmodel.setFrameBgColor("#EEEEEE");
		dialmodel.setFrameBgColor1("#97D4FA");
		dialmodel.setFrameBgColor2("#008bb6");
		jaugeS.setModel(dialmodel);
	}

	public void controlValue() {
		ZGauge gauge = (ZGauge) this.getFellow("speedGauge");
		double val = gauge.getValue();
		if (val > 240)
			val = 0;
		else
			val += 10;
		gauge.setValue(val);
		// logger.info("gauge value: " + val);
	}

	public void controlStyle() {
		// String options = "{colorOfFill:[ '#111', '#ccc', '#ddd', '#eee' ]}";
		String options = "{colorOfFill:[ 'rgba(100,200,255,1)', '#ddd', '#ccc', 'rgba(1,1,1,0.3)' ]"
				+ ",colorOfText: 'rgba(255,255,255,1)'}";
		ZGauge gauge = (ZGauge) this.getFellow("speedGauge");
		gauge.setOptions(options);
		gauge = (ZGauge) this.getFellow("fuelGauge");
		gauge.setOptions(options);
		gauge = (ZGauge) this.getFellow("rpmGauge");
		gauge.setOptions(options);
		// logger.info("gauge value: " + val);
	}

	/**
	 * method to test adding a point to a chart model and update the chart
	 * accordingly
	 */
	public void addPoint() {
		dataEndEpoch += 360000; // + 1/10 of hour
		double value = Math.random()*50;
		BigDecimal val = new BigDecimal(value); 
		//value = val.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		vehicleDataChartModel.addValue("Niveau Carburant", dataEndEpoch, 
				val.setScale(2, BigDecimal.ROUND_DOWN).doubleValue(), null);
		logger.info("Niveau Carburant point added v: " + value);
		//invalidate();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		value = Math.random()*200;
		val = new BigDecimal(value); 
		//value = val.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		vehicleDataChartModel.addValue("Vitesse", dataEndEpoch, 
				val.setScale(2, BigDecimal.ROUND_DOWN).doubleValue(), null);
		logger.info("Vitesse point added v: " + value);

	}
	public void addPoints() {
		HashMap[] points = new HashMap[2];
		dataEndEpoch += 360000; // + 1/10 of hour
		
		double value = Math.random()*50;
		BigDecimal val = new BigDecimal(value); 
		//value = val.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		points[0] = new HashMap();
		points[0].put("series", "Niveau Carburant");
		points[0].put("x", dataEndEpoch);
		points[0].put("y", val.setScale(2, BigDecimal.ROUND_DOWN).doubleValue());
		logger.info("Niveau Carburant point added v: " + value);
		//invalidate();
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		value = Math.random()*200;
		val = new BigDecimal(value); 
		//value = val.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		points[1] = new HashMap();
		points[1].put("series", "Vitesse");
		points[1].put("x", dataEndEpoch);
		points[1].put("y", val.setScale(2, BigDecimal.ROUND_DOWN).doubleValue());
		
		logger.info("Vitesse points added : " + points[1].toString());
		vehicleDataChartModel.addValues(points);

	}
	

	// public void onClick$myComp (ForwardEvent event) {
	// MouseEvent mouseEvent = (MouseEvent) event.getOrigin();
	// alert("You listen onClick: " + mouseEvent.getTarget());
	// }
}