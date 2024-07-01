package cc.doctor.stars.biz.store;

import java.io.InputStream;
import java.io.OutputStream;

public interface Loader {
    void upload(InputStream stream);
    OutputStream download(String uri);
}
