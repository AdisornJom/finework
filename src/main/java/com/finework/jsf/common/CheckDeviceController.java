package com.finework.jsf.common;

import com.finework.core.util.DeviceUtil;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aekasit
 */
@RequestScoped
@Named(CheckDeviceController.CONTROLLER_NAME)
public class CheckDeviceController extends BaseController {

    public static final String CONTROLLER_NAME = "checkDeviceController";

    @Override
    public void init() {
        String isDevice = DeviceUtil.isDevice((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
        if (DeviceUtil.MOBILE.equals(isDevice)) {
//            try {
//                FacesContext.getCurrentInstance().getExternalContext().redirect("mobile_login.xhtml");
////                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("mobile/login.xhtml");
//            } catch (IOException ex) {
//            }
        }
    }
}
