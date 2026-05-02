function PersonalTab({ employee, handleChange }) {
  return (
    <div className="grid grid-cols-2 gap-4">
      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">First Name</label>
        <input
          type="text"
          name="firstName"
          value={employee.firstName}
          onChange={handleChange}
          className="border p-2 rounded"
          required
        />
      </div>

      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">Last Name</label>
        <input
          type="text"
          name="lastName"
          value={employee.lastName}
          onChange={handleChange}
          className="border p-2 rounded"
          required
        />
      </div>

      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">Email</label>
        <input
          type="email"
          name="email"
          value={employee.email}
          onChange={handleChange}
          className="border p-2 rounded"
          required
        />
      </div>

      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">Contact Number</label>
        <input
          type="text"
          name="contactNumber"
          value={employee.contactNumber}
          onChange={handleChange}
          className="border p-2 rounded"
        />
      </div>

      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">Date of Birth</label>
        <input
          type="date"
          name="dateOfBirth"
          value={employee.dateOfBirth}
          onChange={handleChange}
          className="border p-2 rounded"
        />
      </div>

      <div className="flex flex-col">
        <label className="text-gray-700 mb-1">Gender</label>
        <select
          name="gender"
          value={employee.gender}
          onChange={handleChange}
          className="border p-2 rounded"
        >
          <option value="">Select Gender</option>
          <option value="Male">Male</option>
          <option value="Female">Female</option>
        </select>
      </div>

      <div className="flex flex-col col-span-2">
        <label className="text-gray-700 mb-1">Address</label>
        <textarea
          name="address"
          value={employee.address}
          onChange={handleChange}
          className="border p-2 rounded"
          rows="3"
        />
      </div>
    </div>
  );
}

export default PersonalTab;