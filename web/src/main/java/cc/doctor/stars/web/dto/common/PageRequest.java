package cc.doctor.stars.web.dto.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PageRequest<T> {

    @NotNull(message = "分页参数不能为空")
    @Min(value = 1, message = "分页参数不合法")
    private Integer pageNo;
    @NotNull(message = "分页参数不能为空")
    @Min(value = 1, message = "分页参数不合法")
    private Integer pageSize;
    private T data;

    public int offset() {
        pageNo = (pageNo == null || pageNo <= 0) ? 1 : pageNo;
        return (pageNo - 1) * limit();
    }

    public int limit() {
        pageSize = pageSize == null || pageSize <= 0 ? 10 : pageSize;
        return pageSize;
    }

    public Page<?> toPage() {
        return new Page<>(offset(), limit());
    }
}
