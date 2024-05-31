import itertools
import heapq


class EventPriorityQueue:
    def __init__(self):
        self.queue = []
        self.prioritized = {}
        self.events_num = itertools.count()

    def push(self, event):
        if event not in self.prioritized:
            id = next(self.events_num)
            to_set_priority = [event.x, id, event]
            self.prioritized[event] = to_set_priority
            heapq.heappush(self.queue, to_set_priority)

    def top(self):
        while self.queue:
            priority, id, event = heapq.heappop(self.queue)
            if event != 'erased':
                del self.prioritized[event]
                self.push(event)
                return event

        return None

    def pop(self):
        while self.queue:
            priority, id, event = heapq.heappop(self.queue)
            if event != 'erased':
                del self.prioritized[event]
                return event

        return None

    def is_empty(self):
        return not self.queue