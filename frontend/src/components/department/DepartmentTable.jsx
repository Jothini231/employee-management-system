import React from "react";
import { TrashIcon, PencilIcon, EyeIcon } from "@heroicons/react/24/solid";

export default function DepartmentTable({departments,onEdit,onDelete,onView,onAssignManager}) {


  return (
    <div className="overflow-x-auto shadow-md rounded-lg">
          <table className="min-w-full border-collapse border border-gray-200">
            <thead className="bg-gray-200">
          <tr>
            <th className="py-3 px-6 text-left border-b border-gray-300">Name</th>
            <th className="py-3 px-6 text-left border-b border-gray-300">Code</th>
            <th className="py-3 px-6 text-left border-b border-gray-300">Status</th>
            <th className="py-3 px-6 text-left border-b border-gray-300">Manager</th>
            <th className="py-3 px-6 text-left border-b border-gray-300">Employees</th>
            <th className="py-3 px-6 text-center border-b border-gray-300">Actions</th>
          </tr>
        </thead>

        <tbody className="bg-white">
          {departments.map((dept) => (
            <tr key={dept.id} className="border-b border-gray-200 hover:bg-gray-50 cursor-pointer">
              <td className="py-3 px-6">{dept.name}</td>
              <td className="py-3 px-6">{dept.departmentCode}</td>
              <td className="py-3 px-6">{dept.status ? "Active" : "Inactive"}</td>
              <td className="py-3 px-2">
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
              <td className="py-3 px-12">{dept.employeeCount || 0}</td>

              <td className="py-3 px-6 text-center space-x-2">
                <button className="inline-flex items-center justify-center p-1 rounded hover:bg-green-100" onClick={() => onView(dept.id)}><EyeIcon className="w-5 h-5 text-blue-500" /></button>
                <button className="inline-flex items-center justify-center p-1 rounded hover:bg-blue-100" onClick={() => onEdit(dept.id)}><PencilIcon className="h-5 w-5 text-blue-500" /></button>
                <button className="inline-flex items-center justify-center p-1 rounded hover:bg-red-100" onClick={() => onDelete(dept.id,dept.name)}> <TrashIcon className="h-5 w-5 text-red-500" /></button>
                
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}