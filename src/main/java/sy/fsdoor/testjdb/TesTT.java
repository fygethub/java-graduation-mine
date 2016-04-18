package sy.fsdoor.testjdb;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;

import sy.fsdoor.model.Department;
import sy.fsdoor.model.Test_Role;
import sy.fsdoor.model.Test_Tree;
import sy.fsdoor.model.Test_User;

public class TesTT {
	
	private static Configuration configuration = null;

	private static ServiceRegistry serviceRegistry = null;
	private static SessionFactory sessionFactory=null;
	
	static{
		
		configuration=new Configuration().configure("hibernate.cfg.xml");
		serviceRegistry=new ServiceRegistryBuilder()
		.applySettings(configuration.getProperties()).buildServiceRegistry();
		
		sessionFactory=configuration.buildSessionFactory(serviceRegistry);
		
	}
	
	@Test
	public void addDepartment(){
		Department department=new Department("zb", "总部门",null);
		Department xxb=new Department("xxb", "信息部",department);
		Department jxb=new Department("jxb", "机械部",department);
		Department scb=new Department("scb", "生产部",department);
		Department xsb=new Department("xsb", "销售部",department);
		Department azb=new Department("azb", "安装部",department);
		
		Department xxb_1=new Department("java","java",xsb);
		Department xxb_2=new Department("android","java",xsb);
		Department xxb_3=new Department("C","C",xsb);
		
		Set<Department> xSet=new HashSet<Department>();
		xSet.add(xxb_1);
		xSet.add(xxb_2);
		xSet.add(xxb_3);
		xxb.setDepartments(xSet);
		
		Session session=sessionFactory.openSession();
		Transaction transaction=session.getTransaction();
		transaction.begin();
		
		session.save(department);
		session.save(xxb_1);
		session.save(xxb_2);
		session.save(xxb_3);
		session.save(xxb);
		session.save(jxb);
		session.save(scb);
		session.save(azb);
		session.save(xsb);
		
		
		transaction.commit();
		session.close();
	}
	
