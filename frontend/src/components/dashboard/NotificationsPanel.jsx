import React from "react";
import { format } from "date-fns";
import { FaUserPlus, FaCalendarPlus, FaBell } from "react-icons/fa";

const NotificationsPanel = ({ notifications }) => {
  return (
    <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 h-full flex flex-col">
      <div className="flex items-center justify-between mb-6 flex-shrink-0">
        <h3 className="text-lg font-semibold text-gray-900">Notifications</h3>
        {notifications.length > 0 && (
          <span className="bg-indigo-50 text-indigo-700 border border-indigo-100 text-xs font-semibold px-2.5 py-1 rounded-full shadow-sm">
            {notifications.length} New
          </span>
        )}
      </div>

      <div className="space-y-3 flex-1 overflow-y-auto pr-2 custom-scrollbar max-h-[400px] lg:max-h-[none]">
        {notifications.length > 0 ? (
          notifications.map((notification, index) => {
            let Icon = FaBell;
            let iconColor = "text-indigo-500 bg-indigo-100";
            
            if (notification.type === "leave") {
              Icon = FaCalendarPlus;
              iconColor = "text-yellow-600 bg-yellow-100";
            } else if (notification.type === "employee") {
              Icon = FaUserPlus;
              iconColor = "text-green-600 bg-green-100";
            }

            return (
              <div key={index} className="flex items-start p-3 hover:bg-gray-50/80 rounded-xl transition-all border border-transparent hover:border-gray-100 group">
                <div className={`flex-shrink-0 w-10 h-10 rounded-full flex items-center justify-center mr-4 shadow-sm ${iconColor}`}>
                  <Icon className="text-sm" />
                </div>
                <div className="flex-1 min-w-0">
                  <div className="flex justify-between items-baseline mb-0.5">
                    <p className="text-sm font-semibold text-gray-900 truncate pr-2 group-hover:text-indigo-600 transition-colors">
                      {notification.title}
                    </p>
                    <p className="text-[11px] font-medium text-gray-400 whitespace-nowrap">
                      {notification.date ? format(new Date(notification.date), "MMM dd") : "-"}
                    </p>
                  </div>
                  <p className="text-sm text-gray-500 line-clamp-2 leading-relaxed">
                    {notification.description}
                  </p>
                </div>
              </div>
            );
          })
        ) : (
          <div className="py-8 text-center text-gray-500 text-sm">
            You're all caught up!
          </div>
        )}
      </div>
    </div>
  );
};

export default NotificationsPanel;


// to do - improve notifications (if it getting too much of notifications add view btn and move to another page)
