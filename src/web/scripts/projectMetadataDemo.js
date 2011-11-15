/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 *
 * Description: Provides JS for the project metadata demo. It's used by project-metadata-demo.jsp.
 * Author: TCSASSEMBLER
 * Version: 1.0 (Module Assembly - TopCoder Cockpit Project Metadata Service Setup and Integration)
 */
$(document).ready(function() {
    $("#projectTitleSpan").text("Projects Metadata Demo page");

    /**
     * Adds the project metadata.
     */
    $(".add").click(function() {
        var s = $(this).parent();
        var keyId = s.find(".metadataKeyID").val();
        var directProjectId = $("#directProjectInput :selected").val();

        // alert(keyId);
        // alert(directProjectId);

        var value;

        if(s.find(".metadataValue").length > 0) {
            value = s.find(".metadataValue").val();

            if($.trim(value).length == 0) {
                showErrors("The Project metadata value cannot be empty");
                return false;
            }
        } else {
            value = s.find("#metadataListValue :selected").val();
        }

        // alert(value);

        modalPreloader();

        var request = {};
        request['directProjectId'] = directProjectId;
        request['projectMetadataKeyId'] = keyId;
        request['projectMetadataValue'] = value;

        $.ajax({
            type: "POST",
            url: "addProjectMetaData",
            data: request,
            dataType: 'json',
            success: function(jsonResult){

                loadProjectMetadata(false);

                modalAllClose();
            }
        });
    });

    // load the project metadata after loading page.
    loadProjectMetadata(false);

    /**
     * Loads the selected project's project metadata and render.
     *
     * @param showModal whether to show the modal.
     */
    function loadProjectMetadata(showModal) {
        var table = $("#metdataValueSection table tbody");

        table.find("tr").remove();

        var request = {}
        request['directProjectId'] =  $("#directProjectInput :selected").val();

        if(showModal) {
            modalPreloader();
        }

        $.ajax({
            type: "POST",
            url: "getAllProjectMetadata",
            data: request,
            dataType: 'json',
            success: function(jsonResult){
                if(showModal) {
                    modalAllClose();
                }
                $.each(jsonResult.result['return'], function(index, value) {
                    var html = "<tr><td>" + value.projectId + "</td><td>" +
                        value.keyName + "</td><td>" + value.keyId + "</td><td>"
                        + "<input type='text' class='metadataValue' value='" +  value.value + "'></td><td><input type='button' value='Remove' class='delete'/> <input class='update' type='button' style='margin-left:10px' value='Update'/></td>"
                        + "<td style='display:none'>" + value.id + "</td></tr>";

                    table.append(html);
                });
            }
        });
    }

    // render the project metadata when the project is changed.
    $("#directProjectInput").change(function() {
        loadProjectMetadata(true);
    })

    /**
     * Deletes the project metadata.
     */
    $(".delete").live('click', function() {
        var row = $(this).parent().parent();

        var metaDataId = row.find("td:last").text();

        var request = {};
        request['projectMetadataId'] = metaDataId;

        modalPreloader();

        $.ajax({
            type: "POST",
            url: "deleteProjectMetadata",
            data: request,
            dataType: 'json',
            success: function(jsonResult){

                loadProjectMetadata(false);

                modalAllClose();
            }
        });

    });

    /**
     * Updates the project metadata.
     */
    $(".update").live('click', function() {
        var row = $(this).parent().parent();

        var metaDataId = row.find("td:last").text();
        var metaDavaValue = row.find(".metadataValue").val();
        var directProjectId = row.find("td:first").text();
        var metaDataKeyId = row.find('td:eq(2)').text();

        var request = {};
        request['projectMetadataId'] = metaDataId;
        request['projectMetadataValue'] = metaDavaValue;
        request['projectMetadataKeyId'] = metaDataKeyId;
        request['directProjectId'] = directProjectId;

        modalPreloader();

        $.ajax({
            type: "POST",
            url: "updateProjectMetadata",
            data: request,
            dataType: 'json',
            success: function(jsonResult){

                loadProjectMetadata(false);

                modalAllClose();
            }
        });

    });

    /**
     * Searches the projects by project metadata.
     */
    $("input[name='metadataSearchbutton']").click(function(){

        var searchKey = $("#metadataSearchKey :selected").val();
        var searchMethod = $("#metadataSearchMethod :selected").val();
        var metaSearchValue = $("input[name='metadataSearchValue']").val();

        var request = {};
        request['metadataKeyName'] = searchKey;
        request['searchValue'] = metaSearchValue;
        request['searchMethod'] = searchMethod;

        var table = $("#metdataSearchSection table tbody");

        modalPreloader();

        $.ajax({
            type: "POST",
            url: "searchProjectByMetadata",
            data: request,
            dataType: 'json',
            success: function(jsonResult){

                table.find("tr").remove();

                $.each(jsonResult.result['return'], function(index, value) {
                    var html = "<tr><td>" + value.projectId + "</td><td>" +
                        value.projectName + "</td><td>" + value.projectDescription + "</td><td>"
                        + value.projectStatusId + "</td></tr>";

                    table.append(html);
                });

                modalAllClose();
            }
        });
    });

    /**
     * Adds the project metadata key.
     */
    $("#addKeyButton").click(function(){
        var keyName = $("input[name='keyName']").val();
        var keyDescription = $("input[name='keyDescription']").val();
        var singleOrNot = $("#singleChoose :selected").val();

        var request = {};

        request['metadataKeyName'] = keyName;
        request['metaDataKeyDescription'] = keyDescription;
        request['keySingle'] = singleOrNot;

        modalPreloader();

        $.ajax({
            type: "POST",
            url: "addProjectMetadataKey",
            data: request,
            dataType: 'json',
            success: function(jsonResult){
                modalAllClose();

                location.reload(true);
            }
        });
    });


});