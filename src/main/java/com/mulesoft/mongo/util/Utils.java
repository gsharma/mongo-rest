package com.mulesoft.mongo.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.codec.binary.Base64;

public final class Utils {
    public final static class StringUtils {
        public final static boolean isNullOrEmpty(String toCheck) {
            return toCheck == null || toCheck.trim().length() == 0;
        }

        public final static boolean validUrl(final String url, final boolean nonHttpOkay) {
            boolean valid = false;
            if (!isNullOrEmpty(url)) {
                String pattern = nonHttpOkay ? "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
                        : "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
                valid = Pattern.compile(pattern).matcher(url).matches();
            }
            return valid;
        }

        public static Date parseRfc2822DateFromString(final String rfcDate) throws ParseException {
            Date date = null;
            if (!Utils.StringUtils.isNullOrEmpty(rfcDate)) {
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
                date = formatter.parse(rfcDate);
            }
            return date;
        }

        public static String parseRfc2822DateToString(final Date rfcDate) {
            String date = null;
            if (rfcDate != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
                date = formatter.format(rfcDate);
            }
            return date;
        }
    }

    public final static class EncodingUtils {
        public final static String encodeBase64(String toEncode) {
            return encodeBase64(toEncode, false);
        }

        public final static String encodeBase64(String toEncode, boolean urlSafe) {
            String encoded = null;
            try {
                if (!StringUtils.isNullOrEmpty(toEncode)) {
                    if (urlSafe) {
                        encoded = Base64.encodeBase64URLSafeString(toEncode.trim().getBytes("UTF=8"));
                    } else {
                        encoded = Base64.encodeBase64String(toEncode.trim().getBytes("UTF-8"));
                    }
                }
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                // Stupid exception - low likelihood of getting hit by it
                throw new RuntimeException(unsupportedEncodingException);
            }
            return encoded;
        }

        public final static String decodeBase64(final String toDecode) {
            String decoded = null;
            if (!StringUtils.isNullOrEmpty(toDecode)) {
                decoded = new String(Base64.decodeBase64(toDecode));
            }
            return decoded;
        }
    }

    public final static class PropertyUtils {
        public final static String readArtifactProperties() {
            StringBuilder builder = new StringBuilder("mongo-rest artifact properties:\n");
            try {
                Enumeration<URL> urls = Utils.class.getClassLoader().getResources("mongo-rest-scm.properties");
                while (urls.hasMoreElements()) {
                    Properties props = new Properties();
                    props.load(urls.nextElement().openStream());
                    builder.append(String.format("  %s:%s:%s\n    SHA:%s\n    %s@%s\n    %s\n",
                            props.getProperty("maven.groupId", "<NA>"), props.getProperty("maven.artifactId", "<NA>"),
                            props.getProperty("maven.version", "<NA>"), props.getProperty("gitRevision", "<NA>"),
                            props.getProperty("builtBy", "<NA>"), props.getProperty("builtOn", "<NA>"),
                            props.getProperty("builtAt", "<NA>")));
                }
            } catch (IOException exception) { // annoyance
            }
            return builder.toString();
        }
    }

    public final static class CollectionUtils {
        public final static Map<String, String> multiValuedMapToMap(final MultivaluedMap<String, String> toNormalize) {
            Map<String, String> normalized = null;
            if (toNormalize != null) {
                normalized = new HashMap<String, String>(toNormalize.size());
                String key = null;
                List<String> value = null;
                for (Entry<String, List<String>> entry : toNormalize.entrySet()) {
                    key = entry.getKey();
                    value = entry.getValue();
                    if (!StringUtils.isNullOrEmpty(key)) {
                        normalized.put(key.trim(), listToString(value));
                    }
                }
            }
            return normalized;
        }

        public final static void mapToMultiValuedMap(final Map<String, String> toNormalize,
                final MultivaluedMap<String, Object> normalized) {
            if (toNormalize != null && normalized != null) {
                String key = null;
                String value = null;
                for (Entry<String, String> entry : toNormalize.entrySet()) {
                    key = entry.getKey();
                    value = entry.getValue();
                    if (!StringUtils.isNullOrEmpty(key)) {
                        normalized.add(key.trim(), value.trim());
                    }
                }
            }
        }

        @SuppressWarnings("rawtypes")
        public final static String listToString(final List list) {
            StringBuilder listToString = new StringBuilder();
            if (list != null && !list.isEmpty()) {
                for (int iter = 0; iter < list.size(); iter++) {
                    Object entry = list.get(iter);
                    if (entry != null) {
                        if (!StringUtils.isNullOrEmpty(entry.toString())) {
                            listToString.append(entry.toString());
                            if (iter != list.size() - 1) {
                                listToString.append(",");
                            }
                        }
                    }
                }
            }
            return listToString.toString();
        }

        @SuppressWarnings("rawtypes")
        public final static String mapToString(final Map map) {
            StringBuilder mapString = new StringBuilder();
            if (map != null && !map.isEmpty()) {
                Map.Entry entry = null;
                int counter = 0;
                for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
                    entry = (Map.Entry) iter.next();
                    if (entry != null) {
                        mapString.append(entry.getKey()).append("=").append(entry.getValue());
                        if (counter != map.size() - 1) {
                            mapString.append(",");
                        }
                    }
                    counter++;
                }
            }
            return mapString.toString();
        }

        public final static Map<String, String> stringifyMapEntries(Map<Object, Object> map) {
            Map<String, String> stringified = new LinkedHashMap<String, String>(map.size());
            if (map != null && !map.isEmpty()) {
                for (Map.Entry<Object, Object> entry : map.entrySet()) {
                    stringified.put(entry.getKey().toString(), entry.getValue().toString());
                }
            }
            return stringified;
        }
    }
}
