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

    <!-- footer -->
    <div id="footer">
        <div class="footerInner">
            <p class="copyRight">&copy; 2012 TopCoder. All rights reserved.</p>
            <a href="http://www.topcoder.com/tc"><img src="/images/analytics/tc-footer-logo.png" alt="TOPCODER" /></a>
        </div>
    </div>
    <!-- End #footer -->

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
                        <li>
                            <div class="modalContainer">
                                <div class="scrollPanel">
                                    <div class="modalLogo"><img src="/images/analytics/big-nas-logo.png" alt="NASA" /></div>
                                    <h3>Crater Detection</h3>
                                    <dl>
                                        <dt>Client:</dt>
                                        <dd>NASA NTL</dd>
                                        <dt>Contest:</dt>
                                        <dd><a href="javascript:;">NASA NTL Marathon Match 2</a></dd>
                                        <dt>Competitors:</dt>
                                        <dd>70</dd>
                                        <dt>Winner:</dt>
                                        <dd><a href="javascript:;" target="_blank">nhzp339</a></dd>
                                    </dl>
                                    <h4>The Problem</h4>
                                    <p>Detect craters in a given set of orbital images taken under various illumination conditions and camera poses.  Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
                                    <div class="figure">
                                        <img src="/images/analytics/pic-01.jpg" alt="PIC" />
                                        <img src="/images/analytics/pic-02.jpg" alt="PIC" />
                                        <img src="/images/analytics/pic-03.jpg" alt="PIC" />
                                        <img src="/images/analytics/pic-04.jpg" alt="PIC" />
                                        <em>Note: Images can be used here if exists.</em>
                                    </div>
                                    <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. </p>
                                    <br />
                                    <p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur.</p>
                                    <br />
                                    <h4>The Solution</h4>
                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occa cat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                                    <br />
                                    <p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus.</p>
                                    <br />
                                    <h4>The Results</h4>
                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p>
                                    <br />
                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
                                    <br />
                                    <div class="buttonBox">
                                        <a href="#" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="modalContainer">
                                <div class="scrollPanel">
                                    <div class="modalLogo"><img src="/images/analytics/big-intel-logo.png" alt="INTEL" /></div>
                                    <h3>Intel Multi-Threading</h3>
                                    <dl>
                                        <dt>Client:</dt>
                                        <dd>intel</dd>
                                        <dt>Contest:</dt>
                                        <dd><a href="javascript:;">Intel Multi-Threading Competition 4</a></dd>
                                        <dt>Competitors:</dt>
                                        <dd>274</dd>
                                        <dt>Winner:</dt>
                                        <dd><a href="javascript:;">aanbar</a></dd>
                                    </dl>
                                    <h4>The Problem</h4>
                                    <p>Detect craters in a given set of orbital images taken under various illumination conditions and camera poses.  Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
                                    <div class="figure">
                                        <img src="/images/analytics/pic-01.jpg" alt="PIC" />
                                        <img src="/images/analytics/pic-02.jpg" alt="PIC" />
                                        <img src="/images/analytics/pic-03.jpg" alt="PIC" />
                                        <img src="/images/analytics/pic-04.jpg" alt="PIC" />
                                        <em>Note: Images can be used here if exists.</em>
                                    </div>
                                    <h4>The Solution</h4>
                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occa cat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                                    <br />
                                    <h4>The Results</h4>
                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
                                    <br />
                                    <div class="buttonBox">
                                        <a href="#" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="modalContainer">
                                <div class="scrollPanel">
                                    <div class="modalLogo bigLinden"><img src="/images/analytics/big-linden-lab-logo.png" alt="LINDEN-LAB" /></div>
                                    <h3>J2KDecode</h3>
                                    <dl>
                                        <dt>Client:</dt>
                                        <dd>Linden Lab</dd>
                                        <dt>Contest:</dt>
                                        <dd><a href="javascript:;">Linden Lab OpenJPEG </a></dd>
                                        <dt>Competitors:</dt>
                                        <dd>93</dd>
                                        <dt>Winner:</dt>
                                        <dd><a href="javascript:;">Psyho</a></dd>
                                    </dl>
                                    <h4>The Problem</h4>
                                    <p>Detect craters in a given set of orbital images taken under various illumination conditions and camera poses.  Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
                                    <div class="figure">
                                        <img src="/images/analytics/pic-01.jpg" alt="PIC" />
                                        <img src="/images/analytics/pic-02.jpg" alt="PIC" />
                                        <img src="/images/analytics/pic-03.jpg" alt="PIC" />
                                        <img src="/images/analytics/pic-04.jpg" alt="PIC" />
                                        <em>Note: Images can be used here if exists.</em>
                                    </div>
                                    <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. </p>
                                    <br />
                                    <p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur.</p>
                                    <br />
                                    <h4>The Solution</h4>
                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occa cat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                                    <br />
                                    <p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus.</p>
                                    <br />
                                    <h4>The Results</h4>
                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p>
                                    <br />
                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
                                    <br />
                                    <div class="buttonBox">
                                        <a href="#" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="modalContainer">
                                <div class="scrollPanel">
                                    <div class="modalLogo"><img src="/images/analytics/big-nasa-logo.png" alt="NAS" /></div>
                                    <h3>Crater Detection</h3>
                                    <dl>
                                        <dt>Client:</dt>
                                        <dd>NAS</dd>
                                        <dt>Contest:</dt>
                                        <dd><a href="javascript:;">NSA Marathon Match 1</a></dd>
                                        <dt>Competitors:</dt>
                                        <dd>100</dd>
                                        <dt>Winner:</dt>
                                        <dd><a href="javascript:;">gset123</a></dd>
                                    </dl>
                                    <h4>The Problem</h4>
                                    <p>Detect craters in a given set of orbital images taken under various illumination conditions and camera poses.  Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
                                    <div class="figure">
                                        <img src="/images/analytics/pic-01.jpg" alt="PIC" />
                                        <img src="/images/analytics/pic-02.jpg" alt="PIC" />
                                        <img src="/images/analytics/pic-03.jpg" alt="PIC" />
                                        <img src="/images/analytics/pic-04.jpg" alt="PIC" />
                                        <em>Note: Images can be used here if exists.</em>
                                    </div>
                                    <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. </p>
                                    <br />
                                    <p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur.</p>
                                    <br />
                                    <h4>The Solution</h4>
                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occa cat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                                    <br />
                                    <p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus.</p>
                                    <br />
                                    <h4>The Results</h4>
                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p>
                                    <br />
                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
                                    <br />
                                    <div class="buttonBox">
                                        <a href="#" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
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
