<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp"%>

<style>
.fileDrop {
width: 80%;
height: 100px;
border: 1px dotted gray;
background-color: lightslategrey;
margin: auto;
}
</style>

<!-- 첨부파일용 템플릿 handlebars 추가 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js">
</script>

<!-- 첨부파일용 템플릿 추가하기 -->
<script id="template" type="text/x-handlebars-template">
<li>
	<span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}"
alt="Attachment"></span>
	<div class="mail-box-attachment-info">
	<a href="{{getLink}}" class="mail-box-attachment-name">{{fileName}}</a>
	<a href="{{fullName}}"
		class="btn btn-default btn-xs pull-right delbtn"><i class="fa fa-fw fa-remove"></i></a>	
</div>
</li>
</script>
<script>
	var template = Handlebars.compile($("#template").html());
</script>
<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">REGISTER BOARD</h3>
				</div>
				<!-- /.box-header -->

<form role="form" method="post">
	<div class="box-body">
		<div class="form-group">
			<label for="exampleInputEmail1">Title</label> 
			<input type="text"
				name='title' class="form-control" placeholder="Enter Title">
		</div>
		<div class="form-group">
			<label for="exampleInputPassword1">Content</label>
			<textarea class="form-control" name="content" rows="3"
				placeholder="Enter ..."></textarea>
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">Writer</label> 
			<input type="text"
				name="writer" class="form-control" placeholder="Enter Writer">
		</div>
		
		<!-- 첨부파일을 끌어놓을 수 있는 영역 -->
		<div class="form-group">
			<label for="exampleInputEmail1">File DROP Here</label>
			<div class="fileDrop"></div>
		</div>
	</div>
	<!-- /.box-body -->

	<div class="box-footer">
		<div>
			<hr>
		</div>
		
		<ul class="mailbox-attachments clearfix uploadedList">
		</ul>
		
		<button type="submit" class="btn btn-primary">Submit</button>
	</div>
</form>


			</div>
			<!-- /.box -->
		</div>
		<!--/.col (left) -->

	</div>
	<!-- /.row -->
</section>
<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<%@include file="../include/footer.jsp"%>
