import React, { useState, useEffect } from "react";
import { FaUsers, FaBuilding, FaCalendarTimes, FaMoneyBillWave } from "react-icons/fa";
import StatCard from "../../components/dashboard/StatCard";
import { EmployeeBarChart, LeavePieChart, SalaryLineChart } from "../../components/dashboard/Charts";
import RecentActivities from "../../components/dashboard/RecentActivities";
import NotificationsPanel from "../../components/dashboard/NotificationsPanel";
import {
  getDashboardStats,
  getEmployeesPerDepartment,
  getLeaveStatistics,
  getMonthlySalaryExpenses,
  getRecentActivities,
  getNotifications,
} from "../../services/dashboardService";

const Dashboard = () => {
  const [stats, setStats] = useState(null);
  const [deptData, setDeptData] = useState([]);
  const [leaveData, setLeaveData] = useState([]);
  const [salaryData, setSalaryData] = useState([]);
  const [activities, setActivities] = useState([]);
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        setLoading(true);
        const [
          statsRes,
          deptRes,
          leaveRes,
          salaryRes,
          activitiesRes,
          notificationsRes
        ] = await Promise.all([
          getDashboardStats(),
          getEmployeesPerDepartment(),
          getLeaveStatistics(),
          getMonthlySalaryExpenses(),
          getRecentActivities(),
          getNotifications(),
        ]);

        setStats(statsRes.data.data);
        setDeptData(deptRes.data.data);
        setLeaveData(leaveRes.data.data);
        setSalaryData(salaryRes.data.data);
        setActivities(activitiesRes.data.data);
        setNotifications(notificationsRes.data.data);
        setError(null);
      } catch (err) {
        console.error("Error fetching dashboard data:", err);
        setError("Failed to load dashboard data. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchDashboardData();
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center h-full min-h-[60vh]">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="bg-red-50 text-red-600 p-4 rounded-xl border border-red-200">
        {error}
      </div>
    );
  }

  return (
    <div className="space-y-6 max-w-[1600px] mx-auto">
      <div className="flex flex-col md:flex-row md:justify-between md:items-end mb-8 gap-4">
        <div>
          <h1 className="text-3xl font-bold text-gray-900 tracking-tight">Admin Dashboard</h1>
          <p className="text-sm text-gray-500 mt-1.5 font-medium">Welcome back! Here's an overview of your system.</p>
        </div>
      </div>

      
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <StatCard
          title="Total Employees"
          value={stats?.totalEmployees || 0}
          icon={<FaUsers className="text-2xl text-indigo-600" />}
          description="Active staff members"
          colorClass="bg-indigo-50"
        />
        <StatCard
          title="Departments"
          value={stats?.totalDepartments || 0}
          icon={<FaBuilding className="text-2xl text-blue-600" />}
          description="Total company departments"
          colorClass="bg-blue-50"
        />
        <StatCard
          title="On Leave"
          value={stats?.employeesOnLeave || 0}
          icon={<FaCalendarTimes className="text-2xl text-yellow-600" />}
          description="Currently approved leaves"
          colorClass="bg-yellow-50"
        />
        <StatCard
          title="Monthly Expense"
          value={`LKR${stats?.monthlySalaryExpense?.toLocaleString() || 0}`}
          icon={<FaMoneyBillWave className="text-2xl text-green-600" />}
          description="Current month payroll"
          colorClass="bg-green-50"
        />
      </div>

      
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <EmployeeBarChart data={deptData} />
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-1 xl:grid-cols-2 gap-6">
          <LeavePieChart data={leaveData} />
          <SalaryLineChart data={salaryData} />
        </div>
      </div>

      
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div className="lg:col-span-2">
          <RecentActivities activities={activities} />
        </div>
        <div className="lg:col-span-1">
          <NotificationsPanel notifications={notifications} />
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
