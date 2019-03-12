package software_masters.business_planner;

/**
 * This class tests the TemplateSection class
 * @author Wesley Murray
 * @author Lee Kendall
 * @since 2019-02-23
 */

import junit.framework.Assert;
import junit.framework.TestCase;

public class TemplateSectionTester extends TestCase
{
	/**
	 * Verifies method can add children to TemplateSection
	 */
	public void testAddChild()
	{

		// Creates TemplateSections, makes ts2 child of ts1
		TemplateSection ts1 = new TemplateSection("Vision", "Vision", false);
		TemplateSection ts2 = new TemplateSection("Mission", "Mission", false);
		ts1.addChild(ts2);
		ts2.setParent(ts1);

		Assert.assertEquals(ts2, ts1.getChild(0));
	}

	/**
	 * Verifies the removeChild method will only remove child objects if and only if
	 * at least one child still remains afterwards.
	 */
	public void testRemoveChild()
	{

		// Creates TemplateSections, makes ts2 and ts3 children of ts1
		TemplateSection ts1 = new TemplateSection("Mission", "Mission", false);
		TemplateSection ts2 = new TemplateSection("Objectives", "Objective 1", false);
		TemplateSection ts3 = new TemplateSection("Objectives", "Objective 2", false);
		ts1.addChild(ts2);
		ts1.addChild(ts3);
		ts2.setParent(ts1);
		ts3.setParent(ts1);

		// remove ts2
		ts1.removeChild(ts2);
		Assert.assertEquals(ts3, ts1.getChild(0));

		// try to remove ts3
		Assert.assertEquals(false, ts1.removeChild(ts3));
	}

	/**
	 * Verifies content objects can be added to a templateSection
	 *
	public void testAddContent()
	{
		TemplateSection ts1 = new TemplateSection("Vision", "Vision", false);
		Text t1 = new Text();
		ts1.addContent(t1);

		Assert.assertEquals(ts1.getContents().get(0), t1);

	}*/

	/*
	 * we do not not find it necessary to remove content since the developer creates
	 * that object once and can fix a mistake by serializing their new template over
	 * the old one.
	 */

	/**
	 * Verifies that the deepCopy of a templateSection is effective. It should only
	 * clone object if operation is allowed. It should clone all connected children.
	 */
	public void testDeepCopy()
	{
		///////////////// Makes partial VMOSA tree
		TemplateSection ts1 = new TemplateSection("Vision", "Vision", false);
		TemplateSection ts2 = new TemplateSection("Mission", "Mission", false);
		TemplateSection ts3 = new TemplateSection("Objectives", null, true);

		ts1.addChild(ts2);
		ts2.setParent(ts1);
		ts2.addChild(ts3);
		ts3.setParent(ts2);

		// Tries to clone Vision, shouldn't work
		Assert.assertNull(ts1.deepCopy());

		// Tries to clone Objectives, should work
		TemplateSection ts4 = ts3.deepCopy();
		ts4.setParent(ts2);
		Assert.assertTrue(ts3.equals(ts4));

		// Makes sure change in one copy doesn't effect change in another copy
		makeChange(ts3, ts3.deepCopy());

		//////////////////// tests a more complicated tree structure
		TemplateSection ts5 = new TemplateSection("Vision", "Vision", false);
		TemplateSection ts6 = new TemplateSection("Mission", "Mission", true);
		TemplateSection ts7 = new TemplateSection("Objectives", null, true);

		ts5.addChild(ts6);
		ts6.setParent(ts5);
		ts6.addChild(ts7);
		ts7.setParent(ts6);

		// Tries to clone Mission, should work
		TemplateSection ts8 = ts6.deepCopy();
		ts6.setParent(ts8);
		Assert.assertTrue(ts6.equals(ts8));
	}

	/**
	 * @param base
	 * @param copy Helper method that verifies changing one template object does not
	 *             impact the other. In other words it makes sure objects do not
	 *             point to the same object.
	 */
	private void makeChange(TemplateSection base, TemplateSection copy)
	{
		base.setCategory("Object");
		Assert.assertNotSame(base, copy);
	}
}
