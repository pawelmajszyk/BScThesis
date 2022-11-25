package de.kumasoft.dxi.caratmastedataimport.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaratMasterdataDto {

    @CsvBindByPosition(position = 0)
    private String caratId;

    @CsvBindByPosition(position = 1)
    private String description;

    @CsvBindByPosition(position = 2)
    private Double currentRetailPrice;

    @CsvBindByPosition(position = 3)
    private Double ekListPrice;

    @CsvBindByPosition(position = 4)
    private Double ekNetPrice;

    @CsvBindByPosition(position = 5)
    private Integer packagingUnit;

    @CsvBindByPosition(position = 6)
    private Integer amountPerUnit;

    @CsvBindByPosition(position = 7)
    private String currentSupplierCode;

    @CsvBindByPosition(position = 8)
    private String deliveryCapabilityKeyL;

    @CsvBindByPosition(position = 9)
    private String storageKeyH;

    @CsvBindByPosition(position = 10)
    private String purchaseKey;

    @CsvBindByPosition(position = 11)
    private String productGroup;

    @CsvBindByPosition(position = 12)
    private Double uvpNetPrice;

    @CsvBindByPosition(position = 13)
    private Double uvpLowestPrice;

    @CsvBindByPosition(position = 14)
    private String oeNumber;

    @CsvBindByPosition(position = 15)
    private Double retailPrice;

    @CsvBindByPosition(position = 16)
    private Boolean isOwnBrand;

    @CsvBindByPosition(position = 17)
    private Boolean isAutoPlusProduct;

    @CsvBindByPosition(position = 18)
    private String assemblyNumber;

    @CsvBindByPosition(position = 19)
    private String elekalatLink;

    @CsvBindByPosition(position = 20)
    private Double workshopNet;

    @CsvBindByPosition(position = 21)
    private String supplierInternalProductGroup;

    @CsvBindByPosition(position = 22)
    private String supplierInternalDivision;

    @CsvBindByPosition(position = 23)
    private String supplierName;

    @CsvBindByPosition(position = 24)
    private String itemDesignation;

    @CsvBindByPosition(position = 25)
    private String endOfSentenceIndicator;

    @CsvBindByPosition(position = 26)
    private String supplierItemNumber;

    @CsvBindByPosition(position = 27)
    private String supplierItemNumberCompressed;

    @CsvBindByPosition(position = 28)
    private String supplierItemNumberCompressed2;
}
