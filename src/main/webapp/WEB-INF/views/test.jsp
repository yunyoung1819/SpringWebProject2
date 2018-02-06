<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h2> Ajax Test Page </h2>
	
	<div>
		<div>
			REPLYER <input type='text' name='replyer' id='newReplyWriter'>
		</div>
		<div>
			REPLY TEXT <input type='text' name='replytext' id='newReplyText'>
		</div>
		<button id="replyAddBtn">ADD REPLY</button>
	</div>
	<ul id="replies">
	</ul>	
	<!-- jQuery 2.1.4 -->
	<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
	<script type="text/javascript">
	
	var bno = 2;
	
	function getAllList(){
		$.getJSON("/replies/all/" + bno, function(data){
			
			var str = "";
			console.log(data.length);
			
			$(data).each(function(){
				str += "<li data-rno='"+this.rno+"' class='replyLi'>"
				+ this.rno + ":" + this.replytext
				+ "</li>";
			});
			
			$("#replies").html(str);
		});
	}
	
	</script>
</body>
</html>