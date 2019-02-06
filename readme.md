# MythicBetonAddon 1.5c for BetonQuest 2.0 and MythicMobs 4.1.0 or higher
### https://www.spigotmc.org/resources/betonquest.2117/

# [DOWNLOAD](http://mc.hackerzlair.org:8080/job/MythicBetontAddon/) [![Build Status](http://mc.hackerzlair.org:8080/job/MythicBetonAddon/badge/icon)] <br>

## update 1.5c: more NPE handling.
## update 1.4b: more NPE handling.
## update 1.4a: better NPE handling.
## update 1.2a: added faction option into the kill objective.

# mmMythicMobsKillObjective
# mmMythicMobsSpawnEvent
# MythicMobs betonquest Mechanics
# MythicMobs bqhastag Condition since 1.1a

## for BetonQuest 1.8.5 or smaller use https://github.com/BerndiVader/bqMythicMobs/blob/master/bqMythicMobsKillObjective185.jar
## for BetonQuest 1.9.3 or higher use https://github.com/BerndiVader/bqMythicMobs/blob/master/bqMythicMobsKillObjective.jar


## The Objective: mmMythicMobsKillObjective

How to install? - Just copy the jar file into your plugins folder and restart your server.

Syntax? - mmMythicMobsKillObjective type:mmtype1,mmtype2,mmtype3 faction:anyfaction,or,a,list name:This_is_a_Name,This_is_Another_name level:1-5 amount:5 notify events:whatever

Type or name must be given. Or booth. If type is given then name can be optional and vise versa. Type and name can be a list. Example: type:mob1,mob2,mob3 or name:name1,name2,name3

If you use space in names then replace them with "_" in the objective. Example: mobdisplayname "a Mythic Mob" must be "a_Mythic_Mob" in the objective.

Level: Optional. Can be a single value or ranged. Ranged looks this: level:3-8 = all mobs with level between 3 and 8 matches.

Faction: Optional. If this field is set, only mobs that match type or name and faction count.


## The Event: mmMythicMobsSpawnEvent

Syntax? - mmMythicMobsSpawnEvent loc:x,y,z,worldname mobtype:mobname level:integer amount:integer
old Syntax for 1.8.5: - mmMythicMobsSpawnEvent x,y,z,worldname mobtype:mobname level:integer amount:integer


loc: x,y,z are the coordinates followed by the worldname. 
mobtype: valid MythicMobs Mob.
level: the level of the mob. 
amount: is how many mobs will be spawned.

Example: mmMythicMobsSpawnEvent 0,70,0,world BigBoss:99 1
since 1.9.0 new Example: mmMythicMobsSpawnEvent loc:0,70,0,world BigBoss:99 1
This will spawn one mob of the type BigBoss at the location 0,70,0 in world with the level of 99.



## The SkillCondition: bqhastag (since 1.1a)

Syntax? - Use as MythicMobs skill condition.

### - bqhastag{tag=tagname;pack=packagename;action=true||false}

Usage: This condition meets if a player has the BetonQuest tag tagname in the package packagename.

Example:
```
examplebetonquesttagskill:
  TargetConditions:
  - bqhastag{tag=blabla;p=default;a=true}
  Skills:
  - message{msg="taget has the betonquest tag blabla in package default!"} @world
```



## The SkillMechanic: betonquest

Syntax? - Use as MythicMobs skill. 

### - betonquest{package=packagename;conversation=conversationname;startsignal=startconv;endsignal=endconv}

Usage: This mechanics needs an player as trigger. Otherwise it would not work, ofcourse.

Example:
```
mobfile:

Monkey:
  Type: cow
  Display: 'a MythicMobs Monkey'
  AIGoalSelectors:
  - 0 clear
  - 1 randomstroll
  Skills:
  - betonquest{package=default;conversation=innkeeper;startsignal=startconv;endsignal=endconv} @trigger ~onInteract 1
  - skill{s=bqStartConv} @trigger ~onSignal:startconv 1
  - skill{s=bqEndConv} @trigger ~onSignal:endconv 1

skillfile:

bqStartConv:
  Skills:
  - runaigoalselector{s=clear} @self
  - delay 1
  - runaigoalselector{s=lookatplayers} @self
  
bqEndConv:
  Skills:
  - RunAIGoalSelector{s=clear} @self
  - delay 1
  - RunAIGoalSelector{s=randomstroll} @self
```

This example starts the default innkeeper conversation shipped with betonquest for everyplayer who right click the mob. You can use startsignal and endsignal optional to notify the mob whenever a conversation is start- or ended. Usefull if you like to change the mobs pathfindergoals or such. The conversation end signal will be only send if there is no other player having a conversation started with this mob.
