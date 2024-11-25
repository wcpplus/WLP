package com.farm.learn.domain.ex;

import java.util.ArrayList;
import java.util.List;

import com.farm.learn.domain.UserClass;
import com.farm.learn.domain.UserHour;

/**
 * 某课程的用户学习状态
 * 
 * @author macpl
 *
 */
public class ClassLearnView {
	//用户是否学习过该课程或者被指定学习该课程
	private boolean learned;
	private UserClass userclass;
	private List<UserHour> userhours;

	public boolean isLearned() {
		return learned;
	}

	public void setLearned(boolean learned) {
		this.learned = learned;
	}

	public UserClass getUserclass() {
		return userclass;
	}

	public void setUserclass(UserClass userclass) {
		this.userclass = userclass;
	}

	public List<UserHour> getUserhours() {
		return userhours;
	}

	public void setUserhours(List<UserHour> userhours) {
		this.userhours = userhours;
	}

	public static ClassLearnView getNone() {
		ClassLearnView theView = new ClassLearnView();
		theView.setLearned(false);
		theView.setUserclass(null);
		theView.setUserhours(new ArrayList<UserHour>());
		return theView;
	}

}
