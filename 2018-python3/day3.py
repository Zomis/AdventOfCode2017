from days import read_file
from collections import defaultdict


def make_rect(line: str):
    split1 = line.strip().split(" @ ")
    split2 = split1[1].split(": ")
    positions = split2[0].split(",")
    size = split2[1].split("x")
    id = split1[0].split("#")[1]
    return tuple(map(lambda x: int(x), (id, positions[0], positions[1], size[0], size[1])))


boxes = list(make_rect(line) for line in read_file(3))


def filled_box(box):
    for x in range(box[3]):
        for y in range(box[4]):
            yield str(box[1] + x) + "_" + str(box[2] + y)


world = defaultdict(lambda: 0)
for box in boxes:
    print(box)
    for pos in filled_box(box):
        world[pos] += 1
print(len(list(v for v in world.values() if v > 1)))


def has_overlap(claim):
    for pos in filled_box(claim):
        if world[pos] > 1:
#            print(box, "has overlap at", pos)
            return True
    return False


for box in boxes:
    if not has_overlap(box):
        print(box)

