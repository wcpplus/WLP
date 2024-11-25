package com.farm.social.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：应用绑定资源类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "AppbindResource")
@Table(name = "wlp_s_appbind_resource")
public class AppbindResource implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "OBJID", length = 32, nullable = false)
        private String objid;
        @Column(name = "SYSID", length = 32, nullable = false)
        private String sysid;
        @Column(name = "RESOURCETYPE", length = 1, nullable = false)
        private String resourcetype;
        @Column(name = "RESOURCEURL", length = 256, nullable = false)
        private String resourceurl;
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
        public String  getSysid() {
          return this.sysid;
        }
        public void setSysid(String sysid) {
          this.sysid = sysid;
        }
        public String  getResourcetype() {
          return this.resourcetype;
        }
        public void setResourcetype(String resourcetype) {
          this.resourcetype = resourcetype;
        }
        public String  getResourceurl() {
          return this.resourceurl;
        }
        public void setResourceurl(String resourceurl) {
          this.resourceurl = resourceurl;
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