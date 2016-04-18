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
@Table(name = "test_tree")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Test_Tree implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Test_Tree test_Tree;
	private String name;
	private String remark;
	private Integer seq;
	private String url;
	private Set<Test_Tree> test_Trees=new HashSet<Test_Tree>();
	private Set<Test_Role> test_Roles=new HashSet<Test_Role>();
	
	
	
	public Test_Tree() {
		// TODO Auto-generated constructor stub
	}
	
	


	public Test_Tree(String id, String name, String remark, Integer seq,
			String url) {
		super();
		this.id = id;
		this.name = name;
		this.remark = remark;
		this.seq = seq;
		this.url = url;
	}




	public Test_Tree(String id, Test_Tree test_Tree, String name,
			String remark, Integer seq, String url) {
		super();
		this.id = id;
		this.test_Tree = test_Tree;
		this.name = name;
		this.remark = remark;
		this.seq = seq;
		this.url = url;
	}




	public Test_Tree(String id, Test_Tree test_Tree, String name,
			String remark, Integer seq, String url, Set<Test_Tree> test_Trees,
			Set<Test_Role> test_Roles) {
		super();
		this.id = id;
		this.test_Tree = test_Tree;
		this.name = name;
		this.remark = remark;
		this.seq = seq;
		this.url = url;
		this.test_Trees = test_Trees;
		this.test_Roles = test_Roles;
	}

	@Id
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return id;
	}
	
	@Column(name = "NAME", length = 200)
	public String getName() {
		return name;
	}
	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}
	@Column(name = "SEQ")
	public Integer getSeq() {
		return seq;
	}
	@Column(name = "URL", length = 200)
	public String getUrl() {
		return url;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PTREEID")
	public Test_Tree getTest_Tree() {
		return test_Tree;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="test_Tree")
	public Set<Test_Tree> getTest_Trees() {
		return test_Trees;
	}
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="test_role_tree",joinColumns={@JoinColumn(name="TEST_TREE_ID",nullable=false,updatable=false)},inverseJoinColumns={@JoinColumn(name="TEST_ROLE_ID",nullable=false,updatable=false)})
	public Set<Test_Role> getTest_Roles() {
		return test_Roles;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTest_Tree(Test_Tree test_Tree) {
		this.test_Tree = test_Tree;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTest_Trees(Set<Test_Tree> test_Trees) {
		this.test_Trees = test_Trees;
	}

	public void setTest_Roles(Set<Test_Role> test_Roles) {
		this.test_Roles = test_Roles;
	}




	@Override
	public String toString() {
		return "Test_Tree [id=" + id + ", name=" + name + ", remark=" + remark
				+ ", seq=" + seq + ", url=" + url + "]";
	}
	
	
}
