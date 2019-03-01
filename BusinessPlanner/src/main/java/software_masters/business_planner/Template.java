package software_masters.business_planner;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * This class can be both a representation of a business plan or a business plan
 * outline.
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * @since 2019-02-23
 */

public class Template
{
	private String developerTemplateName;
	private String userTemplateName;
	private TemplateSection root;

	/**
	 * This constructor creates a default object for use by XML encoder
	 */
	public Template()
	{
		this(null, null, null);
	}

	/**
	 * Constructor for creating Template object.
	 *
	 * @param developerTemplateName
	 * @param userTemplateName
	 * @param root
	 */
	public Template(String developerTemplateName, String userTemplateName, TemplateSection root)
	{
		this.developerTemplateName = developerTemplateName;
		this.userTemplateName = userTemplateName;
		this.root = root;
	}

	/**
	 * @return the name of the developer template
	 */
	public String getDeveloperTemplateName()
	{
		return developerTemplateName;
	}

	/**
	 * @param developerTemplateName the name of the developer template being used
	 */
	public void setDeveloperTemplateName(String developerTemplateName)
	{
		this.developerTemplateName = developerTemplateName;
	}

	/**
	 * @return the name of the business plan
	 */
	public String getUserTemplateName()
	{
		return userTemplateName;
	}

	/**
	 * @param userTemplateName the name of the business plan
	 */
	public void setUserTemplateName(String userTemplateName)
	{
		this.userTemplateName = userTemplateName;
	}

	/**
	 * @return the start section for the template
	 */
	public TemplateSection getRoot()
	{
		return root;
	}

	/**
	 * @param root the start TemplateSection for the template
	 */
	public void setRoot(TemplateSection root)
	{
		this.root = root;
	}

	/**
	 * This method serializes the object.
	 */
	public void save()
	{
		String filename;
		if (this.userTemplateName == null)
		{
			filename = this.developerTemplateName + ".dev";
		} else
		{
			filename = this.userTemplateName + ".user";
		}
		XMLEncoder encoder = null;
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		} catch (FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File " + filename);
		}
		encoder.writeObject(this);
		encoder.close();

	}

	/**
	 * This method returns an instantiation of associated developer template
	 * 
	 * @return the developerTemplate associated with this template
	 */
	public Template getDeveloperTemplate()
	{
		return Template.loadDeveloperTemplate(this.developerTemplateName);
	}

	/**
	 * This method uses load to read in a developer template. Hides the file
	 * extension.
	 * 
	 * @param name of the template
	 * @return the developerTemplate
	 */
	public static Template loadDeveloperTemplate(String name)
	{
		return Template.load(name + ".dev");
	}

	/**
	 * This method uses load to read in a user template. Hides the file extension.
	 * 
	 * @param name of the template
	 * @return the userTemplate
	 */
	public static Template loadUserTemplate(String name)
	{
		return Template.load(name + ".user");
	}

	/**
	 * This method is used to load a design based on a provided filepath
	 * 
	 * @param filepath the filepath to the xml file representation of the object
	 * @return a template object from memory
	 */
	private static Template load(String filepath)
	{
		XMLDecoder decoder = null;
		try
		{
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filepath)));
		} catch (FileNotFoundException e)
		{
			System.out.println("ERROR: File " + filepath + " not found");
		}
		return (Template) decoder.readObject();
	}

	/**
	 * Tests if two template objects are equal, useful for testing
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Template other = (Template) obj;
		if (developerTemplateName == null)
		{
			if (other.developerTemplateName != null)
				return false;
		} else if (!developerTemplateName.equals(other.developerTemplateName))
			return false;
		if (root == null)
		{
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		if (userTemplateName == null)
		{
			if (other.userTemplateName != null)
				return false;
		} else if (!userTemplateName.equals(other.userTemplateName))
			return false;
		return true;
	}

}
