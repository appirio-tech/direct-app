<%--
 - Author: pvmagacho
 - Version: 1.0 (System Assembly - Direct TopCoder Scorecard Tool Integration)
 - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 -
 - Description: The right side of the topcoder cockpit scorecard page.
--%>
<div id="area2" class="dashboardPage">
	<!-- the right column -->
	<div class="box">
		<a href="/direct/copilot/launchCopilotContest"
			class="button2">Get A Copilot</a> <br /> <a
			href="/direct/launch/home" class="button2">Launch
			New Contest</a>
	</div>
	<span id="contextPath" value="${request.contextPath}" />
	<!-- End .box -->
	<div class="box">
		<h2 class="title">My Projects</h2>
		<a href="/direct/createProjectView.action" class="button2">Create
			a New Project</a>

		<div class="contestsContainer">
			<p class="label">Select a Project</p>
			<div class="inputSelect">
				<!--TCCC-2398-->
				<input type="text" name="" value="Select a Project" id=""
					onfocus="showHideList();" onkeyup="filterProject();" /> <a
					href="javascript:;" onclick="showHideList();" class="selectIco"></a>
			</div>
			<div id="dropDown1" class="contestsDropDown"
				style="height: 200px; overflow: auto;">
				<!-- when the user click the selectIco button we will show this dropdown list -->
				<ul>

				</ul>
			</div>
			<!-- End .contestsDropDown -->
			<p class="projectArchive">
				<a href="javascript:;">Projects Archive</a>
			</p>
		</div>
		<!-- End .contestsContainer -->
	</div>
	<!-- End .box -->

	<!-- help widget -->
	<div id="helpCenterWidget">
		<h6>Help Center</h6>
		<ul class="tabList">
			<li class="firstTab"><a href="javascript:;"
				class="firstTab tab actived" id="FAQTab">FAQ</a></li>
			<li><a href="javascript:;" class="tab" id="videoTab">Videos</a>
			</li>
			<li><a href="javascript:;" class="tab" id="tutorialTab">Tutorials</a>
			</li>
			<li class="last"><a href="javascript:;" class="tab" id="moreTab">&gt;&gt;</a>
			</li>
			<li class="firstTab"><a href="javascript:;"
				class="tab tabMore hide" id="exampleTab">Example</a></li>
			<li class="last"><a href="javascript:;" class="tabMore hide"
				id="lessTab">&lt;&lt;</a></li>
		</ul>
		<div class="clear"></div>

		<!-- the contests of the help widget will be loaded by javascript -->

		<div class="tabContent" id="FAQTabContent">
			<ul>
				<li><a href="javascript:;"
					onclick="window.open('https://topcoder.com/home/help/?p=622')">How
						to monitor Forum Activity?</a></li>
				<li><a href="javascript:;"
					onclick="window.open('https://topcoder.com/home/help/?p=620')">What
						is TopCoder forum?</a></li>
				<li><a href="javascript:;"
					onclick="window.open('https://topcoder.com/home/help/?p=618')">How
						to Extend deadline?</a></li>
				<li><a href="javascript:;"
					onclick="window.open('https://topcoder.com/home/help/?p=616')">How
						to increase Prize?</a></li>
				<li><a href="javascript:;"
					onclick="window.open('https://topcoder.com/home/help/?p=614')">What
						can I do once a contest is running?</a></li>
				<li><a href="javascript:;"
					onclick="window.open('https://topcoder.com/home/help/?p=606')">What
						types of forums does TopCoder provide?</a></li>
				<li><a href="javascript:;"
					onclick="window.open('https://topcoder.com/home/help/?p=604')">How
						do I know a member has posted in forum?</a></li>
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
		<!--End exampleTabContent-->

		<p class="contactUs">
			<a href="javascript:;"
				onclick="window.open('https://topcoder.com/home/lets-talk/')"
				class="darkenBtn" title="CONTACT US"><span class="right"><span
					class="middle">CONTACT US</span> </span> </a>
		</p>

		<div id="helpItemTemplate" class="hide">
			<li><a href="javascript:;" onclick="window.open('{0}')">{1}</a>
			</li>
		</div>
	</div>
</div>