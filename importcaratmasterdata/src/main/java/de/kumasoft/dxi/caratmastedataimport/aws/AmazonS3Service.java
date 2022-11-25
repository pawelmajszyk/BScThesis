package de.kumasoft.dxi.caratmastedataimport.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import com.opencsv.bean.CsvToBeanBuilder;
import de.kumasoft.dxi.caratmastedataimport.dto.CaratMasterdataDto;
import de.kumasoft.dxi.caratmastedataimport.dto.ImportDto;
import de.kumasoft.dxi.caratmastedataimport.mapper.CaratMasterDataMapper;
import de.kumasoft.dxi.inventory.model.CaratMasterdataApi;
import de.kumasoft.dxi.inventory.model.ImportCaratMasterdataApi;
import de.kumasoft.dxi.inventory.model.ImportCaratMasterdataNotificationApi;
import lombok.SneakyThrows;
import org.joda.time.LocalDateTime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AmazonS3Service {

    private static final String BUCKET_EXCHANGE_DATA = System.getenv("BUCKET_EXCHANGE_DATA");
    private static final String DIRECTORY = "/caratmasterdata";

    private final AmazonS3 amazonS3;
    private final Context context;

    public AmazonS3Service(final AmazonS3 amazonS3, final Context context) {
        this.amazonS3 = amazonS3;
        this.context = context;
    }

    @SneakyThrows
    public ImportDto retrieveObjectToImport(S3EventNotification.S3Entity s3Entity) {
        S3Object obj = amazonS3.getObject(new GetObjectRequest(s3Entity.getBucket().getName(), s3Entity.getObject().getUrlDecodedKey()));

        ImportCaratMasterdataApi importCaratMasterdataApi = new ImportCaratMasterdataApi();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(obj.getObjectContent()));

        List<CaratMasterdataApi> apiList = new CsvToBeanBuilder<CaratMasterdataDto>(bufferedReader)
                .withType(CaratMasterdataDto.class)
                .withSkipLines(1)
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build().stream()
                .map(CaratMasterDataMapper::map)
                .collect(Collectors.toList());

        importCaratMasterdataApi.setItems(apiList);

        return ImportDto.builder()
                .importCaratMasterdataApi(importCaratMasterdataApi)
                .bucketName(s3Entity.getBucket().getName())
                .fileName(s3Entity.getObject().getUrlDecodedKey()).build();
    }

    public ImportCaratMasterdataNotificationApi sendImportData(ImportDto importDto) {
        String key = putDataToBucket(importDto.getImportCaratMasterdataApi());

        return new ImportCaratMasterdataNotificationApi()
                .bucket(BUCKET_EXCHANGE_DATA)
                .originalBucketName(importDto.getBucketName())
                .originalSourceName(importDto.getFileName())
                .exchangeFile(key);
    }

    @SneakyThrows
    private String putDataToBucket(Object data) {
        LocalDateTime dateTime = LocalDateTime.now();
        String key = String.format("%s/%s/%s/%s/%s",
                AmazonS3Service.DIRECTORY,
                dateTime.getYear(),
                dateTime.getMonthOfYear(),
                dateTime.getDayOfMonth(),
                UUID.randomUUID());

        amazonS3.putObject(BUCKET_EXCHANGE_DATA, key, ObjectMapperUtils.getObjectMapper().writeValueAsString(data));

        return key;
    }
}