package software_masters.business_planner;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * @author alexander.garuba
 * 
 *         This class represents a business department. It holds any published
 *         business plans for the department along with a default template for
 *         users to create a 'blank' plan
 * 
 * @since 3.12.2019
 */
public class Department implements Serializable
{

	private static final long serialVersionUID = -6137206464324856865L;

	private final Template deptTemplate;
	private ArrayList<Template> templateList;

	public Department()
	{
		this(null, null);
	}

	/**
	 * Constructor for Department object
	 * 
	 * @param deptTemplate default plan template for department
	 * @param templateList existing plans (pre-implementation)
	 */
	public Department(Template deptTemplate, ArrayList<Template> templateList)
	{
		this.deptTemplate = deptTemplate;
		this.setTemplateList(templateList);
	}

	/**
	 * Constructor for new department, no existing plans to add
	 * 
	 * @param deptTemplate default plan template for department
	 */
	public Department(Template deptTemplate)
	{
		this.deptTemplate = deptTemplate;
		setTemplateList(new ArrayList<Template>());
	}

	/**
	 * @return the deptTemplate
	 */
	public Template getDeptTemplate()
	{
		return deptTemplate;
	}

	/**
	 * @return the templateList
	 */
	public ArrayList<Template> getTemplateList()
	{
		return templateList;
	}

	/**
	 * @param templateList the templateList to set
	 */
	public void setTemplateList(ArrayList<Template> templateList)
	{
		this.templateList = templateList;
	}

}
