package sy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BugDaoI;
import sy.dao.BugTypeDaoI;
import sy.dao.ResourceDaoI;
import sy.dao.ResourceTypeDaoI;
import sy.dao.RoleDaoI;
import sy.dao.UserDaoI;
import sy.model.Tbugtype;
import sy.model.Tresource;
import sy.model.Tresourcetype;
import sy.model.Trole;
import sy.model.Tuser;
import sy.service.InitServiceI;
import sy.util.MD5Util;

@Service
public class InitServiceImpl implements InitServiceI {

	@Autowired
	private UserDaoI userDao;

	@Autowired
	private RoleDaoI roleDao;

	@Autowired
	private ResourceDaoI resourceDao;

	@Autowired
	private ResourceTypeDaoI resourceTypeDao;

	@Autowired
	private BugDaoI bugDao;

	@Autowired
	private BugTypeDaoI bugTypeDao;

	@Override
	synchronized public void init() {

		initBugType();// 初始化BUG类型

		initResourceType();// 初始化资源类型

		initResource();// 初始化资源

		initRole();// 初始化角色

		initUser();// 初始化用户

	}

	private void initBugType() {
		Tbugtype cw = new Tbugtype();
		cw.setId("0");
		cw.setName("错误");
		bugTypeDao.saveOrUpdate(cw);

		Tbugtype gn = new Tbugtype();
		gn.setId("1");
		gn.setName("功能");
		bugTypeDao.saveOrUpdate(gn);
	}

