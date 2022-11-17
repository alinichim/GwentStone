package main;

import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fileio.*;

import gamedev.*;

// Interface for lambda expression.
interface Displayer {
	void display(ObjectNode node, Object o);
}

public class Controller {
	static private ArrayList<String> frontRowCards =
		new ArrayList<String>(Arrays.asList(new String[] {"The Ripper", "Miraj", "Goliath", "Warden"}));
	static private ArrayList<String> backRowCards =
		new ArrayList<String>(Arrays.asList(new String[] {"Sentinel", "Berseker", "The Cursed One", "Disciple"}));
	static private ArrayList<String> tanks = new ArrayList<String>(Arrays.asList(new String[] {"Goliath", "Warden"}));
	private static ArrayList<String> spells =
		new ArrayList<String>(Arrays.asList(new String[] {"Winterfell", "Firestorm", "Heart Hound"}));

	public Controller() {}

	static public ObjectNode executeCommand(BattleGround bg, ActionsInput action) {
		// Create new Object Mapper.
		ObjectMapper mapper = new ObjectMapper();

		// Create ObjectNode to map the out data.
		ObjectNode node = mapper.createObjectNode();

		switch (action.getCommand()) {
			case "endPlayerTurn":
				{
					bg.endTurn();
					break;
				}
			case "placeCard":
				{
					Displayer disp = (ObjectNode node, String errorMsg) -> {
						node.put("command", "placeCard");
						node.put("handIdx", 0);
						node.put("error", errorMsg);
					};
					placeCard(bg, action, node, disp);
					break;
				}
			case "cardUsesAttack":
				{
					Displayer disp = (ObjectNode node, String errorMsg) -> {
						node.put("command", "cardUsesAttack");
						node.put("cardAttacker", action.getCardAttacker());
						node.put("cardAttacked", action.getCardAttacked());
						node.put("error", errorMsg);
					};
					cardUsesAttack(bg, action, node, disp);
					break;
				}
			case "cardUsesAbility":
				{
					Displayer disp = (ObjectNode node, String errorMsg) -> {
						node.put("command", "cardUsesAbility");
						node.put("cardAttacker", action.getCardAttacker());
						node.put("cardAttacked", action.getCardAttacked());
						node.put("error", errorMsg);
					};
					cardUsesAbility(bg, action, node, disp);
					break;
				}
			case "useAttackHero":
				{
					Displayer disp = (ObjectNode node, String errorMsg) -> {
						node.put("command", "useAttackHero");
						node.put("cardAttacker", action.getCardAttacker());
						node.put("error", errorMsg);
					};
					useAttackHero(bg, action, node, disp);
					break;
				}
			case "useHeroAbility":
				{
					Displayer disp = (ObjectNode node, String errorMsg) -> {
						node.put("command", "useHeroAbility");
						node.put("affectedRow", action.getAffectedRow());
						node.put("error", errorMsg);
					};
					useHeroAbility(bg, action, node, disp);
					break;
				}
			case "useEnvironmentCard":
				{
					Displayer disp = (ObjectNode node, String errorMsg) -> {
						node.put("command", "useEnvironmentCard");
						node.put("handIdx", action.getHandIdx());
						node.put("affectedRow", action.getAffectedRow());
						node.put("error", errorMsg);
					};
					useEnvironmentCard(bg, acion, node, disp);
					break;
				}



				// TODO: get hand cards.
				// TODO: get deck cards.
				// TODO: get table cards.
				// TODO: get active player.
				// TODO: get player's hero.
				// TODO: get card at position.
				// TODO: get mana.
				// TODO: get frozen cards.
				// TODO: get games played.
				// TODO: get total wins.
		}

		// Check if the node was modified.
		if (node != null)
			out.addAll(node);

		// TODO: Check for dead minions.
		return node;
	}

	// Command to end player's turn.
	static private void endPlayerTurn(BattleGround bg) {
		bg.endTurn();
		break;
	}

