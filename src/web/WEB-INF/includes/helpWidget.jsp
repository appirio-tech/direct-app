<%--
  - Author: TCSASSEMBLER
  - Version: 1.0 (Release Assembly - TC Cockpit Sidebar Header and Footer Update)
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: The help widget appears on the right sidebar of TopCoder Cockpit.
  -
  - The contents of help widget are defined and loaded in the loadHelps.js.
--%>
<%--

<div id="helpCenterWidget">
    <h6>Help Center</h6>
    <ul class="tabList">
        <li class="firstTab"><a href="javascript:;" class="firstTab tab actived" id="FAQTab">FAQ</a></li>
        <li><a href="javascript:;" class="tab" id="videoTab">Videos</a></li>
        <li><a href="javascript:;" class="tab" id="tutorialTab">Tutorials</a></li>
        <li class="last"><a href="javascript:;" class="tab" id="moreTab">&gt;&gt;</a>
        </li>
        <li class="firstTab"><a href="javascript:;" class="tab tabMore hide" id="exampleTab">Example</a></li>
        <li class="last"><a href="javascript:;" class="tabMore hide" id="lessTab">&lt;&lt;</a></li>
    </ul>
    <div class="clear"></div>

    <!-- the contests of the help widget will be loaded by javascript -->

    <div class="tabContent" id="FAQTabContent">
        <ul>
            <li><a href="javascript:;" onclick="window.open('https://www.topcoder.com/help/?p=622')">How to monitor
                Forum Activity?</a></li>
            <li><a href="javascript:;" onclick="window.open('https://www.topcoder.com/help/p=301/?subject=startproject')">Who are the copilots?</a></li>
            <li><a href="javascript:;" onclick="window.open('https://www.topcoder.com/help/?p=618')">How to Extend
                deadline?</a></li>
            <li><a href="javascript:;" onclick="window.open('https://www.topcoder.com/help/?p=616')">How to increase
                Prize?</a></li>
            <li><a href="javascript:;" onclick="window.open('https://www.topcoder.com/help/?p=614')">What can I do once
                a contest is running?</a></li>
            <li><a href="javascript:;" onclick="window.open('https://www.topcoder.com/help/?p=606')">What types of
                forums does TopCoder provide?</a></li>
            <li><a href="javascript:;" onclick="window.open('https://www.topcoder.com/help/?p=604')">How do I know a
                member has posted in forum?</a></li>
        </ul>
        <a href="" target="_blank" class="viewAllLink">View All</a>
    </div>
    <!--End FAQTabContent-->
    <div class="tabContent hide" id="videoTabContent">
        <ul>

        </ul>

        <a href="" target="_blank" class="viewAllLink">View All</a>
    </div>
    <!--End videoTabContent-->
    <div class="tabContent hide" id="tutorialTabContent">
        <ul>

        </ul>

        <a href="" target="_blank" class="viewAllLink">View All</a>
    </div>
    <!--End tutorialTabContent-->
    <div class="tabContent hide" id="exampleTabContent">
        <ul>

        </ul>
        <a href="" target="_blank" class="viewAllLink">View All</a>
    </div>

    </br>
    <div>
        <a href="javascript:;" onclick="window.open('https://www.topcoder.com/direct/TopCoderRoadmap')"><h6>TopCoder Roadmap</h6></a>
    </div>
    </br>
    
    <!--End exampleTabContent-->
    <p class="contactUs">
        <a href="javascript:;" onclick="window.open('https://www.topcoder.com/help/template-help/')" class="darkenBtn"
           title="CONTACT US"><span class="right"><span class="middle">CONTACT US</span></span></a>
    </p>

    <div id="helpItemTemplate" class="hide">
        <li><a href="javascript:;" onclick="window.open('{0}')">{1}</a></li>
    </div>
</div>
--%>