
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import {
    getAllLeaves,
    approveLeave,
    rejectLeave,
    getLeaveByEmployee,
    getLeavesByDepartment,
    getLeavesBetweenDates
} from "../../services/leaveService";

import { getEmployees } from "../../services/employeeService";
import { getDepartments } from "../../services/departmentService";

import LeaveStats from "../../components/leave/LeaveStats";
import LeaveFilters from "../../components/leave/LeaveFilters";
import LeaveTable from "../../components/leave/LeaveTable";

function LeavePage() {

    const navigate = useNavigate();

    const [leaves, setLeaves] = useState([]);
    const [filteredLeaves, setFilteredLeaves] = useState([]);
    const [filterStatus, setFilterStatus] = useState("All");

    const [filterEmpId, setFilterEmpId] = useState("");
    const [filterDeptId, setFilterDeptId] = useState("");
    const [filterStartDate, setFilterStartDate] = useState("");
    const [filterEndDate, setFilterEndDate] = useState("");

    const [employees, setEmployees] = useState([]);
    const [departments, setDepartments] = useState([]);

    useEffect(() => {
        loadLeaves();
        loadEmployeesAndDepartments();
    }, []);

    useEffect(() => {
        if (filterStatus === "All") {
            setFilteredLeaves(leaves);
        } else {
            setFilteredLeaves(
                leaves.filter((l) => l.status === filterStatus)
            );
        }
    }, [leaves, filterStatus]);

    const loadEmployeesAndDepartments = async () => {
        try {
            const [empRes, deptRes] = await Promise.all([
                getEmployees(),
                getDepartments()
            ]);

            setEmployees(empRes.data.data || empRes.data || []);
            setDepartments(deptRes.data.data || deptRes.data || []);

        } catch (error) {
            console.log("Error loading employees/departments:", error);
        }
    };

    const loadLeaves = async () => {
        try {
            const res = await getAllLeaves();
            setLeaves(res.data.data || res.data || []);
        } catch (error) {
            console.log("Error loading leaves:", error);
        }
    };

    const handleApprove = async (id) => {
        try {
            await approveLeave(id);
            loadLeaves();
        } catch (error) {
            console.log("Error approving leave:", error);
        }
    };

    const handleReject = async (id) => {
        try {
            await rejectLeave(id);
            loadLeaves();
        } catch (error) {
            console.log("Error rejecting leave:", error);
        }
    };

    const handleFilterByEmployee = async (empId) => {

        setFilterEmpId(empId);
        setFilterDeptId("");
        setFilterStartDate("");
        setFilterEndDate("");

        if (!empId) {
            loadLeaves();
            return;
        }

        try {
            const res = await getLeaveByEmployee(empId);
            setLeaves(res.data.data || res.data || []);
        } catch (error) {
            console.log("Error filtering by employee:", error);
        }
    };

    const handleFilterByDepartment = async (deptId) => {

        setFilterDeptId(deptId);
        setFilterEmpId("");
        setFilterStartDate("");
        setFilterEndDate("");

        if (!deptId) {
            loadLeaves();
            return;
        }

        try {
            const res = await getLeavesByDepartment(deptId);
            setLeaves(res.data.data || res.data || []);
        } catch (error) {
            console.log("Error filtering by department:", error);
        }
    };

    const handleFilterByDates = async () => {

        if (!filterStartDate || !filterEndDate) return;

        setFilterEmpId("");
        setFilterDeptId("");

        try {
            const res = await getLeavesBetweenDates(
                filterStartDate,
                filterEndDate
            );

            setLeaves(res.data.data || res.data || []);

        } catch (error) {
            console.log("Error filtering by dates:", error);
        }
    };

    const handleClearFilters = () => {
        setFilterEmpId("");
        setFilterDeptId("");
        setFilterStartDate("");
        setFilterEndDate("");
        setFilterStatus("All");

        loadLeaves();
    };

    const stats = {
        total: leaves.length,
        pending: leaves.filter(l => l.status === 'PENDING').length,
        approved: leaves.filter(l => l.status === 'APPROVED').length,
        rejected: leaves.filter(l => l.status === 'REJECTED').length,
    };

    return (
        <div className="p-8 bg-gray-50 min-h-screen">

            <div className="mb-8 flex justify-between items-center">
                <div>
                    <h1 className="text-3xl font-bold text-gray-800">
                        Leave Management
                    </h1>

                    <p className="text-gray-500 mt-1">
                        Review and manage employee leave requests.
                    </p>
                </div>

                <button
                onClick={() => navigate("/apply-leave")}
                className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition cursor-pointer"
                >
                    Apply Leave
                </button>
            </div>

            <LeaveStats stats={stats} />

            <LeaveFilters
                employees={employees}
                departments={departments}

                filterEmpId={filterEmpId}
                filterDeptId={filterDeptId}
                filterStartDate={filterStartDate}
                filterEndDate={filterEndDate}

                setFilterStartDate={setFilterStartDate}
                setFilterEndDate={setFilterEndDate}

                handleFilterByEmployee={handleFilterByEmployee}
                handleFilterByDepartment={handleFilterByDepartment}
                handleFilterByDates={handleFilterByDates}
                handleClearFilters={handleClearFilters}
            />

            <LeaveTable
                employees={employees}
                filteredLeaves={filteredLeaves}
                filterStatus={filterStatus}
                setFilterStatus={setFilterStatus}
                handleApprove={handleApprove}
                handleReject={handleReject}
            />

        </div>
    );
}

export default LeavePage;