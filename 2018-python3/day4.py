from dataclasses import dataclass
from datetime import datetime
from days import read_file
from enum import Enum
from collections import defaultdict
from itertools import groupby
from statistics import mode
import statistics
import operator
import re


class EventType(Enum):
    STARTS_SHIFT = 1
    FALLS_ASLEEP = 2
    WAKES_UP = 3


@dataclass
class Event:
    time: datetime
    guard: int
    event: EventType


@dataclass
class GuardSleep:
    sleep_total: int
    last_sleep: int
    sleeps: list

    def add_sleeps(self, minute):
        for i in range(self.last_sleep, minute):
            self.sleeps.append(i)


def get_guard(line: str):
    if "Guard" in line:
        id = re.search("Guard #(\d+)", line)
        return int(id.group(1))
    return -1


def event_type(line):
    if "begins shift" in line:
        return EventType.STARTS_SHIFT
    if "falls asleep" in line:
        return EventType.FALLS_ASLEEP
    if "wakes up" in line:
        return EventType.WAKES_UP
    raise Exception("Unknown line: " + line)


def part1() -> int:
    events = sorted(list(Event(datetime.strptime(line[1:17], "%Y-%m-%d %H:%M"), get_guard(line), event_type(line)) for line in read_file(4)), key=operator.attrgetter("time"))

    guard = -1
    sleeping = False
    guardsleep = defaultdict(lambda: GuardSleep(0, 0, []))
    for event in events:
        print(event)
        if event.guard >= 0:
            guard = event.guard
            sleeping = False

        if event.event == EventType.FALLS_ASLEEP:
            guardsleep[guard].last_sleep = event.time.minute
            sleeping = True

        if event.event == EventType.WAKES_UP:
            guardsleep[guard].sleep_total += event.time.minute - guardsleep[guard].last_sleep
            guardsleep[guard].add_sleeps(event.time.minute)
            sleeping = False

        # if sleeping:
        #     guardsleep[guard].sleeps.append(event.time.minute)

    print(f"Sleepy guard is {guardsleep}")
    most_sleepy_guard_number = max(guardsleep, key=(lambda key: guardsleep[key].sleep_total))
    print(f"Most sleepy guards is {most_sleepy_guard_number}")

    most_sleepy_guard = guardsleep[most_sleepy_guard_number]
    print(sorted(most_sleepy_guard.sleeps))
    print(mode(sorted(most_sleepy_guard.sleeps)))

    for k in guardsleep:
        current_guard = guardsleep[k]
        try:
            most_common = mode(sorted(current_guard.sleeps))
            amount = len(list((m for m in current_guard.sleeps if m == most_common)))
            print(f"{k} has {most_common} with {amount}")
        except statistics.StatisticsError:
            print("No special for " + str(k))


    return 42


part1()
