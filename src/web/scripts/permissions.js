// JavaScript function for permissions.html
$(document).ready(function(){

  // customize the select
  //$('.display-perpage select').sSelect({ddMaxHeight: '200px'});

  // pop modal window
  $.each(["manageUserDialog","addUserDialog","addProjectDialogPm"],function(index,item){
    $('#' + item).dialog({
          autoOpen: false,
          height:450,
          width: 580,
          modal: true,
          draggable:false,
          resizable:false/*,
          show:{effect:'fade',mode:'show',speed:300},
          hide:{effect:'fade',mode:'hide',speed:300}*/
      });

    $('#' + item + ' .closeDialog').click(function(){
      $('#' + item).dialog("close");
      return false;
    });
    $('#' + item + ' .foot .makeSureButton').click(function(){
      $('#' + item).dialog("close");
      $('#makeSureDialog').dialog("open");
      return false;
    });
  })


  if($('.addCopilotUsers').length >0 ){

    $('.addCopilotUsers').click(function(){
      $('#manageUserDialog').dialog("open");
      return false;
    })
  }
  $('.addUser').click(function(){
    $('#manageUserDialog').dialog("open");
    return false;
  })

  $('.addProject').click(function(){
    $('#addProjectDialogPm').dialog("open");
    return false;
  })

  $('.addMoreUsers').click(function(){
    $('#addUserDialog').dialog("open");
    return false;
  })

  $(".permissions .firstItem").click(function(){
    $("#projects").show();
    $("#users").hide();
    $(this).addClass("on");
    $(".permissions .lastItem").removeClass("on");
  })

  $(".permissions .lastItem").click(function(){
    $("#users").show();
    $("#projects").hide();
    $(this).addClass("on");
    $(".permissions .firstItem").removeClass("on");
  })


  $(".ui-dialog .body .list .listItem").click(function(){
    $(this).toggleClass('active');
  })

  $(".ui-dialog .body .left .subtitle .rightTxt").toggle(
    function(){
      $(this).parent().parent().find('.listItem').addClass('active');
    },
    function(){
      $(this).parent().parent().find('.listItem').removeClass('active');
    }
  );
  $(".ui-dialog .body .right .subtitle .rightTxt").click(function(){
    //$(this).parent().siblings('.list').empty();
    var addItem = $(this).parent().parent().find('.listItem');
    var addTo = $(this).parent().parent().siblings('.left').find('.list');
    var addToList = addTo.find('.listItem');
    var addToLeng = addToList.length;
    var k;

    addItem.each(function(){
      if(addToLeng >0 ){

        for(k=0;k<addToLeng;k++){
          if($.trim($(this).html()) == $.trim(addToList.eq(k).html())){
            addToList.eq(k).remove();
            break;
          }
        }

        $(this).removeClass('active').appendTo(addTo);

      }
      else{
        $(this).removeClass('active').appendTo(addTo);

      }
    });
  });

  $(".ui-dialog .body .middle .addItem").click(function(){
    var addItem = $(this).parent().siblings('.left').find('.active');
    var addTo = $(this).parent().siblings('.right').find('.list');
    var addToList = addTo.find('.listItem');
    var addToLeng = addToList.length;
    var k ;//, existStatus = false;

    addItem.each(function(){
      if(addToLeng >0 ){
        //existStatus = false;
        for(k=0;k<addToLeng;k++){
          if($.trim($(this).html()) == $.trim(addToList.eq(k).html())){
            //existStatus = true;
            addToList.eq(k).remove();
            break;
          }
        }
        //if(!existStatus){
          $(this).removeClass('active').appendTo(addTo);
        //}
      }
      else{
        $(this).removeClass('active').appendTo(addTo);
        //existStatus = false;
      }
    });

  });
  $(".ui-dialog .body .middle .removeItem").click(function(){
    var addItem = $(this).parent().siblings('.right').find('.active');
    var addTo = $(this).parent().siblings('.left').find('.list');
    var addToList = addTo.find('.listItem');
    var addToLeng = addToList.length;
    var k;

    addItem.each(function(){
      if(addToLeng >0 ){

        for(k=0;k<addToLeng;k++){
          if($.trim($(this).html()) == $.trim(addToList.eq(k).html())){
            addToList.eq(k).remove();
            break;
          }
        }

        $(this).removeClass('active').appendTo(addTo);

      }
      else{
        $(this).removeClass('active').appendTo(addTo);

      }
    });
  });




  $.each(["removeProjectDialog","modifyCopilotDialog","makeSureDialog"],function(index,item){
    $('#' + item).dialog({
          autoOpen: false,
          height:160,
          width: 580,
          modal: true,
          draggable:false,
          resizable:false/*,
          show:{effect:'fade',mode:'show',speed:300},
          hide:{effect:'fade',mode:'hide',speed:300}*/
      });

    $('#' + item + ' .closeDialog').click(function(){
      $('#' + item).dialog("close");
      return false;
    });
    $('#' + item + ' .foot .okeyButton').click(function(){
      $('#' + item).dialog("close");
      return false;
    });
    $('#' + item + ' .foot .removeProjectMakeSureButton').click(function(){
      $('#' + item).dialog("close");

      if($('.newDeleteStatus').length >0){
        if($('.newDeleteStatus').hasClass("lastTr")){
          if($('.newDeleteStatus').prev(".trChild").length > 0){
            $('.newDeleteStatus').prev(".trChild").addClass("lastTr");
          }
          $('.newDeleteStatus').remove();
        }else{
          $('.newDeleteStatus').remove();
        }
      }

      return false;
    });
    $('#' + item + ' .foot .makeSureButton').click(function(){
      $('#' + item).dialog("close");
      $('#makeSureDialog').dialog("open");
      return false;
    });

    $('#' + item + ' .foot .okeyButton2').click(function(){
      $('#' + item).dialog("close");
      return false;
    });

    $('#' + item + ' .foot .okey2Button').click(function(){
      $('#' + item).dialog("close");
      return false;
    });

  })
  if($('.closeProjectCopilot').length >0 ){

    $('.closeProjectCopilot').click(function(){
      $('.trChild').removeClass("newDeleteStatus");
      $(this).parent().parent().addClass('newDeleteStatus');
      //$('#removeProjectDialog').dialog("open");
      modalLoad("#copilotRemoveModal");
      return false;
    })
  }

  if($('.modifyProfile').length >0 ){

    $('.modifyProfile').click(function(){
      $('#modifyCopilotDialog').dialog("open");
      return false;
    })
  }


})
