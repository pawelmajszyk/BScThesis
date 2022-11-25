package de.kumasoft.dxi.caratmastedataimport.dto;

import de.kumasoft.dxi.inventory.model.ImportCaratMasterdataApi;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImportDto {

    private String bucketName;
    private String fileName;
    private ImportCaratMasterdataApi importCaratMasterdataApi;
}
