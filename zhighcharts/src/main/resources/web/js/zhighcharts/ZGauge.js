(function () {
	
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
/**
 * 
 * 
 */
zhighcharts.ZGauge = zk.$extends(zul.wgt.Div, {
	_options:null, // gauge options (json string)
	_value:0, // gauge value
	
	/**
	 * member fields, 
	 */
	
	$define: {
		options: function() { // this function will be called after setOptions() .
			if(this.desktop && this.gauge) {
				// updated UI here.
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
		value: function() { // this function will be called after setValue() .
			if(this.desktop && this.gauge) {
				// updated UI here.
				this.options.value = this._value;
				this.gauge.setOptions(this.options);
				this.gauge.draw();
			}
		}
	},
	
	bind_: function () {
		/**
		 * For widget lifecycle , the super bind_ should be called as FIRST
		 * STATEMENT in the function. DONT'T forget to call supers in bind_ , or
		 * you will get error.
		 */
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