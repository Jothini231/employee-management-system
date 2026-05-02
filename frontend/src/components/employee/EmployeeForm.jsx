import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  addEmployee,
  getEmployeeById,
  updateEmployee,
} from "../../services/employeeService";
import { getDepartments } from "../../services/departmentService";

import PersonalTab from "./tabs/PersonalTab";
import JobTab from "./tabs/JobTab";
import PayrollTab from "./tabs/PayrollTab";
import SystemTab from "./tabs/SystemTab";

function EmployeeForm() {
  const navigate = useNavigate();
  const { id } = useParams();

  const [activeTab, setActiveTab] = useState("personal");

  const [employee, setEmployee] = useState({
    firstName: "",
    lastName: "",
    email: "",
    contactNumber: "",
    dateOfBirth: "",
    gender: "",
    address: "",
    employeeCode: "",
    departmentId: "",
    designation: "",
    role: "",
    reportingManager: "",
    dateOfJoining: "",
    status: "Active",
    salary: "",
    bankAccountNumber: "",
    userName: "",
    lastLogin: "",
  });

  const [departments, setDepartments] = useState([]);

  useEffect(() => {
    const loadData = async () => {
      try {
       
        const deptRes = await getDepartments();
        setDepartments(deptRes.data.data);

       
        if (id) {
          const empRes = await getEmployeeById(id);
          const data = empRes.data.data;

          setEmployee({
            ...data,
            departmentId: Number(data.departmentId) || "",
          });
        }
      } catch (error) {
        console.error("Error loading data:", error);
      }
    };

    loadData();
  }, [id]);

  const handleChange = (e) => {
    setEmployee({ ...employee, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const employeeData = {
      ...employee,
      departmentId: employee.departmentId
        ? Number(employee.departmentId)
        : null,
    };

    try {
      if (id) {
        await updateEmployee(id, employeeData);
      } else {
        await addEmployee(employeeData);
      }

      navigate("/employees");
    } catch (error) {
      console.error("Error saving employee:", error);
    }
  };

  return (
    <div className="p-6 max-w-4xl">
      <h2 className="text-2xl font-bold mb-6 text-[#1E2A38]">
        {id ? "Edit Employee" : "Add New Employee"}
      </h2>

      
      <div className="flex gap-4 border-b mb-6">
        {["personal", "job", "payroll", "system"].map((tab) => (
          <button
            key={tab}
            onClick={() => setActiveTab(tab)}
            className={`pb-2 ${
              activeTab === tab
                ? "border-b-2 border-[#1E2A38] font-semibold"
                : ""
            }`}
          >
            {tab.charAt(0).toUpperCase() + tab.slice(1)}
          </button>
        ))}
      </div>

      <form onSubmit={handleSubmit} className="grid grid-cols-1 gap-4">
        {activeTab === "personal" && (
          <PersonalTab employee={employee} handleChange={handleChange} />
        )}

        {activeTab === "job" && (
          <JobTab
            employee={employee}
            handleChange={handleChange}
            departments={departments}
          />
        )}

        {activeTab === "payroll" && (
          <PayrollTab employee={employee} handleChange={handleChange} />
        )}

        {activeTab === "system" && (
          <SystemTab employee={employee} handleChange={handleChange} />
        )}

        <div className="flex justify-between mt-6">
          <button
            type="button"
            onClick={() => navigate("/employees")}
            className="px-4 py-2 rounded bg-gray-300 hover:bg-gray-400"
          >
            Cancel
          </button>

          <button
            type="submit"
            className="px-4 py-2 rounded bg-[#1E2A38] hover:bg-[#2C3A4D] text-white"
          >
            {id ? "Update Employee" : "Save Employee"}
          </button>
        </div>
      </form>
    </div>
  );
}

export default EmployeeForm;