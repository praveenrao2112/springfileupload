<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spring File Upload</title>
</head>
<body>
<h2>Upload File</h2>
<form method="post" action="${pageContext.request.contextPath}/uploadFile" enctype="multipart/form-data">

    <input type="file" name="file" />
    <button type="submit">Upload</button>
</form>
<p>${message}</p>
</body>
</html>