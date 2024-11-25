package com.farm.learn.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：专业类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "Major")
@Table(name = "wlp_l_major")
public class Major implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "IMGID", length = 32)
        private String imgid;
        @Column(name = "NOTE", length = 512)
        private String note;
        @Column(name = "CHAPTERNUM", length = 10, nullable = false)
        private Integer chapternum;
        @Column(name = "CLASSNUM", length = 10, nullable = false)
        private Integer classnum;
        @Column(name = "TITLE", length = 256, nullable = false)
        private String title;
        @Column(name = "ENJOYNUM", length = 10, nullable = false)
        private Integer enjoynum;
        @Column(name = "PCONTENT", length = 128)
        private String pcontent;
        @Column(name = "PSTATE", length = 2, nullable = false)
        private String pstate;
        @Column(name = "EUSER", length = 32, nullable = false)
        private String euser;
        @Column(name = "EUSERNAME", length = 64, nullable = false)
        private String eusername;
        @Column(name = "CUSER", length = 32, nullable = false)
        private String cuser;
        @Column(name = "CUSERNAME", length = 64, nullable = false)
        private String cusername;
        @Column(name = "ETIME", length = 16, nullable = false)
        private String etime;
        @Column(name = "CTIME", length = 16, nullable = false)
        private String ctime;
        @Column(name = "READPOP", length = 1, nullable = false)
        private String readpop;
        @Column(name = "WRITEPOP", length = 1, nullable = false)
        private String writepop;
        @Column(name = "SORT", length = 10, nullable = false)
        private Integer sort;
        
        public Integer getSort() {
			return sort;
		}
		public void setSort(Integer sort) {
			this.sort = sort;
		}
		public String getReadpop() {
			return readpop;
		}
		public void setReadpop(String readpop) {
			this.readpop = readpop;
		}
		public String getWritepop() {
			return writepop;
		}
		public void setWritepop(String writepop) {
			this.writepop = writepop;
		}
		public String  getImgid() {
          return this.imgid;
        }
        public void setImgid(String imgid) {
          this.imgid = imgid;
        }
        public String  getNote() {
          return this.note;
        }
        public void setNote(String note) {
          this.note = note;
        }
        public Integer  getChapternum() {
          return this.chapternum;
        }
        public void setChapternum(Integer chapternum) {
          this.chapternum = chapternum;
        }
        public Integer  getClassnum() {
          return this.classnum;
        }
        public void setClassnum(Integer classnum) {
          this.classnum = classnum;
        }
        public String  getTitle() {
          return this.title;
        }
        public void setTitle(String title) {
          this.title = title;
        }
        public Integer  getEnjoynum() {
          return this.enjoynum;
        }
        public void setEnjoynum(Integer enjoynum) {
          this.enjoynum = enjoynum;
        }
        public String  getPcontent() {
          return this.pcontent;
        }
        public void setPcontent(String pcontent) {
          this.pcontent = pcontent;
        }
        public String  getPstate() {
          return this.pstate;
        }
        public void setPstate(String pstate) {
          this.pstate = pstate;
        }
        public String  getEuser() {
          return this.euser;
        }
        public void setEuser(String euser) {
          this.euser = euser;
        }
        public String  getEusername() {
          return this.eusername;
        }
        public void setEusername(String eusername) {
          this.eusername = eusername;
        }
        public String  getCuser() {
          return this.cuser;
        }
        public void setCuser(String cuser) {
          this.cuser = cuser;
        }
        public String  getCusername() {
          return this.cusername;
        }
        public void setCusername(String cusername) {
          this.cusername = cusername;
        }
        public String  getEtime() {
          return this.etime;
        }
        public void setEtime(String etime) {
          this.etime = etime;
        }
        public String  getCtime() {
          return this.ctime;
        }
        public void setCtime(String ctime) {
          this.ctime = ctime;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}