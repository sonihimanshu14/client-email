
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
public class KafkaService {


    @Service
    public class KafkaConsumer {

        @KafkaListener(topics = "toll-events", groupId = "toll-group")
        public void listen(String message) {
            System.out.println("Received message from Kafka: " + message);
        }
    }

}
