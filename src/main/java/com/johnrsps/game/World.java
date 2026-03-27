package com.johnrsps.game;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class World {
    private static final Set<Player> players = Collections.synchronizedSet(new HashSet<>());
    private static final Set<Npc> npcs = Collections.synchronizedSet(new HashSet<>());

    public static void addPlayer(Player player) { players.add(player); }
    public static void removePlayer(Player player) { players.remove(player); }
    public static Set<Player> getPlayers() { return Collections.unmodifiableSet(players); }
    public static void addNpc(Npc npc) { npcs.add(npc); }
    public static void removeNpc(Npc npc) { npcs.remove(npc); }
    public static Set<Npc> getNpcs() { return Collections.unmodifiableSet(npcs); }
}