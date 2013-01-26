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

CKEDITOR.addTemplates("software_guidelines_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Assembly",
            image: "template.gif",
            description: "Assembly Software Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/assembly_software_guidelines_template.txt'
        },
        {
            title: "UI Prototype",
            image: "template.gif",
            description: "UI Prototype Software Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/ui_prototype_software_guidelines_template.txt'
        },
        {
            title: "Architecture",
            image: "template.gif",
            description: "Architecture Software Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/architecture_software_guidelines_template.txt'
        },
        {
            title: "Conceptualization",
            image: "template.gif",
            description: "Conceptualization Software Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/conceptualization_software_guidelines_template.txt'
        },
        {
            title: "Specification",
            image: "template.gif",
            description: "Specification Software Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/specification_software_guidelines_template.txt'
        },
        {
            title: "Test Scenarios",
            image: "template.gif",
            description: "Test Scenarios Software Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/test_scenarios_software_guidelines_template.txt'
        },
        {
            title: "Test Suites",
            image: "template.gif",
            description: "Test Suites Software Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/test_suites_software_guidelines_template.txt'
        },
        {
            title: "Bug Hunt",
            image: "template.gif",
            description: "Bug Hunt Software Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/bug_hunt_software_guidelines_template.txt'
        }
    ]
});

// loading templates asychronously
var templates = CKEDITOR.getTemplates("software_guidelines_templates").templates;
for (var i = 0; i < templates.length; ++i) {
    getTemplateContent(templates[i]);
}