	private void initResource() {
		Tresourcetype menuType = resourceTypeDao.get(Tresourcetype.class, "0");// 菜单类型
		Tresourcetype funType = resourceTypeDao.get(Tresourcetype.class, "1");// 功能类型

		Tresource xtgl = new Tresource();
		xtgl.setId("xtgl");
		xtgl.setName("系统管理");
		xtgl.setTresourcetype(menuType);
		xtgl.setSeq(0);
		xtgl.setIcon("plugin");
		resourceDao.saveOrUpdate(xtgl);

		Tresource zygl = new Tresource();
		zygl.setId("zygl");
		zygl.setName("资源管理");
		zygl.setTresourcetype(menuType);
		zygl.setTresource(xtgl);
		zygl.setSeq(1);
		zygl.setUrl("/resourceController/manager");
		zygl.setIcon("database_gear");
		zygl.setRemark("管理系统中所有的菜单或功能");
		resourceDao.saveOrUpdate(zygl);

		Tresource zyglTreeGrid = new Tresource();
		zyglTreeGrid.setId("zyglTreeGrid");
		zyglTreeGrid.setName("资源表格");
		zyglTreeGrid.setTresourcetype(funType);
		zyglTreeGrid.setTresource(zygl);
		zyglTreeGrid.setSeq(1);
		zyglTreeGrid.setUrl("/resourceController/treeGrid");
		zyglTreeGrid.setIcon("wrench");
		zyglTreeGrid.setRemark("显示资源列表");
		resourceDao.saveOrUpdate(zyglTreeGrid);

		Tresource zyglMenu = new Tresource();
		zyglMenu.setId("zyglMenu");
		zyglMenu.setName("功能菜单");
		zyglMenu.setTresourcetype(funType);
		zyglMenu.setTresource(zygl);
		zyglMenu.setSeq(2);
		zyglMenu.setUrl("/resourceController/tree");
		zyglMenu.setIcon("wrench");
		resourceDao.saveOrUpdate(zyglMenu);

		Tresource zyglAddPage = new Tresource();
		zyglAddPage.setId("zyglAddPage");
		zyglAddPage.setName("添加资源页面");
		zyglAddPage.setTresourcetype(funType);
		zyglAddPage.setTresource(zygl);
		zyglAddPage.setSeq(3);
		zyglAddPage.setUrl("/resourceController/addPage");
		zyglAddPage.setIcon("wrench");
		resourceDao.saveOrUpdate(zyglAddPage);

		Tresource zyglAdd = new Tresource();
		zyglAdd.setId("zyglAdd");
		zyglAdd.setName("添加资源");
		zyglAdd.setTresourcetype(funType);
		zyglAdd.setTresource(zygl);
		zyglAdd.setSeq(4);
		zyglAdd.setUrl("/resourceController/add");
		zyglAdd.setIcon("wrench");
		resourceDao.saveOrUpdate(zyglAdd);

		Tresource zyglEditPage = new Tresource();
		zyglEditPage.setId("zyglEditPage");
		zyglEditPage.setName("编辑资源页面");
		zyglEditPage.setTresourcetype(funType);
		zyglEditPage.setTresource(zygl);
		zyglEditPage.setSeq(5);
		zyglEditPage.setUrl("/resourceController/editPage");
		zyglEditPage.setIcon("wrench");
		resourceDao.saveOrUpdate(zyglEditPage);

		Tresource zyglEdit = new Tresource();
		zyglEdit.setId("zyglEdit");
		zyglEdit.setName("编辑资源");
		zyglEdit.setTresourcetype(funType);
		zyglEdit.setTresource(zygl);
		zyglEdit.setSeq(6);
		zyglEdit.setUrl("/resourceController/edit");
		zyglEdit.setIcon("wrench");
		resourceDao.saveOrUpdate(zyglEdit);

		Tresource zyglDelete = new Tresource();
		zyglDelete.setId("zyglDelete");
		zyglDelete.setName("删除资源");
		zyglDelete.setTresourcetype(funType);
		zyglDelete.setTresource(zygl);
		zyglDelete.setSeq(7);
		zyglDelete.setUrl("/resourceController/delete");
		zyglDelete.setIcon("wrench");
		resourceDao.saveOrUpdate(zyglDelete);

		Tresource jsgl = new Tresource();
		jsgl.setId("jsgl");
		jsgl.setName("角色管理");
		jsgl.setTresourcetype(menuType);
		jsgl.setTresource(xtgl);
		jsgl.setSeq(2);
		jsgl.setUrl("/roleController/manager");
		jsgl.setIcon("tux");
		resourceDao.saveOrUpdate(jsgl);

		Tresource jsglTreeGrid = new Tresource();
		jsglTreeGrid.setId("jsglTreeGrid");
		jsglTreeGrid.setName("角色表格");
		jsglTreeGrid.setTresourcetype(funType);
		jsglTreeGrid.setTresource(jsgl);
		jsglTreeGrid.setSeq(1);
		jsglTreeGrid.setUrl("/roleController/treeGrid");
		jsglTreeGrid.setIcon("wrench");
		resourceDao.saveOrUpdate(jsglTreeGrid);

		Tresource jsglAddPage = new Tresource();
		jsglAddPage.setId("jsglAddPage");
		jsglAddPage.setName("添加角色页面");
		jsglAddPage.setTresourcetype(funType);
		jsglAddPage.setTresource(jsgl);
		jsglAddPage.setSeq(2);
		jsglAddPage.setUrl("/roleController/addPage");
		jsglAddPage.setIcon("wrench");
		resourceDao.saveOrUpdate(jsglAddPage);

		Tresource jsglAdd = new Tresource();
		jsglAdd.setId("jsglAdd");
		jsglAdd.setName("添加角色");
		jsglAdd.setTresourcetype(funType);
		jsglAdd.setTresource(jsgl);
		jsglAdd.setSeq(3);
		jsglAdd.setUrl("/roleController/add");
		jsglAdd.setIcon("wrench");
		resourceDao.saveOrUpdate(jsglAdd);

		Tresource jsglEditPage = new Tresource();
		jsglEditPage.setId("jsglEditPage");
		jsglEditPage.setName("编辑角色页面");
		jsglEditPage.setTresourcetype(funType);
		jsglEditPage.setTresource(jsgl);
		jsglEditPage.setSeq(4);
		jsglEditPage.setUrl("/roleController/editPage");
		jsglEditPage.setIcon("wrench");
		resourceDao.saveOrUpdate(jsglEditPage);

		Tresource jsglEdit = new Tresource();
		jsglEdit.setId("jsglEdit");
		jsglEdit.setName("编辑角色");
		jsglEdit.setTresourcetype(funType);
		jsglEdit.setTresource(jsgl);
		jsglEdit.setSeq(5);
		jsglEdit.setUrl("/roleController/edit");
		jsglEdit.setIcon("wrench");
		resourceDao.saveOrUpdate(jsglEdit);

		Tresource jsglDelete = new Tresource();
		jsglDelete.setId("jsglDelete");
		jsglDelete.setName("删除角色");
		jsglDelete.setTresourcetype(funType);
		jsglDelete.setTresource(jsgl);
		jsglDelete.setSeq(6);
		jsglDelete.setUrl("/roleController/delete");
		jsglDelete.setIcon("wrench");
		resourceDao.saveOrUpdate(jsglDelete);

		Tresource jsglGrantPage = new Tresource();
		jsglGrantPage.setId("jsglGrantPage");
		jsglGrantPage.setName("角色授权页面");
		jsglGrantPage.setTresourcetype(funType);
		jsglGrantPage.setTresource(jsgl);
		jsglGrantPage.setSeq(7);
		jsglGrantPage.setUrl("/roleController/grantPage");
		jsglGrantPage.setIcon("wrench");
		resourceDao.saveOrUpdate(jsglGrantPage);

		Tresource jsglGrant = new Tresource();
		jsglGrant.setId("jsglGrant");
		jsglGrant.setName("角色授权");
		jsglGrant.setTresourcetype(funType);
		jsglGrant.setTresource(jsgl);
		jsglGrant.setSeq(8);
		jsglGrant.setUrl("/roleController/grant");
		jsglGrant.setIcon("wrench");
		resourceDao.saveOrUpdate(jsglGrant);

		Tresource yhgl = new Tresource();
		yhgl.setId("yhgl");
		yhgl.setName("用户管理");
		yhgl.setTresourcetype(menuType);
		yhgl.setTresource(xtgl);
		yhgl.setSeq(3);
		yhgl.setUrl("/userController/manager");
		yhgl.setIcon("status_online");
		resourceDao.saveOrUpdate(yhgl);

		Tresource yhglDateGrid = new Tresource();
		yhglDateGrid.setId("yhglDateGrid");
		yhglDateGrid.setName("用户表格");
		yhglDateGrid.setTresourcetype(funType);
		yhglDateGrid.setTresource(yhgl);
		yhglDateGrid.setSeq(1);
		yhglDateGrid.setUrl("/userController/dataGrid");
		yhglDateGrid.setIcon("wrench");
		resourceDao.saveOrUpdate(yhglDateGrid);

		Tresource yhglAddPage = new Tresource();
		yhglAddPage.setId("yhglAddPage");
		yhglAddPage.setName("添加用户页面");
		yhglAddPage.setTresourcetype(funType);
		yhglAddPage.setTresource(yhgl);
		yhglAddPage.setSeq(2);
		yhglAddPage.setUrl("/userController/addPage");
		yhglAddPage.setIcon("wrench");
		resourceDao.saveOrUpdate(yhglAddPage);

		Tresource yhglAdd = new Tresource();
		yhglAdd.setId("yhglAdd");
		yhglAdd.setName("添加用户");
		yhglAdd.setTresourcetype(funType);
		yhglAdd.setTresource(yhgl);
		yhglAdd.setSeq(3);
		yhglAdd.setUrl("/userController/add");
		yhglAdd.setIcon("wrench");
		resourceDao.saveOrUpdate(yhglAdd);

		Tresource yhglEditPage = new Tresource();
		yhglEditPage.setId("yhglEditPage");
		yhglEditPage.setName("编辑用户页面");
		yhglEditPage.setTresourcetype(funType);
		yhglEditPage.setTresource(yhgl);
		yhglEditPage.setSeq(4);
		yhglEditPage.setUrl("/userController/editPage");
		yhglEditPage.setIcon("wrench");
		resourceDao.saveOrUpdate(yhglEditPage);

		Tresource yhglEdit = new Tresource();
		yhglEdit.setId("yhglEdit");
		yhglEdit.setName("编辑用户");
		yhglEdit.setTresourcetype(funType);
		yhglEdit.setTresource(yhgl);
		yhglEdit.setSeq(5);
		yhglEdit.setUrl("/userController/edit");
		yhglEdit.setIcon("wrench");
		resourceDao.saveOrUpdate(yhglEdit);

		Tresource yhglDelete = new Tresource();
		yhglDelete.setId("yhglDelete");
		yhglDelete.setName("删除用户");
		yhglDelete.setTresourcetype(funType);
		yhglDelete.setTresource(yhgl);
		yhglDelete.setSeq(6);
		yhglDelete.setUrl("/userController/delete");
		yhglDelete.setIcon("wrench");
		resourceDao.saveOrUpdate(yhglDelete);

		Tresource yhglBatchDelete = new Tresource();
		yhglBatchDelete.setId("yhglBatchDelete");
		yhglBatchDelete.setName("批量删除用户");
		yhglBatchDelete.setTresourcetype(funType);
		yhglBatchDelete.setTresource(yhgl);
		yhglBatchDelete.setSeq(7);
		yhglBatchDelete.setUrl("/userController/batchDelete");
		yhglBatchDelete.setIcon("wrench");
		resourceDao.saveOrUpdate(yhglBatchDelete);

		Tresource yhglGrantPage = new Tresource();
		yhglGrantPage.setId("yhglGrantPage");
		yhglGrantPage.setName("用户授权页面");
		yhglGrantPage.setTresourcetype(funType);
		yhglGrantPage.setTresource(yhgl);
		yhglGrantPage.setSeq(8);
		yhglGrantPage.setUrl("/userController/grantPage");
		yhglGrantPage.setIcon("wrench");
		resourceDao.saveOrUpdate(yhglGrantPage);

		Tresource yhglGrant = new Tresource();
		yhglGrant.setId("yhglGrant");
		yhglGrant.setName("用户授权");
		yhglGrant.setTresourcetype(funType);
		yhglGrant.setTresource(yhgl);
		yhglGrant.setSeq(9);
		yhglGrant.setUrl("/userController/grant");
		yhglGrant.setIcon("wrench");
		resourceDao.saveOrUpdate(yhglGrant);

		Tresource yhglEditPwdPage = new Tresource();
		yhglEditPwdPage.setId("yhglEditPwdPage");
		yhglEditPwdPage.setName("用户修改密码页面");
		yhglEditPwdPage.setTresourcetype(funType);
		yhglEditPwdPage.setTresource(yhgl);
		yhglEditPwdPage.setSeq(10);
		yhglEditPwdPage.setUrl("/userController/editPwdPage");
		yhglEditPwdPage.setIcon("wrench");
		resourceDao.saveOrUpdate(yhglEditPwdPage);

		Tresource yhglEditPwd = new Tresource();
		yhglEditPwd.setId("yhglEditPwd");
		yhglEditPwd.setName("用户修改密码");
		yhglEditPwd.setTresourcetype(funType);
		yhglEditPwd.setTresource(yhgl);
		yhglEditPwd.setSeq(11);
		yhglEditPwd.setUrl("/userController/editPwd");
		yhglEditPwd.setIcon("wrench");
		resourceDao.saveOrUpdate(yhglEditPwd);

		Tresource buggl = new Tresource();
		buggl.setId("buggl");
		buggl.setName("BUG管理");
		buggl.setTresourcetype(menuType);
		buggl.setTresource(xtgl);
		buggl.setSeq(4);
		buggl.setUrl("/bugController/manager");
		buggl.setIcon("bug");
		resourceDao.saveOrUpdate(buggl);

		Tresource bugglDateGrid = new Tresource();
		bugglDateGrid.setId("bugglDateGrid");
		bugglDateGrid.setName("BUG表格");
		bugglDateGrid.setTresourcetype(funType);
		bugglDateGrid.setTresource(buggl);
		bugglDateGrid.setSeq(1);
		bugglDateGrid.setUrl("/bugController/dataGrid");
		bugglDateGrid.setIcon("bug_link");
		resourceDao.saveOrUpdate(bugglDateGrid);

		Tresource bugglAddPage = new Tresource();
		bugglAddPage.setId("bugglAddPage");
		bugglAddPage.setName("添加BUG页面");
		bugglAddPage.setTresourcetype(funType);
		bugglAddPage.setTresource(buggl);
		bugglAddPage.setSeq(2);
		bugglAddPage.setUrl("/bugController/addPage");
		bugglAddPage.setIcon("bug_add");
		resourceDao.saveOrUpdate(bugglAddPage);

		Tresource bugglAdd = new Tresource();
		bugglAdd.setId("bugglAdd");
		bugglAdd.setName("添加BUG");
		bugglAdd.setTresourcetype(funType);
		bugglAdd.setTresource(buggl);
		bugglAdd.setSeq(3);
		bugglAdd.setUrl("/bugController/add");
		bugglAdd.setIcon("bug_add");
		resourceDao.saveOrUpdate(bugglAdd);

		Tresource bugglEditPage = new Tresource();
		bugglEditPage.setId("bugglEditPage");
		bugglEditPage.setName("编辑BUG页面");
		bugglEditPage.setTresourcetype(funType);
		bugglEditPage.setTresource(buggl);
		bugglEditPage.setSeq(4);
		bugglEditPage.setUrl("/bugController/editPage");
		bugglEditPage.setIcon("bug_edit");
		resourceDao.saveOrUpdate(bugglEditPage);

		Tresource bugglEdit = new Tresource();
		bugglEdit.setId("bugglEdit");
		bugglEdit.setName("编辑BUG");
		bugglEdit.setTresourcetype(funType);
		bugglEdit.setTresource(buggl);
		bugglEdit.setSeq(5);
		bugglEdit.setUrl("/bugController/edit");
		bugglEdit.setIcon("bug_edit");
		resourceDao.saveOrUpdate(bugglEdit);

		Tresource bugglDelete = new Tresource();
		bugglDelete.setId("bugglDelete");
		bugglDelete.setName("删除BUG");
		bugglDelete.setTresourcetype(funType);
		bugglDelete.setTresource(buggl);
		bugglDelete.setSeq(6);
		bugglDelete.setUrl("/bugController/delete");
		bugglDelete.setIcon("bug_delete");
		resourceDao.saveOrUpdate(bugglDelete);

		Tresource bugglView = new Tresource();
		bugglView.setId("bugglView");
		bugglView.setName("查看BUG");
		bugglView.setTresourcetype(funType);
		bugglView.setTresource(buggl);
		bugglView.setSeq(7);
		bugglView.setUrl("/bugController/view");
		bugglView.setIcon("bug_link");
		resourceDao.saveOrUpdate(bugglView);

		Tresource sjygl = new Tresource();
		sjygl.setId("sjygl");
		sjygl.setName("数据源管理");
		sjygl.setTresourcetype(menuType);
		sjygl.setTresource(xtgl);
		sjygl.setSeq(5);
		sjygl.setUrl("/druidController/druid");
		sjygl.setIcon("server_database");
		resourceDao.saveOrUpdate(sjygl);

		Tresource wjgl = new Tresource();
		wjgl.setId("wjgl");
		wjgl.setName("文件管理");
		wjgl.setTresourcetype(funType);
		wjgl.setTresource(xtgl);
		wjgl.setSeq(6);
		wjgl.setUrl("");
		wjgl.setIcon("server_database");
		resourceDao.saveOrUpdate(wjgl);

		Tresource wjglView = new Tresource();
		wjglView.setId("wjglView");
		wjglView.setName("浏览服务器文件");
		wjglView.setTresourcetype(funType);
		wjglView.setTresource(wjgl);
		wjglView.setSeq(1);
		wjglView.setUrl("/fileController/fileManage");
		wjglView.setIcon("server_database");
		resourceDao.saveOrUpdate(wjgl);

		Tresource wjglUpload = new Tresource();
		wjglUpload.setId("wjglUpload");
		wjglUpload.setName("上传文件");
		wjglUpload.setTresourcetype(menuType);
		wjglUpload.setTresource(wjgl);
		wjglUpload.setSeq(2);
		wjglUpload.setUrl("/fileController/upload");
		wjglUpload.setIcon("server_database");
		resourceDao.saveOrUpdate(wjglUpload);

		Tresource chart = new Tresource();
		chart.setId("chart");
		chart.setName("图表管理");
		chart.setTresourcetype(menuType);
		chart.setSeq(7);
		chart.setIcon("chart_bar");
		resourceDao.saveOrUpdate(chart);

		Tresource userCreateDatetimeChart = new Tresource();
		userCreateDatetimeChart.setId("userCreateDatetimeChart");
		userCreateDatetimeChart.setName("用户图表");
		userCreateDatetimeChart.setTresourcetype(menuType);
		userCreateDatetimeChart.setUrl("/chartController/userCreateDatetimeChart");
		userCreateDatetimeChart.setSeq(1);
		userCreateDatetimeChart.setIcon("chart_curve");
		userCreateDatetimeChart.setTresource(chart);
		resourceDao.saveOrUpdate(userCreateDatetimeChart);

		/**流程菜单*/
		
		Tresource processtTresource=new Tresource();
		processtTresource.setId("processTresource");
		processtTresource.setName("流程管理");
		processtTresource.setTresourcetype(menuType);
		processtTresource.setUrl("");
		processtTresource.setSeq(1);
		processtTresource.setIcon("server_chart");
		processtTresource.setTresource(xtgl);
		resourceDao.saveOrUpdate(processtTresource);

		
		/**历史信息查询*/
		
		Tresource processthistroyTresource=new Tresource();
		processthistroyTresource.setId("processthistroyTresource");
		processthistroyTresource.setName("历史信息查询");
		processthistroyTresource.setTresourcetype(menuType);
		processthistroyTresource.setUrl("");
		processthistroyTresource.setSeq(1);
		processthistroyTresource.setIcon("server_chart");
		processthistroyTresource.setTresource(processtTresource);
		resourceDao.saveOrUpdate(processthistroyTresource);
		
		/**流程部署*/
		Tresource processtdeployTresource=new Tresource();
		processtdeployTresource.setId("processtdeployTresource");
		processtdeployTresource.setName("流程部署");
		processtdeployTresource.setTresourcetype(menuType);
		processtdeployTresource.setUrl("/processController/deploy");
		processtdeployTresource.setSeq(1);
		processtdeployTresource.setIcon("server_chart");
		processtdeployTresource.setTresource(processtTresource);
		resourceDao.saveOrUpdate(processtdeployTresource);
		
		Tresource processDefineList=new Tresource();
		processDefineList.setId("processDefineList");
		processDefineList.setName("流程定义list");
		processDefineList.setTresourcetype(funType);
		processDefineList.setUrl("/processController/processDefineList");
		processDefineList.setSeq(1);
		processDefineList.setIcon("server_chart");
		processDefineList.setTresource(processtdeployTresource);
		resourceDao.saveOrUpdate(processDefineList);
		
		//上传提交流程
		Tresource upload=new Tresource();
		upload.setId("upload");
		upload.setName("上传提交流程");
		upload.setTresourcetype(funType);
		upload.setUrl("/processController/upload");
		upload.setSeq(1);
		upload.setIcon("server_chart");
		upload.setTresource(processtdeployTresource);
		resourceDao.saveOrUpdate(upload);
		
		//删除流程
		Tresource delete=new Tresource();
		delete.setId("delete");
		delete.setName("删除流程");
		delete.setTresourcetype(funType);
		delete.setUrl("/processController/processdel");
		delete.setSeq(1);
		delete.setIcon("server_chart");
		delete.setTresource(processtdeployTresource);
		resourceDao.saveOrUpdate(delete);
		
		//流程部署表
		
		Tresource processgrid=new Tresource();
		processgrid.setId("processgrid");
		processgrid.setName("流程部署表");
		processgrid.setTresourcetype(funType);
		processgrid.setUrl("/processController/processdeployList");
		processgrid.setSeq(1);
		processgrid.setIcon("server_chart");
		processgrid.setTresource(processtdeployTresource);
		resourceDao.saveOrUpdate(processgrid);
		
		//查看流程图
		
		Tresource processpic=new Tresource();
		processpic.setId("processpic");
		processpic.setName("查看流程图");
		processpic.setTresourcetype(funType);
		processpic.setUrl("/processController/searchProcessPic");
		processpic.setSeq(1);
		processpic.setIcon("server_chart");
		processpic.setTresource(processtdeployTresource);
		resourceDao.saveOrUpdate(processpic);
		
		/**请假流程*/
		Tresource processmanager=new Tresource();
		processmanager.setId("processpic");
		processmanager.setName("请假流程");
		processmanager.setTresourcetype(menuType);
		processmanager.setUrl("/leaveBillController/manager");
		processmanager.setSeq(1);
		processmanager.setIcon("server_chart");
		processmanager.setTresource(processtTresource);
		resourceDao.saveOrUpdate(processmanager);
		
		
		//查看流程
		Tresource processview=new Tresource();
		processview.setId("processview");
		processview.setName("查看流程");
		processview.setTresourcetype(funType);
		processview.setUrl("/personalProcessController/handleViewPage");
		processview.setSeq(1);
		processview.setIcon("server_chart");
		processview.setTresource(processmanager);
		resourceDao.saveOrUpdate(processview);
		
		//添加
		Tresource add=new Tresource();
		add.setId("add");
		add.setName("添加");
		add.setTresourcetype(funType);
		add.setUrl("/leaveBillController/add");
		add.setSeq(1);
		add.setIcon("server_chart");
		add.setTresource(processmanager);
		resourceDao.saveOrUpdate(add);
		
		//删除
		Tresource del=new Tresource();
		del.setId("del");
		del.setName("删除");
		del.setTresourcetype(funType);
		del.setUrl("/leaveBillController/delete");
		del.setSeq(1);
		del.setIcon("server_chart");
		del.setTresource(processmanager);
		resourceDao.saveOrUpdate(del);
		
		//添加请假页面
		Tresource addpage=new Tresource();
		addpage.setId("addpage");
		addpage.setName("添加请假页面");
		addpage.setTresourcetype(funType);
		addpage.setUrl("/leaveBillController/leaveBillAddpage");
		addpage.setSeq(1);
		addpage.setIcon("server_chart");
		addpage.setTresource(processmanager);
		resourceDao.saveOrUpdate(addpage);
		
		//查看流程图
		Tresource viewpic=new Tresource();
		viewpic.setId("viewpic");
		viewpic.setName("查看流程图");
		viewpic.setTresourcetype(funType);
		viewpic.setUrl("/processController/deploysearchProcessPic");
		viewpic.setSeq(1);
		viewpic.setIcon("server_chart");
		viewpic.setTresource(processmanager);
		resourceDao.saveOrUpdate(viewpic);
		
		//请假单
		Tresource grid=new Tresource();
		grid.setId("grid");
		grid.setName("请假单");
		grid.setTresourcetype(funType);
		grid.setUrl("/leaveBillController/datagrid");
		grid.setSeq(1);
		grid.setIcon("server_chart");
		grid.setTresource(processmanager);
		resourceDao.saveOrUpdate(grid);
		
		//个人流程开始
		Tresource start=new Tresource();
		start.setId("start");
		start.setName("个人流程开始");
		start.setTresourcetype(funType);
		start.setUrl("/personalProcessController/startprocess");
		start.setSeq(1);
		start.setIcon("server_chart");
		start.setTresource(processmanager);
		resourceDao.saveOrUpdate(start);
		
		//查看
		Tresource view=new Tresource();
		view.setId("view");
		view.setName("查看");
		view.setTresourcetype(funType);
		view.setUrl("/leaveBillController/view");
		view.setSeq(1);
		view.setIcon("server_chart");
		view.setTresource(processmanager);
		resourceDao.saveOrUpdate(view);
		
		/**@author fydor
		 */
		//编辑
		Tresource eidt=new Tresource();
		eidt.setId("eidt");
		eidt.setName("编辑");
		eidt.setTresourcetype(funType);
		eidt.setUrl("/leaveBillController/edit");
		eidt.setSeq(1);
		eidt.setIcon("server_chart");
		eidt.setTresource(processmanager);
		resourceDao.saveOrUpdate(eidt);
		
		//编辑页面
		Tresource eidtpage=new Tresource();
		eidtpage.setId("eidtpage");
		eidtpage.setName("编辑页面");
		eidtpage.setTresourcetype(funType);
		eidtpage.setUrl("/leaveBillController/editPage");
		eidtpage.setSeq(1);
		eidtpage.setIcon("server_chart");
		eidtpage.setTresource(processmanager);
		resourceDao.saveOrUpdate(eidtpage);
		
		
		/**自定义流程*/
		
		Tresource zdy=new Tresource();
		zdy.setId("zdy");
		zdy.setName("自定义流程");
		zdy.setTresourcetype(menuType);
		zdy.setUrl("http://www.jq22.com/demo/activiti-designer-master20150902/wf/designer");
		zdy.setSeq(1);
		zdy.setIcon("server_chart");
		zdy.setTresource(processtTresource);
		resourceDao.saveOrUpdate(zdy);
		
		/**任务查询*/
		Tresource taskpage=new Tresource();
		taskpage.setId("taskpage");
		taskpage.setName("任务查询");
		taskpage.setTresourcetype(menuType);
		taskpage.setUrl("/personalProcessController/taskPage");
		taskpage.setSeq(1);
		taskpage.setIcon("server_chart");
		taskpage.setTresource(processtTresource);
		resourceDao.saveOrUpdate(taskpage);
		
		//办理任务
		Tresource editpage=new Tresource();
		editpage.setId("editpage");
		editpage.setName("办理任务");
		editpage.setTresourcetype(funType);
		editpage.setUrl("/personalProcessController/taskEditPage");
		editpage.setSeq(1);
		editpage.setIcon("server_chart");
		editpage.setTresource(taskpage);
		resourceDao.saveOrUpdate(editpage);
		
		//准备办理任务
		Tresource taskHandlpage=new Tresource();
		taskHandlpage.setId("taskHandlpage");
		taskHandlpage.setName("准备办理任务");
		taskHandlpage.setTresourcetype(funType);
		taskHandlpage.setUrl("/personalProcessController/taskHandleReady");
		taskHandlpage.setSeq(1);
		taskHandlpage.setIcon("server_chart");
		taskHandlpage.setTresource(taskpage);
		resourceDao.saveOrUpdate(taskHandlpage);
		
		//提交办理任务
		Tresource submittaskHandlpage=new Tresource();
		submittaskHandlpage.setId("submittaskHandlpage");
		submittaskHandlpage.setName("提交办理任务");
		submittaskHandlpage.setTresourcetype(funType);
		submittaskHandlpage.setUrl("/personalProcessController/submitTtask");
		submittaskHandlpage.setSeq(1);
		submittaskHandlpage.setIcon("server_chart");
		submittaskHandlpage.setTresource(taskpage);
		resourceDao.saveOrUpdate(submittaskHandlpage);
		
		//任务查询
		Tresource task=new Tresource();
		task.setId("task");
		task.setName("任务查询");
		task.setTresourcetype(funType);
		task.setUrl("/personalProcessController/task");
		task.setSeq(1);
		task.setIcon("server_chart");
		task.setTresource(taskpage);
		resourceDao.saveOrUpdate(task);
		
		/**流程图片	*/
		Tresource taskPic=new Tresource();
		taskPic.setId("taskPic");
		taskPic.setName("流程图片");
		taskPic.setTresourcetype(funType);
		taskPic.setUrl("/processController/searchProcessPicPage");
		taskPic.setSeq(1);
		taskPic.setIcon("server_chart");
		taskPic.setTresource(taskpage);
		resourceDao.saveOrUpdate(taskPic);
		
		
		Tresource jeasyuiApi = new Tresource();
		jeasyuiApi.setId("jeasyuiApi");
		jeasyuiApi.setName("EasyUI API");
		jeasyuiApi.setTresourcetype(menuType);
		jeasyuiApi.setUrl("http://jeasyui.com/documentation");
		jeasyuiApi.setSeq(1000);
		jeasyuiApi.setIcon("book");
		resourceDao.saveOrUpdate(jeasyuiApi);

	}

