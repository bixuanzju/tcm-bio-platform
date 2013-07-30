/**
 * bioinfer.js
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 * Email: tonghaozju@gmail.com
 */

// ================= INIT FUNCTIONS ============================================
$(function(){
	
	bioinfer.init();

	
	$("#bio-search").live('click', function(){
		var keyword = $('#bio-keyword').val();
		window.open("index.html?t=" + bioinfer.getType() +  "&kw=" + keyword + "&s=" + bioinfer.start + "&o=" + bioinfer.offset, "_self");
	});
	
	$('#bio-keyword').live('keypress', function(e){
		if(e.keyCode==13){
			var keyword = $('#bio-keyword').val();
			window.open("index.html?t=" + bioinfer.getType() + "&kw=" + keyword + "&s=" + bioinfer.start + "&o=" + bioinfer.offset, "_self");
		}
	});
	
});

// ================= UTILITY FUNCTIONS =========================================
var bioinfer = {
	urlDrugInfer : "../v0.9/bioinfer/searchdrug/kw=",
	urlGeneNameInfer : "../v0.9/bioinfer/searchGeneName/kw=",
	urlPAInfer : "../v0.9/bioinfer/searchPA/kw=",
	urlGOIDInfer : "../v0.9/bioinfer/searchGOID/kw=",
	start : 0,
	offset : 8,
	totalNum : 0,
	currPage : 0,
	spinopts : {
		lines: 13, // The number of lines to draw
  		length: 10, // The length of each line
  		width: 4, // The line thickness
  		radius: 10, // The radius of the inner circle
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
	
	init : function() {

		var url = $.url();
		var keyword = url.param("kw");
		var start = url.param("s");
		var offset = url.param("o");
		var type = url.param("t") == undefined ? url.param("t") : url.param("t").split('-');
		bioinfer.currPage = url.fsegment(1);
		
		if(keyword!=undefined && keyword!=""){
			$('#bio-keyword').val(keyword);
			if (type[1] == '1') {
				$('#DrugNameCheckbox').prop('checked', true);
				var url = bioinfer.getUrl(this.urlDrugInfer, keyword, start, offset);
				bioinfer.getDrugInfer(url);
				tcminferVisualization.displayDrugNameToDrugID.display(keyword, null);
			}
			else if (type[1] == '2') {
				$('#GeneNameCheckbox').prop('checked', true);
				var url = bioinfer.getUrl(this.urlGeneNameInfer, keyword, start, offset);
				bioinfer.getGeneNameInfer(url);
				tcminferVisualization.displayGeneNameToPA.display(keyword, null);
			}
			else if (type[1] == '3') {
				$('#PACheckbox').prop('checked', true);
				var url = bioinfer.getUrl(this.urlPAInfer, keyword, start, offset);
				bioinfer.getPAInfer(url);
				tcminferVisualization.displayPAToTargetName.display(keyword, null);
			}
			else if (type[1] == '4') {
				$('#GOIDCheckbox').prop('checked', true);
				var url = bioinfer.getUrl(this.urlGOIDInfer, keyword, start, offset);
				bioinfer.getGOIDInfer(url);
				tcminferVisualization.displayGOToPA.display(keyword, null);
			}
			
//			tcminferVisualization.displaytcm.display(keyword);
		}
		
	},
	
	getDrugInfer : function(url){
		commonjs.ajax("GET", url, "", "", this.showDrug, commonjs.showErrorTip);
		$('.spin-progress').spin(this.spinopts);
	},
	
	getGeneNameInfer : function(url){
		commonjs.ajax("GET", url, "", "", this.showGeneName, commonjs.showErrorTip);
		$('.spin-progress').spin(this.spinopts);
	},
	
	getPAInfer : function(url){
		commonjs.ajax("GET", url, "", "", this.showPA, commonjs.showErrorTip);
		$('.spin-progress').spin(this.spinopts);
	},
	
	getGOIDInfer : function(url){
		commonjs.ajax("GET", url, "", "", this.showGOID, commonjs.showErrorTip);
		$('.spin-progress').spin(this.spinopts);
	},
	
	getType : function() {
		var type = "";
		if($('#DrugNameCheckbox').prop('checked') == true){
			type = "-1";
		}
		if($('#GeneNameCheckbox').prop('checked') == true){
			type = "-2";
		}
		if($('#PACheckbox').prop('checked') == true){
			type = "-3";
		}
		if($('#GOIDCheckbox').prop('checked') == true){
			type = "-4";
		}
		return type;
	},
	
	showDrug : function(data, textStatus, jqXHR) {
		$('.spin-progress').spin(false);
		data = commonjs.strToJson(data);
		if(data.status==false){
			// TODO
		} else {
			$('#tb1-head').html("<tr><th>Drug Name</th><th>Drug ID</th><th>Disease ID</th>" +
					"<th>Disease Name</th><th>TCM Name</th></tr>");
			$('#tb2-head').html("<tr><th>Drug Name</th><th>Drug ID</th><th>Disease ID</th>" +
					"<th>Disease Name</th><th>TCM Name</th></tr>");
			bioinfer.disDetailTab(data);
		}
	},
	
	showGeneName : function(data, textStatus, jqXHR) {
		$('.spin-progress').spin(false);
		data = commonjs.strToJson(data);
		if(data.status==false){
			// TODO
		} else {
			$('#tb1-head').html("<tr><th>Gene Name</th><th>Protein Accession</th><th>Target Name</th>" +
					"<th>Drug Name</th><th>Disease Name</th><th>TCM Name</th></tr>");
			$('#tb2-head').html("<tr><th>Gene Name</th><th>Protein Accession</th><th>Target Name</th>" +
					"<th>Drug Name</th><th>Disease Name</th><th>TCM Name</th></tr>");
			bioinfer.disDetailTab(data);
		}
	},
	
	showPA : function(data, textStatus, jqXHR) {
		$('.spin-progress').spin(false);
		data = commonjs.strToJson(data);
		if(data.status==false){
			// TODO
		} else {
			$('#tb1-head').html("<tr><th>Protein Accession</th><th>Target Name</th>" +
					"<th>Drug Name</th><th>Disease Name</th><th>TCM Name</th></tr>");
			$('#tb2-head').html("<tr><th>Protein Accession</th><th>Target Name</th>" +
					"<th>Drug Name</th><th>Disease Name</th><th>TCM Name</th></tr>");
			bioinfer.disDetailTab(data);
		}
	},
	
	showGOID : function(data, textStatus, jqXHR) {
		$('.spin-progress').spin(false);
		data = commonjs.strToJson(data);
		if(data.status==false){
			// TODO
		} else {
			$('#tb1-head').html("<tr><th>GOID</th><th>Protein Accession</th><th>Target Name</th>" +
					"<th>Drug Name</th><th>Disease Name</th><th>TCM Name</th></tr>");
			$('#tb2-head').html("<tr><th>GOID</th><th>Protein Accession</th><th>Target Name</th>" +
					"<th>Drug Name</th><th>Disease Name</th><th>TCM Name</th></tr>");
			bioinfer.disDetailTab(data);
		}
	},
	
	disDetailTab : function(data){
		for(var i=0; i < data.bioInferData.length; i++) {
			
			var htmlRowTab2 = bioinfer.toHtmlRowTab2(data.bioInferData[i], bioinfer.getType());
			var htmlRowTab1 = bioinfer.toHtmlRowTab1(data.bioInferData[i], bioinfer.getType());
			$('#tab2-table').append(htmlRowTab2);
			$('#tab1-table').append(htmlRowTab1);
			
		}
		$('#total-or-fuzzytip').html("About " + data.totalNum + " results.");
		bioinfer.totalNum = data.totalNum;
		$('.pagination').pagination({
			items : bioinfer.totalNum/8,
			currentPage : bioinfer.currPage.split("-")[1],
			onPageClick : function(pageNumber){
				var keyword = $('#bio-keyword').val();
				window.open("index.html?t=" + bioinfer.getType() + "&kw=" + keyword + "&s=" + (pageNumber-1) * bioinfer.offset + "&o=" + bioinfer.offset + "#page-" + pageNumber, "_self");
			}
		});		
	},
	
	getUrl : function(prefix, keyword, start, offset){
		return prefix + keyword + "&s=" + start + "&o=" + offset; 
	},
	
	splitResource : function(resource){
		var resourceArray = resource.split("/");
		return resourceArray[resourceArray.length-1];
	},
	
	toHtmlRowTab1 : function(bioinferData, type){
		if (type == "-1") {
			$('#tab1-table-sample-row .bio-1').html(bioinfer.splitResource(bioinferData.drugName));
			$('#tab1-table-sample-row .bio-2').html(bioinfer.splitResource(bioinferData.drugID));
			$('#tab1-table-sample-row .bio-3').html(bioinfer.splitResource(bioinferData.diseaseID));
			$('#tab1-table-sample-row .bio-4').html(bioinfer.splitResource(bioinferData.diseaseName));
			$('#tab1-table-sample-row .bio-5').html(bioinfer.splitResource(bioinferData.tcmName));
		}
		else if (type == "-2") {
			$('#tab1-table-sample-row .bio-1').html(bioinfer.splitResource(bioinferData.geneName));
			$('#tab1-table-sample-row .bio-2').html(bioinfer.splitResource(bioinferData.proteinAcce));
			$('#tab1-table-sample-row .bio-3').html(bioinfer.splitResource(bioinferData.targetName));
			$('#tab1-table-sample-row .bio-4').html(bioinfer.splitResource(bioinferData.drugName));
			$('#tab1-table-sample-row .bio-5').html(bioinfer.splitResource(bioinferData.diseaseName));
			$('#tab1-table-sample-row .bio-6').html(bioinfer.splitResource(bioinferData.tcmName));
		}
		else if (type == "-3") {
			$('#tab1-table-sample-row .bio-1').html(bioinfer.splitResource(bioinferData.proteinAcce));
			$('#tab1-table-sample-row .bio-2').html(bioinfer.splitResource(bioinferData.targetName));
			$('#tab1-table-sample-row .bio-3').html(bioinfer.splitResource(bioinferData.drugName));
			$('#tab1-table-sample-row .bio-4').html(bioinfer.splitResource(bioinferData.diseaseName));
			$('#tab1-table-sample-row .bio-5').html(bioinfer.splitResource(bioinferData.tcmName));
		}
		else if (type == "-4") {
			$('#tab1-table-sample-row .bio-1').html(bioinfer.splitResource(bioinferData.goid));
			$('#tab1-table-sample-row .bio-2').html(bioinfer.splitResource(bioinferData.proteinAcce));
			$('#tab1-table-sample-row .bio-3').html(bioinfer.splitResource(bioinferData.targetName));
			$('#tab1-table-sample-row .bio-4').html(bioinfer.splitResource(bioinferData.drugName));
			$('#tab1-table-sample-row .bio-5').html(bioinfer.splitResource(bioinferData.diseaseName));
			$('#tab1-table-sample-row .bio-6').html(bioinfer.splitResource(bioinferData.tcmName));
		}
		
		return '<tr>' + $('#tab1-table-sample-row').html() + '</tr>';
	},
	
	toHtmlRowTab2 : function(bioinferData, type){
		if (type == "-1") {
			$('#tab2-table-sample-row .bio-1').html(bioinferData.drugName);
			$('#tab2-table-sample-row .bio-2').html(bioinferData.drugID);
			$('#tab2-table-sample-row .bio-3').html(bioinferData.diseaseID);
			$('#tab2-table-sample-row .bio-4').html(bioinferData.diseaseName);
			$('#tab2-table-sample-row .bio-5').html(bioinferData.tcmName);
		}
		else if (type == "-2") {
			$('#tab2-table-sample-row .bio-1').html(bioinferData.geneName);
			$('#tab2-table-sample-row .bio-2').html(bioinferData.proteinAcce);
			$('#tab2-table-sample-row .bio-3').html(bioinferData.targetName);
			$('#tab2-table-sample-row .bio-4').html(bioinferData.drugName);
			$('#tab2-table-sample-row .bio-5').html(bioinferData.diseaseName);
			$('#tab2-table-sample-row .bio-6').html(bioinferData.tcmName);
		}
		else if (type == "-3") {
			$('#tab2-table-sample-row .bio-1').html(bioinferData.proteinAcce);
			$('#tab2-table-sample-row .bio-2').html(bioinferData.targetName);
			$('#tab2-table-sample-row .bio-3').html(bioinferData.drugName);
			$('#tab2-table-sample-row .bio-4').html(bioinferData.diseaseName);
			$('#tab2-table-sample-row .bio-5').html(bioinferData.tcmName);
		}
		else if (type == "-4") {
			$('#tab2-table-sample-row .bio-1').html(bioinferData.goid);
			$('#tab2-table-sample-row .bio-2').html(bioinferData.proteinAcce);
			$('#tab2-table-sample-row .bio-3').html(bioinferData.targetName);
			$('#tab2-table-sample-row .bio-4').html(bioinferData.drugName);
			$('#tab2-table-sample-row .bio-5').html(bioinferData.diseaseName);
			$('#tab2-table-sample-row .bio-6').html(bioinferData.tcmName);
		}
		return '<tr>' + $('#tab2-table-sample-row').html() + '</tr>';
	}
		
};

//==============        visualization         ==============================
var tcminferVisualization={
	vstate : "",
	selectNode: "",
	selectId: "",
	vDivid : "visual-div",
	//visualization style
	vstyle : {
		global:{
			backgroundColor: "#DDDDDD"
		},
		nodes:{
			color:{
				discreteMapper: {
                    attrName: "node-type",
                    entries: [
                        { attrValue: "0", value: "#333333" },
                        { attrValue: "1", value: "#006600" },
                        { attrValue: "2", value: "#00FF00" },
                        { attrValue: "3", value: "#0000FF" },
                        { attrValue: "4", value: "#FFFF00" },
                        { attrValue: "5", value: "#00FFFF" },
                        { attrValue: "6", value: "#FF00FF" },
                        { attrValue: "7", value: "#FF0000" },
                    ]
                }
			},
			tooltipText: "${node-name}"
		},
		edges:{
			tooltipText:"${label}",
			sytle:"SOLID",
			Opacity:1,
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
	drawgraph : function(data){
		var vis = new org.cytoscapeweb.Visualization( this.vDivid, this.voptions);
		vis.ready(function(){
			vis.addListener("click", "nodes", function(event) {
                handle_click(event);
            });
			function handle_click(event) {
                var target = event.target;
                tcminferVisualization.selectNode = event.target.data.label;
                tcminferVisualization.selectId = event.target.data.id;
                $("#vselectnode").val(tcminferVisualization.selectNode);
           }
		});
		vis.draw({ network: data, visualStyle: this.vstyle, nodeTooltipsEnabled: true,edgeTooltipsEnabled: true});
	},
	clearSelect : function(){
		$("#vselectnode").val("");
		tcminferVisualization.selectId="";
		tcminferVisualization.selectNode="";
	},
	
	displayGOToPA :{ 
		word : "",
		prev : null,
		display : function(keyword, prev){
			this.word = keyword;
			tcminferVisualization.vstate = "drug";
			$(".status").html("GOID -> Protein Accession");
			var htmlobj=$.ajax({url:"../v0.9/bioinfer/GO2PA/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$("#vback").unbind("click");
			$("#vback").attr("disable",true);
			$("#vgo").unbind("click");
			$("#vgo").click( function(){
				if(tcminferVisualization.selectId==""){
				}
				else if(tcminferVisualization.selectId!="node#0"){
					tcminferVisualization.displayPAToTargetName.display(tcminferVisualization.selectNode, tcminferVisualization.displayGOToPA);
				}
			});
			
		}
	},
	
	displayDrugNameToDrugID :{ 
		word : "",
		prev : null,
		display : function(keyword, prev){
			this.word = keyword;
			tcminferVisualization.vstate = "drug";
			$(".status").html("Drug name -> Drug ID");
			var htmlobj=$.ajax({url:"../v0.9/bioinfer/drugName2drugID/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$("#vback").unbind("click");
			$("#vback").attr("disable",true);
			$("#vgo").unbind("click");
			$("#vgo").click( function(){
				if(tcminferVisualization.selectId==""){
				}
				else if(tcminferVisualization.selectId!="node#0"){
					tcminferVisualization.displayDrugIDToDisID.display(tcminferVisualization.selectNode, tcminferVisualization.displayDrugNameToDrugID);
				}
			});
			
		}
	},
	
	displayDrugIDToDisID :{
		word : "",
		prev : null,
		display : function(keyword, prev){
			this.word=keyword;
			this.prev = prev;
			tcminferVisualization.vstate="drugID";
			$(".status").html("Drug ID -> Disease ID");
			var htmlobj=$.ajax({url:"../v0.9/bioinfer/drugID2diseaseID/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$('#vback').removeAttr("disabled");
			$("#vback").unbind("click");
			$("#vback").click(  function(){
				tcminferVisualization.displayDrugIDToDisID.prev.display(tcminferVisualization.displayDrugIDToDisID.prev.word,
						tcminferVisualization.displayDrugIDToDisID.prev.prev);
			});
			$("#vgo").unbind("click");
			$("#vgo").click( function(){
				if(tcminferVisualization.selectId==""){
				}
				else if(tcminferVisualization.selectId!="node#0"){
					tcminferVisualization.displayDisIDToDisName.display(tcminferVisualization.selectNode, 
							tcminferVisualization.displayDrugIDToDisID);
				}
			});
		}
	},
	
	displayDisIDToDisName :{
		word : "",
		prev : null,
		display : function(keyword, prev){
			this.word=keyword;
			this.prev = prev;
			tcminferVisualization.vstate="diseaseid";
			$(".status").html("Disease ID -> Disease Name");
			var htmlobj=$.ajax({url:"../v0.9/bioinfer/disid2disname/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$("#vback").unbind("click");
			$("#vback").click(  function(){
				tcminferVisualization.displayDisIDToDisName.prev.display(tcminferVisualization.displayDisIDToDisName.prev.word, 
						tcminferVisualization.displayDisIDToDisName.prev.prev);
			});
			$("#vgo").unbind("click");
			$("#vgo").click( function(){
				if(tcminferVisualization.selectId==""){
				}
				else if(tcminferVisualization.selectId!="node#0"){
					tcminferVisualization.displayDisNameToTCM.display(tcminferVisualization.selectNode, 
							tcminferVisualization.displayDisIDToDisName);
				}
			});
		}
	},
	
	displayDisNameToTCM : {
		word : "",
		prev : null,
		display : function(keyword, prev){
			this.word=keyword;
			this.prev = prev;
			tcminferVisualization.vstate="tcm";
			$(".status").html("Disease name -> TCM");
			var htmlobj=$.ajax({url:"../v0.9/bioinfer/disname2tcm/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$("#vback").unbind("click");
			$("#vback").click(  function(){
				tcminferVisualization.displayDisNameToTCM.prev.display(tcminferVisualization.displayDisNameToTCM.prev.word,
						tcminferVisualization.displayDisNameToTCM.prev.prev);
			});
			$("#vgo").unbind("click");
			$("#vgo").attr('disable',true);
		}
	},
	
	displayGeneNameToPA :{ 
		word : "",
		prev : null,
		display : function(keyword){
			this.word = keyword;
			tcminferVisualization.vstate = "gene";
			$(".status").html("Gene name -> Protein Accession");
			var htmlobj=$.ajax({url:"../v0.9/bioinfer/geneName2PA/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$("#vback").unbind("click");
			$("#vback").attr("disable",true);
			$("#vgo").unbind("click");
			$("#vgo").click( function(){
				if(tcminferVisualization.selectId==""){
				}
				else if(tcminferVisualization.selectId!="node#0"){
					tcminferVisualization.displayPAToTargetName.display(tcminferVisualization.selectNode, 
							tcminferVisualization.displayGeneNameToPA);
				}
			});
			
		}
	},
	
	displayPAToTargetName :{
		word : "",
		prev : null,
		display : function(keyword, prev){
			this.word=keyword;
			this.prev = prev;
			tcminferVisualization.vstate="diseaseid";
			$(".status").html("Protein Accession -> Target name");
			var htmlobj=$.ajax({url:"../v0.9/bioinfer/PA2targetName/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$("#vback").unbind("click");
			if (prev == null) {
				$("#vback").attr("disable",true);
			}
			else {
				$("#vback").click(  function(){
					tcminferVisualization.displayPAToTargetName.prev.display(tcminferVisualization.displayPAToTargetName.prev.word,
							tcminferVisualization.displayPAToTargetName.prev.prev);
				});
			}
			$("#vgo").unbind("click");
			$("#vgo").click( function(){
				if(tcminferVisualization.selectId==""){
				}
				else if(tcminferVisualization.selectId!="node#0"){
					tcminferVisualization.displayTargetNameToDrugName.display(tcminferVisualization.selectNode, 
							tcminferVisualization.displayPAToTargetName);
				}
			});
		}
	},
	
	displayTargetNameToDrugName :{
		word : "",
		prev : null,
		display : function(keyword, prev){
			this.word=keyword;
			this.prev = prev;
			tcminferVisualization.vstate="diseaseid";
			$(".status").html("Target name -> Drug name");
			var htmlobj=$.ajax({url:"../v0.9/bioinfer/targetName2drugName/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$("#vback").unbind("click");
			$("#vback").click(  function(){
				tcminferVisualization.displayTargetNameToDrugName.prev.display(tcminferVisualization.displayTargetNameToDrugName.prev.word,
						tcminferVisualization.displayTargetNameToDrugName.prev.prev);
			});
			$("#vgo").unbind("click");
			$("#vgo").click( function(){
				if(tcminferVisualization.selectId==""){
				}
				else if(tcminferVisualization.selectId!="node#0"){
					tcminferVisualization.displayDrugNameToDisName.display(tcminferVisualization.selectNode, 
							tcminferVisualization.displayTargetNameToDrugName);
				}
			});
		}
	},
	
	displayDrugNameToDisName :{
		word : "",
		prev : null,
		display : function(keyword, prev){
			this.word=keyword;
			this.prev = prev
			tcminferVisualization.vstate="diseaseid";
			$(".status").html("Drug name -> Disease name");
			var htmlobj=$.ajax({url:"../v0.9/bioinfer/drugName2disName/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$("#vback").unbind("click");
			$("#vback").click(  function(){
				tcminferVisualization.displayDrugNameToDisName.prev.display(tcminferVisualization.displayDrugNameToDisName.prev.word,
						tcminferVisualization.displayDrugNameToDisName.prev.prev);
			});
			$("#vgo").unbind("click");
			$("#vgo").click( function(){
				if(tcminferVisualization.selectId==""){
				}
				else if(tcminferVisualization.selectId!="node#0"){
					tcminferVisualization.displayDisNameToTCM.display(tcminferVisualization.selectNode, 
							tcminferVisualization.displayDrugNameToDisName);
				}
			});
		}
	}
	
//	displayprotein : {
//		word : "",
//		display : function(keyword){
//			this.word=keyword;
//			tcminferVisualization.vstate="protein";
//			$(".status").html("Protein Accession -> Gene name");
//			var htmlobj=$.ajax({url:"../v0.9/tcminfer/protein2geneid/kw="+keyword+"&s=-1&o=0",async:false});
//			tcminferVisualization.drawgraph(htmlobj.responseText);
//			tcminferVisualization.clearSelect();
//			$("#vback").unbind("click");
//			$("#vback").click(  function(){
//				tcminferVisualization.displaytargetid.display(tcminferVisualization.displaytargetid.word);
//			});
//			$('#vgo').removeAttr("disabled");
//			$("#vgo").unbind("click");
//			$("#vgo").click(  function(){
//				if(tcminferVisualization.selectId==""){
//				}
//				else if(tcminferVisualization.selectId!="node#0"){
//					tcminferVisualization.displaygeneid.display(tcminferVisualization.selectNode);
//				}
//			});
//		}
//	},
//	displaygeneid : {
//		word : "",
//		display : function(keyword){
//			this.word=keyword;
//			tcminferVisualization.vstate="geneid";
//			$(".status").html("Gene name -> Gene product");
//			var htmlobj=$.ajax({url:"../v0.9/tcminfer/geneid2geneprod/kw="+encodeURIComponent(keyword)+"&s=-1&o=0",async:false});
//			tcminferVisualization.drawgraph(htmlobj.responseText);
//			tcminferVisualization.clearSelect();
//			$("#vback").unbind("click");
//			$("#vback").click(  function(){
//				tcminferVisualization.displayprotein.display(tcminferVisualization.displayprotein.word);
//			});
//			$("#vgo").unbind("click");
//			$("#vgo").attr('disable',true);
//		}
//	}
};