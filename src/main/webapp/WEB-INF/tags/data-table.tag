<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="table" type="java.lang.String" %>
<%@ attribute name="options" type="java.lang.Boolean" %>

<c:if test="${!table.isEmpty()}">
    <c:if test="${options}">
        <div id="${table}-menu">
            <button class="btn navbar-light" data-toggle="dropdown" style="padding: 1px 3px;">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="dropdown-menu dropdown-menu-right" >
                <jsp:doBody/>
            </div>
        </div>
    </c:if>

    <link rel="stylesheet" href="${base}/webjars/datatables/1.10.19/css/dataTables.bootstrap4.min.css">
    <script src="${base}/webjars/datatables/1.10.19/js/jquery.dataTables.min.js"></script>
    <script src="${base}/webjars/datatables/1.10.19/js/dataTables.bootstrap4.min.js"></script>
    <script>
        var options = $('#${table} .table-options');
        $('#${table}').dataTable({
            "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
            "columnDefs": [options.length ? { "orderable": false, "targets": -1 } : {}]
        });
        var menu = $('#${table}-menu');
        if (menu.length) {
            var filter = $('#${table}_filter').children()[0];
            filter.setAttribute('style', 'display: inline-flex;');
            filter.appendChild(menu[0]);
        }
    </script>
</c:if>