<%--
  - Author: Blues, TCSASSEMBLER
  - Version: 1.2.1
  - Copyright (C) 2010-2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: HTML header file linking to the CSS and javascript files. It is included by all the other JSP pages.
  -
  - Version 1.1 - Direct - Repost and New Version Assembly Change Note
  - 1) bump common.js version.
  -
  - Version 1.2 - Release Assembly - TC Cockpit Sidebar Header and Footer Update
  - 1) included the loadHelps.js which loads the contents of help widget in right sidebar.
  -
  - Version 1.2.1 (Release Assembly - TC Direct Cockpit Release Three version 1.0)
  - 1) Add a new JS jquery.hoverIntent.minified.js which helps to control the mouse hover event trigger time
  -
  - Version 1.2.2 POC Assembly - Change Rich Text Editor Controls For TopCoder Cockpit note
  - remove include of TinyMCE, replaced with CKEditor.
--%>
<title>TopCoder Cockpit</title>

<!-- Meta Tags -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- External CSS -->
<link rel="stylesheet" href="/css/direct/screen.css?v=214495" media="all" type="text/css" />
<link rel="stylesheet" href="/css/direct/launchcontest.css?v=215011" media="all" type="text/css"/>
<link rel="stylesheet" href="/css/direct/dashboard.css?v=215352" media="all" type="text/css" />
<link rel="stylesheet" href="/css/direct/thickbox.css?v=192822" media="all" type="text/css" />
<link rel="stylesheet" href="/css/direct/jScrollPane.css?v=176771" media="all" type="text/css"/>
<link rel="stylesheet" href="/css/direct/jquery-ui-1.7.2.custom.css?v=206355" media="all" type="text/css"/>
<link rel="stylesheet" href="/css/direct/modal.css?v=211772" media="all" type="text/css"/>
<link rel="stylesheet" href="/css/direct/datepicker.css?v=211688" media="all" type="text/css"/>

<!--[if IE 6]>
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie6.css?v=203928" />
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/homepage-ie6.css?v=176771"/>
<![endif]-->

<!--[if IE 7]>
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie7.css?v=215325"/>
<![endif]-->

<!--[if IE 8]>
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie8.css?v=203310"/>
<![endif]-->

<!--[if IE 9]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie9.css?v=203310"/>
<![endif]-->

<!-- External javascript -->
<script type="text/javascript">
//<![CDATA[
window.CKEDITOR_BASEPATH='/scripts/ckeditor/ckeditor/';
//]]>
</script>

<script type="text/javascript" src="/scripts/jquery-1.4.1.min.js?v=176771"></script>
<script type="text/javascript" src="/scripts/jquery-ui-1.7.2.custom.min.js?v=179771"></script>

<script type="text/javascript" src="/scripts/jquery.tablesorter.min.js?v=176771"></script>
<script type="text/javascript" src="/scripts/thickbox-compressed.js?v=186145"></script>
<script type="text/javascript" src="/scripts/jquery.mousewheel.js?v=176771"></script>
<script type="text/javascript" src="/scripts/jquery.em.js?v=176771"></script>
<script type="text/javascript" src="/scripts/jScrollPane.js?v=176771"></script>
<script type="text/javascript" src="/scripts/jquery.bgiframe.js?v=207894"></script>
<script type="text/javascript" src="/scripts/date.prev.js?v=179771"></script>
<script type="text/javascript" src="/scripts/date.js?v=185881"></script>
<script type="text/javascript" src="/scripts/common.js?v=215290"></script>
<script type="text/javascript" src="/scripts/jquery.datePicker.js?v=214829"></script>
<script type="text/javascript" src="/scripts/jquery.stylish-select.js?v=188719"></script>
<script type="text/javascript" src="/scripts/jquery.scrollfollow.js?v=179771"></script>
<script type="text/javascript" src="/scripts/jquery.blockUI.js?v=179771"></script>
<script type="text/javascript" src="/scripts/ajaxupload2.js?v=209582"></script>
<script type="text/javascript" src="/scripts/jquery.validate.js?v=179836"></script>
<script type="text/javascript" src="/scripts/ckeditor/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/scripts/jquery.autocomplete.js?v=183826"></script>
<script type="text/javascript" src="/scripts/jquery.hoverIntent.minified.js?v=215325"></script>
<script type="text/javascript" src="/scripts/jquery.cookie.js?v=215325"></script>

<script type="text/javascript" src="/scripts/dashboard.js?v=215352"></script>
<script type="text/javascript" src="/scripts/loadHelps.js?v=215005"></script>
<script type="text/javascript" src="/scripts/modalWindows.js?v=211035"></script>
<script type="text/javascript" src="/scripts/maintenance.js?v=2146111"></script>
