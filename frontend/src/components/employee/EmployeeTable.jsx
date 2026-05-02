import { TrashIcon, PencilIcon, EyeIcon } from "@heroicons/react/24/solid";

function EmployeeTable({
  employees,
  getDepartmentName,
  onView,
  onEdit,
  onDelete,
}) {
  return (
    <div className="overflow-x-auto shadow-md rounded-lg">
      <table className="min-w-full border-collapse border border-gray-200">
        <thead className="bg-gray-200">
          <tr>
            <th className="py-3 px-6 text-left border-b border-gray-300">ID</th>
            <th className="py-3 px-6 text-left border-b border-gray-300">First Name</th>
            <th className="py-3 px-6 text-left border-b border-gray-300">Last Name</th>
            <th className="py-3 px-6 text-left border-b border-gray-300">Email</th>
            <th className="py-3 px-6 text-left border-b border-gray-300">Department</th>
            <th className="py-3 px-6 text-left border-b border-gray-300">Role</th>
            <th className="py-3 px-6 text-left border-b border-gray-300">Status</th>
            <th className="py-3 px-6 text-center border-b border-gray-300">Actions</th>
          </tr>
        </thead>

        <tbody className="bg-white">
          {employees.map((emp) => (
            <tr
              key={emp.id}
              className="border-b border-gray-200 hover:bg-gray-50 cursor-pointer"
            >
              <td className="py-3 px-6">{emp.id}</td>
              <td className="py-3 px-6">{emp.firstName}</td>
              <td className="py-3 px-6">{emp.lastName}</td>
              <td className="py-3 px-6">{emp.email}</td>
              <td className="py-3 px-6">
                {emp.departmentName || getDepartmentName(emp.departmentId)}
              </td>
              <td className="py-3 px-6">{emp.role}</td>
              <td className="py-3 px-6">{emp.status}</td>

              <td className="py-3 px-6">
                <div className="flex items-center justify-center gap-2 whitespace-nowrap">
                  <button
                    onClick={() => onView(emp.id)}
                    className="p-1 rounded hover:bg-gray-200"
                  >
                    <EyeIcon className="w-5 h-5 text-blue-500" />
                  </button>

                  <button
                    onClick={() => onEdit(emp.id)}
                    className="inline-flex items-center justify-center p-1 rounded hover:bg-blue-100"
                  >
                    <PencilIcon className="h-5 w-5 text-blue-500" />
                  </button>

                  <button
                    onClick={() =>
                      onDelete(emp.id, emp.firstName + " " + emp.lastName)
                    }
                    className="inline-flex items-center justify-center p-1 rounded hover:bg-red-100"
                  >
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

export default EmployeeTable;