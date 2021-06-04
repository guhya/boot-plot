<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
	<div class="col mb-4">
		<button type="button" onclick="doAdd()" class="btn btn-primary">Write</button>
		<hr>
	</div>
</div>
<div class="row">
	<div class="col">
		<table id="myTable" class="table table-striped table-bordered" style="width:100%">
		    <thead>
		        <tr>
		            <th>Id</th>
		            <th>Latitude</th>
		            <th>Longitude</th>
		            <th>Action</th>
		        </tr>
		    </thead>
		</table>
	</div>
</div>	
<div class="row">
	<div class="col mb-4">
	</div>
</div>

<div id="modalForm" class="modal" aria-hidden="true" tabindex="-1" aria-labelledby="modalTitle">
	<div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="modalTitle">Form</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form id="inputForm" name="inputForm">
					<div class="form-row">
						<input type="text" style="display:none;" name="seq" id="seq" value="">
						<div class="col-md-8 mb-3">
							<label>Latitude</label> 
							<input type="text" class="form-control" name="lat" id="lat" value="" required>
						</div>
						<div class="col-md-4 mb-3">
							<label>Longitude</label> 
							<input type="text" class="form-control" name="lon" id="lon" value="" required>
						</div>
					</div>
					<input type="submit" name="submitHandler" style="display:none"/>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" type="submit" onclick="doSubmit()">Submit</button>
			</div>
		</div>
	</div>
</div>

<script>
	var resetForm = function(){
		document.inputForm.reset();
		$("#inputForm .invalid-feedback").remove();
		$("#inputForm .is-invalid").removeClass("is-invalid");
	};

	var doEdit = function(id){
		resetForm();
		$.ajax({
	        dataType : "JSON",
	        method : "GET",
	        contentType : "application/json",
	        url : "/v1/scooters/get/"+id,
	        success : function(res){
	        	$.each(res.data, function(key, val) {
	        		if($("#"+key).prop("type") != "file"){
	        			$("#"+key).val(val);
	        		}
        	    });
	    		$("#modalForm").modal("show");
	        }
		});
	};

	var doAdd = function(){
		resetForm();
		$("#modalForm").modal("show");
	};
	
	
	var doDelete = function(seq){		
		$.ajax({
	        dataType : "JSON",
	        method : "POST",
	        contentType : "application/json",
	        url : "/v1/scooters/delete",
	        data : JSON.stringify({
	        	"seq" : seq
	        }),
	        success : function(res){
	        	dt.draw(false);
	        }
		});
	};
	
	var doSubmit = function(){
		var frm = document.inputForm;
		if (!frm.checkValidity()) {
			frm.submitHandler.click();
			return;
		}
		
		doSave().then(function(res){
			$("#modalForm").modal("hide");
			dt.draw(false);
		});
	};

	var ewdebug = {};
	var doSave = function(){
		$("#inputForm .invalid-feedback").remove();
		$("#inputForm .is-invalid").removeClass("is-invalid");
		
		var data = {};
		var arr = $("#inputForm").serializeArray();
		for(el in arr){
			if(arr[el].name == "seq" && arr[el].value == "") continue;
			data[arr[el].name] = arr[el].value;
		}
		
		var url = "";
		if(data.seq != undefined)
			url = "/v1/scooters/update";
		else
			url = "/v1/scooters/create";
		
		return $.ajax({
	        dataType : "JSON",
	        method : "POST",
	        contentType : "application/json",
	        url : url,
	        data : JSON.stringify(data),
	        success : function(res){
	        	console.log("Saving data success");
	        },
	        error : function(res){
		        ewdebug = res;
		        if(res.status == 400){
			        var data = ewdebug.responseJSON.data;
					for(key in data){
						if (data.hasOwnProperty(key)){
							var $el = $("#inputForm").find("#"+key);
							$el.addClass("is-invalid");
							$el.after("<div class='invalid-feedback'>"+data[key]+"</div>");
						}
					}
			    }
		    }
		});
	};
	
	var dt;
	var dp = new DOMParser();
	var cols = [
		{"data" : "seq", "width": "10%"},
		{"data" : "lat", "width": "25%"},
		{"data" : "lon", "width": "25%"},
		{
			"className" : "text-center",
			"width": "30%",
			"render" : function (data, type, row, meta) {
				var pd  = "";
					pd += "<div class=\"row\"><div class=\"col\">Are you sure you want to delete ?<hr></div></div></div>";
					pd += "<div class=\"row\"><div class=\"col text-right\">";
					pd += "<a onclick=\"doDelete("+row.seq+")\" class=\"btn btn-outline-danger btn-sm\">Yes</a>";
				    pd += "&nbsp;";
				    pd += "<a onclick=\"\" class=\"btn btn-outline-secondary btn-sm\">No</a>";
					pd += "</div></div></div>";

				var el  = "";
					el += "<a onclick='doEdit("+row.seq+")' class='btn btn-secondary'>Edit</a>";
				    el += "&nbsp;";
				    el += "<a tabindex='0' title='Delete' data-toggle='popover' data-placement='left' data-content='"+pd+"' class='btn btn-danger'>Delete</a>";
				
				return el;
			},
			"orderable" : false
		},
	];
	
	$(document).ready(function(){
		dt = $("#myTable").DataTable({
			dom : 	"<'row'<'col-sm-12 col-md-6'l><'col-sm-12 col-md-6'f>>" +
					"<'row'<'col-sm-12'tr>>" +
					"<'row'<'col-sm-12 col-md-6'i><'mt-2 col-sm-12 col-md-6'p>>",
			pageLength 	: 10,
			lengthMenu 	: [5, 10, 20, 50, 100],
			bProcessing : true,
			bServerSide : true,
			order : [[ 0, "desc" ]],
			sAjaxSource : "/v1/scooters/list",
			fnServerData : function(sSource, aoData, fnCallback, oSettings) {
				var start, size, keyword, sortColumn, sortOrder	
			    for (var i = 0; i < aoData.length; i++) {
			        if (aoData[i].name == "iDisplayStart") start = aoData[i].value;
			        else if (aoData[i].name == "iDisplayLength") size = aoData[i].value;
			        else if (aoData[i].name == "sSearch") keyword = aoData[i].value;
			        else if (aoData[i].name == "iSortCol_0") sortColumn = aoData[i].value;
			        else if (aoData[i].name == "sSortDir_0") sortOrder = aoData[i].value;
			    }
			    
				oSettings.jqXHR = $.ajax({
					dataType : "json",
					type : "GET",
					url : sSource,
					data : {
						pageSize : size,
						page : start / size + 1,
						condition : "all",
						keyword : keyword,
						sortColumn : cols[sortColumn].data,
						sortOrder : sortOrder.toUpperCase()
					},
					success : fnCallback,
					dataFilter : function(data){
						var json = JSON.parse(data);
						json.recordsTotal = json.meta.totalRecords;
						json.recordsFiltered = json.meta.totalRecords;
						return JSON.stringify(json);
					}
				})
			},
			columns : cols,
			language : {
	            	processing : "Loading..."
	            },				
			drawCallback : function(settings) { 
		        var response = settings.json;
				$("[data-toggle='popover']").popover({
					html : true,
					sanitize : false,
					trigger : "focus"
				});
		    },
		});
	});
</script>
