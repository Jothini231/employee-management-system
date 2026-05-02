import React from "react";
import {
  FaTachometerAlt,
  FaUsers,
  FaBookOpen,
  FaCalendarAlt,
  FaMoneyBillWave,
  FaCog,
} from "react-icons/fa";
import { NavLink } from "react-router-dom";

const SideBar = () => {
  const links = [
    { name: "Dashboard", path: "/", icon: <FaTachometerAlt /> },
    { name: "Employee", path: "/employees", icon: <FaUsers /> },
    { name: "Department", path: "/departments", icon: <FaBookOpen /> },
    { name: "Leave", path: "/leave", icon: <FaCalendarAlt /> },
    { name: "Salary", path: "/salary", icon: <FaMoneyBillWave /> },
    { name: "Settings", path: "/settings", icon: <FaCog /> },
  ];
  return (
    <div className="w-64 bg-gray-800 text-white h-screen p-6">
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
    </div>
  );
};

export default SideBar;
