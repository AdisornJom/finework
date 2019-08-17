package com.finework.core.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;

public class ArtifactVersionResolver
{
  private static final Logger LOG = Logger.getLogger(ArtifactVersionResolver.class);
  private static final Pattern GROUP_AND_ARTIFACT = Pattern.compile("^([\\w\\.\\-]+):([\\w\\.\\-]+)$");
  private static final String CLASSPATH_RESOURCE = "META-INF/maven/{0}/{1}/pom.properties";
  private static final String WEBAPP_RESOURCE = "/META-INF/maven/{0}/{1}/pom.properties";

  public String resolveVersion(String groupAndArtifact)
  {
    Matcher matcher = GROUP_AND_ARTIFACT.matcher(groupAndArtifact != null ? groupAndArtifact
      .trim() : "");

    if (!matcher.matches()) {
      LOG.warn("Invalid artifact identifier: " + groupAndArtifact);
      return null;
    }

    String groupId = matcher.group(1);
    String artifactId = matcher.group(2);

    URL propertiesFile = getPropertiesFileFromClassPath(groupId, artifactId);

    if (propertiesFile == null) {
      propertiesFile = getPropertiesFileFromWebappRoot(groupId, artifactId);
    }

    if (propertiesFile != null) {
      LOG.warn("Found project properties: " + propertiesFile);
      return getVersionFromProjectProperties(propertiesFile);
    }

    LOG.warn("No project properties found for: " + groupAndArtifact);
    return null;
  }

  private URL getPropertiesFileFromClassPath(String groupId, String artifactId)
  {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();

    if (cl == null) {
      cl = getClass().getClassLoader();
    }

    String path = MessageFormat.format("META-INF/maven/{0}/{1}/pom.properties", new Object[] { groupId, artifactId });
    return cl.getResource(path);
  }

  private URL getPropertiesFileFromWebappRoot(String groupId, String artifactId)
  {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    if (facesContext == null) {
      return null;
    }

    Object externalContext = facesContext.getExternalContext().getContext();
    if ((externalContext instanceof ServletContext))
    {
      ServletContext servletContext = (ServletContext)externalContext;
      try
      {
        String path = MessageFormat.format("/META-INF/maven/{0}/{1}/pom.properties", new Object[] { groupId, artifactId });
        return servletContext.getResource(path);
      }
      catch (MalformedURLException e) {
        LOG.warn("No project properties found for: " + e.getMessage());
      }

    }

    return null;
  }

  private String getVersionFromProjectProperties(URL url)
  {
    try
    {
      Properties props = new Properties();
      props.load(url.openStream());

      String version = props.getProperty("version");
      LOG.warn("version: " + version);
      return version;
    }
    catch (IOException e) {
      LOG.warn("IOException : " + e.getMessage());
    }return null;
  }
}