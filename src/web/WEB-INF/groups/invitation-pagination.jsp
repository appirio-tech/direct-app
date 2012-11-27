<%--
  - Author: TCSASSEMBER
  - Version: 1.0 (Release Assembly - TopCoder Security Groups Frontend - Invitations Approvals)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - This pagination jsp file to be static included in other pages.
--%>
<div class="tableControlPage">
    <div class="leftSide">Showing <strong>${page==0?1:((page-1) * pageSize+1)}</strong> to <strong>${page==0?(invitations.total):((page-1) * pageSize+fn:length(invitations.values))}</strong> of <strong>${invitations.total}</strong> entries</div>
    <div class="rightSide">
        <strong class="label">Show:</strong>
        <select id="pageSizeSelect">
            <option <s:if test="(page>0) and (pageSize==10)">selected="selected"</s:if> value="10">10</option>
            <option <s:if test="(page>0) and (pageSize==25)">selected="selected"</s:if> value="25">25</option>
            <option <s:if test="(page>0) and (pageSize==50)">selected="selected"</s:if> value="50">50</option>
            <option <s:if test="page==0">selected="selected"</s:if> value="0">All</option>
        </select>
        <span class="label">per Page</span>
        
        <input type="hidden" id="totalInput" value="${invitations.total}"/>
        <ul>
            <li><a href="javascript:;" class="prevPage <s:if test='page<=1'>disable</s:if>">Prev</a></li>
            <s:if test="page>1">
            <li><a href="javascript:;">${page-1}</a></li>
            </s:if>
            <li class="current"><strong>${page == 0 ? 1 : page}</strong></li>
            <s:if test="0<page && page<(((invitations.total)+pageSize-1)/pageSize)">
            <li><a href="javascript:;">${page+1}</a></li>
            </s:if>
            <li><a href="javascript:;" class="nextPage <s:if test='(page==0) || (page==(((invitations.total)+pageSize-1)/pageSize))'>disable</s:if>">Next</a></li>
        </ul>
    </div>
    <span class="corner bl"></span>
    <span class="corner br"></span>
</div>
<!-- End .tableControlPage -->