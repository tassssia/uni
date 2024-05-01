class TreeNode:
    def __init__(self, point, line, axis, left=None, right=None):
        self.point = point
        self.line = line
        self.axis = axis
        self.left = left
        self.right = right

    def is_leaf(self):
        return self.left is None and self.right is None


class TwoDTree:
    def __init__(self, points):
        self.root = self.build(points, 0)

    def build(self, points, depth):
        if len(points) == 0: return None

        axis = depth % 2
        points.sort(key=lambda p: p.x if axis == 0 else p.y)
        median_id = len(points) // 2
        median_val = points[median_id].x if axis == 0 else points[median_id].y

        return TreeNode(points[median_id], median_val, axis,
                        self.build(points[:median_id], depth + 1), self.build(points[median_id + 1:], depth + 1))

    def search(self, region):
        result = []
        self.search_rec(self.root, region, result)
        return result

    def search_rec(self, node, region, result):
        if node is None:
            return

        left, right = region.project_on_axis(node.axis)
        if region.contains(node.point):
            result.append(node.point)

        if left < node.line:
            self.search_rec(node.left, region, result)
        if node.line < right:
            self.search_rec(node.right, region, result)