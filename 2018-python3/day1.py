value: int = 0

def read_file(day: int) -> list:
    with open('inputs/day1.txt') as data:
        for line in data:
            yield int(line)
    return


with open('inputs/day1.txt') as data:
    for line in data:
        value += int(line)

print(value)

def repeat(input: list) -> int:
    value = 0
    seen: set = set()
    while True:
        for num in input:
            value += num
            print("change to", value)
            if value in seen:
                print("seen", value)
                return value
            seen.add(value)

mylist = list(read_file(1))
print("result", repeat(mylist))
