package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {
    ALL(0,"묶음","모든 상품 묶음 발송"),
    EACH(1, "개별","개별 배송");

    private Integer id;
    private String title;
    private String description;
}
