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

    public static <D> PageRequest<D> pageRequest(int pageNo, int pageSize) {
        PageRequest<D> request = new PageRequest<>();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        return request;
    }

    public static <D> PageRequest<D> pageRequest(int pageNo, int pageSize, D data) {
        PageRequest<D> request = new PageRequest<>();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        request.setData(data);
        return request;
    }

    public int offset() {
        pageNo = (pageNo == null || pageNo <= 0) ? 1 : pageNo;
        return (pageNo - 1) * limit();
    }

    public int limit() {
        pageSize = pageSize == null || pageSize <= 0 ? 10 : pageSize;
        return pageSize;
    }

    public <D> Page<D> toPage() {
        return new Page<>(offset(), limit());
    }
}
