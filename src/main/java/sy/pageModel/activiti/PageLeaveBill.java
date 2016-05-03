package sy.pageModel.activiti;

import java.io.Serializable;
import java.util.Date;

public class PageLeaveBill implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Integer days;
	private String content;
	private Date leaveDate;
	private String remark;
	private String  user;
	private Integer state=0;
	
	private Date createdatetimeStart;
	private Date createdatetimeEnd;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getCreatedatetimeStart() {
		return createdatetimeStart;
	}
	public void setCreatedatetimeStart(Date createdatetimeStart) {
		this.createdatetimeStart = createdatetimeStart;
	}
	public Date getCreatedatetimeEnd() {
		return createdatetimeEnd;
	}
	public void setCreatedatetimeEnd(Date createdatetimeEnd) {
		this.createdatetimeEnd = createdatetimeEnd;
	}
	@Override
	public String toString() {
		return "PageLeaveBill [id=" + id + ", days=" + days + ", content="
				+ content + ", leaveDate=" + leaveDate + ", remark=" + remark
				+ ", user=" + user + ", state=" + state
				+ ", createdatetimeStart=" + createdatetimeStart
				+ ", createdatetimeEnd=" + createdatetimeEnd + "]";
	}
	
}
