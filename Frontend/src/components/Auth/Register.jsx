import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { createPlayer } from "../../api";
import "./Auth.css";

export default function Register({ setPlayer }) {
  const [name, setName] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");

    if (!name.trim()) {
      setError("Introdu un nume valid.");
      return;
    }

    try {
      setLoading(true);

      const player = await createPlayer(name.trim());

      localStorage.setItem("playerId", player.id);
      localStorage.setItem("playerName", player.name);

      setPlayer(player); // intră direct în joc
    } catch {
      setError("Nu s-a putut crea playerul (poate există deja?).");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="login-page">
      <form className="login-container" onSubmit={handleSubmit}>
        <h2 className="login-title">
          <span className="login-icon">🧬</span>
          Creare jucător
        </h2>
        <p className="login-subtitle">Bun venit la Fight Simulator AI</p>

        <input
          type="text"
          placeholder="Numele tău..."
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <button className="login-button" type="submit" disabled={loading}>
          {loading ? "Se creează..." : "Continuă"}
        </button>

        {error && <p className="login-error">{error}</p>}

        <hr style={{ margin: "18px 0", opacity: 0.3 }} />

        {/* 🔙 Înapoi la login */}
        <button
          type="button"
          className="login-button"
          style={{
            background: "transparent",
            border: "1px solid #00ffaa",
            color: "#00ffaa",
            boxShadow: "none",
          }}
          onClick={() => navigate("/login")}
        >
          ← Înapoi la login
        </button>
      </form>
    </div>
  );
}
