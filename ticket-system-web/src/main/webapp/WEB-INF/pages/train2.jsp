<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="include/head.jsp" %>
<body>

<div class="container">
    <%@include file="include/navbar.jsp" %>

    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#addTrainModal">
        Add new train
    </button>
    <!-- Button trigger modal -->

    <br/>
    <br/>

    <c:if test="${!empty trainList}">
        <div class="panel panel-default">
            <!-- Default panel contents -->
            <div class="panel-heading" align="center">List of trains</div>
            <div class="table-responsive">
                <table class="table table-hover">
                    <tr class="active">
                        <th>ID</th>
                        <th>Number</th>
                        <th>Quanity of places</th>
                        <th width="10%">Edit</th>
                        <th width="10%">Delete</th>
                    </tr>
                    <c:forEach items="${trainList}" var="train">
                        <tr>
                            <td>${train.id}</td>
                            <td>${train.number}</td>
                            <td>${train.placesQty}</td>

                            <td>
                                <button type="button" class="btn btn-warning">Edit</button>
                            </td>
                            <td>
                                <a href="delete/${train.id}">
                                    <button type="button" class="btn btn-danger">Delete</button>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </c:if>
    <%@include file="include/footer.jsp"%>
</div>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
</body>
</html>