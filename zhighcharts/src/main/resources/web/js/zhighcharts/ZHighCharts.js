(function () {

	//Custom implementation of JSON.stringify -> MyStringify
	
    var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
    escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
    gap,
    indent,
    meta = {    // table of character substitutions
        '\b': '\\b',
        '\t': '\\t',
        '\n': '\\n',
        '\f': '\\f',
        '\r': '\\r',
        '"' : '\\"',
        '\\': '\\\\'
    },
    rep;


	function quote(string,bracketsOn) {
	
	//If the string contains no control characters, no quote characters, and no
	//backslash characters, then we can safely slap some quotes around it.
	//Otherwise we must also replace the offending characters with safe escape
	//sequences.
	
		var b = '"';
		if(!bracketsOn)
			b = '';
	
	    escapable.lastIndex = 0;
	    var result = escapable.test(string) ? b + string.replace(escapable, function (a) {
            var c = meta[a];
            return typeof c === 'string' ? c :
                '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
        }) + b : b + string + b;
	    return result;
	}


	function str(key, holder) {
	
	//Produce a string from holder[key].
	
	    var i,          // The loop counter.
	        k,          // The member key.
	        v,          // The member value.
	        length,
	        mind = gap,
	        partial,
	        value = holder[key];
	
	//If the value has a toJSON method, call it to obtain a replacement value.
	
	    if (value && typeof value === 'object' && typeof value.toJSON === 'function') {
	        value = value.toJSON(key);
	    }
	
	//If we were called with a replacer function, then call the replacer to
	//obtain a replacement value.
	
	    if (typeof rep === 'function') {
	        value = rep.call(holder, key, value);
	    }
	
	//What happens next depends on the value's type.
	
	    switch (typeof value) {
	    case 'string':
	    	
			if(key == "formatter"){
				return quote(value,false);
			}
			else return quote(value,true);
		
	    case 'function':
	    	return value;
	    
	    case 'number':
	
	//MyJSON numbers must be finite. Encode non-finite numbers as null.
	
	        return isFinite(value) ? String(value) : 'null';
	
	    case 'boolean':
	    case 'null':
	
	//If the value is a boolean or null, convert it to a string. Note:
	//typeof null does not produce 'null'. The case is included here in
	//the remote chance that this gets fixed someday.
	
	        return String(value);
	
	//If the type is 'object', we might be dealing with an object or an array or
	//null.
	
	    case 'object':
	
	//Due to a specification blunder in ECMAScript, typeof null is 'object',
	//so watch out for that case.
	
	        if (!value) {
	            return 'null';
	        }
	
	//Make an array to hold the partial results of stringifying this object value.
	
	        gap += indent;
	        partial = [];
	
	//Is the value an array?
	
	        if (Object.prototype.toString.apply(value) === '[object Array]') {
	
	//The value is an array. Stringify every element. Use null as a placeholder
	//for non-MyJSON values.
	
	            length = value.length;
	            for (i = 0; i < length; i += 1) {
	                partial[i] = str(i, value) || 'null';
	            }
	
	//Join all of the elements together, separated with commas, and wrap them in
	//brackets.
	
	            v = partial.length === 0 ? '[]' : gap ?
	                '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']' :
	                '[' + partial.join(',') + ']';
	            gap = mind;
	            return v;
	        }
	
	//If the replacer is an array, use it to select the members to be stringified.
	
	        if (rep && typeof rep === 'object') {
	            length = rep.length;
	            for (i = 0; i < length; i += 1) {
	                k = rep[i];
	                if (typeof k === 'string') {
	                    v = str(k, value);
	                    if (v) {
	                        partial.push(quote(k) + (gap ? ': ' : ':') + v);
	                    }
	                }
	            }
	        } else {
	
	//Otherwise, iterate through all of the keys in the object.
	
	            for (k in value) {
	                if (Object.prototype.hasOwnProperty.call(value, k)) {
	                    v = str(k, value);
	                    if (v) {
	                        partial.push(quote(k) + (gap ? ': ' : ':') + v);
	                    }
	                }
	            }
	        }
	
	//Join all of the member texts together, separated with commas,
	//and wrap them in braces.
	
	        v = partial.length === 0 ? '{}' : gap ?
	            '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}' :
	            '{' + partial.join(',') + '}';
	        gap = mind;
	        return v;
	    }
	}
	
	
	MyStringify = function (value, replacer, space) {
	
	//The stringify method takes a value and an optional replacer, and an optional
	//space parameter, and returns a MyJSON text. The replacer can be a function
	//that can replace values, or an array of strings that will select the keys.
	//A default replacer method can be provided. Use of the space parameter can
	//produce text that is more easily readable.
	
	        var i;
	        gap = '';
	        indent = '';
	
	//If the space parameter is a number, make an indent string containing that
	//many spaces.
	
	        if (typeof space === 'number') {
	            for (i = 0; i < space; i += 1) {
	                indent += ' ';
	            }
	
	//If the space parameter is a string, it will be used as the indent string.
	
	        } else if (typeof space === 'string') {
	            indent = space;
	        }
	
	//If there is a replacer, it must be a function or an array.
	//Otherwise, throw an error.
	
	        rep = replacer;
	        if (replacer && typeof replacer !== 'function' &&
	                (typeof replacer !== 'object' ||
	                typeof replacer.length !== 'number')) {
	            throw new Error('MyJSON.stringify');
	        }
	
	//Make a fake root object containing our value under the key of ''.
	//Return the result of stringifying the value.
	
	        return str('', {'': value});
	    };
	    
    //END OF CUSTOM JSON FUNCTIONS
	
	/**
	 * Overwrites obj1's values with obj2's and adds obj2's if non existent in obj1
	 * @param obj1
	 * @param obj2
	 * @returns obj3 a new object based on obj1 and obj2
	 */
	function merge_options(obj1,obj2){
	    var obj3 = {};
	    for (var attrname in obj1) { obj3[attrname] = obj1[attrname]; }
	    for (var attrname in obj2) { obj3[attrname] = obj2[attrname]; }
	    return obj3;
	}

	function _renderPopupData(popup, data, obj) {
		popup.firstChild.setValue(data.name 
									+ " : " + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', obj.options.from)
									+ " -> " + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', obj.options.to));
		while (popup.lastChild.$oid != popup.firstChild.$oid) {
			popup.removeChild(popup.lastChild);
		}
		var rows = new zul.grid.Rows(),
			grid = null,
			html = null,
			lv = null, 
			lk = null;
		for (var key in data) {
			// html element
			if (key == "html" && data[key] != undefined) {
				html = new zul.wgt.Html();
				html.setStyle("width: 100%;");
				html.setSclass('chartPopupHtml');
				html.setContent(data[key]);
			} else if (key != "name" && key != "html" // other keys than "name" or "html"
				&& data[key] != undefined) { 
				var row = new zul.grid.Row();
				row.setHflex("1");
				lk = new zul.wgt.Label();
				lk.setValue(key);
				lv = new zul.wgt.Label();
				lv.setValue(data[key]);
				row.appendChild(lk);
				row.appendChild(lv);
				rows.appendChild(row);
			}
		} 
		if (rows.nChildren > 0) {
			if (html){
				popup.appendChild(html);
			}
			grid = new zul.grid.Grid();
			grid.setHflex("1");
			grid.setSclass("chartPopupGrid");
			grid.appendChild(rows);
			popup.appendChild(grid);
			rows = null;
		} else {
			rows = null;
			if (html)
				popup.appendChild(html);
		}
		popup.open(popup.parent, [0,0],'after_pointer');
	}

zhighcharts.ZHighCharts = zk.$extends(zul.wgt.Div, {
	_options: null, // global chart options
	_plotOptions: null, // global chart options
	_type: 'line',
	_tooltipFormatter: null,
	_labels: null,
	_lang: null,
	_legend: null,
	_subtitle: null,//subtitle
	_title: null, // Title
	_tooltip: null, // tooltip
	_tooltipOptions: null, // tooltip options
	_titleOptions: null, // titleOptions options
	_subtitleOptions: null, // subtitleOptions options
	_xAxisTitle: null, // xAxis title text
	_yAxisTitle: null, // yAxis title text
	_xPlotBands: null, // xAxis PlotBands
	_yPlotBands: null, // yAxis PlotBands
	_series: null,
	_exportURL: null, //URL of highcharts export server
	_exporting: null, //URL of highcharts export server
	_pane: null, //for polar charts and angular gauges
	_pointClickCallback: null,
	_clickCallback: null,
	
	updateGaugeValue: function (series,val) {
		this.chart.series[series].data[0].update(val);
	},
	
	/**
	 * Zoom out to 1:1
	 */
	zoomOut: function () {
		if (!this.chart)
			return;
		var extremes = this.chart.xAxis[0].getExtremes();
		this.chart.xAxis[0].setExtremes(extremes.dataMin,extremes.dataMax);
	},

	zoom: function (min,max) {
		if (!this.chart)
			return;
		var extremes = this.chart.xAxis[0].getExtremes();
		if (min < extremes.dataMin)
			min = extremes.dataMin;
		if (max > extremes.dataMax)
			max = extremes.dataMax;
		this.chart.xAxis[0].setExtremes(min,max);
	},

	
	addPoint: function (series,data) {
		var dataObj=null;
		if (typeof data === 'string') {
			try {
				dataObj = jq.evalJSON(data);
			} catch (err){
				console.error('ZHighCharts addPoint evalJSON data: %o error: %s',data,err);
				zk.error('ZHighCharts addPoint evalJSON data: ' + data 
						+ '\r\nfailed reason: ' + err);
			}
		} else if (data instanceof Object)
			dataObj = data;
		if (this.chart && dataObj) {
			for (i = 0;i < this.chart.series.length; i++) {
				var tseries = this.chart.series[i];
				if (tseries.name == series) {
					tseries.addPoint(dataObj, true);
					break;
				}
			}
		}
	},
	
	addPoints: function (data, shift) {
		var dataObj, point, series, max= 0, axis, inverted, extremes,tmp,name;
		/* dataObj should be an array of objects
		 * like [{series: 'name1',x: 123,y: 321},{series: 'name2',x: 123,y: 321}]
		 */
		if (typeof data === 'string') {
			try {
				dataObj = jq.evalJSON(data);
			} catch (err){
				console.error('addPoints evalJSON data: %o error: %s',data,err);
				zk.error('ZHighCharts addPoints evalJSON data :' 
						+ data + '\r\nfailed reason: ' + err);
			}
		} else if (data instanceof Object)
			dataObj = data;
		if (this.chart && dataObj) {
			inverted = this.chart.inverted;
			if (inverted)
				axis = this.chart.yAxis[0];
			else
				axis = this.chart.xAxis[0];
			
			for (j=0;j < dataObj.length; j++) {
				point = new Object()
				for (var key in dataObj[j]) {
					if (key != 'series')
						point[key] = dataObj[j][key];
					else
						name = dataObj[j][key];  
				}
				
				for (i = 0;i < this.chart.series.length; i++) {
					var tseries = this.chart.series[i];
					if (tseries.name == name) {
						if (inverted) {
							tmp = point.y;
							point.y = point.x;
							point.x = tmp;
							
							tseries.addPoint(point, false, shift);
							if (point.y > max)
								max = point.y 
						} else {
							tseries.addPoint(point, false, shift);
							if (point.x > max)
								max = point.x 
						}
					}
				}
			}
			extremes = axis.getExtremes();
			if (max > extremes.max) {
				axis.setExtremes(extremes.min, max, false);
			}
			this.chart.redraw();
		}
	}, 

	
	changePoint: function (series,data) {
		var dataObj=null,id,tseries,point,i,type='',isLine;
		if (typeof data === 'string') {
			try {
				dataObj = jq.evalJSON(data);
			} catch (err){
				console.error('ZHighCharts changePoint evalJSON data: %o error: %s',data,err);
				zk.error('ZHighCharts changePoint evalJSON data: ' + data + '\r\nfailed reason: ' + err);
			}
		} else if (data instanceof Object)
			dataObj = data;
		if (this.chart && dataObj) {

			for (i = 0;i < this.chart.series.length; i++) {
				tseries = this.chart.series[i];				
				if (tseries.name == series) {
					type = '' + tseries.options.type;
					if (type)
						isLine = tseries.options.type.indexOf('line') + 1;
					id = tseries.name + dataObj.x;
					point = this.chart.get(id);
					if (!point) {
						// not found by id use index
						if (dataObj.index >=0)
							point = tseries.points[dataObj.index];
					}
					if (point) {
						// if a marker.symbol is defined we need to remove the point before
						// && type != 'line' && type != 'spline'
						if (dataObj.marker && dataObj.marker.symbol && !isLine) {
							point.remove();
							tseries.addPoint(dataObj);
						} else // else update point
							point.update(dataObj);
					} else {
						zk.error('ZHighCharts.changePoint() point not found for data: x=' + dataObj.x
								+ ', y=' + dataObj.y + ', id=<'+ dataObj.id + '> !!!');
					}
				}
			}
		}
	}, 
	
	changePoints: function (data) {
		var dataObj, point, series, max= 0, axis, inverted, extremes,tmp,name,index,tseries,i,isLine,type,foundbyindex;
		/* dataObj should be an array of objects
		 * like [{series: 'name1',x: 123,y: 321},{series: 'name2',x: 123,y: 321}]
		 */
		if (typeof data === 'string') {
			try {
				dataObj = jq.evalJSON(data);
			} catch (err){
				console.error('changePoints evalJSON data: %o error: %s',data,err);
				zk.error('ZHighCharts changePoints evalJSON data: ' + data + '\r\nfailed reason: ' + err);
			}
		} else if (data instanceof Object)
			dataObj = data;
		if (this.chart && dataObj) {
			inverted = this.chart.inverted;
			if (inverted)
				axis = this.chart.yAxis[0];
			else
				axis = this.chart.xAxis[0];
			
			for (j=0;j < dataObj.length; j++) {
				point = new Object()
				for (var key in dataObj[j]) {
					if (key != 'series')
						point[key] = dataObj[j][key];
					else
						name = dataObj[j][key];  
				}
				
				for (i = 0;i < this.chart.series.length; i++) {
					tseries = this.chart.series[i];
					type = this.chart.series[i].options.type
					if (type)
						isLine = type.indexOf('line') + 1;
					
					foundbyindex=false;
					if (tseries.name == name) {
						id = tseries.name + point.x;
						tmp = this.chart.get(id);
						if (!tmp) {
							// not found by id use index
							index = point.index;
							if (index)
								tmp = this.chart.series[i].points[index];
							foundbyindex=true;
						}
						if (tmp) { // type.indexOf("line") < 0 && 
							if (point.marker && point.marker.symbol && !foundbyindex && !isLine) {
								tmp.remove();
								tseries.addPoint(point);
							} else
								tmp.update(point,false);
						}
					}
				}
			}

			this.chart.redraw();
		}
	}, 
	
	removePoints: function (series,data) {
		if (!this.chart)
			return;
		var dataObj = null, id, seriesA= null,pointIds = [],id, index= -1,i,seriesKey;
		if (typeof data === 'string') {
			try {
				dataObj = jq.evalJSON (data);
			} catch (err){
				console.error('ZHighCharts removePoint evalJSON data: %o error: %s',data,err);
				zk.error('ZHighCharts removePoints evalJSON data: ' + data + '\r\nfailed reason: ' + err);
			}
		} else if (data instanceof Object)
			dataObj = data;
		
		if (typeof series === 'string') {
			try {
				seriesA = jq.evalJSON (series);
			} catch (err){
				console.error('ZHighCharts removePoint evalJSON series: %o error: %s',series,err);
				zk.error('ZHighCharts removePoints evalJSON series: ' 
						+ series + '\r\nfailed reason: ' + err);
			}
		} else
			seriesA = series;
		
		if(seriesA && dataObj && dataObj.startIdx != undefined && dataObj.endIdx != undefined) {
			for(var k=0;k < seriesA.length;k++) {
				seriesKey = seriesA[k];
				index = -1;
				for (var j = 0;j < this.chart.series.length;j++) {
					if (seriesKey == this.chart.series[j].name) {
						index = j;
						break;
					}
				}
				if (index >= 0) {
					id = null;
					pointIds = [];
					for (i=dataObj.startIdx;i<=dataObj.endIdx;i++){
						try {
							id = this.chart.series[index].points[i].id;
							if (id) {pointIds.push(id);}
						} catch(err) {
							console.error('removePoint failed accessing series index: %d point index: %d', index, i);
						}
					}
					// now remove points
					for (i=0;i<pointIds.length;i++) {
						this.chart.get(pointIds[i]).remove(false);
					}
				}
			}
		}
		this.chart.redraw();
	},

	getPointAtIndex: function(seriesIdx,pointIdx) {
		if (!this.chart || !this.chart.series 
			|| seriesIdx >= this.chart.series.length
			|| pointIdx >= this.chart.series[seriesIdx].data.length)
			return null;
		return this.chart.series[seriesIdx].points[pointIdx];
	},
	
	getPoint: function(id) {
		if (this.chart)
			return this.chart.get(id);
		return null;
	},
	
	getSeriesPointAtIndex: function(series,pointIdx) {
		var seriesIdx=-1; 
		if (!this.chart ||!this.chart.series)
			return null;
		for (var i=0;i < this.chart.series.length;i++) {
			if (series == this.chart.series[i].name) {
				seriesIdx = i;
				break;
			}
		}
		if (seriesIdx >= 0 && pointIdx >= 0 
				&& pointIdx < this.chart.series[seriesIdx].data.length)
			return this.chart.series[seriesIdx].data[pointIdx];
	},
	getSeriesLength: function(series) {
		var seriesIdx=-1; 
		if (!this.chart ||!this.chart.series)
			return null;
		for (var i=0;i < this.chart.series.length;i++) {
			if (series == this.chart.series[i].name) {
				seriesIdx = i;
				break;
			}
		}
		if (seriesIdx >= 0 && this.chart.series[seriesIdx] 
			&& this.chart.series[seriesIdx].data)
			return this.chart.series[seriesIdx].data.length;
		return 0;
	},
	
	
	addBand: function (series,data, isYBand) {
		var dataObj,axis,extremes, max;
		if (typeof data === 'string') {
			try {
				dataObj = jq.evalJSON (data);
			} catch (err){
				console.error('ZHighCharts addBand evalJSON data: %o error: %s',data,err);
				zk.error('ZHighCharts addBand evalJSON data: ' + data 
						+ '\r\nfailed reason: ' + err);
			}
		} else if (data instanceof Object)
			dataObj = data;
		if (this.chart && dataObj) {
			this._addEventToBand(dataObj);
			if (!isYBand) {
				extremes = this.chart.xAxis[0].getExtremes();		
				if (dataObj.to && dataObj.to > extremes.max)
					max = dataObj.to;
				else
					max = extremes.max;
				this.chart.xAxis[0].setExtremes(extremes.min, max, false);
				this.chart.xAxis[0].addPlotBand(dataObj);
			} else {
				this.chart.yAxis[0].addPlotBand(dataObj);
			}
			this.chart.redraw();
		}
	}, 
	
	changeBand: function (series, data, isYBand) {
		var dataObj,axis,extremes, max;
		if (typeof data === 'string')
			dataObj = jq.evalJSON (data);
		else if (data instanceof Object)
			dataObj = data;
		
		if (dataObj) {
			this._addEventToBand(dataObj);
			if (!isYBand) {
				axis = this.chart.xAxis[0];
				extremes = axis.getExtremes();		
				if (dataObj.to && dataObj.to > extremes.max)
					max = dataObj.to;
				else
					max = extremes.max;
				axis.setExtremes(extremes.min, max, false);
			} else
				axis = this.chart.yAxis[0];
			if (axis) {
				// remove old
				axis.removePlotBand(dataObj.id);
				axis.addPlotBand(dataObj);
				this.chart.redraw();
			}
		}
	}, 
	
	removeBand: function (series,data, isYBand) {
		var dataObj,axis,extremes, max;
		if (typeof data === 'string')
			dataObj = jq.evalJSON (data);
		else if (data instanceof Object)
			dataObj = data;
		if (dataObj) {
			if (!isYBand)
				axis = this.chart.xAxis[0];
			else
				axis = this.chart.yAxis[0];
			if (axis) {
				// remove old
				axis.removePlotBand(dataObj.id);
			}
		}
	}, 
	
	clearBands: function (isYBand) {
		var ids = [];
		if (!isYBand)
			axis = this.chart.xAxis[0];
		else
			axis = this.chart.yAxis[0];
		if (axis) {
			// remove all bands on axis
			for (var i=0;i<axis.plotLinesAndBands.length;i++) {
				ids.push(axis.plotLinesAndBands[i].id);
			}
			for (var i=0;i<ids.length;i++) {
				axis.removePlotBand(ids[i]);
			}
		}
	},
	
	getBandById: function(id, isYBand) {
		var axis;
		if (!isYBand)
			axis = this.chart.xAxis[0];
		else
			axis = this.chart.yAxis[0];
		if (axis) {
			for (var i=0;i<axis.plotLinesAndBands.length;i++) {
				if (axis.plotLinesAndBands[i].id == id) {
					return axis.plotLinesAndBands[i];
				}
			}
		}
		return null;
	},
	
	getBandAtIndex: function(idx, isYBand) {
		var axis;
		if (!isYBand)
			axis = this.chart.xAxis[0];
		else
			axis = this.chart.yAxis[0];
		if (axis && axis.plotLinesAndBands 
				&& idx < axis.plotLinesAndBands.length)
				return axis.plotLinesAndBands[i];
		return null;
	},

	
	// constructor
	$init: function () {
		this.$supers('$init', arguments);
	},
	
	$define: {
		options: function() { // this function will be called after setOptions() .
			if (this._options) {
				try {
					this.chartOptions = 
						merge_options(this.chartOptions,jq.evalJSON (MyStringify(jq.evalJSON(this._options))));
				} catch (err){
					console.error('ZHighCharts evalJSON chart options error: '+ err);
					zk.error('ZHighCharts addBand evalJSON chart options: ' + this._options 
							+ '\r\nfailed reason: ' + err);
				}
			}
			if(this.desktop && this.chart) {
				// updated UI here.
			}
		},
		
		/** Sets the type of chart.
		 * <p>Default: "pie"
		 * <p>Allowed Types:
		 * line, spline, area, areaspline, column, bar, pie, scatter
		 * , stackbar, stackcolumn, stackarea
		 * @param String type
		 */
		/** Returns the type of chart
		 * @return String
		 */
		type: function() { // this function will be called after setType() .
		},
		series: function() {
			if(this.desktop && this.chart && this._series) {
				// updated UI here.
			}
		},
		xPlotBands: function() {
		},
		yPlotBands: function() {
		},
		xAxisTitle: function() {
		},
		yAxisTitle: function() {
		},
		xAxisOptions: function() {
		},
		yAxisOptions: function() {
		},
		tooltipFormatter: function() {
		},
		tooltipOptions: function() {
		},	
		titleOptions: function() {
		},	
		subtitleOptions: function() {
		},	
		plotOptions: function() {
		},	
		pane: function() {
		},	
		exportURL: function() {
			if(this.desktop && this.chart) {
				// updated UI here.
				this.chart.exporting.url = this._exportURL;
				this.chart.redraw();
			}
		},
		exporting: function() {
		},
		labels: function() {
			if(this.desktop && this.chart) {
				// updated UI here.
			}
		},
		legend: function() {
			if(this.desktop && this.chart) {
				// updated UI here.
			}
		},
		title: function() { // this function will be called after setTitle() .
			if(this.desktop && this.chart) {
				// updated UI here.
				// this.chart.title.text = this._title;
			}
		},
		subtitle: function() { // this function will be called after setTitle() .
			if(this.desktop && this.chart) {
				// updated UI here.
				// this.chart.subtitle.text = this._subtitle;
				// this.chart.redraw();
			}
		},
		pointClickCallback: function() {
			
		},
		clickCallback: function() {
			
		}

	},
	
	bind_: function () {
		this.$supers(zhighcharts.ZHighCharts,'bind_', arguments);
				
		// prepare tooltip formatter function
		// first give a standard one
		var strfun = null,series,xPlotBands,yPlotBands,xAxis,yAxis,tooltipOptions,titleOptions,subtitleOptions,
			legend = {},
			pane = {},
			plotOptions = {},
			exporting = {},
			strdlf;
		// initialize series
		try {
			series = jq.evalJSON(MyStringify(jq.evalJSON(this._series)));
		} catch (err) {
			console.error('evalJSON series failed reason: ' + err);
			zk.error('ZHighCharts evalJSON series failed reason: ' + err);
		}
		try {
			xPlotBands = jq.evalJSON(MyStringify(jq.evalJSON(this._xPlotBands)));
		} catch (err) {
			console.error('evalJSON xPlotBands failed reason: ' + err);
			zk.error('ZHighCharts evalJSON xPlotBands failed reason: ' + err);
		}
		try {
			yPlotBands = jq.evalJSON(MyStringify(jq.evalJSON(this._yPlotBands)));
		} catch (err) {
			console.error('evalJSON yPlotBands failed reason: ' + err);
			zk.error('ZHighCharts evalJSON yPlotBands failed reason: ' + err);
		}
		try {
			xAxis = jq.evalJSON(MyStringify(jq.evalJSON(this._xAxisOptions)));
		} catch (err) {
			console.error('evalJSON xAxis options: ' + this._xAxisOptions 
					+' failed reason: ' + err);
			zk.error('evalJSON xAxis options:' + this._xAxisOptions 
					+' failed reason: ' + err);
		}

		try {
			yAxis = jq.evalJSON(MyStringify(jq.evalJSON(this._yAxisOptions)));
		} catch (err) {
			console.error('evalJSON yAxis options: ' + this._yAxisOptions 
					+' failed reason: ' + err);
			zk.error('evalJSON yAxis options: ' + this._yAxisOptions 
					+' failed reason: ' + err);
		}
		try {
			tooltipOptions = jq.evalJSON(MyStringify(jq.evalJSON(this._tooltipOptions)));
		} catch (err) {
			console.error('evalJSON tooltipOptions failed reason: ' + err);
			zk.error('evalJSON tooltipOptions: ' + this._tooltipOptions
					+' failed reason: ' + err);
		}
		try {
			titleOptions = jq.evalJSON(MyStringify(jq.evalJSON(this._titleOptions)));
		} catch (err) {
			console.error('evalJSON titleOptions failed reason: ' + err);
			zk.error('evalJSON titleOptions: ' + this._titleOptions
					+' failed reason: ' + err);
		}
		try {
			subtitleOptions = jq.evalJSON(MyStringify(jq.evalJSON(this._subtitleOptions)));
		} catch (err) {
			console.error('evalJSON subtitleOptions failed reason: ' + err);
			zk.error('evalJSON subtitleOptions: ' + this._subtitleOptions
					+' failed reason: ' + err);
		}
		try {
			exporting = jq.evalJSON(MyStringify(jq.evalJSON(this._exporting)));
		} catch (err) {
			console.error('evalJSON _exporting failed reason: ' + err);
			zk.error('evalJSON _exporting: ' + this._exporting 
					+' failed reason: ' + err);
		}		
		
		if (exporting == undefined)
			exporting = new Object();
		if (exporting && this._exportURL && exporting.url == undefined) {
			exporting.url = this._exportURL;
		}
		
		
		if (xAxis == undefined)
			xAxis = new Object();
		if (yAxis == undefined)
			yAxis = new Object();
		if (xAxis && xPlotBands) {
			xAxis.plotBands = xPlotBands;
		}
		if (xAxis) {
			xAxis.title = {text: this._xAxisTitle};
		}
		if (yAxis && yPlotBands) {
			yAxis.plotBands = yPlotBands;
		}
		if (yAxis) {
			yAxis.title = {text: this._yAxisTitle};
		}
		if (this._plotOptions) {
			try {
				plotOptions = jq.evalJSON(MyStringify(jq.evalJSON(this._plotOptions)));
			} catch (err) {
				console.error('ZHighCharts evalJSON plotOptions failed reason: ' + err);
				zk.error('evalJSON plotOptions: ' + this._plotOptions 
						+' failed reason: ' + err);
			}
		}
		if (this._legend) {
			try {
				legend = jq.evalJSON(MyStringify(jq.evalJSON(this._legend)));
			} catch (err) {
				console.error('ZHighCharts evalJSON legend failed reason: ' + err);
				zk.error('evalJSON legend: ' + this._legend 
						+' failed reason: ' + err);
			}
		}
		
		if (this._pane) {
			try {
				pane = jq.evalJSON(MyStringify(jq.evalJSON(this._pane)));
			} catch (err) {
				console.error('ZHighCharts evalJSON pane failed reason: ' + err);
				zk.error('evalJSON pane: ' + this._pane 
						+' failed reason: ' + err);
			}
		}

		// get component's tooltip formatting function
		if (this._tooltipFormatter){
			strfun = this._tooltipFormatter;
			try {
				eval(strfun);
			} catch (err){
				console.error('ZHighCharts eval formatTooltip error: '+ err);
				zk.error('eval tooltipFormatter: ' + this._tooltipFormatter 
						+' failed reason: ' + err);
			}
		}

		if (tooltipOptions == null ){
			tooltipOptions = new Object();
		}
		if (titleOptions == null ){
			titleOptions = new Object();
		}

		if (titleOptions && this._title && titleOptions.text == undefined) {
			titleOptions.text = this._title;
		}
		if (subtitleOptions == null ){
			subtitleOptions = new Object();
		}

		if (subtitleOptions && this._subtitle && subtitleOptions.text == undefined) {
			subtitleOptions.text = this._subtitle;
		}
		
		if(this._tooltipFormatter)
			tooltipOptions.formatter = function() { 
		    	 	return formatTooltip(this);
		};
		    
	    // click point callback
	    if (this._pointClickCallback) {
	    	if (this._pointClickCallback.indexOf('function pointClick') >= 0) {
				try {
					eval(this._pointClickCallback);
					var click = function() {
						return pointClick(this);
					};

					plotOptions.series.point = {events:{click: click}};
					
				} catch (err){
					console.error('ZHighCharts eval pointClickCallback error: '+ err);
					zk.error('eval pointClickCallback: ' + this._pointClickCallback 
							+' failed reason: ' + err);
				}
	    	} else
	    		zk.error('bad pointClickCallback: ' + this._pointClickCallback 
	    				+ 'must contain "function pointClick"'); 
		}
		
		// prepare global tooltip
		if (this.getTooltip())
			this.bPopup = this.$f(this.getTooltip());
		
		if (!this.chartOptions) {
			this.chartOptions = new Object();
		}
		if (this.chartOptions.mapComp){
			var comp = this.$f(this.chartOptions.mapComp,true);
			if (comp)
				this.chartOptions.mapComp = comp;
		}
		
		this.chartOptions.renderTo = this.$n();
		if (this._type) {
			this.chartOptions.defaultSeriesType = this._type;
		}
		
	    // click chart callback
	    if (this._clickCallback) {
	    	if (this._clickCallback.indexOf('function chartClick') >= 0) {
				try {
					eval(this._clickCallback);
					var click = function() {
						return chartClick(this);
					};

					this.chartOptions.events = {click: click};
					
				} catch (err){
					console.error('ZHighCharts eval clickCallback error: '+ err);
					zk.error('eval clickCallback: ' + this._clickCallback 
							+' failed reason: ' + err);
				}
	    	} else
	    		zk.error('bad clickCallback: ' + this._clickCallback 
	    				+ 'must contain "function chartClick"'); 
		}
		
		if(series && series.length > 0)
			for (i = 0; i < series.length;i++) {
				if (series[i].gauge) {
					var name = series[i].gauge,
					wgt = this.$f(name);
					if (wgt)
						series[i].gauge = wgt;
				}
			}
		// store reference to parent Widget
		this.chartOptions.parentWidget = this;
		
		// add events callback to X plotBands
		this._addBandEvents(xPlotBands);
		// add events callback to Y plotBands
		this._addBandEvents(yPlotBands);
		
		//Force UTC
		Highcharts.setOptions({
		    global: {
		        useUTC: true
		    }
		    //chart: this.chartOptions
		});
				
		// now create the chart itself
		this.chart = new Highcharts.Chart({
			credits: { enabled: false
			},
			
			chart: this.chartOptions,
			
			title: titleOptions,
			subtitle: subtitleOptions,
			
			exporting : exporting,
			xAxis: xAxis,
			
			yAxis: yAxis,
			
			pane : pane,
			
			tooltip: tooltipOptions,
		    legend: legend,
			plotOptions: plotOptions,
			series: series
			});
		
//		console.log(this);
				
	},
	
	unbind_: function () {
	
		this.$supers(zhighcharts.ZHighCharts,'unbind_', arguments);
	},
	
	/**
	 * internal method to add mouseover and click events handler to each plotband
	 * note it should also work for plotlines
	 */
	_addBandEvents: function(plotBands) {
		if (plotBands == null)
			return;
		
		// loop on plotbands to add events listener when some data are associated
		for (var i = 0; i < plotBands.length; i++) {
			// add event only if some data are present
			this._addEventToBand(plotBands[i]);
		}
		
	},
	
	_addEventToBand: function (band) {
		var allow = true;
		if (band.noMouseEvents)
			allow = false;
		if (band.data && allow) {
			var 
			popup = this.bPopup,
			events = {
		        	mouseover: function(e) {
		                // popup band name (it's series name), if tooltip is defined
			            var popup = this.options.popup,
		                data = this.options.data;
		                if (popup) {
		                	if (data.name) {
			            		popup.firstChild.setValue(data.name 
										+ " : " + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.options.from)
										+ " -> " + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.options.to));
			            		if (popup.lastChild.$oid != popup.firstChild.$oid) {
			            			popup.removeChild(popup.lastChild);
			            		}
			                	popup.setSclass("chartPopup");
			                	popup.open(popup.parent, [0,0],'after_pointer');
		                	} 
		                }
		            },
		            mouseout: function(e) {
		            	var popup = this.options.popup;
		            	if (popup) {
		            		popup.firstChild.setValue("");
		            		if (popup.lastChild.$oid != popup.firstChild.$oid) {
		            			popup.removeChild(popup.lastChild);
		            		}
		            		popup.setSclass("chartPopupClosed");
		            		popup.close();
		            	}
		            },
		            click: function(e) {

		                var popup = this.options.popup,
		                data = this.options.data;
		                if (popup) {
		                	_renderPopupData(popup, data, this)
		                }
		            }
		        };
			band.events = events;
			band.popup = popup;
		}
	},
	
	getZclass: function () {
		return this._zclass != null ? this._zclass: "z-ZHighCharts";
	}
});
})();
