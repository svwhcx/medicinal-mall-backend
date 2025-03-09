package com.medicinal.mall.mall.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.medicinal.mall.mall.demos.command.ChangePasswordCmd;
import com.medicinal.mall.mall.demos.command.FindPasswordCmd;
import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.query.UserRequest;
import com.medicinal.mall.mall.demos.token.TokenBuilder;
import com.medicinal.mall.mall.demos.token.TokenInfo;
import com.medicinal.mall.mall.demos.verifycode.IVerifyCode;
import com.medicinal.mall.mall.demos.verifycode.VerifyCodeConstant;
import com.medicinal.mall.mall.demos.dao.UserDao;
import com.medicinal.mall.mall.demos.entity.User;
import com.medicinal.mall.mall.demos.exception.ParamException;
import com.medicinal.mall.mall.demos.exception.UserLogFail;
import com.medicinal.mall.mall.demos.query.VerifyCodeRequest;
import com.medicinal.mall.mall.demos.service.UserService;
import com.medicinal.mall.mall.demos.util.PasswordUtils;
import com.medicinal.mall.mall.demos.verifycode.context.VerifyCodeContext;
import com.medicinal.mall.mall.demos.vo.UserInfoVo;
import com.medicinal.mall.mall.demos.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/24 10:17
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private VerifyCodeContext verifyCodeContext;

    @Override
    public UserLoginVo userLogin(UserRequest userRequest) {

        // 先验证图片验证码是否正确
        IVerifyCode iVerifyCode = verifyCodeContext.getIVerifyCode(userRequest.getVerifyType());
        if (!iVerifyCode.checkVerifyCode(new VerifyCodeRequest(userRequest.getPictureUUID(), userRequest.getVerifyCode(), userRequest.getVerifyType(), null))) {
            throw new ParamException(ResponseDataEnum.VERIFICATION_ERROR);
        }

        // 先对密码进行加密验证
        userRequest.setPassword(PasswordUtils.encryption(userRequest.getPassword()));
        // 使用LambdaQueryWrapper来进行User实体类的账号和密码查询
        // 获取数据库中的用户信息
        User userInfo = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getAccount, userRequest.getAccount()).eq(User::getPassword, userRequest.getPassword()));
        if (userInfo == null) {
            throw new UserLogFail(ResponseDataEnum.LOGIN_FAIL);
        }
        // 如果用户登录成功则进行token的构建。
        UserLoginVo userLoginVo = new UserLoginVo();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUserId(userInfo.getId());
        tokenInfo.setRoleId(RoleEnum.user.getRoleId());
        tokenInfo.setUsername(userInfo.getAccount());
        userLoginVo.setToken(TokenBuilder.buildToken(tokenInfo));
        userLoginVo.setUserInfo(new UserInfoVo(userInfo.getAccount(), userInfo.getAvatar()));
//        userLoginVo.setUsername(userInfo.getAccount());
        return userLoginVo;
    }

    @Override
    public void register(UserRequest userRequest) {
        // 然后验证邮箱是否已经被占用了
        if (userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, userRequest.getEmail())) != null) {
            throw new ParamException(ResponseDataEnum.EMAIL_EXITS);
        }
        // 如果没有注册则验证验证码是否正确
        Boolean verifyRes = verifyCodeContext.getIVerifyCode(VerifyCodeConstant.EMAIL_VERIFY)
                .checkVerifyCode(new VerifyCodeRequest(userRequest.getEmail(), userRequest.getVerifyCode(), VerifyCodeConstant.EMAIL_VERIFY, null));
        if (!verifyRes) {
            throw new ParamException(ResponseDataEnum.VERIFICATION_ERROR);
        }
        // 用户的注册操作，先验证是否已经有用户注册该账号了
        if (userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getAccount, userRequest.getAccount())) != null) {
            throw new UserLogFail(ResponseDataEnum.USERNAME_EXIST);
        }

        // 然后用户注册时的密码强度的一个校验部分
        if (!PasswordUtils.isStrong(userRequest.getPassword())) {
            throw new UserLogFail(ResponseDataEnum.PASSWORD_NOT_STRONG);
        }
        // 都通过了，使用加密手段对用户的密码进行加密存储.
        User user = new User();
        user.setAccount(userRequest.getAccount());
        user.setCreateTime(LocalDateTime.now());
        user.setPassword(PasswordUtils.encryption(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        userDao.insert(user);
    }

    @Override
    public void updateById(User user) {
        Integer userId = UserInfoThreadLocal.get().getUserId();
        user.setId(userId);
        this.userDao.updateById(user);
    }

    @Override
    public void logout(UserLoginVo userLoginVo) {

    }

    @Override
    public void findPassword(FindPasswordCmd findPasswordCmd) {
        // TODO
        // 1. 先从Redis中查找对应的邮箱的验证码
        VerifyCodeRequest verifyCodeRequest = new VerifyCodeRequest(findPasswordCmd.getEmail(), findPasswordCmd.getCode(), VerifyCodeConstant.EMAIL_VERIFY, null);
        if (!verifyCodeContext.getIVerifyCode(verifyCodeRequest).checkVerifyCode(verifyCodeRequest)) {
            throw new ParamException(ResponseDataEnum.VERIFICATION_ERROR);
        }
        // 校验一下用户设置的新密码的强度
        if (!PasswordUtils.isStrong(findPasswordCmd.getNewPwd())) {
            throw new ParamException(ResponseDataEnum.PASSWORD_NOT_STRONG);
        }
        // 如果验证码正确，则根据邮箱修改对应的用户的登录密码
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userLambdaUpdateWrapper.eq(User::getEmail, findPasswordCmd.getEmail());
        userLambdaUpdateWrapper.set(User::getPassword, PasswordUtils.encryption(findPasswordCmd.getNewPwd()));
        userDao.update(userLambdaUpdateWrapper);
    }

    @Override
    public void setMainAddr(Integer addrID) {
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        Integer userID = UserInfoThreadLocal.get().getUserId();
        userLambdaUpdateWrapper.eq(User::getId, userID);
        userLambdaUpdateWrapper.set(User::getMainAddrId, addrID);
        userDao.update(userLambdaUpdateWrapper);
    }

    @Override
    public User getById(Integer userId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, userId);
        return userDao.selectOne(queryWrapper);
    }

    @Override
    public void changePassword(ChangePasswordCmd changePasswordCmd) {
        // 验证新旧密码就好了
        String oldPwd = PasswordUtils.encryption(changePasswordCmd.getNewPassword());
        Integer userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, userId)
                .eq(User::getPassword, oldPwd);
        boolean exists = this.userDao.exists(queryWrapper);
        if (!exists) {
            throw new ParamException(ResponseDataEnum.OLD_PASSWORD_ERROR);
        }
        // 如果存在则将密码进行加密然后修改
        String newPwd = PasswordUtils.encryption(changePasswordCmd.getNewPassword());
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userLambdaUpdateWrapper.eq(User::getId, userId);
        userLambdaUpdateWrapper.set(User::getPassword, newPwd);
        this.userDao.update(userLambdaUpdateWrapper);
    }

    @Override
    public String queryUserNameById(Integer userId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, userId)
                .select(User::getAccount);
        return this.userDao.selectOne(queryWrapper).getAccount();
    }

    @Override
    public Integer queryAddrByUserId(Integer userID) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, userID)
                .select(User::getMainAddrId);
        return this.userDao.selectOne(queryWrapper).getMainAddrId();
    }
}
