function LeaveFilters({
    employees,
    departments,
    filterEmpId,
    filterDeptId,
    filterStartDate,
    filterEndDate,
    setFilterStartDate,
    setFilterEndDate,
    handleFilterByEmployee,
    handleFilterByDepartment,
    handleFilterByDates,
    handleClearFilters
}) {

    return (
                    
            <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-6 mb-8">
                <div className="flex justify-between items-center mb-4">
                    <h2 className="text-lg font-semibold text-gray-800">Advanced Filters</h2>
                    <button onClick={handleClearFilters} className="text-sm text-indigo-600 hover:text-indigo-800 font-medium transition-colors">Clear All Filters</button>
                </div>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    
                    <div className="flex flex-col gap-2">
                        <label className="text-sm font-medium text-gray-700">By Employee</label>
                        <select
                            value={filterEmpId}
                            onChange={(e) => handleFilterByEmployee(e.target.value)}
                            className="bg-gray-50 border border-gray-200 text-gray-700 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5 outline-none"
                        >
                            <option value="">All Employees</option>
                            {employees.map(emp => (
                                <option key={emp.id} value={emp.id}>
                                    {emp.firstName} {emp.lastName} ({emp.employeeCode || `#${emp.id}`})
                                </option>
                            ))}
                        </select>
                    </div>

                    
                    <div className="flex flex-col gap-2">
                        <label className="text-sm font-medium text-gray-700">By Department</label>
                        <select
                            value={filterDeptId}
                            onChange={(e) => handleFilterByDepartment(e.target.value)}
                            className="bg-gray-50 border border-gray-200 text-gray-700 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5 outline-none"
                        >
                            <option value="">All Departments</option>
                            {departments.map(dept => (
                                <option key={dept.id} value={dept.id}>
                                    {dept.name || `Department ${dept.id}`}
                                </option>
                            ))}
                        </select>
                    </div>



                    
                    <div className="flex flex-col gap-2">
                        <label className="text-sm font-medium text-gray-700">By Date Range</label>
                        <div className="flex gap-2 items-center">
                            <input
                                type="date"
                                value={filterStartDate}
                                onChange={(e) => setFilterStartDate(e.target.value)}
                                className="bg-gray-50 border border-gray-200 text-gray-700 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5 outline-none"
                            />
                            <span className="text-gray-500">to</span>
                            <input
                                type="date"
                                value={filterEndDate}
                                onChange={(e) => setFilterEndDate(e.target.value)}
                                className="bg-gray-50 border border-gray-200 text-gray-700 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5 outline-none"
                            />

                        </div>
                    </div>
                </div>
            </div>
    );
}

export default LeaveFilters;