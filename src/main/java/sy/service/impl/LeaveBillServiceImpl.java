package sy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import sy.dao.LeaveBillDaoI;
import sy.dao.UserDaoI;
import sy.model.LeaveBill;
import sy.model.Tuser;
import sy.pageModel.DataGrid;
import sy.pageModel.PageHelper;
import sy.pageModel.User;
import sy.pageModel.activiti.PageLeaveBill;
import sy.service.LeaveBillServiceI;
import sy.util.ClobUtil;

@Service
public class LeaveBillServiceImpl implements LeaveBillServiceI{
	
	@Resource
	LeaveBillDaoI leaveBillDaoI;
	@Resource
	UserDaoI userDaoI;
	
	@Override
	public List<LeaveBill> findLeaveBillList() {
		// TODO Auto-generated method stub
		
		return leaveBillDaoI.find("from LeaveBill");
	}

	@Override
	public void saveLeaveBill(LeaveBill leaveBill) {
		// TODO Auto-generated method stub
		leaveBillDaoI.save(leaveBill);
	}

	@Override
	public LeaveBill findLeaveBillById(Long id) {
		// TODO Auto-generated method stub
		Map<String ,Object> params=new HashMap<String,Object>();
		LeaveBill leaveBill=leaveBillDaoI.get("from LeaveBill l where l.id=:id", params);
		return leaveBill;
	}

	@Override
	public void deleteLeaveBillById(String id) {
		// TODO Auto-generated method stub
		LeaveBill leaveBill=leaveBillDaoI.get(LeaveBill.class, id);
		leaveBillDaoI.delete(leaveBill);
	}

	@Override
	public DataGrid dataGrid(PageLeaveBill pLeaveBill, PageHelper ph) {
		// TODO Auto-generated method stub
		DataGrid dg=new DataGrid();
		List<PageLeaveBill> leaveBills=new ArrayList<PageLeaveBill>();
		Map<String , Object> params=new HashMap<String,Object>();
		String hql="from LeaveBill l	";
	
		//拼接hql查询语句查找出全部请假流程 上帝视角
		//需要添加按人员查找
		List<LeaveBill> l = leaveBillDaoI.find(hql + whereHql(pLeaveBill, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		if (l!=null && l.size()>0) {
			for(LeaveBill leaveBill:l){
				PageLeaveBill pageLeaveBill=new PageLeaveBill();
				BeanUtils.copyProperties(leaveBill, pageLeaveBill,new String []{"remark","user"});
				pageLeaveBill.setUser(leaveBill.getUser().getName());
				pageLeaveBill.setRemark(ClobUtil.getString(leaveBill.getRemark()));
				//pageLeaveBill.setLeaveDate(leaveBill.getLeaveDate());
				//System.out.println(pageLeaveBill.toString());
				
				leaveBills.add(pageLeaveBill);
				
			}
		}
		dg.setRows(leaveBills);
		dg.setTotal(leaveBillDaoI.count("select count (*)"+hql+whereHql(pLeaveBill, params), params));
		
		
		return dg;
	}

	private String orderHql(PageHelper ph) {
		// TODO Auto-generated method stub
		String orderString = "";
		if (ph.getSort() != null && ph.getOrder()!= null) {
			orderString="order by l."+ph.getSort()+" "+ph.getOrder();
		}
		return orderString;
	}

	private String whereHql(PageLeaveBill pLeaveBill, Map<String, Object> params) {
		// TODO Auto-generated method stub
		String whereHql = "";
		whereHql += " where 1=1 ";
		if (pLeaveBill != null) {
			if (pLeaveBill.getCreatedatetimeStart() != null) {
				whereHql += " and l.createdatetime >= :createdatetimeStart ";
				params.put("createdatetimeStart", pLeaveBill.getCreatedatetimeStart());
			}
			if (pLeaveBill.getCreatedatetimeEnd() != null) {
				whereHql += " and l.createdatetime <= :createdatetimeEnd ";
				params.put("createdatetimeEnd", pLeaveBill.getCreatedatetimeEnd());
			}if(pLeaveBill.getUser() != null){
				whereHql += "and l.user.name =:user ";
				params.put("user", pLeaveBill.getUser());
			}
		}
		return whereHql;
	}
	

	@Override
	public void add(LeaveBill leaveBill, User user) {
		// TODO Auto-generated method stub
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("id", user.getId());
		Tuser tuser=userDaoI.get("from Tuser t where t.id = :id",params);
		leaveBill.setUser(tuser);
		
		leaveBillDaoI.save(leaveBill);
	}

	@Override
	public PageLeaveBill findpageLeaveBillById(String id) {
		// TODO Auto-generated method stub
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		LeaveBill leaveBill= leaveBillDaoI.get("from LeaveBill l where l.id=:id",params);
		PageLeaveBill pageLeaveBill=new PageLeaveBill();
		BeanUtils.copyProperties(leaveBill, pageLeaveBill,new String[]{"remark","user"});
		pageLeaveBill.setUser(leaveBill.getUser().getName());
		pageLeaveBill.setRemark(ClobUtil.getString(leaveBill.getRemark()));
		return pageLeaveBill;
	}

	@Override
	public void edit(PageLeaveBill pageLeaveBill) {
		// TODO Auto-generated method stub
		LeaveBill leaveBill=leaveBillDaoI.get(LeaveBill.class,pageLeaveBill.getId());
		if (leaveBill!=null) {
			BeanUtils.copyProperties(pageLeaveBill, leaveBill,new String[]{"remark","user","leaveDate"});
			leaveBill.setRemark(ClobUtil.getClob(pageLeaveBill.getRemark()));
			leaveBill.setLeaveDate(new Date());
			leaveBillDaoI.save(leaveBill);
		}
	}
}
