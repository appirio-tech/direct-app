/** 
 *  * @license Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved. 
 *   * For licensing, see LICENSE.html or http://ckeditor.com/license 
 *     
 */ 
 
CKEDITOR.editorConfig = function (config) { 
    // Define changes to default configuration here. For example: 
    // config.language = 'fr'; 
    // config.uiColor = '#AADC6E'; 
    config.filebrowserImageUploadUrl = null; 
    config.enterMode = CKEDITOR.ENTER_BR; 
    config.toolbar = [ 
       { name: 'document', items : ['Source','-','Templates','-', 'SpellChecker' ] }, 
       { name: 'clipboard', items : [ 'Cut','Copy','Paste','-','Undo','Redo' ] }, 
       { name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] }, 
       { name: 'max', items : [ 'Maximize' ] }, 
       '/', 
       { name: 'styles', items : [ 'Format','Font','FontSize', '-', 'TextColor','BGColor'] }, 
       { name: 'paragraph', items : [ 'Outdent','Indent','-','Blockquote', 
       '-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock' ] }, 
       { name: 'links', items : [ 'Link','Unlink' ] } 
    ]; 
}; 
