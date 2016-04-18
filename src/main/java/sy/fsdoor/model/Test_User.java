package sy.fsdoor.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "test_user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Test_User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private Department department; 
	private String pwd;
	private Test_User test_User;
	private Set<Test_Role> test_Roles = new HashSet<Test_Role>(0);
	private Set<Test_User> test_Users=new HashSet<Test_User>();
	
	
	public Test_User() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Test_User(String id, String name, Department department, String pwd) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.pwd = pwd;
	}



	public Test_User(String id, String name, Department department, String pwd,
			Set<Test_Role> test_Roles, Test_User test_Users) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.pwd = pwd;
		this.test_Roles = test_Roles;
		this.test_User = test_Users;
	}



	public Test_User(String id, String name, Department department, String pwd,
			Set<Test_Role> test_Roles) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.pwd = pwd;
		this.test_Roles = test_Roles;
	}



	@Id
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "NAME", unique = true, nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="test_User")
	public Set<Test_User> getTest_Users() {
		return test_Users;
	}



	public void setTest_Users(Set<Test_User> test_Users) {
		this.test_Users = test_Users;
	}



	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DEPARTMENT_ID")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Column(name = "PWD", nullable = false, length = 100)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="test_role_user",joinColumns={@JoinColumn(name="TEST_USER_ID",nullable=false,updatable=false)},inverseJoinColumns={@JoinColumn(name="TEST_ROLE_ID",nullable=false,updatable=false)})
	public Set<Test_Role> getTest_Roles() {
		return test_Roles;
	}

	public void setTest_Roles(Set<Test_Role> test_Roles) {
		this.test_Roles = test_Roles;
	}


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PUSER_ID")
	public Test_User getTest_User() {
		return test_User;
	}

	public void setTest_User(Test_User test_User) {
		this.test_User = test_User;
	}



	@Override
	public String toString() {
		return "Test_User [id=" + id + ", name=" + name + ", department="
				+ department + ", pwd=" + pwd + ", test_User=" + test_User
				+ ", test_Roles=" + test_Roles + ", test_Users=" + test_Users
				+ "]";
	}

	
}
