import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { login } from "../../api";
import "./Auth.css";

export default function Login({ setPlayer }) {
  const [name, setName] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  async function handleLogin(e) {
    e.preventDefault();
    setError("");

    if (!name.trim()) {
      setError("Introdu un nume.");
      return;
    }

    try {
      setLoading(true);

      const player = await login(name.trim());

      localStorage.setItem("playerId", player.id);
      localStorage.setItem("playerName", player.name);

      setPlayer(player); // intră în joc
    } catch {
      setError("Playerul nu există.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="login-page">
      <form className="login-container" onSubmit={handleLogin}>
        <h2 className="login-title">
          <span className="login-icon">🔑</span>
          Autentificare
        </h2>
        <p className="login-subtitle">Intră în Fight Simulator AI</p>

        <input
          placeholder="Numele playerului"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <button className="login-button" type="submit" disabled={loading}>
          {loading ? "Se verifică..." : "Intră în joc"}
        </button>

        {error && <p className="login-error">{error}</p>}

        <hr style={{ margin: "20px 0", opacity: 0.3 }} />

        <button
          type="button"
          className="login-button secondary"
          onClick={() => navigate("/register")}
        >
          Creează player nou
        </button>
      </form>
    </div>
  );
}
