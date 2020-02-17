**Version: 0.0.82 (BETA)**

* MultiTool preview rendering is working correctly.
* MultiTool teleportation works again (could use some heavy dupe testing)
* Interface Rendering now kinda works
* OBJ Block/Item Rendering Works again (Thanks abused_master)

------------------------------------------------------------------
**Version: 0.0.81 (BETA)**

* Go back to vanilla rendering so at least blocks have some texture (even though they look wacky).
* Working on MultiTool Rendering. Its not on correct layer but placing/remove block is not visible.
* Jar should be signed properly again.

------------------------------------------------------------------
**Version: 0.0.80 (BETA)**

* Fix #91. Completely disabled rendering code.

-------------------------------------------------------------------
**Version: 0.0.79 (BETA)**

* Initial 1.15.2 Port. All rendering is basicly disabled. Items all appear to function correctly.

-------------------------------------------------------------------
**Version: 0.0.78 (BETA)**

* Fix #44 for at least zombies (Render MultiArmor on non-players).
* Changed up armor rendering in a lot of ways, should be faster now as well.
* Fix Linking Card / Multi Armor Settings Editor textures.

-------------------------------------------------------------------
**Version: 0.0.77 (BETA)**

* Only attempt to load armor textures for Atlas it is needed in
* OBJ Block and Item Models
* Multi Tool no longer can place in thin air
* Cleaned up Assets folder

-------------------------------------------------------------------
**Version: 0.0.76 (BETA)**

* Forge now has Mouse Scroll events so removing my direct LWJGL injection
* Remove fluid code now that is breaking since forge changed signatures.
* General Code cleanup

-------------------------------------------------------------------
**Version: 0.0.76 (BETA)**

* Mark Container packet handled so it does not cause a print statement.
* New Models for some blocks
* Multi-Tool Assist render now shows the placed state of block, and actually places that block

-------------------------------------------------------------------
**Version: 0.0.75 (BETA)**

* Updated to Forge 28.0.100
* Loading models now. Look correct in world but in inventory / as item
* Nether Star Block is now Animated

-------------------------------------------------------------------
**Version: 0.0.74 (BETA)**

* Updated to new Forge Fluid API
* Add real texture for Instant Furnace
* Fix Instant Furnace Dupe

-------------------------------------------------------------------
**Version: 0.0.73 (BETA)**

* Fix True Infinity Barrel Stack Overflow Crash.
* Remove un-needed extra event listener now that forge fires the event properly.

-------------------------------------------------------------------
**Version: 0.0.72 (BETA)**

* Add translation for Instant Furnace

-------------------------------------------------------------------
**Version: 0.0.71 (BETA)**

* Forge Version Bump to >= 45

-------------------------------------------------------------------
**Version: 0.0.70 (BETA)**

* Fixed some GUI bugs with the Instant Furnace 
* Instant Furnace now has recipe
* ItemInterface now drops its item again on break

-------------------------------------------------------------------
**Version: 0.0.69 (BETA)**

* Added new block. Instant Furnace. Instantly smelts using RF.

-------------------------------------------------------------------
**Version: 0.0.68 (BETA)**

* Updated to 1.14.4

-------------------------------------------------------------------
**Version: 0.0.67 (BETA)**

* Re-enable Remove Harmful from MultiArmor
* Requires Forge 59+ due to sided issues with Potions that are fixed in that version

-------------------------------------------------------------------
**Version: 0.0.66 (BETA)**

* Add event to MultiTool place again.

-------------------------------------------------------------------
**Version: 0.0.65 (BETA)**

* Change Lang files on Keybinds
* Speed up OBJ load times

-------------------------------------------------------------------
**Version: 0.0.64 (BETA)**

* Fire event when removing potion affects so mods that don't want their affects remove, arn't
* Change MultiTool assist rendering order so can see the full remove preview
* ReAdd left clicking blocks for multi-tool in single player (temporary fix)
* Add a scroll event for use in RailGun / MultiTool
* Bumping up to beta

-------------------------------------------------------------------
**Version: 0.0.63 (ALPHA)**

* Remove Shadow from under Players rendered in player interface
* MultiTool preview render looks better now
* MultiTool remove rendering now appears. Need to work on some transparency issues
* Working on MultiArmor GUI. Mostly works
* Prevent de-syncing issues with Item Interface
* New textures for the Almost/True Infinite Containers
* Adding Server Side checking for MultiTool block clicks as it isn't firing client side (forge bug #5802)
* Disabling Remove Harmful from MultiArmor due to Forge #5920 (causes client to be kicked)
* Fix energy extraction not saying enabled when energy amount is not disable by 4, and using 4x the cost.

-------------------------------------------------------------------
**Version: 0.0.62 (ALPHA)**

* Multi-Tool now renders block to place correctly for all viewpoints.

-------------------------------------------------------------------
**Version: 0.0.62 (ALPHA)**

