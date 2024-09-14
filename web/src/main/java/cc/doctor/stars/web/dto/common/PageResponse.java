package cc.doctor.stars.web.dto.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageResponse<T> extends Response<List<T>> {
    private Integer pageNo;
    private Integer pageSize;
    private Integer total;
    private Integer pages;

    public static <D> PageResponse<D> pageResponse(IPage<?> page, List<D> data) {
        PageResponse<D> pageResponse = new PageResponse<>();
        pageResponse.setCode(Response.SUCCESS_CODE);
        pageResponse.setSuccess(true);
        pageResponse.setData(data);
        pageResponse.setPageNo((int) page.getCurrent());
        pageResponse.setPageSize((int) page.getSize());
        pageResponse.setTotal((int) page.getTotal());
        pageResponse.setPages((int) page.getPages());
        return pageResponse;
    }

    public static <D> PageResponse<D> pageResponse(IPage<?> page) {
        PageResponse<D> pageResponse = new PageResponse<>();
        pageResponse.setCode(Response.SUCCESS_CODE);
        pageResponse.setSuccess(true);
        pageResponse.setPageNo((int) page.getCurrent());
        pageResponse.setPageSize((int) page.getSize());
        pageResponse.setTotal((int) page.getTotal());
        pageResponse.setPages((int) page.getPages());
        return pageResponse;
    }
}
