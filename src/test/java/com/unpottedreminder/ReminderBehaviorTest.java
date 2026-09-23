package com.unpottedreminder;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.Skill;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.VarbitID;
import org.junit.Test;

import static com.unpottedreminder.UnpottedReminderPlugin.AlertType.*;
import static org.junit.Assert.*;

public class ReminderBehaviorTest
{
	@Test
	public void combatSettingsStayWithTheirStyle()
	{
		UnpottedReminderConfig config = new UnpottedReminderConfig()
		{
			@Override public int meleeExperienceThreshold() { return 100; }
			@Override public int rangedExperienceThreshold() { return 200; }
			@Override public int magicExperienceThreshold() { return 300; }
			@Override public int meleeTimeout() { return 1; }
			@Override public int rangedTimeout() { return 2; }
			@Override public int magicTimeout() { return 3; }
			@Override public Color meleeFlashColor1() { return Color.RED; }
			@Override public Color rangedFlashColor1() { return Color.GREEN; }
			@Override public Color magicFlashColor1() { return Color.BLUE; }
			@Override public String meleeAlertMessage() { return "Melee"; }
			@Override public String rangedAlertMessage() { return "Ranged"; }
			@Override public String magicAlertMessage() { return "Magic"; }
		};

		assertEquals(100, UnpottedReminderPlugin.experienceThreshold(config, MELEE));
		assertEquals(200, UnpottedReminderPlugin.experienceThreshold(config, RANGED));
		assertEquals(300, UnpottedReminderPlugin.experienceThreshold(config, MAGIC));
		assertEquals(1, UnpottedReminderPlugin.timeout(config, MELEE));
		assertEquals(2, UnpottedReminderPlugin.timeout(config, RANGED));
		assertEquals(3, UnpottedReminderPlugin.timeout(config, MAGIC));
		assertEquals(Color.RED, UnpottedReminderPlugin.flashColor1(config, MELEE));
		assertEquals(Color.GREEN, UnpottedReminderPlugin.flashColor1(config, RANGED));
		assertEquals(Color.BLUE, UnpottedReminderPlugin.flashColor1(config, MAGIC));
		assertEquals("Melee", UnpottedReminderPlugin.resolveAlertMessage(config, MELEE));
		assertEquals("Ranged", UnpottedReminderPlugin.resolveAlertMessage(config, RANGED));
		assertEquals("Magic", UnpottedReminderPlugin.resolveAlertMessage(config, MAGIC));
	}

	@Test
	public void meleeReminderNeedsAnAvailablePotionForAnUnboostedSelectedStat()
	{
		UnpottedReminderConfig config = new UnpottedReminderConfig() { };
		Map<Skill, Integer> boosts = new HashMap<>();
		boosts.put(Skill.ATTACK, 5);
		boosts.put(Skill.STRENGTH, 0);
		assertTrue(UnpottedReminderPlugin.meleeReminderNeeded(config, boosts, true, true));
		boosts.put(Skill.STRENGTH, 5);
		assertFalse(UnpottedReminderPlugin.meleeReminderNeeded(config, boosts, true, true));
		boosts.put(Skill.ATTACK, 0);
		assertTrue(UnpottedReminderPlugin.meleeReminderNeeded(config, boosts, true, false));
		assertFalse(UnpottedReminderPlugin.meleeReminderNeeded(config, boosts, false, true));
		assertFalse(UnpottedReminderPlugin.meleeReminderNeeded(new UnpottedReminderConfig()
		{
			@Override public MeleeAlertStyle meleeAlertStyle() { return MeleeAlertStyle.STR_ONLY; }
		}, boosts, true, false));
	}

	@Test
	public void activeInfoBoxReadsTheCurrentMessage()
	{
		AtomicReference<String> message = new AtomicReference<>("Before");
		UnpottedReminderConfig config = new UnpottedReminderConfig()
		{
			@Override public String prayerAlertMessage() { return message.get(); }
		};
		UnpottedReminderInfoBox infoBox = new UnpottedReminderInfoBox(
			new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new UnpottedReminderPlugin(), config, PRAYER);
		assertEquals("Before", infoBox.getTooltip());
		message.set("After");
		assertEquals("After", infoBox.getTooltip());
	}

