from dataclasses import dataclass
from datetime import datetime
from days import read_file
from enum import Enum
from collections import defaultdict, namedtuple
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
        guard_id = re.search("Guard #(\\d+)", line)
        return int(guard_id.group(1))
    return -1


def event_type(line):
    if "begins shift" in line:
        return EventType.STARTS_SHIFT
    if "falls asleep" in line:
        return EventType.FALLS_ASLEEP
    if "wakes up" in line:
        return EventType.WAKES_UP
    raise Exception("Unknown line: " + line)


def day4() -> (int, int):
    events = sorted(list(Event(datetime.strptime(line[1:17], "%Y-%m-%d %H:%M"), get_guard(line), event_type(line)) for line in read_file(4)), key=operator.attrgetter("time"))

    guard = -1
    guardsleep = defaultdict(lambda: GuardSleep(0, 0, []))
    for event in events:
        if event.guard >= 0:
            guard = event.guard

        if event.event == EventType.FALLS_ASLEEP:
            guardsleep[guard].last_sleep = event.time.minute

        if event.event == EventType.WAKES_UP:
            guardsleep[guard].sleep_total += event.time.minute - guardsleep[guard].last_sleep
            guardsleep[guard].add_sleeps(event.time.minute)

    most_sleepy_guard_number = max(guardsleep, key=(lambda key: guardsleep[key].sleep_total))

    most_sleepy_guard = guardsleep[most_sleepy_guard_number]
    part1_result = most_sleepy_guard_number * mode(sorted(most_sleepy_guard.sleeps))

    # Part 2
    MostSleepy = namedtuple('MostCommon', ['id', 'minute', 'amount'])
    most_sleepy = MostSleepy(0, 0, 0)
    for k in guardsleep:
        current_guard = guardsleep[k]
        try:
            most_common_minute = mode(sorted(current_guard.sleeps))
            amount = len(list((m for m in current_guard.sleeps if m == most_common_minute)))
            if amount > most_sleepy.amount:
                most_sleepy = MostSleepy(k, most_common_minute, amount)
        except statistics.StatisticsError:
            print("No unique most common minute for " + str(k))

    return part1_result, most_sleepy.id * most_sleepy.minute


if __name__ == '__main__':
    print(day4())
