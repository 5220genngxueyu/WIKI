package com.jiava.wiki.controller;

import com.jiava.wiki.req.UserLoginReq;
import com.jiava.wiki.req.UserQueryReq;
import com.jiava.wiki.req.UserResetReq;
import com.jiava.wiki.req.UserSaveReq;
import com.jiava.wiki.resp.CommonResp;
import com.jiava.wiki.resp.UserLoginResp;
import com.jiava.wiki.resp.UserQueryResp;
import com.jiava.wiki.resp.PageResp;
import com.jiava.wiki.service.UserService;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq req){

        CommonResp<PageResp<UserQueryResp> > resp = new CommonResp<>();
        PageResp<UserQueryResp> list=userService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.save(req);
        return resp;
    }
    @PostMapping("/reset")
    public CommonResp reset(@Valid @RequestBody UserResetReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.reset(req);
        return resp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }
    @PostMapping("/login")
    public CommonResp<UserLoginResp> reset(@Valid @RequestBody UserLoginReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<UserLoginResp> resp = new CommonResp<>();
        UserLoginResp userLoginResp=userService.login(req);
        resp.setContent(userLoginResp);
        return resp;
    }
}
