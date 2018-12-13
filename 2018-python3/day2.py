from days import read_file
from collections import defaultdict

def count_chars(line):
    counts = defaultdict(lambda: 0)
    for c in line:
        counts[c] += 1
    return counts


def count(lines):
    for line in lines:
        values = count_chars(line).values()
        yield (2 in values, 3 in values)


def part1():
    sum2 = 0
    sum3 = 0
    lines = read_file(2)
    for result in count(lines):
        if result[0]:
            sum2 += 1
        if result[1]:
            sum3 += 1
    return sum2 * sum3


def id_match(box_id: str, box_id2: str):
    diffs: int = 0
    matching_chars = ""
    for i in range(len(box_id)):
        if box_id[i] != box_id2[i]:
            diffs += 1
            if diffs > 1:
                return False
        else:
            matching_chars = matching_chars + box_id[i]
    return matching_chars if diffs == 1 else None

def part2():
    box_ids = list(read_file(2))
    for box_id in box_ids:
        for box_id2 in box_ids:
            result = id_match(box_id, box_id2)
            if result:
                return result

print(part2())
