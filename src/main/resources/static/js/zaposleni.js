$(document).ready(function(){ 
	
	var dynamicDiv = $(".dynamicDiv");
	var sendRequest = $("#sendReq");
	var rqDrop = $("#rqDrop");
	var baseUrl = "http://localhost:8080/app/";
	var taskId = 0;
	var tipZahteva = "";
	var r = $("#reqType option:selected").val();
	$('.expiredReq').hide(); 
	
	var descriptionModal = $("#descriptionModal");
	var reqTypeModal = $("#reqTypeModal");
	
	$("#nZah").on("click", function() {
		$('.active').removeClass('active');
		$(this).closest("li").addClass("active");
		$('.zahtev').show();
		$('.expiredReq').hide();
	});
	
	$("#iZah").on("click", function() {
		$('.active').removeClass('active');
		$(this).closest("li").addClass("active");
		$('.zahtev').hide();
		$('.expiredReq').show();
	});
	
	function getUserTasks() {
		$.ajax({
			url: baseUrl + 'user-tasks',
			type: 'GET',
			dataType: 'json',
			success:  function(data) {
				console.log(data);
				for (i = 0; i < data.length; i++) { 
					populateTable(data[i]);
				}
		    },
		    error: function(data) {
		    	console.log(data);
		    }
		});
	}
	
	$("#userTasksBody").on("click", ".taskPreview", function() {
		requestPreview($(this).attr('id'));
	});
	
	function requestPreview(previewId) {
		$.ajax({
			url: baseUrl + 'request-preview/' + previewId,
			type: 'GET',
			dataType: 'json',
			success:  function(data) {
				reqTypeModal.val(data.tipZahteva);
				descriptionModal.val(data.komentar);
				console.log(descriptionModal);
		    },
		    error: function(data) {
		    	console.log(data);
		    }
		});
	}
	
	function populateTable(task) {
		$("#userTasksBody").append(
			'<tr class="clickable-row" id="'+ task.id +'">' +
		        '<td>' + task.id + '</td>' + 
		        '<td>' + task.name + '</td>' + 
		        '<td>' + 
		        		 '<button id="'+task.id+'" class="btn btn-sm btn-primary taskPreview" data-toggle="modal" style="display: inline;" data-target="#myModal">'
		        		 + 'Pregledaj zahtev' + '</button>' + '&nbsp;'+
		        		 '<button id="'+task.id+'" class="btn btn-sm btn-warning rejectNewEx" style="display: inline;">'
		        		 + 'Odij produzenje roka' + '</button>' + '&nbsp;' +
		        		 '<button id="'+task.id+'" class="btn btn-sm btn-success newEx" data-toggle="modal" style="display: inline;" data-target="#timeReq">'
		        		 + 'Produzi rok' + '</button>' +
		        '</td>' + 
		    '</tr>'  
	     )
	}
	
	
	
	function getUserGroups() {
		$.ajax({
			url: baseUrl + 'logged-user',
			type: 'GET',
			dataType: 'json',
			success:  function(data) {
				if(jQuery.inArray("Zaposleni", data) == -1) {
					window.location = "/welcome.html"
				}
		    },
		    error: function(data) {
		    	window.location = "/login";
		    	console.log(data);
		    }
		});
	}
	
	
	function odobriNoviRok(id, action, rok) {
		$.ajax({
			url: baseUrl + 'execute/' + id + '?odobrenoProduzenje=' + action + '&rok=' + rok,
			type: 'POST',
			dataType: 'json',
			success:  function(data) {
				  $("#"+selectedIdSec).closest('tr').remove();
				  alert(data);
		    },
		    error: function(data) {
		    	console.log(data);
		    }
		});
	}
	
	var selectedIdSec = 0;
	$(userTasksBody).on("click", "tr", function () {
	    $('.selected').removeClass('selected');
	    $(this).addClass("selected");
	    selectedIdSec = $(this).attr('id');
	});
	
	$("#rejectNewEx").on("click", function() {
		odobriNoviRok(selectedIdSec, false, "");
		$("#timeReq").modal("toggle");
	});
	
	
	$("#exReqMod").on("click", function() {
		var timeNum = $("#buttondropdown2").val();
		if (timeNum < 1) {
			alert("Rok mora ima broj veci od 0");
			return;
		}
		var rok = "PT"+timeNum+time2;
		if (time2 === "D") {
			rok = "P"+timeNum+time;
		}
		odobriNoviRok(selectedIdSec, true, rok);
	});
	
	
	
	
	function printReqTypeInDropDown(rt) {
		rqDrop.append(
				 '<option class="reqType" value='+rt+'>' + rt + '</option>'
		);
	}
	
	function getReqTypes() {
		$.ajax({
			url: 'http://localhost:8080/app/reqTypes',
			type: 'GET',
			dataType: 'json',
			success:  function(data) {
				for (i = 0; i < data.length; i++) { 
					printReqTypeInDropDown(data[i]);
				}
				console.log(data);
		    }
			});
	}

	
	$(sendRequest).on('click', function() {
		startNewProcess();
	});
	
	var time = "H";
	$("#min").on('click', function() {
		time = "M";
	});
	
	$("#h").on('click', function() {
		time = "H";
	});
	
	$("#days").on('click', function() {
		time = "D";
	});
	
	
	var time2 = "H";
	$("#min2").on('click', function() {
		time2 = "M";
	});
	
	$("#h2").on('click', function() {
		time2 = "H";
	});
	
	$("#days2").on('click', function() {
		time2 = "D";
	});
	
	
	function submitRequest(data) {
		var timeNum = $("#buttondropdown").val();
		taskId = data;
		var reqType = $('#rqDrop option:selected').val();
		var reqDescription = $("#reqDescription").val();
		var urgent = $('input[name=radios]:checked').val();
		var rok = "PT"+timeNum+time;
		if (time === "D") {
			rok = "P"+timeNum+time;
		}
		
		if (reqType === "" || reqDescription === "" || timeNum === "") {
			alert("Sva polja su obavezna");
			return;
		}
		
		if (timeNum < 1) {
			alert("Rok mora ima broj veci od 0");
			return;
		}
		
		if(taskId == 0) {
			alert("task id " + taskId);
			return;
		}
		$.ajax({
			url: baseUrl + "execute/" + taskId + "?komentar=" + reqDescription + "&tipZahteva=" + reqType + "&hitno=" + urgent + "&rokIzvrsavanja=" + rok,
			type: 'POST',
			contentType: "application/json; charset=utf-8",
			success:  function(data) {
					alert(data);
		    }
			});
	}

	function startNewProcess() {
		$.ajax({
			url: baseUrl + "start-process",
			type: 'POST',
			contentType: "application/json; charset=utf-8",
			success:  function(data) {
				submitRequest(data);
		    }
			});
		return taskId;
	}

	getUserTasks()
	getUserGroups();
	getReqTypes();
	
});