<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Images</title>
    <style>
        .classlink {
            margin: 0;
            border: 0;
            background: none;
            overflow: visible;
            color: blue;
            cursor: pointer;
            text-decoration: underline;
        }

        input[type=text], select {
            width: 50%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type=number], select {
            width: 50%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type=submit] {
            width: 30%;
            background-color: #234ca0;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        div {
            border-radius: 5px;
            background-color: #f2f2f2;
            padding: 20px;
        }
    </style>
</head>
<body>
<h3>Images</h3>
<c:forEach var="image" items="${images}">
    <img src=${image.url} alt="Image..." style="width:30%;height:30%;">
    <form action="/delete" method="post">
        <input type="hidden" name="url" value="${image.url}">
        <input type="submit" value="delete" class="classlink">
    </form>
</c:forEach>
</body>
</html>