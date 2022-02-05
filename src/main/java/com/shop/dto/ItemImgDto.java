package com.shop.dto;

import com.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDto { //상품 저장 후 상품 이미지에 대한 데이터를 전달할 DTO

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg){ //ItemImg 객체의 자료형과 멤버변수의 이름이 같을 때 Dto로 값을 복사해서 반환
        return modelMapper.map(itemImg, ItemImgDto.class);
    }

}
