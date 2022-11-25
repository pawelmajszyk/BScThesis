package de.kumasoft.dxi.caratmastedataimport;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import de.kumasoft.dxi.caratmastedataimport.aws.AWSClient;
import de.kumasoft.dxi.caratmastedataimport.aws.AmazonS3Service;
import de.kumasoft.dxi.caratmastedataimport.aws.AmazonSQSService;

public class ImportProcess implements RequestHandler<S3EventNotification, Void> {

    @Override
    public Void handleRequest(S3EventNotification s3EventNotification, Context context) {
        AmazonS3Service amazonS3Service = AWSClient.getAmazonS3Client(context);
        AmazonSQSService amazonSQSService = AWSClient.getAmazonSQSClient(context);

        s3EventNotification.getRecords()
                .stream()
                .map(S3EventNotification.S3EventNotificationRecord::getS3)
                .map(amazonS3Service::retrieveObjectToImport)
                .map(amazonS3Service::sendImportData)
                .map(amazonSQSService::sendNotification)
                .forEach(fileName -> context.getLogger().log(fileName));

        return null;


    }
}
