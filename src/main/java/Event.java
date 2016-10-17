import java.time.Instant;
import java.util.UUID;

/**
 * Event class
 */
class Event {

    private final Instant created = Instant.now();
    private final int clientId;
    private final UUID uuid;

    public Event(int clientId, UUID uuid) {
        this.clientId = clientId;
        this.uuid = uuid;
    }

    public Instant getCreated() {
        return created;
    }

    public int getClientId() {
        return clientId;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (clientId != event.clientId) return false;
        if (created != null ? !created.equals(event.created) : event.created != null) return false;
        return uuid != null ? uuid.equals(event.uuid) : event.uuid == null;

    }

    @Override
    public int hashCode() {
        int result = created != null ? created.hashCode() : 0;
        result = 31 * result + clientId;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        return result;
    }
}
