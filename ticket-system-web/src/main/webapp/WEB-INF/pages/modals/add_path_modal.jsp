<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Modal -->
<div class="modal fade" id="addPathModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Add new path</h4>
            </div>
            <div class="jumbotron" id="err" style="display: none; background-color: #FFA0A0">
                <div class="container">
                    <h3 id="mes">Error</h3>
                </div>

            </div>
            <div class="modal-body">
                <form:form id="pathForm" class="form-horizontal" role="form" action="add" method="get" commandName="path">
                    <div class="form-group" id="commonId">
                        <label for="inputId" class="col-sm-3 control-label">ID</label>

                        <div class="col-sm-9">
                            <form:input path="id" type="text" class="form-control" id="inputId" placeholder="" readonly="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputTitle" class="col-sm-3 control-label">Number</label>

                        <div class="col-sm-9">
                            <form:input path="title" type="text" class="form-control" id="inputTitle" placeholder=""/>
                        </div>
                    </div>
                    <div class="form-group" id="commonLC">
                        <label for="inputLC" class="col-sm-3 control-label">Last change index</label>

                        <div class="col-sm-9">
                            <form:input path="lastChange" type="text" class="form-control" id="inputLC" placeholder="" readonly="true"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="modal-footer">

                            <button id ="submit" type="button" class="btn btn-primary" onclick="addPath();">Add path</button>
                            <button id="close" type="button" class="btn btn-default" data-dismiss="modal" onclick="resetPathForm();">Close</button>

                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/path.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-dialog.js'/>"></script>

