package com.farm.learn.domain.ex;

import java.util.List;

import com.farm.learn.domain.Major;
import com.farm.learn.domain.MajorChapter;
import com.farm.learn.domain.MajorClass;

public class MajorView {
	private Major major = null;
	private String imgurl = null;
	private List<MajorChapter> chapters;
	private List<MajorClass> classes;

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public List<MajorChapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<MajorChapter> chapters) {
		this.chapters = chapters;
	}

	public List<MajorClass> getClasses() {
		return classes;
	}

	public void setClasses(List<MajorClass> classes) {
		this.classes = classes;
	}
}
