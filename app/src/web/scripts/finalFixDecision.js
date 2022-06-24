// the cookie to store bank data

function sendNeedFinalFixesRequest(contestId, submissionId, needFinalFixes) {
    var request = {};
    request['contestId'] = contestId;
    request['submissionId'] = submissionId;
    request['needFinalFix'] = needFinalFixes;

    $.ajax({
        type: 'POST',
        url: ctx + "/contest/needFinalFix",
        data: request,
        cache: false,
        dataType: 'json',
        async: false,
        success: function (jsonResult) {
            modalClose();
            if (needFinalFixes) {
                window.location.href = ctx + "/contest/viewFinalFix?projectId=" + contestId;
            } else {
                $('.finalFixConfirm .p1').hide();
                $('.finalFixConfirm .p2').show();
            }
        }
    });
}

$(document).ready(function() {
    var contestId = $("#contestId").val();

    var winningSubmissionId = '';
    if ($('.winnerCol a.downloadFile').length > 0) {
        var url = $('.winnerCol a.downloadFile').attr('href');
        var p1 = url.indexOf('&sbmid=');
        var p2 = url.indexOf('&', p1 + 1);
        winningSubmissionId = url.substring(p1 + '&sbmid='.length, p2);
    }

    if (winningSubmissionId != '') {
        $('.finalFixConfirm .noBtn').click(function () {
            sendNeedFinalFixesRequest(contestId, winningSubmissionId, false);
        });

        $('.finalFixConfirm .yesBtn').click(function () {
            sendNeedFinalFixesRequest(contestId, winningSubmissionId, true);
        });
    }
});
