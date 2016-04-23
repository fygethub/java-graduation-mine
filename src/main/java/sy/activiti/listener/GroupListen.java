package sy.activiti.listener;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class GroupListen implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**指定个人任务和组任务的办理人*/
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		List<String >list=new ArrayList<String>();
		list.add("aa");
		list.add("bb");
		list.add("cxx");
		delegateTask.addCandidateUsers(list);
	}

}
