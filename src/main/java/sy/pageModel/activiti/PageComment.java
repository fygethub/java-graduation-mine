package sy.pageModel.activiti;

public class PageComment {
	private String id;
	private String userId;
	private String time;
	private String taskId;
	private String processInstanceId;
	private String fullMessage;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getFullMessage() {
		return fullMessage;
	}
	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}
	
}
