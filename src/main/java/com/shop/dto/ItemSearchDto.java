package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {

    private String searchDateType; //상품 등록일을 비교

    private ItemSellStatus searchSellStatus; // 판매상태

    private String searchBy; //상품명, 상품 등록자 아이디 중 조회 유형

    private String searchQuery = ""; // 검색어
}
