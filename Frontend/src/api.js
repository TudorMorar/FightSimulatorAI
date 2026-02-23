const API = "http://localhost:8080/api";


// ---------- PLAYERS ----------

export async function login(name) {
  const res = await fetch(`${API}/players/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name })
  });

  if (!res.ok) throw new Error("Login failed");

  return res.json();
}


export async function createPlayer(name) {
  const res = await fetch("http://localhost:8080/api/players/create", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name })
  });

  if (!res.ok) throw new Error("Create failed");

  return res.json();
}

export async function getPlayerById(id) {
  const res = await fetch(`${API}/players/${id}`);

  if (!res.ok) {
    throw new Error("Player not found");
  }

  return res.json();
}


export async function getPlayers() {
  const res = await fetch(`${API}/players/getAll`);
  return res.json();
}

// ------------PlayerProgress--------------
export async function getPlayerProgress(playerId) {
  const res = await fetch(
    `http://localhost:8080/api/player/${playerId}/progress`
  );

  if (!res.ok) {
    throw new Error("Failed to fetch player progress");
  }

  return res.json();
}

// ---------- CHARACTERS ----------

export async function generateCharacter(data) {
  const res = await fetch("http://localhost:8080/api/generator/create", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  });

  if (!res.ok) throw new Error("Generation failed");

  return res.json();
}



export async function getCharacters() {
  const res = await fetch(`${API}/characters`);
  return res.json();
}

export async function claimCharacter(characterId, playerId) {
  const res = await fetch(
    `${API}/characters/assign/${characterId}/to/${playerId}`,
    { method: "POST" }
  );

  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || "Assignment failed");
  }

  return res.json();
}

// pentru afișare caracterul meu
export async function getCharacterForPlayer(playerId) {
  const res = await fetch(`${API}/characters/player/${playerId}`);

  if (!res.ok) {
    throw new Error("Nu s-a putut încărca personajul jucătorului");
  }

  return res.json();
}

// ---------- FIGHT ----------
export async function fight(aId, bId) {
  const res = await fetch(
    `http://localhost:8080/fight/${aId}/vs/${bId}`,
    {
      method: "POST",
    }
  );

  if (!res.ok) {
    throw new Error("Fight failed");
  }

  return await res.text();
}

