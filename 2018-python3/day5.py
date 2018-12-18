from days import read_file
from string import ascii_lowercase

def react_polymer(polymer: str) -> int:
    for i in range(len(polymer) - 1, 1, -1):
        while i < len(polymer):
            c1 = polymer[i - 1]
            c2 = polymer[i]
            if c1.lower() == c2.lower() and c1 != c2:
                polymer = polymer[0:i-1] + polymer[i+1:]
            else:
                break
    return len(polymer)


original: str = list(read_file(5))[0]
print(react_polymer(original))

min_count = len(original)
for ch in ascii_lowercase:
    copy = original.replace(ch, "")
    copy = copy.replace(ch.upper(), "")
    new_length = react_polymer(copy)
    if new_length < min_count:
        min_count = new_length

print(min_count)
