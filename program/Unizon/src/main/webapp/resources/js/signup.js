	$(document).on("ready", function() {
		
		$("#register_submit_btn").click(function(){
			
			var x = 0;
			var reg_email = new RegExp("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$");
			
			if($("#username").val() == "")
			{
				x = x + 1;
				document.getElementById("username").style.border = "1px solid #CC0000";
				$("#message_un").text("Enter username!");
			}
			else
			{
				document.getElementById("username").style.border = "1px solid #00CC00";
			}
			
			if($("#email").val() == "" || !reg_email.test($("#email").val()))
			{
				x = x + 1;
				document.getElementById("email").style.border = "1px solid #CC0000";
				$("#message_email").text("Enter a valid e-mail address!");
			}
			else
			{
				document.getElementById("email").style.border = "1px solid #00CC00";
			}
			
			if($("#password").val() == "" || $("#confirm_password").val() == "")
			{
				x = x + 1;
				document.getElementById("password").style.border = "1px solid #CC0000";
				document.getElementById("confirm_password").style.border = "1px solid #CC0000";
				$("#message_pw").text("Enter both passwords!");
			}
			else
			{
				if($("#password").val() === $("#confirm_password").val())
				{
					document.getElementById("password").style.border = "1px solid #00CC00";
					document.getElementById("confirm_password").style.border = "1px solid #00CC00";
					$("#message").hide();
				}
				else
				{
					x = x + 1;
					document.getElementById("password").style.border = "1px solid #CC0000";
					document.getElementById("confirm_password").style.border = "1px solid #CC0000";
					$("#message_pw").text("Passwords do not match!");
					return false;
				}
			}
			
			if(x == 0)
			{
				$("#reg_form").trigger("submit");
				showMessage("Registration succesful!");
			}
			
		});	
	});