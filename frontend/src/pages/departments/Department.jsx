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

function Department() {
  const [departments, setDepartments] = useState([]);
  const [isAddOpen, setIsAddOpen] = useState(false);
  const [editDept, setEditDept] = useState(null);
  const [employees, setEmployees] = useState([]);
  const [viewDeptId, setViewDeptId] = useState(null);
  const [assignDeptId,setAssignDeptId] = useState(null);
  const [deleteDeptId,setDeleteDeptId] = useState(null);
  const [deleteDeptName,setDeleteDeptName] = useState("");
  

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
  
  const confirmDeleteDepartment = (id,name) => {
    setDeleteDeptId(id);
    setDeleteDeptName(name);

  }

  const handleDelete = async () => {

    if(deleteDeptId){
    try {
      await deleteDepartment(deleteDeptId);
      await fetchDepartments();
      closeDeleteModal();
    } catch (error) {
      console.log(error);
    }
  }
  };


  const closeDeleteModal = () =>{
    setDeleteDeptId(null);
    setDeleteDeptName("");
  }

  
  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-6">Department Management</h2>
      <button
        className="bg-[#1E2A38] hover:bg-[#2C3A4D] mb-10 text-white px-4 py-2 rounded shadow cursor-pointer"
        onClick={() => setIsAddOpen(true)}
      >
        + Add Department
      </button>

      <DepartmentTable
        departments={departments}
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
    refresh={fetchDepartments}
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

