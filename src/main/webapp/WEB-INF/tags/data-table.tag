<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="table" type="java.lang.String" %>
<%@ attribute name="menu" type="java.lang.Boolean" %>

<link rel="stylesheet" href="${base}/webjars/datatables/1.10.19/css/dataTables.bootstrap4.min.css">
<script src="${base}/webjars/datatables/1.10.19/js/jquery.dataTables.min.js"></script>
<script src="${base}/webjars/datatables/1.10.19/js/dataTables.bootstrap4.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        var dataTables = $('.data-table');
        dataTables.each(function () {
            var table = $(this);
            table.dataTable({
                "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]]
            });

            var menuItems = $('.data-table-menu');
            if (menuItems.length) {
                var filter = $($('#' + table.attr('id') + '_filter').children()[0]);
                filter.css('display', 'inline-flex');
                filter.append(
                    '<div>' +
                    '    <button class="btn navbar-light" data-toggle="dropdown" style="padding: 1px 3px;">' +
                    '        <span class="navbar-toggler-icon"></span>' +
                    '    </button>' +
                    '    <div class="dropdown-menu dropdown-menu-right" ></div>' +
                    '</div>');

                var menuContainer = filter.find('.dropdown-menu');
                menuItems.each(function () { menuContainer.append($(this)) });
            }
        });
    });
</script>