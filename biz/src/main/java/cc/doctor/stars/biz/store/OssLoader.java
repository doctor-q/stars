package cc.doctor.stars.biz.store;

import cc.doctor.stars.biz.enums.StoreTypeEnum;
import cc.doctor.stars.biz.model.File;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ali oss
 */
@Service
public class OssLoader implements Loader {

    @PostConstruct
    public void init() {
        StoreFactory.addLoader(type(), this);
    }

    @Override
    public StoreTypeEnum type() {
        return StoreTypeEnum.OSS;
    }

    @Override
    public void upload(File file, InputStream stream) throws IOException {

    }

    @Override
    public OutputStream createOutputStream(File file) throws FileNotFoundException {
        return null;
    }

    @Override
    public InputStream load(File file) throws FileNotFoundException, IOException {
        return null;
    }

    @Override
    public String createUrl(File file) {
        return null;
    }
}
