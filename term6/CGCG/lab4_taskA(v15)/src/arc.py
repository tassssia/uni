class Arc:
    def __init__(self, focus, prev=None, next=None):
        self.focus = focus
        self.prev = prev
        self.next = next
        self.event = None
        self.seg_left = None
        self.seg_right = None