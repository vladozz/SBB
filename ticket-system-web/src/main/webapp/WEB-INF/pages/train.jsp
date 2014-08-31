<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title><spring:message code="label.title" /></title>
</head>
<body>

<h2><spring:message code="label.title" /></h2>

<form:form method="post" action="add" commandName="train">

    <table>
        <tr>
            <td><form:label path="number">
                <spring:message code="label.firstname" />
            </form:label></td>
            <td><form:input path="number" /></td>
        </tr>
        <tr>
            <td><form:label path="placesQty">
                <spring:message code="label.lastname" />
            </form:label></td>
            <td><form:input path="placesQty" /></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit"
                                   value="<spring:message code="label.addcontact"/>" /></td>
        </tr>
    </table>
</form:form>

<h3><spring:message code="label.contacts" /></h3>
<c:if test="${!empty trainList}">
    <table class="data">
        <tr>
            <th><spring:message code="label.firstname" /></th>
            <th><spring:message code="label.telephone" /></th>
            <th>&nbsp;</th>
        </tr>
        <c:forEach items="${trainList}" var="train">
            <tr>
                <td>${train.number}</td>
                <td>${train.placesQty}</td>
                <td><a href="delete/${train.id}"><spring:message code="label.delete" /></a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>