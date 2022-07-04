<%--
  - Author: Blues, GreatKevin, Veve
  - Version: 1.5
  - Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
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
  -
  - Version 1.4 (TC Direct Rebranding Assembly Project and Contest related pages)
  - Use new font "Source Sans Pro" for the page
  -
  - Version 1.5 (TopCoder Direct - Change Right Sidebar to pure Ajax)
  - Add the right sidebar script reference
--%>
<title>Topcoder Direct</title>

<!-- Meta Tags -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/customFont.css"/>
<!-- External CSS -->
<link rel="stylesheet" href="/css/direct/screen.css" media="all" type="text/css" />
<link rel="stylesheet" href="/css/direct/launchcontest.css" media="all" type="text/css"/>
<link rel="stylesheet" href="/css/direct/dashboard.css" media="all" type="text/css" />
<link rel="stylesheet" href="/css/direct/thickbox.css" media="all" type="text/css" />
<link rel="stylesheet" href="/css/direct/jScrollPane.css" media="all" type="text/css"/>
<link rel="stylesheet" href="/css/direct/jquery-ui-1.7.2.custom.css" media="all" type="text/css"/>
<link rel="stylesheet" href="/css/direct/modal.css" media="all" type="text/css"/>
<link rel="stylesheet" href="/css/direct/datepicker.css" media="all" type="text/css"/>
<link rel="stylesheet" href="/css/direct/instantSearch.css" media="all" type="text/css" />
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/introjs.css"/>

<!--[if IE 6]>
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie6.css" />
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/homepage-ie6.css"/>
<![endif]-->

<!--[if IE 7]>
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie7.css"/>
<![endif]-->

<!--[if IE 8]>
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie8.css"/>
<![endif]-->

<!--[if IE 9]>
<link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie9.css"/>
<![endif]-->

<jsp:include page="serverConfiguration.jsp"/>

<!-- External javascript -->
<script type="text/javascript">
    //<![CDATA[
    window.CKEDITOR_BASEPATH='/scripts/ckeditor/ckeditor/';
    //]]>
</script>

<script type="text/javascript" src="/scripts/jquery-1.4.1.min.js"></script>
<script type="text/javascript" src="/scripts/jquery-ui-1.7.2.custom.min.js"></script>

<script type="text/javascript" src="/scripts/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="/scripts/thickbox-compressed.js"></script>
<script type="text/javascript" src="/scripts/jquery.mousewheel.js"></script>
<script type="text/javascript" src="/scripts/jquery.em.js"></script>
<script type="text/javascript" src="/scripts/jScrollPane.js"></script>
<script type="text/javascript" src="/scripts/jquery.bgiframe.js"></script>
<script type="text/javascript" src="/scripts/date.prev.js"></script>
<script type="text/javascript" src="/scripts/date.js"></script>
<script type="text/javascript" src="/scripts/common.js"></script>
<script type="text/javascript" src="/scripts/jquery.datePicker.js"></script>
<script type="text/javascript" src="/scripts/jquery.stylish-select.js"></script>
<script type="text/javascript" src="/scripts/jquery.scrollfollow.js"></script>
<script type="text/javascript" src="/scripts/jquery.blockUI.js"></script>
<script type="text/javascript" src="/scripts/ajaxupload2.js"></script>
<script type="text/javascript" src="/scripts/jquery.validate.js"></script>
<script type="text/javascript" src="/scripts/ckeditor/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/scripts/jquery.hoverIntent.minified.js"></script>
<script type="text/javascript" src="/scripts/jquery.cookie.js"></script>

<script type="text/javascript" src="/scripts/dashboard.js"></script>
<script type="text/javascript" src="/scripts/loadHelps.js"></script>
<script type="text/javascript" src="/scripts/modalWindows.js"></script>
<script type="text/javascript" src="/scripts/maintenance.js"></script>
<script type="text/javascript" src="/scripts/instantSearch.js"></script>
<script type="text/javascript" src="/scripts/intro.js"></script>
<script type="text/javascript" src="/scripts/jquery.jqtransform.js" ></script>
<script type="text/javascript" src="/scripts/rightSidebar.js" ></script>
<link rel="stylesheet" href="/css/direct/jqtransform.css" media="all" type="text/css"/>
