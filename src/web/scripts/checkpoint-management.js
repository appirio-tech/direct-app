/**
 * Author: TCSASSEMBLER
 * Version: 1.0 (Release Assembly - TopCoder Cockpit Software Checkpoint Management)
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 *
 * The Javascript File used for software checkpoint management.
 */
$(document).ready(function() {
    // dataTable
    $('#tcSoftwareMM .mmDataTable table').dataTable( {
            "bSort": false,
            "sPaginationType": "full_numbers",
            "sDom": '<"mmPagers"lp>rt<"mmPagers"lp>',
            "bAutoWidth": false,
            "oLanguage": {
            "oPaginate": {
            "sPrevious": "Prev"
        },
        "sLengthMenu": 'Show: <select>'+
            '<option value="5">5</option>'+
            '<option value="10">10</option>'+
            '<option value="25">25</option>'+
            '<option value="-1">All</option>'+
            '</select> &nbsp;Per Page'
        }
    } ); 

    /* close modal */
    var  modalClose = function() {
        $('#modalBackground').hide();
        $('.outLay').hide();
    }

    /* load modal (string itemID )*/
    var  modalLoad = function(itemID) {
        modalClose();
        $('#modalBackground').show();
        $(itemID).show();
        modalPosition();
    }
    
    $('#new-modal .outLay .closeModal, #new-modal .outLay .closeIt, #saveSuccessModal a.newButton1').live('click', function(){
        modalClose();
        return false;
    });

    var slotsManage = {
        prizes:[null,null,null,null,null],
        setPrize:function(trigger){
            var row = trigger.parents("tr");
            var index = trigger.parent().find("a").index(trigger);
            if(this.prizes[index]){
                this.removePrize(this.prizes[index]);   
            }
            
            this.prizes[index] = row;
            var slot = $(".slotsMask .slot:eq(" + index + ")").addClass("on");
            slot.find(".place").before($("<span/>",{
                "class":"sub",
                "text": row.find("a.submission").text()
            })).after($("<a/>",{
                "class":"rm",
                "href":"javascript:;"
            }));
            row.find("td:eq(0)").addClass("pTag" + index);
            row.data("prize",index);
            trigger.addClass("on");
            return false;
        },
        removePrize:function(row){
            row.find(".prizes a").removeClass("on");
            var index = row.data("prize");
            row.find("td:eq(0)").removeClass("pTag" + index);
            $(".slotsMask .slot:eq(" + index + ")").removeClass("on").find(".sub,.rm").remove(); 
            row.data("prize",-1);
            this.prizes[index] = null;
            return false;
        },
        clearSlots:function(){
            $.each(this.prizes,function(index,prize){
               if(prize){
                    slotsManage.removePrize(prize);   
               }
            })
        },
        loadAddModal:function(trigger,fbarea){
            $("#addFeedbackModal .modalHeaderCenter span").text(trigger.data("title"));
            $("#addFeedbackModal textarea").val("");
            $("#addFeedbackModal").data("fbarea",fbarea);
            $('#addFeedbackModal .error').hide();
            modalLoad("#addFeedbackModal");
            return false;
        },
        loadEditModal:function(trigger,fbarea){
            $("#editFeedbackModal .modalHeaderCenter span").text(trigger.data("title"));
            var tdiv = $("<div></div>").html(fbarea.html().replace(/<br>/gi, "\n"));            
            $("#editFeedbackModal textarea").val(tdiv.text());
            $("#editFeedbackModal").data("fbarea",fbarea); 
            $('#editFeedbackModal .error').hide();
            modalLoad("#editFeedbackModal");
            return false;
        },
        addFeedback:function(){
            var text = $.trim($("#addFeedbackModal textarea").val());
            var tdiv = $("<div></div>").text(text);
            tdiv.html(tdiv.html().replace(/\n/gi, "<br>"));
            var html=tdiv.html();
            if(html == "") {
                $('#addFeedbackModal .error').html("Feedback to be added cannot be empty.").show();
                return false;
            } else if (html.length>4096) {
                $('#addFeedbackModal .error').html("The maximum feedback length is 4096.").show();
                return false;
            }
            $('#addFeedbackModal .error').hide();
            $("#addFeedbackModal").data("fbarea").html(html);
            $("#addFeedbackModal").data("fbarea").parent().find(".edit").show();
            $("#addFeedbackModal").data("fbarea").parent().find(".add").hide();
            $("#addFeedbackModal").data("fbarea").show();
            modalClose();
        },
        updateFeedback:function(){
            var text = $.trim($("#editFeedbackModal textarea").val());
            var tdiv = $("<div></div>").text(text);
            tdiv.html(tdiv.html().replace(/\n/gi, "<br>"));
            var html=tdiv.html();
            if (html.length>4096) {
                $('#editFeedbackModal .error').html("The maximum feedback length is 4096.").show();
                return false;
            }
            $('#editFeedbackModal .error').hide();
            $("#editFeedbackModal").data("fbarea").html(html);
            if($.trim($("#editFeedbackModal textarea").val()) == ""){               
                $("#editFeedbackModal").data("fbarea").html("");
                $("#editFeedbackModal").data("fbarea").parent().find(".edit").hide();
                $("#editFeedbackModal").data("fbarea").parent().find(".add").show();
                $("#editFeedbackModal").data("fbarea").hide();
            } 
            modalClose();
        },
        noSlots:function(){
            return !this.prizes[0] && !this.prizes[1] && !this.prizes[2] && !this.prizes[3] && !this.prizes[4];
        },
        // check if there's skip in the winner selection.
        hasNoSkip:function(){
            var firstNull = 5;
            var lastNonNull = -1;
            for (var i=0;i<5;++i) {
                if (this.prizes[i])lastNonNull=i;
                else if (i<firstNull)firstNull=i;
            }
            return firstNull==lastNonNull+1;
        }
    }

    $("#tcSoftwareMM .slotsMask a.clearSlots").click(function(){
        slotsManage.clearSlots();            
    })
    $("#tcSoftwareMM .slotsMask .slot .rm").live("click",function(){
        var slot =  $(this).parents(".slot");
        var index = slot.parent().find(".slot").index(slot); 
        slotsManage.removePrize(slotsManage.prizes[index]);
        return false;
    })
    $("#tcSoftwareMM .mmDataTable .prizes a").click(function(){ 
        var parent = $(this).parent();
        var row = $(this).parents("tr");
        if($(this).hasClass("on")){
            slotsManage.removePrize(row);    
        }else{
            if(parent.find("a.on").length>0){
                slotsManage.removePrize(row);   
            }
            slotsManage.setPrize($(this)); 
        }
        return false;
    })

    $("#tcSoftwareMM .slotsMask .feedback .action a.add").click(function(){
        if(!$(this).data("title")){
            $(this).data("title","GENERAL FEEDBACK");    
        }
        slotsManage.loadAddModal($(this),$(this).parents(".feedback").find(".fbArea"));
        return false;
    })
    $("#tcSoftwareMM .slotsMask .feedback .action a.edit").click(function(){
        if(!$(this).data("title")){
            $(this).data("title","GENERAL FEEDBACK");
        }
        slotsManage.loadEditModal($(this),$(this).parents(".feedback").find(".fbArea"));
        return false;
    })
    
    $(" #tcSoftwareMM .mmDataTable .fbMask .add a").click(function(){
        if(!$(this).data("title")){
            $(this).data("title","SUBMISSION " + $(this).parents("tr").find("a.submission").text() + " FEEDBACK");
        }
        slotsManage.loadAddModal($(this),$(this).parents(".fbMask").find(".fbArea"));
        return false;
    })
    $(" #tcSoftwareMM .mmDataTable .fbMask .edit a").click(function(){
        if(!$(this).data("title")){
            $(this).data("title","SUBMISSION " + $(this).parents("tr").find("a.submission").text() + " FEEDBACK");
        }
        slotsManage.loadEditModal($(this),$(this).parents(".fbMask").find(".fbArea"));
        return false;
    })

    $("#addFeedbackModal .modalCommandBox .addBtn").click(function(){
        slotsManage.addFeedback(); 
        return false;
    })
    $("#editFeedbackModal .modalCommandBox .saveBtn").click(function(){
        slotsManage.updateFeedback();
        return false;
    })
    var committed=false;
    $("#tcSoftwareMM .ops a.saveit").click(function(){
        committed=false;
        lockinCheckpointReview();
        return false;
    });
       
    $("#tcSoftwareMM .ops a.lock").click(function(){
        committed=true;
        var hasFeedback = false;
        $("td.fbTd .fbMask .fbArea, div.feedback .fbArea").each(function(){
            if ($.trim($(this).html())!=''){
                hasFeedback = true;
                return false;
            }
        });
        if(!slotsManage.hasNoSkip()){
            modalLoad("#lockinInvalidModal");
        }else if(!hasFeedback){
            modalLoad("#lockinNoFeedbackModal");    
        }else if(slotsManage.noSlots()){
            modalLoad("#lockinNoWinnerModal");
        }else {
            modalLoad("#lockinConfirmModal");            
        }
        return false;
    });
    
    // lockin the checkpoint review
    var lockinCheckpointReview = function() {
        var placements=[];
        var feedbacks=[];
        var submissionIds=[];
        var generalFeedback=$.trim($('div.feedback .fbArea').html());
        var projectId=$('#projectIdInput').val();
        $("td.fbTd .fbMask .fbArea").each(function(){
            feedbacks.push($.trim($(this).html()));        
            var td = $(this).parent().parent().prev();
            var pa = td.find("a.on");
            if (pa.length == 0) {
                placements.push(10);   
            } else {
                placements.push(td.find("a").index(pa)+1);   
            }
            var submissionId=parseInt($.trim(td.prev().find("a").text()).substring(1));
            submissionIds.push(submissionId);
        });
       modalClose(); 
       modalPreloader();
       $.ajax({
            type: 'POST',
            url:  "saveSoftwareCheckpointReviewAction",
            data: {projectId:projectId,
                    placements:placements,feedbacks:feedbacks,submissionIds:submissionIds,
                    committed:committed,generalFeedback:generalFeedback},
            cache: false,
            dataType: 'json',
            timeout: 30000,
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                        function(result) {
                            if (committed)modalLoad("#lockinSuccessModal");
                            else modalLoad("#saveSuccessModal");
                        },
                        function(errorMessage) {
                            showServerError(errorMessage);
                        });
            }
       });
       return false;       
    };
    
    $('#lockinSuccessModal a.newButton1').unbind('click').click(function(){
        window.location.href="softwareSubmissions?roundType=CHECKPOINT&projectId="+$('#projectIdInput').val();
        modalClose();
        modalPreloader();
        return false;
    });
    $("#lockinNoFeedbackModal a.lockinBtn").click(function(){
        // we still need to check no winner is selected
        if(slotsManage.noSlots()){
            modalLoad("#lockinNoWinnerModal");
        } else {
            lockinCheckpointReview();   
        }
        return false;
    })  
    $("#lockinNoWinnerModal a.lockinBtn,#lockinConfirmModal .yesBtn").click(function(){
        modalClose();
        lockinCheckpointReview(); 
        return false;
    })
    
    // setup the placements
    $('div.mmDataTable table td input[type="hidden"]').each(function() {
        var rank=parseInt($(this).val());     
        if (rank<=5) {
            if ($(this).hasClass("finalRank")){
                var td=$(this).parent();
                var slot = $(".slotsMask .slot:eq(" + (rank-1) + ")").addClass("on");
                slot.find(".place").before($("<span/>",{
                    "class":"sub",
                    "text": td.find("a.submission").text()
                }));                
                td.addClass("pTag" + (rank-1));                
            } else {
                $(this).parent().next().find("a:eq("+(rank-1)+")").click();
            }
        }
    });
});

