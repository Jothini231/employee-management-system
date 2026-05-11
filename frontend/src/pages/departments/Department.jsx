import DepartmentModal from "../../components/department/DepartmentModel";
import DepartmentTable from "../../components/department/DepartmentTable";
import { useEffect, useState } from "react";
import {
  assignManager,
  createDepartment,
  deleteDepartment,
  getDepartmentById,
  getDepartments,
  updateDepartment,
} from "../../services/departmentService";
import { getEmployeesByDepartment } from "../../services/employeeService";
import AssignManagerModal from "../../components/department/AssignManagerModel";
import DeleteModal from "../../components/common/DeleteModel";
import { MagnifyingGlassIcon } from "@heroicons/react/24/outline";

function Department() {
  const [departments, setDepartments] = useState([]);
  const [isAddOpen, setIsAddOpen] = useState(false);
  const [editDept, setEditDept] = useState(null);
  const [employees, setEmployees] = useState([]);
  const [viewDeptId, setViewDeptId] = useState(null);
  const [assignDeptId, setAssignDeptId] = useState(null);
  const [deleteDeptId, setDeleteDeptId] = useState(null);
  const [deleteDeptName, setDeleteDeptName] = useState("");
  const [search, setSearch] = useState("");
  const [statusFilter, setStatusFilter] = useState("ALL");

  useEffect(() => {
    loadDepartments();
  }, []);

  const loadDepartments = async () => {
    try {
      const res = await getDepartments();
      setDepartments(res.data.data);
    } catch (error) {
      console.log(error);
    }
  };

  const loadEmployees = async (deptId) => {
    try {
      const res = await getEmployeesByDepartment(deptId);
      setEmployees(res.data.data);
    } catch (error) {
      console.log(error);
    }
  };

  const handleAdd = async (data) => {
    console.log("Sending department data:", data);
    try {
      const response = await createDepartment(data);
      console.log("Response:", response);
      await fetchDepartments();
      setIsAddOpen(false);
    } catch (error) {
      console.log(error);
    }
  };

  const handleEditClick = async (id) => {
    try {
      const res = await getDepartmentById(id);
      setEditDept(res.data.data);
    } catch (error) {
      console.log(error);
    }
  };

  const handleEdit = async (data, managerId) => {
    try {
      await updateDepartment(data.id, data);

      if (managerId) {
        await assignManager(data.id, managerId);
      }
      await fetchDepartments();
      setEditDept(null);
    } catch (error) {
      console.log(error);
    }
  };

  const handleViewEmployees = async (deptId) => {
    setViewDeptId(deptId);
    await loadEmployees(deptId);
  };

  const confirmDeleteDepartment = (id, name) => {
    setDeleteDeptId(id);
    setDeleteDeptName(name);
  };

  const handleDelete = async () => {
    if (deleteDeptId) {
      try {
        await deleteDepartment(deleteDeptId);
        await fetchDepartments();
        closeDeleteModal();
      } catch (error) {
        console.log(error);
      }
    }
  };

  const closeDeleteModal = () => {
    setDeleteDeptId(null);
    setDeleteDeptName("");
  };

  const filteredDepartments = departments.filter((dept) => {
    const matchesSearch = dept.name
      .toLowerCase()
      .includes(search.toLowerCase());

    const matchesStatus =
      statusFilter === "ALL"
        ? true
        : statusFilter === "ACTIVE"
          ? dept.status === true
          : dept.status === false;

    return matchesSearch && matchesStatus;
  });

  return (
    <div className="p-8 bg-gray-50 min-h-screen">
      <div className="mb-8 flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold  text-gray-800">
            Department Management
          </h1>

          <p className="text-gray-500 mt-1">
            Manage all departments in the organization.
          </p>
        </div>

        <button
          className="bg-[#1E2A38] hover:bg-[#2C3A4D]  text-white px-4 py-2 rounded-lg transition cursor-pointer"
          onClick={() => setIsAddOpen(true)}
        >
          + Add Department
        </button>
      </div>

      <div className="mb-6 flex flex-col md:flex-row gap-4 justify-between">
        <div className="relative w-full md:w-1/3">
          <MagnifyingGlassIcon className="w-5 h-5 text-gray-400 absolute left-3 top-1/2 transform -translate-y-1/2" />

          <input
            type="text"
            placeholder="Search departments..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="w-full pl-10 pr-4 py-2 border rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>

        <select
          value={statusFilter}
          onChange={(e) => setStatusFilter(e.target.value)}
          className="w-full md:w-1/4 px-4 py-2 border rounded-lg shadow-sm cursor-pointer"
        >
          <option value="ALL">All Status</option>
          <option value="ACTIVE">Active</option>
          <option value="INACTIVE">Inactive</option>
        </select>
      </div>

      <DepartmentTable
        departments={filteredDepartments}
        onEdit={handleEditClick}
        onDelete={confirmDeleteDepartment}
        onView={handleViewEmployees}
        onAssignManager={(id) => setAssignDeptId(id)}
      />

      {isAddOpen && (
        <DepartmentModal close={() => setIsAddOpen(false)} onSave={handleAdd} />
      )}

      {editDept && (
        <DepartmentModal
          initialData={editDept}
          close={() => setEditDept(null)}
          onSave={handleEdit}
        />
      )}

      {viewDeptId && (
        <div className="fixed inset-0 flex items-center justify-center bg-black/30 backdrop-blur-sm z-50">
          <div className="bg-white w-[500px] rounded-xl shadow-lg p-6">
            <h2 className="text-xl font-bold mb-4">Department Employees</h2>

            <table className="w-full border">
              <thead>
                <tr className="bg-gray-100">
                  <th className="p-2 border">Name</th>
                  <th className="p-2 border">Role</th>
                </tr>
              </thead>

              <tbody>
                {employees.map((emp) => (
                  <tr key={emp.id}>
                    <td className="p-2 border">
                      {emp.firstName} {emp.lastName}
                    </td>
                    <td className="p-2 border">{emp.role}</td>
                  </tr>
                ))}
              </tbody>
            </table>

            <div className="flex justify-end mt-4">
              <button
                onClick={() => setViewDeptId(null)}
                className="px-4 py-2 bg-gray-300 rounded"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}

      {assignDeptId && (
        <AssignManagerModal
          deptId={assignDeptId}
          close={() => setAssignDeptId(null)}
          refresh={loadDepartments}
        />
      )}

      {deleteDeptId && (
        <DeleteModal
          name={deleteDeptName}
          onCancel={closeDeleteModal}
          onConfirm={handleDelete}
        />
      )}
    </div>
  );
}

export default Department;
