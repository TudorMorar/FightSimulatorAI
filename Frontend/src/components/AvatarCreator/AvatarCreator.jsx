import { useState } from "react";
import { generateCharacter } from "../../api";
import "./AvatarCreator.css";

export default function AvatarCreator({ player, refresh }) {
  const [form, setForm] = useState({
    food: "",
    hobby: "",
    style: "",
    education: "",
    car: "",
    drink: "",
  });

  const [loading, setLoading] = useState(false);
  const [character, setCharacter] = useState(null);
  const [error, setError] = useState("");

  function update(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function submit(e) {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const result = await generateCharacter({
        playerId: player.id,
        ...form,
      });

      setCharacter(result);
      refresh();
    } catch {
      setError("Nu s-a putut genera avatarul.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="avatar-creator">
      <h2>🎨 Creează Avatar AI</h2>

      <form onSubmit={submit}>
        <select name="food" onChange={update}>
          <option value="">Mâncare preferată</option>
          <option>Shaorma</option>
          <option>Pizza</option>
          <option>Burger</option>
          <option>Salată</option>
        </select>

        <select name="hobby" onChange={update}>
          <option value="">Hobby</option>
          <option>Gaming</option>
          <option>Sport</option>
          <option>Meme-uri</option>
          <option>Netflix</option>
        </select>

        <select name="style" onChange={update}>
          <option value="">Stil</option>
          <option>Tank</option>
          <option>Rapid</option>
          <option>Brutal</option>
          <option>Norocos</option>
        </select>

        <select name="education" onChange={update}>
          <option value="">Studii</option>
          <option>Facultate</option>
          <option>Master</option>
          <option>Doctorat</option>
          <option>Școala vieții</option>
        </select>

        <select name="car" onChange={update}>
          <option value="">Mașină</option>
          <option>BMW</option>
          <option>Mercedes</option>
          <option>Volkswagen</option>
          <option>Dacia</option>
        </select>

        <select name="drink" onChange={update}>
          <option value="">Băutură</option>
          <option>Bere</option>
          <option>Vin</option>
          <option>Whisky</option>
          <option>Cafea</option>
        </select>

        <button disabled={loading}>
          {loading ? "Se generează..." : "Generează Avatar"}
        </button>
      </form>

      {error && <p className="error">{error}</p>}

      {character && (
        <div className="avatar-result">
          <h3>{character.name}</h3>

          {character.imageUrl && (
            <img
              src={`http://localhost:8080${character.imageUrl}`}
              alt="avatar"
            />
          )}

          <p>⚔ Power: {character.power}</p>
          <p>❤️ Health: {character.health}</p>
          <p>🍀 Luck: {character.luck}</p>
        </div>
      )}
    </div>
  );
}
