package cc.doctor.stars.biz.store;

import cc.doctor.stars.biz.enums.StoreTypeEnum;
import cc.doctor.stars.biz.model.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Loader {
    StoreTypeEnum type();
    void upload(File file, InputStream stream) throws IOException;
    OutputStream createOutputStream(File file) throws IOException;
    InputStream load(File file) throws IOException;
    String createUrl(File file);
}
