/**
 * ontologysearch.js
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 * Email: tonghaozju@gmail.com
 */

// ================= INIT FUNCTIONS ============================================
$(function(){

	mappingsearch.getAllMapping();
	mappingsearch.getSyncProgress();
	
	tcminferVisualization.drawgraph();
	
	$('#sync-btn').live('click', function(){
		if (mappingsearch.syncStatus == 0){
			mappingsearch.syncMapping();
		} else if (mappingsearch.syncStatus == 1){
			if(mappingsearch.popoverStatus == 1){
				$('#sync-btn').popover('hide');
				mappingsearch.popoverStatus = 0;
			} else {
				mappingsearch.getSyncTpye = 1;
				mappingsearch.getSyncProgress();
			}
		}
	});
	
	$(".mapping-detail-btn").live("click", function(){
		var iconobject = $(this).children("i");
		if ( iconobject.attr("class") == "icon-chevron-up icon-black") {
			iconobject.attr("class", "icon-chevron-down icon-black");
			var mappingID = $(this).attr("data-target");
			var totalNum = $(this).parents('.accordion-toggle').children('.total-num').html();
			mappingsearch.currentOpen = mappingID.split("#")[1];
			
			if (totalNum == 0) {
				$('#' + mappingsearch.currentOpen + ' .sub-mapping-graph').remove();
				$('#' + mappingsearch.currentOpen + ' .sub-mapping-graphs').append(mappingsearch.toSubMappingHtml("",""));
			} else {
				var graphName = {"graphname" : $(this).parents('.accordion-toggle').children('.graph-name').html()};
				mappingsearch.getDetailMapping(graphName);
			}
		} else {
			iconobject.attr("class", "icon-chevron-up icon-black");
		}
	});
	
});

// ================= UTILITY FUNCTIONS =========================================
var mappingsearch = {
	
	urlSync : "../v0.9/syncmapping",
	urlGetSyncProcess: "../v0.9/getsyncprogress",
	urlGetAll : "../v0.9/mapping",
	urlSearch : "../v0.9/mapping/ks/",
	urlGetDetail : "../v0.9/mapping/ds/",
	currentOpen : "",// make the open callpase tab
	syncStatus : 0,
	popoverStatus : 0,
	getSyncTpye : 0, // 0 for get status, 1 for get detail
	
	spinopts : {
		lines: 7, // The number of lines to draw
  		length: 8, // The length of each line
  		width: 4, // The line thickness
  		radius: 5, // The radius of the inner circle
  		corners: 1, // Corner roundness (0..1)
  		rotate: 0, // The rotation offset
  		color: '#000', // #rgb or #rrggbb
  		speed: 1, // Rounds per second
  		trail: 60, // Afterglow percentage
  		shadow: false, // Whether to render a shadow
  		hwaccel: false, // Whether to use hardware acceleration
  		className: 'spinner', // The CSS class to assign to the spinner
  		zIndex: 2e9, // The z-index (defaults to 2000000000)
  		top: 'auto', // Top position relative to parent in px
  		left: 'auto' // Left position relative to parent in px
	},
	
	syncMapping: function(){
		commonjs.ajax("GET", this.urlSync, "", "", this.syncMappingCallBack, commonjs.showErrorTip);
		// window.open("index.html", "_self");
		mappingsearch.getSyncProgress();
	},
	
	getSyncProgress: function(){
		commonjs.ajax("GET", this.urlGetSyncProcess, "", "", this.getSyncProcessCallBack, commonjs.showErrorTip);
	},
	
	getAllMapping : function() {
		commonjs.ajax("GET", this.urlGetAll, "", "", this.displayMapping, commonjs.showErrorTip);
	},
	
	searchMapping : function(keywordJson) {
		commonjs.ajax("POST", this.urlSearch, keywordJson, "", this.displayMapping, commonjs.showErrorTip);
	},
	
	getDetailMapping : function(graphNameJson) {
		commonjs.ajax("POST", this.urlGetDetail, graphNameJson, "", this.displayDetail, commonjs.showErrorTip);
		$('#' + mappingsearch.currentOpen + ' .spin-progress').spin(this.spinopts);
	},
	
	syncMappingCallBack : function(data, textStatus, jqXHR){
		data = commonjs.strToJson(data);
		// do nothing
	},
	
	getSyncProcessCallBack : function(data, textStatus, jqXHR){
		data = commonjs.strToJson(data);
		mappingsearch.syncStatus = data.status;
		if(data.status == 1){
			// $('#sync-btn').attr("disabled","disabled");
			$('#sync-btn').html('<i class="icon-repeat icon-white"></i>Updating...Click to get status');
			if(mappingsearch.getSyncTpye == 1){
				if(mappingsearch.popoverStatus == 0){
					$('#sync-btn').popover('destroy');
					$('#sync-btn').popover({
						title : 'Synchronizm Status',
						placement : 'left',
						html : true,
						trigger : 'manual',
						content : mappingsearch.syncDataToHtml(data),
					});
					
					$('#sync-btn').popover('show');
					mappingsearch.popoverStatus = 1;
				} 
				
			}
		} else if(data.status == 0){
			// do nothing : btn enable
		}
	},
	
	syncDataToHtml : function(data){
		var html = '<dl>' +
						'<dt>Status</dt>' + 
  						'<dd>' + 'Synchronizing' + '</dd>' +  
	  					'<dt>Passed Time</dt>' + 
  						'<dd>' + data.passedTime + '</dd>' + 
  						'<dt>Ontology Percent</dt>' + 
  						'<dd>' + data.ontologyPercent + '% (' + data.ontologyPercentF + ')' + '</dd>' + 
  						'<dt>Item Percent</dt>' + 
  						'<dd>' + data.itemPercent + '% (' + data.itemPercentF + ')' + '</dd>' + 
  						'<dt>Estimate Time</dt>' + 
  						'<dd>' + data.estimateTime + '</dd>' + 
					'</dl>';
		return html;
	},
	
	displayMapping : function(data, textStatus, jqXHR) {
		data = commonjs.strToJson(data);
		for(var i=0; i < data.length; i++) {
			var mappingRow = mappingsearch.toMappingHtml(mappingsearch.modifyPrefix(data[i].ontoName), data[i].totalNum, data[i].tripleNum, i+1);
			$('#mappingtable').append(mappingRow);
		}
	},
	
	modifyPrefix: function(name) {
		var resourceArray = name.split("/");
        return "http://www.biotcm.org/" + resourceArray[resourceArray.length-1];
//		return name;
	},
	
	displayDetail : function(data, textStatus, jqXHR) {
		$('#' + mappingsearch.currentOpen + ' .spin-progress').spin(false);
		data = commonjs.strToJson(data);
		$('#' + mappingsearch.currentOpen + ' .sub-mapping-graph').remove();
		for(var i=0; i < data.length; i++) {
			var subMappingRow = mappingsearch.toSubMappingHtml(data[i].ontoName, data[i].totalNum);
			$('#' + mappingsearch.currentOpen + ' .sub-mapping-graphs').append(subMappingRow);
		}
	},
	
	toMappingHtml : function(name, totalNum, tripleNum, i) {
		$('#sample-mapping .graph-name').html(name);
		$('#sample-mapping .triple-num').html(tripleNum);
		$('#sample-mapping .total-num').html(totalNum);
		$('#sample-mapping .mapping-detail-btn').attr("data-target", "#collapse" + i);
		$('#sample-mapping .accordion-body').attr("id", "collapse" + i);
		
		var html = '<div class="accordion-group">' + $('#sample-mapping').html() + '</div>';
		$('#sample-mapping .mapping-detail-btn').attr("data-target", "#collapse0");
		
		return html;
	},
	
	toSubMappingHtml : function(name, totalNum) {
		if(name && totalNum) {
			$('#' + this.currentOpen + ' .sample-mapping-graph .sub-graph-name').html(name);
			$('#' + this.currentOpen + ' .sample-mapping-graph .sub-total-num').html(totalNum);
		} else {
			$('#' + this.currentOpen + ' .sample-mapping-graph .sub-graph-name').html("(no result)");
			$('#' + this.currentOpen + ' .sample-mapping-graph .sub-total-num').html("(no result)");
		}
		
		var html = '<tr class="sub-mapping-graph">' + $('#' + this.currentOpen + ' .sample-mapping-graph').html() + '</tr>';
		
		return html;
	}
	
};


