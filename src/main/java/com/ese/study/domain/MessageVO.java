package com.ese.study.domain;

import java.util.Date;

/*
 * 사용자 간에 메시지를 주고 받는 샘플 프로젝트
 * 
 * 파라미터와 리턴 타입으로 사용하게 되는 MessageVO
 */
public class MessageVO {
	
	private Integer mid;
	private String targetid;
	private String sender;
	private String message;
	private Date opendate;
	private Date senddate;
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public String getTargetid() {
		return targetid;
	}
	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getOpendate() {
		return opendate;
	}
	public void setOpendate(Date opendate) {
		this.opendate = opendate;
	}
	public Date getSenddate() {
		return senddate;
	}
	public void setSenddate(Date senddate) {
		this.senddate = senddate;
	}
	@Override
	public String toString() {
		return "MessageVO [mid=" + mid + ", targetid=" + targetid + ", sender=" + sender + ", message=" + message
				+ ", opendate=" + opendate + ", senddate=" + senddate + "]";
	}

}