* Null check due to forge not coping capabilities on player death (#73)

-------------------------------------------------------------------
**Version: 0.0.61 (ALPHA)**

* Fix crash on reloading texture pack (#72)

-------------------------------------------------------------------
**Version: 0.0.60 (ALPHA)**

* Everything has recipes again
* True Infinity Storage containers
* Worked on rendering some more
* Should not crash anything. Tested SP and MP.

-------------------------------------------------------------------
**Version: 0.0.59 (ALPHA)**

* Initial 1.14 Port
* Many Features are Missing
* Textures are Missing
* Will be reworking storage containers so do not expect them to keep contents between updates 

-------------------------------------------------------------------
**Version: 0.0.58 (RELEASE)**

* Fixes fluid storage not allowing un-filtered extraction (Fixes #71)

-------------------------------------------------------------------
**Version: 0.0.57 (RELEASE)**

* Fixes fluid storage converting fluids (Fixes #69)
* Compiled against forge version bump

-------------------------------------------------------------------
**Version: 0.0.56 (RELEASE)**

* Multi-Armor Helmet now looks forward when on armor stands.
* Updated Gradle build system
* Correct Spelling Error (Fixes #68)
* Add countermeasure for invalid state changes of Overloaded's power items (Fixes #65)
* Change Render format to block for ghost blocks (changes text render posistion)

-------------------------------------------------------------------
**Version: 0.0.55 (RELEASE)**

* Added Waila (Hwyla) support for Hyper Energy Capability (Infinity Capacitor)

-------------------------------------------------------------------
**Version: 0.0.54 (RELEASE)**

* Update to new Forge and Mapping snapshots.
* Fix default compressed netherrack looking like Cobblestone. Will have to manually reset config to see change (or apply change yourself).
* Improve Multi-Armor rendering on Non-player entities
* Add config entry to make Infinity Container have two slots. This is to help with Vanilla but is a performance decrease. Default to disabled. 
* Update invalid fingerprint message to say report to cjm721
* Forgot to do sided checks for some Tile Entities (cause weirdness in single player only)
* Wrote a test (Really wish I could write tests for everything, but Minecraft makes it very annoying)

-------------------------------------------------------------------
**Version: 0.0.53 (RELEASE)**

* Refactor ItemMultiTool (Code cleanup)
* Add lang file for zn_cn (provided by DazzleCool)

-------------------------------------------------------------------
**Version: 0.0.52 (RELEASE)**

* Multi-Tool now gives better error message when unable to break / place
* Multi-Tool now also respect's server's range limits
* Code Cleanup
  * Dynamic Registration now in single place
  * Abstracted multi-armor rendering
* **New config entry for item texture size**. Before item texture size was tied to block resolution
* Config is now synced from server to client
* Config rewrite - physical files are the same though   
* Can now upload to Curse from command line (No mod changes)
* Forge version compiled against bumped up to 2611, should still be compatible with old versions

-------------------------------------------------------------------
**Version: 0.0.51 (RELEASE)**

* Enable ground speed modifier again. (Why can't flight and walk speed be done the same way... 
to many one offs for my liking, although I like how ground speed is done now, might make some more features out of it) 

-------------------------------------------------------------------
**Version: 0.0.50.1 (RELEASE)**

Hotfix to disable ground speed modifications

* jar is now signed (Because why not?)
* Disable ground speed modifications as the way I am currently doing it breaks servers

-------------------------------------------------------------------
**Version: 0.0.50 (RELEASE)**

Mostly quality of life changes / just getting the code base in a better state.

* Changelog will now be appended with previous info (pulled changelogs from last two days also)
* Added One Probe Integration for Item Interface / Player Interface / Hyper * Senders / Infinity Capacitor
* Linking Card will no longer forget the receiver when binding
* Nether Star Block is now Ore Dictionaryed to blockNetherStar 
* Code Cleanup using IDEA's automated tasks 
* Added charged variants of all powered items to creative menu.

Fixed:
* NPE if you somehow placed a Player Interface without setting the placer

-------------------------------------------------------------------
**Version: 0.0.49 (RELEASE)**

* Multi-Armor Settings Editor Implemented (+Recipe)
  * Right Click in GUI to change to more precise input mode for sliders.
* Multi-Armor Changes
  * Ground speed increase now uses power
    * Has config options for balancing
  * Flight has additional config options for balancing.
  * Max Speed limits are also in config
  * Most features can now be enabled/disabled via the settings editor.
  * Settings are saved to the helmet.
  
Fixed:
* Compressed Block now have blast resistance that corresponds to their hardness.

-------------------------------------------------------------------
**Version: 0.0.48 (BETA)**

Making build as beta as most likely will also push an update within next 24 hours to move settings GUI out of Dev Mode. All changes should be stable.

* Railgun implemented and moved out of dev only mode
  * Has recipe (that needs balancing)
    * Many config options for balancing
    * Sneak Scroll to change by Power Delta
      * Ctrl + Sneak to change by 100 * Power Delta
* Cleaned up Generic Data Storage to be more efficient and persist settings (used by armor and railgun)
* Cleaned up locale file as it had old entires

Dev Mode:
* Working on Settings GUI for the Multi-Armor