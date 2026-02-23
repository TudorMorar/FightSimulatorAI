import { useState } from "react";
import CharactersGrid from "./CharactersGrid";
import AvatarCreator from "../AvatarCreator/AvatarCreator";
import "./CharacterSelect.css";

export default function CharacterSelect({ player, chars, onDone }) {
  const [mode, setMode] = useState("predefined");

  return (
    <div className="character-select">
      <h2>🎭 Alege-ți eroul</h2>

      <div className="header-buttons">
        <button
          className={mode === "predefined" ? "active" : ""}
          onClick={() => setMode("predefined")}
        >
          Alege caracter
        </button>

        <button
          className={mode === "ai" ? "active" : ""}
          onClick={() => setMode("ai")}
        >
          Creează cu A.I.
        </button>
      </div>

      {mode === "predefined" && (
        <CharactersGrid chars={chars} player={player} onSelected={onDone} />
      )}

      {mode === "ai" && <AvatarCreator player={player} onCreated={onDone} />}
    </div>
  );
}
