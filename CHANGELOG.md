Version: 0.0.50 (RELEASE)
* Changelog will now be appended with previous info (pulled changelogs from last two days also)
* Added One Probe Integration for Item Interface / Player Interface / Hyper * Senders / Infinity Capacitor
* Linking Card will no longer forget the receiver when binding.

Fixed:
* NPE if you somehow placed a Player Interface without setting the placer

-------------------------------------------------------------------
Version: 0.0.49 (RELEASE)

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
Version: 0.0.48 (BETA)

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