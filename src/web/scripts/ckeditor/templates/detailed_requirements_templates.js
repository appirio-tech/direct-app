function getTemplateContent(template) {
    $.ajax({
        url: template.path,
        type: "GET",
        async: true,
        success: function (result) {
            template.html = result;
        },
        error: function (jqXhr, textStatus, errorThrown) {
            alert("Error '" + jqXhr.status + "' (textStatus: '" + textStatus + "', errorThrown: '" + errorThrown + "')");
        }
    });
}

CKEDITOR.addTemplates("detailed_requirements_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Assembly",
            image: "template.gif",
            description: "Assembly Detailed Requirements.",
            html: '',
            path: '/scripts/ckeditor/templates/assembly_detailed_requirements_template.txt'
        },
        {
            title: "UI Prototype",
            image: "template.gif",
            description: "UI Prototype Detailed Requirements.",
            html: '',
            path: '/scripts/ckeditor/templates/ui_prototype_detailed_requirements_template.txt'
        },
        {
            title: "Architecture",
            image: "template.gif",
            description: "Architecture Detailed Requirements.",
            html: '',
            path: '/scripts/ckeditor/templates/architecture_detailed_requirements_template.txt'
        },
        {
            title: "Conceptualization",
            image: "template.gif",
            description: "Conceptualization Detailed Requirements.",
            html: '',
            path: '/scripts/ckeditor/templates/conceptualization_detailed_requirements_template.txt'
        },
        {
            title: "Specification",
            image: "template.gif",
            description: "Specification Detailed Requirements.",
            html: '',
            path: '/scripts/ckeditor/templates/specification_detailed_requirements_template.txt'
        }
    ]
});

// loading templates asychronously
var templates = CKEDITOR.getTemplates("detailed_requirements_templates").templates;
for (var i = 0; i < templates.length; ++i) {
    getTemplateContent(templates[i]);
}