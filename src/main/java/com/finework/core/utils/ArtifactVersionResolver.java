package com.finework.core.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;

public class ArtifactVersionResolver {

    private static final Logger LOG = Logger.getLogger(ArtifactVersionResolver.class);
    private final static Pattern GROUP_AND_ARTIFACT = Pattern.compile("^([\\w\\.\\-]+):([\\w\\.\\-]+)$");

    private final static String CLASSPATH_RESOURCE = "META-INF/maven/{0}/{1}/pom.properties";

    private final static String WEBAPP_RESOURCE = "/META-INF/maven/{0}/{1}/pom.properties";

    public String resolveVersion(String groupAndArtifact) {

        // match against required pattern
        Matcher matcher = GROUP_AND_ARTIFACT.matcher(
                groupAndArtifact != null ? groupAndArtifact.trim() : "");

        // no match? abort!
        if (!matcher.matches()) {
            LOG.warn("Invalid artifact identifier: " + groupAndArtifact);
            return null;
        }

        // get group and artifact from pattern
        String groupId = matcher.group(1);
        String artifactId = matcher.group(2);

        // try to load properties from classpath
        URL propertiesFile = getPropertiesFileFromClassPath(groupId, artifactId);

        // not found? try it via the servlet context
        if (propertiesFile == null) {
            propertiesFile = getPropertiesFileFromWebappRoot(groupId, artifactId);
        }

        // if we have found anything, resolve the version
        if (propertiesFile != null) {
            LOG.warn("Found project properties: " + propertiesFile);
            return getVersionFromProjectProperties(propertiesFile);
        }

        // nothing found
        LOG.warn("No project properties found for: " + groupAndArtifact);
        return null;

    }

    private URL getPropertiesFileFromClassPath(String groupId, String artifactId) {

        // get the context class loader
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        // fall back to class loader of the current class
        if (cl == null) {
            cl = this.getClass().getClassLoader();
        }

        // try to load project properties
        String path = MessageFormat.format(CLASSPATH_RESOURCE, groupId, artifactId);
        return cl.getResource(path);

    }

    private URL getPropertiesFileFromWebappRoot(String groupId, String artifactId) {

        // try to get FacesContext
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) {
            return null;
        }

        // is it a ServletContext?
        Object externalContext = facesContext.getExternalContext().getContext();
        if (externalContext instanceof ServletContext) {

            // cast to ServletContext
            ServletContext servletContext = (ServletContext) externalContext;

            try {

                // try to get project properties
                String path = MessageFormat.format(WEBAPP_RESOURCE, groupId, artifactId);
                return servletContext.getResource(path);

            } catch (MalformedURLException e) {
                LOG.warn("No project properties found for: " + e.getMessage());
            }

        }

        return null;
    }

    private String getVersionFromProjectProperties(URL url) {

        try {

            // create properties and load values from URL
            Properties props = new Properties();
            props.load(url.openStream());

            // get version from properties
            String version = props.getProperty("version");
            LOG.warn("version: " + version);
            return version;

        } catch (IOException e) {
            LOG.warn("IOException : " + e.getMessage());
            return null;
        }
    }

}
