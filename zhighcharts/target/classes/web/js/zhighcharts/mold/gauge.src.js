
function (out) { 

	var zcls = this.getZclass(),
		uuid = this.uuid;

	out.push('<div><canvas ', 'id="',uuid , '" height="',
			this._height, '" width="',this._width,'">');

	out.push('</canvas></div>');

}