package sy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Tonline entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TONLINE", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tonline implements java.io.Serializable {

	// Fields

	private String id;
	private String loginname;
	private String ip;
	private Date logindatetime;

	// Constructors

	/** default constructor */
	public Tonline() {
	}

	/** full constructor */
	public Tonline(String id, String loginname, String ip, Date logindatetime) {
		this.id = id;
		this.loginname = loginname;
		this.ip = ip;
		this.logindatetime = logindatetime;
	}

	// Property accessors
	@Id
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "LOGINNAME", nullable = false, length = 100)
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "IP", nullable = false, length = 50)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGINDATETIME", nullable = false, length = 7)
	public Date getLogindatetime() {
		return this.logindatetime;
	}

	public void setLogindatetime(Date logindatetime) {
		this.logindatetime = logindatetime;
	}

}