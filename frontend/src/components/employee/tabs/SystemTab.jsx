function SystemTab({ employee, handleChange }) {
  return (
    <div className="grid grid-cols-2 gap-4">
      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">Username</label>
        <input
          type="text"
          name="userName"
          value={employee.userName}
          onChange={handleChange}
          className="border p-2 rounded"
        />
      </div>

      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">Last Login</label>
        <input
          type="datetime-local"
          name="lastLogin"
          value={employee.lastLogin}
          onChange={handleChange}
          className="border p-2 rounded"
        />
      </div>
    </div>
  );
}

export default SystemTab;