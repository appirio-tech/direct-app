<%--
  - Author: isv
  - Version: 1.0 (Submission Viewer Release 1 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders the pagination controls to be displayed on Studio Submissions Grid
  - and List views.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="pagination slotTitle">
    <div class="pages">
        <span class="prev">Prev</span><!-- we are on the first page so the prev link must be deactive -->
        <a href="javascript:;" class="current">1</a>
        <a href="javascript:;">2</a>
        <a href="javascript:;">3</a>
        <a href="javascript:;">4</a>
        <a href="javascript:;">5</a>
        ...
        <a href="javascript:;" class="next">Next</a>
    </div>
    <!-- End .pages -->
    <div class="showPages"><!-- number of rows that can be displayed on the same page -->
        <span class="labelShowPage">Show:</span>
        <span class="labelPerPageClear">per page</span>
    </div>
    <!-- End .showPages -->
</div>
<!-- End .pagination -->
