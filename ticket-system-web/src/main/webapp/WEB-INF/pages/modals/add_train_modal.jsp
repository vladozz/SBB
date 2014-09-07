<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Modal -->
<div class="modal fade" id="addTrainModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Add new train</h4>
            </div>
            <div class="jumbotron" id="err" style="display: none; background-color: #FFA0A0">
                <div class="container">
                    <h3 id="mes">Error</h3>
                </div>

            </div>
            <div class="modal-body">
                <form:form id="trainForm" class="form-horizontal" role="form" action="add" method="get" commandName="train">
                    <div class="form-group" id="commonId">
                        <label for="inputId" class="col-sm-3 control-label">ID</label>

                        <div class="col-sm-9">
                            <form:input path="id" type="text" class="form-control" id="inputId" placeholder="" readonly="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputNumber" class="col-sm-3 control-label">Number</label>

                        <div class="col-sm-9">
                            <form:input path="number" type="text" class="form-control" id="inputNumber" placeholder=""/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPQ" class="col-sm-3 control-label">Quantity of palaces</label>

                        <div class="col-sm-9">
                            <form:input path="placesQty" type="text" class="form-control" id="inputPQ" placeholder=""/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="modal-footer">

                            <button id ="submit" type="button" class="btn btn-primary" onclick="addTrain();">Add train</button>
                            <button id="close" type="button" class="btn btn-default" data-dismiss="modal" onclick="resetTrainForm()">Close</button>

                        </div>
                        <div id="tim"></div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/train.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-dialog.js'/>"></script>

