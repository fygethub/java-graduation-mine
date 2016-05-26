package sy.pageModel.activiti;

import java.util.Date;

public class HistoryInstance {
	private String executionId;
	private String activityId;
	private String activityType;
	private String activityName;
	private String assignee;
	private String processDefinitionId;
	private Long durationInMillis;
	private Date startTime;
	private Date endTime;
	
	
	public Long getDurationInMillis() {
		return durationInMillis;
	}
	public void setDurationInMillis(Long durationInMillis) {
		this.durationInMillis = durationInMillis;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	
	
	
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Override
	public String toString() {
		return "HistoryInstance [executionId=" + executionId + ", activityId="
				+ activityId + ", activityType=" + activityType
				+ ", activityName=" + activityName + ", assignee=" + assignee
				+ ", provessDefinitionId=" + processDefinitionId
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	
}
