var Cellularsignal = function() {};
				
	Cellularsignal.prototype.enable = function(params, success, fail) {
		return cordova.exec( function(args) {
			success(args);
		}, function(args) {
			fail(args);
		}, 'Cellularsignal', 'enable', [params]);
	};
	
	Cellularsignal.prototype.disable = function(params, success, fail) {
		return cordova.exec( function(args) {
			success(args);
		}, function(args) {
			fail(args);
		}, 'Cellularsignal', 'disable', [params]);
	};	   
	
var Cellsignal = function() {};
	
	Cellsignal.prototype.enable = function(listener, func) {
		window.plugins.cellularsignal.enable({success:listener}, 
			function(returnVal) {if(typeof func == "function"){func(returnVal.returnVal);}}, // Success function
			function(error) {alert('Cellular Signal Enable Failed ' + error)}); // Failure function
	};
		
	Cellsignal.prototype.disable = function(func) {
		window.plugins.cellularsignal.disable({}, 
			function(returnVal) {if(typeof func == "function"){func(returnVal.returnVal);}}, // Success function
			function(error) {alert('Cellular Signal Disable Failed ' + error)}); // Failure function
	};
	

module.exports = Cellularsignal;
module.exports = Cellsignal;
