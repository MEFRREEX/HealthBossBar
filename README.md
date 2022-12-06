# HealthBossBar
Displaying the enemy's health in the boss bar

![ApplicationFrameHost 03-12-2022 13-52-53 (3)](https://user-images.githubusercontent.com/83061703/205470064-9afff306-c925-451c-b5b9-9142ecaec50f.gif)

# Configuration:

`config.yml`
```
# Available placeholders: {name}, {tag}, {health}, {maxhealth}.
bossbar-text: "{name}"

# Available colors: PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE.
color: "RED"

# Time after which the boss bar will disappear. In seconds.
bossbar-remove-time: 5
```
`mobs.yml`
```
enable: true

# Available: blacklist, whitelist.
type: "blacklist"

# Enter the networkid of mobs for which you do not want to display the boss bar. 
# You can find out the networkid of each mob at 
# https://github.com/PowerNukkitX/PowerNukkitX/tree/master/src/main/java/cn/nukkit/entity/
mobs:
  - 53
  - 52
  - 131
```
# Commands:
```
/hpbar help - help
/hpbar about - about the plugin
/hpbar reload - reloading configs[/QUOTE]
```
# Permissions:
```
healthbossbar.reload
```
