import { useState, useEffect } from "react";
import { getPlayers, fight } from "../../api";
import Avatar from "../Avatar/Avatar";
import "./FightSimulator.css";

export default function FightSimulator() {
  const myPlayerId = localStorage.getItem("playerId");

  const [players, setPlayers] = useState([]);
  const [enemy, setEnemy] = useState(null);
  const [me, setMe] = useState(null);

  const [phase, setPhase] = useState("idle"); // idle | fighting | ko | finished
  const [winner, setWinner] = useState(null);
  const [result, setResult] = useState("");

  const [confetti, setConfetti] = useState([]);
  const baseUrl = "http://localhost:8080";

  useEffect(() => {
    getPlayers().then((list) => {
      setPlayers(list);
      const mine = list.find((p) => String(p.id) === String(myPlayerId));
      setMe(mine || null);
    });
  }, [myPlayerId]);

  async function handleFight() {
    if (!enemy || !me) return;

    setPhase("fighting");
    setWinner(null);
    setResult("");

    await new Promise((r) => setTimeout(r, 1500));

    const res = await fight(me.id, enemy.id);
    setResult(res);

    const list = await getPlayers();
    setPlayers(list);
    setMe(list.find((p) => String(p.id) === String(myPlayerId)));

    const win = res.includes(me.name) ? me : enemy;
    setWinner(win);

    setPhase("ko");

    setTimeout(() => {
      setConfetti(
        Array.from({ length: 60 }).map(() => ({
          left: Math.random() * 100,
          delay: Math.random() * 2,
        }))
      );
      setPhase("finished");
    }, 1200);
  }

  function resetFight() {
    setPhase("idle");
    setEnemy(null);
    setWinner(null);
    setResult("");
    setConfetti([]);
  }

  if (!me) return <p style={{ textAlign: "center" }}>Se încarcă arena...</p>;

  return (
    <div className="fight-arena">
      {/* ===== YOU ===== */}
      <div className="fighter-card mine">
        <Avatar src={`${baseUrl}${me.character?.imageUrl}`} size="large" />

        <h3>{me.name}</h3>

        {/* STAMINA */}
        <p className="stamina-text">
          🔋 {me.stamina} / {me.maxStamina}
        </p>
        <div className="stamina-bar">
          <div
            className="stamina-fill"
            style={{ width: `${(me.stamina / me.maxStamina) * 100}%` }}
          />
        </div>

        {/* STATS TABLE */}

        <div className="stats-table">
          {(() => {
            const power = me.character?.power || 0;
            const health = me.character?.health || 0;
            const luck = me.character?.luck || 0;
            const max = Math.max(power, health, luck);

            return (
              <>
                <div className={`stat-row ${power === max ? "dominant" : ""}`}>
                  <span className="stat-icon">⚔</span>
                  <span className="stat-label">Power</span>
                  <span className="stat-value">{power}</span>
                </div>

                <div className={`stat-row ${health === max ? "dominant" : ""}`}>
                  <span className="stat-icon">❤️</span>
                  <span className="stat-label">Health</span>
                  <span className="stat-value">{health}</span>
                </div>

                <div className={`stat-row ${luck === max ? "dominant" : ""}`}>
                  <span className="stat-icon">🍀</span>
                  <span className="stat-label">Luck</span>
                  <span className="stat-value">{luck}</span>
                </div>
              </>
            );
          })()}
        </div>
      </div>

      {/* ===== ENEMIES ===== */}
      <div className="enemy-list">
        {players
          .filter((p) => p.id !== me.id)
          .map((p) => (
            <div
              key={p.id}
              className={`enemy-card ${enemy?.id === p.id ? "selected" : ""}`}
              onClick={() => setEnemy(p)}
            >
              <Avatar src={`${baseUrl}${p.character?.imageUrl}`} size="small" />
              <strong>{p.name}</strong>
              <div className="stat">
                <span className="stat-label">⚔</span>
                <span className="stat-value">{p.character?.power}</span>
              </div>
              <div className="stat">
                <span className="stat-label">❤️</span>
                <span className="stat-value">{p.character?.health}</span>
              </div>
              <div className="stat">
                <span className="stat-label">🍀</span>
                <span className="stat-value">{p.character?.luck}</span>
              </div>
            </div>
          ))}
      </div>

      {/* ===== ACTION ===== */}
      <div className="fight-action">
        <button disabled={!enemy || phase !== "idle"} onClick={handleFight}>
          ⚔️ Luptă
        </button>
        {result && <div className="result-box">{result}</div>}
      </div>

      {/* ===== CINEMATIC ===== */}
      {phase !== "idle" && enemy && (
        <div className={`battle-scene ${phase}`}>
          {phase !== "finished" && (
            <>
              <div className="battle-fighter left">
                <img src={`${baseUrl}${me.character?.imageUrl}`} />
              </div>

              <div className="battle-fighter right">
                <img src={`${baseUrl}${enemy.character?.imageUrl}`} />
              </div>

              <div className="battle-flash" />
            </>
          )}

          {phase === "finished" && winner && (
            <>
              <div className="confetti">
                {confetti.map((c, i) => (
                  <span
                    key={i}
                    style={{
                      left: c.left + "%",
                      animationDelay: c.delay + "s",
                    }}
                  />
                ))}
              </div>

              <div className="battle-winner-screen">
                <img
                  src={`${baseUrl}${winner.character?.imageUrl}`}
                  className="winner-avatar"
                  alt={winner.name}
                />
                <h2>🏆 {winner.name} câștigă!</h2>

                <button onClick={resetFight}>Replay</button>
              </div>
            </>
          )}
        </div>
      )}
    </div>
  );
}
