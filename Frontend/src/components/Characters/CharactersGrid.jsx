import "./CharactersGrid.css";
import { claimCharacter } from "../../api";

export default function CharactersGrid({ chars, player, onSelected }) {
  async function handleClaim(characterId) {
    try {
      await claimCharacter(characterId, player.id);
      onSelected(); // 🔥 anunță CharacterSelect că s-a ales un erou
    } catch {
      alert("Caracterul este deja ales sau a apărut o eroare.");
    }
  }

  return (
    <div className="characters-grid">
      {chars.map((c) => (
        <div key={c.id} className="character-slot">
          <div className="card">
            <h3 className="character-name">{c.name}</h3>

            <img src={c.imageUrl} alt={c.name} className="character-image" />

            <div className="stats">
              <p>⚔️ {c.power}</p>
              <p>❤️ {c.health}</p>
              <p>🍀 {c.luck}</p>
            </div>
          </div>

          <button className="select-btn" onClick={() => handleClaim(c.id)}>
            Alege acest caracter
          </button>
        </div>
      ))}
    </div>
  );
}
