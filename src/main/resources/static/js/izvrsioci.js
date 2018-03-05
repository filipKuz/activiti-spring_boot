$(document).ready(function() {

		function efEx() {
				$.ajax({
					url : 'http://localhost:8080/app/ex-eff',
					type : 'GET',
					dataType : 'json',
					success : function(data) {
						for (var i = 0; i < data.length; i++) {
							populateTable(data[i]);
						}
					},
					error : function(data) {
						console.log(data);
					}
				});
			}

			function populateTable(data) {

				$("#exBody").append(
						'<tr class="clickable-row">' + '<td>' + data.izvrsilac
								+ '</td>' + '<td>' + data.brUspenihZadataka
								+ '</td>' + '<td>' + data.brNeuspenihZadataka
								+ '</td>' + '</tr>')

			}
			efEx();
});