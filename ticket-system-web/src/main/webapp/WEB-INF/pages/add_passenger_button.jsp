<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
    Add new passenger
</button>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="inputFN" class="col-sm-3 control-label">First name</label>

                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="inputFN" placeholder="First name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputLN" class="col-sm-3 control-label">Last name</label>

                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="inputLN" placeholder="Last name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputDate" class="col-sm-3 control-label">Date</label>

                        <div class="col-sm-6">
                            <input type="date" class="form-control" id="inputDate">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Add passenger</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>