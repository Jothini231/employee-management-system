import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { getEmployees } from "../../services/employeeService";
import { applyLeave } from "../../services/leaveService";

function ApplyLeave() {
    const navigate = useNavigate();

    const [employees, setEmployees] = useState([]);

    const [formData, setFormData] = useState({
        employeeId: "",
        startDate: "",
        endDate: "",
        reason: "",
        leaveType: "CASUAL"
    });

    const [loading, setLoading] = useState(false);

    useEffect(() => {
        loadEmployees();
    }, []);

    const loadEmployees = async () => {
        try {
            const res = await getEmployees();
            console.log("EMP API RESPONSE:", res);
            setEmployees(res.data.data);
        } catch (error) {
            console.log("Error loading employees:", error);
        }
    };

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!formData.employeeId || !formData.startDate || !formData.endDate) {
            alert("Please fill required fields");
            return;
        }

        try {
            setLoading(true);

            await applyLeave(formData);

            alert("Leave applied successfully!");

            navigate("/leave"); 

        } catch (error) {
            console.log("Error applying leave:", error);
            alert("Failed to apply leave");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-gray-50 p-8 flex justify-center">

            <div className="bg-white shadow-lg rounded-xl w-full max-w-2xl p-6">

                <h2 className="text-2xl font-bold mb-6 text-gray-800">
                    Apply for Leave
                </h2>

                <form onSubmit={handleSubmit} className="space-y-5">

                    
                    <div>
                        <label className="block text-gray-600 mb-1">
                            Employee
                        </label>

                        <select
                            name="employeeId"
                            value={formData.employeeId}
                            onChange={handleChange}
                            className="w-full border rounded-lg p-2"
                        >
                            <option value="">Select Employee</option>
                            {employees.map(emp => (
                                <option key={emp.id} value={emp.id}>
                                    {emp.firstName} {emp.lastName}
                                </option>
                            ))}
                        </select>
                    </div>

                   
                    <div>
                        <label className="block text-gray-600 mb-1">
                            Leave Type
                        </label>

                        <select
                            name="leaveType"
                            value={formData.leaveType}
                            onChange={handleChange}
                            className="w-full border rounded-lg p-2"
                        >
                            <option value="CASUAL">Casual</option>
                            <option value="SICK">Sick</option>
                            <option value="ANNUAL">Annual</option>
                        </select>
                    </div>

                    
                    <div className="grid grid-cols-2 gap-4">

                        <div>
                            <label className="block text-gray-600 mb-1">
                                Start Date
                            </label>

                            <input
                                type="date"
                                name="startDate"
                                value={formData.startDate}
                                onChange={handleChange}
                                className="w-full border rounded-lg p-2"
                            />
                        </div>

                        <div>
                            <label className="block text-gray-600 mb-1">
                                End Date
                            </label>

                            <input
                                type="date"
                                name="endDate"
                                value={formData.endDate}
                                onChange={handleChange}
                                className="w-full border rounded-lg p-2"
                            />
                        </div>

                    </div>

                    
                    <div>
                        <label className="block text-gray-600 mb-1">
                            Reason
                        </label>

                        <textarea
                            name="reason"
                            value={formData.reason}
                            onChange={handleChange}
                            rows="4"
                            className="w-full border rounded-lg p-2"
                            placeholder="Enter reason for leave..."
                        />
                    </div>

                    
                    <div className="flex justify-between">

                        <button
                            type="button"
                            onClick={() => navigate("/leave")}
                            className="px-4 py-2 border rounded-lg"
                        >
                            Cancel
                        </button>

                        <button
                            type="submit"
                            disabled={loading}
                            className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700"
                        >
                            {loading ? "Submitting..." : "Apply Leave"}
                        </button>

                    </div>

                </form>

            </div>

        </div>
    );
}

export default ApplyLeave;