	// Command to place a card on table.
	static private void placeCard(
			BattleGround bg,
			ActionsInput action,
			ObjectNode node,
			Displayer disp
		) {
		// Get the player's HUD.
		Player p = bg.getCurrentPlayer();
		PlayerHUD hud = p.getPlayerHUD();

		// Get the card.
		Card c = hud.getHand().getCards().get(action.getHandIdx());

		// Check if the card is Environment.
		if (c.getType() == 2) {
			disp.display(node, "Cannot place environment card on table.");
			return;
		}

		// Check if there is enough mana to place the card.
		if (hud.getMana() < c.getMana()) {
			disp.display(node, "Not enough mana to place card on table.");
			return;
		}

		// Check if there is space on row.
		ArrayList<Row> targetRows = bg.getCurrentPlayerRows();
		Row targetRow;
		if (frontRowCards.get(c.getName()) != -1) {
			targetRow = (bg.getPlayerTurn() == 0) ? targetRows.get(1) : targetRows.get(0);
		} else {
			targetRow =	(bg.getPlayerTurn() == 0) ? targetRows.get(0) : targetRows.get(1);
		}
		if (targetRow.size() == 5) {
			disp.display(node, "Cannot place card on table since row is full.");
			return;
		}

		// Decrease mana.
		hud.setMana(hud.getMana() - c.getMana());

		// Put the card on the table.
		targetRow.add(c);
	}

	// Command to use card attack.
	static private void cardUsesAttack(
			BattleGround bg,
			ActionsInput action,
			ObjectNode node,
			Displayer disp
		) {
		// Get the coordinates of the attacker and the attacked.
		Coordinates attackerCords = action.getCardAttacker();
		Coordinates attackedCords = action.getCardAttacked();

		// Get the Cards.
		Minion attacker =
			bg.getRows().get(attackerCords.getX()).getMinions().get(attackerCords.getY());
		Minion attacked =
			bg.getRows().get(attackedCords.getX()).getMinions().get(attackedCords.getY());

		// Check if the attacker is frozen.
		if (attacker.getFrozen()) {
			disp.display(node, "Attacker card is frozen.");
			return;
		}

		// Check if the card attacked this round.
		if (attacker.getAttacked()) {
			disp.display(node, "Attacker card has already attacked this turn.");
			return;
		}

		// Check if the cards are allies.
		if (attackerCords.getX() / 2 == attackedCords.getX() / 2) {
			disp.display(node, "Attacked card does not belong to the enemy.");
			return;
		}

		// Check if there is a tank.
		if (tanks.indexOf(attacked.getName()) == -1) {
			Row row = bg.getRows().get(attackedCords.getX() / 2 + 1);
			for (Minion m : row.getMinions())
				if (tanks.indexOf(m.getName()) != -1) {
					disp.display(node, "Attacked card is not of type 'Tank'.");
					return;
				}
		}

		// Attack.
		attacker.attackCommand(attacked);
		attacker.setAttacked(true);
	}

	// Command to use the ability of a special minion.
	public static void cardUsesAbility(
			BattleGround bg,
			ActionsInput action,
			ObjectNode node,
			Displayer disp
		) {
		// Get the coordinates of the attacker and the attacked.
		Coordinates attackerCords = action.getCardAttacker();
		Coordinates attackedCords = action.getCardAttacked();

		// Get the Cards.
		Minion attacker =
			bg.getRows().get(attackerCords.getX()).getMinions().get(attackerCords.getY());
		Minion attacked =
			bg.getRows().get(attackedCords.getX()).getMinions().get(attackedCords.getY());

		// Check if the attacker is frozen.
		if (attacker.getFrozen()) {
			disp.display(node, "Attacker card is frozen.");
			return;
		}

		// Check if the card attacked this round.
		if (attacker.getAttacked()) {
			disp.display(node, "Attacker card has already attacked this turn.");
			return;
		}

		// Check if the attacker is Disciple.
		if (attacker.getName().equals("Disciple")) {
			if (attackerCords.getX() / 2 != attackedCords.getX() / 2) {
				disp.display(node, "Attacked card does not belong to the current player.");
			}
			return;
		} else if (attackerCords.getX() / 2 == attackedCords.getX() / 2) {
			// If it's not Disciple, the attacked card should be an enemy.
			disp.display(node, "Attacked card does not belong to the enemy.");
			return;
		}

		// Check if there is a tank.
		if (tanks.indexOf(attacked.getName()) == -1 && !attacker.getName().equals("Disciple")) {
			Row row = bg.getRows().get(attackedCords.getX() / 2 + 1);
			for (Minion m : row.getMinions())
				if (tanks.indexOf(m.getName()) != -1) {
					disp.display(node, "Attacked card is not of type 'Tank'.");
					return;
				}
		}

		// Use ability.
		attacker.useAbility(attacked);
		attacker.setAttacked(true);
	}
	
