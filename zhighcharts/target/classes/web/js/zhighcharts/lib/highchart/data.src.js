



(function (Highcharts) {	
	
	
	var each = Highcharts.each;
	
	
	
	var Data = function (options) {
		this.init(options);
	};
	
	
	Highcharts.extend(Data.prototype, {
		
	
	init: function (options) {
		this.options = options;
		this.columns = [];
		
		
		
		this.parseCSV();
		
		
		this.parseTable();
		
		
		this.parseTypes();
		
		
		this.findHeaderRow();
		
		
		this.parsed();
		
		
		this.complete();
		
	},
	
	
	parseCSV: function () {
		var options = this.options,
			csv = options.csv,
			columns = this.columns,
			startRow = options.startRow || 0,
			endRow = options.endRow || Number.MAX_VALUE,
			startColumn = options.startColumn || 0,
			endColumn = options.endColumn || Number.MAX_VALUE,
			lines;
			
		if (csv) {
			lines = csv.split(options.lineDelimiter || '\n');
			
			each(lines, function (line, rowNo) {
				if (rowNo >= startRow && rowNo <= endRow) {
					var items = line.split(options.itemDelimiter || ',');
					each(items, function (item, colNo) {
						if (colNo >= startColumn && colNo <= endColumn) {
							if (!columns[colNo - startColumn]) {
								columns[colNo - startColumn] = [];					
							}
							
							columns[colNo - startColumn][rowNo - startRow] = item;
						}
					});
				}
			});
		}
	},
	
	
	parseTable: function () {
		var options = this.options,
			table = options.table,
			columns = this.columns,
			startRow = options.startRow || 0,
			endRow = options.endRow || Number.MAX_VALUE,
			startColumn = options.startColumn || 0,
			endColumn = options.endColumn || Number.MAX_VALUE,
			colNo;
			
		if (table) {
			
			if (typeof table === 'string') {
				table = document.getElementById(table);
			}
			
			each(table.getElementsByTagName('tr'), function (tr, rowNo) {
				colNo = 0; 
				if (rowNo >= startRow && rowNo <= endRow) {
					each(tr.childNodes, function (item) {
						if ((item.tagName === 'TD' || item.tagName === 'TH') && colNo >= startColumn && colNo <= endColumn) {
							if (!columns[colNo]) {
								columns[colNo] = [];					
							}
							columns[colNo][rowNo - startRow] = item.innerHTML;
							
							colNo += 1;
						}
					});
				}
			});
		}
	},
	
	
	findHeaderRow: function () {
		var headerRow = 0;
		each(this.columns, function (column) {
			if (typeof column[0] !== 'string') {
				headerRow = null;
			}
		});
		this.headerRow = 0;			
	},
	
	
	trim: function (str) {
		return str.replace(/^\s+|\s+$/g, '');
	},
	
	
	parseTypes: function () {
		var columns = this.columns,
			col = columns.length, 
			row,
			val,
			floatVal,
			trimVal,
			dateVal;
			
		while (col--) {
			row = columns[col].length;
			while (row--) {
				val = columns[col][row];
				floatVal = parseFloat(val);
				trimVal = this.trim(val);
				
				if (trimVal == floatVal) { 
				
					columns[col][row] = floatVal;
					
					
					if (floatVal > 365 * 24 * 3600 * 1000) {
						columns[col].isDatetime = true;
					} else {
						columns[col].isNumeric = true;
					}					
				
				} else { 
					dateVal = Date.parse(val);
					
					if (col === 0 && typeof dateVal === 'number' && !isNaN(dateVal)) { 
						columns[col][row] = dateVal;
						columns[col].isDatetime = true;
					
					} else { 
						columns[col][row] = trimVal;
					}
				}
				
			}
		}		
	},
	
	parsed: function () {
		if (this.options.parsed) {
			this.options.parsed.call(this, this.columns);
		}
	},
	
	
	complete: function () {
		
		var columns = this.columns,
			hasXData,
			categories,
			firstCol,
			type,
			options = this.options,
			series,
			data,
			name,
			i,
			j;
			
		
		if (options.complete) {
			
			
			if (columns.length > 1) {
				firstCol = columns.shift();
				if (this.headerRow === 0) {
					firstCol.shift(); 
				}
				
				
				hasXData = firstCol.isNumeric || firstCol.isDatetime;
				if (!hasXData) { 
					categories = firstCol;
				}
				
				if (firstCol.isDatetime) {
					type = 'datetime';
				}
			}
			
			
			series = [];
			for (i = 0; i < columns.length; i++) {
				if (this.headerRow === 0) {
					name = columns[i].shift();
				}
				data = [];
				for (j = 0; j < columns[i].length; j++) {
					data[j] = columns[i][j] !== undefined ?
						(hasXData ?
							[firstCol[j], columns[i][j]] :
							columns[i][j]
						) :
						null;
				}
				series[i] = {
					name: name,
					data: data
				};
			}
			
			
			options.complete({
				xAxis: {
					categories: categories,
					type: type
				},
				series: series
			});
		}
	}
	});
	
	
	Highcharts.Data = Data;
	Highcharts.data = function (options) {
		return new Data(options);
	};
}(Highcharts));
