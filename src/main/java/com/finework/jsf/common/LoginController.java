package com.finework.jsf.common;

import com.finework.ejb.facade.CoreFacade;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import java.io.IOException;
import java.security.Principal;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author MR Aekasit Sengnualnim (Aek) System Analyst
 *
 * Progress Software Co.,Ltd Fl. 6-7 306 Supha Road, Phomphlab
 * Phomphlabsattupai, Bangkok 10100 Thailand Tel :+66 (0) 2867 5020 Mobile : +66
 * 91004 1009 Skype : s.aekasit MSN : aekasit.se@gmail.com Email :
 * aekasit.s@kasikornbank.com Website http://www.kasikornbank.com
 *
 * @create 11-11-2014 3:58:48 PM
 */
@RequestScoped
@ManagedBean(name = LoginController.CONTROLLER_NAME)
public class LoginController extends BaseController {

    private static final Logger LOG = Logger.getLogger(LoginController.class);
    public static final String CONTROLLER_NAME = "loginController";
    private String username;
    private String password;
    private String captchaCode;
    //
    @ManagedProperty(value = "#{authenticationManager}")
    private AuthenticationManager authenticationManager;
    @Inject
    private LogController log;
    //

    @Inject
    private CoreFacade coreFacade;

    @PostConstruct
    @Override
    public void init() {
       
    }

    public void login() {
        try {

            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            if (FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() != null) {
                context.redirect(context.getRequestContextPath());
                FacesContext.getCurrentInstance().responseComplete();
            }

            if (StringUtils.isBlank(username)) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "บัญชีผู้ใช้งาน"));
                return;
            }
            if (StringUtils.isBlank(password)) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รหัสผ่าน"));
                return;
            }
//            username="admin";
//            password="1234";
           
            Authentication request = new UsernamePasswordAuthenticationToken(username, password);
            Authentication result = authenticationManager.authenticate(request);
            SecurityContext secu = SecurityContextHolder.getContext();
            secu.setAuthentication(result);

            UserInfoController userInfoController = (UserInfoController) JsfUtil.getManagedBeanValue(UserInfoController.CONTROLLER_NAME);
            userInfoController.init();

            log.login();
            FacesContext.getCurrentInstance().responseComplete();
            context.redirect(context.getRequestContextPath());

        } catch (IOException ex) {
            JsfUtil.addFacesErrorMessage(ex.getMessage());
            LOG.error(ex);
        } catch (AuthenticationException ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.1001"));
            LOG.error(ex);
        } catch (Exception ex) {
            LOG.error(ex);
        }

    }

    public void isLogin() {
        try {

            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

            if (principal != null) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(context.getRequestContextPath());
                FacesContext.getCurrentInstance().responseComplete();
            }

        } catch (IOException ex) {
            LOG.error(ex);
        }
    }

    public void logout() throws IOException {
        this.username = null;
        this.password = null;

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + "/j_spring_security_logout");
        FacesContext.getCurrentInstance().responseComplete();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }



}
