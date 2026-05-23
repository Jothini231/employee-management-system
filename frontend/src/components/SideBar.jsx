import React, { useContext } from "react";
import {
  FaTachometerAlt,
  FaUsers,
  FaBookOpen,
  FaCalendarAlt,
  FaMoneyBillWave,
  FaCog,
} from "react-icons/fa";
import { NavLink } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import { FiLogOut } from "react-icons/fi";
const SideBar = () => {
  const { logout } = useContext(AuthContext);

  const links = [
    { name: "Dashboard", path: "/", icon: <FaTachometerAlt /> },
    { name: "Employee", path: "/employees", icon: <FaUsers /> },
    { name: "Department", path: "/departments", icon: <FaBookOpen /> },
    { name: "Leave", path: "/leave", icon: <FaCalendarAlt /> },
    { name: "Salary", path: "/salary", icon: <FaMoneyBillWave /> },
    { name: "Settings", path: "/settings", icon: <FaCog /> },
  ];
  return (
    <div className="fixed top-0 left-0 w-64 bg-gray-800 text-white h-screen p-6">
      <h1 className="text-2xl font-bold mb-8">Admin</h1>
      <nav>
        {links.map((link) => (
          <NavLink
            key={link.name}
            to={link.path}
            className={({ isActive }) =>
              `flex items-center mb-4 p-2 rounded hover:bg-gray-700 ${
                isActive ? "bg-gray-700" : ""
              }`
            }
          >
            <span className="text-lg mr-3">{link.icon}</span>
            <span>{link.name}</span>
          </NavLink>
        ))}
      </nav>
      
      <div className="absolute bottom-6 left-6 right-6">
        <button
          onClick={logout}
          className="w-full flex items-center p-2 rounded hover:bg-gray-700 transition-colors"
        >
          <span className="text-lg mr-3"><FiLogOut /></span>
          <span>Logout</span>
        </button>
      </div>
    </div>
  );
};

export default SideBar;
