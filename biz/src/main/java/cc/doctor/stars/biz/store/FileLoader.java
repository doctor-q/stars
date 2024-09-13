package cc.doctor.stars.biz.store;

import cc.doctor.stars.biz.enums.StoreTypeEnum;
import cc.doctor.stars.biz.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

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
    public String upload(InputStream stream) throws IOException {
        byte[] bytes = StreamUtils.copyToByteArray(stream);
        String uuid = UUID.randomUUID().toString();
        Files.write(Paths.get(storeProperties.getFileRoot(), uuid), bytes);
        return uuid;
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
