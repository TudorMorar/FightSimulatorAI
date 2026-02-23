import "./Navbar.css";
import { useNavigate } from "react-router-dom";
import Logout from "../Logout/Logout";

export default function Navbar({ setPlayer }) {
  const navigate = useNavigate();

  return (
    <div className="navbar">
      <button onClick={() => navigate("/")}>🏠 Acasă</button>
      <button onClick={() => navigate("/fight")}>⚔️ Arena</button>
      <button onClick={() => navigate("/avatar")}>🧙 Avatar</button>

      <Logout setPlayer={setPlayer} />
    </div>
  );
}
