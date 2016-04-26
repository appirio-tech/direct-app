/*
 * Copyright (C) 2015-2016 TopCoder Inc., All Rights Reserved.
 *
 * Javascript for the project work manager pages.
 *
 * Version 1.1 (TOPCODER DIRECT - ASP INTEGRATION WORK MANAGEMENT IMPROVEMENT) changes:
 * - clicking "Push Submissions" button displays a confirmation dialog
 * - added button for checking the submission's push status
 *
 * @version 1.1
 * @author GreatKevin, isv
 */
$(document).ready(function() {
    // these global variables store the current state of the hColumns control
    var workStepResult = [];
    var challengeResult = [];
    var phaseResult = [];
    var currentWorkStep;
    var currentChallenge;
    var currentPhase;
    var currentPushStatus;
    var pushId = 0;
    var pushStatusChecking = false; 
    
    function checkPushStatus() {
        if (pushStatusChecking) {
            return;
        }
        pushStatusChecking = true;
        $('#checkPushStatusBtn').html("Checking push status... ");
        $.ajax({
            type: 'GET',
            url: ctx + "/getSubmissionPushStatus",
            data: {pushId: pushId},
            cache: false,
            async: true,
            dataType: 'json',
            success: function (jsonResult) {
                pushStatusChecking = false;
                handleJsonResult(jsonResult,
                    function (result) {
                        $('#checkPushStatusBtn').html("Check push status: " + result["pushStatus"]);
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
    }
    
    $("#pushButtonDiv a#checkPushStatusBtn").click(function() {
        checkPushStatus();
    });

    $("#pushButtonDiv a#pushSubmissionsBtn").click(function() {
        $('.pushStatus').hide();
        displayUserConfirmation("#pushSubmissionsConfirmation", "Push Submissions",
            "Do you want to push " + currentPushStatus + " to TopCoder Connect?", "Push", function() {
                closeModal();
                modalPreloader();
                $.ajax({
                    type: 'POST',
                    url: ctx + "/pushSubmissions",
                    data: {contestId: currentChallenge.id, phaseName: currentPhase.label, workStepId: currentWorkStep.id, workStepName: currentWorkStep.workStepType, formData: {projectId: tcDirectProjectId}},
                    cache: false,
                    async: true,
                    dataType: 'json',
                    success: function (jsonResult) {
                        closeModal();
                        handleJsonResult(jsonResult,
                            function (result) {
                                pushId = result["pushId"];
                                showSuccessfulMessage("The " + currentPushStatus + " pushed to work manager");
                                $('.pushStatus').show();
                                checkPushStatus();
                            },
                            function (errorMessage) {
                                showServerError(errorMessage);
                            });
                    }
                });
            }, "Cancel");
    });

    $("#WorkManagerDiv").hColumns({
        noContentString: "No matchable result",
        searchPlaceholderString: "Retrieving result...",
        customNodeTypeIndicator: {phase: "icon-phase"},
        customNodeTypeHandler: {
            phase: function (hColumn, node, data) {
                currentPhase = data;

                modalPreloader();

                // get the submission count and status
                $.ajax({
                    type: 'POST',
                    url:  ctx + "/getSubmissionDataForPhase",
                    data: {contestId: currentChallenge.id, phaseName: currentPhase.label, workStepName: currentWorkStep.workStepType, formData: {projectId: tcDirectProjectId}},
                    cache: false,
                    async: false,
                    dataType: 'json',
                    success: function(jsonResult) {
                        handleJsonResult(jsonResult,
                            function(result) {
                                currentPushStatus = result.pushStatus;
                                $("#pushButtonDiv a#pushSubmissionsBtn").text("Push " + result.pushStatus).show();
                            },
                            function(errorMessage) {
                                showServerError(errorMessage);
                            });
                    }
                });


            }
        },
        nodeSource: function(node_id, callback) {

            $("#pushButtonDiv a").hide();

            // the first column of work steps when node_is is null
            // load the data via ajax for the first column
            if(node_id === null) {
                modalPreloader();
                $.ajax({
                    type: 'POST',
                    url:  ctx + "/getProjectWorkSteps",
                    data: {formData: {projectId: tcDirectProjectId}},
                    cache: false,
                    async: false,
                    dataType: 'json',
                    success: function(jsonResult) {
                        handleJsonResult(jsonResult,
                            function(result) {
                                // cache workstepResult in a global var
                                workStepResult = result;
                            },
                            function(errorMessage) {
                                showServerError(errorMessage);
                            });
                    }
                });

                return callback(null, workStepResult);
            }


            // check if node id is is one of the workstep nodes cached
            var workStepLength = workStepResult.length;

            for(var i = 0; i < workStepLength; i++) {
                if(node_id == workStepResult[i].id) {
                    currentWorkStep = workStepResult[i];
                    modalPreloader();
                    $.ajax({
                        type: 'POST',
                        url:  ctx + "/getWorkStepChallenges",
                        data: {formData: {projectId: tcDirectProjectId}, workStepName: currentWorkStep.workStepType},
                        cache: false,
                        async: false,
                        dataType: 'json',
                        success: function(jsonResult) {
                            handleJsonResult(jsonResult,
                                function(result) {
                                    challengeResult = result;
                                },
                                function(errorMessage) {
                                    showServerError(errorMessage);
                                });
                        }
                    });

                    return callback(null, challengeResult);
                }
            }

            // check if node id is one of the challenge nodes
            var challengeLength = challengeResult.length;

            for(var i = 0; i < challengeLength; i++) {
                if(node_id == challengeResult[i].id) {
                    currentChallenge = challengeResult[i];
                    modalPreloader();
                    $.ajax({
                        type: 'POST',
                        url:  ctx + "/getContestPhasesForWorkStep",
                        data: {contestId: currentChallenge.id, workStepName: currentWorkStep.workStepType, formData: {projectId: tcDirectProjectId}},
                        cache: false,
                        async: false,
                        dataType: 'json',
                        success: function(jsonResult) {
                            handleJsonResult(jsonResult,
                                function(result) {
                                    phaseResult = result;
                                },
                                function(errorMessage) {
                                    showServerError(errorMessage);
                                });
                        }
                    });

                    return callback(null, phaseResult);
                }
            }
        }
    });

});
