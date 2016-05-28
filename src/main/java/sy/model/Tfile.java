package sy.model;

import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name="tfile")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tfile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String userid;
	private String username;
	private Clob remark;
	private Date uploadtime;
	private String size;
	private String url;
	private String filename;
	private String type;
	
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return id;
	}

	@Column(name="USERID",nullable=false,length=200)
	public String getUserid() {
		return userid;
	}
	
	@Column(name="USERNAME",nullable=false,length=200)
	public String getUsername() {
		return username;
	}
	@Column(name="REMARK",nullable=true)
	public Clob getRemark() {
		return remark;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPLOADTIME",nullable=false,length=200)
	public Date getUploadtime() {
		return uploadtime;
	}
	
	@Column(name="SIZE",nullable=true,length=200)
	public String getSize() {
		return size;
	}
	
	@Column(name="URL",nullable=true,length=200)
	public String getUrl() {
		return url;
	}
	
	@Column(name="FILENAME",nullable=true,length=200)
	public String getFilename() {
		return filename;
	}
	
	@Column(name="TYPE",nullable=true,length=20)
	public String getType() {
		return type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRemark(Clob remark) {
		this.remark = remark;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
