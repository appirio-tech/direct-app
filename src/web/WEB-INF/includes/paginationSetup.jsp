<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript" src="/scripts/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $(".paginatedDataTable").dataTable({
            "bFilter": false,
            "bSort": false,
            "sPaginationType": "full_numbers"
        });
        $(".dataTables_info").addClass("hide");
        $(".dataTables_paginate .last").addClass("hide");
        $(".dataTables_paginate .first").addClass("hide");
        $(".previous").html("&nbsp;Prev&nbsp;");
        $(".next").html("&nbsp;Next&nbsp;");
        $(".dataTables_length").addClass("showPages");
    });
</script>
