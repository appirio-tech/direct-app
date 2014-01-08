<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:projectPageType tab="gameplan"/>
    <ui:contestPageType tab="details"/>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                    <div class="area1Content">
                    <div class="currentPage">
                        <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                        <a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>"/></a> &gt;
                        <strong><s:property value="viewData.contestStats.contest.title"/></strong>
                    </div>
                    <div class="areaHeader">
                        <h2 class="title contestTitle" style="background:url('/images/<s:property value="viewData.contest.contestType.letter"/>.png') no-repeat scroll left center transparent">
                       
                            <s:property value="viewData.contestStats.contest.title"/></h2>
                    </div><!-- End .areaHeader -->

                    <div class="container2 tabs3Container">

                        <jsp:include page="includes/contest/tabs.jsp"/>

                        <div class="container2Left"><div class="container2Right"><div class="container2Bottom">
                            <div><div>
                                <div class="container2Content">

                                        <div class="details">

                                            <div class="caption">
                                                <div class="captionLeft"><div class="captionRight">
                                                    <div class="captionInner">
                                                        <h2>Challenge Type</h2>
                                                    </div>
                                                </div></div>
                                            </div><!-- End .caption -->

                                            <div class="detailsContent">
                                                <div class="message">
                                                    <div class="messageTop"><div class="messageBottom"><div class="messageLeft"><div class="messageRight">
                                                        <div class="messageTopLeft"><div class="messageTopRight"><div class="messageBottomLeft"><div class="messageBottomRight">
                                                            <div class="messageInner">
                                                                <p>You can start this project with a various type of
                                                                    challenges, and you could add more later from your
                                                                    Projects tab. Start with something with easier
                                                                    thing</p>
                                                            </div><!-- .messageInner -->
                                                        </div></div></div></div>
                                                    </div></div></div></div>
                                                </div><!-- End .message -->

                                                <div class="tabs4">
                                                    <ul>
                                                        <li class="on">
                                                            <a href="javascript:alert('To be implemented by sub-sequent assemblies');"><span>Studio Challenge</span></a>
                                                        </li>
                                                    </ul>
                                                </div><!-- End .tabs4 -->

                                                <div class="tabs4Content">
                                                    <div><!-- Content of the first tab -->
                                                        <h2>Web Page Design</h2>
                                                        <p>A single or several pages of web design (without HTML coding).</p>

                                                        <div class="message">
                                                            <div class="messageTop"><div class="messageBottom"><div class="messageLeft"><div class="messageRight">
                                                                <div class="messageTopLeft"><div class="messageTopRight"><div class="messageBottomLeft"><div class="messageBottomRight">
                                                                    <div class="messageInner">
                                                                        <p>Description: THIS IS WILL BE AUTOMATICALLY
                                                                            RELATED TO EACH USER SELECTION. Icon design
                                                                            for print, web, mobile or desktop
                                                                            applications. it's best to ask members to
                                                                            design no more than 10-15 icons at one time
                                                                            unless the icons are extremely simple. You
                                                                            will be able to set the timeline and prizes
                                                                            in the next step.</p>
                                                                    </div><!-- .messageInner -->
                                                                </div></div></div></div>
                                                            </div></div></div></div>
                                                        </div><!-- End .message -->

                                                    </div>
                                                </div><!-- End .tabs4Content-->

                                            </div><!-- End .detailsContent -->

                                        </div><!-- End .details -->

                                        <div class="details">

                                            <div class="caption">
                                                <div class="captionLeft"><div class="captionRight">
                                                    <div class="captionInner">
                                                        <h2>Round Type &amp; Schedule</h2>
                                                    </div>
                                                </div></div>
                                            </div><!-- End .caption -->

                                            <div class="detailsContent">
                                                <div class="message">
                                                    <div class="messageTop"><div class="messageBottom"><div class="messageLeft"><div class="messageRight">
                                                        <div class="messageTopLeft"><div class="messageTopRight"><div class="messageBottomLeft"><div class="messageBottomRight">
                                                            <div class="messageInner">
                                                                <p>A challenge can be run in single round or multiple rounds. Both has the same treatment, except that in multiple rounds you encourage (and reward) competitors to submit at a specified checkpoint date and you give them prizes and feedbacks, for some (usually top 5) or all submissions. Multiple round always useful for some complex projects.</p>
                                                            </div><!-- .messageInner -->
                                                        </div></div></div></div>
                                                    </div></div></div></div>
                                                </div><!-- End .message -->

                                                <div class="tabs4">
                                                    <ul>
                                                        <li class="on"><a href="javascript:alert('To be implemented by sub-sequent assemblies');"><span>Multiple Rounds</span></a></li>
                                                    </ul>
                                                </div><!-- End .tabs4 -->

                                                <div class="tabs4Content">
                                                    <div><!-- Content of the first tab -->

                                                        <div class="message">
                                                            <div class="messageTop"><div class="messageBottom"><div class="messageLeft"><div class="messageRight">
                                                                <div class="messageTopLeft"><div class="messageTopRight"><div class="messageBottomLeft"><div class="messageBottomRight">
                                                                    <div class="messageInner">
                                                                        <p>Challenge will be run in multi-rounds. There are several configurations that allows you to tailor your challenges for specific need and proper target. You can set each round timeline as well as prizes.</p>
                                                                    </div><!-- .messageInner -->
                                                                </div></div></div></div>
                                                            </div></div></div></div>
                                                        </div><!-- End .message -->

                                                        <table class="projectStats" cellpadding="0" cellspacing="0">
                                                            <thead>
                                                                <tr>
                                                                    <th colspan="2">Time Frame</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <tr>
                                                                    <td class="column1"><strong>Start Date/Time</strong></td>
                                                                    <td><strong>Start by:</strong> Next 3 days, 00/00/0000 at 00:00 ETC (GMT-05)</td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="column1"><strong>Checkpoint Date/Time</strong></td>
                                                                    <td><strong>Run it for:</strong> 3 days after Start Time, 00/00/0000 at 00:00 ETC (GMT-05)</td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="column1"><strong>End Date/Time</strong></td>
                                                                    <td><strong>Run it for:</strong> 7 days, 00/00/0000 at 00:00 ETC (GMT-05)</td>
                                                                </tr>
                                                            </tbody>
                                                        </table>

                                                    </div>
                                                </div><!-- End .tabs4Content-->

                                            </div><!-- End .detailsContent -->

                                        </div><!-- End .details -->

                                        <div class="details">

                                            <div class="caption">
                                                <div class="captionLeft"><div class="captionRight">
                                                    <div class="captionInner">
                                                        <h2>Prizes</h2>
                                                    </div>
                                                </div></div>
                                            </div><!-- End .caption -->

                                            <div class="detailsContent">

                                                <table class="projectStats" cellpadding="0" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th colspan="2">Main Prizes</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td class="column1"><strong>First Prize</strong></td>
                                                            <td>$ 0000,00</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="column1"><strong>Second Prize</strong></td>
                                                            <td>$ 0000,00</td>
                                                        </tr>
                                                    </tbody>
                                                </table>

                                                <table class="projectStats" cellpadding="0" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>Additional Prizes</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td class="column2">3rd Prize: $00,00 - 4th Prize: $00,00 - 5th Prize: $00,00</td>
                                                        </tr>
                                                    </tbody>
                                                </table>

                                            </div><!-- End .detailsContent -->

                                        </div><!-- End .details -->

                                        <div class="details">

                                            <div class="caption">
                                                <div class="captionLeft"><div class="captionRight">
                                                    <div class="captionInner">
                                                        <h2>Specification</h2>
                                                    </div>
                                                </div></div>
                                            </div><!-- End .caption -->

                                            <div class="detailsContent">

                                                <div class="tabs4">
                                                    <ul>
                                                        <li class="on">
                                                            <a href="javascript:;" onclick="showHideTabs(this,'specificationTabs',0);"><span>General</span></a>
                                                        </li>
                                                        <li>
                                                            <a href="javascript:;" onclick="showHideTabs(this,'specificationTabs',1);"><span>Specifications</span></a>
                                                        </li>
                                                        <li>
                                                            <a href="javascript:;" onclick="showHideTabs(this,'specificationTabs',2);"><span>Rounds Introductions</span></a>
                                                        </li>
                                                    </ul>
                                                </div><!-- End .tabs4 -->

                                                <div id="specificationTabs" class="tabs4Content">
                                                    <div><!-- The content of the first tab -->
                                                        <div class="questions">
                                                            <p><strong>What are the primary goal of this
                                                                challenge?</strong></p>

                                                            <p>Lorem ipsum dolor sit amet, consectectur adipiscing elit.
                                                                Pellentesque mangna felis, tincidunt sed facilisis sit
                                                                amet, dignissim eget velit. integer accumsan, erat in
                                                                lobotis accumsan, ligula ante elementum ante, non
                                                                pretium mangna neque id augue.</p>
                                                        </div><!-- End .questions -->

                                                        <div class="questions">
                                                            <p><strong>Who is Your Target Audience?</strong></p>

                                                            <p>Lorem ipsum dolor sit amet, consectectur adipiscing elit.
                                                                Pellentesque mangna felis, tincidunt sed facilisis sit
                                                                amet, dignissim eget velit. integer accumsan, erat in
                                                                lobotis accumsan, ligula ante elementum ante, non
                                                                pretium mangna neque id augue.</p>
                                                        </div><!-- End .questions -->

                                                        <div class="questions">
                                                            <p><strong>Does This Project Have Any Branding
                                                                Guidelines?</strong></p>

                                                            <p>Lorem ipsum dolor sit amet, consectectur adipiscing
                                                                elit.</p>
                                                        </div>

                                                        <div class="questions">
                                                            <p><strong>Are There Designs, Websites, etc That You Like or
                                                                Dislike? Why?</strong></p>

                                                            <p>Lorem ipsum dolor sit amet, consectectur adipiscing elit.
                                                                Pellentesque mangna felis, tincidunt sed facilisis sit
                                                                amet, dignissim eget velit. integer accumsan, erat in
                                                                lobotis accumsan, ligula ante elementum ante, non
                                                                pretium mangna neque id augue.</p>
                                                        </div>

                                                        <div class="questions">
                                                            <p><strong>Other Instructions for Your Challenge</strong></p>

                                                            <p>Lorem ipsum dolor sit amet, consectectur adipiscing elit.
                                                                Pellentesque mangna felis, tincidunt sed facilisis sit
                                                                amet, dignissim eget velit. integer accumsan, erat in
                                                                lobotis accumsan, ligula ante elementum ante, non
                                                                pretium mangna neque id augue.</p>
                                                        </div>

                                                        <div class="questions">
                                                            <p><strong>What Criteria Must the Designers Meet to Win This
                                                                Challenge?</strong></p>

                                                            <p>Lorem ipsum dolor sit amet, consectectur adipiscing elit.
                                                                Pellentesque mangna felis, tincidunt sed facilisis sit
                                                                amet, dignissim eget velit. integer accumsan, erat in
                                                                lobotis accumsan, ligula ante elementum ante, non
                                                                pretium mangna neque id augue.</p>
                                                        </div>

                                                    </div>

                                                    <div class="hide"><!-- The content of the second tab -->

                                                        <div class="questions">
                                                            <p><strong>Colors:</strong></p>
                                                            <p>Lorem ipsum dolor sit amet, consectectur adipiscing elit.</p>
                                                        </div>

                                                        <div class="questions">
                                                            <p><strong>Fonts:</strong></p>
                                                            <p>Lorem ipsum dolor sit amet, consectectur adipiscing elit.</p>
                                                        </div>

                                                        <div class="questions">
                                                            <p><strong>Additional Requirements &amp; Restrictions:</strong></p>
                                                            <p>Lorem ipsum dolor sit amet, consectectur adipiscing elit.</p>
                                                        </div>

                                                    </div>

                                                    <div class="hide"><!-- The content of the third tab -->

                                                        <div class="questions">
                                                            <p><strong>Round #1 Specific Instruction</strong></p>

                                                            <p>Lorem ipsum dolor sit amet, consectectur adipiscing elit.
                                                                Pellentesque mangna felis, tincidunt sed facilisis sit
                                                                amet, dignissim eget velit. integer accumsan, erat in
                                                                lobotis accumsan, ligula ante elementum ante, non
                                                                pretium mangna neque id augue.</p>
                                                        </div>

                                                        <div class="questions">
                                                            <p><strong>Round #2 Specific Instruction</strong></p>

                                                            <p>Lorem ipsum dolor sit amet, consectectur adipiscing elit.
                                                                Pellentesque mangna felis, tincidunt sed facilisis sit
                                                                amet, dignissim eget velit. integer accumsan, erat in
                                                                lobotis accumsan, ligula ante elementum ante, non
                                                                pretium mangna neque id augue.</p>
                                                        </div>

                                                        <div class="questions">
                                                            <p><strong>Files Deliverables</strong></p>

                                                            <p>PSD, AI, JPG/PNG/GIF</p>
                                                        </div>

                                                    </div>
                                                </div><!-- End tebs4Content -->

                                            </div><!-- End .detailsContent -->

                                        </div><!-- End .details -->

                                        <div class="details">

                                            <div class="caption">
                                                <div class="captionLeft"><div class="captionRight">
                                                    <div class="captionInner">
                                                        <h2>Files</h2>
                                                    </div>
                                                </div></div>
                                            </div><!-- End .caption -->

                                            <div class="detailsContent">
                                                <div class="filesContainer">
                                                    <div class="file">
                                                        <img src="/images/placeholder.png" alt="placeholder"/><span class="fileName">file_name.pdf</span>
                                                    </div>
                                                    <div class="file">
                                                        <img src="/images/placeholder.png" alt="placeholder"/><span class="fileName">file_name.doc</span>
                                                    </div>
                                                </div><!-- End .filesContainer -->
                                            </div><!-- .detailsContent -->

                                        </div><!-- End .details -->

                                        <div class="panel"></div>

                                </div><!-- End .container2Content -->
                            </div></div>
                        </div></div></div>
                    </div><!-- End .container2 -->

                    <a href="javascript:alert('To be implemented by sub-sequent assemblies');" class="button1 backToTop"><span>Back To Top</span></a>
                    </div>
                    </div>

                </div>
                <!-- End #mainContent -->

                <jsp:include page="includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>
