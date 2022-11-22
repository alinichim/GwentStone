package gamedev;

import java.util.ArrayList;
import java.util.Arrays;

public final class BattleGround {
    public static final int MAX_ROWS = 4, TURNS_THRESHOLD = 20, MAX_MANA_INC = 10;
    private final ArrayList<Player> players;
    private final ArrayList<Row> rows;
    private int playerTurn, turns = 0;

    public BattleGround(
            final int playerTurn,
            final ArrayList<Player> players,
            final ArrayList<Row> rows
    ) {
        this.playerTurn = playerTurn % 2;
        this.players = new ArrayList<>();
        for (Player p : players) {
            this.players.add(new Player(p));
        }
        this.rows = new ArrayList<>(MAX_ROWS);
        for (Row r : rows) {
            this.rows.add(new Row(r));
        }
    }

    public BattleGround() {
        this(0, new ArrayList<>(), new ArrayList<>(MAX_ROWS));
        for (int i = 0; i < MAX_ROWS; ++i) {
            rows.add(new Row());
        }
    }

    public BattleGround(final BattleGround bg) {
        this(bg.getPlayerTurn(), bg.getPlayers(), bg.getRows());
    }

    /**
     * This function is used to take care of the steps required at the end of a round.
     */
    public void endTurn() {
        // Get players.
        Player currPlayer = players.get(playerTurn);
        Player nextPlayer = players.get((playerTurn + 1) % 2);
        // Get HUDs.
        PlayerHUD currHUD = currPlayer.getPlayerHUD();
        PlayerHUD nextHUD = nextPlayer.getPlayerHUD();

        // Fill mana.
        if (turns % 2 == 0) {
            if (turns >= TURNS_THRESHOLD) {
                currHUD.setMana(currHUD.getMana() + MAX_MANA_INC);
                nextHUD.setMana(nextHUD.getMana() + MAX_MANA_INC);
            } else {
                currHUD.setMana(currHUD.getMana() + turns / 2 + 1);
                nextHUD.setMana(nextHUD.getMana() + turns / 2 + 1);
            }
        }

        // Unfreeze minions and set the 'attacked' attribute to false.
        ArrayList<Row> targetRows = getCurrentPlayerRows();
        for (Row r : targetRows) {
            for (Minion m : r.getMinions()) {
                m.setFrozen(false);
                m.setAttacked(false);
            }
        }

        // Set the 'attacked' attribute on hero to false.
        currHUD.getHero().setAttacked(false);

        // Players draw cards.
        if (turns % 2 == 0) {
            if (!currHUD.getDeck().getCards().isEmpty()) {
                currHUD.getHand().getCards().add(currHUD.getDeck().getCards().remove(0));
            }
            if (!nextHUD.getDeck().getCards().isEmpty()) {
                nextHUD.getHand().getCards().add(nextHUD.getDeck().getCards().remove(0));
            }
        }
        turns++;

        playerTurn = (playerTurn + 1) % 2;
    }

    /**
     * @return The current player's rows.
     */
    public ArrayList<Row> getCurrentPlayerRows() {
        int a = (playerTurn + 1) % 2;
        Row r1 = rows.get(a * 2);
        Row r2 = rows.get(a * 2 + 1);
        return new ArrayList<>(Arrays.asList(r1, r2));
    }

    // Helper function to get the current player.
    public Player getCurrentPlayer() {
        return players.get(playerTurn);
    }

    // Getters.
    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(final int playerTurn) {
        this.playerTurn = playerTurn % 2;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * This function sets the players for the battleground, by deep-copying them.
     * @param players The list from which the players are copied.
     */
    public void setPlayers(final ArrayList<Player> players) {
        this.players.clear();
        for (Player p : players) {
            this.players.add(new Player(p));
        }
    }

    public ArrayList<Row> getRows() {
        return rows;
    }

    /**
     * This function sets the rows of the battleground, by deep-copying them.
     * @param rows The list from which the rows are copied.
     */
    public void setRows(final ArrayList<Row> rows) {
        this.rows.clear();
        for (Row row : rows) {
            this.rows.add(new Row(row));
        }
    }

    public int getTurns() {
        return turns;
    }

    // Setters.
    public void setTurns(final int turns) {
        this.turns = turns;
    }
}
