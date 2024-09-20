package cc.doctor.stars.crawler.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskStatus {
    NEW(0),
    RUNNING(1),
    SUCCESS(2),
    FAILED(3);

    private final int status;
}
