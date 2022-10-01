package com.codex.ecam.util;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.azure.core.util.IterableStream;
import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubConsumerClient;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.messaging.eventhubs.models.ErrorContext;
import com.azure.messaging.eventhubs.models.EventContext;
import com.azure.messaging.eventhubs.models.EventPosition;
import com.azure.messaging.eventhubs.models.PartitionEvent;


public class AzureEventHubUtil {
	
	private static final String EH_NAMESPACE_CONNECTION_STRING = "Endpoint=sb://ecammessaging.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=wCRUrfBJM3+hf9ODMco514EE8WJNXCI30yY02EWLOi4=";
	private static final String eventHubName = "ecammessage";
	private static final String STORAGE_CONNECTION_STRING = "DefaultEndpointsProtocol=https;AccountName=notificationstorageecam;AccountKey=SPhBVlp+b0+a1jT4440h5qcnNprOH+ZggjFn8okzyLoenhTFZF6ONBRiu7CaMcpurGpsxxcZ5UN2Keb4eGLNSg==;EndpointSuffix=core.windows.net";
	private static final String STORAGE_CONTAINER_NAME = "notifycontainer";
	
    public static void sendSimpleMessae(){


    	
        // create a producer using the namespace connection string and event hub name
        EventHubProducerClient producer = new EventHubClientBuilder()
            .connectionString(EH_NAMESPACE_CONNECTION_STRING, eventHubName)
            .buildProducerClient();

        // prepare a batch of events to send to the event hub    
        EventDataBatch batch = producer.createBatch();
        batch.tryAdd(new EventData(1+"First event"));
        batch.tryAdd(new EventData(2+"Second event"));
        batch.tryAdd(new EventData(3+"Third event"));
        batch.tryAdd(new EventData(4+"Fourth event"));
        batch.tryAdd(new EventData(5+"Fifth event"));

        // send the batch of events to the event hub
        producer.send(batch);

        // close the producer

    }

    
    public static List<String> reciveSimpleMessae() throws IOException{
    	List<String> messages=new ArrayList<String>();
    	EventHubConsumerClient consumer = new EventHubClientBuilder()
    		    .connectionString(EH_NAMESPACE_CONNECTION_STRING+";EntityPath="+eventHubName)
    		    .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
    		    .buildConsumerClient();

    		String partitionId = "1";

    		// Get the first 15 events in the stream, or as many events as can be received within 40 seconds.
    		IterableStream<PartitionEvent> events = consumer.receiveFromPartition(partitionId, 15,
    		    EventPosition.latest(), Duration.ofSeconds(40));
    		int i=0;
    		for (PartitionEvent event : events) {
    			String message=event.getData().getBodyAsString();
    			i++;
    		    System.out.println("Event: "+i+" "+ event.getData().getBodyAsString());
    		    messages.add(message);
    		}
    		return messages;
     }

    
    public static final Consumer<EventContext> PARTITION_PROCESSOR = eventContext -> {
        System.out.printf("Processing event from partition %s with sequence number %d with body: %s %n", 
                eventContext.getPartitionContext().getPartitionId(), eventContext.getEventData().getSequenceNumber(), eventContext.getEventData().getBodyAsString());

       if (eventContext.getEventData().getSequenceNumber() % 10 == 0) {
           eventContext.updateCheckpoint();
       }
   };

   public static final Consumer<ErrorContext> ERROR_HANDLER = errorContext -> {
       System.out.printf("Error occurred in partition processor for partition %s, %s.%n",
           errorContext.getPartitionContext().getPartitionId(),
           errorContext.getThrowable());
   };
   


}
