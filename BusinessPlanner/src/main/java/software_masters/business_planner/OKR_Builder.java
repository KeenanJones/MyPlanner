package software_masters.business_planner;

/**
 * This class builds a VMOSA developer template and saves it to XML.
 * 
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * 
 * @since 2019-02-24
 * 
 * 
 */

/*
 * These template builders could implement a super class since some of it is the
 * same. at the same time these are throw away classes once run.
 */
public class OKR_Builder
{

	private static TemplateSection makeSection(String category, String name, boolean canCopy)
	{
		TemplateSection section = new TemplateSection(category, name, canCopy);
		return section;
	}

	public static Template generateTemplate()
	{
		TemplateSection Vision = makeSection("Vision", "Vision", false);
		TemplateSection Mission = makeSection("Mission", "Mission", false);
		TemplateSection longTermGoals = makeSection("Long Term Goals", null, true);
		TemplateSection shortTermGoals = makeSection("Short Term Goals", null, true);
		TemplateSection Objectives = makeSection("Objectives", null, true);
		TemplateSection keyResults = makeSection("Key Results", null, true);

		Vision.addChild(Mission);
		Mission.addChild(longTermGoals);
		longTermGoals.addChild(shortTermGoals);
		shortTermGoals.addChild(Objectives);
		Objectives.addChild(keyResults);

		keyResults.setParent(Objectives);
		Objectives.setParent(shortTermGoals);
		shortTermGoals.setParent(longTermGoals);
		longTermGoals.setParent(Mission);
		Mission.setParent(Vision);

		return new Template("OKR", null, Vision, "2019", false);
	}

	public static void main(String[] args)
	{
		System.out.println("Start OKR");
		Template VMOSA = generateTemplate();
		VMOSA.save();
		System.out.println("end");
	}

}
