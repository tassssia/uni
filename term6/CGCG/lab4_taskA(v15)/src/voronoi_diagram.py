import math
from point import Point
from segment import Segment
from arc import Arc
from event import Event
from event_priority_queue import EventPriorityQueue

class VoronoiDiagram:
    def __init__(self, points):
        self.res = []
        self.arc_tree = None
        self.site_events = EventPriorityQueue()
        self.arc_events = EventPriorityQueue()

        self.x_min = 0.0
        self.x_max = 0.0
        self.y_min = 0.0
        self.y_max = 0.0

        for p in points:
            self.site_events.push(p)
            if p.x < self.x_min:
                self.x_min = p.x
            if p.x > self.x_max:
                self.x_max = p.x
            if p.y < self.y_min:
                self.y_min = p.y
            if p.y > self.y_max:
                self.y_max = p.y

    def build(self):
        while not self.site_events.is_empty():
            if not self.arc_events.is_empty() and (self.arc_events.top().x <= self.site_events.top().x):
                self.process_arc_event()
            else:
                self.insert_arc(self.site_events.pop())

        while not self.arc_events.is_empty():
            self.process_arc_event()

        self.end_up_edges()

    def process_arc_event(self):
        event = self.arc_events.pop()

        if event.valid:
            segment = Segment(event.point)
            self.res.append(segment)

            arc = event.arc
            if arc.prev is not None:
                arc.prev.next = arc.next
                arc.prev.seg_right = segment
            if arc.next is not None:
                arc.next.prev = arc.prev
                arc.next.seg_left = segment

            if arc.seg_left is not None:
                arc.seg_left.set_end(event.point)
            if arc.seg_right is not None:
                arc.seg_right.set_end(event.point)

            if arc.prev is not None:
                self.recheck_events_around(arc.prev)
            if arc.next is not None:
                self.recheck_events_around(arc.next)

    def recheck_events_around(self, to_check):
        if (to_check.event is not None) and (to_check.event.x != self.x_min):
            to_check.event.valid = False
        to_check.event = None

        if (to_check.prev is None) or (to_check.next is None):
            return

        x, center = self.calc_circle(to_check.prev.focus, to_check.focus, to_check.next.focus)
        if center is not None and (x > self.x_min):
            to_check.event = Event(x, center, to_check)
            self.arc_events.push(to_check.event)

    def calc_circle(self, a, b, c):
        if ((b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y)) > 0:
            return None, None

        A = b.x - a.x
        B = b.y - a.y
        C = c.x - a.x
        D = c.y - a.y
        E = A * (a.x + b.x) + B * (a.y + b.y)
        F = C * (a.x + c.x) + D * (a.y + c.y)
        G = 2 * (A * (c.y - b.y) - B * (c.x - b.x))

        if G == 0:
            return None, None

        ox = 1.0 * (D * E - B * F) / G
        oy = 1.0 * (A * F - C * E) / G
        x = ox + math.sqrt((a.x - ox) ** 2 + (a.y - oy) ** 2)
        o = Point(ox, oy)

        return x, o

    def intersection(self, point, arc):
        if arc is None or arc.focus.x == point.x:
            return None

        a = 0.0
        b = 0.0
        if arc.prev is not None:
            a = (self.calc_intersection(arc.prev.focus, arc.focus, point.x)).y
        if arc.next is not None:
            b = (self.calc_intersection(arc.focus, arc.next.focus, point.x)).y

        if (((arc.prev is None) or (a <= point.y)) and ((arc.next is None) or (point.y <= b))):
            py = point.y
            px = 1.0 * ((arc.focus.x) ** 2 + (arc.focus.y - py) ** 2 - point.x ** 2) / (2 * arc.focus.x - 2 * point.x)
            res = Point(px, py)
            return res

        return None

    def calc_intersection(self, focus1, focus2, inter_x):
        p = focus1
        if focus1.x == focus2.x:
            py = (focus1.y + focus2.y) / 2.0
        elif focus2.x == inter_x:
            py = focus2.y
        elif focus1.x == inter_x:
            py = focus1.y
            p = focus2
        else:
            z0 = 2.0 * (focus1.x - inter_x)
            z1 = 2.0 * (focus2.x - inter_x)

            a = 1.0 / z0 - 1.0 / z1
            b = -2.0 * (focus1.y / z0 - focus2.y / z1)
            c = 1.0 * (focus1.y ** 2 + focus1.x ** 2 - inter_x ** 2) / z0 - 1.0 * (focus2.y ** 2 + focus2.x ** 2 - inter_x ** 2) / z1

            py = 1.0 * (-b - math.sqrt(b * b - 4 * a * c)) / (2 * a)

        px = 1.0 * (p.x ** 2 + (p.y - py) ** 2 - inter_x ** 2) / (2 * p.x - 2 * inter_x)
        return Point(px, py)

    def insert_arc(self, point):
        if self.arc_tree is None:
            self.arc_tree = Arc(point)
        else:
            curr = self.arc_tree
            while curr is not None:
                intersection = self.intersection(point, curr)
                if intersection is not None:
                    next_intersection = self.intersection(point, curr.next)
                    if (curr.next is not None) and (next_intersection is None):
                        curr.next.prev = Arc(curr.focus, curr, curr.next)
                        curr.next = curr.next.prev
                    else:
                        curr.next = Arc(curr.focus, curr)
                    curr.next.seg_right = curr.seg_right

                    curr.next.prev = Arc(point, curr, curr.next)
                    curr.next = curr.next.prev

                    curr = curr.next

                    segment = Segment(intersection)
                    self.res.append(segment)
                    curr.prev.seg_right = curr.seg_left = segment

                    segment = Segment(intersection)
                    self.res.append(segment)
                    curr.next.seg_left = curr.seg_right = segment

                    self.recheck_events_around(curr)
                    self.recheck_events_around(curr.prev)
                    self.recheck_events_around(curr.next)

                    return

                curr = curr.next

            curr = self.arc_tree
            while curr.next is not None:
                curr = curr.next
            curr.next = Arc(point, curr)

            x = self.x_min
            y = (curr.next.focus.y + curr.focus.y) / 2.0
            start = Point(x, y)

            segment = Segment(start)
            curr.seg_right = curr.next.seg_left = segment
            self.res.append(segment)

    def end_up_edges(self):
        bound = 100 * max((self.x_max - self.x_min), (self.y_max - self.y_min))

        curr = self.arc_tree
        while curr.next is not None:
            if curr.seg_right is not None:
                p = self.calc_intersection(curr.focus, curr.next.focus, bound * 2.0)
                curr.seg_right.set_end(p)

            curr = curr.next