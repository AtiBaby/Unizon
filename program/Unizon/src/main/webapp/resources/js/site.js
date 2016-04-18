$(document).on("ready", function() {
	$("#mobile_menu_button").on("click", function() {
		$("#menu_panel").toggleClass("opened");
	});
	
	$("#mobil_menu_log").on("click", function() {
		$("#menu_panel").toggleClass("opened");
	});
	
	$("#mobil_menu_reg").on("click", function() {
		$("#menu_panel").toggleClass("opened");
	});

	$(window).on("resize", function() {
		$("#menu_panel").removeClass("opened");
	});
	
	if($(".rslides1").length > 0){
		$(".rslides1").responsiveSlides({
			pager : true,
			timeout: 4000,
			speed:1000
		});
	}

	if($(".rslides2").length > 0){
		setTimeout(function(){
			$(".rslides2").responsiveSlides({
				pager : true,
				timeout: 4000,
				speed:1000
				
			});
		}, 2000);
	}
	$(window).on("scroll", function(){
		if($("#top_main_nav").offset().top > 0){
			$("#top_main_nav").addClass("scrolled");
		}
		else{
			$("#top_main_nav").removeClass("scrolled");
		}
	});
});