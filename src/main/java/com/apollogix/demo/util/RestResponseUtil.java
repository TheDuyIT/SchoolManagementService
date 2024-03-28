package com.apollogix.demo.util;

import com.apollogix.web.rest.model.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface RestResponseUtil {
    static <X> ResponseEntity<X> ok(X payload) {
        return ResponseEntity.ok(payload);
    }

    static <T> Pagination fromPage(Page<T> page) {
        return new Pagination()
                .totalItem(page.getTotalElements())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize());
    }
}
