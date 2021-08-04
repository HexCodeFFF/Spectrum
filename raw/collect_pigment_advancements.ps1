﻿$c = @("black", "blue", "brown", "cyan", "gray", "green", "light_blue", "light_gray", "lime", "magenta", "orange", "pink", "purple", "red", "white", "yellow")

$c | Foreach-Object {
New-Item -Name "collect_$_`_pigment.json" -ItemType File -Value @"
{
  "criteria": {
    "has_black_pigment": {
      "trigger": "minecraft:inventory_changed",
      "conditions": { "items": [{ "items": [ "spectrum:$_`_pigment" ]}] }
    }
  }
}
"@

}