	private void initResourceType() {
		Tresourcetype t = new Tresourcetype();
		t.setId("0");
		t.setName("菜单");
		resourceTypeDao.saveOrUpdate(t);

		Tresourcetype t2 = new Tresourcetype();
		t2.setId("1");
		t2.setName("功能");
		resourceTypeDao.saveOrUpdate(t2);
	}

	private void initRole() {
		Trole superAdmin = new Trole();
		superAdmin.setId("0");
		superAdmin.setName("超管");
		superAdmin.getTresources().addAll(resourceDao.find("from Tresource t"));// 让超管可以访问所有资源
		superAdmin.setSeq(0);
		superAdmin.setRemark("超级管理员角色，拥有系统中所有的资源访问权限");
		roleDao.saveOrUpdate(superAdmin);

		Trole zyAdmin = new Trole();
		zyAdmin.setId("zyAdmin");
		zyAdmin.setName("资源管理员");
		zyAdmin.setTrole(superAdmin);// 父级是超管
		zyAdmin.setSeq(1);
		zyAdmin.getTresources().addAll(resourceDao.find("from Tresource t where t.tresource.id in ('zygl') or t.id in ('zygl')"));
		roleDao.saveOrUpdate(zyAdmin);

		Trole jsAdmin = new Trole();
		jsAdmin.setId("jsAdmin");
		jsAdmin.setName("角色管理员");
		jsAdmin.setTrole(superAdmin);// 父级是超管
		jsAdmin.setSeq(2);
		jsAdmin.getTresources().addAll(resourceDao.find("from Tresource t where t.tresource.id in ('jsgl') or t.id in ('jsgl')"));
		roleDao.saveOrUpdate(jsAdmin);

		Trole yhAdmin = new Trole();
		yhAdmin.setId("yhAdmin");
		yhAdmin.setName("用户管理员");
		yhAdmin.setTrole(superAdmin);// 父级是超管
		yhAdmin.setSeq(3);
		yhAdmin.getTresources().addAll(resourceDao.find("from Tresource t where t.tresource.id in ('yhgl') or t.id in ('yhgl')"));
		roleDao.saveOrUpdate(yhAdmin);

		Trole sjyAdmin = new Trole();
		sjyAdmin.setId("sjyAdmin");
		sjyAdmin.setName("数据源管理员");
		sjyAdmin.setTrole(superAdmin);// 父级是超管
		sjyAdmin.setSeq(4);
		sjyAdmin.getTresources().addAll(resourceDao.find("from Tresource t where t.tresource.id in ('sjygl') or t.id in ('sjygl')"));
		roleDao.saveOrUpdate(sjyAdmin);

		Trole bugAdmin = new Trole();
		bugAdmin.setId("bugAdmin");
		bugAdmin.setName("BUG管理员");
		bugAdmin.setTrole(superAdmin);// 父级是超管
		bugAdmin.setSeq(5);
		bugAdmin.getTresources().addAll(resourceDao.find("from Tresource t where t.tresource.id in ('buggl') or t.id in ('buggl')"));
		roleDao.saveOrUpdate(bugAdmin);

		Trole guest = new Trole();
		guest.setId("guest");
		guest.setName("Guest");
		guest.getTresources().addAll(resourceDao.find("from Tresource t where t.id in ('xtgl','zygl','zyglTreeGrid','jsgl','jsglTreeGrid','yhgl','yhglDateGrid','jeasyuiApi')"));
		guest.setSeq(1);
		guest.setRemark("只拥有只看的权限");
		roleDao.saveOrUpdate(guest);
	}

