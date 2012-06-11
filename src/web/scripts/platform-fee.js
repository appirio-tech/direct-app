/**
 * Author: minhu
 * Version: 1.0 (Module Assembly - Add Monthly Platform Fee Feature to Admin Page)
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 *
 * The Javascript File used in customer platform fee JSP pages.
 */
$(document).ready(function() {
   var feeId = $('#fee_id').val(); 
   if ($.trim($('#editPaymentDate').html()).length>0) {
       $('#paymentDate').val($.trim($('#editPaymentDate').html()));   
   }
    /* init date-pack */
    if ($('.date-pick').length > 0) {
        $(".date-pick").datePicker().val($('#paymentDate').val()).trigger('change');
    }
   
   // check parameters and submit the form
   $('#submitPlatformFee').click(function() {
       var clientId=$('#fee_clientId').val();
       if (feeId <= 0) {
           if ("-1" == clientId) {    
               showErrors("Please select the customer.");
               return false;
           }
       }
       var paymentDate=$.trim($('#paymentDate').val());
       if (paymentDate.length==0) {    
           showErrors("Please select the payment date.");
           return false;
       }
       var amount = parseFloat($.trim($('#fee_amount').val()));
       if (!(amount >= 0)) {    
           showErrors("The amount value should be a non-negative number.");
           return false;
       } else if (amount > 99999.99) {
           showErrors("The amount value should not be greater than 99999.99.");
           return false;
       }
       
       modalPreloader();
       $.ajax({
            type: 'POST',
            url:  "checkRecordExist",
            data: {'fee':{id:feeId,clientId:clientId,paymentDate:paymentDate,amount:amount}},
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                        function(result) {
                            var func = function() {
                                closeModal();
                                modalPreloader();                          
                                $('#savePlatformFeeForm').submit();
                            };
                            if (result.exist) {
                                showConfirmation("Confirmation",
                                    "There's already another record with the same client and year/month? " +
                                    "Are you sure you want to proceed?", "YES", func);
                            } else {
                               func();
                            }
                        },
                        function(errorMessage) {
                            showServerError(errorMessage);
                        });
            }
       });
       return false;
   });
   
   // if yes, submit the form
   $('#feeExistModal .yesbutton').click(function() {
       modalPreloader();
   });
});

