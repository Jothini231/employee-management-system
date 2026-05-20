import axios from "axios";

const API = "http://localhost:8080/api/dashboard";

export const getDashboardStats = () => axios.get(`${API}/stats`);

export const getEmployeesPerDepartment = () => axios.get(`${API}/employees-per-department`);

export const getLeaveStatistics = () => axios.get(`${API}/leave-statistics`);

export const getMonthlySalaryExpenses = () => axios.get(`${API}/monthly-salary-expenses`);

export const getRecentActivities = () => axios.get(`${API}/recent-activities`);

export const getNotifications = () => axios.get(`${API}/notifications`);