	private void initUser() {
		List<Tuser> l = userDao.find("from Tuser t where t.name in ('fydor','admin1','admin2','admin3','admin4','admin5','guest')");
		if (l != null && l.size() > 0) {
			for (Tuser user : l) {
				userDao.delete(user);
			}
		}

		Tuser admin = new Tuser();
		admin.setId("0");
		admin.setName("fydor");
		admin.setPwd(MD5Util.md5("123456"));
		admin.setCreatedatetime(new Date());
		admin.getTroles().addAll(roleDao.find("from Trole t"));// 给用户赋予所有角色
		userDao.saveOrUpdate(admin);

		Tuser admin1 = new Tuser();
		admin1.setId("1");
		admin1.setName("admin1");
		admin1.setPwd(MD5Util.md5("123456"));
		admin1.setCreatedatetime(new Date());
		admin1.getTroles().addAll(roleDao.find("from Trole t where t.id = 'zyAdmin'"));// 给用户赋予资源管理员角色
		//admin1.setManager(admin);
		userDao.saveOrUpdate(admin1);

		Tuser admin2 = new Tuser();
		admin2.setId("2");
		admin2.setName("admin2");
		admin2.setPwd(MD5Util.md5("123456"));
		admin2.setCreatedatetime(new Date());
		admin2.getTroles().addAll(roleDao.find("from Trole t where t.id = 'jsAdmin'"));// 给用户赋予角色管理员角色
		userDao.saveOrUpdate(admin2);

		Tuser admin3 = new Tuser();
		admin3.setId("3");
		admin3.setName("admin3");
		admin3.setPwd(MD5Util.md5("123456"));
		admin3.setCreatedatetime(new Date());
		admin3.getTroles().addAll(roleDao.find("from Trole t where t.id = 'yhAdmin'"));// 给用户赋予用户管理员角色
		userDao.saveOrUpdate(admin3);

		Tuser admin4 = new Tuser();
		admin4.setId("4");
		admin4.setName("admin4");
		admin4.setPwd(MD5Util.md5("123456"));
		admin4.setCreatedatetime(new Date());
		admin4.getTroles().addAll(roleDao.find("from Trole t where t.id = 'sjyAdmin'"));// 给用户赋予数据源管理员角色
		userDao.saveOrUpdate(admin4);

		Tuser admin5 = new Tuser();
		admin5.setId("5");
		admin5.setName("admin5");
		admin5.setPwd(MD5Util.md5("123456"));
		admin5.setCreatedatetime(new Date());
		admin5.getTroles().addAll(roleDao.find("from Trole t where t.id = 'bugAdmin'"));// 给用户赋予BUG管理员角色
		userDao.saveOrUpdate(admin5);

		Tuser guest = new Tuser();
		guest.setId("guest");
		guest.setName("guest");
		guest.setPwd(MD5Util.md5("123456"));
		guest.setCreatedatetime(new Date());
		guest.getTroles().addAll(roleDao.find("from Trole t where t.id = 'guest'"));// 给用户赋予Guest角色
		userDao.saveOrUpdate(guest);
	}
}
