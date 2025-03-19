package com.medicinal.mall.mall.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.medicinal.mall.mall.demos.command.FindPasswordCmd;
import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.dao.SellerDao;
import com.medicinal.mall.mall.demos.entity.Seller;
import com.medicinal.mall.mall.demos.exception.ParamException;
import com.medicinal.mall.mall.demos.exception.UserLogFail;
import com.medicinal.mall.mall.demos.query.UserRequest;
import com.medicinal.mall.mall.demos.query.VerifyCodeRequest;
import com.medicinal.mall.mall.demos.service.SellerService;
import com.medicinal.mall.mall.demos.token.TokenBuilder;
import com.medicinal.mall.mall.demos.token.TokenInfo;
import com.medicinal.mall.mall.demos.util.PasswordUtils;
import com.medicinal.mall.mall.demos.verifycode.IVerifyCode;
import com.medicinal.mall.mall.demos.verifycode.VerifyCodeConstant;
import com.medicinal.mall.mall.demos.verifycode.context.VerifyCodeContext;
import com.medicinal.mall.mall.demos.vo.UserInfoVo;
import com.medicinal.mall.mall.demos.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:22
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerDao sellerDao;

    @Autowired
    private VerifyCodeContext verifyCodeContext;

    @Override
    public UserLoginVo userLogin(UserRequest userRequest) {

        // 先验证图片验证码是否正确
        IVerifyCode iVerifyCode = verifyCodeContext.getIVerifyCode(VerifyCodeConstant.PHOTO_VERIFY);
        if (!iVerifyCode.checkVerifyCode(new VerifyCodeRequest(userRequest.getPictureUUID(), userRequest.getChaptchaCode(), VerifyCodeConstant.PHOTO_VERIFY, null))){
            throw new ParamException(ResponseDataEnum.VERIFICATION_ERROR);
        }
        // 先对密码进行加密验证
        userRequest.setPassword(PasswordUtils.encryption(userRequest.getPassword()));
        // 使用LambdaQueryWrapper来进行User实体类的账号和密码查询
        // 获取数据库中的用户信息
        LambdaQueryWrapper<Seller> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Seller::getUsername,userRequest.getAccount());
        queryWrapper.eq(Seller::getPassword, userRequest.getPassword());
        Seller sellerInfo = sellerDao.selectOne(queryWrapper);
        if (sellerInfo == null){
            throw new UserLogFail(ResponseDataEnum.LOGIN_FAIL);
        }
        // 如果用户登录成功则进行token的构建。
        UserLoginVo userLoginVo = new UserLoginVo();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUserId(sellerInfo.getId());
        tokenInfo.setRoleId(RoleEnum.seller.getRoleId());
        tokenInfo.setUsername(sellerInfo.getUsername());
        userLoginVo.setToken(TokenBuilder.buildToken(tokenInfo));
        userLoginVo.setUserInfo(new UserInfoVo(sellerInfo.getUsername(), sellerInfo.getAvatar()));
        return userLoginVo;
    }

    @Override
    public void register(UserRequest userRequest) {
        // 然后验证邮箱是否已经被占用了
        if (sellerDao.selectOne(new LambdaQueryWrapper<Seller>().eq(Seller::getEmail, userRequest.getEmail())) != null){
            throw new ParamException(ResponseDataEnum.EMAIL_EXITS);
        }
        // 如果没有注册则验证验证码是否正确
        Boolean verifyRes = verifyCodeContext.getIVerifyCode(VerifyCodeConstant.EMAIL_VERIFY)
                .checkVerifyCode(new VerifyCodeRequest(userRequest.getEmail(), userRequest.getEmailCode(), VerifyCodeConstant.EMAIL_VERIFY,null));
        if (!verifyRes){
            throw new ParamException(ResponseDataEnum.VERIFICATION_ERROR);
        }
        // 用户的注册操作，先验证是否已经有用户注册该账号了
        if (sellerDao.selectOne(new LambdaQueryWrapper<Seller>().eq(Seller::getUsername, userRequest.getAccount())) != null){
            throw new UserLogFail(ResponseDataEnum.USERNAME_EXIST);
        }

        // 然后用户注册时的密码强度的一个校验部分
        if (!PasswordUtils.isStrong(userRequest.getPassword())){
            throw new UserLogFail(ResponseDataEnum.PASSWORD_NOT_STRONG);
        }
        // 都通过了，使用加密手段对用户的密码进行加密存储.
        Seller seller = new Seller();
        seller.setUsername(userRequest.getAccount());
        seller.setPassword(PasswordUtils.encryption(userRequest.getPassword()));
        seller.setEmail(userRequest.getEmail());
        sellerDao.insert(seller);
    }

    @Override
    public void updateById(Seller seller) {
        // 商家修改个人的基本信息。
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        seller.setId(sellerId);
        sellerDao.updateById(seller);
    }

    @Override
    public void findPassword(FindPasswordCmd findPasswordCmd) {
        // TODO
        // 1. 先从Redis中查找对应的邮箱的验证码
        VerifyCodeRequest verifyCodeRequest = new VerifyCodeRequest(findPasswordCmd.getEmail(),findPasswordCmd.getCode(), VerifyCodeConstant.EMAIL_VERIFY,null);
        if (!verifyCodeContext.getIVerifyCode(verifyCodeRequest).checkVerifyCode(verifyCodeRequest)){
            throw new ParamException(ResponseDataEnum.VERIFICATION_ERROR);
        }
        // 校验一下用户设置的新密码的强度
        if (!PasswordUtils.isStrong(findPasswordCmd.getNewPwd())){
            throw new ParamException(ResponseDataEnum.PASSWORD_NOT_STRONG);
        }
        // 如果验证码正确，则根据邮箱修改对应的用户的登录密码
        LambdaUpdateWrapper<Seller> sellerLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        sellerLambdaUpdateWrapper.eq(Seller::getEmail,findPasswordCmd.getEmail());
        sellerLambdaUpdateWrapper.set(Seller::getPassword,PasswordUtils.encryption(findPasswordCmd.getNewPwd()));
        sellerDao.update(sellerLambdaUpdateWrapper);
    }

    @Override
    public void logout(UserLoginVo userLoginVo) {
        // TODO 商家和用户的登出操作都还没有实现
    }

    @Override
    public Seller getInfo() {
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        return this.sellerDao.selectById(sellerId);
    }
}
