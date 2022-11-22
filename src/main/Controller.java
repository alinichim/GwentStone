package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.Coordinates;
import gamedev.BattleGround;
import gamedev.Hero;
import gamedev.Row;
import gamedev.Card;
import gamedev.BattlingCard;
import gamedev.Minion;
import gamedev.Player;
import gamedev.PlayerHUD;
import gamedev.SpecialMinion;
import gamedev.SpellCard;

import java.util.ArrayList;
import java.util.Arrays;

// Interface for lambda expression.
interface Displayer {
    void display(ObjectNode node, Object o);
}

public final class Controller {
    private static final ArrayList<String> FRONT_ROW_CARDS =
            new ArrayList<>(Arrays.asList("The Ripper", "Miraj", "Goliath", "Warden"));
    private static final ArrayList<String> TANKS =
            new ArrayList<>(Arrays.asList("Goliath", "Warden"));
    private static final ArrayList<String> SPELLS =
            new ArrayList<>(Arrays.asList("Winterfell", "Firestorm", "Heart Hound"));

    // Token to check if the game has ended.
    private static boolean endGame;

    private Controller() { }

    /**
     * This method is the interface between the functionality of the game and the input.
     * @param bg The BattleGround.
     * @param action The ActionsInput.
     * @param out The out ArrayNode with the resulted state.
     */
    public static void executeCommand(
            final BattleGround bg,
            final ActionsInput action,
            final ArrayNode out
    ) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        Displayer defaultDisplayer = (ObjectNode nd, Object message) -> {
            nd.putPOJO("command", action.getCommand());
            nd.putPOJO("output", message);
        };

