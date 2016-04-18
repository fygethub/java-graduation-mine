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
@Table(name = "test_role")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Test_Role implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Test_Role test_Role;
	private String name;
	private String remark;
	private Integer seq;
	private Set<Test_Role> test_Roles = new HashSet<Test_Role>(0);
	private Set<Test_Tree> test_Trees = new HashSet<Test_Tree>(0);
	private Set<Test_User> test_Users = new HashSet<Test_User>(0);
	
	
	public Test_Role() {
		// TODO Auto-generated constructor stub
	}
	

	public Test_Role(String id, Test_Role test_Role, String name, String remark,
			Integer seq) {
		super();
		this.id = id;
		this.test_Role = test_Role;
		this.name = name;
		this.remark = remark;
		this.seq = seq;
	}


	public Test_Role(String id, Test_Role test_Role, String name, String remark,
			Integer seq, Set<Test_Role> test_Roles, Set<Test_Tree> test_Trees,
			Set<Test_User> test_Users) {
		super();
		this.id = id;
		this.test_Role = test_Role;
		this.name = name;
		this.remark = remark;
		this.seq = seq;
		this.test_Roles = test_Roles;
		this.test_Trees = test_Trees;
		this.test_Users = test_Users;
	}

	

	public Test_Role(String id, String name, String remark, Integer seq) {
		super();
		this.id = id;
		this.name = name;
		this.remark = remark;
		this.seq = seq;
	}


	@Id
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROLE_ID")
	public Test_Role getTest_Role() {
		return test_Role;
	}


	public void setTest_Role(Test_Role test_Role) {
		this.test_Role = test_Role;
	}


	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "SEQ")
	public Integer getSeq() {
		return this.seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="test_Role")
	public Set<Test_Role> getTest_Roles() {
		return test_Roles;
	}

	public void setTest_Roles(Set<Test_Role> test_Roles) {
		this.test_Roles = test_Roles;
	}
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="test_role_tree",joinColumns={@JoinColumn(name="TEST_ROLE_ID",nullable=false,updatable=false)},inverseJoinColumns={@JoinColumn(name="TEST_TREE_ID",nullable=false,updatable=false)})
	public Set<Test_Tree> getTest_Trees() {
		return test_Trees;
	}

	public void setTest_Trees(Set<Test_Tree> test_Trees) {
		this.test_Trees = test_Trees;
	}
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="test_role_user",joinColumns={@JoinColumn(name="TEST_ROLE_ID",nullable=false,updatable=false)},inverseJoinColumns={@JoinColumn(name="TEST_USER_ID",nullable=false,updatable=false)})
	public Set<Test_User> getTest_Users() {
		return test_Users;
	}

	public void setTest_Users(Set<Test_User> test_Users) {
		this.test_Users = test_Users;
	}


	@Override
	public String toString() {
		return "Test_Role [id=" + id + ", test_Role=" + test_Role + ", name="
				+ name + ", remark=" + remark + ", seq=" + seq
				+"]";
	}

	
}
