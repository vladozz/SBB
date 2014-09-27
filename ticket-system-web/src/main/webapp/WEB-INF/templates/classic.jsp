<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<tiles:insertAttribute name="head"/>
<body>
<div id="wrap">
    <div class="container">
        <tiles:insertAttribute name="header"/>
        <tiles:insertAttribute name="body"/>
    </div>
    <div id="push"></div>
</div>
<tiles:insertAttribute name="footer"/>
</body>

</html>
