/*
 * Copyright (c) 2022, Adam <Adam@sigterm.info>
 * Copyright (c) 2022, Ankou <https://github.com/AnkouOSRS>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.unpottedreminder;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("unpottedreminder")
public interface UnpottedReminderConfig extends Config
{
	@ConfigSection(name = "Melee", description = "Melee reminders", position = 25, closedByDefault = true)
	String MELEE_SECTION = "melee";

	@ConfigSection(name = "Ranged", description = "Ranged reminders", position = 26, closedByDefault = true)
	String RANGED_SECTION = "ranged";

	@ConfigSection(name = "Magic", description = "Magic reminders", position = 27, closedByDefault = true)
	String MAGIC_SECTION = "magic";

	@ConfigSection(name = "Surge", description = "Surge potion reminders", position = 28, closedByDefault = true)
	String SURGE_SECTION = "surge";

	@ConfigSection(name = "Prayer Regeneration", description = "Prayer regeneration reminders", position = 29, closedByDefault = true)
	String PRAYER_SECTION = "prayer";

	@ConfigItem(
			keyName = "enableMelee",
			name = "Alert for Melee",
			description = "Whether or not the warning should display when attacking with melee",
			position = 1, section = MELEE_SECTION
	)
	default boolean enableMelee()
	{
		return true;
	}

	@ConfigItem(
			keyName = "meleeAlertStyle",
			name = "Melee Alert Style",
			description = "Which attack style boost will alert you when using melee",
			position = 2, section = MELEE_SECTION
	)
	default MeleeAlertStyle meleeAlertStyle()
	{
		return MeleeAlertStyle.ATTACK_AND_STRENGTH;
	}

	@ConfigItem(
			keyName = "enableRanged",
			name = "Alert for Ranged",
			description = "Whether or not the warning should display when attacking with ranged",
			position = 1, section = RANGED_SECTION
	)
	default boolean enableRanged()
	{
		return true;
	}

	@ConfigItem(
			keyName = "enableMagic",
			name = "Alert for Magic",
			description = "Whether or not the warning should display when attacking with magic",
			position = 1, section = MAGIC_SECTION
	)
	default boolean enableMagic()
	{
		return false;
	}


	@ConfigItem(
			keyName = "meleeBoostThreshold",
			name = "Melee Boost Threshold",
			description = "Don't alert when melee stats are boosted above this amount",
			position = 3, section = MELEE_SECTION
	)
	default int meleeBoostThreshold()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "rangedBoostThreshold",
			name = "Ranged Boost Threshold",
			description = "Don't alert when ranged stats are boosted above this amount",
			position = 2, section = RANGED_SECTION
	)
	default int rangedBoostThreshold()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "magicBoostThreshold",
			name = "Magic Boost Threshold",
			description = "Don't alert when magic stats are boosted above this amount",
			position = 2, section = MAGIC_SECTION
	)
	default int magicBoostThreshold()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "timeout",
			name = "Timeout",
			description = "Stop showing warning after this long in seconds (unless triggered again)",
			position = 5, section = MELEE_SECTION
	)
	@Units(Units.SECONDS)
	default int meleeTimeout()
	{
		return 10;
	}

	@ConfigItem(
			keyName = "experienceThreshold",
			name = "Xp Threshold",
			description = "Don't alert when xp drop is more than this amount (0 to disable)",
			position = 4, section = MELEE_SECTION
	)
	default int meleeExperienceThreshold()
	{
		return 500;
	}

	@ConfigItem(
			keyName = "shouldFlash",
			name = "Flash overlay",
			description = "Whether or not the overlay should flash colors",
			position = 6, section = MELEE_SECTION
	)
	default boolean meleeShouldFlash()
	{
		return false;
	}

	@Alpha
	@ConfigItem(
			keyName = "flashColor1",
			name = "Flash color 1",
			description = "First color to flash between if 'Flash overlay' is on",
			position = 7, section = MELEE_SECTION
	)
	default Color meleeFlashColor1()
	{
		return new Color(0, 128, 255, 150);
	}

	@Alpha
	@ConfigItem(
			keyName = "flashColor2",
			name = "Flash color 2",
			description = "Second color to flash between if 'Flash overlay' is on",
			position = 8, section = MELEE_SECTION
	)
	default Color meleeFlashColor2()
	{
		return new Color(50, 50, 50, 150);
	}

	@ConfigItem(keyName = "rangedExperienceThreshold", name = "Xp Threshold",
			description = "Don't alert when xp drop is more than this amount (0 to disable)",
			position = 3, section = RANGED_SECTION)
	default int rangedExperienceThreshold()
	{
		return 500;
	}

	@Units(Units.SECONDS)
	@ConfigItem(keyName = "rangedTimeout", name = "Timeout",
			description = "Stop showing warning after this long in seconds (unless triggered again)",
			position = 4, section = RANGED_SECTION)
	default int rangedTimeout()
	{
		return 10;
	}

	@ConfigItem(keyName = "rangedShouldFlash", name = "Flash overlay",
			description = "Whether or not the overlay should flash colors", position = 5, section = RANGED_SECTION)
	default boolean rangedShouldFlash()
	{
		return false;
	}

	@Alpha
	@ConfigItem(keyName = "rangedFlashColor1", name = "Flash color 1",
			description = "First color to flash between if 'Flash overlay' is on", position = 6, section = RANGED_SECTION)
	default Color rangedFlashColor1()
	{
		return new Color(0, 128, 255, 150);
	}

	@Alpha
	@ConfigItem(keyName = "rangedFlashColor2", name = "Flash color 2",
			description = "Second color to flash between if 'Flash overlay' is on", position = 7, section = RANGED_SECTION)
	default Color rangedFlashColor2()
	{
		return new Color(50, 50, 50, 150);
	}

	@ConfigItem(keyName = "rangedAlertMessage", name = "Alert message",
			description = "Text shown in the overlay, infobox tooltip, and notification", position = 8, section = RANGED_SECTION)
	default String rangedAlertMessage()
	{
		return "Drink a boost potion!";
	}

	@ConfigItem(keyName = "magicExperienceThreshold", name = "Xp Threshold",
			description = "Don't alert when xp drop is more than this amount (0 to disable)",
			position = 3, section = MAGIC_SECTION)
	default int magicExperienceThreshold()
	{
		return 500;
	}

	@Units(Units.SECONDS)
	@ConfigItem(keyName = "magicTimeout", name = "Timeout",
			description = "Stop showing warning after this long in seconds (unless triggered again)",
			position = 4, section = MAGIC_SECTION)
	default int magicTimeout()
	{
		return 10;
	}

	@ConfigItem(keyName = "magicShouldFlash", name = "Flash overlay",
			description = "Whether or not the overlay should flash colors", position = 5, section = MAGIC_SECTION)
	default boolean magicShouldFlash()
	{
		return false;
	}

	@Alpha
	@ConfigItem(keyName = "magicFlashColor1", name = "Flash color 1",
			description = "First color to flash between if 'Flash overlay' is on", position = 6, section = MAGIC_SECTION)
	default Color magicFlashColor1()
	{
		return new Color(0, 128, 255, 150);
	}

	@Alpha
	@ConfigItem(keyName = "magicFlashColor2", name = "Flash color 2",
			description = "Second color to flash between if 'Flash overlay' is on", position = 7, section = MAGIC_SECTION)
	default Color magicFlashColor2()
	{
		return new Color(50, 50, 50, 150);
	}

	@ConfigItem(keyName = "magicAlertMessage", name = "Alert message",
			description = "Text shown in the overlay, infobox tooltip, and notification", position = 8, section = MAGIC_SECTION)
	default String magicAlertMessage()
	{
		return "Drink a boost potion!";
	}

	@ConfigItem(
			keyName = "showOverlay",
			name = "Show overlay",
			description = "Whether or not to show a visual alert when warning you to pot",
			position = 13
	)
	default boolean showOverlay()
	{
		return true;
	}

	@ConfigItem(
			keyName = "alertDisplayMode",
			name = "Alert display",
			description = "Whether the visual alert is shown as a screen overlay or an infobox alongside your other infoboxes",
			position = 14
	)
	default AlertDisplayMode alertDisplayMode()
	{
		return AlertDisplayMode.OVERLAY;
	}

	@ConfigItem(
			keyName = "shouldNotify",
			name = "Notify",
			description = "Whether or not to notify you when warning you to pot",
			position = 15
	)
	default boolean shouldNotify()
	{
		return false;
	}

	@Units(Units.SECONDS)
	@ConfigItem(
			keyName = "notifyCooldown",
			name = "Notify Cooldown",
			description = "Seconds until notifier can be triggered again (0 to disable)",
			position = 16
	)
	default int notifyCooldown()
	{
		return 5;
	}

	@ConfigItem(
			keyName = "useWhitelist",
			name = "Enable NPC whitelist",
			description = "Whether or not to only alert when attacking NPCs in the list below (comma-separated)",
			position = 17
	)
	default boolean useWhitelist()
	{
		return false;
	}

	@ConfigItem(
			keyName = "whitelist",
			name = "NPC Whitelist",
			description = "Only alert when attacking NPCs in this comma-separated list when toggled above (supports wildcards)",
			position = 18
	)
	default String whitelist()
	{
		return "";
	}

	@ConfigItem(
			keyName = "useBlacklist",
			name = "Enable NPC blacklist",
			description = "Whether or not to alert when attacking NPCs in the list below (comma-separated)",
			position = 19
	)
	default boolean useBlacklist()
	{
		return false;
	}

	@ConfigItem(
			keyName = "blacklist",
			name = "NPC Blacklist",
			description = "Don't alert when attacking NPCs in this comma-separated list when toggled above (supports wildcards)",
			position = 20
	)
	default String blacklist()
	{
		return "";
	}

	@ConfigItem(
			keyName = "alertWhenNotInteracting",
			name = "Alert when not targeting any NPC",
			description = "Whether or not to alert when you are not interacting with an NPC",
			position = 21
	)
	default boolean alertWhenNotInteracting()
	{
		return false;
	}

	@ConfigItem(
			keyName = "onlyInInstances",
			name = "Only alert in instances",
			description = "Whether or not to only alert when you are in an instanced area in-game",
			position = 22
	)
	default boolean onlyInInstances()
	{
		return false;
	}

	@ConfigItem(
			keyName = "alertMessage",
			name = "Alert message",
			description = "The message to display in the overlay and notification. Also shown as the infobox tooltip",
			position = 9, section = MELEE_SECTION
	)
	default String meleeAlertMessage()
	{
		return "Drink a boost potion!";
	}

	@ConfigItem(keyName = "enableSurge", name = "Alert for Surge",
			description = "Remind when a Surge potion can restore special attack energy", position = 1, section = SURGE_SECTION)
	default boolean enableSurge()
	{
		return false;
	}

	@Units(Units.SECONDS)
	@ConfigItem(keyName = "surgeTimeout", name = "Timeout",
			description = "Stop showing the reminder after this many seconds", position = 2, section = SURGE_SECTION)
	default int surgeTimeout()
	{
		return 10;
	}

	@ConfigItem(keyName = "surgeShouldFlash", name = "Flash overlay",
			description = "Whether or not the overlay should flash colors", position = 3, section = SURGE_SECTION)
	default boolean surgeShouldFlash()
	{
		return false;
	}

	@Alpha
	@ConfigItem(keyName = "surgeFlashColor1", name = "Flash color 1",
			description = "First overlay color", position = 4, section = SURGE_SECTION)
	default Color surgeFlashColor1()
	{
		return new Color(89, 123, 132, 150);
	}

	@Alpha
	@ConfigItem(keyName = "surgeFlashColor2", name = "Flash color 2",
			description = "Second overlay color", position = 5, section = SURGE_SECTION)
	default Color surgeFlashColor2()
	{
		return new Color(50, 50, 50, 150);
	}

	@ConfigItem(keyName = "surgeAlertMessage", name = "Alert message",
			description = "Text shown in the overlay, infobox tooltip, and notification",
			position = 6, section = SURGE_SECTION)
	default String surgeAlertMessage()
	{
		return "Drink a Surge potion!";
	}

	@ConfigItem(keyName = "enablePrayer", name = "Alert for Prayer Regeneration",
			description = "Prayer Enhance also qualifies; remind when neither effect is active", position = 1, section = PRAYER_SECTION)
	default boolean enablePrayer()
	{
		return false;
	}

	@Units(Units.SECONDS)
	@ConfigItem(keyName = "prayerTimeout", name = "Timeout",
			description = "Stop showing the reminder after this many seconds", position = 2, section = PRAYER_SECTION)
	default int prayerTimeout()
	{
		return 10;
	}

	@ConfigItem(keyName = "prayerShouldFlash", name = "Flash overlay",
			description = "Whether or not the overlay should flash colors", position = 3, section = PRAYER_SECTION)
	default boolean prayerShouldFlash()
	{
		return false;
	}

	@Alpha
	@ConfigItem(keyName = "prayerFlashColor1", name = "Flash color 1",
			description = "First overlay color", position = 4, section = PRAYER_SECTION)
	default Color prayerFlashColor1()
	{
		return new Color(140, 89, 80, 150);
	}

	@Alpha
	@ConfigItem(keyName = "prayerFlashColor2", name = "Flash color 2",
			description = "Second overlay color", position = 5, section = PRAYER_SECTION)
	default Color prayerFlashColor2()
	{
		return new Color(50, 50, 50, 150);
	}

	@ConfigItem(keyName = "prayerAlertMessage", name = "Alert message",
			description = "Text shown in the overlay, infobox tooltip, and notification",
			position = 6, section = PRAYER_SECTION)
	default String prayerAlertMessage()
	{
		return "Activate prayer regeneration!";
	}

	@ConfigItem(
			keyName = "useVialIcon",
			name = "Show vial icon instead of text on overlay",
			description = "Whether or not to show an empty vial icon instead of the alert message. Always on for the infobox display",
			position = 24
	)
	default boolean useVialIcon()
	{
		return false;
	}
}
