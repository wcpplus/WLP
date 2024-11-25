package com.farm.learn.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：专业权限类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "MajorPop")
@Table(name = "wlp_l_majorpop")
public class MajorPop implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "MAJORID", length = 32, nullable = false)
        private String majorid;
        @Column(name = "ONAME", length = 64, nullable = false)
        private String oname;
        @Column(name = "OID", length = 32, nullable = false)
        private String oid;
        @Column(name = "FUNTYPE", length = 1, nullable = false)
        private String funtype;
        @Column(name = "POPTYPE", length = 1, nullable = false)
        private String poptype;

        public String  getMajorid() {
          return this.majorid;
        }
        public void setMajorid(String majorid) {
          this.majorid = majorid;
        }
        public String  getOname() {
          return this.oname;
        }
        public void setOname(String oname) {
          this.oname = oname;
        }
        public String  getOid() {
          return this.oid;
        }
        public void setOid(String oid) {
          this.oid = oid;
        }
        public String  getFuntype() {
          return this.funtype;
        }
        public void setFuntype(String funtype) {
          this.funtype = funtype;
        }
        public String  getPoptype() {
          return this.poptype;
        }
        public void setPoptype(String poptype) {
          this.poptype = poptype;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}