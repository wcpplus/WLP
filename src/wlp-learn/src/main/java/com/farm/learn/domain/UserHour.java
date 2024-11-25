package com.farm.learn.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：用户课时类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "UserHour")
@Table(name = "wlp_u_userhour")
public class UserHour implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "ETIME", length = 14)
        private String etime;
        @Column(name = "STIME", length = 14)
        private String stime;
        @Column(name = "LTIME", length = 14)
        private String ltime;
        @Column(name = "CLASSID", length = 32, nullable = false)
        private String classid;
        @Column(name = "USERID", length = 32, nullable = false)
        private String userid;
        @Column(name = "PSTATE", length = 1, nullable = false)
        private String pstate;
        @Column(name = "HOURID", length = 32, nullable = false)
        private String hourid;
        
        public String getLtime() {
			return ltime;
		}
		public void setLtime(String ltime) {
			this.ltime = ltime;
		}
		public String  getEtime() {
          return this.etime;
        }
        public void setEtime(String etime) {
          this.etime = etime;
        }
        public String  getStime() {
          return this.stime;
        }
        public void setStime(String stime) {
          this.stime = stime;
        }
        public String  getClassid() {
          return this.classid;
        }
        public void setClassid(String classid) {
          this.classid = classid;
        }
        public String  getUserid() {
          return this.userid;
        }
        public void setUserid(String userid) {
          this.userid = userid;
        }
        public String  getPstate() {
          return this.pstate;
        }
        public void setPstate(String pstate) {
          this.pstate = pstate;
        }
        public String  getHourid() {
          return this.hourid;
        }
        public void setHourid(String hourid) {
          this.hourid = hourid;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}