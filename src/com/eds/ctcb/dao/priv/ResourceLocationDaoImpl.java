package com.eds.ctcb.dao.priv;

import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDaoImpl;

public class ResourceLocationDaoImpl extends BaseDaoImpl implements ResourceLocationDao {
	public List<String> getAccessableUrlList4Role(Long roleId){
		String hql = "select distinct rl.location.url from ResourceLocation rl where rl.resource.id in (select rr.resource.id from RoleResource rr where rr.role.id = ?)";
		QryBean qryBean = new QryBean(hql,new Object[]{roleId});
		return (List<String>)(this.qryInList(qryBean));
	}
}
