package com.farm.social.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：评论对象类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "EvaluationObj")
@Table(name = "wlp_s_evaluation_obj")
public class EvaluationObj implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "SCORE", length = 10, nullable = false)
        private Float score;
        @Column(name = "NUM", length = 10, nullable = false)
        private Integer num;
        @Column(name = "APPTYPE", length = 1, nullable = false)
        private String apptype;
        @Column(name = "APPID", length = 32, nullable = false)
        private String appid;
        @Column(name = "PCONTENT", length = 128)
        private String pcontent;

        public Float  getScore() {
          return this.score;
        }
        public void setScore(Float score) {
          this.score = score;
        }
        public Integer  getNum() {
          return this.num;
        }
        public void setNum(Integer num) {
          this.num = num;
        }
        public String  getApptype() {
          return this.apptype;
        }
        public void setApptype(String apptype) {
          this.apptype = apptype;
        }
        public String  getAppid() {
          return this.appid;
        }
        public void setAppid(String appid) {
          this.appid = appid;
        }
        public String  getPcontent() {
          return this.pcontent;
        }
        public void setPcontent(String pcontent) {
          this.pcontent = pcontent;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}