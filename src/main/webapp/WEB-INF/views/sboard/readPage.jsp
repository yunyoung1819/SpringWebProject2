<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    
<%@include file="../include/header.jsp" %>
<style type="text/css">
.popup {position: absolute;}
.back {background-color: gray; opacity: 0.5; width: 100%; height:300%; overflow:hidden; z-index:1101;}
.front{
	z-index:1110; opacity: 1; border: 1px; margin: auto;
}
.show{
	position:relative;
	max-width: 1200px;
	max-height: 800px;
	overflow: auto;
}
</style>

<div class='popup back' style="display:none;"></div>
	<div id="popup_front" class='popup front' style="display:none;">
	<img id="popup_img">	
</div>	

<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<!-- upload.js 포함 -->
<script type="text/javascript" src="/resources/js/upload.js"></script>
<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!--  general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">READ BOARD</h3>
				</div>
				<!-- ./box-header -->
			
			<form role="form"  action="modifyPage" method="post"> 				<!-- action : 폼을 전송할 서버 쪽 스크립트 파일을 지정합니다. -->
				<input type='hidden' name='bno' value="${boardVO.bno}"> 		<!-- 게시물 번호 -->
				<input type='hidden' name='page' value="${cri.page}">			<!-- 현재 조회하는 페이지 번호 -->
				<input type='hidden' name='perPageNum' value="${cri.perPageNum}"> <!-- 한 페이지당 출력하는 데이터 개수 -->
				<input type='hidden' name='searchType' value="${cri.searchType}"><!-- 검색 타입 -->
				<input type='hidden' name='keyword' value="${cri.keyword }"><!-- 검색 단어 -->
			</form>
				<div class="box-body">
					<div class="form-group">
						<lable for="exampleInputEmail1">Title</lable>
						<input type="text" name="title" class="form-control" value="${boardVO.title}" readonly="readonly">
					</div>
					<div class="form-group">
						<lable for="exampleInputPassword1">Content</lable>
						<textarea class="form-control" name="content" rows="3" readonly="readonly">${boardVO.content}</textarea>
					</div>
					<div class="form-group">
						<lable for="exampleInputEmail1">Writer</lable>
						<input type="text" name="writer" class="form-control" value="${boardVO.writer}" readonly="readonly">
					</div>
				</div>
				<!-- /.box-body -->

				<ul class="mailbox-attachments clearfix uploadedList">
				</ul>
				<c:if test="${login.uid == boardVO.writer}">
					<button type="submit" class="btn btn-warning" id="modifyBtn">수정</button>
					<button type="submit" class="btn btn-danger" id="removeBtn">삭제</button>
				</c:if>
					<button type="submit" class="btn btn-primary" id="goListBtn">목록가기</button>
			</div>
			<!-- /.box -->
		</div>
		<!--/.col (left) -->
	</div>
	<!-- /.row -->
	
	<!-- 댓글 등록 부분 -->
	<!-- 댓글 등록에 필요한 <div> -->
	<div class="row">
		<div class="col-md-12">
			<div class="box box-success">
				<div class="box-header">
					<h3 class="box-title">댓글 작성하기</h3>
				</div>
			
			  <c:if test="${not empty login}">
				<div class="box-body">
					<label for="newReplyWriter">Writer</label>
					<input class="form-control" type="text" placeholder="USER ID" 
					id="newReplyWriter" value="${login.uid }" readonly="readonly">
					<label for="newReplyText">Reply Text</label>
					<input class="form-control" type="text" placeholder="REPLY TEXT" id="newReplyText">				
				</div>
				
				<!-- /.box-body -->
				<div class="box-footer">
					<button type="submit" class="btn btn-primary" id="replyAddBtn">댓글 작성</button>
				</div>
			  </c:if>
			  
			  <c:if test="${empty login }">
			  	<div class="box-body">
			  		<div><a href="javascript:goLogin();">Login Please</a></div>
			  	</div>
			  </c:if>
			</div>
			
			<!-- The time line -->
			<ul class="timeline">
				<!-- timeline time label -->
				<li class="time-label" id="repliesDiv">
					<span class="bg-green">
					댓글목록 열기 <small id='replycntSmall'> [ ${boardVO.replycnt} ] </small>
					</span>
				</li>
			</ul>
			
			<div class='text-center'>
				<ul id="pagination" class="pagination pagination-sm no-margin ">
				</ul>
			</div>
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	
	<!-- Modal -->
	<!-- 수정과 삭제를 위한 Modal창(팝업과 유사하지만 다른 작업을 할 수 없도록 제한된 팝업) -->
	<div id="modifyModal" class="modal modal-primary fade" role="dialog">
		<div class="modal-dialog">
		<!-- Modal content -->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body" data-rno>
				<p><input type="text" id="replytext" class="form-control"></p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info" id="replyModBtn">댓글 수정</button>
				<button type="button" class="btn btn-danger" id="replyDelBtn">댓글 삭제</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
			</div>
		</div>
		</div>
	</div>

