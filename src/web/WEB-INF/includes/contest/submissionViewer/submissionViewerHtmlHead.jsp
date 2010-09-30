<%--
  - Author: isv, flexme, TCSDEVELOPER
  - Version 1.3 (Direct Submission Viewer Release 4 ) change notes: Added some JS, CSS files for NOWINNER pages.
  - Version 1.2 (Direct Submission Viewer Release 3 ) change notes: Added some JS, CSS files for CHECKOUT page.
  - Version 1.1 (Direct Submission Viewer Release 2 ) change notes: Added some JS, CSS files for dialog, block UI.
  -
  - Version: 1.3
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders HTML HEAD area to be included into Studio Submissions Grid, List and
  - Single views.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<title>TopCoder Direct</title>

<!-- Meta Tags -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- External CSS -->
<link rel="stylesheet" href="/css/screen.css" media="all" type="text/css" />
<link rel="stylesheet" href="/css/dashboard.css" media="all" type="text/css" />
<link rel="stylesheet" href="/css/modal.css" media="all" type="text/css" />
<link rel="stylesheet" href="/css/jScrollPane.css" media="all" type="text/css" />
<link rel="stylesheet" href="/css/thickbox.css" media="all" type="text/css" />
<link rel="stylesheet" href="/css/jquery.selectbox.css"  type="text/css" />
<s:if test="formData.viewType.toString() == 'SINGLE' || formData.viewType.toString() == 'CHECKOUT'">
<link rel="stylesheet" href="/css/jquery-ui-1.7.2.custom.css"  type="text/css" />
</s:if>
<!--[if IE 6]>
<link rel="stylesheet" type="text/css" media="screen" href="/css/dashboard-ie6.css" />
<![endif]-->
<s:if test="formData.viewType.toString() == 'NOWINNER'">
<link rel="stylesheet" href="/css/nowinner.css" media="all" type="text/css" />
</s:if>

<!-- External javascript -->
<script type="text/javascript" src="/scripts/jquery-1.4.1.min.js"></script> 
<s:if test="formData.viewType.toString() == 'SINGLE' || formData.viewType.toString() == 'CHECKOUT'">
<script type="text/javascript" src="/scripts/jquery-ui-1.7.2.custom.min.js"></script>
</s:if>
<script type="text/javascript" src="/scripts/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="/scripts/fullcalendar.min.js"></script>
<script type="text/javascript" src="/scripts/jquery.mousewheel.js"></script>
<script type="text/javascript" src="/scripts/jquery.em.js"></script>
<script type="text/javascript" src="/scripts/jScrollPane.js"></script>
<script type="text/javascript" src="/scripts/jquery.ba-resize.min.js"></script>

<script type="text/javascript" src="/scripts/dashboard.js"></script>

<script type="text/javascript" src="/scripts/jquery.stylish-select.js"></script>
<script type="text/javascript" src="/scripts/date.js"></script>
<script type="text/javascript" src="/scripts/jquery.datePicker.js"></script>
<script type="text/javascript" src="/scripts/jquery.bgiframe.js"></script>
<script type="text/javascript" src="/scripts/jquery.scrollfollow.js"></script>

<script type="text/javascript" src="/scripts/jquery.select.js"></script>
<script type="text/javascript" src="/scripts/studio.js"></script>
<script type="text/javascript" src="/scripts/ui.core.js"></script>  
<script type="text/javascript" src="/scripts/ui.widget.js"></script>
<script type="text/javascript" src="/scripts/ui.mouse.js"></script>
<script type="text/javascript" src="/scripts/ui.sortable.js"></script>
<script type="text/javascript" src="/scripts/ui.draggable.js"></script>
<script type="text/javascript" src="/scripts/ui.droppable.js"></script>
<script type="text/javascript" src="/scripts/ui.accordion.js"></script>
<script type="text/javascript" src="/scripts/thickbox-compressed.js"></script>


<s:if test="formData.viewType.toString() == 'GRID'">
    <script type="text/javascript" src="/scripts/bank-grid.js"></script>
</s:if>
<s:if test="formData.viewType.toString() == 'LIST'">
    <script type="text/javascript" src="/scripts/bank-list.js"></script>
</s:if>
<s:if test="formData.viewType.toString() == 'SINGLE'">
    <script type="text/javascript" src="/scripts/jquery-cycle.js"></script>
    <script type="text/javascript" src="/scripts/jquery-easing.js"></script>
    <script type="text/javascript" src="/scripts/bank-single.js"></script>
    <script type="text/javascript" src="/scripts/common.js"></script>
    <script type="text/javascript" src="/scripts/jquery.blockUI.js"></script>
    <!--[if IE 6]>
    <script src="/scripts/DD_belatedPNG.js" type="text/javascript"></script>
    <script src="/scripts/ie6.js" type="text/javascript"></script>
    <![endif]-->
</s:if>
<s:if test="formData.viewType.toString() == 'CHECKOUT'">
    <script type="text/javascript" src="/scripts/common.js"></script>
    <script type="text/javascript" src="/scripts/jquery.blockUI.js"></script>
    <script type="text/javascript" src="/scripts/checkout.js"></script>
</s:if>
<script type="text/javascript" src="/scripts/json2.js"></script>
<script type="text/javascript" src="/scripts/jquery.cookie.js"></script>