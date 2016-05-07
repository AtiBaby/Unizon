$(document).on('ready',function(){
	    $('.category_list > li').click(function() {
	        $('.category_list > li').css('background-color','#FFFFFF');
	        $(this).css('background-color','#DDD');
	        $(this).children('a').css('text-decoration','none');
	      });

});