//==============        visualization         ==============================
var tcminferVisualization={
	vDivid : "visual-div",
	
	graphUri : "../v0.9/mappinggraph",
	//visualization style
	vstyle : {
		global:{
			backgroundColor: "#DDDDDD"
		},
		nodes:{
			shape: "ROUNDRECT",
			color: "#FF0000",
			tooltipText: "${node-name}"
		},
		edges:{
			tooltipText:"${label}",
			sytle:"SOLID",
			Opacity:0.7,
			color:"#011e59",
			width:1
		}
	},
	// initialization options
	voptions : {
		// where you have the Cytoscape Web SWF
		swfPath: "../lib/cytoscapeweb/swf/CytoscapeWeb",
		// where you have the Flash installer SWF
		flashInstallerPath: "../lib/cytoscapeweb/swf/playerProductInstall"
	},
	// init and draw
	drawgraph : function(){
		var htmlobj=$.ajax({url : this.graphUri, async : false});
		var graphml=htmlobj.responseText;
		var vis = new org.cytoscapeweb.Visualization( this.vDivid, this.voptions);
		vis.ready(function(){
			vis.addListener("click", "nodes", function(event) {
                handle_click(event);
            });
			function handle_click(event) {
                var target = event.target;
                tcminferVisualization.selectNode = event.target.data.label;
                tcminferVisualization.selectId = event.target.data.id;
                $("#vfitertext").val(tcminferVisualization.selectNode);
           }
		});
		vis.draw({ network: graphml, visualStyle: this.vstyle, nodeTooltipsEnabled: true,edgeTooltipsEnabled: true,edgesMerged:true});
		$("#vfilter").unbind("click");
		$("#vfilter").click(  function(){
			var selabel=$("#vfitertext").val();
			var nodes=vis.nodes();
			var seId="";
			for(var i=0;i<nodes.length;i++){
				if(nodes[i].data.label==selabel){
					seId=nodes[i].data.id;
					break;
				}
			}
			var neNodes=vis.firstNeighbors([seId]).neighbors;
			vis.filter("nodes",function(node) {
			    if(seId==""){
			    	return true;
			    }
			    else if(node.data.id==seId){
			    	return true;
			    }
			    else if(neNodes==undefined){
			    	return false;
			    }
			    else{
			    	for(var i=0;i<neNodes.length;i++){
			    		if(neNodes[i].data.id==node.data.id){
			    			return true;
			    		}
			    	}
			    	return false;
			    }
			    return true;
			});
		});
	}
}
