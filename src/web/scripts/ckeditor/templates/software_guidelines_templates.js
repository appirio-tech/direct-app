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

CKEDITOR.addTemplates("default_software_guidelines_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Assembly",
            image: "template.gif",
            description: "Assembly Submission Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/assembly_software_guidelines_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("assembly_software_guidelines_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Assembly",
            image: "template.gif",
            description: "Assembly Submission Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/assembly_software_guidelines_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("ui_prototype_software_guidelines_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "UI Prototype",
            image: "template.gif",
            description: "UI Prototype Submission Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/ui_prototype_software_guidelines_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("architecture_software_guidelines_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Architecture",
            image: "template.gif",
            description: "Architecture Submission Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/architecture_software_guidelines_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("conceptualization_software_guidelines_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Conceptualization",
            image: "template.gif",
            description: "Conceptualization Submission Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/conceptualization_software_guidelines_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("specification_software_guidelines_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Specification",
            image: "template.gif",
            description: "Specification Submission Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/specification_software_guidelines_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("bug_hunt_software_guidelines_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Bug Hunt",
            image: "template.gif",
            description: "Bug Hunt Submission Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/bug_hunt_software_guidelines_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("test_scenarios_software_guidelines_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Test Scenarios",
            image: "template.gif",
            description: "Test Scenarios Submission Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/test_scenarios_software_guidelines_template.txt'
        }
    ]
});

CKEDITOR.addTemplates("test_suites_software_guidelines_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Test Suites",
            image: "template.gif",
            description: "Test Suites Submission Guidelines.",
            html: '',
            path: '/scripts/ckeditor/templates/test_suites_software_guidelines_template.txt'
        }
    ]
});

var templatesList = [
                        "default_software_guidelines_templates",
                        "assembly_software_guidelines_templates",
                        "ui_prototype_software_guidelines_templates",
                        "architecture_software_guidelines_templates",
                        "conceptualization_software_guidelines_templates",
                        "specification_software_guidelines_templates",
                        "bug_hunt_software_guidelines_templates",
                        "test_scenarios_software_guidelines_templates",
                        "test_suites_software_guidelines_templates"
                    ];
                    
// loading templates asychronously
for (var i = 0; i < templatesList.length; ++i) {
    var templates = CKEDITOR.getTemplates(templatesList[i]).templates;
    for (var j = 0; j < templates.length; ++j) {
        getTemplateContent(templates[j]);
    }
}