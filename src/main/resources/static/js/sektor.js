$(document).ready(function(){ 
	
	var allTasksBody = $("#allTasksBody");
	var userTasksBody = $("#userTasksBody");
	var baseUrl = "http://localhost:8080/app/";
	var descriptionModal = $("#descriptionModal");
	var reqTypeModal = $("#reqTypeModal");
	var rejectId = 0;
	$("#secIzvr").hide();
	var selectedIdSec = 0;
	
	$(userTasksBody).on("click", "tr", function () {
	    $('.selected').removeClass('selected');
	    $(this).addClass("selected");
	    selectedIdSec = $(this).attr('id');
	});
	
	$("#sekOdobrava").on("click", function() {
		$('.active').removeClass('active');
		$(this).closest("li").addClass("active");
		$(userTasksBody).empty();
		$(allTasksBody).empty();
		getUserTasks($(this).attr('id'));
		getAllTasks($(this).attr('id'));
		$("#secIzvr").hide();
		$("#secIzvr").hide();
		$(".odobrava").show();
		$(".hitno").hide();
		$(".overa").hide();
	});
	$("#sekBira").on("click", function() {
		$('.active').removeClass('active');
		$(this).closest("li").addClass("active");
		$(userTasksBody).empty();
		$(allTasksBody).empty();
		getUserTasks($(this).attr('id'));
		getAllTasks($(this).attr('id'));
		$("#secIzvr").hide();
		$(".odobrava").hide();
		$(".hitno").show();
		$(".overa").hide();
	});
	$("#odabirIzvrsilaca").on("click", function() {
		$('.active').removeClass('active');
		$(this).closest("li").addClass("active");
		$(userTasksBody).empty();
		$(allTasksBody).empty();
		getUserTasks($(this).attr('id'));
		getAllTasks($(this).attr('id'));
		$("#secIzvr").show();
		$(".odobrava").hide();
		$(".hitno").hide();
		$(".overa").hide();
	});
	$("#sekOvera").on("click", function() {
		$('.active').removeClass('active');
		$(this).closest("li").addClass("active");
		$(userTasksBody).empty();
		$(allTasksBody).empty();
		$("#secIzvr").hide();
		$(".odobrava").hide();
		$(".hitno").hide();
		getUserTasks($(this).attr('id'));
		getAllTasks($(this).attr('id'));
		$(".overa").show();
	});
	
	function getUserGroups() {
		$.ajax({
			url: baseUrl + 'logged-user',
			type: 'GET',
			dataType: 'json',
			success:  function(data) {
				console.log(data);
				var redirect = false
				for (var i = 0; i < data.length; i++) {
					if(data[i].slice(0, 9) !== "Direktori") {
						redirect = true;
					}else {
						redirect = false;
					}
				}
				if (redirect) {
					window.location = "/welcome.html";
				}
		    },
		    error: function(data) {
		    	console.log(data);
		    	window.location = "/login";
		    }
		});
	}
	
	function getUserTasks(type) {
		$.ajax({
			url: baseUrl + "user-tasks-gen/" + type,
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
	
	function getAllTasks(type) {
		$.ajax({
			url: baseUrl + "all-tasks-gen/" + type,
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
	
	$(".changeR").on("click", function() {
		var change = $('input[name=radios]:checked').val();
		if(change === "true") {
			$("#changeReq").prop('disabled', false);
			$("#reqDetails").prop('disabled', false);
		}else {
			$("#changeReq").prop('disabled', true);
			$("#reqDetails").prop('disabled', true);
		}
	});
	
	function append() {
		$.ajax({
			url: baseUrl + 'table-append',
			type: 'GET',
			dataType: 'json',
			success:  function(data) {
				populateTable(data);
		    },
		    error: function(data) {
		    	alert(data);
		    }
		});
	}
	
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
	
	$("#allTasksBody").on("click", ".claimTask", function() {
		claimTask($(this).attr('id'));
		$(this).closest('tr').remove();
		
	});
	
	$("#secOk").on("click", function() {
		if(selectedIdSec < 1) {
			alert("Morate selektovati red u tabeli");
			return;
		}
		requestPreview(selectedIdSec);
	});
	
	$("#unclaimTask").on("click", function() {
		if(selectedIdSec < 1) {
			alert("Morate selektovati red u tabeli");
			return;
		}
		unclaimTask(selectedIdSec);
		$("#"+selectedIdSec).closest('tr').remove();
	});
	
	var rejectReqId = 0;
	$(userTasksBody).on("click", ".rejectReq",function() {
		rejectReqId = $(this).attr("id");
	});
	
	
	function requestAction(action, id, obrazlozenje, obrazlozenjeIzmene, izmena, izmenjeno) {
		$.ajax({
			url: baseUrl + "execute/" + id + "?odobreno=" + action + "&obrazlozenjeIzmene=" + obrazlozenjeIzmene + "&izmenjeno=" + izmenjeno+ "&izmena=" + izmena + "&obrazlozenje=" + obrazlozenje + "&pogresan=" + false,
			type: 'POST',
			contentType: "application/json; charset=utf-8",
			success:  function(data) {
					alert(data);
		    }
		});
	}
	
	$("#rejectRequest").on("click", function() {
		var obrazlozenje = $("#reqRejection").val();
		if(selectedIdSec < 1 || obrazlozenje.length < 5) {
			alert("Obrazlozenje mora biti duze od 4 karaktera");
			return;
		}
		requestAction(false, selectedIdSec, obrazlozenje, "", false, "");
		$("#"+selectedIdSec).closest('tr').remove();
		$('#rejectModal').modal('toggle');
		$("#reqRejection").val("");
	});
	
	$("#secApprove").on("click", function() {
		if(selectedIdSec < 1) {
			alert("Morate seletkovati red u tabeli");
			return;
		}
		var changed = $('input[name=radios]:checked').val();
		var obrazlozenjeIzmene = $("#changeReq").val();
		var izmenjeno = $("#reqDetails").val();
		requestAction(true, selectedIdSec, "", obrazlozenjeIzmene, izmenjeno, changed);
		$("#"+selectedIdSec).closest('tr').remove();
		$('#secModal').modal('toggle');
		$("#changeReq").val("");
		$("#reqDetails").val("");
	});
	
	function requestPreview(previewId) {
		$.ajax({
			url: baseUrl + 'request-preview/' + previewId,
			type: 'GET',
			dataType: 'json',
			success:  function(data) {
				reqTypeModal.val(data.tipZahteva);
				descriptionModal.val(data.komentar);
				$("#reqDetails").val(data.komentar);
				console.log(descriptionModal);
		    },
		    error: function(data) {
		    	console.log(data);
		    }
		});
	}
	
	function fillExSelect(ex) {
		$("#selectmultiple").append(
			'<option value="'+ ex.id +'">' + ex.firstName + ' ' + ex.lastName + ' ' + '(' + ex.id + ')' + '</option>'		
		)
	}
	
	function exList() {
		$.ajax({
			url: baseUrl + 'ex-list',
			type: 'GET',
			dataType: 'json',
			success:  function(data) {
				$("#selectmultiple").empty();
				for (i = 0; i < data.length; i++) { 
					fillExSelect(data[i]);
				}
		    },
		    error: function(data) {
		    	console.log(data);
		    }
		});
	}
	
	$("#notValid").on("click", function() {
		if(selectedIdSec < 1) {
			alert("Morate seletkovati red u tabeli");
			return;
		}
		invalidReq(selectedIdSec);
		$("#"+selectedIdSec).closest('tr').remove();
	});
	
	function invalidReq(id) {
		$.ajax({
			url: baseUrl + "execute/" + id + "?odobreno=" + "&obrazlozenjeIzmene="+ "&izmenjeno=" +"&izmena=" +"&obrazlozenje=" + "&pogresan=" + true,
			type: 'POST',
			contentType: "application/json; charset=utf-8",
			success:  function(data) {
					alert(data);
		    },
		    error:function(data) {
		    	console.log(data);
		    }
		});
	}
	
	function sam(id, action) {
		$.ajax({
			url: baseUrl + "execute/" + id + "?izvrsavaSam=" + action,
			type: 'POST',
			contentType: "application/json; charset=utf-8",
			success:  function(data) {
				$("#"+id).closest('tr').remove();
				alert(data);
		    },
		    error:function(data) {
		    	console.log(data);
		    }
		});
	}
	
	function overiIzvestaj(id, overeno) {
		$.ajax({
			url: baseUrl + "execute/" + id + "?overeno=" + overeno,
			type: 'POST',
			contentType: "application/json; charset=utf-8",
			success:  function(data) {
				$("#"+id).closest('tr').remove();
				alert(data);
		    },
		    error:function(data) {
		    	console.log(data);
		    }
		});
	}
	
	$("#hitnoSam").on("click", function() {
		if(selectedIdSec < 1) {
			alert("Morate selektovati red u tabeli");
			return;
		}
		sam(selectedIdSec, true);
	});
	
	$("#proslediI").on("click", function() {
		if(selectedIdSec < 1) {
			alert("Morate selektovati red u tabeli");
			return;
		}
		sam(selectedIdSec, false);
	});
	
	$("#secIzvr").on("click", function() {
		exList();
	});
	
	$(".tasksContainer").on("click", ".taskPreview", function() {
		requestPreview($(this).attr('id'));
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
	
	
	$("#izvSel").on("click", function() {
		odabirI();
		$("#secModalIzvr").modal("toggle");
	});
	
	function odabirI() {
		if(selectedIdSec < 1) {
			alert("Morate selektovati red u tabeli");
			return;
		}
		var timeNum = $("#buttondropdown").val();
		if (timeNum < 1) {
			alert("Morate uneti broj za vreme");
			return;
		}
		var rok = "PT"+timeNum+time;
		if (time === "D") {
			rok = "P"+timeNum+time;
		}
		var selectedValues = $('#selectmultiple').val();
		if (selectedValues.length < 1) {
			alert("Morate odabrati minimun 1 izvrsioca");
			return;
		}
		var izvrsioci = "";
		for (var i = 0; i < selectedValues.length; i++) {
			izvrsioci += selectedValues[i] + ",";
		}
		izvrsioci = izvrsioci.slice(0, -1);
		$.ajax({
			url: baseUrl + "execute/" + selectedIdSec + "?izvrsioci=" + izvrsioci + "&rok=" + rok,
			type: 'POST',
			contentType: "application/json; charset=utf-8",
			success:  function(data) {
				$("#"+selectedIdSec).closest('tr').remove();
				alert(data);
		    },
		    error:function(data) {
		    	console.log(data);
		    }
		});
	}
	
	
	$(".odobrava").show();
	$(".hitno").hide();
	$(".overa").hide();
	getUserTasks("sekOdobrava");
	getAllTasks("sekOdobrava");
	getUserGroups();
	
});