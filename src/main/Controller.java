package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;

import gamedev.BattleGround;

public class Controller {
	static private ArrayList<String> frontRowCards =
		new ArrayList<String>(Arrays.asList({"The Ripper", "Miraj", "Goliath", "Warden"}));
	static private ArrayList<String> backRowCards =
		new ArrayList<String>(Arrays.asList({"Sentinel", "Berseker", "The Cursed One", "Disciple"}));
	static private ArrayList<String> tanks = new ArrayList<String>(Arrays.asList({"Goliath", "Warden"}));

	public Controller() {}

	static public ObjectNode executeCommand(BattleGround bg, ActionsInput action, ObjectMapper mapper) {
		// Create ObjectNode to map the out data.
		ObjectNode node = mapper.createObjectNode();

		// Add the command to node.
		node.put("command", action.getCommand());

		switch (action.getCommand()) {
			case "endPlayerTurn":
				{
				}
			case "placeCard":
				{
				}
			case "cardUsesAttack":
				{
				}
				// TODO: ability attack.
				// TODO: hero ability attack.
				// TODO: env card.
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
	}

	// Command to end player's turn.
	static private void endPlayerTurn(BattleGround bg) {
		bg.endTurn();
		break;
	}

	// Command to place a card on table.
	static private void placeCard(BattleGround bg, ActionsInput action, ObjectNode node) {
		// Add the hand index to this node.
		node.put("handIdx", action.getHandIdx());

		// Get the player's HUD.
		Player p = bg.getCurrentPlayer();
		PlayerHUD hud = p.getPlayerHUD();

		// Get the card.
		Card c = hud.getHand().getCards().get(action.getHandIdx());

		// Check if the card is Environment.
		if (c.getType() == 2) {
			node.put("error", "Cannot place environment card on table.");
			return;
		}

		// Check if there is enough mana to place the card.
		if (hud.getMana() < c.getMana()) {
			node.put("error", "Not enough mana to place card on table.");
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
			node.put("error", "Cannot place environment card on table since row is full.");
			return;
		}
		targetRow.add(c);
	}

	// Command to use card attack.
	static private void cardUsesAttack(BattleGround bg, ActionsInput action, ObjectNode node) {
		// Get the coordinates of the attacker and the attacked.
		Coordinates attackerCords = action.getCardAttacker();
		Coordinates attackedCords = action.getCardAttacked();

		// Add coordinates to node.
		node.put("cardAttacker", attackerCords);
		node.put("cardAttacked", attackedCords);

		// Get the Cards.
		Minion attacker =
			bg.getRows().get(attackerCords.getX()).getMinions().get(attackerCords.getY());
		Minion attacked =
			bg.getRows().get(attackedCords.getX()).getMinions().get(attackedCords.getY());

		// Check if the attacker is frozen.
		if (attacker.getFrozen()) {
			node.put("error", "Attacker card is frozen.");
			return;
		}

		// Check if the card attacked this round.
		if (attacker.getAttacked()) {
			node.put("error", "Attacker card has already attacked this turn.");
			return;
		}

		// Check if the cards are allies.
		if (attackerCords.getX() / 2 == attackedCords.getX() / 2) {
			node.put("error", "Attacked card does not belong to the enemy.");
			return;
		}

		// Check if there is a tank.
		if (tanks.indexOf(attacked.getName()) == -1) {
			Row row = bg.getRows().get(attackedCords.getX() / 2 + 1);
			for (Minion m : row.getMinions())
				if (tanks.indexOf(m.getName()) != -1) {
					node.put("error", "Attacked card is not of type 'Tank'.");
					return;
				}
		}

		attacker.attackCommand(attacked);
		attacker.setAttacked(true);
	}

	// Command 

}
