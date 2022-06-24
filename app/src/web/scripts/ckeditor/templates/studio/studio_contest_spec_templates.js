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

CKEDITOR.addTemplates("default_studio_contest_spec_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Studio Other Design",
            image: "template.gif",
            description: "Studio Other Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/studio_other_design_contest_spec_templateJW110411.txt'
        }
    ]
});

CKEDITOR.addTemplates("app_frontend_contest_spec_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Application Front-End Design",
            image: "template.gif",
            description: "Application Front-End Design Challenge Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/app_frontend_design_contest_spec_templateJW110411.txt'
        }
    ]
});

CKEDITOR.addTemplates("banner_or_icon_design_contest_spec_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Banner Design",
            image: "template.gif",
            description: "Banner Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/banner_design_contest_spec_templateJW110411.txt'
        },
        {
            title: "Icon Design",
            image: "template.gif",
            description: "Icon Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/icon_design_contest_spec_templateJW110411.txt'
        }
    ]
});

CKEDITOR.addTemplates("front-end_flash_design_contest_spec_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Front-End Flash Design",
            image: "template.gif",
            description: " Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/front-end_flash_design_contest_spec_templateJW110411.txt'
        }
    ]
});

CKEDITOR.addTemplates("ideagen_contest_spec_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Idea Generation",
            image: "template.gif",
            description: "Idea Generation Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/ideagen_contest_spec_templateJW110511.txt'
        }
    ]
});

CKEDITOR.addTemplates("logo_design_contest_spec_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Logo Design",
            image: "template.gif",
            description: "Logo Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/logo_design_contest_spec_templateJW110411.txt'
        }
    ]
});

CKEDITOR.addTemplates("print_or_presentation_design_contest_spec_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Print Design",
            image: "template.gif",
            description: "Print Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/print_design_contest_spec_templateJW110411.txt'
        },
        {
            title: "Presentation Design",
            image: "template.gif",
            description: "Presentation Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/presentation_design_contest_spec_templateJW110411.txt'
        }
    ]
});

CKEDITOR.addTemplates("studio_other_design_contest_spec_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Studio Other Design",
            image: "template.gif",
            description: "Studio Other Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/studio_other_design_contest_spec_templateJW110411.txt'
        }
    ]
});

CKEDITOR.addTemplates("web_design_contest_spec_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Web Design",
            image: "template.gif",
            description: "Web Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/web_design_contest_spec_templateJW110411.txt'
        }
    ]
});

CKEDITOR.addTemplates("widget_or_mobile_design_contest_spec_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Widget Design",
            image: "template.gif",
            description: "Widget Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/widget_design_contest_spec_templateJW110411.txt'
        },
        {
            title: "Mobile Design",
            image: "template.gif",
            description: "Mobile Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/mobile_design_contest_spec_templateJW110411.txt'
        }
    ]
});

CKEDITOR.addTemplates("wireframe_contest_spec_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Wireframe Design",
            image: "template.gif",
            description: "Wireframe Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/wireframe_contest_spec_templateJW110511.txt'
        }
    ]
});

var templatesList = [
                        "default_studio_contest_spec_templates",
                        "app_frontend_contest_spec_templates",
                        "banner_or_icon_design_contest_spec_templates",
                        "front-end_flash_design_contest_spec_templates",
                        "ideagen_contest_spec_templates",
                        "logo_design_contest_spec_templates",
                        "print_or_presentation_design_contest_spec_templates",
                        "studio_other_design_contest_spec_templates",
                        "web_design_contest_spec_templates",
                        "widget_or_mobile_design_contest_spec_templates",
                        "wireframe_contest_spec_templates"
                    ];
                    
// loading templates asychronously
for (var i = 0; i < templatesList.length; ++i) {
    var templates = CKEDITOR.getTemplates(templatesList[i]).templates;
    for (var j = 0; j < templates.length; ++j) {
        getTemplateContent(templates[j]);
    }
}