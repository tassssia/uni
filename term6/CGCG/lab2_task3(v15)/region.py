import matplotlib.pyplot as plt

class Region:
    def __init__(self, vertex, width, height):
        # vertex is top left corner if width and height are positive
        self.vertex = vertex
        self.width = width
        self.height = height

    def contains(self, point):
        return self.width >= point.x - self.vertex.x >= 0 and self.height >= self.vertex.y - point.y >= 0

    def project_on_axis(self, axis):
        return (self.vertex.x, self.vertex.x + self.width) if axis == 0 \
            else (self.vertex.y - self.height, self.vertex.y)

    def plot(self):
        x_coords = [self.vertex.x, self.vertex.x + self.width,
                    self.vertex.x + self.width, self.vertex.x,
                    self.vertex.x]

        y_coords = [self.vertex.y, self.vertex.y,
                    self.vertex.y - self.height, self.vertex.y - self.height,
                    self.vertex.y]

        plt.plot(x_coords, y_coords, color="black")
