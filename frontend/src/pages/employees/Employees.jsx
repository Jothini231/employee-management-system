import { useEffect, useState } from "react";
import { getEmployees, deleteEmployee } from "../../services/employeeService";
import { getDepartments } from "../../services/departmentService";
import { useNavigate } from "react-router-dom";
import DeleteModal from "../../components/common/DeleteModel";
import EmployeeTable from "../../components/employee/EmployeeTable";

function Employees() {
  const [employees, setEmployees] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [deleteEmployeeId, setDeleteEmployeeId] = useState(null);
  const [deleteEmployeeName, setDeleteEmployeeName] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    loadEmployees();
    loadDepartments();
  }, []);

  const loadEmployees = async () => {
    try {
      const res = await getEmployees();
      setEmployees(res.data.data);
    } catch (error) {
      console.log(error);
    }
  };

  const loadDepartments = async () => {
    try {
      const res = await getDepartments();
      setDepartments(res.data.data);
    } catch (error) {
      console.log(error);
    }
  };

  const getDepartmentName = (departmentId) => {
    if (!departmentId) return "N/A";
    const department = departments.find((dept) => dept.id === departmentId);
    return department ? department.name : "Unknown Department";
  };

  const confirmDeleteEmployee = (id, name) => {
    setDeleteEmployeeId(id);
    setDeleteEmployeeName(name);
  };

  const handleDelete = async () => {
    if (deleteEmployeeId) {
      try {
        await deleteEmployee(deleteEmployeeId);
        await loadEmployees();
        closeDeleteModal();
      } catch (err) {
        console.log(err);
      }
    }
  };

  const closeDeleteModal = () => {
    setDeleteEmployeeId(null);
    setDeleteEmployeeName("");
  };

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-6">Employee Management</h2>

      <button
        onClick={() => navigate("/employees/add")}
        className="bg-[#1E2A38] hover:bg-[#2C3A4D] mb-10 text-white px-4 py-2 rounded shadow cursor-pointer"
      >
        + Add Employee
      </button>

      
      <EmployeeTable
        employees={employees}
        getDepartmentName={getDepartmentName}
        onView={(id) => navigate(`/employees/view/${id}`)}
        onEdit={(id) => navigate(`/employees/edit/${id}`)}
        onDelete={confirmDeleteEmployee}
      />

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

export default Employees;