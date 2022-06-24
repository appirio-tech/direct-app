package com.topcoder.project.phases.template.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.template.ConfigurationException;
import com.topcoder.project.phases.template.DefaultPhaseTemplate;
import com.topcoder.project.phases.template.PhaseTemplatePersistence;
import com.topcoder.project.phases.template.StartDateGenerator;
import com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence;
import com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator;

public class DefaultPhaseTemplateFailureTest extends TestCase {

    private static final String NAMESPACE = "com.topcoder.project.phases.template.DefaultPhaseTemplate";

    private PhaseTemplatePersistence phaseTemplatePersistence;

    private StartDateGenerator startDateGenerator;

    private Workdays workdays;

    private String templateName;

    private Date date;

    private DefaultPhaseTemplate defaultPhaseTemplate;

    protected void setUp() throws Exception {
        FailureTestsHelper.loadAllConfig();
        phaseTemplatePersistence = new XmlPhaseTemplatePersistence(
                new String[] { "test_files/failuretests/template_file1.xml" });
        startDateGenerator = new RelativeWeekTimeStartDateGenerator(1, 0, 0, 0,
                0);
        workdays = new DefaultWorkdays();
        templateName = "templateName";
        date = new Date();
        defaultPhaseTemplate = new DefaultPhaseTemplate(
                phaseTemplatePersistence, startDateGenerator, workdays);
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.DefaultPhaseTemplate(String)'
     */
    public void testDefaultPhaseTemplateStringNamespaceNull() throws Exception {
        try {
            new DefaultPhaseTemplate(null);
            fail("IllegalArgumentException must be thrown since namespace is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.DefaultPhaseTemplate(String)'
     */
    public void testDefaultPhaseTemplateStringNamespaceEmpty() throws Exception {
        try {
            new DefaultPhaseTemplate("");
            fail("IllegalArgumentException must be thrown since namespace is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.DefaultPhaseTemplate(String)'
     */
    public void testDefaultPhaseTemplateStringNamespaceEmptyTrimmed()
            throws Exception {
        try {
            new DefaultPhaseTemplate("  ");
            fail("IllegalArgumentException must be thrown since namespace is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.DefaultPhaseTemplate(String)'
     */
    public void testDefaultPhaseTemplateStringPersistenceMissed() {
        try {
            new DefaultPhaseTemplate(NAMESPACE + 1);
            fail("ConfigurationException must be thrown since persistence property is missed in configuration");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.DefaultPhaseTemplate(String)'
     */
    public void testDefaultPhaseTemplateStringPersistenceClassMissed() {
        try {
            new DefaultPhaseTemplate(NAMESPACE + 2);
            fail("ConfigurationException must be thrown since persistence.class property is missed in configuration");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.DefaultPhaseTemplate(String)'
     */
    public void testDefaultPhaseTemplateStringStartDateGeneratorMissed() {
        try {
            new DefaultPhaseTemplate(NAMESPACE + 3);
            fail("ConfigurationException must be thrown since start_date_generator property is missed in configuration");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.DefaultPhaseTemplate(String)'
     */
    public void testDefaultPhaseTemplateStringStartDateGeneratorClassMissed() {
        try {
            new DefaultPhaseTemplate(NAMESPACE + 4);
            fail("ConfigurationException must be thrown since since start_date_generator.class property is missed in configuration");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.DefaultPhaseTemplate(String)'
     */
    public void testDefaultPhaseTemplateStringWorkdaysMissed() {
        try {
            new DefaultPhaseTemplate(NAMESPACE + 5);
            fail("ConfigurationException must be thrown since workdays property is missed in configuration");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.DefaultPhaseTemplate(String)'
     */
    public void testDefaultPhaseTemplateStringWorkdaysObjectSpecificationNamespaceMissed() {
        try {
            new DefaultPhaseTemplate(NAMESPACE + 6);
            fail("ConfigurationException must be thrown since workdays.object_specification_namespace property is missed in configuration");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.DefaultPhaseTemplate(String)'
     */
    public void testDefaultPhaseTemplateStringWorkdaysObjectKeyMissed() {
        try {
            new DefaultPhaseTemplate(NAMESPACE + 7);
            fail("ConfigurationException must be thrown since workdays.object_key property is missed in configuration");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.DefaultPhaseTemplate(PhaseTemplatePersistence,
     * StartDateGenerator, Workdays)'
     */
    public void testDefaultPhaseTemplatePhaseTemplatePersistenceNullStartDateGeneratorWorkdays() {
        try {
            new DefaultPhaseTemplate(null, startDateGenerator, workdays);
            fail("IllegalArgumentException must be thrown since phase template persistence is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.DefaultPhaseTemplate(PhaseTemplatePersistence,
     * StartDateGenerator, Workdays)'
     */
    public void testDefaultPhaseTemplatePhaseTemplatePersistenceStartDateGeneratorNullWorkdays() {
        try {
            new DefaultPhaseTemplate(phaseTemplatePersistence, null, workdays);
            fail("IllegalArgumentException must be thrown since stard date generator is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.DefaultPhaseTemplate(PhaseTemplatePersistence,
     * StartDateGenerator, Workdays)'
     */
    public void testDefaultPhaseTemplatePhaseTemplatePersistenceStartDateGeneratorWorkdaysNull() {
        try {
            new DefaultPhaseTemplate(phaseTemplatePersistence,
                    startDateGenerator, null);
            fail("IllegalArgumentException must be thrown since workdays is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.applyTemplate(String,
     * Date)'
     */
    public void testApplyTemplateStringDateTemplateNull() throws Exception {
        try {
            defaultPhaseTemplate.applyTemplate(null, date);
            fail("IllegalArgumentException must be thrown since template name is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.applyTemplate(String,
     * Date)'
     */
    public void testApplyTemplateStringDateTemplateEmpty() throws Exception {
        try {
            defaultPhaseTemplate.applyTemplate("", date);
            fail("IllegalArgumentException must be thrown since template name is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.applyTemplate(String,
     * Date)'
     */
    public void testApplyTemplateStringDateTemlateEmptyTrimmed()
            throws Exception {
        try {
            defaultPhaseTemplate.applyTemplate("    ", date);
            fail("IllegalArgumentException must be thrown since template name is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.applyTemplate(String,
     * Date)'
     */
    public void testApplyTemplateStringDateNull() throws Exception {
        try {
            defaultPhaseTemplate.applyTemplate(templateName, null);
            fail("IllegalArgumentException must be thrown since date is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.applyTemplate(String)'
     */
    public void testApplyTemplateStringNull() throws Exception {
        try {
            defaultPhaseTemplate.applyTemplate(null);
            fail("IllegalArgumentException must be thrown since template name is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.applyTemplate(String)'
     */
    public void testApplyTemplateStringEmpty() throws Exception {
        try {
            defaultPhaseTemplate.applyTemplate("");
            fail("IllegalArgumentException must be thrown since template name is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.applyTemplate(String)'
     */
    public void testApplyTemplateStringEmptyTrimmed() throws Exception {
        try {
            defaultPhaseTemplate.applyTemplate("    ");
            fail("IllegalArgumentException must be thrown since template name is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.setPersistence(PhaseTemplatePersistence)'
     */
    public void testSetPersistenceNull() {
        try {
            defaultPhaseTemplate.setPersistence(null);
            fail("IllegalArgumentException must be thrown since persistence is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.setStartDateGenerator(StartDateGenerator)'
     */
    public void testSetStartDateGeneratorNull() {
        try {
            defaultPhaseTemplate.setStartDateGenerator(null);
            fail("IllegalArgumentException must be thrown since start date generator is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.DefaultPhaseTemplate.setWorkdays(Workdays)'
     */
    public void testSetWorkdaysNull() {
        try {
            defaultPhaseTemplate.setWorkdays(null);
            fail("IllegalArgumentException must be thrown since workdays is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    protected void tearDown() throws Exception {
        FailureTestsHelper.clearTestConfig();
    }
}