package cc.doctor.stars.web.service;

import cc.doctor.stars.biz.mapper.RsCollectMapper;
import cc.doctor.stars.biz.mapper.RsHistoryMapper;
import cc.doctor.stars.web.dto.RsCollectRequest;
import cc.doctor.stars.web.dto.RsDetailResponse;
import cc.doctor.stars.web.dto.common.PageRequest;
import cc.doctor.stars.web.dto.common.PageResponse;
import cc.doctor.stars.web.dto.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RsService {

    @Autowired
    private RsCollectMapper rsCollectMapper;

    @Autowired
    private RsHistoryMapper rsHistoryMapper;

    public Response<?> collectRs(RsCollectRequest request) {
        return null;
    }

    public PageResponse<?> pageCollectRs(PageRequest<?> request) {
        return null;
    }

    public PageResponse<?> pageRsHistory(PageRequest<?> request) {
        return null;
    }

    public Response<RsDetailResponse> rsDetail(Integer resId) {
        return null;
    }
}
