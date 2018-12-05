package com.finework.core.utils;

import java.io.Serializable;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * @author MR Aekasit Sengnualnim (Aek) System Analyst
 *
 * Progress Software Co.,Ltd Fl. 6-7 306 Supha Road, Phomphlab
 * Phomphlabsattupai, Bangkok 10100 Thailand Tel :+66 (0) 2867 5020 Mobile : +66
 * 91004 1009 Skype : s.aekasit MSN : aekasit.se@gmail.com Email :
 * aekasit.s@kasikornbank.com Website http://www.kasikornbank.com
 *
 * @create 30-01-2015 2:44:36 PM
 */
@ManagedBean(name = "maven")
@ApplicationScoped
public class JSFMavenBean implements Serializable {

    private final Map<String, String> version = new LazyVersionMap(new ArtifactVersionResolver());

    public Map<String, String> getVersion() {
        return version;
    }

}
