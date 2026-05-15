import React from "react";
import { FaSearch, FaRedo } from "react-icons/fa";

function SalaryFilters({
  months,
  month,
  setMonth,
  departmentId,
  setDepartmentId,
  employeeId,
  setEmployeeId,
  employees,
  departments,
  handleSearch,
  handleReset,
  fetchData,
}){

    return (
        <div className="bg-white p-4 rounded-xl border border-gray-100 shadow-sm mb-6 flex flex-wrap gap-4 items-end">
        <div className="flex-1 min-w-[200px]">
          <label className="block text-sm font-medium text-gray-600 mb-1">
            Month
          </label>
          <select
            value={month}
            onChange={(e) => setMonth(e.target.value)}
            className="w-full border border-gray-200 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 text-gray-700 bg-white"
          >
            <option value="">All Months</option>
            {months.map((m) => (
                <option key={m} value={m}>{m}</option>
            ))}
          </select>
        </div>

        <div className="flex-1 min-w-[200px]">
          <label className="block text-sm font-medium text-gray-600 mb-1">
            Department
          </label>
          <select
            value={departmentId}
            onChange={(e) => setDepartmentId(e.target.value)}
            className="w-full border border-gray-200 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 text-gray-700 bg-white"
          >
            <option value="">All Departments</option>
            {departments.map((dept) => (
              <option key={dept.id || dept._id} value={dept.id || dept._id}>
                {dept.name || dept.departmentName}
              </option>
            ))}
          </select>
        </div>

        <div className="flex-1 min-w-[200px]">
          <label className="block text-sm font-medium text-gray-600 mb-1">
            Employee
          </label>
          <select
            value={employeeId}
            onChange={(e) => setEmployeeId(e.target.value)}
            className="w-full border border-gray-200 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 text-gray-700 bg-white"
          >
            <option value="">All Employees</option>
            {employees.map((emp) => (
              <option key={emp.id || emp._id} value={emp.id || emp._id}>
                {emp.firstName} {emp.lastName}
              </option>
            ))}
          </select>
        </div>

        <div className="flex gap-3">
          {/* <button
            onClick={handleSearch}
            className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-lg flex items-center gap-2 transition font-medium h-[42px]"
          >
            <FaSearch /> Search
          </button> */}
          <button
            onClick={handleReset}
            className="bg-white border border-gray-300 hover:bg-gray-50 text-gray-700 px-6 py-2 rounded-lg flex items-center gap-2 transition font-medium h-[42px]"
          >
            <FaRedo /> Reset
          </button>
        </div>
      </div>
    );
}

export default SalaryFilters;