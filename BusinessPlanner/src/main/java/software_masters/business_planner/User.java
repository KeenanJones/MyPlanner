package software_masters.business_planner;

/**
 * @author alexander.garuba
 * 
 *         This class represents a user in the business plan database.
 */
public class User
{

	private final String name;
	private final String password;
	private final Department department;

	private boolean isAdmin;

	/**
	 * Constructor for User. Called in addUser --> Server class
	 * 
	 * @param name       username
	 * @param password   password (authentication)
	 * @param department the user's department
	 * @param isAdmin    whether the user is an administrator or not
	 */
	public User(String name, String password, Department department, boolean isAdmin)
	{
		this.name = name;
		this.password = password;
		this.department = department;
		this.isAdmin = isAdmin;
	}

	//Getters
	
	public Department getDepartment()
	{
		return department;
	}
	public String getName()
	{
		return name;
	}

	public String getPassword()
	{
		return password;
	}

	public boolean isAdmin()
	{
		return isAdmin;
	}
	
	public String toString()
	{
		return "Username: " + getName() + "\nPassword: " + getPassword() + "\nDepartment: " + getDepartment() + "\nAdmin Permissions: " + isAdmin() + "\n";
	}

}
