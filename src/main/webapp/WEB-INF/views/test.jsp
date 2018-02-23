<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style>
#modDiv {
	width: 300px;
	height: 100px;
	background-color: gray;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -50px;
	margin-left: -150px;
	padding: 10px;
	z-index: 1000;
}

.pagination {
  width: 100%;
}

.pagination li{
  list-style: none;
  float: left; 
  padding: 3px; 
  border: 1px solid blue;
  margin:3px;  
}

.pagination li a{
  margin: 3px;
  text-decoration: none;  
}
</style>
</head>
<body>

	<div id='modDiv' style="display: none;">
	<!-- display:none를 적용하면 화면에서 보이지 않게 된다 -->
		<div class='modal-title'></div>
		<div>
			<input type='text' id='replytext'>
		</div>
		<div>
			<button type="button" id="replyModBtn">Modify</button>
			<button type="button" id="replyDelBtn">DELETE</button>
		</div>
	</div>

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
	
	<!-- 댓글 페이지를 위한 <ul> 처리 -->
	<ul class="pagination">
	</ul>
	
	<!-- jQuery 2.1.4 -->
	<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
	
	<script type="text/javascript">
	
	/* 댓글 목록 조회 */
	var bno = 720896;
	
	getPageList(1);
	
	function getAllList(){
		$.getJSON("/replies/all/" + bno, function(data){
			
			var str = "";
			console.log(data.length);
			
			$(data).each(function(){
				str += "<li data-rno='"+this.rno+"' class='replyLi'>"
				+ this.rno + ":" + this.replytext  
				+ "<button>MOD</button></li>";
			});
			
			$("#replies").html(str);
		});
	}
	
	/* 댓글 추가하기 */
	$("#replyAddBtn").on("click", function(){
		
		var replyer = $("#newReplyWriter").val();
		var replytext = $("#newReplyText").val();
		
		$.ajax({
			type : 'post',
			url : '/replies',
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "POST"
			},
			dataType : 'text',
			data : JSON.stringify({
				bno : bno,
				replyer : replyer,
				replytext : replytext
			}),
			success : function(result){
				
				if(result == 'SUCCESS'){
					alert("등록 되었습니다.");
					//getAllList();
					getPageList(replyPage);
				}
			}
		});
	});
	
	$("#replies").on("click", ".replyLi button", function(){
		
		var reply = $(this).parent();
		
		var rno = reply.attr("data-rno");
		var replytext = reply.text();
		
		$(".modal-title").html(rno);
		$("#replytext").val(replytext);
		$("#modDiv").show("slow");
		
	});
	
	/* 댓글 삭제 호출하기 */
	$("#replyDelBtn").on("click", function(){
		
		var rno = $(".modal-title").html();
		var replytext = $("#replytext").val();
		
		$.ajax({
			type : 'delete',
			url : '/replies/' + rno,
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "DELETE"
			},
			dataType : 'text',
			success : function(result){
				console.log("result : " + result);
				if(result == 'SUCCESS'){
					alert("삭제되었습니다");
					$("#modDiv").hide("slow");
					//getAllList();
					getPageList(replyPage);
				}
			}
		});
	});
	
	/* 댓글 수정하기 */
	$("#replyModBtn").on("click", function(){
		
		var rno = $(".modal-title").html();
		var replytext = $("#replytext").val();
		
		$.ajax({
			type : 'put',
			url : '/replies/' + rno,
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "PUT"},
				data : JSON.stringify({replytext:replytext}),
				dataType:'text',
				success:function(result){
					console.log("result:" + result);
					if(result == 'SUCCESS'){
						alert("수정되었습니다.")
						$("#modDiv").hide("slow");
						//getAllList();
						getPageList(replyPage);
					}
				}
			});
		});
	
	/* 댓글 페이징 처리*/
	function getPageList(page){
		
		$.getJSON("/replies/" + bno + "/" + page, function(data){
			
			var str = "";
			
			$(data.list).each(function(){
				str += "<li data-rno='"+this.rno+"' class='replyLi'>"
				+ this.rno + ":" + this.replytext +
				"<button>MDO</button></li>";
			});
			
			$("#replies").html(str);
			
			printPaging(data.pageMaker);
		});
	}
	
	/* 화면에 페이지 번호 출력 */
	function printPaging(pageMaker){
		
		var str = "";
		
		if(pageMaker.prev){
			str += "<li><a href='"+(pageMaker.startPage-1)+"'> << </a></li>";
		}
		
		for(var i = pageMaker.startPage, len=pageMaker.endPage; i<=len; i++){
			var strClass = pageMaker.cri.page == i?'class=active':'';
			str += "<li "+strClass+"><a href='"+i+"'>"+i+"</li>";
		}
		
		if(pageMaker.next){
			str += "<li><a href='"+(pageMaker.endPage + 1)+"'> >> </a></li>";
		}
		
		$('.pagination').html(str);
	}
	
	/* 페이지 번호 이벤트 처리 */
	var replyPage = 1;
	
	$(".pagination").on("click", "li a", function(event){
		
		event.preventDefault();
		
		replyPage = $(this).attr("href");
		
		console.log("************");
		console.log(replyPage);
		
		getPageList(replyPage);
	});
	</script>
</body>
</html>