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
			}
			else if (type[1] == '2') {
				$('#GeneNameCheckbox').prop('checked', true);
				var url = bioinfer.getUrl(this.urlGeneNameInfer, keyword, start, offset);
				bioinfer.getGeneNameInfer(url);
			}
			else if (type[1] == '3') {
				$('#PACheckbox').prop('checked', true);
				var url = bioinfer.getUrl(this.urlPAInfer, keyword, start, offset);
				bioinfer.getPAInfer(url);
			}
			else if (type[1] == '4') {
				$('#GOIDCheckbox').prop('checked', true);
				var url = bioinfer.getUrl(this.urlGOIDInfer, keyword, start, offset);
				bioinfer.getGOIDInfer(url);
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
			$('#tab1-table-sample-row .bio-1').html(bioinfer.splitResource(bioinferData.GOID));
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
			$('#tab2-table-sample-row .bio-1').html(bioinferData.tcmName);
			$('#tab2-table-sample-row .bio-2').html(bioinferData.diseaseName);
			$('#tab2-table-sample-row .bio-3').html(bioinferData.diseaseID);
			$('#tab2-table-sample-row .bio-4').html(bioinferData.drugID);
			$('#tab2-table-sample-row .bio-5').html(bioinferData.drugName);
		}
		else if (type == "-2") {
			console.log(bioinferData);
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
			$('#tab2-table-sample-row .bio-1').html(bioinferData.GOID);
			$('#tab2-table-sample-row .bio-2').html(bioinferData.proteinAcce);
			$('#tab2-table-sample-row .bio-3').html(bioinferData.targetName);
			$('#tab2-table-sample-row .bio-4').html(bioinferData.drugName);
			$('#tab2-table-sample-row .bio-5').html(bioinferData.diseaseName);
			$('#tab2-table-sample-row .bio-6').html(bioinferData.tcmName);
		}
		return '<tr>' + $('#tab2-table-sample-row').html() + '</tr>';
	}
		
};