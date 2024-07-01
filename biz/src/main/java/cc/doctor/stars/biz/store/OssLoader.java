package cc.doctor.stars.biz.store;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * ali oss
 */
public class OssLoader implements Loader {
    @Override
    public void upload(InputStream stream) {

    }

    @Override
    public OutputStream download(String uri) {
        return null;
    }
}
