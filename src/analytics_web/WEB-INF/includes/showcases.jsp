<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment is to be included on the bottom of TopCoder Analytics page.
  - It defines the content of the footer section and the Registration dialog box.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- section -->
<div class="section showcaseSection">
    <h2>Showcase</h2>
    <ul id="showcaseCarousel">
        <% /*
        <li>
            <div class="title lindenLab">
                <h3>J2KDecode</h3>
                <img src="/images/analytics/small-linden-lab-logo.png" alt="Linden Lab" />
            </div>
            <dl>
                <dt>Client:</dt>
                <dd>Linden Lab</dd>
                <dt>Contest:</dt>
                <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=13772">Linden Lab OpenJPEG</a></dd>
                <dt>Objective:</dt>
                <dd>Speed up an image compression algorithm used to render the virtual worlds of Second Life.</dd>
                <dt>Competitors:</dt>
                <dd>93</dd>
                <dt>Winner:</dt>
                <dd><a href="http://community.topcoder.com/tc?module=MemberProfile&cr=10597114&tab=long">Psyho</a></dd>
            </dl>
            <a href="javascript:;" class="btn" id="button1"><span class="left"><span class="right">View Details</span></span></a>
        </li>
        */ %>
        <li>
            <div class="title lifetech">
                <h3>SFF Compression</h3>
                <img src="/images/analytics/small-ion-torrent-logo.jpg" alt="Life Technologies Corporation" />
            </div>
            <dl>
                <dt>Client:</dt>
                <dd>Life Technologies Corporation</dd>
                <dt>Contest:</dt>
                <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=15023">SFF Compression</a></dd>
                <dt>Objective:</dt>
                <dd>Improve the performance of data compression techniques used in genetic analysis.</dd>
                <dt>Competitors:</dt>
                <dd>173</dd>
                <dt>Winner:</dt>
                <dd><a href="http://community.topcoder.com/tc?module=MemberProfile&cr=7584274&tab=long">kirkifer</a></dd>
            </dl>
            <a href="javascript:;" class="btn" id="button1"><span class="left"><span class="right">View Details</span></span></a>
        </li>
        <% /*
        <li>
            <div class="title nas">
                <h3>Crater Detection</h3>
                <img src="/images/analytics/small-nsa-logo.png" alt="NAS" />
            </div>
            <dl>
                <dt>Client:</dt>
                <dd>NASA Tournament Lab</dd>
                <dt>Contest:</dt>
                <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=14570">NASA NTL Marathon Match 2</a></dd>
                <dt>Objective:</dt>
                <dd>Automatically identify moon craters in photographs of the lunar surface.</dd>
                <dt>Competitors:</dt>
                <dd>70</dd>
                <dt>Winner:</dt>
                <dd><a href="http://community.topcoder.com/tc?module=MemberProfile&cr=11789293&tab=long">nhzp339</a></dd>
            </dl>
            <a href="javascript:;" class="btn" id="button3"><span class="left"><span class="right">View Details</span></span></a>
        </li>
        <li>
            <div class="title nas">
                <h3>Vehicle Recognition</h3>
                <img src="/images/analytics/small-nsa-logo.png" alt="NAS" />
            </div>
            <dl>
                <dt>Client:</dt>
                <dd>NASA Tournament Lab</dd>
                <dt>Contest:</dt>
                <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=14481">NASA NTL Marathon Match 1</a></dd>
                <dt>Objective:</dt>
                <dd>Invent an algorithm that can determine whether a satellite photograph contains a vehicle.</dd>
                <dt>Competitors:</dt>
                <dd>139</dd>
                <dt>Winner:</dt>
                <dd><a href="http://community.topcoder.com/tc?module=MemberProfile&cr=22779588&tab=long">msiro</a></dd>
            </dl>
            <a href="javascript:;" class="btn" id="button4"><span class="left"><span class="right">View Details</span></span></a>
        </li>
        */ %>
        <li>
            <div class="title nasa">
                <h3>NASA Medical Kit</h3>
                <img src="/images/analytics/small-nasa-logo.png" alt="NASA" />
            </div>
            <dl>
                <dt>Client:</dt>
                <dd>NASA Space Life Sciences Directorate</dd>
                <dt>Contest:</dt>
                <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=14134">NASA-TopCoder - 0133</a></dd>
                <dt>Objective:</dt>
                <dd>Calculate the most valuable set of medical resources for any given space mission.</dd>
                <dt>Competitors:</dt>
                <dd>4</dd>
                <dt>Winner:</dt>
                <dd><a href="http://www.topcoder.com/tc?module=MemberProfile&cr=22839984&tab=long">mkolfman</a></dd>
            </dl>
            <a href="javascript:;" class="btn" id="button2"><span class="left"><span class="right">View Details</span></span></a>
        </li>
        <li>
            <div class="title lifetech">
                <h3>DATCompression</h3>
                <img src="/images/analytics/small-ion-torrent-logo.jpg" alt="Life Technologies Corporation" />
            </div>
            <dl>
                <dt>Client:</dt>
                <dd>Life Technologies Corporation</dd>
                <dt>Contest:</dt>
                <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=15038">DATCompression</a></dd>
                <dt>Objective:</dt>
                <dd>Lower the cost of life-saving genetic analysis by compressing data into small files at high speeds.</dd>
                <dt>Competitors:</dt>
                <dd>256</dd>
                <dt>Winner:</dt>
                <dd><a href="http://community.topcoder.com/tc?module=MemberProfile&cr=21733694&tab=long">tqfp</a></dd>
            </dl>
            <a href="javascript:;" class="btn" id="button3"><span class="left"><span class="right">View Details</span></span></a>
        </li>
        <% /*
        <li>
            <div class="title syngenta">
                <h3>Elite Classifier</h3>
            </div>
            <dl>
                <dt>Client:</dt>
                <dd>Syngenta</dd>
                <dt>Contest:</dt>
                <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=15024">Soybean Marathon Match 1</a></dd>
                <dt>Objective:</dt>
                <dd>Sift through data from several years of field trials of new soybean varieties to find the most promising ones.</dd>
                <dt>Competitors:</dt>
                <dd>71</dd>
                <dt>Winner:</dt>
                <dd><a href="http://www.topcoder.com/tc?module=MemberProfile&cr=23028193&tab=long">elder1g</a></dd>
            </dl>
            <a href="javascript:;" class="btn" id="button7"><span class="left"><span class="right">View Details</span></span></a>
        </li>
        */ %>
        <li>
            <div class="title uspto">
                <h3>Patent Labeling</h3>
                <img src="/images/analytics/small-uspto-logo.png" alt="United States Patent and Trademark Office" />
            </div>
            <dl>
                <dt>Client:</dt>
                <dd>United States Patent and Trademark Office</dd>
                <dt>Contest:</dt>
                <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=15027">USPTO Algorithm Challenge</a></dd>
                <dt>Objective:</dt>
                <dd>Automatically locate the labels in patent drawings and convert them into searchable text fields.</dd>
                <dt>Competitors:</dt>
                <dd>95</dd>
                <dt>Winner:</dt>
                <dd><a href="http://www.topcoder.com/tc?module=MemberProfile&cr=283329&tab=long">JacoCronje</a></dd>
            </dl>
            <a href="javascript:;" class="btn" id="button4"><span class="left"><span class="right">View Details</span></span></a>
        </li>

    </ul>
</div>
<!-- End .section -->