</section>
<!-- /.content-wrapper -->

<script>
$(document).ready(function(){
	
	var formObj = $("form[role='form']");
	
	console.log("formObj: ", formObj);
	
	// 수정 버튼
	$(".btn-warning").on("click", function(){
		formObj.attr("action", "/sboard/modifyPage");
		formObj.attr("method", "get");
		formObj.submit();
	});
	
	// 삭제 버튼
	$(".btn-danger").on("click", function(){
		formObj.attr("action", "/sboard/removePage");
		formObj.submit();
	});
	
	// 목록 버튼 
	$(".btn-primary").on("click", function(){
		formObj.attr("method", "get");
		formObj.attr("action", "/sboard/list");
		formObj.submit();
	});
	
	// 첨부파일에 대한 템플릿과 JavaScript 처리 부분
	var bno = ${boardVO.bno};
	var template = Handlebars.compile($("#templateAttach").html());
	
	$.getJSON("/sboard/getAttach/"+bno,function(list){
		
		$(list).each(function(){
			
			var fileInfo = getFileInfo(this);
			
			var html = template(fileInfo);
			
			$(".uploadedList").append(html);
			
		});
	});
	
	// 첨부파일이 이미지 파일인 경우는 원본 파일의 경로를 특정한 <div>에 <img> 객체로 만들어서 넣은 후 해당 <div>를 맨 앞쪽으로 보여주게 처리함
	$(".uploadedList").on("click", ".mailbox-attachment-info a", function(event){
		
		var fileLink = $(this).attr("href");
		
		if(checkImageType(fileLink)){
			alert('test');
			event.preventDefault();
			
			var imgTag = $("#popup_img");
			imgTag.attr("src", fileLink);
			
			console.log(imgTag.attr("src"));
			
			$(".popup").show('slow');
			imgTag.addClass("show");
		}
	});
	
	$("#popup_img").on("click", function(){
		
		$(".popup").hide('slow');
		
	});
	
	$("#removeBtn").on("click", function(){
		
		var replyCnt = $("#replycntSmall").html().replace(/[^0-9]/g, "");
		
		if(replyCnt > 0){
			alert("댓글이 달린 게시물을 삭제할 수 없습니다.");
			return;
		}
		
		var arr = [];
		$(".uploadedList li").each(function(index){
			arr.push($(this).attr("data-src"));
		});
		
		if(arr.length > 0){
			$.post("/deleteAllFiles",{files:arr},function(){
				
			});
		}
		
		formObj.attr("action", "/sboard/removePage");
		formObj.submit();
	});

});
</script>

