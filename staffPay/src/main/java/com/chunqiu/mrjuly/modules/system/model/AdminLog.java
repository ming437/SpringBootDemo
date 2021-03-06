package com.chunqiu.mrjuly.modules.system.model;

import org.hibernate.validator.constraints.Length;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.chunqiu.mrjuly.common.persistence.DataEntity;

/**
 * 系统日志Entity
 * @author qj
 * @version 2019-01-14
 */
public class AdminLog extends DataEntity<AdminLog, Long> {

	private static final long serialVersionUID = 1L;
	private String username;		// 操作用户
	private String operation;		// 操作内容
	private Long time;		// 耗时
	private String method;		// 操作方法
	private String params;		// 方法参数
	private String ip;		// 操作者IP
	private Date createTime;		// 创建时间
	private String location;		// 操作地点

	public AdminLog() {
		super();
	}

	public AdminLog(Long id){
		super(id);
	}

	@Length(min=0, max=50, message="操作用户长度必须介于 0 和 50 之间")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Length(min=0, max=64, message="操作者IP长度必须介于 0 和 64 之间")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Length(min=0, max=50, message="操作地点长度必须介于 0 和 50 之间")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}