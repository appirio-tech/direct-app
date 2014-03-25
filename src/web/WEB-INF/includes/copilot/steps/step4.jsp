<%--
  - Author: GreatKevin
  - Version: 1.1
  - Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TC Direct Cockpit Release Three version 1.0)
  - - Update the top note of the step 4.
  - - Update the title
  - - Update the default copilot posting start time to 24 hours from current time and round to nearest hour.
  -
  - Description: The step 4 of post a copilot.
  - Since: TC Cockpit Post a Copilot Assembly 1
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="postCopilotStep4 stepDiv">
    <!-- form -->
    <div class="form">
        
        
        <div class="noteContainer">
            <p>
            Tell us when you would like this Copilot Opportunity to start. You may post it now, or schedule it for a later date.
           </p>
        </div>
        
        <!-- row -->
        <div class="row">
            <span class="intro">All fields marked with <span class="red">*</span> are mandatory</span>
            <div class="clear"></div>
            
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <h2>Copilot Opportunity Schedule</h2>
                </div></div>
            </div><!-- End .title -->
            
            
            <div class="rowItem">
                <div class="lineItem">
                    <label>Do you want to post this opportunity to the Copilots now?</label>
                    <a href="javascript:;" class="red-button-big newButtonGreen"><span class="right"><span class="middle">YES, POST NOW</span></span></a>
                    <div class="clear"></div>
                </div>
                <div class="lineItem amountItem inputItem">
                    <label><strong>OR</strong></label>
                    
                    <div class="postFrame hide">
                        <span class="grayArrow"></span>
						<p>Most copilot postings are listed with $<span class="defaultFirstPlaceCost"></span> first place and $<span class="defaultSecondPlaceCost"></span> second place payments for a total cost of $<span class="defaultTotalPlaceCost"></span>. <br />Would you like to proceed with this amount or set your own amount?</p>
                        
                        <ul>
                            <li>
                                <input type="radio" class="radio2 proceedRadio" name="amount" id="proceed1"/><label for="proceed1">Proceed</label>
                            </li>
                            <li>
                                <input type="radio"  class="radio2 useAmountRadio" name="amount" id="useAmount1"/><label for="useAmount1"  class="useAmountLabel">Use this amount</label> 
								<label class="first">1st Place</label>
							    <span class="dw">$</span>
							    <input type="text" class="amountText newAmountText" id="amountText1" value=""/>
							    <label class="second">2nd Place</label>
							    <span class="dw">$</span>
							    <span class="prizeInfo"></span>
                            </li>
                        </ul>
                    </div>
                    
                    <div class="clear"></div>
                </div>
                <div class="lineItem">
                    <label>Would you like to schedule it for a later date?</label>
                    <a href="javascript:;" class="red-button-big newButtonGreen"><span class="right"><span class="middle">YES, DO IT LATER</span></span></a>
                    <div class="clear"></div>
                </div>               
            
                <div class="lineItem dateItem inputItem">
                    <label>&nbsp;</label>
                    
                    <div class="postFrame hide">
                        <span class="grayArrow"></span>
                        
                        <p>Most copilot postings are listed with $<span class="defaultFirstPlaceCost"></span> first place and $<span class="defaultSecondPlaceCost"></span> second place payments for a total cost of $<span class="defaultTotalPlaceCost"></span>. <br />Would you like to proceed with this amount or set your own amount?</p>
                        
                        <ul>
                            <li>
                                <input type="radio" class="radio2 proceedRadio" name="amount" id="proceed2"/><label for="proceed2">Proceed</label>
                            </li>
                            <li>
                                <input type="radio"  class="radio2 useAmountRadio" name="amount" id="useAmount2"/><label for="useAmount2"  class="useAmountLabel">Use this amount</label> 
								<label class="first">1st Place</label>
							    <span class="dw">$</span>
							    <input type="text" class="amountText newAmountText" id="amountText2" value=""/>
							    <label class="second">2nd Place</label>
							    <span class="dw">$</span>
							    <span class="prizeInfo"></span>
                            </li>
                        </ul>                            
                        
                        <p>&nbsp;</p>
                        
                        <p>Start Date</p>
                        <div id="launchContestOut" class="noBorder">
                            <div class="row schedule">
                                <jsp:useBean id="currentDate" class="java.util.Date" />
                                <input id="currentCopilotDate" type="hidden" value='<fmt:formatDate value="${currentDate}" type="date" pattern="MM/dd/yyyy HH:mm" />' />
                                
                                <input id="startDate" name="startDate" type="text"  class="text date-pick" readonly="true"/>
                                <div class="startEtSelect">
                                    <select id="startTime" name="startTime" ><jsp:include page="../../common/timeOptions.jsp"/></select>
                                </div>
                                <span>ET (UTC-05)</span>
                            </div>
                        </div>                          
                        
                    </div>
                    
                    <div class="clear"></div>
                </div>                
                            
            </div>
            <!--End .rowItem-->
            
        </div>
        <!-- End .row -->
        
    </div>
    <!-- End .form --> 
</div>
