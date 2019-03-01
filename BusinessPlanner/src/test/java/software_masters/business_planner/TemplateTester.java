package software_masters.business_planner;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TemplateTester extends TestCase
{
	/**
	 * Verifies the developer template object is properly serialized and can be read
	 * in.
	 */
	public void testSaveAndLoadDeveloper()
	{
		Template VMOSA = VMOSA_builder.generateTemplate();
		VMOSA.save();
		Template VMOSA_clone = Template.loadDeveloperTemplate("VMOSA");

		// verify loaded object and serialized object contain the same content
		Assert.assertTrue(VMOSA.equals(VMOSA_clone));

		// verify changing one does not change the other
		makeChange(VMOSA, VMOSA_clone);
	}

	/**
	 * Verifies the user template can be serialized and read in. This is done
	 * separately because this can be significantly more complicated than the
	 * developer template.
	 */
	public void testSaveAndLoadUser()
	{
		// user template
		Template userVMOSA = VMOSA_builder.generateTemplate();
		userVMOSA.setUserTemplateName("MyBusinessPlan");

		/* Add extra child branch and test serialization process */
		TemplateSection vision = userVMOSA.getRoot();
		TemplateSection mission = vision.getChild(0);
		TemplateSection objective = mission.getChild(0).deepCopy();
		mission.addChild(objective);
		objective.setParent(mission);

		userVMOSA.save();
		Template userCopy = Template.loadUserTemplate("MyBusinessPlan");

		// verify loaded object and serialized object contain the same content
		Assert.assertTrue(userVMOSA.equals(userCopy));

		// verify changing one does not change the other
		makeChange(userVMOSA, userCopy);
	}

	/**
	 * @param base
	 * @param copy Helper method that verifies changing one template object does not
	 *             impact the other. In other words it makes sure objects do not
	 *             point to the same object.
	 */
	private void makeChange(Template base, Template copy)
	{
		base.getRoot().setCategory("Object");
		Assert.assertNotSame(base, copy);
	}

}
