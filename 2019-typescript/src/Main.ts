const fs = require('fs');
import Day from "./Day";
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
  let input = fs.readFileSync(`inputs/${i}`, 'utf8').trim();
  let parsed1 = d.parse(input);
  let result1 = d.part1(parsed1);

  let parsed2 = d.parse(input);
  let result2 = d.part2(parsed2);
  console.log("Day", i, result1, result2);
}
