<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>查询</title>

    <!-- Bootstrap -->
    <link href="/bootstrap/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<form class="form-inline" method="post" action="/queryData">
    <div class="form-group">
        <label for="exampleInputEmail1">电话号码</label>
        <input type="text" name="tel" class="form-control" id="exampleInputEmail1" placeholder="Telphone">
    </div>
    <div class="form-group">
        <label >年度</label>
        <select name="year">
            <option value="2017" class="form-control" >2017</option>
            <option value="2018" class="form-control" >2018</option>
            <option value="2019" class="form-control" >2019</option>
        </select>
    </div>
    <button type="submit" class="btn btn-default">查询</button>
</form>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="/jquery/jquery-2.1.1.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/bootstrap/js/bootstrap.js"></script>
</body>
</html>