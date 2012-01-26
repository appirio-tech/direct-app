<%--
  - Author: isv, flexme, minhu
  -
  - Version 1.4 (Release Assembly - TopCoder Cockpit Submission Viewer Revamp) change notes:
  -   Moved bank-single to its owning jsp to avoid JS initializing issue.
  - Version 1.3 (Direct Submission Viewer Release 4 ) change notes: Added some JS, CSS files for NOWINNER pages.
  - Version 1.2 (Direct Submission Viewer Release 3 ) change notes: Added some JS, CSS files for CHECKOUT page.
  - Version 1.1 (Direct Submission Viewer Release 2 ) change notes: Added some JS, CSS files for dialog, block UI.
  -
  - Version: 1.4
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders HTML HEAD area to be included into Studio Submissions Grid, List and
  - Single views.
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<title>TopCoder Direct</title>

<!-- Meta Tags -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- External CSS -->
<link rel="stylesheet" href="/css/screen.css?v=211772" media="all" type="text/css" />
<link rel="stylesheet" href="/css/dashboard.css?v=212170" media="all" type="text/css" />
<link rel="stylesheet" href="/css/modal.css?v=211772" media="all" type="text/css" />
<link rel="stylesheet" href="/css/jScrollPane.css?v=176771" media="all" type="text/css" />
<link rel="stylesheet" href="/css/thickbox.css?v=192822" media="all" type="text/css" />
<link rel="stylesheet" href="/css/jquery.selectbox.css?v=185283"  type="text/css" />
<s:if test="formData.viewType.toString() == 'SINGLE' || formData.viewType.toString() == 'CHECKOUT'">
<link rel="stylesheet" href="/css/jquery-ui-1.7.2.custom.css?v=206355"  type="text/css" />
</s:if>
<!--[if IE 6]>
<link rel="stylesheet" type="text/css" media="screen" href="/css/dashboard-ie6.css?v=203928" />
<![endif]-->
<s:if test="formData.viewType.toString() == 'NOWINNER'">
<link rel="stylesheet" href="/css/nowinner.css?v=189618" media="all" type="text/css" />
</s:if>

<!-- External javascript -->
<script type="text/javascript" src="/scripts/jquery-1.4.1.min.js?v=176771"></script> 
<s:if test="formData.viewType.toString() == 'SINGLE' || formData.viewType.toString() == 'CHECKOUT'">
</s:if>
<script type="text/javascript" src="/scripts/jquery-ui-1.7.2.custom.min.js?v=179771"></script>
<script type="text/javascript" src="/scripts/common.js?v=211772"></script>
<script type="text/javascript" src="/scripts/jquery.tablesorter.min.js?v=176771"></script>
<script type="text/javascript" src="/scripts/fullcalendar.min.js?v=205096"></script>
<script type="text/javascript" src="/scripts/jquery.mousewheel.js?v=176771"></script>
<script type="text/javascript" src="/scripts/jquery.em.js?v=176771"></script>
<script type="text/javascript" src="/scripts/jScrollPane.js?v=176771"></script>
<script type="text/javascript" src="/scripts/jquery.ba-resize.min.js?v=176771"></script>

<script type="text/javascript" src="/scripts/dashboard.js?v=212170"></script>

<script type="text/javascript" src="/scripts/jquery.stylish-select.js?v=188719"></script>
<script type="text/javascript" src="/scripts/date.js?v=185881"></script>
<script type="text/javascript" src="/scripts/date.prev.js?v=179771"></script>
<script type="text/javascript" src="/scripts/jquery.datePicker.js?v=209150"></script>
<script type="text/javascript" src="/scripts/jquery.bgiframe.js?v=207894"></script>
<script type="text/javascript" src="/scripts/jquery.scrollfollow.js?v=179771"></script>

<script type="text/javascript" src="/scripts/jquery.select.js?v=185283"></script>
<script type="text/javascript" src="/scripts/studio.js?v=211035"></script>
<script type="text/javascript" src="/scripts/ui.core.js?v=185283"></script>  
<script type="text/javascript" src="/scripts/ui.widget.js?v=185283"></script>
<script type="text/javascript" src="/scripts/ui.mouse.js?v=185283"></script>
<script type="text/javascript" src="/scripts/ui.sortable.js?v=185283"></script>
<script type="text/javascript" src="/scripts/ui.draggable.js?v=185283"></script>
<script type="text/javascript" src="/scripts/ui.droppable.js?v=185283"></script>
<script type="text/javascript" src="/scripts/ui.accordion.js?v=185283"></script>
<script type="text/javascript" src="/scripts/thickbox-compressed.js?v=186145"></script>

<s:if test="formData.viewType.toString() == 'GRID'">
    <script type="text/javascript" src="/scripts/bank-grid.js?v=211035"></script>
</s:if>
<s:if test="formData.viewType.toString() == 'LIST'">
    <script type="text/javascript" src="/scripts/bank-list.js?v=211035"></script>
</s:if>
<s:if test="formData.viewType.toString() == 'SINGLE'">
    <script type="text/javascript" src="/scripts/jquery-cycle.js?v=185283"></script>
    <script type="text/javascript" src="/scripts/jquery-easing.js?v=185283"></script>
    <script type="text/javascript" src="/scripts/common.js?v=211772"></script>
    <script type="text/javascript" src="/scripts/jquery.blockUI.js?v=179771"></script>
    <!--[if IE 6]>
    <script src="/scripts/DD_belatedPNG.js?v=185283" type="text/javascript"></script>
    <script src="/scripts/ie6.js?v=205148" type="text/javascript"></script>
    <![endif]-->
</s:if>
<s:if test="formData.viewType.toString() == 'CHECKOUT'">
    <script type="text/javascript" src="/scripts/common.js?v=211772"></script>
    <script type="text/javascript" src="/scripts/jquery.blockUI.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/checkout.js?v=209530"></script>
</s:if>
<s:if test="formData.viewType.toString() == 'CHECKOUT_CONFIRM'">
    <script type="text/javascript" src="/scripts/common.js?v=211772"></script>
    <script type="text/javascript" src="/scripts/jquery.blockUI.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/confirmCheckout.js?v=206299"></script>
</s:if>
<script type="text/javascript" src="/scripts/json2.js?v=186145"></script>
<script type="text/javascript" src="/scripts/jquery.cookie.js?v=187251"></script>

<script type="text/javascript" src="/scripts/modalWindows.js?v=211035"></script>

