/**
* Here's the mold file , a mold means a HTML struct that the widget really presented.
* yep, we build html in Javascript , that make it more clear and powerful.
*/
function (out) { 

	var zcls = this.getZclass(),
		uuid = this.uuid;

	out.push('<div><canvas ', 'id="',uuid , '" height="',
			this._height, '" width="',this._width,'">');

	out.push('</canvas></div>');

}