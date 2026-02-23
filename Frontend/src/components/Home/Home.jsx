import { useEffect, useState } from "react";
import PlayerProgress from "../PlayerProgress/PlayerProgress";
import CharacterSelect from "../Characters/CharacterSelect";
import "./Home.css";
import { getCharacters, getCharacterForPlayer } from "../../api";

export default function Home({ player }) {
  const [myCharacter, setMyCharacter] = useState(undefined);
  const [chars, setChars] = useState([]);
  const [loading, setLoading] = useState(true);

  // 🔥 funcție async pură (nu e apelată direct din effect)
  async function loadData() {
    setLoading(true);

    try {
      const [charData, allChars] = await Promise.all([
        getCharacterForPlayer(player.id),
        getCharacters(),
      ]);

      const c = Array.isArray(charData) ? charData[0] : charData;

      if (c && c.id) {
        c.imageUrl = `http://localhost:8080/uploads/${c.id}.png`;
        setMyCharacter(c);
      } else {
        setMyCharacter(null);
      }

      setChars(
        allChars.map((c) => ({
          ...c,
          imageUrl: `http://localhost:8080/uploads/${c.id}.png`,
        }))
      );
    } catch {
      setMyCharacter(null);
    } finally {
      setLoading(false);
    }
  }

  // 🔥 effect doar pornește task async
  useEffect(() => {
    Promise.resolve().then(loadData);
  }, [player.id]);

  if (loading || myCharacter === undefined) return <p>Loading...</p>;

  if (myCharacter === null) {
    return <CharacterSelect player={player} chars={chars} onDone={loadData} />;
  }

  return (
    <>
      <div className="welcome-bar">
        <strong>Bine ai venit, {player.name}! 🎮</strong>
      </div>

      <PlayerProgress playerId={player.id} />
    </>
  );
}
