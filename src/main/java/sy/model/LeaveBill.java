package sy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="leavebill")
@DynamicInsert(true)
@DynamicUpdate(true)
public class LeaveBill {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;//主键ID
	
	@Column(name="DAYS",nullable=false)
	private Integer days;// 请假天数
	
	@Column(name="CONTENT",length=200)
	private String content;// 请假内容
	
	@Column(name="DATE",length=7)
	@Temporal(TemporalType.TIMESTAMP)
	private Date leaveDate = new Date();// 请假时间
	
	@Column(name="REMARK",length=200)
	private String remark;// 备注
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	private Tuser user;// 请假人
	
	@Column(name="STATE",length=1)
	private Integer state=0;// 请假单状态 0初始录入,1.开始审批,2为审批完成

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

	public Tuser getUser() {
		return user;
	}

	public void setUser(Tuser user) {
		this.user = user;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	
	
}
