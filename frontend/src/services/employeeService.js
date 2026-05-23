import api from "./api";

const API = "http://localhost:8080/api/employees";

export const getEmployees = () => api.get(API);

export const deleteEmployee = (id) => api.delete(`${API}/${id}`);

export const addEmployee = (employeeData) => api.post(API, employeeData);

export const getEmployeeById = (id) => api.get(`${API}/${id}`);

export const updateEmployee = (id,employeeData) => api.put(`${API}/${id}`,employeeData);

export const getEmployeesByDepartment = (deptId) => api.get(`${API}/department/${deptId}`);
