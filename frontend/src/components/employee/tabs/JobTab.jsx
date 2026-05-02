function JobTab({ employee, handleChange, departments }) {
  return (
    <div className="grid grid-cols-2 gap-4">
      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">Employee Code</label>
        <input
          type="text"
          name="employeeCode"
          value={employee.employeeCode}
          onChange={handleChange}
          className="border p-2 rounded"
        />
      </div>

      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">Department</label>
        <select
          name="departmentId"
          value={employee.departmentId}
          onChange={handleChange}
          className="border p-2 rounded"
          required
        >
          <option value="">Select Department</option>
          {departments.map((dept) => (
            <option key={dept.id} value={dept.id}>
              {dept.name}
            </option>
          ))}
        </select>
      </div>

      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">Designation</label>
        <input
          type="text"
          name="designation"
          value={employee.designation}
          onChange={handleChange}
          className="border p-2 rounded"
        />
      </div>

      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">Role</label>
        <input
          type="text"
          name="role"
          value={employee.role}
          onChange={handleChange}
          className="border p-2 rounded"
        />
      </div>

      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">Reporting Manager</label>
        <input
          type="text"
          name="reportingManager"
          value={employee.reportingManager}
          onChange={handleChange}
          className="border p-2 rounded"
        />
      </div>

      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">Date of Joining</label>
        <input
          type="date"
          name="dateOfJoining"
          value={employee.dateOfJoining}
          onChange={handleChange}
          className="border p-2 rounded"
        />
      </div>

      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">Status</label>
        <select
          name="status"
          value={employee.status}
          onChange={handleChange}
          className="border p-2 rounded"
        >
          <option value="Active">Active</option>
          <option value="Inactive">Inactive</option>
        </select>
      </div>
    </div>
  );
}

export default JobTab;