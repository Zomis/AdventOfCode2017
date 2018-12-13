def read_file(day: int) -> list:
    with open('inputs/day' + str(day) + '.txt') as data:
        for line in data:
            yield line
    return
