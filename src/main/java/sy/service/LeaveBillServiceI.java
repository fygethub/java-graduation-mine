package sy.service;

import java.util.List;

import sy.model.LeaveBill;
import sy.pageModel.DataGrid;
import sy.pageModel.PageHelper;
import sy.pageModel.User;
import sy.pageModel.activiti.PageLeaveBill;


public interface LeaveBillServiceI {
	
	List<LeaveBill> findLeaveBillList();

	void saveLeaveBill(LeaveBill leaveBill);

	LeaveBill findLeaveBillById(Long id);

	void deleteLeaveBillById(String id);

	DataGrid dataGrid(PageLeaveBill pLeaveBill, PageHelper ph);//显示请假流程的表格

	void add(LeaveBill leaveBill, User user);

	PageLeaveBill findpageLeaveBillById(String id);

	void edit(PageLeaveBill pageLeaveBill);

}
