package com.example.study.service;

import com.example.study.model.entity.Partner;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.PartnerApiRequest;
import com.example.study.model.network.response.PartnerApiResponse;
import com.example.study.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartnerApiLogicService extends BaseService<PartnerApiRequest, PartnerApiResponse, Partner> {

   private final CategoryRepository categoryRepository;


    @Override
    public Header<PartnerApiResponse> create(Header<PartnerApiRequest> request) {
        PartnerApiRequest body = request.getData();
        Partner partner = Partner.builder()
                .name(body.getName())
                .status(body.getStatus())
                .partnerNumber(body.getPartnerNumber())
                .businessNumber(body.getBusinessNumber())
                .address(body.getAddress())
                .callCenter(body.getCallCenter())
                .ceoName(body.getCeoName())
                .registeredAt(body.getRegisteredAt())
                .unregisteredAt(body.getUnregisteredAt())
                .category(categoryRepository.getOne(body.getCategoryId()))
                .build();
        Partner newPartner = baseRepository.save(partner);
        return response(newPartner);
    }

    @Override
    public Header<PartnerApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<PartnerApiResponse> update(Header<PartnerApiRequest> request) {
        PartnerApiRequest body = request.getData();
        return baseRepository.findById(body.getId())
                .map(partner -> {
                    partner.setStatus(body.getStatus())
                            .setRegisteredAt(body.getRegisteredAt())
                            .setPartnerNumber(body.getPartnerNumber())
                            .setName(body.getName())
                            .setCeoName(body.getCeoName())
                            .setAddress(body.getAddress())
                            .setBusinessNumber(body.getBusinessNumber())
                            .setUnregisteredAt(body.getUnregisteredAt())
                            .setCallCenter(body.getCallCenter())
                            .setCategory(categoryRepository.getOne(body.getCategoryId()));
                    return partner;

                })
                .map(baseRepository::save)
                .map(this::response)
                .orElseGet(()->Header.ERROR("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {
         return baseRepository.findById(id)
                 .map(partner -> {
                     baseRepository.delete(partner);
                     return Header.OK();
                 })
                 .orElseGet(()->Header.ERROR("데이터 없음"));


    }

    private Header<PartnerApiResponse> response(Partner partner){
        PartnerApiResponse body = PartnerApiResponse.builder()
                .id(partner.getId())
                .name(partner.getName())
                .address(partner.getAddress())
                .partnerNumber(partner.getPartnerNumber())
                .businessNumber(partner.getBusinessNumber())
                .categoryId(partner.getCategory().getId())
                .registeredAt(partner.getRegisteredAt())
                .unregisteredAt(partner.getUnregisteredAt())
                .status(partner.getStatus())
                .ceoName(partner.getCeoName())
                .build();
        return Header.OK(body);
    }

    @Override
    public Header<List<PartnerApiResponse>> search(Pageable pageable) {
        Page<Partner> partners = baseRepository.findAll(pageable);
        List<PartnerApiResponse> partnerApiResponseList = partners.stream()
                .map(this::response)
                .map(Header::getData)
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .currentElements(partners.getNumberOfElements())
                .currentPage(partners.getNumber())
                .totalPages(partners.getTotalPages())
                .totalElements(partners.getTotalElements())
                .build();
        return Header.OK(partnerApiResponseList,pagination);
    }
}
