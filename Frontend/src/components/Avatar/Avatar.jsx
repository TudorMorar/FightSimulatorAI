import "./Avatar.css";

export default function Avatar({ src, alt = "", size = "medium" }) {
  return (
    <img
      src={src}
      alt={alt}
      className={`avatar avatar-${size}`}
      draggable="false"
    />
  );
}
