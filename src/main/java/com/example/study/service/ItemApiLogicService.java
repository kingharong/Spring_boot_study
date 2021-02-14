package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.Item;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.PartnerRepository;
import com.fasterxml.jackson.databind.ser.Serializers;
import jdk.javadoc.internal.doclets.formats.html.markup.Head;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemApiLogicService extends BaseService<ItemApiRequest, ItemApiResponse, Item> {
    private final PartnerRepository partnerRepository;



    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {
        ItemApiRequest body = request.getData();
        try {
            Item item = Item.builder()
                    .status(body.getStatus())
                    .name(body.getName())
                    .title(body.getTitle())
                    .content(body.getContent())
                    .price(body.getPrice())
                    .brandName(body.getBrandName())
                    .registeredAt(LocalDateTime.now())
                    .partner(partnerRepository.getOne(body.getPartnerId()))
                    .build();
            Item newItem = baseRepository.save(item);
            return response(newItem);
        }catch (Exception e){
            return Header.ERROR("오류");
        }
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(item -> response(item))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        ItemApiRequest body= request.getData();
        return baseRepository.findById(body.getId())
                .map(item -> {
                    item.setName(body.getName())
                            .setStatus(body.getStatus())
                            .setPrice(body.getPrice())
                            .setPartner(partnerRepository.getOne(body.getPartnerId()))
                            .setContent(body.getContent())
                            .setRegisteredAt(body.getRegisteredAt())
                            .setBrandName(body.getBrandName())
                            .setTitle(body.getTitle())
                            .setStatus(body.getStatus())
                            .setUnregisteredAt(body.getUnregisteredAt());
                    return item;
                })
                .map(newItem->(baseRepository.save(newItem)))
                        .map(item ->response(item))
                .orElseGet(()->Header.ERROR("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {
        baseRepository.findById(id)
                .map(item-> {baseRepository.delete(item);
                return Header.OK();})
                .orElseGet(()->Header.ERROR("데이터 없음"));
        return null;
    }

    @Override
    public Header<List<ItemApiResponse>> search(Pageable pageable) {
        return null;
    }

    public Header<ItemApiResponse> response(Item item){
        ItemApiResponse body = ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .registeredAt(item.getRegisteredAt())
                .unregisteredAt(item.getUnregisteredAt())
                .partnerId(item.getPartner().getId())
                .build();

        return Header.OK(body);
    }



}
