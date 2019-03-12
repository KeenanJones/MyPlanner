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
public class VMOSA_Builder
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
		TemplateSection Objective = makeSection("Objectives", null, true);
		TemplateSection Strategy = makeSection("Strategies", null, true);
		TemplateSection ActionPlan = makeSection("Action Plans", null, true);

		Vision.addChild(Mission);
		Mission.addChild(Objective);
		Objective.addChild(Strategy);
		Strategy.addChild(ActionPlan);

		ActionPlan.setParent(Strategy);
		Strategy.setParent(Objective);
		Objective.setParent(Mission);
		Mission.setParent(Vision);

		return new Template("VMOSA", null, Vision);
	}

	public static void main(String[] args)
	{
		System.out.println("Start VMOSA");
		Template VMOSA = generateTemplate();
		VMOSA.save();
		System.out.println("end");
	}

}
