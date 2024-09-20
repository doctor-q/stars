package cc.doctor.stars.biz.store;

import cc.doctor.stars.biz.enums.StoreTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "store")
@Component
@Data
public class StoreProperties {
    private StoreTypeEnum type;
    private String fileRoot;
    private String fileDownloadRoot;

}
