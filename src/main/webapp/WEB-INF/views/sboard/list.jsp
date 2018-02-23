<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>

<%@include file="../include/header.jsp"%>

<!-- Main content -->
<section class="content">
	<div class="row">
	<!-- left column -->
	<div class="col-md-12">
		<!-- general form elements -->
		<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">Search Board</h3>
				</div>
				<div class="box-body">
				
				<select name="searchType">
					<option value="n" <c:out value="${cri.searchType == null ? 'selected' : ''}"/>> --- </option>
					<option value="t" <c:out value="${cri.searchType eq 't' ? 'selected' : ''}"/>> Title </option>
					<option value="c" <c:out value="${cri.searchType eq 'c' ? 'selected' : ''}"/>> Content </option>
					<option value="w" <c:out value="${cri.searchType eq 'w' ? 'selecte' : ''}"/>> Writer </option>
					<option value="tc" <c:out value="${cri.searchType eq 'tc' ? 'selected' : ''}" />> Title OR Content </option>
					<option value="cw" <c:out value="${cri.searchType eq 'cw' ? 'selected' : ''}" />> Content OR Writer </option>
					<option value="tcw" <c:out value="${cri.searchType eq 'tcw' ? 'selected' : ''}"/>> Title OR Content OR Writer </option>
				</select>
				
				<input type="text" name='keyword' id="keywordInput" value='${cri.keyword }'></input>
				<button id='searchBtn'>Search</button>
				<button id='newBtn'>New Board</button>
				<button id='aExcelDown'>엑셀 다운로드</button>
				<table class="table table-bordered">
					<tr>
						<th style="width: 10px">BNO</th>
						<th>TITLE</th>
						<th>WRITER</th>
						<th>REGDATE</th>
						<th style="width: 40px">VIEWCNT</th>
					</tr>
					
					<!-- BoardController에서 전달받은 list를 화면에 출력하는 코드 -->
					<c:forEach items="${list}" var="boardVO">
						<tr>
							<td>${boardVO.bno}</td>
							<td>
								<a href='/sboard/readPage${pageMaker.makeSearch(pageMaker.cri.page)}&bno=${boardVO.bno}'>
									${boardVO.title}
								</a>
							</td>
							<td>${boardVO.writer}</td>
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" 
									value="${boardVO.regdate}" />
							</td>
							<td><span class="badege bg-red">${boardVO.viewcnt}</span></td>
						</tr>																																																		
					</c:forEach>
																
				</table>
				</div>
				<!-- /.box-body -->
				<div class="box-footer">
					<div class="text-center">
						<ul class="pagination">
							
							<c:if test="${pageMaker.prev}">
								<li>
									<a href="list${pageMaker.makeSearch(pageMaker.startPage -1 )}">&laquo;</a>
								</li>
							</c:if>
							
							<c:forEach begin="${pageMaker.startPage}"
								end="${pageMaker.endPage}" var="idx">
								<li
									<c:out value="${pageMaker.cri.page == idx?'class=active':''}"/>>
									<a href="list${pageMaker.makeSearch(idx)}">${idx}</a>
								</li>
							</c:forEach>
							
							<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
								<li>
									<a href="list${pageMaker.makeSearch(pageMaker.endPage + 1)}">&raquo;</a>
								</li>
							</c:if>
							
						</ul>
					</div>
					<div class="text-center">
						<ul class="pagination">
						
							
						</ul>
					</div>
					
				</div>
				<form id="excelFileForm" name="massiveForm" enctype="multipart/form-data" method="post">
					<input id="inputExcelFile" type="file" name="excelFile" style="display:none;"/></br>
				</form>
				<iframe name="exFrame" id="exFrame" style="display: none"></iframe>
				<!-- /.box-footer-->
			</div>
		</div>
		<!--/.col (left) -->
	</div>
	<!-- /.row -->
</section>
<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<script>
	var result = '${msg}';
	
	if(result == 'SUCCESS'){
		alert("처리가 완료되었습니다.");
	}
	
	$(document).ready(function(){
		
		$('#searchBtn').on("click", function(event){
			
			self.location = "list" + '${pageMaker.makeQuery(1)}'
								   + "&searchType="
								   + $("select option:selected").val()
								   + "&keyword=" + $('#keywordInput').val();
		});
		
		$('#newBtn').on("click", function(evt){
			
			self.location = "register";
		});
		
		//Download Excel File
		$('#aExcelDown').bind("click", function(){
			var params = "";
			params += '${pageMaker.makeQuery(1)}';
			params += "&searchType=" + $("select option:selected").val();
			params += "&keyword=" + $('#keywordInput').val();
			
			if(confirm("엑셀 파일을 다운로드 하시겠습니까?") == true){
				$('#exFrame').attr('src', 'http://localhost:8080/sboard/excelDown.do');
			}else{
				return; 
			}
		});
	});
			
</script>

<%@include file="../include/footer.jsp"%>
