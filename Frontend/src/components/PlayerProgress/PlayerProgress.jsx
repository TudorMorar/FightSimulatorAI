import { useEffect, useState, useRef } from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import "./PlayerProgress.css";

export default function PlayerProgress({ playerId }) {
  const [progress, setProgress] = useState(null);
  const [loading, setLoading] = useState(true);
  const [levelUp, setLevelUp] = useState(false);
  const [collapsed, setCollapsed] = useState(false);

  const prevLevel = useRef(null);

  useEffect(() => {
    let mounted = true;

    const load = () => {
      fetch(`http://localhost:8080/api/player/${playerId}/progress`)
        .then((res) => res.json())
        .then((data) => {
          if (!mounted) return;

          if (prevLevel.current !== null && data.level > prevLevel.current) {
            setLevelUp(true);
            setTimeout(() => setLevelUp(false), 1200);
          }

          prevLevel.current = data.level;
          setProgress(data);
          setLoading(false);
        })
        .catch(() => setLoading(false));
    };

    load(); // prima încărcare

    const interval = setInterval(load, 30_000); // 🔥 refresh la 30 sec

    return () => {
      mounted = false;
      clearInterval(interval);
    };
  }, [playerId]);

  if (loading || !progress) return null;

  return (
    <div className={`left-panel ${levelUp ? "level-up" : ""}`}>
      <div className="left-panel-inner">
        <div className="pp-header">
          <h2 className="pp-title">
            <span className="pp-icon">🛡</span>
            <span className="pp-name">{progress.playerName}</span>
          </h2>

          <button
            className="pp-toggle"
            onClick={() => setCollapsed(!collapsed)}
            aria-label="Toggle panel"
          >
            {collapsed ? "⬆" : "⬇"}
          </button>
        </div>

        {!collapsed && (
          <>
            <div className="pp-xp-row">
              <ProgressBar
                level={progress.level}
                xp={progress.xp}
                requiredXp={progress.requiredXp}
                animated
              />

              {/* 🔋 STAMINA */}
              <div className="pp-stamina-vertical">
                <div
                  className="pp-stamina-fill"
                  style={{
                    height: `${
                      (progress.stamina / progress.maxStamina) * 100
                    }%`,
                  }}
                />
                <span className="pp-stamina-value">
                  {progress.stamina}/{progress.maxStamina}
                </span>
              </div>
            </div>

            {progress.character ? (
              <ProgressBar
                title={progress.character.name}
                level={progress.character.level}
                xp={progress.character.xp}
                requiredXp={progress.character.requiredXp}
                animated
              />
            ) : (
              <p className="pp-warning">⚠️ No character created yet</p>
            )}
          </>
        )}
      </div>
    </div>
  );
}
