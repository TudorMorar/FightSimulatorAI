import "./Progress.css";

const ProgressBar = ({ title, level, xp, requiredXp }) => {
  const percent = Math.min((xp / requiredXp) * 100, 100);

  return (
    <div className="progress-wrapper">
      <div className="progress-title">
        <div className="progress-name">{title}</div>
        <div className="progress-level">Level {level}</div>
      </div>

      <div className="progress-bar">
        <div className="progress-fill" style={{ width: `${percent}%` }} />
      </div>

      <div className="progress-xp">
        {xp} / {requiredXp} XP
      </div>
    </div>
  );
};

export default ProgressBar;
