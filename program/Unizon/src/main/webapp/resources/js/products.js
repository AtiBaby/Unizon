$(document).on('ready',function(){
	$('.category_list a').each(function(i, e){
		var arrow = $("<div class='arrow'><i class='fa fa-arrow-down' aria-hidden='true'></i></div>");
		if($(this).next().prop('tagName') == 'UL')
			$(this).parent().prepend(arrow);
	});

	$(".category_list .arrow").on("click",function(){
		$(this).parent().children("ul").stop().toggle(400);
		$(this).toggleClass("rotated");
	});
	
	    $('.category_list > li').click(function() {
	        $('.category_list > li').css('background-color','#FFFFFF');
	        $(this).css('background-color','#DDD');
	        $(this).children('a').css('text-decoration','none');
	      });

});