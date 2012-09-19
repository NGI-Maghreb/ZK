package org.ngi.zhighcharts;

import java.io.Serializable;

import org.zkoss.lang.Objects;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Div;
import org.zkoss.zul.event.ChartDataEvent;
import org.zkoss.zul.event.ChartDataListener;

public class ZGauge extends Div implements EventListener {
	/* member fields */
	protected  Log logger = Log.lookup(this.getClass());

	private String _options; // json string for gauge options
	private double _value; // to hold value apart from options
	
	public ZGauge() {
		super();
	}

	private void refreshValue(){
		smartUpdate("refresh", this._value);
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return _value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		if (this._value != value) {
			this._value = value;
			smartUpdate("value", this._value);
		}
	}

	public String getOptions() {
		return _options;
	}

	public void setOptions(String options) {
		if (!Objects.equals(_options, options)) {
			_options = options;
			options = options.replaceAll(" ", "");
			smartUpdate("options", _options);
		}
	}
	
	/**
	 * render properties at component creation time
	 * @see org.zkoss.zul.Div#renderProperties(org.zkoss.zk.ui.sys.ContentRenderer)
	 */
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer)
	throws java.io.IOException {
		super.renderProperties(renderer);

		render(renderer, "options", _options);
		render(renderer, "value", _value);
	}

	/* (non-Javadoc)
	 * @see org.zkoss.zk.ui.event.EventListener#onEvent(org.zkoss.zk.ui.event.Event)
	 */
	@Override
	public void onEvent(Event arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * The default zclass is "z-ZGauge"
	 */
	public String getZclass() {
		return (this._zclass != null ? this._zclass : "z-ZGauge");
	}


}
