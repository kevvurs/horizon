$(document).ready(function (){
    $.ajax({                                      
      url: "/initmap",              
      type: "get",          
      dataType: "json",                
      beforeSend: function() {
          $('#dock').attr("class","loader");
      },
      success: function(multidatum) {
    	  $('#dock').attr("class","done");
    	  google.charts.load('upcoming', {'packages':['geochart']});
    	  google.charts.setOnLoadCallback(mapit(multidatum));
      },
      error: function(a, b, c) {
    	  alert("Error: " + c);
      } 
   });
});

function mapit(votes) {
	google.load('visualization', '1', {'packages': ['geochart']});
	google.setOnLoadCallback(drawVisualization);
	function drawVisualization() {
		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Country');
		data.addColumn('number', 'Value'); 
		data.addColumn({type:'string', role:'tooltip'});
		var input = new Array();
		var len = data.state.length;
		for (i = 0; i < len; i++) { 
			state = data.state[i];
			winner = countVotes(state.repvote, state.demvote);
			msg = mkTip(winner);
			data.addRows([[{v:state.code,f:state.name},winner,msg]]);
			ivalue[state.code] = (msg + '  will <span style="color:#444; font-weight:bold;">win</span> in Alaska.');
		}
		
		var options = {
				 backgroundColor: {fill:'#FFFFFF',stroke:'#FFFFFF' ,strokeWidth:0 },
				 colorAxis:  {minValue: 0, maxValue: 2,  colors: ['#000000','#FF3333','#3333FF']},
				 legend: 'none',	
				 backgroundColor: {fill:'#FFFFFF',stroke:'#FFFFFF' ,strokeWidth:0 },	
				 datalessRegionColor: '#f5f5f5',
				 displayMode: 'regions',
				 enableRegionInteractivity: 'true',
				 resolution: 'provinces',
				 sizeAxis: {minValue: 1, maxValue:1,minSize:10,  maxSize: 10},
				 region:'US',
				 keepAspectRatio: true,
				 width:600,
				 height:400,
				 tooltip: {textStyle: {color: '#444444'}, trigger:'hover'}	
		};
		
		var chart = new google.visualization.GeoChart(document.getElementById('visualization')); 
		google.visualization.events.addListener(chart, 'select', function() {
			var selection = chart.getSelection();
			if (selection.length == 1) {
				var selectedRow = selection[0].row;
				var selectedRegion = data.getValue(selectedRow, 0);
				if(ivalue[selectedRegion] != '') { alert(ivalue[selectedRegion]); }}
		});
		
		chart.draw(data, options);
	}
	
	function countVotes(rep, dem) {
		if (rep > dem) {
			return 1;
		} else if (dem > rep) {
			return 2;
		} else {
			return 0;
		}
	}
	
	function mkTip(victor) {
		if (victor == 2) {
			return "Won by Clinton.";
		} else if (victor == 1) {
			return "Won by Trump."
		} else {
			return "Undecided."
		}
	}
}