	@Test
	public void utilityRemindersRequireAUsablePotionAndInactiveEffect()
	{
		Map<Integer, Integer> varbits = new HashMap<>();
		AtomicInteger energy = new AtomicInteger(500);
		Client client = (Client) Proxy.newProxyInstance(Client.class.getClassLoader(), new Class[]{Client.class},
			(proxy, method, args) -> method.getName().equals("getVarbitValue")
				? varbits.getOrDefault((Integer) args[0], 0) : energy.get());
		UnpottedReminderConfig config = new UnpottedReminderConfig()
		{
			@Override public boolean enableSurge() { return true; }
			@Override public boolean enablePrayer() { return true; }
			@Override public int surgeTimeout() { return 4; }
			@Override public int prayerTimeout() { return 5; }
			@Override public Color surgeFlashColor1() { return Color.YELLOW; }
			@Override public Color prayerFlashColor1() { return Color.MAGENTA; }
		};
		Item[] items = {new Item(ItemID._1DOSESURGE, 1), new Item(ItemID.RAIDS_VIAL_PRAYER_STRONG_1, 1),
			new Item(ItemID._1DOSE1PRAYER_REGENERATION, 1)};

		assertTrue(UnpottedReminderPlugin.utilityNeeded(SURGE, config, client, items));
		assertTrue(UnpottedReminderPlugin.utilityNeeded(PRAYER, config, client, items));
		for (int dose : new int[]{ItemID.RAIDS_VIAL_PRAYER_WEAK_1, ItemID.RAIDS_VIAL_PRAYER_WEAK_4,
			ItemID.RAIDS_VIAL_PRAYER_1, ItemID.RAIDS_VIAL_PRAYER_4,
			ItemID.RAIDS_VIAL_PRAYER_STRONG_1, ItemID.RAIDS_VIAL_PRAYER_STRONG_4})
		{
			Item[] enhance = {new Item(dose, 1)};
			assertTrue(UnpottedReminderPlugin.utilityNeeded(PRAYER, config, client, enhance));
		}
		assertEquals(4, UnpottedReminderPlugin.timeout(config, SURGE));
		assertEquals(5, UnpottedReminderPlugin.timeout(config, PRAYER));
		assertEquals(Color.YELLOW, UnpottedReminderPlugin.flashColor1(config, SURGE));
		assertEquals(Color.MAGENTA, UnpottedReminderPlugin.flashColor1(config, PRAYER));
		assertEquals("Activate prayer regeneration!", UnpottedReminderPlugin.resolveAlertMessage(config, PRAYER));
		energy.set(750);
		assertTrue(UnpottedReminderPlugin.utilityNeeded(SURGE, config, client, items));
		energy.set(760);
		assertFalse(UnpottedReminderPlugin.utilityNeeded(SURGE, config, client, items));
		energy.set(500);
		varbits.put(VarbitID.SURGE_POTION_TIMER, 1);
		assertFalse(UnpottedReminderPlugin.utilityNeeded(SURGE, config, client, items));
		varbits.put(VarbitID.RAIDS_PRAYERENHANCE_TIMER, 1);
		assertFalse(UnpottedReminderPlugin.utilityNeeded(PRAYER, config, client, items));
		varbits.remove(VarbitID.RAIDS_PRAYERENHANCE_TIMER);
		varbits.put(VarbitID.PRAYER_REGENERATION_POTION_TIMER, 1);
		assertFalse(UnpottedReminderPlugin.utilityNeeded(PRAYER, config, client, items));
		assertFalse(UnpottedReminderPlugin.utilityNeeded(SURGE, new UnpottedReminderConfig() { }, client, items));
		assertFalse(UnpottedReminderPlugin.utilityNeeded(PRAYER, new UnpottedReminderConfig() { }, client, items));
	}
}
