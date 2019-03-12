package software_masters.business_planner;

import java.util.ArrayList;

/**
 * @author alexander.garuba
 * 
 *         This class represents a business department. It holds any published
 *         business plans for the department along with a default template for
 *         users to create a 'blank' plan
 * 
 * @since 3.12.2019
 */
public class Department
{

	public final Template deptTemplate;
	public ArrayList<Template> templateList;

	/**
	 * Constructor for Department object
	 * 
	 * @param deptTemplate default plan template for department
	 * @param templateList existing plans (pre-implementation)
	 */
	public Department(Template deptTemplate, ArrayList<Template> templateList)
	{
		this.deptTemplate = deptTemplate;
		this.templateList = templateList;
	}

	/**
	 * Constructor for new department, no existing plans to add
	 * 
	 * @param deptTemplate default plan template for department
	 */
	public Department(Template deptTemplate)
	{
		this.deptTemplate = deptTemplate;
		templateList = new ArrayList<Template>();
	}

}
