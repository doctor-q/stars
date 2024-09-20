package cc.doctor.stars.biz.store;

import cc.doctor.stars.biz.enums.StoreTypeEnum;
import cc.doctor.stars.biz.model.File;
import cc.doctor.stars.biz.utils.SpringBeanUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StoreFactory {

    private static Map<StoreTypeEnum, Loader> loaders = new ConcurrentHashMap<>();

    public static void addLoader(StoreTypeEnum storeType, Loader loader) {
        loaders.put(storeType, loader);
    }

    private static Loader getLoader(Integer storeType) {
        if (storeType == null) {
            return null;
        }
        StoreTypeEnum storeTypeEnum = StoreTypeEnum.getByType(storeType);
        if (storeTypeEnum != null) {
            return loaders.get(storeTypeEnum);
        }
        return null;
    }

    public static void upload(File file, InputStream stream) throws IOException {
        Loader loader = getLoader(file.getStoreType());
        if (loader != null) {
            loader.upload(file, stream);
        }
    }

    public static InputStream load(File file) throws IOException {
        Loader loader = getLoader(file.getStoreType());
        return loader == null ? null : loader.load(file);
    }

    public static OutputStream createOutputStream(File file) throws IOException {
        Loader loader = getLoader(file.getStoreType());
        return loader == null ? null : loader.createOutputStream(file);
    }

    public static String createUrl(File file) {
        Loader loader = getLoader(file.getStoreType());
        return loader == null ? null : loader.createUrl(file);
    }

    public static Integer defaultStoreType() {
        StoreProperties storeProperties = SpringBeanUtils.getBean(StoreProperties.class);
        if (storeProperties.getType() == null) {
            return StoreTypeEnum.FILE.getType();
        } else {
            return storeProperties.getType().getType();
        }
    }
}
