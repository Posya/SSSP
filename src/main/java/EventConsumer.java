/**
 * EventConsumer interface
 */
@FunctionalInterface
interface EventConsumer {
    Event consume(Event event);
}