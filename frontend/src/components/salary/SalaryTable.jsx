
import React from "react";
import { FaEye, FaEdit, FaTrash } from "react-icons/fa";

function SalaryTable({
    salaries,
    employees,
    departments,
    formatCurrency,
    setDeleteSalaryId,
    navigate,
}) {
  return (
    <div className="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
      <table className="min-w-full text-sm">
        <thead className="text-sm text-gray-700 uppercase bg-gray-50 border-b border-gray-100">
          <tr>
            <th className="py-3 px-6 text-left font-medium text-gray-900">Employee</th>
            <th className="py-3 px-6 text-left font-medium text-gray-900">Department</th>
            <th className="py-3 px-6 text-left font-medium text-gray-900">Basic Salary</th>
            <th className="py-3 px-6 text-left font-medium text-gray-900">Allowances</th>
            <th className="py-3 px-6 text-left font-medium text-gray-900">Bonuses</th>
            <th className="py-3 px-6 text-left font-medium text-gray-900">Deductions</th>
            <th className="py-3 px-6 text-left font-medium text-gray-900">Net Salary</th>
            <th className="py-3 px-6 text-left font-medium text-gray-900">Month</th>
            <th className="py-3 px-6 text-center font-medium text-gray-900">Actions</th>
          </tr>
        </thead>

        <tbody>
          {salaries.map((salary) => {
              const employee = employees.find(
                  (emp) => emp.id === salary.employeeId,
                );

                const department = departments.find(
                  (dept) => dept.id === employee?.departmentId,
                );
                return (
            <tr
              key={salary.id}
              className=" bg-white border-b border-gray-200 hover:bg-gray-50 transition "
            >
              <td className="px-6 py-5 font-medium text-gray-700">{employee ? `${employee.firstName} ${employee.lastName}` : "N/A"} </td>      
              <td className="px-6 py-5 font-medium text-gray-700">{department ? department.name || department.departmentName : "N/A"} </td>        
              <td className="px-6 py-5 font-medium text-gray-700">{formatCurrency(salary.basicSalary)}</td>
              <td className="px-6 py-5 font-medium text-gray-700">
                {formatCurrency(salary.allowance)}
              </td>
              <td className="px-6 py-5 font-medium text-gray-700">{formatCurrency(salary.bonus)}</td>
              <td className="px-6 py-5 font-medium text-gray-700">{formatCurrency(salary.deduction)}</td>
              <td className="px-6 py-5 font-medium text-gray-700">{formatCurrency(salary.netSalary)}</td>
              <td className="px-6 py-5 font-medium text-gray-700">{salary.month}</td>

              <td className="px-6 py-5">
               <div className="flex justify-center gap-2">
                        
                        <button
                          onClick={() =>
                            navigate(`/salaries/edit/${salary.id}`)
                          }
                          className="p-2 bg-orange-50 text-orange-500 hover:bg-orange-100 rounded-md transition"
                        >
                          <FaEdit />
                        </button>
                        <button
                          onClick={() => setDeleteSalaryId(salary.id)}
                          className="p-2 bg-red-50 text-red-500 hover:bg-red-100 rounded-md transition"
                        >
                          <FaTrash />
                        </button>
                </div>
              </td>
            </tr>
                );
            }
          )}
        </tbody>
      </table>
    </div>
  );
}

export default SalaryTable;