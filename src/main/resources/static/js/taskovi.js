$(document).ready(function(){ 
	
	var allTasksBody = $("#allTasksBody");
	var userTasksBody = $("#userTasksBody");
	var baseUrl = "http://localhost:8080/app/";
	var descriptionModal = $("#descriptionModal");
	var reqTypeModal = $("#reqTypeModal");
	
	var rejectId = 0;
	
	function getUserGroups() {
		$.ajax({
			url: baseUrl + 'logged-user',
			type: 'GET',
			dataType: 'json',
			success:  function(data) {
				console.log(data);
				if(jQuery.inArray("Zaposleni", data) !== -1 || jQuery.inArray("DirektoriSektoraPopravke", data) !== -1 || jQuery.inArray("DirektoriSektoraNabavke", data) !== -1 || jQuery.inArray("DirektoriSektoraOdobrenje", data) !== -1) {
					window.location = "/welcome.html"
				}
				$("#exec").hide();
				if(jQuery.inArray("Izvrsioci", data) !== -1) {
					$(".e").hide();
					$("#exec").show();
				}
		    },
		    error: function(data) {
		    	console.log(data);
		    	window.location = "/login";
		    }
		});
	}
	
	function getUserTasks() {
		$.ajax({
			url: baseUrl + 'user-tasks',
			type: 'GET',
			dataType: 'json',
			success:  function(data) {
				for (i = 0; i < data.length; i++) { 
					populateTable(data[i]);
				}
		    },
		    error: function(data) {
		    	console.log(data);
		    }
		});
	}
	
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
	
	function getAllTasks() {
		$.ajax({
			url: baseUrl + 'all-tasks',
			type: 'GET',
			dataType: 'json',
			success:  function(data) {
				for (i = 0; i < data.length; i++) { 
					populateTableAllTasks(data[i]);
				}
		    },
		    error: function(data) {
		    	console.log(data);
		    }
		});
	}
	
	function populateTable(task) {
		userTasksBody.append(
			'<tr class="clickable-row" id="'+ task.id +'">' +
		        '<td>' + task.id + '</td>' + 
		        '<td>' + task.name + '</td>' + 
		        '<td>' + 
		        		 '<button id="'+task.id+'" class="btn btn-primary taskPreview" data-toggle="modal" style="display: inline;" data-target="#myModal">'
		        		 + 'Pregledaj zahtev' + '</button>' +
		        '</td>' + 
		    '</tr>'  
	     )
	}
	
	
	$("#secApproveI").on("click", function() {
		if(selectedIdSec < 1) {
			alert("Morate seletkovati red u tabeli");
			return;
		}
	});
	
	$(".approveReq").on("click", function() {
		if(selectedIdSec < 1) {
			alert("Morate seletkovati red u tabeli");
			return;
		}
		requestAction(true, selectedIdSec, "");
		$("#"+selectedIdSec).closest('tr').remove();
	});
	
	var selectedIdSec = 0;
	$(userTasksBody).on("click", "tr", function () {
	    $('.selected').removeClass('selected');
	    $(this).addClass("selected");
	    selectedIdSec = $(this).attr('id');
	});
	
	
	$("#rejectRequest").on("click", function() {
		if(selectedIdSec < 1) {
			alert("Morate seletkovati red u tabeli");
			return;
		}
		var obrazlozenje = $("#reqRejection").val();
		requestAction(false, selectedIdSec, obrazlozenje);
		$("#"+selectedIdSec).closest('tr').remove();
		 $('#rejectModal').modal('toggle');
	});
	
	$("#notValid").on("click", function() {
		if(selectedIdSec < 1) {
			alert("Morate seletkovati red u tabeli");
			return;
		}
		invalidReq(selectedIdSec);
		$("#"+selectedIdSec).closest('tr').remove();
	});
	
	function requestAction(action, id, obrazlozenje) {
		
		$.ajax({
			url: baseUrl + "execute/" + id + "?odobreno=" + action + "&obrazlozenje=" + obrazlozenje + "&pogresan=",
			type: 'POST',
			contentType: "application/json; charset=utf-8",
			success:  function(data) {
					alert(data);
		    },
		    error: function(data) {
		    	console.log(data);
		    }
		});
	}
	
	function invalidReq(id) {
		$.ajax({
			url: baseUrl + "execute/" + id + "?odobreno=" + "&obrazlozenje=" + "&pogresan=" + true,
			type: 'POST',
			contentType: "application/json; charset=utf-8",
			success:  function(data) {
					alert(data);
		    },
		    error: function(data) {
		    	console.log(data);
		    }
		});
	}
	
	$("#exec").on("click", function() {
		if(selectedIdSec < 1) {
			alert("Morate seletkovati red u tabeli");
			return;
		}
		ex();
	});
	
	function ex() {
		$.ajax({
			url: baseUrl + "execute/" + selectedIdSec + "?izvrseno=" + true,
			type: 'POST',
			contentType: "application/json; charset=utf-8",
			success:  function(data) {
				$("#"+selectedIdSec).closest('tr').remove();
				console.log(data);
		    },
		    error: function(data) {
		    	console.log(data);
		    }
		});
	}
	
	
	function populateTableAllTasks(task) {
		allTasksBody.append(
			'<tr class="clickable-row" id="'+ task.id +'">' +
		        '<td>' + task.id + '</td>' + 
		        '<td>' + task.name + '</td>' + 
		        '<td>' + '<button id="'+task.id+'" class="btn btn-primary claimTask">' + 'Preuzmi task' + '</button>' + '&nbsp;' +
		        '<button id="'+task.id+'" class="btn btn-primary taskPreview" data-toggle="modal" style="display: inline;" data-target="#myModal">' 
		        + 'Pregledaj zahtev' + '</button>' +  '</td>' + 
		    '</tr>'  
	     )
	}
	

	$("#allTasksBody").on("click", ".claimTask", function() {
		claimTask($(this).attr('id'));
		$(this).closest('tr').remove();
		
	});
	
	$(".tasksContainer").on("click", ".taskPreview", function() {
		requestPreview($(this).attr('id'));
	});
	
	function claimTask(taskId) {
		$.ajax({
			url: baseUrl + 'claim/' + taskId,
			type: 'GET',
			success:  function(data) {
				populateTable(data);
		    },
		    error: function(data) {
		    	alert(data);
		    }
		});
	}
	
	function unclaimTask(taskId) {
		$.ajax({
			url: baseUrl + 'unclaim/' + taskId,
			type: 'GET',
			success:  function(data) {
				populateTableAllTasks(data);
		    },
		    error: function(data) {
		    	alert(data);
		    }
		});
	}
	$("#unclaimTask").on("click", function() {
		if(selectedIdSec < 1) {
			alert("Morate selektovati red u tabeli");
			return;
		}
		unclaimTask(selectedIdSec);
		$("#"+selectedIdSec).closest('tr').remove();
	});
	
	
	getUserGroups();
	getUserTasks();
	getAllTasks();

});