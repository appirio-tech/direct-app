package com.topcoder.project.phases.template.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.ConfigurationException;
import com.topcoder.project.phases.template.PersistenceException;
import com.topcoder.project.phases.template.PhaseGenerationException;
import com.topcoder.project.phases.template.PhaseTemplatePersistence;
import com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence;

public class XmlPhaseTemplatePersistenceFailureTest extends TestCase {

    private static final String NAMESPACE = "com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence";

    private String templateName = "TCS Component Project 1";

    private Project project = new Project(new Date(System.currentTimeMillis()),
            new DefaultWorkdays());

    private PhaseTemplatePersistence phaseTemplatePersistence;

    protected void setUp() throws Exception {
        FailureTestsHelper.loadAllConfig();
        phaseTemplatePersistence = new XmlPhaseTemplatePersistence(
                new String[] { "test_files/failuretests/template_file1.xml" });
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String)'
     */
    public final void testXmlPhaseTemplatePersistenceStringNamespaceNull()
            throws Exception {
        try {
            new XmlPhaseTemplatePersistence((String) null);
            fail("IllegalArgumentException must be thrown since namespace is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String)'
     */
    public final void testXmlPhaseTemplatePersistenceStringNamespaceEmpty()
            throws Exception {
        try {
            new XmlPhaseTemplatePersistence("");
            fail("IllegalArgumentException must be thrown since namespace is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String)'
     */
    public final void testXmlPhaseTemplatePersistenceStringNamespaceEmptyTrimmed()
            throws Exception {
        try {
            new XmlPhaseTemplatePersistence("   ");
            fail("IllegalArgumentException must be thrown since namespace is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String)'
     */
    public final void testXmlPhaseTemplatePersistenceStringTemplateFilesNotExist() {
        try {
            new XmlPhaseTemplatePersistence(NAMESPACE + 1);
            fail("ConfigurationException must be thrown since template_files property is not defined in configuration");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String)'
     */
    public final void testXmlPhaseTemplatePersistenceStringFileNotFound() {
        try {
            new XmlPhaseTemplatePersistence(NAMESPACE + 2);
            fail("ConfigurationException must be thrown since template file is not found");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String)'
     */
    public final void testXmlPhaseTemplatePersistenceStringNameEmpty() {
        try {
            new XmlPhaseTemplatePersistence(NAMESPACE + 3);
            fail("ConfigurationException must be thrown since name attribute is empty");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String)'
     */
    public final void testXmlPhaseTemplatePersistenceStringNameEmptyTrimmed() {
        try {
            new XmlPhaseTemplatePersistence(NAMESPACE + 4);
            fail("ConfigurationException must be thrown since name attribute is empty trimmed");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String)'
     */
    public final void testXmlPhaseTemplatePersistenceStringWrongXML() {
        try {
            new XmlPhaseTemplatePersistence(NAMESPACE + 5);
            fail("ConfigurationException must be thrown since xml is wrong");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String[])'
     */
    public final void testXmlPhaseTemplatePersistenceStringArrayNull()
            throws Exception {
        try {
            new XmlPhaseTemplatePersistence((String[]) null);
            fail("IllegalArgumentException must be thrown since fileNames is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String)'
     */
    public final void testXmlPhaseTemplatePersistenceStringArrayFileNotFound() {
        try {
            new XmlPhaseTemplatePersistence(
                    new String[] { "test_files/failuretests/template_file_wrong.xml" });
            fail("PersistenceException must be thrown since template file is not found");
        } catch (PersistenceException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String)'
     */
    public final void testXmlPhaseTemplatePersistenceStringArrayNameEmpty() {
        try {
            new XmlPhaseTemplatePersistence(
                    new String[] { "test_files/failuretests/template_file4.xml" });
            fail("PersistenceException must be thrown since name attribute is empty");
        } catch (PersistenceException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String)'
     */
    public final void testXmlPhaseTemplatePersistenceStringArrayNameEmptyTrimmed() {
        try {
            new XmlPhaseTemplatePersistence(
                    new String[] { "test_files/failuretests/template_file5.xml" });
            fail("PersistenceException must be thrown since name attribute is empty trimmed");
        } catch (PersistenceException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String)'
     */
    public final void testXmlPhaseTemplatePersistenceStringArrayWrongXML() {
        try {
            new XmlPhaseTemplatePersistence(
                    new String[] { "test_files/failuretests/template_file6.xml" });
            fail("PersistenceException must be thrown since xml is wrong");
        } catch (PersistenceException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String[])'
     */
    public final void testXmlPhaseTemplatePersistenceStringArrayEmpty()
            throws Exception {
        try {
            new XmlPhaseTemplatePersistence(new String[0]);
            fail("IllegalArgumentException must be thrown since fileNames is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String[])'
     */
    public final void testXmlPhaseTemplatePersistenceStringArrayInnerNull()
            throws Exception {
        try {
            new XmlPhaseTemplatePersistence(new String[] {
                    "test_files/failuretests/template_file1.xml", null,
                    "test_files/failuretests/template_file2.xml" });
            fail("IllegalArgumentException must be thrown since inner values of fileNames is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String[])'
     */
    public final void testXmlPhaseTemplatePersistenceStringArrayInnerEmpty()
            throws Exception {
        try {
            new XmlPhaseTemplatePersistence(new String[] {
                    "test_files/failuretests/template_file1.xml", "",
                    "test_files/failuretests/template_file2.xml" });
            fail("IllegalArgumentException must be thrown since inner values of fileNames is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.XmlPhaseTemplatePersistence(String[])'
     */
    public final void testXmlPhaseTemplatePersistenceStringArrayInnerEmptyTrimmed()
            throws Exception {
        try {
            new XmlPhaseTemplatePersistence(new String[] {
                    "test_files/failuretests/template_file1.xml", "   ",
                    "test_files/failuretests/template_file2..xml" });
            fail("IllegalArgumentException must be thrown since inner values of fileNames is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.generatePhases(String,
     * Project)'
     */
    public final void testGeneratePhasesTemplateNameNull() throws Exception {
        try {
            phaseTemplatePersistence.generatePhases(null, project);
            fail("IllegalArgumentException must be thrown since template name is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.generatePhases(String,
     * Project)'
     */
    public final void testGeneratePhasesTemplateNameEmpty() throws Exception {
        try {
            phaseTemplatePersistence.generatePhases("", project);
            fail("IllegalArgumentException must be thrown since template name is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.generatePhases(String,
     * Project)'
     */
    public final void testGeneratePhasesTemplateNameEmptyTrimmed()
            throws Exception {
        try {
            phaseTemplatePersistence.generatePhases("   ", project);
            fail("IllegalArgumentException must be thrown since template name is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.generatePhases(String,
     * Project)'
     */
    public final void testGeneratePhasesProjectNull() throws Exception {
        try {
            phaseTemplatePersistence.generatePhases(templateName, null);
            fail("IllegalArgumentException must be thrown since template name is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.generatePhases(String,
     * Project)'
     */
    public final void testGeneratePhasesTemplateNotFound() throws Exception {
        try {
            phaseTemplatePersistence.generatePhases("TCS Component Project 2",
                    project);
            fail("IllegalArgumentException must be thrown since template name is not found");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.generatePhases(String,
     * Project)'
     */
    public final void testGeneratePhasesTemplatePhaseTypeNotFound()
            throws Exception {
        phaseTemplatePersistence = new XmlPhaseTemplatePersistence(
                new String[] { "test_files/failuretests/template_file7.xml" });
        try {
            phaseTemplatePersistence.generatePhases(templateName, project);
            fail("PhaseGenerationException must be thrown since phase type is not defined");
        } catch (PhaseGenerationException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence.generatePhases(String,
     * Project)'
     */
    public final void testGeneratePhasesTemplatePhaseNotFound()
            throws Exception {
        phaseTemplatePersistence = new XmlPhaseTemplatePersistence(
                new String[] { "test_files/failuretests/template_file8.xml" });
        try {
            phaseTemplatePersistence.generatePhases(templateName, project);
            fail("PhaseGenerationException must be thrown since phase is not defined");
        } catch (PhaseGenerationException e) {
            // good
        }
    }

    protected void tearDown() throws Exception {
        FailureTestsHelper.clearTestConfig();
    }
}