	// Command to attack a hero.
	public static void useAttackHero(
			BattleGround bg,
			ActionsInput action,
			ObjectNode node,
			Displayer disp
		) {
		// Get the coordinates of the attacker.
		Coordinates attackerCords = action.getCardAttacker();

		// Get the Cards.
		Minion attacker =
			bg.getRows().get(attackerCords.getX()).getMinions().get(attackerCords.getY());

		// Check if the attacker is frozen.
		if (attacker.getFrozen()) {
			disp.display(node, "Attacker card is frozen.");
			return;
		}

		// Check if the card attacked this round.
		if (attacker.getAttacked()) {
			disp.display(node, "Attacker card has already attacked this turn.");
			return;
		}

		// Check if there is a tank.
		Row row = bg.getRows().get((3 - attackerCords.getX()) / 2 + 1);
		for (Minion m : row.getMinions())
			if (tanks.indexOf(m.getName()) != -1) {
				disp.display(node, "Attacked card is not of type 'Tank'.");
				return;
			}

		// Get the hero.
		Hero hero = bg.getPlayers().get(bg.getPlayerTurn()).getPlayerHUD().getHero();
		// Attack.
		attacker.attackCommand(hero);
		attacker.setAttacked(true);
	}

	// Command to use hero's ability.
	public static void useHeroAbility(
			BattleGround bg,
			ActionsInput action,
			ObjectNode node,
			Displayer disp
		) {
		// Get player's HUD.
		PlayerHUD hud = bg.getPlayers().get(bg.getPlayerTurn()).getPlayerHUD();

		// Get the hero.
		Hero hero = hud.getHero();

		// Check if there is enough mana to use the ability.
		int refMana = hud.getMana();
		if (refMana < hero.getMana()) {
			disp.display(node, "Not enough mana to use hero's ability.");
			return;
		}

		// Check if the hero already attacked this round.
		if (hero.getAttacked()) {
			disp.display(node, "Hero has already attacked this turn.");
			return;
		}

		// Check if the target is valid.
		String heroName = hero.getName();
		int affectedRow = action.getAffectedRow();
		if (heroName.equals("Lord Royce") || heroName.equals("Empress Thorina")) {
			if (affectedRow / 2 == bg.getPlayerTurn()) {
				disp.display(node, "Selected row does not belong to the enemy.");
				return;
			}
		} else if (affectedRow / 2 != bg.getPlayerTurn()) {
			disp.display(node, "Selected row does not belong to the current player.");
			return;
		}

		// Use the ability.
		hero.useAbility(bg, affectedRow);
		hero.setAttacked(true);
	}

	// Command to use an environment card.
	private static void useEnvironmentCard(
			BattleGround bg,
			ActionsInput action,
			ObjectNode node,
			Displayer disp
		) {
		// Get handIdx and affectedRow.
		int handIdx = action.getHandIdx(), affectedRow = action.getAffectedRow();

		// Get the current player's HUD.
		PlayerHUD currHUD = bg.getPlayers().get(bg.getPlayerTurn()).getPlayerHUD();

		// Get the environment card.
		Card c = currHUD.getHand().getCards().get(handIdx);

		// Check if the the card is a spell.
		if (spells.indexOf(c.getName()) == -1) {
			disp.display(node, "Chosen card is not of type environment.");
			return;
		}

		// Check if the player has enough mana.
		if (hud.getMana() < c.getMana()) {
			disp.display(node, "Not enough mana to use environment card.");
			return;
		}

		// Check if the target row is the enemy's row.
		if (affectedRow != bg.getPlayerTurn() / 2) {
			disp.display(node, "Chosen row does not belong to the enemy.");
			return;
		}

		// Check if there's enough space in case of 'Heart Hound'.
		if (c.getName().equals("Heart Hound")) {
			Row targetRow = bg.getRows().get(3 - affectedRow);
			if (targetRow.getCards().size() == 5) {
				disp.display(node, "Cannot steal enemy card since the player's row is full.");
				return;
			}
		}

		// Use spell.
		((SpellCard) c).useSpell(bg, affectedRow);

		// Discard this spell.
		hud.getHand().getCards().remove(c);
	}

	// TODO: statistic functions
	
	// Command to get the cards in hand of a player.
	private static void getCardsInHand(
			BattleGround bg,
			ActionsInput action,
			ObjectNode node,
			Displayer outDisp
			) {
		// Get the player.
		Player p = bg.getPlayers().get(action.getPlayerIdx());

		// Get the player's cards in hand.
		ArrayList<Card> cards = p.getPlayerHUD().getHand().getCards();

		// Display the data in node.
		outDisp.display(node, cards);
	}

