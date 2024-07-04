package pl.wielkopolan.flightpublisher.services.kafka;

public interface ProducerService<T> {
    void sendMessage(T messageObject);
}
