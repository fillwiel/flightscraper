package pl.wielkopolan.flightpublisher.services;

public interface PublisherService<T> {
    void publish(T flight);
}
