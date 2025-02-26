package com.medicinal.mall.mall.demos.aop.aspect;

import com.medicinal.mall.mall.demos.aop.annotation.TokenVerify;
import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.exception.ParamException;
import com.medicinal.mall.mall.demos.token.TokenInfo;
import com.medicinal.mall.mall.demos.util.ParamAssert;
import com.medicinal.mall.mall.demos.util.TokenUtil;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


@Component
@Aspect
public class TokenVerifyAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(TokenVerifyAspect.class);

    @Pointcut("@annotation(com.medicinal.mall.mall.demos.aop.annotation.TokenVerify)")
    private void tokenPointcut(){}

    @Before(value = "tokenPointcut() && @annotation(tokenVerify)")
    public void tokenVerify(TokenVerify tokenVerify){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = Objects.requireNonNull(requestAttributes).getRequest();
        String token = servletRequest.getHeader("token");
        ParamAssert.notNull(token, ResponseDataEnum.TOKEN_NOT_EXIST);
        // 拦截非法token
        if (!TokenUtil.isValid(token)){
            throw new ParamException(ResponseDataEnum.INVALID_TOKEN);
        }
        TokenInfo tokenInfo = TokenUtil.getTokenInfo(token);
        LOGGER.info("token is {}",tokenInfo);
        // 鉴权
        checkRole(tokenInfo,tokenVerify);
        if (tokenVerify.isNeedInfo()){
            UserInfoThreadLocal.put(tokenInfo);
        }
    }


    @After(value = "tokenPointcut() && @annotation(tokenVerify)")
    public void tokenVerifyAfter(TokenVerify tokenVerify){
        if (tokenVerify.isNeedInfo()){
            // 对之前的ThreadLocal中的数据进行销毁，防止出现内存泄露
            UserInfoThreadLocal.remove();
        }
    }

    private void checkRole(TokenInfo tokenInfo,TokenVerify tokenVerify){
        for (RoleEnum roleEnum : tokenVerify.value()) {
            if (roleEnum.getRoleId().equals(tokenInfo.getRoleId())){
                return;
            }
        }
        throw new ParamException(ResponseDataEnum.NO_ACCESS);
    }

}
