import axios from "axios";

const API = "http://localhost:8080/api/employees";

export const getEmployees = () => axios.get(API);

export const deleteEmployee = (id) => axios.delete(`${API}/${id}`);

export const addEmployee = (employeeData) => axios.post(API, employeeData);

export const getEmployeeById = (id) => axios.get(`${API}/${id}`);

export const updateEmployee = (id,employeeData) => axios.put(`${API}/${id}`,employeeData);

export const getEmployeesByDepartment = (deptId) => axios.get(`${API}/department/${deptId}`);