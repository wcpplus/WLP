package com.farm.social.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：用户评价类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "EvaluationUser")
@Table(name = "wlp_s_evaluation_user")
public class EvaluationUser implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "OBJID", length = 32, nullable = false)
        private String objid;
        @Column(name = "SCORE", length = 10, nullable = false)
        private Integer score;
        @Column(name = "NOTE", length = 2048, nullable = false)
        private String note;
        @Column(name = "CTIME", length = 16, nullable = false)
        private String ctime;
        @Column(name = "USERID", length = 32, nullable = false)
        private String userid;

        public String  getObjid() {
          return this.objid;
        }
        public void setObjid(String objid) {
          this.objid = objid;
        }
        public Integer  getScore() {
          return this.score;
        }
        public void setScore(Integer score) {
          this.score = score;
        }
        public String  getNote() {
          return this.note;
        }
        public void setNote(String note) {
          this.note = note;
        }
        public String  getCtime() {
          return this.ctime;
        }
        public void setCtime(String ctime) {
          this.ctime = ctime;
        }
        public String  getUserid() {
          return this.userid;
        }
        public void setUserid(String userid) {
          this.userid = userid;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}