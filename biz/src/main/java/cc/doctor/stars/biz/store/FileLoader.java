package cc.doctor.stars.biz.store;

import cc.doctor.stars.biz.enums.StoreTypeEnum;
import cc.doctor.stars.biz.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileLoader implements Loader {

    @Autowired
    private StoreProperties storeProperties;

    @PostConstruct
    public void init() {
        StoreFactory.addLoader(type(), this);
        if (storeProperties.getFileRoot() == null) {
            storeProperties.setFileRoot("/tmp");
        }
    }

    @Override
    public StoreTypeEnum type() {
        return StoreTypeEnum.FILE;
    }

    @Override
    public void upload(File file, InputStream stream) throws IOException {
        byte[] bytes = StreamUtils.copyToByteArray(stream);
        Files.write(Paths.get(storeProperties.getFileRoot(), file.getPath()), bytes);
    }

    @Override
    public OutputStream createOutputStream(File file) throws IOException {
        java.io.File f = new java.io.File(storeProperties.getFileRoot(), file.getPath());
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        return new FileOutputStream(f);
    }

    @Override
    public InputStream load(File file) throws IOException {
        java.io.File f = new java.io.File(storeProperties.getFileRoot(), file.getPath());
        return new FileInputStream(f);
    }

    @Override
    public String createUrl(File file) {
        return storeProperties.getFileDownloadRoot() + file.getId();
    }
}
