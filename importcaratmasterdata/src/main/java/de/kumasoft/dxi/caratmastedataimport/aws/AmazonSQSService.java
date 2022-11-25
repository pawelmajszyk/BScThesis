package de.kumasoft.dxi.caratmastedataimport.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.sqs.AmazonSQS;
import de.kumasoft.dxi.inventory.model.ImportCaratMasterdataNotificationApi;
import lombok.SneakyThrows;

public class AmazonSQSService {

    private static final String SQS_EXCHANGE_QUEUE = System.getenv("SQS_EXCHANGE_QUEUE");

    private final AmazonSQS amazonSQS;
    private final Context context;

    public AmazonSQSService(AmazonSQS amazonSQS, Context context) {
        this.amazonSQS = amazonSQS;
        this.context = context;
    }

    @SneakyThrows
    public String sendNotification(ImportCaratMasterdataNotificationApi importCaratMasterdataNotificationApi) {
        amazonSQS.sendMessage(amazonSQS.getQueueUrl(SQS_EXCHANGE_QUEUE).getQueueUrl(),
                ObjectMapperUtils.getObjectMapper().writeValueAsString(importCaratMasterdataNotificationApi));

        return importCaratMasterdataNotificationApi.getExchangeFile();
    }
}