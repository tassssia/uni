class Segment:
    def __init__(self, start):
        self.start = start
        self.end = None
        self.done = False

    def set_end(self, end):
        if not self.done:
            self.end = end
            self.done = True