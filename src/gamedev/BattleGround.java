package gamedev;

import java.util.ArrayList;

import gamedev.Player;
import gamedev.PlayerHUD;
import gamedev.Deck;
import gamedev.CardCollection;
import gamedev.Row;

public class BattleGround {
	private int playerTurn, turns = 0;
	private ArrayList<Player> players;
	private ArrayList<Row> rows;

	public BattleGround(
			int playerTurn,
			ArrayList<Player> players,
			ArrayList<Row> rows
		) {
		this.playerTurn = playerTurn % 2;
		this.players = new ArrayList<Player>();
		for (Player p : players) this.players.add(new Player(p));
		this.rows = new ArrayList<Row>();
		for (Row r : rows) this.rows.add(new Row(r));
	}
	
	public BattleGround() {
		this(0, new ArrayList<Player>(), new ArrayList<Row>());
	}

	public BattleGround(BattleGround bg) {
		this(bg.getPlayerTurn(), bg.getPlayers(), bg.getRows());
	}

	// When the turn ends, the mana is filled up, minions are unfrozen and
	// each player takes one card from their package.
	public void endTurn() {
		Player p = players.get(playerTurn);
		PlayerHUD hud = p.getPlayerHUD();
		
		// Fill mana.
		turns++;
		if (turns >= 10) hud.setMana(hud.getMana() + 10);
		else hud.setMana(hud.getMana() + turns);

		// Unfreeze minions and set the 'attacked' attribute to false.
		ArrayList<Row> targetRows = getCurrentPlayerRows();
		for (Row r : targetRows)
			for (Minion m : r.getMinions()) {
				m.setFrozen(false);
				m.setAttacked(false);
			}

		// Set the 'attacked' attribute on hero to false.
		hud.getHero().setAttacked(false);

		// Players draw cards.
		hud.getHand().getCards().add(hud.getDeck().getCards().remove(0));
		playerTurn = (playerTurn + 1) % 2;
		hud = players.get(playerTurn).getPlayerHUD();
		hud.getHand().getCards().add(hud.getDeck().getCards().remove(0));
	}

	// Helper function to get the rows of the current player.
	public ArrayList<Row> getCurrentPlayerRows() {
		return (ArrayList<Row>) rows.subList(playerTurn * 2, playerTurn * 2 + 2);
	}


	// Helper function to get the current player.
	public Player getCurrrentPlayer() {
		return players.get(playerTurn);
	}

	// Getters.
	public int getPlayerTurn() { return playerTurn; }
	public ArrayList<Player> getPlayers() { return players; }
	public ArrayList<Row> getRows () { return rows; }
	public int getTurns() { return turns; }
	// Setters.
	public void setTurns(int turns) { this.turns = turns; }
	public void setPlayerTurn(int playerTurn) { this.playerTurn = playerTurn % 2; }
	public void setPlayers(ArrayList<Player> players) {
		this.players.clear();
		for (Player p : players) this.players.add(new Player(p));
	}
	public void setRows(ArrayList<Row> rows) {
		this.rows.clear();
		for (Row row : rows) this.rows.add(new Row(row));
	}
}
