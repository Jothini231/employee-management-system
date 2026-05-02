function PayrollTab({ employee, handleChange }) {
  return (
    <div className="grid grid-cols-2 gap-4">
      {["salary", "bankAccountNumber"].map((field) => (
        <div key={field} className="flex flex-col">
          <label className="text-gray-700 mb-1">
            {field === "salary" ? "Salary" : "Bank Account Number"}
          </label>
          <input
            type={field === "salary" ? "number" : "text"}
            name={field}
            value={employee[field]}
            onChange={handleChange}
            className="border p-2 rounded"
          />
        </div>
      ))}
    </div>
  );
}

export default PayrollTab;