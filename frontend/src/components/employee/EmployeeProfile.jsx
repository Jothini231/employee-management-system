import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getEmployees, deleteEmployee,getEmployeeById } from "../../services/employeeService";
import { getDepartments } from "../../services/departmentService";
import DeleteModal from "../common/DeleteModel";


function EmployeeProfile() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [employee, setEmployee] = useState(null);
   const [departments, setDepartments] = useState([]);
  const [activeTab, setActiveTab] = useState("overview");

  const [deleteEmployeeId, setDeleteEmployeeId] = useState(null);
  const [deleteEmployeeName, setDeleteEmployeeName] = useState("");

  useEffect(() => {
    loadEmployee();
    loadDepartments(); 
  }, [id]);

  const loadEmployee = async () => {
  try {
    const res = await getEmployeeById(id);
    setEmployee(res.data.data);
  } catch (err) {
    console.error("Error loading employee:", err);
  }
};
const loadDepartments = async () => {
  try {
    const res = await getDepartments();
    setDepartments(res.data.data);
  } catch (err) {
    console.error("Error loading departments:", err);
  }
};

 
  const getDepartmentName = (departmentId) => {
    if (!departmentId) return "N/A";
    const department = departments.find(dept => dept.id === departmentId);
    return department ? department.name : "Unknown Department";
  };

  const confirmDeleteEmployee = (id, name) => {
    setDeleteEmployeeId(id);
    setDeleteEmployeeName(name);
  };

 const handleDelete = async () => {
  if (!deleteEmployeeId) return;

  try {
    await deleteEmployee(deleteEmployeeId);
    navigate("/employees");
  } catch (err) {
    console.error("Error deleting employee:", err);
  }
};

  const closeDeleteModal = () => {
    setDeleteEmployeeId(null);
    setDeleteEmployeeName("");
  };

  if (!employee) return <p className="p-6">Loading...</p>;

  return (
    <div className="p-6 max-w-4xl">
      <h2 className="text-2xl font-bold mb-4 text-[#1E2A38]">Employee Profile</h2>
      <div className="flex items-center gap-6 mb-6">
       
        <div className="h-24 w-24 rounded-full bg-gray-300 flex items-center justify-center text-2xl font-bold">
          {employee.firstName.charAt(0)}
        </div>
       
        <div>
          <h3 className="text-xl font-semibold">{employee.firstName} {employee.lastName}</h3>
          <p>Email: {employee.email}</p>
          <p>Department: {employee.departmentName || getDepartmentName(employee.departmentId)}</p>
          <p>Position: {employee.designation}</p>
          <p>Status: {employee.status}</p>
        </div>
      </div>

     
      <div className="flex gap-4 border-b mb-4">
        {["overview", "leave", "salary", "documents"].map((tab) => (
          <button
            key={tab}
            onClick={() => setActiveTab(tab)}
            className={`pb-2 ${
              activeTab === tab && "border-b-2 border-blue-500 font-semibold"
            }`}
          >
            {tab === "leave" ? "Leave History" : tab.charAt(0).toUpperCase() + tab.slice(1)}
          </button>
        ))}
      </div>

      
      <div className="mb-6">
        {activeTab === "overview" && (
          <div className="grid grid-cols-2 gap-4">
            <p><strong>Contact:</strong> {employee.contactNumber}</p>
            <p><strong>DOB:</strong> {employee.dateOfBirth}</p>
            <p><strong>Gender:</strong> {employee.gender}</p>
            <p><strong>Address:</strong> {employee.address}</p>
          </div>
        )}
        {activeTab === "leave" && <p>Leave history content here...</p>}
        {activeTab === "salary" && <p>Salary details content here...</p>}
        {activeTab === "documents" && <p>Employee documents content here...</p>}
      </div>

    
     <div className="flex gap-4 mt-6">

        <button
          onClick={() => navigate("/employees")}
          className="px-4 py-2 bg-gray-300 hover:bg-gray-400 rounded"
        >
          Back
        </button>

        <button
          onClick={() => navigate(`/employees/edit/${employee.id}`)}
          className="px-4 py-2 bg-[#1E2A38] hover:bg-[#2C3A4D] text-white rounded flex items-center gap-1"
        >
          Edit
        </button>
        <button
          onClick={() => confirmDeleteEmployee(employee.id, `${employee.firstName} ${employee.lastName}`)}
          className="px-4 py-2 bg-red-500 hover:bg-red-600 text-white rounded flex items-center gap-1"
        >
          Delete
        </button>
        
      </div>
      {deleteEmployeeId && (
        <DeleteModal
          name={deleteEmployeeName}
          onCancel={closeDeleteModal}
          onConfirm={handleDelete}
        />
      )}
    </div>
  );
}

export default EmployeeProfile;