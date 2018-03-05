$(document).ready(function(){ 

	
	$(".navigation").load("nav.html", function() {
		getUserGroups()
	});
	
	function getUserGroups() {
		$.ajax({
			url : "http://localhost:8080/app/logged-user",
			type : 'GET',
			dataType : 'json',
			success : function(data) {
				if (jQuery.inArray("Zaposleni", data) == -1) {
					$(".navigation #zapNav").hide();	
					$(".navigation #sekNav").hide();
				}
				if (jQuery.inArray("Zaposleni", data) !== -1) {
					
					$(".navigation #taskNav").hide();
					$(".navigation #sekNav").hide();
					
				}
				if(jQuery.inArray("DirektoriSektoraPopravke", data) !== -1 || jQuery.inArray("DirektoriSektoraNabavke", data) !== -1 || jQuery.inArray("DirektoriSektoraOdobrenje", data) !== -1) {
					$(".navigation #taskNav").hide();
					$(".navigation #zapNav").hide();
					$(".navigation #sekNav").show();
				}
				
				$(".nav #login").hide();
			},
			error : function(data) {
				$(".nav #zapNav").hide();
				$(".nav #taskNav").hide();
				$(".nav #taskoviSektora").hide();
				$(".nav #logOut").hide();
				console.log(data);
			}
		});
	}
	
});