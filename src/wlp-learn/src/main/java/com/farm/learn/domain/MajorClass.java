package com.farm.learn.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：专业课程类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "MajorClass")
@Table(name = "wlp_l_majorclass")
public class MajorClass implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "CHAPTERID", length = 32, nullable = false)
	private String chapterid;
	@Column(name = "CLASSID", length = 32, nullable = false)
	private String classid;
	@Column(name = "SORT", length = 10, nullable = false)
	private Integer sort;
	@Column(name = "MAJORID", length = 32, nullable = false)
	private String majorid;
	@Transient
	private Classt classt;
	@Transient
	private String imgUrl;
	@Transient
	private UserClass userclass;
	@Transient
	private Boolean readable;

	public Boolean getReadable() {
		return readable;
	}

	public void setReadable(Boolean readable) {
		this.readable = readable;
	}

	public UserClass getUserclass() {
		return userclass;
	}

	public void setUserclass(UserClass userclass) {
		this.userclass = userclass;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Classt getClasst() {
		return classt;
	}

	public void setClasst(Classt classt) {
		this.classt = classt;
	}

	public String getChapterid() {
		return this.chapterid;
	}

	public void setChapterid(String chapterid) {
		this.chapterid = chapterid;
	}

	public String getClassid() {
		return this.classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getMajorid() {
		return this.majorid;
	}

	public void setMajorid(String majorid) {
		this.majorid = majorid;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}