	// Command to get the cards in player's deck.
	private static void getPlayerDeck(
			BattleGround bg,
			ActionsInput action,
			ObjectNode node,
			Displayer outDisp
			) {
		// Get the player.
		Player p = bg.getPlayers().get(action.getPlayerIdx());

		// Get the player's cards in deck.
		ArrayList<Card> cards = p.getPlayerHUD().getDeck().getCards();

		// Display the data in node.
		outDisp.display(node, cards);
	}

	// Command to get all the cards on the table.
	private static void getCardsOnTable(
			BattleGround bg,
			ObjectNode node,
			Displayer outDisp
			) {
		// Create a new list.
		ArrayList<Card> cards = new ArrayList<Card>();

		// Add the cards on the table.
		for (Row r : bg.getRows()) cards.addAll(r.getCards());

		// Display data in node.
		outDisp.display(node, cards);
	}

	// Command to get the current player.
	private static void getPlayerTurn(
			BattleGround bg,
			ObjectNode node,
			Displayer outDisp
			) {
		// Get the current player index.
		int idx = bg.getPlayerTurn() + 1;
		
		// Display data in node.
		outDisp.display(node, idx);
	}

	// Command to display player's hero.
	private static void getPlayerHero(
			BattleGround bg,
			ActionsInput action,
			ObjectNode node,
			Displayer outDisp
			) {
		// Get the player.
		Player p = bg.getPlayers().get(action.getPlayerIdx() - 1);

		// Get player's hero.
		Hero h = p.getPlayerHUD().getHero();

		// Display data in node.
		outDisp.display(node, h);
	}

	// Command to get a card at position.
	private static void getCardAtPosition(
			BattleGround bg,
			ActionsInput action,
			ObjectNode node,
			Displayer outDisp
			) {
		// Get the row cards.
		ArrayList<Card> cards = bg.getRows().get(action.getX()).getCards();

		// Check if the card is in the row.
		if (action.getY() >= cards.size()) {
			// Display error.
			outDisp(node, "No card at that position.");
			return;
		}

		// Display data in node.
		outDisp(node, cards.get(action.getY()));
	}

	// Command to get the mana of a player.
	private static void getPlayerMana(
			BattleGround bg,
			ActionsInput action,
			ObjectNode node,
			Displayer outDisp
			) {
		// Get the player.
		Player p = bg.getPlayers().get(action.getPlayerIdx() - 1);

		// Display data in node.
		outDisp.display(node, p.getPlayerHUD().getMana());
	}

	// Command to get the environment cards in player's hand.
	private static void getPlayerDeck(
			BattleGround bg,
			ActionsInput action,
			ObjectNode node,
			Displayer outDisp
			) {
		// Get the player.
		Player p = bg.getPlayers().get(action.getPlayerIdx());

		// Get the player's cards in hand.
		ArrayList<Card> handCards = p.getPlayerHUD().getHand().getCards();

		// Filter cards.
		ArrayList<Card> cards = new ArrayList<Card>();
		for (Card c : handCards)
			if (spells.indexOf(c.getName()) != -1) cards.add(c);

		// Display the data in node.
		outDisp.display(node, cards);
	}

	// Command to get all frozen cards.
	private static void getFrozenCardsOnTable(
			BattleGround bg,
			ObjectNode node,
			Displayer outDisp
			) {
		// Create new list.
		ArrayList<Card> cards = new ArrayList<Card>();

		// Find frozen cards.
		for (Row r : bg.getRows()) {
			for (Card c : r.getCards()) {
				if (c.getFrozen()) cards.add(c);
			}
		}

		// Display data in node.
		outDisp.display(node, cards);
	}

	// Command to get total games played.
	private static void getTotalGamesPlayed(
			BattleGround bg,
			ObjectNode node,
			Displayer outDisp
			) {
		// Get players.
		ArrayList<Player> players = bg.getPlayers();

		// Calculate number of games played.
		int games = players.get(0).getWins() + player.get(1).getWins();

		// Display data in node.
		outDisp.display(node, games);
	}

	// Command to get player one wins.
	private static void getPlayerOneWins(
			BattleGround bg,
			ObjectNode node,
			Displayer outDisp
			) {
		// Get player.
		Player p = bg.getPlayers().get(0);

		// Display data in node.
		outDisp.display(node, p.getWins());
	}

	// Command to get player two wins.
	private static void getPlayerTwoWins(
			BattleGround bg,
			ObjectNode node,
			Displayer outDisp
			) {
		// Get player.
		Player p = bg.getPlayers().get(1);

		// Display data in node.
		outDisp.display(node, p.getWins());
	}
}
