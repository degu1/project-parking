package se.iths.parking_lot.dtos;

import se.iths.parking_lot.entities.Queue;
import se.iths.parking_lot.entities.User;

import java.util.List;

public record QueueDto(Long id, List<QueueSlotDto> queueSlots) {
    public static QueueDto from(Queue queue) {
        return new QueueDto(queue.getId(),QueueSlotDto.from(queue.getQueueSlots()));
    }

    public static List<QueueDto> from(List<Queue> queues) {
        return queues.stream()
                .map(queue -> QueueDto.from(queue)).toList();
    }

    public Queue toQueue() {
        return Queue.fromDto(this);
    }
}
