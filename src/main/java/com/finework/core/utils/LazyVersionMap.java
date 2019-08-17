package com.finework.core.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.Logger;

public class LazyVersionMap
  implements Map<String, String>
{
  private static final Logger LOG = Logger.getLogger(LazyVersionMap.class);
  private final ArtifactVersionResolver resolver;
  private final Map<String, String> cache = new HashMap();

  public LazyVersionMap(ArtifactVersionResolver resolver) {
    this.resolver = resolver;
  }

  public String get(Object key)
  {
    if (key == null) {
      return null;
    }

    String groupAndArtifact = key.toString();

    if (this.cache.containsKey(groupAndArtifact)) {
      String cachedVersion = (String)this.cache.get(groupAndArtifact);
      return cachedVersion;
    }

    String version = this.resolver.resolveVersion(groupAndArtifact);
    this.cache.put(groupAndArtifact, version);
    return version;
  }

  public void clear()
  {
    throw new UnsupportedOperationException();
  }

  public boolean containsKey(Object key)
  {
    return get(key) != null;
  }

  public boolean containsValue(Object value)
  {
    return this.cache.containsValue(value);
  }

  public Set<Map.Entry<String, String>> entrySet()
  {
    return this.cache.entrySet();
  }

  public boolean isEmpty()
  {
    return this.cache.isEmpty();
  }

  public Set<String> keySet()
  {
    return this.cache.keySet();
  }

  public String put(String key, String value)
  {
    throw new UnsupportedOperationException();
  }

  public void putAll(Map<? extends String, ? extends String> m)
  {
    throw new UnsupportedOperationException();
  }

  public String remove(Object key)
  {
    throw new UnsupportedOperationException();
  }

  public int size()
  {
    return this.cache.size();
  }

  public Collection<String> values()
  {
    return this.cache.values();
  }
}