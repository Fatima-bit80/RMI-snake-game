






# RMI-snake-game
real time multiplayer snake game using java RMI, the goal is to become the biggest snake alive. you grow by eating fruits and die by colliding with yourself, other players or the game border




## What is this?

A real-time multiplayer Snake game where up to **6 players** connect to a shared server, wait in a lobby, and compete simultaneously on the same board. All communication (lobby chat, ready signals, direction inputs, and live game state) flows through **Java RMI**.

The server pushes updates to every client using a callback pattern: it holds a remote reference to each connected client and calls methods on them directly, so there is no polling.

---

## Screenshots
<div align="center">


<table>
  <tr>
    <th>Start Page</th>
    <th>Lobby</th>
    <th>In Game</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/53222dbc-ec39-4efa-a76f-410c1f5db4ad" width="300"></td>
    <td><img src="https://github.com/user-attachments/assets/c0ccc7f9-12b6-47fe-9fcd-0b8e2ad71249" width="300"></td>
    <td><img src="https://github.com/user-attachments/assets/c8a8519c-005a-467e-a391-f6e040ee42a8" width="300"></td>
  </tr>
</table>
</div>
---

## Features

- 🐍 **Up to 6 simultaneous players** on one board
- 💬 **Real-time lobby chat** — messages broadcast to all players instantly
- ✅ **Ready system** — game starts only when all players confirm
- 🎮 **Live game loop** — server ticks every 400 ms, notifies each client on its own thread
- 🍎 **Fruit & growth** — 2 fruits always on the board; eating one grows the snake
- 💀 **Collision detection** — border and snake-body collisions eliminate players
- 🔌 **Late join** — players can enter an ongoing game mid-session
- 🎨 **6 color-coded snake skins** (rose, tomato, yellow, green, blue, purple)
- 🖼️ Pixel-art GUI with custom `PressStart2P` font

---

## Architecture

```
┌─────────────────────────────────┐        ┌────────────────────────────────┐
│           CLIENT                │        │            SERVER              │
│                                 │        │                                │
│  StartPagePanel                 │        │  SnakeServerImp                │
│  LobbyPanel          ──RMI──▶  │        │  ├─ connect()                  │
│  MainGamePanel                  │        │  ├─ requestStartGame()         │
│                                 │        │  ├─ setDirection()             │
│  SnakeClientImp      ◀──RMI──  │        │  ├─ displayLobbyChat()         │
│  (ISnakeClient)                 │        │  └─ disconnect()               │
│                                 │        │                                │
│  Receives pushed updates:       │        │  Holds ISnakeClient refs       │
│  • startGame()                  │        │  and calls them directly       │
│  • updateGame()                 │        │  to push updates               │
│  • addASnakeToLobby()           │        │                                │
│  • die()  ...                   │        │                                │
└─────────────────────────────────┘        └────────────────────────────────┘
```

---

## How RMI Works Here

### 1 — Server setup
```bash
# Step 1: launch the RMI registry
LaunchRegistry.main()   # LocateRegistry.createRegistry(1099)

# Step 2: start the server
SnakeServerImp.main()   # binds "snakeServer" in the registry
```

### 2 — Client connects
```java
ISnakeServer server = (ISnakeServer) Naming.lookup("rmi://" + ip + ":1099/snakeServer");
int myId = server.connect(thisClient, playerName);   // passes itself as a remote ref
```

### 3 — Server pushes back
The server stores each `ISnakeClient` reference and calls it like a local object:
```java
for (int id : new ArrayList<>(players.keySet())) {
    players.get(id).updateGame(snakes, fruits);   // RMI call to the client
}
```

---

## Concurrency & Thread Safety

The server is accessed by multiple client threads simultaneously. Three layers of protection are used:

| Mechanism | Applied To |
|---|---|
| `ConcurrentHashMap` | `players`, `snakes`, `fruits` maps |
| `CopyOnWriteArrayList` | `availableColors` pool |
| `synchronized` methods | `connect()`, `disconnect()`, `addToLobby()`, `findCoordinate()` |
| Snapshot iteration | All notification loops — `new ArrayList<>(players.keySet())` |
| `SwingUtilities.invokeLater()` | Every GUI update on the client (EDT safety) |

**Snapshot iteration** is the key pattern for avoiding `ConcurrentModificationException` when a player disconnects mid-loop:
```java
// Safe — iterates a copy, not the live map
for (int id : new ArrayList<>(players.keySet())) {
    players.get(id).updateGame(snakes, fruits);
}
```

---

## Real-Time Game Loop

Once all players are ready, `startGame()` runs on its own thread:

```java
while (true) {
    Thread.sleep(400);                              // tick rate
    if (players.isEmpty()) break;
    for (Snake s : new ArrayList<>(snakes.values()))
        growOrMoveSnake(s);                         // move, eat, detect collisions
    resetPositions();
    setPlayersCoordinatesTrue();
    notifyNewSnakePositions();                      // broadcast
}
```

Each client is notified **in its own thread**, so a slow or disconnected player never blocks the loop:

```java
for (int id : new ArrayList<>(players.keySet())) {
    new Thread(() -> players.get(id).updateGame(snakes, fruits)).start();
}
```

---

## Project Structure

```
src/main/java/org/example/
├── Server/
│   ├── ISnakeServer.java        # Remote interface (server side)
│   └── SnakeServerImp.java      # Game logic, game loop, RMI impl
├── Client/
│   ├── ISnakeClient.java        # Remote interface (client callbacks)
│   └── SnakeClientImp.java      # Client RMI impl, GUI bridge
├── GUI/
│   ├── MainFrame.java           # CardLayout host, screen switching
│   ├── start/StartPagePanel     # Connect screen
│   ├── lobby/LobbyPanel         # Lobby + chat
│   └── game/
│       ├── MainGamePanel        # Keyboard input, layout
│       └── SnakesPanel          # Renders snakes & fruits
├── Model/
│   └── Snake.java               # Serializable game entity
├── Statics/
│   ├── Coordinate.java          # Tile + pixel position
│   ├── Config.java              # All constants
│   └── Images.java              # Pre-loaded sprites
└── Enums/
    ├── ColorCode.java           # 6 player colors
    └── SnakePartCode.java       # Head / Body / Tail sprite keys
```

---

## Getting Started

### Prerequisites
- Java 17+
- Maven (or your IDE's build tool)

### Run the server

```bash
# 1. Start the RMI registry (keep this running)
mvn exec:java -Dexec.mainClass="org.example.Statics.LaunchRegistry"

# 2. Start the game server
mvn exec:java -Dexec.mainClass="org.example.Server.SnakeServerImp"
```

> **Note:** set `java.rmi.server.hostname` in `SnakeServerImp.main()` to your machine's IP if running across a network.

### Run the client

```bash
mvn exec:java -Dexec.mainClass="org.example.Client.SnakeClientImp"
```

Enter the server's IP address and a snake name, then click **CONNECT**.

### Controls

| Key | Action |
|-----|--------|
| `W` | Move Up |
| `S` | Move Down |
| `A` | Move Left |
| `D` | Move Right |

> 180° turns are blocked server-side — you can't reverse directly into yourself.

---

## Configuration

All constants live in `Config.java`:

| Constant | Value | Meaning |
|---|---|---|
| `TILE_SIZE` | `40` | Pixels per grid cell |
| `COLUMNS / ROWS` | `33 / 17` | Board dimensions |
| `MAX_NB_PLAYERS` | `4` | Max simultaneous players |
| `PORT` | `1099` | RMI registry port |
| Game tick | `400 ms` | `Thread.sleep(400)` in game loop |

---





