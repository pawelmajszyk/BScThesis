package de.kumasoft.dxi.caratmastedataimport.mapper;

import de.kumasoft.dxi.caratmastedataimport.dto.CaratMasterdataDto;
import de.kumasoft.dxi.inventory.model.CaratMasterdataApi;

public class CaratMasterDataMapper {

    public static CaratMasterdataApi map(CaratMasterdataDto dto) {
        CaratMasterdataApi caratMasterdataApi = new CaratMasterdataApi();

        caratMasterdataApi.setCaratId(dto.getCaratId());
        caratMasterdataApi.setDescription(dto.getDescription());
        caratMasterdataApi.setCurrentRetailPrice(dto.getCurrentRetailPrice());
        caratMasterdataApi.setEkListPrice(dto.getEkListPrice());
        caratMasterdataApi.setEkNetPrice(dto.getEkNetPrice());
        caratMasterdataApi.setPackagingUnit(dto.getPackagingUnit());
        caratMasterdataApi.setAmountPerUnit(dto.getAmountPerUnit());
        caratMasterdataApi.setCurrentSupplierCode(dto.getCurrentSupplierCode());
        caratMasterdataApi.setDeliveryCapabilityKeyL(dto.getDeliveryCapabilityKeyL());
        caratMasterdataApi.setStorageKeyH(dto.getStorageKeyH());
        caratMasterdataApi.setPurchaseKey(dto.getPurchaseKey());
        caratMasterdataApi.setProductGroup(dto.getProductGroup());
        caratMasterdataApi.setUvpNetPrice(dto.getUvpNetPrice());
        caratMasterdataApi.setUvpLowestPrice(dto.getUvpLowestPrice());
        caratMasterdataApi.setOeNumber(dto.getOeNumber());
        caratMasterdataApi.setRetailPrice(dto.getRetailPrice());
        caratMasterdataApi.setIsOwnBrand(dto.getIsOwnBrand());
        caratMasterdataApi.isAutoPlusProduct(dto.getIsAutoPlusProduct());
        caratMasterdataApi.setAssemblyNumber(dto.getAssemblyNumber());
        caratMasterdataApi.setElekalatLink(dto.getElekalatLink());
        caratMasterdataApi.setSupplierInternalDivision(dto.getSupplierInternalDivision());
        caratMasterdataApi.setSupplierInternalProductGroup(dto.getSupplierInternalProductGroup());
        caratMasterdataApi.setSupplierName(dto.getSupplierName());
        caratMasterdataApi.setItemDesignation(dto.getItemDesignation());
        caratMasterdataApi.setEndOfSentenceIndicator(dto.getEndOfSentenceIndicator());
        caratMasterdataApi.setSupplierItemNumber(dto.getSupplierItemNumber());
        caratMasterdataApi.setSupplierItemNumberCompressed(dto.getSupplierItemNumberCompressed());
        caratMasterdataApi.setSupplierItemNumberCompressed2(dto.getSupplierItemNumberCompressed2());
        caratMasterdataApi.setWorkshopNet(dto.getWorkshopNet());

        return caratMasterdataApi;
    }
}
