<!-- 将JSP页面都放到WEB-INF目录下。WEB-INF目录下的任何文件或子目录都受保护，无法通过浏览器直接访问，但
控制器依然可以转发请求到这些页面。-->
<!DOCTYPE html>
<html>
<head>
<title>Save Product</title>
<style type="text/css">@import url(css/main.css);</style>
</head>
<body>
<div id="global">
    <h4>The product has been saved.</h4>
    <p>
        <h5>Details:</h5>
        Product Name: ${product.name}<br/>
        Description: ${product.description}<br/>
        Price: $${product.price}
    </p>
</div>
</body>
</html>