<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Images</title>
   <style>

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
<h3>Find all inner images by url</h3>
<form action="/images" method="post" class="search">

    <input type="number", name="depth" placeholder="Crawling depth..." >
    <input type="text" name="image" placeholder="Image url..."/>
    <br>
    <input type="submit" value="Search" />
</form>

</body>
</html>