(function () {
	
	
	function merge_options(obj1,obj2){
	    var obj3 = {};
	    for (var attrname in obj1) { obj3[attrname] = obj1[attrname]; }
	    for (var attrname in obj2) { obj3[attrname] = obj2[attrname]; }
	    return obj3;
	}

zhighcharts.ZGauge = zk.$extends(zul.wgt.Div, {
	_options:null, 
	_value:0, 
	
	
	
	$define: {
		options: function() { 
			if(this.desktop && this.gauge) {
				
				var options;
				try {
					options = jq.evalJSON(this._options)
				} catch (err) {
					console.error('jq.evalJSON(%s) failed reason: %s',this._options, err);
				}
				if (options) {
					this.options = merge_options(this.options, options);
					this.gauge.setOptions(this.options);
					this.gauge.draw();
				}
			}
		},
		value: function() { 
			if(this.desktop && this.gauge) {
				
				this.options.value = this._value;
				this.gauge.setOptions(this.options);
				this.gauge.draw();
			}
		}
	},
	
	bind_: function () {
		
		this.$supers(zhighcharts.ZGauge,'bind_', arguments);
		try {
			this.options = jq.evalJSON(this._options);
		} catch (err) {
			console.error('bind component: jq.evalJSON(%s) failed reason: %s',this._options, err);
		}
		if (!this.options)
			this.options = new Object();
		this.options.value = this._value;
		
		this.gauge = new Gauge(this.$n(), this.options);
	},
	
	unbind_: function () {
	
		this.$supers(zhighcharts.ZGauge,'unbind_', arguments);
	},
	
	getZclass: function () {
		return this._zclass != null ? this._zclass: "z-ZGauge";
	}
});
})();