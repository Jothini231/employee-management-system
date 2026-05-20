
import React from "react";
import { FaEdit, FaEye, FaTrash } from "react-icons/fa";

function EmployeeTable({
  employees,
  getDepartmentName,
  onView,
  onEdit,
  onDelete,
}) {
  return (
    <div className="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
      <table className="min-w-full text-sm">
        <thead className="text-sm text-gray-700 uppercase bg-gray-50 border-b border-gray-100">
          <tr>
            <th className="py-3 px-6 text-left font-medium text-gray-900">ID</th>
            <th className="py-3 px-6 text-left font-medium text-gray-900">Name</th>
            <th className="py-3 px-6 text-left font-medium text-gray-900">Email</th>
            <th className="py-3 px-6 text-left font-medium text-gray-900">Department</th>
            <th className="py-3 px-6 text-left font-medium text-gray-900">Role</th>
            <th className="py-3 px-6 text-left font-medium text-gray-900">Status</th>
            <th className="py-3 px-6 text-center font-medium text-gray-900">Actions</th>
          </tr>
        </thead>

        <tbody>
          {employees.map((emp) => (
            <tr
              key={emp.id}
              className=" bg-white border-b border-gray-200 hover:bg-gray-50 transition "
            >
              <td className="px-6 py-5 font-medium text-gray-700">{emp.id}</td>
              <td className="px-6 py-5 font-medium text-gray-700">{emp.firstName} {emp.lastName}</td>
              <td className="px-6 py-5 font-medium text-gray-700">{emp.email}</td>
              <td className="px-6 py-5 font-medium text-gray-700">
                {emp.departmentName || getDepartmentName(emp.departmentId)}
              </td>
              <td className="px-6 py-5 font-medium text-gray-700">{emp.role}</td>
              <td className="px-6 py-5">
                  <span
                    className={`px-3 py-1 rounded-full text-xs font-medium ${
                      emp.status === "ACTIVE"
                        ? "bg-green-100 text-green-700"
                        : "bg-red-100 text-red-600"
                    }`}
                  >
                    {emp.status}
                  </span>
                </td>

              <td className="px-6 py-5">
                <div className="flex items-center justify-center gap-2 whitespace-nowrap">
                  <button
                    onClick={() => onView(emp.id)}
                    className="p-2 bg-blue-50 text-blue-500 hover:bg-blue-100 rounded-md transition"
                  >
                    <FaEye/>
                  </button>

                  <button
                    onClick={() => onEdit(emp.id)}
                    className="p-2 bg-orange-50 text-orange-500 hover:bg-orange-100 rounded-md transition"
                  >
                    <FaEdit />
                  </button>

                  <button
                    onClick={() =>
                      onDelete(emp.id, emp.firstName + " " + emp.lastName)
                    }
                    className="p-2 bg-red-50 text-red-500 hover:bg-red-100 rounded-md transition"
                  >
                    <FaTrash />
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default EmployeeTable;