import { FaCalendarAlt } from "react-icons/fa";
import LeaveRow from "./LeaveRow";

function LeaveTable({
    employees,
    filteredLeaves,
    filterStatus,
    setFilterStatus,
    handleApprove,
    handleReject
}) {

    return (
        <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">

            <div className="p-6 border-b border-gray-100 flex justify-between items-center">

                <h2 className="text-lg font-semibold text-gray-800">
                    Recent Leave Requests
                </h2>

                <select
                    value={filterStatus}
                    onChange={(e) => setFilterStatus(e.target.value)}
                    className="bg-gray-50 border border-gray-200 text-gray-700 text-sm rounded-lg block p-2.5 outline-none"
                >
                    <option value="All">All Statuses</option>
                    <option value="PENDING">Pending</option>
                    <option value="APPROVED">Approved</option>
                    <option value="REJECTED">Rejected</option>
                </select>

            </div>

            <div className="overflow-x-auto">

                <table className="w-full text-sm text-left text-gray-500">

                    <thead className="text-xs text-gray-700 uppercase bg-gray-50 border-b border-gray-100">

                        <tr>
                            <th scope="col" className="px-6 py-4 font-medium text-gray-900">Emp Name</th>
                             <th scope="col" className="px-6 py-4 font-medium text-gray-900">Leave Type</th>
                                 <th scope="col" className="px-6 py-4 font-medium text-gray-900">Duration</th>
                                <th scope="col" className="px-6 py-4 font-medium text-gray-900">Days</th>
                                <th scope="col" className="px-6 py-4 font-medium text-gray-900">Reason</th>
                                 <th scope="col" className="px-6 py-4 font-medium text-gray-900">Status</th>
                               <th scope="col" className="px-6 py-4 font-medium text-gray-900 text-right">Actions</th>
                        </tr>

                    </thead>

                    <tbody>

                        {filteredLeaves.length > 0 ? (
                            filteredLeaves.map((leave) => (
                                <LeaveRow
                                    key={leave.id}
                                    leave={leave}
                                    employees={employees}
                                    handleApprove={handleApprove}
                                    handleReject={handleReject}
                                />
                            ))
                        ) : (
                            <tr>
                                <td
                                    colSpan="7"
                                    className="px-6 py-12 text-center text-gray-500"
                                >
                                    <div className="flex flex-col items-center justify-center">
                                        <FaCalendarAlt
                                            size={40}
                                            className="text-gray-300 mb-3"
                                        />

                                        <p className="text-base font-medium text-gray-900 mb-1">
                                            No leave requests found
                                        </p>
                                    </div>
                                </td>
                            </tr>
                        )}

                    </tbody>

                </table>

            </div>

        </div>
    );
}

export default LeaveTable;