        switch (action.getCommand()) {
            case "endPlayerTurn" -> bg.endTurn();
            case "placeCard" -> {
                Displayer displayer = (ObjectNode nd, Object errorMsg) -> {
                    nd.putPOJO("command", action.getCommand());
                    nd.putPOJO("handIdx", 0);
                    nd.putPOJO("error", errorMsg);
                };
                placeCard(bg, action, node, displayer);
            }
            case "cardUsesAttack" -> {
                Displayer displayer = (ObjectNode nd, Object errorMsg) -> {
                    nd.putPOJO("command", action.getCommand());
                    nd.putPOJO("cardAttacker", action.getCardAttacker());
                    nd.putPOJO("cardAttacked", action.getCardAttacked());
                    nd.putPOJO("error", errorMsg);
                };
                cardUsesAttack(bg, action, node, displayer);
            }
            case "cardUsesAbility" -> {
                Displayer displayer = (ObjectNode nd, Object errorMsg) -> {
                    nd.putPOJO("command", action.getCommand());
                    nd.putPOJO("cardAttacker", action.getCardAttacker());
                    nd.putPOJO("cardAttacked", action.getCardAttacked());
                    nd.putPOJO("error", errorMsg);
                };
                cardUsesAbility(bg, action, node, displayer);
            }
            case "useAttackHero" -> {
                Displayer displayer = (ObjectNode nd, Object errorMsg) -> {
                    nd.putPOJO("command", action.getCommand());
                    nd.putPOJO("cardAttacker", action.getCardAttacker());
                    nd.putPOJO("error", errorMsg);
                };
                useAttackHero(bg, action, node, displayer);
            }
            case "useHeroAbility" -> {
                Displayer displayer = (ObjectNode nd, Object errorMsg) -> {
                    nd.putPOJO("command", action.getCommand());
                    nd.putPOJO("affectedRow", action.getAffectedRow());
                    nd.putPOJO("error", errorMsg);
                };
                useHeroAbility(bg, action, node, displayer);
            }
            case "useEnvironmentCard" -> {
                Displayer displayer = (ObjectNode nd, Object errorMsg) -> {
                    nd.putPOJO("command", action.getCommand());
                    nd.putPOJO("handIdx", action.getHandIdx());
                    nd.putPOJO("affectedRow", action.getAffectedRow());
                    nd.putPOJO("error", errorMsg);
                };
                useEnvironmentCard(bg, action, node, displayer);
            }
            case "getCardsInHand" -> {
                Displayer displayer = (ObjectNode nd, Object message) -> {
                    nd.putPOJO("command", action.getCommand());
                    nd.putPOJO("playerIdx", action.getPlayerIdx());
                    nd.putPOJO("output", message);
                };
                getCardsInHand(bg, action, node, displayer);
            }
            case "getPlayerDeck" -> {
                Displayer displayer = (ObjectNode nd, Object message) -> {
                    nd.putPOJO("command", action.getCommand());
                    nd.putPOJO("playerIdx", action.getPlayerIdx());
                    nd.putPOJO("output", message);
                };
                getPlayerDeck(bg, action, node, displayer);
            }
            case "getCardsOnTable" -> getCardsOnTable(bg, node, defaultDisplayer);
            case "getPlayerTurn" -> getPlayerTurn(bg, node, defaultDisplayer);
            case "getPlayerHero" -> {
                Displayer displayer = (ObjectNode nd, Object message) -> {
                    nd.putPOJO("command", action.getCommand());
                    nd.putPOJO("playerIdx", action.getPlayerIdx());
                    nd.putPOJO("output", message);
                };
                getPlayerHero(bg, action, node, displayer);
            }
            case "getCardAtPosition" -> {
                Displayer displayer = (ObjectNode nd, Object message) -> {
                    nd.putPOJO("command", action.getCommand());
                    nd.putPOJO("x", action.getX());
                    nd.putPOJO("y", action.getY());
                    nd.putPOJO("output", message);
                };
                getCardAtPosition(bg, action, node, displayer);
            }
            case "getPlayerMana" -> {
                Displayer displayer = (ObjectNode nd, Object message) -> {
                    nd.putPOJO("command", action.getCommand());
                    nd.putPOJO("playerIdx", action.getPlayerIdx());
                    nd.putPOJO("output", message);
                };
                getPlayerMana(bg, action, node, displayer);
            }
            case "getEnvironmentCardsInHand" -> {
                Displayer displayer = (ObjectNode nd, Object message) -> {
                    nd.putPOJO("command", action.getCommand());
                    nd.putPOJO("playerIdx", action.getPlayerIdx());
                    nd.putPOJO("output", message);
                };
                getEnvironmentCardsInHand(bg, action, node, displayer);
            }
            case "getFrozenCardsOnTable" -> getFrozenCardsOnTable(bg, node, defaultDisplayer);
            case "getTotalGamesPlayed" -> getTotalGamesPlayed(bg, node, defaultDisplayer);
            case "getPlayerOneWins" -> getPlayerOneWins(bg, node, defaultDisplayer);
            case "getPlayerTwoWins" -> getPlayerTwoWins(bg, node, defaultDisplayer);
            default -> { }
        }

        // Check if the enemy hero is dead.
        if (!endGame) {
            Hero h = bg.getPlayers().get((bg.getPlayerTurn() + 1) % 2).getPlayerHUD().getHero();
            if (h.getHealth() <= 0) {
                String playerPos = (bg.getPlayerTurn() == 0) ? "one" : "two";
                node.putPOJO("gameEnded", "Player " + playerPos + " killed the enemy hero.");
                endGame = true;
                bg.getCurrentPlayer().setWins(bg.getCurrentPlayer().getWins() + 1);
            }
        }

        // Check for dead minions.
        for (Row r : bg.getRows()) {
            r.getMinions().removeIf((m) -> m.getHealth() <= 0);
        }

