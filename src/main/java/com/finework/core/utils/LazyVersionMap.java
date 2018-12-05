package com.finework.core.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

public class LazyVersionMap implements Map<String, String> {

    private static final Logger LOG = Logger.getLogger(LazyVersionMap.class);

    private final ArtifactVersionResolver resolver;

    private final Map<String, String> cache = new HashMap<String, String>();

    public LazyVersionMap(ArtifactVersionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public String get(Object key) {

        // check for null argument
        if (key == null) {
            return null;
        }

        // the key is always a string
        String groupAndArtifact = key.toString();

        // check for an existing result
        if (cache.containsKey(groupAndArtifact)) {
            String cachedVersion = cache.get(groupAndArtifact);
            return cachedVersion;
        }

        // resolve and cache result
        String version = resolver.resolveVersion(groupAndArtifact);
        cache.put(groupAndArtifact, version);
        return version;

    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return cache.containsValue(value);
    }

    @Override
    public Set<java.util.Map.Entry<String, String>> entrySet() {
        return cache.entrySet();
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return cache.keySet();
    }

    @Override
    public String put(String key, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public Collection<String> values() {
        return cache.values();
    }

}
