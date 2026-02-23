import { useNavigate } from "react-router-dom";

export default function Logout({ setPlayer }) {
  const navigate = useNavigate();

  function handleLogout() {
    localStorage.clear(); // șterge sesiunea
    setPlayer(null); // spune App-ului că nu mai e logat
    navigate("/login"); // redirecționează
  }

  return <button onClick={handleLogout}>Logout</button>;
}
