$(document).ready(function() {
    // these global variables store the current state of the hColumns control
    var workStepResult = [];
    var challengeResult = [];
    var phaseResult = [];
    var currentWorkStep;
    var currentChallenge;
    var currentPhase;
    var currentPushStatus;

    $("#pushButtonDiv a").click(function() {

		modalPreloader();

        $.ajax({
            type: 'POST',
            url:  ctx + "/pushSubmissions",
            data: {contestId: currentChallenge.id, phaseName: currentPhase.label, workStepId: currentWorkStep.id, formData: {projectId: tcDirectProjectId}},
            cache: false,
            async: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                    function(result) {
                        showSuccessfulMessage("The " + currentPushStatus + " pushed to work manager");
                    },
                    function(errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
    })

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
                    data: {contestId: currentChallenge.id, phaseName: currentPhase.label, formData: {projectId: tcDirectProjectId}},
                    cache: false,
                    async: false,
                    dataType: 'json',
                    success: function(jsonResult) {
                        handleJsonResult(jsonResult,
                            function(result) {
                                currentPushStatus = result.pushStatus;
                                $("#pushButtonDiv a").text("Push " + result.pushStatus).show();
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
                        url:  ctx + "/getWorkStepDesignChallenges",
                        data: {formData: {projectId: tcDirectProjectId}},
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
