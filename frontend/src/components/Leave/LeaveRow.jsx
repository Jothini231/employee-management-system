import { FaCheck, FaTimes } from "react-icons/fa";

function LeaveRow({
    leave,
    employees,
    handleApprove,
    handleReject
}) {
    
    const employee = employees.find(emp => emp.id === leave.employeeId);

    return (
        <tr className="bg-white border-b hover:bg-gray-50 transition-colors">

            <td className="px-6 py-4 font-medium text-gray-900">
                {employee ? `${employee.firstName} ${employee.lastName}` : leave.employeeId}
            </td>

            <td className="px-6 py-4">
                <span className="px-2.5 py-1 bg-gray-100 text-gray-700 rounded-md text-xs font-medium">
                    {leave.leaveType}
                </span>
            </td>

            <td className="px-6 py-4 whitespace-nowrap">
                {leave.startDate} to {leave.endDate}
            </td>

            <td className="px-6 py-4 font-medium">
                {leave.totalDays}
            </td>

            <td className="px-6 py-4 max-w-xs truncate">
                {leave.reason}
            </td>

            <td className="px-6 py-4">

                <span
                    className={`inline-flex items-center px-2.5 py-1 rounded-full text-xs font-medium border ${
                        leave.status === "APPROVED"
                            ? "bg-green-50 text-green-700 border-green-200"
                            : leave.status === "REJECTED"
                            ? "bg-red-50 text-red-700 border-red-200"
                            : "bg-yellow-50 text-yellow-700 border-yellow-200"
                    }`}
                >
                    {leave.status}
                </span>

            </td>

            <td className="px-6 py-4 text-right">

                <div className="flex justify-end gap-2">

                    {leave.status === "PENDING" ? (
                        <>
                            <button
                                onClick={() => handleApprove(leave.id)}
                                className="p-2 text-white bg-green-500 hover:bg-green-600 rounded-lg"
                            >
                                <FaCheck size={14} />
                            </button>

                            <button
                                onClick={() => handleReject(leave.id)}
                                className="p-2 text-white bg-red-500 hover:bg-red-600 rounded-lg"
                            >
                                <FaTimes size={14} />
                            </button>
                        </>
                    ) : (
                        <span className="text-gray-400 text-xs italic">
                            Reviewed
                        </span>
                    )}

                </div>

            </td>

        </tr>
    );
}

export default LeaveRow;