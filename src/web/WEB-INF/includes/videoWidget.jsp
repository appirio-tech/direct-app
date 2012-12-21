<%--
  - Author: TCSASSEMBLER
  - Version: 1.0 (Release Assembly - TC Cockpit Sidebar Header and Footer Update)
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: The help video area appears on the home page of TopCoder Cockpit.
  -
  - The default links of movie and all videos page can be overriden in the loadHelps.js.
--%>
<div class="box videoBox">
    <!-- video wrapper -->
    <div class="videoContent">
        <object id="videoContainer" type="application/x-shockwave-flash" data="https://www.youtube.com/v/ubU0_mVtxJc">
            <param name="movie" id="movieLink" value="https://www.youtube.com/v/xUxfwF4WK54"/>
            <param name="FlashVars" value="playerMode=embedded"/>
        </object>
    </div>
    <a href="https://www.youtube.com/user/TopCoderInc/videos?view=0" target="_blank" class="allVideos">All Videos</a>

    <div class="clear"></div>
</div>