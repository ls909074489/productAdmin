package com.yy.modules.sys.feedback;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.yy.common.annotation.MetaData;
import com.yy.frame.entity.BaseEntity;
import com.yy.modules.sys.user.UserRef;

@MetaData(value = "意见反馈")
@Entity
@Table(name = "yy_free_back")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FeedbackEntity extends BaseEntity{
	
	private static final long serialVersionUID = -1960513658056423524L;

	 @MetaData(value = "意见类型")
	 @Column(name="feedback_type",length=2)
	 private String feedbackType="0";  
	 
	 @MetaData(value = "返回意见")
	 @Column(name="feedback_content",length=500)
     private String feedbackContent;


	 @MetaData(value="用户id")
	 @ManyToOne(cascade=CascadeType.REFRESH,optional = true)
	 @NotFound(action=NotFoundAction.IGNORE)
	 @JoinColumn(name = "user_id",nullable=true)
	 private UserRef user;
	 
	 @MetaData(value = "登录ip")
	 @Column(name = "login_ip", length = 50)
	 private String loginIp;
	 


	public String getFeedbackType() {
		return feedbackType;
	}


	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}


	public String getFeedbackContent() {
		return feedbackContent;
	}


	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}


	public UserRef getUser() {
		return user;
	}


	public void setUser(UserRef user) {
		this.user = user;
	}


	public String getLoginIp() {
		return loginIp;
	}


	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	 
	 
}
