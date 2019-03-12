package software_masters.business_planner;

/**
 * This class builds a Centre Assessment developer template and saves it to XML.
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * @since 2019-02-25
 */
public class Centre_Assess_Builder
{

	private static TemplateSection makeSection(String category, String name, boolean canCopy)
	{
		TemplateSection section = new TemplateSection(category, name, canCopy);
		return section;
	}

	public static Template generateTemplate()
	{
		TemplateSection collegeMission = makeSection("College Mission", "College Mission", false);
		TemplateSection programMission = makeSection("Program Mission", "Program Mission", false);
		TemplateSection goal = makeSection("Goals", null, true);
		TemplateSection studentLearningObjective = makeSection("Student Learning Objectives", null, true);
		TemplateSection tool = makeSection("Tools", null, true);
		TemplateSection target = makeSection("Targets", null, true);

		collegeMission.addChild(programMission);
		programMission.addChild(goal);
		goal.addChild(studentLearningObjective);
		studentLearningObjective.addChild(tool);
		tool.addChild(target);

		target.setParent(tool);
		tool.setParent(studentLearningObjective);
		studentLearningObjective.setParent(goal);
		goal.setParent(programMission);
		programMission.setParent(collegeMission);

		return new Template("Centre_Assessment", null, collegeMission);
	}

	public static void main(String[] args)
	{
		System.out.println("Start Centre Assess Builder");
		Template Centre_Assessment = generateTemplate();
		Centre_Assessment.save();
		System.out.println("end");
	}

}
