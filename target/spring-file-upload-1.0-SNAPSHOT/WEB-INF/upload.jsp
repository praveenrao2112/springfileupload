<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spring File Upload</title>
</head>
<body>
<h2>Upload File</h2>
<form method="POST" enctype="multipart/form-data" action="upload">
    <input type="file" name="file"/>
    <button type="submit">Upload</button>
</form>
<p style="color:green;">${message}</p>
</body>
</html>