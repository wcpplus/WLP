package com.farm.learn.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：专业步骤类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "MajorChapter")
@Table(name = "wlp_l_majorchapter")
public class MajorChapter implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "SORT", length = 10, nullable = false)
        private Integer sort;
        @Column(name = "MAJORID", length = 32, nullable = false)
        private String majorid;
        @Column(name = "NOTE", length = 512)
        private String note;
        @Column(name = "TITLE", length = 256, nullable = false)
        private String title;

        public Integer  getSort() {
          return this.sort;
        }
        public void setSort(Integer sort) {
          this.sort = sort;
        }
        public String  getMajorid() {
          return this.majorid;
        }
        public void setMajorid(String majorid) {
          this.majorid = majorid;
        }
        public String  getNote() {
          return this.note;
        }
        public void setNote(String note) {
          this.note = note;
        }
        public String  getTitle() {
          return this.title;
        }
        public void setTitle(String title) {
          this.title = title;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}