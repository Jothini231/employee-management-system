import React, { useEffect, useState } from "react";
import {  assignManager } from "../../services/departmentService";
import { getEmployeesByDepartment } from "../../services/employeeService";

export default function AssignManagerModal({ deptId, close, refresh }) {
  const [managers, setManagers] = useState([]);
  const [selectedManagerId, setSelectedManagerId] = useState("");

  useEffect(() => {
    loadManagers();
  }, []);

  const loadManagers = async () => {
    try {
      const res = await getEmployeesByDepartment(deptId);
      setManagers(res.data.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleSave = async () => {
    if (!selectedManagerId) return;

    await assignManager(deptId, Number(selectedManagerId));

    close();
    refresh();
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black/30 backdrop-blur-sm">

      <div className="bg-white w-[400px] p-6 rounded-xl shadow-lg">

        <h2 className="text-xl font-bold mb-4 text-[#1E2A38]">
          Assign Manager
        </h2>

       
        {managers.length === 0 ? (
          <p className="text-red-500 text-sm mb-4">
            No managers available in this department
          </p>
        ) : (
          <select
            className="w-full p-2 border rounded mb-4"
            value={selectedManagerId}
            onChange={(e) => setSelectedManagerId(e.target.value)}
          >
            <option value="">Select Manager</option>
            {managers.map((m) => (
              <option key={m.id} value={m.id}>
                {m.firstName} {m.lastName}
              </option>
            ))}
          </select>
        )}

        
        <div className="flex justify-end gap-2">
          <button
            onClick={close}
            className="px-3 py-1 bg-gray-300 rounded"
          >
            Cancel
          </button>

          <button
            disabled={managers.length === 0 || !selectedManagerId}
            onClick={handleSave}
            className={`px-3 py-1 text-white rounded
              ${managers.length === 0 || !selectedManagerId
                ? "bg-gray-400 cursor-not-allowed"
                : "bg-blue-600 hover:bg-blue-700"}
            `}
          >
            Save
          </button>
        </div>

      </div>
    </div>
  );
}