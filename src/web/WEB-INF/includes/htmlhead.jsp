<%--
  - Author: Blues, GreatKevin
  - Version: 1.3
  - Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
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
  -
  - Version 1.3 (Module Assembly - TopCoder Cockpit Instant Search)
  - - Add css and js for the instant search feature
--%>
<title>TopCoder Cockpit</title>

<!-- Meta Tags -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- External CSS -->
<link rel="stylesheet" href="/css/direct/screen.svn228836.css" media="all" type="text/css" />
<link rel="stylesheet" href="/css/direct/launchcontest.svn228529.css" media="all" type="text/css"/>
<link rel="stylesheet" href="/css/direct/dashboard.svn228890.css" media="all" type="text/css" />
<link rel="stylesheet" href="/css/direct/thickbox.svn219034.css" media="all" type="text/css" />
<link rel="stylesheet" href="/css/direct/jScrollPane.svn219034.css" media="all" type="text/css"/>
<link rel="stylesheet" href="/css/direct/jquery-ui-1.7.2.custom.svn219034.css" media="all" type="text/css"/>
<link rel="stylesheet" href="/css/direct/modal.svn224391.css" media="all" type="text/css"/>
<link rel="stylesheet" href="/css/direct/datepicker.svn219034.css" media="all" type="text/css"/>
<link rel="stylesheet" href="/css/direct/instantSearch.svn224700.css" media="all" type="text/css" />
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/introjs.css"/> 

<!--[if IE 6]>
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie6.svn219021.css" />
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/homepage-ie6.svn219021.css"/>
<![endif]-->

<!--[if IE 7]>
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie7.svn224473.css"/>
<![endif]-->

<!--[if IE 8]>
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie8.svn219021.css"/>
<![endif]-->

<!--[if IE 9]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie9.svn219021.css"/>
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
<script type="text/javascript" src="/scripts/thickbox-compressed.svn186145.js"></script>
<script type="text/javascript" src="/scripts/jquery.mousewheel.svn224404.js"></script>
<script type="text/javascript" src="/scripts/jquery.em.js?v=176771"></script>
<script type="text/javascript" src="/scripts/jScrollPane.svn176771.js"></script>
<script type="text/javascript" src="/scripts/jquery.bgiframe.svn224404.js"></script>
<script type="text/javascript" src="/scripts/date.prev.svn179771.js"></script>
<script type="text/javascript" src="/scripts/date.svn185881.js"></script>
<script type="text/javascript" src="/scripts/common.svn227019.js"></script>
<script type="text/javascript" src="/scripts/jquery.datePicker.js?v=214829"></script>
<script type="text/javascript" src="/scripts/jquery.stylish-select.svn224404.js"></script>
<script type="text/javascript" src="/scripts/jquery.scrollfollow.js?v=179771"></script>
<script type="text/javascript" src="/scripts/jquery.blockUI.js?v=179771"></script>
<script type="text/javascript" src="/scripts/ajaxupload2.svn226962.js"></script>
<script type="text/javascript" src="/scripts/jquery.validate.svn185881.js"></script>
<script type="text/javascript" src="/scripts/ckeditor/ckeditor/ckeditor.svn224404.js"></script>
<script type="text/javascript" src="/scripts/jquery.autocomplete.js?v=183826"></script>
<script type="text/javascript" src="/scripts/jquery.hoverIntent.minified.js?v=215325"></script>
<script type="text/javascript" src="/scripts/jquery.cookie.js?v=215325"></script>

<script type="text/javascript" src="/scripts/dashboard.svn214921.js"></script>
<script type="text/javascript" src="/scripts/loadHelps.svn218254.js"></script>
<script type="text/javascript" src="/scripts/modalWindows.svn225388.js"></script>
<script type="text/javascript" src="/scripts/maintenance.svn227218.js"></script>
<script type="text/javascript" src="/scripts/instantSearch.svn228735.js"></script>
<script type="text/javascript" src="/scripts/intro.js"></script> 
