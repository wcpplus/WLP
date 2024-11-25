INSERT INTO ALONE_APP_VERSION VALUES ('v1.0.0',  now(), 'USERNAME');
create table WLP_S_FAVORITE_OBJ
(
   ID                   varchar(32) not null,
   PCONTENT             varchar(128),
   APPID                varchar(32) not null,
   APPTYPE              varchar(1) not null comment '1:专业,2:课程,3:课时',
   NUM                  int not null,
   primary key (ID)
);
create table WLP_S_FAVORITE_USER
(
   ID                   varchar(32) not null,
   USERID               varchar(32) not null,
   DOTYPE               varchar(1) not null comment '1.主动收藏',
   CTIME                varchar(16) not null,
   OBJID                varchar(32) not null,
   primary key (ID)
);

alter table WLP_S_FAVORITE_USER add constraint FK_Reference_55 foreign key (OBJID)
      references WLP_S_FAVORITE_OBJ (ID) on delete restrict on update restrict;

      
      
      
create table WLP_S_COMMENTS_OBJ
(
   ID                   varchar(32) not null,
   PCONTENT             varchar(128),
   APPID                varchar(32) not null,
   APPTYPE              varchar(1) not null comment '1:专业,2:课程,3:课时',
   NUM                  int not null,
   primary key (ID)
);
create table WLP_S_COMMENTS_USER
(
   ID                   varchar(32) not null,
   USERID               varchar(32) not null,
   CTIME                varchar(16) not null,
   NOTE                 varchar(4096) not null,
   PARENTID             varchar(32),
   OBJID                varchar(32) not null,
   primary key (ID)
);

alter table WLP_S_COMMENTS_USER add constraint FK_Reference_32 foreign key (OBJID)
      references WLP_S_COMMENTS_OBJ (ID) on delete restrict on update restrict;

      
      

create table WLP_S_EVALUATION_OBJ
(
   ID                   varchar(32) not null,
   PCONTENT             varchar(128),
   APPID                varchar(32) not null,
   APPTYPE              varchar(1) not null comment '1:专业,2:课程,3:课时',
   NUM                  float(10,2) not null,
   SCORE                int not null,
   primary key (ID)
);

create table WLP_S_EVALUATION_USER
(
   ID                   varchar(32) not null,
   USERID               varchar(32) not null,
   CTIME                varchar(16) not null,
   NOTE                 varchar(2048) not null,
   SCORE                int not null,
   OBJID                varchar(32) not null,
   primary key (ID)
);

alter table WLP_S_EVALUATION_USER add constraint FK_Reference_38 foreign key (OBJID)
      references WLP_S_EVALUATION_OBJ (ID) on delete restrict on update restrict;
      
      
create table WLP_S_OUTSYS
(
   ID                   varchar(32) not null,
   SYSNAME              varchar(64) not null,
   PCONTENT             varchar(128),
   BASEURL              varchar(512) not null,
   PSTATE               varchar(1) not null,
   primary key (ID)
);

create table WLP_S_APPBIND_OBJ
(
   ID                   varchar(32) not null,
   PCONTENT             varchar(128),
   APPID                varchar(32) not null,
   APPTYPE              varchar(1) not null comment '1:专业,2:课程,3:课时',
   NUM                  int not null,
   primary key (ID)
);

create table WLP_S_APPBIND_RESOURCE
(
   ID                   varchar(32) not null,
   USERID               varchar(32) not null,
   CTIME                varchar(16) not null,
   RESOURCEURL          varchar(256) not null,
   RESOURCETYPE         varchar(1) not null comment '1.知识库,2.问答,3.答题室',
   SYSID                varchar(32) not null,
   OBJID                varchar(32) not null,
   primary key (ID)
);

alter table WLP_S_APPBIND_RESOURCE add constraint FK_Reference_37 foreign key (OBJID)
      references WLP_S_APPBIND_OBJ (ID) on delete restrict on update restrict;

alter table WLP_S_APPBIND_RESOURCE add constraint FK_Reference_47 foreign key (SYSID)
      references WLP_S_OUTSYS (ID) on delete restrict on update restrict;
