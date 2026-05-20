import React from "react";
import { format } from "date-fns";

const RecentActivities = ({ activities }) => {
  return (
    <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 h-full flex flex-col">
      <h3 className="text-lg font-semibold text-gray-900 mb-6 flex-shrink-0">Recent Activities</h3>
      <div className="overflow-x-auto flex-1">
        <table className="w-full text-left border-collapse whitespace-nowrap">
          <thead>
            <tr className="bg-gray-50/50">
              <th className="py-3 px-4 border-b border-gray-100 text-xs font-semibold text-gray-500 uppercase tracking-wider rounded-tl-xl">Activity</th>
              <th className="py-3 px-4 border-b border-gray-100 text-xs font-semibold text-gray-500 uppercase tracking-wider">Employee</th>
              <th className="py-3 px-4 border-b border-gray-100 text-xs font-semibold text-gray-500 uppercase tracking-wider">Date</th>
              <th className="py-3 px-4 border-b border-gray-100 text-xs font-semibold text-gray-500 uppercase tracking-wider text-center rounded-tr-xl">Status</th>
            </tr>
          </thead>
          <tbody>
            {activities.length > 0 ? (
              activities.map((activity, index) => {
                let statusColor = "bg-gray-100 text-gray-800";
                if (activity.status === "Success" || activity.status === "Approved") statusColor = "bg-green-100 text-green-800";
                if (activity.status === "Pending") statusColor = "bg-yellow-100 text-yellow-800";
                if (activity.status === "Rejected" || activity.status === "Failed") statusColor = "bg-red-100 text-red-800";

                return (
                  <tr key={index} className="hover:bg-gray-50/80 transition-colors border-b border-gray-50 last:border-0 group">
                    <td className="py-4 px-4 text-sm text-gray-800 group-hover:text-gray-900 transition-colors">{activity.activity}</td>
                    <td className="py-4 px-4 text-sm font-medium text-gray-900">{activity.employeeName}</td>
                    <td className="py-4 px-4 text-sm text-gray-500">
                      {activity.date ? format(new Date(activity.date), "MMM dd, yyyy") : "-"}
                    </td>
                    <td className="py-4 px-4 text-center">
                      <span className={`px-3 py-1.5 rounded-full text-xs font-medium tracking-wide shadow-sm ${statusColor}`}>
                        {activity.status}
                      </span>
                    </td>
                  </tr>
                );
              })
            ) : (
              <tr>
                <td colSpan="4" className="py-8 text-center text-gray-500 text-sm">
                  No recent activities found.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default RecentActivities;
