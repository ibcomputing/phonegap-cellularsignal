var cellularsignal = function() {};
				
	cellularsignal.prototype.enable = function(params, success, fail) {
	    alert('plugin enable called');
	    return cordova.exec(function (args) {
	        alert('plugin enable success');
	        //success(args);
		}, function(args) {
		    alert('plugin enable failure');
		    //fail(args);
		    console.log(args);
		}, 'IbcCellularSignal', 'enable', [params]);
	};
	
	cellularsignal.prototype.disable = function(params, success, fail) {
		return cordova.exec( function(args) {
			success(args);
		}, function(args) {
			fail(args);
		}, 'IbcCellularSignal', 'disable', [params]);
	};	   
	
var Cellsignal = function() {};
	
	Cellsignal.enable = function(listener, func) {
	    console.log(window.plugins.cellularsignal);
	    window.plugins.cellularsignal.enable({ success: listener },
			function(returnVal) {if(typeof func == "function"){func(returnVal.returnVal);}}, // Success function
			function (error) {
			    //alert('Cellular Signal Enable Failed ' + error)
			}); // Failure function
	};
		
	Cellsignal.disable = function(func) {
		window.plugins.cellularsignal.disable({}, 
			function(returnVal) {if(typeof func == "function"){func(returnVal.returnVal);}}, // Success function
			function (error) {
			    //alert('Cellular Signal Disable Failed ' + error)
			}); // Failure function
	};

	if (!window.plugins) {
	    window.plugins = {};
	}
	if (!window.plugins.cellularsignal) {
	    window.plugins.cellularsignal = new cellularsignal();
	}

	if (module.exports) {
	    module.exports = cellularsignal;
	    module.exports = Cellsignal;
	}
	
	
