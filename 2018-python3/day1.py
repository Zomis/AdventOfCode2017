from days import read_file

value: int = 0
for line in read_file(1):
    value += int(line)
print(value)


def repeat(input: list) -> int:
    value = 0
    seen: set = set()
    while True:
        for num in input:
            value += int(num)
            if value in seen:
                return value
            seen.add(value)

mylist = list(read_file(1))
print(repeat(mylist))
