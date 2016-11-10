var exec = require('cordova/exec');

function LabelPrinter() {
	console.log("labelprinter.js: is created");
}

/**
 * 
 * @param options.mac				Printer mac address.
 * @param options.data				Data to print.
 * @param callback					Success callback function.
 * @param fallback					Error callback function.
 */

LabelPrinter.prototype.print = function (callback, fallback, options) {
	var service = "LabelPrinterPlugin";
	var action = "print";
	var argArray = [options];

	function win(winParam) {
		if (typeof callback === "function") {
			callback(winParam);
		}
	}

	function fail(error) {
		if (typeof fallback === "function") {
			fallback(error);
		}
	}
	
	exec(win, fail, service, action, argArray);
	
};

module.exports = new LabelPrinter();