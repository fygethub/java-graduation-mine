package sy.fsdoor.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "department_")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Department {
	
	private String id;
	private String name;
	private Department department;
	private Set<Department> departments=new HashSet<Department>(0);
	private Set<Test_User> test_users=new HashSet<Test_User>(0);
	
	public Department() {
		// TODO Auto-generated constructor stub
	} 
	
	
	
	public Department(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	

	public Department(String id, String name, Department department) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
	}



	public Department(String id, String name, Department department,
			Set<Department> departments, Set<Test_User> test_users) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.departments = departments;
		this.test_users = test_users;
	}
	@Id
	@Column(name = "ID", nullable = false, length = 30)
	public String getId() {
		return id;
	}
	@Column(name = "NAME", nullable = false, length = 200)
	public String getName() {
		return name;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DEPARTMENT_ID")
	public Department getDepartment() {
		return department;
	}
	@OneToMany(fetch=FetchType.LAZY, mappedBy="department")
	public Set<Department> getDepartments() {
		return departments;
	}
	@OneToMany(fetch=FetchType.LAZY, mappedBy="department")
	public Set<Test_User> getTest_users() {
		return test_users;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}
	public void setTest_users(Set<Test_User> test_users) {
		this.test_users = test_users;
	}



	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}
	
}
