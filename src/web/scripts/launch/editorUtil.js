/**
 * Copyright (C) 2010 - 2016 TopCoder Inc., All Rights Reserved.
 *
 * Editor Utility Javascript
 * @version 1.1
 */

/**
 * This method is used to initialize code mirrors with some exisiting data
 * @param toggleableEditorIds - ids of the textareas to be converted into CMs
 * @param checkboxIds - ids of checkboxes controlling the toggle between CM and CKEditor
 * @param values - values with which the CMs are instantiated
 */
function initCodeMirrorEditors(toggleableEditorIds, checkboxIds, values) {
    $.each(toggleableEditorIds, function(it, id) {
        var codeMirrorEditor = codeMirrorTemplate(id);
        codeMirrorEditor.setValue(values[it]);

        bindCheckboxes(id, codeMirrorEditor, checkboxIds, it);
    });
}

/**
 * Util method to bind the behavior of checkboxes
 * Used to toggle the view of editors(CKEditor and CM) on the toggle of checkbox
 * @param id - id of the element to which CM is bound
 * @param codeMirrorEditor - codeMirrorEditor instance
 * @param checkboxIds - ids of the checkboxes
 * @param it - index of the iterator while initializing CMs
 */
function bindCheckboxes(id, codeMirrorEditor, checkboxIds, it) {
    $("#" + id).data(id + '-cm', codeMirrorEditor);

    $("#" + checkboxIds[it]).bind("change", function () {
        toggleEditorMode(id, checkboxIds[it]);
    });

    toggleEditorMode(id, checkboxIds[it]);
}

/**
 * This method is used to initialize new codemirror instances
 * @param toggleableEditorIds - ids of the textareas to which CM needs to be bound
 * @param checkboxIds - ids of checkboxes toggling the view of editor
 */
function initBlankCodeMirrorEditors(toggleableEditorIds, checkboxIds) {
    $.each(toggleableEditorIds, function(it, id) {
        var codeMirrorEditor = codeMirrorTemplate(id);
        bindCheckboxes(id, codeMirrorEditor, checkboxIds, it);
    });
}

/**
 * Helper to find the codemirror instance bound to the element whose id is passed
 * @param elementId - id of element to which CM is bound
 * @returns codemirror instance
 */
function findCMById(elementId) {
    return $("#" + elementId).data(elementId + '-cm');
}

/**
 * Method to take care of toggle of editors (CKEditor and CM) based on the checkbox
 * @param elementId - id of element to which both editors are bound
 * @param checkboxId - id of checkbox controlling the toggle
 */
function toggleEditorMode(elementId, checkboxId) {
    var codeMirrorEditor = findCMById(elementId);
    var ckEditor = $("#cke_" + elementId);

    if ($("#" + checkboxId).is(":checked")) {
        ckEditor.hide();
        $(codeMirrorEditor.getWrapperElement()).show();
    } else {
        ckEditor.show();
        $(codeMirrorEditor.getWrapperElement()).hide();
    }
}

/**
 * Helper template to generate a CM instance
 * @param id - id of element to which CM is bound
 * @returns CM instance
 */
function codeMirrorTemplate(id) {
    return CodeMirror.fromTextArea(document.getElementById(id), {
        mode: 'markdown',
        autoRefresh: true,
        lineNumbers: true,
        theme: "default",
        extraKeys: {"Enter": "newlineAndIndentContinueMarkdownList"}
    });
}