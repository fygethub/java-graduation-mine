package sy.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sy.service.InitServiceI;
import sy.service.UserServiceI;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-hibernate.xml", "classpath:spring-druid.xml" })
public class Test1 {

	@Autowired
	private InitServiceI initService;

	@Autowired
	private UserServiceI userService;

	// @Test
	// public void t1() {
	// Tuser u = userService.getById("0");
	// userService.delete(u);
	// }

	// @Test
	// public void t2() {
	// Tuser u = userService.get("from Tuser t join fetch t.troles r where t.id=:id and r.name='超管'", new HashMap<String, Object>() {
	// {
	// put("id", "0");
	// }
	// });
	// if (u != null) {
	// Set<Trole> ts = u.getTroles();
	// for (Trole t : ts) {
	// System.out.println(t);
	// }
	// }
	// }
	//
	// @Test
	// public void t3() {
	// Tuser u = userService.get("from Tuser t join fetch t.troles r where t.id=:id", new HashMap<String, Object>() {
	// {
	// put("id", "0");
	// }
	// });
	// if (u != null) {
	// Set<Trole> ts = u.getTroles();
	// for (Trole t : ts) {
	// System.out.println(t);
	// }
	// }
	// }
	//
	// @Test
	// public void t4() {
	// Tuser u = new Tuser();
	// u.setId("1");
	// u.setName("孙宇");
	// u.setPwd("123456");
	// u.setCreatedatetime(new Date());
	// // userService.save(u);
	// }
	//
	// @Test
	// public void t5() {
	// List<Object[]> l = userService.findBySql("select * from tuser");
	// System.out.println(JSON.toJSONStringWithDateFormat(l, "yyyy-MM-dd hh:mm:ss"));
	// }
	//
	// @Test
	// public void t6() {
	// List<Object[]> l = userService.findBySql("select * from tuser t where t.id = :id", new HashMap<String, Object>() {
	// {
	// put("id", "0");
	// }
	// });
	// System.out.println(JSON.toJSONStringWithDateFormat(l, "yyyy-MM-dd hh:mm:ss"));
	// }
	//
	// @Test
	// public void t7() {
	// int i = userService.executeSql("update tuser t set t.name='nnn' where t.id='0'");
	// System.out.println(i);
	// }
	//
	// @Test
	// public void t8() {
	// int i = userService.executeSql("update tuser t set t.name=:name where t.id=:id", new HashMap<String, Object>() {
	// {
	// put("id", "0");
	// put("name", "孙宇");
	// }
	// });
	// System.out.println(i);
	// }
	//
	// @Test
	// public void t9() {
	// BigInteger i = userService.countBySql("select count(1) from tuser t where t.id=:id", new HashMap<String, Object>() {
	// {
	// put("id", "1");
	// }
	// });
	// System.out.println(i);
	// }

	@Test
	public void test() {
		// ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"classpath:spring.xml", "classpath:spring-hibernate.xml", "classpath:spring-druid.xml" });
		// System.out.println(ac);
		initService.init();
	}

}
