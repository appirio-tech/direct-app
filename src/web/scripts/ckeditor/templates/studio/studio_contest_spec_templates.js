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

CKEDITOR.addTemplates("studio_contest_spec_templates", {
    imagesPath: "/scripts/ckeditor/templates/images/",
    templates: [
        {
            title: "Application Front-End Design",
            image: "template.gif",
            description: "Application Front-End Design Contest Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/app_frontend_design_contest_spec_templateJW110411.txt'
        },
        {
            title: "Banner Design",
            image: "template.gif",
            description: "Banner Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/banner_design_contest_spec_templateJW110411.txt'
        },
        {
            title: "Front-End Flash Design",
            image: "template.gif",
            description: " Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/front-end_flash_design_contest_spec_templateJW110411.txt'
        },
        {
            title: "Icon Design",
            image: "template.gif",
            description: "Icon Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/icon_design_contest_spec_templateJW110411.txt'
        },
        {
            title: "Idea Generation",
            image: "template.gif",
            description: "Idea Generation Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/ideagen_contest_spec_templateJW110511.txt'
        },
        {
            title: "Logo Design",
            image: "template.gif",
            description: "Logo Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/logo_design_contest_spec_templateJW110411.txt'
        },
        {
            title: "Mobile Design",
            image: "template.gif",
            description: "Mobile Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/mobile_design_contest_spec_templateJW110411.txt'
        },
        {
            title: "Presentation Design",
            image: "template.gif",
            description: "Presentation Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/presentation_design_contest_spec_templateJW110411.txt'
        },
        {
            title: "Print Design",
            image: "template.gif",
            description: "Print Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/print_design_contest_spec_templateJW110411.txt'
        },
        {
            title: "Studio Other Design",
            image: "template.gif",
            description: "Studio Other Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/studio_other_design_contest_spec_templateJW110411.txt'
        },
        {
            title: "Web Design",
            image: "template.gif",
            description: "Web Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/web_design_contest_spec_templateJW110411.txt'
        },
        {
            title: "Widget Design",
            image: "template.gif",
            description: "Widget Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/widget_design_contest_spec_templateJW110411.txt'
        },
        {
            title: "Wireframe Design",
            image: "template.gif",
            description: "Wireframe Design Specification.",
            html: '',
            path: '/scripts/ckeditor/templates/studio/wireframe_contest_spec_templateJW110511.txt'
        },
    ]
});

// loading templates asychronously
var templates = CKEDITOR.getTemplates("studio_contest_spec_templates").templates;
for (var i = 0; i < templates.length; ++i) {
    getTemplateContent(templates[i]);
}