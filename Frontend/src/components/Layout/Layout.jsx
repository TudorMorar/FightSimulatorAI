import Logo from "../Logo/Logo";
import Navbar from "../Navbar/Navbar";
import { Outlet } from "react-router-dom";

export default function Layout({ setPlayer }) {
  return (
    <>
      <Logo />
      <Navbar setPlayer={setPlayer} /> {/* 🔥 trimitem setPlayer mai departe */}
      <div className="app-container">
        <Outlet />
      </div>
    </>
  );
}
