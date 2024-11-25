package com.farm.category.service.impl;

import com.farm.category.domain.Tag;
import com.farm.category.domain.TagType;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import org.hibernate.mapping.Array;

import com.farm.category.dao.TagDaoInter;
import com.farm.category.dao.TagtypeDaoInter;
import com.farm.category.service.TagServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：标签服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class TagServiceImpl implements TagServiceInter {
	@Resource
	private TagDaoInter tagDaoImpl;
	@Resource
	private TagtypeDaoInter tagtypeDaoImpl;
	private static final Logger log = Logger.getLogger(TagServiceImpl.class);

	@Override
	@Transactional
	public Tag insertTagEntity(Tag entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		return tagDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public Tag editTagEntity(Tag entity, LoginUser user) {
		Tag entity2 = tagDaoImpl.getEntity(entity.getId());
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setName(entity.getName());
		entity2.setSort(entity.getSort());
		entity2.setPstate(entity.getPstate());
		tagDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteTagEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		tagDaoImpl.deleteEntity(tagDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public Tag getTagEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return tagDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createTagSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "WLP_C_TAG a  left join WLP_C_TAGTYPE b on a.typeid=b.id",
				"a.ID as ID,a.TYPEID as TYPEID,a.NAME as NAME,a.SORT as SORT,a.PCONTENT as PCONTENT,a.PSTATE as PSTATE,b.name as TYPENAME");
		return dbQuery;
	}

	@Override
	@Transactional
	public List<TagType> getAllTags() {
		List<TagType> types = tagtypeDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PSTATE", "1", "=")).toList());
		List<Tag> tags = tagDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PSTATE", "1", "=")).toList());
		Collections.sort(types, new Comparator<TagType>() {
			@Override
			public int compare(TagType o1, TagType o2) {
				return o1.getSort() - o2.getSort();
			}
		});
		Collections.sort(tags, new Comparator<Tag>() {
			@Override
			public int compare(Tag o1, Tag o2) {
				return o1.getSort() - o2.getSort();
			}
		});
		for (TagType type : types) {
			if (type.getTags() == null) {
				type.setTags(new ArrayList<Tag>());
			}
			for (Tag tag : tags) {
				if (tag.getTypeid().equals(type.getId())) {
					type.getTags().add(tag);
				}
			}
		}
		return types;
	}

}