        // Check if the node is empty.
        if (!node.isEmpty()) {
            out.add(node);
        }
    }

    /**
     * This method converts a Card in ObjectNode.
     * @param c The Card.
     * @param node The resulted ObjectNode.
     */
    private static void convertCard(final Card c, final ObjectNode node) {
        node.putPOJO("name", c.getName());
        node.putPOJO("description", c.getDescription());
        node.putPOJO("colors", c.getColors());
        node.putPOJO("mana", c.getMana());

        // Check if this card is not a spell.
        if (c.getType() != 2) {
            node.putPOJO("health", ((BattlingCard) c).getHealth());

            // Check if this card is not a Hero.
            if (c.getType() != Card.HERO_TYPE) {
                node.putPOJO("attackDamage", ((Minion) c).getAttackDamage());
            }
        }
    }

    /**
     * This method converts a list of Cards into an ArrayNode.
     * @param cards The list of Cards.
     * @param arrayNode The resulted ArrayNode.
     */
    private static void convertCardList(final ArrayList<Card> cards, final ArrayNode arrayNode) {
        for (Card c : cards) {
            ObjectNode node = arrayNode.addObject();
            convertCard(c, node);
        }
    }

    /**
     * This method executes the 'placeCard' command.
     * @param bg The BattleGround.
     * @param action The ActionsInput.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void placeCard(
            final BattleGround bg,
            final ActionsInput action,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Get the player's HUD.
        Player p = bg.getCurrentPlayer();
        PlayerHUD hud = p.getPlayerHUD();

        // Get the card.
        Card c = hud.getHand().getCards().get(action.getHandIdx());

        // Check if the card is Environment.
        if (c.getType() == 2) {
            displayer.display(node, "Cannot place environment card on table.");
            return;
        }

        // Check if there is enough mana to place the card.
        if (hud.getMana() < c.getMana()) {
            displayer.display(node, "Not enough mana to place card on table.");
            return;
        }

        // Check if there is space on row.
        ArrayList<Row> targetRows = bg.getCurrentPlayerRows();
        Row targetRow;
        if (FRONT_ROW_CARDS.contains(c.getName())) {
            targetRow = (bg.getPlayerTurn() == 1) ? targetRows.get(1) : targetRows.get(0);
        } else {
            targetRow = (bg.getPlayerTurn() == 1) ? targetRows.get(0) : targetRows.get(1);
        }
        if (targetRow.getMinions().size() == Row.MAX_SIZE) {
            displayer.display(node, "Cannot place card on table since row is full.");
            return;
        }

        // Decrease mana.
        hud.setMana(hud.getMana() - c.getMana());

        // Put the card on the table.
        targetRow.getMinions().add((Minion) c);
        hud.getHand().getCards().remove(c);
    }

    /**
     * This method executes the 'cardUsesAttack' command.
     * @param bg The BattleGround.
     * @param action The ActionsInput.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void cardUsesAttack(
            final BattleGround bg,
            final ActionsInput action,
            final ObjectNode node,
            final Displayer displayer
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
            displayer.display(node, "Attacker card is frozen.");
            return;
        }

        // Check if the card attacked this round.
        if (attacker.getAttacked()) {
            displayer.display(node, "Attacker card has already attacked this turn.");
            return;
        }

        // Check if the cards are allies.
        if (attackerCords.getX() / 2 == attackedCords.getX() / 2) {
            displayer.display(node, "Attacked card does not belong to the enemy.");
            return;
        }

        // Check if there is a tank.
        if (!TANKS.contains(attacked.getName())) {
            Row row = bg.getRows().get(attackedCords.getX() / 2 + 1);
            for (Minion m : row.getMinions()) {
                if (TANKS.contains(m.getName())) {
                    displayer.display(node, "Attacked card is not of type 'Tank'.");
                    return;
                }
            }
        }

        // Attack.
        attacker.attackCommand(attacked);
        attacker.setAttacked(true);
    }

    /**
     * This method executes the 'cardUsesAbility' command.
     * @param bg The BattleGround.
     * @param action The ActionsInput.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    public static void cardUsesAbility(
            final BattleGround bg,
            final ActionsInput action,
            final ObjectNode node,
            final Displayer displayer
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
            displayer.display(node, "Attacker card is frozen.");
            return;
        }

        // Check if the card attacked this round.
        if (attacker.getAttacked()) {
            displayer.display(node, "Attacker card has already attacked this turn.");
            return;
        }

        // Check if the attacker is Disciple.
        if (attacker.getName().equals("Disciple")) {
            if (attackerCords.getX() / 2 != attackedCords.getX() / 2) {
                displayer.display(node, "Attacked card does not belong to the current player.");
                return;
            }
            // Use ability.
            ((SpecialMinion) attacker).useAbility(attacked);
            attacker.setAttacked(true);
            return;
        } else if (attackerCords.getX() / 2 == attackedCords.getX() / 2) {
            // If it's not Disciple, the attacked card should be an enemy.
            displayer.display(node, "Attacked card does not belong to the enemy.");
            return;
        }

        // Check if there is a tank.
        if (!TANKS.contains(attacked.getName()) && !attacker.getName().equals("Disciple")) {
            Row row = bg.getRows().get(attackedCords.getX() / 2 + 1);
            for (Minion m : row.getMinions()) {
                if (TANKS.contains(m.getName())) {
                    displayer.display(node, "Attacked card is not of type 'Tank'.");
                    return;
                }
            }
        }

        // Use ability.
        ((SpecialMinion) attacker).useAbility(attacked);
        attacker.setAttacked(true);
    }

    /**
     * This method executes the 'useAttackHero' command.
     * @param bg The BattleGround.
     * @param action The ActionsInput.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    public static void useAttackHero(
            final BattleGround bg,
            final ActionsInput action,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Get the coordinates of the attacker.
        Coordinates attackerCords = action.getCardAttacker();

        // Get the Cards.
        Minion attacker =
                bg.getRows().get(attackerCords.getX()).getMinions().get(attackerCords.getY());

        // Check if the attacker is frozen.
        if (attacker.getFrozen()) {
            displayer.display(node, "Attacker card is frozen.");
            return;
        }

        // Check if the card attacked this round.
        if (attacker.getAttacked()) {
            displayer.display(node, "Attacker card has already attacked this turn.");
            return;
        }

        // Check if there is a tank.
        Row row = bg.getRows().get((BattleGround.MAX_ROWS - 1 - attackerCords.getX()) / 2 + 1);
        for (Minion m : row.getMinions()) {
            if (TANKS.contains(m.getName())) {
                displayer.display(node, "Attacked card is not of type 'Tank'.");
                return;
            }
        }

        // Get the hero.
        Hero hero = bg.getPlayers().get((bg.getPlayerTurn() + 1) % 2).getPlayerHUD().getHero();
        // Attack.
        attacker.attackCommand(hero);
        attacker.setAttacked(true);
    }

    /**
     * This method executes the 'useHeroAbility' command.
     * @param bg The BattleGround.
     * @param action The ActionsInput.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    public static void useHeroAbility(
            final BattleGround bg,
            final ActionsInput action,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Get player's HUD.
        PlayerHUD hud = bg.getPlayers().get(bg.getPlayerTurn()).getPlayerHUD();

        // Get the hero.
        Hero hero = hud.getHero();

        // Check if there is enough mana to use the ability.
        if (hud.getMana() < hero.getMana()) {
            displayer.display(node, "Not enough mana to use hero's ability.");
            return;
        }

        // Check if the hero already attacked this round.
        if (hero.getAttacked()) {
            displayer.display(node, "Hero has already attacked this turn.");
            return;
        }

        // Check if the target is valid.
        String heroName = hero.getName();
        int affectedRow = action.getAffectedRow();
        if (heroName.equals("Lord Royce") || heroName.equals("Empress Thorina")) {
            if (affectedRow / 2 != bg.getPlayerTurn()) {
                displayer.display(node, "Selected row does not belong to the enemy.");
                return;
            }
        } else if (affectedRow / 2 == bg.getPlayerTurn()) {
            displayer.display(node, "Selected row does not belong to the current player.");
            return;
        }

        // Use the ability.
        hero.useAbility(bg, affectedRow);
        hud.setMana(hud.getMana() - hero.getMana());
        hero.setAttacked(true);
    }

    /**
     * This method executes the 'useEnvironmentCard' command.
     * @param bg The BattleGround.
     * @param action The ActionsInput.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void useEnvironmentCard(
            final BattleGround bg,
            final ActionsInput action,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Get handIdx and affectedRow.
        int handIdx = action.getHandIdx(), affectedRow = action.getAffectedRow();

        // Get the current player's HUD.
        PlayerHUD currHUD = bg.getPlayers().get(bg.getPlayerTurn()).getPlayerHUD();

        // Get the environment card.
        Card c = currHUD.getHand().getCards().get(handIdx);

        // Check if the card is a spell.
        if (!SPELLS.contains(c.getName())) {
            displayer.display(node, "Chosen card is not of type environment.");
            return;
        }

        // Check if the player has enough mana.
        if (currHUD.getMana() < c.getMana()) {
            displayer.display(node, "Not enough mana to use environment card.");
            return;
        }

        // Check if the target row is the enemy's row.
        if (bg.getPlayerTurn() != affectedRow / 2) {
            displayer.display(node, "Chosen row does not belong to the enemy.");
            return;
        }

        // Check if there's enough space in case of 'Heart Hound'.
        if (c.getName().equals("Heart Hound")) {
            Row targetRow = bg.getRows().get(BattleGround.MAX_ROWS - 1 - affectedRow);
            if (targetRow.getMinions().size() == Row.MAX_SIZE) {
                displayer.display(node, "Cannot steal enemy card since the player's row is full.");
                return;
            }
        }

        // Use spell.
        ((SpellCard) c).useSpell(bg, affectedRow);
        currHUD.setMana(currHUD.getMana() - c.getMana());

        // Discard this spell.
        currHUD.getHand().getCards().remove(c);
    }


    /**
     * This method executes the 'getCardsInHand' command.
     * @param bg The BattleGround.
     * @param action The ActionsInput.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void getCardsInHand(
            final BattleGround bg,
            final ActionsInput action,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Get the player.
        Player p = bg.getPlayers().get(action.getPlayerIdx() - 1);

        // Get the player's cards in hand.
        ArrayList<Card> cards = p.getPlayerHUD().getHand().getCards();

        // Create new ArrayNode.
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        convertCardList(cards, arrayNode);

        // Display data in node.
        displayer.display(node, arrayNode);
    }

    /**
     * This method executes the 'getPlayerDeck' command.
     * @param bg The BattleGround.
     * @param action The ActionsInput.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void getPlayerDeck(
            final BattleGround bg,
            final ActionsInput action,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Get the player.
        Player p = bg.getPlayers().get(action.getPlayerIdx() - 1);

        // Get the player's cards in deck.
        ArrayList<Card> cards = p.getPlayerHUD().getDeck().getCards();

        // Create new ArrayNode.
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        convertCardList(cards, arrayNode);

        // Display data in node.
        displayer.display(node, arrayNode);
    }

    /**
     * This method executes the 'getCardsOnTable' command.
     * @param bg The BattleGround.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void getCardsOnTable(
            final BattleGround bg,
            final ObjectNode node,
            final Displayer displayer
    ) {


        // Create new ArrayNode.
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode rowsArr = mapper.createArrayNode();

        // Add the cards on the table.
        for (Row r : bg.getRows()) {
            // Create a new list.
            ArrayList<Card> cards = new ArrayList<>(r.getMinions());

            // Create new ArrayNode.
            ArrayNode arr = mapper.createArrayNode();

            // Convert cards.
            convertCardList(cards, arr);

            rowsArr.add(arr);
        }

        // Display data in node.
        displayer.display(node, rowsArr);
    }

    /**
     * This method executes the 'getPlayerTurn' command.
     * @param bg The BattleGround.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void getPlayerTurn(
            final BattleGround bg,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Get the current player index.
        int idx = bg.getPlayerTurn() + 1;

        // Display data in node.
        displayer.display(node, idx);
    }

    /**
     * This method executes the 'getPlayerHero' command.
     * @param bg The BattleGround.
     * @param action The ActionsInput.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void getPlayerHero(
            final BattleGround bg,
            final ActionsInput action,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Get the player.
        Player p = bg.getPlayers().get(action.getPlayerIdx() - 1);

        // Get player's hero.
        Hero h = p.getPlayerHUD().getHero();

        // Create new ObjectNode.
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode newNode = mapper.createObjectNode();
        convertCard(h, newNode);

        // Display data in node.
        displayer.display(node, newNode);
    }

    /**
     * This method executes the 'getCardAtPosition' command.
     * @param bg The BattleGround.
     * @param action The ActionsInput.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void getCardAtPosition(
            final BattleGround bg,
            final ActionsInput action,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Get the row cards.
        ArrayList<Minion> minions = bg.getRows().get(action.getX()).getMinions();

        // Check if the card is in the row.
        if (action.getY() >= minions.size()) {
            // Display error.
            displayer.display(node, "No card available at that position.");
            return;
        }

        // Create new ObjectNode.
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode card = mapper.createObjectNode();
        convertCard(minions.get(action.getY()), card);

        // Display data in node.
        displayer.display(node, card);

        // Display data in node.
        displayer.display(node, card);
    }

    /**
     * This method executes the 'getPlayerMana' command.
     * @param bg The BattleGround.
     * @param action The ActionsInput.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void getPlayerMana(
            final BattleGround bg,
            final ActionsInput action,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Get the player.
        Player p = bg.getPlayers().get(action.getPlayerIdx() - 1);

        // Display data in node.
        displayer.display(node, p.getPlayerHUD().getMana());
    }

    /**
     * This method executes the 'getEnvironmentCardsInHand' command.
     * @param bg The BattleGround.
     * @param action The ActionsInput.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void getEnvironmentCardsInHand(
            final BattleGround bg,
            final ActionsInput action,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Get the player.
        Player p = bg.getPlayers().get(action.getPlayerIdx() - 1);

        // Get the player's cards in hand.
        ArrayList<Card> handCards = p.getPlayerHUD().getHand().getCards();

        // Filter cards.
        ArrayList<Card> cards = new ArrayList<>();
        for (Card c : handCards) {
            if (SPELLS.contains(c.getName())) {
                cards.add(c);
            }
        }

        // Create new ArrayNode.
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        convertCardList(cards, arrayNode);

        // Display data in node.
        displayer.display(node, arrayNode);
    }

    /**
     * This method executes the 'getFrozenCardsOnTable' command.
     * @param bg The BattleGround.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void getFrozenCardsOnTable(
            final BattleGround bg,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Create new list.
        ArrayList<Card> cards = new ArrayList<>();

        // Find frozen cards.
        for (Row r : bg.getRows()) {
            for (Minion m : r.getMinions()) {
                if (m.getFrozen()) {
                    cards.add(m);
                }
            }
        }

        // Create new ArrayNode.
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        convertCardList(cards, arrayNode);

        // Display data in node.
        displayer.display(node, arrayNode);
    }

    /**
     * This method executes the 'getTotalGamesPlayed' command.
     * @param bg The BattleGround.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void getTotalGamesPlayed(
            final BattleGround bg,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Get players.
        ArrayList<Player> players = bg.getPlayers();

        // Calculate number of games played.
        int games = players.get(0).getWins() + players.get(1).getWins();

        // Display data in node.
        displayer.display(node, games);
    }

    /**
     * This method executes the 'getPlayerOneWins' command.
     * @param bg The BattleGround.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void getPlayerOneWins(
            final BattleGround bg,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Get player.
        Player p = bg.getPlayers().get(0);

        // Display data in node.
        displayer.display(node, p.getWins());
    }

    /**
     * This method executes the 'getPlayerTwoWins' command.
     * @param bg The BattleGround.
     * @param node The ObjectNode.
     * @param displayer The Displayer.
     */
    private static void getPlayerTwoWins(
            final BattleGround bg,
            final ObjectNode node,
            final Displayer displayer
    ) {
        // Get player.
        Player p = bg.getPlayers().get(1);

        // Display data in node.
        displayer.display(node, p.getWins());
    }

    /**
     * This method returns the 'endGame' state of the game.
     * @return The 'endGame' state.
     */
    public static boolean getEndGame() {
        return endGame;
    }

    /**
     * This method sets the 'endGame' state of the game.
     * @param endGame The 'endGame' state.
     */
    public static void setEndGame(final boolean endGame) {
        Controller.endGame = endGame;
    }
}
