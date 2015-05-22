/*
 * CODENVY CONFIDENTIAL
 * __________________
 *
 *  [2012] - [2015] Codenvy, S.A.
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.im.managers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nullable;
import javax.inject.Named;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;

/**
 * @author Anatoliy Bazko
 */
@Singleton
public class StorageManager {

    public static final String STORAGE_FILE_NAME = "config.properties";
    private final Path storageFile;

    @Inject
    public StorageManager(@Named("installation-manager.storage_dir") String storageDir) {
        this.storageFile = Paths.get(storageDir).resolve(STORAGE_FILE_NAME);
    }

    /**
     * Stores installation related properties.
     *
     * @param newProperties
     *         properties to store
     * @throws java.io.IOException
     *         if any I/O error occurred
     */
    public void storeProperties(@Nullable Map<String, String> newProperties) throws IOException {
        if (newProperties != null && !newProperties.isEmpty()) {
            Path storageFile = getStorageFile();
            Properties properties = loadProperties(storageFile);

            for (Map.Entry<String, String> entry : newProperties.entrySet()) {
                properties.put(entry.getKey(), entry.getValue());
            }

            try (OutputStream out = new BufferedOutputStream(newOutputStream(storageFile))) {
                properties.store(out, null);
            }
        }
    }

    /**
     * Loads installation related properties.
     * If there is no stored specific property then no value will be returned.
     *
     * @param names
     *         properties names to load
     * @throws IOException
     *         if any I/O error occurred
     */
    public Map<String, String> loadProperties(@Nullable final Collection<String> names) throws IOException {
        if (names == null || names.isEmpty()) {
            return Collections.emptyMap();
        }

        Path storageFile = getStorageFile();
        Properties properties = loadProperties(storageFile);

        Map<String, String> result = new HashMap<>((Map)properties);

        Iterator<Map.Entry<String, String>> iter = result.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            if (!names.contains(entry.getKey())) {
                iter.remove();
            }
        }

        return result;
    }

    private Properties loadProperties(Path propertiesFile) throws IOException {
        Properties properties = new Properties();

        if (exists(propertiesFile)) {
            try (InputStream in = new BufferedInputStream(newInputStream(propertiesFile))) {
                properties.load(in);
            }
        }
        return properties;
    }

    private Path getStorageFile() throws IOException {
        if (!exists(storageFile.getParent())) {
            createDirectories(storageFile.getParent());
        }

        return storageFile;
    }

}
