package software_masters.business_planner;

/**
 * This class represents the data in a given business plan section.
 * 
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * 
 * @since 2019-02-23
 * 
 * 
 */

import java.util.ArrayList;

public class TemplateSection
{

	private String category;
	private String name;

	private TemplateSection parent;
	private boolean canCopy;

	private ArrayList<TemplateSection> children = new ArrayList<TemplateSection>();
	private ArrayList<Content> contents = new ArrayList<Content>();

	/**
	 * constructor for serialization
	 */
	public TemplateSection()
	{
		this(null, null, true);
	}

	/**
	 * @param category
	 * @param name
	 * @param canCopy
	 */
	public TemplateSection(String category, String name, boolean canCopy)
	{
		this.category = category;
		this.name = name;
		this.canCopy = canCopy;
	}

	/**
	 * @return if the TemplateSection can be copied
	 */
	public boolean isCanCopy()
	{
		return canCopy;
	}

	/**
	 * @param canCopy the canCopy to set
	 */
	public void setCanCopy(boolean canCopy)
	{
		this.canCopy = canCopy;
	}

	/**
	 * @return if TemplateSection can be removed
	 */
	public boolean canRemove()
	{
		return (parent.getChildren().size() > 1);
	}

	/**
	 * @return the category
	 */
	public String getCategory()
	{
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category)
	{
		this.category = category;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the parent
	 */
	public TemplateSection getParent()
	{
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(TemplateSection parent)
	{
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public ArrayList<TemplateSection> getChildren()
	{
		return children;
	}

	/**
	 * This method adds a child TemplateSection object
	 * 
	 * @param child a TemplateSection object
	 */
	public void addChild(TemplateSection child)
	{
		this.children.add(child);
	}

	/**
	 * Allows user to remove a child if at least one child still exists.
	 * 
	 * @param child
	 * @return boolean indicating if child was removed
	 */
	public boolean removeChild(TemplateSection child)
	{
		if (child.canRemove())
		{
			this.children.remove(child);
			return true;
		}
		return false;
	}

	/**
	 * This method returns a child TemplateSection object
	 * 
	 * @param index the location of child in arraylist of children
	 * @return the child object associated with specified index
	 */
	public TemplateSection getChild(int index)
	{
		return this.children.get(index);
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(ArrayList<TemplateSection> children)
	{
		this.children = children;
	}

	/**
	 * @return the contents
	 */
	public ArrayList<Content> getContents()
	{
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(ArrayList<Content> contents)
	{
		this.contents = contents;
	}

	/**
	 * This method adds a content object to section
	 * 
	 * @param c1 a content object to be added to list of contents
	 */
	public void addContent(Content c1)
	{
		this.contents.add(c1);
	}

	/**
	 * This method determines if deep copy is allowed and if so initiates a
	 * recursive deep copy algorithm.
	 * 
	 * @return a clone of the TemplateSection using a recursive deep copy method.
	 */
	public TemplateSection deepCopy()
	{
		if (!this.isCanCopy())
		{
			return null;
		}
		return recDeepCopyHelper();
	}

	/**
	 * This method is a helper for deep copy. It was separated from deep copy to
	 * isolate the recursion algorithm.
	 * 
	 * @return a clone of the TemplateSection using a recursive deep copy method.
	 */
	private TemplateSection recDeepCopyHelper()
	{
		TemplateSection copy = new TemplateSection(this.category, this.name, this.canCopy);
		for (Content c1 : this.contents)
		{
			copy.addContent(c1.copy());
		}
		/* Base case - Current TemplateSection has no children */
		if (children.isEmpty())
		{
			return copy;
		}
		/* Recursive case - Current TemplateSection has children */
		else
		{
			TemplateSection temp;
			for (TemplateSection c : this.children)
			{
				temp = c.recDeepCopyHelper();
				copy.addChild(temp);
				temp.setParent(copy);
			}
			return copy;
		}
	}

	/**
	 * Tests if two templateSections are equal, useful for testing
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
		TemplateSection other = (TemplateSection) obj;
		if (canCopy != other.canCopy)
			return false;
		if (category == null)
		{
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (children == null)
		{
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (contents == null)
		{
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null)
		{
			if (other.parent != null)
				return false;
		}
		return true;
	}

}
