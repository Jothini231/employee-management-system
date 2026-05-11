import { useEffect, useState } from "react";
import {
  MagnifyingGlassIcon,
  ArrowPathIcon,
} from "@heroicons/react/24/outline";

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

  const [filteredEmployees, setFilteredEmployees] = useState([]);
  const [search, setSearch] = useState("");
  const [departmentFilter, setDepartmentFilter] = useState("");
  const [statusFilter, setStatusFilter] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    loadEmployees();
    loadDepartments();
  }, []);

  useEffect(() => {
    filterEmployees();
  }, [search, departmentFilter, statusFilter, employees]);

  const loadEmployees = async () => {
    try {
      const res = await getEmployees();
      setEmployees(res.data.data);
      setFilteredEmployees(res.data.data);
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

  const filterEmployees = () => {
    let filtered = [...employees];

    if (search) {
      filtered = filtered.filter(
        (emp) =>
          emp.firstName.toLowerCase().includes(search.toLowerCase()) ||
          emp.lastName.toLowerCase().includes(search.toLowerCase()) ||
          emp.email.toLowerCase().includes(search.toLowerCase()),
      );
    }

    if (departmentFilter) {
      filtered = filtered.filter(
        (emp) => String(emp.departmentId) === departmentFilter,
      );
    }

    if (statusFilter) {
      filtered = filtered.filter((emp) => emp.status === statusFilter);
    }

    setFilteredEmployees(filtered);
  };

  const resetFilters = () => {
    setSearch("");
    setDepartmentFilter("");
    setStatusFilter("");
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
    <div className="p-8 bg-gray-50 min-h-screen">
      <div className="mb-8 flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold  text-gray-800">
            Employee Management
          </h1>

          <p className="text-gray-500 mt-1">
            Manage and view employee information
          </p>
        </div>

        <button
          onClick={() => navigate("/employees/add")}
          className="bg-[#1E2A38] hover:bg-[#2C3A4D]  text-white px-4 py-2 rounded-lg transition cursor-pointer"
        >
          + Add Employee
        </button>
      </div>

      <div className="bg-white p-5 rounded-2xl shadow-sm border border-gray-100 mb-6">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
          <div className="relative">
            <input
              type="text"
              placeholder="Search by name, email or phone..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              className="w-full border border-gray-200 rounded-xl py-3 px-4 pr-10 outline-none focus:ring-2 focus:ring-blue-500"
            />

            <MagnifyingGlassIcon className="w-5 h-5 text-gray-400 absolute top-3.5 right-3" />
          </div>

          <select
            value={departmentFilter}
            onChange={(e) => setDepartmentFilter(e.target.value)}
            className="border border-gray-200 rounded-xl px-4 py-3 outline-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="">All Departments</option>

            {departments.map((dept) => (
              <option key={dept.id} value={dept.id}>
                {dept.name}
              </option>
            ))}
          </select>

          <select
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value)}
            className="border border-gray-200 rounded-xl px-4 py-3 outline-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="">All Status</option>
            <option value="Active">Active</option>
            <option value="Inactive">Inactive</option>
          </select>

          <button
            onClick={resetFilters}
            className="border border-gray-200 rounded-xl px-4 py-3 flex items-center justify-center gap-2 hover:bg-gray-100"
          >
            <ArrowPathIcon className="w-5 h-5" />
            Reset
          </button>
        </div>
      </div>

      <EmployeeTable
        employees={filteredEmployees}
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
