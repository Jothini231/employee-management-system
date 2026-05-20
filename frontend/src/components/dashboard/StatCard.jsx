import React from "react";

const StatCard = ({ title, value, icon, description, colorClass }) => {
  return (
    <div className="bg-white rounded-2xl p-6 shadow-sm border border-gray-100 hover:shadow-md transition-all duration-300 flex flex-col justify-between">
      <div className="flex items-center justify-between mb-2">
        <div className={`p-3 rounded-2xl ${colorClass} flex items-center justify-center`}>
          {icon}
        </div>
        <div className="flex-1 text-right ml-4">
          <p className="text-sm font-medium text-gray-500 mb-1 uppercase tracking-wider">{title}</p>
          <h3 className="text-3xl font-bold text-gray-900 tracking-tight">{value}</h3>
        </div>
      </div>
      <div className="mt-4 pt-4 border-t border-gray-50 flex items-center">
        <p className="text-sm text-gray-500 font-medium">{description}</p>
      </div>
    </div>
  );
};

export default StatCard;
