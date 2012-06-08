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

<!-- modal -->
<div id="modalBackground"></div>
<div id="modal">
    <!-- register -->
    <div id="registerModal" class="modal">
        <a href="javascript:;" class="colseModal">CLOSE</a>
        <!-- title -->
        <div class="modalTitle">
            <div class="modalTitleLeft">
                <div class="modalTitleRight">
                    <div class="modalTitleInner">
                        <h2>REGISTER</h2>
                    </div>
                </div>
            </div>
        </div>
        <!-- End .modalTitle -->
        <!-- section -->
        <div class="modalSection">
            <div class="modalSectionInner">
                <div class="row">
                    <label>First Name:</label>
                    <div class="field">
                        <input type="text" class="text" id="firstName" tabindex="3" />
                    </div>
                </div>
                <div class="errorRow firstNameErr hide">
                    <span>Please fill in your first name</span>
                </div>
                <div class="row">
                    <label>Last Name:</label>
                    <div class="field">
                        <input type="text" class="text" id="lastName" tabindex="4" />
                    </div>
                </div>
                <div class="errorRow lastNameErr hide">
                    <span>Please fill in your last name</span>
                </div>
                <div class="row">
                    <label>Handle:</label>
                    <div class="field">
                        <input type="text" class="handelText" id="handel" tabindex="5" />
                    </div>
                </div>
                <div class="errorRow handleErr hide">
                    <span>Please fill in your desired handle</span>
                </div>
                <div class="row">
                    <label>E-mail:</label>
                    <div class="field">
                        <input type="text" class="text" id="email" tabindex="6" />
                    </div>
                </div>
                <div class="errorRow emailErr hide">
                    <span>Please fill in your email</span>
                </div>
                <div class="row">
                    <label>Password:</label>
                    <div class="field">
                        <input type="password" class="text" id="passwordWord" tabindex="7" />
                    </div>
                </div>
                <div class="errorRow regPasswordErr hide">
                    <span>Please fill in your password</span>
                </div>
                <div class="passwordStrength">
                    <span>Password Strength:</span>
                    <img src="/images/analytics/password-strength-empty.png" alt="" />
                    <img src="/images/analytics/password-strength-empty.png" alt="" />
                    <img src="/images/analytics/password-strength-empty.png" alt="" />
                    <img src="/images/analytics/password-strength-empty.png" alt="" />
                    <div class="clear"></div>
                </div>
                <div class="row">
                    <label>Confirm Password:</label>
                    <div class="field">
                        <input type="password" class="text" id="confirmPassword" tabindex="8" />
                    </div>
                </div>
                <div class="errorRow passwordNotMactchErr hide">
                    <span>Password does not match</span>
                </div>
                <div class="row">
                    <label>Verification Code:</label>
                    <div class="code">
                        <img id="veriImg" src="/images/analytics/code.png" alt="CODE" />
                    </div>
                </div>
                <div class="codeInput">
                    <a href="javascript:;" class="tryAnotherCode">Try another code</a>
                    <div class="clear"></div>
                    <div class="field">
                        <input type="text" class="text" id="verificationCode" tabindex="9" />
                        <div class="veriCodeErr" style="display:none"></div>
                    </div>
                </div>
                <div class="errorRow veriCodeErr hide">
                    <span>Please enter the verification code</span>
                </div>
                <div class="clear"></div>
                <div class="accept">
                    <input type="checkbox" class="checobox" id="checkboxPrivacy" tabindex="10" />
                    <label>I have read and accept the <a href="http://www.topcoder.com/tc?module=Static&d1=about&d2=privacy">Privacy Policy</a></label>
                </div>
                <div class="clear"></div>
                <div class="errorRow policyErr hide">
                    <span>Please accept the privary policy</span>
                </div>
                <div class="buttonBox">
                    <a href="javascript:;" class="btn" id="submitButton"><span class="left"><span class="right">Submit</span></span></a>
                    <a href="javascript:;" class="btn resetButton"><span class="left"><span class="right">Reset</span></span></a>
                </div>
                <div class="clear"></div>
            </div>
        </div>
        <!-- End .modalSection -->
        <div class="errorModalBottom">
            <div class="modalBottomLeft">
                <div class="modalBottomRight">
                    <p>Please correct the errors above.</p>
                </div>
            </div>
        </div>
        <!-- bottom -->
        <div class="modalBottom">
            <div class="modalBottomLeft">
                <div class="modalBottomRight"></div>
            </div>
        </div>
        <!-- End .modalBottom -->
    </div>
    <!-- End #register -->
    <!-- detail -->
    <div id="detailModal" class="modal">
        <a href="javascript:;" class="colseModal">CLOSE</a>
        <!-- title -->
        <div class="modalTitle">
            <div class="modalTitleLeft">
                <div class="modalTitleRight">
                    <div class="modalTitleInner">
                        <h2>Showcase</h2>
                    </div>
                </div>
            </div>
        </div>
        <!-- End .modalTitle -->
        <!-- section -->
        <div class="modalSection">
            <div class="modalSectionInner">
                <ul id="detailModalCarousel">
                    <% /*
                    <li>
                        <div class="modalContainer">
                            <div class="scrollPanel">
                                <div class="modalLogo bigLinden"><img src="/images/analytics/big-linden-lab-logo.png" alt="Linden Lab" /></div>
                                <h3>J2KDecode</h3>
                                <dl>
                                    <dt>Client:</dt>
                                    <dd>Linden Lab</dd>
                                    <dt>Contest:</dt>
                                    <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=13772">Linden Lab OpenJPEG</a></dd>
                                    <dt>Competitors:</dt>
                                    <dd>93</dd>
                                    <dt>Winner:</dt>
                                    <dd><a href="http://community.topcoder.com/tc?module=MemberProfile&cr=10597114&tab=long">Psyho</a></dd>
                                </dl>
                                <h4>Objective</h4>
                                <p>Speed up an image compression algorithm used to render the virtual worlds of Second Life.</p>
                                <br />
                                <h4>The Problem</h4>
                                <p>The virtual reality of Second Life immerses its users in a graphical environment that relies on heavy image compression to make the most of its users' bandwidth. Linden Lab, the makers of Second Life, called on the TopCoder community to speed up its image decoding algorithm in order to improve the performance of its virtual worlds. </p>
                                <div class="figure">
                                    <img src="/images/analytics/figures/second.life.657x394.jpg" alt="Second Life" />
                                </div>
                                <h4>The Solution</h4>
                                <p>The contest winner identified three bottlenecks in the image decoding algorithm. He found that performance could be improved significantly by implementing better memory management and rearranging the order of certain operations.</p>
                                <br />
                                <h4>The Results</h4>
                                <p>The winning submission was more than three times faster than the original algorithm, leading to a significantly better experience for millions of Second Life citizens.</p>
                                <br />
                                <div class="buttonBox">
                                    <a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=13772" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </li>
                    */ %>
                    <li>
                        <div class="modalContainer">
                            <div class="scrollPanel">
                                <div class="modalLogo bigLifeTech"><img src="/images/analytics/ion-torrent-logo.jpg" alt="Life Tech" /></div>
                                <h3>SFF Compression</h3>
                                <dl>
                                    <dt>Client:</dt>
                                    <dd>LifeTech</dd>
                                    <dt>Contest:</dt>
                                    <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=15023">SFF Compression</a></dd>
                                    <dt>Competitors:</dt>
                                    <dd>173</dd>
                                    <dt>Winner:</dt>
                                    <dd><a href="http://community.topcoder.com/tc?module=MemberProfile&cr=7584274&tab=long">kirkifer</a></dd>
                                </dl>
                                <h4>Objective</h4>
                                <p>Improve the performance of data compression techniques used in genetic analysis.</p>
                                <br />
                                <h4>The Problem</h4>
                                <p>The client has large quantities of genetic data stored in a special file format, the result of a DNA sequencing process called pyrosequencing. TopCoder members competed to develop custom compression and decompression algorithms that balance small file sizes with fast processing. </p>
                                <div class="figure">
                                    <img src="/images/analytics/figures/gene.sequence.png" alt="Gene Sequence" />
                                </div>
                                <br />
                                <h4>The Solution</h4>
                                <p>The winner analyzed the file format to find details that were redundant and could therefore be left out of the encoding. He then found a general encoding process that would work well for the type of data found in the files and optimized it with domain-specific knowledge.</p>
                                <br />
                                <h4>The Results</h4>
                                <p>The winning submission was five times faster than the original algorithm and resulted in file sizes that were half as large, leading to huge cost savings and service improvements.</p>
                                <br />
                                <div class="buttonBox">
                                    <a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=15023" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </li>
                    <% /*
                    <li>
                        <div class="modalContainer">
                            <div class="scrollPanel">
                                <div class="modalLogo"><img src="/images/analytics/big-nas-logo.png" alt="NASA" /></div>
                                <h3>Crater Detection</h3>
                                <dl>
                                    <dt>Client:</dt>
                                    <dd>NASA Tournament Lab</dd>
                                    <dt>Contest:</dt>
                                    <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=14570">NASA NTL Marathon Match 2</a></dd>
                                    <dt>Competitors:</dt>
                                    <dd>70</dd>
                                    <dt>Winner:</dt>
                                    <dd><a href="http://community.topcoder.com/tc?module=MemberProfile&cr=11789293&tab=long">nhzp339</a></dd>
                                </dl>
                                <h4>Objective</h4>
                                <p>Automatically identify moon craters in photographs of the lunar surface.</p>
                                <br/>
                                <h4>The Problem</h4>
                                <p>NASA has a large collection of images of the lunar surface taken by orbiting spacecraft. The images are taken under various lighting conditions and camera angles, making it difficult to consistently identify moon craters with an automatic process. </p>
                                <div class="figure">
                                    <img src="/images/analytics/craters.600x400.jpg" alt="Craters" />
                                </div>
                                <br />
                                <h4>The Solution</h4>
                                <p>The winning algorithm divides each image into smaller blocks and enhances the contrast between black and white pixels within each block. Then it searches for regions of pixels that are black on one side and white on the other, evaluating the probability that such a region contains the shadow of a lunar crater.</p>
                                <br />
                                <h4>The Results</h4>
                                <p>The winning entry recognized lunar images with an average precision of 60.3%, representing a significant advance over previous approaches.</p>
                                <br />
                                <div class="buttonBox">
                                    <a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=14570" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="modalContainer">
                            <div class="scrollPanel">
                                <div class="modalLogo"><img src="/images/analytics/big-nasa-logo.png" alt="NAS" /></div>
                                <h3>NASA Imaging Problem</h3>
                                <dl>
                                    <dt>Client:</dt>
                                    <dd>NASA Tournament Lab</dd>
                                    <dt>Contest:</dt>
                                    <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=14481">NASA NTL Marathon Match 1</a></dd>
                                    <dt>Competitors:</dt>
                                    <dd>139</dd>
                                    <dt>Winner:</dt>
                                    <dd><a href="http://community.topcoder.com/tc?module=MemberProfile&cr=22779588&tab=long">msiro</a></dd>
                                </dl>
                                <h4>Objective</h4>
                                <p>Invent an algorithm that can determine whether a satellite photograph contains a vehicle.</p>
                                <br />
                                <h4>The Problem</h4>
                                <p>NASA would like to process satellite photography of the earth automatically instead of relying on human image analysts. One of the basic questions it seeks to answer is whether a given satellite image includes a vehicle. </p>
                                <div class="figure">
                                    <img src="/images/analytics/vehicle.150x150.jpg" alt="Vehicle" />
                                </div>
                                <br />
                                <h4>The Solution</h4>
                                <p>The top submissions employed a panoply of algorithms from the toolbox of image recognition, including edge detection, neural networks, and color clustering heuristics.</p>
                                <br />
                                <h4>The Results</h4>
                                <p>The best algorithm covered 91.7% of the area under the Receiver Operating Characteristic curve, indicating that it resists noise well as its sensitivity increases. Xavier Bouyssounouse of NASA said, "We were very impressed with the #1 and #2 algorithms, which used state-of-the-art computer vision approaches, including the choice of some interesting features extracted from the image.   These top two algorithms will be very helpful to our project."</p>
                                <br />
                                <div class="buttonBox">
                                    <a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=14481" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </li>
                    */ %>
                    <li>
                        <div class="modalContainer">
                            <div class="scrollPanel">
                                <div class="modalLogo"><img src="/images/analytics/nasa-logo.png" alt="NASA" /></div>
                                <h3>NASA Medical Kit</h3>
                                <dl>
                                    <dt>Client:</dt>
                                    <dd>NASA Space Life Sciences Directorate</dd>
                                    <dt>Contest:</dt>
                                    <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=14134">NASA-TopCoder - 0133</a></dd>
                                    <dt>Competitors:</dt>
                                    <dd>4</dd>
                                    <dt>Winner:</dt>
                                    <dd><a href="http://www.topcoder.com/tc?module=MemberProfile&cr=22839984&tab=long">mkolfman</a></dd>
                                </dl>
                                <h4>Objective</h4>
                                <p>Calculate the most valuable set of medical resources for any given space mission.</p>
                                <br />
                                <h4>The Problem</h4>
                                <p>NASA has a limited amount of cargo capacity to pack a medical kit for each mission. In order to make the best possible use of this capacity, it asked TopCoder members to develop an algorithm that would select medical resources to minimize the probability of evacuation under the constraints of a given mission.</p>
                                <div class="figure">
                                    <img src="/images/analytics/figures/space.station.jpg" alt="Space Station" />
                                </div>
                                <br/>
                                <h4>The Solution</h4>
                                <p>Many of the top submissions relied on a large library of medical kits that they computed in advance. The winning solution made little use of precomputation, preferring to start with a full selection of medical items and using probabilistic formulas to select which ones to remove until the desired kit size was achieved.</p>
                                <br />
                                <h4>The Results</h4>
                                <p>The competition was a resounding success for NASA, resulting in algorithms that calculated medical kits more than 300 times faster than the previous state of the art. "We were blown away with the results," said Baraquiel Reyna, who is in charge of medical hardware for the International Space Station. "The amount of useful code developed in such a short amount of time really made us reconsider some of the ways that we write software. We are currently using the algorithm to redesign the contents of the medical kits that are rotating around the earth as we speak!" </p>
                                <br />
                                <div class="buttonBox">
                                    <a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=14134" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="modalContainer">
                            <div class="scrollPanel">
                                <div class="modalLogo"><img src="/images/analytics/ion-torrent-logo.jpg" alt="ion torrent" /></div>
                                <h3>DATCompression</h3>
                                <dl>
                                    <dt>Client:</dt>
                                    <dd>Life Technologies Corporation / Ion Torrent Systems, Inc.</dd>
                                    <dt>Contest:</dt>
                                    <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=15038">DATCompression</a></dd>
                                    <dt>Competitors:</dt>
                                    <dd>256</dd>
                                    <dt>Winner:</dt>
                                    <dd><a href="http://community.topcoder.com/tc?module=MemberProfile&cr=21733694&tab=long">tqfp</a></dd>
                                </dl>
                                <h4>Objective</h4>
                                <p>Lower the cost of life-saving genetic analysis by compressing data into small files at high speeds.</p>
                                <br />
                                <h4>The Problem</h4>
                                <p>Ion Torrent's genome sequencing technology promises to make life-saving genetic analysis more affordable than ever before. One of the keys to processing genetic data economically is compressing and decompressing the data with high efficiency, resulting in small file sizes and short processing times. </p>
                                <div class="figure">
                                    <img src="/images/analytics/figures/dna.sequencing.gif" alt="DNA Sequencing" />
                                </div>
                                <h4>The Solution</h4>
                                <p>The top submissions used many refinements of the standard compression techniques of Huffman coding and delta coding, with hardware-specific processing speedups.</p>
                                <br />
                                <h4>The Results</h4>
                                <p>The client is examining how to implement the fastest approaches in their production environment, and is preparing a follow-up contest for that purpose. </p>
                                <br />
                                <div class="buttonBox">
                                    <a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=15038" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </li>
                    <% /*
                    <li>
                        <div class="modalContainer">
                            <div class="scrollPanel">
                                <h3>Elite Classifier</h3>
                                <dl>
                                    <dt>Client:</dt>
                                    <dd>Omicron</dd>
                                    <dt>Contest:</dt>
                                    <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=15024">Soybean Marathon Match 1</a></dd>
                                    <dt>Competitors:</dt>
                                    <dd>71</dd>
                                    <dt>Winner:</dt>
                                    <dd><a href="http://www.topcoder.com/tc?module=MemberProfile&cr=23028193&tab=long">elder1g</a></dd>
                                </dl>
                                <h4>Objective</h4>
                                <p>Sift through data from several years of field trials of new soybean varieties to find the most promising ones.</p>
                                <br />
                                <h4>The Problem</h4>
                                <p>A producer of agricultural seeds wants to improve the efficiency of its soybean breeding program by focusing trial resources on varieties with a high likelihood of success. Contestants were asked to develop an algorithm capable of identifying elite varieties based on a training set of elite lines and testing data that spans more than a decade. </p>
                                <div class="figure">
                                    <img src="/images/analytics/soybeans.1790x2700.jpg" alt="Soybeans" />
                                </div>
                                <br />
                                <h4>The Results</h4>
                                <p>The winning algorithms will be put to use in breed-selection software that aims to improve soybean harvests for farmers worldwide. </p>
                                <br />
                                <div class="buttonBox">
                                    <a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=15024" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </li>
                    */ %>
                    <li>
                        <div class="modalContainer">
                            <div class="scrollPanel">
                                <div class="modalLogo"><img src="/images/analytics/uspto-logo.png" alt="United States Patent and Trademark Office" /></div>
                                <h3>Patent Labeling</h3>
                                <dl>
                                    <dt>Client:</dt>
                                    <dd>United States Patent and Trademark Office</dd>
                                    <dt>Contest:</dt>
                                    <dd><a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=15027">USPTO Algorithm Challenge</a></dd>
                                    <dt>Competitors:</dt>
                                    <dd>95</dd>
                                    <dt>Winner:</dt>
                                    <dd><a href="http://www.topcoder.com/tc?module=MemberProfile&cr=283329&tab=long">JacoCronje</a></dd>
                                </dl>
                                <h4>Objective</h4>
                                <p>Automatically locate the labels in patent drawings and convert them into searchable text fields.</p>
                                <br />
                                <h4>The Problem</h4>
                                <p>The United States Patent and Trademark Office possesses a vast library of patent drawings that are difficult to search because they have not been annotated in a machine-readable format. TopCoder members were challenged to develop algorithms that analyze the scanned pages to automatically label them and to distinguish the figures from the text. </p>
                                <div class="figure">
                                    <img src="/images/analytics/figures/patent.jpg" alt="patent" />
                                </div>
                                <br />
                                <h4>The Solution</h4>
                                <p>The top submissions used the techniques of image recognition to identify the borders of the images within each page and to extract the textual portions.</p>
                                <br />
                                <h4>The Results</h4>
                                <p>The Patent Office intends to use the top algorithms to prepare a fully searchable database of patent drawings that will ultimately be made available to the general public. The winning algorithm achieved an F-score (a combined measure of precision and recall) of 78.6% for identifying figures and 64% for identifying labels. </p>
                                <br />
                                <div class="buttonBox">
                                    <a href="http://community.topcoder.com/longcontest/stats/?module=ViewOverview&rd=15027" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <!-- bottom -->
        <div class="modalBottom">
            <div class="modalBottomLeft">
                <div class="modalBottomRight"></div>
            </div>
        </div>
        <!-- End .modalBottom -->
    </div>
    <!-- End #register -->
</div>
<!-- End #modal -->
