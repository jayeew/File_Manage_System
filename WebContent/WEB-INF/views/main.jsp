<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type"
	content="multipart/form-data; charset=utf-8" />

<title>File Manage System</title>

<meta name="description"
	content="Source code generated using layoutit.com">
<meta name="author" content="LayoutIt!">

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link href="css/fileinput.min.css" media="all" rel="stylesheet"
	type="text/css" />

<!-- 处理静态资源 -->
<script type="text/javascript" src="scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$(".delete").click(function() {
			var href = $(this).attr("href");
			$("form").attr("action", href).submit();
			return false;
		});
	})
</script>

</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="row">
					<div class="col-md-4">
						<h3>File Manage System</h3>
					</div>

					<div class="col-md-4">
						<form action="search" method="post">
							<div class="input-group" style="margin-top: 20px">
								<input type="text" class="form-control"
									placeholder="Please Enter the file name here" name="keys" />
								<!-- <input type="submit" value="Go Searching" style="background: #87cefa; color: #fff; border: 1px #000;"> -->
								<span class="input-group-btn">
									<button type="submit" class="btn btn-info btn-search"
										onclick="javascrtpt:window.location.href='search'">
										Go Searching</button>
								</span>
							</div>
						</form>
					</div>

					<div class="col-md-4">
						<div class="btn-group" style="margin-top: 20px">

							<button class="btn btn-default" type="button"
								onclick="javascrtpt:window.location.href='log'">
								<em class="glyphicon glyphicon-align-left"></em> login
							</button>
							<button class="btn btn-default" type="button"
								onclick="javascrtpt:window.location.href='logout'">
								<em class="glyphicon glyphicon-align-center"></em> logout
							</button>
							<button class="btn btn-default" type="button"
								onclick="javascrtpt:window.location.href='reg'">
								<em class="glyphicon glyphicon-align-right"></em> sign up
							</button>
							<!--<input id="input-id" class="btn btn-default" type="file" class="file"/>
							<em class="glyphicon glyphicon-align-justify"></em> upload-->

							<!-- Button trigger modal -->
							<button type="button" class="btn btn-primary" data-toggle="modal"
								data-target="#exampleModal">upload file</button>

							<!-- Modal -->
							<div class="modal fade" id="exampleModal" tabindex="-1"
								role="dialog" aria-labelledby="exampleModalLabel"
								aria-hidden="true">
								<div class="modal-dialog modal-lg" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="exampleModalLabel">upload
												file Modal</h5>
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
										</div>
										<div class="modal-body">

											<c:if test="${empty requestScope.user }">

											</c:if>

											<c:if test="${!empty requestScope.user }" var="xmp">
												<c:if test="${requestScope.user.id == 1}">
													<form method="post" action="upload"
														enctype="multipart/form-data">
														<div class="file-loading">
															<input id="input-b1" type="file" class="file" name="file" />
														</div>
														<div class="input-group">
															<span class="input-group-addon">(+_+)?</span> <input
																type="text" class="form-control"
																placeholder="say what you want" name="desc">
														</div>
													</form>
												</c:if>
												<c:if test="${requestScope.user.id != 1}">
													<form method="post" action="log"
														enctype="multipart/form-data">
														<div class="file-loading">
															<input id="input-b1" type="file" class="file" name="file" />
														</div>
														<div class="input-group">
															<span class="input-group-addon">(+_+)?</span> <input
																type="text" class="form-control"
																placeholder="say what you want" name="desc">
														</div>
													</form>
												</c:if>

											</c:if>
											<!-- 
										
												<form method="post" action="upload" enctype="multipart/form-data">
												<div class="file-loading">
													<input id="input-b1" type="file" class="file" name="file" />
												</div>
												<div class="input-group">
                                   				<span class="input-group-addon">(+_+)?</span>
                                    			<input type="text" class="form-control" placeholder="say what you want" name="desc">
                                    			</div>
											</form>										
										
										 -->

											<div id="kartik-file-errors"></div>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-secondary"
												data-dismiss="modal">Close</button>
											<button type="button" class="btn btn-primary"
												title="Your custom upload logic">Save</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="btn-group">
							<span class="label label-info"><a href="txt">Txt</a></span> <span
								class="label label-info"><a href="png">Png</a></span> <span
								class="label label-info"><a href="zip">Zip</a></span> <span
								class="label label-info"><a href="jpg">Jpg</a></span> <span
								class="label label-info"><a href="sblmd">Sort By Last
									Modified Date</a></span> <span class="label label-info"><a
								href="sbldt">Sort By Download Times</a></span>
						</div>
					</div>
				</div>

				<c:if test="${empty requestScope.files }">
				no file info
			</c:if>
				<c:if test="${!empty requestScope.files }"></c:if>

				<table class="table">
					<thead>
						<tr>
							<th>NO.</th>
							<th>Name</th>
							<th>Description</th>
							<th>Last Modified Date</th>
							<th>Download Times</th>
							<th>Size</th>
							<th>Uploader</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach items="${requestScope.files }" var="emp"
							varStatus="status">
							<c:if test="${status.index % 5 == 0 }" >
								<tr >
								<td>${status.index+1 }</td>
								<td>${emp.file_name }</td>
								<td>${emp.file_desc }</td>
								<td>${emp.file_data }</td>
								<td>${emp.file_download }</td>
								<td>${emp.file_size }KB</td>
								<td>${emp.file_Uper }</td>
								<td>
									<form action="emp/${emp.file_name }" method="POST">
										<input type="hidden" name="_method" value="DELETE" /> <input
											type="submit" value="Del"  style='font-size:12px' class="btn btn-default">
									</form>
								</td>
								<td><c:if test="${empty requestScope.user }">
									</c:if> <c:if test="${!empty requestScope.user }" var="xmp">
										<c:if test="${requestScope.user.id == 1}">
											<button type="button" class="btn btn-default"
												onclick="javascrtpt:window.location.href='emp/${emp.file_name }'" style='font-size:12px'>
												Download</button>
										</c:if>
										<c:if test="${requestScope.user.id != 1}">
											<button type="button" class="btn btn-default"
												onclick="javascrtpt:window.location.href='log'">
												Download</button>
										</c:if>
									</c:if></td>
							</tr>
							</c:if>
							<c:if test="${status.index % 5 == 1 }" >
								<tr class="active">
								<td>${status.index+1 }</td>
								<td>${emp.file_name }</td>
								<td>${emp.file_desc }</td>
								<td>${emp.file_data }</td>
								<td>${emp.file_download }</td>
								<td>${emp.file_size }KB</td>
								<td>${emp.file_Uper }</td>
								<td>
									<form action="emp/${emp.file_name }" method="POST">
										<input type="hidden" name="_method" value="DELETE" /> <input
											type="submit" value="Del"  style='font-size:12px' class="btn btn-default">
									</form>
								</td>
								<td><c:if test="${empty requestScope.user }">
									</c:if> <c:if test="${!empty requestScope.user }" var="xmp">
										<c:if test="${requestScope.user.id == 1}">
											<button type="button" class="btn btn-default"
												onclick="javascrtpt:window.location.href='emp/${emp.file_name }'" style='font-size:12px'>
												Download</button>
										</c:if>
										<c:if test="${requestScope.user.id != 1}">
											<button type="button" class="btn btn-default"
												onclick="javascrtpt:window.location.href='log'">
												Download</button>
										</c:if>
									</c:if></td>
							</tr>
							</c:if>
							<c:if test="${status.index % 5 == 2 }" >
								<tr class="success">
								<td>${status.index+1 }</td>
								<td>${emp.file_name }</td>
								<td>${emp.file_desc }</td>
								<td>${emp.file_data }</td>
								<td>${emp.file_download }</td>
								<td>${emp.file_size }KB</td>
								<td>${emp.file_Uper }</td>
								<td>
									<form action="emp/${emp.file_name }" method="POST">
										<input type="hidden" name="_method" value="DELETE" /> <input
											type="submit" value="Del"  style='font-size:12px' class="btn btn-default">
									</form>
								</td>
								<td><c:if test="${empty requestScope.user }">
									</c:if> <c:if test="${!empty requestScope.user }" var="xmp">
										<c:if test="${requestScope.user.id == 1}">
											<button type="button" class="btn btn-default"
												onclick="javascrtpt:window.location.href='emp/${emp.file_name }'" style='font-size:12px'>
												Download</button>
										</c:if>
										<c:if test="${requestScope.user.id != 1}">
											<button type="button" class="btn btn-default"
												onclick="javascrtpt:window.location.href='log'">
												Download</button>
										</c:if>
									</c:if></td>
							</tr>
							</c:if>
							<c:if test="${status.index % 5 == 3 }" >
								<tr class="warning">
								<td>${status.index+1 }</td>
								<td>${emp.file_name }</td>
								<td>${emp.file_desc }</td>
								<td>${emp.file_data }</td>
								<td>${emp.file_download }</td>
								<td>${emp.file_size }KB</td>
								<td>${emp.file_Uper }</td>
								<td>
									<form action="emp/${emp.file_name }" method="POST">
										<input type="hidden" name="_method" value="DELETE" /> <input
											type="submit" value="Del"  style='font-size:12px' class="btn btn-default">
									</form>
								</td>
								<td><c:if test="${empty requestScope.user }">
									</c:if> <c:if test="${!empty requestScope.user }" var="xmp">
										<c:if test="${requestScope.user.id == 1}">
											<button type="button" class="btn btn-default"
												onclick="javascrtpt:window.location.href='emp/${emp.file_name }'" style='font-size:12px'>
												Download</button>
										</c:if>
										<c:if test="${requestScope.user.id != 1}">
											<button type="button" class="btn btn-default"
												onclick="javascrtpt:window.location.href='log'">
												Download</button>
										</c:if>
									</c:if></td>
							</tr>
							</c:if>
							<c:if test="${status.index % 5 == 4 }" >
								<tr class="danger">
								<td>${status.index+1 }</td>
								<td>${emp.file_name }</td>
								<td>${emp.file_desc }</td>
								<td>${emp.file_data }</td>
								<td>${emp.file_download }</td>
								<td>${emp.file_size }KB</td>
								<td>${emp.file_Uper }</td>
								<td>
									<form action="emp/${emp.file_name }" method="POST">
										<input type="hidden" name="_method" value="DELETE" /> <input
											type="submit" value="Del"  style='font-size:12px' class="btn btn-default">
									</form>
								</td>
								<td><c:if test="${empty requestScope.user }">
									</c:if> <c:if test="${!empty requestScope.user }" var="xmp">
										<c:if test="${requestScope.user.id == 1}">
											<button type="button" class="btn btn-default"
												onclick="javascrtpt:window.location.href='emp/${emp.file_name }'" style='font-size:12px'>
												Download</button>
										</c:if>
										<c:if test="${requestScope.user.id != 1}">
											<button type="button" class="btn btn-default"
												onclick="javascrtpt:window.location.href='log'">
												Download</button>
										</c:if>
									</c:if></td>
							</tr>
							</c:if>
						
						</c:forEach>
						<!-- 
						<tr>
							<td><a href="emp/test">data back</a></td>
						</tr>
						 -->
					</tbody>
				</table>

				<!-- 
				 共有${requestScope.pageBean.totalRecord}个员工，共${requestScope.pageBean.totalPage }页，当前为${requestScope.pageBean.pageNum}页
				 -->
				<%-- 构建分页导航 --%>

				<br> <br />



				<!-- -->

				<div class="row">
					<div class="col-md-4"></div>
					<div class="col-md-4">
						<a
							href="${pageContext.request.contextPath}/FindAllWithPage?pageNum=1">First
							Page</a>
						<%--如果当前页为第一页时，就没有上一页这个超链接显示 --%>
						<c:if test="${requestScope.pageBean.pageNum ==1}">
							<c:forEach begin="${requestScope.pageBean.start}"
								end="${requestScope.pageBean.end}" step="1" var="i">
								<c:if test="${requestScope.pageBean.pageNum == i}">
                          ${i}
                     </c:if>
								<c:if test="${requestScope.pageBean.pageNum != i}">
									<a
										href="${pageContext.request.contextPath}/FindAllWithPage?pageNum=${i}">${i}</a>
								</c:if>
							</c:forEach>
							<a
								href="${pageContext.request.contextPath}/FindAllWithPage?pageNum=${requestScope.pageBean.pageNum+1}">Next</a>
						</c:if>
						<%--如果当前页不是第一页也不是最后一页，则有上一页和下一页这个超链接显示 --%>
						<c:if
							test="${requestScope.pageBean.pageNum > 1 && requestScope.pageBean.pageNum < requestScope.pageBean.totalPage}">
							<a
								href="${pageContext.request.contextPath}/FindAllWithPage?pageNum=${requestScope.pageBean.pageNum-1}">Pre</a>
							<c:forEach begin="${requestScope.pageBean.start}"
								end="${requestScope.pageBean.end}" step="1" var="i">
								<c:if test="${requestScope.pageBean.pageNum == i}">
                         ${i}
                     </c:if>
								<c:if test="${requestScope.pageBean.pageNum != i}">
									<a
										href="${pageContext.request.contextPath}/FindAllWithPage?pageNum=${i}">${i}</a>
								</c:if>
							</c:forEach>
							<a
								href="${pageContext.request.contextPath}/FindAllWithPage?pageNum=${requestScope.pageBean.pageNum+1}">Next</a>
						</c:if>

						<%-- 如果当前页是最后一页，则只有上一页这个超链接显示，下一页没有 --%>
						<c:if
							test="${requestScope.pageBean.pageNum == requestScope.pageBean.totalPage}">
							<a
								href="${pageContext.request.contextPath}/FindAllWithPage?pageNum=${requestScope.pageBean.pageNum-1}">Pre</a>
							<c:forEach begin="${requestScope.pageBean.start}"
								end="${requestScope.pageBean.end}" step="1" var="i">
								<c:if test="${requestScope.pageBean.pageNum == i}">
                         ${i}
                     </c:if>
								<c:if test="${requestScope.pageBean.pageNum != i}">
									<a
										href="${pageContext.request.contextPath}/FindAllWithPage?pageNum=${i}">${i}</a>
								</c:if>
							</c:forEach>
						</c:if>
						<%--尾页 --%>
						<a
							href="${pageContext.request.contextPath}/FindAllWithPage?pageNum=${requestScope.pageBean.totalPage}">Last
							Page</a>
					</div>
					<div class="col-md-4"></div>
				</div>
			</div>

		</div>
	</div>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/scripts.js"></script>
	<script src="js/fileinput.min.js"></script>
	<script>
		$(document).on('ready', function() {
			$("#input-b1").fileinput({
				showPreview : true,
				showUpload : true,
				showCaption : true,
				browseClass : "btn btn-success",
				browseLabel : "Select file",
				uploadClass : "btn btn-info",
				uploadLabel : "Upload",
				removeClass : "btn btn-danger",
				removeLabel : "Delete",
				elErrorContainer : '#kartik-file-errors',
				allowedFileExtensions : [ "jpg", "png", "gif" ],
			//uploadUrl : "Upload"
			});
		});
	</script>
</body>
</html>