<!-- Handlebars를 사용하는 템플릿 코드 -->
<script id="template" type="text/x-handlebars-template">
{{#each .}}
<li class="replyLi" data-rno={{rno}}>
	<i class="fa fa-comments bg-blue"></i>
	<div class="timeline-item">
		<span class="time">
			<i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
		</span>
		<h3 class="timeline-header"><strong>{{rno}}</strong> -{{replyer}}</h3>
		<div class="timeline-body">{{replytext}}</div>
			<div class="timeline-footer">
			    {{#eqReplyer replyer }}
				<a class="btn btn-primary btn-xs"
					data-toggle="modal" data-target="#modifyModal"> Modify </a>
				{{eqReplyer}}
			</div>
	</div>
</li>
{{/each}}
</script>

<!-- 첨부파일 조회 handlebars 템플릿 -->
<script id="templateAttach" type="text/x-handlebars-template">
<li data-src='{{fullName}}'>
	<span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}" alt="Attachment"></span>
<div class="mailbox-attachment-info">
<a href="{{getLink}}" class="mailbox-attachment-name">{{fullName}}</a>
</span> 
</div>
</li>
</script>

<script>
	<!-- 로그인한 사용자만이 작성한 댓글의 수정과 삭제 작업이 가능하도록 처리 -->
	Handlebars.registerHelper("eqReplyer", function(replyer, block) {
		var accum = '';
		if(replyer == '${login.uid}') {
			accum += block.fn();
		}
		return accum;
	}); 
	<!-- prettifyDate regdate 에 대한 Javascript의 처리 -->
	Handlebars.registerHelper("prettifyDate", function(timeValue){
		
		var dateObj = new Date(timeValue);
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth() + 1;
		var date = dateObj.getDate();
		return year + "/" + month + "/" + date;
	});
	
	var printData = function(replyArr, target, templateObject){
		
		var template = Handlebars.compile(templateObject.html());
		
		var html = template(replyArr);
		$(".replyLi").remove();
		target.after(html);
	}
</script>

<script>
	var bno = ${boardVO.bno}
	var replyPage = 1;
	
	function getPage(pageInfo){
		
		$.getJSON(pageInfo, function(data){
			printData(data.list, $("#repliesDiv"), $('#template'));
			printPaging(data.pageMaker, $(".pagination"));
			
			$("#modifyModal").modal('hide');
			$("#replycntSmall").html("[ " + data.pageMaker.totalCount + " ]")
		});
	}
	
	// 화면 하단 페이징 처리 출력
		var printPaging = function(pageMaker, target) {

		var str = "";

		if (pageMaker.prev) {
			str += "<li><a href='" + (pageMaker.startPage - 1)
					+ "'> << </a></li>";
		}

		for (var i = pageMaker.startPage, len = pageMaker.endPage; i <= len; i++) {
			var strClass = pageMaker.cri.page == i ? 'class=active' : '';
			str += "<li "+strClass+"><a href='"+i+"'>" + i + "</a></li>";
		}

		if (pageMaker.next) {
			str += "<li><a href='" + (pageMaker.endPage + 1)
					+ "'> >> </a></li>";
		}

		target.html(str);
	};
	
	// 댓글 목록의 이벤트 처리 (Reply List 버튼 클릭시 댓글 목록 표출)
	$("#repliesDiv").on("click", function(){
		
		if($(".timeline li").size() > 1){ // 목록을 가져오는 버튼이 보여지는 <li>만 있는 경우에 1페이지의 댓글 목록을 가져오기 위해 처리
			return;
		}
		getPage("/replies/" + bno + "/1");
	});
	
	// 댓글 페이징(1,2 ..) 의 이벤트 처리
	$(".pagination").on("click", "li a", function(event){
		
		event.preventDefault();
		
		replyPage = $(this).attr("href");
		
		getPage("/replies/" +bno+ "/" + replyPage);
	});
	
	// 댓글 등록 버튼
	$("#replyAddBtn").on("click", function(){
		
		var replyerObj = $("#newReplyWriter"); //Writer
		var replytextObj = $("#newReplyText"); //ReplyText
		
		var replyer = replyerObj.val();
		var replytext = replytextObj.val();
		
		console.log("replyer : ", replyer);
		console.log("replytextObj : ", replytextObj);
		
		$.ajax({
			type : 'post',
			url : '/replies/',
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "POST"
			},
			dataType : 'text',
			data : JSON.stringify({bno:bno, replyer:replyer, replytext:replytext}),
			success : function(result){
				console.log("result: " + result);
				if(result == 'SUCCESS'){
					alert("등록 되었습니다.");
					replyPage = 1;
					getPage("/replies/" + bno + "/" + replyPage);
					replyerObj.val("");
					replytextObj.val("");
				}
			}});
	});
	
	// 각 댓글의 버튼 이벤트 처리
	$(".timeline").on("click", ".replyLi", function(event){
		
		var reply = $(this);
		
		$("#replytext").val(reply.find('.timeline-body').text());
		$(".modal-title").html(reply.attr("data-rno"));
	});
	
	// 댓글의 수정 버튼
	$("#replyModBtn").on("click", function(){
		
		var rno = $(".modal-title").html();
		var replytext = $("#replytext").val();
		
		console.log("댓글 수정 rno : ", rno);
		console.log("댓글 수정 replytext : ", replytext);
		
		// ajax 호출
		$.ajax({
			type : 'put',
			url : '/replies/' + rno,
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "PUT"},
			data : JSON.stringify({replytext:replytext}),
			dataType : 'text',
			success : function(result){
				console.log("댓글 수정 result: " + result);
				if(result == 'SUCCESS'){
					alert("수정 되었습니다.");
					getPage("/replies/" +bno+ "/" + replyPage);
				}
			}
		});
	});
	
	// 댓글의 삭제 버튼
	$("#replyDelBtn").on("click", function(){

		var rno = $(".modal-title").html();
		var replytext = $("#replytext").val();
		
		console.log("댓글삭제 rno : " + rno);
		console.log("댓글삭제 replytext : " + replytext);
		
		// Ajax 호출
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
					alert("삭제 되었습니다.");
					getPage("/replies/" + bno + "/" + replyPage);
				}
			}});
	});
	
</script>
<%@include file="../include/footer.jsp"%>
