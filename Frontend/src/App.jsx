import { useEffect, useState } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import Layout from "./components/Layout/Layout";
import Home from "./components/Home/Home";
import FightSimulator from "./components/FightSimulator/FightSimulator";
import Login from "./components/Auth/Login";
import Register from "./components/Auth/Register";
import AvatarCreator from "./components/AvatarCreator/AvatarCreator";
import { getPlayerById } from "./api";
import "./App.css";

export default function App() {
  const [player, setPlayer] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let alive = true;
    const id = localStorage.getItem("playerId");

    Promise.resolve().then(async () => {
      if (!id) {
        alive && setLoading(false);
        return;
      }

      try {
        const p = await getPlayerById(id);
        alive && setPlayer(p);
      } catch {
        localStorage.clear();
      } finally {
        alive && setLoading(false);
      }
    });

    return () => {
      alive = false;
    };
  }, []);

  if (loading) return <p>Loading...</p>;

  return (
    <Routes key={player ? "auth" : "guest"}>
      {/* 🔐 GUEST */}
      {!player && (
        <>
          <Route path="/login" element={<Login setPlayer={setPlayer} />} />
          <Route
            path="/register"
            element={<Register setPlayer={setPlayer} />}
          />
          <Route path="*" element={<Navigate to="/login" />} />
        </>
      )}

      {/* 🎮 LOGAT */}
      {player && (
        <Route path="/" element={<Layout setPlayer={setPlayer} />}>
          <Route index element={<Home player={player} />} />
          <Route path="fight" element={<FightSimulator />} />
          <Route path="avatar" element={<AvatarCreator />} />
          <Route path="*" element={<Navigate to="/" />} />
        </Route>
      )}
    </Routes>
  );
}
