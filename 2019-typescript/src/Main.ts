const fs = require('fs');
import Day from "./Day";
import TestCase from "./TestCase";
interface NewDay {
  new(): Day<any>
}

for (var i = 25; i >= 1; i--) {
  let day: NewDay;
//  if (i !== 1) continue;
  try {
     let imported = require(`./Day${i}`) as any;
     day = imported.default as NewDay;
  } catch (e) {
    console.log(`Skipping Day${i}`);
    console.log(e);
    continue;
  }

  let d: Day<any> = new day();
  let a = d as any;
  if (a.testCases) {
    let tests = a.testCases as Array<TestCase>
    for (let testIndex in tests) {
      let test = tests[testIndex];
      let v = d.parse(test.text);
      d.part1(v);
      console.log(`Running test case ${testIndex} on Day${i} : ${v}`);
    }
  }
  let input = fs.readFileSync(`inputs/${i}`, 'utf8').trim();
  let parsed1 = d.parse(input);
  let result1 = d.part1(parsed1);

  let parsed2 = d.parse(input);
  let result2 = d.part2(parsed2);
  console.log("Day", i, result1, result2);
}
