import React from "react";
import { TrashIcon, PencilIcon, EyeIcon } from "@heroicons/react/24/solid";

export default function DepartmentTable({
  departments,
  onEdit,
  onDelete,
  onView,
  onAssignManager,
}) {
  return (
    <div className="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
      <table className="min-w-full text-sm">
        <thead className="text-sm text-gray-700 uppercase bg-gray-50 border-b border-gray-100">
          <tr>
            <th className="py-5 px-6 text-left font-medium text-gray-900">
              Name
            </th>
            <th className="py-5 px-6 text-left font-medium text-gray-900">
              Code
            </th>
            <th className="py-5 px-6 text-left font-medium text-gray-900">
              Status
            </th>
            <th className="py-5 px-6 text-left font-medium text-gray-900">
              Manager
            </th>
            <th className="py-5 px-6 text-left font-medium text-gray-900">
              Employees
            </th>
            <th className="py-5 px-6 text-center font-medium text-gray-900">
              Actions
            </th>
          </tr>
        </thead>

        <tbody>
          {departments.map((dept) => (
            <tr
              key={dept.id}
              className="border-b border-gray-200 hover:bg-gray-50 transition"
            >
              <td className="px-6 py-5 font-medium text-gray-700">
                {dept.name}
              </td>
              <td className="px-6 py-5 font-medium text-gray-700">
                {dept.departmentCode}
              </td>
              <td className="px-6 py-5">
                <span
                  className={`px-3 py-1 rounded-full text-xs font-medium ${
                    dept.status
                      ? "bg-green-100 text-green-700"
                      : "bg-red-100 text-red-600"
                  }`}
                >
                  {dept.status ? "Active" : "Inactive"}
                </span>
              </td>

              <td className="px-6 py-5 font-medium text-gray-700">
                {dept.manager ? (
                  <div className="flex items-center gap-2">
                    <span className="text-[#1E2A38] font-bold">
                      {dept.manager.firstName} {dept.manager.lastName}
                    </span>

                    <button
                      className="ml-2 text-xs px-2 py-1 bg-blue-100 text-blue-600 rounded hover:bg-blue-200 cursor-pointer"
                      onClick={() => onAssignManager(dept.id)}
                    >
                      Change
                    </button>
                  </div>
                ) : (
                  <button
                    className="ml-2 text-xs px-2 py-1 bg-blue-100 text-blue-600 rounded hover:bg-blue-200 cursor-pointer"
                    onClick={() => onAssignManager(dept.id)}
                  >
                    Assign Manager
                  </button>
                )}
              </td>
              <td className="py-5 px-12">{dept.employeeCount || 0}</td>

              <td className="py-5 px-6 ">
                <div className="flex items-center justify-center gap-2 whitespace-nowrap">
                  <button
                    className="border border-gray-200 p-2 rounded-lg hover:bg-blue-50 cursor-pointer"
                    onClick={() => onView(dept.id)}
                  >
                    <EyeIcon className="w-5 h-5 text-blue-500" />
                  </button>
                  <button
                    className="border border-gray-200 p-2 rounded-lg hover:bg-blue-50 cursor-pointer"
                    onClick={() => onEdit(dept.id)}
                  >
                    <PencilIcon className="h-5 w-5 text-blue-500" />
                  </button>
                  <button
                    className="border border-gray-200 p-2 rounded-lg hover:bg-red-50 cursor-pointer"
                    onClick={() => onDelete(dept.id, dept.name)}
                  >
                    {" "}
                    <TrashIcon className="h-5 w-5 text-red-500" />
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
