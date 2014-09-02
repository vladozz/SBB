<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="myapp.homepage" />

<tiles:insertTemplate template="/WEB-INF/layouts/classic.jsp">
    <tiles:putAttribute name="head" value="/WEB-INF/pages/tiles/head.jsp"/>
    <tiles:putAttribute name="header" value="/WEB-INF/pages/tiles/header.jsp" />

    <tiles:putAttribute name="body" value="/WEB-INF/pages/tiles/body.jsp" />
    <tiles:putAttribute name="footer" value="/WEB-INF/pages/tiles/footer.jsp" />
</tiles:insertTemplate>

