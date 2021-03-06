<div class="modal fade" id="confirm-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Confirmation Required</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you sure you want to proceed?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="confirmChanges">Yes</button>
            </div>
        </div>
    </div>
</div>

<script>
    var actions = $('.confirm-action');
    actions.each(function () {
        var element = $(this);
        var callback = element.attr( 'onclick');
        element.attr('data-toggle', 'modal')
            .attr('data-target', '#confirm-modal')
            .attr( 'onclick' , '');

        element.on('click', function () {
            $('#confirmChanges').attr( 'onclick' , callback);
        });
    });
</script>