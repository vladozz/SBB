<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Modal -->
<div class="modal fade" id="addStationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Add new station</h4>
            </div>
            <div class="jumbotron" id="err" style="display: none; background-color: #FFA0A0">
                <div class="container">
                    <h3 id="mes">Error</h3>
                </div>

            </div>
            <div class="modal-body">
                <form:form id="stationForm" class="form-horizontal" role="form" action="add" method="get" commandName="station">
                    <div class="form-group" id="commonId">
                        <label for="inputId" class="col-sm-3 control-label">ID</label>

                        <div class="col-sm-9">
                            <form:input path="id" type="text" class="form-control" id="inputId" placeholder="" readonly="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputTitle" class="col-sm-3 control-label">Title</label>

                        <div class="col-sm-9">
                            <form:input path="title" type="text" class="form-control" id="inputTitle" placeholder=""/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputTZ" class="col-sm-3 control-label">Time zone</label>

                        <div class="col-sm-9">
                            <p><form:select path="timeZone" size="1" class = "form-control" id="inputTZ" >
                                <option value="GMT-12:00">GMT-12:00</option>
                                <option value="GMT-11:00">GMT-11:00</option>
                                <option value="GMT-10:00">GMT-10:00</option>
                                <option value="GMT-09:00">GMT-09:00</option>
                                <option value="GMT-08:00">GMT-08:00</option>
                                <option value="GMT-07:00">GMT-07:00</option>
                                <option value="GMT-06:00">GMT-06:00</option>
                                <option value="GMT-05:00">GMT-05:00</option>
                                <option value="GMT-04:00">GMT-04:00</option>
                                <option value="GMT-03:00">GMT-03:00</option>
                                <option value="GMT-02:00">GMT-02:00</option>
                                <option value="GMT-01:00">GMT-01:00</option>
                                <option value="GMT">GMT</option>
                                <option value="GMT+01:00">GMT+01:00</option>
                                <option value="GMT+02:00">GMT+02:00</option>
                                <option value="GMT+03:00">GMT+03:00</option>
                                <option value="GMT+04:00">GMT+04:00</option>
                                <option value="GMT+05:00">GMT+05:00</option>
                                <option value="GMT+06:00">GMT+06:00</option>
                                <option value="GMT+07:00">GMT+07:00</option>
                                <option value="GMT+08:00">GMT+08:00</option>
                                <option value="GMT+09:00">GMT+09:00</option>
                                <option value="GMT+10:00">GMT+10:00</option>
                                <option value="GMT+11:00">GMT+11:00</option>
                                <option value="GMT+12:00">GMT+12:00</option>
                                <option value="GMT+13:00">GMT+13:00</option>
                                <option value="GMT+14:00">GMT+14:00</option>
                            </form:select></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="modal-footer">

                            <button id ="submit" type="button" class="btn btn-primary" onclick="addStation();">Add station</button>
                            <button id="close" type="button" class="btn btn-default" data-dismiss="modal" onclick="resetStationForm();">Close</button>

                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>



<script type="text/javascript" src="<c:url value='/resources/js/station.js'/>"></script>


