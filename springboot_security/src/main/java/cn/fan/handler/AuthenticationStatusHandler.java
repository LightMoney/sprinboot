//package cn.fan.handler;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.hthl.api.base.AdminControllerApi;
//import com.hthl.context.LoginContextHolder;
//import com.hthl.context.PrincipalContextHolder;
//import com.hthl.context.TokenContextHolder;
//import com.hthl.core.kafka.config.KafkaLoginLogConfig;
//import com.hthl.core.kafka.sender.KafkaSender;
//import com.hthl.core.properties.SercurityCoreProperties;
//import com.hthl.core.response.ResponseResult;
//import com.hthl.framework.domain.base.log.LogVo;
//import com.hthl.framework.system.OnlyIdPrefixConstant;
//import com.hthl.framework.util.CustomRequestUtils;
//import com.hthl.framework.utils.OnlyIdUtils;
//import com.hthl.service.BearerTokenObtain;
//import org.apache.commons.collections.MapUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
//import org.springframework.security.oauth2.provider.*;
//import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Date;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author zhijian
// * @date 2018/12/09   0009
// * @description 成功失败处理器
// */
//@Component
//public class AuthenticationStatusHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {
//    private static final Logger logger = LoggerFactory.getLogger(AuthenticationStatusHandler.class);
//
//    /**
//     * spring容器中的ObjectMapper
//     */
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Resource(name = "loginKafkaSender")
//    private KafkaSender kafkaSender;
//
//    @Autowired
//    private KafkaLoginLogConfig kafkaLoginLogConfig;
//    /**
//     * 属性类
//     */
//    @Autowired
//    private SercurityCoreProperties properties;
//
//    /**
//     * 注入获取client信息的service 此类由spring 容器管理
//     */
//    @Autowired(required = false)
//    private ClientDetailsService clientDetailsService;
//
//    /**
//     * 注入获取验证token的类
//     */
//    @Autowired(required = false)
//    private AuthorizationServerTokenServices authorizationServerTokenServices;
//
//    @Autowired
//    private AdminControllerApi adminControllerApi;
//
//    /**
//     * 登陆失败处理器
//     *
//     * @param httpServletRequest
//     * @param httpServletResponse
//     * @param e
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        logger.error("登录失败");
//        if (properties.getBrowser().getResultType().trim().equalsIgnoreCase(ResultType.JSON.toString())) {
//            // 失败了返回错误信息
//            httpServletResponse.setContentType("application/json;charset=utf-8");
//            String message = e.getMessage();
//            if (e instanceof BadCredentialsException) {
//                message = "密码错误！";
//            }
//            httpServletResponse.getWriter().println(objectMapper.writeValueAsString(new ResponseResult(false, message)));
//        } else {
//            // 调用默认实现
//            AuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();
//            authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
//        }
//    }
//
//    /**
//     * 退出成功处理器
//     *
//     * @param request
//     * @param response
//     * @param authentication
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        logger.error("退出成功");
//        pushLogoutLog(request, BearerTokenObtain.extract(request));
//        TokenContextHolder.clearContext();
//        response.setContentType("application/json;charset=utf-8");
//        PrintWriter writer = response.getWriter();
//        writer.write(objectMapper.writeValueAsString(new ResponseResult(true, "退出成功")));
//        writer.close();
//    }
//
//    /**
//     * 登陆成功处理器
//     *
//     * @param httpServletRequest
//     * @param httpServletResponse
//     * @param authentication
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//        // 处理需要token去访问请求的逻辑，求请头必须带着提供clientId和clientSecret的base64
//        String header = httpServletRequest.getHeader("Authorization");
//        try {
//            if (header == null || !header.toLowerCase().startsWith("basic ")) {
//                throw new MissingRequestHeaderException("missing clientId and clientSecret encoded header parameter");
//            }
//            String[] tokens = Basic64Decoder.extractAndDecodeHeader(header);
//            // 断言关键字，判断是否满足要求(默认没有开启断言  需要VM加入参数 -ea 或者 -enableassertions 开启)
//            assert tokens.length == 2;
//            String clientId = tokens[0];
//            String clientSecret = tokens[1];
//            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
//            if (clientDetails == null) {
//                throw new UnapprovedClientAuthenticationException("client_id \"" + clientId + " \" is not found about information");
//            } else if (!clientSecret.trim().equals(clientDetails.getClientSecret().trim())) {
//                throw new UnapprovedClientAuthenticationException("client_secret \"" + clientSecret + " \" is valid failed");
//            }
//            // Map<String, String> requestParameters, String clientId, Collection<String> scope,String grantType
//            TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
//            // 获取oauth2Request
//            OAuth2Request storedOAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
//            //获取
//            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(storedOAuth2Request, authentication);
//
//            // 获取token(默认是12小时过期，刷新token是30天)
//            OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
//            TokenContextHolder.setContext(accessToken.getValue());
//            pushLoginLog(httpServletRequest, accessToken.getValue());
//
//            // 创建一个map(返回到前端)
//            Map<String, Object> tokenMap = new ConcurrentHashMap<>(2);
//            tokenMap.put("access_token", accessToken.getValue());
//            tokenMap.put("refresh_token", accessToken.getRefreshToken().getValue());
//
//            // 添加cookie
//            Cookie cookie = new Cookie(BearerTokenExtractor.TOKEN_ACCESS_SIGN, accessToken.getValue());
//            cookie.setPath("/");
//            // 禁止js操作，防止XSS攻击
//            cookie.setHttpOnly(true);
//            cookie.setMaxAge(accessToken.getExpiresIn());
//            httpServletResponse.addCookie(cookie);
//
//            // 转成json返回信息
//            httpServletResponse.setContentType("application/json;charset=utf-8");
//            PrintWriter writer = httpServletResponse.getWriter();
//            writer.write(objectMapper.writeValueAsString(new ResponseResult(true, tokenMap)));
//            writer.close();
//            logger.info("登录成功");
//            //加载用户的组织缓存
//            sendHttpRequestLoadOrganize(LoginContextHolder.getCurUserId(), LoginContextHolder.getCurEnterPriseId());
//        } catch (IOException e) {
//            logger.error("登录异常！", e);
//            PrintWriter writer = httpServletResponse.getWriter();
//            writer.write(objectMapper.writeValueAsString(new ResponseResult(false, "登录认证异常！")));
//            writer.close();
//        }
//    }
//
//
//    private void sendHttpRequestLoadOrganize(Integer userId, Integer enterpriseId) {
//        new Thread(() -> {
//            adminControllerApi.loadUserOrganizeRedis(userId, enterpriseId);
//        }).start();
//    }
//
//    /**
//     * 推送登录日志
//     * @param httpServletRequest 请求
//     * @param token token
//     */
//    private void pushLoginLog(HttpServletRequest httpServletRequest, String token) {
//        Map<String, Object> principal = PrincipalContextHolder.getContextHolder().getContext();
//        LogVo logVo = new LogVo();
//        setBaseInfo(logVo, principal);
//        logVo.setVisitIp(CustomRequestUtils.getIpAddr(httpServletRequest));
//        logVo.setOperateEvent(httpServletRequest.getRequestURI());
//        logVo.setLogType(LogVo.LogType.LOGIN.getVal());
//        kafkaSender.sendData(kafkaLoginLogConfig.getTopic(), JSONObject.toJSONString(logVo));
//    }
//
//    /**
//     * 推送登录日志
//     * @param httpServletRequest 请求
//     * @param token token
//     */
//    private void pushLogoutLog(HttpServletRequest httpServletRequest, String token) {
//        TokenContextHolder.setContext(token);
//        Map<String, Object> principal = PrincipalContextHolder.getContextHolder().getContext();
//        LogVo logVo = new LogVo();
//        setBaseInfo(logVo, principal);
//        logVo.setVisitIp(CustomRequestUtils.getIpAddr(httpServletRequest));
//        logVo.setOperateEvent(httpServletRequest.getRequestURI());
//        logVo.setLogoutTime(new Date());
//        logVo.setLogType(LogVo.LogType.LOGOUT.getVal());
//        kafkaSender.sendData(kafkaLoginLogConfig.getTopic(), JSONObject.toJSONString(logVo));
//    }
//
//    /**
//     * 设置基本信息
//     */
//    private void setBaseInfo(LogVo logDomainVo, Map<String, Object> principal) {
//        if (MapUtils.isNotEmpty(principal)) {
//            //登录信息
//            logDomainVo.setLoginTime((Date)principal.get("loginTime"));
//            logDomainVo.setAdminId((Integer) principal.get("userId"));
//            logDomainVo.setAdminName((String)principal.get("adminName"));
//            logDomainVo.setAdminExternalId((String)principal.get("adminExternalId"));
//            logDomainVo.setLogStatus(LogVo.LogStatus.SUCCESS.getVal());
//        } else {
//            //异常状态的操作日志
//            logDomainVo.setLogStatus(LogVo.LogStatus.FAIL.getVal());
//        }
//        Date date = new Date();
//        logDomainVo.setOperateTime(date);
//        // id,code
//        logDomainVo.setLogId(System.currentTimeMillis() + "");
//        logDomainVo.setLogCode(OnlyIdUtils.generate(OnlyIdPrefixConstant.LOG_PREFIX));
//        //置空描述
//        logDomainVo.setDescription(null);
//    }
//}