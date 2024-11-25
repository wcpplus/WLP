package com.farm.learn.domain.ex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.farm.category.domain.ClassType;
import com.farm.category.domain.Tag;
import com.farm.learn.domain.ClassChapter;
import com.farm.learn.domain.ClassHour;
import com.farm.learn.domain.Classt;
import com.farm.learn.domain.UserHour;

public class ClassView {
	private Classt classt = null;
	private String imgurl = null;
	private String introText = null;
	private List<ClassChapter> chapters;
	private List<ClassHour> hours;
	private ClassType type;
	private List<ClassType> types;
	private List<Tag> tags;

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<ClassType> getTypes() {
		return types;
	}

	public void setTypes(List<ClassType> types) {
		this.types = types;
	}

	public ClassType getType() {
		return type;
	}

	public void setType(ClassType type) {
		this.type = type;
	}

	public List<ClassChapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<ClassChapter> chapters) {
		this.chapters = chapters;
	}

	public List<ClassHour> getHours() {
		return hours;
	}

	public void setHours(List<ClassHour> hours) {
		this.hours = hours;
	}

	public Classt getClasst() {
		return classt;
	}

	public String getIntroText() {
		return introText;
	}

	public void setIntroText(String introText) {
		this.introText = introText;
	}

	public void setClasst(Classt classt) {
		this.classt = classt;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public void bind(ClassLearnView learnView) {
		Map<String, UserHour> userHourDic = new HashMap<String, UserHour>();
		for (UserHour uhour : learnView.getUserhours()) {
			userHourDic.put(uhour.getHourid(), uhour);
		}
		for (ClassHour hour : hours) {
			hour.setUserhour(userHourDic.get(hour.getId()));
		}
	}
}
