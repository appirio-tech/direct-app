/**
 * Author: TCSASSEMBLER
 * Version: 1.0 (TC-Studio - Wireframe Viewer Modal Window Direct integration assembly v1.0)
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 *
 * The JS script used to restrict the ability to download pages via "view source", right click save page etc. 
 */
function noRightClick(){
    $(document).bind('contextmenu', function(){return false;});
    document.onkeydown = function(e){
        if((window.event.ctrlKey) && (window.event.keyCode == 83))
        {
            alert('Can\'t save!');
        }
        if((window.event.ctrlKey) && (window.event.keyCode == 85))
        {
            alert('Can\'t view source!');
        }
    }
}