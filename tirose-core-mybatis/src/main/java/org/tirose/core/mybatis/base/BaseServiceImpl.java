/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.tirose.core.mybatis.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.tirose.core.secure.bean.SnowingUser;
import org.tirose.core.secure.util.SecureUtil;
import org.tirose.core.tool.constant.SnowingConstant;
import org.tirose.core.tool.util.BeanUtil;
import org.tirose.core.tool.util.DateUtil;
import org.tirose.core.tool.util.StringUtil;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 业务封装基础类
 *
 * @param <M> mapper
 * @param <T> model
 * @author Chill
 */
@Validated
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

	private Class<T> modelClass;

	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		Type type = this.getClass().getGenericSuperclass();
		this.modelClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[1];
	}

	@Override
	public boolean save(T entity) {
		SnowingUser user = SecureUtil.getUser();
		if (user != null) {
			entity.setCreateUser(user.getUserId());
			entity.setUpdateUser(user.getUserId());
		}
		Date now = DateUtil.now();
		entity.setGmtCreate(now);
		entity.setGmtUpdate(now);
		if (entity.getStatus() == null) {
			entity.setStatus(SnowingConstant.DB_STATUS_NORMAL);
		}
		entity.setIsDeleted(SnowingConstant.DB_NOT_DELETED);

		entity.setDeleteToken(StringUtil.randomUUID());
		return super.save(entity);
	}

	@Override
	public boolean updateById(T entity) {
		SnowingUser user = SecureUtil.getUser();
		if (user != null) {
			entity.setUpdateUser(user.getUserId());
		}
		entity.setGmtUpdate(DateUtil.now());

		return super.updateById(entity);
	}

	@Override
	public boolean saveOrUpdate(T entity) {
		if (entity.getId() == null) {
			return this.save(entity);
		} else {
			return this.updateById(entity);
		}
	}

	@Override
	public Serializable saveAndReturnId(T entity) {
		save(entity);
		return entity.getId();
	}

	@Override
	public boolean deleteLogic(@NotEmpty List<Long> ids) {
		SnowingUser user = SecureUtil.getUser();
		List<T> list = new ArrayList<>();
		ids.forEach(id -> {
			T entity = BeanUtil.newInstance(modelClass);
			if (user != null) {
				entity.setUpdateUser(user.getUserId());
			}
			entity.setGmtUpdate(DateUtil.now());
			entity.setId(id);
			list.add(entity);
		});
		return super.updateBatchById(list) && super.removeByIds(ids);
	}

}
