# MythicMobsKillObjective & MythicMobsSpawnEvent for BetonQuest 1.8.5 or smaller and MythicMobs 4.0.0 or higher
https://www.spigotmc.org/resources/betonquest.2117/


## The Objective: mmMythicMobsKillObjective

How to install? - Just copy the jar file into your plugins folder and restart your server.

Syntax? - mmMythicMobsKillObjective type:mmtype1,mmtype2,mmtype3 name:This_is_a_Name,This_is_Another_name level:1-5 amount:5 notify events:whatever

Type or name must be given. Or booth. If type is given then name can be optional and vise versa. type and name can be a list. Example: type:mob1,mob2,mob3 or name:name1,name2,name3

If you use space in names then replace them with "_" in the objective. Example: mobdisplayname "a Mythic Mob" must be "a_Mythic_Mob" in the objective.

Level: Optional. Can be a single value or ranged. Ranged looks this: level:3-8 = all mobs with level between 3 and 8 matches.


## The Event: mmMythicMobsSpawnEvent

Syntax? - mmMythicMobsSpawnEvent x,y,z,worldname mobtype:level amount


x,y,z are the coordinates followed by the worldname. mobtype is the mobtype followed by the moblevel. amount is how many mobs will be spawned.

Example: mmMythicMobsSpawnEvent 0,70,0,world BigBoss:99 1
This will spawn one mob of the type BigBoss at the location 0,70,0 in world with the level of 99.

