<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<!-- Modal -->
<div class="modal fade" id="addTrainModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Add new train</h4>
            </div>
            <div class="modal-body">
                <form:form class="form-horizontal" role="form" action="add" method="post" commandName="train">
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
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Add train</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>