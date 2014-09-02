<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<!-- Modal -->
<div class="modal fade" id="addStationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Add new train</h4>
            </div>
            <div class="modal-body">
                <form:form class="form-horizontal" role="form" action="add" method="post" commandName="station">
                    <div class="form-group">
                        <label for="inputTitle" class="col-sm-3 control-label">Title</label>

                        <div class="col-sm-9">
                            <form:input path="title" type="text" class="form-control" id="inputTitle" placeholder=""/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputTZ" class="col-sm-3 control-label">Time zone (as a number)</label>

                        <div class="col-sm-9">
                            <form:input path="timeZone" type="text" class="form-control" id="inputTZ" placeholder=""/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Add station</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>