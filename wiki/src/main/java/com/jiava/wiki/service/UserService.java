package com.jiava.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiava.wiki.config.WikiApplication;
import com.jiava.wiki.domain.Ebook;
import com.jiava.wiki.domain.User;
import com.jiava.wiki.domain.UserExample;
import com.jiava.wiki.exception.BusinessException;
import com.jiava.wiki.exception.BusinessExceptionCode;
import com.jiava.wiki.mapper.UserMapper;
import com.jiava.wiki.req.UserQueryReq;
import com.jiava.wiki.req.UserResetReq;
import com.jiava.wiki.req.UserSaveReq;
import com.jiava.wiki.resp.UserQueryResp;
import com.jiava.wiki.resp.PageResp;
import com.jiava.wiki.util.CopyUtil;
import com.jiava.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(WikiApplication.class);

    @Resource
    private UserMapper userMapper;
    @Resource
    private SnowFlake snowFlake;

    public PageResp<UserQueryResp> list(UserQueryReq req) {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andLoginNameEqualTo(req.getLoginName());
        }
//PageHelper只会对之后第一条查询数据生效,所以和要分页的sql放在一起
        PageHelper.startPage(req.getPage(), req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);


        PageInfo<User> pageInfo = new PageInfo<>(userList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());
        List<UserQueryResp> respList = CopyUtil.copyList(userList, UserQueryResp.class);
        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setList(respList);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    //    保存
    public void save(UserSaveReq req) {
        User user = CopyUtil.copy(req, User.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            //新增
            if(selectByLoginName(user.getLoginName())==null){
            user.setId(snowFlake.nextId());
            userMapper.insert(user);
            }
            else{
                //用户名已存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else {
            user.setLoginName(null);
            user.setPassword(null);
            //更新,用updateByPrimaryKeySelective，只更新user里原来就不为空的属性
            //这样就无论如何都改不了用户名了
            userMapper.updateByPrimaryKeySelective(user);
        }
    }
    public void reset(UserResetReq req) {
        User user = CopyUtil.copy(req, User.class);
        userMapper.updateByPrimaryKeySelective(user);
    }

    //删除
    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }
    public User selectByLoginName(String LoginName){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(LoginName);
        List<User> userList=userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(userList)){
            return  null;
        }else {
            return userList.get(0);
        }
    }
}
