import {
    FaCalendarAlt,
    FaCheckCircle,
    FaTimesCircle,
    FaClock
} from "react-icons/fa";

function LeaveStats({ stats }) {

    return (
        <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
               <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-100 flex items-center justify-between">                   <div>
                        <p className="text-sm font-medium text-gray-500 mb-1">Total Requests</p>
                        <h3 className="text-2xl font-bold text-gray-800">{stats.total}</h3>
                     </div>
                     <div className="bg-blue-100 p-3 rounded-full text-blue-600">
                         <FaCalendarAlt size={24} />
                   </div>
                </div>
                 <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-100 flex items-center justify-between">
                     <div>
                        <p className="text-sm font-medium text-gray-500 mb-1">Pending</p>
                        <h3 className="text-2xl font-bold text-gray-800">{stats.pending}</h3>
                    </div>
                    <div className="bg-yellow-100 p-3 rounded-full text-yellow-600">
                        <FaClock size={24} />
                    </div>
                 </div>
                <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-100 flex items-center justify-between">
                    <div>
                         <p className="text-sm font-medium text-gray-500 mb-1">Approved</p>
                         <h3 className="text-2xl font-bold text-gray-800">{stats.approved}</h3>
                     </div>
                     <div className="bg-green-100 p-3 rounded-full text-green-600">
                         <FaCheckCircle size={24} />
                     </div>
                </div>
                <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-100 flex items-center justify-between">
                   <div>
                       <p className="text-sm font-medium text-gray-500 mb-1">Rejected</p>
                        <h3 className="text-2xl font-bold text-gray-800">{stats.rejected}</h3>
                     </div>
                     <div className="bg-red-100 p-3 rounded-full text-red-600">
                         <FaTimesCircle size={24} />
                    </div>
                </div>
            </div>
    );
}

export default LeaveStats;