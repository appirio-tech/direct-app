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

CKEDITOR.addTemplates("default_detailed_requirements_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Assembly",
            image: "template.gif",
            description: "Assembly Detailed Requirements.",
            html: '',
            path: '/scripts/ckeditor/templates/assembly_detailed_requirements_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("assembly_detailed_requirements_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Assembly",
            image: "template.gif",
            description: "Assembly Detailed Requirements.",
            html: '',
            path: '/scripts/ckeditor/templates/assembly_detailed_requirements_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("ui_prototype_detailed_requirements_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "UI Prototype",
            image: "template.gif",
            description: "UI Prototype Detailed Requirements.",
            html: '',
            path: '/scripts/ckeditor/templates/ui_prototype_detailed_requirements_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("architecture_detailed_requirements_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Architecture",
            image: "template.gif",
            description: "Architecture Detailed Requirements.",
            html: '',
            path: '/scripts/ckeditor/templates/architecture_detailed_requirements_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("conceptualization_detailed_requirements_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Conceptualization",
            image: "template.gif",
            description: "Conceptualization Detailed Requirements.",
            html: '',
            path: '/scripts/ckeditor/templates/conceptualization_detailed_requirements_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("specification_detailed_requirements_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Specification",
            image: "template.gif",
            description: "Specification Detailed Requirements.",
            html: '',
            path: '/scripts/ckeditor/templates/specification_detailed_requirements_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("bug_hunt_detailed_requirements_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Bug Hunt",
            image: "template.gif",
            description: "Bug Hunt Detailed Requirements.",
            html: '',
            path: '/scripts/ckeditor/templates/bug_hunt_detailed_requirements_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("test_scenarios_detailed_requirements_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Test Scenarios",
            image: "template.gif",
            description: "Test Scenarios Detailed Requirements.",
            html: '',
            path: '/scripts/ckeditor/templates/test_scenarios_detailed_requirements_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("test_suites_detailed_requirements_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Test Suites",
            image: "template.gif",
            description: "Test Suites Detailed Requirements.",
            html: '',
            path: '/scripts/ckeditor/templates/test_suites_detailed_requirements_template.txt'
        }
    ]
});

var templatesList = [
                        "default_detailed_requirements_templates",
                        "assembly_detailed_requirements_templates",
                        "ui_prototype_detailed_requirements_templates",
                        "architecture_detailed_requirements_templates",
                        "conceptualization_detailed_requirements_templates",
                        "specification_detailed_requirements_templates",
                        "bug_hunt_detailed_requirements_templates",
                        "test_scenarios_detailed_requirements_templates",
                        "test_suites_detailed_requirements_templates"
                    ];
                    
// loading templates asychronously
for (var i = 0; i < templatesList.length; ++i) {
    var templates = CKEDITOR.getTemplates(templatesList[i]).templates;
    for (var j = 0; j < templates.length; ++j) {
        getTemplateContent(templates[j]);
    }
}