	@Test
	public void addRole() {
		// TODO Auto-generated method stub
		Test_Role admin=new Test_Role("admin","admin", "admin", 10);
		Test_Role role=new Test_Role("role",admin,"role", "role", 9);
		Test_Role role1=new Test_Role("role1",admin,"role1", "role1", 8);
		Test_Role role2=new Test_Role("role2",admin,"role2", "role2", 7);
	
		Set<Test_Role> test_Roles=new HashSet<Test_Role>();
		test_Roles.add(role1);
		test_Roles.add(role2);
		
		Department department=new Department("zb", "总部门",null);
		Department xxb=new Department("xxb", "信息部",department);
		
		Test_User test_User=new Test_User("1","lisi",xxb, "123456", test_Roles);
		
		Session session=sessionFactory.openSession();
		Transaction transaction=session.getTransaction();
		transaction.begin();
		
		/*session.save(admin);
		session.save(role1);
		session.save(role2);
		session.save(role);
		
		session.save(department);
		session.save(xxb);*/
		
		session.saveOrUpdate(test_User);
		
		transaction.commit();
		session.close();
	}
	
	
	@Test
	public void addtree(){
		
		Test_Tree test_Tree=new Test_Tree("xt", "system","all can get", 10, "/test.jsp");
		Test_Tree zsj=new Test_Tree("zsh", test_Tree,"zhushuji","all can get", 9, "/zhusuj.jsp");
		
		Test_Role admin=new Test_Role("admin","admin", "admin", 10);
		Test_Role role=new Test_Role("role",admin,"role", "role", 9);
		
		Set<Test_Role> test_Roles=new HashSet<Test_Role>();
		test_Roles.add(admin);
		test_Roles.add(role);
		
		zsj.setTest_Roles(test_Roles);
		
		Session session=sessionFactory.openSession();
		Transaction transaction=session.getTransaction();
		transaction.begin();
		session.save(test_Tree);
		session.save(zsj);
		transaction.commit();
		session.close();
	}
	/**给角色添加tree*/
	@Test
	public void addRoleTree(){
		Test_Role admin=new Test_Role("admin","admin", "admin", 10);
		Test_Role role1=new Test_Role("role1",admin,"role1", "role1", 8);
		Test_Role role2=new Test_Role("role2",admin,"role2", "role2", 7);
		
		Test_Tree test_Tree=new Test_Tree("xt", "system","all can get", 10, "/test.jsp");
		Test_Tree zsj=new Test_Tree("zsh", test_Tree,"zhushuji","all can get", 9, "/zhusuj.jsp");
		Set<Test_Tree> test_Trees=new HashSet<Test_Tree>();
		Set<Test_Tree> test_Trees1=new HashSet<Test_Tree>();
		test_Trees.add(zsj);
		test_Trees.add(test_Tree);
		
		test_Trees1.add(zsj);
		
		admin.setTest_Trees(test_Trees);
		role1.setTest_Trees(test_Trees);
		role2.setTest_Trees(test_Trees1);
		
		Session session=sessionFactory.openSession();
		Transaction transaction=session.getTransaction();
		transaction.begin();
		
		session.saveOrUpdate(admin);
		//session.saveOrUpdate(role2);
		
		transaction.commit();
		session.close();
	}
	
	
	@Test
	public void addUserRole(){
		Test_Role admin=new Test_Role("admin","admin", "admin", 10);
		Test_Role role=new Test_Role("role",admin,"role", "role", 9);
		Test_Role role1=new Test_Role("role1",admin,"role1", "role1", 8);
		Test_Role role2=new Test_Role("role2",admin,"role2", "role2", 7);
		
		Set<Test_Role >  test_Roles=new HashSet<Test_Role>();
		test_Roles.add(admin);
		Test_User test_User=new Test_User("123", "张三",null, "123123",test_Roles);
		
		Session session=sessionFactory.openSession();
		Transaction transaction=session.getTransaction();
		transaction.begin();
		session.save(test_User);
		/*session.save(role);
		session.save(role1);
		session.save(role2);*/
		transaction.commit();
		session.close();
	}
	
	
	
	@Test
	public void test(){
		Session session=sessionFactory.openSession();
		Transaction transaction=session.getTransaction();
		transaction.begin();
		/*Query q=session.createQuery("select distinct r from Test_User as u left join  u.test_Roles as r left join  r.test_Trees as p where u.name=?");
		q.setParameter(0, "lisi");
		System.out.println(q.toString());
		List<Test_Tree> test_Trees=q.list();
		for(Test_Tree test_Tree:test_Trees){
			System.out.println(test_Tree.toString()+test_Tree.getTest_Tree());
		}*/
		
		/*Query q=session.createQuery("select distinct r from Test_Role as r left join r.test_Trees as p join r.test_Users as u where u.name=? ");
		
		q.setParameter(0, "lisi");
		List<Test_Role> test_Roles=q.list();
		for(Test_Role test_Role:test_Roles){
			System.out.println(test_Role.getId());
		}*/
		
		Query q=session.createQuery(" from Test_User as r where r.name=? ");
		
		Department department=new Department("zb", "总部门",null);
		Department azb=new Department("azb", "安装部",department);
		q.setParameter(0, "lisi");
		List<Test_User> test_Users=q.list();
		for(Test_User test_user:test_Users){
			/*Set<Test_Role>Roles= test_Role.getTest_Roles();
			for(Test_Role r:Roles){
				System.out.println(r.toString());
			}
			System.out.println(test_user.getDepartment().toString());*/
			session.save(azb);
			test_user.setDepartment(azb);
			session.saveOrUpdate(test_user);
			transaction.commit();
		}
		session.close();
		
	}
	
	
	@Test
	public void hqlTest(){
		Session session=sessionFactory.openSession();
		Transaction transaction=session.getTransaction();
		transaction.begin();
		
		String hql="from Test_User as u right join Department ";
		
		session.close();
	}
}
