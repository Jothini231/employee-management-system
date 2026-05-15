import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { addSalary, getSalaryById, updateSalary } from "../../services/salaryService";
import { getEmployees } from "../../services/employeeService";

function AddEditSalaryPage() {
    const { id } = useParams();
    const isEditMode = Boolean(id);
    const navigate = useNavigate();

    const [employees, setEmployees] = useState([]);
    const [loading, setLoading] = useState(false);
    const [formData, setFormData] = useState({
        employeeId: "",
        basicSalary: "",
        allowance: "",
        bonus: "",
        deduction: "",
        month: "",
    });

    useEffect(() => {
        fetchEmployees();
        if (isEditMode) fetchSalary();
    }, [id]);

    const fetchEmployees = async () => {
        try {
            const res = await getEmployees();
            setEmployees(res.data.data || []);
        } catch (error) {
            console.log(error);
        }
    };

    const fetchSalary = async () => {
        try {
            const res = await getSalaryById(id);
            setFormData(res.data.data);
        } catch (error) {
            console.log(error);
        }
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!formData.employeeId || !formData.basicSalary || !formData.month) {
            alert("Please fill required fields");
            return;
        }

        try {
            setLoading(true);
            if (isEditMode) {
                await updateSalary(id, formData);
            } else {
                await addSalary(formData);
            }
            navigate("/salary");
        } catch (error) {
            console.log(error);
            alert("Failed to save salary");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-gray-50 p-8 flex justify-center">

            <div className="bg-white shadow-lg rounded-xl w-full max-w-2xl p-6">

                <h2 className="text-2xl font-bold mb-6 text-gray-800">
                    {isEditMode ? "Edit Salary" : "Add Salary"}
                </h2>

                <form onSubmit={handleSubmit} className="space-y-5">

                    <div>
                        <label className="block text-gray-600 mb-1">Employee</label>
                        <select
                            name="employeeId"
                            value={formData.employeeId}
                            onChange={handleChange}
                            disabled={isEditMode}
                            className="w-full border rounded-lg p-2"
                        >
                            <option value="">Select Employee</option>
                            {employees.map((emp) => (
                                <option key={emp.id} value={emp.id}>
                                    {emp.firstName} {emp.lastName}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div>
                        <label className="block text-gray-600 mb-1">Month</label>
                        <input
                            type="text"
                            name="month"
                            value={formData.month}
                            onChange={handleChange}
                            placeholder="e.g. May 2026"
                            className="w-full border rounded-lg p-2"
                        />
                    </div>

                    {/* <div className="flex flex-col gap-4"> */}
                        <div>
                            <label className="block text-gray-600 mb-1">Basic Salary</label>
                            <input
                                type="number"
                                name="basicSalary"
                                value={formData.basicSalary}
                                onChange={handleChange}
                                className="w-full border rounded-lg p-2"
                            />
                        </div>

                        <div>
                            <label className="block text-gray-600 mb-1">Allowance</label>
                            <input
                                type="number"
                                name="allowance"
                                value={formData.allowance}
                                onChange={handleChange}
                                className="w-full border rounded-lg p-2"
                            />
                        </div>

                        <div>
                            <label className="block text-gray-600 mb-1">Bonus</label>
                            <input
                                type="number"
                                name="bonus"
                                value={formData.bonus}
                                onChange={handleChange}
                                className="w-full border rounded-lg p-2"
                            />
                        </div>

                        <div>
                            <label className="block text-gray-600 mb-1">Deduction</label>
                            <input
                                type="number"
                                name="deduction"
                                value={formData.deduction}
                                onChange={handleChange}
                                className="w-full border rounded-lg p-2"
                            />
                        </div>
                    {/* </div> */}

                    <div className="flex justify-between pt-6">
                        <button
                            type="button"
                            onClick={() => navigate("/salary")}
                            className="px-4 py-2 border rounded-lg"
                        >
                            Cancel
                        </button>

                        <button
                            type="submit"
                            disabled={loading}
                            className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700"
                        >
                            {loading ? "Submitting..." : isEditMode ? "Update" : "Save"}
                        </button>
                    </div>

                </form>
            </div>
        </div>
    );
}

export default AddEditSalaryPage;