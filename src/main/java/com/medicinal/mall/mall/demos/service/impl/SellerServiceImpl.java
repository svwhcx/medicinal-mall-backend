package com.medicinal.mall.mall.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.medicinal.mall.mall.demos.command.FindPasswordCmd;
import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.dao.SellerDao;
import com.medicinal.mall.mall.demos.entity.Seller;
import com.medicinal.mall.mall.demos.entity.User;
import com.medicinal.mall.mall.demos.exception.ParamException;
import com.medicinal.mall.mall.demos.exception.UserLogFail;
import com.medicinal.mall.mall.demos.query.UserRegistryRequest;
import com.medicinal.mall.mall.demos.query.VerifyCodeRequest;
import com.medicinal.mall.mall.demos.service.SellerService;
import com.medicinal.mall.mall.demos.util.PasswordUtils;
import com.medicinal.mall.mall.demos.verifycode.IVerifyCode;
import com.medicinal.mall.mall.demos.verifycode.VerifyCodeConstant;
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
    private IVerifyCode iVerifyCode;

    @Override
    public UserLoginVo userLogin(User user) {
        // 先对密码进行加密验证
        user.setPassword(PasswordUtils.encryption(user.getPassword()));
        // 使用LambdaQueryWrapper来进行User实体类的账号和密码查询
        // 获取数据库中的用户信息
        Seller sellerInfo = sellerDao.selectOne(new LambdaQueryWrapper<Seller>().select(Seller::getUsername,Seller::getPassword));
        if (sellerInfo == null){
            throw new UserLogFail(ResponseDataEnum.LOGIN_FAIL);
        }
        // 如果用户登录成功则进行token的构建。
        return new UserLoginVo();
    }

    @Override
    public void register(UserRegistryRequest userRegistryRequest) {
        // 然后验证邮箱是否已经被占用了
        if (sellerDao.selectOne(new LambdaQueryWrapper<Seller>().eq(Seller::getEmail,userRegistryRequest.getEmail())) != null){
            throw new ParamException(ResponseDataEnum.EMAIL_EXITS);
        }
        // 如果没有注册则验证验证码是否正确
        Boolean verifyRes = iVerifyCode.checkVerifyCode(new VerifyCodeRequest(userRegistryRequest.getEmail(),userRegistryRequest.getVerifyCode(), VerifyCodeConstant.EMAIL_VERIFY));
        if (!verifyRes){
            throw new ParamException(ResponseDataEnum.VERIFICATION_ERROR);
        }
        // 用户的注册操作，先验证是否已经有用户注册该账号了
        if (sellerDao.selectOne(new LambdaQueryWrapper<Seller>().eq(Seller::getUsername,userRegistryRequest.getUsername())) != null){
            throw new UserLogFail(ResponseDataEnum.USERNAME_EXIST);
        }

        // 然后用户注册时的密码强度的一个校验部分
        if (!PasswordUtils.isStrong(userRegistryRequest.getPassword())){
            throw new UserLogFail(ResponseDataEnum.PASSWORD_NOT_STRONG);
        }
        // 都通过了，使用加密手段对用户的密码进行加密存储.
        Seller seller = new Seller();
        seller.setUsername(userRegistryRequest.getUsername());
        seller.setPassword(PasswordUtils.encryption(userRegistryRequest.getPassword()));
        seller.setEmail(userRegistryRequest.getEmail());
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
        VerifyCodeRequest verifyCodeRequest = new VerifyCodeRequest(findPasswordCmd.getEmail(),findPasswordCmd.getCode(), VerifyCodeConstant.EMAIL_VERIFY);
        if (!iVerifyCode.checkVerifyCode(verifyCodeRequest)){
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
        // TODO 商家和用户的登录操作都还没有实现
    }

    @Override
    public Seller getInfo() {
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        return this.sellerDao.selectById(sellerId);
    }
}
